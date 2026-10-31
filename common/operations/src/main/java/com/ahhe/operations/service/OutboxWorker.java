package com.ahhe.operations.service;

import com.ahhe.operations.model.OperationContext;
import com.ahhe.operations.model.OperationContext.Grant;
import com.ahhe.operations.model.OperationContext.Role;
import com.ahhe.operations.model.OperationException;
import com.ahhe.operations.repository.JsonCodec;
import com.ahhe.operations.repository.OperationalStore;
import com.ahhe.operations.repository.OperationalStore.Document;
import com.ahhe.operations.repository.OperationalStore.Mutation;
import com.ahhe.operations.repository.WorkTransport;
import java.time.Clock;
import java.util.List;
import java.util.Optional;

public final class OutboxWorker {
  private final OperationalStore store;
  private final WorkTransport transport;
  private final JsonCodec codec;
  private final Clock clock;
  private final String businessId;
  private final String principalId;
  private String continuation = "";

  public OutboxWorker(
      OperationalStore store,
      WorkTransport transport,
      JsonCodec codec,
      Clock clock,
      String businessId,
      String principalId) {
    this.store = store;
    this.transport = transport;
    this.codec = codec;
    this.clock = clock;
    this.businessId = businessId;
    this.principalId = principalId;
  }

  public void cycle() {
    var page = store.scan(businessId, "O!", continuation, 100);
    for (var record : page.records()) {
      var work = codec.decode(record.document().json(), OperationEngine.Work.class);
      if (work.state().equals("PENDING")) {
        transport.send(work.id());
      }
    }
    continuation = page.continuationToken();
    var delivery = transport.receive();
    if (delivery.isPresent()) {
      project(delivery.get().workId());
      transport.acknowledge(delivery.get());
    }
  }

  public void project(String workId) {
    var fence = required("F!scope");
    var state = codec.decode(fence.document().json(), OperationEngine.Fence.class);
    if (!state.open()) {
      throw new OperationException(503, "SCOPE_FENCED", "Worker is paused for recovery.");
    }
    var authority = required(OperationEngine.key("grant", principalId));
    var actor = codec.decode(authority.document().json(), Grant.class);
    if (!actor.permits(Role.WORKER, clock.instant())
        || actor.kind() != OperationContext.PrincipalKind.SERVICE) {
      throw OperationException.denied();
    }
    var current = required("O!" + workId);
    var work = codec.decode(current.document().json(), OperationEngine.Work.class);
    if (work.state().equals("COMPLETED")) {
      return;
    }
    if (!work.state().equals("PENDING")) {
      throw OperationException.unresolved("Work requires authorized repair.");
    }
    var audit = required("X!" + work.operationId());
    var workerAudit =
        new OperationContext.Audit(
            work.id(),
            "audit.project",
            new OperationContext(
                businessId,
                principalId,
                "",
                new OperationContext.Beneficiary(
                    OperationContext.BeneficiaryKind.BUSINESS, businessId),
                List.of(),
                "Build a recoverable audit view",
                work.id(),
                work.operationId()),
            actor,
            clock.instant(),
            List.of("X!" + work.operationId()));
    store.commit(
        businessId,
        List.of(
            new Mutation(
                "F!scope", Optional.of(fence.version()), fence.revision(), fence.document()),
            new Mutation(
                authority.key(),
                Optional.of(authority.version()),
                authority.revision(),
                authority.document()),
            new Mutation(
                current.key(),
                Optional.of(current.version()),
                current.revision() + 1,
                new Document(
                    "work",
                    work.id(),
                    "",
                    codec.encode(
                        new OperationEngine.Work(work.id(), work.operationId(), "COMPLETED", "")))),
            new Mutation(
                OperationEngine.key("auditview", work.id()), Optional.empty(), 1, audit.document()),
            new Mutation(
                "W!" + work.id(),
                Optional.empty(),
                1,
                new Document("worker-audit", work.id(), "", codec.encode(workerAudit)))));
  }

  private OperationalStore.Stored required(String key) {
    return store
        .read(businessId, key)
        .orElseThrow(
            () -> OperationException.unresolved("Required worker record is missing: " + key));
  }
}
