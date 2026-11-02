package com.ahhe.operations.service;

import static com.ahhe.operations.model.OperationContext.Role.*;
import static com.ahhe.operations.service.BusinessRecords.*;

import com.ahhe.operations.model.Checks;
import com.ahhe.operations.model.OperationContext;
import com.ahhe.operations.model.OperationContext.Outcome;
import com.ahhe.operations.model.OperationException;
import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;

public final class ReturnService {
  private final OperationEngine engine;
  private final InspectionService inspection;

  public ReturnService(OperationEngine engine, InspectionService inspection) {
    this.engine = engine;
    this.inspection = inspection;
  }

  public record RequestInput(
      String id,
      String orderLineId,
      BigDecimal quantity,
      String reason,
      String requestedRemedy,
      String policyId) {}

  public record AuthorizeInput(String rmaId, long expectedRevision) {}

  public record ReceiveInput(
      String id,
      String rmaId,
      String trackingReference,
      String location,
      String conditionEvidenceId) {}

  public record IdentifyInput(
      String packageId,
      long expectedRevision,
      String rmaId,
      BigDecimal receivedQuantity,
      String receivedPartRevision,
      String reason) {}

  public record RemedyInput(
      String id,
      String rmaId,
      long expectedRevision,
      String kind,
      String gatePlanId,
      long expectedGatePlanRevision,
      List<String> inspectionIds,
      String reason) {}

  public Outcome policy(OperationContext context, ReturnPolicy policy) {
    return engine.execute(
        context,
        COMMERCIAL,
        "return-policy.approve",
        policy,
        unit -> {
          unit.requireInternal();
          unit.requireBeneficiaryCustomer("");
          if (!policy.effectiveUntil().isAfter(engine.now())) {
            throw new IllegalArgumentException("Approved policy must have a future expiry.");
          }
          unit.create("returnpolicy", policy.id(), "", policy);
        });
  }

  public Outcome request(OperationContext context, RequestInput input) {
    var role =
        engine.actorGrant(context.businessId(), context.principalId()).customerId().isEmpty()
            ? RETURNS
            : CUSTOMER;
    return engine.execute(
        context,
        role,
        "return.request",
        input,
        unit -> {
          Checks.id(input.id());
          Checks.positive(input.quantity());
          Checks.text(input.reason(), 1000);
          if (!List.of("REFUND", "CREDIT", "REPLACEMENT", "REPAIR")
              .contains(input.requestedRemedy())) {
            throw new IllegalArgumentException("Unsupported requested remedy.");
          }
          var order = unit.load("orderline", input.orderLineId(), OrderLine.class);
          unit.requireBeneficiaryCustomer(order.customerId());
          var policy = unit.load("returnpolicy", input.policyId(), ReturnPolicy.class);
          var expires = order.deliveredAt().plus(policy.windowDays(), ChronoUnit.DAYS);
          unit.boundValidity(expires);
          unit.boundValidity(policy.effectiveUntil());
          if (!order.verified()
              || !engine.now().isBefore(expires)
              || !engine.now().isBefore(policy.effectiveUntil())
              || input.quantity().compareTo(order.quantity().subtract(order.returnAllocated()))
                  > 0) {
            throw OperationException.unresolved(
                "Order, window, policy or eligible quantity requires review.");
          }
          unit.create(
              "rma",
              input.id(),
              order.customerId(),
              new Rma(
                  input.id(),
                  order.customerId(),
                  order.id(),
                  input.quantity(),
                  input.reason(),
                  input.requestedRemedy(),
                  policy.id(),
                  unit.revision("returnpolicy", policy.id()),
                  expires,
                  ReturnStatus.REQUESTED));
        });
  }

  public Outcome authorize(OperationContext context, AuthorizeInput input) {
    return engine.execute(
        context,
        RETURNS,
        "return.authorize",
        input,
        unit -> {
          unit.requireInternal();
          var rma = unit.load("rma", input.rmaId(), Rma.class);
          unit.expect("rma", rma.id(), input.expectedRevision());
          unit.requireBeneficiaryCustomer(rma.customerId());
          var policy = unit.load("returnpolicy", rma.policyId(), ReturnPolicy.class);
          unit.expect("returnpolicy", policy.id(), rma.policyRevision());
          var order = unit.load("orderline", rma.orderLineId(), OrderLine.class);
          unit.boundValidity(rma.expiresAt());
          unit.boundValidity(policy.effectiveUntil());
          if (rma.status() != ReturnStatus.REQUESTED
              || !engine.now().isBefore(rma.expiresAt())
              || !engine.now().isBefore(policy.effectiveUntil())
              || rma.quantity().compareTo(order.quantity().subtract(order.returnAllocated())) > 0) {
            throw OperationException.unresolved(
                "Return authorization is stale or exceeds remaining eligibility.");
          }
          unit.update(
              "orderline",
              order.id(),
              order.customerId(),
              allocations(
                  order, order.returnAllocated().add(rma.quantity()), order.remedyAllocated()));
          unit.update("rma", rma.id(), rma.customerId(), status(rma, ReturnStatus.AUTHORIZED));
        });
  }

  public Outcome receive(OperationContext context, ReceiveInput input) {
    return engine.execute(
        context,
        RETURNS,
        "return-package.receive-in-quarantine",
        input,
        unit -> {
          unit.requireInternal();
          Checks.id(input.id());
          Checks.text(input.trackingReference(), 200);
          Checks.text(input.location(), 200);
          InventoryService.evidence(unit, List.of(input.conditionEvidenceId()), true);
          String customerId = "";
          if (input.rmaId() != null && !input.rmaId().isEmpty()) {
            var rma = unit.load("rma", input.rmaId(), Rma.class);
            unit.requireBeneficiaryCustomer(rma.customerId());
            if (rma.status() != ReturnStatus.AUTHORIZED
                || !engine.now().isBefore(rma.expiresAt())) {
              throw OperationException.unresolved("Expected return is not active.");
            }
            customerId = rma.customerId();
          } else {
            unit.requireBeneficiaryCustomer("");
          }
          unit.create(
              "tracking",
              OperationEngine.digest(input.trackingReference()),
              customerId,
              input.id());
          unit.create(
              "package",
              input.id(),
              customerId,
              new ReturnPackage(
                  input.id(),
                  customerId,
                  input.rmaId() == null ? "" : input.rmaId(),
                  input.trackingReference(),
                  input.location(),
                  input.conditionEvidenceId(),
                  "RECEIVING_QUARANTINE",
                  List.of()));
          var holdId = OperationEngine.qualityHoldId("package", input.id());
          unit.create("qualityhold", holdId, customerId, new QualityHold(holdId, 0));
        });
  }

  public Outcome identify(OperationContext context, IdentifyInput input) {
    return engine.execute(
        context,
        RETURNS,
        "return-package.identify-contents",
        input,
        unit -> {
          unit.requireInternal();
          Checks.text(input.reason(), 1000);
          var item = unit.load("package", input.packageId(), ReturnPackage.class);
          unit.expect("package", item.id(), input.expectedRevision());
          var rma = unit.load("rma", input.rmaId(), Rma.class);
          var order = unit.load("orderline", rma.orderLineId(), OrderLine.class);
          Checks.positive(input.receivedQuantity());
          Checks.text(input.receivedPartRevision(), 200);
          unit.requireBeneficiaryCustomer(rma.customerId());
          if (!item.status().equals("RECEIVING_QUARANTINE")
              || rma.status() != ReturnStatus.AUTHORIZED
              || (!item.rmaId().isEmpty() && !item.rmaId().equals(rma.id()))
              || input.receivedQuantity().compareTo(rma.quantity()) != 0
              || !input.receivedPartRevision().equals(order.partRevision())) {
            throw OperationException.unresolved(
                "Package cannot be identified to this active return.");
          }
          unit.create("rmapackage", rma.id(), rma.customerId(), item.id());
          unit.update(
              "package",
              item.id(),
              rma.customerId(),
              new ReturnPackage(
                  item.id(),
                  rma.customerId(),
                  rma.id(),
                  item.trackingReference(),
                  item.location(),
                  item.conditionEvidenceId(),
                  "IDENTIFIED",
                  List.of(
                      new ReturnedItem(input.receivedPartRevision(), input.receivedQuantity()))));
          var hold =
              unit.load(
                  "qualityhold",
                  OperationEngine.qualityHoldId("package", item.id()),
                  QualityHold.class);
          unit.update("qualityhold", hold.id(), rma.customerId(), hold);
          unit.update("rma", rma.id(), rma.customerId(), status(rma, ReturnStatus.IDENTIFIED));
        });
  }

  public Outcome remedy(OperationContext context, RemedyInput input) {
    return engine.execute(
        context,
        COMMERCIAL,
        "remedy.approve-pending-execution",
        input,
        unit -> {
          unit.requireInternal();
          Checks.id(input.id());
          Checks.text(input.reason(), 1000);
          Checks.bounded(input.inspectionIds(), 10);
          var rma = unit.load("rma", input.rmaId(), Rma.class);
          unit.expect("rma", rma.id(), input.expectedRevision());
          unit.requireBeneficiaryCustomer(rma.customerId());
          var order = unit.load("orderline", rma.orderLineId(), OrderLine.class);
          var packageId = unit.load("rmapackage", rma.id(), String.class);
          var gate = unit.load("gateplan", input.gatePlanId(), GatePlan.class);
          unit.expect("gateplan", gate.id(), input.expectedGatePlanRevision());
          if (!gate.operation().equals("RETURN_INSPECTION")
              || !gate.subjectType().equals("package")
              || !gate.subjectId().equals(packageId)
              || !gate.orderLineId().equals(order.id())) {
            throw OperationException.unresolved(
                "An approved inspection plan for the actual returned package is required.");
          }
          if (rma.status() != ReturnStatus.IDENTIFIED
              || input.inspectionIds().isEmpty()
              || !List.of("REFUND", "CREDIT", "REPLACEMENT", "REPAIR").contains(input.kind())
              || order.remedyAllocated().add(rma.quantity()).compareTo(order.quantity()) > 0) {
            throw OperationException.unresolved(
                "Return, inspection or remaining remedy allocation is unresolved.");
          }
          var covered = new java.util.HashSet<String>();
          for (var id : input.inspectionIds()) {
            var result = inspection.evaluated(unit, id, order.customerId());
            if (!result.subjectType().equals("package")
                || !result.subjectId().equals(packageId)
                || result.disposition() == Disposition.INCONCLUSIVE
                || !covered.add(result.planId())) {
              throw OperationException.unresolved(
                  "Inspect the actual returned package, not original production.");
            }
          }
          if (!covered.equals(java.util.Set.copyOf(gate.requiredPlanIds()))) {
            throw OperationException.unresolved(
                "Every required return inspection must have a conclusive result.");
          }
          // Manual pilot permits exact allocations only; no implicit financial rounding policy.
          BigDecimal fraction;
          try {
            fraction = rma.quantity().divide(order.quantity());
          } catch (ArithmeticException exception) {
            throw OperationException.unresolved(
                "Partial remedy requires an approved rounding policy.");
          }
          var item = order.itemAmount().multiply(fraction);
          var tax = order.taxAmount().multiply(fraction);
          var discount = order.discountAmount().multiply(fraction);
          unit.create("remedy-for-rma", rma.id(), rma.customerId(), input.id());
          unit.create(
              "remedy",
              input.id(),
              rma.customerId(),
              new Remedy(
                  input.id(),
                  rma.customerId(),
                  rma.id(),
                  input.kind(),
                  rma.quantity(),
                  item,
                  tax,
                  discount,
                  item.add(tax).subtract(discount),
                  order.currency(),
                  "APPROVED_EXECUTION_UNAVAILABLE",
                  List.copyOf(input.inspectionIds())));
          unit.update(
              "orderline",
              order.id(),
              order.customerId(),
              allocations(
                  order, order.returnAllocated(), order.remedyAllocated().add(rma.quantity())));
          unit.update("rma", rma.id(), rma.customerId(), status(rma, ReturnStatus.REMEDY_APPROVED));
        });
  }

  private Rma status(Rma rma, ReturnStatus status) {
    return new Rma(
        rma.id(),
        rma.customerId(),
        rma.orderLineId(),
        rma.quantity(),
        rma.reason(),
        rma.requestedRemedy(),
        rma.policyId(),
        rma.policyRevision(),
        rma.expiresAt(),
        status);
  }

  private OrderLine allocations(OrderLine line, BigDecimal returned, BigDecimal remedies) {
    return new OrderLine(
        line.id(),
        line.customerId(),
        line.sourceReference(),
        line.partRevision(),
        line.quantity(),
        returned,
        remedies,
        line.itemAmount(),
        line.taxAmount(),
        line.discountAmount(),
        line.currency(),
        line.deliveredAt(),
        line.verified());
  }
}
