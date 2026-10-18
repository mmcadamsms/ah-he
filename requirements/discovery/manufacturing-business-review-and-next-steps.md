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

The immediate work is therefore a broad learning tour across the question
corpus, followed by prioritized question packets. The tour creates context and
reveals what deserves deeper study; it does not require final answers to every
question. A position paper is written only after its prerequisite focused
questions have sufficient answers and evidence to support synthesis.

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

- Added a learning-tour gate before the six business decision gates in
  `discovery/README.md`.
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
| 1 | Design the first learning tour | Define the people, facilities, topics, quotes, datasets, visits, and small experiments needed for a broad orientation across the business system |
| 2 | Conduct the learning tour | Record provisional insight, vocabulary, contradictions, evidence leads, and changed assumptions under canonical question IDs across every major discovery domain |
| 3 | Publish learning-tour findings and triage | Produce an insight map, identify duplicates and already-converted questions, explain plausible and implausible paths, and select the 25-40 questions in the first economic-wedge packet |
| 4 | Answer the economic-wedge questions | Deeply answer the selected `BIZ`, `PRI`, `MKT`, `BEN`, `FNC`, `MFG`, `CNC`, `ADD`, and `FAB` questions using market data, real quotes, interviews, calculations, contrary evidence, and explicit uncertainty |
| 5 | Build the supporting evidence artifacts | Produce the benchmark part library, normalized external quotes, complete capability-ecosystem costs, and 36-month financial scenarios required by the packet |
| 6 | Update the canonical question records | Record answers and evidence under each ID; mark questions `Answered`, `Exploring`, `Deferred`, `Converted`, or `Superseded`; identify unresolved blockers |
| 7 | Write Position Paper 01 | Only after steps 1-6, synthesize the answered packet into a ranked process-by-market position and select at most three wedges for customer testing |
| 8 | Select and answer the target-customer packet | Answer the relevant `BIZ`, `MKT`, `GTM`, `CUS`, `TRU`, `CRD`, and `APR` questions through 20-30 interviews, RFQs, quote requests, or paid tests |
| 9 | Write Position Paper 02 | Synthesize the answered customer packet into a primary customer, secondary customer, initial offer, trust model, acquisition hypothesis, and stop conditions |
| 10 | Answer the Day-1 boundary and market-entry packets | Resolve prohibited work, claims, data, inspection, human-review, ownership, outsourcing, staffing, cash, maintenance, quality, supplier, and crawl-stage questions |
| 11 | Write Position Paper 04 | Synthesize the answered entry packet into crawl, walk, and run boundaries with measurable advancement, pause, pivot, and stop gates |
| 12 | Answer location questions and write Position Paper 03 | Use the chosen customer, entry model, first capability, workforce, utilities, logistics, risk, and capital assumptions to answer the location packet before synthesizing a shortlist |

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
5. whether the next substantive work should be designing and conducting the
   first manufacturing-business learning tour.
