package com.ahhe.operations.service;

import com.ahhe.operations.model.Checks;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;

public final class BusinessRecords {
  private BusinessRecords() {}

  public enum Condition {
    UNRESOLVED,
    QUARANTINED,
    QUALIFIED,
    CONSUMED
  }

  public enum WorkStatus {
    PLANNED,
    IN_PROGRESS,
    INTERRUPTED,
    COMPLETED_PENDING_VERIFICATION,
    FAILED,
    RELEASED
  }

  public enum Disposition {
    ACCEPTED,
    NONCONFORMING,
    INCONCLUSIVE
  }

  public enum DecisionRule {
    INTERVAL_CONTAINMENT
  }

  public enum SamplingScope {
    SINGLE_WORKPIECE
  }

  public enum ReturnStatus {
    REQUESTED,
    AUTHORIZED,
    RECEIVING_QUARANTINE,
    IDENTIFIED,
    REMEDY_APPROVED,
    FINANCIAL_PENDING,
    COMPLETED,
    REJECTED
  }

  public record Customer(String id, String name, String sourceReference, boolean verified) {
    public Customer {
      Checks.id(id);
      Checks.text(name, 200);
      Checks.text(sourceReference, 200);
    }
  }

  public record OrderLine(
      String id,
      String customerId,
      String sourceReference,
      String partRevision,
      BigDecimal quantity,
      BigDecimal returnAllocated,
      BigDecimal remedyAllocated,
      BigDecimal itemAmount,
      BigDecimal taxAmount,
      BigDecimal discountAmount,
      String currency,
      Instant deliveredAt,
      boolean verified) {
    public OrderLine {
      Checks.id(id);
      Checks.id(customerId);
      Checks.text(sourceReference, 200);
      Checks.text(partRevision, 200);
      Checks.positive(quantity);
      Checks.nonnegative(returnAllocated);
      Checks.nonnegative(remedyAllocated);
      Checks.nonnegative(itemAmount);
      Checks.nonnegative(taxAmount);
      Checks.nonnegative(discountAmount);
      if (!currency.matches("[A-Z]{3}")
          || discountAmount.compareTo(itemAmount) > 0
          || returnAllocated.compareTo(quantity) > 0
          || remedyAllocated.compareTo(quantity) > 0) {
        throw new IllegalArgumentException("Invalid order amounts, currency or allocations.");
      }
      Checks.required(deliveredAt);
    }
  }

  public record Inventory(
      String id,
      String customerId,
      String owner,
      String category,
      String materialLot,
      BigDecimal quantity,
      BigDecimal reserved,
      String unit,
      String location,
      String custodian,
      Condition condition,
      List<String> parents) {
    public Inventory {
      Checks.id(id);
      customerId = customerId == null ? "" : customerId;
      Checks.text(owner, 200);
      Checks.text(category, 100);
      Checks.text(materialLot, 200);
      Checks.nonnegative(quantity);
      Checks.nonnegative(reserved);
      if (reserved.compareTo(quantity) > 0) {
        throw new IllegalArgumentException("Reserved quantity exceeds physical quantity.");
      }
      Checks.text(unit, 30);
      Checks.text(location, 200);
      Checks.id(custodian);
      Checks.required(condition);
      Checks.bounded(parents, 20);
      parents = List.copyOf(parents);
    }
  }

  public record Reservation(
      String id,
      String inventoryId,
      String orderLineId,
      String customerId,
      BigDecimal quantity,
      String status) {}

  public record Movement(
      String id,
      String inventoryId,
      String customerId,
      String origin,
      String destination,
      String requestedBy,
      String status,
      String acceptedBy) {}

  public record Asset(
      String id,
      String name,
      String manufacturerModel,
      String ownerPrincipalId,
      boolean qualified,
      String holdReason,
      BigDecimal meter,
      Instant meterObservedAt,
      Instant qualifiedUntil) {
    public Asset {
      Checks.id(id);
      Checks.text(name, 200);
      Checks.text(manufacturerModel, 200);
      Checks.id(ownerPrincipalId);
      Checks.nonnegative(meter);
      Checks.required(meterObservedAt);
      Checks.required(qualifiedUntil);
      Checks.required(holdReason);
      if (!qualified && holdReason.isBlank()) {
        throw new IllegalArgumentException("An unqualified asset requires a hold reason.");
      }
    }
  }

  public record CarePlan(
      String id,
      String assetId,
      String procedureRevision,
      long calendarDays,
      BigDecimal usageInterval,
      BigDecimal baselineMeter,
      Instant baselineTime,
      String timeZone,
      long meterFreshnessSeconds,
      boolean mandatory) {
    public CarePlan {
      Checks.id(id);
      Checks.id(assetId);
      Checks.text(procedureRevision, 200);
      if (calendarDays < 1 || calendarDays > 3650 || meterFreshnessSeconds < 1) {
        throw new IllegalArgumentException(
            "Explicit bounded care intervals and freshness are required.");
      }
      Checks.positive(usageInterval);
      Checks.nonnegative(baselineMeter);
      Checks.required(baselineTime);
      ZoneId.of(timeZone);
    }
  }

  public record CareWork(
      String id,
      String planId,
      long planRevision,
      String assetId,
      WorkStatus status,
      List<String> evidenceIds,
      String findings,
      Instant completedAt,
      BigDecimal completedMeter,
      String performedBy) {}

  public record CareEligibility(
      boolean eligible, boolean due, String reason, Instant calendarDueAt) {}

  public record InspectionPlan(
      String id,
      String customerId,
      String orderLineId,
      String characteristic,
      String checkpoint,
      String methodRevision,
      String unit,
      BigDecimal lower,
      BigDecimal upper,
      BigDecimal maximumUncertainty,
      String gaugeId,
      boolean rawEvidenceRequired,
      DecisionRule decisionRule,
      SamplingScope samplingScope) {
    public InspectionPlan {
      Checks.id(id);
      Checks.id(customerId);
      Checks.id(orderLineId);
      Checks.text(characteristic, 300);
      Checks.text(checkpoint, 200);
      Checks.text(methodRevision, 200);
      Checks.text(unit, 30);
      Checks.decimal(lower);
      Checks.decimal(upper);
      Checks.nonnegative(maximumUncertainty);
      Checks.id(gaugeId);
      Checks.required(decisionRule);
      Checks.required(samplingScope);
      if (lower.compareTo(upper) > 0) {
        throw new IllegalArgumentException("Inspection limits are reversed.");
      }
    }
  }

  public record InspectionResult(
      String id,
      String customerId,
      String planId,
      long planRevision,
      String subjectType,
      String subjectId,
      long subjectRevision,
      String reservationId,
      long reservationRevision,
      long qualityHoldRevision,
      BigDecimal measuredValue,
      BigDecimal uncertainty,
      String unit,
      String gaugeId,
      long gaugeRevision,
      Instant measuredAt,
      Disposition disposition,
      List<String> evidenceIds) {}

  public record Release(
      String id,
      String customerId,
      String orderLineId,
      String operation,
      List<String> requiredResultIds,
      String machineId,
      Instant evaluatedAt,
      Instant validUntil) {}

  public record GatePlan(
      String id,
      String customerId,
      String orderLineId,
      String operation,
      String subjectType,
      String subjectId,
      String machineId,
      List<String> requiredPlanIds) {}

  public record QualityHold(String id, long openFindings) {}

  public record Nonconformance(
      String id,
      String customerId,
      String resultId,
      String status,
      String resolutionResultId,
      String reason) {}

  public record Artifact(
      String id,
      String customerId,
      String objectKey,
      String versionId,
      String sha256,
      long size,
      String mediaType,
      String status,
      String reviewedBy,
      String reviewReason) {}

  public record Benchmark(
      String id,
      String name,
      String ownedSourceReference,
      String usageAuthority,
      String geometryArtifactId,
      String material,
      String tolerance,
      String finish,
      String unit) {
    public Benchmark {
      Checks.id(id);
      Checks.text(name, 200);
      Checks.text(ownedSourceReference, 300);
      Checks.text(usageAuthority, 500);
      Checks.id(geometryArtifactId);
      Checks.text(material, 100);
      Checks.text(tolerance, 200);
      Checks.text(finish, 200);
      Checks.text(unit, 30);
    }
  }

  public record Quote(
      String id,
      String benchmarkId,
      long benchmarkRevision,
      String provider,
      String termsRevision,
      BigDecimal quantity,
      String material,
      String tolerance,
      String finish,
      BigDecimal price,
      BigDecimal shipping,
      BigDecimal tax,
      String currency,
      int leadDays,
      Instant quotedAt,
      Instant expiresAt,
      String evidenceId) {
    public Quote {
      Checks.id(id);
      Checks.id(benchmarkId);
      Checks.text(provider, 200);
      Checks.text(termsRevision, 200);
      Checks.positive(quantity);
      Checks.text(material, 100);
      Checks.text(tolerance, 200);
      Checks.text(finish, 200);
      Checks.nonnegative(price);
      Checks.nonnegative(shipping);
      Checks.nonnegative(tax);
      if (!currency.matches("[A-Z]{3}") || leadDays < 0 || leadDays > 3650) {
        throw new IllegalArgumentException("Invalid quote currency or lead time.");
      }
      Checks.required(quotedAt);
      Checks.required(expiresAt);
      if (!expiresAt.isAfter(quotedAt)) {
        throw new IllegalArgumentException("Quote expiration must follow quotation.");
      }
      Checks.id(evidenceId);
    }
  }

  public record QuoteComparison(
      String id,
      List<String> quoteIds,
      List<String> scopeDifferences,
      List<BigDecimal> rawDeliveredPrices,
      List<BigDecimal> adjustments,
      List<String> adjustmentReasons,
      List<BigDecimal> adjustedPrices,
      BigDecimal internalCost,
      String currency,
      Instant evaluatedAt) {}

  public record ReturnPolicy(
      String id, String revisionReference, int windowDays, Instant effectiveUntil) {
    public ReturnPolicy {
      Checks.id(id);
      Checks.text(revisionReference, 300);
      if (windowDays < 1 || windowDays > 3650) {
        throw new IllegalArgumentException("An approved bounded return window is required.");
      }
      Checks.required(effectiveUntil);
    }
  }

  public record Rma(
      String id,
      String customerId,
      String orderLineId,
      BigDecimal quantity,
      String reason,
      String requestedRemedy,
      String policyId,
      long policyRevision,
      Instant expiresAt,
      ReturnStatus status) {}

  public record ReturnPackage(
      String id,
      String customerId,
      String rmaId,
      String trackingReference,
      String location,
      String conditionEvidenceId,
      String status,
      List<ReturnedItem> confirmedContents) {}

  public record ReturnedItem(String partRevision, BigDecimal quantity) {
    public ReturnedItem {
      Checks.text(partRevision, 200);
      Checks.positive(quantity);
    }
  }

  public record Remedy(
      String id,
      String customerId,
      String rmaId,
      String kind,
      BigDecimal quantity,
      BigDecimal itemAmount,
      BigDecimal taxAmount,
      BigDecimal discountAmount,
      BigDecimal total,
      String currency,
      String status,
      List<String> inspectionIds) {}
}
