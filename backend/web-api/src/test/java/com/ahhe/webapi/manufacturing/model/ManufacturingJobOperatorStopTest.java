package com.ahhe.webapi.manufacturing.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class ManufacturingJobOperatorStopTest {

  @Test
  void waitsUntilOperatorExplicitlyResumes() throws Exception {
    ManufacturingJob job =
        new ManufacturingJob(UUID.randomUUID(), "part.step", "aluminum", 5);

    Thread waiter = Thread.ofVirtual().start(() -> job.awaitOperator("Inspect the part."));

    awaitStatus(job, ManufacturingJob.JobStatus.WAITING_FOR_OPERATOR);
    assertThat(waiter.isAlive()).isTrue();

    job.resumeFromOperatorStop();
    waiter.join(Duration.ofSeconds(2));

    assertThat(waiter.isAlive()).isFalse();
    assertThat(job.getStatus()).isEqualTo(ManufacturingJob.JobStatus.SIMULATING);
    assertThat(job.getTimeline())
        .anyMatch(event -> event.title().equals("Program paused at M00"))
        .anyMatch(event -> event.title().equals("Operator resumed program"));
  }

  private void awaitStatus(ManufacturingJob job, ManufacturingJob.JobStatus expected)
      throws InterruptedException {
    for (int attempt = 0; attempt < 100; attempt++) {
      if (job.getStatus() == expected) {
        return;
      }
      Thread.sleep(10);
    }
    throw new AssertionError("Job did not enter " + expected);
  }
}

