import { render, screen } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import { vi } from "vitest";
import App from "./App";

vi.mock("./MachineScene", () => ({
  MachineScene: () => <div aria-label="Mock machine scene" />,
}));

vi.mock("./api", async () => {
  return {
    loadSample: vi.fn().mockResolvedValue(
      new File(["ISO-10303-21;"], "T8_housing_bracket.step", {
        type: "application/step",
      }),
    ),
    submitJob: vi.fn().mockResolvedValue({
      id: "12345678-0000-0000-0000-000000000000",
      fileName: "T8_housing_bracket.step",
      requestedMaterial: "6061 aluminum",
      simulationSpeed: 5,
      status: "QUEUED",
      progress: 1,
      currentStage: "Submission received",
      tools: [],
      operations: [],
      machine: {
        machine: "2016 Haas UMC-750",
        executionStatus: "IDLE",
        currentLine: 0,
        totalLines: 0,
        currentBlock: "",
        axes: { x: 0, y: 0, z: 0, b: 0, c: 0 },
        spindleRpm: 0,
        feedMmPerMinute: 0,
        activeTool: "None",
        materialRemovedPercent: 0,
        elapsedSeconds: 0,
        estimatedTotalSeconds: 0,
        estimatedRemainingSeconds: 0,
        currentBlockSeconds: 0,
        currentBlockRemainingSeconds: 0,
        playbackRate: 5,
        timingBasis: "Official rates and calibration defaults.",
        alarms: [],
      },
      timeline: [],
      safetyNotice: "Demonstration only.",
    }),
    getJob: vi.fn(),
    updateSimulationSpeed: vi.fn(),
    resumeJob: vi.fn(),
  };
});

test("starts the included sample workflow", async () => {
  const user = userEvent.setup();
  render(<App />);

  expect(
    screen.getByRole("heading", { name: "See how your part could be made." }),
  ).toBeInTheDocument();

  await user.click(
    screen.getByRole("button", { name: "Run included bracket" }),
  );

  expect(
    await screen.findByRole("heading", { name: "Submission received" }),
  ).toBeInTheDocument();
  expect(screen.getByText(/T8_housing_bracket.step/)).toBeInTheDocument();
});
