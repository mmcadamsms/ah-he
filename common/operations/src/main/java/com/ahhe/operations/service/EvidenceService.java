package com.ahhe.operations.service;

import static com.ahhe.operations.model.OperationContext.Role.CUSTOMER;
import static com.ahhe.operations.model.OperationContext.Role.QUALITY;
import static com.ahhe.operations.service.BusinessRecords.Artifact;

import com.ahhe.operations.model.Checks;
import com.ahhe.operations.model.Hashes;
import com.ahhe.operations.model.OperationContext;
import com.ahhe.operations.model.OperationContext.Outcome;
import com.ahhe.operations.model.OperationException;
import com.ahhe.operations.repository.EvidenceStore;
import com.ahhe.operations.repository.JsonCodec;

public final class EvidenceService {
  private final OperationEngine engine;
  private final EvidenceStore store;
  private final JsonCodec codec;

  public EvidenceService(OperationEngine engine, EvidenceStore store, JsonCodec codec) {
    this.engine = engine;
    this.store = store;
    this.codec = codec;
  }

  public record UploadInput(
      String id, String customerId, String mediaType, String sha256, long size) {}

  public record VerifyInput(
      String id,
      long expectedRevision,
      String expectedSha256,
      String scanEvidenceReference,
      String reviewReason) {}

  public Outcome upload(
      OperationContext context, String id, String customerId, String mediaType, byte[] bytes) {
    Checks.id(id);
    if (bytes.length == 0 || bytes.length > 10 * 1024 * 1024) {
      throw new IllegalArgumentException("Evidence must contain 1 byte to 10 MiB.");
    }
    Checks.text(mediaType, 100);
    var grant = engine.actorGrant(context.businessId(), context.principalId());
    if (!grant.customerId().isEmpty() && !grant.customerId().equals(customerId)) {
      throw OperationException.denied();
    }
    var input = new UploadInput(id, customerId, mediaType, hash(bytes), bytes.length);
    var key = context.businessId() + "/" + id + "/" + input.sha256();
    return engine.execute(
        context,
        grant.customerId().isEmpty() ? QUALITY : CUSTOMER,
        "artifact.upload-pending-verification",
        input,
        unit -> {
          unit.requireBeneficiaryCustomer(customerId);
          var version = store.putImmutable(key, bytes);
          unit.create(
              "artifact",
              id,
              customerId,
              new Artifact(
                  id,
                  customerId,
                  key,
                  version,
                  input.sha256(),
                  bytes.length,
                  mediaType,
                  "UPLOADED_UNVERIFIED",
                  "",
                  ""));
        });
  }

  public Outcome verify(OperationContext context, VerifyInput input) {
    return engine.execute(
        context,
        QUALITY,
        "artifact.attest-approved-scan-and-review",
        input,
        unit -> {
          unit.requireInternal();
          Checks.text(input.scanEvidenceReference(), 500);
          Checks.text(input.reviewReason(), 1000);
          var artifact = unit.load("artifact", input.id(), Artifact.class);
          unit.expect("artifact", artifact.id(), input.expectedRevision());
          if (!artifact.status().equals("UPLOADED_UNVERIFIED")
              || !artifact.sha256().equals(input.expectedSha256())
              || !hash(store.read(artifact.objectKey(), artifact.versionId()))
                  .equals(artifact.sha256())) {
            throw OperationException.unresolved(
                "Artifact state/hash does not match the approved scan attestation.");
          }
          unit.update(
              "artifact",
              artifact.id(),
              artifact.customerId(),
              new Artifact(
                  artifact.id(),
                  artifact.customerId(),
                  artifact.objectKey(),
                  artifact.versionId(),
                  artifact.sha256(),
                  artifact.size(),
                  artifact.mediaType(),
                  "VERIFIED",
                  context.principalId(),
                  "Approved scan evidence: "
                      + input.scanEvidenceReference()
                      + "\n"
                      + input.reviewReason()));
        });
  }

  public byte[] download(String businessId, String principalId, String id) {
    var stored = engine.read(businessId, principalId, "artifact", id);
    var artifact = codec.decode(stored.document().json(), Artifact.class);
    if (!artifact.status().equals("VERIFIED")) {
      throw OperationException.unresolved(
          "Unverified content is not downloadable through the user API.");
    }
    var bytes = store.read(artifact.objectKey(), artifact.versionId());
    if (!hash(bytes).equals(artifact.sha256())) {
      throw OperationException.unresolved("Required evidence failed integrity verification.");
    }
    return bytes;
  }

  public static String hash(byte[] bytes) {
    return Hashes.sha256(bytes);
  }
}
