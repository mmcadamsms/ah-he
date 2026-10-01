# Requirements Documentation Standards

## Purpose

Requirements are the durable, low-cost record of product and engineering
decisions. Important behavior must not exist only in chat history, source code,
an agent prompt, or an individual's memory.

All requirements documents use Markdown (`.md`) so they remain human-readable,
diffable, searchable, inexpensive to process, and accessible to development
agents.

## Conversation-to-Requirement Rule

When a conversation introduces or changes substantive behavior, update the
corresponding requirement in the same work session before implementation is
considered complete.

Substantive decisions include:

- user-visible behavior and workflow;
- domain terminology and distinctions;
- business rules and optimization objectives;
- safety boundaries and prohibited behavior;
- assumptions and customer-provided constraints;
- supported and unsupported inputs;
- integration and architecture boundaries;
- measurable acceptance criteria;
- important edge cases and failure behavior; and
- decisions that invalidate an earlier implementation assumption.

Short-lived debugging details and routine implementation mechanics do not need
product requirements unless they establish a durable constraint.

## Required Structure

Every feature requirement should contain:

1. **Problem** — the customer or operational need.
2. **Objective** — the intended outcome.
3. **Terminology** — domain terms whose distinction affects behavior.
4. **Inputs** — customer, machine, material, tool, geometry, and configuration
   information required to make the decision.
5. **Rules** — deterministic business and engineering behavior.
6. **Assumptions** — defaults used when information is unavailable.
7. **Constraints** — safety, legal, technical, machine, and scope boundaries.
8. **Acceptance Criteria** — independently verifiable outcomes.
9. **Failure Behavior** — explicit errors, warnings, and unresolved states.
10. **Out of Scope** — capabilities intentionally deferred.
11. **References** — authoritative standards, manuals, ADRs, and related
    requirements.

Use additional sections when the domain needs them. Manufacturing requirements,
for example, should distinguish physical setups, indexed orientations,
workholding, feature accessibility, CAM operations, postprocessing, and
verification.

## Decision Traceability

Requirements must link to:

- related requirements;
- relevant ADRs;
- implementation prompts in `requirements/prompts/`;
- verification fixtures or tests when available; and
- authoritative external references.

Prompts implement requirements; prompts do not replace them.

## Updating Existing Requirements

When new information contradicts an earlier assumption:

1. Update the requirement first.
2. State the corrected terminology or rule explicitly.
3. Update affected ADRs and prompts.
4. Add or revise acceptance criteria and tests.
5. Identify existing implementation that is now legacy, incomplete, or
   misleading.
6. Never preserve incorrect behavior merely because code already exists.

## Assumptions and Approval

If required customer information is unavailable, document:

- the inferred assumption;
- why it is needed;
- alternatives considered;
- its effect on cost, quality, safety, setups, or delivery; and
- whether customer or qualified-human approval is required.

The system must not present an assumption as customer-provided fact.

## Acceptance Criteria Quality

Acceptance criteria must be:

- observable and measurable;
- stated in domain terms;
- independent of a particular internal implementation where possible;
- inclusive of important one-case, many-case, and impossible-case behavior;
  and
- explicit about errors rather than accepting success-shaped fallbacks.

Example:

> A part whose required features are safely reachable from one clamping
> produces exactly one physical setup. Additional setups are created only when
> accessibility or workholding constraints require them.

Avoid:

> The planner creates two setups.

## Requirement Status

Implementation status belongs in the linked prompt pipeline:

```text
new → in-progress → deploying → validating → completed
```

The functional requirement remains the durable description of intended
behavior regardless of implementation status.

## Review Checklist

Before completing a change:

- [ ] Every substantive conversational decision is represented in Markdown.
- [ ] Terminology matches the latest domain understanding.
- [ ] Assumptions are distinguished from known facts.
- [ ] Safety and failure behavior are explicit.
- [ ] Acceptance criteria cover the corrected behavior.
- [ ] Relevant ADRs and prompts link back to the requirement.
- [ ] Tests verify the requirement rather than a weaker proxy.

