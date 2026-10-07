import { MachineScene } from "./MachineScene";
import type { ManufacturingJob } from "./types";
import { useState } from "react";

type Props = {
  job: ManufacturingJob;
  onReset: () => void;
  onPlaybackRateChange: (speed: number) => void;
  onResume: () => void;
};

export function JobDashboard({
  job,
  onReset,
  onPlaybackRateChange,
  onResume,
}: Props) {
  const axes = job.machine.axes;
  const [verboseGcode, setVerboseGcode] = useState(true);
  return (
    <main className="dashboard">
      <section className="job-heading">
        <div>
          <div className="eyebrow">BUILD {job.id.slice(0, 8).toUpperCase()}</div>
          <h1>{job.currentStage}</h1>
          <p>
            {job.fileName} in {job.requestedMaterial}
          </p>
        </div>
        <button className="secondary-button" onClick={onReset}>
          Start another
        </button>
      </section>

      <div className="progress-track">
        <div className="progress-fill" style={{ width: `${job.progress}%` }} />
      </div>
      <div className="progress-labels">
        <span>{job.status.replaceAll("_", " ")}</span>
        <strong>{job.progress}%</strong>
      </div>

      {job.failure && <div className="error-banner">{job.failure}</div>}
      <div className="safety-banner">{job.safetyNotice}</div>

      <section className="panel cycle-control-panel">
        <div>
          <div className="eyebrow">CYCLE-TIME PLAYBACK</div>
          <h2>Commanded machine time</h2>
          <p>{job.machine.timingBasis}</p>
        </div>
        <label>
          Playback rate
          <div className="range-control">
            <input
              type="range"
              min="1"
              max="100"
              value={job.machine.playbackRate}
              onChange={(event) =>
                onPlaybackRateChange(Number(event.target.value))
              }
            />
            <output>{job.machine.playbackRate.toFixed(0)}×</output>
          </div>
          {job.status === "WAITING_FOR_OPERATOR" && (
            <button className="primary-button operator-resume" onClick={onResume}>
              Inspection complete — resume program
            </button>
          )}
        </label>
        <div className="time-telemetry">
          <TimeMetric
            label="ESTIMATED CYCLE"
            value={formatDuration(job.machine.estimatedTotalSeconds)}
          />
          <TimeMetric
            label="SIMULATED ELAPSED"
            value={formatDuration(job.machine.elapsedSeconds)}
          />
          <TimeMetric
            label="ESTIMATED REMAINING"
            value={formatDuration(job.machine.estimatedRemainingSeconds)}
          />
          <TimeMetric
            label="CURRENT BLOCK"
            value={`${job.machine.currentBlockSeconds.toFixed(2)} s`}
          />
        </div>
      </section>

      <section className="dashboard-grid">
        <article className="panel machine-panel">
          <div className="panel-title">
            <div>
              <div className="eyebrow">DIGITAL MACHINE</div>
              <h2>{job.machine.machine}</h2>
            </div>
            <span className={`status-pill ${job.machine.executionStatus.toLowerCase()}`}>
              {job.machine.executionStatus}
            </span>
          </div>
          <MachineScene
            stock={job.stock?.dimensions}
            part={job.geometry?.dimensions}
            machine={job.machine}
          />
          <div className="telemetry-grid">
            {(["x", "y", "z", "b", "c"] as const).map((axis) => (
              <div key={axis}>
                <span>{axis.toUpperCase()}</span>
                <strong>
                  {axes[axis].toFixed(3)}
                  {axis === "b" || axis === "c" ? "°" : " mm"}
                </strong>
              </div>
            ))}
            <div>
              <span>SPINDLE</span>
              <strong>{job.machine.spindleRpm.toLocaleString()} rpm</strong>
            </div>
            <div>
              <span>FEED</span>
              <strong>{job.machine.feedMmPerMinute.toFixed(0)} mm/min</strong>
            </div>
            <div>
              <span>TOOL</span>
              <strong>{job.machine.activeTool}</strong>
            </div>
          </div>
          <div className="current-block">
            <span>
              BLOCK {job.machine.currentLine}/{job.machine.totalLines}
            </span>
            <code>{job.machine.currentBlock || "Waiting for program..."}</code>
          </div>
        </article>

        <article className="panel">
          <div className="eyebrow">CUSTOMER VISIBILITY</div>
          <h2>Live activity</h2>
          <div className="timeline">
            {[...job.timeline].reverse().map((event, index) => (
              <div className="timeline-event" key={`${event.timestamp}-${index}`}>
                <div className={`timeline-dot ${event.actor.toLowerCase()}`} />
                <div>
                  <div className="event-heading">
                    <strong>{event.title}</strong>
                    <span>{event.actor}</span>
                  </div>
                  <p>{event.detail}</p>
                </div>
              </div>
            ))}
          </div>
        </article>
      </section>

      <section className="summary-grid">
        <article className="panel">
          <div className="eyebrow">MATERIAL + STOCK</div>
          <h2>{job.stock?.selectedGrade ?? "Selection pending"}</h2>
          {job.stock && (
            <>
              <div className="dimension-row">
                <strong>{job.stock.dimensions.xMm} mm</strong>
                <span>×</span>
                <strong>{job.stock.dimensions.yMm} mm</strong>
                <span>×</span>
                <strong>{job.stock.dimensions.zMm} mm</strong>
              </div>
              <p>{job.stock.humanInstruction}</p>
            </>
          )}
          {job.geometry?.warnings.map((warning) => (
            <div className="warning-line" key={warning}>
              {warning}
            </div>
          ))}
        </article>

        <article className="panel">
          <div className="eyebrow">MAGAZINE LOAD</div>
          <h2>{job.tools.length || "—"} tools planned</h2>
          <div className="tool-list">
            {job.tools.map((tool) => (
              <div className="tool-row" key={tool.pocket}>
                <span className="tool-pocket">{tool.toolCode}</span>
                <div>
                  <strong>{tool.name}</strong>
                  <p>{tool.purpose}</p>
                </div>
              </div>
            ))}
          </div>
        </article>
      </section>

      <section className="panel operations-panel">
        <div className="eyebrow">PROCESS ROUTER</div>
        <h2>Human and machine operations</h2>
        <div className="operation-list">
          {job.operations.map((operation) => (
            <div className="operation" key={operation.sequence}>
              <span className={`actor-badge ${operation.actor.toLowerCase()}`}>
                {operation.actor}
              </span>
              <div>
                <small>
                  {operation.sequence} · {operation.setup}
                </small>
                <strong>{operation.title}</strong>
                <p>{operation.description}</p>
              </div>
              <span>{Math.ceil(operation.estimatedSeconds / 60)} min</span>
            </div>
          ))}
        </div>
      </section>

      <section className="panel gcode-panel">
        <div className="panel-title">
          <div>
            <div className="eyebrow">REPRESENTATIVE OUTPUT</div>
            <h2>Haas-style G-code</h2>
          </div>
          <div className="gcode-controls">
            <label className="checkbox-label">
              <input
                type="checkbox"
                checked={verboseGcode}
                onChange={(event) => setVerboseGcode(event.target.checked)}
              />
              Verbose comments
            </label>
            <span>
              Cutting-path progress: {job.machine.materialRemovedPercent.toFixed(1)}%
            </span>
          </div>
        </div>
        {job.programManifest && (
          <div className="program-manifest">
            <div className="manifest-summary">
              <strong>{job.programManifest.programNumber}</strong>
              <span>{job.programManifest.physicalSetups} physical setup</span>
              <span>
                {job.programManifest.indexedOrientations} indexed orientations
              </span>
              <span>{job.programManifest.operatorStops} operator stop</span>
            </div>
            <p>{job.programManifest.strategy}</p>
            <div className="manifest-sections">
              {job.programManifest.sections.map((section) => (
                <div key={section.sequence}>
                  <strong>
                    {section.sequence}. {section.name}
                  </strong>
                  <span>
                    {section.machineOrientation} · {section.workOffset}
                  </span>
                  <p>{section.operatorAction}</p>
                </div>
              ))}
            </div>
          </div>
        )}
        <pre>
          {job.gcode
            ? verboseGcode
              ? job.gcode
              : compactGcode(job.gcode)
            : "Program generation pending..."}
        </pre>
      </section>
    </main>
  );
}

function TimeMetric({ label, value }: { label: string; value: string }) {
  return (
    <div>
      <span>{label}</span>
      <strong>{value}</strong>
    </div>
  );
}

function formatDuration(totalSeconds: number) {
  const seconds = Math.max(0, Math.round(totalSeconds));
  const hours = Math.floor(seconds / 3600);
  const minutes = Math.floor((seconds % 3600) / 60);
  const remainder = seconds % 60;
  return hours > 0
    ? `${hours}h ${minutes}m ${remainder}s`
    : `${minutes}m ${remainder}s`;
}

function compactGcode(gcode: string) {
  return gcode
    .split("\n")
    .map((line) => line.replace(/\([^)]*\)/g, "").trim())
    .filter(Boolean)
    .join("\n");
}
