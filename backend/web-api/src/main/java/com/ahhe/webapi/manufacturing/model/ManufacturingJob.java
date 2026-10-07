package com.ahhe.webapi.manufacturing.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class ManufacturingJob {

  public static final String SAFETY_NOTICE =
      "DEMONSTRATION ONLY. Generated process plans and G-code are not validated for physical "
          + "machine use. A qualified machinist must use approved CAM, postprocessing, collision "
          + "verification, and prove-out procedures.";

  private final UUID id;
  private final String fileName;
  private final String requestedMaterial;
  private final Instant createdAt;
  private volatile double simulationSpeed;
  private JobStatus status;
  private int progress;
  private String currentStage;
  private Instant updatedAt;
  private GeometryAnalysis geometry;
  private StockSelection stock;
  private List<ToolDefinition> tools = List.of();
  private List<ManufacturingOperation> operations = List.of();
  private String gcode;
  private ProgramManifest programManifest;
  private MachineState machine;
  private final List<JobEvent> timeline = new ArrayList<>();
  private String failure;
  private long operatorResumeSequence;

  public ManufacturingJob(UUID id, String fileName, String requestedMaterial) {
    this(id, fileName, requestedMaterial, 5);
  }

  public ManufacturingJob(
      UUID id, String fileName, String requestedMaterial, double simulationSpeed) {
    this.id = id;
    this.fileName = fileName;
    this.requestedMaterial = requestedMaterial;
    this.simulationSpeed = clampSimulationSpeed(simulationSpeed);
    this.createdAt = Instant.now();
    this.updatedAt = createdAt;
    this.status = JobStatus.QUEUED;
    this.progress = 1;
    this.currentStage = "Submission received";
    this.machine = MachineState.idle();
    addEvent(
        "INTAKE",
        "Drawing received",
        fileName + " was accepted and queued for background analysis.",
        Actor.SYSTEM,
        EventStatus.COMPLETED);
  }

  public synchronized void updateStage(
      JobStatus status, int progress, String stage, String detail, Actor actor) {
    this.status = status;
    this.progress = Math.max(0, Math.min(100, progress));
    this.currentStage = stage;
    this.updatedAt = Instant.now();
    addEvent(status.name(), stage, detail, actor, EventStatus.ACTIVE);
  }

  public synchronized void setGeometry(GeometryAnalysis geometry) {
    this.geometry = geometry;
    this.updatedAt = Instant.now();
  }

  public synchronized void setPlan(
      StockSelection stock,
      List<ToolDefinition> tools,
      List<ManufacturingOperation> operations) {
    this.stock = stock;
    this.tools = List.copyOf(tools);
    this.operations = List.copyOf(operations);
    this.updatedAt = Instant.now();
  }

  public synchronized void setGcode(String gcode) {
    this.gcode = gcode;
    this.updatedAt = Instant.now();
  }

  public synchronized void setProgram(String gcode, ProgramManifest programManifest) {
    this.gcode = gcode;
    this.programManifest = programManifest;
    this.updatedAt = Instant.now();
  }

  public synchronized void setMachine(MachineState machine) {
    this.machine = machine;
    if (machine.totalLines() > 0
        && machine.estimatedTotalSeconds() > 0
        && status == JobStatus.SIMULATING) {
      this.progress =
          Math.max(
              this.progress,
              Math.min(
                  99,
                  70
                      + (int)
                          Math.round(
                              29.0 * machine.elapsedSeconds() / machine.estimatedTotalSeconds())));
    }
    this.updatedAt = Instant.now();
  }

  public synchronized void setSimulationSpeed(double simulationSpeed) {
    this.simulationSpeed = clampSimulationSpeed(simulationSpeed);
    this.machine =
        new MachineState(
            machine.machine(),
            machine.executionStatus(),
            machine.currentLine(),
            machine.totalLines(),
            machine.currentBlock(),
            machine.axes(),
            machine.spindleRpm(),
            machine.feedMmPerMinute(),
            machine.activeTool(),
            machine.materialRemovedPercent(),
            machine.elapsedSeconds(),
            machine.estimatedTotalSeconds(),
            machine.estimatedRemainingSeconds(),
            machine.currentBlockSeconds(),
            machine.currentBlockRemainingSeconds(),
            this.simulationSpeed,
            machine.timingBasis(),
            machine.alarms());
    this.updatedAt = Instant.now();
  }

  public synchronized void complete() {
    this.status = JobStatus.COMPLETED;
    this.progress = 100;
    this.currentStage = "Ready for human review";
    this.updatedAt = Instant.now();
    addEvent(
        "COMPLETED",
        "Simulation complete",
        "Remove the simulated part, clean and inspect it, then package and send it.",
        Actor.HUMAN,
        EventStatus.COMPLETED);
  }

  public synchronized void fail(String message) {
    this.status = JobStatus.FAILED;
    this.failure = message;
    this.currentStage = "Job failed";
    this.updatedAt = Instant.now();
    addEvent("FAILED", "Processing stopped", message, Actor.SYSTEM, EventStatus.FAILED);
  }

  private void addEvent(
      String stage, String title, String detail, Actor actor, EventStatus eventStatus) {
    timeline.add(new JobEvent(Instant.now(), stage, title, detail, actor, eventStatus));
  }

  public UUID getId() {
    return id;
  }

  public String getFileName() {
    return fileName;
  }

  public String getRequestedMaterial() {
    return requestedMaterial;
  }

  public double getSimulationSpeed() {
    return simulationSpeed;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public synchronized JobStatus getStatus() {
    return status;
  }

  public synchronized int getProgress() {
    return progress;
  }

  public synchronized String getCurrentStage() {
    return currentStage;
  }

  public synchronized Instant getUpdatedAt() {
    return updatedAt;
  }

  public synchronized GeometryAnalysis getGeometry() {
    return geometry;
  }

  public synchronized StockSelection getStock() {
    return stock;
  }

  public synchronized List<ToolDefinition> getTools() {
    return tools;
  }

  public synchronized List<ManufacturingOperation> getOperations() {
    return operations;
  }

  public synchronized String getGcode() {
    return gcode;
  }

  public synchronized ProgramManifest getProgramManifest() {
    return programManifest;
  }

  public synchronized MachineState getMachine() {
    return machine;
  }

  public synchronized List<JobEvent> getTimeline() {
    return List.copyOf(timeline);
  }

  public synchronized String getFailure() {
    return failure;
  }

  public String getSafetyNotice() {
    return SAFETY_NOTICE;
  }

  public enum JobStatus {
    QUEUED,
    ANALYZING_GEOMETRY,
    SELECTING_STOCK,
    PLANNING_TOOLING,
    GENERATING_GCODE,
    SIMULATING,
    WAITING_FOR_OPERATOR,
    COMPLETED,
    FAILED
  }

  public enum Actor {
    SYSTEM,
    HUMAN,
    MACHINE
  }

  public enum EventStatus {
    ACTIVE,
    COMPLETED,
    FAILED
  }

  public record Dimensions(double xMm, double yMm, double zMm) {}

  public record GeometryAnalysis(
      String format,
      Dimensions dimensions,
      int pointsAnalyzed,
      int solidCount,
      double volumeMm3,
      List<FaceAnalysis> faces,
      List<String> warnings) {

    public GeometryAnalysis(
        String format,
        Dimensions dimensions,
        int pointsAnalyzed,
        List<String> warnings) {
      this(format, dimensions, pointsAnalyzed, 0, 0, List.of(), warnings);
    }
  }

  public record FaceAnalysis(
      String id,
      String surfaceType,
      double areaMm2,
      Vector3 centerMm,
      Vector3 normal,
      int edgeCount,
      IndexedOrientation candidateOrientation,
      CylindricalSurface cylinder) {}

  public record Vector3(double x, double y, double z) {}

  public record IndexedOrientation(double bDegrees, double cDegrees) {}

  public record CylindricalSurface(
      double radiusMm, Vector3 axisDirection, Vector3 axisPointMm, boolean internal) {}

  public record StockSelection(
      String requestedCategory,
      String selectedGrade,
      String form,
      Dimensions dimensions,
      String humanInstruction) {}

  public record ToolDefinition(
      int pocket,
      String toolCode,
      String name,
      double diameterMm,
      double stickoutMm,
      int cuttingEdges,
      String holder,
      String purpose) {}

  public record ManufacturingOperation(
      int sequence,
      String actor,
      String setup,
      String title,
      String description,
      int estimatedSeconds) {}

  public record ProgramManifest(
      String programNumber,
      int physicalSetups,
      int indexedOrientations,
      int operatorStops,
      String strategy,
      List<ProgramSection> sections) {}

  public record ProgramSection(
      int sequence,
      String name,
      String machineOrientation,
      String workOffset,
      String operatorAction) {}

  public record AxisPosition(double x, double y, double z, double b, double c) {}

  public record MachineState(
      String machine,
      String executionStatus,
      int currentLine,
      int totalLines,
      String currentBlock,
      AxisPosition axes,
      int spindleRpm,
      double feedMmPerMinute,
      String activeTool,
      double materialRemovedPercent,
      double elapsedSeconds,
      double estimatedTotalSeconds,
      double estimatedRemainingSeconds,
      double currentBlockSeconds,
      double currentBlockRemainingSeconds,
      double playbackRate,
      String timingBasis,
      List<String> alarms) {

    public static MachineState idle() {
      return new MachineState(
          "2016 Haas UMC-750",
          "IDLE",
          0,
          0,
          "",
          new AxisPosition(0, 0, 0, 0, 0),
          0,
          0,
          "None",
          0,
          0,
          0,
          0,
          0,
          0,
          5,
          "Official Haas maximum rates plus explicitly identified calibration defaults.",
          List.of());
    }
  }

  public record JobEvent(
      Instant timestamp,
      String stage,
      String title,
      String detail,
      Actor actor,
      EventStatus status) {}

  private static double clampSimulationSpeed(double speed) {
    if (!Double.isFinite(speed)) {
      return 5;
    }
    return Math.max(1, Math.min(100, speed));
  }

  public void awaitOperator(String instruction) {
    long observedSequence;
    synchronized (this) {
      observedSequence = operatorResumeSequence;
      status = JobStatus.WAITING_FOR_OPERATOR;
      currentStage = "Operator action required";
      updatedAt = Instant.now();
      addEvent(
          "OPERATOR_STOP",
          "Program paused at M00",
          instruction,
          Actor.HUMAN,
          EventStatus.ACTIVE);
      while (operatorResumeSequence == observedSequence) {
        try {
          wait();
        } catch (InterruptedException interrupted) {
          Thread.currentThread().interrupt();
          throw new IllegalStateException("Operator wait was interrupted.", interrupted);
        }
      }
      status = JobStatus.SIMULATING;
      currentStage = "Simulating the UMC-750";
      updatedAt = Instant.now();
      addEvent(
          "OPERATOR_RESUME",
          "Operator resumed program",
          "The required inspection was acknowledged and automatic execution resumed.",
          Actor.HUMAN,
          EventStatus.COMPLETED);
    }
  }

  public synchronized void resumeFromOperatorStop() {
    if (status != JobStatus.WAITING_FOR_OPERATOR) {
      throw new IllegalArgumentException("The job is not waiting for an operator.");
    }
    operatorResumeSequence++;
    notifyAll();
  }
}
