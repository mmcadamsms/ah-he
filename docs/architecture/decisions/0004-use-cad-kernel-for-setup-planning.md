# ADR 0004: Use a CAD Kernel and Separate Geometry/CAM Worker

## Status

Accepted

## Context

Bounding-box parsing cannot identify faces, pockets, holes, undercuts,
workholding surfaces, or tool accessibility. It cannot support setup
optimization or geometry-following CAM. Implementing a complete STEP B-rep
kernel and machining algorithms directly in the Java API would duplicate
specialized, safety-critical geometry software.

## Decision

The Java API remains the canonical job orchestrator and customer-facing
contract. Exact CAD/topology and CAM computation will run in a local,
source-inspectable geometry/CAM worker built around an established open-source
CAD kernel.

The worker contract returns:

- normalized solid/topology and tessellation;
- recognized prismatic features;
- stock candidates;
- candidate physical setups and indexed orientations;
- feature-accessibility and collision evidence;
- generated toolpaths and unmachined regions; and
- deterministic diagnostics and provenance.

Java owns material/tool catalogs, business rules, setup-objective weights,
approval state, Haas postprocessing, cycle-time simulation, and API exposure.

The setup optimizer minimizes physical clamping states. It may use any number
of indexed B/C orientations inside one physical setup.

Workholding is selected from a typed fixture-strategy catalog rather than a
"largest planar face" heuristic. Candidate interfaces may be planar,
cylindrical, internal, hole/thread based, or sacrificial, and are filtered by
customer-approved contact and finish constraints.

Setup selection is dependency-aware. A later setup can require locating or
clamping features created by an earlier setup, and the optimizer must return a
feasible ordered sequence rather than an unordered feature-covering set.

## Consequences

- Actual geometry rather than an envelope drives stock, setup, and toolpaths.
- The worker can use native geometry and numerical libraries without moving the
  public backend away from Java.
- A stable versioned worker contract and deterministic fixtures are required.
- Open-source multi-axis CAM remains incomplete; the first supported scope is
  prismatic 3+2 machining.
- Generated output remains unqualified until compared with approved CAM and
  postprocessed simulation.
