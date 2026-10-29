# Machine Maintenance and Care

## Problem

Production depends on more than a machine being powered on or unreserved.
Missed oiling, cleaning, inspections, or service can compromise reliability,
accuracy, and safety. A generic reminder cannot establish that the correct
procedure was performed on the correct machine.

## Objective

Maintain every production and support machine correctly throughout its
lifecycle using applicable, approved care procedures, attributable completion
evidence, and explicit production-eligibility consequences.

This specifies future behavior. It does not implement maintenance automation
or prescribe servicing instructions for any particular machine.

## Terminology

- **Asset:** A uniquely identified machine or controlled support-equipment
  unit, whether owned, leased, or otherwise used for operations.
- **Maintenance plan:** Revisioned applicable procedures, triggers, required
  competence, evidence, and availability/return-to-service rules for an asset.
- **Work order:** A particular maintenance intervention, including routine
  cleaning or lubrication when required by the plan.
- **Production hold:** Software ineligibility for specified work; not an
  energy-isolating device, protective stop, or permission to enter a hazard.

## Inputs

Asset identity, manufacturer/model/serial/configuration, manuals and service
bulletins, approved local procedures, duty/environment, current condition,
meter history and freshness, prior work orders, inspection/calibration status,
trained-person availability, consumables/spares, and production dependencies.

## Rules

1. **MC-01 - Complete coverage.** Register all production and support
   machines, including relevant saws, compressors, extraction, coolant/chip
   equipment, robots, washers, and inspection equipment, not only CNCs.
   Assign an accountable owner. Each asset needs an approved applicable plan
   or an explicit reviewed disposition; absence of a plan is not evidence
   that no care is required.
2. **MC-02 - Correct procedure and applicability.** Associate tasks with
   applicable OEM instructions, machine options/configuration, document
   revisions, service bulletins, and approved site obligations. Resolve
   conflicting guidance through qualified review. Do not copy one machine's
   intervals or lubricant into another's plan by default.
3. **MC-03 - Care categories.** Evaluate required lubrication/oiling/greasing,
   chip and residue removal, cleaning, coolant/fluid care, filtration,
   inspections, leak/wear checks, guards/interlocks, and scheduled servicing
   as applicable. Define approved products, compatibility, method, acceptance
   evidence, and restrictions in the task procedure, not generic software
   defaults. Any exception requires an attributable approved rationale.
4. **MC-04 - Due logic.** Support calendar, runtime, cycle, condition,
   event-based, and combined triggers according to the approved plan. Record
   the applicable time zone, boundary semantics, meter units, baseline, and
   whether any trigger or a specified combination makes the task due.
   Thresholds must not be extended by rounding, rescheduling, or reminders.
5. **MC-05 - Uncertain usage.** Detect stale/unavailable readings, counter
   resets/replacements, gaps, and conflicting meters. Do not assume zero
   usage. Apply only an approved conservative fallback; otherwise report
   unknown eligibility and hold affected work pending review.
6. **MC-06 - Plan before the interruption.** Forecast due work across planned
   production, including jobs that would cross a required limit. Reserve
   downtime, trained personnel, safe-access windows, consumables, spares, and
   required external service. Propagate shared-support outages to dependent
   equipment/jobs and produce need-by demand in the existing inventory model.
7. **MC-07 - Evidence of actual work.** Record asset/task/procedure revision,
   performer and authorization, actual timestamps and meter readings,
   applicable measurements, findings, consumed products/quantities/lot
   references where controlled, and required evidence. Automated reminders,
   automatic lubrication commands, elapsed downtime, and checked boxes alone
   are not proof that the required outcome occurred.
8. **MC-08 - Safe execution.** Require the approved task-specific access,
   hazardous-energy, stored-energy, environmental, and competence procedures.
   Production scheduling cannot authorize servicing or bypass validated zone
   controls. Keep work records distinct from verified isolation, controlled
   restoration, deliberate reset, and separate restart authorization.
9. **MC-09 - Lifecycle and interruption.** Distinguish planned, due, in
   progress, awaiting parts, interrupted, completed-pending-verification,
   failed, and released work. A partially completed procedure, failed finding,
   or interruption must leave explicit remaining actions and eligibility,
   not silently reset the next-due baseline.
10. **MC-10 - Return to service.** Apply required post-service inspections,
    functional checks, calibration/qualification, and authorized release.
    Correct maintenance does not automatically make an independently
    unqualified gauge, damaged fixture, or failed safety device available.
11. **MC-11 - Holds and exceptions.** Overdue mandatory tasks, unacceptable
    findings, missing required evidence, unknown applicability, or unresolved
    condition must block affected production eligibility. Classify advisory
    tasks explicitly. Any permitted deferral needs a qualified authority,
    reason, risk basis, expiry, and bounded scope; legally/OEM-prohibited or
    safety-critical holds cannot be waived by ordinary production approval.
12. **MC-12 - Durable history.** Persist schedules, baselines, work orders,
    evidence, holds, and releases across restarts. Deduplicate imported
    readings and task generation; retries must not create duplicate completed
    service. Corrections preserve original entries, actor, reason, and time.
13. **MC-13 - Feedback into planning.** Failed lubrication, abnormal wear,
    loss of accuracy, or recurring defects must create reviewable corrective
    work and identify affected production since the last known acceptable
    state. Do not automatically rewrite approved intervals or remove holds
    because a later observation looks normal.
14. **MC-14 - Visible accountability.** Show upcoming/overdue/blocked work,
    responsible people, limiting evidence, missing resources, affected jobs,
    and release status. Notifications and escalation remain idempotent and
    auditable; acknowledging a notification does not complete maintenance.

## Assumptions

- Existing asset identity, custody, inventory, qualification, and audit
  requirements remain authoritative; this is not a separate asset registry.
- OEM instructions and competent site review determine actual procedures,
  products, intervals, qualifications, and permissible deferrals. None are
  invented or universally approved by this document.
- Condition monitoring may be manual or integrated. Sensor availability does
  not itself establish measurement freshness, validity, or task completion.

## Constraints

- Software eligibility is separate from physical isolation and safety
  authorization. Unknown safety state never grants access or restart.
- Production urgency cannot override applicable safety or mandatory service.
- Disposal, cleaning agents, fluids, and waste follow applicable approved
  environmental and handling procedures.
- A predictive-maintenance model may recommend investigation, not silently
  replace required preventive tasks or approved service limits.

## Acceptance Criteria

| ID | Observable outcome |
|----|--------------------|
| MC-AC-01 | A CNC and a shared support machine each expose their own applicable plan, owner, procedure revision, and next required work. |
| MC-AC-02 | A new asset without a reviewed plan is visibly unresolved and cannot silently become qualified for affected production. |
| MC-AC-03 | Calendar and usage triggers, including a combined rule, produce the documented due outcome at exact boundaries and across local daylight-saving changes. |
| MC-AC-04 | A job projected to cross a mandatory service limit is flagged before assignment; downtime and consumable/spare needs appear in planning. |
| MC-AC-05 | A stale meter or reset cannot move the next service later by treating missing usage as zero. |
| MC-AC-06 | Completed cleaning/oiling records show the actual applicable procedure, actor, evidence, and consumed resources, without a reminder counting as completion. |
| MC-AC-07 | Interrupted work and a failed post-service check keep the asset held until the required completion and release evidence exists. |
| MC-AC-08 | A compressor or extraction outage invalidates dependent production eligibility without granting any machine-control authority. |
| MC-AC-09 | Restarting the automation or replaying a reading preserves due baselines, holds, and one attributable completion history. |
| MC-AC-10 | Deferrals expire as approved, corrections remain auditable, and production approval cannot waive a prohibited safety-critical hold. |

## Failure Behavior

Expose missing-plan, ambiguous-applicability, overdue, stale-meter,
awaiting-resource, incomplete-evidence, failed-check, and release-required
conditions. Preserve work and history during integration outages. Do not infer
completion, restore availability, or advance service baselines from an error,
missing reading, restart, or unconfirmed external-service report.

## Out of Scope

- Implementing the scheduler, sensor integration, or work-order UI in this PR.
- Machine-specific lubrication, cleaning, energy-isolation, or repair steps.
- Automatic hazardous servicing or machine restart.
- Replacing OEM guidance, professional maintenance judgment, or applicable
  legal and safety programs.

## References

- [Inventory and Equipment Lifecycle](inventory-tooling-and-equipment-lifecycle.md)
- [In-Process Inspection](in-process-inspection-and-progressive-verification.md)
- [Workholding Assessment](pass-by-pass-workholding-analysis.md)
- [Human and Automation Zone Safety](../facility-operations/human-automation-zone-safety-and-scheduling.md)
- [Proposed Progressive Verification Design](../../../docs/manufacturing/progressive-manufacturing-verification-design.md)
- [Requirements/Design Prompt](../../prompts/deploying/20261028-progressive-manufacturing-verification.md)
- [Haas UMC Maintenance](https://www.haascnc.com/service/online-manuals/umc-series/umc---maintenance.html):
  an example OEM reference, not an adopted procedure for every asset.
- [OSHA 29 CFR 1910.147](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.147):
  US hazardous-energy-control reference; applicability and procedures require
  competent site review.
