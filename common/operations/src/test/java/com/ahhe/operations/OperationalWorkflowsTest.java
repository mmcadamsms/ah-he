package com.ahhe.operations;

import static com.ahhe.operations.model.OperationContext.Role.*;
import static com.ahhe.operations.service.BusinessRecords.*;
import static org.assertj.core.api.Assertions.*;

import com.ahhe.operations.model.OperationContext;
import com.ahhe.operations.model.OperationContext.*;
import com.ahhe.operations.model.OperationException;
import com.ahhe.operations.repository.*;
import com.ahhe.operations.service.*;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;
import java.util.concurrent.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationalWorkflowsTest {
  private static final Instant NOW = Instant.parse("2026-10-31T20:00:00Z");
  private final TestOperationalStore store = new TestOperationalStore();
  private final JsonCodec codec = new JsonCodec();
  private final Map<String, byte[]> blobs = new HashMap<>();
  private OperationEngine engine;
  private InventoryService inventory;
  private CareService care;
  private InspectionService inspection;
  private CommercialService commercial;
  private ReturnService returns;
  private EvidenceService evidence;

  @BeforeEach
  void initialize() {
    engine = new OperationEngine(store, codec, Clock.fixed(NOW, ZoneOffset.UTC));
    inventory = new InventoryService(engine);
    care = new CareService(engine);
    inspection = new InspectionService(engine);
    commercial = new CommercialService(engine);
    returns = new ReturnService(engine, inspection);
    evidence =
        new EvidenceService(
            engine,
            new EvidenceStore() {
              public String putImmutable(String key, byte[] bytes) {
                if (blobs.containsKey(key) && !Arrays.equals(blobs.get(key), bytes)) {
                  throw OperationException.conflict("Immutable content mismatch.");
                }
                blobs.put(key, bytes.clone());
                return "test-version";
              }

              public byte[] read(String key, String version) {
                return blobs.get(key).clone();
              }
            },
            codec);
    engine.initialize(
        "shop",
        new Grant(
            "admin",
            PrincipalKind.HUMAN,
            "",
            Set.of(ADMIN, INVENTORY, MAINTENANCE, QUALITY, COMMERCIAL, RETURNS),
            true,
            NOW.plusSeconds(86400)));
    inventory.customer(
        business(), new Customer("customerA", "Example customer A", "verified-source-A", true));
    inventory.customer(
        business(), new Customer("customerB", "Example customer B", "verified-source-B", true));
    inventory.orderLine(business(), line("lineA", "customerA"));
    inventory.orderLine(business(), line("lineB", "customerB"));
    put(
        "artifact",
        "proof",
        "",
        new Artifact(
            "proof",
            "",
            "test-proof",
            "test-version",
            "verified-test-hash",
            4,
            "text/plain",
            "VERIFIED",
            "admin",
            "Test fixture: admitted evidence"));
  }

  private OrderLine line(String id, String customer) {
    return new OrderLine(
        id,
        customer,
        "verified-" + id,
        "drawing-revision-1",
        bd("10"),
        BigDecimal.ZERO,
        BigDecimal.ZERO,
        bd("100"),
        bd("8"),
        bd("10"),
        "USD",
        NOW.minusSeconds(86400),
        true);
  }

  private BigDecimal bd(String value) {
    return new BigDecimal(value);
  }

  private OperationContext business() {
    return context("admin", "", BeneficiaryKind.BUSINESS, "shop");
  }

  private OperationContext customer() {
    return context("admin", "", BeneficiaryKind.CUSTOMER, "customerA");
  }

  private OperationContext context(
      String principal, String represented, BeneficiaryKind kind, String id) {
    return new OperationContext(
        "shop",
        principal,
        represented,
        new Beneficiary(kind, id),
        List.of(),
        "Explicit test purpose",
        UUID.randomUUID().toString(),
        "");
  }

  private long revision(String type, String id) {
    return store.read("shop", OperationEngine.key(type, id)).orElseThrow().revision();
  }

  private <T> T record(String type, String id, Class<T> target) {
    return codec.decode(
        store.read("shop", OperationEngine.key(type, id)).orElseThrow().document().json(), target);
  }

  private void put(String type, String id, String customer, Object value) {
    var key = OperationEngine.key(type, id);
    var old = store.read("shop", key);
    store.commit(
        "shop",
        List.of(
            new OperationalStore.Mutation(
                key,
                old.map(OperationalStore.Stored::version),
                old.map(entry -> entry.revision() + 1).orElse(1L),
                new OperationalStore.Document(type, id, customer, codec.encode(value)))));
  }

  private Inventory stock(String id, Condition condition) {
    return new Inventory(
        id,
        "customerA",
        "shop",
        "WIP",
        "lot-1",
        bd("10"),
        BigDecimal.ZERO,
        "pieces",
        "receiving",
        "admin",
        condition,
        List.of());
  }

  private void qualifiedStock() {
    inventory.receive(customer(), stock("stock", Condition.QUARANTINED));
    inventory.qualify(
        customer(),
        "stock",
        new InventoryService.RevisionInput(
            revision("inventory", "stock"), "Approved receipt qualification", List.of("proof")));
  }

  private void qualifiedAsset(String id) {
    care.asset(
        business(),
        new Asset(
            id,
            "Test asset",
            "test-model",
            "admin",
            false,
            "NO_PLAN",
            BigDecimal.ZERO,
            NOW,
            NOW.plusSeconds(86400)));
    care.plan(
        business(),
        new CarePlan(
            id + "-plan",
            id,
            "approved-procedure-v1",
            30,
            bd("100"),
            BigDecimal.ZERO,
            NOW.minusSeconds(86400),
            "America/Los_Angeles",
            86400,
            true));
    care.work(business(), new CareService.WorkInput(id + "-work", id + "-plan"));
    care.progress(
        business(),
        new CareService.ProgressInput(
            id + "-work",
            revision("carework", id + "-work"),
            WorkStatus.IN_PROGRESS,
            List.of(),
            "Performed approved safe procedure"));
    care.progress(
        business(),
        new CareService.ProgressInput(
            id + "-work",
            revision("carework", id + "-work"),
            WorkStatus.COMPLETED_PENDING_VERIFICATION,
            List.of("proof"),
            "Actual work evidence"));
    care.release(
        business(),
        new CareService.ReleaseInput(
            id + "-work",
            revision("carework", id + "-work"),
            true,
            NOW.plusSeconds(86400),
            List.of("proof"),
            "Qualified post-service check"));
  }

  private void inspectionSetup() {
    qualifiedStock();
    inventory.reserve(
        customer(),
        new InventoryService.ReserveInput(
            "reservation", "stock", revision("inventory", "stock"), "lineA", BigDecimal.ONE));
    inventory.issue(
        customer(),
        new InventoryService.IssueInput(
            "reservation",
            revision("reservation", "reservation"),
            new Inventory(
                "workpiece",
                "customerA",
                "shop",
                "WIP",
                "lot-1",
                BigDecimal.ONE,
                BigDecimal.ONE,
                "pieces",
                "receiving",
                "admin",
                Condition.QUALIFIED,
                List.of("stock")),
            "Issue one controlled workpiece"));
    qualifiedAsset("gauge");
    qualifiedAsset("machine");
    inspection.plan(
        customer(),
        new InspectionPlan(
            "plan",
            "customerA",
            "lineA",
            "Bore diameter",
            "Before transfer",
            "approved-manual-method",
            "mm",
            bd("9.9"),
            bd("10.1"),
            bd("0.02"),
            "gauge",
            true,
            DecisionRule.INTERVAL_CONTAINMENT,
            SamplingScope.SINGLE_WORKPIECE));
  }

  private void measure(String id, String value) {
    inspection.measure(
        customer(),
        new InspectionService.MeasureInput(
            id,
            "plan",
            revision("inspectionplan", "plan"),
            "inventory",
            "workpiece",
            "reservation",
            bd(value),
            bd("0.01"),
            "mm",
            NOW,
            List.of("proof")));
  }

  @Test
  void commitResponseLossReplaysOriginalReceiptWithoutDuplicateEffects() {
    var context = customer();
    store.failAfterCommit = true;
    assertThatThrownBy(
            () -> inventory.receive(context, stock("retry-stock", Condition.QUARANTINED)))
        .isInstanceOf(OperationException.class);
    var first = inventory.receive(context, stock("retry-stock", Condition.QUARANTINED));
    var replay = inventory.receive(context, stock("retry-stock", Condition.QUARANTINED));
    assertThat(replay).isEqualTo(first);
    assertThat(revision("inventory", "retry-stock")).isEqualTo(1);
    var changedPurpose =
        new OperationContext(
            "shop",
            "admin",
            "",
            context.primaryBeneficiary(),
            List.of(),
            "Different intent",
            context.idempotencyKey(),
            "");
    assertThatThrownBy(
            () -> inventory.receive(changedPurpose, stock("retry-stock", Condition.QUARANTINED)))
        .isInstanceOf(OperationException.class)
        .hasMessageContaining("different intent");
  }

  @Test
  void concurrentReservationsCannotOversubscribeStock() throws Exception {
    qualifiedStock();
    long expected = revision("inventory", "stock");
    var executor = Executors.newFixedThreadPool(2);
    try {
      var results =
          executor.<Boolean>invokeAll(
              List.of(() -> reserveAttempt("r1", expected), () -> reserveAttempt("r2", expected)));
      assertThat(
              results.stream()
                  .map(
                      result -> {
                        try {
                          return result.get();
                        } catch (Exception exception) {
                          throw new AssertionError(exception);
                        }
                      })
                  .filter(Boolean::booleanValue)
                  .count())
          .isEqualTo(1);
      assertThat(record("inventory", "stock", Inventory.class).reserved())
          .isEqualByComparingTo("6");
    } finally {
      executor.shutdownNow();
    }
  }

  private boolean reserveAttempt(String id, long revision) {
    try {
      inventory.reserve(
          customer(), new InventoryService.ReserveInput(id, "stock", revision, "lineA", bd("6")));
      return true;
    } catch (OperationException exception) {
      assertThat(exception.status()).isIn(409, 422);
      return false;
    }
  }

  @Test
  void splitReconcilesGenealogyAndNeverCommitsUnbalancedChildren() {
    qualifiedStock();
    var parent = record("inventory", "stock", Inventory.class);
    var child =
        new Inventory(
            "child",
            parent.customerId(),
            parent.owner(),
            "REMNANT",
            parent.materialLot(),
            bd("8"),
            BigDecimal.ZERO,
            parent.unit(),
            parent.location(),
            parent.custodian(),
            Condition.QUALIFIED,
            List.of(parent.id()));
    assertThatThrownBy(
            () ->
                inventory.split(
                    customer(),
                    new InventoryService.SplitInput(
                        "stock",
                        revision("inventory", "stock"),
                        List.of(child),
                        bd("1"),
                        "Cut stock")))
        .isInstanceOf(OperationException.class);
    assertThat(store.read("shop", OperationEngine.key("inventory", "child"))).isEmpty();
    inventory.split(
        customer(),
        new InventoryService.SplitInput(
            "stock",
            revision("inventory", "stock"),
            List.of(child),
            bd("2"),
            "Approved cut and remnant"));
    assertThat(record("inventory", "stock", Inventory.class).condition())
        .isEqualTo(Condition.CONSUMED);
    assertThat(record("inventory", "child", Inventory.class).parents()).containsExactly("stock");
  }

  @Test
  void movementPlanDoesNotBecomeConfirmedCustodyUntilAcceptance() {
    qualifiedStock();
    inventory.move(
        customer(),
        new InventoryService.MoveInput(
            "move", "stock", revision("inventory", "stock"), "inspection"));
    assertThat(record("inventory", "stock", Inventory.class).location()).isEqualTo("receiving");
    inventory.accept(
        customer(), new InventoryService.AcceptMovement("move", revision("movement", "move")));
    assertThat(record("inventory", "stock", Inventory.class).location()).isEqualTo("inspection");
    assertThatThrownBy(
            () ->
                inventory.accept(
                    customer(),
                    new InventoryService.AcceptMovement("move", revision("movement", "move"))))
        .isInstanceOf(OperationException.class);
  }

  @Test
  void customerProvisioningAndBeneficiaryDoNotGrantStaffOrOtherCustomerAccess() {
    var customerPrincipal = OperationEngine.principalId("https://issuer.example", "customer-user");
    var customerGrant =
        new Grant(
            customerPrincipal,
            PrincipalKind.HUMAN,
            "customerA",
            Set.of(CUSTOMER_ADMIN),
            true,
            NOW.plusSeconds(86400));
    inventory.provision(business(), customerGrant);
    var context = context(customerPrincipal, "customerA", BeneficiaryKind.CUSTOMER, "customerA");
    assertThatThrownBy(
            () ->
                inventory.provision(
                    context,
                    new Grant(
                        "bad-user",
                        PrincipalKind.HUMAN,
                        "customerA",
                        Set.of(ADMIN),
                        true,
                        NOW.plusSeconds(1000))))
        .isInstanceOf(OperationException.class);
    assertThatThrownBy(() -> engine.read("shop", customerPrincipal, "orderline", "lineB"))
        .isInstanceOf(OperationException.class);
    returns.policy(
        business(), new ReturnPolicy("policy", "approved-policy", 30, NOW.plusSeconds(86400)));
    returns.request(
        context,
        new ReturnService.RequestInput(
            "own-return", "lineA", BigDecimal.ONE, "Reported issue", "REFUND", "policy"));
    assertThatThrownBy(
            () ->
                returns.request(
                    context,
                    new ReturnService.RequestInput(
                        "other-return",
                        "lineB",
                        BigDecimal.ONE,
                        "Reported issue",
                        "REFUND",
                        "policy")))
        .isInstanceOf(OperationException.class);
  }

  @Test
  void concurrentAuthorityRevocationPreventsBusinessCommit() {
    store.beforeCommit =
        () ->
            put(
                "grant",
                "admin",
                "",
                new Grant(
                    "admin",
                    PrincipalKind.HUMAN,
                    "",
                    Set.of(ADMIN),
                    false,
                    NOW.plusSeconds(86400)));
    assertThatThrownBy(
            () -> inventory.receive(customer(), stock("revoked-stock", Condition.QUARANTINED)))
        .isInstanceOf(OperationException.class);
    assertThat(store.read("shop", OperationEngine.key("inventory", "revoked-stock"))).isEmpty();
  }

  @Test
  void calendarDueBoundaryUsesLocalDstAndStaleMetersNeverCountAsZero() {
    var baseline = Instant.parse("2026-03-07T16:00:00Z");
    var due = Instant.parse("2026-03-08T15:00:00Z");
    var plan =
        new CarePlan(
            "p",
            "a",
            "procedure",
            1,
            bd("100"),
            BigDecimal.ZERO,
            baseline,
            "America/Los_Angeles",
            60,
            true);
    var asset =
        new Asset(
            "a", "Asset", "model", "admin", true, "", BigDecimal.ZERO, due, due.plusSeconds(1000));
    assertThat(CareService.eligibility(asset, plan, due).calendarDueAt()).isEqualTo(due);
    assertThat(CareService.eligibility(asset, plan, due).due()).isTrue();
    assertThat(CareService.eligibility(asset, plan, due.plusSeconds(61)).reason())
        .isEqualTo("METER_UNKNOWN");
  }

  @Test
  void interruptedCareDoesNotAdvanceBaselineOrReleaseAsset() {
    qualifiedAsset("asset");
    var before = record("careplan", "asset-plan", CarePlan.class).baselineTime();
    care.work(business(), new CareService.WorkInput("interrupted", "asset-plan"));
    care.progress(
        business(),
        new CareService.ProgressInput(
            "interrupted",
            revision("carework", "interrupted"),
            WorkStatus.IN_PROGRESS,
            List.of(),
            "Actual intervention started"));
    care.progress(
        business(),
        new CareService.ProgressInput(
            "interrupted",
            revision("carework", "interrupted"),
            WorkStatus.INTERRUPTED,
            List.of(),
            "Awaiting safe completion"));
    assertThat(record("careplan", "asset-plan", CarePlan.class).baselineTime()).isEqualTo(before);
    assertThat(record("asset", "asset", Asset.class).qualified()).isFalse();
    assertThatThrownBy(
            () ->
                care.release(
                    business(),
                    new CareService.ReleaseInput(
                        "interrupted",
                        revision("carework", "interrupted"),
                        true,
                        NOW.plusSeconds(86400),
                        List.of("proof"),
                        "Attempt")))
        .isInstanceOf(OperationException.class);
  }

  @Test
  void failedMeasurementIsPreservedAndReleaseRequiresReinspectionDisposition() {
    inspectionSetup();
    measure("failure", "11");
    measure("accepted", "10");
    assertThat(record("inspectionresult", "failure", InspectionResult.class).disposition())
        .isEqualTo(Disposition.NONCONFORMING);
    inspection.gatePlan(
        customer(),
        new GatePlan(
            "gate",
            "customerA",
            "lineA",
            "TRANSFER",
            "inventory",
            "workpiece",
            "machine",
            List.of("plan")));
    var input =
        new InspectionService.ReleaseInput(
            "release", "gate", revision("gateplan", "gate"), List.of("accepted"));
    assertThatThrownBy(() -> inspection.release(customer(), input))
        .isInstanceOf(OperationException.class);
    inspection.resolve(
        customer(),
        new InspectionService.ResolveInput(
            "failure",
            revision("nonconformance", "failure"),
            "accepted",
            "Authorized rework and reinspection disposition"));
    inspection.release(customer(), input);
    assertThat(record("inspectionresult", "failure", InspectionResult.class).measuredValue())
        .isEqualByComparingTo("11");
  }

  @Test
  void gatePlanCannotBeSatisfiedByClientSelectedSubsetOrStaleGauge() {
    inspectionSetup();
    inspection.plan(
        customer(),
        new InspectionPlan(
            "second-plan",
            "customerA",
            "lineA",
            "Second characteristic",
            "Before transfer",
            "approved-method",
            "mm",
            bd("9.9"),
            bd("10.1"),
            bd("0.02"),
            "gauge",
            true,
            DecisionRule.INTERVAL_CONTAINMENT,
            SamplingScope.SINGLE_WORKPIECE));
    measure("pass", "10");
    inspection.gatePlan(
        customer(),
        new GatePlan(
            "complete-gate",
            "customerA",
            "lineA",
            "FINAL_RELEASE",
            "inventory",
            "workpiece",
            "machine",
            List.of("plan", "second-plan")));
    assertThatThrownBy(
            () ->
                inspection.release(
                    customer(),
                    new InspectionService.ReleaseInput(
                        "incomplete",
                        "complete-gate",
                        revision("gateplan", "complete-gate"),
                        List.of("pass"))))
        .isInstanceOf(OperationException.class);
    inspection.gatePlan(
        customer(),
        new GatePlan(
            "one-gate",
            "customerA",
            "lineA",
            "TRANSFER",
            "inventory",
            "workpiece",
            "machine",
            List.of("plan")));
    store.beforeCommit =
        () -> {
          var gauge = record("asset", "gauge", Asset.class);
          put(
              "asset",
              "gauge",
              "",
              new Asset(
                  gauge.id(),
                  gauge.name(),
                  gauge.manufacturerModel(),
                  "admin",
                  false,
                  "GAUGE_RECALLED",
                  gauge.meter(),
                  gauge.meterObservedAt(),
                  gauge.qualifiedUntil()));
        };
    assertThatThrownBy(
            () ->
                inspection.release(
                    customer(),
                    new InspectionService.ReleaseInput(
                        "stale", "one-gate", revision("gateplan", "one-gate"), List.of("pass"))))
        .isInstanceOf(OperationException.class);
    assertThat(store.read("shop", OperationEngine.key("release", "stale"))).isEmpty();
  }

  @Test
  void quoteComparisonPreservesRawPriceScopeDifferencesAndInternalCost() {
    commercial.benchmark(
        business(),
        new Benchmark(
            "benchmark",
            "Owned test geometry",
            "owned-source",
            "Approved company-owned use",
            "proof",
            "aluminum",
            "0.1 mm",
            "machined",
            "mm"));
    commercial.quote(business(), quote("q1", "10", 5, NOW.plusSeconds(1000)));
    commercial.quote(business(), quote("q2", "20", 10, NOW.plusSeconds(1000)));
    commercial.compare(
        business(),
        new CommercialService.ComparisonInput(
            "comparison",
            List.of("q1", "q2"),
            List.of(BigDecimal.ZERO, bd("-5")),
            List.of("No adjustment", "Approved scope adjustment"),
            bd("70")));
    var comparison = record("comparison", "comparison", QuoteComparison.class);
    assertThat(comparison.scopeDifferences()).contains("q2: quantity", "q2: lead time");
    assertThat(comparison.rawDeliveredPrices().getFirst()).isEqualByComparingTo("110");
    assertThat(comparison.internalCost()).isEqualByComparingTo("70");
    commercial.quote(business(), quote("expired", "10", 5, NOW.minusSeconds(1)));
    assertThatThrownBy(
            () ->
                commercial.compare(
                    business(),
                    new CommercialService.ComparisonInput(
                        "bad",
                        List.of("q1", "expired"),
                        List.of(BigDecimal.ZERO, BigDecimal.ZERO),
                        List.of("None", "None"),
                        bd("70"))))
        .isInstanceOf(OperationException.class);
  }

  private Quote quote(String id, String quantity, int days, Instant expiry) {
    return new Quote(
        id,
        "benchmark",
        revision("benchmark", "benchmark"),
        "authorized-provider",
        "terms-v1",
        bd(quantity),
        "aluminum",
        "0.1 mm",
        "machined",
        bd("100"),
        bd("5"),
        bd("5"),
        "USD",
        days,
        NOW.minusSeconds(100),
        expiry,
        "proof");
  }

  @Test
  void returnAllocationQuarantineAndFaultRefundRemainTruthfulAndDeduplicated() {
    returns.policy(
        business(), new ReturnPolicy("policy", "approved-policy", 30, NOW.plusSeconds(86400)));
    returns.request(
        customer(),
        new ReturnService.RequestInput(
            "rma", "lineA", BigDecimal.ONE, "Reported defect", "REFUND", "policy"));
    returns.request(
        customer(),
        new ReturnService.RequestInput(
            "duplicate", "lineA", bd("10"), "Reported defect", "REFUND", "policy"));
    returns.authorize(customer(), new ReturnService.AuthorizeInput("rma", revision("rma", "rma")));
    assertThatThrownBy(
            () ->
                returns.authorize(
                    customer(),
                    new ReturnService.AuthorizeInput("duplicate", revision("rma", "duplicate"))))
        .isInstanceOf(OperationException.class);
    returns.receive(
        business(),
        new ReturnService.ReceiveInput("package", "", "tracking", "quarantine", "proof"));
    assertThat(record("package", "package", ReturnPackage.class).customerId()).isEmpty();
    returns.identify(
        customer(),
        new ReturnService.IdentifyInput(
            "package",
            revision("package", "package"),
            "rma",
            BigDecimal.ONE,
            "drawing-revision-1",
            "Confirmed exact contents/revision/quantity"));
    qualifiedAsset("return-gauge");
    inspection.plan(
        customer(),
        new InspectionPlan(
            "return-plan",
            "customerA",
            "lineA",
            "Reported defect",
            "Inbound",
            "approved-return-method",
            "mm",
            bd("9.9"),
            bd("10.1"),
            bd("0.02"),
            "return-gauge",
            true,
            DecisionRule.INTERVAL_CONTAINMENT,
            SamplingScope.SINGLE_WORKPIECE));
    inspection.measure(
        customer(),
        new InspectionService.MeasureInput(
            "returned-defect",
            "return-plan",
            1,
            "package",
            "package",
            "",
            bd("11"),
            bd("0.01"),
            "mm",
            NOW,
            List.of("proof")));
    inspection.gatePlan(
        customer(),
        new GatePlan(
            "return-gate",
            "customerA",
            "lineA",
            "RETURN_INSPECTION",
            "package",
            "package",
            "return-gauge",
            List.of("return-plan")));
    var context = customer();
    var input =
        new ReturnService.RemedyInput(
            "remedy",
            "rma",
            revision("rma", "rma"),
            "REFUND",
            "return-gate",
            revision("gateplan", "return-gate"),
            List.of("returned-defect"),
            "Accepted shop responsibility");
    var first = returns.remedy(context, input);
    assertThat(returns.remedy(context, input)).isEqualTo(first);
    var remedy = record("remedy", "remedy", Remedy.class);
    assertThat(remedy.total()).isEqualByComparingTo("9.8");
    assertThat(remedy.status()).isEqualTo("APPROVED_EXECUTION_UNAVAILABLE");
    assertThat(record("orderline", "lineA", OrderLine.class).remedyAllocated())
        .isEqualByComparingTo("1");
  }

  @Test
  void evidenceRequiresVerifiedHashAndApprovedScanAttestationBeforeDownload() {
    var context = business();
    var bytes = "owned test evidence".getBytes(java.nio.charset.StandardCharsets.UTF_8);
    evidence.upload(context, "upload", "", "text/plain", bytes);
    assertThatThrownBy(() -> evidence.download("shop", "admin", "upload"))
        .isInstanceOf(OperationException.class);
    assertThatThrownBy(
            () ->
                evidence.verify(
                    business(),
                    new EvidenceService.VerifyInput(
                        "upload", 1, "wrong", "approved-scan-result", "Actual controlled review")))
        .isInstanceOf(OperationException.class);
    evidence.verify(
        business(),
        new EvidenceService.VerifyInput(
            "upload",
            1,
            EvidenceService.hash(bytes),
            "approved-scan-result",
            "Actual controlled review"));
    assertThat(evidence.download("shop", "admin", "upload")).isEqualTo(bytes);
  }

  @Test
  void accessAdministratorDoesNotImplicitlyBecomeAnInspectorOrStockOperator() {
    put(
        "grant",
        "admin",
        "",
        new Grant("admin", PrincipalKind.HUMAN, "", Set.of(ADMIN), true, NOW.plusSeconds(86400)));
    assertThatThrownBy(
            () -> inventory.receive(customer(), stock("admin-only", Condition.QUARANTINED)))
        .isInstanceOf(OperationException.class);
    assertThat(store.read("shop", OperationEngine.key("inventory", "admin-only"))).isEmpty();
  }

  @Test
  void heldWorkpieceCannotBeReallocatedAndAFormerPassCannotResolveALaterFailure() {
    inspectionSetup();
    measure("prior-pass", "10");
    measure("failure", "11");
    assertThatThrownBy(
            () ->
                inspection.resolve(
                    customer(),
                    new InspectionService.ResolveInput(
                        "failure",
                        revision("nonconformance", "failure"),
                        "prior-pass",
                        "Attempt to reuse an older pass")))
        .isInstanceOf(OperationException.class);
    inventory.cancel(
        customer(),
        new InventoryService.CancelReservation(
            "reservation", revision("reservation", "reservation"), "Withdraw affected production"));
    assertThatThrownBy(
            () ->
                inventory.reserve(
                    customer(),
                    new InventoryService.ReserveInput(
                        "must-not-reserve",
                        "workpiece",
                        revision("inventory", "workpiece"),
                        "lineA",
                        BigDecimal.ONE)))
        .isInstanceOf(OperationException.class);
    assertThat(store.read("shop", OperationEngine.key("reservation", "must-not-reserve")))
        .isEmpty();
  }

  @Test
  void currentCustodyRevisionInvalidatesAnEarlierMeasuredRelease() {
    inspectionSetup();
    measure("pass", "10");
    inspection.gatePlan(
        customer(),
        new GatePlan(
            "gate",
            "customerA",
            "lineA",
            "TRANSFER",
            "inventory",
            "workpiece",
            "machine",
            List.of("plan")));
    inventory.move(
        customer(),
        new InventoryService.MoveInput(
            "transfer", "workpiece", revision("inventory", "workpiece"), "next-setup"));
    inventory.accept(
        customer(),
        new InventoryService.AcceptMovement("transfer", revision("movement", "transfer")));
    assertThatThrownBy(
            () ->
                inspection.release(
                    customer(),
                    new InspectionService.ReleaseInput(
                        "stale-release", "gate", revision("gateplan", "gate"), List.of("pass"))))
        .isInstanceOf(OperationException.class);
    var history = engine.history("shop", "admin", "inventory", "workpiece", "", 50);
    assertThat(history.records()).hasSize(2);
    var original =
        codec.decode(history.records().getFirst().document().json(), OperationEngine.Change.class);
    assertThat(codec.decode(original.postImage().json(), Inventory.class).location())
        .isEqualTo("receiving");
  }

  @Test
  void aSingleWorkpieceDecisionRuleCannotAcceptABatchOrHideExpiredQualification() {
    qualifiedStock();
    inventory.reserve(
        customer(),
        new InventoryService.ReserveInput(
            "batch-reservation", "stock", revision("inventory", "stock"), "lineA", BigDecimal.ONE));
    qualifiedAsset("gauge");
    inspection.plan(
        customer(),
        new InspectionPlan(
            "batch-plan",
            "customerA",
            "lineA",
            "Diameter",
            "Before transfer",
            "approved-method",
            "mm",
            bd("9.9"),
            bd("10.1"),
            bd("0.02"),
            "gauge",
            true,
            DecisionRule.INTERVAL_CONTAINMENT,
            SamplingScope.SINGLE_WORKPIECE));
    assertThatThrownBy(
            () ->
                inspection.measure(
                    customer(),
                    new InspectionService.MeasureInput(
                        "batch-pass",
                        "batch-plan",
                        1,
                        "inventory",
                        "stock",
                        "batch-reservation",
                        bd("10"),
                        bd("0.01"),
                        "mm",
                        NOW,
                        List.of("proof"))))
        .isInstanceOf(OperationException.class);
    var asset = record("asset", "gauge", Asset.class);
    put(
        "asset",
        asset.id(),
        "",
        new Asset(
            asset.id(),
            asset.name(),
            asset.manufacturerModel(),
            asset.ownerPrincipalId(),
            true,
            "",
            asset.meter(),
            asset.meterObservedAt(),
            NOW));
    var assessment = care.assess("shop", "admin", asset.id());
    assertThat(assessment.eligibility().eligible()).isFalse();
    assertThat(assessment.eligibility().reason()).isEqualTo("QUALIFICATION_EXPIRED");
  }

  @Test
  void outboxSurvivesFailedSendAndDeduplicatesWorkerProjection() {
    inventory.provision(
        business(),
        new Grant(
            "worker", PrincipalKind.SERVICE, "", Set.of(WORKER), true, NOW.plusSeconds(86400)));
    var pending = store.scan("shop", "O!", "", 200).records().getFirst();
    var work = codec.decode(pending.document().json(), OperationEngine.Work.class);
    var worker =
        new OutboxWorker(
            store,
            new WorkTransport() {
              public void send(String id) {
                throw new OperationException(503, "TEST_SEND_FAILURE", "Send unavailable.");
              }

              public Optional<Delivery> receive() {
                return Optional.empty();
              }

              public void acknowledge(Delivery delivery) {}
            },
            codec,
            Clock.fixed(NOW, ZoneOffset.UTC),
            "shop",
            "worker");
    assertThatThrownBy(worker::cycle).isInstanceOf(OperationException.class);
    assertThat(
            codec
                .decode(
                    store.read("shop", pending.key()).orElseThrow().document().json(),
                    OperationEngine.Work.class)
                .state())
        .isEqualTo("PENDING");
    worker.project(work.id());
    worker.project(work.id());
    assertThat(
            codec
                .decode(
                    store.read("shop", pending.key()).orElseThrow().document().json(),
                    OperationEngine.Work.class)
                .state())
        .isEqualTo("COMPLETED");
  }
}
