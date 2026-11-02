# Prompt: Implement Shared Operational Foundation and Workflows 1-5

## Requirement Reference

- [Unified platform](../../functional-areas/platform-foundation/unified-operations-storage-and-logging.md).
- [Actor/beneficiary context](../../functional-areas/platform-foundation/actor-and-beneficiary-operation-context.md).
- [Design](../../../docs/architecture/unified-operational-platform-design.md).
- [Backlog](../../software-development-backlog.md).

## Objective

Implement the agreed shared design and initial manual workflows for backlog
items 1-5, with Azure-backed durable records, attributable context, protected
evidence, guarded audit/outbox and authenticated UI/API. User authorized
implementation on 2026-10-31. Deployment spending and domain policies still
require explicit configuration/approval.

## Acceptance Criteria

1. Durable conditional storage, command receipts, audit/context and work
   survive retries/restarts without weakening domain guards.
2. All five manual domain workflows have typed commands, authorization,
   explicit failures, UI integration and focused tests.
3. Preserve the existing manufacturing prototype; no simulation becomes
   accepted production, maintenance or inspection evidence.
4. Required actual evidence and configured policies cannot be replaced by
   invented values or successful-looking integration stubs.
5. Provide local/deployment guidance, Loki-on-Blob configuration and provider
   contract tests without provisioning resources.
6. Record remaining full-requirement/integration/qualification work plainly;
   initial slices are not advertised as completed production systems.

## Scope

- Shared API/worker library, Azure Table/Blob/Queue adapters, verified actor/
  beneficiary grants, manual inventory/care/inspection/benchmark/return flows.
- Existing web client staff/customer operational interface.
- Local Azure emulator and Kubernetes/Loki configuration and verification.
- No physical machine control, unselected payment/carrier integration, AWS
  production adapter, unapproved operating policy or live cloud provisioning.

## Testing Requirements

- [x] Atomic store/timeout/idempotency, context/access and queue/evidence cases.
- [x] Domain quantity, due/hold, measured release, quote and remedy invariants.
- [x] API and frontend behavior, current prototype regression and builds.
- [x] Document remaining real-cloud/provider/recovery qualification.

## Delivery Evidence and Limits

- Domain/API/worker tests and packaged Java runtimes built; real Azurite adapter
  contract executed without skips.
- Nine React tests, TypeScript/Vite build and scoped ESLint completed; existing
  prototype hook dependency warning remains outside this change.
- Packaged API/worker loopback startup, protected API/readiness/metrics checked.
- Bicep compiled; 23 Kubernetes resources passed strict schema checks; pinned
  Loki/collector configurations and native CRI/JSON transformation checked.
- No Docker daemon, live Azure/Kubernetes IAM/network/PVC, scanner, restore,
  payment/carrier/physical integration or AWS qualification is claimed.
- [Operational runbook and remaining delivery matrix](../../../docs/runbooks/operational-workflows.md).

## PR Review Follow-up (2026-11-02)

- Recovered the original five-item discussion and checked the shared design,
  domain services, API/portal, tests and explicit deployment limitations.
- Reproduced four defects with failing regression tests, then fixed consumed
  stock movement, competing care holds, prequalification measurements (including
  release of older results) and duplicate-quote self-comparisons.
- Preserved actual return inspection/allocation and pending-execution semantics;
  no carrier/payment/repair/replacement effects were added.
- Verified 44 Java tests with no skips, including the real Azurite contract,
  both packaged runtimes, nine web tests, web lint and operational build.
  The existing prototype hook warning and Gradle/SDK deprecations remain.
- Added PR automation and a loopback-only ephemeral emulator validation script
  that fails when the adapter contract is skipped.
- Corrected backlog/runbook status: all five are partial manual deliveries,
  smaller than the original recommended first slices, not full completion.
  See UF-25 / UF-AC-19 through UF-AC-22 for the clarified regression guards.

## Dependencies

- [Design review](../deploying/20261031-unified-operations-storage-design.md).
- Approved scoped customer/maintenance/inspection/return policies for actual use.

---
**Status:** deploying (PR #14 and follow-up submission; no environment deployment performed)

**Created:** 2026-10-31
