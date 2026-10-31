package com.ahhe.webapi.operations;

import com.ahhe.operations.model.Checks;
import com.ahhe.operations.model.OperationContext;
import com.ahhe.operations.model.OperationException;
import com.ahhe.operations.repository.AzureStorageFactory;
import com.ahhe.operations.repository.JsonCodec;
import com.ahhe.operations.service.*;
import java.time.Clock;
import java.time.Instant;
import java.util.Set;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@ConditionalOnProperty(name = "operations.enabled", havingValue = "true")
public class OperationsConfiguration {
  @Bean
  public JsonCodec operationsCodec() {
    return new JsonCodec();
  }

  @Bean
  public Clock operationsClock() {
    return Clock.systemUTC();
  }

  @Bean
  public AzureStorageFactory.Storage operationsStorage(
      Environment environment,
      @Value("${operations.table-endpoint:}") String table,
      @Value("${operations.blob-endpoint:}") String blob,
      @Value("${operations.queue-endpoint:}") String queue,
      @Value("${operations.business-id:}") String businessId,
      @Value("${operations.provider:azure}") String provider,
      @Value("${operations.connection-string:}") String connection,
      @Value("${operations.create-local-resources:false}") boolean createResources) {
    if (!provider.equals("azure")) {
      throw new IllegalArgumentException(
          "Only the Azure adapter is implemented; unsupported providers are not silently"
              + " substituted.");
    }
    return AzureStorageFactory.create(
        table,
        blob,
        queue,
        "OperationalRecords",
        "operational-evidence",
        AzureStorageFactory.queueName(businessId),
        connection,
        Set.of(environment.getActiveProfiles()).contains("local"),
        createResources);
  }

  @Bean
  public OperationEngine operationEngine(
      AzureStorageFactory.Storage storage,
      JsonCodec codec,
      Clock clock,
      @Value("${operations.business-id:}") String businessId,
      @Value("${operations.bootstrap-principal:}") String bootstrap,
      @Value("${operations.bootstrap-expires-at:}") String expiry) {
    Checks.id(businessId);
    var engine = new OperationEngine(storage.records(), codec, clock);
    if (!bootstrap.isBlank()) {
      if (!bootstrap.matches("[a-f0-9]{64}")) {
        throw new IllegalArgumentException(
            "Bootstrap principal must be the verified issuer/subject SHA-256 mapping.");
      }
      var grant =
          new OperationContext.Grant(
              Checks.id(bootstrap),
              OperationContext.PrincipalKind.HUMAN,
              "",
              Set.of(OperationContext.Role.ADMIN),
              true,
              Instant.parse(Checks.text(expiry, 100)));
      if (!grant.expiresAt().isAfter(clock.instant())) {
        throw new IllegalArgumentException("Bootstrap authority expiry must be in the future.");
      }
      engine.initialize(businessId, grant);
    }
    return engine;
  }

  @Bean
  public InventoryService inventoryService(OperationEngine engine) {
    return new InventoryService(engine);
  }

  @Bean
  public CareService careService(OperationEngine engine) {
    return new CareService(engine);
  }

  @Bean
  public InspectionService inspectionService(OperationEngine engine) {
    return new InspectionService(engine);
  }

  @Bean
  public CommercialService commercialService(OperationEngine engine) {
    return new CommercialService(engine);
  }

  @Bean
  public ReturnService returnService(OperationEngine engine, InspectionService inspection) {
    return new ReturnService(engine, inspection);
  }

  @Bean
  public EvidenceService evidenceService(
      OperationEngine engine, AzureStorageFactory.Storage storage, JsonCodec codec) {
    return new EvidenceService(engine, storage.evidence(), codec);
  }

  @Bean
  public HealthIndicator operationalStorage(
      AzureStorageFactory.Storage storage,
      JsonCodec codec,
      @Value("${operations.business-id}") String businessId) {
    return () -> {
      try {
        var fence =
            storage
                .records()
                .read(businessId, "F!scope")
                .orElseThrow(
                    () -> OperationException.unresolved("Operating scope is not initialized."));
        return codec.decode(fence.document().json(), OperationEngine.Fence.class).open()
            ? Health.up().build()
            : Health.outOfService().build();
      } catch (OperationException exception) {
        return Health.down().withDetail("code", exception.code()).build();
      }
    };
  }
}
