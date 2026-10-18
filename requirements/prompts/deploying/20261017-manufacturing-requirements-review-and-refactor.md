# Prompt: Review and Refactor Manufacturing Business Discovery

## Requirement Reference

- **Functional Area:** Manufacturing business discovery
- **Requirement Docs:** `requirements/discovery/` and
  `requirements/position-papers/`

## Objective

Review the current additive/subtractive manufacturing business questions,
identify material gaps, improve the discovery information architecture, and
publish a concise prioritized assessment for owner review.

## Acceptance Criteria

1. The review covers the complete discovery-question and position-paper corpus.
2. A second non-OpenAI model provides an independent opinion.
3. Material missing questions are added without reusing or changing existing
   question IDs.
4. Broad mixed topics are split where a dedicated decision area makes the
   corpus easier to navigate.
5. The generated canonical question index is current and contains no duplicate
   IDs.
6. A concise Markdown assessment identifies the highest-value next actions and
   explains what should not be attempted yet.
7. The changes are published in a draft pull request for owner review.

## Scope

### In Scope

- discovery questions;
- discovery navigation and grouping;
- manufacturing-business position-paper references;
- additive, subtractive, commercial, financial, operating, maintenance, and
  go-to-market gaps; and
- a prioritized next-steps assessment.

### Out of Scope

- answering the position papers;
- approving a business plan, target customer, location, or capital purchase;
- implementing software or physical manufacturing capabilities; and
- renumbering permanent discovery-question IDs.

## Technical Notes

- Preserve the distinction among discovery questions, decisions, requirements,
  and implementation prompts.
- Prefer a decision sequence and stage gates over treating every open question
  as equally urgent.
- Regenerate the canonical index with
  `.\scripts\update-discovery-question-index.ps1`.

## Testing Requirements

- [ ] Regenerate and validate the discovery question index.
- [ ] Verify all added and moved questions have a state.
- [ ] Verify changed Markdown links resolve within the repository.

## Dependencies

- `docs/standards/requirements-standards.md`
- `requirements/discovery/README.md`
- `requirements/position-papers/README.md`

---
**Status:** deploying
**Created:** 2026-10-17
**Author:** Copilot
