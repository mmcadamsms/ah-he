const terminalStatuses = new Set(["COMPLETED", "FAILED"]);
let currentJobId;
let pollTimer;
let playbackUpdateTimer;
let currentGcode = "";

const byId = (id) => document.getElementById(id);
const escapeHtml = (value) =>
  String(value ?? "")
    .replaceAll("&", "&amp;")
    .replaceAll("<", "&lt;")
    .replaceAll(">", "&gt;")
    .replaceAll('"', "&quot;")
    .replaceAll("'", "&#039;");

byId("part-file").addEventListener("change", (event) => {
  byId("selected-file").textContent = event.target.files[0]
    ? `Selected: ${event.target.files[0].name}`
    : "";
});

byId("initial-playback-rate").addEventListener("input", (event) => {
  byId("initial-playback-output").textContent = `${event.target.value}×`;
});

byId("playback-rate").addEventListener("input", (event) => {
  const speed = Number(event.target.value);
  byId("playback-output").textContent = `${speed}×`;
  window.clearTimeout(playbackUpdateTimer);
  playbackUpdateTimer = window.setTimeout(() => updatePlaybackRate(speed), 150);
});

byId("verbose-gcode").addEventListener("change", renderGcode);

byId("resume-button").addEventListener("click", async () => {
  if (!currentJobId) {
    return;
  }
  byId("resume-button").disabled = true;
  try {
    const response = await fetch(
      `/api/v1/manufacturing-jobs/${currentJobId}/resume`,
      { method: "POST" },
    );
    render(await readResponse(response));
  } catch (error) {
    byId("job-error").hidden = false;
    byId("job-error").textContent = error.message;
  } finally {
    byId("resume-button").disabled = false;
  }
});

byId("intake-form").addEventListener("submit", async (event) => {
  event.preventDefault();
  const file = byId("part-file").files[0];
  if (!file) {
    showFormError("Choose an exported STEP or STL model.");
    return;
  }
  await submit(file);
});

byId("sample-button").addEventListener("click", async () => {
  setBusy(true);
  try {
    const response = await fetch("/samples/T8_housing_bracket.step");
    if (!response.ok) {
      throw new Error("The included sample model could not be loaded.");
    }
    const file = new File(
      [await response.blob()],
      "T8_housing_bracket.step",
      { type: "application/step" },
    );
    byId("selected-file").textContent = `Selected: ${file.name}`;
    await submit(file);
  } catch (error) {
    showFormError(error.message);
  } finally {
    setBusy(false);
  }
});

byId("reset-button").addEventListener("click", () => {
  window.clearInterval(pollTimer);
  currentJobId = undefined;
  byId("dashboard").hidden = true;
  byId("landing").hidden = false;
});

async function submit(file) {
  setBusy(true);
  showFormError("");
  const form = new FormData();
  form.append("file", file);
  form.append("material", byId("material").value);
  form.append("simulationSpeed", byId("initial-playback-rate").value);
  try {
    const response = await fetch("/api/v1/manufacturing-jobs", {
      method: "POST",
      body: form,
    });
    const job = await readResponse(response);
    currentJobId = job.id;
    byId("landing").hidden = true;
    byId("dashboard").hidden = false;
    render(job);
    startPolling();
  } catch (error) {
    showFormError(error.message);
  } finally {
    setBusy(false);
  }
}

function startPolling() {
  window.clearInterval(pollTimer);
  pollTimer = window.setInterval(async () => {
    if (!currentJobId) {
      return;
    }
    try {
      const response = await fetch(
        `/api/v1/manufacturing-jobs/${currentJobId}`,
      );
      const job = await readResponse(response);
      render(job);
      if (terminalStatuses.has(job.status)) {
        window.clearInterval(pollTimer);
      }
    } catch (error) {
      byId("job-error").hidden = false;
      byId("job-error").textContent = error.message;
    }
  }, 500);
}

async function readResponse(response) {
  const payload = await response.json();
  if (!response.ok || !payload.data) {
    throw new Error(payload.error?.message ?? "The request failed.");
  }
  return payload.data;
}

function render(job) {
  byId("job-id").textContent = `BUILD ${job.id.slice(0, 8).toUpperCase()}`;
  byId("job-stage").textContent = job.currentStage;
  byId("job-summary").textContent =
    `${job.fileName} in ${job.requestedMaterial}`;
  byId("job-status").textContent = job.status.replaceAll("_", " ");
  byId("job-progress").textContent = `${job.progress}%`;
  byId("progress-fill").style.width = `${job.progress}%`;
  byId("safety-notice").textContent = job.safetyNotice;
  byId("job-error").hidden = !job.failure;
  byId("job-error").textContent = job.failure ?? "";
  byId("resume-button").hidden = job.status !== "WAITING_FOR_OPERATOR";

  const machine = job.machine;
  byId("machine-name").textContent = machine.machine;
  byId("machine-status").textContent = machine.executionStatus;
  byId("block-number").textContent =
    `BLOCK ${machine.currentLine}/${machine.totalLines}`;
  byId("current-block").textContent =
    machine.currentBlock || "Waiting for program...";
  byId("removal-percent").textContent =
    `Cutting-path progress: ${machine.materialRemovedPercent.toFixed(1)}%`;
  byId("playback-rate").value = String(Math.round(machine.playbackRate));
  byId("playback-output").textContent = `${machine.playbackRate.toFixed(0)}×`;
  byId("timing-basis").textContent = machine.timingBasis;
  byId("time-telemetry").innerHTML = [
    ["ESTIMATED CYCLE", formatDuration(machine.estimatedTotalSeconds)],
    ["SIMULATED ELAPSED", formatDuration(machine.elapsedSeconds)],
    ["ESTIMATED REMAINING", formatDuration(machine.estimatedRemainingSeconds)],
    ["CURRENT BLOCK", `${machine.currentBlockSeconds.toFixed(2)} s`],
  ]
    .map(
      ([label, value]) =>
        `<div><span>${label}</span><strong>${escapeHtml(value)}</strong></div>`,
    )
    .join("");

  const axes = machine.axes;
  const telemetry = [
    ["X", `${axes.x.toFixed(3)} mm`],
    ["Y", `${axes.y.toFixed(3)} mm`],
    ["Z", `${axes.z.toFixed(3)} mm`],
    ["B", `${axes.b.toFixed(3)}°`],
    ["C", `${axes.c.toFixed(3)}°`],
    ["SPINDLE", `${machine.spindleRpm.toLocaleString()} rpm`],
    ["FEED", `${machine.feedMmPerMinute.toFixed(0)} mm/min`],
    ["TOOL", machine.activeTool],
  ];
  byId("telemetry").innerHTML = telemetry
    .map(
      ([label, value]) =>
        `<div><span>${label}</span><strong>${escapeHtml(value)}</strong></div>`,
    )
    .join("");

  byId("timeline").innerHTML = [...job.timeline]
    .reverse()
    .map(
      (event) => `
        <div class="timeline-event">
          <div class="timeline-dot ${event.actor.toLowerCase()}"></div>
          <div>
            <div class="event-heading">
              <strong>${escapeHtml(event.title)}</strong>
              <span>${escapeHtml(event.actor)}</span>
            </div>
            <p>${escapeHtml(event.detail)}</p>
          </div>
        </div>`,
    )
    .join("");

  if (job.stock) {
    byId("stock-grade").textContent = job.stock.selectedGrade;
    const dimensions = job.stock.dimensions;
    byId("stock-dimensions").innerHTML =
      `<strong>${dimensions.xMm} mm</strong><span>×</span>` +
      `<strong>${dimensions.yMm} mm</strong><span>×</span>` +
      `<strong>${dimensions.zMm} mm</strong>`;
    byId("stock-instruction").textContent = job.stock.humanInstruction;
  }

  byId("geometry-warnings").innerHTML = (job.geometry?.warnings ?? [])
    .map((warning) => `<div class="warning-line">${escapeHtml(warning)}</div>`)
    .join("");

  byId("tool-count").textContent = `${job.tools.length || "—"} tools planned`;
  byId("tool-list").innerHTML = job.tools
    .map(
      (tool) => `
        <div class="tool-row">
          <span class="tool-pocket">${escapeHtml(tool.toolCode)}</span>
          <div>
            <strong>${escapeHtml(tool.name)}</strong>
            <p>${escapeHtml(tool.purpose)}</p>
          </div>
        </div>`,
    )
    .join("");

  byId("operation-list").innerHTML = job.operations
    .map(
      (operation) => `
        <div class="operation">
          <span class="actor-badge ${operation.actor.toLowerCase()}">
            ${escapeHtml(operation.actor)}
          </span>
          <div>
            <small>${operation.sequence} · ${escapeHtml(operation.setup)}</small>
            <strong>${escapeHtml(operation.title)}</strong>
            <p>${escapeHtml(operation.description)}</p>
          </div>
          <span>${Math.ceil(operation.estimatedSeconds / 60)} min</span>
        </div>`,
    )
    .join("");

  currentGcode = job.gcode ?? "";
  renderProgramManifest(job.programManifest);
  renderGcode();
  drawMachine(job);
}

function drawMachine(job) {
  const canvas = byId("machine-canvas");
  const context = canvas.getContext("2d");
  const width = canvas.width;
  const height = canvas.height;
  context.clearRect(0, 0, width, height);
  context.fillStyle = "#071018";
  context.fillRect(0, 0, width, height);

  context.strokeStyle = "#17303d";
  context.lineWidth = 1;
  for (let x = 0; x < width; x += 30) {
    context.beginPath();
    context.moveTo(x, 0);
    context.lineTo(x, height);
    context.stroke();
  }
  for (let y = 0; y < height; y += 30) {
    context.beginPath();
    context.moveTo(0, y);
    context.lineTo(width, y);
    context.stroke();
  }

  const stock = job.stock?.dimensions ?? { xMm: 100, yMm: 70, zMm: 35 };
  const part = job.geometry?.dimensions ?? {
    xMm: stock.xMm - 10,
    yMm: stock.yMm - 10,
    zMm: stock.zMm - 7,
  };
  const scale = Math.min(4, 440 / Math.max(stock.xMm, stock.yMm));
  const stockWidth = stock.xMm * scale;
  const stockHeight = stock.yMm * scale;
  const left = width / 2 - stockWidth / 2;
  const top = height / 2 - stockHeight / 2 + 20;

  context.fillStyle = "rgba(111, 135, 146, 0.48)";
  context.fillRect(left, top, stockWidth, stockHeight);
  context.strokeStyle = "#9dc0ce";
  context.lineWidth = 2;
  context.strokeRect(left, top, stockWidth, stockHeight);

  const removed = job.machine.materialRemovedPercent / 100;
  const partWidth = part.xMm * scale;
  const partHeight = part.yMm * scale;
  context.fillStyle = `rgba(54, 211, 153, ${Math.max(0.08, removed)})`;
  context.fillRect(
    width / 2 - partWidth / 2,
    height / 2 - partHeight / 2 + 20,
    partWidth,
    partHeight,
  );

  const toolX = width / 2 + job.machine.axes.x * scale;
  const toolY = height / 2 + job.machine.axes.y * scale + 20;
  context.save();
  context.translate(toolX, toolY);
  context.rotate((job.machine.axes.c * Math.PI) / 180);
  context.fillStyle = "#ffc857";
  context.fillRect(-4, -32, 8, 64);
  context.fillStyle = "#17242b";
  context.fillRect(-12, -43, 24, 18);
  context.restore();

  context.fillStyle = "#8ca1ad";
  context.font = "13px Consolas";
  context.fillText(
    `B ${job.machine.axes.b.toFixed(1)}°  C ${job.machine.axes.c.toFixed(1)}°`,
    18,
    24,
  );
}

function setBusy(busy) {
  byId("submit-button").disabled = busy;
  byId("sample-button").disabled = busy;
  byId("submit-button").textContent = busy
    ? "Starting..."
    : "Analyze and simulate";
}

function showFormError(message) {
  byId("form-error").hidden = !message;
  byId("form-error").textContent = message;
}

async function updatePlaybackRate(speed) {
  if (!currentJobId) {
    return;
  }
  try {
    const response = await fetch(
      `/api/v1/manufacturing-jobs/${currentJobId}/simulation-speed`,
      {
        method: "PATCH",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ speed }),
      },
    );
    const job = await readResponse(response);
    render(job);
  } catch (error) {
    byId("job-error").hidden = false;
    byId("job-error").textContent = error.message;
  }
}

function formatDuration(totalSeconds) {
  const seconds = Math.max(0, Math.round(totalSeconds ?? 0));
  const hours = Math.floor(seconds / 3600);
  const minutes = Math.floor((seconds % 3600) / 60);
  const remainder = seconds % 60;
  if (hours > 0) {
    return `${hours}h ${minutes}m ${remainder}s`;
  }

  function renderProgramManifest(manifest) {
    if (!manifest) {
      byId("program-manifest").innerHTML = "";
      return;
    }
    byId("program-manifest").innerHTML = `
      <div class="manifest-summary">
        <strong>${escapeHtml(manifest.programNumber)}</strong>
        <span>${manifest.physicalSetups} physical setup</span>
        <span>${manifest.indexedOrientations} indexed orientations</span>
        <span>${manifest.operatorStops} operator stop</span>
      </div>
      <p>${escapeHtml(manifest.strategy)}</p>
      <div class="manifest-sections">
        ${manifest.sections
          .map(
            (section) => `
              <div>
                <strong>${section.sequence}. ${escapeHtml(section.name)}</strong>
                <span>${escapeHtml(section.machineOrientation)} · ${escapeHtml(section.workOffset)}</span>
                <p>${escapeHtml(section.operatorAction)}</p>
              </div>`,
          )
          .join("")}
      </div>`;
  }

  function renderGcode() {
    if (!currentGcode) {
      byId("gcode").textContent = "Program generation pending...";
      return;
    }
    if (byId("verbose-gcode").checked) {
      byId("gcode").textContent = currentGcode;
      return;
    }
    byId("gcode").textContent = currentGcode
      .split("\n")
      .map((line) => line.replace(/\([^)]*\)/g, "").trim())
      .filter(Boolean)
      .join("\n");
  }
  return `${minutes}m ${remainder}s`;
}
