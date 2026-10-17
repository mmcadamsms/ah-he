# Human and Automation Zone Safety and Scheduling

## Problem

The planned facility may contain robots, machine-tending systems, conveyors,
mobile robots, automated storage, material movement, and other equipment that
can operate with limited human attendance. Some areas may be safe only when
humans are excluded. Other work will require employees to enter, cross, set up,
inspect, recover, clean, maintain, or manually operate within those same areas.

A person may also need to cross one automated area to reach another work area
or an exit. Production scheduling, access badges, floor markings, or a software
status label cannot by themselves make that crossing safe. Without a validated
zone model and fail-safe transitions, the facility could restart automation
while a person remains present, trap someone behind access controls, route a
person through active hazards, or allow production priority to override life
safety.

## Objective

Provide a facility-wide zone-safety operating model that:

- defines physical and logical safety zones and their hazards;
- controls transitions among automation-only, shared human/automation,
  human-only, energy-isolated, and emergency states;
- separates production scheduling and priority from safety authorization;
- coordinates human access, interventions, material movement, and automation;
- preserves safe ingress, egress, evacuation, rescue, and alternate routes;
- prevents automatic restart while a person may remain exposed;
- records zone requests, transitions, occupancy, interventions, overrides,
  faults, and responsible actors; and
- requires qualified risk assessment, safety design, commissioning, validation,
  and periodic revalidation.

## Terminology

- **Zone:** A bounded physical area whose hazards, access routes, equipment,
  operating modes, and safety controls are explicitly defined.
- **Zone boundary:** A fixed or validated dynamic boundary implemented with
  appropriate barriers, gates, scanners, light curtains, mats, doors,
  distance, or other safeguards.
- **Automation-only state:** Hazardous automatic operation is permitted only
  while human presence is excluded and all required safeguards are healthy.
- **Shared state:** Human and automated activity may coexist only for tasks and
  equipment covered by a validated collaborative or speed-and-separation risk
  reduction design.
- **Human-only state:** Human work is permitted while hazardous automatic
  operation in the zone is inhibited. This state does not necessarily satisfy
  servicing or maintenance energy-isolation requirements.
- **Energy-isolated state:** Applicable hazardous energy is isolated, locked,
  tagged, released, blocked, or otherwise controlled under the approved
  lockout/tagout or alternative-protection procedure.
- **Emergency state:** Automation enters the validated emergency response or
  safe state and human evacuation or rescue takes precedence over production.
- **Degraded or unknown state:** Required safety status, communications,
  occupancy, boundary, or device health cannot be established. Hazardous
  automation is prohibited until the condition is resolved and validated.
- **Zone transition:** The controlled sequence that changes a zone from one
  validated state to another.
- **Transition request:** A scheduling or operational request to change state.
  A request is not safety authorization and does not prove the transition
  completed.
- **Occupancy:** The best supported determination of people present in or
  committed to enter a zone. Badge data alone is not automatically
  safety-rated occupancy detection.
- **Intervention:** Authorized human entry for setup, loading, unloading,
  inspection, clearing, recovery, cleaning, adjustment, maintenance, rescue,
  or another defined task.
- **Safeguarded space:** An area where access is controlled by the protective
  measures established by the applicable machine or robot risk assessment.
- **Safety function:** A validated function that reduces risk, such as a safe
  stop, guard interlock, speed limitation, separation monitoring, presence
  detection, safe torque off, or emergency stop.
- **Production scheduler:** The system that sequences jobs, equipment, people,
  and material. It may request a transition but must not substitute for the
  safety-control system.
- **Safety-control system:** The safety-rated hardware, logic, devices, and
  procedures that determine whether hazardous operation or access is allowed.

## Inputs

- scaled facility layout and zone boundaries;
- equipment, robot, AMR, AGV, conveyor, vehicle, and stored-energy hazards;
- machine and robot risk assessments;
- required performance level, safety integrity, and stopping-distance
  calculations;
- fixed guarding, gates, doors, scanners, light curtains, mats, interlocks,
  emergency stops, warning devices, and safety-controller architecture;
- normal, setup, collaborative, recovery, maintenance, cleaning, and emergency
  tasks;
- employee roles, qualifications, authorization, and required PPE;
- occupancy and access-detection technologies and their safety ratings;
- pedestrian, vehicle, material, intervention, rescue, and evacuation routes;
- production schedules, intervention requests, maintenance windows, and job
  priorities;
- fire, chemical, thermal, pressure, electrical, mechanical, and environmental
  hazards;
- lockout/tagout and alternative-protection procedures;
- network, power, communications, and device-failure behavior;
- emergency action, incident response, first aid, and rescue plans;
- applicable OSHA requirements, adopted codes, consensus standards, insurer
  conditions, and qualified safety-engineering decisions; and
- revision-controlled commissioning and validation evidence.

## Zone Model

1. Every automation zone must have a unique identity, owner, purpose, physical
   boundary, hazards, entrances, exits, adjacent zones, equipment, routes, and
   allowed states.
2. A zone may contain subzones only when boundary interactions and combined
   hazards are included in the risk assessment.
3. Zone boundaries may be fixed or dynamically monitored, but dynamic
   boundaries must use validated safety functions appropriate to the risk.
4. Ordinary GPS, Wi-Fi, camera analytics, badge systems, scheduling software,
   or unsafety-rated geofencing may provide evidence or convenience but must
   not be the sole protective measure for a hazardous boundary.
5. Adjacent zones must account for projectiles, reach, stopping distance,
   suspended loads, heat, fumes, pressure, fire, mobile equipment, and hazards
   that cross a drawn floor line.
6. Each zone must publish the active state locally using clear visual and,
   where required, audible indication that is understandable without consulting
   a remote application.
7. Human-only does not imply energy-isolated. The task-specific risk
   assessment determines when lockout/tagout or another qualified hazardous
   energy control is required.
8. Shared operation is allowed only for validated applications and tasks; a
   robot marketed as collaborative does not make the entire zone collaborative.

## Zone-State Transition Rules

1. A scheduler, operator, maintenance system, or automation may request a
   transition, but only the safety-control system and approved procedure may
   authorize the hazardous state change.
2. A transition to human access must, as applicable:
   - stop or quiesce affected automation;
   - prevent new automated work from entering;
   - place equipment in the validated safe operating state;
   - control stored and hazardous energy for the intended task;
   - verify gates, doors, barriers, and safety devices;
   - account for connected and adjacent equipment;
   - indicate that entry is permitted; and
   - record the requesting and responsible actors.
3. If a person must cross an automation-only zone, that crossing must use a
   permanently separated safe path, a validated shared-mode path, or a
   completed transition to a human-permitted state. Scheduling permission
   alone is insufficient.
4. Entry must not be enabled until the zone reports the required safe state.
5. Opening an interlocked access point or detecting an unauthorized entry must
   produce the validated protective response.
6. A person inside a zone must be able to stop hazardous motion and leave
   without needing a credential, scheduler approval, network connection, or
   production-system response.
7. A transition back to automation-only must, as applicable:
   - complete or suspend the intervention;
   - account for every authorized entrant;
   - verify exit and zone clearance using the approved method;
   - restore guards, gates, tools, fixtures, and process conditions;
   - remove locks or tags only under the approved procedure;
   - require deliberate reset from a safe location with zone visibility or
     equivalent validated evidence;
   - provide a warning interval where required; and
   - require a separate deliberate start command.
8. Reset must not itself initiate hazardous motion.
9. Automatic or remote restart is prohibited when occupancy, access-point
   status, safety-device health, intervention completion, or zone clearance is
   unknown.
10. A failed or interrupted transition must enter a defined safe or restricted
    state rather than guessing the intended state.
11. Transitions involving several zones must be coordinated as one validated
    sequence where hazards or routes interact.
12. Transition logic, timing, stopping distance, device placement, and failure
    behavior must be validated and revision controlled.

## Scheduling and Priority Rules

1. Production scheduling may reserve zones, people, equipment, and transition
   windows, but it cannot override a safety function or authorize hazardous
   access.
2. Safety and emergency priorities always outrank production throughput,
   utilization, due date, and automation efficiency.
3. The scheduling model must include:
   - zone state and transition time;
   - intervention duration and uncertainty;
   - qualified-person availability;
   - access and travel routes;
   - adjacent-zone dependencies;
   - required isolation and restoration time;
   - material and equipment movements;
   - evacuation and emergency constraints; and
   - recovery from delay or transition failure.
4. The scheduler must not plan a person or vehicle through a zone whose
   projected state does not permit the route.
5. The scheduler must provide a safe reachable route to the task location and
   back, not merely reserve the destination zone.
6. Conflicting requests must be resolved using an approved priority policy
   that distinguishes emergency, rescue, safety intervention, maintenance,
   quality hold, material recovery, and ordinary production.
7. Priority changes must never shorten or skip a required stop, isolation,
   verification, warning, reset, or authorization step.
8. The system must detect zone deadlocks, unreachable work areas, trapped
   personnel risk, circular transition dependencies, and evacuation routes
   blocked by planned automation states.
9. Delayed human work must not cause automation to resume if the intervention
   remains open or occupancy is unresolved.
10. Unplanned interventions must follow an approved safe-entry workflow rather
    than being forced into the ordinary production schedule.

## Ingress, Egress, Occupancy, and Access Rules

1. Required exit routes and emergency egress must remain available under every
   normal zone schedule and credible single failure defined by the risk
   assessment.
2. Emergency egress must not depend solely on software scheduling, ordinary
   access control, badge readers, or network availability.
3. The facility should avoid designs that require routine passage through an
   automation-only hazard zone to reach occupied work areas.
4. Where crossing cannot be eliminated, the crossing design must be separately
   risk assessed and protected.
5. Access authorization, safety permission, and occupancy confirmation are
   distinct facts.
6. The system must know which entry points are open, closed, locked,
   interlocked, faulted, bypassed, or under maintenance.
7. Occupancy methods must address tailgating, multiple people, visitors,
   contractors, dropped badges, sensor occlusion, people hidden by equipment,
   ladders or platforms, pits, overhead areas, and rescue entry.
8. Any safety-device bypass or muting must be task-specific, time-bounded,
   authorized, indicated, logged, and protected by the validated alternative
   measures.
9. Visitors and unqualified personnel must not receive access merely because a
   zone is in human-only state.
10. The system must account for people who enter one zone and exit through
    another.

## Human Intervention and Hazardous Energy Control

1. Every intervention type must define the permitted zone state, required
   qualification, energy-control method, tools, PPE, duration, communication,
   and completion evidence.
2. Routine production access, minor servicing, setup, recovery, maintenance,
   and emergency rescue must not be treated as equivalent tasks.
3. Tasks requiring lockout/tagout must use the approved energy-control
   procedure; a software stop or human-only state is not a substitute.
4. Group interventions must define a responsible authorized employee and
   individual protection for each exposed person as required.
5. Stored mechanical, pneumatic, hydraulic, electrical, thermal, pressure,
   gravity, chemical, and motion energy must be addressed.
6. Intervention permits or work records must link the zone, equipment, task,
   entrants, isolation points, start, status, completion, and release.
7. Recovery after jams, dropped material, robot faults, sensor faults, and
   incomplete cycles must begin from a known state and must not assume the
   programmed position equals the physical position.
8. Remote assistance may guide an authorized person but must not remotely
   remove protection or restart hazardous operation without the validated
   local procedure.

## Mobile Automation and Shared Routes

1. AGVs, AMRs, forklifts, carts, cranes, and pedestrians must have defined
   routes, right-of-way, intersections, speeds, stopping behavior, load
   constraints, and degraded-mode rules.
2. A mobile robot entering a human-only zone must satisfy the permitted state
   and task rules for that zone.
3. A zone transition must account for mobile equipment already committed to or
   physically inside the zone.
4. Blind corners, doors, narrow aisles, charging areas, docks, elevators,
   ramps, reflective surfaces, temporary obstructions, and mixed traffic must
   be included in validation.
5. Loss of localization, obstacle sensing, communications, map validity, or
   route authorization must produce the validated safe response.

## Status, Audit, and Change Control

Record, subject to retention and access policy:

- transition request, reason, source, priority, and planned window;
- preconditions and safety-device health;
- actual transition start and completion;
- prior and resulting state;
- entry and exit events;
- responsible and authorizing actors;
- intervention, isolation, permit, and completion records;
- alarms, protective stops, emergency stops, faults, bypasses, and resets;
- warnings and acknowledgments;
- restart authorization and start event;
- rejected or timed-out transitions;
- schedule conflicts and overrides;
- software, safety logic, configuration, layout, device, and risk-assessment
  revision; and
- incident, near-miss, test, inspection, and validation evidence.

Changes to boundaries, equipment, tasks, routes, speeds, payloads, software,
safety logic, sensors, stopping performance, staffing, or adjacent operations
must trigger the defined management-of-change and revalidation process.

## Assumptions

- Early facilities should prefer physical separation and simple fixed zones
  over complex dynamic zoning where practical.
- Production systems may display zone state and coordinate schedules, but
  certified or otherwise qualified safety controls and procedures govern
  hazardous transitions.
- Personnel-location technologies may improve awareness while remaining
  advisory unless explicitly designed, rated, and validated as part of a
  safety function.
- Exact safety architecture, performance levels, separation distances, and
  standards applicability require qualified risk assessment and engineering.

## Constraints

- Generative AI must not directly authorize zone entry, remove isolation,
  reset safety functions, or start hazardous automation.
- Ordinary business software, cloud availability, or network connectivity must
  not be required for emergency stop or emergency egress.
- Cybersecurity controls must protect safety configuration and commands without
  preventing emergency response.
- Safety controls must fail to the validated safe or restricted state.
- The design must comply with applicable OSHA requirements, adopted building
  and fire codes, consensus standards, manufacturer instructions, and insurer
  conditions.
- Human privacy and employment policies must govern personnel-location and
  access records without weakening required safety evidence.

## Failure Behavior

Hazardous automation must remain inhibited or transition to the validated safe
state when:

- zone state, occupancy, boundary, route, or safety-device health is unknown;
- an entry point is open, bypassed, faulted, or reports conflicting state;
- an authorized entrant has not been accounted for;
- a transition times out or preconditions change;
- stopping performance, safe speed, separation, or position cannot be assured;
- required isolation, permit, inspection, or intervention completion is
  missing;
- communications, localization, power, or control faults affect a safety
  dependency;
- a schedule requests an unsafe route or state;
- emergency egress or rescue access would be blocked;
- an unauthorized entry or unexpected presence is detected; or
- configuration or validation does not match the physical system.

The system must expose the blocking reason and required qualified response. It
must not silently downgrade protection to preserve production.

## Acceptance Criteria

1. Every automation zone has revision-controlled boundaries, hazards,
   equipment, access points, adjacent zones, routes, and permitted states.
2. Automation-only, shared, human-only, energy-isolated, emergency, and
   degraded states have explicit entry, operation, and exit rules.
3. A production schedule can request but cannot directly complete or bypass a
   safety transition.
4. A human cannot be routed through an active automation-only zone without a
   physically separated safe route or completed validated transition.
5. Entry permission is withheld until required safe-state preconditions are
   confirmed.
6. Opening an interlocked boundary or detecting unexpected presence produces
   the validated protective response.
7. A person can stop hazardous motion and exit without production-system or
   network authorization.
8. Restart is impossible until entrants are accounted for, boundaries are
   restored, safety devices are healthy, intervention release is complete, and
   a deliberate reset and separate start occur.
9. Reset alone does not initiate motion.
10. A task requiring hazardous-energy isolation cannot be released using only
    a software stop or human-only zone state.
11. The scheduler accounts for transition, isolation, intervention, travel,
    restoration, and uncertainty time.
12. Emergency, rescue, and life-safety priorities cannot be displaced by
    production priority.
13. Required egress remains available under all approved zone schedules.
14. The system detects plans that would create an unreachable work area,
    trapped-person risk, circular zone dependency, or blocked evacuation path.
15. Zone occupancy and access records distinguish authorization, entry,
    presence evidence, exit, and clearance.
16. Mobile automation follows validated zone, route, intersection, and
    degraded-mode rules.
17. Failed communications, sensing, localization, interlock, or transition
    state inhibits hazardous operation as defined by the risk assessment.
18. Every override, bypass, mute, fault, protective stop, emergency stop,
    reset, intervention, and restart is auditable.
19. A physical or software change affecting the risk assessment triggers
    management of change and required revalidation.
20. Qualified safety reviewers can trace each implemented safety function to
    its hazard, required risk reduction, design, test, and validation evidence.

## Out of Scope

- Treating a scheduling application as a safety-rated controller.
- Assuming cobot branding makes unrestricted human sharing safe.
- Replacing machine-specific, robot-specific, task-specific, or facility-wide
  risk assessment.
- Defining final performance levels, safety integrity levels, separation
  distances, stopping times, or device placement without qualified engineering.
- Using personnel surveillance for unrelated performance management.
- Allowing production optimization to weaken required safeguards.

## References

- [Facility Site, Utilities, and Layout](facility-site-utilities-and-layout.md)
- [Human and Automation Zone Safety Questions](../../discovery/topics/human-automation-zone-safety.md)
- [Legal, Safety, and Compliance Questions](../../discovery/topics/legal-safety-and-compliance.md)
- [Standards, Certifications, and Quality Systems](../../discovery/topics/standards-certifications-and-quality-systems.md)
- [Inventory, Material Movement, Tooling, and Equipment Lifecycle Management](../manufacturing-automation/inventory-tooling-and-equipment-lifecycle.md)
- [OSHA Control of Hazardous Energy](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.147)
- [OSHA Exit Routes](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.36)
- [OSHA Emergency Action Plans](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.38)
- [OSHA Machine Guarding](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.212)
- [OSHA Robotics Technical Manual](https://www.osha.gov/otm/section-iv-chapter-4)
- ISO 12100, ISO 10218, ISO 13849, IEC 62061, ISO 3691-4, ANSI/A3 R15.06,
  ANSI/A3 R15.08, ANSI B11 standards, NFPA 79, and applicable adopted editions.
