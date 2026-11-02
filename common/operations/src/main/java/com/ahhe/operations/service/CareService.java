package com.ahhe.operations.service;

import static com.ahhe.operations.model.OperationContext.Role.MAINTENANCE;
import static com.ahhe.operations.model.OperationContext.Role.QUALITY;
import static com.ahhe.operations.service.BusinessRecords.*;

import com.ahhe.operations.model.Checks;
import com.ahhe.operations.model.OperationContext;
import com.ahhe.operations.model.OperationContext.Outcome;
import com.ahhe.operations.model.OperationException;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;

public final class CareService {
  private final OperationEngine engine;

  public CareService(OperationEngine engine) {
    this.engine = engine;
  }

  public record WorkInput(String id, String planId) {}

  public record ProgressInput(
      String id,
      long expectedRevision,
      WorkStatus status,
      List<String> evidenceIds,
      String findings) {}

  public record ReleaseInput(
      String workId,
      long expectedRevision,
      boolean postServiceAccepted,
      Instant qualifiedUntil,
      List<String> evidenceIds,
      String reason) {}

  public record MeterInput(
      String assetId, long expectedRevision, BigDecimal reading, Instant observedAt) {}

  public record Assessment(
      CareEligibility eligibility, Instant assessedAt, long assetRevision, long planRevision) {}

  public Assessment assess(String businessId, String principalId, String assetId) {
    var assetRecord = engine.read(businessId, principalId, "asset", assetId);
    var binding = engine.read(businessId, principalId, "assetplan", assetId);
    var planId = engine.decode(binding, String.class);
    var planRecord = engine.read(businessId, principalId, "careplan", planId);
    if (engine.read(businessId, principalId, "asset", assetId).revision() != assetRecord.revision()
        || engine.read(businessId, principalId, "assetplan", assetId).revision()
            != binding.revision()
        || engine.read(businessId, principalId, "careplan", planId).revision()
            != planRecord.revision()) {
      throw OperationException.conflict("Asset/care evidence changed while assessing; reload.");
    }
    var assessedAt = engine.now();
    return new Assessment(
        eligibility(
            engine.decode(assetRecord, Asset.class),
            engine.decode(planRecord, CarePlan.class),
            assessedAt),
        assessedAt,
        assetRecord.revision(),
        planRecord.revision());
  }

  public Outcome asset(OperationContext context, Asset asset) {
    return engine.execute(
        context,
        MAINTENANCE,
        "asset.register",
        asset,
        unit -> {
          unit.requireInternal();
          if (asset.qualified() || asset.meterObservedAt().isAfter(engine.now())) {
            throw new IllegalArgumentException(
                "New assets are held; future meter readings are not accepted.");
          }
          unit.create("asset", asset.id(), "", asset);
          unit.create("assetcare", asset.id(), "", "");
          unit.create("assetqualifiedfrom", asset.id(), "", Instant.EPOCH);
        });
  }

  public Outcome plan(OperationContext context, CarePlan plan) {
    return engine.execute(
        context,
        MAINTENANCE,
        "care-plan.approve",
        plan,
        unit -> {
          unit.requireInternal();
          var asset = unit.load("asset", plan.assetId(), Asset.class);
          if (plan.baselineTime().isAfter(engine.now())
              || plan.baselineMeter().compareTo(asset.meter()) > 0) {
            throw OperationException.unresolved("Plan baseline exceeds observed history.");
          }
          unit.create("careplan", plan.id(), "", plan);
          unit.create("assetplan", asset.id(), "", plan.id());
        });
  }

  public Outcome meter(OperationContext context, MeterInput input) {
    return engine.execute(
        context,
        MAINTENANCE,
        "asset.meter",
        input,
        unit -> {
          unit.requireInternal();
          var asset = unit.load("asset", input.assetId(), Asset.class);
          unit.expect("asset", asset.id(), input.expectedRevision());
          Checks.nonnegative(input.reading());
          if (input.observedAt().isAfter(engine.now())
              || !input.observedAt().isAfter(asset.meterObservedAt())) {
            throw OperationException.unresolved(
                "Meter timestamp is future, repeated or out of order.");
          }
          boolean reset = input.reading().compareTo(asset.meter()) < 0;
          unit.update(
              "asset",
              asset.id(),
              "",
              new Asset(
                  asset.id(),
                  asset.name(),
                  asset.manufacturerModel(),
                  asset.ownerPrincipalId(),
                  asset.qualified() && !reset,
                  reset ? "METER_RESET_REVIEW_REQUIRED" : asset.holdReason(),
                  input.reading(),
                  input.observedAt(),
                  asset.qualifiedUntil()));
        });
  }

  public Outcome work(OperationContext context, WorkInput input) {
    return engine.execute(
        context,
        MAINTENANCE,
        "care-work.create",
        input,
        unit -> {
          unit.requireInternal();
          Checks.id(input.id());
          var plan = unit.load("careplan", input.planId(), CarePlan.class);
          var asset = unit.load("asset", plan.assetId(), Asset.class);
          unit.create(
              "carework",
              input.id(),
              "",
              new CareWork(
                  input.id(),
                  plan.id(),
                  unit.revision("careplan", plan.id()),
                  asset.id(),
                  WorkStatus.PLANNED,
                  List.of(),
                  "",
                  Instant.EPOCH,
                  BigDecimal.ZERO,
                  ""));
        });
  }

  public Outcome progress(OperationContext context, ProgressInput input) {
    return engine.execute(
        context,
        MAINTENANCE,
        "care-work.progress",
        input,
        unit -> {
          unit.requireInternal();
          var work = unit.load("carework", input.id(), CareWork.class);
          unit.expect("carework", work.id(), input.expectedRevision());
          var plan = unit.load("careplan", work.planId(), CarePlan.class);
          unit.expect("careplan", plan.id(), work.planRevision());
          var asset = unit.load("asset", work.assetId(), Asset.class);
          boolean allowed =
              switch (work.status()) {
                case PLANNED, INTERRUPTED -> input.status() == WorkStatus.IN_PROGRESS;
                case IN_PROGRESS -> SetProgress.allowed(input.status());
                default -> false;
              };
          if (!allowed) {
            throw OperationException.unresolved("Invalid or completed maintenance transition.");
          }
          var activeWork = unit.load("assetcare", asset.id(), String.class);
          if (!activeWork.isEmpty() && !activeWork.equals(work.id())) {
            throw OperationException.unresolved(
                "Another maintenance intervention must be completed and reviewed first.");
          }
          Checks.text(input.findings(), 1000);
          InventoryService.evidence(
              unit,
              input.evidenceIds(),
              input.status() == WorkStatus.COMPLETED_PENDING_VERIFICATION);
          unit.update(
              "assetcare", asset.id(), "", input.status() == WorkStatus.FAILED ? "" : work.id());
          unit.update(
              "carework",
              work.id(),
              "",
              new CareWork(
                  work.id(),
                  work.planId(),
                  work.planRevision(),
                  work.assetId(),
                  input.status(),
                  List.copyOf(input.evidenceIds()),
                  input.findings(),
                  input.status() == WorkStatus.COMPLETED_PENDING_VERIFICATION
                      ? engine.now()
                      : Instant.EPOCH,
                  asset.meter(),
                  context.principalId()));
          unit.update(
              "asset",
              asset.id(),
              "",
              new Asset(
                  asset.id(),
                  asset.name(),
                  asset.manufacturerModel(),
                  asset.ownerPrincipalId(),
                  false,
                  "MAINTENANCE_" + input.status(),
                  asset.meter(),
                  asset.meterObservedAt(),
                  asset.qualifiedUntil()));
        });
  }

  public Outcome release(OperationContext context, ReleaseInput input) {
    return engine.execute(
        context,
        QUALITY,
        "care-work.verify-and-release",
        input,
        unit -> {
          unit.requireInternal();
          var work = unit.load("carework", input.workId(), CareWork.class);
          unit.expect("carework", work.id(), input.expectedRevision());
          var plan = unit.load("careplan", work.planId(), CarePlan.class);
          unit.expect("careplan", plan.id(), work.planRevision());
          var asset = unit.load("asset", work.assetId(), Asset.class);
          if (work.status() != WorkStatus.COMPLETED_PENDING_VERIFICATION) {
            throw OperationException.unresolved(
                "Actual completed work is required before verification.");
          }
          if (!unit.load("assetcare", asset.id(), String.class).equals(work.id())) {
            throw OperationException.unresolved(
                "Only the asset's active maintenance intervention can be released.");
          }
          Checks.text(input.reason(), 1000);
          InventoryService.evidence(unit, input.evidenceIds(), true);
          if (input.postServiceAccepted()
              && (!input.qualifiedUntil().isAfter(engine.now())
                  || asset.meter().compareTo(work.completedMeter()) < 0
                  || engine
                      .now()
                      .isAfter(
                          asset.meterObservedAt().plusSeconds(plan.meterFreshnessSeconds())))) {
            throw OperationException.unresolved(
                "Post-service qualification or current meter evidence is unresolved.");
          }
          var status = input.postServiceAccepted() ? WorkStatus.RELEASED : WorkStatus.FAILED;
          unit.update("assetcare", asset.id(), "", "");
          unit.update(
              "carework",
              work.id(),
              "",
              new CareWork(
                  work.id(),
                  work.planId(),
                  work.planRevision(),
                  work.assetId(),
                  status,
                  work.evidenceIds(),
                  work.findings() + "\nReview: " + input.reason(),
                  work.completedAt(),
                  work.completedMeter(),
                  work.performedBy()));
          unit.update(
              "asset",
              asset.id(),
              "",
              new Asset(
                  asset.id(),
                  asset.name(),
                  asset.manufacturerModel(),
                  asset.ownerPrincipalId(),
                  input.postServiceAccepted(),
                  input.postServiceAccepted() ? "" : "POST_SERVICE_CHECK_FAILED",
                  asset.meter(),
                  asset.meterObservedAt(),
                  input.qualifiedUntil()));
          if (input.postServiceAccepted()) {
            unit.load("assetqualifiedfrom", asset.id(), Instant.class);
            unit.update("assetqualifiedfrom", asset.id(), "", engine.now());
            unit.update(
                "careplan",
                plan.id(),
                "",
                new CarePlan(
                    plan.id(),
                    plan.assetId(),
                    plan.procedureRevision(),
                    plan.calendarDays(),
                    plan.usageInterval(),
                    work.completedMeter(),
                    work.completedAt(),
                    plan.timeZone(),
                    plan.meterFreshnessSeconds(),
                    plan.mandatory()));
          }
        });
  }

  public static CareEligibility eligibility(Asset asset, CarePlan plan, Instant now) {
    var dueAt =
        plan.baselineTime()
            .atZone(ZoneId.of(plan.timeZone()))
            .plusDays(plan.calendarDays())
            .toInstant();
    boolean stale =
        asset.meterObservedAt().isAfter(now)
            || now.isAfter(asset.meterObservedAt().plusSeconds(plan.meterFreshnessSeconds()))
            || asset.meter().compareTo(plan.baselineMeter()) < 0;
    boolean due =
        !now.isBefore(dueAt)
            || asset.meter().subtract(plan.baselineMeter()).compareTo(plan.usageInterval()) >= 0;
    boolean qualificationExpired = !now.isBefore(asset.qualifiedUntil());
    boolean eligible =
        asset.qualified() && !qualificationExpired && !stale && (!plan.mandatory() || !due);
    return new CareEligibility(
        eligible,
        due,
        stale
            ? "METER_UNKNOWN"
            : !asset.qualified()
                ? asset.holdReason()
                : qualificationExpired ? "QUALIFICATION_EXPIRED" : due ? "SERVICE_DUE" : "",
        dueAt);
  }

  public static void requireEligible(OperationEngine.Unit unit, String assetId, Instant now) {
    var asset = unit.load("asset", assetId, Asset.class);
    var planId = unit.load("assetplan", assetId, String.class);
    var plan = unit.load("careplan", planId, CarePlan.class);
    if (!eligibility(asset, plan, now).eligible()) {
      throw OperationException.unresolved("Asset is held, due or has stale usage evidence.");
    }
    unit.boundValidity(asset.meterObservedAt().plusSeconds(plan.meterFreshnessSeconds()));
    unit.boundValidity(asset.qualifiedUntil());
    if (plan.mandatory()) {
      unit.boundValidity(eligibility(asset, plan, now).calendarDueAt());
    }
  }

  public static void requireQualifiedAt(OperationEngine.Unit unit, Asset asset, Instant measuredAt) {
    var qualifiedFrom = unit.load("assetqualifiedfrom", asset.id(), Instant.class);
    if (measuredAt.isBefore(qualifiedFrom) || !measuredAt.isBefore(asset.qualifiedUntil())) {
      throw OperationException.unresolved(
          "Measurement timestamp is outside the actual gauge qualification interval.");
    }
  }

  private static final class SetProgress {
    static boolean allowed(WorkStatus status) {
      return status == WorkStatus.INTERRUPTED
          || status == WorkStatus.FAILED
          || status == WorkStatus.COMPLETED_PENDING_VERIFICATION;
    }
  }
}
