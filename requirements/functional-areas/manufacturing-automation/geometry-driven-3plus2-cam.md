# Geometry-Driven Prismatic 3+2 CAM

## Problem

The initial prototype uses a part envelope to emit a fixed sequence of generic
passes and indexed orientations. It does not inspect the actual solid, identify
machinable features, reason about workholding, minimize physical re-clamps, or
generate toolpaths that follow the submitted geometry.

## Objective

For prismatic parts, convert an uploaded CAD solid into the minimum feasible
set of physical workholding setups for a configured Haas UMC-750. Within each
physical setup, use any legal number of automatic B/C indexed orientations and
generate geometry-driven CAM operations and Haas-style NC programs.

## Terminology

- **Physical setup:** The part is clamped, located, and assigned a verified
  work offset. Unclamping, flipping, changing fixtures, or establishing another
  work offset creates another physical setup.
- **Indexed orientation:** The UMC changes B/C while the part remains clamped.
  An indexed orientation does not increase physical setup count.
- **Program:** A qualified NC program associated with one physical setup. It
  may contain many indexed orientations.

## Setup Optimization Objective

The planner uses a lexicographic objective:

1. Cover every required machinable feature.
2. Minimize physical setups/re-clamps.
3. Reject fixture, holder, tool, spindle, trunnion, and travel collisions.
4. Preserve stable workholding and adequate clamping surfaces.
5. Minimize cycle time, rotary motion, tool changes, and long-reach tooling.

The setup count is never fixed. If one clamping provides safe access to all
required features, the result is one physical setup. Additional setups are
created only when required by inaccessible/clamped surfaces, undercuts,
workholding, tool reach, machine limits, or inspection requirements.

## Geometry Analysis

The CAD kernel must provide:

- valid solid bodies and units;
- exact and oriented bounds, volume, and center of mass;
- face topology, surface type, normals, loops, area, and adjacency;
- holes, pockets, slots, planar faces, bosses, steps, chamfers, and fillets;
- tessellation for visibility, collision, and visualization; and
- explicit failures for invalid, open, or non-manifold geometry.

## Stock Selection

1. Evaluate rectangular billet orientations against the part's oriented
   bounding boxes.
2. Add process-appropriate facing, sawing, and workholding allowance.
3. Select the smallest standard stock that contains the oriented part and
   permits the proposed fixtures and tool access.
4. Preserve the part coordinate transform from CAD to stock and every setup.

## Accessibility and Workholding

1. Generate candidate tool directions from planar-face normals, cylindrical
   axes, principal axes, and approved UMC B/C orientations.
2. Determine which features are visible and machinable from each orientation,
   including tool/holder reach and undercut checks.
3. Reserve stable clamp/fixture contact surfaces and mark them inaccessible in
   that physical setup.
4. Solve feature coverage with the minimum number of physical clamping states.
5. Use multiple indexed orientations inside a clamping state when they improve
   access without requiring operator re-fixturing.
6. If another physical setup is required, generate a feasible transition
   workholding plan that exposes the previously clamped regions.

Workholding candidates are not limited to planar faces. The fixture catalog
must support, where applicable:

- vise or soft-jaw contact on rectangular, angled, or irregular regions;
- three-jaw/four-jaw chuck or collet contact on external cylinders;
- expanding mandrels on internal bores;
- fixture pins, screws, and clamps using existing holes, threads, slots, or
  bosses;
- dovetail, pedestal, tab, or other sacrificial stock features;
- vacuum, magnetic, adhesive, or custom fixtures when material/process rules
  permit them; and
- customer-approved contact faces, witness marks, or intentionally unfinished
  surfaces.

The planner must not assume every CAD face requires the same finish. Setup
optimization consumes explicit manufacturing constraints:

- faces and features requiring machining;
- as-stock surfaces that may remain;
- cosmetic/no-mark surfaces;
- allowed clamp/contact regions;
- prohibited clamp/contact regions;
- sacrificial stock allowed or prohibited;
- acceptable witness marks and their limits; and
- datum, tolerance, and inspection requirements.

Without those constraints, the system must state its assumptions and request
approval rather than silently treating the largest planar face as the clamp
face.

## Setup Transition Planning

A selected setup after the first must identify:

- the surfaces/features used to locate the part;
- the surfaces/features that receive clamp force;
- which of those features exist in raw stock versus which must be created by
  earlier operations;
- the fixture type, jaw/collet/mandrel geometry, and required custom fixture;
- how previously clamped or inaccessible regions become exposed;
- the stock/part transform and next work offset (for example G55);
- probing/indicating steps required to establish that offset;
- acceptable contact pressure, distortion, and witness-mark constraints;
- clearance to tools, holders, spindle, trunnion, and fixture; and
- an operation order that does not remove a required locating/clamping feature
  before its final use.

The optimizer may select a multi-setup coverage combination only when its
setups can be placed in a feasible dependency order. For example, if Setup 2
uses an expanding mandrel in a finished bore, Setup 1 must create and inspect
that bore before the part is removed and transferred to Setup 2.

If no feasible way exists to hold the part while exposing the remaining
features, the system must report an unresolved workholding problem rather than
emit a nominal second setup.

## CAM Operations

For the supported prismatic scope, generate geometry-following:

- stock facing;
- adaptive or layer roughing;
- planar and contour finishing;
- pocket and slot milling;
- drilling and peck drilling;
- chamfering and edge breaks; and
- rest machining with smaller tools.

Every path must retain its source feature, setup, orientation, tool, holder,
feed/speed calculation, engagement limits, and safety/retract strategy.

## Program Bundle

Produce one NC program per physical setup. Each bundle includes:

- setup sheet and fixture/work-offset instructions;
- stock and part transforms;
- indexed-orientation sections;
- tool and holder list;
- verbose comments describing each feature and operation;
- operator stops only where human action is actually required;
- calculated commanded cycle time; and
- warnings and unmachined/inaccessible feature lists.

## Acceptance Criteria

1. A part accessible from one clamping produces exactly one physical setup.
2. A part requiring an underside flip produces the minimum additional setup
   and a separate program/work-offset plan.
3. Indexed B/C orientations do not appear as separate physical setups.
4. Toolpaths follow recognized geometry rather than only the bounding box.
5. Every required feature is mapped to a generated operation or an explicit
   unsupported/inaccessible error.
6. The simulator consumes the actual generated paths and reports remaining
   stock or uncut regions.
7. Tests include known one-setup and multi-setup fixtures with expected setup
   counts and feature coverage.

## Out of Scope for This Increment

- freeform simultaneous five-axis swarf/flow-line machining;
- casting/forging near-net stock;
- automatic custom fixture design;
- tolerance-stack and metrology-plan generation; and
- production execution without qualified CAM/post/verification approval.
