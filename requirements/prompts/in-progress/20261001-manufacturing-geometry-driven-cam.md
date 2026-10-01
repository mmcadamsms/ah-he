# Prompt: Complete Geometry-Driven Prismatic 3+2 CAM

## Requirement Reference

- **Functional Area:** manufacturing automation
- **Requirement Doc:** `requirements/functional-areas/manufacturing-automation/geometry-driven-3plus2-cam.md`

## Objective

Replace the legacy envelope-based demonstration planner with deterministic,
geometry-driven stock selection, workholding, minimum physical setup
optimization, feature-level CAM, per-setup NC programs, and verification for
prismatic 3+2 machining on the configured Haas UMC-750.

## Completed Foundation

- [x] Local Java/Spring manufacturing job API
- [x] Customer progress portal and machine playback
- [x] G-code-derived commanded cycle timing and playback-rate control
- [x] Period-specific UMC-750 timing profile and calibration boundaries
- [x] Source-inspectable CadQuery/OCP/Open CASCADE geometry worker
- [x] Exact STEP solid count, bounds, volume, faces, normals, and surface types
- [x] Generic deterministic minimum-set setup optimizer
- [x] Dependency ordering for later setups that require earlier-machined
      locating or clamping features
- [x] Tests proving one-setup selection, minimum multi-setup selection,
      impossible coverage reporting, and setup prerequisite ordering

## Remaining Work

### Customer Manufacturing Constraints

- [ ] Add portal/API input for surfaces allowed to remain as-stock
- [ ] Add customer-approved and prohibited clamp/contact regions
- [ ] Capture cosmetic/no-mark surfaces and witness-mark allowances
- [ ] Capture sacrificial stock/tab/dovetail permission
- [ ] Capture finish, datum, tolerance, and inspection requirements
- [ ] Require explicit approval of inferred assumptions

### Feature Recognition

- [ ] Validate one solid body or require the user to select a body
- [ ] Recognize planar faces, steps, pockets, slots, bosses, and open profiles
- [ ] Recognize through/blind holes, bores, counterbores, countersinks, and
      threads where represented
- [ ] Extract cylindrical axes/radii and classify internal versus external
- [ ] Identify undercuts and features outside supported prismatic 3+2 scope
- [ ] Preserve stable feature IDs from geometry through setup, CAM, and UX

### Workholding Candidate Generation

- [ ] Implement vise and custom soft-jaw candidates
- [ ] Implement external chuck/collet candidates for cylindrical interfaces
- [ ] Implement expanding-mandrel candidates for internal bores
- [ ] Implement existing-hole/thread fixture-plate candidates
- [ ] Implement sacrificial pedestal/tab/dovetail candidates
- [ ] Model locating features, clamp regions, forces, distortion, and contact
      restrictions
- [ ] Generate setup-transition instructions, work offsets, and probing steps
- [ ] Reject candidates with fixture, spindle, trunnion, holder, or travel
      collisions

### Setup Optimization

- [ ] Build feature-accessibility matrices for legal B/C orientations
- [ ] Include tool and holder reach for every feature/orientation
- [ ] Solve minimum physical re-clamps before secondary cycle-time objectives
- [ ] Allow any number of indexed orientations inside one clamping
- [ ] Ensure later setup prerequisites are created and retained by earlier
      operations
- [ ] Report unresolved/inaccessible features instead of emitting nominal
      setups

### Geometry-Driven CAM

- [ ] Select standard stock from oriented part bounds and workholding allowance
- [ ] Generate geometry-following facing and layer/adaptive roughing
- [ ] Generate pockets, slots, drilling, contouring, chamfers, and rest
      machining
- [ ] Associate every path with source feature, setup, orientation, tool,
      holder, feeds/speeds, and engagement limits
- [ ] Preserve stock and part transforms across physical setups
- [ ] Remove the fixed generic orientation/path recipe

### NC Program Bundle and UX

- [ ] Emit one NC program per physical setup
- [ ] Include all automatic indexed orientations within that setup program
- [ ] Generate setup sheets, fixture details, tools, offsets, comments, and
      operator transitions
- [ ] Display why each setup is required and how the part is held
- [ ] Display covered, unmachined, unsupported, and customer-approved features
- [ ] Retain compact and verbose G-code views

### Verification

- [ ] Add deterministic one-setup, two-setup, three-setup, and impossible-part
      fixtures
- [ ] Verify stock fully contains the transformed part
- [ ] Verify every required feature is covered exactly once or intentionally
      revisited
- [ ] Add holder/fixture/machine collision checks
- [ ] Add voxel/SDF or CAD-kernel remaining-stock verification
- [ ] Compare generated paths and cycle time against approved reference CAM
- [ ] Keep physical-machine execution prohibited until qualified review

## Current Pause Point

Exact STEP topology reaches Java successfully, and the generic setup optimizer
and dependency sequencing are tested. The next implementation step is mapping
kernel faces/features plus customer contact constraints into typed fixture
candidates. The setup count currently displayed by the legacy portal generator
is not yet the optimized manufacturing answer.

---
**Status:** in-progress (paused)
**Created:** 2026-10-01
**Author:** @mmcadamsms and Copilot

