# Prompt: Add Physically Derived CNC Cycle Timing

## Requirement Reference

- **Functional Area:** manufacturing automation
- **Requirement Doc:** `requirements/functional-areas/manufacturing-automation/cnc-prototype.md`

## Objective

Replace fixed per-block simulation delay with a Haas UMC-750 cycle-time model
derived from modal G-code, machine rates, and material/tool cutting parameters.
Add customer-controlled playback acceleration without changing estimated
machine time.

## Acceptance Criteria

1. Linear feed time is calculated from path length and programmed feed.
2. Rapid and rotary timing uses the configured machine profile.
3. Tool change, spindle ramp, coolant, dwell, G81, and G83 add modeled time.
4. Generated RPM and feed are derived from cutting speed, chip load, flute
   count, tool diameter, and machine spindle limit.
5. The portal shows total, elapsed, remaining, and block timing.
6. A 1x-to-100x slider changes playback rate during an active simulation.
7. Tests validate exact feed, inverse-time, rotary, drilling, and tool-change
   timing behavior.

## Scope

### In Scope

- G20/G21, G90/G91, G93/G94, G00-G04, G80/G81/G83, G98/G99
- M03/M05/M06/M08/M09/M30
- Linear and indexed B/C movement
- Configurable machine and auxiliary timing

### Out of Scope

- Controller look-ahead emulation
- Servo following-error dynamics
- Cutting-force, chatter, thermal, or tool-wear simulation
- Exact simultaneous five-axis kinematics or collision verification

## Testing Requirements

- [x] Unit tests for modal interpretation and timing
- [x] G-code generator and simulator tests
- [x] API integration coverage
- [x] Docker-hosted browser smoke test

---
**Status:** in-progress
**Created:** 2026-09-30
**Author:** @mmcadamsms and Copilot
