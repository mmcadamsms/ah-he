# ADR 0008: Separate Manufacturing Evidence from Playback and Release

## Status

Proposed - requirements/design only; not an implemented or validated system.

## Context

Commanded-time playback cannot establish final-NC collision clearance, cutting
engagement, evolving stock stiffness, workholding stress, completed machine
care, or measured part conformance.
Combining those concepts with animation progress or one success flag would
allow unsupported predictions to appear as production approval.

[ADR 0004](0004-use-cad-kernel-for-setup-planning.md) already places exact
geometry/CAM work outside the Java API, which owns orchestration and approval.
The new capabilities should extend that boundary rather than duplicate a
geometry kernel or create disconnected asset and quality records.

## Proposed Decision

Keep final-program verification, physical analysis, maintenance evidence,
inspection evidence, and production-release decisions distinct, linked through
revisioned dependencies.
Keep the Java API as canonical orchestrator and geometry/stock computation at
the existing worker boundary. Expose mechanics through a scoped analysis
adapter; selecting a solver or another deployable runtime is deferred.

Treat analysis method qualification, job execution status, findings, evidence
freshness, and authorized disposition as separate facts. Missing, unsupported,
failed, or obsolete evidence cannot satisfy required production gates.
Preserve independent physical-machine and zone-safety authorization.

## Alternatives

- **Animate synthetic stress from time/progress:** Rejected as physical evidence;
  it has no supported relationship to actual engagement, preload, or stock.
- **One completed/passed flag:** Rejected; it hides unevaluated failure modes,
  missing measurements, and maintenance-release dependencies.
- **Universal raised-Z dry run:** Rejected; a workpiece-datum change is not
  automatically safe for rotary motion, fixture clearance, or axis travel.
  An approved air-cut context must be verified separately and cannot qualify
  cutting loads or production geometry.
- **Full mechanics platform immediately:** Deferred until bounded models,
  required inputs, validation, licensing, and operating cost are understood.

## Consequences

- More explicit provenance and gate handling are required, but partial or
  stale work cannot masquerade as complete verification.
- Care and inspection can be developed incrementally without claiming that
  numerical mechanics or physical execution are already supported.
- This proposal does not add a solver, approve engineering limits, relax
  prototype warnings, or authorize machine control.

## References

- [Workholding Requirements](../../../requirements/functional-areas/manufacturing-automation/pass-by-pass-workholding-analysis.md)
- [CNC Program Verification](../../../requirements/functional-areas/manufacturing-automation/cnc-program-verification-and-cutting-engagement.md)
- [Machine-Care Requirements](../../../requirements/functional-areas/manufacturing-automation/machine-maintenance-and-care.md)
- [Inspection Requirements](../../../requirements/functional-areas/manufacturing-automation/in-process-inspection-and-progressive-verification.md)
- [Proposed Design and Verification Stages](../../manufacturing/progressive-manufacturing-verification-design.md)
