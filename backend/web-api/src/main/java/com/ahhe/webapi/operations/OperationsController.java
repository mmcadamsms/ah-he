package com.ahhe.webapi.operations;

import com.ahhe.operations.model.OperationContext;
import com.ahhe.operations.model.OperationContext.Outcome;
import com.ahhe.operations.model.OperationException;
import com.ahhe.operations.repository.OperationalStore;
import com.ahhe.operations.service.*;
import com.ahhe.operations.service.BusinessRecords.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/operations")
@ConditionalOnProperty(name = "operations.enabled", havingValue = "true")
public class OperationsController {
  private final OperationEngine engine;
  private final InventoryService inventory;
  private final CareService care;
  private final InspectionService inspection;
  private final CommercialService commercial;
  private final ReturnService returns;
  private final EvidenceService evidence;
  private final ObjectMapper mapper;
  private final String businessId;

  public OperationsController(
      OperationEngine engine,
      InventoryService inventory,
      CareService care,
      InspectionService inspection,
      CommercialService commercial,
      ReturnService returns,
      EvidenceService evidence,
      ObjectMapper mapper,
      @Value("${operations.business-id}") String businessId) {
    this.engine = engine;
    this.inventory = inventory;
    this.care = care;
    this.inspection = inspection;
    this.commercial = commercial;
    this.returns = returns;
    this.evidence = evidence;
    this.mapper = mapper;
    this.businessId = businessId;
  }

  public record ContextInput(
      String representedCustomerId,
      OperationContext.Beneficiary primaryBeneficiary,
      List<OperationContext.Beneficiary> additionalBeneficiaries,
      String purpose) {}

  public record Command<T>(ContextInput context, T input) {}

  public record Meta(Instant timestamp, UUID requestId) {}

  public record Envelope<T>(T data, Meta meta) {}

  public record View(String id, String type, long revision, JsonNode record) {}

  public record Page(List<View> records, String continuationToken, Instant asOf) {}

  public record Account(String businessId, OperationContext.Grant grant) {}

  private String principal(Jwt jwt) {
    if (jwt.getIssuer() == null || jwt.getSubject() == null) {
      throw OperationException.denied();
    }
    return OperationEngine.principalId(jwt.getIssuer().toString(), jwt.getSubject());
  }

  private OperationContext context(Jwt jwt, String key, ContextInput input) {
    if (input == null) {
      throw new IllegalArgumentException("Actor/beneficiary operation context is required.");
    }
    return new OperationContext(
        businessId,
        principal(jwt),
        input.representedCustomerId(),
        input.primaryBeneficiary(),
        input.additionalBeneficiaries(),
        input.purpose(),
        key,
        "");
  }

  private <T> Envelope<T> response(T value) {
    return new Envelope<>(value, new Meta(Instant.now(), UUID.randomUUID()));
  }

  @GetMapping("/me")
  public Envelope<Account> me(@AuthenticationPrincipal Jwt jwt) {
    return response(new Account(businessId, engine.actorGrant(businessId, principal(jwt))));
  }

  @GetMapping("/records/{type}")
  public Envelope<Page> list(
      @AuthenticationPrincipal Jwt jwt,
      @PathVariable String type,
      @RequestParam(defaultValue = "") String continuationToken,
      @RequestParam(defaultValue = "50") int size) {
    var page = engine.list(businessId, principal(jwt), type, continuationToken, size);
    return response(
        new Page(
            page.records().stream().map(this::view).toList(),
            page.continuationToken(),
            engine.now()));
  }

  @GetMapping("/records/{type}/{id}")
  public Envelope<View> get(
      @AuthenticationPrincipal Jwt jwt, @PathVariable String type, @PathVariable String id) {
    return response(view(engine.read(businessId, principal(jwt), type, id)));
  }

  @GetMapping("/records/{type}/{id}/history")
  public Envelope<Page> history(
      @AuthenticationPrincipal Jwt jwt,
      @PathVariable String type,
      @PathVariable String id,
      @RequestParam(defaultValue = "") String continuationToken,
      @RequestParam(defaultValue = "50") int size) {
    var page = engine.history(businessId, principal(jwt), type, id, continuationToken, size);
    var views =
        page.records().stream()
            .map(
                entry -> {
                  var change = engine.decode(entry, OperationEngine.Change.class);
                  try {
                    var frame = mapper.createObjectNode().put("operationId", change.operationId());
                    frame.set("state", mapper.readTree(change.postImage().json()));
                    return new View(
                        change.postImage().id(),
                        change.postImage().type(),
                        change.revision(),
                        frame);
                  } catch (IOException exception) {
                    throw new OperationException(
                        500,
                        "OPERATIONAL_SCHEMA_INVALID",
                        "History requires reconciliation.",
                        exception);
                  }
                })
            .toList();
    return response(new Page(views, page.continuationToken(), engine.now()));
  }

  @GetMapping("/audit/{operationId}")
  public Envelope<OperationContext.Audit> audit(
      @AuthenticationPrincipal Jwt jwt, @PathVariable String operationId) {
    return response(engine.audit(businessId, principal(jwt), operationId));
  }

  @GetMapping("/assets/{id}/eligibility")
  public Envelope<CareService.Assessment> eligibility(
      @AuthenticationPrincipal Jwt jwt, @PathVariable String id) {
    return response(care.assess(businessId, principal(jwt), id));
  }

  private View view(OperationalStore.Stored value) {
    try {
      return new View(
          value.document().id(),
          value.document().type(),
          value.revision(),
          mapper.readTree(value.document().json()));
    } catch (IOException exception) {
      throw new IllegalStateException("Invalid persisted operational record.", exception);
    }
  }

  @PostMapping("/customers")
  public Envelope<Outcome> customer(
      @AuthenticationPrincipal Jwt jwt,
      @RequestHeader("Idempotency-Key") String key,
      @RequestBody Command<Customer> command) {
    return response(inventory.customer(context(jwt, key, command.context()), command.input()));
  }

  @PostMapping("/order-lines")
  public Envelope<Outcome> order(
      @AuthenticationPrincipal Jwt jwt,
      @RequestHeader("Idempotency-Key") String key,
      @RequestBody Command<OrderLine> command) {
    return response(inventory.orderLine(context(jwt, key, command.context()), command.input()));
  }

  @PostMapping("/grants")
  public Envelope<Outcome> grant(
      @AuthenticationPrincipal Jwt jwt,
      @RequestHeader("Idempotency-Key") String key,
      @RequestBody Command<OperationContext.Grant> command) {
    return response(inventory.provision(context(jwt, key, command.context()), command.input()));
  }

  @PostMapping("/grants/revoke")
  public Envelope<Outcome> revoke(
      @AuthenticationPrincipal Jwt jwt,
      @RequestHeader("Idempotency-Key") String key,
      @RequestBody Command<InventoryService.RevokeGrant> command) {
    return response(inventory.revoke(context(jwt, key, command.context()), command.input()));
  }

  @PostMapping("/grants/replace")
  public Envelope<Outcome> replaceGrant(
      @AuthenticationPrincipal Jwt jwt,
      @RequestHeader("Idempotency-Key") String key,
      @RequestBody Command<InventoryService.ReplaceGrant> command) {
    return response(inventory.replaceGrant(context(jwt, key, command.context()), command.input()));
  }

  @PostMapping("/inventory-items")
  public Envelope<Outcome> receive(
      @AuthenticationPrincipal Jwt jwt,
      @RequestHeader("Idempotency-Key") String key,
      @RequestBody Command<Inventory> command) {
    return response(inventory.receive(context(jwt, key, command.context()), command.input()));
  }

  @PostMapping("/inventory-items/{id}/qualify")
  public Envelope<Outcome> qualify(
      @AuthenticationPrincipal Jwt jwt,
      @RequestHeader("Idempotency-Key") String key,
      @PathVariable String id,
      @RequestBody Command<InventoryService.RevisionInput> command) {
    return response(inventory.qualify(context(jwt, key, command.context()), id, command.input()));
  }

  @PostMapping("/reservations")
  public Envelope<Outcome> reserve(
      @AuthenticationPrincipal Jwt jwt,
      @RequestHeader("Idempotency-Key") String key,
      @RequestBody Command<InventoryService.ReserveInput> command) {
    return response(inventory.reserve(context(jwt, key, command.context()), command.input()));
  }

  @PostMapping("/transformations")
  public Envelope<Outcome> split(
      @AuthenticationPrincipal Jwt jwt,
      @RequestHeader("Idempotency-Key") String key,
      @RequestBody Command<InventoryService.SplitInput> command) {
    return response(inventory.split(context(jwt, key, command.context()), command.input()));
  }

  @PostMapping("/reservations/issue")
  public Envelope<Outcome> issue(
      @AuthenticationPrincipal Jwt jwt,
      @RequestHeader("Idempotency-Key") String key,
      @RequestBody Command<InventoryService.IssueInput> command) {
    return response(inventory.issue(context(jwt, key, command.context()), command.input()));
  }

  @PostMapping("/reservations/release")
  public Envelope<Outcome> releaseReservation(
      @AuthenticationPrincipal Jwt jwt,
      @RequestHeader("Idempotency-Key") String key,
      @RequestBody Command<InventoryService.CancelReservation> command) {
    return response(inventory.cancel(context(jwt, key, command.context()), command.input()));
  }

  @PostMapping("/movements")
  public Envelope<Outcome> move(
      @AuthenticationPrincipal Jwt jwt,
      @RequestHeader("Idempotency-Key") String key,
      @RequestBody Command<InventoryService.MoveInput> command) {
    return response(inventory.move(context(jwt, key, command.context()), command.input()));
  }

  @PostMapping("/movements/accept")
  public Envelope<Outcome> accept(
      @AuthenticationPrincipal Jwt jwt,
      @RequestHeader("Idempotency-Key") String key,
      @RequestBody Command<InventoryService.AcceptMovement> command) {
    return response(inventory.accept(context(jwt, key, command.context()), command.input()));
  }

  @PostMapping("/assets")
  public Envelope<Outcome> asset(
      @AuthenticationPrincipal Jwt jwt,
      @RequestHeader("Idempotency-Key") String key,
      @RequestBody Command<Asset> command) {
    return response(care.asset(context(jwt, key, command.context()), command.input()));
  }

  @PostMapping("/care-plans")
  public Envelope<Outcome> carePlan(
      @AuthenticationPrincipal Jwt jwt,
      @RequestHeader("Idempotency-Key") String key,
      @RequestBody Command<CarePlan> command) {
    return response(care.plan(context(jwt, key, command.context()), command.input()));
  }

  @PostMapping("/meter-readings")
  public Envelope<Outcome> meter(
      @AuthenticationPrincipal Jwt jwt,
      @RequestHeader("Idempotency-Key") String key,
      @RequestBody Command<CareService.MeterInput> command) {
    return response(care.meter(context(jwt, key, command.context()), command.input()));
  }

  @PostMapping("/care-work")
  public Envelope<Outcome> work(
      @AuthenticationPrincipal Jwt jwt,
      @RequestHeader("Idempotency-Key") String key,
      @RequestBody Command<CareService.WorkInput> command) {
    return response(care.work(context(jwt, key, command.context()), command.input()));
  }

  @PostMapping("/care-work/progress")
  public Envelope<Outcome> progress(
      @AuthenticationPrincipal Jwt jwt,
      @RequestHeader("Idempotency-Key") String key,
      @RequestBody Command<CareService.ProgressInput> command) {
    return response(care.progress(context(jwt, key, command.context()), command.input()));
  }

  @PostMapping("/care-work/release")
  public Envelope<Outcome> careRelease(
      @AuthenticationPrincipal Jwt jwt,
      @RequestHeader("Idempotency-Key") String key,
      @RequestBody Command<CareService.ReleaseInput> command) {
    return response(care.release(context(jwt, key, command.context()), command.input()));
  }

  @PostMapping("/inspection-plans")
  public Envelope<Outcome> inspectionPlan(
      @AuthenticationPrincipal Jwt jwt,
      @RequestHeader("Idempotency-Key") String key,
      @RequestBody Command<InspectionPlan> command) {
    return response(inspection.plan(context(jwt, key, command.context()), command.input()));
  }

  @PostMapping("/inspection-results")
  public Envelope<Outcome> measure(
      @AuthenticationPrincipal Jwt jwt,
      @RequestHeader("Idempotency-Key") String key,
      @RequestBody Command<InspectionService.MeasureInput> command) {
    return response(inspection.measure(context(jwt, key, command.context()), command.input()));
  }

  @PostMapping("/nonconformances/resolve")
  public Envelope<Outcome> resolve(
      @AuthenticationPrincipal Jwt jwt,
      @RequestHeader("Idempotency-Key") String key,
      @RequestBody Command<InspectionService.ResolveInput> command) {
    return response(inspection.resolve(context(jwt, key, command.context()), command.input()));
  }

  @PostMapping("/gate-plans")
  public Envelope<Outcome> gate(
      @AuthenticationPrincipal Jwt jwt,
      @RequestHeader("Idempotency-Key") String key,
      @RequestBody Command<GatePlan> command) {
    return response(inspection.gatePlan(context(jwt, key, command.context()), command.input()));
  }

  @PostMapping("/operation-releases")
  public Envelope<Outcome> release(
      @AuthenticationPrincipal Jwt jwt,
      @RequestHeader("Idempotency-Key") String key,
      @RequestBody Command<InspectionService.ReleaseInput> command) {
    return response(inspection.release(context(jwt, key, command.context()), command.input()));
  }

  @PostMapping("/benchmarks")
  public Envelope<Outcome> benchmark(
      @AuthenticationPrincipal Jwt jwt,
      @RequestHeader("Idempotency-Key") String key,
      @RequestBody Command<Benchmark> command) {
    return response(commercial.benchmark(context(jwt, key, command.context()), command.input()));
  }

  @PostMapping("/quotes")
  public Envelope<Outcome> quote(
      @AuthenticationPrincipal Jwt jwt,
      @RequestHeader("Idempotency-Key") String key,
      @RequestBody Command<Quote> command) {
    return response(commercial.quote(context(jwt, key, command.context()), command.input()));
  }

  @PostMapping("/quote-comparisons")
  public Envelope<Outcome> comparison(
      @AuthenticationPrincipal Jwt jwt,
      @RequestHeader("Idempotency-Key") String key,
      @RequestBody Command<CommercialService.ComparisonInput> command) {
    return response(commercial.compare(context(jwt, key, command.context()), command.input()));
  }

  @PostMapping("/return-policies")
  public Envelope<Outcome> policy(
      @AuthenticationPrincipal Jwt jwt,
      @RequestHeader("Idempotency-Key") String key,
      @RequestBody Command<ReturnPolicy> command) {
    return response(returns.policy(context(jwt, key, command.context()), command.input()));
  }

  @PostMapping("/returns")
  public Envelope<Outcome> request(
      @AuthenticationPrincipal Jwt jwt,
      @RequestHeader("Idempotency-Key") String key,
      @RequestBody Command<ReturnService.RequestInput> command) {
    return response(returns.request(context(jwt, key, command.context()), command.input()));
  }

  @PostMapping("/returns/authorize")
  public Envelope<Outcome> authorize(
      @AuthenticationPrincipal Jwt jwt,
      @RequestHeader("Idempotency-Key") String key,
      @RequestBody Command<ReturnService.AuthorizeInput> command) {
    return response(returns.authorize(context(jwt, key, command.context()), command.input()));
  }

  @PostMapping("/return-packages")
  public Envelope<Outcome> returned(
      @AuthenticationPrincipal Jwt jwt,
      @RequestHeader("Idempotency-Key") String key,
      @RequestBody Command<ReturnService.ReceiveInput> command) {
    return response(returns.receive(context(jwt, key, command.context()), command.input()));
  }

  @PostMapping("/return-packages/identify")
  public Envelope<Outcome> identify(
      @AuthenticationPrincipal Jwt jwt,
      @RequestHeader("Idempotency-Key") String key,
      @RequestBody Command<ReturnService.IdentifyInput> command) {
    return response(returns.identify(context(jwt, key, command.context()), command.input()));
  }

  @PostMapping("/remedies")
  public Envelope<Outcome> remedy(
      @AuthenticationPrincipal Jwt jwt,
      @RequestHeader("Idempotency-Key") String key,
      @RequestBody Command<ReturnService.RemedyInput> command) {
    return response(returns.remedy(context(jwt, key, command.context()), command.input()));
  }

  @PostMapping(value = "/artifacts", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public Envelope<Outcome> upload(
      @AuthenticationPrincipal Jwt jwt,
      @RequestHeader("Idempotency-Key") String key,
      @RequestPart("context") ContextInput input,
      @RequestPart("file") MultipartFile file,
      @RequestParam String id,
      @RequestParam(defaultValue = "") String customerId)
      throws IOException {
    return response(
        evidence.upload(
            context(jwt, key, input),
            id,
            customerId,
            file.getContentType() == null ? "application/octet-stream" : file.getContentType(),
            file.getBytes()));
  }

  @PostMapping("/artifacts/verify")
  public Envelope<Outcome> verify(
      @AuthenticationPrincipal Jwt jwt,
      @RequestHeader("Idempotency-Key") String key,
      @RequestBody Command<EvidenceService.VerifyInput> command) {
    return response(evidence.verify(context(jwt, key, command.context()), command.input()));
  }

  @GetMapping("/artifacts/{id}/content")
  public ResponseEntity<byte[]> download(
      @AuthenticationPrincipal Jwt jwt, @PathVariable String id) {
    return ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_OCTET_STREAM)
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment")
        .header("X-Content-Type-Options", "nosniff")
        .body(evidence.download(businessId, principal(jwt), id));
  }
}
