# Progressive Manufacturing Verification - Proposed Design

## Scope and Status

Requirements/design only. No CNC verifier, stress solver, maintenance scheduler,
inspection integration, or production-release mechanism is added by this change.

The governing requirements are
[CNC program/engagement verification](../../requirements/functional-areas/manufacturing-automation/cnc-program-verification-and-cutting-engagement.md),
[workholding assessment](../../requirements/functional-areas/manufacturing-automation/pass-by-pass-workholding-analysis.md),
[machine care](../../requirements/functional-areas/manufacturing-automation/machine-maintenance-and-care.md),
and [in-process inspection](../../requirements/functional-areas/manufacturing-automation/in-process-inspection-and-progressive-verification.md).
The proposed boundary is recorded in
[ADR 0008](../architecture/decisions/0008-separate-manufacturing-evidence-from-playback.md).

## Current Baseline

The [UMC simulator](../../backend/web-api/src/main/java/com/ahhe/webapi/manufacturing/service/HaasUmc750Simulator.java)
plays timed G-code blocks. Its material-removed percentage is proportional to
elapsed cutting time and capped at a display value; it is not evolving stock
geometry, cutter engagement, or stiffness evidence.

The existing [CAM requirements](../../requirements/functional-areas/manufacturing-automation/geometry-driven-3plus2-cam.md)
already require valid workholding and inspected transfer features.
[Asset lifecycle requirements](../../requirements/functional-areas/manufacturing-automation/inventory-tooling-and-equipment-lifecycle.md)
already exclude unqualified equipment and account for maintenance demand.
The proposal makes these dependencies explicit and verifiable during the route.

## Proposed Flow

```text
Revisioned part + stock + process + workholding + quality requirements
  -> dependency-aware route, toolpaths, and final posted NC
  -> interpreted NC motions and actual stock/engagement history
  -> swept collision/gouge/limit verification and cut quantities
  -> stock/contact checkpoints and load cases
  -> scoped analytical screening / qualified numerical analysis
  -> review findings and required physical inspection checkpoints
  -> eligible resources + accepted prerequisites
  -> controlled operation and actual evidence collection
  -> re-evaluate affected gates after findings, changes, or maintenance
  -> final quality disposition (not just "machining complete")
```

Planning can evaluate future operations before execution. An inspection gate
can require a future physical measurement; a prediction cannot satisfy it.
During actual manufacture, new condition/measurement evidence can invalidate
an assumption and hold affected downstream work. This is not a proposal for a
real-time safety loop or autonomous parameter adjustment.

## Responsibilities

| Boundary | Proposed responsibility |
|----------|-------------------------|
| Java API/orchestration | Canonical job/asset identities, immutable input references, dependency gates, approvals, current eligibility, and audit exposure |
| NC interpretation/orchestration | Supported controller semantics and motion/modal timeline mapped to final NC blocks, consistent with commanded-time interpretation |
| Geometry/CAM worker | Shared ordered material-removal/engagement states, retained holding features, swept collision/gouge checks, coordinate transforms, and provenance |
| Analysis adapter | Declared supported force/contact/mechanics models, load cases, numerical work where justified, scoped findings and diagnostics |
| Asset/work-order domain | Applicable maintenance plans, due calculations, usage uncertainty, interventions, consumable demand, holds, and return-to-service evidence |
| Inspection/quality domain | Characteristic/checkpoint plans, qualified methods, actual results, sampling scope, nonconformance, containment, and final disposition |
| Client | Clearly separated predictions, actual observations, incomplete checks, and authorized dispositions |

These are logical responsibilities, not a decision to create new deployable
services. Keep geometry work at the ADR 0004 boundary and do not implement a
geometry/mechanics kernel in the Java controller. Solver, storage, queue, and
deployment selections remain open until supported by a bounded prototype.

## Evidence Contract and Validity

Every evidence record needs stable identity, subject and scope, contributing
input revisions/fingerprint, producer/model/procedure version, timestamp,
units/frame where applicable, assumptions, findings, and raw artifact
references. Maintain distinct record types for analysis, maintenance
completion/release, and inspection; do not flatten them into one `passed` flag.

For analysis, record the operation/pass/path interval, stock/contact checkpoint,
load case, method fidelity, supported failure modes, sampling/mesh resolution,
uncertainty, limits, and convergence/coverage diagnostics. Per-mode outcomes
distinguish within-declared-limits, exceeded, unresolved, and not evaluated.
Job completion, model qualification, current applicability, and human approval
are separate attributes. "Solver completed" does not mean "setup safe."

Inspection records contain actual values/observations and applicable decision
rules. Maintenance work completion is separate from post-service verification
and asset release. A part can pass one characteristic while still being held
for another; a maintained machine can remain unqualified for a specific job.

Input changes create new evidence and invalidate dependent gates instead of
overwriting old results. Track dependencies explicitly so a jaw change can
invalidate stock/contact/load analysis and required setup checks without
silently discarding unrelated accepted material certificates.

## Final-Program and Stock-Aware Analysis Strategy

Verification consumes the delivered NC artifact and execution context. Reuse
one versioned supported interpretation of motion/modal semantics rather than
letting timing, collision, and stress engines interpret offsets differently.
Unsupported controller behavior is a coverage gap, not an instruction to skip
a block. Full-machine claims require the relevant geometry and motion model.

Track cutting and non-cutting tool geometry separately. Swept checks cover
rapid/retract/index transitions as well as cutting, with explicit geometric
error bounds; endpoint sampling alone is inadequate. The shared ordered stock
history distinguishes permitted removal from protected-part/fixture gouges.
Measure newly removed volume and peak engagement within cuts, not an average
percentage of the billet. Link these intervals to feed/spindle state and
supported load models, while retaining commanded-time estimates separately.

Provide a separately approved above-zero air-cut plan where the intended
machine/controller supports a qualified method. Explicitly identify workpiece
datum and clearance; check the resulting actual swept motions and travel,
including rotary transitions, against the entire relevant setup. It is not a
generic translation of every Z command. Preserve the production artifact and
context; derived dry-run configuration/evidence remains separately identified,
with required restoration checks. An air cut does not validate production-depth
collisions, engagement, forces, workholding, or dimensional conformance.

Start with a bounded supported fixture/material/load class and analytical
reference cases. Use actual cutter sweeps and engagement to locate critical
states, including events within a pass and holding-feature loss. Reuse
unchanged stock states and immutable results only when their input fingerprints
and model scope match.

Screen simple supported equilibrium/retention cases first. Escalate declared
cases to contact/deformation analysis when geometry, thin sections, preload,
fixture compliance, or local pressure require it. Analytical screening cannot
fill in an unevaluated stress field. Do not choose an FEA library or claim
force-model accuracy before verifying input availability and licensing.

Numerical work needs mesh/contact refinement and verification against
analytical cases; physical validation needs suitably instrumented reference
setups and an approved uncertainty/acceptance protocol. Distinguish isolated
contact stress singularities from meaningful evaluated quantities. A converged
numerical answer may still be physically wrong if loads or contacts are wrong.

Persist critical-state history and findings. Rendering may interpolate visual
motion, but must not manufacture physical analysis samples or let playback
speed change predicted forces, stresses, or estimated machine time.

## Progressive Gates and Machine Care

The route connects feature creation, verification, and consumption. For
example:

| Point in the route | Required evidence before the dependent action |
|--------------------|-----------------------------------------------|
| Before program release | Current final-NC/context verification, declared collision/gouge/engagement coverage, and required qualified disposition |
| Before initial cutting | Applicable machine/fixture readiness and specified stock, setup, datum, and clamping checks |
| After roughing a critical region | Required intermediate dimensions/condition and review of unexpected distortion before finishing |
| Before transferring onto a locating bore | Specified bore/datum inspection while accessible, plus the next setup's workholding evidence |
| Before removing the last holding feature | A qualified supported retention/transfer strategy; physical checks where required |
| After maintenance affecting accuracy | Required service completion and post-service qualification before affected production resumes |
| Before shipment | Required intermediate/final acceptance, traceability, and resolved nonconformances |

Maintenance shares the existing asset and inventory ledger. Work orders
reserve downtime, safe access, people, and parts; required checks schedule
qualified metrology resources as well. Due calculations use approved
calendar/usage/condition rules, explicit meter freshness, and projected use;
software must not make an unknown asset available by assuming zero use.

Persist gates, work orders, meter baselines, inspection results, and audit
events. Use idempotent external-event keys and optimistic revision checks so
retries cannot duplicate completion or release work against stale prerequisites.
Recheck current eligibility at operation release, not only at quote/planning
time. Current approved eligibility remains authoritative if an asynchronous
calculation finishes for an obsolete revision.

The scheduler can request holds/interventions; existing independent machine
and zone-safety controls authorize access and restoration. No successful record,
acknowledgement, or software reset commands physical motion.

## Staged Implementation and Verification

| Stage | Bounded deliverable | Required evidence before advancing |
|-------|---------------------|-----------------------------------|
| 1 | Revisioned contracts, missing-input reporting, gate dependencies, manual maintenance and inspection capture | Domain tests for stale evidence, holds, duplicate events, interrupted work, and restart persistence |
| 2 | Final-NC stock/engagement verification and a declared analytical workholding scope | Rapid/holder/index-sweep collision, gouge/volume, one-setup/indexed, support-loss/within-pass, frame/unit, and hand-checkable reference cases |
| 3 | Selected higher-fidelity contact/deformation models | Numerical convergence plus physical validation with agreed tolerances, calibrated inputs, and explicit exclusions |
| 4 | Qualified integrations and scoped production workflows | End-to-end containment, safe intervention boundaries, post-service release, measurement lineage, and final-release checks |

Inspection and care planning can progress without waiting for a full mechanics
solver, but unsupported workholding remains unresolved for any job that needs
it. Do not call a stage production-ready merely because the software runs.

## Open Decisions

- First supported fixture/contact/material/load class and available input data.
- Controller/program subset, machine/collision-model coverage, and geometric
  error budget for actual posted-NC verification.
- Qualified machine/controller-specific above-zero air-cut method, datum and
  clearance rules, supported motions, and restoration/authorization evidence.
- Force model, model calibration, mesh/solver choice, resource budget, and
  approved physical validation protocol.
- Asset-specific manuals, required tasks/intervals, data freshness rules,
  release authority, and non-waivable obligations.
- Characteristic criticality, metrology capability, sampling/uncertainty
  decision rules, independent-check needs, and concession authority.
- Persistence/integration mechanisms and deployment isolation if numerical
  work requires a separate runtime; record subsequent architectural choices
  in ADRs rather than silently treating these proposals as implemented.
