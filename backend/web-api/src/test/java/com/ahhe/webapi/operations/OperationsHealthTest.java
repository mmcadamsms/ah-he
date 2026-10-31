package com.ahhe.webapi.operations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.ahhe.operations.model.OperationException;
import com.ahhe.operations.repository.AzureStorageFactory;
import com.ahhe.operations.repository.JsonCodec;
import com.ahhe.operations.repository.OperationalStore;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.boot.actuate.health.Status;

class OperationsHealthTest {
  @Test
  void readinessRequiresAnInitializedOpenScopeAndReachableAuthoritativeStorage() {
    var records = mock(OperationalStore.class);
    var health =
        new OperationsConfiguration()
            .operationalStorage(
                new AzureStorageFactory.Storage(records, null, null), new JsonCodec(), "shop");
    when(records.read("shop", "F!scope")).thenReturn(Optional.empty());
    assertThat(health.health().getStatus()).isEqualTo(Status.DOWN);
    when(records.read("shop", "F!scope"))
        .thenReturn(
            Optional.of(
                new OperationalStore.Stored(
                    "F!scope",
                    1,
                    "etag",
                    new OperationalStore.Document("fence", "scope", "", "{\"open\":false}"))));
    assertThat(health.health().getStatus()).isEqualTo(Status.OUT_OF_SERVICE);
    when(records.read("shop", "F!scope"))
        .thenReturn(
            Optional.of(
                new OperationalStore.Stored(
                    "F!scope",
                    1,
                    "etag",
                    new OperationalStore.Document("fence", "scope", "", "{\"open\":true}"))));
    assertThat(health.health().getStatus()).isEqualTo(Status.UP);
    when(records.read("shop", "F!scope"))
        .thenThrow(new OperationException(503, "STORAGE_UNAVAILABLE", "Storage unavailable."));
    assertThat(health.health().getStatus()).isEqualTo(Status.DOWN);
  }
}
