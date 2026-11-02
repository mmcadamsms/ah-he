package com.ahhe.worker;

import com.ahhe.operations.model.Checks;
import com.ahhe.operations.model.OperationException;
import com.ahhe.operations.repository.AzureStorageFactory;
import com.ahhe.operations.repository.JsonCodec;
import com.ahhe.operations.service.OutboxWorker;
import java.time.Clock;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class WorkerApplication {
  public static void main(String[] args) {
    SpringApplication.run(WorkerApplication.class, args);
  }

  @Bean
  public OutboxWorker outbox(
      Environment environment,
      @Value("${operations.business-id}") String business,
      @Value("${operations.worker-principal}") String principal,
      @Value("${operations.table-endpoint}") String table,
      @Value("${operations.blob-endpoint}") String blob,
      @Value("${operations.queue-endpoint}") String queue,
      @Value("${operations.provider:azure}") String provider,
      @Value("${operations.connection-string:}") String connection) {
    if (!provider.equals("azure")) {
      throw new IllegalArgumentException("Unsupported operational storage provider.");
    }
    var storage =
        AzureStorageFactory.create(
            table,
            blob,
            queue,
            "OperationalRecords",
            "operational-evidence",
            AzureStorageFactory.queueName(business),
            connection,
            Set.of(environment.getActiveProfiles()).contains("local"),
            false);
    return new OutboxWorker(
        storage.records(),
        storage.work(),
        new JsonCodec(),
        Clock.systemUTC(),
        Checks.id(business),
        Checks.id(principal));
  }

  @Bean
  public Poller poller(OutboxWorker outbox) {
    return new Poller(outbox);
  }

  @Bean
  public HealthIndicator outboxHealthIndicator(Poller poller) {
    return () -> {
      String failure = poller.failureCode;
      return failure.isEmpty()
          ? Health.up().build()
          : Health.down().withDetail("code", failure).build();
    };
  }

  public static final class Poller {
    private static final Logger LOG = LoggerFactory.getLogger(Poller.class);
    private final OutboxWorker outbox;
    private volatile String failureCode = "INITIALIZING";

    public Poller(OutboxWorker outbox) {
      this.outbox = outbox;
    }

    @Scheduled(fixedDelayString = "${operations.poll-ms:15000}")
    public void tick() {
      failureCode = "CYCLE_IN_PROGRESS";
      try {
        outbox.cycle();
        failureCode = "";
      } catch (OperationException exception) {
        failureCode = exception.code();
        LOG.atError()
            .addKeyValue("event.name", "outbox.cycle.failed")
            .addKeyValue("error.code", exception.code())
            .log("Outbox remains pending; reconciliation will retry.");
      }
    }
  }
}
