# CNC Program Verification, Cutting Engagement, and Dry Runs

## Problem

An animated toolpath can look plausible while a rapid move enters stock, the
holder hits a clamp, a rotary sweep collides between clear endpoints, or a
cut removes far more material than intended. Geometry-only CAM previews also
cannot establish that the final posted NC program follows the intended path.

## Objective

Before any future physical execution, simulate the exact final CNC program
against the configured machine, tools, fixtures, and evolving stock. Identify
collisions, gouges, excessive or unsupported cuts, and remaining material;
quantify what each cut removes and its engagement, estimated loads, and time.

This specifies future verification. The current prototype's playback is not
a qualified collision/material-removal verifier and must not be used to
authorize real-machine execution.

## Terminology

- **Program verification:** Evaluation of the final posted NC plus its
  execution context, not only pre-postprocessor CAM paths.
- **Cutting engagement:** The intersection of the active cutting geometry
  with remaining stock during motion; it changes within a pass.
- **Gouge:** Removal from protected final-part geometry or other material
  that must remain at that stage, beyond approved geometric allowances.
- **Collision:** An impermissible intersection or clearance violation among
  machine/tool/holder/fixture/stock/part geometry under the configured rules.
- **Coverage:** The motions, controller semantics, bodies, and physical
  effects that were actually evaluated, including explicit exclusions.
- **Above-zero dry run / air cut:** A separately approved non-cutting
  execution plan at clearance from the verified workpiece datum and physical
  stock/fixture envelope. Workpiece Z zero is not machine home or a universal
  safe plane.

## Inputs

Final NC program bundle and hashes; controller/postprocessor versions and
supported modal behavior; machine configuration/kinematics/travel; units and
work/tool offsets; actual stock and part geometry/revisions; setup transforms;
cutting and non-cutting tool/holder geometry; fixtures/clamps and protected
regions; operator/setup transitions; feeds/speeds and permitted overrides;
material/process models; approved clearances, allowances, and engagement limits.
An air-cut request additionally needs an approved machine/controller-specific
method, explicit datum/clearance definition, scope, and qualified authorization.

## Rules

1. **PV-01 - Verify the delivered program.** Parse and evaluate the final
   posted NC that would be delivered, retaining line/block, operation, setup,
   tool, and orientation mappings. Compare it with the intended CAM route.
   Verifying a different path or an older program cannot qualify this one.
2. **PV-02 - Explicit controller scope.** Interpret supported units, modal
   state, offsets, tool length/radius compensation, cycles, subprograms, and
   rotary behavior consistently with the intended controller configuration.
   Unknown commands or unresolved macro/probe/conditional behavior must yield
   an unsupported/unresolved result, not be skipped or assumed harmless.
3. **PV-03 - Actual machine and setup context.** Use the applicable machine
   geometry/kinematics, travel and rotary limits, configured tool assemblies,
   fixture/jaw/clamp state, stock, and coordinate transforms. Missing geometry,
   offsets, initial states, or setup confirmation limits coverage explicitly
   and prevents a claim of full-machine verification.
4. **PV-04 - Continuous motion coverage.** Check swept motion or a documented
   conservative/adaptive approximation, including arcs, rapid positioning,
   retracts, indexing/unwind, and supported tool-change movements. Clear start
   and end poses alone do not prove a collision-free transition. Record
   geometric/sampling error bounds relative to approved clearance criteria.
5. **PV-05 - Intended cutting versus impact.** Permit stock intersection only
   by supported active cutting geometry under an allowed cutting state.
   Distinguish flutes/tip from shank, holder, spindle, and other non-cutting
   bodies. A rapid entering stock, a holder entering a pocket wall, or a tool
   contacting protected fixture geometry cannot be accepted as material removal.
6. **PV-06 - Evolving stock and protected material.** Update remaining stock
   in actual program order, retaining removed volume, remaining bodies,
   protected part surfaces, required allowances, and holding/locating
   material. Detect unintended gouges, uncut regions, unsupported fragments,
   and premature loss of holding features; do not subtract a synthetic
   percentage or repeatedly count overlapping sweeps as newly removed volume.
7. **PV-07 - Quantify the cut.** For each supported pass and critical
   within-pass interval, report removed volume, material-removal rate,
   axial/radial engagement or their applicable operation-specific equivalents,
   chip-thickness/engagement estimates where supported, feed/spindle state,
   and estimated cutting duration. Report peak conditions as well as totals;
   averaging a heavy entry cut over the full operation is insufficient.
8. **PV-08 - Process-limit checks.** Compare supported engagement, tool reach,
   cutting conditions, estimated force, torque/power, and tool/workholding
   deflection/loading against approved applicable limits. Use calibrated
   model inputs and uncertainty; geometry verification alone cannot prove
   acceptable force, chatter stability, tool life, or machine capability.
   Link stock/engagement evidence to the workholding analysis rather than
   maintaining a contradictory second stock history.
9. **PV-09 - Time is a separate estimate.** Reuse commanded-time semantics
   for rapid/feed motion, cycles, indexing, dwell, and configured overheads.
   Separate cutting/non-cutting machine time from operator and inspection
   delays. State acceleration/controller/process limitations and distinguish
   an estimate from actual production time. Playback speed changes neither
   cut quantity nor estimated machine time.
10. **PV-10 - Diagnose before execution.** Identify the earliest failing
    motion, involved bodies, location, stock state, limit, and evidence.
    Show critical engagement peaks and subsequent affected operations.
    Required collision, gouge, limit, or unsupported-coverage findings block
    dependent release until corrected and reverified or routed through a
    qualified explicitly scoped process; ordinary acknowledgement cannot
    turn an unverified program into a verified one.
11. **PV-11 - Reviewable correction.** Propose process/path/tool/fixture
    changes for qualified review, regenerate the posted program as needed,
    and verify the resulting artifact again. Do not silently clamp feeds,
    alter offsets, change contact conditions, or rewrite G-code while
    continuing to label the original program verified.
12. **PV-12 - Revision-bound evidence.** Persist program/context fingerprint,
    evaluated coverage, engine/model versions, geometric resolution, load
    assumptions, findings, and disposition. Editing NC, stock, offsets, tools,
    fixture state, machine configuration, or applicable limits invalidates
    affected verification and downstream evidence.
13. **PV-13 - Runtime assumptions remain checks.** Specify the physical
    setup/tool/offset and in-process checks needed to confirm the simulated
    context. A verified digital setup does not prove that the correct tool,
    clamp, or blank is on the real machine. Changed conditions require review
    and affected re-verification before continuation.
14. **PV-14 - Explicit above-zero dry run.** Support a separately planned
    operator-requested air cut above the verified workpiece datum and clear
    of the complete applicable stock/fixture envelope. Use only a qualified
    machine/controller-specific method and approved clearance. Verify its
    actual motions, tool/holder envelope, travel, retracts, cycles, and rotary
    transitions before any future physical use. Do not assume that adding
    one Z offset is safe for every orientation, setup, or controller mode.
15. **PV-15 - Keep dry-run evidence separate.** Preserve the production NC
    and its verified offsets/context. Any derived dry-run artifact/configuration
    needs its own identity, original-program link, verification, limitations,
    and authorized restoration checks before production. Do not silently
    rewrite production zero/tool offsets. An accepted air cut demonstrates
    only its scoped non-cutting checks, not production-depth clearance,
    material removal, cutting force, workholding adequacy, or part conformance.

## Assumptions

- Start with a declared supported machine/controller/program subset; do not
  imply universal G-code or full simultaneous five-axis verification.
- Exact paths, controller semantics, setup data, and qualified models are
  dependencies, not capabilities supplied by current animation.
- Appropriate machining/verification owners must approve clearances,
  engagement limits, uncertainty budgets, physical comparison cases, and
  release criteria. No universal safe cut depth or stock-removal rate exists.

## Constraints

- This is preflight planning evidence, not a safety-rated collision-prevention
  device or a guarantee against real-world crashes.
- Existing qualified CAM/postprocessor review, approved machine prove-out,
  setup verification, maintenance eligibility, and independent safety
  procedures remain necessary. This document supplies no prove-out instructions.
- Partial coverage, missing setup information, resource exhaustion, and
  engine failure cannot be displayed as a complete successful verification.

## Acceptance Criteria

| ID | Observable outcome |
|----|--------------------|
| PV-AC-01 | A posted-program/offset discrepancy is diagnosed against the actual NC, even when the source CAM path appears clear. |
| PV-AC-02 | A rapid through stock, a shank/holder collision with otherwise clear cutting flutes, and a fixture collision each identify the offending block and bodies. |
| PV-AC-03 | An indexed move with clear endpoints but an intervening collision fails verification. |
| PV-AC-04 | A reference pocket/overlapping-pass case reports newly removed volume and remaining stock without double counting; protected-part gouges are detected. |
| PV-AC-05 | A within-pass engagement spike is reported with supported depth/width or equivalent metrics, load implications, and applicable limits rather than hidden in an average. |
| PV-AC-06 | Unsupported modal/macro behavior or missing machine/tool/fixture context yields explicit coverage gaps and cannot qualify the whole program. |
| PV-AC-07 | Program, offset, tool, fixture, or stock changes invalidate affected verification, workholding evidence, and dependent approvals. |
| PV-AC-08 | Known analytic/swept-geometry cases verify geometric error bounds; comparison with approved external verification and controlled physical evidence qualifies the declared scope before production use. |
| PV-AC-09 | Playback-rate changes preserve removed volume, engagement/load results, and commanded-time estimates; inspection/operator delays remain separately identified. |
| PV-AC-10 | Required physical setup confirmations remain unsatisfied by digital simulation or stop acknowledgement alone. |
| PV-AC-11 | An above-zero dry-run request identifies the workpiece datum, approved clearance/method, original-program link, and separately checked air-cut context; an undefined zero or unsupported transformation remains blocked. |
| PV-AC-12 | A case where a raised/indexed move violates travel or fixture clearance is rejected; a successful air cut cannot satisfy production cutting/load/inspection gates or silently alter the original offsets. |

## Failure Behavior

Expose collision, gouge, limit-exceeded, unsupported-controller-feature,
missing-context, incomplete-coverage, stale-evidence, and analysis-failed states.
Preserve the failing path interval and diagnostics. Do not skip problem blocks,
use envelope-only clearance as full-machine evidence, or return success from a
timeout or missing model.

## Out of Scope

- Implementing a qualified NC interpreter, stock engine, collision verifier,
  force model, or machine connection in this PR.
- Guaranteeing prevention of every crash or verifying unmodeled controller,
  physical setup, material, dynamic, or failure behavior.
- Automatic machine prove-out, parameter changes, or physical execution.

The air-cut requirements specify future planning and verification behavior,
not instructions to edit offsets or run a real machine in this increment.

## References

- [Geometry-Driven Prismatic 3+2 CAM](geometry-driven-3plus2-cam.md)
- [Workholding Assessment](pass-by-pass-workholding-analysis.md)
- [In-Process Inspection](in-process-inspection-and-progressive-verification.md)
- [Machine Maintenance and Care](machine-maintenance-and-care.md)
- [Current CNC Prototype Scope](cnc-prototype.md)
- [Commanded Cycle-Time Model](../../../docs/manufacturing/umc-750-cycle-time-model.md)
- [Proposed Progressive Verification Design](../../../docs/manufacturing/progressive-manufacturing-verification-design.md)
- [Requirements/Design Prompt](../../prompts/deploying/20261028-progressive-manufacturing-verification.md)
