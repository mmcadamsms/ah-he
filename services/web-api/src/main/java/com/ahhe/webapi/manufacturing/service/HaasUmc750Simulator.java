package com.ahhe.webapi.manufacturing.service;

import com.ahhe.webapi.manufacturing.model.ManufacturingJob;
import com.ahhe.webapi.manufacturing.model.ManufacturingJob.AxisPosition;
import com.ahhe.webapi.manufacturing.model.ManufacturingJob.MachineState;
import com.ahhe.webapi.manufacturing.service.HaasGcodeInterpreter.ProgramTiming;
import com.ahhe.webapi.manufacturing.service.HaasGcodeInterpreter.TimedBlock;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HaasUmc750Simulator {

  private final HaasGcodeInterpreter interpreter;
  private final HaasUmc750MachineProfile profile;
  private final long tickMillis;

  @Autowired
  public HaasUmc750Simulator(
      HaasGcodeInterpreter interpreter,
      @Value("${manufacturing.simulation.tick-ms:100}") long tickMillis) {
    this.interpreter = interpreter;
    this.profile = HaasUmc750MachineProfile.standard2016Configuration();
    this.tickMillis = Math.max(0, tickMillis);
  }

  HaasUmc750Simulator(long tickMillis) {
    this.interpreter = new HaasGcodeInterpreter();
    this.profile = HaasUmc750MachineProfile.standard2016Configuration();
    this.tickMillis = Math.max(0, tickMillis);
  }

  public ProgramTiming estimate(String gcode) {
    return interpreter.analyze(gcode);
  }

  public void simulate(ManufacturingJob job, String gcode) {
    ProgramTiming timing = estimate(gcode);
    double simulatedElapsed = 0;
    double cuttingElapsed = 0;

    for (TimedBlock block : timing.blocks()) {
      double blockElapsed = 0;
      if (block.durationSeconds() <= 0 || tickMillis == 0) {
        blockElapsed = block.durationSeconds();
        simulatedElapsed += block.durationSeconds();
        if (block.cutting()) {
          cuttingElapsed += block.durationSeconds();
        }
        publish(
            job,
            timing,
            block,
            1,
            simulatedElapsed,
            cuttingElapsed,
            block.durationSeconds());
        handleOperatorStop(job, block);
        continue;
      }

      while (blockElapsed < block.durationSeconds()) {
        double playbackRate = job.getSimulationSpeed();
        double simulatedAdvance =
            Math.min(block.durationSeconds() - blockElapsed, tickMillis / 1000.0 * playbackRate);
        blockElapsed += simulatedAdvance;
        simulatedElapsed += simulatedAdvance;
        if (block.cutting()) {
          cuttingElapsed += simulatedAdvance;
        }
        double fraction = blockElapsed / block.durationSeconds();
        publish(job, timing, block, fraction, simulatedElapsed, cuttingElapsed, blockElapsed);
        sleep();
      }
      handleOperatorStop(job, block);
    }
  }

  private void handleOperatorStop(ManufacturingJob job, TimedBlock block) {
    if (!block.status().equals("OPERATOR STOP") || tickMillis == 0) {
      return;
    }
    job.awaitOperator(
        "Inspect the completed top-side work, verify dimensions and workholding, clear chips, "
            + "and select Resume only when the machine is safe to continue indexing.");
  }

  private void publish(
      ManufacturingJob job,
      ProgramTiming timing,
      TimedBlock block,
      double fraction,
      double simulatedElapsed,
      double cuttingElapsed,
      double blockElapsed) {
    double removed =
        timing.cuttingSeconds() <= 0
            ? 0
            : Math.min(92, cuttingElapsed * 92.0 / timing.cuttingSeconds());
    AxisPosition axes = interpolate(block.start(), block.end(), fraction);
    int spindleRpm =
        (int)
            Math.round(
                block.startSpindleRpm()
                    + (block.endSpindleRpm() - block.startSpindleRpm()) * fraction);
    double remaining = Math.max(0, timing.totalSeconds() - simulatedElapsed);
    double blockRemaining = Math.max(0, block.durationSeconds() - blockElapsed);
    String executionStatus =
        block.lineNumber() == timing.blocks().size() && fraction >= 1
            ? "PROGRAM COMPLETE"
            : block.status();

    job.setMachine(
        new MachineState(
            profile.name(),
            executionStatus,
            block.lineNumber(),
            timing.blocks().size(),
            block.raw(),
            axes,
            spindleRpm,
            block.feedMmPerMinute(),
            block.activeTool(),
            round(removed, 1),
            round(simulatedElapsed, 2),
            round(timing.totalSeconds(), 2),
            round(remaining, 2),
            round(block.durationSeconds(), 2),
            round(blockRemaining, 2),
            job.getSimulationSpeed(),
            timing.timingBasis(),
            List.of()));
  }

  private AxisPosition interpolate(AxisPosition start, AxisPosition end, double fraction) {
    return new AxisPosition(
        interpolate(start.x(), end.x(), fraction),
        interpolate(start.y(), end.y(), fraction),
        interpolate(start.z(), end.z(), fraction),
        interpolate(start.b(), end.b(), fraction),
        interpolateRotary(start.c(), end.c(), fraction));
  }

  private double interpolate(double start, double end, double fraction) {
    return start + (end - start) * fraction;
  }

  private double interpolateRotary(double start, double end, double fraction) {
    double delta = ((end - start + 540) % 360) - 180;
    double value = start + delta * fraction;
    double normalized = value % 360;
    return normalized < 0 ? normalized + 360 : normalized;
  }

  private void sleep() {
    try {
      Thread.sleep(tickMillis);
    } catch (InterruptedException interrupted) {
      Thread.currentThread().interrupt();
      throw new IllegalStateException("Simulation was interrupted.", interrupted);
    }
  }

  private double round(double value, int places) {
    double factor = Math.pow(10, places);
    return Math.round(value * factor) / factor;
  }
}
