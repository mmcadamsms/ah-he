package com.ahhe.operations;

import com.ahhe.operations.model.OperationException;
import com.ahhe.operations.repository.OperationalStore;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class TestOperationalStore implements OperationalStore {
  private final Map<String, Stored> data = new LinkedHashMap<>();
  private long version;
  public boolean failAfterCommit;
  public Runnable beforeCommit;

  @Override
  public synchronized Optional<Stored> read(String businessId, String key) {
    return Optional.ofNullable(data.get(businessId + "/" + key));
  }

  @Override
  public synchronized Page scan(String businessId, String prefix, String token, int size) {
    var records =
        data.entrySet().stream()
            .filter(entry -> entry.getKey().startsWith(businessId + "/" + prefix))
            .map(Map.Entry::getValue)
            .sorted(java.util.Comparator.comparing(Stored::key))
            .filter(record -> token.isEmpty() || record.key().compareTo(token) > 0)
            .toList();
    var result = records.stream().limit(size).toList();
    return new Page(result, records.size() > size ? result.getLast().key() : "");
  }

  @Override
  public synchronized void commit(String businessId, List<Mutation> mutations) {
    OperationalStore.validate(mutations);
    if (beforeCommit != null) {
      var hook = beforeCommit;
      beforeCommit = null;
      hook.run();
    }
    for (var mutation : mutations) {
      var current = data.get(businessId + "/" + mutation.key());
      if (mutation.expectedVersion().isEmpty()
          ? current != null
          : current == null || !current.version().equals(mutation.expectedVersion().get())) {
        throw OperationException.conflict("Test conditional conflict.");
      }
    }
    for (var mutation : mutations) {
      data.put(
          businessId + "/" + mutation.key(),
          new Stored(mutation.key(), mutation.revision(), "v" + ++version, mutation.document()));
    }
    if (failAfterCommit) {
      failAfterCommit = false;
      throw new OperationException(
          503, "STORAGE_OUTCOME_UNKNOWN", "Simulated response loss after commit.");
    }
  }
}
