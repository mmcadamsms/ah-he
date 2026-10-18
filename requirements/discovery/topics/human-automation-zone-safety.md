# Human and Automation Zone Safety Questions

## Purpose

This topic tracks unresolved decisions about operating people and automation
within the same facility, including automation-only areas, validated shared
areas, human-only work, energy isolation, interventions, travel routes,
schedule coordination, priority, emergency egress, occupancy, mobile robots,
and fail-safe zone transitions.

These are discovery questions, not approved safety designs. Question IDs are
permanent and canonical in this file. Final answers require task-specific and
equipment-specific risk assessment by qualified safety professionals.

## Zone Definitions and Boundaries

### ZON-001 — Which physical areas require formal automation safety zones?

**State:** Exploring

**First-pass answer:** Create a formal zone anywhere a person could be exposed
to hazardous machine motion, stored or released energy, robot/mobile-equipment
travel, process emissions, or a dependent escape/access route. Boundaries must
follow the full hazard envelope and foreseeable intervention, not just painted
equipment footprints.

**Evidence:**
- [OSHA machine guarding, 29 CFR 1910.212](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.212) — **Evidence class:** binding U.S. regulation. It requires protection from point-of-operation, ingoing-nip, rotating-part, flying-chip, and spark hazards.
- [ISO 12100:2010](https://www.iso.org/standard/51528.html) — **Evidence class:** official consensus-standard scope. It requires lifecycle hazard identification and risk reduction for intended use and foreseeable misuse.

**Conflicting evidence:** Vendor cell drawings are useful starting points but
may omit site routes, adjacent equipment, workpieces, utilities, and maintenance
reach.

**Confidence:** High on the criterion; medium on actual boundaries.

**Business-specific validation:** A qualified machinery-safety professional and
affected workers must map every task and energy/hazard reach on the installed
layout.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The exposure-based zoning rule is sound, but the cited ISO page is scope-level; actual boundaries still depend on installed stopping, projectile, emission, and task-reach analysis.

### ZON-002 — Which zone states are needed for the planned facility?

**State:** Exploring

Consider automation-only, validated shared operation, human-only,
energy-isolated, emergency, commissioning, and degraded or unknown states.

**First-pass answer:** Use only states with distinct enforceable safety
conditions: automation-only; validated shared/collaborative operation;
human-permitted protective stop or manual mode; verified energy-isolated;
commissioning/test; emergency/evacuation; and degraded/unknown restricted.
Each state needs permitted tasks, required safeguards, entry rules, and a safe
response to loss of conditions.

**Evidence:**
- [ISO 10218-2:2025](https://www.iso.org/standard/83569.html) — **Evidence class:** official consensus-standard scope. It covers integration, commissioning, operation, maintenance, safeguarding, and collaborative robot applications.
- [OSHA robotics hazard evaluation](https://www.osha.gov/robotics/hazard-evaluation-solutions) — **Evidence class:** primary official guidance. Robot hazards and controls differ during normal production, programming, troubleshooting, and maintenance.

**Conflicting evidence:** More software states can appear precise but increase
mode confusion; combine states unless they change a safety function or allowed
task.

**Confidence:** Medium-high.

**Business-specific validation:** Derive states from task-based risk assessments
and prove operators can distinguish them during normal, fault, and power-loss
scenarios.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Distinct enforceable states are the right model, but keep the state set minimal and do not treat “collaborative” as a blanket permission unless the full application is validated.

### ZON-003 — How are a zone's hazards, equipment, boundaries, access points, routes, adjacent zones, and permitted tasks documented?

**State:** Exploring

**First-pass answer:** Maintain a versioned zone dossier and marked layout with
hazard inventory, equipment/energy sources, maximum envelopes and stopping
distances, physical and sensed boundaries, every portal and route, adjacent
dependencies, safety functions, permitted tasks by state, reset points, and
validation evidence. Link it to the approved risk assessment and change log.

**Evidence:**
- [ISO 12100:2010](https://www.iso.org/standard/51528.html) — **Evidence class:** official consensus-standard scope. It calls for documenting and verifying hazard identification, risk assessment, and risk reduction.
- [MIL-HDBK-61B Configuration Management Guidance](https://quicksearch.dla.mil/qsDocDetails.aspx?ident_number=202239) — **Evidence class:** primary official guidance. Configuration identification, controlled change, status accounting, and audits keep records aligned with the physical system.

**Conflicting evidence:** A digital twin can improve visualization but is not
proof that the installed boundary, stopping performance, or software version
matches the model.

**Confidence:** High.

**Business-specific validation:** Field-walk every dossier against as-built
equipment, then conduct periodic configuration audits and post-change checks.

**Second-opinion review:** GPT-5.4 — Agree. A versioned zone dossier tied to risk assessment and change control is the right control, with the caveat that digital models do not replace as-built field audit.

### ZON-004 — Which zones should use fixed physical separation rather than dynamic sensing?

**State:** Exploring

**First-pass answer:** Prefer fixed guards and controlled interlocked access for
high-severity hazards, fast or long stopping motion, projectiles/process hazards,
poor visibility, frequent sensor obstruction, or tasks that do not require
routine sharing. Dynamic sensing is a supplement, not a substitute, unless its
complete safety function and separation distance are validated.

**Evidence:**
- [OSHA machine guarding, 29 CFR 1910.212](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.212) — **Evidence class:** binding U.S. regulation. Guards must protect workers from identified machine hazards.
- [ISO 13855:2010](https://www.iso.org/standard/54957.html) — **Evidence class:** official consensus-standard scope. Safeguard placement depends on approach speed and total system stopping performance.

**Conflicting evidence:** Scanner-based layouts can improve access and
flexibility, but floor-space savings alone do not establish equivalent risk
reduction.

**Confidence:** High on preference; medium on each application.

**Business-specific validation:** Measure worst-case stopping time and intrusion
paths, then have a qualified integrator justify fixed versus sensed protection.

**Second-opinion review:** GPT-5.4 — Agree. Fixed separation should remain the default for severe, fast, or poorly visible hazards; dynamic sensing is supplemental unless stopping distance and fault response are validated on site.

### ZON-005 — When are dynamic scanner-based or task-dependent boundaries justified?

**State:** Exploring

**First-pass answer:** Use them only when variable access materially benefits
the task and a safety-rated sensing/control chain can maintain required
separation for every validated speed, payload, approach, obstruction, and fault.
The default on lost field validity, localization, communication, or device
health must be a safe restricted state, and ordinary camera or AI scene
classification is advisory unless it is part of a validated safety-rated
protective function.

**Evidence:**
- [ISO 13849-1:2023](https://www.iso.org/standard/71572.html) — **Evidence class:** official consensus-standard scope. It covers design and performance of safety-related control-system parts, including software.
- [ISO 13855:2010](https://www.iso.org/standard/54957.html) — **Evidence class:** official consensus-standard scope. It connects safeguard location to human approach and machine stopping time.

**Conflicting evidence:** Supplier demonstrations show technical feasibility,
not dependable performance in dust, reflections, pallets, changing layouts, or
maintenance bypass conditions.

**Confidence:** Medium.

**Business-specific validation:** Test the installed system at worst speed/load,
all approach paths, environmental extremes, occlusions, and injected faults.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Dynamic boundaries are defensible only with a safety-rated sensing and control chain; ordinary AI or camera classification is advisory evidence, not a sufficient protective function.

### ZON-006 — Which hazards can cross a nominal zone boundary?

**State:** Exploring

Consider robot reach, projectiles, suspended loads, mobile equipment, heat,
fire, pressure, fumes, dust, lasers, noise, and released energy.

**First-pass answer:** Treat any hazard whose credible trajectory, plume,
radiation, pressure wave, noise field, suspended load path, vehicle stopping
path, or connected energy flow extends beyond the marker as cross-boundary.
The zone must include the maximum credible envelope or add engineered
containment and coordinated adjacent-zone controls.

**Evidence:**
- [ISO 12100:2010](https://www.iso.org/standard/51528.html) — **Evidence class:** official consensus-standard scope. Risk assessment covers machinery hazards across lifecycle phases, not only normal motion.
- [OSHA machine guarding, 29 CFR 1910.212](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.212) — **Evidence class:** binding U.S. regulation. Flying chips and sparks are expressly included alongside mechanical contact hazards.

**Conflicting evidence:** Nominal OEM reach and rated containment can understate
tool/workpiece release, hose failure, smoke, or altered payload trajectories.

**Confidence:** High on method; medium on envelopes.

**Business-specific validation:** Complete process-specific calculations/tests
for ejection, stopping, ventilation, fire, pressure, laser, noise, and lifting.

**Second-opinion review:** GPT-5.4 — Agree. The answer correctly treats cross-boundary hazards as more than robot reach; the key qualification is that the envelope must be measured or calculated, not assumed from nominal drawings.

### ZON-007 — When must adjacent zones transition together because their hazards or routes interact?

**State:** Exploring

**First-pass answer:** Couple transitions when one zone can energize, move into,
feed, eject toward, ventilate through, block escape from, or strand a person in
another zone. Use a documented dependency/interlock matrix; if safe independence
cannot be proved, the affected group transitions as one safety unit.

**Evidence:**
- [OSHA 29 CFR 1910.147](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.147) — **Evidence class:** binding U.S. regulation. Energy-control procedures must address machines, energy sources, and affected employees exposed during servicing.
- [ISO 10218-2:2025](https://www.iso.org/standard/83569.html) — **Evidence class:** official consensus-standard scope. Robot application integration includes connected equipment, safeguarding, commissioning, and maintenance.

**Conflicting evidence:** Independent zones improve availability, but only
where interfaces and escape routes are physically and functionally independent.

**Confidence:** High.

**Business-specific validation:** Inject each adjacent-zone start, stop, and
fault while occupied test procedures verify no transferred hazard or trapping.

**Second-opinion review:** GPT-5.4 — Agree. Adjacent zones should transition together whenever hazards, routes, or shared safeguards interact; the review point is to prove those dependencies explicitly in the installed layout.

### ZON-008 — How are temporary zones created for commissioning, rigging, construction, repair, or unusual work?

**State:** Exploring

**First-pass answer:** Require a time-limited permit and task risk assessment
that defines boundaries, energy state, barriers/signals, route changes,
qualified owner, entrants, adjacent shutdowns, communications, rescue, expiry,
and restoration inspection. Temporary controls must be at least as effective
as the displaced permanent safeguard for the exposure created.

**Evidence:**
- [OSHA 29 CFR 1910.147](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.147) — **Evidence class:** binding U.S. regulation. Non-routine servicing requires specific hazardous-energy control and employee protection.
- [NIOSH Safe Maintenance Guidelines for Robotic Workstations](https://stacks.cdc.gov/view/cdc/158177) — **Evidence class:** primary official technical guidance. Commissioning, programming, repair, and troubleshooting create distinct robot-workstation exposures.

**Conflicting evidence:** Cones and tape are fast and visible but are not
machine safeguarding; they are suitable only where the hazard is already
controlled.

**Confidence:** High.

**Business-specific validation:** A qualified safety reviewer must approve the
permit template and observe representative rigging, commissioning, and repair.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Temporary zones need the same hazard analysis, authority, marking, and expiration controls as permanent ones; permits or schedules alone are not a safety function.

### ZON-009 — How are floor markings, signs, lights, audible signals, gates, barriers, and HMIs kept consistent with actual zone state?

**State:** Exploring

**First-pass answer:** Drive safety-relevant indications from validated safety
state or positively monitored device status, use one site-wide legend, and
fail disagreement into a restricted state. Inspect physical labels/barriers,
proof-test lamps and audible devices, and prohibit an HMI or scheduler from
displaying "safe" without authoritative confirmation.

**Evidence:**
- [ISO 13849-1:2023](https://www.iso.org/standard/71572.html) — **Evidence class:** official consensus-standard scope. Safety-related control functions require designed and validated performance, including software behavior.
- [OSHA robotics hazard evaluation](https://www.osha.gov/robotics/hazard-evaluation-solutions) — **Evidence class:** primary official guidance. Effective robot safeguarding uses engineered barriers, interlocks, presence sensing, warnings, and procedures as a system.

**Conflicting evidence:** Rich dashboards and camera overlays improve awareness,
but advisory data can be stale or wrong and cannot silently become a safety
function.

**Confidence:** High.

**Business-specific validation:** Perform discrepancy tests between every
indicator, physical safeguard, controller state, and loss-of-power/network case.

**Second-opinion review:** GPT-5.4 — Agree. Signs, markings, lights, and HMIs must follow the authoritative safety state and fail safe when stale or contradictory.

### ZON-010 — What prevents an ordinary software label, camera classification, badge record, or map geofence from being mistaken for a safety-rated boundary?

**State:** Exploring

**First-pass answer:** Explicitly classify each control as safety-rated or
advisory in drawings, interfaces, training, and code; use independent
safety-rated hardware/control functions for hazardous motion. Advisory systems
may request or corroborate a transition but cannot authorize it, and their
failure must not degrade the safety function.

**Evidence:**
- [ISO 13849-1:2023](https://www.iso.org/standard/71572.html) — **Evidence class:** official consensus-standard scope. Safety-related control parts are designed to required performance levels; ordinary software is not equivalent by assertion.
- [NIST SP 800-82 Rev. 3](https://csrc.nist.gov/pubs/sp/800/82/r3/final) — **Evidence class:** primary official OT guidance. Segmentation, least functionality, authentication, configuration control, and fail-safe considerations reduce dependence on ordinary networked systems.

**Conflicting evidence:** AI vision and geofencing can detect useful conditions,
but public demonstrations do not prove the diagnostic coverage, independence,
and failure behavior required for a safety function.

**Confidence:** High.

**Business-specific validation:** Have a machinery-safety engineer review the
safety requirements specification and trace every claimed boundary to certified
components and validation tests.

**Second-opinion review:** GPT-5.4 — Agree. Ordinary software labels, geofences, badge records, and camera classifications are advisory only unless they are part of a validated safety-rated function.

## State Transitions and Safety Authority

### ZON-011 — What exact preconditions are required to transition from automation-only to human-permitted access?

**State:** Exploring

**First-pass answer:** Require an authorized request, controlled stop of all
affected equipment, verified safe position/energy condition for the intended
task, closure of incoming hazards, healthy access safeguarding, warning where
needed, and positive grant of entry. Servicing that could expose unexpected
energization requires full lockout/tagout, not merely a protective stop.

**Evidence:**
- [OSHA 29 CFR 1910.147](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.147) — **Evidence class:** binding U.S. regulation. It requires shutdown, isolation, lock/tag application, stored-energy control, and verification before servicing exposure.
- [ISO 14118:2017](https://www.iso.org/standard/69190.html) — **Evidence class:** official consensus-standard scope. It addresses prevention of unexpected startup.

**Conflicting evidence:** Fast protective stops support routine safeguarded
tasks but do not isolate stored energy or every connected source.

**Confidence:** High.

**Business-specific validation:** Define and validate entry preconditions for
each task/equipment combination with a qualified safety professional.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Entry and restart preconditions and authority belong to the validated safety function or approved procedure, not to schedulers or ordinary controls; clearance evidence must be explicit.

### ZON-012 — What exact preconditions are required to transition from human-permitted access back to automation-only?

**State:** Exploring

**First-pass answer:** Require work completion and permit closure, tools/parts
removed, guards restored, all people accounted for and outside, access points
closed, safety devices healthy, correct configuration selected, reset from a
position that permits checking the zone, warning if needed, and a separate
deliberate command to start hazardous motion.

**Evidence:**
- [OSHA 29 CFR 1910.147](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.147) — **Evidence class:** binding U.S. regulation. Release from lockout includes inspection, employee checks, lock removal rules, and notification before re-energization.
- [ISO 14119](https://www.iso.org/standard/63987.html) — **Evidence class:** official consensus-standard scope. It covers design and selection of guard interlocking devices.

**Conflicting evidence:** Badge counts and vision may support clearance, but
neither reliably proves no hidden person, tool, or unsafe machine condition.

**Confidence:** High.

**Business-specific validation:** Validate restart using multiple entrants,
hidden locations, incomplete cycles, failed devices, and power-loss scenarios.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Entry and restart preconditions and authority belong to the validated safety function or approved procedure, not to schedulers or ordinary controls; clearance evidence must be explicit.

### ZON-013 — Which system may request a transition, and which safety system or procedure may authorize it?

**State:** Exploring

**First-pass answer:** Production, maintenance, or fleet software may request a
transition; only the validated safety-control system plus required local human
procedure may authorize it. The request interface must be non-safety-rated,
bounded, authenticated, logged, and unable to force an unsafe state.

**Evidence:**
- [ISO 13849-1:2023](https://www.iso.org/standard/71572.html) — **Evidence class:** official consensus-standard scope. Safety-related control functions require engineered performance and software controls.
- [NIST SP 800-82 Rev. 3](https://csrc.nist.gov/pubs/sp/800/82/r3/final) — **Evidence class:** primary official OT guidance. It supports segmentation, least privilege, authenticated control, configuration management, and safe operation.

**Conflicting evidence:** Integrated orchestration reduces delay, but shared
ordinary software also increases common-mode and cyber failure exposure.

**Confidence:** High.

**Business-specific validation:** Review the authority boundary in architecture
and prove malformed, stale, duplicated, and unauthorized requests fail safely.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Entry and restart preconditions and authority belong to the validated safety function or approved procedure, not to schedulers or ordinary controls; clearance evidence must be explicit.

### ZON-014 — How is production scheduling prevented from bypassing or substituting for safety authorization?

**State:** Exploring

**First-pass answer:** Make scheduler output advisory: it can reserve time and
submit a request, but it cannot write safety outputs, reset safeguards, release
lockout, or start motion. Use one-way/status-limited interfaces where practical,
least privilege, independent safety logic, and audit records comparing request
with authorization.

**Evidence:**
- [NIST SP 800-82 Rev. 3](https://csrc.nist.gov/pubs/sp/800/82/r3/final) — **Evidence class:** primary official OT-security guidance. Network segmentation, least privilege, application allowlisting, and controlled remote access limit ordinary-system authority.
- [ISO 13849-1:2023](https://www.iso.org/standard/71572.html) — **Evidence class:** official consensus-standard scope. The safety-related control system, not business software, provides the validated safety function.

**Conflicting evidence:** Unified MES/robot platforms can coordinate efficiently,
but vendor integration does not by itself establish safety integrity or
independence.

**Confidence:** High.

**Business-specific validation:** Conduct design review and penetration/fault
testing showing scheduler compromise cannot defeat a safety function.

**Second-opinion review:** GPT-5.4 — Agree. The answer correctly rejects production scheduling as a substitute for safety authorization.

### ZON-015 — Which equipment must stop, slow, isolate, drain, block, cool, or otherwise reach a safe condition before entry?

**State:** Exploring

**First-pass answer:** Include every source that can expose the entrant:
robots/machines, feeders, conveyors, mobile assets, gravity/suspended loads,
hydraulic/pneumatic pressure, electrical and thermal energy, chemicals, vacuum,
and connected upstream/downstream equipment. The required condition follows
the task: monitored stop or reduced mode only where validated; otherwise
isolate, dissipate, restrain, block, and verify.

**Evidence:**
- [OSHA 29 CFR 1910.147](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.147) — **Evidence class:** binding U.S. regulation. It covers electrical, mechanical, hydraulic, pneumatic, chemical, thermal, and other hazardous energy.
- [ISO 12100:2010](https://www.iso.org/standard/51528.html) — **Evidence class:** official consensus-standard scope. Hazard identification spans all lifecycle tasks and foreseeable conditions.

**Conflicting evidence:** Stopping visible motion is quicker but can leave
stored, gravity, thermal, or connected-process hazards uncontrolled.

**Confidence:** High.

**Business-specific validation:** Produce task-specific energy-control diagrams
and physically verify each zero/controlled-energy condition.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Safe-entry logic must include all coupled equipment, stored energy, and adjacent-process effects; the exact stop, isolate, or manual allowances remain task- and machine-specific.

### ZON-016 — Which tasks are allowed under a protective stop, human-only state, controlled manual mode, or lockout/tagout?

**State:** Exploring

**First-pass answer:** Allow routine safeguarded access under a protective stop
only when the validated stop prevents exposure and no servicing exception
applies. Use controlled manual mode for tasks requiring motion with enabling
device, reduced speed, and direct control. Use lockout/tagout whenever servicing
could expose unexpected energization, startup, or hazardous-energy release.

**Evidence:**
- [OSHA 29 CFR 1910.147](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.147) — **Evidence class:** binding U.S. regulation. Its servicing scope and narrow minor-servicing exception determine when energy control is required.
- [NIOSH Safe Maintenance Guidelines for Robotic Workstations](https://stacks.cdc.gov/view/cdc/158177) — **Evidence class:** primary official technical guidance. It distinguishes production safeguarding from programming, troubleshooting, and maintenance protections.

**Conflicting evidence:** Frequent lockout can burden short interventions, but
frequency does not convert servicing into a safe production task.

**Confidence:** High on the rule; medium on task classification.

**Business-specific validation:** Qualified safety/legal review of every
intervention and any claimed minor-servicing alternative.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Safe-entry logic must include all coupled equipment, stored energy, and adjacent-process effects; the exact stop, isolate, or manual allowances remain task- and machine-specific.

### ZON-017 — How are connected machines, conveyors, robots, stored energy, and adjacent processes included in a transition?

**State:** Exploring

**First-pass answer:** Maintain an isolation/dependency matrix that identifies
every capable energy or material transfer and the safe state required for each
transition. Use hardwired or safety-rated interlocks where the dependency is
automatic; use documented group energy control for servicing and verify all
sources before entry.

**Evidence:**
- [OSHA 29 CFR 1910.147](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.147) — **Evidence class:** binding U.S. regulation. Energy-control procedures must identify shutdown, isolation, stored-energy, and verification steps.
- [ISO 10218-2:2025](https://www.iso.org/standard/83569.html) — **Evidence class:** official consensus-standard scope. Robot-system integration covers applications/cells and associated equipment, not the robot alone.

**Conflicting evidence:** Separate machine controls can appear independent while
shared conveyors, utilities, programs, or escape routes preserve coupling.

**Confidence:** High.

**Business-specific validation:** Trace energy/material paths from current
schematics and inject starts/faults from every connected system.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Safe-entry logic must include all coupled equipment, stored energy, and adjacent-process effects; the exact stop, isolate, or manual allowances remain task- and machine-specific.

### ZON-018 — Which safety devices and conditions must be healthy before a zone can enter each state?

**State:** Exploring

**First-pass answer:** The safety requirements specification must list required
guards/interlocks, emergency stops, presence sensing, safety PLC/I/O,
brakes/contactors, mode/enabling devices, reset circuits, speed/position
monitoring, communications where safety-rated, and process containment for each
state. Any required unhealthy, bypassed, or unvalidated element blocks the
state and enters a predefined safe condition.

**Evidence:**
- [ISO 13849-1:2023](https://www.iso.org/standard/71572.html) — **Evidence class:** official consensus-standard scope. It addresses safety functions, architecture, diagnostics, and performance levels.
- [OSHA robotics hazard evaluation](https://www.osha.gov/robotics/hazard-evaluation-solutions) — **Evidence class:** primary official guidance. Robot safeguards use interlocked barriers, presence sensing, controls, and procedures in combination.

**Conflicting evidence:** Device self-diagnostics improve coverage but do not
prove correct placement, wiring, configuration, or stopping performance.

**Confidence:** High on method; medium on device list.

**Business-specific validation:** Build and execute a state-by-state safety
validation plan with injected single and foreseeable combined faults.

**Second-opinion review:** GPT-5.4 — Agree. Health checks, mid-sequence fault handling, and timeout-to-restricted-state are the right fail-safe principles for transition control.

### ZON-019 — What happens if transition conditions change or fail midway through the sequence?

**State:** Exploring

**First-pass answer:** Abort to the safest achievable restricted state, inhibit
hazardous motion and further automatic transitions, preserve egress, announce
the fault, and require diagnosis and authorized recovery. Do not automatically
roll back through hazardous intermediate states or infer clearance from an
incomplete sequence.

**Evidence:**
- [ISO 13849-1:2023](https://www.iso.org/standard/71572.html) — **Evidence class:** official consensus-standard scope. Safety-related controls require defined behavior under faults and diagnostic coverage.
- [ISO 14118:2017](https://www.iso.org/standard/69190.html) — **Evidence class:** official consensus-standard scope. Prevention of unexpected startup applies during abnormal and intervention states.

**Conflicting evidence:** Automatic recovery improves uptime but can obscure a
changed physical condition or person still in the zone.

**Confidence:** High.

**Business-specific validation:** Fault-inject loss of every precondition at
each sequence step and verify motion inhibition, egress, indication, and logs.

**Second-opinion review:** GPT-5.4 — Agree. Health checks, mid-sequence fault handling, and timeout-to-restricted-state are the right fail-safe principles for transition control.

### ZON-020 — How long may a transition remain pending before it times out into a restricted state?

**State:** Exploring

**First-pass answer:** Set a short, task-specific timeout no longer than the
period for which all preconditions and human understanding remain valid. On
expiry cancel the authorization, retain or re-enter the safe restricted state,
and require a fresh local check; never start because a timer expired.

**Evidence:**
- [ISO 13849-1:2023](https://www.iso.org/standard/71572.html) — **Evidence class:** official consensus-standard scope. Timing and sequence behavior are part of the designed safety function.
- [ISO 12100:2010](https://www.iso.org/standard/51528.html) — **Evidence class:** official consensus-standard scope. Risk estimation must account for exposure and foreseeable human interaction.

**Conflicting evidence:** Long windows reduce repeated checks but allow the
physical situation, personnel, or route to change unnoticed.

**Confidence:** Medium; no universal duration exists.

**Business-specific validation:** Time real transitions, analyze how quickly
conditions can change, and validate timeout/retry behavior with affected users.

**Second-opinion review:** GPT-5.4 — Agree. Health checks, mid-sequence fault handling, and timeout-to-restricted-state are the right fail-safe principles for transition control.

### ZON-021 — Which transitions require a warning interval, local acknowledgment, or supervisor approval?

**State:** Exploring

**First-pass answer:** Warn before any transition that can create hazardous
motion or revoke human access. Require local acknowledgment where people may be
present or cannot see the initiating station; require supervisor or permit
approval for commissioning, bypass, degraded protection, rescue, group
lockout, and unusual work. Warning never substitutes for clearance.

**Evidence:**
- [OSHA robotics hazard evaluation](https://www.osha.gov/robotics/hazard-evaluation-solutions) — **Evidence class:** primary official guidance. Warnings supplement engineered safeguards and procedures in robot systems.
- [OSHA 29 CFR 1910.147](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.147) — **Evidence class:** binding U.S. regulation. Notification and authorized control are required around application and release of energy control.

**Conflicting evidence:** Alarm-heavy systems create habituation; targeted
warnings tied to imminent action are more credible than constant beacons.

**Confidence:** High on categories; medium on approval levels.

**Business-specific validation:** Conduct human-factors trials for audibility,
visibility, comprehension, response time, and alarm burden.

**Second-opinion review:** GPT-5.4 — Agree. Warning intervals, acknowledgments, and approvals should follow consequence, visibility, and restart risk rather than convenience or schedule pressure.

### ZON-022 — Where may reset controls be located, and what zone visibility or presence evidence is required?

**State:** Exploring

**First-pass answer:** Locate reset outside the hazard zone at a position where
the operator can inspect the safeguarded space and cannot reach it from inside;
use additional reset/clearance stations or trapped-key/presence procedures for
hidden areas. Reset must not be possible solely from remote video, badge data,
or an obstructed viewpoint.

**Evidence:**
- [ISO 10218-2:2025](https://www.iso.org/standard/83569.html) — **Evidence class:** official consensus-standard scope. Robot-cell integration includes safeguarding, reset, commissioning, and operational access.
- [NIOSH robot-related fatalities, 1992–2017](https://stacks.cdc.gov/view/cdc/230667/cdc_230667_DS1.pdf) — **Evidence class:** empirical government surveillance study. Fatal incidents disproportionately involved workers struck by robots, often during non-routine work.

**Conflicting evidence:** Cameras improve coverage but can have occlusion,
latency, failure, and depth limitations.

**Confidence:** High.

**Business-specific validation:** Perform sightline studies with actual tooling,
pallets, pits, platforms, lighting, and every foreseeable entrant position.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Reset and hazardous-motion commands must stay distinct and locally verifiable; remote requests may coordinate, but they should not replace local sight, clearance, or confirmation where motion can start.

### ZON-023 — How is reset kept separate from the command that initiates hazardous motion?

**State:** Exploring

**First-pass answer:** Use distinct controls and logic: reset only restores
readiness after checks; a separate deliberate start command initiates motion.
Neither closing a gate, restoring power, clearing an alarm, timing out, nor
resetting a device may automatically start hazardous operation.

**Evidence:**
- [ISO 13850:2015](https://www.iso.org/standard/70143.html) — **Evidence class:** official consensus-standard scope. Releasing an emergency-stop device permits restart but is not itself a restart command.
- [ISO 14118:2017](https://www.iso.org/standard/69190.html) — **Evidence class:** official consensus-standard scope. It addresses prevention of unexpected startup.

**Conflicting evidence:** Single-action restart is faster, but it removes the
operator's opportunity to verify the restored physical state.

**Confidence:** High.

**Business-specific validation:** Test every reset, guard closure, power return,
fault clear, and remote command for absence of unintended motion.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Reset and hazardous-motion commands must stay distinct and locally verifiable; remote requests may coordinate, but they should not replace local sight, clearance, or confirmation where motion can start.

### ZON-024 — Which transitions may be requested remotely, and which actions must always occur locally?

**State:** Exploring

**First-pass answer:** Remote systems may request shutdown, restriction, or a
future access window. Entry clearance, lockout verification/release, bypass
approval, zone-clear confirmation, reset after human access, and start of
hazardous motion should occur locally unless a formal risk assessment proves an
equally reliable means with complete presence/condition verification.

**Evidence:**
- [NIST SP 800-82 Rev. 3](https://csrc.nist.gov/pubs/sp/800/82/r3/final) — **Evidence class:** primary official OT guidance. Remote access should be limited, authenticated, monitored, segmented, and consistent with safe operation.
- [OSHA 29 CFR 1910.147](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.147) — **Evidence class:** binding U.S. regulation. Authorized employees physically apply and verify hazardous-energy control.

**Conflicting evidence:** Remote diagnostics can reduce response time, but
connectivity and video do not prove local physical clearance.

**Confidence:** High on conservative boundary; medium for specialized designs.

**Business-specific validation:** Qualified safety and OT-security review plus
loss-of-network/video, stale-state, credential, and spoofed-command tests.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Reset and hazardous-motion commands must stay distinct and locally verifiable; remote requests may coordinate, but they should not replace local sight, clearance, or confirmation where motion can start.

### ZON-025 — How are zone state machines, timing, stopping distance, device placement, and failure behavior validated and versioned?

**State:** Exploring

**First-pass answer:** Maintain a safety requirements specification, cause/effect
and state-transition tables, safety calculations, measured worst-case stopping
times, placement calculations, software/configuration hashes, and traceable
validation protocols. Validate normal, boundary, fault, power-loss, restart,
and changed-payload cases; approve and archive every released version.

**Evidence:**
- [ISO 13849-1:2023](https://www.iso.org/standard/71572.html) — **Evidence class:** official consensus-standard scope. It covers design and validation inputs for safety-related control-system performance.
- [ISO 13855:2010](https://www.iso.org/standard/54957.html) — **Evidence class:** official consensus-standard scope. Safeguard positioning depends on approach and stopping performance.

**Conflicting evidence:** Simulation and vendor certificates reduce test burden
but do not verify site integration, as-built wiring, loads, wear, or software.

**Confidence:** High.

**Business-specific validation:** Independent qualified validation on the
as-built installation, followed by baseline configuration capture and signed
change control.

**Second-opinion review:** GPT-5.4 — Agree. Versioned state logic, measured stopping data, and fault-case validation are the right evidence package.

## Ingress, Egress, Occupancy, and Travel

### ZON-026 — Can every routinely occupied area be reached without crossing an active automation-only zone?

**State:** Exploring

**First-pass answer:** Design for yes. Offices, restrooms, inspection, break,
maintenance, and exit access should have protected pedestrian routes that do
not depend on stopping automation. Any unavoidable crossing needs physical
separation or a validated access transition and cannot be part of required
emergency egress while hazardous operation continues.

**Evidence:**
- [OSHA 29 CFR 1910.36](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.36) — **Evidence class:** binding U.S. regulation. Exit routes must be permanent, generally provide alternatives, and lead to safety.
- [OSHA 29 CFR 1910.37](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.37) — **Evidence class:** binding U.S. regulation. Exit routes must remain unobstructed, adequately marked, lighted, and available.

**Conflicting evidence:** Shared crossings save floor space but create recurring
production/access conflict and dependence on controls.

**Confidence:** High.

**Business-specific validation:** Overlay all routine and emergency pedestrian
trips on the as-built layout and observe peak-shift movement.

**Second-opinion review:** GPT-5.4 — Agree. Protected routes, exits, crossings, and escape points must not depend on active automation or ordinary IT; when crossings remain, anti-trap and validated stop/separation logic are required.

### ZON-027 — Where crossing an automation zone cannot be avoided, what separated path or validated transition protects the crossing?

**State:** Exploring

**First-pass answer:** Prefer an overhead, underpass, fenced walkway, or
interlocked corridor that prevents exposure without relying on behavior. If a
shared crossing remains, use a safety-rated stop/separation function, controlled
gates, clear sightlines, anti-trap design, and positive clearance before
automation resumes.

**Evidence:**
- [OSHA machine guarding, 29 CFR 1910.212](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.212) — **Evidence class:** binding U.S. regulation. Workers must be guarded from machine hazards.
- [ISO 13855:2010](https://www.iso.org/standard/54957.html) — **Evidence class:** official consensus-standard scope. Separation requires placement based on approach speed and stopping time.

**Conflicting evidence:** Painted pedestrian lanes improve organization but do
not stop a robot, vehicle, load, or projectile.

**Confidence:** High.

**Business-specific validation:** Validate worst-case stopping, sight distance,
gate logic, throughput, accessibility, and evacuation under representative use.

**Second-opinion review:** GPT-5.4 — Agree. Protected routes, exits, crossings, and escape points must not depend on active automation or ordinary IT; when crossings remain, anti-trap and validated stop/separation logic are required.

### ZON-028 — Which primary and alternate routes exist for employees, visitors, maintenance, material handlers, and emergency responders?

**State:** Exploring

**First-pass answer:** Maintain role-specific primary and alternate route maps
covering normal travel, deliveries, oversized loads, maintenance/rigging, and
emergency access. Visitor routes should avoid industrial hazards; responder
routes must reach isolation, fire, medical, and rescue points even when normal
credentials, automation, or utilities fail.

**Evidence:**
- [OSHA 29 CFR 1910.36](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.36) — **Evidence class:** binding U.S. regulation. Workplaces generally need at least two remote exit routes where one could be blocked.
- [OSHA 29 CFR 1910.38](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.38) — **Evidence class:** binding U.S. regulation. Emergency plans address reporting, evacuation, critical operations, accounting, rescue/medical duties, and contacts.

**Conflicting evidence:** One-way routing reduces interaction but may fail during
blocked aisles, fire, construction, or disabled equipment.

**Confidence:** High.

**Business-specific validation:** Walk and time each route with representative
loads, mobility needs, responders, smoke/visibility assumptions, and closures.

**Second-opinion review:** GPT-5.4 — Agree. Protected routes, exits, crossings, and escape points must not depend on active automation or ordinary IT; when crossings remain, anti-trap and validated stop/separation logic are required.

### ZON-029 — How are required exit routes kept available under every approved zone schedule?

**State:** Exploring

**First-pass answer:** Model exit routes as immutable safety constraints, not
schedulable capacity. No zone state, parked vehicle, pallet, locked gate,
temporary work, or failed ordinary system may obstruct or reduce required
egress; monitor and inspect routes and reject schedules that conflict.

**Evidence:**
- [OSHA 29 CFR 1910.37](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.37) — **Evidence class:** binding U.S. regulation. Exit routes must be free and unobstructed and employees must be able to open exit doors from inside without keys or special knowledge.
- [OSHA 29 CFR 1910.36](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.36) — **Evidence class:** binding U.S. regulation. Exit capacity and arrangement must support safe escape.

**Conflicting evidence:** Temporary material staging may seem operationally
necessary, but production convenience cannot override required egress.

**Confidence:** High.

**Business-specific validation:** Authority having jurisdiction and safety
professional review, daily inspection, and evacuation drills under worst
approved zone states.

**Second-opinion review:** GPT-5.4 — Agree. Protected routes, exits, crossings, and escape points must not depend on active automation or ordinary IT; when crossings remain, anti-trap and validated stop/separation logic are required.

### ZON-030 — How can a person stop hazardous operation and leave a zone if badges, networks, schedulers, or ordinary controls fail?

**State:** Exploring

**First-pass answer:** Provide local, directly accessible safety-rated stop
devices and mechanical/electromechanical escape release that do not depend on
badge, server, cloud, scheduler, or ordinary network availability. Loss of those
systems must retain or create a safe state and never prevent egress.

**Evidence:**
- [ISO 13850:2015](https://www.iso.org/standard/70143.html) — **Evidence class:** official consensus-standard scope. Emergency-stop function design is a machinery safety function.
- [OSHA 29 CFR 1910.37](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.37) — **Evidence class:** binding U.S. regulation. Exit doors must be openable from inside without keys, tools, or special knowledge.

**Conflicting evidence:** Central access systems improve accountability, but
life safety cannot depend on their availability or authorization.

**Confidence:** High.

**Business-specific validation:** De-energize/disconnect each ordinary system
and demonstrate stop, release, illumination, alarm, and exit from every location.

**Second-opinion review:** GPT-5.4 — Agree. Protected routes, exits, crossings, and escape points must not depend on active automation or ordinary IT; when crossings remain, anti-trap and validated stop/separation logic are required.

### ZON-031 — Which access points are entries, exits, emergency releases, material portals, robot passages, or maintenance openings?

**State:** Exploring

**First-pass answer:** Classify and uniquely identify every opening by permitted
direction, users/loads, zone states, safeguard, emergency behavior, and whether
it permits human access. Material and robot portals must prevent or detect
person entry where that is not intended; maintenance openings need equivalent
control to personnel gates.

**Evidence:**
- [ISO 14119](https://www.iso.org/standard/63987.html) — **Evidence class:** official consensus-standard scope. It governs selection/design of interlocks associated with guards.
- [OSHA machine guarding, 29 CFR 1910.212](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.212) — **Evidence class:** binding U.S. regulation. Openings may not expose workers to machine hazards.

**Conflicting evidence:** A small opening may be treated as non-accessible but
body reach, children/visitors, tools, climbing, and foreseeable misuse can alter
that conclusion.

**Confidence:** High.

**Business-specific validation:** Conduct an as-built opening inventory and
reach/access assessment with representative loads and foreseeable misuse.

**Second-opinion review:** GPT-5.4 — Agree. Protected routes, exits, crossings, and escape points must not depend on active automation or ordinary IT; when crossings remain, anti-trap and validated stop/separation logic are required.

### ZON-032 — How are access authorization, entry permission, physical presence, exit, and zone clearance represented as separate facts?

**State:** Exploring

**First-pass answer:** Model them independently: qualification/authorization is
who may request; entry permission is a time-bound safe-state grant; presence is
observed or controlled occupancy; exit is a recorded crossing; clearance is a
positive determination that no person remains and restart checks passed. No
single badge event may imply all five.

**Evidence:**
- [ISO 13849-1:2023](https://www.iso.org/standard/71572.html) — **Evidence class:** official consensus-standard scope. Safety logic requires explicit functions and validated fault behavior.
- [NIOSH robot-related fatalities, 1992–2017](https://stacks.cdc.gov/view/cdc/230667/cdc_230667_DS1.pdf) — **Evidence class:** empirical government surveillance study. Fatal robot incidents demonstrate the consequence of people being inside operating envelopes during non-routine work.

**Conflicting evidence:** Badge systems provide useful identity and event logs
but cannot prove physical presence, tailgating, or clearance.

**Confidence:** High.

**Business-specific validation:** Develop the state/data model and test dropped
badges, tailgating, alternate exits, network loss, and manual rescue.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Separating authorization, presence, exit, clearance, and multi-person accounting is correct, but badges, cameras, and logs are advisory unless safety-rated and validated against hidden-person and tailgating cases.

### ZON-033 — Which occupancy-detection methods are safety rated, and which provide advisory evidence only?

**State:** Exploring

**First-pass answer:** Only devices and complete control functions selected,
installed, and validated for the required safety performance—such as suitable
interlocked gates, light curtains, laser scanners, pressure-sensitive devices,
or trapped-key systems—may authorize safety action. Cameras, Wi-Fi, badges,
ordinary lidar, analytics, and production databases remain advisory unless the
entire implementation is certified and validated as a safety function.

**Evidence:**
- [ISO 13849-1:2023](https://www.iso.org/standard/71572.html) — **Evidence class:** official consensus-standard scope. Safety performance applies to the complete safety-related control function.
- [ISO 13855:2010](https://www.iso.org/standard/54957.html) — **Evidence class:** official consensus-standard scope. Presence-sensing safeguard placement depends on approach and stopping performance.

**Conflicting evidence:** Advisory sensor fusion can improve detection, but
adding non-safety channels does not automatically increase safety integrity.

**Confidence:** High.

**Business-specific validation:** Verify component certifications, architecture,
coverage, placement, diagnostics, proof-test interval, and as-built validation.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Separating authorization, presence, exit, clearance, and multi-person accounting is correct, but badges, cameras, and logs are advisory unless safety-rated and validated against hidden-person and tailgating cases.

### ZON-034 — How are multiple entrants, tailgating, contractors, visitors, dropped badges, hidden people, pits, platforms, and rescue entry handled?

**State:** Exploring

**First-pass answer:** Use controlled group entry with individual identification,
personal locks/tags or trapped keys where appropriate, anti-tailgating controls,
an entry roster, physical sweep/clear procedure, coverage of hidden spaces, and
special rescue authority. Contractors and visitors require briefing,
escorting/qualification, and equal protection; a missing badge never proves a
person's location.

**Evidence:**
- [OSHA 29 CFR 1910.147](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.147) — **Evidence class:** binding U.S. regulation. Group lockout must provide each authorized employee protection equivalent to personal lockout.
- [OSHA 29 CFR 1910.38](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.38) — **Evidence class:** binding U.S. regulation. Emergency plans include employee accounting and rescue/medical responsibilities.

**Conflicting evidence:** Automated people counting reduces manual burden but
can fail with occlusion, tailgating, unusual routes, or rescue entry.

**Confidence:** High.

**Business-specific validation:** Drill all listed cases, including a deliberately
hidden person and lost credential, without exposing participants to hazards.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Separating authorization, presence, exit, clearance, and multi-person accounting is correct, but badges, cameras, and logs are advisory unless safety-rated and validated against hidden-person and tailgating cases.

### ZON-035 — How is a person tracked when entering through one zone and leaving through another?

**State:** Exploring

**First-pass answer:** Use a controlled multi-zone journey/permit that records
identity, origin, authorized route and destination, each boundary crossing, and
final acceptance/exit. Zone occupancy cannot be decremented until an
authoritative crossing or manual reconciliation occurs; discrepancies freeze
affected restarts.

**Evidence:**
- [ISO 13849-1:2023](https://www.iso.org/standard/71572.html) — **Evidence class:** official consensus-standard scope. Safety control behavior must be explicit and validated under faults.
- [OSHA 29 CFR 1910.38](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.38) — **Evidence class:** binding U.S. regulation. Emergency arrangements require accounting for employees after evacuation.

**Conflicting evidence:** Location technology can provide useful advisory
continuity, but signal loss, credential exchange, and map error prevent treating
ordinary tracking as conclusive.

**Confidence:** Medium.

**Business-specific validation:** Test every permitted cross-zone route,
alternate exit, missed read, tailgate, network loss, and manual reconciliation.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Separating authorization, presence, exit, clearance, and multi-person accounting is correct, but badges, cameras, and logs are advisory unless safety-rated and validated against hidden-person and tailgating cases.

### ZON-036 — When is destination acceptance or an entry permit required before someone begins crossing several zones?

**State:** Exploring

**First-pass answer:** Require it whenever the destination may be unavailable,
the route crosses controlled zones, return is not continuously assured, special
qualification/PPE/isolation is needed, or an intermediate transition could trap
the traveler. Acceptance must reserve both destination and safe outbound/return
path for the permit window.

**Evidence:**
- [OSHA 29 CFR 1910.37](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.37) — **Evidence class:** binding U.S. regulation. Egress must remain available and unobstructed.
- [ISO 12100:2010](https://www.iso.org/standard/51528.html) — **Evidence class:** official consensus-standard scope. Risk assessment includes foreseeable tasks and access throughout the machinery lifecycle.

**Conflicting evidence:** Just-in-time route requests maximize utilization but
can leave a person waiting in an intermediate controlled area.

**Confidence:** High on trigger conditions.

**Business-specific validation:** Model and tabletop all multi-zone journeys,
including refusal, delay, fault, evacuation, and occupied destination.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Separating authorization, presence, exit, clearance, and multi-person accounting is correct, but badges, cameras, and logs are advisory unless safety-rated and validated against hidden-person and tailgating cases.

### ZON-037 — What prevents a transition from trapping a person between zones or behind a locked access point?

**State:** Exploring

**First-pass answer:** Enforce anti-trap interlocks and route-level occupancy:
do not revoke egress or close both sides until positive transfer is complete;
provide inside release and local stop; fail power/network loss toward unlocked
egress where code permits and safe motion is inhibited. Scheduler reservations
cannot override this logic.

**Evidence:**
- [OSHA 29 CFR 1910.37](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.37) — **Evidence class:** binding U.S. regulation. Exit access must remain free and doors openable from inside without special knowledge.
- [ISO 14119](https://www.iso.org/standard/63987.html) — **Evidence class:** official consensus-standard scope. Guard-locking and interlocking require designed behavior.

**Conflicting evidence:** Security locking and contamination control can conflict
with free egress and require code/safety-engineered solutions.

**Confidence:** High.

**Business-specific validation:** Fault-inject each door, sensor, PLC, network,
and power state with occupied transfer spaces and authority review.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Separating authorization, presence, exit, clearance, and multi-person accounting is correct, but badges, cameras, and logs are advisory unless safety-rated and validated against hidden-person and tailgating cases.

### ZON-038 — How are people accounted for before automation restarts after an intervention, evacuation, or power loss?

**State:** Exploring

**First-pass answer:** Require permit/lock closure, individual reconciliation,
physical sweep or validated presence-clearing method, restoration of guards and
safe configuration, local reset with zone visibility, warning, and separate
start. After evacuation or power loss, presume occupancy/state unknown until a
fresh clearance is completed.

**Evidence:**
- [OSHA 29 CFR 1910.147](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.147) — **Evidence class:** binding U.S. regulation. Release from energy control includes machine inspection and checks that employees are safely positioned or removed.
- [ISO 14118:2017](https://www.iso.org/standard/69190.html) — **Evidence class:** official consensus-standard scope. Unexpected startup must be prevented.

**Conflicting evidence:** Last-known badge or camera state may accelerate
reconciliation but becomes stale after power/network loss or unrecorded movement.

**Confidence:** High.

**Business-specific validation:** Drill multiple entrants, evacuation, UPS loss,
manual override, and interrupted clearance before allowing restart.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Separating authorization, presence, exit, clearance, and multi-person accounting is correct, but badges, cameras, and logs are advisory unless safety-rated and validated against hidden-person and tailgating cases.

## Scheduling, Priority, and Coordination

### ZON-039 — Which zone, route, equipment, and qualified-person reservations must be included in production scheduling?

**State:** Exploring

**First-pass answer:** Reserve the affected zone and connected equipment, safe
inbound/outbound route, isolation/restoration window, required qualified people,
tools/PPE/permits, and any adjacent-zone or material-movement exclusions.
Capacity is not available merely because the destination machine is idle.

**Evidence:**
- [GAO Schedule Assessment Guide](https://www.gao.gov/assets/gao-16-89g.pdf) — **Evidence class:** primary official guidance. Credible schedules include logic, calendars, resources, constraints, and actual status.
- [OSHA 29 CFR 1910.147](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.147) — **Evidence class:** binding U.S. regulation. Servicing requires authorized employees, energy control, coordination, and verification.

**Conflicting evidence:** Detailed reservation improves feasibility but can
create false precision if intervention duration and availability data are poor.

**Confidence:** High.

**Business-specific validation:** Shadow-schedule representative interventions
and compare every planned resource and route with observed use.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Scheduling should reserve zones, routes, equipment, and qualified people and represent access time, but it may only plan around safety constraints, not authorize or override them; AI scheduler autonomy is not mature enough to replace human review.

### ZON-040 — How are zone transition, stopping, isolation, travel, intervention, restoration, and restart time represented?

**State:** Exploring

**First-pass answer:** Model them as separate precedence-linked activities with
minimum/typical/high durations and explicit readiness/clearance milestones.
Capture actual timestamps for request, stop achieved, isolation verified, entry,
work, exit, restoration, validation, and production restart; do not hide them
inside one maintenance duration.

**Evidence:**
- [GAO Schedule Assessment Guide](https://www.gao.gov/assets/gao-16-89g.pdf) — **Evidence class:** primary official guidance. Reliable schedules use complete activity logic, realistic durations, resources, and status.
- [OSHA 29 CFR 1910.147](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.147) — **Evidence class:** binding U.S. regulation. Shutdown, isolation, stored-energy control, verification, work, and release are distinct required steps.

**Conflicting evidence:** One average is simple but conceals skew, access delay,
and restoration risk; excessive detail can exceed measurement quality.

**Confidence:** High.

**Business-specific validation:** Time at least twenty representative transitions
by task class and fit planning ranges rather than one optimistic point.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Scheduling should reserve zones, routes, equipment, and qualified people and represent access time, but it may only plan around safety constraints, not authorize or override them; AI scheduler autonomy is not mature enough to replace human review.

### ZON-041 — What priority order applies to emergencies, rescue, life safety, incident response, maintenance, quality, material recovery, and production?

**State:** Exploring

**First-pass answer:** Immutable order is immediate life safety and rescue,
emergency stabilization/evacuation, incident command and preservation of safe
conditions, then legally or technically necessary containment. After safety is
secured, prioritize maintenance/quality/material recovery and production by
consequence and approved business rules; due dates never outrank safety.

**Evidence:**
- [OSHA 29 CFR 1910.38](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.38) — **Evidence class:** binding U.S. regulation. Emergency action plans cover reporting, evacuation, critical operations, accounting, rescue/medical duties, and contacts.
- [ISO 12100:2010](https://www.iso.org/standard/51528.html) — **Evidence class:** official consensus-standard scope. Risk reduction is based on hazard severity and probability, not production value.

**Conflicting evidence:** Stopping production can create secondary hazards, so
emergency procedures may require trained critical shutdown actions before
evacuation.

**Confidence:** High.

**Business-specific validation:** Site emergency planning with responders,
authority having jurisdiction, insurer, utilities, and process specialists.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Scheduling should reserve zones, routes, equipment, and qualified people and represent access time, but it may only plan around safety constraints, not authorize or override them; AI scheduler autonomy is not mature enough to replace human review.

### ZON-042 — Which priorities may be configured, and which safety priorities are immutable?

**State:** Exploring

**First-pass answer:** Customer, setup, maintenance, inspection, and recovery
sequence may be configured within the safe feasible set. Emergency stop,
egress, rescue access, hazardous-energy control, safety-device fault response,
qualified authorization, and prevention of occupied-zone restart are immutable
constraints controlled outside ordinary scheduling.

**Evidence:**
- [NIST SP 800-82 Rev. 3](https://csrc.nist.gov/pubs/sp/800/82/r3/final) — **Evidence class:** primary official OT guidance. Least privilege, separation, configuration control, and fail-safe operation limit business-system authority.
- [OSHA 29 CFR 1910.147](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.147) — **Evidence class:** binding U.S. regulation. Production priority cannot waive required hazardous-energy protection.

**Conflicting evidence:** Configurable "safety priority" interfaces may aid
commissioning, but ordinary users should not be able to redefine risk controls.

**Confidence:** High.

**Business-specific validation:** Trace every scheduler setting to prove it
cannot alter safety logic or required procedure.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Scheduling should reserve zones, routes, equipment, and qualified people and represent access time, but it may only plan around safety constraints, not authorize or override them; AI scheduler autonomy is not mature enough to replace human review.

### ZON-043 — How are simultaneous requests from production, maintenance, inspection, cleaning, material handling, and emergency response resolved?

**State:** Exploring

**First-pass answer:** First reject any combination lacking a safe route,
compatible zone state, resources, or isolation. Emergency/life-safety command
preempts all; otherwise one zone coordinator or deterministic reservation
service sequences requests using risk, committed work, constraint impact, and
aging, with named human authority for exceptions and a visible audit trail.

**Evidence:**
- [GAO Schedule Assessment Guide](https://www.gao.gov/assets/gao-16-89g.pdf) — **Evidence class:** primary official guidance. Schedule logic and constrained resources must be reconciled rather than double-booked.
- [OSHA 29 CFR 1910.147](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.147) — **Evidence class:** binding U.S. regulation. Coordination is required where servicing and affected employees or outside personnel interact.

**Conflicting evidence:** Automated optimization can rank routine requests, but
unmodeled hazards and emergency command require human authority.

**Confidence:** High on hierarchy; medium on routine weights.

**Business-specific validation:** Tabletop simultaneous-request scenarios and
measure starvation, unsafe combinations, overrides, and response latency.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Scheduling should reserve zones, routes, equipment, and qualified people and represent access time, but it may only plan around safety constraints, not authorize or override them; AI scheduler autonomy is not mature enough to replace human review.

### ZON-044 — How does the scheduler verify a safe reachable route to and from the task rather than reserving only the destination?

**State:** Exploring

**First-pass answer:** Represent routes as time-dependent resources with
direction, clearance, zone state, access qualification, vehicle/load envelope,
and alternate egress. A reservation is feasible only if origin-to-destination
and return/exit paths remain connected for the complete task window; the safety
system still authorizes each actual crossing.

**Evidence:**
- [GAO Schedule Assessment Guide](https://www.gao.gov/assets/gao-16-89g.pdf) — **Evidence class:** primary official guidance. Complete logic and resource constraints are prerequisites to a credible schedule.
- [OSHA 29 CFR 1910.37](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.37) — **Evidence class:** binding U.S. regulation. Required exit access must remain available and unobstructed.

**Conflicting evidence:** Static shortest-path maps are efficient but ignore
temporary obstructions, zone transitions, loads, and occupied intersections.

**Confidence:** Medium-high.

**Business-specific validation:** Compare modeled routes with physical travel
for every role/load and inject closures, delays, and changing zone states.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Scheduling should reserve zones, routes, equipment, and qualified people and represent access time, but it may only plan around safety constraints, not authorize or override them; AI scheduler autonomy is not mature enough to replace human review.

### ZON-045 — How are circular zone dependencies, unreachable work, route conflicts, and potential trapped-person conditions detected?

**State:** Exploring

**First-pass answer:** Validate every schedule against a directed
zone/route/resource graph: detect cycles, mutually exclusive reservations,
missing safe return paths, lock sequences, and states with occupancy but no
egress. Reject unsafe plans before release and monitor actual state for
deviation; anti-trap safety logic remains independent.

**Evidence:**
- [GAO Schedule Assessment Guide](https://www.gao.gov/assets/gao-16-89g.pdf) — **Evidence class:** primary official guidance. Schedule-network logic is tested for missing, dangling, and invalid relationships.
- [OSHA 29 CFR 1910.37](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.37) — **Evidence class:** binding U.S. regulation. Egress cannot be obstructed or depend on special knowledge.

**Conflicting evidence:** Graph checks prove the model, not the floor; stale
maps or unreported obstructions can invalidate a mathematically feasible route.

**Confidence:** High on method; medium on completeness.

**Business-specific validation:** Independently enumerate unsafe graph states,
seed them into planning tests, and conduct as-built route audits.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Deadlock detection, conservative delay modeling, grouped interventions, due-date recalculation, and record retention are sound, but uncertainty should bias toward delayed restart rather than optimistic automation reuse.

### ZON-046 — How are delays and uncertainty in human interventions represented without causing premature automation restart?

**State:** Exploring

**First-pass answer:** Use bounded duration ranges and explicit "work complete,"
"all persons clear," "safeguards restored," and "release accepted" events.
Scheduler expiry may replan but never infer task completion or issue restart;
the zone remains safely occupied/restricted until authoritative local release.

**Evidence:**
- [GAO Schedule Assessment Guide](https://www.gao.gov/assets/gao-16-89g.pdf) — **Evidence class:** primary official guidance. Schedule risk analysis should reflect duration uncertainty and update forecasts with actual status.
- [ISO 14118:2017](https://www.iso.org/standard/69190.html) — **Evidence class:** official consensus-standard scope. Unexpected startup must be prevented during intervention.

**Conflicting evidence:** Predictive duration models may improve planning, but
they cannot replace positive completion and clearance evidence.

**Confidence:** High.

**Business-specific validation:** Compare predicted and actual intervention
distributions and test late, abandoned, and communication-loss cases.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Deadlock detection, conservative delay modeling, grouped interventions, due-date recalculation, and record retention are sound, but uncertainty should bias toward delayed restart rather than optimistic automation reuse.

### ZON-047 — How are unplanned interventions inserted without encouraging unsafe shortcuts?

**State:** Exploring

**First-pass answer:** Provide a fast formal path: stop and contain, classify the
task, invoke the approved access/energy-control procedure, reserve route and
qualified response, replan affected work, and record cause/duration. Performance
measures must not penalize a worker for requesting a safe intervention or
reward hidden jams and resets.

**Evidence:**
- [OSHA 29 CFR 1910.147](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.147) — **Evidence class:** binding U.S. regulation. Unexpected servicing exposure still requires energy-control protection.
- [NIOSH robot-related fatalities, 1992–2017](https://stacks.cdc.gov/view/cdc/230667/cdc_230667_DS1.pdf) — **Evidence class:** empirical government surveillance study. Fatalities frequently involved workers exposed during maintenance and other non-routine tasks.

**Conflicting evidence:** Strict permit processes can drive workarounds if slow;
the remedy is an efficient safe path, not reduced protection.

**Confidence:** High.

**Business-specific validation:** Observe simulated jams/faults across shifts
and measure time, compliance, pressure, and undocumented workarounds.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Deadlock detection, conservative delay modeling, grouped interventions, due-date recalculation, and record retention are sound, but uncertainty should bias toward delayed restart rather than optimistic automation reuse.

### ZON-048 — When may several nearby interventions be grouped into one planned human-access window?

**State:** Exploring

**First-pass answer:** Group work only when one validated isolation/zone state
safely covers every task, qualifications and permits are compatible, the scope
does not create conflicting work, egress remains clear, and the added duration
does not increase risk beyond approval. Close each task individually before
common restoration and restart validation.

**Evidence:**
- [OSHA 29 CFR 1910.147](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.147) — **Evidence class:** binding U.S. regulation. Group lockout and shift/personnel changes must preserve equivalent individual protection.
- [ISO 12100:2010](https://www.iso.org/standard/51528.html) — **Evidence class:** official consensus-standard scope. Risk assessment must cover actual task combinations and lifecycle activities.

**Conflicting evidence:** Bundling reduces transition loss but lengthens exposure
and can create simultaneous-work hazards or scope creep.

**Confidence:** High on conditions.

**Business-specific validation:** Pilot planned windows with pre-job briefing,
individual task sign-off, conflict review, and observed restoration.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Deadlock detection, conservative delay modeling, grouped interventions, due-date recalculation, and record retention are sound, but uncertainty should bias toward delayed restart rather than optimistic automation reuse.

### ZON-049 — How are customer due dates and automation utilization recalculated when safety access takes longer than planned?

**State:** Exploring

**First-pass answer:** Update actual transition/intervention duration, remaining
capacity, constraint queue, maintenance state, and earliest safe restart; then
recompute completion dates and communicate affected commitments. Do not recover
utilization by compressing validation, overlapping incompatible work, or
restarting before clearance.

**Evidence:**
- [GAO Schedule Assessment Guide](https://www.gao.gov/assets/gao-16-89g.pdf) — **Evidence class:** primary official guidance. Statused schedules should use actual progress and valid logic to forecast completion and risk.
- [ISO 14118:2017](https://www.iso.org/standard/69190.html) — **Evidence class:** official consensus-standard scope. Schedule pressure does not remove unexpected-startup controls.

**Conflicting evidence:** Utilization targets can encourage rapid recovery, but
local utilization is a poor objective when it increases WIP, risk, or rework.

**Confidence:** High.

**Business-specific validation:** Run schedule-impact scenarios and review
customer-notification thresholds with commercial and operations owners.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Deadlock detection, conservative delay modeling, grouped interventions, due-date recalculation, and record retention are sound, but uncertainty should bias toward delayed restart rather than optimistic automation reuse.

### ZON-050 — Which scheduling records are retained to compare planned and actual zone use, intervention time, delay, and risk?

**State:** Exploring

**First-pass answer:** Retain requested/approved windows, requester and purpose,
planned resource/route/isolation, state-transition timestamps, entrants,
actual task and duration, faults/denials/overrides, reason codes, clearance and
restart evidence, displaced jobs, and resulting due-date changes. Protect
privacy while preserving incident and improvement traceability.

**Evidence:**
- [GAO Schedule Assessment Guide](https://www.gao.gov/assets/gao-16-89g.pdf) — **Evidence class:** primary official guidance. Baselines, status dates, actual progress, logic changes, and schedule-risk records support credible analysis.
- [OSHA 29 CFR 1910.147](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.147) — **Evidence class:** binding U.S. regulation. Periodic inspections and training/certification records provide accountability for energy-control practice.

**Conflicting evidence:** Extensive surveillance may chill reporting and create
privacy/security risk; retain only decision-useful, access-controlled records.

**Confidence:** High.

**Business-specific validation:** Define legal retention/access rules, sample
incident reconstruction needs, and audit timestamp/reason-code accuracy.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Deadlock detection, conservative delay modeling, grouped interventions, due-date recalculation, and record retention are sound, but uncertainty should bias toward delayed restart rather than optimistic automation reuse.

## Human Intervention and Energy Control

### ZON-051 — Which intervention types are expected in each zone?

**State:** Exploring

Consider setup, loading, unloading, inspection, cleaning, replenishment, jam
clearing, recovery, maintenance, calibration, programming, rescue, and manual
fallback.

**First-pass answer:** Build a zone/task inventory covering the listed work plus
tool changes, sampling, waste removal, troubleshooting, contractor work, and
foreseeable misuse. For each, record frequency, duration, body location,
required motion/energy, people exposed, safe mode, and abnormal triggers; rare
fault recovery often drives the highest risk.

**Evidence:**
- [NIOSH Safe Maintenance Guidelines for Robotic Workstations](https://stacks.cdc.gov/view/cdc/158177) — **Evidence class:** primary official technical guidance. It analyzes programming, adjustment, troubleshooting, repair, and maintenance exposures.
- [ISO 12100:2010](https://www.iso.org/standard/51528.html) — **Evidence class:** official consensus-standard scope. Risk assessment covers intended use, foreseeable misuse, and all lifecycle phases.

**Conflicting evidence:** OEM task lists rarely capture site-specific jams,
cleaning, material variation, or improvised recovery.

**Confidence:** High on method; medium on completeness.

**Business-specific validation:** Observe comparable deployed equipment and run
operator/maintainer task workshops before design freeze.

**Second-opinion review:** GPT-5.4 — Agree. Intervention taxonomy, qualification, lockout, group protection, energy control, and traceable records are correctly prioritized over convenience or speed.

### ZON-052 — Which qualifications, authorizations, tools, PPE, permits, communications, and supervision does each intervention require?

**State:** Exploring

**First-pass answer:** Define a task-control matrix from hazard and complexity:
required demonstrated competency, role authorization, energy-control status,
permit, tools/test instruments, PPE, communication method, attendant or
supervisor, and rescue/escalation. Authorization expires with task, equipment,
configuration, or qualification changes.

**Evidence:**
- [OSHA 29 CFR 1910.147](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.147) — **Evidence class:** binding U.S. regulation. It defines authorized/affected roles and requires energy-control training and coordination.
- [NIOSH Safe Maintenance Guidelines for Robotic Workstations](https://stacks.cdc.gov/view/cdc/158177) — **Evidence class:** primary official technical guidance. Safe robot intervention depends on trained personnel, procedures, controls, and task-appropriate equipment.

**Conflicting evidence:** Tenure or vendor course completion is evidence of
exposure to training, not demonstrated competence on the installed task.

**Confidence:** High.

**Business-specific validation:** Practical assessments on representative
interventions, reviewed by safety, equipment, and trade-qualified authorities.

**Second-opinion review:** GPT-5.4 — Agree. Intervention taxonomy, qualification, lockout, group protection, energy control, and traceable records are correctly prioritized over convenience or speed.

### ZON-053 — Which interventions require full lockout/tagout rather than a protective stop or controlled operating mode?

**State:** Exploring

**First-pass answer:** Full lockout/tagout is required when servicing exposes a
worker to unexpected energization/startup or hazardous-energy release, unless a
specific regulatory exception and equally effective documented alternative
applies. Protective/manual modes are for validated tasks that require controlled
energy or motion and meet the applicable safeguarding conditions.

**Evidence:**
- [OSHA 29 CFR 1910.147](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.147) — **Evidence class:** binding U.S. regulation. It defines servicing scope and the narrow minor-servicing exception.
- [NIOSH Safe Maintenance Guidelines for Robotic Workstations](https://stacks.cdc.gov/view/cdc/158177) — **Evidence class:** primary official technical guidance. Unexpected robot movement during maintenance and troubleshooting is a recognized severe hazard.

**Conflicting evidence:** Control-reliable stops reduce risk but may not isolate
gravity, pressure, thermal, electrical, or connected equipment energy.

**Confidence:** High.

**Business-specific validation:** Qualified safety/legal classification of every
task and documented alternative protective measure before use.

**Second-opinion review:** GPT-5.4 — Agree. Intervention taxonomy, qualification, lockout, group protection, energy control, and traceable records are correctly prioritized over convenience or speed.

### ZON-054 — Which hazardous energy sources and stored-energy conditions must be isolated, blocked, released, restrained, or verified?

**State:** Exploring

**First-pass answer:** Inventory electrical, mechanical/kinetic, gravity,
hydraulic, pneumatic, vacuum, spring, thermal, chemical, pressure, radiation,
capacitor/battery, and connected process/material energy. Identify isolation
points and methods to dissipate, block, restrain, cool, ground, blank, or
otherwise render each source safe, then verify before work.

**Evidence:**
- [OSHA 29 CFR 1910.147](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.147) — **Evidence class:** binding U.S. regulation. It covers electrical, mechanical, hydraulic, pneumatic, chemical, thermal, and other energy and requires stored-energy control and verification.
- [ISO 14118:2017](https://www.iso.org/standard/69190.html) — **Evidence class:** official consensus-standard scope. It addresses isolation and other measures preventing unexpected startup.

**Conflicting evidence:** A disconnect or software stop may not control
downstream stored energy, backfeed, gravity, or independent supplies.

**Confidence:** High.

**Business-specific validation:** Field-verified energy-control drawings and
zero-energy tests performed by qualified personnel.

**Second-opinion review:** GPT-5.4 — Agree. Intervention taxonomy, qualification, lockout, group protection, energy control, and traceable records are correctly prioritized over convenience or speed.

### ZON-055 — How are group lockout and protection of every exposed employee handled?

**State:** Exploring

**First-pass answer:** Use a primary authorized coordinator and documented group
procedure, but give every exposed authorized employee personal lock/tag control
or an equivalent group lockbox mechanism. Account for all people and energy
points, define shift changes, and prohibit re-energization until each person's
protection is properly transferred or removed under the approved exception.

**Evidence:**
- [OSHA 29 CFR 1910.147(f)(3)](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.147) — **Evidence class:** binding U.S. regulation. Group energy control must provide protection equivalent to personal lockout and include responsibility and individual lock application.
- [OSHA lockout/tagout control of hazardous energy](https://www.osha.gov/control-hazardous-energy) — **Evidence class:** primary official guidance. It provides compliance resources for programs, procedures, training, and inspections.

**Conflicting evidence:** A supervisor-only lock is administratively simple but
does not provide each exposed authorized employee equivalent personal control.

**Confidence:** High.

**Business-specific validation:** Observe a multi-trade, multi-shift drill,
including contractor turnover and an absent worker.

**Second-opinion review:** GPT-5.4 — Agree. Intervention taxonomy, qualification, lockout, group protection, energy control, and traceable records are correctly prioritized over convenience or speed.

### ZON-056 — How are intervention start, entrants, isolation points, work status, completion, inspection, and release recorded?

**State:** Exploring

**First-pass answer:** Use a permit/work record tied to zone and equipment:
requester, scope, risk assessment, authorized entrants, isolation identifiers
and verification, start/stop times, work/findings/changes, tests and inspection,
tools/parts accountability, individual sign-off, restoration, and named release.
The physical locks/keys remain authoritative during work.

**Evidence:**
- [OSHA 29 CFR 1910.147](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.147) — **Evidence class:** binding U.S. regulation. Procedures, periodic inspections, training, notification, group control, and release require accountable implementation.
- [MIL-HDBK-61B Configuration Management Guidance](https://quicksearch.dla.mil/qsDocDetails.aspx?ident_number=202239) — **Evidence class:** primary official guidance. Changes and as-maintained configuration require traceable status and verification.

**Conflicting evidence:** Electronic permits improve sharing but must not become
a remote substitute for personal energy control or physical inspection.

**Confidence:** High.

**Business-specific validation:** Reconstruct a seeded intervention solely from
the record and compare it with physical locks, configuration, and participants.

**Second-opinion review:** GPT-5.4 — Agree. Intervention taxonomy, qualification, lockout, group protection, energy control, and traceable records are correctly prioritized over convenience or speed.

### ZON-057 — How is physical machine and robot position verified after faults, jams, dropped parts, sensor errors, or incomplete cycles?

**State:** Exploring

**First-pass answer:** Treat controller state as uncertain after such events.
Under an approved safe mode or isolation, a qualified person compares actual
axes, tooling, workpiece, fixtures, loads, and connected equipment with known
references; inspect for hidden damage and re-home/recalibrate only through the
OEM/integrator procedure before controlled test.

**Evidence:**
- [NIOSH Safe Maintenance Guidelines for Robotic Workstations](https://stacks.cdc.gov/view/cdc/158177) — **Evidence class:** primary official technical guidance. Fault diagnosis and robot maintenance expose workers to unexpected movement and require specialized controls.
- [ISO 10218-2:2025](https://www.iso.org/standard/83569.html) — **Evidence class:** official consensus-standard scope. Integration, commissioning, operation, and maintenance include application-specific validation.

**Conflicting evidence:** Encoders and digital twins provide strong evidence,
but slippage, dropped parts, damaged tooling, sensor failure, or lost reference
can make logical position differ from physical condition.

**Confidence:** High.

**Business-specific validation:** Develop fault-specific recovery checklists and
seed loss-of-reference, collision, and incomplete-cycle cases.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Position verification, manual modes, bypass control, and return-to-service evidence are the right controls, but teach or reduced-speed allowances must stay narrow and application-validated.

### ZON-058 — Which tasks may use enabling devices, hold-to-run controls, reduced speed, safe limited speed, or monitored manual modes?

**State:** Exploring

**First-pass answer:** Limit these modes to necessary setup, teaching,
diagnosis, calibration, or recovery tasks that cannot be performed isolated.
Require task-specific risk assessment, trained authorized person, direct view
and control, enabling/hold-to-run function, validated speed/force limits, and
exclusion of other people; exit the mode when the task ends.

**Evidence:**
- [ISO 10218-2:2025](https://www.iso.org/standard/83569.html) — **Evidence class:** official consensus-standard scope. Robot-cell integration includes manual/teach and collaborative operating controls.
- [NIOSH Safe Maintenance Guidelines for Robotic Workstations](https://stacks.cdc.gov/view/cdc/158177) — **Evidence class:** primary official technical guidance. Programming and troubleshooting within robot envelopes require special reduced/manual controls and procedures.

**Conflicting evidence:** Reduced speed lowers kinetic risk but does not control
sharp tools, pinch points, hot processes, payload release, or another machine.

**Confidence:** High on restrictions; medium on limits.

**Business-specific validation:** Measure actual safe-limited performance and
validate every permitted task with tooling and payload.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Position verification, manual modes, bypass control, and return-to-service evidence are the right controls, but teach or reduced-speed allowances must stay narrow and application-validated.

### ZON-059 — How are bypasses, muting, overrides, temporary safeguards, and alternative protective measures authorized and limited?

**State:** Exploring

**First-pass answer:** Prohibit routine bypass. Where technically necessary,
require a documented task/risk assessment, named qualified authorization,
time-limited and mode-limited control, equivalent alternative protection,
visible indication, logging, prevention of automatic carryover, and independent
restoration check. Muting must occur only for validated material conditions.

**Evidence:**
- [ISO 14119](https://www.iso.org/standard/63987.html) — **Evidence class:** official consensus-standard scope. Interlock selection/design addresses foreseeable defeat and guard operation.
- [ISO 13849-1:2023](https://www.iso.org/standard/71572.html) — **Evidence class:** official consensus-standard scope. Overrides and modes affecting safety functions require controlled, validated logic.

**Conflicting evidence:** Bypass capability supports diagnosis and production
flow, but normalized or poorly indicated bypasses erase the assumed safeguard.

**Confidence:** High.

**Business-specific validation:** Inventory every bypass path and test
authorization, expiry, indication, logging, and restart prevention.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Position verification, manual modes, bypass control, and return-to-service evidence are the right controls, but teach or reduced-speed allowances must stay narrow and application-validated.

### ZON-060 — What evidence is required before an intervention is released and normal automation is restored?

**State:** Exploring

**First-pass answer:** Require completed scope and inspection, fault cause and
changes recorded, tools/parts removed, physical position/configuration verified,
guards and safety devices restored and tested, calibration/quality checks where
affected, all people and personal locks cleared, dry/controlled test completed,
and named maintenance, safety, and production release as applicable.

**Evidence:**
- [OSHA 29 CFR 1910.147](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.147) — **Evidence class:** binding U.S. regulation. Release requires machine inspection, employee checks, controlled lock removal, and notification.
- [ISO 12100:2010](https://www.iso.org/standard/51528.html) — **Evidence class:** official consensus-standard scope. Risk-reduction measures require documentation and verification across the lifecycle.

**Conflicting evidence:** Alarm-free automatic self-test is useful but does not
prove physical restoration, product quality, or complete personnel clearance.

**Confidence:** High.

**Business-specific validation:** Asset/task-specific recommissioning protocols,
witnessed on representative repairs and safety-device replacements.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Position verification, manual modes, bypass control, and return-to-service evidence are the right controls, but teach or reduced-speed allowances must stay narrow and application-validated.

## Mobile Automation and Shared Operation

### ZON-061 — Which AGVs, AMRs, forklifts, cranes, carts, conveyors, and pedestrians share routes or intersections?

**State:** Exploring

**First-pass answer:** Create a route-conflict inventory by asset, load,
operating envelope, speed, stopping distance, visibility, schedule, and user
group. Separate pedestrians from powered/mobile and suspended-load traffic
where practicable; explicitly control every unavoidable intersection, doorway,
dock, lift, and conveyor transfer.

**Evidence:**
- [ISO 3691-4:2023](https://www.iso.org/standard/78482.html) — **Evidence class:** official consensus-standard scope. It addresses safety requirements and verification for driverless industrial trucks and their systems.
- [ANSI/A3 R15.08 industrial mobile robots](https://www.automate.org/store/products/ansi-a3-r15-08-american-national-standard-for-industrial-mobile-robots-imrs-imr-systems-and-imr-applications-safety-requirements-parts-1-2-and-3-pdf-download) — **Evidence class:** official industry consensus-standard source. The series covers IMR design, system integration, and deployed applications.

**Conflicting evidence:** Fleet maps show commanded paths, not forklift
shortcuts, carts, overhanging loads, pedestrians, or temporary obstructions.

**Confidence:** High on method; medium until layout and fleet are known.

**Business-specific validation:** Observe and video-map representative peak and
off-shift traffic, loads, near misses, and stopping behavior.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Mobile-automation rules need route-specific testing at worst load, surface, grade, and visibility, and fleet permissions or degraded autonomy are coordination aids, not substitutes for local obstacle detection and safe stop.

### ZON-062 — What right-of-way, speed, warning, stopping, load, intersection, and passing rules apply?

**State:** Exploring

**First-pass answer:** Prefer physical separation and engineered traffic
control over behavioral right-of-way. Set speed from worst-case stopping,
visibility, load stability, floor/grade, pedestrian proximity, and intersection
risk; define no-pass/no-entry areas, horn/light use, load limits, crossing
control, blocked-route behavior, and pedestrian rules consistently across
manual and autonomous vehicles.

**Evidence:**
- [ISO 3691-4:2023](https://www.iso.org/standard/78482.html) — **Evidence class:** official consensus-standard scope. Driverless-truck safety is evaluated as a vehicle-and-system application.
- [ANSI/A3 R15.08 industrial mobile robots](https://www.automate.org/store/products/ansi-a3-r15-08-american-national-standard-for-industrial-mobile-robots-imrs-imr-systems-and-imr-applications-safety-requirements-parts-1-2-and-3-pdf-download) — **Evidence class:** official industry consensus-standard source. It assigns design, integration, and user responsibilities for IMR risk reduction.

**Conflicting evidence:** A universal pedestrian-has-priority rule is easy to
teach but cannot guarantee a loaded vehicle can stop in time.

**Confidence:** High on factors; medium on values.

**Business-specific validation:** Instrument stopping tests at worst load,
speed, floor, grade, turn, sensor contamination, and reaction conditions.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Mobile-automation rules need route-specific testing at worst load, surface, grade, and visibility, and fleet permissions or degraded autonomy are coordination aids, not substitutes for local obstacle detection and safe stop.

### ZON-063 — How does a mobile robot know whether a destination zone currently permits entry and operation?

**State:** Exploring

**First-pass answer:** Use an authenticated, fail-safe zone-entry handshake as
coordination logic: the fleet requests destination and route, the authoritative
zone interface returns a time/state-bound permission, and independent onboard
safety functions continue obstacle detection and stopping. Missing, stale,
contradictory, or revoked permission causes stop at a defined safe holding
point. Zone permission is coordination, not the primary personnel-protection
layer.

**Evidence:**
- [ISO 3691-4:2023](https://www.iso.org/standard/78482.html) — **Evidence class:** official consensus-standard scope. It covers driverless-truck systems and verification, not navigation software alone.
- [NIST SP 800-82 Rev. 3](https://csrc.nist.gov/pubs/sp/800/82/r3/final) — **Evidence class:** primary official OT guidance. Authentication, segmentation, integrity, availability, and fail-safe behavior matter for networked control.

**Conflicting evidence:** Cloud/fleet orchestration improves routing but cannot
be the sole real-time personnel-protection layer.

**Confidence:** Medium-high.

**Business-specific validation:** Test stale maps, delayed/duplicated messages,
network loss, revoked access, wrong destination, and occupied zone.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Mobile-automation rules need route-specific testing at worst load, surface, grade, and visibility, and fleet permissions or degraded autonomy are coordination aids, not substitutes for local obstacle detection and safe stop.

### ZON-064 — How are mobile assets already inside or committed to a zone handled during a transition?

**State:** Exploring

**First-pass answer:** Before granting a conflicting state, identify assets
inside, crossing, or past the last safe stopping point. Command them to a
validated hold/exit, confirm stopped position and load stability, reserve
egress, and prevent new entry. If communication/localization is uncertain,
freeze the transition and treat the route as occupied.

**Evidence:**
- [ISO 3691-4:2023](https://www.iso.org/standard/78482.html) — **Evidence class:** official consensus-standard scope. Safe driverless-truck applications include system and environment interaction.
- [ISO 13849-1:2023](https://www.iso.org/standard/71572.html) — **Evidence class:** official consensus-standard scope. Safety-related sequence logic requires defined fault behavior.

**Conflicting evidence:** Predictive arrival data helps planning but cannot
replace positive safe-position confirmation.

**Confidence:** High on principle; medium on implementation.

**Business-specific validation:** Inject transitions at every commitment point,
including braking, docking, lift travel, network loss, and unstable load.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Mobile-automation rules need route-specific testing at worst load, surface, grade, and visibility, and fleet permissions or degraded autonomy are coordination aids, not substitutes for local obstacle detection and safe stop.

### ZON-065 — How are blind corners, doors, ramps, docks, elevators, narrow aisles, reflective surfaces, charging areas, and temporary obstructions validated?

**State:** Exploring

**First-pass answer:** Treat each as an application hazard and validate with the
actual vehicle, payload, sensors, traffic, lighting, floor, weather/contamination,
and building interface. Establish speed/one-way controls, warning or interlock,
safe waiting positions, obstruction detection, and revalidation after layout or
surface changes.

**Evidence:**
- [ANSI/A3 R15.08 industrial mobile robots](https://www.automate.org/store/products/ansi-a3-r15-08-american-national-standard-for-industrial-mobile-robots-imrs-imr-systems-and-imr-applications-safety-requirements-parts-1-2-and-3-pdf-download) — **Evidence class:** official industry consensus-standard source. IMR integration and end-use risk assessment include the deployed environment and attachments.
- [ISO 12100:2010](https://www.iso.org/standard/51528.html) — **Evidence class:** official consensus-standard scope. Foreseeable operating and abnormal conditions belong in risk assessment.

**Conflicting evidence:** Factory acceptance tests on clean, flat floors do not
represent reflections, clutter, doors, ramps, mixed traffic, or worn braking.

**Confidence:** High.

**Business-specific validation:** Structured route acceptance tests at worst
conditions and periodic audits with temporary obstructions.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Mobile-automation rules need route-specific testing at worst load, surface, grade, and visibility, and fleet permissions or degraded autonomy are coordination aids, not substitutes for local obstacle detection and safe stop.

### ZON-066 — What happens when localization, obstacle detection, route authorization, communications, braking, or map validity is lost?

**State:** Exploring

**First-pass answer:** Transition to a validated minimal-risk condition—normally
a controlled stop with load stable and warnings active—before the vehicle
leaves its assured stopping envelope. Block autonomous restart and route
transitions until the fault is diagnosed, position/map re-established, and
local area checked; define recovery/towing under energy control.

**Evidence:**
- [ISO 3691-4:2023](https://www.iso.org/standard/78482.html) — **Evidence class:** official consensus-standard scope. Driverless-truck systems require safety verification, including system failures.
- [NIST SP 800-82 Rev. 3](https://csrc.nist.gov/pubs/sp/800/82/r3/final) — **Evidence class:** primary official OT guidance. Availability and safe operation must account for communications and system compromise/failure.

**Conflicting evidence:** Continued degraded navigation may reduce blockage but
is unjustified unless the degraded mode itself is safety-validated.

**Confidence:** High.

**Business-specific validation:** Fault-inject each loss at worst speed/load and
location; measure stop, indication, containment, and recovery.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Mobile-automation rules need route-specific testing at worst load, surface, grade, and visibility, and fleet permissions or degraded autonomy are coordination aids, not substitutes for local obstacle detection and safe stop.

### ZON-067 — Which human/robot tasks qualify for validated shared or collaborative operation?

**State:** Exploring

**First-pass answer:** Only tasks whose complete application—robot, tool,
payload, workpiece, fixture, environment, speeds/forces, foreseeable contact,
other machines, and human behavior—has a documented risk assessment and
validated collaborative protective method qualify, and most current deployed
collaborative applications remain narrow, low-energy handling or assist tasks.
A "cobot" product label does not qualify the application; many sharp, hot,
heavy, or high-energy processes still need separation.

**Evidence:**
- [ISO 10218-2:2025](https://www.iso.org/standard/83569.html) — **Evidence class:** official consensus-standard scope. Collaborative operation is assessed at the integrated robot application/cell level.
- [NIOSH Center for Occupational Robotics Research](https://www.cdc.gov/niosh/centers/robotics.html) — **Evidence class:** primary official research/guidance source. NIOSH treats emerging collaborative systems as application-specific occupational risks requiring evidence.

**Conflicting evidence:** Low robot force/speed can reduce contact injury, but
tooling, payload, trapping, process energy, and foreseeable misuse may dominate.

**Confidence:** High on qualification rule; low on any task before testing.

**Business-specific validation:** Qualified collaborative-application risk
assessment and force/pressure, stopping, separation, and task trials.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The answer correctly rejects cobot labels as proof; most deployed collaborative applications remain narrow and low-energy, while hot, sharp, heavy, trapping, or ejecting tasks usually still need separation.

### ZON-068 — How are payload, tooling, grippers, sharp edges, hot parts, pinch points, projectiles, and process hazards included in collaborative risk assessment?

**State:** Exploring

**First-pass answer:** Treat the moving application, not the bare robot, as the
hazard source. Analyze maximum credible payload and speed, retention failure,
tool/workpiece geometry and temperature, crush/shear against fixtures, ejection,
process emissions, and human body locations; eliminate hazards or add
separation before relying on collaborative limits.

**Evidence:**
- [ISO 12100:2010](https://www.iso.org/standard/51528.html) — **Evidence class:** official consensus-standard scope. Machinery risk assessment covers all hazards, tasks, and foreseeable misuse.
- [ISO 10218-2:2025](https://www.iso.org/standard/83569.html) — **Evidence class:** official consensus-standard scope. The integrator validates the robot application/cell, including end effectors and process.

**Conflicting evidence:** Published robot force/pressure data usually exclude
application-specific sharp, hot, trapping, and projectile hazards.

**Confidence:** High.

**Business-specific validation:** Test worst payload/tool/workpiece and credible
failures with qualified safety engineering; redesign where safe limits cannot
be demonstrated.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The answer correctly rejects cobot labels as proof; most deployed collaborative applications remain narrow and low-energy, while hot, sharp, heavy, trapping, or ejecting tasks usually still need separation.

## Emergency, Failure, Cybersecurity, and Validation

### ZON-069 — What zone response is required for fire, smoke, gas, chemical release, medical emergency, intrusion, earthquake, flood, or utility failure?

**State:** Exploring

**First-pass answer:** Predefine event-specific detection, alarm, safe shutdown
or continued critical operation, energy/process isolation, door/route behavior,
evacuation or shelter, responder access, accounting, communications, and
restart authority. The response must consider secondary hazards: stopping can
be unsafe for some hot, pressurized, suspended, or chemical processes.

**Evidence:**
- [OSHA 29 CFR 1910.38](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.38) — **Evidence class:** binding U.S. regulation. Emergency action plans cover reporting, evacuation, critical operations, accounting, rescue/medical duties, and contacts.
- [NIST SP 800-82 Rev. 3](https://csrc.nist.gov/pubs/sp/800/82/r3/final) — **Evidence class:** primary official OT guidance. Industrial systems require availability and fail-safe planning for utility, network, and control-system disruptions.

**Conflicting evidence:** A universal "stop everything" rule can worsen certain
process hazards; event- and process-specific safe states are required.

**Confidence:** High on planning method; medium on responses.

**Business-specific validation:** Coordinate scenario plans and drills with the
fire authority, emergency services, insurer, utilities, OEMs, and safety experts.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Event-specific safe states, responder access, local safety independence from ordinary IT or cloud, cyber change control, and management-of-change revalidation are right; exact shutdown behavior is process-specific and is not always “stop everything.”

### ZON-070 — How do emergency stop, protective stop, process shutdown, energy isolation, evacuation, and rescue differ?

**State:** Exploring

**First-pass answer:** Emergency stop is a complementary rapid machinery
function for an emerging hazard; protective stop is safeguarding-triggered
motion control; process shutdown brings equipment/process to a defined state;
energy isolation prevents hazardous-energy release for work; evacuation moves
people to safety; rescue retrieves/cares for people under incident command.
None automatically performs all others.

**Evidence:**
- [ISO 13850:2015](https://www.iso.org/standard/70143.html) — **Evidence class:** official consensus-standard scope. It defines emergency-stop design principles and treats it as complementary protection.
- [OSHA 29 CFR 1910.147](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.147) — **Evidence class:** binding U.S. regulation. Stopping a control circuit is not equivalent to hazardous-energy isolation.

**Conflicting evidence:** HMIs often use "stop" generically, which can hide
materially different residual energy and restart conditions.

**Confidence:** High.

**Business-specific validation:** Define, label, train, and drill each function
for every equipment/process family.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Event-specific safe states, responder access, local safety independence from ordinary IT or cloud, cyber change control, and management-of-change revalidation are right; exact shutdown behavior is process-specific and is not always “stop everything.”

### ZON-071 — Which failures require one zone, connected zones, or the entire facility to enter a safe state?

**State:** Exploring

**First-pass answer:** Contain locally only when hazard independence is proved.
Trip connected zones for shared energy, material transfer, common safeguards,
routes, smoke/chemical spread, or control dependencies. Use facility response
for common utilities, fire/gas, structural events, compromised central safety
services, or uncertainty whose credible effects cross zones.

**Evidence:**
- [ISO 12100:2010](https://www.iso.org/standard/51528.html) — **Evidence class:** official consensus-standard scope. Risk assessment considers severity, exposure, and all foreseeable hazard interactions.
- [NIST SP 800-82 Rev. 3](https://csrc.nist.gov/pubs/sp/800/82/r3/final) — **Evidence class:** primary official OT guidance. Common networks, utilities, and control dependencies can create system-wide availability and safety consequences.

**Conflicting evidence:** Broad trips simplify safety but can create unnecessary
outages or unsafe loss of critical ventilation/process control.

**Confidence:** Medium-high.

**Business-specific validation:** Hazard/dependency analysis plus fault-injection
and emergency drills for local, coupled, and common-cause failures.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Event-specific safe states, responder access, local safety independence from ordinary IT or cloud, cyber change control, and management-of-change revalidation are right; exact shutdown behavior is process-specific and is not always “stop everything.”

### ZON-072 — How are emergency responders given safe access without depending on ordinary production credentials or schedules?

**State:** Exploring

**First-pass answer:** Provide code/authority-approved emergency access,
mechanical override or responder key control, exterior isolation/stop points,
current hazard and layout information, escorts/liaison, and routes that remain
usable during network/power loss. Emergency override must inhibit hazardous
operation and be protected from ordinary misuse.

**Evidence:**
- [OSHA 29 CFR 1910.38](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.38) — **Evidence class:** binding U.S. regulation. Plans identify rescue/medical duties and emergency contacts.
- [OSHA 29 CFR 1910.37](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.37) — **Evidence class:** binding U.S. regulation. Exit/access arrangements cannot depend on keys or special knowledge for occupants.

**Conflicting evidence:** Open responder access can conflict with security,
energized equipment, and hazardous atmospheres; authority-approved controlled
override is needed, not unrestricted entry.

**Confidence:** High.

**Business-specific validation:** Joint pre-incident walkthrough and drill with
fire/EMS/law enforcement and authority having jurisdiction.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Event-specific safe states, responder access, local safety independence from ordinary IT or cloud, cyber change control, and management-of-change revalidation are right; exact shutdown behavior is process-specific and is not always “stop everything.”

### ZON-073 — Which zone safety functions must continue during loss of ordinary network, server, cloud, scheduling, or building-management systems?

**State:** Exploring

**First-pass answer:** Local hazard detection, guard/interlock and presence
sensing, safe stop, emergency stop, prevention of unexpected restart, egress
release/lighting required by code, alarms, and safe-state retention must not
depend on ordinary IT/cloud/scheduler availability. Loss of those services
should block new hazardous operation, not disable protection.

**Evidence:**
- [NIST SP 800-82 Rev. 3](https://csrc.nist.gov/pubs/sp/800/82/r3/final) — **Evidence class:** primary official OT guidance. OT architecture must account for availability, segmentation, communications loss, and safe operation.
- [ISO 13849-1:2023](https://www.iso.org/standard/71572.html) — **Evidence class:** official consensus-standard scope. Safety-related controls require defined performance independent of ordinary business functions.

**Conflicting evidence:** Central/cloud monitoring improves visibility but adds
dependencies and cannot replace local safety execution.

**Confidence:** High.

**Business-specific validation:** Disconnect every ordinary dependency and
prove each required local safety and egress function.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Event-specific safe states, responder access, local safety independence from ordinary IT or cloud, cyber change control, and management-of-change revalidation are right; exact shutdown behavior is process-specific and is not always “stop everything.”

### ZON-074 — How is safety configuration protected from unauthorized or accidental cyber and software changes?

**State:** Exploring

**First-pass answer:** Isolate safety engineering access; require named
role-based authorization, strong authentication, approved engineering stations,
signed/versioned backups, change tickets and peer review, offline recovery,
logging/alerting, and post-change validation. Disable unused remote paths and
never permit business systems to write safety logic directly.

**Evidence:**
- [NIST SP 800-82 Rev. 3](https://csrc.nist.gov/pubs/sp/800/82/r3/final) — **Evidence class:** primary official OT-security guidance. It addresses segmentation, access control, remote access, configuration management, monitoring, backup, and recovery.
- [NIST IR 8183 Rev. 1, Cybersecurity Framework Manufacturing Profile](https://csrc.nist.gov/pubs/ir/8183/r1/final) — **Evidence class:** primary official manufacturing guidance. It applies identify-protect-detect-respond-recover outcomes to manufacturing systems.

**Conflicting evidence:** Air gaps reduce some exposure but are rarely complete;
vendors, laptops, removable media, and maintenance ports remain change paths.

**Confidence:** High.

**Business-specific validation:** Architecture review, access inventory,
restore test, configuration comparison, and authorized penetration/change-control
exercise.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Event-specific safe states, responder access, local safety independence from ordinary IT or cloud, cyber change control, and management-of-change revalidation are right; exact shutdown behavior is process-specific and is not always “stop everything.”

### ZON-075 — Which changes trigger management of change and partial or complete revalidation?

**State:** Exploring

Consider boundaries, equipment, software, safety logic, scanners, guards,
routes, speeds, payloads, tasks, shifts, staffing, adjacent operations, and
stopping performance.

**First-pass answer:** Every listed change—and repairs/substitutions, firmware,
network interfaces, workpieces/tooling, environmental conditions, bypasses, and
new failure history—requires screened management of change. Revalidate all
affected safety functions and interactions; perform complete revalidation when
the hazard model, architecture, performance level, stopping envelope, or zone
dependencies may have changed.

**Evidence:**
- [ISO 12100:2010](https://www.iso.org/standard/51528.html) — **Evidence class:** official consensus-standard scope. Risk assessment and risk reduction apply across the machinery lifecycle.
- [NIST SP 800-82 Rev. 3](https://csrc.nist.gov/pubs/sp/800/82/r3/final) — **Evidence class:** primary official guidance. OT configuration and change management are necessary because software, network, and component changes alter risk.

**Conflicting evidence:** "Like-for-like" replacement may still change firmware,
diagnostics, stopping, failure modes, or certification.

**Confidence:** High.

**Business-specific validation:** Create a change-screening matrix and challenge
it with representative mechanical, software, payload, layout, and staffing cases.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Event-specific safe states, responder access, local safety independence from ordinary IT or cloud, cyber change control, and management-of-change revalidation are right; exact shutdown behavior is process-specific and is not always “stop everything.”

### ZON-076 — How often are gates, interlocks, scanners, emergency stops, warnings, stopping times, occupancy methods, and transition sequences tested?

**State:** Exploring

**First-pass answer:** Follow law, OEM, component safety manuals, calculated
proof-test intervals, and risk assessment; perform pre-use/shift checks where
failure can be obvious and severe, scheduled functional proof tests, measured
stopping tests at defined intervals, and immediate retest after faults, bypass,
repair, impact, software/configuration change, or adverse trend. One universal
calendar interval is not defensible.

**Evidence:**
- [ISO 13849-1:2023](https://www.iso.org/standard/71572.html) — **Evidence class:** official consensus-standard scope. Required performance depends on architecture, diagnostics, component reliability, and validation.
- [OSHA 29 CFR 1910.147](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.147) — **Evidence class:** binding U.S. regulation. Energy-control procedures require at least annual periodic inspection; other safety devices may require different intervals.

**Conflicting evidence:** Frequent pushbutton tests may miss wiring, logic,
placement, and degraded stopping performance; rare full tests miss latent faults.

**Confidence:** High on risk-based scheduling; medium on intervals.

**Business-specific validation:** Obtain device/OEM proof-test requirements and
set intervals from achieved performance and recorded failure history.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Risk-based proof testing, incident review, anti-gaming metrics, role separation, and layered evidence are correct, but several citations are scope-level standards pages and do not replace clause-level review or as-built validation.

### ZON-077 — Which incidents, near misses, unexpected stops, bypasses, access denials, transition failures, and unsafe requests are reviewed?

**State:** Exploring

**First-pass answer:** Review all injuries, exposures, safety-function demands or
failures, bypasses, unauthorized/unsafe requests, trapped-person or egress
events, unexpected motion/startup, repeated access denials, and transition
aborts. Triage by potential severity and recurrence, not actual harm; preserve
data, contain, assign cause/corrective action, and verify effectiveness.

**Evidence:**
- [NIOSH robot-related fatalities, 1992–2017](https://stacks.cdc.gov/view/cdc/230667/cdc_230667_DS1.pdf) — **Evidence class:** empirical government surveillance study. Severe robot incidents frequently involve non-routine exposure and struck-by hazards.
- [ISO 12100:2010](https://www.iso.org/standard/51528.html) — **Evidence class:** official consensus-standard scope. Incident experience is an input to hazard identification and risk reduction.

**Conflicting evidence:** Reviewing every nuisance stop equally overwhelms
investigation; potential-severity triage must still detect recurring weak signals.

**Confidence:** High.

**Business-specific validation:** Define thresholds/reason codes and audit
whether sampled events, especially no-injury events, reached the right review.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Risk-based proof testing, incident review, anti-gaming metrics, role separation, and layered evidence are correct, but several citations are scope-level standards pages and do not replace clause-level review or as-built validation.

### ZON-078 — How are safety metrics designed so they do not reward hiding stops, interventions, near misses, or needed human access?

**State:** Exploring

**First-pass answer:** Never target fewer reports or safety stops in isolation.
Track reporting participation, response/closure time, repeat events, safeguard
availability, overdue tests, bypass exposure time, access demand versus plan,
and verified risk reduction. Separate production loss attribution from blame,
protect good-faith reporting, and review suspicious drops in interventions.

**Evidence:**
- [OSHA Recommended Practices for Safety and Health Programs](https://www.osha.gov/safety-management) — **Evidence class:** primary official guidance. Worker participation and reporting without retaliation are foundational program elements.
- [NIOSH Center for Occupational Robotics Research](https://www.cdc.gov/niosh/centers/robotics.html) — **Evidence class:** primary official research/guidance source. Emerging automation risks require surveillance and learning rather than assuming absence of reports means absence of hazards.

**Conflicting evidence:** Stop-rate reduction may reflect real improvement or
suppression; it is credible only with exposure, reporting, and severity context.

**Confidence:** High.

**Business-specific validation:** Confidential worker interviews, metric gaming
review, and comparison of logs, maintenance records, and reported events.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Risk-based proof testing, incident review, anti-gaming metrics, role separation, and layered evidence are correct, but several citations are scope-level standards pages and do not replace clause-level review or as-built validation.

### ZON-079 — Which qualified roles approve risk assessments, safety requirements, integration, commissioning, validation, operations, and modifications?

**State:** Exploring

**First-pass answer:** Separate accountable roles: employer/owner accepts
residual risk; qualified machinery-safety engineer leads risk assessment and
safety requirements; integrator designs and declares the application; controls,
electrical, fire/code, industrial hygiene, ergonomics, OT security, maintenance,
operations, and worker representatives review their domains; an independent
qualified validator approves tests before use and after material change.

**Evidence:**
- [ISO 10218-2:2025](https://www.iso.org/standard/83569.html) — **Evidence class:** official consensus-standard scope. It places safety obligations on integration, commissioning, operation, and maintenance of robot applications/cells.
- [ANSI/A3 R15.08 industrial mobile robots](https://www.automate.org/store/products/ansi-a3-r15-08-american-national-standard-for-industrial-mobile-robots-imrs-imr-systems-and-imr-applications-safety-requirements-parts-1-2-and-3-pdf-download) — **Evidence class:** official industry consensus-standard source. The series distinguishes manufacturer, integrator, and end-user responsibilities.

**Conflicting evidence:** Vendor approval is valuable but not independent and
does not transfer the employer's site-specific duty.

**Confidence:** High on role separation; medium on exact credentials.

**Business-specific validation:** Jurisdiction/customer/insurer review of a
signed RACI and competency evidence before procurement and commissioning.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Risk-based proof testing, incident review, anti-gaming metrics, role separation, and layered evidence are correct, but several citations are scope-level standards pages and do not replace clause-level review or as-built validation.

### ZON-080 — What evidence proves each zone and transition is safe enough for its intended tasks and operating modes?

**State:** Exploring

**First-pass answer:** No single certificate proves this. The evidence package
must include approved task/hazard analysis, safety requirements and calculations,
component suitability, as-built drawings/configuration, measured stopping and
separation, software/logic review, normal and fault validation, energy-control
procedures, human-factors/egress tests, training/competency, residual-risk
acceptance, and controlled change/proof-test records.

**Evidence:**
- [ISO 12100:2010](https://www.iso.org/standard/51528.html) — **Evidence class:** official consensus-standard scope. It requires documented hazard identification, risk estimation/evaluation, risk reduction, and verification.
- [ISO 13849-1:2023](https://www.iso.org/standard/71572.html) — **Evidence class:** official consensus-standard scope. Safety-related control performance requires design evidence and validation, not component labels alone.

**Conflicting evidence:** OEM declarations and simulations support the case but
do not cover site integration, actual tasks, people, adjacent hazards, wear, or
unauthorized changes.

**Confidence:** High.

**Business-specific validation:** Independent qualified validation of the
as-built system with affected workers, authority/insurer input, and signed
residual-risk acceptance before production.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Risk-based proof testing, incident review, anti-gaming metrics, role separation, and layered evidence are correct, but several citations are scope-level standards pages and do not replace clause-level review or as-built validation.

## Related Topics

- [Facility, Site, Utilities, and Layout](facility-site-utilities-and-layout.md)
- [Legal, Safety, and Compliance](legal-safety-and-compliance.md)
- [Standards, Certifications, and Quality Systems](standards-certifications-and-quality-systems.md)
- [Manufacturing Processes and Quality](manufacturing-processes-and-quality.md)
- [Inventory, Tooling, and Asset Management](inventory-tooling-and-asset-management.md)
- [Minimum Shop Support Capabilities](minimum-shop-support-capabilities.md)
- [People and Operating Model](people-and-operating-model.md)
- [Insurance and Risk Financing](insurance-and-risk-financing.md)
