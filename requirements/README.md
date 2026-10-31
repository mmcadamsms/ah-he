# Requirements

## Overview

Product requirements are organized by **functional area** — logical groupings of related features and capabilities. Each functional area has its own directory with requirement documents.

## Functional Areas

| Area | Description |
|------|-------------|
| [user-management](functional-areas/user-management/) | User accounts, roles, profiles, authentication |
| [approvals](functional-areas/approvals/) | Approval workflows, chains, delegation, escalation |
| [notifications](functional-areas/notifications/) | Email, push, in-app notification delivery |
| [manufacturing-automation](functional-areas/manufacturing-automation/) | CAD intake, planning, CNC verification, workholding assessment, machine care, in-process inspection, and inventory/tooling lifecycle |
| [commercial-operations](functional-areas/commercial-operations/) | Customer credit, payment terms, quoting controls, collections, and commercial risk |
| [facility-operations](functional-areas/facility-operations/) | Site selection, utilities, shop layout, receiving, employee space, safety, and expansion |
| [platform-foundation](functional-areas/platform-foundation/) | Shared durable storage, evidence, asynchronous work, access, logging, and recovery |

> Add new functional areas as the product grows. Each area gets its own directory under `functional-areas/`.

### Progressive Manufacturing Verification

New requirements/design, with runtime implementation deferred:

- [CNC Verification, Cutting Engagement, and Above-Zero Dry Runs](functional-areas/manufacturing-automation/cnc-program-verification-and-cutting-engagement.md)
- [Pass-by-Pass Workholding Assessment](functional-areas/manufacturing-automation/pass-by-pass-workholding-analysis.md)
- [Machine Maintenance and Care](functional-areas/manufacturing-automation/machine-maintenance-and-care.md)
- [In-Process Inspection](functional-areas/manufacturing-automation/in-process-inspection-and-progressive-verification.md)
- [Proposed Integration Design](../docs/manufacturing/progressive-manufacturing-verification-design.md)

## Software Development Backlog

[Definition-Led Software Development Backlog](software-development-backlog.md)
stack-ranks unfinished software against the synchronized repository source.
It describes high-level requirements, remaining implementation slices, open
decisions, and dependencies, and distinguishes delivered prototype/email scope
from requirements-only work. The rank reflects definition and bounded
implementation readiness, not business priority or production qualification.

The [Unified Operational Platform Design](../docs/architecture/unified-operational-platform-design.md)
proposes shared Azure-first storage and logging for backlog items 1-5 on
Kubernetes, with provider boundaries for a later AWS move.
Its [shared requirement](functional-areas/platform-foundation/unified-operations-storage-and-logging.md)
and proposed ADR are **for review; implementation waits for user agreement**.

## Agent Prompt Workflow

Requirements are translated into actionable **prompt files** that agents pick up and implement. See [prompts/README.md](prompts/README.md) for the full workflow.

## Discovery Questions

Questions that need investigation or a business decision before they can
become requirements live in [discovery/](discovery/). This area is an
intentional free-flow backlog for uncertainty, alternatives, and topics that
must not be mistaken for approved product behavior.

The current portfolio assessment and recommended decision sequence are in
[Manufacturing Business Review and Next Steps](discovery/manufacturing-business-review-and-next-steps.md).
The breadth-first discovery method is defined in the
[Manufacturing Business Learning Tour](discovery/manufacturing-business-learning-tour.md).

When a question is answered, record the decision and promote any resulting
behavior into the appropriate document under `functional-areas/`.

## Writing Good Requirements

Incoming constituent responses live in [feedback/](feedback/). They remain
untrusted proposals until reviewed and promoted through the requirements and
prompt workflow; collection does not authorize automatic agent execution.

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

## Position Papers

Strategic business research deliverables and their ordered frameworks live in
[position-papers](position-papers/). Discovery questions feed those papers,
but the papers have their own top-level area because they synthesize evidence
and state a reasoned position rather than serving as question backlogs.
