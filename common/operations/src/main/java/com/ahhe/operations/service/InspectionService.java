package com.ahhe.operations.service;

import static com.ahhe.operations.model.OperationContext.Role.QUALITY;
import static com.ahhe.operations.service.BusinessRecords.*;

import com.ahhe.operations.model.Checks;
import com.ahhe.operations.model.OperationContext;
import com.ahhe.operations.model.OperationContext.Outcome;
import com.ahhe.operations.model.OperationException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public final class InspectionService {
  private final OperationEngine engine;

  public InspectionService(OperationEngine engine) {
    this.engine = engine;
  }

  public record MeasureInput(
      String id,
      String planId,
      long expectedPlanRevision,
      String subjectType,
      String subjectId,
      String reservationId,
      BigDecimal value,
      BigDecimal uncertainty,
      String unit,
      Instant measuredAt,
      List<String> evidenceIds) {}

  public record ReleaseInput(
      String id,
      String gatePlanId,
      long expectedGatePlanRevision,
      List<String> requiredResultIds) {}

  public record ResolveInput(
      String findingId, long expectedRevision, String acceptedResultId, String reason) {}

  public Outcome plan(OperationContext context, InspectionPlan plan) {
    return engine.execute(
        context,
        QUALITY,
        "inspection-plan.approve",
        plan,
        unit -> {
          unit.requireInternal();
          var order = unit.load("orderline", plan.orderLineId(), OrderLine.class);
          unit.requireBeneficiaryCustomer(order.customerId());
          if (!plan.customerId().equals(order.customerId())) {
            throw OperationException.unresolved("Plan customer does not match the order.");
          }
          unit.load("asset", plan.gaugeId(), Asset.class);
          unit.create("inspectionplan", plan.id(), plan.customerId(), plan);
        });
  }

  public Outcome measure(OperationContext context, MeasureInput input) {
    return engine.execute(
        context,
        QUALITY,
        "inspection.measure",
        input,
        unit -> {
          unit.requireInternal();
          Checks.id(input.id());
          Checks.decimal(input.value());
          Checks.nonnegative(input.uncertainty());
          var plan = unit.load("inspectionplan", input.planId(), InspectionPlan.class);
          unit.expect("inspectionplan", plan.id(), input.expectedPlanRevision());
          unit.requireBeneficiaryCustomer(plan.customerId());
          requireSubject(unit, input.subjectType(), input.subjectId(), input.reservationId(), plan);
          CareService.requireEligible(unit, plan.gaugeId(), engine.now());
          var gauge = unit.load("asset", plan.gaugeId(), Asset.class);
          CareService.requireQualifiedAt(unit, gauge, input.measuredAt());
          unit.boundValidity(gauge.qualifiedUntil());
          if (!gauge.qualifiedUntil().isAfter(engine.now())
              || input.measuredAt().isAfter(engine.now())
              || !input.unit().equals(plan.unit())
              || input.uncertainty().compareTo(plan.maximumUncertainty()) > 0) {
            throw OperationException.unresolved(
                "Units, uncertainty, timestamp or gauge qualification are unresolved.");
          }
          InventoryService.evidence(unit, input.evidenceIds(), plan.rawEvidenceRequired());
          var holdId = OperationEngine.qualityHoldId(input.subjectType(), input.subjectId());
          var hold = unit.load("qualityhold", holdId, QualityHold.class);
          var disposition =
              input.value().add(input.uncertainty()).compareTo(plan.lower()) < 0
                      || input.value().subtract(input.uncertainty()).compareTo(plan.upper()) > 0
                  ? Disposition.NONCONFORMING
                  : input.value().subtract(input.uncertainty()).compareTo(plan.lower()) >= 0
                          && input.value().add(input.uncertainty()).compareTo(plan.upper()) <= 0
                      ? Disposition.ACCEPTED
                      : Disposition.INCONCLUSIVE;
          var result =
              new InspectionResult(
                  input.id(),
                  plan.customerId(),
                  plan.id(),
                  unit.revision("inspectionplan", plan.id()),
                  input.subjectType(),
                  input.subjectId(),
                  unit.revision(input.subjectType(), input.subjectId()),
                  input.reservationId(),
                  input.subjectType().equals("inventory")
                      ? unit.revision("reservation", input.reservationId())
                      : 0,
                  unit.revision("qualityhold", holdId),
                  input.value(),
                  input.uncertainty(),
                  input.unit(),
                  gauge.id(),
                  unit.revision("asset", gauge.id()),
                  input.measuredAt(),
                  disposition,
                  List.copyOf(input.evidenceIds()));
          unit.create("inspectionresult", input.id(), plan.customerId(), result);
          if (disposition != Disposition.ACCEPTED) {
            unit.update(
                "qualityhold",
                holdId,
                plan.customerId(),
                new QualityHold(holdId, hold.openFindings() + 1));
            unit.create(
                "nonconformance",
                input.id(),
                plan.customerId(),
                new Nonconformance(input.id(), plan.customerId(), input.id(), "OPEN", "", ""));
          }
        });
  }

  public Outcome resolve(OperationContext context, ResolveInput input) {
    return engine.execute(
        context,
        QUALITY,
        "nonconformance.resolve-after-reinspection",
        input,
        unit -> {
          unit.requireInternal();
          Checks.text(input.reason(), 1000);
          var finding = unit.load("nonconformance", input.findingId(), Nonconformance.class);
          unit.expect("nonconformance", finding.id(), input.expectedRevision());
          var original = unit.load("inspectionresult", finding.resultId(), InspectionResult.class);
          var accepted = accepted(unit, input.acceptedResultId(), original.customerId());
          if (!finding.status().equals("OPEN")
              || !original.subjectType().equals(accepted.subjectType())
              || !original.subjectId().equals(accepted.subjectId())
              || !original.planId().equals(accepted.planId())
              || accepted.measuredAt().isBefore(original.measuredAt())
              || accepted.qualityHoldRevision() <= original.qualityHoldRevision()) {
            throw OperationException.unresolved(
                "Finding requires accepted later reinspection of the same subject/characteristic.");
          }
          var holdId = OperationEngine.qualityHoldId(original.subjectType(), original.subjectId());
          var hold = unit.load("qualityhold", holdId, QualityHold.class);
          if (hold.openFindings() <= 0) {
            throw OperationException.unresolved("Quality hold history is inconsistent.");
          }
          unit.update(
              "qualityhold",
              holdId,
              original.customerId(),
              new QualityHold(holdId, hold.openFindings() - 1));
          unit.update(
              "nonconformance",
              finding.id(),
              finding.customerId(),
              new Nonconformance(
                  finding.id(),
                  finding.customerId(),
                  finding.resultId(),
                  "RESOLVED_REINSPECTED",
                  accepted.id(),
                  input.reason()));
        });
  }

  public Outcome release(OperationContext context, ReleaseInput input) {
    return engine.execute(
        context,
        QUALITY,
        "operation.release",
        input,
        unit -> {
          unit.requireInternal();
          Checks.id(input.id());
          Checks.bounded(input.requiredResultIds(), 10);
          if (input.requiredResultIds().isEmpty()) {
            throw OperationException.unresolved("Release requires actual measured checkpoints.");
          }
          var gate = unit.load("gateplan", input.gatePlanId(), GatePlan.class);
          unit.expect("gateplan", gate.id(), input.expectedGatePlanRevision());
          var order = unit.load("orderline", gate.orderLineId(), OrderLine.class);
          unit.requireBeneficiaryCustomer(order.customerId());
          CareService.requireEligible(unit, gate.machineId(), engine.now());
          var covered = new java.util.HashSet<String>();
          for (var id : input.requiredResultIds()) {
            var result = accepted(unit, id, order.customerId());
            var plan = unit.load("inspectionplan", result.planId(), InspectionPlan.class);
            if (!plan.orderLineId().equals(order.id())
                || !result.subjectType().equals(gate.subjectType())
                || !result.subjectId().equals(gate.subjectId())
                || !covered.add(plan.id())) {
              throw OperationException.unresolved("A checkpoint belongs to another order.");
            }
            requireNoHold(unit, result);
            if (result.subjectType().equals("inventory")) {
              var reservation = unit.load("reservation", result.reservationId(), Reservation.class);
              if (!java.util.Set.of("RESERVED", "ISSUED").contains(reservation.status())) {
                throw OperationException.unresolved(
                    "A released reservation cannot authorize production.");
              }
            }
          }
          if (!covered.equals(java.util.Set.copyOf(gate.requiredPlanIds()))) {
            throw OperationException.unresolved(
                "Every checkpoint in the approved gate plan must be satisfied.");
          }
          unit.create(
              "release",
              input.id(),
              order.customerId(),
              new Release(
                  input.id(),
                  order.customerId(),
                  order.id(),
                  gate.operation(),
                  List.copyOf(input.requiredResultIds()),
                  gate.machineId(),
                  engine.now(),
                  unit.validUntil()));
        });
  }

  public Outcome gatePlan(OperationContext context, GatePlan input) {
    return engine.execute(
        context,
        QUALITY,
        "gate-plan.approve",
        input,
        unit -> {
          unit.requireInternal();
          Checks.id(input.id());
          Checks.text(input.operation(), 200);
          Checks.bounded(input.requiredPlanIds(), 10);
          if (input.requiredPlanIds().isEmpty()
              || java.util.Set.copyOf(input.requiredPlanIds()).size()
                  != input.requiredPlanIds().size()) {
            throw new IllegalArgumentException(
                "A gate requires distinct approved inspection plans.");
          }
          var order = unit.load("orderline", input.orderLineId(), OrderLine.class);
          unit.requireBeneficiaryCustomer(order.customerId());
          if (!order.customerId().equals(input.customerId())) {
            throw OperationException.denied();
          }
          unit.load("asset", input.machineId(), Asset.class);
          if (!java.util.Set.of("inventory", "package").contains(input.subjectType())) {
            throw new IllegalArgumentException("Unsupported physical subject.");
          }
          unit.load(
              "qualityhold",
              OperationEngine.qualityHoldId(input.subjectType(), input.subjectId()),
              QualityHold.class);
          for (var id : input.requiredPlanIds()) {
            var plan = unit.load("inspectionplan", id, InspectionPlan.class);
            if (!plan.orderLineId().equals(order.id())) {
              throw OperationException.unresolved(
                  "Gate plans must belong to the exact order line.");
            }
          }
          unit.create("gateplan", input.id(), order.customerId(), input);
        });
  }

  public InspectionResult accepted(OperationEngine.Unit unit, String id, String customerId) {
    var result = evaluated(unit, id, customerId);
    if (result.disposition() != Disposition.ACCEPTED) {
      throw OperationException.unresolved("Checkpoint is not accepted.");
    }
    return result;
  }

  public InspectionResult evaluated(OperationEngine.Unit unit, String id, String customerId) {
    var result = unit.load("inspectionresult", id, InspectionResult.class);
    var plan = unit.load("inspectionplan", result.planId(), InspectionPlan.class);
    unit.expect("inspectionplan", plan.id(), result.planRevision());
    requireSubject(unit, result.subjectType(), result.subjectId(), result.reservationId(), plan);
    unit.expect(result.subjectType(), result.subjectId(), result.subjectRevision());
    if (result.subjectType().equals("inventory")) {
      unit.expect("reservation", result.reservationId(), result.reservationRevision());
    }
    CareService.requireEligible(unit, result.gaugeId(), engine.now());
    var gauge = unit.load("asset", result.gaugeId(), Asset.class);
    CareService.requireQualifiedAt(unit, gauge, result.measuredAt());
    unit.boundValidity(gauge.qualifiedUntil());
    unit.expect("asset", gauge.id(), result.gaugeRevision());
    InventoryService.evidence(unit, result.evidenceIds(), plan.rawEvidenceRequired());
    if (!result.customerId().equals(customerId) || !gauge.qualifiedUntil().isAfter(engine.now())) {
      throw OperationException.unresolved("Checkpoint is not accepted or currently qualified.");
    }
    return result;
  }

  public static void requireNoHold(OperationEngine.Unit unit, InspectionResult result) {
    var hold =
        unit.load(
            "qualityhold",
            OperationEngine.qualityHoldId(result.subjectType(), result.subjectId()),
            QualityHold.class);
    if (hold.openFindings() != 0) {
      throw OperationException.unresolved("Unresolved nonconformance still contains the subject.");
    }
  }

  private void requireSubject(
      OperationEngine.Unit unit,
      String type,
      String id,
      String reservationId,
      InspectionPlan plan) {
    switch (type) {
      case "inventory" -> {
        var item = unit.load("inventory", id, Inventory.class);
        var reservation = unit.load("reservation", reservationId, Reservation.class);
        if (!reservation.inventoryId().equals(item.id())
            || !reservation.orderLineId().equals(plan.orderLineId())
            || !java.util.Set.of("RESERVED", "ISSUED", "RELEASED").contains(reservation.status())
            || !item.category().equals("WIP")
            || !item.unit().equals("pieces")
            || item.quantity().compareTo(BigDecimal.ONE) != 0
            || reservation.quantity().compareTo(BigDecimal.ONE) != 0) {
          throw OperationException.unresolved(
              "Physical inventory is not bound to this exact order checkpoint.");
        }
      }
      case "package" -> {
        var item = unit.load("package", id, ReturnPackage.class);
        var rma = unit.load("rma", item.rmaId(), Rma.class);
        if (!rma.orderLineId().equals(plan.orderLineId())
            || !item.status().equals("IDENTIFIED")
            || item.confirmedContents().size() != 1
            || item.confirmedContents().getFirst().quantity().compareTo(BigDecimal.ONE) != 0) {
          throw OperationException.unresolved(
              "Returned contents are not identified to this checkpoint's order.");
        }
      }
      default ->
          throw new IllegalArgumentException(
              "Only inventory or identified return-package subjects are supported.");
    }
  }
}
