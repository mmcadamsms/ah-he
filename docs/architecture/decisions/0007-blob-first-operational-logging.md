# ADR 0007: Evaluate Blob-First Operational Logging

## Status

Proposed. This records a design recommendation, not approval to deploy or spend.

## Context

We need very-low-cost service diagnostics with a common JSON contract, batched
VM writes, useful time/field queries, and selectable redundancy. Shared append
blobs are attractive for reducing object counts, but introduce multiwriter retry
and rollover coordination. Timestamped filenames alone do not index JSON fields,
and cheap storage can be overwhelmed by transaction, query, or recovery costs.

## Decision

Evaluate existing object-storage-native log engines (OpenObserve, Loki, and
Quickwit where full-text indexing matters) against an on-demand analytical lake.
The user's append-blob suggestion is not a constraint that excludes better
commodity-storage approaches. Include fixed compute, metadata/WAL recovery,
index amplification, licensing/maintenance, and engineering effort in comparison.

For the DIY lane, propose a blob-first log lake and benchmark immutable per-writer
NDJSON block batches against bounded, sharded append blobs before selecting ingestion.
The preferred first candidate is immutable block batches, followed by background
conversion to compressed, typed, time-partitioned Parquet block blobs.

For the DIY option, evaluate Iceberg/Delta transactions before building a custom
catalog. Publish validated immutable output through a safe snapshot commit;
readers honor the selected format's metadata and deletion semantics, not raw
wildcards. Start query evaluation with in-region, on-demand DuckDB and compare
managed serverless SQL where appropriate. Do not
promise an arbitrary-field index, full-text performance, or measured savings.

Select storage redundancy independently from logical-deletion protection and
backup. Keep diagnostic shedding/loss tolerances separate from business/audit
durability. No paid resources or runtime changes are authorized by this ADR.

The detailed contract, alternatives, failure rules, benchmarks, sources, and
approval gates are in the canonical
[logging design and requirements](../../../requirements/functional-areas/observability/low-cost-azure-blob-logging.md).

## Consequences

- Open JSON/Parquet formats preserve query-engine choice and avoid mandatory
  always-on indexed ingestion.
- Log-native engines may save more implementation/operating effort than their
  continuously running components cost; workload evidence must decide.
- Column and row-group pruning can reduce reads for suitable queries, but broad
  trace/message searches may still need scans or a separately costed index.
- Spooling, compaction, publication, late arrivals, retention, and recovery add
  engineering/operational work that must be included in total cost.
- Append-to-block conversion writes new objects; redundancy is not backup.
- Implementation remains gated on workload measurements and owner-approved
  budget, retention, security, freshness, and recovery objectives.
