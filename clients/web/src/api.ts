import type { ApiResponse, ManufacturingJob } from "./types";

export async function submitJob(
  file: File,
  material: string,
  simulationSpeed: number,
): Promise<ManufacturingJob> {
  const form = new FormData();
  form.append("file", file);
  form.append("material", material);
  form.append("simulationSpeed", String(simulationSpeed));
  const response = await fetch("/api/v1/manufacturing-jobs", {
    method: "POST",
    body: form,
  });
  return readResponse(response);
}

export async function updateSimulationSpeed(
  id: string,
  speed: number,
): Promise<ManufacturingJob> {
  const response = await fetch(
    `/api/v1/manufacturing-jobs/${id}/simulation-speed`,
    {
      method: "PATCH",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ speed }),
    },
  );
  return readResponse(response);
}

export async function resumeJob(id: string): Promise<ManufacturingJob> {
  const response = await fetch(`/api/v1/manufacturing-jobs/${id}/resume`, {
    method: "POST",
  });
  return readResponse(response);
}

export async function getJob(id: string): Promise<ManufacturingJob> {
  const response = await fetch(`/api/v1/manufacturing-jobs/${id}`);
  return readResponse(response);
}

export async function loadSample(): Promise<File> {
  const response = await fetch("/samples/T8_housing_bracket.step");
  if (!response.ok) {
    throw new Error("The included sample model could not be loaded.");
  }
  const blob = await response.blob();
  return new File([blob], "T8_housing_bracket.step", {
    type: "application/step",
  });
}

async function readResponse(response: Response): Promise<ManufacturingJob> {
  const payload = (await response.json()) as
    | ApiResponse<ManufacturingJob>
    | { error: { message: string } };
  if (!response.ok || !("data" in payload)) {
    throw new Error(
      "error" in payload ? payload.error.message : "The request failed.",
    );
  }
  return payload.data;
}
