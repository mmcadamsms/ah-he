package com.ahhe.operations;

import static org.assertj.core.api.Assertions.*;

import com.ahhe.operations.model.OperationException;
import com.ahhe.operations.repository.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

@EnabledIfEnvironmentVariable(named = "OPS_TEST_CONNECTION_STRING", matches = ".+")
class AzureStorageContractTest {
  @Test
  void azureAdaptersPreserveAtomicConditionsPaginationImmutableObjectsAndQueuePointers() {
    var suffix = UUID.randomUUID().toString().replace("-", "");
    var storage =
        AzureStorageFactory.create(
            System.getenv("OPS_TEST_TABLE_ENDPOINT"),
            System.getenv("OPS_TEST_BLOB_ENDPOINT"),
            System.getenv("OPS_TEST_QUEUE_ENDPOINT"),
            "OpsContract" + suffix,
            "ops-contract-" + suffix,
            "ops-contract-" + suffix,
            System.getenv("OPS_TEST_CONNECTION_STRING"),
            true,
            true);
    var store = storage.records();
    var first = new OperationalStore.Document("sample", "first", "", "{\"value\":1}");
    store.commit(
        "test",
        List.of(new OperationalStore.Mutation("H!sample!first", Optional.empty(), 1, first)));
    var original = store.read("test", "H!sample!first").orElseThrow();
    store.commit(
        "test",
        List.of(
            new OperationalStore.Mutation(
                original.key(),
                Optional.of(original.version()),
                2,
                new OperationalStore.Document("sample", "first", "", "{\"value\":2}"))));
    assertThatThrownBy(
            () ->
                store.commit(
                    "test",
                    List.of(
                        new OperationalStore.Mutation(
                            "H!sample!must-not-exist", Optional.empty(), 1, first),
                        new OperationalStore.Mutation(
                            original.key(), Optional.of(original.version()), 3, first))))
        .isInstanceOf(OperationException.class);
    assertThat(store.read("test", "H!sample!must-not-exist")).isEmpty();
    assertThat(store.read("test", original.key()).orElseThrow().revision()).isEqualTo(2);
    store.commit(
        "test",
        List.of(
            new OperationalStore.Mutation(
                "H!sample!second",
                Optional.empty(),
                1,
                new OperationalStore.Document("sample", "second", "", "{}")),
            new OperationalStore.Mutation(
                "H!sample!third",
                Optional.empty(),
                1,
                new OperationalStore.Document("sample", "third", "", "{}"))));
    var page = store.scan("test", "H!sample!", "", 2);
    assertThat(page.records()).hasSize(2);
    assertThat(page.continuationToken()).isNotBlank();
    assertThat(store.scan("test", "H!sample!", page.continuationToken(), 2).records()).hasSize(1);
    assertThat(store.read("other-business", original.key())).isEmpty();
    var bytes = new byte[] {1, 2, 3};
    var objectVersion = storage.evidence().putImmutable("test/immutable", bytes);
    storage.evidence().putImmutable("test/immutable", bytes);
    assertThatThrownBy(() -> storage.evidence().putImmutable("test/immutable", new byte[] {4}))
        .isInstanceOf(OperationException.class);
    assertThat(storage.evidence().read("test/immutable", objectVersion)).isEqualTo(bytes);
    var id = UUID.randomUUID().toString();
    storage.work().send(id);
    var delivery = storage.work().receive().orElseThrow();
    assertThat(delivery.workId()).isEqualTo(id);
    storage.work().acknowledge(delivery);
    assertThat(storage.work().receive()).isEmpty();
    var restarted =
        AzureStorageFactory.create(
            System.getenv("OPS_TEST_TABLE_ENDPOINT"),
            System.getenv("OPS_TEST_BLOB_ENDPOINT"),
            System.getenv("OPS_TEST_QUEUE_ENDPOINT"),
            "OpsContract" + suffix,
            "ops-contract-" + suffix,
            "ops-contract-" + suffix,
            System.getenv("OPS_TEST_CONNECTION_STRING"),
            true,
            true);
    assertThat(restarted.records().read("test", original.key()).orElseThrow().revision())
        .isEqualTo(2);
  }
}
