package com.ahhe.operations.repository;

import com.ahhe.operations.model.Checks;
import com.ahhe.operations.model.Hashes;
import com.azure.data.tables.TableClientBuilder;
import com.azure.data.tables.TableServiceClientBuilder;
import com.azure.identity.WorkloadIdentityCredentialBuilder;
import com.azure.storage.blob.BlobContainerClientBuilder;
import com.azure.storage.queue.QueueClientBuilder;
import java.net.URI;

public final class AzureStorageFactory {
  private AzureStorageFactory() {}

  public record Storage(OperationalStore records, EvidenceStore evidence, WorkTransport work) {}

  public static Storage create(
      String tableEndpoint,
      String blobEndpoint,
      String queueEndpoint,
      String tableName,
      String containerName,
      String queueName,
      String connectionString,
      boolean local,
      boolean createResources) {
    var tables = new TableClientBuilder().tableName(tableName);
    var blobs = new BlobContainerClientBuilder().containerName(containerName);
    var queues = new QueueClientBuilder().queueName(queueName);
    if (connectionString != null && !connectionString.isBlank()) {
      if (!local) {
        throw new IllegalArgumentException(
            "Connection strings are permitted only in the explicit local profile.");
      }
      for (String endpoint : new String[] {tableEndpoint, blobEndpoint, queueEndpoint}) {
        var host = URI.create(endpoint).getHost();
        if (!"127.0.0.1".equals(host) && !"localhost".equals(host)) {
          throw new IllegalArgumentException("Local emulator endpoints must use loopback.");
        }
      }
      if (!connectionString.contains("BlobEndpoint=" + blobEndpoint)
          || !connectionString.contains("QueueEndpoint=" + queueEndpoint)
          || !connectionString.contains("TableEndpoint=" + tableEndpoint)) {
        throw new IllegalArgumentException(
            "Connection string must match all explicit loopback endpoints.");
      }
      tables.connectionString(connectionString);
      blobs.connectionString(connectionString);
      queues.connectionString(connectionString);
    } else {
      for (String endpoint : new String[] {tableEndpoint, blobEndpoint, queueEndpoint}) {
        if (!URI.create(Checks.text(endpoint, 1000)).getScheme().equals("https")) {
          throw new IllegalArgumentException("Cloud storage endpoints require HTTPS.");
        }
      }
      var credential = new WorkloadIdentityCredentialBuilder().build();
      tables.endpoint(tableEndpoint).credential(credential);
      blobs.endpoint(blobEndpoint).credential(credential);
      queues.endpoint(queueEndpoint).credential(credential);
    }
    var table = tables.buildClient();
    var blob = blobs.buildClient();
    var queue = queues.buildClient();
    if (createResources) {
      if (!local) {
        throw new IllegalArgumentException(
            "Runtime provisioning is permitted only for local emulation.");
      }
      new TableServiceClientBuilder()
          .connectionString(Checks.text(connectionString, 5000))
          .buildClient()
          .createTableIfNotExists(tableName);
      blob.createIfNotExists();
      queue.createIfNotExists();
    }
    return new Storage(
        new AzureOperationalStore(table),
        new AzureEvidenceStore(blob, connectionString == null || connectionString.isBlank()),
        new AzureWorkTransport(queue));
  }

  public static String queueName(String businessId) {
    return "ops-work-" + Hashes.sha256(Checks.id(businessId)).substring(0, 32);
  }
}
