# Manufacturing Business Requirements Review and Next Steps

## Status

- **Status:** Draft for owner review
- **Reviewed:** 2026-10-17
- **Scope:** Additive, subtractive, fabrication, commercial, operating, and
  business-planning requirements
- **Authority:** Assessment and proposed work sequence, not an approved business
  plan or capital decision

## Executive Assessment

The repository has unusually broad manufacturing, safety, quality, facility,
commercial-risk, and automation coverage. Its primary weakness is not lack of
questions; it is the absence of a disciplined way to decide **which questions
must be answered next**.

Before this review, the corpus contained **1,105 canonical questions and all
1,105 were `Open`**. Questions required before the first customer interview sat
beside questions for facility-wide robotics, regulated quality systems, and
multi-process automation. Detailed target-state requirements had also been
written while their source questions remained open.

This review adds missing commercial and operating areas and reorganizes several
mixed topics. The resulting corpus has **1,175 canonical questions across 30
topic files**: 1,173 open and 2 confirmed duplicates marked `Superseded`.
That count reinforces the central conclusion: this is a reference corpus, not
a sequential checklist.

## Findings

| Area | Assessment | Implication |
|---|---|---|
| Strategic breadth | Strong coverage of markets, facilities, tax, workforce, supply chain, quality, safety, and automation | Preserve the breadth, but drive work through a short decision sequence |
| Decision state | Nearly every question is open, including questions already represented in functional requirements | Run a conversion and traceability audit as decisions are reviewed |
| Additive manufacturing | Previously only six general process questions, with little feedstock, build, postprocessing, safety, qualification, or ownership economics | Treat additive as a distinct capability decision |
| Subtractive manufacturing | Strong prismatic 3+2 milling detail, but weaker turning, mill-turn, Swiss, grinding, EDM, probing feedback, and hybrid handoff coverage | Compare complete subtractive archetypes rather than assuming a UMC-750-centered shop |
| Fabrication | Laser, bending, and welding appeared in vision and support-equipment questions without a coherent production topic | Evaluate fabrication as its own process-and-market system |
| Go-to-market | Customer and market questions were broad, but pipeline, channels, qualification, acquisition economics, and commercial experiments were thin | Validate the route to revenue before building broad capability |
| Financial viability | Tax and incentives were detailed, but integrated cash flow, runway, working capital, financing constraints, and downside gates were not | Build a 36-month financial model before material capital commitments |
| Operations and reliability | Shop control and maintenance were scattered across people, facility, insurance, inventory, and machine questions | Treat production control and reliability as explicit operating capabilities |
| Functional requirements | Several specifications are high quality but represent target-state controls or prototypes rather than approved near-term business priorities | Preserve them, but do not confuse implemented prototypes with a selected business model |

## Independent Second-Model Opinion

A separate **Gemini 4 Argon** review independently reached the same core
conclusions:

- the corpus is comprehensive but insufficiently prioritized;
- additive, go-to-market, integrated finance, maintenance, and hybrid process
  coverage were material gaps;
- the UMC-750 prototype, native mobile channels, broad equipment vision, and
  facility-wide dynamic automation may be premature assumptions;
- location should follow the target customer and market-entry model; and
- many discovery questions should eventually be marked `Converted` and linked
  to existing functional requirements.

The independent review proposed a larger directory and metadata migration.
This PR uses a smaller refactor instead: preserve permanent IDs and existing
links, add a decision sequence, split only the clearest mixed topics, and avoid
large mechanical churn before the owner reviews the structure.

The independent review also undercounted the original corpus. The repository's
canonical parser confirmed the correct baseline was 1,105 questions, not 856.

## Refactor Included in This PR

- Added a six-gate business decision sequence to `discovery/README.md`.
- Split additive questions into a dedicated topic and expanded design, build,
  feedstock, safety, qualification, postprocessing, and ownership economics.
- Added dedicated topics for:
  - fabrication, forming, and welding;
  - sales and go-to-market;
  - financial model and capital readiness;
  - production planning and shop operations; and
  - maintenance, reliability, and continuity.
- Moved the existing `OPS-*` questions out of the people topic and marked two
  confirmed duplicates as superseded.
- Added missing turning, advanced subtractive, probing, and hybrid-process
  handoff questions.
- Added a manufacturing-automation requirements index that distinguishes
  prototypes, operating foundations, and later automation investment.
- Reordered the position-paper execution sequence to:
  **margin and market -> target customer -> market entry -> location**.

## Recommended Next Steps

| Order | Work | Required output or exit criterion |
|---|---|---|
| 1 | Execute Position Paper 01 | Rank 5-8 process-by-market wedges using market size, reachable demand, contribution margin, required utilization, capital, qualification, risk, and defensibility; select at most three for customer testing |
| 2 | Execute Position Paper 02 | Select a primary and secondary customer; complete 20-30 structured interviews and obtain real RFQs, quote requests, or paid tests that expose buying criteria and first-order trust barriers |
| 3 | Define Day-1 exclusions | Record prohibited products, regulated work, unsupported claims, data restrictions, and mandatory human/legal/quality review gates |
| 4 | Execute Position Paper 04 | Decide whether the crawl stage is design/coordination, partner manufacturing, overflow supply, one owned process, acquisition, or another model; define stop and advancement gates |
| 5 | Build the integrated financial model | Produce 36-month base, downside, and delayed-ramp cash scenarios with startup uses, owner labor, working capital, debt service, maintenance, replacement capital, and shutdown reserves |
| 6 | Create a benchmark part and quote library | Collect normalized market price, lead-time, scope, and quality evidence for representative additive, machining, turning, and fabrication parts |
| 7 | Decide the role of additive and fabrication | For each, choose among internal support capability, outsourced customer offering, first owned production capability, later capability, or explicit exclusion |
| 8 | Cost complete capability ecosystems | For the top two owned-capability candidates, include preparation, handling, tooling, metrology, maintenance, utilities, waste, safety, software, staffing, and outside processes |
| 9 | Map founder capability and first hires | Identify which qualified decisions the founders can own, which expertise must be contracted, and the first mandatory employee or partner |
| 10 | Define customer approval and inspection envelopes | Specify what is approved before release and what tolerances, materials, processes, records, and claims the initial operation can actually verify |
| 11 | Execute Position Paper 03 | Shortlist regions and properties only after the customer, entry model, first capability, staffing, utilities, and capital assumptions are bounded |
| 12 | Audit discovery-to-requirement traceability | Mark reviewed questions `Answered`, `Converted`, `Deferred`, or `Superseded`; link converted questions to the requirements that implement them |

## Defer Until the Evidence Supports It

Do not treat the following as near-term commitments:

- a broad year-two/year-three machine shopping list;
- native iOS and Android applications before a customer channel requires them;
- a UMC-750 or laser cutter as the first owned process before comparative
  economics and customer evidence;
- facility-wide mobile robots or dynamic automation zones before a stable
  physical process exists;
- opportunity-zone optimization before ordinary operating economics and
  eligible capital are known;
- in-house metal additive production before feedstock, EHS, postprocessing,
  qualification, demand, and full installed cost are validated; or
- automation whose business case depends on unmeasured labor savings or
  unproven unattended reliability.

## Owner Review Focus

The most consequential items for review are:

1. whether the proposed decision sequence is correct;
2. whether additive, fabrication, machining, and partner-manufacturing should
   all remain candidates for Position Paper 01;
3. whether market entry should precede detailed location ranking;
4. whether the new topic splits are useful without creating too much
   documentation overhead; and
5. whether the next substantive work should begin with Position Paper 01.
