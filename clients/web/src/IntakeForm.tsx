import { useState, type FormEvent } from "react";
import { loadSample, submitJob } from "./api";
import type { ManufacturingJob } from "./types";

type Props = {
  onSubmitted: (job: ManufacturingJob) => void;
};

const materials = [
  "6061 aluminum",
  "1018 steel",
  "409 stainless steel",
  "Acetal plastic",
];

export function IntakeForm({ onSubmitted }: Props) {
  const [file, setFile] = useState<File>();
  const [material, setMaterial] = useState(materials[0]);
  const [simulationSpeed, setSimulationSpeed] = useState(5);
  const [error, setError] = useState("");
  const [submitting, setSubmitting] = useState(false);

  const submit = async (event: FormEvent) => {
    event.preventDefault();
    if (!file) {
      setError("Choose an exported STEP or STL model.");
      return;
    }
    await send(file);
  };

  const send = async (model: File) => {
    setSubmitting(true);
    setError("");
    try {
      onSubmitted(await submitJob(model, material, simulationSpeed));
    } catch (submissionError) {
      setError(
        submissionError instanceof Error
          ? submissionError.message
          : "Submission failed.",
      );
    } finally {
      setSubmitting(false);
    }
  };

  const runSample = async () => {
    setSubmitting(true);
    setError("");
    try {
      const sample = await loadSample();
      setFile(sample);
      onSubmitted(await submitJob(sample, material, simulationSpeed));
    } catch (sampleError) {
      setError(
        sampleError instanceof Error ? sampleError.message : "Sample failed.",
      );
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <form className="intake-card" onSubmit={submit}>
      <div className="eyebrow">START A BUILD</div>
      <h2>Give us the part model.</h2>
      <p>
        Export from SolidWorks or Fusion 360 as STEP or STL. The prototype
        estimates geometry; it does not read drawing tolerances or native CAD
        features.
      </p>
      <label>
        Part model
        <input
          type="file"
          accept=".step,.stp,.stl,.sldprt,.f3d"
          onChange={(event) => setFile(event.target.files?.[0])}
        />
      </label>
      <label>
        Simulation playback
        <div className="range-control">
          <input
            type="range"
            min="1"
            max="100"
            value={simulationSpeed}
            onChange={(event) => setSimulationSpeed(Number(event.target.value))}
          />
          <output>{simulationSpeed}×</output>
        </div>
        <small>
          1× follows estimated machine time. Higher values accelerate only the
          visualization.
        </small>
      </label>
      <label>
        Material
        <select
          value={material}
          onChange={(event) => setMaterial(event.target.value)}
        >
          {materials.map((option) => (
            <option key={option}>{option}</option>
          ))}
        </select>
      </label>
      {file && <div className="selected-file">Selected: {file.name}</div>}
      {error && <div className="error-banner">{error}</div>}
      <div className="button-row">
        <button className="primary-button" disabled={submitting} type="submit">
          {submitting ? "Starting..." : "Analyze and simulate"}
        </button>
        <button
          className="secondary-button"
          disabled={submitting}
          type="button"
          onClick={runSample}
        >
          Run included bracket
        </button>
      </div>
    </form>
  );
}
