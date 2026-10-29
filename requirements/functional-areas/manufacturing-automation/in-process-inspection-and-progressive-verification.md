# In-Process Inspection and Progressive Verification

## Problem

Final inspection can discover a defect only after additional work has been
invested or the feature has become inaccessible. Later operations may also
depend on an unverified datum, locating bore, surface, or workholding condition.

## Objective

Naturally verify the part as it is built: plan inspections at meaningful
manufacturing checkpoints, record actual evidence, and prevent dependent work
from proceeding on unverified or nonconforming prerequisites. Final acceptance
remains separate; inspection is not postponed until the part is finished.

These are requirements for future implementation, not a claim that the
prototype currently measures or accepts real parts.

## Terminology

- **Characteristic:** A revisioned dimension, tolerance, geometry, surface,
  material/traceability, or other specified condition requiring verification.
- **Checkpoint:** A planned inspection window tied to manufacturing state,
  feature accessibility, and dependent operations.
- **Evidence:** Actual observations/measurements with method, equipment,
  provenance, uncertainty or capability basis, and disposition.
- **Gate:** A production dependency that remains blocked until its required
  evidence is accepted. Simulation results are predictions, not measurements.

## Inputs

Drawing/model revision, datum scheme and tolerances, critical characteristics,
customer/quality requirements, operation/setup sequence and remaining stock,
workholding assumptions, inspection access, qualified measurement methods and
equipment, calibration status, sampling/decision rules, and authorized reviewers.

## Rules

1. **IP-01 - Inspection is part of the route.** Generate a revisioned
   inspection plan alongside the manufacturing plan. Link each required
   characteristic to its creation operation, verification checkpoint, and
   downstream dependencies; do not add only a generic final-inspection task.
2. **IP-02 - Inspect while accessible.** Place checks after meaningful feature
   creation and before required features become obscured, are clamped over,
   coated/assembled, removed, or irreversibly consumed. If inspection access
   conflicts with holding/access constraints, explicitly replan or escalate.
3. **IP-03 - Verify prerequisites.** Verify required initial stock/material,
   locating and clamping conditions, work offsets/datums, transfer features,
   and setup changes before dependent work. For example, a bore used to
   locate the next physical setup needs its specified acceptance evidence
   before transfer; its presence in a simulated solid is insufficient.
4. **IP-04 - Risk-directed checkpoints.** Define required checks at appropriate
   first-piece/setup, roughing-to-finishing, tool/fixture-change, critical
   feature, and pre-transfer stages. State the risk/customer/process basis.
   Do not assume every toolpath block needs a measurement or that a final
   check can replace a required intermediate one.
5. **IP-05 - Capable methods.** Specify the characteristic, datum reference,
   units, tolerance/limit revision, equipment/method, access, environment,
   uncertainty/capability basis, and acceptance rule. On-machine probing,
   manual gauges, vision, or off-machine metrology may be used only within
   their qualified scope; a camera image alone is not a dimensional result.
6. **IP-06 - Independence where needed.** Account for correlated machine,
   work-offset, fixture, and probe errors, temperature, clamping deformation,
   and calibration. Measuring on the machine that created a feature does not
   automatically prove independent conformance. Require independent or
   unclamped-state verification where the quality plan demands it.
7. **IP-07 - Actual evidence and genealogy.** Store characteristic/checkpoint,
   part/serial or approved lot scope, job/operation/setup, drawing revision,
   measured value and units or explicit qualitative observation, limits,
   method/equipment/calibration, actor, time, conditions, raw evidence
   references, and decision. Keep predicted values distinct from measured
   values and supplier certificates distinct from observed checks.
8. **IP-08 - Explicit dispositions.** Distinguish not yet inspected, pending
   review, accepted, nonconforming, inconclusive, and not applicable with an
   approved reason. Missing evidence, uncertainty that prevents a decision,
   or an expired/unqualified instrument must not become an automatic pass.
9. **IP-09 - Enforced dependencies.** Required checkpoints must be accepted
   before release of their dependent operations. Schedule inspection time,
   trained people, equipment, movement, and safe-access/settling windows.
   An operator acknowledging a stop is not evidence of inspection completion.
10. **IP-10 - Nonconformance and containment.** Hold affected parts/WIP,
    preserve measurements, identify potentially affected production since
    the last known acceptable check, and route rework, scrap, or concession
    through authorized disposition. Reworked features need an approved
    reinspection plan. Re-measuring must not erase a failing original.
11. **IP-11 - Invalidation.** A contributing drawing, program, setup, fixture,
    tool, measurement-method, calibration, or process change triggers an
    impact assessment of affected evidence and downstream releases.
    Unaffected accepted evidence may be retained only with a recorded basis.
12. **IP-12 - Controlled sampling.** Specify whether evidence applies to one
    part, first-piece, a defined lot, or another approved scope. Sampling
    requires an approved risk/statistical/customer basis and escalation rule;
    one passing sample must not silently accept every part.
13. **IP-13 - Improvement without autonomous correction.** Trend appropriate
    measurements and link drift, unexpected deformation, tool wear, or machine
    condition to reviewable maintenance/process actions. Do not automatically
    change offsets, feeds, clamps, limits, or sampling from unreviewed trends.
14. **IP-14 - Final release remains distinct.** At completion, reconcile
    required intermediate and final checks, outstanding nonconformances,
    traceability, and authorized concessions. Passing intermediate checks
    does not establish final unclamped dimensions, all characteristics, or
    permission to ship.

## Assumptions

- Product/customer tolerances and the approved quality plan determine the
  characteristics, methods, frequency, decision rules, and release authority.
  No universal sampling rate, uncertainty ratio, or gauge capability is
  approved here.
- Human or automated collection may be used; both must be attributable and
  supported by actual evidence. Missing integrations require a controlled
  manual workflow or a hold, not fabricated observations.
- Inspection should use natural process windows and avoid unnecessary
  re-clamping without omitting checks needed for conformance.

## Constraints

- Safe entry, machine stops, isolation, probing motion, and restart remain
  subject to approved machine/zone procedures and independent safety controls.
- Simulation, visual animation, process completion, and accepted workholding
  estimates cannot substitute for required physical inspection.
- Evidence and corrections must persist across restarts and retries, with
  original failures and authorized dispositions preserved.

## Acceptance Criteria

| ID | Observable outcome |
|----|--------------------|
| IP-AC-01 | A multi-operation part has feature-linked checkpoints during manufacture, not only a final inspection operation. |
| IP-AC-02 | A locating bore created in one setup must be inspected as required before the next setup can depend on it. |
| IP-AC-03 | A feature that becomes inaccessible after transfer receives an earlier feasible check or an explicit unresolved planning finding. |
| IP-AC-04 | An M00 acknowledgement or completed animation cannot satisfy a required measured checkpoint. |
| IP-AC-05 | Measurements retain part/lot scope, revisions, method, calibrated equipment, units, acceptance rule, actor, and original evidence. |
| IP-AC-06 | Missing measurements, inconclusive decisions, and unqualified equipment hold dependent work rather than passing it. |
| IP-AC-07 | A failed critical check contains affected WIP, identifies the suspect production interval/scope, and requires authorized disposition and reinspection after rework. |
| IP-AC-08 | Changing a contributing setup or program invalidates affected releases; replaying a result after restart does not duplicate or overwrite its history. |
| IP-AC-09 | An accepted first-piece result cannot accept unrelated units without the approved sampling scope and rule. |
| IP-AC-10 | Final release refuses missing required checkpoints or unresolved nonconformances, even when all machining operations are marked complete. |

## Failure Behavior

Keep required but missing, inaccessible, unqualified, stale, conflicting,
failed, or inconclusive checks visible and blocked at their dependent gates.
Identify the affected characteristic, part/lot, operation, and reason.
Integration failures retain pending work and original evidence; they do not
invent a measurement, erase a failure, or fall back to shipment approval.

## Out of Scope

- Implementing metrology integrations, checkpoint UI, or quality release in
  this documentation-only PR.
- Defining all customer-specific quality plans or replacing final inspection.
- Automatically certifying a measurement method or issuing concessions.
- Autonomous probing, offset correction, or physical-machine control.

## References

- [Geometry-Driven Prismatic 3+2 CAM](geometry-driven-3plus2-cam.md)
- [CNC Program Verification](cnc-program-verification-and-cutting-engagement.md)
- [Workholding Assessment](pass-by-pass-workholding-analysis.md)
- [Machine Maintenance and Care](machine-maintenance-and-care.md)
- [Inventory and Equipment Lifecycle](inventory-tooling-and-equipment-lifecycle.md)
- [Human and Automation Zone Safety](../facility-operations/human-automation-zone-safety-and-scheduling.md)
- [Proposed Progressive Verification Design](../../../docs/manufacturing/progressive-manufacturing-verification-design.md)
- [Requirements/Design Prompt](../../prompts/deploying/20261028-progressive-manufacturing-verification.md)
