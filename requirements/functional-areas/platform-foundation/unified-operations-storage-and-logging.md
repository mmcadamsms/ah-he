# Unified Operations Storage and Logging

**Status:** Proposed design for review; no runtime implementation or deployment.

## Problem

Inventory/custody, machine care, in-process inspection, benchmark quotes and
returns need consistent shared identities, durable history, protected documents,
and dependable asynchronous processing. Independent stores and logging formats
would duplicate data and make holds, quantities, measurements, financial effects
and recovery difficult to trust.

## Objective

Design one Kubernetes-based operational foundation for
[backlog items 1-5](../../software-development-backlog.md), initially using Azure
Storage with a clean provider boundary suitable for a later AWS implementation.
Unify software diagnostics without confusing logs with durable business audit.
Present the design for user changes/agreement before implementation.

## Terminology

- **Operational transaction scope:** Records that can be atomically changed and
  guarded together; proposed pilot scope is one operating organization.
- **Head:** Authoritative current business state/version.
- **Change history:** Durable attributable changes reconcilable to current
  state, committed with the business mutation.
- **Evidence artifact:** Actual protected document/measurement/photo, distinct
  from a log or prediction.
- **Outbox:** Durable obligation committed with a business change; queues only
  notify workers that the obligation exists.
- **Projection:** Rebuildable query view, not release/allocation authority.
- **Provider port:** Application contract for semantics, not a cloud SDK wrapper
  promising impossible cross-provider equivalence.
- **Diagnostic loss budget:** Approved telemetry loss allowance; does not permit
  losing required business history.

## Inputs

The five linked domain requirements; operating organization/customer/actor
identities; approved policies, units/currencies and revisions; storage/identity/
network environment; artifacts and access/retention classification; expected
concurrency, throughput, evidence/log volume and queries; recovery targets,
diagnostic loss bounds, region/redundancy and total-cost budget.

## Rules

1. **UF-01 - Review gate.** Implement only after the unified design is agreed.
   Proposed architecture and example limits are not deployment approval.
2. **UF-02 - Shared records.** Reuse asset/item, part/revision, order/line,
   actor, custody, evidence, quality and return identities. Do not create
   separate registries for each ranked feature.
3. **UF-03 - Azure first.** Evaluate Table, Blob, Queue and filesystem storage
   together for their appropriate roles. Favor low total cost, not “Blob for
   every data type” or storage capacity pricing alone.
4. **UF-04 - Portable semantics.** Keep domain logic cloud-neutral with
   versioned conditional commit/read, evidence, transport and view contracts.
   Validate adapters; reject unsupported semantics rather than silently
   weakening atomicity or authorization.
5. **UF-05 - Atomic business change.** A supported command guards all
   relevant heads/prerequisites and commits state, history, command result
   and outbox together. Explicitly reject unsupported transaction scope/size.
6. **UF-06 - Retry safety.** Durable idempotency records bind caller, action,
   scope and request digest. Conflicts, duplicate/reordered events, restarts
   and unknown commit outcomes cannot duplicate quantity, completion, release
   or remedy. Provider retry tokens alone are insufficient.
7. **UF-07 - Actual truth.** Keep planned/predicted and confirmed/measured
   facts distinct. Unknown location, usage, qualification, inspection or
   execution is not zero, passed, available or refunded.
8. **UF-08 - Authoritative release.** Recheck current evidence/hold/policy/
   meter/time dependencies during release/allocation commands. Stale
   projections, logs and acknowledgements cannot authorize release.
9. **UF-09 - Correct history.** Corrections retain original facts, actor,
   reason/approval, revisions and effects. Exact quantities/money retain
   explicit units/currency and approved conversion/rounding.
10. **UF-10 - Durable work.** Incomplete outbox obligations are discoverable
    without a successful queue send/index update. Renewal, consumer receipts,
    explicit poison/blocked state and reconciliation survive queue TTL,
    duplicates, worker death and ambiguous acknowledgements.
11. **UF-11 - External effects.** Reserve financial/return allocations before
    execution. Persist provider intent/reference, reconcile unknown results,
    and do not release allocations or retry money blindly during ambiguity.
12. **UF-12 - Evidence safety.** Stage/verify/scan artifacts, use immutable
    versions/hashes, and guard links/pins versus deletion. Required missing
    or unqualified evidence holds the operation. Signed grants are temporary,
    never identity or permanent records.
13. **UF-13 - Isolation.** Authenticate staff/customers/services and enforce
    organization/customer/action permissions at every read/write/artifact
    boundary. Use separate least-privilege workload identities; trace context
    and scanned labels are not access credentials.
14. **UF-14 - Unified diagnostics.** Use OTel-compatible timestamps, severity,
    resource and correlation semantics across runtimes. Apply redaction,
    limits, scoped access, bounded buffers and approved diagnostic loss.
15. **UF-15 - Separate audit.** Business history must persist with its command
    regardless of logging availability. No debug sampler or diagnostic
    retention policy deletes required quality/maintenance/financial history.
16. **UF-16 - Existing engines first.** Evaluate Loki on Blob against
    appropriate alternatives and archive-only compute. Do not build a
    custom log catalog/search lake without a measured, approved justification.
17. **UF-17 - Explicit operations.** Expose outbox age, failed writes/scans,
    telemetry loss, WAL condition, backup age and query freshness through a
    health/alert path independent of successful log ingestion.
18. **UF-18 - Recoverable system.** Back up consistent operational records,
    receipts, allocations/work, evidence versions, schema/policies and needed
    engine state. Test restore/reconciliation before restarting external
    actions. Redundancy and Blob versioning alone do not back up Tables.
19. **UF-19 - Honest scope.** Complete bounded manual workflows before claiming
    external integrations or full domain delivery. Track all remaining linked
    requirements; no unverified manual order supports money movement.
20. **UF-20 - Cloud move.** Prove destination semantic contracts and a fenced
    export/import/cutover with balances, hashes, access and replay verification.
    Never describe replacing a connection string as a completed migration.

## Assumptions

- Java/Spring API and worker remain the canonical business runtimes; AKS is
  the first compute candidate and provider SDKs remain adapter details.
- Low-volume pilot permits one operational partition per organization. Scale
  and query measurements must confirm this; unsupported workloads trigger review.
- Humans provide approved care/inspection/return policies and actual evidence
  before qualified automatic integrations are selected.
- Azure Storage is a cost preference, not a measured lowest-total-cost result.
- Numeric retention/recovery/loss/volume/cost targets remain approval inputs.

## Constraints

- No direct hazardous servicing, physical safety authorization, CNC motion,
  autonomous measurement correction or blanket financial-policy approval.
- No cross-partition transaction, exactly-once queue, universal search index,
  zero-loss regional failover or unlimited hot-partition scale claim.
- No shared account keys in application code, customer secrets in telemetry,
  unapproved customer-file uploads, or cross-customer evidence visibility.
- Local/PVC buffers and log-engine metadata/WAL have separate loss/recovery
  profiles; all state cannot be assumed safely reconstructible from log blobs.

## Acceptance Criteria

| ID | Observable outcome after implementation approval |
|----|--------------------------------------------------|
| UF-AC-01 | Two concurrent reservations/return allocations cannot exceed the authoritative remaining quantity; balances and history reconcile. |
| UF-AC-02 | A stock split, command receipt and history/outbox either commit together or leave no partial business mutation. |
| UF-AC-03 | Retry after timeout-after-commit or process restart returns the original result without duplicate stock, care, inspection or remedy effects. |
| UF-AC-04 | A concurrent gauge/asset hold or evidence/policy revision prevents a release evaluated against stale dependencies. |
| UF-AC-05 | Lost/expired queue wakeup, failed send, worker death and duplicate dequeue retain recoverable incomplete work; poison work is visible. |
| UF-AC-06 | An ambiguous payment/label response remains unresolved and cannot free allocations or claim completed execution without reconciliation. |
| UF-AC-07 | An unscanned/missing evidence object cannot pass a checkpoint; concurrent link/GC cannot delete newly pinned evidence. |
| UF-AC-08 | Staff/customer/device permission tests prevent other-customer access, forged attribution and unsigned label-based access. |
| UF-AC-09 | Java/Python/work queues share correlation and safe structured diagnostics; secret/customer-content probes do not leak into logs. |
| UF-AC-10 | Log outage/restart/disk exhaustion produces the approved diagnostic-loss behavior and independent alert without losing required business audit. |
| UF-AC-11 | Checkpoint/restore preserves balances, original failures, receipts, artifact versions and pending work; provider reconciliation prevents duplicate refunds. |
| UF-AC-12 | Table/AWS conformance and fenced migration preserve the same declared atomicity, access and replay outcomes before portability is claimed. |
| UF-AC-13 | All five domain suites reference the original requirements, explicitly tracking first-slice versus full completion and missing external capabilities. |
| UF-AC-14 | Representative contention, volume, query and recovery tests record total cost/resource use and reject unqualified hot-partition/log-engine configurations. |

## Failure Behavior

Expose conflict, unknown commit/execution, stale/missing policy or evidence,
held qualification, oversized/unsupported transaction, unavailable storage,
blocked work, denied artifact, stale projection, diagnostic shedding and failed
recovery as distinct states. Retry only with stable identity and bounded policy.
Never catch these as successful releases, zero usage, completed service or
issued refunds.

## Out of Scope

Implementation before user agreement; provisioning cloud infrastructure in this
design PR; full ERP/general-purpose approval system; automatic scraping,
unqualified inspection/CAM/mechanics, physical machine/safety control; unapproved
operating policies; multi-cloud active-active writes; claiming measured savings.

## References

- [Unified design and authoritative provider sources](../../../docs/architecture/unified-operational-platform-design.md).
- [Proposed ADR 0009](../../../docs/architecture/decisions/0009-azure-first-portable-operational-platform.md).
- [Design/review prompt](../../prompts/deploying/20261031-unified-operations-storage-design.md).
- [1: Inventory and lifecycle](../manufacturing-automation/inventory-tooling-and-equipment-lifecycle.md).
- [2: Machine care](../manufacturing-automation/machine-maintenance-and-care.md).
- [3: In-process inspection](../manufacturing-automation/in-process-inspection-and-progressive-verification.md).
- [4: External quote benchmarks](../commercial-operations/external-market-quote-benchmarking.md).
- [5: Returns and remedies](../commercial-operations/return-authorization-and-refund-workflow.md).
- [Prior logging proposal #8](https://github.com/mmcadamsms/ah-he/pull/8).
