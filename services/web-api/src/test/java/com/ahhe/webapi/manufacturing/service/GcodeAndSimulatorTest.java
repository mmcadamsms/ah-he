package com.ahhe.webapi.manufacturing.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.ahhe.webapi.manufacturing.model.ManufacturingJob;
import com.ahhe.webapi.manufacturing.model.ManufacturingJob.Dimensions;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class GcodeAndSimulatorTest {

  @Test
  void generatesAndSimulatesMultiAxisProgram() {
    GcodeGenerator generator = new GcodeGenerator();
    var material =
        new ProcessPlanner.Material(
            "6061-T6 aluminum", 300, 100, 8100, 0.06, 0.12, 0.12, 0.5, 0.45);
    var generated =
        generator.generateProgram(
            new Dimensions(80, 40, 12), new Dimensions(90, 50, 20), material);
    String gcode = generated.gcode();
    ManufacturingJob job = new ManufacturingJob(UUID.randomUUID(), "part.step", "aluminum");

    HaasUmc750Simulator simulator = new HaasUmc750Simulator(0);
    var estimate = simulator.estimate(gcode);
    simulator.simulate(job, gcode);

    assertThat(gcode)
        .contains(
            "G21 G90",
            "B90. C0.",
            "B-35. C180.",
            "NOT FOR MACHINE EXECUTION",
            "VC/FZ FORMULA",
            "M00 (INSPECTION REQUIRED",
            "1 PHYSICAL CLAMPING, 3 AUTOMATIC B/C ORIENTATIONS");
    assertThat(generated.manifest().physicalSetups()).isEqualTo(1);
    assertThat(generated.manifest().indexedOrientations()).isEqualTo(3);
    assertThat(generated.manifest().operatorStops()).isEqualTo(1);
    assertThat(estimate.totalSeconds()).isGreaterThan(45);
    assertThat(job.getMachine().executionStatus()).isEqualTo("PROGRAM COMPLETE");
    assertThat(job.getMachine().alarms()).isEmpty();
    assertThat(job.getMachine().materialRemovedPercent()).isGreaterThan(80);
    assertThat(job.getMachine().elapsedSeconds()).isEqualTo(job.getMachine().estimatedTotalSeconds());
  }
}
