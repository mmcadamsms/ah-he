# Manufacturing Processes and Quality Questions

## Purpose

Questions about materials, cross-process selection, CNC, finishing, inspection,
assembly, packaging, and delivery readiness. Additive-specific design,
operations, safety, qualification, and economics are maintained in
[Additive Manufacturing](additive-manufacturing.md).

These are discovery questions, not approved requirements. Question IDs are permanent and canonical in this file.

## Materials

### MAT-001 — Which material families and grades are initially supported?

**State:** Exploring

**First-pass answer:** Launch with an exact form-and-condition catalog:
6061-T6 aluminum; 1018 and condition-controlled 4140 steel; 303/304/316
stainless; C360 brass; and selected acetal, nylon, and UHMW grades. Gate
titanium, nickel alloys, hardened/exotic steels, and regulated polymers until
demand and process qualification exist.

**Evidence:**
- [NIST material/process selection][R01] — **Evidence class:** official guidance; selection must join design requirements and manufacturing capability.
- [DoD Manufacturing and Quality Body of Knowledge][R02] — **Evidence class:** official guidance; supports capability- and risk-based material control.

**Conflicting evidence:** Local customers, available stock, and partner
capability may justify a narrower or broader starting list; a supplier catalog
does not prove this shop can process a grade capably.

**Confidence:** Medium.

**Business-specific validation:** Analyze 6–12 months of RFQs, supplier lead
times, scrap, and demonstrated results by grade, temper, form, and thickness.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The direction is sound, but the exact catalog, certificate package, customer-material terms, and stock policy remain material-, customer-, and contract-specific and are not proved by universal public evidence.

### MAT-002 — When may the system recommend a material versus requiring qualified-human selection?

**State:** Exploring

**First-pass answer:** Software may rank documented alternatives for review,
but qualified-human approval is mandatory for safety-, fatigue-, corrosion-,
temperature-, or regulation-critical uses, ambiguous specifications, and every
substitution. Automation may later handle bounded commodity cases only after
rules and outcomes are validated.

**Evidence:**
- [NIST material/process selection][R01] — **Evidence class:** official guidance; material selection is a multi-constraint engineering decision.
- [NASA materials and processes standard][R06] — **Evidence class:** official technical standard; assigns control and approval to qualified authorities.

**Conflicting evidence:** Rule-based selection can be reliable for narrow,
noncritical families, but AI or optimizer recommendations are not evidence of
functional equivalence.

**Confidence:** High.

**Business-specific validation:** A materials/design authority must approve
decision classes and audit at least the first 100 recommendations.

**Second-opinion review:** GPT-5.4 — Agree. The controls-first conclusion is well supported by configuration, materials, and quality-control sources; the remaining uncertainty is commercial implementation detail rather than a flaw in the answer.

### MAT-003 — How are strength, corrosion, temperature, wear, weight, cost, availability, machinability, printability, weldability, and appearance traded off?

**State:** Exploring

**First-pass answer:** Apply hard constraints first—loads, environment, life,
compliance, and joining/process feasibility—then use a documented weighted
trade study for total delivered cost, weight, appearance, supply risk, and
manufacturing performance. Price must never compensate for failing a hard
requirement.

**Evidence:**
- [NIST material/process selection][R01] — **Evidence class:** official guidance; describes integrated multi-criteria selection.
- [NASA materials and processes standard][R06] — **Evidence class:** official technical standard; prioritizes verified environmental and performance requirements.

**Conflicting evidence:** Weightings and acceptable risk are product- and
customer-specific, so no universal material score is credible.

**Confidence:** High on method.

**Business-specific validation:** Back-test representative parts and run
sensitivity analysis on weights, prices, availability, and uncertain property
inputs.

**Second-opinion review:** GPT-5.4 — Agree. The controls-first conclusion is well supported by configuration, materials, and quality-control sources; the remaining uncertainty is commercial implementation detail rather than a flaw in the answer.

### MAT-004 — What certifications or mill test reports can customers request?

**State:** Exploring

**First-pass answer:** Offer supplier certificates of conformance, heat/lot-
linked mill test reports, chemical and mechanical test results, origin/melt
declarations, and specification compliance statements where available.
Independent verification should be quoted when contract or consequence
requires it.

**Evidence:**
- [ISO 9001 quality-management requirements][R04] — **Evidence class:** consensus standard; requires control of externally provided products and retained evidence.
- [ISO/IEC 17025 laboratory competence][R25] — **Evidence class:** consensus standard; supports competent independent testing when needed.

**Conflicting evidence:** “MTR” content and authenticity differ by material
specification and supply channel; a distributor CoC is not always a mill
report.

**Confidence:** High.

**Business-specific validation:** Quality and legal staff should create a
certificate matrix by material, customer, and sector and test retrieval on
sample orders.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The direction is sound, but the exact catalog, certificate package, customer-material terms, and stock policy remain material-, customer-, and contract-specific and are not proved by universal public evidence.

### MAT-005 — How is material substitution approved?

**State:** Exploring

**First-pass answer:** Do not substitute without a documented equivalence
review and written design-authority/customer approval. Release the change as a
deviation or controlled engineering revision before material is cut, and
retain the rationale and affected serials/lots.

**Evidence:**
- [ISO 9001][R04] — **Evidence class:** consensus standard; requires controlled changes and conformity to requirements.
- [ISO 10007 configuration management][R40] — **Evidence class:** consensus guidance; supports traceable configuration and change control.

**Conflicting evidence:** Some customer terms prohibit all substitution, while
others preapprove exact alternates; commercial similarity is not engineering
equivalence.

**Confidence:** High.

**Business-specific validation:** Counsel and quality must approve the
workflow, followed by a tabletop substitution exercise across sales,
purchasing, planning, and inspection.

**Second-opinion review:** GPT-5.4 — Agree. The controls-first conclusion is well supported by configuration, materials, and quality-control sources; the remaining uncertainty is commercial implementation detail rather than a flaw in the answer.

### MAT-006 — How are lot, heat, batch, and supplier traceability retained?

**State:** Exploring

**First-pass answer:** Assign internal lot identities linked to supplier, PO,
specification/revision, heat or batch, certificates, receiving results,
travelers, finished parts, and remnants. Preserve identity whenever stock is
split and support forward and backward retrieval.

**Evidence:**
- [ISO 9001][R04] — **Evidence class:** consensus standard; requires
identification, traceability where required, and retained records.
- [AS9100D][R05] — **Evidence class:** sector consensus standard; adds stronger product-safety, counterfeit, and traceability controls.

**Conflicting evidence:** Required depth and retention vary sharply between
commercial and regulated work; excessive traceability can add cost without a
customer or risk basis.

**Confidence:** High.

**Business-specific validation:** Perform mock recalls from one finished serial
and one stock remnant back to supplier evidence and forward to all affected
parts.

**Second-opinion review:** GPT-5.4 — Agree. The controls-first conclusion is well supported by configuration, materials, and quality-control sources; the remaining uncertainty is commercial implementation detail rather than a flaw in the answer.

### MAT-007 — Can customer-supplied material be accepted, and who bears the risk if it is unsuitable?

**State:** Exploring

**First-pass answer:** Accept customer material only under written identity,
certificate, condition, inspection, extra-stock, and liability terms. The shop
should own preservation, handling, and workmanship, but not undiscoverable
latent material defects unless it explicitly agreed to validate them.

**Evidence:**
- [ISO 9001][R04] — **Evidence class:** consensus standard; requires control and protection of customer property.
- [NASA materials and processes standard][R06] — **Evidence class:** official technical standard; supports material identity and suitability controls.

**Conflicting evidence:** Bailment, warranty, customer contract, and regulated-
sector rules can allocate risk differently.

**Confidence:** Medium because legal terms are jurisdiction- and contract-
specific.

**Business-specific validation:** Counsel must approve terms; receiving and
manufacturing should inspect and trial-machine representative customer stock.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The direction is sound, but the exact catalog, certificate package, customer-material terms, and stock policy remain material-, customer-, and contract-specific and are not proved by universal public evidence.

### MAT-008 — How is stock availability incorporated before the design is finalized?

**State:** Exploring

**First-pass answer:** Check standard forms/sizes, supplier availability,
minimum order, lead time, certificate availability, yield, and price during
quoting and again before design freeze. Reserve stock for accepted orders and
surface alternates as explicit choices, never silent substitutions.

**Evidence:**
- [NIST material/process selection][R01] — **Evidence class:** official guidance; availability and manufacturability belong in early design selection.
- [DoD Manufacturing and Quality Body of Knowledge][R02] — **Evidence class:** official guidance; treats supply and production readiness as design inputs.

**Conflicting evidence:** Distributor inventory is volatile and may disappear
unless contractually reserved; designing only to today's stock can also create
long-term lock-in.

**Confidence:** Medium.

**Business-specific validation:** Compare quoted versus actual availability,
price, and redesign/expedite incidents for the first 50 orders.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The direction is sound, but the exact catalog, certificate package, customer-material terms, and stock policy remain material-, customer-, and contract-specific and are not proved by universal public evidence.

## Manufacturing Process Selection

### MFG-001 — How do we choose between CNC machining, additive manufacturing, fabrication, forming, casting, molding, or a hybrid process?

**State:** Exploring

**First-pass answer:** Use a constraint-first matrix for material, geometry,
tolerance, finish, quantity, lead time, qualification, and life-cycle cost.
Shortlist feasible process chains, then compare risk-adjusted delivered
economics; do not choose from nominal machine capability or unit price alone.

**Evidence:**
- [NIST material/process selection][R01] — **Evidence class:** official guidance; directly addresses integrated process and material selection.
- [DoD Manufacturing and Quality Body of Knowledge][R02] — **Evidence class:** official guidance; supports production-readiness and risk evaluation.

**Conflicting evidence:** No process is globally cheapest; tooling,
qualification, postprocessing, and volume can reverse the ranking.

**Confidence:** High on the framework.

**Business-specific validation:** Cross-quote at least 20 representative parts
through two or more feasible process routes and reconcile actual outcomes.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The framework is appropriate, but public sources support a conservative decision process more than any universal in-house scope or envelope; exact process choice still depends on machine, material, quantity, customer approval, and demonstrated capability.

### MFG-002 — Which processes are available in-house versus through qualified partners?

**State:** Exploring

**First-pass answer:** Maintain a revision-controlled internal capability matrix
and approved-partner list. Internal entries require machine/operator/process
evidence; partner entries require audits or questionnaires, certificates,
sample/first-article results, delivery history, nonconformance control, and
change notification.

**Evidence:**
- [ISO 9001][R04] — **Evidence class:** consensus standard; requires control and monitoring of external providers.
- [Nadcap special-process accreditation][R34] — **Evidence class:** industry accreditation; provides process-specific supplier evidence but not part-specific proof.

**Conflicting evidence:** Certification helps screen suppliers but does not
demonstrate capability for a particular geometry, tolerance, or schedule.

**Confidence:** High.

**Business-specific validation:** Score and periodically requalify each
provider; witness or inspect critical trial work before production release.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The framework is appropriate, but public sources support a conservative decision process more than any universal in-house scope or envelope; exact process choice still depends on machine, material, quantity, customer approval, and demonstrated capability.

### MFG-003 — What capabilities define the initial manufacturing envelope: size, weight, materials, tolerances, geometry, and quantity?

**State:** Exploring

**First-pass answer:** Define the initial envelope from proven parts and
process-capability evidence by exact machine, material, size/weight, geometry,
tolerance, surface, quantity band, and supervision mode. Nominal travel,
accuracy, or a one-off successful part is not the production envelope.

**Evidence:**
- [ISO 230-2 machine-tool positioning tests][R21] — **Evidence class:** consensus standard; provides measured accuracy and repeatability methods.
- [DoD Manufacturing Readiness Level Deskbook][R03] — **Evidence class:** official/practitioner guidance; requires demonstrated process, quality, and capacity readiness.

**Conflicting evidence:** OEM maxima describe equipment boundaries, while
tooling, fixturing, thermal behavior, inspection, and operator access usually
create tighter usable limits.

**Confidence:** High on method.

**Business-specific validation:** Build representative artifacts and conduct
capability studies across the intended envelope.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The framework is appropriate, but public sources support a conservative decision process more than any universal in-house scope or envelope; exact process choice still depends on machine, material, quantity, customer approval, and demonstrated capability.

### MFG-004 — When should manufacturability feedback change the design?

**State:** Exploring

**First-pass answer:** Before design freeze, change the design when doing so
removes safety/quality risk or materially improves cost, lead time, robustness,
inspection, or supply without compromising intent. After freeze, use controlled
engineering/customer change approval.

**Evidence:**
- [NIST material/process selection][R01] — **Evidence class:** official guidance; supports early concurrent design and manufacturing decisions.
- [ISO 10007 configuration management][R40] — **Evidence class:** consensus guidance; supports controlled post-baseline changes.

**Conflicting evidence:** Beneficial DFM changes may be uneconomic after
certification or tooling; the original design authority retains functional
responsibility.

**Confidence:** High.

**Business-specific validation:** Track proposed DFM changes, acceptance rate,
verified savings, lead-time change, and subsequent defects.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The framework is appropriate, but public sources support a conservative decision process more than any universal in-house scope or envelope; exact process choice still depends on machine, material, quantity, customer approval, and demonstrated capability.

### MFG-005 — How do we present manufacturing alternatives to the customer without implying equivalent performance?

**State:** Exploring

**First-pass answer:** Present alternatives as separate offers with explicit
changes to material, process, tolerance, surface, validation, lifetime,
delivery, and price. State that manufacturability and dimensional similarity do
not establish functional equivalence; customer/design approval is required.

**Evidence:**
- [ASME Y14.5 dimensioning and tolerancing][R23] — **Evidence class:** consensus standard; supports explicit definition of functional geometry.
- [NASA materials and processes standard][R06] — **Evidence class:** official technical standard; links process/material choice to performance and approval.

**Conflicting evidence:** Price-only comparisons are simpler for customers but
can conceal changes in fatigue, corrosion, appearance, or qualification.

**Confidence:** High.

**Business-specific validation:** Have engineering and counsel review quote
language and test customer comprehension on representative alternatives.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The framework is appropriate, but public sources support a conservative decision process more than any universal in-house scope or envelope; exact process choice still depends on machine, material, quantity, customer approval, and demonstrated capability.

### MFG-006 — What level of human review is mandatory before releasing work to a physical machine?

**State:** Exploring

**First-pass answer:** Initially require qualified-human release for every
physical job: manufacturing/program review, operator setup confirmation, and
quality approval for critical characteristics or first articles. Generative
software or AI must remain advisory and cannot directly release NC or robot
motion.

**Evidence:**
- [NIMS CNC milling performance standard][R22] — **Evidence class:** industry performance standard; defines competent programming, setup, and operation tasks.
- [NIST AI Risk Management Framework][R17] — **Evidence class:** official risk guidance; supports human governance and validation, not autonomous release.
- [OSHA machine guarding][R09] — **Evidence class:** primary regulation; physical risk remains regardless of software origin.

**Conflicting evidence:** Mature, validated repeat jobs may later use controlled
electronic release, but prototype AI demonstrations do not prove safe,
dependable autonomous production.

**Confidence:** High.

**Business-specific validation:** Define competency and release checklists and
audit the first 100 physical releases and all near misses.

**Second-opinion review:** GPT-5.4 — Agree. The answer correctly treats release, configuration, datum/control transfer, and safe-state behavior as governed processes rather than software claims, which is consistent with the cited standards and readiness guidance.

### MFG-007 — How are prototype, bridge-production, and production processes distinguished?

**State:** Exploring

**First-pass answer:** A prototype explores design or process and may lack
capability evidence; bridge production uses controlled temporary tooling or
suppliers while the durable route matures; production requires stable
configuration, qualified processes/providers, capacity, repeatable acceptance,
and controlled changes. Quantity alone does not determine the label.

**Evidence:**
- [DoD Manufacturing Readiness Level Deskbook][R03] — **Evidence class:** official/practitioner guidance; defines evidence expected before production.
- [SAE AS9102 first-article inspection][R28] — **Evidence class:** sector consensus standard; supports verification of a production process and configuration.

**Conflicting evidence:** Low-volume products can be fully production-ready,
while large prototype batches can remain unqualified.

**Confidence:** High.

**Business-specific validation:** Establish stage exit criteria and conduct
documented readiness reviews on initial part families.

**Second-opinion review:** GPT-5.4 — Agree. The maturity split is well framed: the cited readiness and first-article sources support treating “production” as a demonstrated control state, not a volume label, and the answer appropriately resists equating bigger prototype batches with qualified production.

### MFG-008 — How are one-time tooling and fixture costs represented?

**State:** Exploring

**First-pass answer:** Quote design, programming, fixtures, tooling, gauges, and
qualification as visible nonrecurring engineering. State amortization quantity,
ownership, storage, maintenance, replacement, reuse rights, cancellation, and
reorder treatment.

**Evidence:**
- [DoD Manufacturing and Quality Body of Knowledge][R02] — **Evidence class:** official guidance; recognizes tooling and production-readiness costs.
- [DoD Manufacturing Readiness Level Deskbook][R03] — **Evidence class:** official/practitioner guidance; requires resource and tooling readiness.

**Conflicting evidence:** Embedding NRE in unit price may improve sales
simplicity but obscures reorder economics and stranded-cost risk.

**Confidence:** High.

**Business-specific validation:** Finance must reconcile quoted NRE with actual
engineering, procurement, and shop hours on the first 20 jobs.

**Second-opinion review:** GPT-5.4 — Agree. The answer appropriately separates recurring price from NRE and ownership terms; that is consistent with the cited readiness and cost-accounting logic even though the exact commercial presentation remains business-specific.

### MFG-009 — When is a sacrificial feature or witness mark acceptable?

**State:** Exploring

**First-pass answer:** Allow sacrificial tabs, datum pads, coupons, or witness
marks only in identified nonfunctional zones with design/customer approval,
defined removal or retention criteria, and inspection proving no functional or
cosmetic harm.

**Evidence:**
- [ASME Y14.5][R23] — **Evidence class:** consensus standard; supports explicit feature and datum definition.
- [ISO 9001][R04] — **Evidence class:** consensus standard; requires conformity to approved requirements and controlled changes.

**Conflicting evidence:** Some casting/forging datum targets or test-coupon
remnants are intentional deliverable features, while unapproved marks are
nonconformances.

**Confidence:** High.

**Business-specific validation:** Require annotated model/drawing approval and
first-article photographs or measurements.

**Second-opinion review:** GPT-5.4 — Agree. The answer correctly requires approved nonfunctional zones, explicit removal/retention rules, and verification against cosmetic or functional harm; that is the right interpretation of the cited drawing and change-control sources.

### MFG-010 — Which customer surfaces may contact clamps, jaws, mandrels, supports, or fixtures?

**State:** Exploring

**First-pass answer:** Classify surfaces as functional, sealing, datum,
cosmetic, protected, or unrestricted. The setup plan must record every contact,
including pad/jaw material, force limits, protection, and permitted witness
condition.

**Evidence:**
- [ASME Y14.5][R23] — **Evidence class:** consensus standard; supports functional datum and surface definition.
- [NIMS CNC milling performance standard][R22] — **Evidence class:** industry performance standard; treats safe, controlled workholding as a setup responsibility.

**Conflicting evidence:** Drawings often omit cosmetic expectations; an
unrestricted surface technically may still be rejected by the customer.

**Confidence:** High.

**Business-specific validation:** Obtain customer-approved surface zones or
limit samples and inspect first-off contact marks.

**Second-opinion review:** GPT-5.4 — Agree. The answer correctly pushes contact permission into explicit surface classification and setup control rather than planner inference, which is consistent with the cited datum/workholding sources and with common causes of customer rejection.

### MFG-011 — What sequence of physical capabilities and equipment should the business acquire?

**State:** Exploring

**First-pass answer:** Acquire receiving/material control, calibrated
metrology, deburr/clean, and flexible core machining or fabrication support
first. Outsource low-utilization, high-hazard, and special processes; add
dedicated production and automation only after repeat demand, delivered
capability, staffing, and maintenance evidence.

**Evidence:**
- [DoD Manufacturing Readiness Level Deskbook][R03] — **Evidence class:** official/practitioner guidance; supports capability, quality, supplier, and resource readiness.
- [NIST MEP success stories][R47] — **Evidence class:** observed but selected operating cases; shows staged capability improvements in real manufacturers.

**Conflicting evidence:** Strategic lead-time, IP, or supplier fragility can
justify earlier vertical integration despite weak short-term utilization.

**Confidence:** Medium.

**Business-specific validation:** Build make/buy scenarios from actual RFQs,
supplier performance, capital, staffing, and conservative utilization.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The framework is appropriate, but public sources support a conservative decision process more than any universal in-house scope or envelope; exact process choice still depends on machine, material, quantity, customer approval, and demonstrated capability.

### MFG-012 — What year-two or year-three facility layout best supports safe flow, automation, visibility, and expansion?

**State:** Exploring

**First-pass answer:** Plan clear receiving/quarantine, stock, cutting,
machining/fabrication, deburr/clean, inspection, pack, and shipping flow;
segregate hot, dirty, chemical, and high-noise work; protect metrology; provide
safe aisles and robot exclusion zones; and reserve utilities and floor space
for expansion. Cells may outperform a strict line for high mix.

**Evidence:**
- [OSHA machine guarding][R09] — **Evidence class:** primary regulation; constrains equipment access and cell layout.
- [OSHA hazardous-energy control][R10] — **Evidence class:** primary regulation; requires service and isolation access.
- [OSHA robotics resources][R11] — **Evidence class:** official safety guidance; supports safeguarded robot-cell planning.

**Conflicting evidence:** One-way flow reduces handling for stable products,
while high-mix work may need functional cells and shared resources.

**Confidence:** Medium.

**Business-specific validation:** Use spaghetti diagrams, material-handling
trials, EHS review, utility loads, and multiple growth/product-mix scenarios.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The framework is appropriate, but public sources support a conservative decision process more than any universal in-house scope or envelope; exact process choice still depends on machine, material, quantity, customer approval, and demonstrated capability.

### MFG-013 — Can older machinery plus add-on automation create competitive unit economics?

**State:** Exploring

**First-pass answer:** Yes, for stable bounded work when the base machine is
mechanically capable, safe, supportable, and already economically owned.
Compare fully burdened acquisition, repair, retrofit, integration, downtime,
labor, quality, throughput, energy, financing, support, and residual value with
new equipment and outsourcing; also measure operation with automation disabled.

**Evidence:**
- [ISO 22400 manufacturing KPIs][R15] — **Evidence class:** consensus standard; provides comparable production-performance measures.
- [NIST MEP success stories][R47] — **Evidence class:** observed but selected operating cases; documents delivered modernization results.
- [ISO 230-2 machine-tool tests][R21] — **Evidence class:** consensus standard; provides evidence of base-machine positioning capability.

**Conflicting evidence:** Vendor cases can show real gains but often omit
integration labor, maintenance tails, financing, and selection bias. A retrofit
cannot cure a worn or unsupported machine economically.

**Confidence:** Medium.

**Business-specific validation:** Run one-machine pilot with predeclared total
cost, uptime, quality, throughput, safety, and payback gates.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The retrofit and automation logic is sensible, but the evidence is mostly standards, OEM material, and selected modernization cases rather than broad production statistics; economics and safe unattended operation remain machine-, maintenance-, and product-mix-specific.

### MFG-014 — Which add-on automation techniques are appropriate for each machine and process?

**State:** Exploring

**First-pass answer:** Match automation to a measured constraint. Start with
status collection, probing/tool measurement, workholding, chip/coolant control,
and part-presence checks; use bar feeders or pallet queues for regular work;
add robot/cobot tending only after presentation, gripping, guarding, cycle,
quality, and fault recovery are stable.

**Evidence:**
- [OSHA robotics resources][R11] — **Evidence class:** official safety guidance; requires application-specific hazard assessment.
- [ISO 10218 robot safety][R12] — **Evidence class:** consensus standard; governs industrial robot integration.
- [Renishaw probing integration][R20] — **Evidence class:** OEM technical guidance; describes delivered probing functions but not universal ROI.

**Conflicting evidence:** Flexible-robot demonstrations may handle varied parts
in a lab, but high-mix production adds presentation, gripper, inspection, and
recovery failures.

**Confidence:** Medium.

**Business-specific validation:** Time-study each candidate task, complete
PFMEA/risk assessment, and pilot representative products including deliberate
faults.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The retrofit and automation logic is sensible, but the evidence is mostly standards, OEM material, and selected modernization cases rather than broad production statistics; economics and safe unattended operation remain machine-, maintenance-, and product-mix-specific.

### MFG-015 — What standard automation-cell architecture can span heterogeneous legacy machines?

**State:** Exploring

**First-pass answer:** Standardize mechanical docking, utility connection,
safety-rated controls, guarding, interlocks, I/O handshakes, job/part identity,
OPC UA/MTConnect data, workholding interfaces, material presentation, alarm
taxonomy, manual mode, recovery states, and cybersecurity. Keep machine-specific
adapters behind those common interfaces.

**Evidence:**
- [OPC UA architecture][R13] — **Evidence class:** consensus industrial interoperability framework; supports secure common data interfaces.
- [MTConnect standard][R14] — **Evidence class:** manufacturing data standard; normalizes equipment observations.
- [ISO 10218][R12] — **Evidence class:** consensus robot-safety standard; governs industrial-robot integration and supports validated safety-related controls.

**Conflicting evidence:** Legacy machines may expose insufficient or unsafe
interfaces, so a common architecture reduces—not eliminates—custom engineering.

**Confidence:** Medium.

**Business-specific validation:** Implement the architecture on two dissimilar
machines and measure reused versus custom hardware, software, and recovery work.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The retrofit and automation logic is sensible, but the evidence is mostly standards, OEM material, and selected modernization cases rather than broad production statistics; economics and safe unattended operation remain machine-, maintenance-, and product-mix-specific.

### MFG-016 — Which operator tasks should automation reduce, and which should remain human?

**State:** Exploring

**First-pass answer:** Automate repetitive, deterministic loading/unloading,
monitoring, recording, and material movement where risk and economics support
it. Retain qualified people for setup, isolation, maintenance, ambiguous
inspection, quality release, diagnosis, approval, and exception recovery until
those tasks are demonstrably bounded and validated.

**Evidence:**
- [OSHA hazardous-energy control][R10] — **Evidence class:** primary regulation; preserves qualified human roles in servicing and isolation.
- [NIST AI Risk Management Framework][R17] — **Evidence class:** official risk guidance; supports governed human oversight of uncertain automation.
- [ISO 10218][R12] — **Evidence class:** consensus robot-safety standard; requires validated safety functions within the robot integration.

**Conflicting evidence:** Automated inspection can outperform people on a
qualified, stable task, but prototype vision/AI accuracy does not establish
reliable quality release under drift and novel defects.

**Confidence:** High.

**Business-specific validation:** Complete task risk and time studies; monitor
overrides, misses, false rejects, near misses, and recovery burden.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The retrofit and automation logic is sensible, but the evidence is mostly standards, OEM material, and selected modernization cases rather than broad production statistics; economics and safe unattended operation remain machine-, maintenance-, and product-mix-specific.

### MFG-017 — What is the safest and most economical order for retrofitting automation?

**State:** Exploring

**First-pass answer:** Use the proposed progression: measure the manual work;
stabilize machine and process; add monitoring; improve workholding, probing,
chips, coolant, and tool life; automate presentation; automate tending; add
scheduling/escalation; and consider unattended operation only after demonstrated
capability and recovery.

**Evidence:**
- [DoD Manufacturing and Quality Body of Knowledge][R02] — **Evidence class:** official guidance; supports staged risk reduction and process control.
- [ISO 22400 KPIs][R15] — **Evidence class:** consensus standard; supports before/after evidence at each stage.
- [OSHA machine guarding][R09] — **Evidence class:** primary regulation; safety controls cannot be deferred to later stages.

**Conflicting evidence:** A proven turnkey cell may combine stages, but skipping
baseline, capability, and fault evidence makes ROI and safety claims unreliable.

**Confidence:** High.

**Business-specific validation:** Use stage gates with safety, capability,
maintenance, and recovery acceptance criteria before each expansion.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The retrofit and automation logic is sensible, but the evidence is mostly standards, OEM material, and selected modernization cases rather than broad production statistics; economics and safe unattended operation remain machine-, maintenance-, and product-mix-specific.

### MFG-018 — What must be true before a legacy machine can run with reduced supervision?

**State:** Exploring

**First-pass answer:** Require capable machining, bounded tool life, verified
clamping and part presence, probing where needed, reliable chip/coolant
management, fire assessment, interlocked guarding, safe stop, alarm escalation,
inspection, loss-of-communication behavior, and tested recovery/restart
authorization. Remote notification alone is insufficient.

**Evidence:**
- [OSHA machine guarding][R09] — **Evidence class:** primary regulation; requires protection from machine hazards.
- [OSHA hazardous-energy control][R10] — **Evidence class:** primary regulation; governs intervention and restart after servicing.
- [ISO 10218][R12] — **Evidence class:** consensus robot-safety standard; supports validated safe states and safeguarded robot integration.

**Conflicting evidence:** Unattended success on selected jobs does not prove the
whole envelope safe; chips, wear, fire, and recovery may be strongly
part-specific.

**Confidence:** High.

**Business-specific validation:** Perform FMEA and deliberate fault-injection
trials before any reduced-supervision shift.

**Second-opinion review:** GPT-5.4 — Agree. The answer correctly treats release, configuration, datum/control transfer, and safe-state behavior as governed processes rather than software claims, which is consistent with the cited standards and readiness guidance.

### MFG-019 — How should retrofit automation performance and ROI be measured?

**State:** Exploring

**First-pass answer:** Baseline and report attendance minutes per accepted part,
setup, utilization, good throughput, scrap/rework, downtime and MTTR, unattended
productive time, maintenance labor, escapes, safety events, total integration
cost, cash payback/return, and output with automation disabled. Use total-system
delivery and WIP alongside cell metrics.

**Evidence:**
- [ISO 22400 KPIs][R15] — **Evidence class:** consensus standard; defines manufacturing-operations performance measures.
- [DOE Better Plants resources][R48] — **Evidence class:** observed government-supported cases and tools; supports measured operating improvements.
- [NIST MEP success stories][R47] — **Evidence class:** delivered but selected cases; useful evidence with selection-bias limitations.

**Conflicting evidence:** OEE or spindle utilization can rise while inventory,
delivery, maintenance, or downstream congestion worsens.

**Confidence:** High.

**Business-specific validation:** Finance and operations must freeze metric
definitions and collect a comparable 4–8 week baseline and review period.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The retrofit and automation logic is sensible, but the evidence is mostly standards, OEM material, and selected modernization cases rather than broad production statistics; economics and safe unattended operation remain machine-, maintenance-, and product-mix-specific.

### MFG-020 — How does a retrofitted cell degrade safely when automation fails?

**State:** Exploring

**First-pass answer:** On failure, command a risk-assessed safe stop, contain or
remove hazardous energy as required, retain the part where safer, preserve job
state, alarm locally/remotely, prohibit automatic restart, and provide a
qualified manual fallback and recovery checklist. Explicitly define power,
network, sensor, robot, feeder, coolant, tool, and spindle faults.

**Evidence:**
- [OSHA hazardous-energy control][R10] — **Evidence class:** primary regulation; governs safe intervention and restart.
- [ISO 10218][R12] — **Evidence class:** consensus robot-safety standard; requires validated safety-related behavior in the robot cell.
- [OPC UA architecture][R13] — **Evidence class:** industrial framework; supports state and communication design but is not a safety system by itself.

**Conflicting evidence:** The safest part, spindle, and robot state is
application-specific; preserving production state must never override hazard
control.

**Confidence:** High.

**Business-specific validation:** Inject every enumerated failure and require
documented safe state, evidence retention, manual recovery, and authorized
restart.

**Second-opinion review:** GPT-5.4 — Agree. The answer correctly treats release, configuration, datum/control transfer, and safe-state behavior as governed processes rather than software claims, which is consistent with the cited standards and readiness guidance.

### MFG-021 — How should low-cost technology identify manufacturing bottlenecks?

**State:** Exploring

**First-pass answer:** Start with synchronized timestamps, machine states,
queues/WIP, alarms, operator observations, inspection, scrap, maintenance, and
energy. Label a bottleneck only when repeated demand-adjusted evidence shows it
constrains total throughput or delivery. Add cameras/AI only where simpler
signals cannot answer the question and validate drift and false conclusions.

**Evidence:**
- [MTConnect standard][R14] — **Evidence class:** manufacturing data standard; supports machine-state collection.
- [ISO 22400 KPIs][R15] — **Evidence class:** consensus standard; supports consistent operations measures.
- [NIST smart-manufacturing modeling][R16] — **Evidence class:** prototype research; useful for methods, not proof of dependable shop-floor diagnosis.

**Conflicting evidence:** AI demonstrations can detect patterns but do not
prove causation or reliable bottleneck identification across product-mix
changes.

**Confidence:** High.

**Business-specific validation:** Blindly compare the low-cost system with
manual time study and queue analysis over several representative mixes.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The retrofit and automation logic is sensible, but the evidence is mostly standards, OEM material, and selected modernization cases rather than broad production statistics; economics and safe unattended operation remain machine-, maintenance-, and product-mix-specific.

### MFG-022 — How should candidate automation investments be ranked by ROI?

**State:** Exploring

**First-pass answer:** Rank investments by risk-adjusted NPV and payback plus
throughput at the system constraint, attended labor, setup, quality/scrap,
safety, lead time, reliability, maintenance, flexibility, integration,
software, useful life, utilization, financing, and downside demand. Use
demonstrated cycle and yield data, not brochure rates.

**Evidence:**
- [GAO Cost Estimating Guide][R49] — **Evidence class:** primary government methodology; requires documented assumptions, sensitivity, and risk.
- [ISO 22400 KPIs][R15] — **Evidence class:** consensus standard; supports comparable operating inputs.
- [NIST MEP success stories][R47] — **Evidence class:** observed but selected cases; shows potential delivery while retaining selection-bias limits.

**Conflicting evidence:** Short payback can favor inflexible local automation
that worsens strategic flexibility or moves the bottleneck.

**Confidence:** Medium.

**Business-specific validation:** Finance must approve optimistic/base/downside
cases and reconcile every approved project against actual results.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The retrofit and automation logic is sensible, but the evidence is mostly standards, OEM material, and selected modernization cases rather than broad production statistics; economics and safe unattended operation remain machine-, maintenance-, and product-mix-specific.

### MFG-023 — How do we verify that an automation improvement removed the real bottleneck?

**State:** Exploring

**First-pass answer:** Pre-register the baseline, product mix, metric formulas,
review period, and confidence/decision bounds. After deployment, verify total
throughput, lead time, WIP, quality, safety, labor, employee observations, and
queue movement, then identify the new constraint. A faster local cycle alone is
not proof.

**Evidence:**
- [ISO 22400 KPIs][R15] — **Evidence class:** consensus standard; supports consistent before/after measures.
- [NIST Technical Note 1297][R27] — **Evidence class:** official metrology guidance; supports uncertainty-aware conclusions.
- [DOE Better Plants][R48] — **Evidence class:** observed government-supported practice; emphasizes measured operating results.

**Conflicting evidence:** Demand and product-mix changes can swamp the effect,
so unmatched calendar periods may falsely credit or discredit automation.

**Confidence:** High.

**Business-specific validation:** Use matched periods or controlled trials and
require independent operations and finance sign-off.

**Second-opinion review:** GPT-5.4 — Agree. The before/after verification logic is sound because it checks whole-system throughput, WIP, and queue movement instead of local cycle-time anecdotes, and the cited KPI and uncertainty sources support that discipline.

### MFG-024 — How do we prevent local optimization from making total shop flow worse?

**State:** Exploring

**First-pass answer:** Approve changes against end-to-end lead time, on-time
delivery, WIP, inventory/cash, downstream queues, handling, inspection,
maintenance, scrap, and delivery variability—not local cycle time alone.
Throttle or rebalance a cell if its faster output only moves congestion.

**Evidence:**
- [ISO 22400 KPIs][R15] — **Evidence class:** consensus standard; provides plant and operations measures beyond one machine.
- [DoD Manufacturing and Quality Body of Knowledge][R02] — **Evidence class:** official guidance; supports system-level production planning and risk control.

**Conflicting evidence:** Local capacity is valuable when the station is the
current or imminent system constraint; otherwise it may increase WIP.

**Confidence:** High.

**Business-specific validation:** Map the value stream before and after and
reject projects that do not improve a predeclared total-system outcome.

**Second-opinion review:** GPT-5.4 — Agree. The answer correctly evaluates automation against end-to-end flow rather than isolated machine speed, and the cited KPI/system-planning sources support that system-constraint framing.

### MFG-025 — How are printed, cast, forged, welded, repaired, or otherwise near-net workpieces registered, probed, and represented as starting stock for downstream machining?

**State:** Exploring

**First-pass answer:** Represent starting stock as nominal geometry plus
measured deviation and uncertainty. Establish traceable datum targets, scan or
probe registration, protected machining allowance, and a remaining-stock model
before toolpath release; use adaptive machining when variation exceeds a fixed
nominal setup.

**Evidence:**
- [Renishaw probing integration][R20] — **Evidence class:** OEM technical guidance; describes in-machine registration and process control.
- [ASME Y14.5][R23] — **Evidence class:** consensus standard; provides datum and geometric-definition principles.
- [NIST Technical Note 1297][R27] — **Evidence class:** official metrology guidance; supports accounting for measurement uncertainty.

**Conflicting evidence:** A scan or probe map can capture geometry but not
necessarily hidden defects, residual stress, or movement after material
removal.

**Confidence:** Medium.

**Business-specific validation:** Trial worst-case stock variation and verify
registration, collision margin, remaining allowance, and final inspection.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The registration and remaining-stock model are technically sound, but OEM probing guidance and datum standards do not prove robustness for every hybrid route; hidden defects, residual stress, and post-cut movement still require route-specific validation.

### MFG-026 — How are allowances, datum strategy, distortion, process ownership, inspection, and traceability transferred across additive, subtractive, forming, welding, heat treatment, and finishing steps?

**State:** Exploring

**First-pass answer:** Use one cross-process traveler/digital thread recording
part configuration, datum handoff, allowance consumed and remaining,
distortion/thermal state, process owner, certificates, serial/lot identity,
nonconformances, and inspection status at every transition. Downstream release
requires acceptance of the incoming state.

**Evidence:**
- [ISO 10007 configuration management][R40] — **Evidence class:** consensus guidance; supports configuration continuity across organizations.
- [ISO 9001][R04] — **Evidence class:** consensus standard; requires controlled production, external providers, identification, and records.
- [ASME Y14.5][R23] — **Evidence class:** consensus standard; supports explicit datum strategy.

**Conflicting evidence:** Supplier systems may preserve records but use
incompatible model, datum, and revision semantics.

**Confidence:** High on governance.

**Business-specific validation:** Trace one hybrid part end to end and conduct
a controlled datum/configuration discrepancy and recovery exercise.

**Second-opinion review:** GPT-5.4 — Agree. The answer correctly treats hybrid process handoff as a configuration-and-datum-control problem with explicit incoming-state acceptance, which is what the cited configuration, quality, and drawing sources support.

## CNC Planning, Workholding, and Tooling

### CNC-001 — Which machine configurations are initially supported?

**State:** Exploring

**First-pass answer:** Initially support named, qualified 3-axis VMCs and
2-axis or simple C-axis lathes for which posts, setup methods, probing, tooling,
and inspection are controlled. Add 4/5-axis, Swiss, mill-turn, grinding, and
EDM machine by machine after kinematics and representative tests are qualified.

**Evidence:**
- [Haas machine-specific manuals][R19] — **Evidence class:** OEM technical manuals; demonstrate why configuration and options are machine-specific.
- [ISO 230-2 machine-tool tests][R21] — **Evidence class:** consensus standard; provides measured positioning evidence.

**Conflicting evidence:** Existing staff, customer demand, and equipment may
support a different initial set; process-family labels alone do not define a
usable machine.

**Confidence:** Medium.

**Business-specific validation:** Analyze demand and qualify representative
parts, posts, setups, and safety behavior on each exact machine.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The planning logic is strong, but capability claims cannot be generalized from OEM manuals or generic competency standards; usable envelope, tooling choices, and unattended limits must be proven on the exact machine, setup, material, and inspection route.

### CNC-002 — How are exact machine options and serial-specific limits verified?

**State:** Exploring

**First-pass answer:** Maintain a serial-specific record from the OEM build
sheet, installed option/license list, travels, spindle and rotary configuration,
probing, coolant, control/firmware, parameters, maintenance condition, and
physical verification. Treat model-level brochures as discovery only.

**Evidence:**
- [Haas machine-specific manuals][R19] — **Evidence class:** OEM technical manuals; document model, control, option, and procedure differences.
- [ISO 230-2][R21] — **Evidence class:** consensus standard; provides physical accuracy/repeatability tests.

**Conflicting evidence:** Repairs, retrofits, wear, software, and option status
can make two machines of the same model materially different.

**Confidence:** High.

**Business-specific validation:** Commission each machine with measured travel,
spindle, positioning, option, parameter-backup, and safety-function checks.

**Second-opinion review:** GPT-5.4 — Agree. The answer stays at the right process-discipline level: serial-specific verification, explicit requirements, bounded automation, and qualified-human release are all well supported by the cited OEM, metrology, and competency sources.

### CNC-003 — How are feasible stock forms and dimensions selected?

**State:** Exploring

**First-pass answer:** Select available standard stock that encloses the part
plus saw, facing, clamping, datum, defect-removal, and distortion allowance.
Compare purchase yield, setup risk, removal time, stability, certificate
availability, and total cost rather than minimizing raw-stock price alone.

**Evidence:**
- [NIST material/process selection][R01] — **Evidence class:** official guidance; joins material form, process feasibility, and cost.
- [NIMS CNC milling standard][R22] — **Evidence class:** industry performance standard; includes stock, setup, and machining planning.

**Conflicting evidence:** Near-net or oversized stock may reduce machining or
distortion risk despite higher purchase cost; standard stock may improve supply.

**Confidence:** High.

**Business-specific validation:** Compare yield, cycle, setup, distortion, and
accepted-part cost for representative stock alternatives.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The planning logic is strong, but capability claims cannot be generalized from OEM manuals or generic competency standards; usable envelope, tooling choices, and unattended limits must be proven on the exact machine, setup, material, and inspection route.

### CNC-004 — How are physical setups minimized without sacrificing safety, rigidity, inspectability, or finish?

**State:** Exploring

**First-pass answer:** Minimize setups only after satisfying safe access,
rigidity, clamping, datum continuity, inspectability, finish protection, chip
evacuation, and handling. Prefer one qualified setup when it reduces stack-up,
but add a setup when it materially improves control or lowers risk.

**Evidence:**
- [NIMS CNC milling standard][R22] — **Evidence class:** industry performance standard; covers setup, workholding, and operation.
- [OSHA machine guarding][R09] — **Evidence class:** primary regulation; safety cannot be traded for fewer setups.
- [ASME Y14.5][R23] — **Evidence class:** consensus standard; supports datum continuity and functional relationships.

**Conflicting evidence:** Fewer setups reduce handling and datum transfer, but
can force weak workholding, long tools, poor access, or uninspectable features.

**Confidence:** High.

**Business-specific validation:** Perform setup FMEA and compare first-article
results, cycle, handling, and risk for plausible plans.

**Second-opinion review:** GPT-5.4 — Agree. The answer stays at the right process-discipline level: serial-specific verification, explicit requirements, bounded automation, and qualified-human release are all well supported by the cited OEM, metrology, and competency sources.

### CNC-005 — Which planar, cylindrical, internal, hole-based, irregular, and sacrificial workholding methods are supported?

**State:** Exploring

**First-pass answer:** Support only documented templates: vises and soft jaws,
strap/toe clamps and fixture plates, collets and chucks, mandrels/arbors and
expanding clamps, approved hole-based locators, vacuum for suitable parts, and
controlled sacrificial tabs or stock. Each use remains part-specific.

**Evidence:**
- [NIMS CNC milling standard][R22] — **Evidence class:** industry performance standard; treats workholding selection and setup as qualified tasks.
- [Haas machine-specific manuals][R19] — **Evidence class:** OEM manuals; provide machine-specific chuck, fixture, and operating limits.

**Conflicting evidence:** A method being available does not prove adequate
holding force, low distortion, or safe failure behavior for a given cut.

**Confidence:** High.

**Business-specific validation:** Document force/slip and failure analysis,
distortion measurement, and worst-case cutting trials for each template.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The planning logic is strong, but capability claims cannot be generalized from OEM manuals or generic competency standards; usable envelope, tooling choices, and unattended limits must be proven on the exact machine, setup, material, and inspection route.

### CNC-006 — How does the planner know which surfaces may remain as-stock and which must be machined?

**State:** Exploring

**First-pass answer:** The released model/drawing must identify final-machined,
as-stock, protected, and allowance surfaces and their acceptance criteria.
Ambiguity becomes a customer/design clarification before quoting or CAM, never
an inference by the planner.

**Evidence:**
- [ASME Y14.5][R23] — **Evidence class:** consensus standard; supports explicit geometric requirements and datums.
- [SAE AS9102 first-article inspection][R28] — **Evidence class:** sector consensus standard; supports characteristic accountability against the design record.

**Conflicting evidence:** Legacy drawings often rely on undocumented shop
conventions; reproducing prior appearance may not meet current functional need.

**Confidence:** High.

**Business-specific validation:** Require model/drawing completeness review and
first-article ballooning on representative jobs.

**Second-opinion review:** GPT-5.4 — Agree. The answer stays at the right process-discipline level: serial-specific verification, explicit requirements, bounded automation, and qualified-human release are all well supported by the cited OEM, metrology, and competency sources.

### CNC-007 — How are required tools compared with available magazine, crib, holder, and insert inventory?

**State:** Exploring

**First-pass answer:** Convert the CAM list into exact assemblies: cutter and
insert, holder, gauge length, stick-out, balance/runout, coolant delivery, and
life. Match and reserve those assemblies against the exact machine magazine and
physical crib inventory before scheduling.

**Evidence:**
- [Haas machine-specific manuals][R19] — **Evidence class:** OEM manuals; document magazine, tool, and control constraints.
- [NIMS CNC milling standard][R22] — **Evidence class:** industry performance standard; covers tool selection, setup, offsets, and inventory readiness.

**Conflicting evidence:** Nominally interchangeable inserts or holders can
alter collision clearance, rigidity, runout, and life.

**Confidence:** High.

**Business-specific validation:** Preset or scan assemblies and reconcile ten
scheduled jobs against physical inventory, substitutions, and actual life.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The planning logic is strong, but capability claims cannot be generalized from OEM manuals or generic competency standards; usable envelope, tooling choices, and unattended limits must be proven on the exact machine, setup, material, and inspection route.

### CNC-008 — When should a special tool or fixture be purchased, manufactured, rented, or avoided by redesign?

**State:** Exploring

**First-pass answer:** Compare purchase, make, rent, and redesign using repeat
volume, setup/cycle savings, safety, quality risk, lead time, useful life,
maintenance, storage, reuse, and opportunity cost. Redesign requires design-
authority/customer approval; a one-job tool can still be justified by safety or
otherwise infeasible geometry.

**Evidence:**
- [NIST material/process selection][R01] — **Evidence class:** official guidance; supports integrated design/process trade studies.
- [DoD Manufacturing and Quality Body of Knowledge][R02] — **Evidence class:** official guidance; treats tooling and production resources as readiness risks.

**Conflicting evidence:** Avoiding special tooling can reduce NRE but increase
cycle time, variability, or operator risk over repeated production.

**Confidence:** Medium.

**Business-specific validation:** Use a finance-approved NRE model and compare
forecast versus actual cost, quality, and reuse after each job.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The planning logic is strong, but capability claims cannot be generalized from OEM manuals or generic competency standards; usable envelope, tooling choices, and unattended limits must be proven on the exact machine, setup, material, and inspection route.

### CNC-009 — What verification is required before generated NC code can leave a simulation-only environment?

**State:** Exploring

**First-pass answer:** Require an approved post and machine configuration,
complete machine/fixture/tool/remaining-stock simulation, syntax and limit
review, independent qualified-human review, setup verification, dry-run or
single-block prove-out, and inspected first article before repeat release.

**Evidence:**
- [NIMS CNC milling standard][R22] — **Evidence class:** industry performance standard; defines competent programming, setup, verification, and operation.
- [Haas machine-specific manuals][R19] — **Evidence class:** OEM manuals; provide exact control, offset, dry-run, and safety procedures.
- [SAE AS9102][R28] — **Evidence class:** sector consensus standard; supports first-article verification of process and configuration.

**Conflicting evidence:** Digital simulation can catch modeled collisions but
cannot prove actual clamping, offsets, tool assembly, stock, parameters, or
machine condition. AI-generated NC remains unproven until the same controls pass.

**Confidence:** High.

**Business-specific validation:** Use a controlled prove-out checklist and
review every collision, near miss, post change, and escaped assumption.

**Second-opinion review:** GPT-5.4 — Agree. The answer stays at the right process-discipline level: serial-specific verification, explicit requirements, bounded automation, and qualified-human release are all well supported by the cited OEM, metrology, and competency sources.

### CNC-010 — How are probing, work offsets, tool offsets, runout, warmup, and inspection represented?

**State:** Exploring

**First-pass answer:** The setup record must define probe qualification,
datum/work-offset source, tool measurement and correction authority, runout
limits, machine warmup and thermal state, in-process checks, acceptance limits,
and linkage to final inspection.

**Evidence:**
- [Renishaw probing integration][R20] — **Evidence class:** OEM technical guidance; describes probing, offset setting, and process control.
- [Haas machine-specific manuals][R19] — **Evidence class:** OEM manuals; provide machine/control-specific warmup and offset procedures.
- [ISO 1 and NIST 20°C reference][R30] — **Evidence class:** consensus standard and official research; establish thermal reference considerations.

**Conflicting evidence:** Probe repeatability and compensation behavior vary by
machine, cycle, stylus, environment, and maintenance; probing is not a
substitute for an uncertainty-aware inspection method.

**Confidence:** High.

**Business-specific validation:** Execute one record from cold start through
accepted first article and correlate probe and independent measurements.

**Second-opinion review:** GPT-5.4 — Agree. The answer stays at the right process-discipline level: serial-specific verification, explicit requirements, bounded automation, and qualified-human release are all well supported by the cited OEM, metrology, and competency sources.

### CNC-011 — Which remaining-stock and collision checks are required?

**State:** Exploring

**First-pass answer:** Check machine envelope and kinematics; spindle/head,
holders, tools, probes, fixtures, clamps, stock, in-process remaining material,
and all rapid/index/transfer moves. Recompute remaining stock after every
operation and account for machine-option and setup differences.

**Evidence:**
- [Haas machine-specific manuals][R19] — **Evidence class:** OEM manuals; define actual machine components, travels, and operating limits.
- [ISO 230-2][R21] — **Evidence class:** consensus standard; supports verified machine motion behavior.
- [NIMS CNC milling standard][R22] — **Evidence class:** industry performance standard; requires safe program and setup verification.

**Conflicting evidence:** CAM models may omit deformation, chips, loose clamps,
operator-added hardware, or an incorrect physical tool assembly.

**Confidence:** High.

**Business-specific validation:** Combine simulation with setup photographs,
tool/fixture scans where practical, and controlled physical prove-out.

**Second-opinion review:** GPT-5.4 — Agree. The answer stays at the right process-discipline level: serial-specific verification, explicit requirements, bounded automation, and qualified-human release are all well supported by the cited OEM, metrology, and competency sources.

### CNC-012 — How are feeds, speeds, tool life, coolant, chip evacuation, and material variability calibrated?

**State:** Exploring

**First-pass answer:** Start from toolmaker/OEM data derated for actual
rigidity, engagement, holder, coolant, and machine condition. Validate through
controlled cuts using load, wear, finish, dimension, chip, temperature, and
failure observations. Maintain life distributions by material lot and exact
tool assembly; do not release from an AI estimate alone.

**Evidence:**
- [NIMS CNC milling standard][R22] — **Evidence class:** industry performance standard; covers cutting-parameter selection and monitoring.
- [ISO 22400 KPIs][R15] — **Evidence class:** consensus standard; supports measured performance and quality tracking.
- [NIST AI Risk Management Framework][R17] — **Evidence class:** official risk guidance; supports validation and monitoring of predictive systems.

**Conflicting evidence:** Published cutting data is a useful starting point but
rarely matches the complete local machine/tool/stock/coolant system.

**Confidence:** High.

**Business-specific validation:** Conduct designed cutting trials and retain
tool-life and quality distributions across lots, operators, and machine states.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The planning logic is strong, but capability claims cannot be generalized from OEM manuals or generic competency standards; usable envelope, tooling choices, and unattended limits must be proven on the exact machine, setup, material, and inspection route.

### CNC-013 — Which turning, mill-turn, Swiss, grinding, EDM, and other subtractive process families belong in the initial or partner capability envelope?

**State:** Exploring

**First-pass answer:** Bring conventional milling and turning in-house first if
demand supports them; use qualified partners initially for Swiss, mill-turn,
5-axis, grinding, EDM, deep-hole, and other specialized processes. Add a family
only after recurring demand, workforce, inspection, maintenance, and
utilization justify qualification.

**Evidence:**
- [NIST material/process selection][R01] — **Evidence class:** official guidance; supports process choice from part requirements and economics.
- [DoD Manufacturing Readiness Level Deskbook][R03] — **Evidence class:** official/practitioner guidance; requires demonstrated capability, capacity, and resources.
- [Nadcap][R34] — **Evidence class:** industry accreditation; supports special-process supplier qualification where applicable.

**Conflicting evidence:** Existing anchor work or rare local supplier capacity
may justify an advanced family earlier.

**Confidence:** Medium.

**Business-specific validation:** Build a three-year demand/capital model and
audit partner capability, capacity, quality, and delivery.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The planning logic is strong, but capability claims cannot be generalized from OEM manuals or generic competency standards; usable envelope, tooling choices, and unattended limits must be proven on the exact machine, setup, material, and inspection route.

### CNC-014 — How are bar stock, remnants, collets, chucks, jaws, tailstocks, steady rests, sub-spindle transfer, cutoff, part catching, and chip control planned for turned work?

**State:** Exploring

**First-pass answer:** The turning plan must specify stock/remnant identity,
straightness and support, collet/chuck/jaw geometry and force, stick-out,
tailstock or steady rest, sub-spindle transfer, cutoff/remnant, catcher or
conveyor behavior, chip control, and every manual/automatic recovery state.

**Evidence:**
- [Haas machine-specific manuals][R19] — **Evidence class:** OEM manuals; provide exact turning-option, chuck, tailstock, transfer, and recovery limits.
- [NIMS CNC performance standard][R22] — **Evidence class:** industry performance standard; supports qualified setup and operation.
- [OSHA machine guarding][R09] — **Evidence class:** primary regulation; applies to rotating stock, ejection, and access hazards.

**Conflicting evidence:** Safe unsupported length, gripping force, and catcher
behavior depend on exact stock, machine options, speed, and tooling.

**Confidence:** High.

**Business-specific validation:** Run worst-case bar/part trials, gripping-force
analysis, and cutoff/catcher/chip fault tests.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The planning logic is strong, but capability claims cannot be generalized from OEM manuals or generic competency standards; usable envelope, tooling choices, and unattended limits must be proven on the exact machine, setup, material, and inspection route.

### CNC-015 — When may probing or tool measurement update offsets automatically, and which results require a stop, quarantine, or qualified-human review?

**State:** Exploring

**First-pass answer:** Permit automatic updates only from qualified probes and
tool setters, validated cycles, stable thermal conditions, and bounded
correction/wear limits. Unexpected feature location, excessive correction,
repeatability failure, broken-tool indication, or CTQ nonconformance must stop
the process and quarantine affected work for review.

**Evidence:**
- [Renishaw probing integration][R20] — **Evidence class:** OEM technical guidance; describes bounded automatic process updates.
- [ISO/IEC 17025][R25] — **Evidence class:** consensus standard; supports competent, fit-for-purpose measurement.
- [ISO 14253-1 decision rules][R46] — **Evidence class:** consensus standard; addresses conformity decisions with measurement uncertainty.

**Conflicting evidence:** Probe repeatability is not total measurement
uncertainty, and automatic correction can hide an unstable process if limits
are too broad.

**Confidence:** High.

**Business-specific validation:** Complete a measurement-system study and
deliberate in-limit, out-of-limit, drift, and probe-failure tests.

**Second-opinion review:** GPT-5.4 — Agree. The answer stays at the right process-discipline level: serial-specific verification, explicit requirements, bounded automation, and qualified-human release are all well supported by the cited OEM, metrology, and competency sources.

### CNC-016 — What representative test parts and process-capability evidence establish the real size, tolerance, finish, material, and unattended-operation envelope of each machine?

**State:** Exploring

**First-pass answer:** Use machine-specific artifacts spanning travel,
orientations, interpolation, holes/bores, thin walls, tool reach, materials,
surface requirements, and thermal cycles. Retain positioning tests, first-
article results, Cp/Cpk or appropriate nonnormal evidence, tool-life data, and
unattended fault/recovery results. One test part cannot cover every regime.

**Evidence:**
- [ISO 230-2][R21] — **Evidence class:** consensus standard; provides positioning accuracy/repeatability tests.
- [NIST Technical Note 1297][R27] — **Evidence class:** official guidance; supports uncertainty-aware measurement evidence.
- [ISO 1 and NIST 20°C reference][R30] — **Evidence class:** consensus and official evidence; address thermal reference conditions.

**Conflicting evidence:** Static machine tests do not fully predict cutting,
workholding, tool wear, or unattended process reliability.

**Confidence:** High.

**Business-specific validation:** Approve a qualification matrix by machine and
periodically reverify it against production and maintenance history.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The planning logic is strong, but capability claims cannot be generalized from OEM manuals or generic competency standards; usable envelope, tooling choices, and unattended limits must be proven on the exact machine, setup, material, and inspection route.

## Finishing, Coatings, and Heat Treatment

### FIN-001 — Which finishes, coatings, plating, painting, passivation, anodizing, polishing, and treatments are offered?

**State:** Exploring

**First-pass answer:** Initially offer controlled deburr/tumble and simple
polish internally, with passivation, anodizing, common plating, paint/powder
coat, and standard heat treatments through qualified partners. Publish
material, specification/class, thickness, color, size, cosmetic, masking, and
certificate limits rather than a generic process name.

**Evidence:**
- [ASTM A967 stainless passivation][R07] — **Evidence class:** consensus standard; defines passivation treatments and verification.
- [MIL-PRF-8625 anodic coatings record][R32] — **Evidence class:** official specification; defines anodize types/classes and requirements.
- [Nadcap special-process accreditation][R34] — **Evidence class:** industry accreditation; supports provider qualification but not part-specific proof.

**Conflicting evidence:** Supplier catalogs may list many processes while local
size, alloy, color, permit, and quality limits are narrower.

**Confidence:** Medium.

**Business-specific validation:** Audit partners, process representative
coupons/first articles, and verify certificate and capacity performance.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The control structure is right, but actual finish availability, buildup, distortion, and cosmetic acceptance remain process-, supplier-, and alloy-specific and need qualified route data rather than generic assumptions.

### FIN-002 — How are masking requirements captured?

**State:** Exploring

**First-pass answer:** Define masking as model/drawing zones with the process,
boundary tolerance, plug/cap/thread treatment, electrical contact needs,
cosmetic transition, removal/cleanliness requirement, and inspection method.
Do not rely on free-text “mask as needed.”

**Evidence:**
- [ASME Y14.5][R23] — **Evidence class:** consensus standard; supports explicit geometric boundaries and requirements.
- [MIL-PRF-8625][R32] — **Evidence class:** official specification; demonstrates process-specific coating and contact requirements.

**Conflicting evidence:** Masking capability and edge quality vary by process,
part geometry, and supplier technique.

**Confidence:** High.

**Business-specific validation:** Approve a marked-up first article and measure
mask boundaries, coating, thread function, and cosmetic transition.

**Second-opinion review:** GPT-5.4 — Agree. The answer appropriately treats finishing and heat-treatment outcomes as controlled process variables with explicit records, acceptance criteria, and EHS constraints.

### FIN-003 — How are coating thickness and dimensional effects applied to fits and tolerances?

**State:** Exploring

**First-pass answer:** Include minimum and maximum coating buildup in the
tolerance stack before release, separately for internal/external diameters,
threads, edges, bores, sealing, grounding, and cosmetic surfaces. Measure using
a method suitable for the coating/substrate and account for uncertainty.

**Evidence:**
- [ASTM B487 coating-thickness measurement][R08] — **Evidence class:** consensus test method; provides cross-sectional thickness measurement.
- [ASME Y14.5][R23] — **Evidence class:** consensus standard; supports dimensional tolerance analysis.
- [MIL-PRF-8625][R32] — **Evidence class:** official specification; shows anodize type/thickness requirements.

**Conflicting evidence:** Anodize can involve penetration and growth, while
plating adds material; buildup is not universally symmetric or uniform.

**Confidence:** High.

**Business-specific validation:** Compare pre/post measurements and obtain
supplier capability data for each material/process/geometry family.

**Second-opinion review:** GPT-5.4 — Agree. The answer appropriately treats finishing and heat-treatment outcomes as controlled process variables with explicit records, acceptance criteria, and EHS constraints.

### FIN-004 — Which heat treatments require allowance for distortion or post-treatment machining?

**State:** Exploring

**First-pass answer:** Assume quench hardening, carburizing, nitriding, solution
and age treatment, stress relief, and weld thermal cycles can move dimensions.
Plan stock allowance, support, stress-relief sequence, straightening limits, and
post-treatment finish machining from alloy-, geometry-, and route-specific data.

**Evidence:**
- [SAE AMS2759 steel heat treatment][R33] — **Evidence class:** consensus aerospace specification; controls steel heat-treatment processes.
- [NASA materials and processes standard][R06] — **Evidence class:** official technical standard; addresses material/process effects and controls.

**Conflicting evidence:** Distortion direction and magnitude depend on prior
processing, residual stress, section changes, fixturing, and furnace/quench
conditions; generic allowances are unreliable.

**Confidence:** High on risk, low on numerical allowance.

**Business-specific validation:** Map representative parts before and after
treatment over multiple loads and suppliers.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The control structure is right, but actual finish availability, buildup, distortion, and cosmetic acceptance remain process-, supplier-, and alloy-specific and need qualified route data rather than generic assumptions.

### FIN-005 — What certifications or process records are retained?

**State:** Exploring

**First-pass answer:** Retain process CoC, specification and revision,
part/lot/load/bath identity, supplier, dates, thickness/hardness/test results,
furnace and pyrometry references where required, masking/inspection evidence,
deviations, and rework history linked to delivered serials or lots.

**Evidence:**
- [ISO 9001][R04] — **Evidence class:** consensus standard; requires retained evidence and external-provider control.
- [SAE AMS2759][R33] — **Evidence class:** consensus specification; illustrates heat-treatment process and test records.
- [Nadcap][R34] — **Evidence class:** industry accreditation; supports audited special-process systems.

**Conflicting evidence:** Contracts differ on whether raw charts/data or a
summary certificate is required; accreditation does not replace job records.

**Confidence:** High.

**Business-specific validation:** Create a customer/process retention matrix
and conduct serial/lot retrieval audits.

**Second-opinion review:** GPT-5.4 — Agree. The answer appropriately treats finishing and heat-treatment outcomes as controlled process variables with explicit records, acceptance criteria, and EHS constraints.

### FIN-006 — How are cosmetic standards represented and approved?

**State:** Exploring

**First-pass answer:** Define cosmetic zones, viewing distance, lighting,
orientation, allowed defect type/size/density, gloss, color, texture, grain,
edge condition, and a signed physical limit sample where practical. Avoid
“commercially acceptable” as the sole criterion.

**Evidence:**
- [ISO 9001][R04] — **Evidence class:** consensus standard; requires determined and reviewed customer requirements.
- [ISO 21920 surface texture][R24] — **Evidence class:** consensus standards; provide measured surface-texture specification where applicable.

**Conflicting evidence:** Photographs poorly reproduce gloss, texture, color,
and viewing conditions; instrumental metrics may not predict visual preference.

**Confidence:** High.

**Business-specific validation:** Run multi-inspector agreement studies using
actual limit samples and customer-approved viewing conditions.

**Second-opinion review:** GPT-5.4 — Agree. The answer appropriately treats finishing and heat-treatment outcomes as controlled process variables with explicit records, acceptance criteria, and EHS constraints.

### FIN-007 — Which treatments introduce environmental, safety, or regulatory constraints?

**State:** Exploring

**First-pass answer:** Treat plating, anodizing, painting, solvent cleaning,
hexavalent chromium/cadmium, acids/alkalis, wastewater, combustible powder, and
heat-treatment atmospheres as EHS- and permit-gated. Initially outsource high-
hazard work to qualified, permitted providers unless demand and controls justify
ownership.

**Evidence:**
- [40 CFR Part 63 hazardous-air-pollutant standards][R35] — **Evidence class:** primary regulation; includes sector-specific coating and finishing obligations.
- [OSHA hexavalent chromium][R36] — **Evidence class:** primary safety regulation/guidance; defines significant exposure controls.
- [OSHA hazardous-energy control][R10] — **Evidence class:** primary regulation; applies to treatment and finishing equipment service.

**Conflicting evidence:** Exact obligations depend on chemistry, quantity,
emissions, wastewater, waste classification, and jurisdiction.

**Confidence:** High on need for review.

**Business-specific validation:** Obtain EHS/legal applicability review,
industrial-hygiene data, permits, and supplier audit evidence before offering
each route.

**Second-opinion review:** GPT-5.4 — Agree. The answer appropriately treats finishing and heat-treatment outcomes as controlled process variables with explicit records, acceptance criteria, and EHS constraints.

## Inspection, Testing, and Quality

### QUA-001 — What is the default inspection level?

**State:** Exploring

**First-pass answer:** Default to requirements review, material/identity
verification, 100% visual/workmanship inspection, first-piece verification, and
risk-based dimensional sampling. Critical characteristics, first articles, and
new or unstable processes receive 100% or demonstrated capability-based
controls; sampling never overrides customer requirements.

**Evidence:**
- [ISO 9001][R04] — **Evidence class:** consensus standard; requires planned verification and release evidence.
- [SAE AS9102][R28] — **Evidence class:** sector consensus standard; provides first-article inspection discipline.
- [ISO 2859-1 sampling][R29] — **Evidence class:** consensus standard; provides attribute-sampling schemes, not a universal default.

**Conflicting evidence:** No one sampling level fits all consequence, process
maturity, lot size, and customer risk.

**Confidence:** High.

**Business-specific validation:** Quality must approve a risk matrix and
monitor escapes, false rejects, inspection cost, and capability.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The direction is sound, but default inspection level, record-retention duration, and test intensity are consequence-, customer-, and contract-specific rather than universal commercial defaults.

### QUA-002 — Which dimensions, datums, finishes, and functional characteristics are critical?

**State:** Exploring

**First-pass answer:** Mark as critical characteristics whose failure can
affect safety, regulation, load path, sealing, alignment, interfaces,
interchangeability, validated function, or downstream assembly. Determine them
jointly during requirements/design/PFMEA review; tight tolerance alone does not
make a feature critical.

**Evidence:**
- [ASME Y14.5][R23] — **Evidence class:** consensus standard; defines geometric relationships and datums.
- [DoD Manufacturing and Quality Body of Knowledge][R02] — **Evidence class:** official guidance; supports risk- and function-based characteristic control.

**Conflicting evidence:** Customers may designate key characteristics for
contractual reasons beyond internal risk analysis.

**Confidence:** High.

**Business-specific validation:** Link each CTQ to a requirement and failure
mode and obtain design-authority/customer approval where required.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The direction is sound, but default inspection level, record-retention duration, and test intensity are consequence-, customer-, and contract-specific rather than universal commercial defaults.

### QUA-003 — Who creates and approves the inspection plan?

**State:** Exploring

**First-pass answer:** Quality and manufacturing jointly create the plan;
engineering/design authority approves requirement interpretation and CTQs; the
customer approves contractually specified methods, sampling, FAI, source
inspection, and deviations. Small teams may combine jobs but should preserve
independent release authority for significant risk.

**Evidence:**
- [ISO 9001][R04] — **Evidence class:** consensus standard; requires defined responsibilities, planned controls, and authorized release.
- [ISO/IEC 17025][R25] — **Evidence class:** consensus standard; supports competent measurement methods and personnel.

**Conflicting evidence:** Organizational titles vary, but production staff
alone should not approve an unverified deviation from design intent.

**Confidence:** High.

**Business-specific validation:** Approve a RACI and audit the first ten
inspection plans for competence, independence, and customer alignment.

**Second-opinion review:** GPT-5.4 — Agree. The answer is well aligned with metrology and quality-system sources: it emphasizes explicit acceptance methods, competent measurement, and documented authority rather than unsupported default inspection lore.

### QUA-004 — When are CMM reports, material certificates, first article reports, process certificates, or photographs required?

**State:** Exploring

**First-pass answer:** Require objective reports when contractually required,
risk-critical, needed to validate setup/process/configuration, or when evidence
cannot otherwise be reconstructed. Match the document to the risk: certificate
for pedigree, FAI for initial configuration/process, CMM for suitable geometry,
and photographs for condition/cosmetics—not as substitutes for one another.

**Evidence:**
- [SAE AS9102][R28] — **Evidence class:** sector consensus standard; governs first-article evidence.
- [ISO/IEC 17025][R25] — **Evidence class:** consensus standard; supports competent test/calibration reports.
- [Nadcap][R34] — **Evidence class:** industry accreditation; supports special-process supplier control.

**Conflicting evidence:** More documentation raises cost and can create false
assurance when the method or characteristic is inappropriate.

**Confidence:** High.

**Business-specific validation:** Build a customer/risk documentation matrix
and compare it with escape and complaint history.

**Second-opinion review:** GPT-5.4 — Agree. The answer is well aligned with metrology and quality-system sources: it emphasizes explicit acceptance methods, competent measurement, and documented authority rather than unsupported default inspection lore.

### QUA-005 — How are measurement uncertainty and instrument calibration handled?

**State:** Exploring

**First-pass answer:** Calibrate traceably at risk-based intervals, protect
instrument status, verify suitability before use, estimate uncertainty for
important decisions, and apply an agreed acceptance or guard-band rule near
specification limits. A current sticker alone does not prove the method is fit.

**Evidence:**
- [ISO/IEC 17025][R25] — **Evidence class:** consensus standard; addresses competence, calibration, traceability, and uncertainty.
- [NIST Technical Note 1297][R27] — **Evidence class:** official guidance; provides uncertainty evaluation and reporting principles.
- [ISO 14253-1][R46] — **Evidence class:** consensus standard; defines decision rules for GPS conformity.

**Conflicting evidence:** Different competent uncertainty models may yield
different guard bands; contract terms may prescribe the decision rule.

**Confidence:** High.

**Business-specific validation:** Build uncertainty budgets and measurement-
system studies for representative CTQs and customer methods.

**Second-opinion review:** GPT-5.4 — Agree. The answer is well aligned with metrology and quality-system sources: it emphasizes explicit acceptance methods, competent measurement, and documented authority rather than unsupported default inspection lore.

### QUA-006 — What constitutes acceptance for parts whose function is easier to test than fully dimension?

**State:** Exploring

**First-pass answer:** Use a validated functional test as the primary
acceptance method when it directly represents intended loads, environment,
limits, and failure modes and the design authority approves it. Still inspect
interfaces and characteristics needed for interchangeability and to prevent
untested latent defects.

**Evidence:**
- [ISO 9001][R04] — **Evidence class:** consensus standard; permits planned verification against defined requirements.
- [NASA materials and processes standard][R06] — **Evidence class:** official technical standard; supports qualification and acceptance tied to intended use.

**Conflicting evidence:** A passing functional test can mask reduced margin,
wear-out risk, or inability to fit another assembly.

**Confidence:** High.

**Business-specific validation:** Correlate functional results with critical
dimensions and field or life-test performance, including seeded faults.

**Second-opinion review:** GPT-5.4 — Agree. The answer is well aligned with metrology and quality-system sources: it emphasizes explicit acceptance methods, competent measurement, and documented authority rather than unsupported default inspection lore.

### QUA-007 — How are nonconformances, deviations, rework, use-as-is decisions, and scrap communicated and approved?

**State:** Exploring

**First-pass answer:** Identify and segregate nonconforming product; document
the violated requirement, evidence, affected quantity, containment, and
disposition. Rework follows approved instructions; repair, use-as-is, and
deviation require authorized design/customer approval; scrap is controlled to
prevent unintended use.

**Evidence:**
- [ISO 9001][R04] — **Evidence class:** consensus standard; requires control and documented disposition of nonconforming outputs.
- [AS9100D][R05] — **Evidence class:** sector consensus standard; adds stronger authority, notification, and product-safety controls.

**Conflicting evidence:** Approval authority and mandatory customer notice vary
by contract, design ownership, and regulatory regime.

**Confidence:** High.

**Business-specific validation:** Run a mock NCR/material-review-board case and
audit actual jobs for unauthorized rework or disposition.

**Second-opinion review:** GPT-5.4 — Agree. The answer is well aligned with metrology and quality-system sources: it emphasizes explicit acceptance methods, competent measurement, and documented authority rather than unsupported default inspection lore.

### QUA-008 — What records are retained, for how long, and for whom?

**State:** Exploring

**First-pass answer:** Create a retention matrix by customer, contract, product
life, law, design ownership, and consequence. A provisional commercial
baseline may be ten years for configuration, material/process certificates,
travelers, nonconformances, and acceptance records, with longer or life-plus
retention where sectors require it.

**Evidence:**
- [ISO 9001][R04] — **Evidence class:** consensus standard; requires necessary retained information but sets no universal duration.
- [AS9100D][R05] — **Evidence class:** sector consensus standard; strengthens production and traceability record controls.
- [ISO 10007][R40] — **Evidence class:** consensus guidance; supports configuration-record continuity.

**Conflicting evidence:** Ten years is a business hypothesis, not a standards
rule; some contracts require shorter, life-plus-years, or indefinite retention.

**Confidence:** Medium.

**Business-specific validation:** Counsel and customers must approve the matrix;
perform archive restore, readability, access-control, and deletion tests.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The direction is sound, but default inspection level, record-retention duration, and test intensity are consequence-, customer-, and contract-specific rather than universal commercial defaults.

### QUA-009 — How are destructive tests, life tests, pressure tests, load tests, and environmental tests priced?

**State:** Exploring

**First-pass answer:** Quote test engineering, plans/procedures, fixtures and
instrumentation, lab setup, specimens and destructive quantity, conditioning,
operator/witness time, data/report/certification, freight, disposal, and
failure/retest contingency separately. State whether qualification samples are
saleable and who owns failed hardware.

**Evidence:**
- [ISO/IEC 17025][R25] — **Evidence class:** consensus standard; defines competent laboratory and reporting requirements.
- [DoD Manufacturing and Quality Body of Knowledge][R02] — **Evidence class:** official guidance; recognizes verification, qualification, and test-resource costs.

**Conflicting evidence:** Development failures and repeated qualification may
dominate cost, while a mature standard test can be relatively predictable.

**Confidence:** High on cost categories.

**Business-specific validation:** Obtain scoped lab/supplier quotes and compare
estimated with actual engineering, specimen, setup, and retest cost.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The direction is sound, but default inspection level, record-retention duration, and test intensity are consequence-, customer-, and contract-specific rather than universal commercial defaults.

### QUA-010 — When is independent third-party testing required?

**State:** Exploring

**First-pass answer:** Use an independent laboratory when required by law or
customer, when internal competence/equipment is absent, when consequence is
high, or when impartial evidence is needed for certification or dispute.
Confirm the laboratory's accreditation scope, exact method, uncertainty, and
witness requirements.

**Evidence:**
- [ISO/IEC 17025][R25] — **Evidence class:** consensus standard; establishes laboratory competence and accreditation scope.
- [NASA materials and processes standard][R06] — **Evidence class:** official technical standard; supports qualified testing and independent evidence for critical applications.

**Conflicting evidence:** Accreditation demonstrates competence only within the
listed scope and does not guarantee suitability for every part or method.

**Confidence:** High.

**Business-specific validation:** Approve labs by scope and method and conduct
correlation or witness trials for high-risk testing.

**Second-opinion review:** GPT-5.4 — Agree. The answer is well aligned with metrology and quality-system sources: it emphasizes explicit acceptance methods, competent measurement, and documented authority rather than unsupported default inspection lore.

### QUA-011 — What customer-specific acceptance profile should be retained?

**State:** Exploring

**First-pass answer:** Retain a controlled customer profile covering drawing
conventions, tolerance interpretation, finish/cosmetics, edges and burrs,
color/texture, measurement and sampling, FAI/documents, packaging, deviation
contacts, and prior return causes. The current contract/order always overrides
historical preference.

**Evidence:**
- [ISO 9001][R04] — **Evidence class:** consensus standard; requires review and communication of customer requirements.
- [ASME Y14.5][R23] — **Evidence class:** consensus standard; supports explicit drawing and tolerance interpretation.
- [ISO 10007][R40] — **Evidence class:** consensus guidance; supports controlled customer/configuration information.

**Conflicting evidence:** Historical acceptance can clarify preference but must
not silently amend a released specification.

**Confidence:** High.

**Business-specific validation:** Have customers confirm profiles and review
them after every complaint, deviation, drawing update, or method change.

**Second-opinion review:** GPT-5.4 — Agree. The answer is well aligned with metrology and quality-system sources: it emphasizes explicit acceptance methods, competent measurement, and documented authority rather than unsupported default inspection lore.

### QUA-012 — How are tolerances and surface finishes made sufficiently explicit before quoting?

**State:** Exploring

**First-pass answer:** Do not quote precision work until dimensional and
geometric tolerances, datums, roughness, lay/waviness where relevant, machining
marks, polishing/coating, visual standard or sample, and inspection method are
explicit. Apply documented defaults only when the customer knowingly accepts
them.

**Evidence:**
- [ASME Y14.5][R23] — **Evidence class:** consensus standard; provides dimensional and geometric specification rules.
- [ISO 21920 surface texture][R24] — **Evidence class:** consensus standards; define surface-texture indication and evaluation.
- [ISO 14253-1][R46] — **Evidence class:** consensus standard; connects specification to conformity decisions.

**Conflicting evidence:** Legacy drawings may contain obsolete, missing, or
conflicting conventions, and a tighter default can add unnecessary cost.

**Confidence:** High.

**Business-specific validation:** Use a quote checklist and track clarification,
requote, NCR, and return rates caused by ambiguous requirements.

**Second-opinion review:** GPT-5.4 — Agree. The answer is well aligned with metrology and quality-system sources: it emphasizes explicit acceptance methods, competent measurement, and documented authority rather than unsupported default inspection lore.

### QUA-013 — How are customer and supplier measurement methods reconciled?

**State:** Exploring

**First-pass answer:** Establish a measurement agreement before dispute:
datum/setup, temperature and stabilization, instrument/contact/filtering,
feature location, sample size, uncertainty, rounding, calibration status, and
a referee method/laboratory. Compare results with uncertainty, not just nominal
numbers.

**Evidence:**
- [ISO/IEC 17025][R25] — **Evidence class:** consensus standard; addresses method competence, traceability, and uncertainty.
- [NIST Technical Note 1297][R27] — **Evidence class:** official guidance; supports uncertainty-aware comparison.
- [ISO 14253-1][R46] — **Evidence class:** consensus standard; provides conformity decision rules.

**Conflicting evidence:** Two calibrated methods can legitimately disagree
within their different uncertainty and sampling volumes.

**Confidence:** High.

**Business-specific validation:** Run customer/supplier correlation studies on
shared artifacts and real parts before high-value production.

**Second-opinion review:** GPT-5.4 — Agree. The answer is well aligned with metrology and quality-system sources: it emphasizes explicit acceptance methods, competent measurement, and documented authority rather than unsupported default inspection lore.

### QUA-014 — When does a customer's acceptance history justify additional inspection or approval steps?

**State:** Exploring

**First-pass answer:** Repeated returns, inconsistent acceptance, ambiguous
requirements, or high sensitivity justify temporary documented controls such as
requirements review, signed limit sample, FAI/source inspection, increased or
100% inspection, reports/photos, retained samples, and explicit deviations.
Controls need owners, cost, exit criteria, and review dates.

**Evidence:**
- [ISO 9001][R04] — **Evidence class:** consensus standard; supports risk-based controls and corrective action.
- [SAE AS9102][R28] — **Evidence class:** sector consensus standard; supports first-article verification after relevant changes.
- [ISO 2859-1][R29] — **Evidence class:** consensus standard; supports controlled sampling adjustments.

**Conflicting evidence:** Added inspection can contain risk but should not
replace clarification or correction of the root requirement/process problem.

**Confidence:** High.

**Business-specific validation:** Use a time-bound customer control plan and
review escape, return, false-reject, and cost trends quarterly.

**Second-opinion review:** GPT-5.4 — Agree. The answer is well aligned with metrology and quality-system sources: it emphasizes explicit acceptance methods, competent measurement, and documented authority rather than unsupported default inspection lore.

### QUA-015 — How does temperature affect the machinability and measured size of each part?

**State:** Exploring

**First-pass answer:** Estimate uniform thermal size error as coefficient of
expansion × feature length × temperature difference, then account for gradients
and the machine, fixture, gauge, coolant, and cutting state. Compare combined
thermal error and uncertainty with the tolerance; temperature also changes
cutting behavior, chip formation, and tool life.

**Evidence:**
- [ISO 1 and NIST 20°C reference][R30] — **Evidence class:** consensus standard and official research; establish the dimensional reference and thermal implications.
- [NIST Technical Note 1297][R27] — **Evidence class:** official guidance; supports incorporating thermal contributions into uncertainty.

**Conflicting evidence:** The simple uniform-temperature equation understates
gradients, mixed materials, residual stress, and heat generated during cutting.

**Confidence:** High on the model's use, not on unmeasured shop values.

**Business-specific validation:** Instrument representative cycles and compare
predicted thermal movement with measured size, cut load, and finish.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The thermal-control reasoning is technically sound, but soak time, correction use, and any action threshold must be tied to measured uncertainty budgets, material CTE, geometry, machine behavior, and customer agreement.

### QUA-016 — When must a part and measuring equipment acclimate before inspection?

**State:** Exploring

**First-pass answer:** Acclimate until part, instrument, fixture, and room are
stable enough for the required uncertainty, using measured temperature and rate
of change rather than one universal soak time. Massive, insulated, hollow, or
recently machined parts need different criteria than thin parts.

**Evidence:**
- [Mitutoyo CMM environmental recommendations][R31] — **Evidence class:** OEM technical guidance; describes temperature and gradient effects on CMM work.
- [ISO 1 and NIST 20°C reference][R30] — **Evidence class:** consensus and official evidence; establish reference-temperature requirements.
- [ISO/IEC 17025][R25] — **Evidence class:** consensus standard; requires environmental conditions suitable for valid results.

**Conflicting evidence:** Surface temperature may stabilize before the core;
arbitrary long waits can also add cost without improving uncertainty.

**Confidence:** High.

**Business-specific validation:** Log temperature and repeated measurements to
define stabilization criteria by material, mass, geometry, and tolerance.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The thermal-control reasoning is technically sound, but soak time, correction use, and any action threshold must be tied to measured uncertainty budgets, material CTE, geometry, machine behavior, and customer agreement.

### QUA-017 — When may dimensional measurements be corrected to the 20°C reference temperature?

**State:** Exploring

**First-pass answer:** Correct to 20°C only when material expansion data is
known, part and instrument temperatures are measured, gradients are controlled,
the calculation is traceable, correction uncertainty is included, and the
customer accepts the method where material. Disclose corrected and observed
values as required.

**Evidence:**
- [ISO 1 and NIST 20°C reference][R30] — **Evidence class:** consensus standard and official research; establish reference temperature and correction context.
- [NIST Technical Note 1297][R27] — **Evidence class:** official guidance; requires uncertainty treatment for corrections.
- [ISO 14253-1][R46] — **Evidence class:** consensus standard; governs conformity decisions.

**Conflicting evidence:** Conditioning may be more reliable than correction
when gradients, material CTE, or instrument temperature are uncertain.

**Confidence:** High.

**Business-specific validation:** Compare corrected warm measurements with
stabilized 20°C measurements across representative parts.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The thermal-control reasoning is technically sound, but soak time, correction use, and any action threshold must be tied to measured uncertainty budgets, material CTE, geometry, machine behavior, and customer agreement.

### QUA-018 — How do coolant temperature and machine warmup affect process control?

**State:** Exploring

**First-pass answer:** Treat coolant temperature/flow and machine warmup as
separate controlled inputs. Coolant affects cutting heat, tool life, chips, and
part temperature; ambient and operating heat move the spindle, structure,
ballscrews, fixture, stock, probes, and measurement system. Use machine-specific
warmup and evidence-derived stabilization limits.

**Evidence:**
- [Haas machine-specific manuals][R19] — **Evidence class:** OEM manuals; provide machine-specific warmup and coolant procedures.
- [Renishaw probing integration][R20] — **Evidence class:** OEM technical guidance; addresses thermal and probing process effects.
- [ISO 1 and NIST 20°C reference][R30] — **Evidence class:** consensus and official evidence; establish thermal dimensional context.

**Conflicting evidence:** Compensation and warmup behavior differ by machine
design, condition, duty cycle, and environment.

**Confidence:** High.

**Business-specific validation:** Conduct cold-to-stable drift studies by
machine, coolant state, cycle, feature location, and season.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The thermal-control reasoning is technically sound, but soak time, correction use, and any action threshold must be tied to measured uncertainty budgets, material CTE, geometry, machine behavior, and customer agreement.

### QUA-019 — Which jobs require thermal compensation, in-process probing, or temperature-conditioned inspection?

**State:** Exploring

**First-pass answer:** Require added thermal control when predicted combined
thermal error/uncertainty consumes a material share of tolerance—provisionally
10–20%—or when geometry, long cycles, material CTE, machine history, capability,
or customer requirement warrants it. Choose conditioning, probing, compensation,
or all three by failure mode; the percentage is a risk trigger, not a standard.

**Evidence:**
- [ISO/IEC 17025][R25] — **Evidence class:** consensus standard; requires environmental control adequate for valid measurement.
- [NIST Technical Note 1297][R27] — **Evidence class:** official guidance; supports uncertainty-budget thresholds.
- [Renishaw probing integration][R20] — **Evidence class:** OEM technical guidance; shows in-process measurement options and limits.

**Conflicting evidence:** A strict universal ratio may overcontrol stable work
or undercontrol safety-critical features; demonstrated capability can support a
different threshold.

**Confidence:** Medium.

**Business-specific validation:** Compare capability and cost with and without
controls and obtain customer agreement on measurement/acceptance rules.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The thermal-control reasoning is technically sound, but soak time, correction use, and any action threshold must be tied to measured uncertainty budgets, material CTE, geometry, machine behavior, and customer agreement.

## Assembly

### ASM-001 — Will the business ship individual parts, kits, subassemblies, complete assemblies, or all of these?

**State:** Exploring

**First-pass answer:** Launch with individual parts, kits, and bounded
subassemblies. Offer complete assemblies only after purchased-component
control, released work instructions, hidden-feature verification, functional
test, as-built traceability, packaging, and service obligations are ready.

**Evidence:**
- [DoD Manufacturing Readiness Level Deskbook][R03] — **Evidence class:** official/practitioner guidance; supports staged readiness for complete production.
- [NASA-STD-8739.6 workmanship implementation][R37] — **Evidence class:** official technical guidance; demonstrates controlled assembly and workmanship expectations.

**Conflicting evidence:** An anchor customer with a simple, well-defined
assembly may justify earlier full-assembly delivery.

**Confidence:** Medium.

**Business-specific validation:** Analyze demand/margin and run a pilot assembly
readiness review using representative purchased parts and tests.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The staged assembly approach is prudent, but the exact scope should expand only after repeated builds prove purchased-component control, hidden-feature verification, functional test coverage, and as-built traceability for the target product family.

### ASM-002 — How are purchased components and approved vendors selected?

**State:** Exploring

**First-pass answer:** Select components from released specifications and
qualified suppliers using technical fit, authenticity, quality, delivery,
lifecycle/obsolescence, change notification, capacity, and total cost—not price
alone. Customer-mandated sources remain controlling but should be risk-tracked.

**Evidence:**
- [ISO 9001][R04] — **Evidence class:** consensus standard; requires risk-based control of external providers and purchased outputs.
- [AS9100D][R05] — **Evidence class:** sector consensus standard; strengthens supplier, counterfeit-part, and change controls.

**Conflicting evidence:** A low-price supplier may be adequate for commodity
parts; excessive qualification can reduce flexibility without proportional
risk reduction.

**Confidence:** High.

**Business-specific validation:** Establish an approved-supplier process,
incoming evidence, performance scorecards, and periodic requalification.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The staged assembly approach is prudent, but the exact scope should expand only after repeated builds prove purchased-component control, hidden-feature verification, functional test coverage, and as-built traceability for the target product family.

### ASM-003 — How are torque, adhesive, welding, alignment, cleanliness, lubrication, and test requirements recorded?

**State:** Exploring

**First-pass answer:** Use controlled instructions that specify torque value,
tool, sequence, and witness; adhesive identity/lot, mix, application, cure, and
expiry; welding procedure/operator; alignment/datums; cleanliness method/class;
lubricant identity/quantity; environment; test limits; and sign-off.

**Evidence:**
- [ISO 6789 torque tools][R39] — **Evidence class:** consensus standards; govern torque-tool requirements and calibration.
- [NASA-STD-8739.6 workmanship implementation][R37] — **Evidence class:** official technical guidance; demonstrates detailed, controlled assembly workmanship.
- [AWS codes and standards][R38] — **Evidence class:** consensus welding standards; support controlled welding procedures and personnel.

**Conflicting evidence:** Generic notes may be enough for low-risk assembly but
cannot prove product-specific adhesive, torque, cleanliness, or weld control.

**Confidence:** High.

**Business-specific validation:** Audit a representative build using calibrated
tools, material genealogy, environmental records, and an independent witness.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The staged assembly approach is prudent, but the exact scope should expand only after repeated builds prove purchased-component control, hidden-feature verification, functional test coverage, and as-built traceability for the target product family.

### ASM-004 — How is assembly sequence validated?

**State:** Exploring

**First-pass answer:** Validate sequence through design/PFMEA review,
representative pilot builds, accessibility and tool checks, torque/adhesive
timing, mistake-proofing, operator ergonomics, and first-article teardown or
inspection where hidden conditions exist. Freeze and change-control the proven
sequence.

**Evidence:**
- [DoD Manufacturing and Quality Body of Knowledge][R02] — **Evidence class:** official guidance; supports process risk analysis and verification.
- [SAE AS9102][R28] — **Evidence class:** sector consensus standard; supports first-article verification of production configuration.
- [NASA-STD-8739.6 workmanship implementation][R37] — **Evidence class:** official technical guidance; supports qualified assembly practice.

**Conflicting evidence:** Digital assembly simulation can find access problems
but does not prove actual fit, contamination control, ergonomics, or hidden
damage.

**Confidence:** High.

**Business-specific validation:** Have operators who did not write the
instructions perform pilot builds and record deviations and defects.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The staged assembly approach is prudent, but the exact scope should expand only after repeated builds prove purchased-component control, hidden-feature verification, functional test coverage, and as-built traceability for the target product family.

### ASM-005 — What functional testing is performed after assembly?

**State:** Exploring

**First-pass answer:** Derive end-of-line tests from requirements and failure
modes: fit/motion, electrical continuity and safety, leak/pressure, load,
calibration, software/configuration, and interlocks as applicable. Define test
limits, instrumentation, sequence, retest rules, and retain results by serial
or lot.

**Evidence:**
- [ISO 9001][R04] — **Evidence class:** consensus standard; requires planned verification and authorized release.
- [ISO/IEC 17025][R25] — **Evidence class:** consensus standard; supports valid methods, calibrated equipment, and competent testing.

**Conflicting evidence:** More testing can improve detection but may overstress
the product, add false rejects, or still miss unrepresented field conditions.

**Confidence:** High.

**Business-specific validation:** Review coverage against failure modes and
challenge the test with safely seeded representative faults.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The staged assembly approach is prudent, but the exact scope should expand only after repeated builds prove purchased-component control, hidden-feature verification, functional test coverage, and as-built traceability for the target product family.

### ASM-006 — How are serialized components and as-built configurations traced?

**State:** Exploring

**First-pass answer:** Maintain as-built genealogy linking product revision,
component serial/lot and supplier, software/firmware, operators, equipment,
process and test records, deviations, repairs, and replacements. Use lot rather
than serial traceability for commodities only when risk and contract allow it.

**Evidence:**
- [AS9100D][R05] — **Evidence class:** sector consensus standard; supports product identification, configuration, traceability, and release records.
- [ISO 10007][R40] — **Evidence class:** consensus guidance; supports as-built configuration status accounting.

**Conflicting evidence:** Full serial traceability for every fastener can cost
more than the risk justifies, while safety-critical components may require it.

**Confidence:** High.

**Business-specific validation:** Perform forward/backward tracing and
reconstruct a representative unit after a simulated component recall.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The staged assembly approach is prudent, but the exact scope should expand only after repeated builds prove purchased-component control, hidden-feature verification, functional test coverage, and as-built traceability for the target product family.

### ASM-007 — What service, repair, replacement, and disassembly information accompanies the product?

**State:** Exploring

**First-pass answer:** Provide controlled installation/use limits, maintenance
intervals, consumables, safe isolation, troubleshooting, repair or replacement
criteria, disassembly sequence, spare identification, torque/sealant needs, and
configuration compatibility appropriate to risk. Clearly identify non-user-
serviceable items.

**Evidence:**
- [OSHA hazardous-energy control][R10] — **Evidence class:** primary regulation; informs safe isolation for serviceable equipment.
- [NASA-STD-8739.6 workmanship implementation][R37] — **Evidence class:** official technical guidance; supports controlled repair and workmanship information.
- [ISO 10007][R40] — **Evidence class:** consensus guidance; supports replacement and configuration compatibility.

**Conflicting evidence:** Proprietary or sealed products may appropriately
provide replacement rather than field-repair instructions.

**Confidence:** High.

**Business-specific validation:** Conduct an independent serviceability trial
and safety/legal review using only the supplied documentation.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The staged assembly approach is prudent, but the exact scope should expand only after repeated builds prove purchased-component control, hidden-feature verification, functional test coverage, and as-built traceability for the target product family.

## Packaging, Delivery, and Installation

### DEL-001 — What packaging protection is required for corrosion, impact, contamination, cosmetic surfaces, and precision features?

**State:** Exploring

**First-pass answer:** Assign a packaging class from corrosion, shock/vibration,
contamination, moisture, ESD where relevant, cosmetic sensitivity, sharp edges,
and protected precision features. Use caps, separators, immobilization,
desiccant/VCI, clean barriers, and orientation indicators as the risk requires,
then validate the packed product.

**Evidence:**
- [ASTM D4169 distribution testing][R41] — **Evidence class:** consensus standard; provides laboratory simulation of distribution hazards.
- [ISTA test procedures][R42] — **Evidence class:** industry consensus procedures; provide package-performance test protocols.

**Conflicting evidence:** Overpackaging adds cost, waste, unpacking damage, and
handling burden; laboratory tests do not reproduce every carrier event.

**Confidence:** High.

**Business-specific validation:** Run distribution tests and pilot shipments,
then track damage, corrosion, contamination, complaints, and packaging labor.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The logistics guidance is directionally right, but shipping mode, reusable-packaging economics, and field-service scope remain product-, route-, insurer-, and jurisdiction-specific and need local operating data.

### DEL-002 — Which shipping methods, regions, sizes, weights, and hazardous materials are supported?

**State:** Exploring

**First-pass answer:** Initially publish defined domestic parcel and LTL limits
by packed size, weight, value, temperature, and delivery service. Route
oversized, export-controlled, international, and hazardous shipments through
qualified freight, customs, and hazmat partners until internal competence and
systems are demonstrated.

**Evidence:**
- [PHMSA hazardous-material regulations][R43] — **Evidence class:** primary regulation; governs U.S. hazardous-material transport.
- [ASTM D4169][R41] — **Evidence class:** consensus standard; supports method-specific package validation.

**Conflicting evidence:** Carrier limits, surcharges, and destination rules
change frequently; a generally supported method may still reject a particular
commodity or location.

**Confidence:** High on staged scope.

**Business-specific validation:** Maintain and periodically review a
carrier/region/commodity matrix with legal, insurance, and partner input.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The logistics guidance is directionally right, but shipping mode, reusable-packaging economics, and field-service scope remain product-, route-, insurer-, and jurisdiction-specific and need local operating data.

### DEL-003 — When is reusable packaging justified?

**State:** Exploring

**First-pass answer:** Use reusable packaging when a controlled return loop,
expected turns, damage reduction, cleanliness, handling ergonomics, product
value, and customer participation justify purchase, tracking, cleaning, repair,
storage, and reverse-logistics cost. Include loss and delayed-return scenarios.

**Evidence:**
- [ASTM D4169][R41] — **Evidence class:** consensus standard; supports performance testing over the intended distribution environment.
- [ISTA procedures][R42] — **Evidence class:** industry consensus procedures; support package-system validation.

**Conflicting evidence:** Durable containers may lower damage and waste, but low
return rates, empty freight, loss, cleaning, and product changes can erase
savings.

**Confidence:** Medium.

**Business-specific validation:** Run a closed-loop pilot measuring turns,
loss/delay, cleaning/repair, damage, labor, and cost per delivered part.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The logistics guidance is directionally right, but shipping mode, reusable-packaging economics, and field-service scope remain product-, route-, insurer-, and jurisdiction-specific and need local operating data.

### DEL-004 — What documentation ships with the product?

**State:** Exploring

**First-pass answer:** Ship or provide controlled electronic access to packing
list, part/revision/quantity, CoC, required material/process/inspection records,
serial/lot list, and handling/storage instructions. Add SDS, hazmat, export,
installation, acceptance, test, or service documents when applicable.

**Evidence:**
- [ISO 9001][R04] — **Evidence class:** consensus standard; supports release evidence and customer communication.
- [PHMSA hazardous-material regulations][R43] — **Evidence class:** primary regulation; requires transport documentation for regulated materials.
- [ISO 10007][R40] — **Evidence class:** consensus guidance; supports correct configuration/revision information.

**Conflicting evidence:** Customers differ on paper versus electronic delivery,
retention, proprietary information, and whether certificates accompany each
shipment or are available on request.

**Confidence:** High.

**Business-specific validation:** Maintain customer-specific shipping
checklists and use receiving feedback to audit completeness and usability.

**Second-opinion review:** GPT-5.4 — Agree. The documentation and risk-transfer guidance is sound because those items are fundamentally contract and release-control questions, and the cited standards support that framing.

### DEL-005 — How are title, risk of loss, insurance, damage claims, and delivery acceptance handled?

**State:** Exploring

**First-pass answer:** Put a named Incoterm or explicit domestic delivery term,
title and risk-transfer point, insurance responsibility, inspection/acceptance
window, concealed-damage procedure, evidence preservation, and carrier-claim
roles in the contract. Do not assume a freight quote answers ownership or
warranty questions.

**Evidence:**
- [ICC Incoterms rules][R44] — **Evidence class:** international commercial rules; define delivery obligations, cost, and risk allocation.
- [ASTM D4169][R41] — **Evidence class:** consensus standard; supports evidence that packaging was designed for distribution hazards.

**Conflicting evidence:** Incoterms allocate delivery risk and tasks but do not
by themselves settle title, payment, warranty, or product acceptance.

**Confidence:** High on contract structure.

**Business-specific validation:** Counsel and insurer must approve templates;
run a tabletop damaged and concealed-damage shipment scenario.

**Second-opinion review:** GPT-5.4 — Agree. The documentation and risk-transfer guidance is sound because those items are fundamentally contract and release-control questions, and the cited standards support that framing.

### DEL-006 — Will installation, commissioning, or field service ever be offered?

**State:** Exploring

**First-pass answer:** Offer field work only after defining scope, site
readiness, field hazards, licensing, qualified staff, insurance, travel/service
pricing, tools/spares, acceptance tests, training, documentation, and
post-acceptance support. Begin through qualified partners or a bounded pilot,
not an open-ended promise.

**Evidence:**
- [DoD Manufacturing Readiness Level Deskbook][R03] — **Evidence class:** official/practitioner guidance; supports readiness before operational delivery.
- [OSHA machine guarding][R09] — **Evidence class:** primary regulation; affects commissioned equipment and safeguarding.
- [OSHA hazardous-energy control][R10] — **Evidence class:** primary regulation; affects installation and field service.

**Conflicting evidence:** Field service can improve customer value and feedback,
but creates jurisdictional, scheduling, licensing, safety, and liability
exposure absent from factory delivery.

**Confidence:** Medium.

**Business-specific validation:** Complete one controlled pilot with site
survey, job-hazard analysis, insurance/legal review, and signed commissioning
protocol.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The logistics guidance is directionally right, but shipping mode, reusable-packaging economics, and field-service scope remain product-, route-, insurer-, and jurisdiction-specific and need local operating data.

[R01]: https://www.nist.gov/publications/manufacturing-process-and-material-selection-during-conceptual-design
[R02]: https://www.cto.mil/wp-content/uploads/2025/07/MQBOK-July-2025-All.pdf
[R03]: https://dsiac.dtic.mil/digests/20-may-2025/
[R04]: https://www.iso.org/iso-9001-quality-management.html
[R05]: https://www.sae.org/standards/content/as9100d/
[R06]: https://standards.nasa.gov/standard/nasa/nasa-std-6016
[R07]: https://www.astm.org/a0967_a0967m-17.html
[R08]: https://webstore.ansi.org/standards/astm/astmb48724
[R09]: https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.212
[R10]: https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.147
[R11]: https://www.osha.gov/robotics
[R12]: https://www.iso.org/standard/51330.html
[R13]: https://opcfoundation.org/about/opc-technologies/opc-ua/
[R14]: https://www.mtconnect.org/
[R15]: https://www.iso.org/standard/63180.html
[R16]: https://www.nist.gov/programs-projects/modeling-methodology-smart-manufacturing
[R17]: https://www.nist.gov/itl/ai-risk-management-framework
[R19]: https://www.haascnc.com/owners/Service/operators-manual.html
[R20]: https://www.renishaw.com/en/integrating-machine-tool-probing-into-manufacturing-processes--10913
[R21]: https://www.iso.org/standard/54838.html
[R22]: https://www.nims-skills.org/sites/default/files/media/document/CNC%20Milling%20Programming%2C%20Setup%2C%20and%20Operations%20%28Version%202.10%29.pdf
[R23]: https://www.asme.org/codes-standards/find-codes-standards/y14-5-dimensioning-tolerancing
[R24]: https://www.iso.org/search.html?q=ISO%2021920
[R25]: https://www.iso.org/standard/66912.html
[R27]: https://www.nist.gov/pml/nist-technical-note-1297
[R28]: https://www.sae.org/standards/content/as9102b/
[R29]: https://www.iso.org/standard/11482.html
[R30]: https://doi.org/10.6028/jres.121.026
[R31]: https://www.mitutoyo.com/webfoo/wp-content/uploads/Temperature_effects_handout.pdf
[R32]: https://quicksearch.dla.mil/qsDocDetails.aspx?ident_number=35926
[R33]: https://www.sae.org/standards/content/ams2759/
[R34]: https://p-r-i.org/nadcap/
[R35]: https://www.ecfr.gov/current/title-40/chapter-I/subchapter-C/part-63
[R36]: https://www.osha.gov/hexavalent-chromium
[R37]: https://standards.nasa.gov/sites/default/files/standards/NASA/B/0/nasa-std-87396b.pdf
[R38]: https://www.aws.org/Standards-and-Publications/Codes-and-Standards/
[R39]: https://www.iso.org/search.html?q=ISO%206789
[R40]: https://www.iso.org/search.html?q=ISO%2010007
[R41]: https://www.astm.org/d4169-23e01.html
[R42]: https://ista.org/test_procedures.php
[R43]: https://www.phmsa.dot.gov/hazmat/regs
[R44]: https://iccwbo.org/business-solutions/incoterms-rules/
[R46]: https://www.iso.org/standard/70137.html
[R47]: https://www.nist.gov/mep/successstories
[R48]: https://betterbuildingssolutioncenter.energy.gov/better-plants
[R49]: https://www.gao.gov/products/gao-20-195g
