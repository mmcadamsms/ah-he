# Discovery Questions

## Purpose

This directory is the durable place for open-ended questions about the
business, product, customers, manufacturing system, and operating model.

Discovery questions are not approved requirements. They capture uncertainty
that must be discussed, researched, tested, or decided before implementation
or business execution depends on it.

## Topic Index

Questions are organized first by broad topic area and then by more specific
sections. This gives each question a clear home without preventing free-form
capture.

| Broad topic area | Typical questions | Current location |
|---|---|---|
| **Business and commercial** | Business model, initial offering, approvals, pricing, scheduling, warranties | [Business-wide backlog](business-idea-question-backlog.md#business-purpose-and-initial-market) |
| **Customer and market** | Customer segments, intent discovery, trust, communication, underserved markets | [Business-wide backlog](business-idea-question-backlog.md#customer-intent-and-requirements-discovery) |
| **Technical and product** | Engineering, CAD, platform architecture, integrations, automation boundaries | [Business-wide backlog](business-idea-question-backlog.md#design-and-engineering) |
| **Manufacturing and operations** | Process selection, CNC, additive, finishing, inspection, assembly, delivery | [Business-wide backlog](business-idea-question-backlog.md#manufacturing-process-selection) |
| **Human, employee, and organization** | Roles, skills, staffing, training, working conditions, human approvals | [Business-wide backlog](business-idea-question-backlog.md#people-employees-and-organization) |
| **Supply chain and geography** | Industrial clusters, manufacturing regions, domestic materials, suppliers | [Industrial geography and domestic supply chain](topics/industrial-geography-and-domestic-supply-chain.md) |
| **Financial and capital** | Unit economics, cash flow, equipment investment, financing, risk reserves | [Business-wide backlog](business-idea-question-backlog.md#pricing-quoting-and-unit-economics) |
| **Legal, safety, and compliance** | Liability, regulations, prohibited work, insurance, safe human oversight | [Business-wide backlog](business-idea-question-backlog.md#legal-liability-and-regulatory-boundaries) |
| **Data, AI, and security** | Customer IP, data handling, AI limitations, auditability, privacy, security | [Business-wide backlog](business-idea-question-backlog.md#data-privacy-and-intellectual-property) |
| **Lifecycle and sustainability** | Returns, repair, warranty, waste, recycling, environmental impact | [Business-wide backlog](business-idea-question-backlog.md#returns-warranty-and-lifecycle) |

Add a topic file when a group of questions will benefit from its own research,
decisions, evidence, or follow-through. Keep the table above as the navigation
index.

## Question Prefixes

Prefixes identify the subject, not the team that must answer it:

| Prefix examples | Topic |
|---|---|
| `BIZ`, `APR` | Business model and commercial approval |
| `CUS`, `TRU` | Customer and market |
| `ENG`, `CAD`, `PLT` | Technical and product |
| `MFG`, `CNC`, `ADD`, `FIN`, `QUA`, `ASM`, `OPS` | Manufacturing and operations |
| `HUM` | Human, employee, workforce, and organization |
| `GEO`, `SUP`, `MAT` | Geography, suppliers, and materials |
| `PRI`, `SCH` | Financial, pricing, and scheduling |
| `LEG`, `SAF` | Legal, safety, and compliance |
| `DAT`, `AIA` | Data, security, and AI |
| `LIF`, `SUS` | Lifecycle and sustainability |

## Working Style

Keep this area easy to use:

1. Add questions as they arise, even if they are incomplete.
2. Put each question in the closest topic file. If no topic fits, add it to the
   business-wide backlog and create a topic later when a cluster emerges.
3. Preserve the intent of questions asked in conversation.
4. Add context beneath a question when the reason for asking is not obvious.
5. Record partial answers, competing options, assumptions, and evidence
   without forcing an early decision.
6. Do not silently turn an unanswered question into a product assumption.

The backlog can contain free-form prose. Question identifiers are recommended
because they make discussion and traceability easier, but wording quality
should not prevent capturing an important question.

## Suggested Question States

| State | Meaning |
|---|---|
| `Open` | The question has not been sufficiently answered. |
| `Exploring` | Research, prototyping, or discussion is underway. |
| `Answered` | A decision or sufficiently supported answer is recorded. |
| `Deferred` | The question matters but is intentionally postponed. |
| `Converted` | The answer has been captured in a formal requirement, ADR, runbook, or policy. |

## Answer Record

When a question becomes answerable, add a short record beneath it:

```markdown
**State:** Answered

**Answer:** The decision in plain language.

**Why:** Evidence, tradeoffs, constraints, and rejected alternatives.

**Follow-through:**
- Requirement: `requirements/functional-areas/...`
- ADR: `docs/architecture/decisions/...`
- Prompt: `requirements/prompts/...`
- Validation: test, experiment, interview, quote, or other evidence
```

Not every answer needs every link. Important product behavior must ultimately
be represented in the relevant functional requirement according to
[Requirements Documentation Standards](../../docs/standards/requirements-standards.md).

## Relationship to Requirements

Use this distinction:

- **Discovery question:** What do we still need to learn or decide?
- **Decision:** What answer are we choosing, and why?
- **Requirement:** What must the product or operation do as a result?
- **Prompt:** What implementation work delivers the requirement?

Question backlogs may remain broad and conversational. Formal requirements
must use the structured sections and measurable acceptance criteria defined by
the repository standards.
