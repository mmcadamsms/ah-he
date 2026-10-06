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
| **Business and commercial** | Business model, initial offering, approvals, pricing, scheduling, learning | [Business and commercial strategy](topics/business-and-commercial-strategy.md) |
| **Business climate and community** | Zoning, permitting, local acceptance, economic development, industrial compatibility | [Business climate and community acceptance](topics/business-climate-and-community-acceptance.md) |
| **Customer and market** | Customer segments, demand clusters, buyer needs, proximity, trust, underserved markets | [Customer demand and market geography](topics/customer-demand-and-market-geography.md) |
| **Customer intent and experience** | Requirements discovery, assumptions, communication, trust, visibility | [Customer intent and experience](topics/customer-intent-and-experience.md) |
| **Technical and product** | Engineering, CAD, revisions, deliverables, design approval | [Engineering, CAD, and product development](topics/engineering-cad-and-product-development.md) |
| **Data, AI, and platform** | Customer IP, architecture, integrations, automation boundaries, auditability | [Data, AI, and platform](topics/data-ai-and-platform.md) |
| **Manufacturing and quality** | Materials, process selection, CNC, additive, finishing, inspection, assembly | [Manufacturing processes and quality](topics/manufacturing-processes-and-quality.md) |
| **Suppliers and procurement** | Supplier qualification, subcontracting, outside processing, purchased components | [Suppliers and procurement](topics/suppliers-and-procurement.md) |
| **People and operating model** | Roles, qualifications, training, organization, support, operational resilience | [People and operating model](topics/people-and-operating-model.md) |
| **Workforce and labor markets** | Labor cost, skill availability, reliability, productivity, recruiting, retention | [Workforce cost and labor markets](topics/workforce-cost-and-labor-markets.md) |
| **Supply chain, geography, and logistics** | Industrial clusters, domestic materials, wholesale inputs, fuel, electricity, freight, customer delivery | [Industrial geography](topics/industrial-geography-and-domestic-supply-chain.md), [raw materials and energy](topics/raw-materials-energy-and-input-costs.md), and [freight and shipping](topics/freight-shipping-and-customer-delivery.md) |
| **Tax, financial, and capital** | Tax structure, unit economics, cash flow, equipment investment, financing, incentives | [Tax, finance, and capital](topics/tax-finance-and-capital.md) |
| **Legal, safety, and compliance** | Liability, regulation, prohibited work, insurance, safe human oversight | [Legal, safety, and compliance](topics/legal-safety-and-compliance.md) |
| **Lifecycle and sustainability** | Returns, repair, warranty, waste, recycling, environmental impact | [Lifecycle and sustainability](topics/lifecycle-and-sustainability.md) |

Add a topic file when a group of questions will benefit from its own research,
decisions, evidence, or follow-through. Keep the table above as the navigation
index.

The complete canonical list is [Question Index](question-index.md). New or
uncategorized questions temporarily go to [Question Inbox](question-inbox.md).
Partial directions already established in conversation are recorded separately
in [Emerging Directions](emerging-directions.md).

## Question Prefixes

Prefixes identify the subject, not the team that must answer it:

| Prefix examples | Topic |
|---|---|
| `BIZ`, `APR`, `BCL` | Business model, commercial approval, and business climate |
| `CUS`, `MKT`, `TRU` | Customer, demand, market geography, and trust |
| `ENG`, `CAD`, `PLT` | Technical and product |
| `MFG`, `CNC`, `ADD`, `FIN`, `QUA`, `ASM`, `OPS` | Manufacturing and operations |
| `HUM`, `LAB` | Human, employee, workforce, labor markets, and organization |
| `GEO`, `SUP`, `MAT`, `INP`, `LOG` | Geography, suppliers, materials, energy, input costs, freight, and delivery |
| `TAX`, `PRI`, `SCH` | Tax, financial, pricing, and scheduling |
| `LEG`, `SAF` | Legal, safety, and compliance |
| `DAT`, `AIA` | Data, security, and AI |
| `LIF`, `SUS` | Lifecycle and sustainability |

## Working Style

Keep this area easy to use:

1. Add questions as they arise, even if they are incomplete.
2. Search [Question Index](question-index.md) and the topic files before
   assigning a new ID.
3. Put each question in the closest topic file. If no topic fits, add it to the
   [Question Inbox](question-inbox.md) and create or choose a topic during
   triage.
4. Preserve the intent of questions asked in conversation.
5. Add context beneath a question when the reason for asking is not obvious.
6. Record partial answers, competing options, assumptions, and evidence
   without forcing an early decision.
7. Do not silently turn an unanswered question into a product assumption.

## Canonical Question and Duplicate Rules

Each question has exactly one canonical location and one permanent identifier.

Canonical format:

```markdown
### LAB-026 — Which US labor markets have workers willing and able to perform the planned work?

**State:** Open

**Aliases:**
- Where do hard workers live?
- Which locations have workers willing to do this work?
```

Rules:

1. IDs use an approved topic prefix plus a three-digit sequence.
2. IDs are never reused, renumbered, or moved to a different meaning.
3. The canonical question may be clarified, but its original intent must remain.
4. When the same question is asked again, add the new wording as an alias,
   source quotation, or additional context under the existing ID.
5. When a question substantially overlaps but is not equivalent, add a
   `**Related:**` link instead of silently duplicating it.
6. When two questions are confirmed duplicates, keep the older canonical ID
   and mark the newer ID `Superseded` with a link. Never delete the historical
   ID.
7. Update [Question Index](question-index.md) whenever questions are added,
   renamed, moved, or change state.
8. Free-form wording belongs in the question body; indexing depends on the ID,
   not exact phrasing.

Regenerate and validate the canonical index with:

```powershell
.\scripts\update-discovery-question-index.ps1
```

The command fails if the same ID exists in more than one topic file.

## Suggested Question States

| State | Meaning |
|---|---|
| `Open` | The question has not been sufficiently answered. |
| `Exploring` | Research, prototyping, or discussion is underway. |
| `Answered` | A decision or sufficiently supported answer is recorded. |
| `Deferred` | The question matters but is intentionally postponed. |
| `Converted` | The answer has been captured in a formal requirement, ADR, runbook, or policy. |
| `Superseded` | The question duplicates an older canonical ID and links to it. |

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
