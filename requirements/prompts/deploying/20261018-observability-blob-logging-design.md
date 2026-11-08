# Prompt: Document a Low-Cost Azure Blob Logging Proposal

## Context

The user requested a reviewable Markdown design and PR for shared JSON service
logging: minute-scale VM buffers, multiwriter append blobs, background block-blob
and Parquet conversion, time/field queries, low total cost, and LRS/ZRS/GRS choices.
The follow-up invites better approaches on commodity object storage, including
existing log engines and table formats rather than a custom append pipeline.

## Requirements

- [Logging design and requirements](../../functional-areas/observability/low-cost-azure-blob-logging.md)
- [ADR 0007](../../../docs/architecture/decisions/0007-blob-first-operational-logging.md)

## Scope

Documentation only. Compare alternatives, explain query mechanics and failure
semantics, cite first-party sources, distinguish hypotheses from measured results,
and submit a PR targeting main for human review.

Do not deploy infrastructure, enable paid services, add credentials, implement
logging code, or imply approval of the proposed architecture.

## Acceptance Criteria

- [x] Common event contract and producer retry/restart boundaries are documented.
- [x] Shared append and immutable block ingestion are compared, including limits.
- [x] Parquet publication, pruning, query placement, and broad-scan limits are clear.
- [x] Cost model, candidate retention, redundancy, and backup distinctions are clear.
- [x] Loki, OpenObserve, Quickwit, Iceberg, and Delta Lake alternatives are compared.
- [x] Open approvals and implementation benchmark/acceptance criteria are recorded.
- [x] Documentation links, JSON example, and diff whitespace are checked.
- [ ] Human review selects or revises the proposal; no implementation is authorized.

## Validation

Documentation checks only; runtime benchmarks, fault injection, and cloud query
execution are future implementation work, not claimed results of this prompt.
