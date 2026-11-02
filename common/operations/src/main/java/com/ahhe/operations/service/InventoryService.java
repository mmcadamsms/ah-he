package com.ahhe.operations.service;

import static com.ahhe.operations.model.OperationContext.Role.*;
import static com.ahhe.operations.service.BusinessRecords.*;

import com.ahhe.operations.model.Checks;
import com.ahhe.operations.model.OperationContext;
import com.ahhe.operations.model.OperationContext.Grant;
import com.ahhe.operations.model.OperationContext.Outcome;
import com.ahhe.operations.model.OperationException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public final class InventoryService {
  private final OperationEngine engine;

  public InventoryService(OperationEngine engine) {
    this.engine = engine;
  }

  public record RevisionInput(long expectedRevision, String reason, List<String> evidenceIds) {}

  public record ReserveInput(
      String id,
      String inventoryId,
      long expectedRevision,
      String orderLineId,
      BigDecimal quantity) {}

  public record SplitInput(
      String inventoryId,
      long expectedRevision,
      List<Inventory> children,
      BigDecimal consumedQuantity,
      String reason) {}

  public record MoveInput(
      String id, String inventoryId, long expectedRevision, String destination) {}

  public record AcceptMovement(String movementId, long expectedRevision) {}

  public record RevokeGrant(String principalId, long expectedRevision, String reason) {}

  public record IssueInput(
      String reservationId, long expectedRevision, Inventory wip, String reason) {}

  public record CancelReservation(String reservationId, long expectedRevision, String reason) {}

  public record ReplaceGrant(Grant grant, long expectedRevision, String reason) {}

  public Outcome revoke(OperationContext context, RevokeGrant input) {
    var customerActor =
        !engine.actorGrant(context.businessId(), context.principalId()).customerId().isEmpty();
    return engine.execute(
        context,
        customerActor ? CUSTOMER_ADMIN : ADMIN,
        "grant.revoke",
        input,
        unit -> {
          Checks.text(input.reason(), 500);
          var grant = unit.load("grant", input.principalId(), Grant.class);
          unit.expect("grant", grant.id(), input.expectedRevision());
          if (customerActor && !unit.actor().customerId().equals(grant.customerId())) {
            throw OperationException.denied();
          }
          unit.update(
              "grant",
              grant.id(),
              grant.customerId(),
              new Grant(
                  grant.id(),
                  grant.kind(),
                  grant.customerId(),
                  grant.roles(),
                  false,
                  grant.expiresAt()));
        });
  }

  public Outcome customer(OperationContext context, Customer customer) {
    return engine.execute(
        context,
        COMMERCIAL,
        "customer.create",
        customer,
        unit -> {
          unit.requireInternal();
          unit.create("customer", customer.id(), customer.id(), customer);
        });
  }

  public Outcome replaceGrant(OperationContext context, ReplaceGrant input) {
    var customerActor =
        !engine.actorGrant(context.businessId(), context.principalId()).customerId().isEmpty();
    return engine.execute(
        context,
        customerActor ? CUSTOMER_ADMIN : ADMIN,
        "grant.replace-reviewed",
        input,
        unit -> {
          Checks.text(input.reason(), 1000);
          var replacement = Checks.required(input.grant());
          var current = unit.load("grant", replacement.id(), Grant.class);
          unit.expect("grant", current.id(), input.expectedRevision());
          if (!current.customerId().equals(replacement.customerId())
              || current.kind() != replacement.kind()
              || replacement.roles().isEmpty()
              || !replacement.expiresAt().isAfter(engine.now())
              || (!replacement.customerId().isEmpty()
                  && !Set.of(CUSTOMER, CUSTOMER_ADMIN).containsAll(replacement.roles()))
              || (customerActor && !unit.actor().customerId().equals(replacement.customerId()))) {
            throw OperationException.denied();
          }
          unit.update("grant", replacement.id(), replacement.customerId(), replacement);
        });
  }

  public Outcome orderLine(OperationContext context, OrderLine line) {
    return engine.execute(
        context,
        COMMERCIAL,
        "orderline.import",
        line,
        unit -> {
          unit.requireInternal();
          if (!unit.load("customer", line.customerId(), Customer.class).verified()
              || !line.verified()
              || line.returnAllocated().signum() != 0
              || line.remedyAllocated().signum() != 0
              || line.deliveredAt().isAfter(engine.now())) {
            throw OperationException.unresolved(
                "Verified customer/order facts and zero initial allocations are required.");
          }
          unit.create(
              "order-source",
              OperationEngine.digest(line.sourceReference()),
              line.customerId(),
              line.id());
          unit.create("orderline", line.id(), line.customerId(), line);
        });
  }

  public Outcome provision(OperationContext context, Grant grant) {
    boolean customerProvisioning =
        !engine.actorGrant(context.businessId(), context.principalId()).customerId().isEmpty();
    return engine.execute(
        context,
        customerProvisioning ? CUSTOMER_ADMIN : ADMIN,
        "grant.provision",
        grant,
        unit -> {
          if (!unit.actor().customerId().isEmpty()) {
            if (!unit.actor().customerId().equals(grant.customerId())
                || !Set.of(CUSTOMER, CUSTOMER_ADMIN).containsAll(grant.roles())) {
              throw OperationException.denied();
            }
          } else {
            unit.requireInternal();
          }
          if (!grant.customerId().isEmpty()) {
            unit.load("customer", grant.customerId(), Customer.class);
            if (!Set.of(CUSTOMER, CUSTOMER_ADMIN).containsAll(grant.roles())) {
              throw OperationException.denied();
            }
          }
          if (grant.roles().isEmpty() || !grant.expiresAt().isAfter(engine.now())) {
            throw new IllegalArgumentException(
                "A nonempty role grant with a future expiry is required.");
          }
          if ((grant.kind() == OperationContext.PrincipalKind.HUMAN
                  || !grant.customerId().isEmpty())
              && !grant.id().matches("[a-f0-9]{64}")) {
            throw new IllegalArgumentException(
                "API user grants require the lowercase SHA-256 issuer/subject principal ID.");
          }
          unit.create("grant", grant.id(), grant.customerId(), grant);
        });
  }

  public Outcome receive(OperationContext context, Inventory input) {
    return engine.execute(
        context,
        INVENTORY,
        "inventory.receive",
        input,
        unit -> {
          unit.requireInternal();
          if (input.quantity().signum() <= 0
              || input.reserved().signum() != 0
              || !input.parents().isEmpty()
              || input.condition() != Condition.QUARANTINED) {
            throw new IllegalArgumentException(
                "Receipts require positive unreserved stock in quarantine.");
          }
          if (!input.customerId().isEmpty()) {
            unit.load("customer", input.customerId(), Customer.class);
          }
          unit.create("inventory", input.id(), input.customerId(), input);
          var holdId = OperationEngine.qualityHoldId("inventory", input.id());
          unit.create("qualityhold", holdId, input.customerId(), new QualityHold(holdId, 0));
        });
  }

  public Outcome qualify(OperationContext context, String id, RevisionInput input) {
    return engine.execute(
        context,
        QUALITY,
        "inventory.qualify",
        List.of(id, input),
        unit -> {
          unit.requireInternal();
          var item = unit.load("inventory", id, Inventory.class);
          unit.expect("inventory", id, input.expectedRevision());
          Checks.text(input.reason(), 500);
          if (item.condition() == Condition.CONSUMED) {
            throw OperationException.unresolved("Consumed stock cannot be qualified.");
          }
          if (unit.load(
                      "qualityhold",
                      OperationEngine.qualityHoldId("inventory", id),
                      QualityHold.class)
                  .openFindings()
              != 0) {
            throw OperationException.unresolved(
                "Resolve nonconformance and reinspection before qualification.");
          }
          evidence(unit, input.evidenceIds(), true);
          unit.update(
              "inventory",
              id,
              item.customerId(),
              copy(
                  item,
                  item.quantity(),
                  item.reserved(),
                  item.location(),
                  item.custodian(),
                  Condition.QUALIFIED));
        });
  }

  public Outcome reserve(OperationContext context, ReserveInput input) {
    return engine.execute(
        context,
        INVENTORY,
        "inventory.reserve",
        input,
        unit -> {
          unit.requireInternal();
          Checks.id(input.id());
          Checks.positive(input.quantity());
          var item = unit.load("inventory", input.inventoryId(), Inventory.class);
          var order = unit.load("orderline", input.orderLineId(), OrderLine.class);
          unit.requireBeneficiaryCustomer(order.customerId());
          unit.expect("inventory", item.id(), input.expectedRevision());
          requireNoQualityHold(unit, item.id());
          if (item.condition() != Condition.QUALIFIED
              || item.quantity().subtract(item.reserved()).compareTo(input.quantity()) < 0
              || (!item.customerId().isEmpty() && !item.customerId().equals(order.customerId()))) {
            throw OperationException.unresolved(
                "Stock is unqualified, insufficient or belongs to another customer scope.");
          }
          unit.update(
              "inventory",
              item.id(),
              item.customerId(),
              copy(
                  item,
                  item.quantity(),
                  item.reserved().add(input.quantity()),
                  item.location(),
                  item.custodian(),
                  item.condition()));
          unit.create(
              "reservation",
              input.id(),
              order.customerId(),
              new Reservation(
                  input.id(),
                  item.id(),
                  order.id(),
                  order.customerId(),
                  input.quantity(),
                  "RESERVED"));
        });
  }

  public Outcome split(OperationContext context, SplitInput input) {
    return engine.execute(
        context,
        INVENTORY,
        "inventory.split",
        input,
        unit -> {
          unit.requireInternal();
          var parent = unit.load("inventory", input.inventoryId(), Inventory.class);
          unit.expect("inventory", parent.id(), input.expectedRevision());
          requireNoQualityHold(unit, parent.id());
          Checks.bounded(input.children(), 20);
          Checks.nonnegative(input.consumedQuantity());
          Checks.text(input.reason(), 500);
          if (input.children().isEmpty()
              || parent.reserved().signum() != 0
              || parent.condition() != Condition.QUALIFIED) {
            throw OperationException.unresolved(
                "Split requires unreserved qualified stock and bounded children.");
          }
          var total = input.consumedQuantity();
          for (var child : input.children()) {
            if (!child.unit().equals(parent.unit())
                || !child.materialLot().equals(parent.materialLot())
                || !child.owner().equals(parent.owner())
                || !child.customerId().equals(parent.customerId())
                || child.reserved().signum() != 0
                || child.quantity().signum() <= 0
                || child.condition() != Condition.QUALIFIED
                || !child.parents().equals(List.of(parent.id()))
                || !child.location().equals(parent.location())
                || !child.custodian().equals(parent.custodian())) {
              throw OperationException.unresolved(
                  "Children must preserve units, ownership, lot, custody and genealogy.");
            }
            total = total.add(child.quantity());
            unit.create("inventory", child.id(), child.customerId(), child);
            var holdId = OperationEngine.qualityHoldId("inventory", child.id());
            unit.create("qualityhold", holdId, child.customerId(), new QualityHold(holdId, 0));
          }
          if (total.compareTo(parent.quantity()) != 0) {
            throw OperationException.unresolved(
                "Consumed and retained quantities must exactly reconcile.");
          }
          unit.update(
              "inventory",
              parent.id(),
              parent.customerId(),
              copy(
                  parent,
                  BigDecimal.ZERO,
                  BigDecimal.ZERO,
                  parent.location(),
                  parent.custodian(),
                  Condition.CONSUMED));
        });
  }

  public Outcome move(OperationContext context, MoveInput input) {
    return engine.execute(
        context,
        INVENTORY,
        "movement.request",
        input,
        unit -> {
          unit.requireInternal();
          Checks.id(input.id());
          Checks.text(input.destination(), 200);
          var item = unit.load("inventory", input.inventoryId(), Inventory.class);
          unit.expect("inventory", item.id(), input.expectedRevision());
          requireMovable(item);
          unit.create(
              "movement",
              input.id(),
              item.customerId(),
              new Movement(
                  input.id(),
                  item.id(),
                  item.customerId(),
                  item.location(),
                  input.destination(),
                  context.principalId(),
                  "REQUESTED",
                  ""));
        });
  }

  public Outcome accept(OperationContext context, AcceptMovement input) {
    return engine.execute(
        context,
        INVENTORY,
        "movement.confirm-and-accept",
        input,
        unit -> {
          unit.requireInternal();
          var movement = unit.load("movement", input.movementId(), Movement.class);
          unit.expect("movement", movement.id(), input.expectedRevision());
          var item = unit.load("inventory", movement.inventoryId(), Inventory.class);
          requireMovable(item);
          if (!movement.status().equals("REQUESTED")
              || !item.location().equals(movement.origin())) {
            throw OperationException.unresolved(
                "Movement is already complete or origin no longer matches.");
          }
          unit.update(
              "inventory",
              item.id(),
              item.customerId(),
              copy(
                  item,
                  item.quantity(),
                  item.reserved(),
                  movement.destination(),
                  context.principalId(),
                  item.condition()));
          unit.update(
              "movement",
              movement.id(),
              movement.customerId(),
              new Movement(
                  movement.id(),
                  item.id(),
                  movement.customerId(),
                  movement.origin(),
                  movement.destination(),
                  movement.requestedBy(),
                  "CONFIRMED_ACCEPTED",
                  context.principalId()));
        });
  }

  static void evidence(OperationEngine.Unit unit, List<String> ids, boolean required) {
    Checks.bounded(ids, 10);
    if (required && ids.isEmpty()) {
      throw OperationException.unresolved("Required actual evidence is missing.");
    }
    for (String id : ids) {
      var artifact = unit.load("artifact", id, Artifact.class);
      if (!artifact.status().equals("VERIFIED")) {
        throw OperationException.unresolved("Evidence is not verified: " + id);
      }
    }
  }

  public Outcome issue(OperationContext context, IssueInput input) {
    return engine.execute(
        context,
        INVENTORY,
        "reservation.issue-to-wip",
        input,
        unit -> {
          unit.requireInternal();
          Checks.text(input.reason(), 1000);
          var reservation = unit.load("reservation", input.reservationId(), Reservation.class);
          unit.expect("reservation", reservation.id(), input.expectedRevision());
          unit.requireBeneficiaryCustomer(reservation.customerId());
          var parent = unit.load("inventory", reservation.inventoryId(), Inventory.class);
          requireNoQualityHold(unit, parent.id());
          var wip = Checks.required(input.wip());
          if (!reservation.status().equals("RESERVED")
              || parent.condition() != Condition.QUALIFIED
              || parent.reserved().compareTo(reservation.quantity()) < 0
              || parent.quantity().compareTo(reservation.quantity()) < 0
              || !wip.category().equals("WIP")
              || !wip.customerId().equals(reservation.customerId())
              || !wip.owner().equals(parent.owner())
              || !wip.unit().equals(parent.unit())
              || !wip.materialLot().equals(parent.materialLot())
              || !wip.parents().equals(List.of(parent.id()))
              || !wip.location().equals(parent.location())
              || !wip.custodian().equals(parent.custodian())
              || wip.condition() != Condition.QUALIFIED
              || wip.quantity().compareTo(reservation.quantity()) != 0
              || wip.reserved().compareTo(reservation.quantity()) != 0) {
            throw OperationException.unresolved(
                "WIP issue must preserve custody, ownership, genealogy and exact reserved"
                    + " quantity.");
          }
          var remaining = parent.quantity().subtract(reservation.quantity());
          unit.update(
              "inventory",
              parent.id(),
              parent.customerId(),
              copy(
                  parent,
                  remaining,
                  parent.reserved().subtract(reservation.quantity()),
                  parent.location(),
                  parent.custodian(),
                  remaining.signum() == 0 ? Condition.CONSUMED : parent.condition()));
          unit.create("inventory", wip.id(), wip.customerId(), wip);
          var holdId = OperationEngine.qualityHoldId("inventory", wip.id());
          unit.create("qualityhold", holdId, wip.customerId(), new QualityHold(holdId, 0));
          unit.update(
              "reservation",
              reservation.id(),
              reservation.customerId(),
              new Reservation(
                  reservation.id(),
                  wip.id(),
                  reservation.orderLineId(),
                  reservation.customerId(),
                  reservation.quantity(),
                  "ISSUED"));
        });
  }

  public Outcome cancel(OperationContext context, CancelReservation input) {
    return engine.execute(
        context,
        INVENTORY,
        "reservation.release",
        input,
        unit -> {
          unit.requireInternal();
          Checks.text(input.reason(), 1000);
          var reservation = unit.load("reservation", input.reservationId(), Reservation.class);
          unit.expect("reservation", reservation.id(), input.expectedRevision());
          unit.requireBeneficiaryCustomer(reservation.customerId());
          var item = unit.load("inventory", reservation.inventoryId(), Inventory.class);
          if (!java.util.Set.of("RESERVED", "ISSUED").contains(reservation.status())
              || item.reserved().compareTo(reservation.quantity()) < 0) {
            throw OperationException.unresolved(
                "Reservation cannot be released twice or beyond the held quantity.");
          }
          unit.update(
              "inventory",
              item.id(),
              item.customerId(),
              copy(
                  item,
                  item.quantity(),
                  item.reserved().subtract(reservation.quantity()),
                  item.location(),
                  item.custodian(),
                  item.condition()));
          unit.update(
              "reservation",
              reservation.id(),
              reservation.customerId(),
              new Reservation(
                  reservation.id(),
                  reservation.inventoryId(),
                  reservation.orderLineId(),
                  reservation.customerId(),
                  reservation.quantity(),
                  "RELEASED"));
        });
  }

  private void requireMovable(Inventory item) {
    if (item.condition() == Condition.CONSUMED || item.quantity().signum() == 0) {
      throw OperationException.unresolved("Consumed stock has no physical quantity to move.");
    }
  }

  private void requireNoQualityHold(OperationEngine.Unit unit, String id) {
    if (unit.load("qualityhold", OperationEngine.qualityHoldId("inventory", id), QualityHold.class)
            .openFindings()
        != 0) {
      throw OperationException.unresolved(
          "Nonconforming stock cannot satisfy allocation or transformation.");
    }
  }

  private Inventory copy(
      Inventory item,
      BigDecimal quantity,
      BigDecimal reserved,
      String location,
      String custodian,
      Condition condition) {
    return new Inventory(
        item.id(),
        item.customerId(),
        item.owner(),
        item.category(),
        item.materialLot(),
        quantity,
        reserved,
        item.unit(),
        location,
        custodian,
        condition,
        item.parents());
  }
}
