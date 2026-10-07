# Requirements

## Overview

Product requirements are organized by **functional area** — logical groupings of related features and capabilities. Each functional area has its own directory with requirement documents.

## Functional Areas

| Area | Description |
|------|-------------|
| [user-management](functional-areas/user-management/) | User accounts, roles, profiles, authentication |
| [approvals](functional-areas/approvals/) | Approval workflows, chains, delegation, escalation |
| [notifications](functional-areas/notifications/) | Email, push, in-app notification delivery |
| [manufacturing-automation](functional-areas/manufacturing-automation/) | CAD intake, manufacturing planning, machine simulation, and production visibility |
| [commercial-operations](functional-areas/commercial-operations/) | Customer credit, payment terms, quoting controls, collections, and commercial risk |
| [facility-operations](functional-areas/facility-operations/) | Site selection, utilities, shop layout, receiving, employee space, safety, and expansion |

> Add new functional areas as the product grows. Each area gets its own directory under `functional-areas/`.

## Agent Prompt Workflow

Requirements are translated into actionable **prompt files** that agents pick up and implement. See [prompts/README.md](prompts/README.md) for the full workflow.

## Discovery Questions

Questions that need investigation or a business decision before they can
become requirements live in [discovery/](discovery/). This area is an
intentional free-flow backlog for uncertainty, alternatives, and topics that
must not be mistaken for approved product behavior.

When a question is answered, record the decision and promote any resulting
behavior into the appropriate document under `functional-areas/`.

## Writing Good Requirements

A good requirement document:
1. **States the problem** — what user need does this address?
2. **Defines acceptance criteria** — how do we know it's done?
3. **Lists constraints** — performance, security, compatibility
4. **References related requirements** — cross-link to dependencies
5. **Is agent-legible** — clear enough that an AI agent can implement it

## Conversation-Driven Updates

Substantive product and engineering decisions made in conversations must be
written into the relevant Markdown requirement during the same work session.
Chat history is not the system of record.

See
[Requirements Documentation Standards](../docs/standards/requirements-standards.md)
for the required structure, traceability rules, assumption handling, and review
checklist.
