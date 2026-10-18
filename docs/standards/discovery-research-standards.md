# Discovery Research Standards

## Purpose

Define what counts as a concise, trustworthy first-pass answer to a discovery
question and how independent review is recorded.

The goal is practical decision support, not thesis-length treatment. Answers
must distinguish evidence from inference and must not present a general
internet conclusion as proof of what will happen in this specific business.

## Evidence Hierarchy

Prefer evidence in this order:

1. **Observed operating results** — delivered products, operating facilities,
   measured production outcomes, field performance, actual customer behavior,
   or documented implementations.
2. **Primary official evidence** — laws, regulations, standards bodies,
   government datasets, agency guidance, tariffs, permits, court records, and
   authoritative technical manuals.
3. **Audited or transaction evidence** — audited filings, lender or insurer
   requirements, acquisition records, invoices, quotes, contracts, and
   documented commercial results.
4. **Empirical research** — peer-reviewed studies, reproducible experiments,
   surveys with disclosed methods, and measured case studies.
5. **Supplier or practitioner evidence** — vendor documentation, integrator
   case studies, trade publications, and experienced-practitioner reports.
6. **Theory and forward-looking claims** — simulations, preprints, forecasts,
   conceptual work, expert opinion, and AI capability claims without broad
   operating evidence.

Lower-ranked evidence may still be important, especially for emerging AI,
robotics, and automation capabilities. Label it accurately and avoid treating
promise, demonstration, or laboratory success as equivalent to dependable
commercial delivery.

## Required Answer Format

Keep each answer as short as the question permits. A typical answer should use
two to five concise paragraphs and two to five strong sources.

```markdown
**State:** Exploring

**First-pass answer:** Direct answer in plain language. State when the evidence
supports only a conditional answer or when the decision is business-specific.

**Evidence:**
- [Source title](https://example.com) — **Evidence class:** observed operation.
  Explain the relevant real-world result and any important limitation.
- [Source title](https://example.com) — **Evidence class:** official data.
  Explain what the data supports.

**Conflicting evidence:** Explain material disagreement, why sources differ,
and which interpretation is more credible. Use `None found in this pass` only
after actively looking for contrary evidence.

**Confidence:** High, medium, or low. Explain the main reason.

**Business-specific validation:** State what quote, interview, trial, local
fact, professional review, or operating measurement is still required.

**Second-opinion review:** Pending
```

## Answer Rules

1. Answer the actual question before providing background.
2. Use internet-accessible links whenever possible.
3. Prefer original sources over pages that summarize another source.
4. State the publication or observation date when recency matters.
5. Distinguish national evidence from state, local, industry, machine, material,
   or customer-specific evidence.
6. State when a question has no universal answer and identify the variables
   that determine the business-specific result.
7. Include contrary evidence or explain why apparently conflicting results are
   not comparable.
8. Do not convert correlation, vendor claims, prototypes, or demonstrations
   into causal or production-reliability claims.
9. For safety, legal, tax, engineering, quality, and regulated-work questions,
   identify the qualified professional or authority needed before action.
10. If public evidence cannot answer the question, say so directly and define
    the smallest real-world validation needed.
11. Preserve source quotations only when necessary and keep them short.
12. Never invent a citation, result, customer, implementation, or measurement.

## Question State

- Use `Exploring` for a useful first-pass answer that still needs business-
  specific evidence or a decision.
- Use `Answered` only when the question can be answered with sufficient
  evidence at its stated level and no unresolved blocker changes the answer.
- Use `Converted` when the durable consequence is already represented in a
  requirement, ADR, policy, or runbook and link the follow-through.
- Use `Deferred` when the answer is not needed for the current business stage.
- Use `Superseded` only for a confirmed duplicate with a canonical replacement.

## Independent Second Opinion

Every first-pass answer must receive a review from a model different from the
one that produced the first pass. Prefer a non-OpenAI model when one is
available in the execution environment. If it is unavailable, use a distinct
OpenAI model and record that limitation rather than skipping independent
review.

The reviewer must:

1. check that cited sources exist and support the stated claim;
2. challenge source quality, selection bias, recency, and generalization;
3. look for credible contrary evidence;
4. distinguish empirical delivery from theory or vendor promise;
5. identify missing safety, regulatory, financial, or operating constraints;
6. revise the answer when the evidence supports a better conclusion; and
7. replace `Pending` with:

```markdown
**Second-opinion review:** [Reviewer model] — Agree, agree with qualifications,
or disagree. Summarize the independent reasoning, source concerns, and any
change made to the first-pass answer.
```

Agreement without independent reasoning is not sufficient.

## Review Checklist

- [ ] The answer begins with a direct conclusion.
- [ ] Every material factual claim has a verifiable source.
- [ ] Empirical evidence is weighted above theory.
- [ ] AI-forward claims are included but labeled by maturity.
- [ ] Contrary evidence or source disagreement is addressed.
- [ ] Confidence and business-specific validation are explicit.
- [ ] The second opinion is independent and recorded.
- [ ] The question state matches the answer's maturity.
