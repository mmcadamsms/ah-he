# Prompt: Rank Defined Unfinished Software Development

## Requirement Reference

- **Functional Areas:** All six existing areas; cross-functional assessment.
- **Requirement Sources:** [Functional-area index](../../README.md#functional-areas).
- **Deliverable:** [Definition-Led Software Development Backlog](../../software-development-backlog.md).

## Objective

Assess the repository's defined but unfinished software-development work against
actual source, stack-rank it primarily by definition with bounded implementation
readiness as a tie-breaker, and publish a linked Markdown report in a follow-up
PR. Synchronize main and merge it into the working branch before assessment.

## Acceptance Criteria

1. The report records the synchronized baseline commit and assessment method.
2. Every substantive functional requirement is accounted for, including current
   branch changes; separate unmerged proposals are not silently imported.
3. Ranked items describe remaining work, high-level requirements, implementation
   status, a bounded first slice, prerequisites/open decisions, and observable
   completion for that slice.
4. Existing implementation is not scheduled anew based on stale prompt folders
   or checkboxes; documentation completion is not mistaken for runtime delivery.
5. Definition ranking is distinct from business priority and dependency order,
   and no unsupported production/safety/physical validation claim is made.
6. The report is discoverable from the root agent and requirements indexes and
   published in a main-targeted PR with a direct report link.

## Scope

### In Scope

- Requirements, architecture, prompt, and runtime-source census.
- Manufacturing, commercial, facility, notification, and scaffold coverage.
- Ranked assessment, source links, exclusions, and dependency-aware guidance.
- Main synchronization and a documentation-only follow-up PR.

### Out of Scope

- Implementing ranked capabilities or changing existing runtime behavior.
- Selecting providers, storage, messaging, solvers, or engineering limits.
- Approving operating/safety/financial policies or production release.
- Reclassifying older prompts or importing separate proposal branches.
- Automatically promoting constituent feedback into requirements.

## Technical Notes

- Assess the whole synchronized branch, not only the newest manufacturing PR.
- Check requirements against source; status folders are insufficient evidence.
- Keep first-slice completion separate from completion of the full requirement.
- Record unresolved choices explicitly rather than inventing numeric scores,
  estimates, defaults, or architecture decisions.
- This document is assessment work, not a new cross-functional runtime feature.

## Testing Requirements

- [x] Verify consecutive ranks and coverage of all substantive requirements.
- [x] Validate changed Markdown relative links, including fragment targets.
- [x] Run `git diff --check` and review scope/source consistency.
- No runtime tests are required for this documentation-only assessment.

## Dependencies

- [Requirements standards](../../../docs/standards/requirements-standards.md).
- [Existing runtime boundaries](../../../docs/architecture/decisions/0005-organize-code-by-runtime-boundary.md).
- [Prompt workflow](../README.md).
- Synchronized baseline `7fc0bb8d0e5314087a2c7c45fa737eaf8cac7402`.

---
**Status:** deploying

**Created:** 2026-10-31

**Author:** GitHub Copilot
