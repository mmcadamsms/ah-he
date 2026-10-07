import { useEffect, useState } from "react";
import { getJob, resumeJob, updateSimulationSpeed } from "./api";
import { IntakeForm } from "./IntakeForm";
import { JobDashboard } from "./JobDashboard";
import type { ManufacturingJob } from "./types";
import "./styles.css";

const terminalStatuses = new Set(["COMPLETED", "FAILED"]);

export default function App() {
  const [job, setJob] = useState<ManufacturingJob>();

  useEffect(() => {
    if (!job || terminalStatuses.has(job.status)) {
      return;
    }
    const timer = window.setInterval(async () => {
      try {
        setJob(await getJob(job.id));
      } catch {
        // Keep the last known state; the next interval retries.
      }
    }, 500);
    return () => window.clearInterval(timer);
  }, [job?.id, job?.status]);

  if (job) {
    return (
      <JobDashboard
        job={job}
        onReset={() => setJob(undefined)}
        onPlaybackRateChange={async (speed) =>
          setJob(await updateSimulationSpeed(job.id, speed))
        }
        onResume={async () => setJob(await resumeJob(job.id))}
      />
    );
  }

  return (
    <main className="landing">
      <nav>
        <div className="brand-mark">FP</div>
        <div className="brand-copy">
          <strong>ForgePath</strong>
          <span>manufacturing visibility prototype</span>
        </div>
        <div className="prototype-pill">LOCAL PROTOTYPE</div>
      </nav>
      <section className="hero">
        <div className="hero-copy">
          <div className="eyebrow">IDEA → MATERIAL → MOTION</div>
          <h1>See how your part could be made.</h1>
          <p>
            Submit an exported CAD model. Follow every proposed stock, tool,
            human handoff, program block, and machine movement—without hiding
            the work behind a status spinner.
          </p>
          <div className="machine-facts">
            <span>2016 HAAS UMC-750</span>
            <span>3+2 INDEXED</span>
            <span>30+1 TOOLS</span>
          </div>
        </div>
        <IntakeForm onSubmitted={setJob} />
      </section>
      <section className="promise-strip">
        <div>
          <strong>01</strong>
          <span>Geometry analyzed</span>
        </div>
        <div>
          <strong>02</strong>
          <span>Stock + tools planned</span>
        </div>
        <div>
          <strong>03</strong>
          <span>Operations generated</span>
        </div>
        <div>
          <strong>04</strong>
          <span>Machine simulated live</span>
        </div>
      </section>
    </main>
  );
}
