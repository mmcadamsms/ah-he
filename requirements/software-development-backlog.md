# Definition-Led Software Development Backlog

## Assessment Basis

**Assessment date:** 2026-10-31

**Source baseline:** [`7fc0bb8d0e5314087a2c7c45fa737eaf8cac7402`](https://github.com/mmcadamsms/ah-he/commit/7fc0bb8d0e5314087a2c7c45fa737eaf8cac7402)

**Working branch:** `docs/ranked-software-development-backlog`

`main` was synchronized and `origin/main` was merged into the working branch
before this assessment. That merge was already up to date. The baseline includes
the manufacturing verification requirements from #10 and the implemented
feedback-export changes from #11.

This is a stack rank of **unfinished software by definition and readiness to
scope a bounded implementation**, not by market opportunity, urgency, or safety
importance. Inventory, machine care, and measured inspection are the strongest
shared workflow foundations. Geometry-driven CAM is the clearest continuation
of an existing implementation, but its remaining engineering work is substantial.

All six functional areas, their substantive requirements, implementation
prompts, runtime source, relevant architecture, and the feedback workflow were
considered. This is a source-based assessment, not a fresh execution of the test
suites or a claim of production or physical-machine validation.

## How to Read the Rank

Definition comes first: identifiable inputs, business rules, outputs, failure
behavior, and observable acceptance criteria. Among similarly defined items,
the order favors a bounded first slice, reusable prerequisites, existing code
seams, and fewer unresolved external or engineering contracts. Adjacent ranks
are qualitative judgments, not precise scores or effort estimates.

| Band | Meaning |
|------|---------|
| A | Record-and-rule workflow is well defined enough to scope a bounded implementation. Storage, permissions, and applicable operating policies still need decisions. |
| B | Intended feature is defined, but completing it depends heavily on missing upstream records, integrations, algorithms, or qualified input data. |
| C | Operating outcome is documented, but the software/model/integration approach needs substantial definition before implementation can be responsibly scoped. |

For every item below, **first slice** and **done for that slice** are deliberately
narrower than the complete requirement. Finishing a slice must not mark the whole
capability complete. These recommendations do not approve new policies,
engineering limits, external providers, purchases, or machine-control authority.

## Stack Rank

| Rank | Unfinished work | Band | Implementation today | Main reason for position |
|------|-----------------|------|----------------------|--------------------------|
| 1 | Inventory, asset identity, custody, and WIP ledger | A | Requirements only | Explicit transaction, genealogy, availability, and reconciliation rules; foundation for several other defined workflows. |
| 2 | Machine maintenance and care workflow | A | Requirements/design only | Concrete triggers, evidence, due/held states, and restart cases; manual capture can precede integrations. |
| 3 | In-process inspection and measured release gates | A | Requirements/design only; demo has an inspection task | Explicit checkpoints, measured evidence, containment, and release failures; does not require a mechanics solver to start. |
| 4 | Owned benchmark and external quote record library | A | Requirements only | A bounded manual-record workflow is defined without selecting or automating external quote providers. |
| 5 | Return authorization, receiving, and remedy workflow | A | Requirements only | Particularly detailed records and lifecycle; full delivery is blocked by absent order, identity, carrier, and payment foundations. |
| 6 | Geometry-driven 3+2 CAM: constraints through actual planning | B | Partially implemented | Existing kernel and optimizer give a concrete continuation point; fixture generation, accessibility, integration, and actual CAM remain. |
| 7 | Shipment identity, custody, and evidence | B | Requirements only | Basic package records are clear; carrier integration and performance analysis need missing order and operating history. |
| 8 | Customer credit and payment-risk gates | B | Requirements only | Exposure and gating rules are documented; financial sources, thresholds, authorities, and policies are not selected. |
| 9 | Customer acceptance and returns-cost risk | B | Requirements only | Clear distinctions and controls, but depends on quality, orders, returns, costs, and approved customer acceptance rules. |
| 10 | Learning-job economics and process-tuning workflow | B | Requirements only | A bounded approval/learning record is possible; credible evaluation requires approved budgets and comparable actuals. |
| 11 | Historical-part actuals and explainable analog estimating | B | Requirements only | Data requirements and comparison principles are clear; actual production history and estimation methods are missing. |
| 12 | Thermal-condition records and dimensional-control integration | B | Requirements only | Defined measurement safeguards, but useful correction and release depend on inspection plans and qualified material/metrology inputs. |
| 13 | Final-NC stock, collision, engagement, and above-zero dry-run verification | B | Requirements/design only; motion/timing playback exists | Detailed failure cases, but no stock-removal/collision engine or qualified controller and air-cut scope exists. |
| 14 | Bottleneck evidence and automation-improvement analysis | C | Requirements only | Evaluation goals are explicit; evidence collection, causal interpretation, and risk-adjusted comparison need more definition and real data. |
| 15 | Pass-by-pass workholding stress and stability analysis | C | Requirements/design only | Failure modes are well documented; force/contact models, supported scope, solver, calibration, and validation remain open. |
| 16 | Advisory zone-state records and production scheduling integration | C | Operating requirements only | Safety boundaries are explicit; software contracts and the qualified physical safety interface have not been designed. |
| 17 | Facility, utilities, and layout planning software | C | Planning requirements only | Candidate outputs and mandatory gates are defined, but most work is physical planning and no specific software product or layout method is selected. |

## Ranked Work Details

### 1. Inventory, Asset Identity, Custody, and WIP Ledger

**Requirement:** [Inventory, Tooling, and Equipment Lifecycle](functional-areas/manufacturing-automation/inventory-tooling-and-equipment-lifecycle.md).

**Remaining work / first slice:** Build durable controlled-item, asset, location,
custody, reservation, and movement records. Start with qualified manual
transactions for receiving, moving, consuming, splitting stock/remnants, and
transferring WIP. No inventory domain implementation currently exists.

**High-level requirements:** Distinguish inventory classes, ownership, physical
location, condition, qualification, and planned versus confirmed movement.
Preserve attributable actors, reconciliable quantities, and parent-child material
genealogy. Uncertain custody or location must remain visible rather than becoming
available stock. The complete feature also needs tooling-component use,
refurbishment/retirement, qualification, time-phased demand, and dated shortages;
those are follow-on slices, not part of a single small ledger ticket.

**Dependencies / decisions:** Durable storage and concurrency/idempotency
contracts, actor and access model, units/control levels, and initial transaction
scope. Automated machine locations, accounting, and predictive tool life can
follow; a generic catalog life cannot stand in for measured remaining life.

**Done for the first slice:** A stock-to-remnant-to-WIP scenario reconciles
quantities and custody through correction, duplicate-event replay, and restart.
Quarantined or unqualified items cannot satisfy a qualified reservation.

### 2. Machine Maintenance and Care Workflow

**Requirement:** [Machine Maintenance and Care](functional-areas/manufacturing-automation/machine-maintenance-and-care.md).

**Remaining work / first slice:** Add revisioned care plans, due evaluation,
manual work records, downtime, asset holds, and post-service release. Cover both
production machines and shared support assets; no care scheduler or evidence
ledger currently exists.

**High-level requirements:** Use approved asset-specific procedures for cleaning,
oiling, inspection, servicing, and other applicable tasks. Handle calendar,
usage, and combined triggers, stale/reset meters, consumables/spares, dependency
outages, interrupted work, bounded deferrals, and attributable completion.
Reminders are not completed maintenance; missing plans and failed post-service
checks do not qualify an asset.

**Dependencies / decisions:** Rank 1 asset identity/history, approved OEM plans
and intervals, meter freshness rules, qualified release authority, and durable
evidence. Direct meter/CMMS integrations and scheduling projections follow the
manual foundation. Business software does not perform safe isolation.

**Done for the first slice:** Configured calendar/usage boundaries, stale meters,
interrupted care, failed qualification, and restart produce the required due or
held outcomes without duplicate completion history (`MC-AC-01` through
`MC-AC-10`, selecting the cases applicable to this slice).

### 3. In-Process Inspection and Measured Release Gates

**Requirement:** [In-Process Inspection and Progressive Verification](functional-areas/manufacturing-automation/in-process-inspection-and-progressive-verification.md).

**Remaining work / first slice:** Implement revisioned, feature/characteristic-
linked inspection checkpoints, manual measurement evidence, dispositions, and
dependent-operation/final-release gates. The existing planner's generic
inspection task and simulator's operator acknowledgement are not this feature.

**High-level requirements:** Inspect naturally during manufacture, before
dependent, irreversible, or inaccessible stages. Preserve actual measurements,
units, method, qualified/calibrated equipment, uncertainty/acceptance rule,
part/lot scope, actor, and original evidence. Missing or inconclusive checks hold
work; failures contain affected WIP, require authorized disposition, and require
reinspection after rework. First-piece acceptance is not unrestricted lot
acceptance, and final release remains separate.

**Dependencies / decisions:** Rank 1 WIP and gauge records, revisioned part/
characteristic/operation identity, approved criticality and sampling/decision
rules, and release authority. Manual characteristic entry can precede complete
automatic CAM checkpoint placement or metrology integration.

**Done for the first slice:** A required locating-feature measurement blocks the
dependent setup until accepted; an M00 acknowledgement cannot satisfy it.
Missing evidence, unqualified equipment, rework, revision changes, and restart
preserve the required holds and history (`IP-AC-02`, `IP-AC-04` through
`IP-AC-10`).

### 4. Owned Benchmark and External Quote Record Library

**Requirement:** [External Market Quote Benchmarking](functional-areas/commercial-operations/external-market-quote-benchmarking.md).

**Remaining work / first slice:** Build a rights-controlled, revisioned benchmark
library and manual quote-entry/comparison workflow. Automated provider
collection is not necessary for this first slice and is not implemented.

**High-level requirements:** Preserve geometry/drawing revision and authority
to use each benchmark. Record provider, date, quantity, material, tolerances,
finishing, lead time, scope, price, tax/shipping, and exclusions. Keep original
quotes separate from visible normalization adjustments, age/confidence, internal
cost, and proposed selling price. Never upload customer IP without authority.

**Dependencies / decisions:** Record ownership/access/retention, supported
benchmark fields, comparison scope, and approved normalization rules. Provider
adapters, licensed data, and authorized automation need separate decisions.

**Done for the first slice:** Two quotes for one benchmark can be compared
without hiding different quantities, services, lead times, or stale evidence;
unapproved benchmark use is blocked and every adjustment retains its rationale.

### 5. Return Authorization, Receiving, and Remedy Workflow

**Requirement:** [Return Authorization and Refund Workflow](functional-areas/commercial-operations/return-authorization-and-refund-workflow.md).

**Remaining work / first slice:** Implement the internal authorization,
package/return identity, receiving quarantine, identification, inspection, and
remedy-decision record. Add actual carrier credential issuance and payment/
credit execution only through subsequently selected integrations.

**High-level requirements:** Link customer, order/line, part/revision, quantity,
eligibility, reasons, evidence, package, and return identity. Enforce authorized
lifecycle transitions and customer visibility. Distinguish unidentified receipt,
inspection, approved remedy, pending financial execution, and completion.
Preserve itemized refund/credit calculations and prevent duplicate remedies.

**Dependencies / decisions:** Missing order/customer foundation and authenticated
access, rank 1 custody, rank 3 inspection, return policies and transition guards,
approval authorities, and carrier/payment/accounting contracts. Manual records
must not pretend that labels were issued or money was refunded.

**Done for the first slice:** An identified and an unidentified returned package
follow the proper quarantine/review paths; unauthorized or duplicate remedies
are rejected and a recorded remedy remains pending until actual execution
evidence exists.

### 6. Geometry-Driven 3+2 CAM: Constraints Through Actual Planning

**Requirement:** [Geometry-Driven 3+2 CAM](functional-areas/manufacturing-automation/geometry-driven-3plus2-cam.md).

**Continuation prompt:** [Paused CAM Implementation](prompts/in-progress/20261001-manufacturing-geometry-driven-cam.md).

**Remaining work / first slice:** Continue at the documented paused seam:
customer constraints and kernel features into typed workholding/setup candidates
and the existing optimizer. Start by defining and carrying revisioned constraint
and feature contracts through intake, API, worker, and planning; qualify a
declared initial candidate/accessibility scope before replacing the demo route.

**High-level requirements:** Capture datums, tolerances, contact/no-mark areas,
finish and sacrificial-stock permissions. Generate actual geometry-based
features, stock/workholding candidates, reachability and setup dependencies.
Optimize physical re-clamps, not a fixed setup count; indexed orientations can
share one clamping. Complete the remaining geometry-following toolpaths,
unmachined-region reporting, Haas posting, and verification in later slices.

**What already exists:** Exact STEP topology, cylindrical radius/axis and
internal/external classification, orientation proposals, and a generic
dependency-aware setup optimizer. The orchestrator does not call that optimizer;
its planner/G-code generator still produce the envelope-based fixed demo route.
Cylindrical extraction must not be restarted solely because a prompt checkbox
is stale.

**Dependencies / decisions:** Stable feature/revision contracts, supported
prismatic feature and fixture classes, accessibility/occlusion/collision methods,
and declared unsupported geometry. Follow accepted
[ADR 0004](../docs/architecture/decisions/0004-use-cad-kernel-for-setup-planning.md);
geometric candidates alone are not qualified holding-load evidence.

**Done for the first slice:** Customer constraints reach candidate filtering
without silent loss; a supported one-clamping case stays one setup, a dependent
case is correctly ordered, and an impossible/unsupported case stays unresolved.
Integration tests prove the orchestrator consumes actual candidate/optimizer
results rather than exercising the optimizer only in isolation.

### 7. Shipment Identity, Custody, and Evidence

**Requirement:** [Shipment Evidence and Carrier Performance](functional-areas/commercial-operations/shipment-evidence-and-carrier-performance.md).

**Remaining work / first slice:** Add package identity, outbound/return custody
events, risk-based evidence capture, and manual carrier-event/claim records.
Carrier APIs, scorecards, and selection recommendations are later work.

**High-level requirements:** Link packages to order contents and lifecycle
events. Capture sufficient packing, condition, handoff, and receiving evidence;
use staged photos when sufficient rather than making continuous video universal.
Record carrier service, events, failures, claims, costs, and source/coverage.
Missing evidence does not establish fault.

**Dependencies / decisions:** Order identity, rank 1 custody, evidence storage,
access/retention and capture policy, carrier/claim interfaces, and real history.
Reuse package identity and events with rank 5 instead of duplicating them.

**Done for the first slice:** One outbound/return package retains its content,
handoff, evidence, and event history; missing capture is explicitly visible and
does not automatically blame the customer, carrier, or shop.

### 8. Customer Credit and Payment-Risk Gates

**Requirement:** [Customer Credit and Payment Risk](functional-areas/commercial-operations/customer-credit-and-payment-risk.md).

**Remaining work / first slice:** Implement a revisioned customer-terms/exposure
record and policy-driven gate evaluator using explicitly supplied financial
inputs. Integrate live receivables, purchases, production commitments, payments,
and collections afterward.

**High-level requirements:** Include unrecovered committed material, WIP,
tooling, and outside-service cost, not receivables alone. Evaluate at quote,
purchase, production, milestone, and shipment gates. Preserve effective-dated
terms, deposits/milestones, reasoned bounded overrides, and audit. Distinguish
valid disputes from delinquency.

**Dependencies / decisions:** Customer/order identity, cost and receivable
sources, legal/accounting policy, approved thresholds, authority, and freshness
rules. The repository does not select numeric credit limits or financial
providers; those cannot become invented implementation defaults.

**Done for the first slice:** Approved test policies gate a customer with
unrecovered commitments even without overdue receivables; dispute treatment,
policy revisions, and override expiry retain explicit reasons and history.

### 9. Customer Acceptance and Returns-Cost Risk

**Requirement:** [Customer Returns and Acceptance Risk](functional-areas/commercial-operations/customer-returns-and-acceptance-risk.md).

**Remaining work / first slice:** Capture customer-specific acceptance
requirements and structured return reason/responsibility/cost records. Then
connect evidence-based quote/release review and corrective-action reporting.

**High-level requirements:** Preserve drawing, inspection, cosmetic, packaging,
and other agreed acceptance expectations. Distinguish customer, shop, carrier,
shared, and unresolved responsibility; measured direct costs and consequential
effects are not interchangeable. Use documented evidence and mitigation rather
than reputation-based surcharges.

**Dependencies / decisions:** Ranks 3, 5, and 7 quality/returns/shipment facts,
customer/order records, cost categories, and approved acceptance/review policy.
The RMA lifecycle belongs to rank 5; this item adds acceptance and economic
interpretation, not a second return system.

**Done for the first slice:** A disputed cosmetic/transport return retains the
actual agreed acceptance rule, evidence, unresolved responsibility, and separately
classified costs without unsupported blame or automatic pricing penalties.

### 10. Learning-Job Economics and Process-Tuning Workflow

**Requirement:** [Learning-Job Economics and Process Tuning](functional-areas/manufacturing-automation/learning-job-economics-and-process-tuning.md).

**Remaining work / first slice:** Build a learning-job proposal, authorized
acceptance, bounded investment/loss budget, objective, measurement, and
expiry/graduation/stop record. Automating economic comparisons comes after
comparable actual-job evidence.

**High-level requirements:** Separate normal price, accepted price, and
intentional learning investment. State measurable learning goals and a bounded
approval rather than permanently underpricing work. Compare like-for-like
repetitions using actual time, cost, quality, and throughput. Never relax safety
or quality to claim learning success.

**Dependencies / decisions:** Order/cost identity, approved loss and approval
policy, rank 11 actuals, and repeatability/comparison rules. A manually approved
learning record can precede the full historical estimator.

**Done for the first slice:** A learning job has an approved bounded investment
and measurable exit conditions; budget/expiry breaches require the specified
review and cannot silently continue as an ordinary profitable job.

### 11. Historical-Part Actuals and Explainable Analog Estimating

**Requirement:** [Historical-Part Analog Estimating](functional-areas/manufacturing-automation/historical-part-analog-estimating.md).

**Remaining work / first slice:** Capture/import revisioned actual part/job
history with provenance and query exact revisions. Build family/similarity search,
explainable comparison, normalization, and estimate generation only after usable
history and comparison methods exist.

**High-level requirements:** Retain geometry, material, process, setup, time,
cost, quality, and delivery evidence. Prefer exact revision, then family, then
explained broader analogs. Distinguish actuals, estimates, normalized values,
abnormal jobs, confidence, and commercial price. Apply current approved rates
without overwriting original history; enforce customer/project isolation.

**Dependencies / decisions:** Ranks 1 and 3 operational evidence, real job/cost
capture, retention/access rules, rate/version contracts, and selected similarity
and normalization methods. Simulated elapsed time is not actual shop performance.

**Done for the first slice:** Exact-revision retrieval preserves source actuals
and exposes missing costs/evidence; no fabricated history or confidently priced
analog is returned when data is insufficient.

### 12. Thermal-Condition Records and Dimensional-Control Integration

**Requirement:** [Thermal Environment and Dimensional Control](functional-areas/facility-operations/thermal-environment-and-dimensional-control.md).

**Remaining work / first slice:** Attach approved thermal-control plans and
temperature/stabilization evidence to inspection, preserving raw and any
approved corrected measurement. Add excursion review/holds before automated
sensor, coolant, machine-compensation, or facility integrations.

**High-level requirements:** Use the specified reference temperature and
qualified material coefficient/method/uncertainty. Record part, environment,
machine/coolant conditions as applicable, warmup/stabilization, and excursions.
Reinspect or review affected work; a displayed corrected value must not erase the
raw result. Illustrative coefficients are not universal approved defaults.

**Dependencies / decisions:** Rank 3 measurement records, metrology-capable
plans, material data, correction and acceptance authority, sensors/freshness,
and operating evidence. Physical HVAC design and compensation qualification
remain separate from this software slice.

**Done for the first slice:** A temperature excursion exposes affected evidence
and required review; an unqualified correction is not presented as accepted
conformance and raw observations remain available.

### 13. Final-NC, Stock, Collision, Engagement, and Dry-Run Verification

**Requirement:** [CNC Program Verification and Cutting Engagement](functional-areas/manufacturing-automation/cnc-program-verification-and-cutting-engagement.md).

**Remaining work / first slice:** Define a versioned final-NC verification
contract, supported controller semantics, actual stock/tool/holder/fixture/
machine context, and explicit coverage gaps. Then implement and qualify bounded
swept-geometry stock removal and collision/engagement checks. Above-zero air-cut
preparation and verification are a separate dependent slice.

**High-level requirements:** Verify the actual posted program and offsets.
Detect rapid, shank/holder, fixture, rotary-sweep, travel, and protected-part
gouge problems; endpoints alone are insufficient. Calculate newly removed
volume, remaining stock, within-pass engagement and supported loads without
double counting or using animation progress. Revisions invalidate affected
evidence; unsupported semantics cannot qualify the whole program.

For an above-zero dry run, preserve the original NC and offsets, identify the
workpiece datum and approved method/clearance, and separately check the air-cut
motion and envelopes. It is **not a universal Z lift**, and a successful air cut
does not prove cutting loads or part conformance.

**Dependencies / decisions:** Rank 6 actual planning/context, controller subset,
geometric error budget, stock/collision method, qualified tool/fixture/machine
models, load inputs, and machine-specific dry-run approval/validation. The timed
interpreter and synthetic removed-material percentage do not fulfill this scope.

**Done for the first slice:** Missing context and unsupported NC yield blocking
coverage findings tied to exact revisions. Subsequent numerical slices need the
reference collision/removal cases in `PV-AC-01` through `09`; air-cut work needs
the separate cases in `PV-AC-11` and `12` before any production-use claim.

### 14. Bottleneck Evidence and Automation-Improvement Analysis

**Requirement:** [Bottleneck-Driven Automation Improvement](functional-areas/manufacturing-automation/bottleneck-driven-automation-improvement.md).

**Remaining work / first slice:** Define and implement manual bottleneck
evidence-period and intervention-proposal records. Before automated ranking,
specify coverage, comparison, causal limitations, and risk-adjusted economic
methods using actual operating observations.

**High-level requirements:** State product mix, evidence period, coverage,
confidence, alternative explanations, and total-system consequences. Compare
automation against simpler/process alternatives and include downtime, support,
risk, and realized results. Keep human approval; do not automate hazardous
control or personnel judgments.

**Dependencies / decisions:** Ranks 1, 2, 3, and 11 operating evidence, economics,
policy/authority, data collection, and qualified comparison methods. A dashboard
or queue length alone does not establish the true bottleneck.

**Done for the first slice:** A proposal retains evidence and competing
explanations and cannot label a speculative causal/ROI conclusion as measured.
Automated intervention ranking remains incomplete until its method is defined.

### 15. Pass-by-Pass Workholding Stress and Stability Analysis

**Requirement:** [Pass-by-Pass Workholding Analysis](functional-areas/manufacturing-automation/pass-by-pass-workholding-analysis.md).

**Remaining work / first slice:** Select a supported analytical contact/
fixture/material/load class and validation plan, then implement revisioned
analysis inputs/results and explicit unresolved states. Progress to qualified
analytical screening and, only where justified, higher-fidelity contact/
deformation models. No mechanics solver or force estimator is implemented.

**High-level requirements:** Track evolving stock, preload/contact, calibrated
or otherwise qualified cutting-force provenance/uncertainty, frames, indexed
orientation/gravity, and within-pass peaks. Evaluate declared slip/lift/
overturning, contact/clamp strength, and deflection modes. Removing a holding
feature during a pass can invalidate the setup even when endpoints look safe.
Report unevaluated modes, invalidate stale evidence, and connect measured checks.

**Dependencies / decisions:** Rank 6 fixture/contact context, rank 13 stock/
engagement evidence, material/contact/load inputs, approved limits, solver/model
choice and budget, and analytical/numerical/physical reference validation. Do
not equate the existing setup optimizer with fixture-load qualification.

**Done for the first slice:** Inputs and unsupported modes are traceable and
block dependent approval. A later qualified model must pass within-pass support
loss, orientation, uncertainty, convergence/reference, and disagreement cases
in `WH-AC-01` through `10` for its declared scope.

### 16. Advisory Zone-State Records and Scheduling Integration

**Requirement:** [Human and Automation Zone Safety and Scheduling](functional-areas/facility-operations/human-automation-zone-safety-and-scheduling.md).

**Remaining work / first slice:** Specify an advisory zone model, revisioned
state/transition/audit contract, schedule requests, and rejected/unresolved
status display. Select the qualified physical safety integration only after
risk assessment; no zone scheduler or safety interface exists.

**High-level requirements:** Model zone boundaries, permitted states, access,
occupancy evidence, routes, transitions, intervention time, priorities, and
dependencies. Distinguish requests from validated actual state. Business
scheduling must not authorize entry, bypass safety, reset, or start machinery.
Unknown/degraded conditions remain explicit; egress is independent, and reset
never starts motion.

**Dependencies / decisions:** Actual layout/hazards, identity/privacy/retention,
qualified safety engineering and devices, state-source freshness and failure
contracts, and a production scheduler. A business-software demonstration does
not validate physical protective responses.

**Done for the first slice:** Requests cannot promote a zone to an authorized
safe state; missing/conflicting physical evidence is shown as unresolved and
transition history remains attributable. Physical safety acceptance remains
separate and mandatory before operation.

### 17. Facility, Utilities, and Layout Planning Software

**Requirement:** [Facility, Site, Utilities, and Layout](functional-areas/facility-operations/facility-site-utilities-and-layout.md).

**Remaining work / first slice:** First decide whether the needed product is a
structured candidate/utility/gate register, scaled layout viewer, optimization
tool, or integration with existing planning software. A manual revisioned
candidate register is a plausible bounded starting point, not yet an approved
software specification.

**High-level requirements:** Retain phased equipment, installed footprints/
service clearances, zones, utilities/capacity/gaps, improvements, costs/timing,
permissions, material/people routes, egress, expansion, evidence, and approver.
Mandatory site failures or unresolved gates cannot be averaged away in a score
or bypassed by an attractive layout.

**Dependencies / decisions:** Product/UI/data scope, real property/equipment/
utility inputs, applicable codes and qualified engineering, layout method,
scoring policy, and change control. Buying, surveying, permitting, and building
the facility are not software-development deliverables.

**Done for a subsequently approved register slice:** A candidate retains
revisioned evidence and unresolved mandatory gates and cannot be recommended as
acceptable while a required gate remains unresolved.

## Implementation Evidence and Completion Traps

| Surface | Current source evidence | Consequence for the backlog |
|---------|-------------------------|-----------------------------|
| Web/API prototype | [React intake](../clients/web/src/IntakeForm.tsx), [dashboard application](../clients/web/src/App.tsx), [static client](../clients/web/static/app.js), [job controller](../backend/web-api/src/main/java/com/ahhe/webapi/manufacturing/controller/ManufacturingJobController.java), and [orchestrator](../backend/web-api/src/main/java/com/ahhe/webapi/manufacturing/service/ManufacturingOrchestrator.java) implement the submission/planning/playback slice. | Do not schedule the entire prototype anew. File/material/playback inputs are not customer constraint capture or production approval. |
| Geometry/CAM | [Geometry worker](../backend/geometry-cam-worker/server.py) and [setup optimizer](../backend/web-api/src/main/java/com/ahhe/webapi/manufacturing/service/SetupOptimizer.java) implement topology and generic optimization foundations. [Planner](../backend/web-api/src/main/java/com/ahhe/webapi/manufacturing/service/ProcessPlanner.java) and [G-code generator](../backend/web-api/src/main/java/com/ahhe/webapi/manufacturing/service/GcodeGenerator.java) retain the fixed demonstration route. | Rank 6 is genuinely incomplete end to end, but already implemented cylindrical extraction and optimizer behavior must be reused. |
| Timing versus physics | [Interpreter](../backend/web-api/src/main/java/com/ahhe/webapi/manufacturing/service/HaasGcodeInterpreter.java), [simulator](../backend/web-api/src/main/java/com/ahhe/webapi/manufacturing/service/HaasUmc750Simulator.java), and [timing model](../docs/manufacturing/umc-750-cycle-time-model.md) provide commanded-time playback. | The synthetic removed-material percentage is not evolving stock, engagement, force, or stress. Ranks 13 and 15 remain unimplemented. |
| Records and measured execution | [In-memory repository](../backend/web-api/src/main/java/com/ahhe/webapi/manufacturing/repository/InMemoryManufacturingJobRepository.java) and [job model](../backend/web-api/src/main/java/com/ahhe/webapi/manufacturing/model/ManufacturingJob.java) hold demonstration job state. | No inventory/commercial/care/measurement ledger exists. Simulated completion and generic human/machine actor categories are not durable attributable production evidence. |
| Existing tests | [Java tests](../backend/web-api/src/test/java/com/ahhe/webapi/manufacturing/), [React sample-workflow test](../clients/web/src/App.test.tsx), and [mailer offline tests](../scripts/tests/discovery-mail.tests.ps1) exist. | Test source is evidence of implemented scope, not proof all requirements pass or a fresh test-run result. |
| Discovery email helper | [Mailer](../scripts/discovery-mail.ps1), [requirements](functional-areas/notifications/discovery-email-learning-tour.md), and [runbook](../docs/runbooks/discovery-email-learning-tour.md) cover scheduling/state, replies, extra requests, stop/resume, and export, including #11 sanitization. | Do not restart delivered mailer features. Live success of the ownership fix on the other PC was not established here; that is an operational validation issue, not a newly defined feature. |
| Prompt status | The [prototype](prompts/in-progress/20260930-manufacturing-cnc-prototype.md) and [timing](prompts/in-progress/20260930-manufacturing-realistic-cycle-time.md) prompts remain in progress. The [completed inventory prompt](prompts/completed/20261015-manufacturing-inventory-lifecycle-planning.md) explicitly excluded software implementation, and the [completed zone prompt](prompts/completed/20261017-facility-human-automation-zone-safety.md) delivered requirements/discovery. | Folder names and checkboxes do not establish runtime completion. This assessment does not move or tidy those older prompts. |
| New manufacturing design | [Proposed integration design](../docs/manufacturing/progressive-manufacturing-verification-design.md) and [ADR 0008](../docs/architecture/decisions/0008-separate-manufacturing-evidence-from-playback.md) describe revisioned evidence and separated release gates. | #10 merged requirements/design, not a verifier, stress solver, care scheduler, or measured inspection system. The ADR is still Proposed. |

## Whole-Branch Coverage and Work Not Yet Ready to Rank

The branch contains 19 substantive functional requirement documents, excluding
the six area READMEs. Every one is accounted for below.

| Area/surface | Coverage | Disposition |
|--------------|----------|-------------|
| Manufacturing automation | Ten substantive documents | Existing [prototype scope](functional-areas/manufacturing-automation/cnc-prototype.md) is excluded from restart; nine incomplete requirement streams are covered by ranks 1, 2, 3, 6, 10, 11, 13, 14, and 15. |
| Commercial operations | Five substantive documents | All covered by ranks 4, 5, 7, 8, and 9; shared order/package/return facts must not be built separately for each. |
| Facility operations | Three substantive documents | Ranks 12, 16, and 17 distinguish software from physical metrology/safety/site work. |
| Notifications | One substantive document | Delivered discovery-mail scope is excluded from restart. General push/in-app/event notification features are only named in the [area README](functional-areas/notifications/README.md). |
| Approvals | [Area README](functional-areas/approvals/README.md) names request/routing/delegation/escalation/audit features | No complete workflow/state/permission/API specification or domain implementation; definition-first work, not a highly defined build ticket. Domain-specific release/override records above do not imply a finished general approvals engine. |
| User management | [Area README](functional-areas/user-management/README.md) names authentication/RBAC/profile/organization/session features | No complete identity/hierarchy/authorization specification or implementation; a cross-cutting blocker requiring definition, not an invented ready-to-build authentication project. |
| Runtime scaffolds | [Background worker](../backend/background-worker/README.md), [Android](../clients/android/README.md), [iOS](../clients/ios/README.md), and [shared Kotlin](../common/kotlin/README.md) | Source census found no Java/Kotlin/Swift implementation in these surfaces. READMEs/build scaffolds are not finished applications, but specific worker/mobile feature behavior is not yet defined enough to rank. |
| Architecture/infrastructure | [Architecture](../ARCHITECTURE.md) and [prototype ADR](../docs/architecture/decisions/0002-cnc-prototype-vertical-slice.md) | Durable production data, identity/authorization, and messaging need architecture decisions. The prototype intentionally omitted persistence/auth; do not turn “add a database” or “choose a queue” into a supposedly complete feature specification. |
| Research, position papers, and feedback | [Discovery](discovery/), [position papers](position-papers/), and [feedback handling](feedback/README.md) | Research/strategy and human triage are not unfinished software features merely because questions or prompts remain open. Individual generated responses are untrusted proposals, not automatic requirements; automatic AI dispatch is not approved scope. |
| Separate branches/proposals | Previously discussed object-storage logging proposal #8 is absent from this baseline | It was not silently imported or ranked as branch content. Only work actually present after the requested main synchronization is assessed. |

## Dependency-Aware Execution Guidance

The definition rank is **not an instruction to implement all 17 in order**.

| Lane | Sensible initial sequence | Important boundary |
|------|--------------------------|--------------------|
| Operational evidence | Define shared persistence, attributable identity/access, revision, correction, and replay contracts; rank 1 ledger; ranks 2 and 3 manual care/inspection; package/job-actual integrations as needed. | Resolve cross-cutting architecture/policies and issue bounded implementation prompts. Do not build a new deployable service for every domain. |
| CAM continuation | Rank 6 constraint/feature contracts; typed candidate generation/accessibility and optimizer integration; geometry-following CAM/posting; rank 13 bounded final-NC verification; rank 15 qualified workholding models. | Existing topology/optimizer code is reused. Each declared engineering scope needs its own evidence; planning success is not production release. |
| Commercial records | Rank 4 manual owned benchmarks can proceed independently of full CAM; establish customer/order/access foundations, then share shipment/RMA/acceptance facts across ranks 5, 7, 8, and 9. | Do not invent policy limits or claim external labels/payments from local record updates. |
| Learning and analysis | Collect actuals for rank 11; rank 10 can first record manually approved learning plans; deepen analogs, rank 12 thermal integration, and rank 14 improvement analysis when supporting data/methods exist. | Synthetic simulation output cannot substitute for real economics, quality, temperature, or bottleneck evidence. |
| Facility/safety | Define rank 17 product scope and real layout/hazards; define rank 16 advisory contracts alongside qualified safety engineering. | Physical safety authority, emergency stop/egress, permits, and commissioning remain outside ordinary business software. |

Use the existing [prompt pipeline](prompts/README.md) to turn a selected first
slice into an implementation prompt with its own acceptance cases and unresolved
decisions. Preserve the runtime boundaries in
[ADR 0005](../docs/architecture/decisions/0005-organize-code-by-runtime-boundary.md).
Reassess this snapshot when source, requirements, policies, or qualified inputs
change rather than treating the rank as permanent.

This assessment and its PR publication are tracked by the
[assessment prompt](prompts/deploying/20261031-definition-led-software-backlog.md).
