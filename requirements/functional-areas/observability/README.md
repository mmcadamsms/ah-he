# Observability

Requirements and proposed designs for operational visibility, logging, and
diagnostics. Proposed infrastructure must not be mistaken for deployed behavior.

| Document | Scope |
|----------|-------|
| [Low-Cost Blob Logging and Object-Storage Alternatives](low-cost-azure-blob-logging.md) | Proposed JSON contract, Parquet/Iceberg/Delta, Loki/OpenObserve/Quickwit comparison, efficient queries, cost, retention, and recovery. |

The logging proposal covers operational diagnostics, not mandatory business audit
records or a complete metrics/tracing platform. See
[ADR 0007](../../../docs/architecture/decisions/0007-blob-first-operational-logging.md).
