# Prompt: Build Local CNC Automation Prototype

## Requirement Reference

- **Functional Area:** manufacturing automation
- **Requirement Doc:** `requirements/functional-areas/manufacturing-automation/cnc-prototype.md`

## Objective

Create a locally runnable prototype that turns an uploaded STEP/STL part and
material choice into a visible, asynchronous stock-selection, tooling,
operation-planning, representative G-code, and Haas UMC-750 simulation flow.

## Acceptance Criteria

1. A no-auth React portal submits a supported CAD export and material.
2. A Java API starts processing asynchronously and exposes live job state.
3. The result contains stock, tooling, human/machine operations, G-code, and
   simulated UMC-750 telemetry.
4. The included licensed sample can drive the complete demonstration.
5. Docker Compose starts the complete prototype locally.
6. Automated backend and frontend tests cover the primary behavior.

## Scope

### In Scope

- STEP/STL bounding-box analysis
- Conservative stock and tooling proposal
- Representative multi-setup Haas-style G-code
- Time-stepped five-axis telemetry simulation
- React progress portal
- Local containers and documentation

### Out of Scope

- Native SolidWorks/Fusion parsing
- Production CAM or controller integration
- Exact stock-removal, collision, fixture, or cutting-force simulation
- Authentication, persistence, purchasing, and shipping integrations

## Technical Notes

- Generated programs are demonstrative and must carry a prominent safety
  warning.
- Use an in-process asynchronous worker for this vertical slice, behind domain
  service interfaces that can later move to the background-worker service.
- Do not execute or dynamically load uploaded content.

## Testing Requirements

- [ ] Unit tests for geometry analysis, planning, G-code, and parsing
- [ ] API integration test for asynchronous job submission
- [ ] React component test for the intake experience
- [ ] Local container smoke test

## Dependencies

- None

---
**Status:** in-progress
**Created:** 2026-09-30
**Author:** @mmcadamsms and Copilot

