package com.ahhe.operations.repository;

import com.ahhe.operations.model.OperationException;
import com.azure.core.util.BinaryData;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.models.BlobStorageException;
import java.util.Arrays;

public final class AzureEvidenceStore implements EvidenceStore {
  private final BlobContainerClient container;
  private final boolean requireVersioning;

  public AzureEvidenceStore(BlobContainerClient container, boolean requireVersioning) {
    this.container = container;
    this.requireVersioning = requireVersioning;
  }

  @Override
  public String putImmutable(String key, byte[] bytes) {
    try {
      container.getBlobClient(key).upload(BinaryData.fromBytes(bytes), false);
    } catch (BlobStorageException exception) {
      if (exception.getStatusCode() == 409) {
        if (!Arrays.equals(read(key, ""), bytes)) {
          throw OperationException.conflict("Artifact ID already has different immutable content.");
        }
      } else {
        throw new OperationException(
            503, "EVIDENCE_UNAVAILABLE", "Evidence upload is unavailable.", exception);
      }
    }
    try {
      var version =
          java.util.Optional.ofNullable(container.getBlobClient(key).getProperties().getVersionId())
              .orElse("");
      if (requireVersioning && version.isEmpty()) {
        throw OperationException.unresolved("Cloud evidence requires enabled Blob versioning.");
      }
      return version;
    } catch (BlobStorageException exception) {
      throw new OperationException(
          503, "EVIDENCE_UNAVAILABLE", "Evidence version is unavailable.", exception);
    }
  }

  @Override
  public byte[] read(String key, String versionId) {
    try {
      var client = container.getBlobClient(key);
      if (!versionId.isEmpty()) {
        client = client.getVersionClient(versionId);
      }
      if (client.getProperties().getBlobSize() > 10 * 1024 * 1024) {
        throw new OperationException(
            422, "EVIDENCE_TOO_LARGE", "Stored evidence exceeds the bounded reader.");
      }
      return client.downloadContent().toBytes();
    } catch (BlobStorageException exception) {
      throw new OperationException(
          503, "EVIDENCE_UNAVAILABLE", "Required evidence is unavailable.", exception);
    }
  }
}
