# Production Planning and Shop Operations Questions

## Purpose

Questions about turning accepted work into controlled execution: ownership,
instructions, release, dispatch, WIP, handoffs, schedule performance, actual
cost, and learning. People and qualifications remain in the people topic;
this file owns the operating system used by those people.

These are discovery questions, not approved requirements. Question IDs are
permanent and canonical in this file.

## Operating Responsibilities and Control

### OPS-001 — Which roles are needed initially?

**State:** Superseded

**Superseded by:** [HUM-001](people-and-operating-model.md#hum-001--which-people-and-roles-are-required-for-the-first-commercially-viable-version-of-the-business)

### OPS-002 — Which responsibilities require engineering, machining, quality, welding, procurement, legal, or customer-service qualifications?

**State:** Exploring

**First-pass answer:** Use a responsibility/authorization matrix, not job titles.
Reserve regulated, safety-critical, contractual, and acceptance decisions for
people with the required license, demonstrated competency, customer approval,
or delegated authority. At minimum distinguish design authority, CNC/program
release, first-article and final acceptance, welding procedure/personnel
qualification, supplier approval, contract/export review, and customer
commitment authority.

**Evidence:**
- [ISO 9001 quality-management overview](https://www.iso.org/iso-9001-quality-management.html) — **Evidence class:** official standards-body guidance. ISO 9001 centers controlled responsibilities, competence, documented processes, external providers, and release of conforming output.
- [OSHA 29 CFR 1910.147](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.147) — **Evidence class:** binding U.S. regulation. It illustrates that authorization and training depend on the hazardous task, not merely employment role.

**Conflicting evidence:** Small shops commonly combine roles successfully, but
one person holding several roles does not remove independence, competency, or
customer/regulatory constraints.

**Confidence:** High on the matrix approach; medium on exact qualifications
until products and jurisdictions are selected.

**Business-specific validation:** Map each offered process and target customer
to law, code, contract, insurer, and customer-specific qualification; have
qualified legal, engineering, quality, and safety reviewers approve the matrix.

**Second-opinion review:** GPT-5.4 — Agree. A responsibility and authorization matrix is stronger than job titles, with the main qualification that combined roles in a small shop still need documented competency, independence where required, and customer/regulatory acceptance.

### OPS-003 — What work instructions and checklists are mandatory?

**State:** Exploring

**First-pass answer:** Require controlled instructions where omission,
sequence, revision, or judgment can materially affect safety, legality, quality,
delivery, or recovery. The initial set should cover order/revision review,
setup and program prove-out, machine start/stop, inspection and acceptance,
nonconformance, hazardous energy, maintenance/restart, packaging, shipping,
data backup, and emergency response. Instructions must identify current
revision, prerequisites, hold points, acceptance evidence, and escalation.

**Evidence:**
- [ISO 9001 quality-management overview](https://www.iso.org/iso-9001-quality-management.html) — **Evidence class:** official standards-body guidance. The standard uses controlled documented information and process controls where needed for consistent conforming output.
- [NIOSH Safe Maintenance Guidelines for Robotic Workstations](https://stacks.cdc.gov/view/cdc/158177) — **Evidence class:** primary official technical guidance. It provides task-specific maintenance and intervention controls because informal practice around automation has produced severe injuries.

**Conflicting evidence:** Overly detailed checklists can become stale and
encourage box-checking; too little standard work leaves critical knowledge
tacit. The control depth should follow consequence and variability.

**Confidence:** High on categories; medium on the instruction set before the
process map exists.

**Business-specific validation:** Observe each job family, perform task risk
and quality analyses, test draft instructions with representative operators,
and audit whether records discriminate good from failed execution.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Controlled instructions should follow consequence and variability, but the shop should resist checklist bloat and keep human judgment points explicit rather than burying them in paperwork.

### OPS-004 — How are training and authorization tracked?

**State:** Superseded

**Superseded by:** [HUM-009](people-and-operating-model.md#hum-009--how-are-training-demonstrated-competency-authorization-and-recertification-recorded)

### OPS-005 — What business hours and support commitments are offered?

**State:** Exploring

**First-pass answer:** Offer only hours and response targets supported by named
coverage, escalation, supplier/OEM availability, and customer economics.
Initially publish order/engineering support hours, receiving/shipping windows,
production shifts, emergency-contact scope, acknowledgment target, and the
difference between acknowledgment, diagnosis, workaround, and resolution.
Avoid 24/7 promises until they are staffed and exercised.

**Evidence:**
- [NIST SP 800-34 Rev. 1](https://csrc.nist.gov/pubs/sp/800/34/r1/upd1/final) — **Evidence class:** primary official continuity guidance. It links response and recovery commitments to impact analysis, roles, contact procedures, and tested capability.
- [HSE shift-handover guidance](https://www.hse.gov.uk/humanfactors/topics/shift-handover.htm) — **Evidence class:** primary official safety guidance informed by incidents. Extending coverage across shifts requires prepared, two-way, written and verbal transfer of task-relevant information.

**Conflicting evidence:** Long support windows can win work, but nominal
availability without qualified decision-makers or parts can create misleading
service commitments and fatigue risk.

**Confidence:** Medium; this is a commercial and staffing decision.

**Business-specific validation:** Interview target customers about true
response needs, price each coverage model, run an after-hours drill, and align
the published service level with employment rules and supplier contracts.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Published hours and response targets should reflect staffed, exercised capability; 24/7 or near-real-time promises are misleading without named coverage, fatigue controls, and supplier/OEM support.

### OPS-006 — How are project ownership and handoffs managed?

**State:** Exploring

**First-pass answer:** Give every order one accountable owner while assigning
named owners for commercial, engineering, purchasing, production, quality, and
shipping stages. A handoff is complete only when the receiving owner accepts a
versioned package containing scope, revision, open risks, due dates, material,
tooling/program status, quality holds, customer decisions, and next action;
workflow status alone is not acceptance.

**Evidence:**
- [HSE shift-handover guidance](https://www.hse.gov.uk/humanfactors/topics/shift-handover.htm) — **Evidence class:** primary official guidance. It recommends preparation, two-way communication, written plus verbal records, sufficient time, and cross-checking by the receiver.
- [MIL-HDBK-61B Configuration Management Guidance](https://quicksearch.dla.mil/qsDocDetails.aspx?ident_number=202239) — **Evidence class:** primary official guidance. It treats configuration identification, change control, status accounting, and verification as linked controls.

**Conflicting evidence:** A single project manager improves accountability but
can become a bottleneck or single point of failure; distributed ownership works
only with explicit acceptance and current shared records.

**Confidence:** High.

**Business-specific validation:** Pilot the handoff package on five jobs,
measure missing-information returns and aging, and test continuity when the
primary owner is absent.

**Second-opinion review:** GPT-5.4 — Agree. Single accountable ownership plus explicit receiving acceptance is sound, and the important caveat is to avoid turning one project owner into a hidden single point of failure.

### OPS-007 — Which operational metrics matter?

**State:** Exploring

**First-pass answer:** Use a small balanced set tied to decisions: recordable
and leading safety events; first-pass yield, escapes, rework and scrap; on-time
delivery and promise accuracy; queue/lead time, WIP and constraint throughput;
actual versus estimated labor/material/outside cost; cash conversion and
overdue receivables; downtime/repeat faults; and corrective-action aging.
Always retain denominators and reason codes.

**Evidence:**
- [NIST MEP: Lean Tools Provide Lead Time Reduction](https://www.nist.gov/mep/successstories/2024/lean-tools-provide-lead-time-reduction) — **Evidence class:** observed operating result reported by a government manufacturing network. A manufacturer linked standard work and flow changes to measured WIP, lead-time, and output changes.
- [DOE Operations & Maintenance Best Practices Guide](https://www.pnnl.gov/main/publications/external/technical_reports/PNNL-19634.pdf) — **Evidence class:** primary official guidance. It uses availability, reliability, maintenance completion, backlog, and cost measures to manage equipment performance.

**Conflicting evidence:** Utilization and OEE can expose loss but may reward
overproduction, hidden downtime, or local optimization if detached from demand,
quality, WIP, and the system constraint.

**Confidence:** High on metric families; medium on targets.

**Business-specific validation:** Baseline eight to twelve weeks, verify source
data against job records, and retain only measures that trigger a named review
or corrective decision.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The metric families are right, but utilization or OEE should stay subordinate to safety, flow, quality, and cash because local-efficiency targets are easy to game.

### OPS-008 — What failure modes could stop the business, and what contingency plans are required?

**State:** Exploring

**First-pass answer:** Analyze loss of the constraint machine, power/air/HVAC,
qualified people, critical supplier or outside process, inspection capability,
facility access, OT/ERP/files, cash/credit, permits, and customer communication.
For each intolerable outage define prevention, safe shutdown, alternate
capacity, approved data restore, decision authority, recovery objective, and
customer re-commitment; test the highest-consequence paths.

**Evidence:**
- [NIST SP 800-34 Rev. 1](https://csrc.nist.gov/pubs/sp/800/34/r1/upd1/final) — **Evidence class:** primary official guidance. It provides business-impact analysis, recovery strategies, plan development, exercises, and maintenance.
- [NIST SP 800-82 Rev. 3](https://csrc.nist.gov/pubs/sp/800/82/r3/final) — **Evidence class:** primary official OT guidance. It addresses operational availability, safe states, backups, recovery, communications, and industrial-control dependencies.

**Conflicting evidence:** Redundancy reduces some outages but adds cost,
complexity, common software/configuration risks, and maintenance burden; an
untested backup supplier is not demonstrated continuity.

**Confidence:** High on method; low on ranked failures before the first value
stream and facility are fixed.

**Business-specific validation:** Run a business-impact workshop and four live
exercises: constraint-machine loss, utility outage, supplier loss, and
ERP/program-file restore.

**Second-opinion review:** GPT-5.4 — Agree. The answer properly broadens continuity beyond machines to people, data, cash, permits, and communications; the ranking of top failure modes still has to come from the actual value stream and contracts.

### OPS-009 — What must be documented so the business does not depend on tacit knowledge?

**State:** Exploring

**First-pass answer:** Document the minimum reproducible operating system:
customer/contract decisions, quote assumptions, current drawings and programs,
process plans, setup and inspection methods, machine parameters and backups,
supplier sources and approvals, maintenance/recovery steps, safety controls,
qualifications, deviations, job history, and decision/escalation rules. Each
critical activity also needs a trained backup who can execute it.

**Evidence:**
- [MIL-HDBK-61B Configuration Management Guidance](https://quicksearch.dla.mil/qsDocDetails.aspx?ident_number=202239) — **Evidence class:** primary official guidance. Configuration identification, change control, status accounting, and audits preserve what the approved configuration actually is.
- [HSE shift-handover guidance](https://www.hse.gov.uk/humanfactors/topics/shift-handover.htm) — **Evidence class:** primary official guidance informed by incidents. Written records plus two-way transfer reduce context loss across people and shifts.

**Conflicting evidence:** Documentation cannot capture all craft judgment and
can decay; apprenticeship and supervised practice remain necessary. Conversely,
"tribal knowledge" is not a recoverable business control.

**Confidence:** High.

**Business-specific validation:** Select ten critical tasks, ask a trained
alternate to execute from the controlled record, and close every ambiguity or
missing dependency found.

**Second-opinion review:** GPT-5.4 — Agree. A minimum reproducible operating system plus trained backups is the right standard; documentation alone is not enough, but tacit knowledge alone is not a recoverable control.

## Production Planning and Execution

### OPS-010 — What information must be complete before an order is accepted, released to engineering, or released to production?

**State:** Exploring

**First-pass answer:** Use three distinct gates. Acceptance requires defined
scope, customer/revision identity, quantity, price, dates, terms, quality and
regulatory clauses, feasibility, and explicit assumptions. Engineering release
requires resolved technical inputs and approval authority. Production release
requires an approved configuration, routing, material, capacity, tooling,
programs, inspection plan, outside-process plan, qualifications, and cleared
holds; missing items need an authorized conditional-release record.

**Evidence:**
- [ISO 9001 quality-management overview](https://www.iso.org/iso-9001-quality-management.html) — **Evidence class:** official standards-body guidance. ISO 9001 addresses customer requirements, process control, documented information, external providers, and controlled release.
- [MIL-HDBK-61B Configuration Management Guidance](https://quicksearch.dla.mil/qsDocDetails.aspx?ident_number=202239) — **Evidence class:** primary official guidance. Baselines and controlled changes prevent work from proceeding against ambiguous or superseded configurations.

**Conflicting evidence:** Strict gates can delay urgent work; informal release
is faster but transfers uncertainty and rework to the shop. Conditional release
is credible only when residual risk and authority are explicit.

**Confidence:** High.

**Business-specific validation:** Test the three checklists against recent
representative RFQs and pilot jobs; record which missing fields actually caused
delay, scrap, or commercial dispute.

**Second-opinion review:** GPT-5.4 — Agree. Separate acceptance, engineering release, and production release gates are justified, and conditional release is only credible when residual risk, missing inputs, and approval authority are explicit.

### OPS-011 — How are routing, job travelers, revisions, material, tooling, programs, inspection, outside processes, packaging, and required approvals controlled for each job?

**State:** Exploring

**First-pass answer:** Issue one versioned job packet or electronic traveler
linked to the approved product baseline. It should identify every operation,
input revision, lot/serial material, tooling and program version, inspection
and hold points, outside-process purchase/return evidence, quantity disposition,
packaging, signatures, and status. Changes must preserve who approved what,
when, why, and which physical units were affected.

**Evidence:**
- [MIL-HDBK-61B Configuration Management Guidance](https://quicksearch.dla.mil/qsDocDetails.aspx?ident_number=202239) — **Evidence class:** primary official configuration guidance. It connects identification, change control, status accounting, and verification across a configuration lifecycle.
- [ISO 9001 quality-management overview](https://www.iso.org/iso-9001-quality-management.html) — **Evidence class:** official standards-body guidance. Controlled documented information, traceability where required, external-provider control, and release records are core quality-system mechanisms.

**Conflicting evidence:** Paper travelers remain resilient and visible but can
be illegible or stale; digital systems improve validation and traceability but
can fail, be misconfigured, or encourage blind status updates.

**Confidence:** High.

**Business-specific validation:** Trace one finished part backward and forward
through every operation and change, then perform the same test during a system
outage.

**Second-opinion review:** GPT-5.4 — Agree. One versioned traveler or packet tied to the approved baseline is the right control, with the practical qualification that paper and digital methods each need an outage-tolerant fallback.

### OPS-012 — How are finite capacity, bottlenecks, setup families, material availability, maintenance, outside-process queues, and due dates used to release and dispatch work?

**State:** Exploring

**First-pass answer:** Build a resource- and calendar-constrained schedule from
routing times, setup families, qualified labor, machine/tool capacity, planned
maintenance, confirmed material, outside-process lead time, and customer due
date. Release only work with a credible path through the constraint; dispatch
the next operation using due-date risk, constraint protection, setup economy,
and material readiness, with human review of exceptions. Optimization or AI
schedulers can support dispatch, but they should not make unattended release
decisions until master data, exception handling, and recovery behavior are
proven under live conditions.

**Evidence:**
- [GAO Schedule Assessment Guide](https://www.gao.gov/assets/gao-16-89g.pdf) — **Evidence class:** primary official guidance. A credible schedule logically sequences work, assigns resources, reflects calendars and constraints, analyzes critical paths, and updates actual progress.
- [NIST MEP: Lean Tools Provide Lead Time Reduction](https://www.nist.gov/mep/successstories/2024/lean-tools-provide-lead-time-reduction) — **Evidence class:** observed operating result. Flow, standard work, and smaller batches were associated with large measured WIP and lead-time reductions in a manufacturer.

**Conflicting evidence:** Optimization and AI schedulers can generate useful
sequences, but research and demonstrations do not establish dependable
autonomous dispatch under bad master data, breakdowns, expedites, and human
constraints.

**Confidence:** High on finite-capacity principles; medium on the dispatch rule.

**Business-specific validation:** Calibrate setup/run distributions and
supplier queues from actuals, shadow-schedule live jobs for eight weeks, and
compare promise accuracy, WIP, and constraint starvation.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Finite-capacity release and dispatch are well supported, but autonomous AI scheduling claims are broader than current shop-floor evidence; keep planners in the loop until master data, exceptions, and recovery behavior are proven live.

### OPS-013 — What WIP limits, queue rules, expedite authority, and exception signals prevent the shop from starting more work than it can finish reliably?

**State:** Exploring

**First-pass answer:** Set an initial WIP cap by value stream or constraint from
required throughput and observed flow time, then adjust experimentally. New work
enters only when capacity is released; use FIFO by default, protect the
constraint from starvation, and allow expedites only through one named
authority with recorded customer and displaced-job impact. Signal blocked,
aging, held, missing-input, and over-cap conditions visibly.

**Evidence:**
- [NIST MEP: Lean Tools Provide Lead Time Reduction](https://www.nist.gov/mep/successstories/2024/lean-tools-provide-lead-time-reduction) — **Evidence class:** observed operating result. A real manufacturer reported WIP falling from 105 to 5 units, lead time falling 50%, and output increasing after flow and standard-work changes.
- [Execution of Pull System and CONWIP Using Kanban](https://doaj.org/article/4da9d14132c8466ba028b0ea3bf335af) — **Evidence class:** empirical case study. A bearing-manufacturing implementation reported lower WIP and replenishment times after recalibrating its pull/WIP controls.

**Conflicting evidence:** A cap set too low starves resources and misses demand;
one set too high merely labels congestion. Published case results are not
portable without local variability and routing data.

**Confidence:** Medium-high.

**Business-specific validation:** Pilot caps on one product family, change one
parameter at a time, and monitor throughput, aging, expedite frequency,
starvation, and on-time delivery.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. WIP caps, visible exception signals, and single-point expedite authority are sound, but published Kanban or CONWIP gains are not portable without local variability, routing, and demand data.

### OPS-014 — How are partial quantities, first articles, holds, nonconformances, rework, scrap, and replacement work reflected in job status and schedule commitments?

**State:** Exploring

**First-pass answer:** Track quantity and disposition by physical lot/serial and
operation, not one job-level percent complete. Separate good, pending
inspection, first-article, on hold, nonconforming, rework, scrap, replacement,
and shipped quantities; preserve links to cause and authorization. Recalculate
remaining material, capacity, outside processing, inspection, and promised date
whenever disposition changes.

**Evidence:**
- [ISO 9001 quality-management overview](https://www.iso.org/iso-9001-quality-management.html) — **Evidence class:** official standards-body guidance. The standard covers controlled production, identification/traceability where needed, release, and control of nonconforming outputs.
- [GAO Schedule Assessment Guide](https://www.gao.gov/assets/gao-16-89g.pdf) — **Evidence class:** primary official guidance. Schedules should use actual progress, valid logic, and updated forecasts rather than unsupported percent-complete claims.

**Conflicting evidence:** A simple job status is easier to operate, but it hides
mixed dispositions and creates false available-to-ship and completion signals.
Fine-grained status is useful only if updates occur at the work.

**Confidence:** High.

**Business-specific validation:** Model three scenarios—failed first article,
partial outside-process rejection, and late scrap—and verify inventory, cost,
capacity, and customer dates reconcile.

**Second-opinion review:** GPT-5.4 — Agree. Quantity and disposition should be tracked at lot, serial, and operation level; one job-level percent complete is too weak for scheduling, inventory, or customer-commitment control.

### OPS-015 — What shift, employee, automation, supplier, and customer handoff information must be recorded so work does not lose context?

**State:** Exploring

**First-pass answer:** Record job/configuration identity, completed and current
operation, exact physical location and quantity, machine/robot state, offsets
and program version, quality results and holds, material/tool condition, active
permits or isolation, supplier/customer promises, abnormal events, open risks,
next safe action, owner, and due time. High-risk handoffs require direct
two-way acknowledgment, not a passive log entry.

**Evidence:**
- [HSE shift-handover guidance](https://www.hse.gov.uk/humanfactors/topics/shift-handover.htm) — **Evidence class:** primary official guidance informed by major incidents. It recommends preparation, face-to-face two-way exchange, written and verbal records, and receiver cross-checking.
- [NIOSH Safe Maintenance Guidelines for Robotic Workstations](https://stacks.cdc.gov/view/cdc/158177) — **Evidence class:** primary official technical guidance. Automation maintenance and troubleshooting require explicit control of machine state, hazards, and personnel.

**Conflicting evidence:** Automated event logs provide accurate timestamps but
usually omit intent, physical condition, and unresolved judgment; narrative
handoffs can omit data or become inconsistent.

**Confidence:** High.

**Business-specific validation:** Observe ten real handoffs across shift,
automation, supplier, and customer boundaries; test whether a fresh qualified
person can safely continue without contacting the originator.

**Second-opinion review:** GPT-5.4 — Agree. The listed handoff facts are the right minimum, and high-risk transfers do need two-way acknowledgment because event logs rarely capture intent, abnormal conditions, or unresolved judgment.

### OPS-016 — How are actual setup, run, attended labor, material, tooling, outside service, inspection, downtime, scrap, and rework captured at job close?

**State:** Exploring

**First-pass answer:** Capture actuals at the event or transaction source, with
job, operation, resource, employee or automation, start/stop, quantity,
material lot, tooling consumption, purchase/service cost, inspection effort,
downtime reason, and scrap/rework disposition. At close, reconcile traveler,
inventory, time, purchase receipts, quality records, and shipment; freeze the
baseline while allowing auditable corrections.

**Evidence:**
- [GAO Schedule Assessment Guide](https://www.gao.gov/assets/gao-16-89g.pdf) — **Evidence class:** primary official guidance. Credible forecasting depends on status dates, actual progress, resource use, and traceable schedule updates.
- [DOE Operations & Maintenance Best Practices Guide](https://www.pnnl.gov/main/publications/external/technical_reports/PNNL-19634.pdf) — **Evidence class:** primary official guidance. Work-order histories and consistent labor, material, failure, downtime, and cost data support reliability and economic decisions.

**Conflicting evidence:** Detailed manual collection burdens operators and
creates false precision; controller/ERP automation reduces entry but can assign
idle time, shared labor, consumables, or scrap to the wrong job.

**Confidence:** High on data categories; medium on economical granularity.

**Business-specific validation:** Time-study representative jobs, reconcile
five closes to payroll/inventory/invoices/controller logs, and retain fields
whose accuracy changes quoting, scheduling, or corrective action.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Event-source actuals and close reconciliation are the right control model, but data capture should stop short of false precision or operator burden that degrades accuracy.

### OPS-017 — Which daily, weekly, and monthly reviews turn safety, quality, delivery, cost, cash, capacity, and maintenance evidence into assigned corrective action?

**State:** Exploring

**First-pass answer:** Use a daily tier for immediate safety, quality, due-date,
constraint, staffing, and equipment exceptions; a weekly review for schedule,
WIP, supplier, maintenance, cash, and corrective-action recovery; and a monthly
management review for trends, capacity/capital, recurring causes, customer
risk, and policy changes. Every exception needs an owner, due date, containment,
root-cause threshold, and effectiveness check.

**Evidence:**
- [NIST MEP: Success Lean Process Implementation](https://www.nist.gov/mep/successstories/2024/success-lean-process-implementation) — **Evidence class:** observed operating result reported by a government manufacturing network. Visual management, standard work, flow, and bottleneck focus were associated with measured WIP and output improvement.
- [ISO 9001 quality-management overview](https://www.iso.org/iso-9001-quality-management.html) — **Evidence class:** official standards-body guidance. Performance evaluation, internal review, corrective action, and continual improvement are management-system controls, not merely reporting.

**Conflicting evidence:** More meetings do not improve performance; reviews
without reliable data or closure discipline become status theater. Conversely,
fully automated dashboards do not assign accountable corrective action.

**Confidence:** High on cadence and closure mechanics; medium on meeting scope.

**Business-specific validation:** Pilot the three tiers for eight weeks, audit
action aging and recurrence, and remove metrics or meetings that produce no
decision.

**Second-opinion review:** GPT-5.4 — Agree. Daily/weekly/monthly review tiers are appropriate as long as each exception produces an owner, due date, containment, and effectiveness check rather than another passive dashboard.

## Related Topics

- [People and Operating Model](people-and-operating-model.md)
- [Business and Commercial Strategy](business-and-commercial-strategy.md)
- [Inventory, Tooling, and Asset Management](inventory-tooling-and-asset-management.md)
- [Maintenance, Reliability, and Continuity](maintenance-reliability-and-continuity.md)
- [Manufacturing Processes and Quality](manufacturing-processes-and-quality.md)
