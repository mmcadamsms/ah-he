# Pass-by-Pass Workholding Load and Stress Assessment

## Problem

A fixture that holds the initial billet may become inadequate as cuts remove
support, reduce stiffness, expose a thin wall, or change cutter engagement.
Motion playback and collision checks do not establish that the workpiece,
clamps, locating features, or fixture can withstand those loads.

## Objective

Automatically estimate and, where qualified models support it, simulate
workholding behavior throughout the actual machining sequence. Identify the
pass and stock state that create a problem early enough to change the process
plan, tooling, cutting conditions, support, or fixture.

This is a future capability specification, not an implemented solver or
authorization to run the prototype's generated programs.

## Terminology

- **Stock state:** Remaining material and connected bodies at an identified
  point in the toolpath, including sacrificial and holding features.
- **Load case:** A defined combination of cutting force/moment, clamp preload,
  gravity, and other supported loads at a stock state and orientation.
- **Screening:** A bounded analytical estimate for a declared supported case;
  it is not automatically a stress simulation.
- **Stress analysis:** A numerical or analytical mechanics model with explicit
  geometry, material, contact, boundary conditions, and validity evidence.
- **Physical setup / indexed orientation:** The existing CAM definitions
  apply. Indexing while retaining the clamping is not another physical setup.

## Inputs

| Input | Required information |
|-------|----------------------|
| Geometry | Part/stock revisions, units, remaining material, connected bodies, tolerances, datums, and permitted contact/mark regions |
| Process | Actual ordered toolpaths, operation/pass/block identity, tool/holder geometry, feeds/speeds, engagement, and setup transitions |
| Material | Grade/condition, applicable mechanical properties and cutting-force evidence, ranges, units, and provenance |
| Workholding | Fixture/jaw/support geometry, locating scheme, contact regions, preload or supported actuation-to-force model, fixture ratings, and friction/retention evidence |
| Machine | Configuration, coordinate transforms, B/C orientations, applicable motion/load envelope, and qualification/maintenance status |
| Policy | Approved model scope, uncertainty treatment, allowable deformation/contact/strength limits, review authority, and validation evidence |

## Rules

### Geometry and Sequence

1. **WH-01 - Real material evolution.** Derive stock states from tool sweeps
   through the ordered remaining material, using the final-program verification
   stock/engagement history where available, not elapsed time, playback speed,
   operation count, or the prototype's material-removed percentage. Preserve
   links to the source geometry and path revisions.
2. **WH-02 - Critical moments within a pass.** Assess initial clamping, cutter
   entry/exit, engagement changes, critical intermediate cuts, operation ends,
   indexing, re-clamping, and release where relevant. End-of-pass samples alone
   are insufficient when an intermediate peak or support loss is possible.
   Record sampling resolution, refinement rules, and unresolved coverage.
3. **WH-03 - Holding-feature survival.** Track locators, jaw engagement,
   sacrificial tabs, pedestals, and supporting webs until their last required
   use. Detect disconnected bodies and removal of required holding material.
   A feature being present in the final CAD model is not evidence that the
   workpiece remains adequately supported throughout machining.
4. **WH-04 - Frames and orientations.** Resolve tool, workpiece, fixture, and
   machine transforms consistently. Re-evaluate the gravity/load direction at
   each indexed orientation and transfer setup; never silently reuse the
   first orientation's load case.

### Loads, Contacts, and Failure Modes

5. **WH-05 - Traceable cutting loads.** Estimate force direction, magnitude,
   moment, and application region from supported tool engagement and process
   conditions. Record coefficient/model sources, calibration scope, wear and
   process assumptions, and uncertainty. A generic material label or spindle
   power alone must not become an unexplained exact force.
6. **WH-06 - Actual retention mechanism.** Represent preload, contact opening,
   locating/support constraints, and the fixture's supported retention
   mechanism. Include coolant/contact-condition uncertainty where relevant.
   Friction, pressure, torque-to-preload, adhesive, vacuum, and magnetic
   retention inputs must not be invented or treated as interchangeable.
7. **WH-07 - Separate assessments.** Report the supported assessments of
   slip, lift/separation, overturning, contact pressure/marking, workpiece
   deflection, workpiece stress, and fixture/clamp/fastener loading. Identify
   which bodies and failure modes were not evaluated. A slip calculation does
   not establish fixture strength or part accuracy.
8. **WH-08 - Deformation through the sequence.** Include clamping-induced
   distortion and changing stock stiffness in supported models, including
   thin walls and unsupported spans. Distinguish loaded shape from release
   or springback predictions. Missing residual-stress, thermal, plasticity,
   or dynamic models must be disclosed when they affect applicability.
9. **WH-09 - Validity and limits.** Compare outputs against explicitly
   approved, revisioned limits with units and uncertainty treatment. Distinguish
   an estimated margin from a validated result. Disclose contact/mesh
   convergence, singularities, simplifications, and unmodeled failure modes;
   a static stress result is not proof against chatter or fatigue.

### Planning and Evidence

10. **WH-10 - Qualified feasibility before optimization.** Unsafe or unresolved
    workholding is not a feasible candidate merely because it reduces setup
    count or cycle time. Within supported feasible candidates, preserve the
    existing minimum-physical-re-clamp objective; do not mandate two setups or
    only planar vise contact.
11. **WH-11 - Explainable replanning.** Identify the earliest failing or
    unresolved pass, the limiting feature/contact, and the evidence. Propose
    reviewable changes such as sequencing, support, engagement, or fixture
    changes, then reassess affected downstream states. Do not autonomously
    apply unqualified cutting or clamping changes.
12. **WH-12 - Versioned results.** Retain the input fingerprint, stock/path
    checkpoint, model/solver version, assumptions, limits, resolution,
    diagnostics, and reviewer disposition. Changes to a contributing input
    invalidate affected evidence and approvals; old results remain auditable.
13. **WH-13 - Honest presentation.** Show pass-level findings and the worst
    supported cases with units, uncertainty, and model scope. Label screening,
    simulation, and physical measurements separately. A heatmap must identify
    its body, quantity, scale, stock state, and unsupported regions rather
    than color an entire setup as safe.
14. **WH-14 - Measured confirmation.** Link required setup and in-process
    inspections to the assumptions they verify. A changed clamp condition,
    observed deflection, damaged support, or out-of-model cutting condition
    requires impact review, not automatic reuse of the original prediction.

## Assumptions

- This increment records requirements/design only. The current simulator
  provides commanded-time/motion playback, not stock-aware stress analysis.
- A first implementation may support only declared fixture, material, and
  quasi-static load classes. Unsupported cases remain unresolved.
- No default friction, clamp force, strength limit, material coefficient,
  accuracy target, safety factor, or solver is approved here. A qualified
  engineering owner must approve those inputs and their applicability.
- Customer contact/finish requirements and qualified-human review remain
  necessary; missing information must not be inferred as permission.

## Constraints

- Analysis is planning evidence, not a safety-rated control or production
  release by itself. Existing prototype warnings and physical-machine
  execution prohibitions remain in force.
- Resource/time budgets must be explicit. A cancelled, timed-out, coarse, or
  unconverged analysis is incomplete, not a favorable result.
- Machine readiness, collision clearance, dimensional inspection, and
  workholding validity are distinct checks; none substitutes for the others.

## Acceptance Criteria

| ID | Observable outcome |
|----|--------------------|
| WH-AC-01 | A supported reference case reports preload and cutting-load effects at identified stock/path checkpoints with reproducible inputs and units. |
| WH-AC-02 | Removing a required tab/support during a pass identifies that intermediate failure even when the pass endpoints appear acceptable. |
| WH-AC-03 | A thin-wall case changes predicted deflection as stock is removed; playback-rate changes do not change the analysis. |
| WH-AC-04 | Two indexed orientations produce correctly transformed load cases while remaining one physical setup. |
| WH-AC-05 | Screening that evaluates slip but not clamp strength visibly reports the latter as not evaluated. |
| WH-AC-06 | Missing preload, unsupported contact/material, disconnected stock, or a failed solver produces an explicit unresolved finding and blocks dependent release. |
| WH-AC-07 | Changing a path, jaw/contact region, material property, or approved limit invalidates affected results and approvals without deleting their history. |
| WH-AC-08 | Analytical checks, numerical refinement, and appropriately measured reference cases establish the declared model scope before production use; tolerances are approved before comparison. |
| WH-AC-09 | A known feasible one-clamping case remains one setup; an infeasible candidate cannot win on setup count or cycle time. |
| WH-AC-10 | Required measured checks link to the specific model assumptions; disagreement creates a hold and impact review. |

## Failure Behavior

Return explicit missing-input, unsupported-model, incomplete-coverage,
non-convergence, exceeded-limit, stale-evidence, or review-required findings.
Do not replace an unavailable analysis with synthetic stresses, zero loads, an
all-green heatmap, or an approval. Preserve partial evidence and diagnostics
without presenting an incomplete sequence as fully assessed.

## Out of Scope

- Implementing a force model, stock-removal engine, mesher, or solver in this PR.
- Automatically designing or certifying custom fixtures.
- Claiming all materials, contact mechanisms, dynamic effects, or continuous
  five-axis machining are supported.
- Closed-loop force control or automatic machine/clamp commands.

## References

- [Geometry-Driven Prismatic 3+2 CAM](geometry-driven-3plus2-cam.md)
- [CNC Program Verification](cnc-program-verification-and-cutting-engagement.md)
- [In-Process Inspection](in-process-inspection-and-progressive-verification.md)
- [Machine Maintenance and Care](machine-maintenance-and-care.md)
- [Inventory and Equipment Lifecycle](inventory-tooling-and-equipment-lifecycle.md)
- [Proposed Progressive Verification Design](../../../docs/manufacturing/progressive-manufacturing-verification-design.md)
- [ADR 0004: Geometry/CAM Worker](../../../docs/architecture/decisions/0004-use-cad-kernel-for-setup-planning.md)
- [Proposed ADR 0008](../../../docs/architecture/decisions/0008-separate-manufacturing-evidence-from-playback.md)
- [Requirements/Design Prompt](../../prompts/deploying/20261028-progressive-manufacturing-verification.md)
