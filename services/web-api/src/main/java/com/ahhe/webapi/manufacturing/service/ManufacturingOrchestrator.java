package com.ahhe.webapi.manufacturing.service;

import com.ahhe.webapi.manufacturing.model.ManufacturingJob;
import com.ahhe.webapi.manufacturing.model.ManufacturingJob.Actor;
import com.ahhe.webapi.manufacturing.model.ManufacturingJob.GeometryAnalysis;
import com.ahhe.webapi.manufacturing.model.ManufacturingJob.JobStatus;
import com.ahhe.webapi.manufacturing.repository.ManufacturingJobRepository;
import com.ahhe.webapi.manufacturing.service.ProcessPlanner.Plan;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ManufacturingOrchestrator {

  private final ManufacturingJobRepository repository;
  private final GeometryAnalyzer geometryAnalyzer;
  private final ProcessPlanner processPlanner;
  private final GcodeGenerator gcodeGenerator;
  private final HaasUmc750Simulator simulator;

  public ManufacturingOrchestrator(
      ManufacturingJobRepository repository,
      GeometryAnalyzer geometryAnalyzer,
      ProcessPlanner processPlanner,
      GcodeGenerator gcodeGenerator,
      HaasUmc750Simulator simulator) {
    this.repository = repository;
    this.geometryAnalyzer = geometryAnalyzer;
    this.processPlanner = processPlanner;
    this.gcodeGenerator = gcodeGenerator;
    this.simulator = simulator;
  }

  @Async("manufacturingExecutor")
  public void process(ManufacturingJob job, byte[] content) {
    try {
      job.updateStage(
          JobStatus.ANALYZING_GEOMETRY,
          10,
          "Analyzing CAD geometry",
          "Reading the exchange file and estimating its model envelope.",
          Actor.SYSTEM);
      GeometryAnalysis geometry = geometryAnalyzer.analyze(job.getFileName(), content);
      job.setGeometry(geometry);

      job.updateStage(
          JobStatus.SELECTING_STOCK,
          25,
          "Selecting material stock",
          "Mapping the requested material to a machinable grade and adding workholding allowance.",
          Actor.SYSTEM);
      Plan plan = processPlanner.createPlan(job.getRequestedMaterial(), geometry);
      job.setPlan(plan.stock(), plan.tools(), plan.operations());

      job.updateStage(
          JobStatus.PLANNING_TOOLING,
          40,
          "Planning tooling and human work",
          "Building the magazine load list and the setup/inspection sequence.",
          Actor.HUMAN);

      job.updateStage(
          JobStatus.GENERATING_GCODE,
          55,
          "Generating representative G-code",
          "Creating a non-production Haas-style program for the proposed setups.",
          Actor.SYSTEM);
      GcodeGenerator.GeneratedProgram generatedProgram =
          gcodeGenerator.generateProgram(
              geometry.dimensions(), plan.stock().dimensions(), plan.material());
      String gcode = generatedProgram.gcode();
      job.setProgram(gcode, generatedProgram.manifest());

      job.updateStage(
          JobStatus.SIMULATING,
          70,
          "Simulating the UMC-750",
          "Interpreting program blocks and publishing machine telemetry.",
          Actor.MACHINE);
      simulator.simulate(job, gcode);
      job.complete();
    } catch (RuntimeException exception) {
      job.fail(exception.getMessage());
    } finally {
      repository.save(job);
    }
  }
}
