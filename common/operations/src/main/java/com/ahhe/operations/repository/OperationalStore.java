package com.ahhe.operations.repository;

import com.ahhe.operations.model.Checks;
import com.ahhe.operations.model.OperationException;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public interface OperationalStore {
  record Document(String type, String id, String customerId, String json, int schemaVersion) {
    public Document(String type, String id, String customerId, String json) {
      this(type, id, customerId, json, 1);
    }

    public Document {
      if (schemaVersion != 1) {
        throw new OperationException(
            500, "UNSUPPORTED_RECORD_SCHEMA", "Operational schema requires migration.");
      }
      Checks.id(type);
      Checks.id(id);
      customerId = customerId == null ? "" : customerId;
      if (!customerId.isEmpty()) {
        Checks.id(customerId);
      }
      Checks.required(json);
      if (json.getBytes(StandardCharsets.UTF_8).length > 24 * 1024) {
        throw new OperationException(
            422, "RECORD_TOO_LARGE", "Record exceeds the 24 KiB portable payload limit.");
      }
    }
  }

  record Stored(String key, long revision, String version, Document document) {}

  record Mutation(String key, Optional<String> expectedVersion, long revision, Document document) {}

  record Page(List<Stored> records, String continuationToken) {
    public Page {
      records = List.copyOf(records);
    }
  }

  Optional<Stored> read(String businessId, String key);

  Page scan(String businessId, String prefix, String continuationToken, int size);

  void commit(String businessId, List<Mutation> mutations);

  static void validate(List<Mutation> mutations) {
    if (mutations.isEmpty() || mutations.size() > 90) {
      throw new OperationException(
          422, "TRANSACTION_TOO_LARGE", "A transaction requires 1-90 mutations.");
    }
    var keys = new HashSet<String>();
    int bytes = 0;
    for (Mutation mutation : mutations) {
      if (!mutation.key().matches("[A-Za-z0-9_!-]{1,300}") || !keys.add(mutation.key())) {
        throw new IllegalArgumentException("Invalid or repeated transaction key.");
      }
      bytes += mutation.document().json().getBytes(StandardCharsets.UTF_8).length + 1024;
    }
    if (bytes > 512 * 1024) {
      throw new OperationException(422, "TRANSACTION_TOO_LARGE", "Transaction exceeds 512 KiB.");
    }
  }
}
