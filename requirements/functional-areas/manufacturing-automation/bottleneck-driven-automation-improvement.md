# Bottleneck-Driven Automation Improvement

## Problem

A small manufacturing business cannot afford to automate every process at
once, and visual intuition alone can misidentify where labor, machine capacity,
quality, or lead time is actually being lost. Automation applied to the wrong
constraint may move waiting or inventory elsewhere without improving customer
delivery or business return.

## Objective

Use off-the-shelf, low-total-cost observation technology and operational data
to identify measurable manufacturing bottlenecks, compare possible
interventions, recommend the next automation investment by risk-adjusted
return, and verify whether the implemented change improved total system
performance.

## Terminology

- **Observation:** Raw or derived evidence about work, including camera,
  machine, sensor, operator, material, quality, maintenance, and job events.
- **Bottleneck:** A constrained resource or recurring condition that materially
  limits total system throughput, delivery, quality, safety, or cost.
- **Intervention:** A proposed process, tooling, software, equipment,
  workholding, staffing, layout, or automation change.
- **Local optimization:** Improvement at one station that does not improve, or
  harms, total system performance.
- **Risk-adjusted ROI:** Expected economic benefit after considering
  uncertainty, implementation cost, downtime, maintenance, safety, quality,
  flexibility, and failure risk.

## Inputs

The analysis may consume:

- job routing and timestamps;
- queue and work-in-process state;
- machine state, alarms, cycle events, load, and utilization;
- cameras and derived vision events;
- low-cost sensors and edge-device events;
- setup and operator-attendance time;
- material movement and waiting;
- inspection, scrap, rework, and quality-escape data;
- maintenance and downtime;
- energy and consumables;
- staffing and schedule;
- customer promise and actual delivery;
- intervention cost and useful-life assumptions; and
- structured employee observations.

## Rules

1. Prefer commercially available, interoperable, low-TCO components before
   proposing custom hardware or software.
2. Use the least intrusive observation method that can answer the question.
3. Camera and AI output is evidence with a confidence level, not an
   unquestioned source of truth.
4. Bottleneck findings must cite the observation period, product mix, data
   coverage, assumptions, confidence, and alternative explanations.
5. Recommendations must compare multiple intervention types, including
   process simplification, scheduling, maintenance, tooling, layout, training,
   outsourcing, staffing, and automation.
6. Rank interventions by total-system, risk-adjusted return rather than
   reduction of labor at one station.
7. Recommendations must include expected benefit, implementation cost,
   downtime, recurring cost, safety and quality effects, maintenance burden,
   useful life, flexibility, dependencies, and payback assumptions.
8. A qualified human approves physical or hazardous-process changes.
9. Observation systems must not bypass machine safety functions or directly
   control hazardous equipment merely because they detect a condition.
10. After implementation, compare actual results with the baseline and detect
    whether the constraint moved elsewhere.
11. Preserve negative results so failed automation ideas are not repeatedly
    proposed.
12. Employees may provide context, corrections, and improvement ideas; the
    system must not use incomplete camera interpretation as an automatic
    disciplinary conclusion.

## Assumptions

- Early implementations may combine manual event labeling with automated data.
- Existing machines may expose little or no reliable digital telemetry.
- Cameras may initially provide process-state evidence rather than precision
  inspection.
- Financial models use explicit ranges when demand, uptime, or benefit is
  uncertain.

## Constraints

- Protect employee and customer privacy.
- Minimize retention of identifiable video when derived events are sufficient.
- Isolate observation networks from machine-control networks.
- Comply with applicable employment, surveillance, safety, cybersecurity, and
  recording laws.
- Do not represent correlation as causal proof.
- Do not recommend reduced staffing or unattended operation without separate
  safety, quality, and recovery validation.

## Failure Behavior

The system must report an unresolved result rather than fabricate a bottleneck
or ROI recommendation when:

- data coverage is insufficient;
- clocks or event sources cannot be reconciled;
- camera confidence is inadequate;
- product mix or operating conditions make the baseline incomparable;
- costs or benefits cannot be bounded;
- employee observations materially conflict with automated interpretation; or
- the proposed change creates unresolved safety, quality, or compliance risk.

## Acceptance Criteria

1. A finding identifies the constrained resource or condition, observation
   period, affected jobs, evidence, confidence, and alternative explanations.
2. At least two materially different interventions are compared when feasible.
3. Every recommended intervention includes a reproducible cost-and-benefit
   model with explicit assumptions.
4. The ranking includes total-system throughput or delivery effects, not only
   station utilization or labor reduction.
5. Camera-derived classifications expose confidence and can be corrected by an
   authorized human.
6. Physical automation recommendations cannot be marked approved without
   qualified-human safety and quality review.
7. Post-implementation analysis compares the same measurable outcomes against
   the baseline and identifies any shifted bottleneck.
8. The project record links raw or summarized evidence, recommendation,
   approval, implementation, and measured outcome.
9. A failed or uneconomic intervention remains searchable as organizational
   learning.

## Out of Scope

- Autonomous control of hazardous machines from generative AI output.
- Employee performance or disciplinary decisions based only on computer
  vision.
- Replacing qualified safety engineering, industrial engineering, accounting,
  or quality approval.
- Assuming labor reduction is the primary or preferred improvement objective.

## References

- [Draft Business Vision and Phased Plan](../../discovery/business-vision-and-phased-plan.md)
- [Manufacturing Processes and Quality Questions](../../discovery/topics/manufacturing-processes-and-quality.md)
- [Data, AI, and Platform Questions](../../discovery/topics/data-ai-and-platform.md)
- [Legal, Safety, and Compliance Questions](../../discovery/topics/legal-safety-and-compliance.md)
- [Geometry-Driven Prismatic 3+2 CAM](geometry-driven-3plus2-cam.md)
