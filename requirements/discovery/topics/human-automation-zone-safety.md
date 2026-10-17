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

**State:** Open

### ZON-002 — Which zone states are needed for the planned facility?

**State:** Open

Consider automation-only, validated shared operation, human-only,
energy-isolated, emergency, commissioning, and degraded or unknown states.

### ZON-003 — How are a zone's hazards, equipment, boundaries, access points, routes, adjacent zones, and permitted tasks documented?

**State:** Open

### ZON-004 — Which zones should use fixed physical separation rather than dynamic sensing?

**State:** Open

### ZON-005 — When are dynamic scanner-based or task-dependent boundaries justified?

**State:** Open

### ZON-006 — Which hazards can cross a nominal zone boundary?

**State:** Open

Consider robot reach, projectiles, suspended loads, mobile equipment, heat,
fire, pressure, fumes, dust, lasers, noise, and released energy.

### ZON-007 — When must adjacent zones transition together because their hazards or routes interact?

**State:** Open

### ZON-008 — How are temporary zones created for commissioning, rigging, construction, repair, or unusual work?

**State:** Open

### ZON-009 — How are floor markings, signs, lights, audible signals, gates, barriers, and HMIs kept consistent with actual zone state?

**State:** Open

### ZON-010 — What prevents an ordinary software label, camera classification, badge record, or map geofence from being mistaken for a safety-rated boundary?

**State:** Open

## State Transitions and Safety Authority

### ZON-011 — What exact preconditions are required to transition from automation-only to human-permitted access?

**State:** Open

### ZON-012 — What exact preconditions are required to transition from human-permitted access back to automation-only?

**State:** Open

### ZON-013 — Which system may request a transition, and which safety system or procedure may authorize it?

**State:** Open

### ZON-014 — How is production scheduling prevented from bypassing or substituting for safety authorization?

**State:** Open

### ZON-015 — Which equipment must stop, slow, isolate, drain, block, cool, or otherwise reach a safe condition before entry?

**State:** Open

### ZON-016 — Which tasks are allowed under a protective stop, human-only state, controlled manual mode, or lockout/tagout?

**State:** Open

### ZON-017 — How are connected machines, conveyors, robots, stored energy, and adjacent processes included in a transition?

**State:** Open

### ZON-018 — Which safety devices and conditions must be healthy before a zone can enter each state?

**State:** Open

### ZON-019 — What happens if transition conditions change or fail midway through the sequence?

**State:** Open

### ZON-020 — How long may a transition remain pending before it times out into a restricted state?

**State:** Open

### ZON-021 — Which transitions require a warning interval, local acknowledgment, or supervisor approval?

**State:** Open

### ZON-022 — Where may reset controls be located, and what zone visibility or presence evidence is required?

**State:** Open

### ZON-023 — How is reset kept separate from the command that initiates hazardous motion?

**State:** Open

### ZON-024 — Which transitions may be requested remotely, and which actions must always occur locally?

**State:** Open

### ZON-025 — How are zone state machines, timing, stopping distance, device placement, and failure behavior validated and versioned?

**State:** Open

## Ingress, Egress, Occupancy, and Travel

### ZON-026 — Can every routinely occupied area be reached without crossing an active automation-only zone?

**State:** Open

### ZON-027 — Where crossing an automation zone cannot be avoided, what separated path or validated transition protects the crossing?

**State:** Open

### ZON-028 — Which primary and alternate routes exist for employees, visitors, maintenance, material handlers, and emergency responders?

**State:** Open

### ZON-029 — How are required exit routes kept available under every approved zone schedule?

**State:** Open

### ZON-030 — How can a person stop hazardous operation and leave a zone if badges, networks, schedulers, or ordinary controls fail?

**State:** Open

### ZON-031 — Which access points are entries, exits, emergency releases, material portals, robot passages, or maintenance openings?

**State:** Open

### ZON-032 — How are access authorization, entry permission, physical presence, exit, and zone clearance represented as separate facts?

**State:** Open

### ZON-033 — Which occupancy-detection methods are safety rated, and which provide advisory evidence only?

**State:** Open

### ZON-034 — How are multiple entrants, tailgating, contractors, visitors, dropped badges, hidden people, pits, platforms, and rescue entry handled?

**State:** Open

### ZON-035 — How is a person tracked when entering through one zone and leaving through another?

**State:** Open

### ZON-036 — When is destination acceptance or an entry permit required before someone begins crossing several zones?

**State:** Open

### ZON-037 — What prevents a transition from trapping a person between zones or behind a locked access point?

**State:** Open

### ZON-038 — How are people accounted for before automation restarts after an intervention, evacuation, or power loss?

**State:** Open

## Scheduling, Priority, and Coordination

### ZON-039 — Which zone, route, equipment, and qualified-person reservations must be included in production scheduling?

**State:** Open

### ZON-040 — How are zone transition, stopping, isolation, travel, intervention, restoration, and restart time represented?

**State:** Open

### ZON-041 — What priority order applies to emergencies, rescue, life safety, incident response, maintenance, quality, material recovery, and production?

**State:** Open

### ZON-042 — Which priorities may be configured, and which safety priorities are immutable?

**State:** Open

### ZON-043 — How are simultaneous requests from production, maintenance, inspection, cleaning, material handling, and emergency response resolved?

**State:** Open

### ZON-044 — How does the scheduler verify a safe reachable route to and from the task rather than reserving only the destination?

**State:** Open

### ZON-045 — How are circular zone dependencies, unreachable work, route conflicts, and potential trapped-person conditions detected?

**State:** Open

### ZON-046 — How are delays and uncertainty in human interventions represented without causing premature automation restart?

**State:** Open

### ZON-047 — How are unplanned interventions inserted without encouraging unsafe shortcuts?

**State:** Open

### ZON-048 — When may several nearby interventions be grouped into one planned human-access window?

**State:** Open

### ZON-049 — How are customer due dates and automation utilization recalculated when safety access takes longer than planned?

**State:** Open

### ZON-050 — Which scheduling records are retained to compare planned and actual zone use, intervention time, delay, and risk?

**State:** Open

## Human Intervention and Energy Control

### ZON-051 — Which intervention types are expected in each zone?

**State:** Open

Consider setup, loading, unloading, inspection, cleaning, replenishment, jam
clearing, recovery, maintenance, calibration, programming, rescue, and manual
fallback.

### ZON-052 — Which qualifications, authorizations, tools, PPE, permits, communications, and supervision does each intervention require?

**State:** Open

### ZON-053 — Which interventions require full lockout/tagout rather than a protective stop or controlled operating mode?

**State:** Open

### ZON-054 — Which hazardous energy sources and stored-energy conditions must be isolated, blocked, released, restrained, or verified?

**State:** Open

### ZON-055 — How are group lockout and protection of every exposed employee handled?

**State:** Open

### ZON-056 — How are intervention start, entrants, isolation points, work status, completion, inspection, and release recorded?

**State:** Open

### ZON-057 — How is physical machine and robot position verified after faults, jams, dropped parts, sensor errors, or incomplete cycles?

**State:** Open

### ZON-058 — Which tasks may use enabling devices, hold-to-run controls, reduced speed, safe limited speed, or monitored manual modes?

**State:** Open

### ZON-059 — How are bypasses, muting, overrides, temporary safeguards, and alternative protective measures authorized and limited?

**State:** Open

### ZON-060 — What evidence is required before an intervention is released and normal automation is restored?

**State:** Open

## Mobile Automation and Shared Operation

### ZON-061 — Which AGVs, AMRs, forklifts, cranes, carts, conveyors, and pedestrians share routes or intersections?

**State:** Open

### ZON-062 — What right-of-way, speed, warning, stopping, load, intersection, and passing rules apply?

**State:** Open

### ZON-063 — How does a mobile robot know whether a destination zone currently permits entry and operation?

**State:** Open

### ZON-064 — How are mobile assets already inside or committed to a zone handled during a transition?

**State:** Open

### ZON-065 — How are blind corners, doors, ramps, docks, elevators, narrow aisles, reflective surfaces, charging areas, and temporary obstructions validated?

**State:** Open

### ZON-066 — What happens when localization, obstacle detection, route authorization, communications, braking, or map validity is lost?

**State:** Open

### ZON-067 — Which human/robot tasks qualify for validated shared or collaborative operation?

**State:** Open

### ZON-068 — How are payload, tooling, grippers, sharp edges, hot parts, pinch points, projectiles, and process hazards included in collaborative risk assessment?

**State:** Open

## Emergency, Failure, Cybersecurity, and Validation

### ZON-069 — What zone response is required for fire, smoke, gas, chemical release, medical emergency, intrusion, earthquake, flood, or utility failure?

**State:** Open

### ZON-070 — How do emergency stop, protective stop, process shutdown, energy isolation, evacuation, and rescue differ?

**State:** Open

### ZON-071 — Which failures require one zone, connected zones, or the entire facility to enter a safe state?

**State:** Open

### ZON-072 — How are emergency responders given safe access without depending on ordinary production credentials or schedules?

**State:** Open

### ZON-073 — Which zone safety functions must continue during loss of ordinary network, server, cloud, scheduling, or building-management systems?

**State:** Open

### ZON-074 — How is safety configuration protected from unauthorized or accidental cyber and software changes?

**State:** Open

### ZON-075 — Which changes trigger management of change and partial or complete revalidation?

**State:** Open

Consider boundaries, equipment, software, safety logic, scanners, guards,
routes, speeds, payloads, tasks, shifts, staffing, adjacent operations, and
stopping performance.

### ZON-076 — How often are gates, interlocks, scanners, emergency stops, warnings, stopping times, occupancy methods, and transition sequences tested?

**State:** Open

### ZON-077 — Which incidents, near misses, unexpected stops, bypasses, access denials, transition failures, and unsafe requests are reviewed?

**State:** Open

### ZON-078 — How are safety metrics designed so they do not reward hiding stops, interventions, near misses, or needed human access?

**State:** Open

### ZON-079 — Which qualified roles approve risk assessments, safety requirements, integration, commissioning, validation, operations, and modifications?

**State:** Open

### ZON-080 — What evidence proves each zone and transition is safe enough for its intended tasks and operating modes?

**State:** Open

## Related Topics

- [Facility, Site, Utilities, and Layout](facility-site-utilities-and-layout.md)
- [Legal, Safety, and Compliance](legal-safety-and-compliance.md)
- [Standards, Certifications, and Quality Systems](standards-certifications-and-quality-systems.md)
- [Manufacturing Processes and Quality](manufacturing-processes-and-quality.md)
- [Inventory, Tooling, and Asset Management](inventory-tooling-and-asset-management.md)
- [Minimum Shop Support Capabilities](minimum-shop-support-capabilities.md)
- [People and Operating Model](people-and-operating-model.md)
- [Insurance and Risk Financing](insurance-and-risk-financing.md)
