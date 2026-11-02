# Operational Workflows: Initial Manual Pilot

**Scope:** Runnable Azure-backed foundation, authenticated API/React portal, and
bounded manual slices of backlog items 1-5. This is not full requirement
completion, deployed infrastructure, or production qualification.

## Delivered and Remaining

| Area | Implemented | Still required for full delivery |
|------|-------------|---------------------------------|
| Shared foundation | Conditional Table commits; exact decimals; actor/beneficiary/grant context; receipts, post-image history, audit and outbox; version-pinned evidence; queue-backed audit projection | General onboarding/multiple affiliations, purpose-attributed read audit, backup/restore, migration, deletion/GC, poison repair and external-effect workers |
| 1. Inventory | Quarantined receipt, evidence qualification, quantity-preserving split, reservation, issue into single-unit WIP, cancellation, requested/confirmed custody movement | UOM transformations, dimensional remnants, complete tool components/lifecycle, MRP, forecasts and integrations |
| 2. Care | Approved asset/plan, calendar-or-usage triggers, meter freshness/reset holds, actual tasks/evidence, separate qualified release and current eligibility assessment | Multiple/revised applicable plans, event/condition forecasting, support-resource propagation, scheduling and device integration |
| 3. Inspection | Revisioned numeric plans, uncertainty interval containment, actual evidence, calibrated gauges, causal reinspection, complete declared checkpoint gates | Sampling/batch acceptance, metrology adapters, automatic checkpoints/offset changes and machine control |
| 4. Benchmarks | Owned authorized library, manual quote scope/expiry/currency, raw prices and reasoned adjustments distinct from internal cost | Selected external providers, repeated comparisons and approved normalization/integration |
| 5. Returns | Verified manual order source/window, RMA allocation, one quarantined package, confirmed contents, singleton actual inspection and approved basic remedy allocation | Multi-package/batch methods, freight/fees/rounding policy, cancellation/status breadth, carrier/QR/payment/repair/replacement execution |
| Diagnostics | Structured Java stdout; pinned Loki-on-Blob and OTel configuration; private Kubernetes services; independent health/Prometheus endpoints | Real Azure identity/network/PVC and failure qualification; approved dashboards, alert destinations, retention/loss budget and recovery tests |

These are **partial implementations of the proposed first slices**, not
completion of all five backlog acceptance criteria. In particular, approved
quantity/custody correction transactions, explicit downtime records and plan
amendments, complete benchmark inspection/packaging/exclusion scope, and broad
return lifecycle/financial policies remain unfinished. The portal is a generic
staff/customer command workspace, not a finished task-oriented customer product.

### PR #14 Review Follow-up (2026-11-02)

| Item | Source review and regression evidence |
|------|---------------------------------------|
| 1. Inventory | Balanced split/reservation/WIP and custody remain distinct. Fixed confirmation of an old movement after its source stock was consumed; neither request nor confirmation can move a zero-quantity identity. |
| 2. Care | Fixed overlapping care records overwriting an unfinished hold. One active intervention per asset is guarded atomically, retained through interruption/verification and cleared only on release or explicit failure. |
| 3. Inspection | Fixed measurement backdating before actual gauge qualification. Qualification start, expiry, current gauge/subject revisions and all declared checkpoints are guarded. |
| 4. Benchmarks | Fixed self-comparison: at least two distinct quote records are required (not necessarily different providers). Existing raw price, scope differences, reasoned adjustments, internal cost and expiry checks remain separate. |
| 5. Returns | Existing tests cover unidentified quarantine, exact contents, excess authorization refusal, actual defective-part inspection, itemized allocation and unchanged-key remedy replay. Approvals still do not execute money, labels, repairs or replacements. |

The workflow in `.github/workflows/operations.yml` runs Java/domain/API/worker
tests and packaged builds against a private ephemeral Azurite instance, plus
web tests/lint/build. It explicitly fails if the adapter contract was skipped.
Real Azure/Kubernetes qualification and production acceptance are separate gates;
repository branch-protection settings must require these jobs before merge.

`APPROVED_EXECUTION_UNAVAILABLE` is intentional: a remedy approval is not a
refund, carrier label, credit, repair or replacement. No consumer pretends to
perform these effects. A defective returned part may support a conclusive refund
review without being falsely accepted as production quality.

## Code and Build

Domain code and provider-neutral ports live in `common/operations`; only Azure
adapters exist. `backend/web-api` and `backend/background-worker` consume this
module. The test-only fake store is never a runtime fallback.

From the repository root with Java 21 and Node available:

```powershell
.\gradlew.bat :common:operations:test :backend:web-api:test :backend:background-worker:test :backend:web-api:bootJar :backend:background-worker:bootJar
Push-Location .\clients\web
npm ci
npm test
npm run lint
Pop-Location
.\scripts\build-operations-web.ps1
```

For the complete Java check **including the non-skipped Azure adapter contract**:

```powershell
npm ci --prefix .\infrastructure\local
.\scripts\test-operational-workflows.ps1
```

Use PowerShell 7 and Java 21. The script allocates loopback ports, generates
temporary test-only credentials, verifies emulator readiness and cleans up its
own emulator process/data; no cloud credentials or deployment are involved.

The build script selects `VITE_OPERATIONS_ONLY=true`. For local React development,
the existing application exposes the portal at `/operations`. Tokens stay in
page memory, not local storage. Use API access tokens for the configured audience,
not identity tokens. Local sign-out clears protected state and suppresses late
responses; it neither revokes an identity-provider token nor cancels an already
submitted command.

Build container images from the repository root, after building both JARs and
the operational web bundle:

```powershell
docker build -f .\backend\web-api\Dockerfile -t YOUR_REGISTRY/web-api:YOUR_TAG .
docker build -f .\backend\background-worker\Dockerfile -t YOUR_REGISTRY/background-worker:YOUR_TAG .
docker build -f .\clients\web\Dockerfile.operations -t YOUR_REGISTRY/operations-web:YOUR_TAG .\clients\web
```

The dependency-free manufacturing Dockerfile/start script is unchanged.
When operational mode is enabled, the API denies the unauthenticated prototype
namespace. Prototype simulation never supplies accepted business evidence.

## Storage and Configuration

| Setting | Meaning |
|---------|---------|
| `OPERATIONS_ENABLED` | API defaults to false; explicitly enable operational mode |
| `OPERATIONS_PROVIDER` | Only `azure`; an unknown provider fails rather than substituting storage |
| `OPERATIONS_BUSINESS_ID` | Stable operating-business ID; pilot supports one business per runtime |
| `OPERATIONS_TABLE_ENDPOINT`, `OPERATIONS_BLOB_ENDPOINT`, `OPERATIONS_QUEUE_ENDPOINT` | Explicit HTTPS cloud endpoints |
| `OPERATIONS_JWT_ISSUER`, `OPERATIONS_JWT_JWKS`, `OPERATIONS_JWT_AUDIENCE` | Verified OIDC issuer/keys and API audience |
| `OPERATIONS_BOOTSTRAP_PRINCIPAL`, `OPERATIONS_BOOTSTRAP_EXPIRES_AT` | Approved initial human SHA-256 principal and future UTC expiry; bootstrap creates ADMIN only |
| `OPERATIONS_WORKER_PRINCIPAL` | Stable named SERVICE principal, initially `operations-worker` |
| `OPERATIONS_HEALTH_GROUP` | Operational API: `readinessState,operationalStorage` |
| `APP_ENVIRONMENT` | Low-cardinality deployment environment, not customer identity |
| `OPERATIONS_CONNECTION_STRING` | Loopback emulator only, with explicit `local` Spring profile |
| `OPERATIONS_CREATE_LOCAL_RESOURCES` | Local API-only test provisioning; never cloud provisioning |

`OperationalRecords` uses partition `<businessId>!ops-v1`. Heads, histories,
grants, receipts, contexts, outbox and initial audit views are in that same
partition. Every supported transaction guards the hot `F!scope` row and all
loaded dependencies. This is a deliberate low-throughput consistency choice,
not automatic sharding or unlimited scalability.

New asset registration also creates `assetcare` (the active-work claim) and
`assetqualifiedfrom` (the actual qualification start). An older pre-review
local database lacks these prerequisites and must be reconciled or recreated
before qualifying assets; missing records fail closed, not as invented history.

Documents carry schema version 1 and at most **24 KiB UTF-8 JSON**. A commit
allows **90 mutations / 512 KiB**, including audit and dependency guards.
BigDecimal values persist and travel as decimal strings. Supply strings for
decimals/large integers inside portal JSON fields; unsafe numeric literals are
rejected. Provider ETags and domain revisions are distinct.

Evidence uses private `operational-evidence` blobs and exact hash/version
references. Cloud Blob versioning is required. Identical immutable-put retries
are accepted; changed content is a conflict. Upload does **not** scan a file:
it remains `UPLOADED_UNVERIFIED`. A QUALITY actor must record an actual approved
scan/review evidence reference, exact hash and reason before linking/downloading.
Unverified downloads are denied. Approved downloads are attachment/octet-stream
with `nosniff`. Application append-only/version-pinned data is not certified WORM
or protection against every privileged credential compromise.

## Identity and Workflow Order

Human API principals are lowercase SHA-256 of the exact verified
`issuer + newline + subject`, UTF-8. Do not hash a display name, email address,
client-supplied tenant or customer ID.

```powershell
.\scripts\get-operational-identifiers.ps1 -BusinessId shop -Issuer https://issuer.example -Subject opaque-subject
```

The helper also derives the exact business-scoped queue name:
`ops-work-` plus the first 32 SHA-256 characters of the business ID.

Bootstrap ADMIN can manage access, not automatically execute domain commands.
Assign qualified people explicit `INVENTORY`, `MAINTENANCE`, `QUALITY`,
`COMMERCIAL` and `RETURNS` roles. Register the named worker as a SERVICE with
`WORKER`, a future expiry and an approved reason. Cloud identity/RBAC does not
grant these business roles. Customer administrators can grant only their own
customer's CUSTOMER/CUSTOMER_ADMIN roles. The pilot supports one customer
affiliation per grant and BUSINESS/CUSTOMER/ORDER_LINE beneficiaries only.

Use the portal catalog and typed controller DTOs as the wire contract:

1. Verify an account; choose represented customer if applicable, beneficiary
   and purpose. Create approved grants/customer/order references as needed.
2. Upload actual evidence and record its qualified review. Receive stock in
   quarantine, qualify, reserve and issue into WIP; movement requests do not
   change actual custody until confirmation.
3. Register actual asset capability validity, approved care plan/baseline and
   meter readings. Record actual care tasks/evidence, then separately release.
   Current eligibility can expire after a historical successful release.
4. Establish inspection and required gate plans. Numeric manual inspection
   supports `INTERVAL_CONTAINMENT` / `SINGLE_WORKPIECE`: quantity-one WIP in
   `pieces` with an exact reservation, or a confirmed single-item return.
   A passing sample cannot accept a batch. Earlier passes cannot clear later
   failures; causal reinspection and current dependency revisions are required.
5. Record owned benchmark artifacts/manual quotes. Customer-scoped geometry
   is rejected by this library; no automatic outbound customer-file sharing.
6. Record verified order/window, request RMA, receive and confirm exact package
   contents, perform actual return inspection and approve supported remedy
   allocation. No financial or carrier execution occurs.

Keep the command's idempotency key **and exact unchanged body** when an outcome
is unknown. Retry that same intent; using its key for different intent conflicts.
Do not manufacture a replacement command to guess whether the first committed.

## Local Azure Emulator

`infrastructure/local/package.json` pins Azurite 3.35.0. Run `npm ci` there, then
launch its `node_modules/.bin/azurite.cmd` on **loopback only**, using private
temporary data and a randomly generated test account key in `AZURITE_ACCOUNTS`.
Supply matching explicit Blob/Queue/Table endpoints and connection string to
the API in the `local` Spring profile. The API may initialize local resources;
the worker does not. Do not commit keys/settings or reuse real cloud account
keys. Cloud credentials use Kubernetes federated workload identity only.

The adapter contract runs when all four private environment values are set:
`OPS_TEST_CONNECTION_STRING`, `OPS_TEST_TABLE_ENDPOINT`,
`OPS_TEST_BLOB_ENDPOINT`, `OPS_TEST_QUEUE_ENDPOINT`. Without them it is skipped,
not proof of provider conformance. It checks real SDK conditional rollback,
paging, restart persistence, immutable blob retries and queue pointer delivery.
Azurite cannot qualify Azure identity, private networking or Blob versioning.

## Azure and Kubernetes Deployment Preparation

Nothing in this implementation provisions a subscription or cluster. Review
costs/permissions first; do not run deployment commands on an unapproved cluster.

`infrastructure/azure/operations-storage.bicep` creates separate operational and
diagnostic StorageV2 accounts, private endpoints, versioned evidence, table,
queue, three managed identities and exact service-account federations. Supply
an existing private-endpoint subnet and DNS zone IDs keyed `blob`, `queue`,
`table`; those zones must already resolve from the Kubernetes network.
Choose lowercase/alphanumeric name prefix (3-8 characters), LRS/ZRS, evidence/
diagnostic soft-delete windows and the helper's exact queue name explicitly.
No AKS, Grafana, PVC backing account, protected backup account or price promise
is included. Built-in data roles are scoped to the table/container/queue;
they include deletion rights that need privileged-access review.

Use an existing cluster with OIDC/workload identity and an enforcing network
policy implementation. Fill **every `REPLACE_*`** in the Kubernetes examples,
pin built application image digests and approved upstream digests, and review
server-side dry runs before applying anything. Select an approved operational
node pool; label it `ahhe-operational-logs=true`. All supplied workloads and the
agent select those nodes. The root agent can read node pod logs and write its
dedicated spool: it is a trusted node-level component, not a customer sandbox.

Create configuration maps named `loki-config`, `otel-agent-config` and
`otel-gateway-config` from their respective YAML files in
`infrastructure/kubernetes/operations`. ConfigMap file keys must be
`loki.yaml`, `collector-agent.yaml`, `collector-gateway.yaml`.
Then use `workloads.yaml`, `logging-workloads.yaml`, `network-policy.yaml`.
Ingress requires existing approved TLS/controller/hostname; label its namespace
`ahhe-ingress=true`. Label the independent monitoring namespace
`ahhe-monitoring=true`; authorize only reviewed operators/proxies there.
The example permits outbound HTTPS; replace that with approved identity/private
endpoint allowlists before production and qualify DNS/probe behavior.

Initialize API authority before enabling the worker. After bootstrap, remove
bootstrap values from runtime configuration; restart deliberately when changing
environment ConfigMaps. API readiness checks the open initialized storage scope;
worker readiness reports failed/uncompleted cycles rather than just an HTTP
port. These checks do not certify physical availability, all IAM permissions,
evidence scanners or recovery. Kubelet liveness remains independent of a storage
outage so it does not create a dependency-driven restart storm.

## Logging, Query and Independent Alerts

Loki **3.7.9** uses TSDB v13 on private Azure Blob with federated identity.
OTel contrib **0.164.0** converts CRI/application JSON, bounds memory and uses
durable exporter queues. Source offsets and agent queues share the dedicated
node-local spool; losing/retiring that node can lose buffered diagnostics.
Gateway and Loki use separate per-writer PVCs. Qualify locking, fsync, restart,
mount permissions and latency on the selected Azure Files storage class;
record a justified disk exception if needed. Never share one WAL across writers.

The example is a **single-writer, non-HA pilot**. Do not simply scale Loki replicas
with an in-memory ring/replication factor one. Default 30-day retention,
2 MiB/s rate, 4 MiB burst, 20 GiB Loki and 5 GiB gateway PVC requests are review
inputs, not measured capacity, approved policy or a bounded total-cost estimate.
Gateway queue entries are bounded; overflow and pod-log rotation can cause
diagnostic loss. Business audit remains transactional in Table, outside Loki.

Only `service.name` and `deployment.environment.name` become Loki index labels.
Operation IDs/actions remain structured metadata, not per-order/customer/user
streams. The collector allowlists structured attributes and redacts recognized
Bearer patterns; this is **not complete PII/secret redaction**. Producers must
never log tokens, signed URLs, payloads, customer geometry or financial secrets;
review third-party/error logs before production.

Loki's `auth_enabled` means a tenant header is required, **not authentication**.
The gateway fixes `X-Scope-OrgID: operations-pilot`; it is not user-controlled.
Loki/OTLP have no public ingress. Use an existing authenticated staff Grafana
server-side datasource/proxy at `http://loki.operations.svc:3100`, with that same
fixed tenant header. Never expose Loki directly or give customers arbitrary
tenant headers. Query a bounded time range, for example
`{service_name="web-api", deployment_environment_name="pilot"}` and filter
structured `operation_id` for correlation.

Scrape API `:8080/actuator/prometheus`, worker `:8081/actuator/prometheus`,
collector `:8888/metrics` and Loki `:3100/metrics` through independent authorized
monitoring. The supplied policy authorizes the reviewed Grafana pod for Loki;
add an equally narrow metrics-scraper rule if it is a different pod.
Configure alerts outside Loki for failed readiness, worker failures/lag,
collector refusal/send failures/full queues and PVC capacity, unavailable Loki
and absent expected traffic. An alert route/dashboard is not deployed here.

## Production Acceptance Gate

Before production: approve scope/qualified operators and domain policies,
customer identity onboarding, region/redundancy/budget, retention/loss allowance,
separate protected backups and RPO/RTO. Prove a consistent checkpoint restore,
evidence hash/version integrity, outbox replay/fencing and no duplicate external
effects. Test Azure token renewal/RBAC/private DNS, crash/WAL recovery, log
ingestion/loss, authenticated query isolation and independent alerts on real
infrastructure. Run component licensing and vulnerability review (Loki is
AGPLv3). AWS adapters/cutover remain unimplemented and require the same semantic
contracts; portability is not a connection-string swap.
