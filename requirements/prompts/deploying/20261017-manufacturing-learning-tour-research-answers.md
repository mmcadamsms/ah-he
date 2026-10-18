# Prompt: Research Manufacturing Discovery Answers

## Requirement Reference

- **Discovery Corpus:** `requirements/discovery/topics/`
- **Research Standard:** `docs/standards/discovery-research-standards.md`
- **Learning Tour:** `requirements/discovery/manufacturing-business-learning-tour.md`

## Objective

Produce concise, evidence-backed first-pass answers for the canonical
manufacturing-business discovery questions, then obtain an independent review
from a different model for every answer before publishing the work in a pull
request.

## Acceptance Criteria

1. Every canonical discovery question receives a first-pass answer or an
   explicit explanation of why public research cannot answer it.
2. Material factual claims link to internet-accessible sources.
3. Empirical implementations, deliveries, measurements, transactions, and
   official evidence are weighted above theoretical or promotional claims.
4. Material conflicting evidence is explained rather than hidden.
5. AI, robotics, and automation possibilities are included with explicit
   maturity and evidence limitations.
6. Confidence and required business-specific validation are stated.
7. A separate model pass reviews every answer and records agreement,
   qualifications, disagreement, and corrections.
8. Question states reflect answer maturity.
9. The canonical question index contains no duplicate IDs and matches the
   researched corpus.
10. The completed work is published in a draft pull request.

## Scope

### In Scope

- all canonical discovery questions;
- internet-accessible research and citations;
- concise reasoning and conflict resolution;
- empirical-versus-theoretical evidence classification;
- business-specific validation recommendations; and
- independent second-opinion review.

### Out of Scope

- treating internet research as a substitute for qualified legal, tax,
  engineering, safety, or regulatory advice;
- claiming a vendor demonstration proves production reliability;
- making capital purchases or final business commitments; and
- thesis-length treatment where a concise answer is sufficient.

## Technical Notes

- Follow `docs/standards/discovery-research-standards.md`.
- Record answers under their canonical question IDs.
- Preserve permanent IDs and original question intent.
- Prefer original sources and real operating evidence.
- State uncertainty plainly.
- Gemini reviewer endpoints were attempted first but were unavailable; GPT-5.4
  is the fallback independent reviewer and must be named in every review.

## Testing Requirements

- [ ] Validate every question has one state and no duplicate ID.
- [ ] Validate changed relative Markdown links.
- [ ] Check that every researched answer includes evidence, confidence,
      business-specific validation, and second-opinion review.
- [ ] Regenerate `requirements/discovery/question-index.md`.

## Dependencies

- Manufacturing Business Learning Tour
- Complete canonical discovery corpus

---
**Status:** deploying
**Created:** 2026-10-17
**Author:** Copilot
