# CNC Manufacturing Automation Prototype

## Problem

Customers need a visible, understandable path from a CAD model to a proposed
subtractive manufacturing process. The first prototype must demonstrate that
path locally without implying that automatically generated toolpaths are safe
to run on a physical machine.

## User Story

As a customer, I want to upload a part model and select a material so that I can
watch the system analyze the part, propose stock and tooling, plan human and
machine operations, generate representative G-code, and simulate the work on a
2016 Haas UMC-750.

## Functional Requirements

1. The portal has no authentication and accepts one part file plus a material.
2. The prototype accepts exported STEP (`.step`, `.stp`) and STL (`.stl`) files.
   Native SolidWorks and Fusion 360 archives are retained for a later phase
   because they require vendor-specific translators; the portal tells users to
   export STEP or STL.
3. Submission immediately creates a background job and returns a tracking ID.
4. The system estimates the model bounding box, chooses a material grade, and
   proposes rectangular billet dimensions with machining allowance.
5. The system creates a tool-magazine load list appropriate for the proposed
   operations.
6. The process plan includes explicit human work before, between, and after
   machine operations.
7. The system produces representative Haas-style G-code for multiple 3+2
   indexed setups, including B- and C-axis positioning.
8. A machine simulator parses the generated program and publishes current
   axes, spindle speed, feed, active tool, program block, elapsed time, and
   cutting-path progress. True volumetric stock removal requires a later
   voxel/SDF or CAD-kernel simulation.
9. The portal continuously shows job events, process-plan status, tooling,
   stock, G-code, and machine telemetry.
10. The final operation directs a human to remove, clean, inspect, package, and
    ship the part.
11. The repository includes a licensed sample mechanical part that can be run
    directly from the portal.
12. Cycle time is derived from parsed G-code rather than a fixed delay:
    programmed feed distance, rapid travel, rotary indexing, tool changes,
    spindle acceleration/deceleration, coolant latency, dwell, and drilling
    cycles contribute to the estimate.
13. The customer can select playback from 1x through 100x. Playback changes
    wall-clock visualization speed only; it does not change estimated machine
    time.
14. The portal displays estimated total, simulated elapsed, estimated
    remaining, and current-block duration.
15. The system distinguishes a physical workholding setup from automatic B/C
    indexed orientations. It must not imply that an indexed move is an
    operator re-clamp.
16. Generated programs include verbose section comments and a machine-readable
    manifest describing physical setups, orientations, work offsets, and
    operator actions.
17. M00 pauses simulation until the user explicitly acknowledges the operator
    task. Human wait time is not added to commanded machine cycle time.

## Machine Profile

The simulated machine profile is a 2016-era Haas UMC-750 configuration:

- X travel: 762 mm
- Y travel: 508 mm
- Z travel: 508 mm
- Period-machine B travel: -35 to +110 degrees
- Period-machine C travel: +/-13,320 degrees before unwind
- Standard spindle: 8,100 RPM
- Tool magazine: 30+1 positions
- Table diameter: 500 mm
- Maximum table load: 300 kg

Exact options vary by machine serial number. The profile must therefore remain
configurable before any future connection to physical equipment.

Published machine-profile inputs used by the timing prototype:

- Linear rapid: 22,860 mm/min (900 in/min)
- Maximum programmed G94 feed: 16,510 mm/min (650 in/min)
- B/C rapid: 50 degrees/second
- Standard spindle: 8,100 RPM
- Average tool-to-tool change: 2.8 seconds
- Average chip-to-chip benchmark: 3.6 seconds

Haas does not publish serial-specific acceleration, spindle ramp, rotary settle,
or coolant latency. Those values are explicit calibration defaults and must be
measured on the intended machine before relying on cycle-time estimates.

## Safety Constraints

- The prototype is a planning and visualization demonstration, not a CAM
  system, postprocessor, collision verifier, or machine-control interface.
- Generated G-code must never be presented as safe to execute.
- A qualified machinist must validate geometry, fixtures, offsets, tools,
  feeds, speeds, clearances, collisions, controller options, and the final
  program using approved CAM and verification software.
- The prototype must not send commands to a physical machine.
- Haas MTConnect support, where available, is read-only monitoring and is not a
  machine-control API.
- Cycle time is commanded-time estimation, not a promise of production time.
  It excludes operator delays, probing variability, control look-ahead,
  feed/rapid overrides, tool wear, chip evacuation interruptions, and
  unmodeled controller behavior.
- A program that leaves the part in one fixture may contain several indexed
  orientations. A part flip, re-clamp, new fixture, or new work offset is a new
  physical setup and normally requires a separate qualified NC program.

## Non-Functional Requirements

- Everything runs locally with Docker Compose.
- Backend implementation uses Java 21 and Spring Boot.
- Customer UX uses React and TypeScript.
- Uploaded files are limited to 10 MB and are not executed.
- Jobs are isolated by opaque identifiers.
- Prototype state may be in-memory; production persistence is out of scope.

## Acceptance Criteria

1. Running `docker compose up --build` starts the portal and API locally.
2. A user can run the included sample or upload a supported model and material.
3. The API returns before background planning and simulation finish.
4. The portal visibly advances from intake through completed simulation.
5. Stock, tooling, operations, G-code, and changing UMC-750 telemetry appear.
6. Unsupported formats and invalid files receive explicit validation errors.
7. Backend and frontend automated tests pass.

## Out of Scope

- Production CAM, exact feature recognition, cutting-force simulation, cutter
  compensation validation, fixture design, collision detection, metrology,
  controller communications, procurement, authentication, payment, packaging,
  and shipping integrations.

## References

- Haas UMC specifications and service documentation:
  <https://www.haascnc.com/service/online-manuals/umc-series/umc---specifications.html>
- Haas machine data collection:
  <https://www.haascnc.com/service/troubleshooting-and-how-to/how-to/machine-data-collection---ngc.html>
- MTConnect standard: <https://www.mtconnect.org/>
- CAMotics open-source 3-axis simulator:
  <https://github.com/CauldronDevelopmentLLC/CAMotics>
- FreeCAD parts library: <https://github.com/FreeCAD/FreeCAD-library>
