package com.ahhe.operations.service;

import static com.ahhe.operations.model.OperationContext.Role.ADMIN;

import com.ahhe.operations.model.Checks;
import com.ahhe.operations.model.Hashes;
import com.ahhe.operations.model.OperationContext;
import com.ahhe.operations.model.OperationContext.Audit;
import com.ahhe.operations.model.OperationContext.Grant;
import com.ahhe.operations.model.OperationContext.Outcome;
import com.ahhe.operations.model.OperationContext.Role;
import com.ahhe.operations.model.OperationException;
import com.ahhe.operations.repository.JsonCodec;
import com.ahhe.operations.repository.OperationalStore;
import com.ahhe.operations.repository.OperationalStore.Document;
import com.ahhe.operations.repository.OperationalStore.Mutation;
import com.ahhe.operations.repository.OperationalStore.Stored;
import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

public final class OperationEngine {
  private static final org.slf4j.Logger LOG =
      org.slf4j.LoggerFactory.getLogger(OperationEngine.class);
  private final OperationalStore store;
  private final JsonCodec codec;
  private final Clock clock;

  public OperationEngine(OperationalStore store, JsonCodec codec, Clock clock) {
    this.store = store;
    this.codec = codec;
    this.clock = clock;
  }

  public record Fence(boolean open) {}

  public record Receipt(String digest, Outcome outcome) {}

  public record Work(String id, String operationId, String state, String errorCode) {}

  public record Change(String operationId, long revision, Document postImage) {}

  public record DigestInput(String action, Object input, OperationContext context) {}

  public static String principalId(String issuer, String subject) {
    return digest(Checks.text(issuer, 500) + "\n" + Checks.text(subject, 500));
  }

  public static String digest(String value) {
    return Hashes.sha256(value);
  }

  public static String key(String type, String id) {
    return "H!" + Checks.id(type) + "!" + Checks.id(id);
  }

  public Instant now() {
    return clock.instant();
  }

  public static String qualityHoldId(String type, String id) {
    return digest(Checks.id(type) + "\n" + Checks.id(id));
  }

  public void initialize(String businessId, Grant initialAdministrator) {
    if (store.read(businessId, "F!scope").isPresent()) {
      return;
    }
    var operationId = UUID.randomUUID().toString();
    var context =
        new OperationContext(
            businessId,
            initialAdministrator.id(),
            "",
            new OperationContext.Beneficiary(OperationContext.BeneficiaryKind.BUSINESS, businessId),
            List.of(),
            "Explicit deployment bootstrap of the operating business",
            operationId,
            "");
    store.commit(
        businessId,
        List.of(
            create("F!scope", new Document("fence", "scope", "", codec.encode(new Fence(true)))),
            create(
                key("grant", initialAdministrator.id()),
                new Document(
                    "grant", initialAdministrator.id(), "", codec.encode(initialAdministrator))),
            create(
                "X!" + operationId,
                new Document(
                    "audit",
                    operationId,
                    "",
                    codec.encode(
                        new Audit(
                            operationId,
                            "bootstrap",
                            context,
                            initialAdministrator,
                            now(),
                            List.of(key("grant", initialAdministrator.id()))))))));
  }

  private Mutation create(String key, Document document) {
    return new Mutation(key, Optional.empty(), 1, document);
  }

  public Outcome execute(
      OperationContext context, Role role, String action, Object input, Consumer<Unit> operation) {
    var unit = new Unit(context);
    var grant = unit.load("grant", context.principalId(), Grant.class);
    if (!grant.permits(role, now())) {
      throw OperationException.denied();
    }
    unit.actor = grant;
    unit.boundValidity(grant.expiresAt());
    unit.validateContext();
    var receiptId =
        UUID.nameUUIDFromBytes(
                (context.principalId() + "\n" + context.idempotencyKey())
                    .getBytes(StandardCharsets.UTF_8))
            .toString();
    var digest = digest(codec.encode(new DigestInput(action, input, context)));
    var previous = store.read(context.businessId(), "C!" + receiptId);
    if (previous.isPresent()) {
      var receipt = codec.decode(previous.get().document().json(), Receipt.class);
      if (!receipt.digest().equals(digest)) {
        throw OperationException.conflict("Command key was already used for different intent.");
      }
      return receipt.outcome();
    }
    operation.accept(unit);
    if (!grant.permits(role, now())) {
      throw OperationException.denied();
    }
    if (!now().isBefore(unit.validUntil)) {
      throw OperationException.unresolved(
          "Authority or prerequisite validity expired before commit.");
    }
    var outcome = new Outcome(receiptId, List.copyOf(unit.changed.keySet()), now());
    var mutations = new ArrayList<Mutation>();
    unit.loaded.forEach(
        (key, stored) ->
            mutations.add(
                new Mutation(
                    key,
                    Optional.of(stored.version()),
                    stored.revision() + (unit.changed.containsKey(key) ? 1 : 0),
                    unit.changed.getOrDefault(key, stored.document()))));
    unit.changed.forEach(
        (key, document) -> {
          if (!unit.loaded.containsKey(key)) {
            mutations.add(create(key, document));
          }
          long revision = unit.loaded.containsKey(key) ? unit.loaded.get(key).revision() + 1 : 1;
          var historyId = UUID.randomUUID().toString();
          mutations.add(
              create(
                  "E!" + key + "!" + String.format("%020d", revision),
                  new Document(
                      "change",
                      historyId,
                      document.customerId(),
                      codec.encode(new Change(receiptId, revision, document)))));
        });
    mutations.add(
        create(
            "C!" + receiptId,
            new Document(
                "receipt",
                receiptId,
                grant.customerId(),
                codec.encode(new Receipt(digest, outcome)))));
    mutations.add(
        create(
            "X!" + receiptId,
            new Document(
                "audit",
                receiptId,
                grant.customerId(),
                codec.encode(
                    new Audit(
                        receiptId,
                        action,
                        context,
                        grant,
                        outcome.committedAt(),
                        outcome.subjects())))));
    mutations.add(
        create(
            "O!" + receiptId,
            new Document(
                "work",
                receiptId,
                "",
                codec.encode(new Work(receiptId, receiptId, "PENDING", "")))));
    store.commit(context.businessId(), mutations);
    LOG.atInfo()
        .addKeyValue("event.name", "operation.committed")
        .addKeyValue("operation.id", receiptId)
        .addKeyValue("operation.action", action)
        .log("Operational change committed.");
    return outcome;
  }

  public Stored read(String businessId, String principalId, String type, String id) {
    var grant = grant(businessId, principalId);
    requireRead(grant, type);
    var stored =
        store
            .read(businessId, key(type, id))
            .orElseThrow(() -> new OperationException(404, "NOT_FOUND", "Record not found."));
    visible(grant, stored.document());
    return stored;
  }

  public <T> T decode(Stored record, Class<T> type) {
    return codec.decode(record.document().json(), type);
  }

  public OperationalStore.Page history(
      String businessId, String principalId, String type, String id, String token, int size) {
    var grant = grant(businessId, principalId);
    requireRead(grant, type);
    read(businessId, principalId, type, id);
    var page = store.scan(businessId, "E!" + key(type, id) + "!", token, size);
    return new OperationalStore.Page(
        page.records().stream()
            .filter(
                record ->
                    grant.customerId().isEmpty()
                        || grant.customerId().equals(record.document().customerId()))
            .toList(),
        page.continuationToken());
  }

  public Audit audit(String businessId, String principalId, String operationId) {
    if (!grant(businessId, principalId).permits(ADMIN, now())) {
      throw OperationException.denied();
    }
    var entry =
        store
            .read(businessId, "X!" + Checks.id(operationId))
            .orElseThrow(
                () -> new OperationException(404, "NOT_FOUND", "Audit context not found."));
    return codec.decode(entry.document().json(), Audit.class);
  }

  public OperationalStore.Page list(
      String businessId, String principalId, String type, String token, int size) {
    var grant = grant(businessId, principalId);
    requireRead(grant, type);
    var page = store.scan(businessId, "H!" + Checks.id(type) + "!", token, size);
    var visible =
        page.records().stream()
            .filter(
                record ->
                    grant.customerId().isEmpty()
                        || type.equals("returnpolicy")
                        || (!record.document().customerId().isEmpty()
                            && grant.customerId().equals(record.document().customerId())))
            .toList();
    return new OperationalStore.Page(visible, page.continuationToken());
  }

  public Grant actorGrant(String businessId, String principalId) {
    var grant =
        store
            .read(businessId, key("grant", principalId))
            .map(record -> codec.decode(record.document().json(), Grant.class))
            .orElseThrow(OperationException::denied);
    if (!grant.active() || !now().isBefore(grant.expiresAt())) {
      throw OperationException.denied();
    }

    return grant;
  }

  private Grant grant(String businessId, String principalId) {
    return actorGrant(businessId, principalId);
  }

  private void visible(Grant grant, Document document) {
    if (!grant.customerId().isEmpty()
        && !document.type().equals("returnpolicy")
        && !grant.customerId().equals(document.customerId())) {
      throw OperationException.denied();
    }
  }

  private void requireRead(Grant grant, String type) {
    if (grant.roles().contains(ADMIN)) {
      return;
    }
    if (!grant.customerId().isEmpty()) {
      if (java.util.Set.of(
                  "customer", "orderline", "rma", "package", "remedy", "returnpolicy", "artifact")
              .contains(type)
          || (type.equals("grant") && grant.roles().contains(Role.CUSTOMER_ADMIN))) {
        return;
      }
      throw OperationException.denied();
    }
    Role required =
        switch (type) {
          case "inventory", "reservation", "movement" -> Role.INVENTORY;
          case "asset", "assetplan", "careplan", "carework" -> Role.MAINTENANCE;
          case "inspectionplan",
                  "inspectionresult",
                  "nonconformance",
                  "qualityhold",
                  "gateplan",
                  "release",
                  "artifact" ->
              Role.QUALITY;
          case "customer",
                  "orderline",
                  "benchmark",
                  "quote",
                  "comparison",
                  "remedy",
                  "returnpolicy" ->
              Role.COMMERCIAL;
          case "rma", "package" -> Role.RETURNS;
          case "auditview" -> Role.WORKER;
          default -> throw OperationException.denied();
        };
    if (!grant.permits(required, now())
        && !(required == Role.MAINTENANCE && grant.permits(Role.QUALITY, now()))) {
      throw OperationException.denied();
    }
  }

  public final class Unit {
    private final OperationContext context;
    private final Map<String, Stored> loaded = new LinkedHashMap<>();
    private final Map<String, Document> changed = new LinkedHashMap<>();
    private Grant actor;
    private Instant validUntil = Instant.MAX;

    private Unit(OperationContext context) {
      this.context = context;
      var fence = loadSpecial("F!scope", Fence.class);
      if (!fence.open()) {
        throw new OperationException(
            503, "SCOPE_FENCED", "Writes are paused for recovery or migration.");
      }
    }

    public Grant actor() {
      return actor;
    }

    public void boundValidity(Instant deadline) {
      Checks.required(deadline);
      if (deadline.isBefore(validUntil)) {
        validUntil = deadline;
      }
    }

    public Instant validUntil() {
      return validUntil;
    }

    public OperationContext context() {
      return context;
    }

    public <T> T load(String type, String id, Class<T> recordType) {
      var result = loadSpecial(key(type, id), recordType);
      if (actor != null) {
        visible(actor, loaded.get(key(type, id)).document());
      }
      return result;
    }

    private <T> T loadSpecial(String key, Class<T> type) {
      var stored =
          loaded.computeIfAbsent(
              key,
              ignored ->
                  store
                      .read(context.businessId(), key)
                      .orElseThrow(
                          () ->
                              OperationException.unresolved("Required record is missing: " + key)));
      return codec.decode(changed.getOrDefault(key, stored.document()).json(), type);
    }

    public long revision(String type, String id) {
      String key = key(type, id);
      if (!loaded.containsKey(key)) {
        throw new IllegalStateException("Revision must be read after the record.");
      }
      return loaded.get(key).revision();
    }

    public void expect(String type, String id, long expected) {
      if (revision(type, id) != expected) {
        throw OperationException.conflict("The expected record revision is stale.");
      }
    }

    public void create(String type, String id, String customerId, Object record) {
      var key = key(type, id);
      if (loaded.containsKey(key) || changed.containsKey(key)) {
        throw OperationException.conflict("The record already participates in this command.");
      }
      var document = new Document(type, id, customerId, codec.encode(record));
      visible(actor, document);
      changed.put(key, document);
    }

    public void update(String type, String id, String customerId, Object record) {
      var key = key(type, id);
      if (!loaded.containsKey(key)) {
        throw new IllegalStateException("An update requires an authoritative read.");
      }
      var document = new Document(type, id, customerId, codec.encode(record));
      visible(actor, document);
      changed.put(key, document);
    }

    private void validateContext() {
      if (!context.representedCustomerId().isEmpty()) {
        load("customer", context.representedCustomerId(), BusinessRecords.Customer.class);
        if (!actor.customerId().isEmpty()
            && !actor.customerId().equals(context.representedCustomerId())) {
          throw OperationException.denied();
        }
      } else if (!actor.customerId().isEmpty()) {
        throw OperationException.denied();
      }
      var beneficiaries = new ArrayList<>(context.additionalBeneficiaries());
      beneficiaries.add(context.primaryBeneficiary());
      for (var beneficiary : beneficiaries) {
        switch (beneficiary.kind()) {
          case BUSINESS -> {
            if (!actor.customerId().isEmpty() || !beneficiary.id().equals(context.businessId())) {
              throw OperationException.denied();
            }
          }
          case CUSTOMER -> load("customer", beneficiary.id(), BusinessRecords.Customer.class);
          case ORDER_LINE -> load("orderline", beneficiary.id(), BusinessRecords.OrderLine.class);
        }
      }
    }

    public void requireInternal() {
      if (!actor.customerId().isEmpty()) {
        throw OperationException.denied();
      }
    }

    public void requireBeneficiaryCustomer(String customerId) {
      var beneficiary = context.primaryBeneficiary();
      boolean matches =
          switch (beneficiary.kind()) {
            case BUSINESS -> customerId.isEmpty();
            case CUSTOMER -> beneficiary.id().equals(customerId);
            case ORDER_LINE ->
                load("orderline", beneficiary.id(), BusinessRecords.OrderLine.class)
                    .customerId()
                    .equals(customerId);
          };
      if (!matches) {
        throw OperationException.unresolved(
            "Primary beneficiary does not match the operation's customer scope.");
      }
    }
  }
}
