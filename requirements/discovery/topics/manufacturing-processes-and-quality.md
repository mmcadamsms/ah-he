# Manufacturing Processes and Quality Questions

## Purpose

Questions about materials, process selection, CNC, additive manufacturing, finishing, inspection, assembly, packaging, and delivery readiness.

These are discovery questions, not approved requirements. Question IDs are permanent and canonical in this file.

## Materials

### MAT-001 — Which material families and grades are initially supported?

**State:** Open

### MAT-002 — When may the system recommend a material versus requiring qualified-human selection?

**State:** Open

### MAT-003 — How are strength, corrosion, temperature, wear, weight, cost, availability, machinability, printability, weldability, and appearance traded off?

**State:** Open

### MAT-004 — What certifications or mill test reports can customers request?

**State:** Open

### MAT-005 — How is material substitution approved?

**State:** Open

### MAT-006 — How are lot, heat, batch, and supplier traceability retained?

**State:** Open

### MAT-007 — Can customer-supplied material be accepted, and who bears the risk if it is unsuitable?

**State:** Open

### MAT-008 — How is stock availability incorporated before the design is finalized?

**State:** Open

## Manufacturing Process Selection

### MFG-001 — How do we choose between CNC machining, additive manufacturing, fabrication, forming, casting, molding, or a hybrid process?

**State:** Open

### MFG-002 — Which processes are available in-house versus through qualified partners?

**State:** Open

### MFG-003 — What capabilities define the initial manufacturing envelope: size, weight, materials, tolerances, geometry, and quantity?

**State:** Open

### MFG-004 — When should manufacturability feedback change the design?

**State:** Open

### MFG-005 — How do we present manufacturing alternatives to the customer without implying equivalent performance?

**State:** Open

### MFG-006 — What level of human review is mandatory before releasing work to a physical machine?

**State:** Open

### MFG-007 — How are prototype, bridge-production, and production processes distinguished?

**State:** Open

### MFG-008 — How are one-time tooling and fixture costs represented?

**State:** Open

### MFG-009 — When is a sacrificial feature or witness mark acceptable?

**State:** Open

### MFG-010 — Which customer surfaces may contact clamps, jaws, mandrels, supports, or fixtures?

**State:** Open

### MFG-011 — What sequence of physical capabilities and equipment should the business acquire?

**State:** Open

### MFG-012 — What year-two or year-three facility layout best supports safe flow, automation, visibility, and expansion?

**State:** Open

### MFG-013 — Can older machinery plus add-on automation create competitive unit economics?

**State:** Open

Compare acquisition, retrofit, integration, maintenance, downtime, labor,
quality, throughput, financing, energy, support, and residual value against
new equipment and outsourcing.

### MFG-014 — Which add-on automation techniques are appropriate for each machine and process?

**State:** Open

Potential techniques include:

- bar feeders and part catchers;
- pallet pools and external queues;
- robot or cobot tending;
- automatic doors;
- automated chucks, vises, fixtures, and clamps;
- probing and tool measurement;
- vision and part-presence checks;
- conveyors and bin handling;
- coolant, chip, and mist management;
- tool-life and process monitoring;
- low-cost sensors and edge controllers; and
- remote status and escalation.

### MFG-015 — What standard automation-cell architecture can span heterogeneous legacy machines?

**State:** Open

Define modular mechanical, electrical, safety, controls, networking, software,
workholding, material-presentation, and recovery interfaces so every retrofit
does not become an unrelated custom project.

### MFG-016 — Which operator tasks should automation reduce, and which should remain human?

**State:** Open

Distinguish repetitive loading, unloading, monitoring, data entry, inspection,
and material movement from setup, judgment, maintenance, problem solving,
quality release, safety, and exception recovery.

### MFG-017 — What is the safest and most economical order for retrofitting automation?

**State:** Open

Potential progression:

1. observe and measure the manual process;
2. stabilize the machine and process;
3. add monitoring and data collection;
4. improve workholding, probing, chip control, and tool-life management;
5. automate material presentation;
6. automate tending;
7. add coordinated scheduling and escalation; and
8. consider unattended operation only after demonstrated reliability.

### MFG-018 — What must be true before a legacy machine can run with reduced supervision?

**State:** Open

Include process capability, tool-life confidence, workholding verification,
part presence, probing, chip evacuation, coolant, fire risk, door and guarding,
alarm handling, safe stop, remote notification, restart authorization,
inspection, and proven recovery behavior.

### MFG-019 — How should retrofit automation performance and ROI be measured?

**State:** Open

Measure:

- operator attendance minutes per good part;
- setup time;
- spindle utilization;
- good-part throughput;
- scrap and rework;
- downtime and mean time to recovery;
- unattended productive time;
- maintenance labor;
- quality escapes;
- safety events;
- integration cost;
- payback and return; and
- performance with automation disabled.

### MFG-020 — How does a retrofitted cell degrade safely when automation fails?

**State:** Open

Define safe stop, alarm, part retention, tool and spindle state, loss of
communications, sensor failure, robot or feeder fault, power recovery, manual
fallback, restart authorization, and evidence needed before resuming.

### MFG-021 — How should low-cost technology identify manufacturing bottlenecks?

**State:** Open

Compare cameras and AI vision with machine signals, sensors, timestamps,
operator input, WIP, queues, alarms, inspection, scrap, energy, and maintenance
data. Define the minimum evidence required before labeling an activity as a
bottleneck.

### MFG-022 — How should candidate automation investments be ranked by ROI?

**State:** Open

Include throughput, attended labor, setup, quality, scrap, safety, lead time,
reliability, maintenance, flexibility, integration cost, recurring software
cost, useful life, utilization, financing, and downside risk.

### MFG-023 — How do we verify that an automation improvement removed the real bottleneck?

**State:** Open

Require before-and-after baselines, comparable product mix, confidence bounds,
new-constraint detection, quality and safety checks, employee feedback, and a
defined review period.

### MFG-024 — How do we prevent local optimization from making total shop flow worse?

**State:** Open

Evaluate whether faster output at one station increases downstream queues,
inventory, handling, inspection load, maintenance, scrap, or delivery
variability.

## CNC Planning, Workholding, and Tooling

### CNC-001 — Which machine configurations are initially supported?

**State:** Open

### CNC-002 — How are exact machine options and serial-specific limits verified?

**State:** Open

### CNC-003 — How are feasible stock forms and dimensions selected?

**State:** Open

### CNC-004 — How are physical setups minimized without sacrificing safety, rigidity, inspectability, or finish?

**State:** Open

### CNC-005 — Which planar, cylindrical, internal, hole-based, irregular, and sacrificial workholding methods are supported?

**State:** Open

### CNC-006 — How does the planner know which surfaces may remain as-stock and which must be machined?

**State:** Open

### CNC-007 — How are required tools compared with available magazine, crib, holder, and insert inventory?

**State:** Open

### CNC-008 — When should a special tool or fixture be purchased, manufactured, rented, or avoided by redesign?

**State:** Open

### CNC-009 — What verification is required before generated NC code can leave a simulation-only environment?

**State:** Open

### CNC-010 — How are probing, work offsets, tool offsets, runout, warmup, and inspection represented?

**State:** Open

### CNC-011 — Which remaining-stock and collision checks are required?

**State:** Open

### CNC-012 — How are feeds, speeds, tool life, coolant, chip evacuation, and material variability calibrated?

**State:** Open

## Additive Manufacturing

### ADD-001 — Which additive processes and materials are initially supported?

**State:** Open

### ADD-002 — How are orientation, support, anisotropy, warpage, shrinkage, porosity, and surface finish incorporated?

**State:** Open

### ADD-003 — When is additive appropriate for a functional final part versus a prototype, pattern, fixture, or visual model?

**State:** Open

### ADD-004 — What postprocessing is required?

**State:** Open

### ADD-005 — How are build failures and material-property uncertainty priced and communicated?

**State:** Open

### ADD-006 — What dimensional and mechanical validation is required?

**State:** Open

## Finishing, Coatings, and Heat Treatment

### FIN-001 — Which finishes, coatings, plating, painting, passivation, anodizing, polishing, and treatments are offered?

**State:** Open

### FIN-002 — How are masking requirements captured?

**State:** Open

### FIN-003 — How are coating thickness and dimensional effects applied to fits and tolerances?

**State:** Open

### FIN-004 — Which heat treatments require allowance for distortion or post-treatment machining?

**State:** Open

### FIN-005 — What certifications or process records are retained?

**State:** Open

### FIN-006 — How are cosmetic standards represented and approved?

**State:** Open

### FIN-007 — Which treatments introduce environmental, safety, or regulatory constraints?

**State:** Open

## Inspection, Testing, and Quality

### QUA-001 — What is the default inspection level?

**State:** Open

### QUA-002 — Which dimensions, datums, finishes, and functional characteristics are critical?

**State:** Open

### QUA-003 — Who creates and approves the inspection plan?

**State:** Open

### QUA-004 — When are CMM reports, material certificates, first article reports, process certificates, or photographs required?

**State:** Open

### QUA-005 — How are measurement uncertainty and instrument calibration handled?

**State:** Open

### QUA-006 — What constitutes acceptance for parts whose function is easier to test than fully dimension?

**State:** Open

### QUA-007 — How are nonconformances, deviations, rework, use-as-is decisions, and scrap communicated and approved?

**State:** Open

### QUA-008 — What records are retained, for how long, and for whom?

**State:** Open

### QUA-009 — How are destructive tests, life tests, pressure tests, load tests, and environmental tests priced?

**State:** Open

### QUA-010 — When is independent third-party testing required?

**State:** Open

## Assembly

### ASM-001 — Will the business ship individual parts, kits, subassemblies, complete assemblies, or all of these?

**State:** Open

### ASM-002 — How are purchased components and approved vendors selected?

**State:** Open

### ASM-003 — How are torque, adhesive, welding, alignment, cleanliness, lubrication, and test requirements recorded?

**State:** Open

### ASM-004 — How is assembly sequence validated?

**State:** Open

### ASM-005 — What functional testing is performed after assembly?

**State:** Open

### ASM-006 — How are serialized components and as-built configurations traced?

**State:** Open

### ASM-007 — What service, repair, replacement, and disassembly information accompanies the product?

**State:** Open

## Packaging, Delivery, and Installation

### DEL-001 — What packaging protection is required for corrosion, impact, contamination, cosmetic surfaces, and precision features?

**State:** Open

### DEL-002 — Which shipping methods, regions, sizes, weights, and hazardous materials are supported?

**State:** Open

### DEL-003 — When is reusable packaging justified?

**State:** Open

### DEL-004 — What documentation ships with the product?

**State:** Open

### DEL-005 — How are title, risk of loss, insurance, damage claims, and delivery acceptance handled?

**State:** Open

### DEL-006 — Will installation, commissioning, or field service ever be offered?

**State:** Open
