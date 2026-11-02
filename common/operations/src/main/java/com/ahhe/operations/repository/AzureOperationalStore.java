package com.ahhe.operations.repository;

import com.ahhe.operations.model.Checks;
import com.ahhe.operations.model.OperationException;
import com.azure.core.util.Context;
import com.azure.data.tables.TableClient;
import com.azure.data.tables.models.ListEntitiesOptions;
import com.azure.data.tables.models.TableEntity;
import com.azure.data.tables.models.TableServiceException;
import com.azure.data.tables.models.TableTransactionAction;
import com.azure.data.tables.models.TableTransactionActionType;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class AzureOperationalStore implements OperationalStore {
  private final TableClient client;

  public AzureOperationalStore(TableClient client) {
    this.client = client;
  }

  private String partition(String businessId) {
    return Checks.id(businessId) + "!ops-v1";
  }

  @Override
  public Optional<Stored> read(String businessId, String key) {
    try {
      return Optional.of(stored(client.getEntity(partition(businessId), key)));
    } catch (TableServiceException exception) {
      if (status(exception) == 404) {
        return Optional.empty();
      }
      throw unavailable(exception);
    }
  }

  @Override
  public Page scan(String businessId, String prefix, String token, int size) {
    if (!prefix.matches("[A-Za-z0-9_!-]{1,150}") || size < 1 || size > 200) {
      throw new IllegalArgumentException("Invalid bounded scan.");
    }
    var options =
        new ListEntitiesOptions()
            .setFilter(
                "PartitionKey eq '"
                    + partition(businessId)
                    + "' and RowKey ge '"
                    + prefix
                    + "' and RowKey lt '"
                    + prefix
                    + "~'")
            .setTop(size);
    try {
      var pages =
          client
              .listEntities(options, Duration.ofSeconds(30), Context.NONE)
              .iterableByPage(token.isBlank() ? null : token, size)
              .iterator();
      if (!pages.hasNext()) {
        return new Page(List.of(), "");
      }
      var page = pages.next();
      return new Page(
          page.getValue().stream().map(this::stored).toList(),
          Optional.ofNullable(page.getContinuationToken()).orElse(""));
    } catch (TableServiceException exception) {
      throw unavailable(exception);
    }
  }

  @Override
  public void commit(String businessId, List<Mutation> mutations) {
    OperationalStore.validate(mutations);
    var actions = new ArrayList<TableTransactionAction>();
    for (Mutation mutation : mutations) {
      var document = mutation.document();
      var entity =
          new TableEntity(partition(businessId), mutation.key())
              .addProperty("Revision", mutation.revision())
              .addProperty("Type", document.type())
              .addProperty("Id", document.id())
              .addProperty("CustomerId", document.customerId())
              .addProperty("Payload", document.json());
      entity.addProperty("SchemaVersion", document.schemaVersion());
      if (mutation.expectedVersion().isPresent()) {
        entity.addProperty("odata.etag", mutation.expectedVersion().get());
        actions.add(
            new TableTransactionAction(TableTransactionActionType.UPDATE_REPLACE, entity, true));
      } else {
        actions.add(new TableTransactionAction(TableTransactionActionType.CREATE, entity));
      }
    }
    try {
      client.submitTransaction(actions);
    } catch (TableServiceException exception) {
      int status = status(exception);
      String error =
          Optional.ofNullable(exception.getValue())
              .map(value -> value.getErrorCode().toString())
              .orElse("");
      if (status == 409
          || status == 412
          || java.util.Set.of(
                  "UpdateConditionNotSatisfied", "EntityAlreadyExists", "ConditionNotMet")
              .contains(error)) {
        throw OperationException.conflict(
            "Concurrent change; reload and retry with the same command key.");
      }
      throw unavailable(exception);
    }
  }

  private Stored stored(TableEntity entity) {
    var fields = entity.getProperties();
    if (!(fields.get("SchemaVersion") instanceof Integer schema)
        || schema != 1
        || !(fields.get("Revision") instanceof Long)) {
      throw new OperationException(
          500, "UNSUPPORTED_RECORD_SCHEMA", "Stored operational metadata requires reconciliation.");
    }
    return new Stored(
        entity.getRowKey(),
        ((Number) fields.get("Revision")).longValue(),
        entity.getETag(),
        new Document(
            (String) fields.get("Type"),
            (String) fields.get("Id"),
            (String) fields.get("CustomerId"),
            (String) fields.get("Payload"),
            schema));
  }

  private OperationException unavailable(TableServiceException cause) {
    return new OperationException(
        503,
        "STORAGE_OUTCOME_UNKNOWN",
        "Storage is unavailable or the outcome is unknown; retry with the same command key.",
        cause);
  }

  private int status(TableServiceException exception) {
    return Optional.ofNullable(exception.getResponse())
        .map(response -> response.getStatusCode())
        .orElse(0);
  }
}
