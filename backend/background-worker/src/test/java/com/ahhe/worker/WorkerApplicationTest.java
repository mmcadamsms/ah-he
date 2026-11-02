package com.ahhe.worker;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import com.ahhe.operations.model.OperationException;
import com.ahhe.operations.service.OutboxWorker;
import org.junit.jupiter.api.Test;
import org.springframework.boot.actuate.health.Status;

class WorkerApplicationTest {
  @Test
  void readinessTracksFailedCyclesAndRecoveryRatherThanMerelyAnOpenHttpPort() {
    var worker = mock(OutboxWorker.class);
    var poller = new WorkerApplication.Poller(worker);
    var health = new WorkerApplication().outboxHealthIndicator(poller);
    assertThat(health.health().getStatus()).isEqualTo(Status.DOWN);
    doThrow(new OperationException(503, "STORAGE_UNAVAILABLE", "Storage unavailable."))
        .when(worker)
        .cycle();
    poller.tick();
    assertThat(health.health().getStatus()).isEqualTo(Status.DOWN);
    assertThat(health.health().getDetails()).containsEntry("code", "STORAGE_UNAVAILABLE");
    doNothing().when(worker).cycle();
    poller.tick();
    assertThat(health.health().getStatus()).isEqualTo(Status.UP);
    doThrow(new IllegalStateException("Unexpected worker failure.")).when(worker).cycle();
    assertThatThrownBy(poller::tick).isInstanceOf(IllegalStateException.class);
    assertThat(health.health().getStatus()).isEqualTo(Status.DOWN);
  }
}
