# Maintenance, Reliability, and Continuity Questions

## Purpose

Questions about keeping equipment, utilities, controls, data, suppliers, and
qualified operating capacity available. Reliability is treated as a business
capability because downtime can erase the apparent advantage of inexpensive
equipment or aggressive automation.

These are discovery questions, not approved requirements. Question IDs are
permanent and canonical in this file.

## Asset and Maintenance Strategy

### REL-001 — Which machines, utilities, controls, software, tools, gauges, and supplier capabilities are critical to revenue, safety, quality, or delivery?

**State:** Exploring

**First-pass answer:** Classify an asset or external capability as critical when
its loss can create an intolerable safety or compliance condition, stop the
constraint operation, invalidate inspection, corrupt recoverable data, or miss
a customer commitment before an approved fallback can be activated. Use an
asset/dependency register with consequence, maximum tolerable outage, available
fallback, recovery time, owner, and evidence; do not classify only by purchase
price.

**Evidence:**
- [DOE Operations & Maintenance Best Practices Guide](https://www.pnnl.gov/main/publications/external/technical_reports/PNNL-19634.pdf) — **Evidence class:** primary official guidance. It organizes O&M around equipment importance, condition, performance, and life-cycle cost rather than treating every asset equally.
- [NIST SP 800-34 Rev. 1, Contingency Planning Guide](https://csrc.nist.gov/pubs/sp/800/34/r1/upd1/final) — **Evidence class:** primary official guidance. Business-impact analysis links system dependencies and outage effects to recovery priorities.

**Conflicting evidence:** Criticality matrices can imply precision that the
underlying outage and fallback data do not support; supplier concentration and
single-person knowledge may be more critical than the machine itself.

**Confidence:** High on the method; low on which assets qualify before the
actual product mix, contracts, layout, and fallbacks are known.

**Business-specific validation:** Walk the value stream with operations,
quality, safety, IT/OT, maintenance, and purchasing; simulate loss of each
candidate for one shift and one supplier lead time.

**Second-opinion review:** GPT-5.4 — Agree. Consequence-based criticality is the right frame; the main qualification is that single-source suppliers, software, and single-person knowledge can be more critical than machine price.

### REL-002 — What preventive, condition-based, predictive, calibration, cleaning, lubrication, and statutory maintenance is required for each critical asset?

**State:** Exploring

**First-pass answer:** Build an asset-specific plan from the current OEM manual,
applicable law and inspection obligations, safety functions, calibration needs,
known failure modes, and local operating conditions. Start with prescribed
cleaning, inspection, lubrication, backup, proof-test, and calibration tasks;
add condition monitoring only where a measurable precursor and a response
window have been demonstrated.

**Evidence:**
- [Haas VF/VM maintenance schedule](https://www.haascnc.com/service/online-manuals/vf-vm-series---service-manual/vf-vm---maintenance.html) — **Evidence class:** OEM technical data. The deployed-machine schedule distinguishes daily, weekly, monthly, semiannual, and annual tasks by subsystem.
- [DOE Operations & Maintenance Best Practices Guide](https://www.pnnl.gov/main/publications/external/technical_reports/PNNL-19634.pdf) — **Evidence class:** primary official guidance with operating examples. It distinguishes reactive, preventive, predictive, and reliability-centered approaches and condition-monitoring methods.

**Conflicting evidence:** Predictive-maintenance marketing often promises broad
failure prediction, but sensors cannot predict random failures or failures with
no detectable precursor; excessive time-based maintenance can also introduce
defects.

**Confidence:** High on the planning approach; medium on task selection until
exact assets and manuals are available.

**Business-specific validation:** Obtain model/serial-specific manuals,
warranty terms, insurer and authority requirements, calibration intervals, and
six to twelve months of condition and failure data.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. OEM and legal baselines are sound, but predictive or AI-driven tasks are justified only where a measurable precursor and actionable response window are proven on that specific asset.

### REL-003 — How are maintenance intervals adjusted using duty cycle, environment, failure history, condition evidence, and manufacturer guidance?

**State:** Exploring

**First-pass answer:** Treat the OEM interval as the controlled baseline unless
the manufacturer or a qualified engineering review permits otherwise. Shorten
it for high duty, contamination, heat, vibration, safety significance, or
adverse trend; lengthen it only through documented failure/condition evidence,
risk review, and change approval. Record meter basis, last/next due, rationale,
and rollback trigger.

**Evidence:**
- [Haas lathe maintenance schedule](https://www.haascnc.com/service/troubleshooting-and-how-to/reference-documents/lathe-maintenance-schedule.alarm%25253Dgeneral_9959-0000) — **Evidence class:** OEM technical data. It varies tasks by elapsed period and operating condition rather than supplying one universal interval.
- [DOE Operations & Maintenance Best Practices Guide](https://www.pnnl.gov/main/publications/external/technical_reports/PNNL-19634.pdf) — **Evidence class:** primary official guidance. It supports condition- and reliability-centered decisions based on failure consequence and measured equipment condition.

**Conflicting evidence:** Calendar compliance is easy to audit but may
over-maintain lightly used equipment; purely condition-based work may miss
hidden or age-related failures and may conflict with warranty or law.

**Confidence:** High on controlled adjustment; medium on any particular
interval.

**Business-specific validation:** Establish runtime/cycle counters,
environmental limits, failure codes, oil/vibration/thermal baselines, and obtain
written OEM approval before extending warranty-sensitive intervals.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Using the OEM interval as the controlled baseline is prudent; interval extension needs evidence, change control, and warranty/regulatory review because many hidden or age-related failures are not forecast by condition data.

### REL-004 — Which maintenance work requires employees, OEM service, independent specialists, integrators, electricians, riggers, calibration providers, or other qualified parties?

**State:** Exploring

**First-pass answer:** Assign work by hazard, legal qualification, proprietary
access, lifting/energized-work exposure, calibration scope, and ability to
validate the restored safety and process function. Routine trained-operator
care can remain internal; safety-circuit changes, energized electrical work,
rigging, regulated inspections, accredited calibration, and warranty-controlled
repairs normally require specifically qualified parties.

**Evidence:**
- [OSHA 29 CFR 1910.147](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.147) — **Evidence class:** binding U.S. regulation. It distinguishes affected and authorized employees and requires training for hazardous-energy control.
- [NIOSH Safe Maintenance Guidelines for Robotic Workstations](https://stacks.cdc.gov/view/cdc/158177) — **Evidence class:** primary official technical guidance based on workplace hazards. It treats robot maintenance, programming, and troubleshooting as specialized high-risk activities.

**Conflicting evidence:** An OEM technician is not automatically qualified for
site electrical, rigging, or safety validation, while a competent independent
provider may be fully capable even if the OEM prefers proprietary service.

**Confidence:** High on qualification-based assignment; medium on role
boundaries before jurisdiction and equipment contracts are known.

**Business-specific validation:** Create a task-to-qualification matrix reviewed
by the safety professional, electrician/authority having jurisdiction,
calibration owner, insurer, OEM, and equipment integrator.

**Second-opinion review:** GPT-5.4 — Agree. Qualification should follow hazard, legal scope, and validation duty; OEM involvement does not by itself satisfy site electrical, rigging, calibration, or safety-release responsibilities.

### REL-005 — Which critical spares, backups, consumables, parameter files, licenses, manuals, and recovery tools must be held or rapidly available?

**State:** Exploring

**First-pass answer:** Hold or contract rapid access for items whose verified
replenishment time exceeds the maximum tolerable outage, especially
single-source safety components, controls, drives, sensors, seals, filters,
lubricants, workholding, calibrated masters, restore media, machine parameters,
PLC/robot programs, licenses, keys, and current manuals. Store tested backups
offline and separately from the machine; periodically prove restoration.

**Evidence:**
- [NIST SP 800-82 Rev. 3](https://csrc.nist.gov/pubs/sp/800/82/r3/final) — **Evidence class:** primary official OT-security guidance. It addresses configuration management, backups, recovery, removable media, and availability constraints of operational technology.
- [DOE Operations & Maintenance Best Practices Guide](https://www.pnnl.gov/main/publications/external/technical_reports/PNNL-19634.pdf) — **Evidence class:** primary official guidance. Its inventory and maintenance-planning practices connect spare availability to equipment criticality and downtime.

**Conflicting evidence:** Stocking every possible component ties up cash and
invites obsolescence; vendor "next-day" promises may fail during common-cause
events or after product discontinuation.

**Confidence:** High on the lead-time-versus-outage rule; medium on quantities.

**Business-specific validation:** Obtain written lead times and end-of-life
status, price outage scenarios, conduct a bare-metal/controller restore test,
and set min/max levels from consumption and common-mode risk.

**Second-opinion review:** GPT-5.4 — Agree. Lead time versus maximum tolerable outage is the right decision rule, and the key correction is to prove backup restore and parameter/license recovery rather than assuming stocked parts alone create continuity.

## Reliability and Recovery

### REL-006 — Which failure, downtime, mean-time-between-failure, mean-time-to-repair, repeat-fault, and maintenance-compliance measures are decision-useful?

**State:** Exploring

**First-pass answer:** Track event-level timestamps and causes first, then use
availability, planned/unplanned downtime, constraint lost hours, MTTR, repeat
faults, schedule compliance, overdue critical work, first-time fix, and
production impact by asset. Use MTBF only for comparable operating populations
and stated exposure units; pair every average with distribution, censoring, and
failure-mode detail.

**Evidence:**
- [NIST/SEMATECH e-Handbook of Statistical Methods: reliability](https://www.itl.nist.gov/div898/handbook/apr/apr.htm) — **Evidence class:** primary official statistical guidance. Reliability estimates depend on distributional assumptions, test design, censoring, and failure definition.
- [DOE Operations & Maintenance Best Practices Guide](https://www.pnnl.gov/main/publications/external/technical_reports/PNNL-19634.pdf) — **Evidence class:** primary official guidance. It recommends performance measures including availability, reliability, work-order backlog, and preventive-maintenance completion.

**Conflicting evidence:** A rising MTBF can reflect fewer recorded failures,
lower utilization, or aggregation rather than improvement; high maintenance
compliance can mean the wrong work was completed on time.

**Confidence:** High.

**Business-specific validation:** Define failure, downtime start/stop,
planned-versus-unplanned, exposure unit, and reason codes; audit a sample
against controller alarms, labor records, and production loss.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Event-level data before summary KPIs is correct; MTBF is easy to misuse unless exposure units, censoring, and failure definitions are controlled.

### REL-007 — How are serviceability, parts availability, control obsolescence, vendor support, and repair lead time included in equipment selection and replacement?

**State:** Exploring

**First-pass answer:** Score total recovery capability, not purchase price:
diagnostic access, modular replacement, safe access, local skills, documented
interfaces, backup/restore rights, parts availability and end-of-life notice,
service response, loaners, cybersecurity support, and demonstrated repair time.
Contract critical commitments and include a replacement trigger before support
or controls become unserviceable.

**Evidence:**
- [NIST SP 800-82 Rev. 3](https://csrc.nist.gov/pubs/sp/800/82/r3/final) — **Evidence class:** primary official guidance. It identifies long OT life cycles, legacy components, vendor dependence, patch constraints, and availability as selection and life-cycle risks.
- [DOE Operations & Maintenance Best Practices Guide](https://www.pnnl.gov/main/publications/external/technical_reports/PNNL-19634.pdf) — **Evidence class:** primary official guidance. Life-cycle O&M cost and maintainability are treated as material acquisition decisions.

**Conflicting evidence:** Proprietary integrated equipment may deliver better
initial performance and support but create lock-in; open components can improve
replaceability while shifting integration responsibility to the owner.

**Confidence:** High on criteria; medium on weights.

**Business-specific validation:** During procurement, run a timed diagnostic and
backup/restore demonstration, quote five- and ten-year spares/service, contact
reference users, and price a control retrofit and one-week outage.

**Second-opinion review:** GPT-5.4 — Agree. Maintainability, obsolescence, and repairability belong in selection and replacement decisions, though proprietary platforms may still be acceptable where contracted support materially lowers recovery risk.

### REL-008 — What safe diagnostic, repair, test, recommissioning, and production-release evidence is required after maintenance or a failure?

**State:** Exploring

**First-pass answer:** Require a controlled work record, verified energy
control, fault diagnosis, as-found/as-left condition, parts and software
changes, independent inspection where warranted, guard/interlock/safety-function
proof tests, calibration or geometry checks, dry run, first-piece verification,
and named production release. A reset or disappearance of an alarm is not
release evidence.

**Evidence:**
- [OSHA 29 CFR 1910.147](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.147) — **Evidence class:** binding U.S. regulation. Release from lockout requires inspection, employee checks, notification, and controlled re-energization.
- [ISO 12100:2010](https://www.iso.org/standard/51528.html) — **Evidence class:** official consensus-standard scope. It requires lifecycle hazard identification, risk reduction, documentation, and verification.

**Conflicting evidence:** OEM automated self-tests can be useful, but they may
not test site integration, tooling, altered parameters, product quality, or all
safety channels.

**Confidence:** High on the release framework; medium on test content.

**Business-specific validation:** Develop asset-specific return-to-service
checklists with the OEM/integrator, safety engineer, quality owner, and process
owner; execute seeded-fault acceptance tests before production.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Alarm disappearance is not release evidence; return to service needs safety, functional, and quality proof, and the cited ISO page supports the lifecycle principle but not clause-level test content.

### REL-009 — What alternate machine, outside supplier, manual fallback, spare utility, data recovery, or customer-communication plan exists for each critical dependency?

**State:** Exploring

**First-pass answer:** Every critical dependency needs one documented,
executable response: safe shutdown/dwell, alternate qualified capacity,
approved outside supplier, reduced manual mode, spare utility, tested data
restore, inventory buffer, or prompt customer re-commitment. Record activation
criteria, decision owner, capacity/quality limits, required approvals, contact
paths, recovery sequence, and expected time.

**Evidence:**
- [NIST SP 800-34 Rev. 1](https://csrc.nist.gov/pubs/sp/800/34/r1/upd1/final) — **Evidence class:** primary official guidance. It requires business-impact analysis, recovery strategies, plans, testing, training, and maintenance.
- [NIST SP 800-82 Rev. 3](https://csrc.nist.gov/pubs/sp/800/82/r3/final) — **Evidence class:** primary official OT guidance. It emphasizes availability, safe operation, backups, alternate communications, and recovery for industrial systems.

**Conflicting evidence:** Nominal alternate capacity is not continuity if it
lacks tooling, programs, approval, material, inspection, people, or transport;
manual fallback can introduce greater safety and quality risk.

**Confidence:** High on required plan content; low on feasibility until tested.

**Business-specific validation:** Exercise the highest-impact machine, utility,
supplier, and data-loss scenarios using real contacts, files, tooling, sample
parts, and customer notification drafts.

**Second-opinion review:** GPT-5.4 — Agree. Every critical dependency needs a tested fallback path, and nominal alternate suppliers or manual workarounds do not count until tooling, quality, files, logistics, and approvals are demonstrated.

### REL-010 — How often must continuity and recovery plans be exercised, and what results require capital, process, supplier, or inventory changes?

**State:** Exploring

**First-pass answer:** Test at least annually and after material changes, with
more frequent tabletop or component tests for high-impact dependencies and a
full operational exercise for the most critical recovery paths. Trigger action
when achieved recovery exceeds the approved objective, data or safety evidence
is incomplete, capacity/quality is inadequate, contacts or backups fail, or the
same weakness recurs.

**Evidence:**
- [NIST SP 800-34 Rev. 1](https://csrc.nist.gov/pubs/sp/800/34/r1/upd1/final) — **Evidence class:** primary official guidance. It calls for testing, training, exercises, plan maintenance, and corrective actions, with frequency based on system impact and change.
- [CISA Cybersecurity Performance Goals](https://www.cisa.gov/cyber-guidance/cybersecurity-performance-goals-cpgs) — **Evidence class:** primary official guidance. It includes tested backups and incident-response planning as baseline protections for critical operations.

**Conflicting evidence:** Annual compliance exercises can pass while avoiding
the hardest restore or production-transfer step; frequent full shutdown tests
may themselves create safety and delivery risk.

**Confidence:** Medium-high; frequency must be consequence-based.

**Business-specific validation:** Set recovery-time and recovery-point
objectives, then perform one tabletop, one technical restore, and one
alternate-production exercise before approving capital or inventory levels.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Annual and post-change exercising is a sound minimum, but the most credible evidence comes from technical restore and alternate-production drills that test the hardest step, not just tabletop review.

### REL-011 — What safe dwell state, alarm escalation, on-call response time, remote-diagnostic boundary, and site-response capability are required for reduced-attendance or unattended operation?

**State:** Exploring

**First-pass answer:** Unattended operation is justified only where the
equipment can detect defined abnormal conditions, transition without human
action to a validated safe dwell, preserve fire/life-safety functions, and
remain safe for the entire worst-case response time. Remote access may diagnose
and request controlled actions but should not bypass safeguards, lockout, local
clearance, or physical verification, and AI/anomaly-detection outputs should be
treated as advisory until the installed scenario has been validated. Response
targets follow hazard escalation and damage progression, not staffing
convenience.

**Evidence:**
- [NIST SP 800-82 Rev. 3](https://csrc.nist.gov/pubs/sp/800/82/r3/final) — **Evidence class:** primary official OT guidance. It addresses remote access, least privilege, monitoring, segmentation, fail-safe operation, and availability.
- [NIOSH Safe Maintenance Guidelines for Robotic Workstations](https://stacks.cdc.gov/view/cdc/158177) — **Evidence class:** primary official technical guidance. It documents the special danger of intervention, troubleshooting, and unexpected movement inside automated workstations.

**Conflicting evidence:** Vendor remote-monitoring and AI anomaly-detection
features can shorten diagnosis, but demonstrations and alerts do not prove safe
autonomous recovery or timely physical response.

**Confidence:** Medium; the answer is equipment- and hazard-specific.

**Business-specific validation:** Conduct a formal unattended-operation risk
assessment with the OEM/integrator, fire authority, insurer, safety
professional, OT security owner, and responders; test loss of network, power,
sensor, suppression, and on-call acknowledgment.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The answer correctly treats remote diagnostics and AI anomaly detection as supportive rather than authoritative; unattended operation still needs asset-specific hazard review, worst-case response proof, and explicit attended restart criteria.

## Related Topics

- [Suppliers and Procurement](suppliers-and-procurement.md)
- [Facility, Site, Utilities, and Layout](facility-site-utilities-and-layout.md)
- [Insurance and Risk Financing](insurance-and-risk-financing.md)
- [Inventory, Tooling, and Asset Management](inventory-tooling-and-asset-management.md)
- [Data, AI, and Platform](data-ai-and-platform.md)
