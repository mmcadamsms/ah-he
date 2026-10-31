# Prompt: Design Unified Operations Storage and Logging

## Requirement Reference

- **Functional Area:** platform-foundation; backlog items 1-5.
- [Shared requirement](../../functional-areas/platform-foundation/unified-operations-storage-and-logging.md).
- [Design deliverable](../../../docs/architecture/unified-operational-platform-design.md).
- [ADR 0009](../../../docs/architecture/decisions/0009-azure-first-portable-operational-platform.md).
- [Actor and beneficiary context](../../functional-areas/platform-foundation/actor-and-beneficiary-operation-context.md).

## Objective

Produce a unified Azure Storage-first, Kubernetes-based design for inventory/
custody/WIP, care, measured inspection, market benchmarks and returns/remedies,
including logging, protected evidence, provider boundaries and recovery. Present
the design for user changes/agreement; implement only after approval.

## Acceptance Criteria

1. All five ranked requirements have shared records, bounded initial slices
   and a path to their remaining full behavior.
2. Table atomicity, concurrent release/allocation guards, durable command
   receipts/outbox, evidence lifecycle and external-effect ambiguity are explicit.
3. Azure/AWS contracts and migration preserve semantics rather than promise
   drop-in cloud equivalence.
4. Logging evaluates the prior proposal without silently merging it and
   separates diagnostics from business audit.
5. Identity, Kubernetes/PVC dependencies, recovery, retention, cost and
   numerical targets awaiting approval are visible.
6. Documentation is navigable, validated and presented in a design-only PR;
   no implementation or cloud spending is performed before agreement.
7. Every business operation distinguishes actor/affiliation/representation
   authority, beneficiary/purpose, subject and economic allocation, preserving
   context through work/history and protecting customer-provisioned access.

## Scope

### In Scope

- Requirements, proposed design/ADR, prior proposal and provider-document review.
- Shared cloud capabilities, failure protocols, domain integration, test and
  delivery sequence, and review decisions.

### Out of Scope

- Runtime/storage/infrastructure implementation or deployment before agreement.
- Approved policies, financial providers, autonomous machine/safety control.
- Silently accepting or merging PR #8 or unrelated branches.

## Technical Notes

- Baseline main after #12: `68b1cfb73143e90f35526715252070f698641fcb`.
- Keep provider SDKs outside domain code and existing runtime boundaries.
- Current Table and log-connector semantics are verified against public sources;
  version-specific implementation conformance must follow approval.

## Testing Requirements

- [x] Validate changed Markdown paths/fragments and whitespace.
- [x] Check requirement/design/ADR status, traceability and consistent scope.
- [x] Confirm all five domain references and explicit implementation gate.
- Future behavioral/provider/recovery tests are specified in the design, not
  claimed executed by documentation review.

## Dependencies

- [Requirements standards](../../../docs/standards/requirements-standards.md).
- [API standards](../../../docs/standards/api-standards.md).
- [Prompt pipeline](../README.md).
- User review and design agreement before implementation.

## Review Decisions

- 2026-10-31: User approved Grafana Loki backed by Azure Blob Storage for
  logging. Product selection is settled; broader architecture, deployment
  details, policies and implementation approval remain pending.
- 2026-10-31: User requires first-class actors and beneficiaries for business
  operations, including customer-provisioned users, internal customer-serving
  staff and shared business benefit. Added shared context requirements and
  design; implementation remains gated on broader agreement.

---
**Status:** deploying (design-only PR; awaiting user review/agreement, not runtime deployment)

**Created:** 2026-10-31

**Author:** GitHub Copilot
