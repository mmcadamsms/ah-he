export type Dimensions = {
  xMm: number;
  yMm: number;
  zMm: number;
};

export type GeometryAnalysis = {
  format: string;
  dimensions: Dimensions;
  pointsAnalyzed: number;
  warnings: string[];
};

export type StockSelection = {
  requestedCategory: string;
  selectedGrade: string;
  form: string;
  dimensions: Dimensions;
  humanInstruction: string;
};

export type ToolDefinition = {
  pocket: number;
  toolCode: string;
  name: string;
  diameterMm: number;
  stickoutMm: number;
  purpose: string;
};

export type ManufacturingOperation = {
  sequence: number;
  actor: "HUMAN" | "MACHINE";
  setup: string;
  title: string;
  description: string;
  estimatedSeconds: number;
};

export type ProgramManifest = {
  programNumber: string;
  physicalSetups: number;
  indexedOrientations: number;
  operatorStops: number;
  strategy: string;
  sections: {
    sequence: number;
    name: string;
    machineOrientation: string;
    workOffset: string;
    operatorAction: string;
  }[];
};

export type MachineState = {
  machine: string;
  executionStatus: string;
  currentLine: number;
  totalLines: number;
  currentBlock: string;
  axes: {
    x: number;
    y: number;
    z: number;
    b: number;
    c: number;
  };
  spindleRpm: number;
  feedMmPerMinute: number;
  activeTool: string;
  materialRemovedPercent: number;
  elapsedSeconds: number;
  estimatedTotalSeconds: number;
  estimatedRemainingSeconds: number;
  currentBlockSeconds: number;
  currentBlockRemainingSeconds: number;
  playbackRate: number;
  timingBasis: string;
  alarms: string[];
};

export type JobEvent = {
  timestamp: string;
  stage: string;
  title: string;
  detail: string;
  actor: "SYSTEM" | "HUMAN" | "MACHINE";
  status: "ACTIVE" | "COMPLETED" | "FAILED";
};

export type ManufacturingJob = {
  id: string;
  fileName: string;
  requestedMaterial: string;
  simulationSpeed: number;
  status: string;
  progress: number;
  currentStage: string;
  geometry?: GeometryAnalysis;
  stock?: StockSelection;
  tools: ToolDefinition[];
  operations: ManufacturingOperation[];
  gcode?: string;
  programManifest?: ProgramManifest;
  machine: MachineState;
  timeline: JobEvent[];
  failure?: string;
  safetyNotice: string;
};

export type ApiResponse<T> = {
  data: T;
  meta: {
    timestamp: string;
    requestId: string;
  };
};
