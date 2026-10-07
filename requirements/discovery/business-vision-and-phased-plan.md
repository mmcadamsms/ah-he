# Draft Business Vision and Phased Plan

## Status

- **Status:** Early discovery notes
- **Captured:** 2026-10-06
- **Authority:** Directional intent and hypotheses, not an approved business
  plan, budget, facility design, or product requirement

This document preserves the current concept so it can be tested, refined, and
converted into decisions, requirements, financial models, and implementation
plans over time.

## How to Read These Notes

| Classification | Meaning |
|---|---|
| **Stated principle** | A value or operating direction the business intends to preserve |
| **Strategic hypothesis** | A belief that must be tested with evidence |
| **Candidate choice** | A possible capability, machine, market, or sequence that has not been approved |
| **Open decision** | A question tracked by a permanent ID in the discovery index |

### Current classification

| Item | Classification |
|---|---|
| Small machine and metal job shop | Stated direction |
| Honor God and family | Stated principle |
| Create opportunities to employ family | Stated principle requiring governance |
| Incessant emphasis on automation | Stated principle requiring practical definition |
| Prefer off-the-shelf, low-TCO technology to observe work, identify bottlenecks, and prioritize the highest-return automation improvement | Stated operating principle |
| Customer always knows where the part is | Stated customer-experience principle |
| Reproducible history of how each part was made | Stated operating principle |
| AI lets a small company perform above its apparent size | Strategic hypothesis |
| US reshoring will accelerate with AI and robotics | Strategic hypothesis |
| Older machinery plus carefully engineered retrofit automation can create a capital-efficient competitive advantage | Strategic hypothesis |
| Small/medium runs plus custom metal work form an attractive niche | Strategic hypothesis |
| Fast turnaround, high quality, and competitive price can coexist | Strategic hypothesis |
| Established job shops with temporary overflow may be practical first customers for a new supplier without a reputation | Strategic hypothesis |
| Begin physical production with a laser cutter | Candidate capacity-ramp choice |
| Year-two/year-three equipment list | Candidate target state |
| Virtual systems should lead physical build-out | Open sequencing decision |

## Workstream Map

The original notes use three phases, but virtual work may need to start before
the physical Phase 2 build. Until sequencing is decided, organize the effort as
three connected workstreams:

| Workstream | Immediate purpose | Primary outputs |
|---|---|---|
| **A — Basic business plan** | Test market, economics, location, customer, tax, workforce, and initial capability assumptions | Business model, customer ramp, process maps, financial scenarios, location shortlist, first-capability decision |
| **B — Detailed physical plan** | Define the plausible year-two/year-three shop once assumptions are supported | Facility flow, equipment sequence, staffing, safety, utilities, capacity, capital plan, ROI |
| **C — Virtual business foundation** | Model and automate the customer and internal experience while learning AI | Low-TCO front end, back end, digital thread, simulations, customer visibility, operating controls |

Workstream C may begin immediately and proceed alongside A. Major irreversible
physical commitments in B should depend on evidence produced by A and virtual
experiments from C.

## Core Business Idea

Build a small machine shop and metal job shop with an unusually strong
emphasis on automation.

Potential work includes:

- small- to medium-run manufactured parts;
- subtractive manufacturing;
- additive manufacturing where appropriate;
- laser-cut flat and rolled metal parts;
- custom metal work such as railings;
- design-to-part services;
- direct-to-consumer work; and
- rapid-turnaround industrial or custom orders.

## Stated Values and Ownership Intent

The currently stated values are:

1. Honor God and family.
2. Create opportunities to employ family.
3. Apply an incessant emphasis on automation.

These values need later translation into durable operating principles,
governance, employment practices, customer commitments, and decision rules.
They must not remain slogans whose practical meaning changes from one decision
to another.

## Strategic Thesis

### AI leverage

The business intends to use AI to perform above the scale normally associated
with a small company.

Current beliefs include:

- creating and operating in the virtual world is becoming easier;
- knowledge about interacting with the physical world is becoming more
  attainable through AI;
- producing and transforming physical objects remains a major frontier,
  especially before full robotization;
- a small team may be able to access engineering, planning, software,
  documentation, sales, support, and operational capabilities that previously
  required a much larger organization; and
- automation should improve both the production process and the business
  systems surrounding it.

### Reshoring

The business hypothesis is that the United States can bring back a meaningful
amount of manufacturing as AI, automation, and robotics reduce reliance on
low-cost labor countries.

Related hypotheses:

- investment will flow toward physical infrastructure and physical capital
  used to create additional infrastructure and capital;
- a highly automated small job shop can participate in that investment;
- local speed, quality, transparency, flexibility, and engineering support can
  offset some labor-cost disadvantage; and
- the business may be able to ride a broader reshoring curve.

These are hypotheses requiring market, customer, cost, workforce, technology,
and capital validation.

### Scrappy capital efficiency and retrofit automation

The current hunch is that the business may need to start small and win work by
combining non-new equipment—potentially ten years old or more—with carefully
selected add-on automation.

The intended advantage is not merely buying inexpensive machines. It is
assembling a coherent production system in which older, less inherently
automated machines become less operator-intensive through:

- standardized controls and data collection;
- probing and in-process measurement;
- bar feeders, part catchers, pallet systems, or external work queues;
- robot or cobot tending where technically and economically justified;
- automatic door, chuck, vise, fixture, and material-handling interfaces;
- tool-life, spindle-load, vibration, coolant, chip, and fault monitoring;
- vision, presence, orientation, and completion checks;
- low-cost sensors and edge controllers;
- remote status and escalation;
- reliable restart and recovery procedures; and
- software that coordinates work across heterogeneous equipment.

This is a strategic hypothesis, not permission to bypass safety systems,
machine limits, qualified integration, or human review. Some older machines
will be poor automation candidates because of unsupported controls, weak
reliability, missing interlocks, poor repeatability, unavailable parts, or an
unfavorable total retrofit cost.

The proposed differentiator is therefore:

> Buy selectively, integrate intelligently, automate incrementally, measure
> the real result, and retain humans for judgment, setup, maintenance, quality,
> exception handling, and safety-critical decisions.

### Bottleneck-driven automation investment

The business must use practical, off-the-shelf, low-total-cost technology to
observe actual work and identify where time, labor, machine capacity, quality,
or flow is being lost.

Potential observation sources include:

- ordinary industrial or commercial cameras;
- edge vision models;
- machine-state signals;
- inexpensive sensors;
- operator interaction and waiting;
- job and material timestamps;
- alarms and downtime;
- queue and work-in-process levels;
- inspection and scrap data;
- energy and spindle-load data; and
- structured employee observations.

The system should then compare candidate interventions and recommend the next
piece of automation with the strongest risk-adjusted return. A camera or AI
classification is evidence to validate, not an unquestioned fact. The business
must be able to explain which bottleneck was detected, how it was measured,
what alternatives were considered, and whether the intervention actually
improved the process after deployment.

## Potential Market Position

The currently imagined niche combines:

- small- and medium-run parts;
- custom metal work;
- direct customer access;
- very fast turnaround;
- high quality;
- competitive pricing;
- visible order progress; and
- an automation-centric cost structure.

### Initial-customer hypothesis

One possible first market is established job shops or manufacturers that have
won more work than their internal capacity can deliver.

They may be willing to try a new supplier when:

- the overflow is clearly defined;
- drawings and acceptance criteria are complete;
- the work is noncritical or can be independently inspected;
- lead time matters;
- incumbent suppliers are full;
- the new shop communicates quickly;
- pricing compensates for qualification risk; and
- the first order is small enough to limit customer exposure.

Early overflow work may provide machining experience, process data, references,
inspection evidence, and cash flow. However, accepting lower initial margins
must be a deliberate customer-acquisition investment with limits. The business
must avoid becoming permanently dependent on low-margin, last-minute,
high-liability work that stronger shops do not want.

The concept is loosely analogous to the ease and speed associated with
SendCutSend, while potentially expanding into broader machining, fabrication,
design assistance, additive manufacturing, assemblies, and richer customer
visibility.

This comparison expresses an experience aspiration, not an assertion that the
business model or capabilities should copy another company.

## Customer Experience Principles

### Continuous visibility

The customer should always know where the part is within the general shop
flow.

The experience may include:

- a clear view of the shop's general work stages;
- a simplified or cartoon-like visualization of the part moving through those
  stages;
- actual photographs as the part changes from raw material to finished
  product;
- milestone, delay, approval, and inspection status; and
- progressively richer production evidence.

### Channel transparency

Potential customer channels include:

1. website;
2. iPhone application;
3. Android application; and
4. API.

Channel priority and total cost of ownership remain open questions.

### Reproducibility and digital history

The business should know, to the best of its ability, how every customer part
was made.

Proposed levels of detail:

1. **High level:** major stages and completion status.
2. **Moderate:** setup, machine, material, tooling, outside processing, and
   inspection summary.
3. **Detailed:** operation sequence, parameters, measurements, revisions, and
   approvals.
4. **Full detail:** actual NC/G-code used, detailed machine and environmental
   logs where available, images, video, temperatures, motor output, inspection
   evidence, and other traceable production records.

The appropriate retention, customer access, security, and cost at each level
remain unresolved.

### Continuous improvement

Every completed part should create structured learning:

- what went well;
- what did not go well;
- what could improve;
- what assumptions proved wrong;
- what changed from estimate to actual;
- what should be reused;
- what should be avoided; and
- what recommendations apply to the next similar part.

## Phase 1 — Develop the Basic Plan

Phase 1 should establish a realistic initial business model and learning plan.

### Shop capabilities

Define:

- initial processes;
- supported materials;
- part-size and weight envelopes;
- tolerances;
- quantities;
- finishes and outside processes;
- inspection capability;
- operating hours;
- required people; and
- capabilities intentionally deferred.

### Customer market

Estimate:

- realistic initial customers;
- customer acquisition;
- quote volume;
- order conversion;
- repeat business;
- order value;
- customer concentration;
- geographic reach; and
- a realistic customer ramp.

### Customer and internal flows

Map customer flow from idea or RFQ through requirements, design, approval,
quote, manufacturing, inspection, delivery, and support.

Map internal flow for:

- sales;
- engineering;
- purchasing;
- material;
- scheduling;
- production;
- outside processing;
- quality;
- packaging;
- shipping;
- invoicing; and
- learning capture.

### Financing and tax

Research:

- owner capital;
- debt;
- equipment financing;
- leases;
- investors;
- grants;
- incentives;
- working capital;
- state and local taxes;
- Opportunity Zones;
- owner taxation;
- employee taxation; and
- business taxation.

### Location and customer questions

Determine:

- where current customers are;
- where customers are likely to be in five years;
- where labor, suppliers, materials, energy, and logistics are favorable;
- where the community and regulators accept the planned work; and
- where the combined economics are strongest.

### Capacity ramp

Evaluate ways to begin at break-even or slightly positive cash flow while
learning and preserving expansion options.

One proposed path is to begin with a laser cutter and ship laser-cut parts from
flat and rolled metal before acquiring the full machining and fabrication
capability.

The sequence must be validated against demand, utilization, margin, financing,
competition, staffing, facility requirements, and the cost of later expansion.

### Vision visualization

Create a video that flies through a plausible year-two or year-three operation.

The visualization should help test:

- facility flow;
- equipment placement;
- customer experience;
- staffing;
- automation;
- material movement;
- safety;
- growth assumptions; and
- whether the proposed future is coherent and desirable.

The video must clearly distinguish aspiration from a funded or approved plan.

## Phase 2 — Develop the Detailed Business Plan

Phase 2 should turn validated assumptions into a detailed plan for the
approximately year-two or year-three operation.

### Initial target layout and capability candidates

Every item remains subject to capacity, ROI, customer, facility, safety, and
sequencing research.

| Candidate capability or equipment | Primary questions |
|---|---|
| Forklift | Capacity, aisle width, fuel, charging, certification, indoor use, maintenance |
| Overhead crane | Required loads, span, structure, permitting, inspection, alternatives |
| Metal bandsaw | Stock forms, capacity, automation, material flow, chip and coolant handling |
| Five-axis CNC mill | Customer demand, utilization, work envelope, tooling, metrology, programming |
| Three-axis CNC mill | Product mix, cost, simplicity, redundancy, fixture strategy |
| CNC lathe | Diameter/length range, live tooling, bar feeding, automation, demand |
| Mill and lathe tooling | Initial package, tool management, holders, workholding, inventory |
| Packing station | Packaging profiles, corrosion control, scales, labels, shipping systems |
| Laser cutter | Material/thickness range, sheet handling, gas, extraction, nesting, utilization |
| Welding station | Processes, qualifications, ventilation, fixturing, fire protection |
| Tube-cutting station | Tube profiles, lengths, loading, downstream fabrication, demand |
| Additional capability | To be identified through customer and process research |

### Facility flow

The detailed plan should model:

- receiving;
- raw-material storage;
- sawing and preparation;
- machining;
- additive manufacturing;
- laser and tube cutting;
- welding and fabrication;
- deburring and cleaning;
- inspection;
- outside-process staging;
- assembly;
- packaging;
- shipping;
- scrap and waste;
- maintenance;
- people and visitors; and
- future expansion.

### Return on investment

Estimate ROI for:

- the entire business;
- each major equipment purchase;
- facility improvements;
- automation;
- software and platform development;
- staffing and training;
- inventory; and
- customer-acquisition spending.

Models should include utilization ramp, working capital, financing, taxes,
maintenance, tooling, scrap, downtime, labor, energy, material, freight, and
resale value.

## Phase 3 — Develop the Virtual Business

The current note labels virtual-business development as Phase 3 while also
stating that virtual pieces should lead the build-out while AI capability is
being learned.

This creates an explicit sequencing question:

> Should the virtual business be developed first as an enabling foundation,
> incrementally alongside Phases 1 and 2, or only after the physical plan is
> sufficiently validated?

The intended work includes:

- learning to use AI effectively;
- modeling the business virtually before committing physical capital;
- developing low-total-cost-of-ownership front-end systems;
- developing low-total-cost-of-ownership back-end control systems;
- simulating customer and internal flows;
- testing automation and visibility concepts; and
- preserving a path from prototype software to durable operating systems.

## Canonical Discovery References

- [Question Index](question-index.md)
- [Business and Commercial Strategy](topics/business-and-commercial-strategy.md)
- [Customer Demand and Market Geography](topics/customer-demand-and-market-geography.md)
- [Customer Intent and Experience](topics/customer-intent-and-experience.md)
- [Engineering, CAD, and Product Development](topics/engineering-cad-and-product-development.md)
- [Manufacturing Processes and Quality](topics/manufacturing-processes-and-quality.md)
- [People and Operating Model](topics/people-and-operating-model.md)
- [Workforce Cost and Labor Markets](topics/workforce-cost-and-labor-markets.md)
- [Industrial Geography and Domestic Supply Chain](topics/industrial-geography-and-domestic-supply-chain.md)
- [Raw Materials, Energy, and Input Costs](topics/raw-materials-energy-and-input-costs.md)
- [Tax, Finance, and Capital](topics/tax-finance-and-capital.md)
- [Freight, Shipping, and Customer Delivery](topics/freight-shipping-and-customer-delivery.md)
- [Business Climate and Community Acceptance](topics/business-climate-and-community-acceptance.md)
- [Data, AI, and Platform](topics/data-ai-and-platform.md)

## Promotion Rule

As hypotheses are answered:

1. Record the answer under the canonical question ID.
2. Convert durable product or operational behavior into a formal requirement.
3. Record architectural decisions as ADRs.
4. Create implementation prompts only after the requirement is sufficiently
   clear.
5. Keep this document as the historical vision source rather than silently
   rewriting early assumptions to look inevitable.
