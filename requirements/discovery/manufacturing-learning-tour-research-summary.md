# Manufacturing Learning-Tour Research Summary

## Status

- **Status:** First research and independent-review pass complete
- **Completed:** 2026-10-17
- **Scope:** All canonical manufacturing-business discovery questions
- **Authority:** Evidence-backed discovery record, not an approved business
  plan, professional opinion, or capital decision

## Coverage

| Measure | Result |
|---|---:|
| Canonical questions | 1,175 |
| Active questions researched | 1,173 |
| Confirmed duplicates preserved as `Superseded` | 2 |
| Active questions remaining `Open` | 0 |
| Active questions marked `Exploring` | 1,173 |
| Active questions with completed second-opinion review | 1,173 |
| Unique internet source URLs in the corpus | 874 |

Every active question now contains:

- a direct first-pass answer;
- two to five evidence sources or equivalent referenced sources;
- evidence-strength classification;
- material conflicting evidence;
- confidence and its basis;
- business-specific validation still required; and
- an independent second-opinion verdict.

The answers remain `Exploring` because most questions ultimately depend on the
selected customer, process, location, equipment, contract, risk tolerance,
qualified professional, actual quote, or measured operating result.

## Evidence Method

Research followed
[Discovery Research Standards](../../docs/standards/discovery-research-standards.md).
Evidence was weighted in this order:

1. delivered products, operating facilities, measured production outcomes,
   field performance, actual customer behavior, and documented
   implementations;
2. primary government, regulatory, standards, tariff, permit, and technical
   evidence;
3. audited, contractual, transaction, lender, insurer, quote, and invoice
   evidence;
4. empirical studies and disclosed-method surveys;
5. supplier, integrator, and practitioner evidence; and
6. theory, forecasts, demonstrations, preprints, and forward-looking AI or
   automation claims.

Theory and emerging AI evidence were retained where relevant, but labeled so a
demonstration or forecast is not mistaken for dependable commercial delivery.

## Independent Review

The intended Gemini reviewer endpoints were attempted first but were
unavailable before execution. GPT-5.4, a model different from the first-pass
research model, performed the independent review instead. This limitation is
recorded rather than presenting the review as cross-provider when it was not.

| Review verdict | Questions |
|---|---:|
| Agree | 467 |
| Agree with qualifications | 702 |
| Disagree and revise | 4 |

The high qualification count is expected: public evidence usually supports a
method, boundary, or candidate direction but cannot establish the correct
price, machine, customer, location, staffing level, tolerance, or risk
threshold for this specific business.

### Material disagreements

| Question | Correction made |
|---|---|
| `FNC-003` | Reframed the detailed cost taxonomy as a decision-useful modeling approach because the original sources did not directly support every cost bucket or reserve treatment |
| `FAC-007` | Removed an implied preference for leasing; lease, buy, and build must be compared using control, upgrade, cash, and net-present-value evidence |
| `FAC-035` | Removed an implied baseline dock and drive-in-door count; actual concurrency, equipment delivery, flow, and resilience determine the requirement |
| `TAX-040` | Updated Opportunity Zone treatment for current 2026 transition guidance rather than describing post-2026 treatment as unresolved |

Reviewers also corrected or qualified source links, FDA additive-manufacturing
guidance, robot-safety citations, R&D tax treatment, credit-law applicability,
AI/autonomy maturity, facility generalizations, utility evidence, shipping
rules, and environmental claims.

## Source-Link Audit

An automated reachability pass covered all 874 unique URLs. It directly
reached 681 links and identified another 170 as access-controlled or
rate-limited rather than missing. Obsolete links were replaced with current
authoritative pages, publication landing pages, DOI permalinks, official
regulations, or stable government sources. Remaining automated-client
exceptions on authoritative FDA, DLA, acquisition.gov, EIA, NASA, EEOC, and
FMCSA hosts were independently checked or retained as official sources whose
servers reject or throttle automated requests.

The audit artifact is stored outside the repository in the session workspace;
the durable repository record remains the citations under each question.

## Cross-Cutting Conclusions

1. **No public source can select the business model by itself.** Public
   evidence narrows plausible choices; interviews, RFQs, quotes, trials, and
   operating measurements must determine the actual choice.
2. **There is no universal best first process.** Machining, additive,
   fabrication, partner manufacturing, and design/coordination each become
   attractive under different customer, utilization, qualification, capital,
   and risk conditions.
3. **Complete capability ecosystems matter more than machine price.** Tooling,
   metrology, handling, maintenance, utilities, safety, postprocessing,
   software, staffing, waste, and working capital frequently reverse an
   apparent equipment advantage.
4. **Location conclusions are parcel- and process-specific.** State rankings
   and industrial concentration identify candidates but do not prove zoning,
   utility, building, labor, freight, insurance, or permitting feasibility.
5. **AI can create leverage before it can safely control production.** Current
   evidence is strongest for research, documentation, estimation assistance,
   anomaly triage, workflow support, and bounded recommendations. Hazardous
   equipment control still requires deterministic engineering, validated
   safety functions, and qualified human authority.
6. **Automation economics require measured baselines.** Labor-saving claims,
   unattended operation, predictive maintenance, and bottleneck improvements
   require actual cycle, attendance, downtime, yield, quality, recovery, and
   cash evidence.
7. **Regulated and high-consequence work needs explicit Day-1 boundaries.**
   Certification, export control, cybersecurity, product safety, engineering
   responsibility, and inspection capability can make otherwise attractive
   demand inappropriate for an early-stage shop.
8. **Financial survival depends on cash timing, not reported margin alone.**
   Deposits, receivables, material, WIP, outside processing, debt service,
   maintenance, replacement capital, and owner labor must be modeled together.

## How to Use the Research

1. Use the full corpus as the learning-tour reference.
2. Select the first focused question packet based on the insights and
   contradictions now visible.
3. Replace public assumptions with actual interviews, quotes, invoices,
   contracts, trials, and measured outcomes.
4. Move a question to `Answered` only when its stated level is genuinely
   resolved.
5. Synthesize answered packets into position papers; do not copy this summary
   into a business decision without the question-level evidence.
6. Convert approved consequences into requirements, ADRs, experiments, or
   operating controls with traceability back to the question IDs.

## Important Limitations

- Internet research cannot determine the founders' actual capabilities,
  customer willingness to buy, local property feasibility, negotiated
  financing, insurance appetite, supplier performance, or production yield.
- Standards and regulations change; current applicability must be confirmed at
  quote, contract, permit, and production time.
- Some authoritative standards are paywalled; accessible official summaries
  establish scope but not every normative requirement.
- Vendor case studies and demonstrations are directional evidence, not proof
  of repeatable performance in this business.
- Legal, tax, safety, engineering, quality, environmental, insurance, and
  regulated-product conclusions require the qualified reviews identified in
  the individual answers.
