# ADR 0009: Azure-First Portable Operational Platform

## Status

Proposed. User review/agreement is required before implementation or deployment.

The **logging backend subdecision is approved**: on 2026-10-31 the user selected
Grafana Loki backed by Azure Blob Storage. This does not accept the remaining
operational storage design or authorize runtime deployment.

## Context

Backlog items 1-5 require shared durable inventory/asset/custody, care, measured
inspection, benchmark quotes, and return/remedy records. The user requests
Kubernetes compute, Azure Storage-first cost choices, unified logging, and
compartmentalized storage suitable for a later AWS move.

The current prototype is in-memory and has no shared operational ledger.
Azure Table transactions are same-partition, queues can redeliver and expire,
and neither Blob diagnostics nor cloud redundancy replaces business audit and
backup. Proposed ADR 0007 remains in separate unmerged PR #8; ADR 0008 keeps
manufacturing predictions/evidence/release distinct.

## Proposed Decision

Use domain modules in the existing Java API and background worker. Propose
Azure Table Storage for authoritative records, private block blobs for evidence,
and Queue Storage for wakeups backed by a transactional outbox. Keep conditional
commit, evidence, view, and transport semantics behind cloud-neutral ports.

For the initial pilot, keep one operational transaction partition per
organization so all supported quantity, qualification, evidence, order-line and
remedy dependencies can be guarded atomically. Enforce bounded commands; do not
claim automatic cross-partition scaling. Review a transactional database
exception if scope/scale proves unsuitable rather than relax correctness.

Use the user-selected Grafana Loki backed by Azure Blob as the unified
diagnostic backend. Propose OpenTelemetry collection and Grafana staff UI,
accounting for continuously running compute,
identity support and per-writer persistent WAL/buffers. Do not build a bespoke
Parquet/catalog/search platform by default. Business audit commits with business
state, outside the diagnostic pipeline.

Use Kubernetes workload identity/least privilege, authenticated staff/customer
scopes, protected evidence lifecycle, independent backup and reconciled external
effects. AWS S3/DynamoDB/SQS adapters and a fenced migration must pass the same
semantic tests before portability is claimed.

## Alternatives and Tradeoffs

- **SQL/PostgreSQL first:** Easier cross-entity transactions/queries; a valid
  future exception if it reduces total engineering/operating cost. Not the
  Azure Storage-first proposal requested for review.
- **Cosmos DB / Service Bus by default:** Additional capabilities and cost
  profiles may justify later use; not necessary merely to persist the initial
  bounded workflow.
- **Blob-only business records:** Cheap objects do not provide the needed
  guarded multi-record changes and query behavior without substantial machinery.
- **One partition per aggregate immediately:** Better distribution, but
  cross-aggregate quantities and gates would need a proved coordination design.
- **One service/store per ranked item:** Duplicates identity/evidence and makes
  cross-domain holds/remedies harder to reconcile.
- **Custom log lake / alpha Blob exporter as production default:** Lower idle
  compute is possible but ingestion/recovery/query maturity and engineering
  burden informed the selection; Loki is the approved backend. Deployment
  qualification remains required and any replacement needs further agreement.
- **Provider-agnostic generic CRUD:** Conceals atomicity, identity, queue and
  lifecycle differences; explicit capability contracts are safer.

## Consequences

The small pilot can prioritize correctness and cheap storage services, but
inherits application-managed views, outbox recovery, evidence coordination and
Table backups. Hot-partition limits, WAL filesystem needs, identity connectors,
policies, costs and recovery objectives are explicit acceptance gates.

Nothing here changes accepted ADR 0004/0005 boundaries, accepts ADR 0008,
merges/supersedes the separate logging PR, or authorizes machine control.
Following user changes/agreement, update this ADR's status and issue bounded
implementation prompts for the complete domain requirements.

## References

- [Unified design](../unified-operational-platform-design.md).
- [Shared requirements](../../../requirements/functional-areas/platform-foundation/unified-operations-storage-and-logging.md).
- [Ranked backlog](../../../requirements/software-development-backlog.md).
- [Runtime boundaries](0005-organize-code-by-runtime-boundary.md).
- [Separated manufacturing evidence proposal](0008-separate-manufacturing-evidence-from-playback.md).
- [Earlier logging PR #8](https://github.com/mmcadamsms/ah-he/pull/8).
