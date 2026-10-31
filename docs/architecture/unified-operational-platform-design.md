# Unified Operational Platform: Azure-First Storage and Logging

## Review Status and Recommendation

**Status:** Proposed for user review; design only. Implementation, deployment,
spending, and final operating policies require approval.

**Approved logging decision (2026-10-31):** The user selected Grafana Loki
backed by Azure Blob Storage. Logging product selection is settled; deployment
sizing, security, retention, recovery and the broader platform design are not.

**Date:** 2026-10-31. **Repository baseline:** `68b1cfb73143e90f35526715252070f698641fcb`
(main after #12).

Build backlog items **1-5** as domains inside the existing Java API and background
worker, with one shared durable operational store, evidence store, identity/
authorization boundary, and diagnostics pipeline. Use Azure Storage services
first; keep cloud SDKs behind explicit capability contracts. Run compute on
Kubernetes, initially AKS. Do not create five microservices or five asset/quality
registries.

| Layer | Recommended starting design | Why / limitation |
|-------|-----------------------------|------------------|
| Business records | Azure **Table Storage**, not Cosmos DB for Table | Conditional writes and same-partition transactions; no relational joins, secondary indexes, or cross-partition transaction promise. |
| Documents and evidence | Private immutable application-managed **block blobs** | CAD, certificates, photos, measurements, procedures, quote evidence; metadata and permissions remain in the operational store. |
| Asynchronous work | Azure **Queue Storage** backed by a transactional outbox | Cheap work notifications; duplicate/out-of-order delivery is normal, not business truth. |
| Logs | OpenTelemetry-compatible collection -> **Grafana Loki TSDB on Azure Blob Storage**; Grafana UI proposed for staff | User-approved logging backend. Continuously running compute and WAL storage are real costs; qualify deployment before production. |
| Recovery | Separate protected Blob backup account plus consistent application checkpoints | Redundancy is not backup; Table records, evidence, configuration, and replay state all matter. |
| Temporary persistent buffers | Per-writer Kubernetes PVCs; evaluate Azure Files, allow a justified disk exception | Blob/Queue cannot replace a filesystem WAL. Test filesystem semantics, restart, performance, and cost for the selected logging release. |
| Cloud boundary | Narrow Java ports + Azure adapters; AWS target S3/DynamoDB/SQS | Preserves domain semantics, not drop-in SDK/API compatibility. Migration and conformance tests are necessary. |

The most consequential compromise is **one operational transaction partition per
organization for the initial pilot**. This makes cross-domain quantity, hold,
inspection, and remedy checks atomic without inventing distributed transactions.
It deliberately limits write scalability. Splitting a live organization across
partitions is a new design/migration decision, not an automatic load-balancing
setting. If measured scale or required cross-site behavior makes this unacceptable,
approve a transactional database exception rather than silently weaken correctness.

## Scope and Relationship to Earlier Work

| Backlog item | Unified domain | Initial implementation slice after approval | Full requirement still to finish |
|--------------|----------------|-------------------------------------------|----------------------------------|
| 1 | Inventory/asset/custody/WIP | Controlled receipts, movements, reservations, stock split/remnants, WIP handoffs, corrections, quarantine | Tool components/use/refurbishment, qualification, time-phased demand, projected shortages and integrations |
| 2 | Machine care | Approved plans, calendar/usage triggers, manual work, holds, verification/release | Condition/event triggers, projected service conflicts, resource demand, shared-support propagation and integrations |
| 3 | Quality/inspection | Manual revisioned characteristics/checkpoints, observations, containment, dependent and final release | Automatic checkpoint placement, approved sampling, qualified metrology integration and impact analysis |
| 4 | Benchmark/market quotes | Owned library, manual quotes, explicit scope differences and reviewer adjustments | Authorized provider adapters, repeated comparisons and approved normalization methods |
| 5 | Returns/remedies | Customer/order-line foundation, RMA/package records, eligibility allocation, quarantined receiving, inspection and approved remedy | Selected carrier/QR/freight and financial execution adapters; full customer status experience |

This design covers the architecture and delivery path for all five, not a claim
that the initial slice implements every requirement. The underlying
[ranked requirements](../../requirements/software-development-backlog.md) remain
authoritative. CNC CAM, mechanics, physical machine control, autonomous inspection,
full ERP/accounting, and a general-purpose hierarchy engine are not included.

The earlier logging proposal is still open in
[PR #8](https://github.com/mmcadamsms/ah-he/pull/8). Its
[requirement at the reviewed commit](https://github.com/mmcadamsms/ah-he/blob/4789140c6de84bb462cf944f4aee25ee6d8ebe25/requirements/functional-areas/observability/low-cost-azure-blob-logging.md)
compares OpenObserve, Loki, Quickwit, and Parquet/DuckDB, including Iceberg/Delta
publication. This proposal carries forward low-cost commodity storage, correlation,
bounded buffers, queries near the data, and independent recovery. The user
selected Loki on Azure Blob for the ongoing support/query need of items 1-5.
It does not merge #8, accept ADR 0007, or secretly build a second logging lake.
Reconcile or supersede that proposal explicitly after this design is agreed.

## Runtime and Dependency Structure

```text
Web staff/customer UI
        |
OIDC identity + scoped authorization
        |
Java web-api: domain commands, validation, authoritative reads
        |
OperationalStore / EvidenceStore / WorkTransport ports
        |
Azure Tables         Azure Blob evidence         Azure Queues
        |                                           |
        +---- committed outbox / workflow state ----+
                                                    |
                      Java background-worker: dispatcher, reconciliation,
                      due evaluation, scans, projections, external adapters

API / worker / geometry-worker structured console logs
        |
Kubernetes collection agent -> OTel gateway -> Loki -> Blob chunks + index
                                                |
                                      authenticated Grafana queries
```

Domain/application code must not import Azure SDK types, expose provider URLs,
or derive business identity from a blob path. Keep the geometry worker unchanged
unless an approved integration requires it. Shared Java contracts/adapters belong
in `common/` only when API and worker actually consume them; follow
[ADR 0005](decisions/0005-organize-code-by-runtime-boundary.md). The background
worker currently has no application implementation; its README is not an
already functioning durable processor.

## Shared Business Model

Every record has organization, stable opaque ID, schema version, aggregate
revision, created/recorded time, applicable effective/event time, and attributable
actor. Organization and customer are distinct: an operating organization can
serve many customers. Actor identity comes from verified credentials; requester,
performer, confirmer, approver, device, and service roles remain separate.

Every business operation also carries explicit beneficiary context. Actor,
representation, beneficiary, subject and ownership are independent concepts;
neither a user/customer identity nor an order ID can stand in for all of them.

| Shared object | Required information / ownership |
|---------------|----------------------------------|
| Customer / order / line | Verified/imported source, revision, customer access grant, part/revision, fulfilled/eligible quantity, monetary breakdown/currency, payment references and policy references; no payment secrets |
| Principal / affiliation / authority | Stable human/service/device identity, verified credential mapping, business/customer membership, scoped role/delegation/service authority and effective revisions |
| Operation context | Executing/requesting/approving principals, represented party/authority, primary/additional beneficiaries, purpose, affected subjects, causal operation and validation revisions |
| Part / job / operation | Drawing/model revision, ordered route and scope, planned versus actual state, original production references; simulated jobs cannot silently become actual production |
| Controlled item / lot / asset | Category, ownership, units, lot/serial, condition, confirmed location/custody, qualification, reservations, genealogy |
| Location / handling unit | Hierarchy, contents, origin/destination, requested/confirmed/accepted handoff and safety restrictions |
| Care plan / work order | Approved applicability/procedure revision, trigger semantics, baseline/meter history, tasks, actual work, consumed resources, post-service checks and releases |
| Characteristic / checkpoint / result | Revisioned limits, units, access/dependency, method/gauge/calibration, raw observation, uncertainty, sampling scope, decision, nonconformance and reinspection |
| Benchmark / quote | Ownership/permissions, immutable file revision, provider/terms/access, quantity/scope/prices/lead time/expiration, source artifact, explicit adjustment and reviewer |
| RMA / package / remedy | Order-line allocation, package sequence, credentials/execution facts, receipt/quarantine, identity, inspection, responsibility, itemized calculation and remaining remedy allocation |
| Hold / release | Subject, scope, reason, dependency versions, approver, expiry, evidence; release is a command decision, never a Boolean copied from a log |
| Artifact | Owner/access class, media type/size/hash, scan state, immutable provider object version, lifecycle/hold status, linked records |
| Command receipt / change event / outbox | Idempotency digest and result, changed revisions, immutable actor/representation/beneficiary context, transactional post-images or reconstructible change set, consumer work state |

Use exact decimal quantities and money with explicit unit/currency and approved
rounding/conversion rules, not binary floating-point balances. Store provider-
neutral decimal representations in Tables; do not coerce arbitrary precision
into its limited scalar types. Observations preserve the submitted precision,
units, and original evidence. Unknown is a state, not zero or accepted.

### Actor, Representation, and Beneficiary Context

The user identified both sides as required participants in all business
operations. Use **beneficiary** for the receiving side, matching the examples
of customer, customer order and shop/business. The field expresses purpose or
intended benefit, not necessarily who pays.

| Context dimension | Meaning | Example |
|-------------------|---------|---------|
| Operating business | Data/operational boundary | The shop operating the system |
| Actor | Who actually requested/performed/approved this step | Customer-provisioned user, internal employee, named worker service or device |
| Affiliation | Actor's verified membership/ownership | Employee of shop; invited member of customer account |
| Represented party / authority | For whom the actor is permitted to act and under what grant | Employee processes a customer return under internal business authority |
| Beneficiary | Who/what the action serves | Customer, order/line/job, or the shared business capability |
| Subject | What is changed/observed/accessed | Stock unit, compressor, measured bore, quote, package |
| Ownership / payer / allocation | Separate property or economic facts | Customer-owned stock; business-paid freight; approved shared overhead |

Proposed versioned `OperationContext` fields:

```text
contextId, schemaVersion, businessId, operationId, action, purpose
executingPrincipalId, initiatingPrincipalId, actorRoleReferences
representedPartyId (when applicable), authorityId/revision
primaryBeneficiary { kind, id }, additionalBeneficiaries[]
subjectReferences[], relationship/authority validation revisions
causedByOperationId, recordedAt, occurredAt, allocationReference (optional)
```

Required principals resolve through authenticated mappings; requester/performer/
confirmer/approver remain separate. A customer's administrator can provision
users and bounded roles only inside that verified customer account. An internal
employee serving a customer remains an employee, using a checked internal grant;
the system does not pretend the employee logged in as the customer's user.
Scheduled work has a stable executing service/device principal and explicit
business mandate, not an anonymous `SYSTEM` actor.

Require a primary typed beneficiary and purpose for every business operation;
allow additional validated beneficiaries. Initial kinds are business, customer,
order, order line and job, with checked parent links. Business-level work serves
shared capability without enumerating all customers or inventing an order.
Beneficiary lists are bounded by the existing command limits. Resource ownership
and action permissions still govern each affected subject.

| Workflow | Actor / representation example | Beneficiary / subject example |
|----------|--------------------------------|-------------------------------|
| 1: Stock/WIP | Employee or receiving device acts under inventory authority | Order/job benefits; actual stock/WIP is the subject; customer ownership remains separate |
| 2: Care | Maintainer performs and qualified reviewer releases; scheduler has its own principal | Business/shared capability benefits; compressor is subject, not a fictitious customer |
| 3: Inspection | Inspector measures; independent approver where required | Validated order/job benefits; part/characteristic is subject; benefiting customer cannot self-accept without authority |
| 4: Benchmarks | Authorized commercial reviewer | Business market knowledge benefits; owned benchmark/quote is subject |
| 5: Returns | Customer-provisioned user requests; employee inspects; authorized executor handles remedy | Verified customer/order/line benefits; RMA/package is subject; payer/refund method follows separate policy |
| Platform work | Named backup/reconciliation service | Business continuity benefits; checkpoint/work state is subject |

For unidentified receiving, use the real business beneficiary of quarantine
and record unresolved customer/order association. That is an explicit receiving
purpose, not a fallback permitting refunds. Mixed/shared customer work retains
authorized internal attribution while each customer's view hides other
customers. Intended benefit never grants access, consent, monetary liability or
an automatic cost split.

The API validates this context rather than trusting a client-supplied actor,
on-behalf-of party or beneficiary. Guard current authority and parent-link
revisions with committing effects, including authoritative customer membership/
role grants stored in the operational scope. Identity-provider credential
validity is checked at request/worker execution; it cannot be made atomic with
a Table transaction. Locally authoritative grant revocations participate in
commit guards; external revocation latency/session lifetime needs an explicit
security policy, not an instant cross-system revocation claim.

Persist the validated snapshot with the command/change/work/approval; heads
reference their creating/latest operation rather than overwriting all historical
actor context. Queue envelopes carry an operation/work reference and safe trace
context, not credentials or complete personal/beneficiary lists. Workers load
the committed context, keep the originating actor/causation, record their own
executing principal and recheck action authority. Automatic follow-up is permitted
only by explicit service policy, not by inheriting a former user's privilege.

Command digests include representation and beneficiary intent; replay cannot
change either silently. Reassociation/corrections create new history while
retaining prior context and then-valid authority versions. Imported performer
claims retain provenance/verification status separate from the authenticated
importing principal. Read/search purpose can be business- or customer-scoped
without manufacturing a mutation per returned row; sensitive read/export audit
follows its approved policy.

Beneficiary/actor views are rebuildable keyed projections, never authorization
or release authority. Loki receives safe opaque operation/context references
and low-cardinality business/domain classification, not personal principal
details, grant tokens or customer beneficiary labels. Backups and AWS migration
preserve context, authority references and corrections.

The [operation-context requirement](../../requirements/functional-areas/platform-foundation/actor-and-beneficiary-operation-context.md)
defines acceptance cases for customer provisioning, internal representation,
shared work, invalid/revoked grants, replay, unknown receiving and privacy.
This refines the data/authorization design without approving implementation.

## Storage Layout and Atomic Boundary

Provision separate environments and separate data classes; account names and
regions are deployment configuration, not domain contracts.

| Resource | Logical contents | Isolation / lifecycle |
|----------|------------------|-----------------------|
| `OperationalRecords` table | All authoritative mutable heads, append-only change rows, command receipts, outbox, pins, policies for items 1-5 | Partition `orgId!ops-v1`; application-enforced customer/role scope |
| `OperationalViews` table | Rebuildable customer lists, due dates, item location/status, lookup aliases, reporting summaries | Partition/query-key design by access pattern; not authoritative for release or allocation |
| `PlatformControl` table | Organization directory, schema/capability versions, reconciliation and backup runs | Small control plane; not transactionally coupled to business commits |
| Evidence Blob containers | Staging, verified attachments, exports | Separate customer/organization access checks, retention classes, no public containers |
| Work queues | Dispatch, scan, notification and external-integration wakeups | Pointer envelopes, not evidence/financial payloads |
| Diagnostics account | Loki chunks/index and engine-managed state | Independent retention, quota and privileges; may start LRS if loss risk is approved |
| Backup account | Checkpoints, change suffixes, referenced artifact copies and verification manifests | Separate deletion authority; approved retention/immutability where applicable |

Azure Tables transactions require the same table and PartitionKey, with at most
100 entities and 4 MiB total; individual entities also have limits [S1, S2].
Proposal: impose a conservative **64 KiB serialized record** and **90 mutations /
512 KiB commit** application limit, including audit, receipt, outbox, and
dependency fences. These are reviewable portability/test limits, not Azure
service limits or observed workload figures. Larger objects go to Blob; larger
business changes become explicitly staged work, not batches falsely presented
as one transaction. Reject an unsupported atomic command before mutation.

Example RowKey families (encode/validate IDs; `!` is a separator, not path syntax):

```text
H!inventory!<id>         authoritative item head
H!asset!<id>             qualification/hold authority
H!orderline!<id>         return/remedy allocation authority
H!inspection!<id>        measured result/revision
X!<operation-id>         immutable validated actor/beneficiary operation context
E!<aggregate-id>!<rev>   immutable change with transaction ID
C!<command-id>           durable command digest/result
O!<work-id>              pending/blocked/completed consumer obligations
P!<artifact-id>          authoritative reference pin/lifecycle guard
A!<source-kind>!<id>     create-only authoritative external identity binding
F!scope                  write-fence authority for checkpoints/cutover
```

No tenant-global business sequence on every write. Revisions are aggregate-local;
one transaction ID connects multi-aggregate changes. Do not infer a total order
across aggregates from wall-clock timestamps. The `F!scope` checkpoint guard
does participate in every business/work/lifecycle commit in the pilot: a Table
conditional replacement enforces its open/fenced state and epoch. This is a
**hot shared row** and can cause conflicts even for unrelated writes. Include
it in the contention/cost spike and mutation budget; low-volume correctness is
the deliberate tradeoff, not a claim of unlimited partition throughput.
Domain revisions do not advance when only a provider fence/ETag is refreshed.
Append-only is enforced by the
application's conditional insert and privileges; Table Storage alone is not
WORM protection. Protected audit exports and restricted administrative actions
provide a separate tamper/recovery boundary.

### Authoritative Command Protocol

1. Authenticate, resolve operating organization and principal affiliations,
   validate actor/representation/beneficiary/subject context and current action
   authority, then validate schema/policy. Client context is requested intent,
   not trusted identity or permission.
2. Require stable `Idempotency-Key` for mutating commands. Persist its caller/
   action/scope binding, canonical request digest including representation/
   beneficiary intent, target, and result in the same
   operational partition. A reused key with different intent is a conflict.
3. Read authoritative heads and all prerequisites, not dashboard projections.
   Evaluate exact quantity, qualification, evidence, policy, clock/expiry, and
   applicable version rules.
4. Submit one conditional partition transaction: guard the open scope fence,
   mutate affected heads, fence
   dependency heads including applicable authority/relationship guards, insert
   validated operation context, audit/change rows, command result, and outbox work.
   All read dependencies influencing a release/allocation must be included
   with expected-version guards. In Tables, a conditional replacement of the
   relevant head can serve as that guard even if its business fields do not
   change; version handling must not falsely invalidate unchanged evidence.
5. On conflict re-read and revalidate within bounded retries; never blind
   overwrite. On timeout the outcome is unknown: resolve the command receipt,
   or return retryable pending/unknown with the same key. Do not report success
   from an exception or allocate again under a new key.
6. Return committed business state. Worker failure afterward does not undo or
   conceal the commit; pending external actions remain explicitly pending.

An inventory split atomically consumes the parent, creates bounded children,
records quantities/genealogy, and writes audit. A reservation compares current
quantity/qualification, not a stale availability view. A release transaction
guards job, evidence, gauge/asset qualifications, procedure/policy versions and
holds together. Large dependency sets exceeding the cap are unresolved and need
a reviewed bounded plan, not an unguarded release.

Clock-based validity must be checked at actual software operation release,
not only by a background due job. Return the decision's evaluated time and
valid-until bound; never issue a long-lived general “machine available” pass.
Subsequent operation commands recheck current holds, meter freshness, and limits.
This cannot stop a physically running machine; independent safe execution
procedures/controllers remain mandatory.

Cross-organization transactions and distributed cross-partition commits are
not supported. The pilot can record cross-site movements inside its organization
partition, but shared ownership/transactions between organizations require
explicit pending custody and reconciliation, not atomic claims. Maintain
homed order-line authority and no duplicate external order-source binding.
Create source/order aliases and unique human-readable RMA bindings as
create-only authoritative rows in the same commit as their business records.
A lookup projection is not a uniqueness constraint. Immutable policy and
procedure versions are referenced by ID; selecting which version is currently
applicable must guard the authoritative policy head as well.

## Outbox, Queues, and Recovery of Work

Queues are at-least-once wakeups. Storage Queue messages have a 64 KB limit and
an explicit TTL; omitted TTL defaults to seven days [S3]. Use a small versioned
envelope with organization, work ID, schema, correlation/trace context and no
credentials; select TTL deliberately. Persist business deadlines in Tables.

The dispatcher scans `O!` ranges in known organization partitions with bounded
paging, a recurring full reconciliation cycle, backoff, and persisted progress.
An asynchronously maintained “pending work” view accelerates discovery but is
not the only way committed work can be found. An incomplete scan must resume;
do not permanently advance past work simply because its next-attempt time is
in the future.

| Failure window | Required behavior |
|----------------|-------------------|
| Commit succeeds, queue send never happens | Outbox reconciliation sends the committed work later. |
| Queue send succeeds, producer cannot record it | Resend is safe; consumer processes the same logical work ID. |
| Duplicate/out-of-order dequeue | Consumer inbox/work state and expected aggregate revisions prevent duplicate effects; superseded work is explicitly recorded. |
| Worker dies during processing | Renew visibility while working; another worker may retry after timeout. Conditional work ownership and commit guards, not the receipt timeout, protect correctness. |
| Work commit succeeds, queue delete fails | Replay sees durable completion and only acknowledges. |
| TTL expires / message is lost after publication | Outbox retains incomplete consumer obligations and periodically re-enqueues; queue publication alone is not completion. |
| Poison work / unsupported schema | Persist blocked work/error and poison reference, alert, stop automatic retry storm; authorized repair/redrive preserves work ID. |
| Notification/provider response is ambiguous | Record pending/unknown execution and reconcile using stable provider key/reference; do not assume success or resend financial effects blindly. |

Consumers acknowledge obligations in durable work state with guarded updates;
cleanup requires all required consumers completed or explicitly disposed.
Consumer business effects, receipt, and completion share the operational atomic
boundary. Visibility/pop receipts are provider-opaque [S4]; expose them only as
transport handles. There is no exactly-once transport claim.

External payment/carrier actions cannot participate in a Table transaction.
Reserve eligible quantity/remedy allocation before dispatch. Use a durable
execution intent, selected provider idempotency where supported, and reference/
status reconciliation. If the provider cannot resolve an ambiguous result,
require qualified manual reconciliation. Cancellation/expiry cannot free an
allocation while execution is unresolved. Store neither card data nor banking
credentials in our business records.

## Blob Evidence Protocol and Data Protection

Upload into a staging object with size/media/access limits. The application
authorizes each request; any direct-upload grant is short-lived, one-object and
least privilege. Do not persist SAS/signed URLs as artifact identity or put them
in logs, labels, queues, or audit payloads.

Verify length/hash and scan outcome, then create a final immutable object/
version under a unique logical artifact ID. Persist metadata and lifecycle state.
Direct-upload grants target staging only; final objects are written by a
restricted service with create-only/version-aware semantics. Quarantined or
malicious content is not published as downloadable verified evidence.
Linking evidence atomically creates/updates a reference pin with the consuming
business record; a missing/unverified/unscanned object cannot satisfy a required
checkpoint. Never overwrite the original failing measurement or accepted quote
source with a “corrected” file.

Tables and Blob are not one transaction. Use a visible pending workflow:
uploaded but unlinked objects may be garbage collected only after a retention
window and a conditional lifecycle tombstone. Pin creation and GC eligibility
checks share the organization partition; new pins fail once deletion is staged.
The deletion worker revalidates tombstone/version and all pins before deleting
the exact object version. Failed deletions stay pending; retries cannot delete a
replacement object. Cross-organization artifact sharing is initially copy-on-
import, not uncontrolled references into another tenant's lifecycle.

Use separate policies for diagnostics, business records, evidence and backups.
Customer confidentiality, legal hold, export constraints, malware, permitted
quote-file use and approved retention are explicit metadata/authorization rules.
Do not tier current release evidence into Archive or apply generic log deletion
to inspection history. Log engines own their chunk/index deletion protocol;
external lifecycle rules must not break a live Loki index.

## Domain Rules and User/API Experience

Use the existing REST envelope/error conventions. New routes are proposals,
not changes to the current demonstration API:

| Domain | Proposed resources/commands | Important guarded behavior |
|--------|----------------------------|----------------------------|
| Inventory | `/api/v1/inventory-items`, `/movements`, `/reservations`, `/transformations`, `/assets` | Confirmed custody vs requested movement; balanced split/consume; no negative quantity; quarantined/unqualified resources excluded |
| Care | `/api/v1/maintenance-plans`, `/maintenance-work-orders`, `/asset-meter-readings` | Applicable approved plans; exact due boundary/time zone; stale/reset meter is unknown, partial work cannot reset baseline |
| Inspection | `/api/v1/inspection-plans`, `/inspection-results`, `/nonconformances`, `/operation-releases` | Raw actual observations, calibrated method/sampling scope, all required dependencies and reinspection; M00/demo completion not acceptance |
| Benchmarks | `/api/v1/benchmarks`, `/market-quotes`, `/quote-comparisons` | Ownership/terms, raw price vs adjustments/internal cost, mismatch/expiration visible, no unauthorized scraping or external uploads |
| Returns | `/api/v1/orders`, `/returns`, `/return-packages`, `/remedies` | Exact customer/order line, remaining quantity, credential and execution facts, quarantine, itemized remedy and duplicate prevention |
| Shared | `/api/v1/artifacts`, `/work-items`, scoped `/audit-events` | Access-controlled evidence/status/history, retryability and pending states |

Use POST action subresources for approvals/releases/transitions rather than
allowing clients to PATCH themselves to accepted/completed. Require expected
business revision; conflicts are 409, explicit unresolved prerequisites 422,
authorization failures 401/403, transient unavailability 503 with retry context.
Large background actions return 202 with a durable work resource. Pagination:
bounded continuation tokens for Table access patterns; page/size may be supported
only where a materialized view supplies it. Do not invent cheap arbitrary sort,
accurate global total counts, or relational join behavior.

Views include `asOf`, source revisions, and pending/reconciliation status.
Authorization is checked again on every list/detail/artifact operation, never
only during indexing. Customer views exclude internal quote/cost/audit details.
UI should distinguish pending, stale, unknown, held, rejected, actual and
predicted; no optimistic display of a financial/quality release before commit.

### Workflow and Policy Coverage

| Flow | State/control requirements |
|------|----------------------------|
| Inventory | Receipt/identified -> qualified or quarantine; reservation -> issue/consume or cancelled; requested movement -> executed/confirmed -> acceptance where required. Corrections append reason/approval and balanced effects. |
| Care | Planned/due -> in progress -> interrupted/awaiting parts/failed or completed-pending-verification -> released. Baseline advances only on approved applicable completion; advisory/mandatory task and bounded deferral stay distinct. |
| Inspection | Not inspected -> pending review -> accepted/nonconforming/inconclusive or approved not-applicable. Nonconformance -> contained -> disposition/rework -> required reinspection. Final release is separately evaluated. |
| Quotes | Owned/authorized revision -> recorded raw quote -> comparable or unusable/review-required; adjustment revisions preserve raw facts. Expired quotes remain history, not current usable comparisons. |
| Returns | Requested/under review/additional information -> authorized or rejected; credentials issued -> pickup/transit/delivery -> quarantine -> identified -> inspection -> remedy approved -> financial pending or repair/replacement in progress -> completed. Cancelled/expired/unidentified branches preserve allocations and execution uncertainty. |

Policies are revisioned data approved by appropriate staff, not hardcoded
invented thresholds. Required before the corresponding workflow: care plan/
meter fallback, inspection decision/sampling rules, return windows/eligibility/
fees/refund arithmetic, currency/quantity conversion, and authority matrix.
Default behavior for missing policy is explicit unresolved/held, not automatic
acceptance. Carrier printerless QR and freight variants depend on the selected
provider; record capability gaps instead of promising universal support.

Minimal customer/order import is required for item 5, not a full ERP. Imported
records carry source identity and verified revisions and are idempotent. Validate
ownership, fulfilled quantity, prior returns/remedies/disputes and financial
components before authorization. Source conflicts hold review; an unverified
manual order cannot justify money movement.

## Portable Storage Contracts

These are proposed responsibilities, not code or an all-purpose ORM.

| Port | Required semantics | Azure first | AWS target |
|------|--------------------|-------------|------------|
| `OperationalStore` | Authoritative scoped reads; bounded conditional atomic commit with expected versions, create-only rows, durable receipts/change/outbox; explicit conflict/unknown outcome | Table entity-group transaction and ETags | DynamoDB consistent reads/conditional transaction; application receipts survive the provider's short idempotency window |
| `OperationalViewStore` | Bounded keyed queries, provider-opaque cursors, projection checkpoints/rebuild | Table keyed views | DynamoDB keyed views; do not rely on eventually consistent GSI for gates |
| `EvidenceStore` | Put immutable object, stat/verify exact version, stream/range read, authorized temporary grants, protected deletion | Blob | S3; validate conditions/version/retention semantics |
| `WorkTransport` | Send pointer, receive/renew/ack visibility handle; at-least-once, no global ordering | Storage Queue | SQS Standard; durable application dedupe remains required |
| `WorkloadIdentity` / deployment configuration | Obtain credentials and provider-specific clients outside domain code | Entra workload identity | EKS workload identity/roles; subject/permission migration required |
| Log collection/storage configuration | OTel model and existing engine-supported connector | Loki Azure backend | Loki S3 backend; not Java repository adapters |

Startup checks provider capabilities against the application's contract. Refuse
an incompatible provider/configuration instead of quietly degrading to non-atomic
CRUD. AWS transactions permit different partition scopes and have different item
limits, but domain logic continues using the declared narrower operational
boundary [S5]. Receipts must outlive provider retry tokens; financial history is
not deduplicated only for ten minutes. Object retention/immutability APIs are
provider-specific admin capabilities, not portable booleans.

For an AWS move: stop/fence source business writes, quiesce/drain external
execution, create a consistent checkpoint, copy referenced objects and hashes,
import canonical records/receipts/allocations/outbox, rebuild views, run balance/
gate/access/replay tests, switch identity/endpoints, and then resume a single
writer authority. No casual dual-writing. After destination writes begin,
rollback requires a reconciled reverse migration, not pointing DNS at stale data.
Loki data/index/schema and WAL recovery need their own supported migration/
restore procedure; changing the bucket alone does not migrate log history.

## Unified Software Logging

### Event Contract and Collection

All server runtimes emit structured diagnostics with OTel-compatible meanings
[S7]. Standard fields: event/observed timestamps, severity, service/version/
environment, event name, body, request/correlation ID, trace/span IDs, safe
organization/domain/aggregate/work/command references, error code, duration,
and schema version. Trace context propagates through HTTP and queued work,
but client-supplied context is not authorization. Generate missing IDs.

Java uses the existing logging ecosystem with structured encoding; Python uses
a matching adapter. Kubernetes agents collect stdout once, parse multiline
exceptions safely, enrich trusted deployment metadata, and send OTel logs through
a restricted gateway to Loki's supported OTLP HTTP endpoint [S8]. Do not enable
duplicate SDK and stdout ingestion for the same record by default. Local
prototype/PowerShell logs can later use the same schema and optional authenticated
agent without changing mail state or making a disconnected desktop depend on
cloud diagnostics.

Never log tokens, signed URLs, carrier labels, addresses, payment details, email/
feedback bodies, CAD, raw measurement files, or unbounded customer input. Use
allowlisted structured fields, redacted exceptions, length/rate limits, and
untrusted-input escaping. Correlate to protected records by opaque ID. Diagnostic
retention and sampling are separate from the durable business change history.

### Engine Choice, Buffering, and Query Limits

Use the user-selected Grafana Loki with TSDB on Azure Blob. Pin and qualify
the deployment release; the Grafana staff UI remains part of the proposed
design. Index only low-cardinality environment/service/domain
labels; keep request, customer, item, trace, pod-instance and work IDs as
structured metadata, not high-cardinality labels. Keep Loki private behind an
authenticated, authorized ingestion/query gateway: the engine is not the
application's customer authentication system. Strip externally supplied tenant
headers and derive any Loki tenant routing from trusted deployment/service
identity. Staff query scope is enforced at the gateway/Grafana boundary; no
direct customer log access. Require time/service bounds,
query concurrency/scan budgets, and visible freshness. Loki does not provide a
universal arbitrary-text/JSON-field inverted index [S9].

The alternatives below preserve the selection rationale; they are not an
invitation to replace the approved backend without further user agreement.

| Option | Assessment / deployment qualification |
|--------|-----------------------------|
| **Loki on Azure Blob - selected** | User-approved backend. Measure steady compute, WAL/PVCs, retention, restart and chosen-release identity support before production. |
| OpenObserve | Alternative if structured SQL/UI materially improves the actual workflow; evaluate metadata/WAL recovery, HA dependencies, supported identity and all-in cost before selection. |
| Archive-only Blob + occasional query | Lower idle compute candidate, not a complete interactive logging service. Existing exporter/format/query handling still needs validation; do not build a custom ingestion/catalog platform just to minimize the storage bill. |
| Quickwit / indexed search | Consider only if measured full-text/field needs justify index amplification and metadata/compute dependencies. |
| Separate Parquet lake | Deferred unless long-retention analytics earns its duplicate pipeline. Evaluate existing engine export plus Iceberg/Delta rather than inventing concurrent snapshot publication. |

OTel Collector's Azure Blob exporter currently reports **alpha** stability [S10].
Fluent Bit's documented Azure Blob output currently lists key/SAS authentication
[S11]. Neither is silently assumed to provide a production-grade workload-
identity archive pipeline. If Loki's chosen release cannot satisfy identity/
security requirements, resolve an explicit connector change or tightly scoped
credential exception at review, not a shared account key hidden in code.

Buffer nonempty batches approximately one minute or to an approved size limit;
benchmark actual collector and engine behavior. Collector persistent queues and
Loki WAL require separate per-writer volumes and tested restart recovery [S12,
S13]. Prefer Azure Files if its CSI/access mode/fsync/locking/throughput behavior
passes the selected release's tests; otherwise document a small Azure Disk PVC
exception with its cost and failure profile. Do not share a WAL directory or put
it only in `emptyDir`. Local buffers can still be lost during node/disk failure;
diagnostic loss bounds require approval.

Limit telemetry memory/disk and service impact. Apply approved low-severity
shedding/backpressure with counters/alerts; inability to persist required
**business audit** instead blocks the business commit. Loki WAL corruption/
disk-full behavior and Collector queue/retry exhaustion can cause diagnostic
loss [S12, S13]; never treat a log acknowledgement as a business durability receipt.

An independent health path reports failed business writes, outbox age/blocked
work, evidence scan failures, backup age, log drops/WAL failures, and query
freshness. Scraped metrics plus a selected alert destination must work without
successful ingestion into Loki. Metrics/trace backends beyond this minimal
health function are not silently added as a full observability platform.

## Kubernetes, Security, and Operations

Stateless API replicas and separate background-worker replicas share cloud data,
not process memory. Configure worker concurrency by work class, visibility
renewal, resource limits, jittered retries, and graceful drain. Repeated due jobs
use stable generation keys and compare approved plan/meter revisions.
Kubernetes CronJobs may trigger reconciliation/backup, but duplicated/missed
ticks do not authorize or complete work.

Private workloads have separate service accounts and least-privilege cloud data
roles via AKS/Entra workload identity [S14, S15]. The API, business worker, log
writer, evidence scanner, backup writer and retention administrator do not share
an all-powerful credential. Staff/customer OIDC authentication is separate from
pod identity. Proposed initial staff provider is Entra; customer identity provider/
onboarding is a review decision. Issuer/audience validation, local principal
mapping and scoped policies prevent identity-provider choice from entering
inventory logic.

Suggested permission classes: inventory operator, maintainer, quality reviewer/
release authority, commercial reviewer, return receiver, financial executor,
customer-own-order reader/requester, and platform operator. Approve the matrix
and separation-of-duty rules before enabling writes. Log read access does not
grant customer-evidence read access; platform admin cannot impersonate a
measurement performer or silently waive prohibited holds.

Private endpoints/network restrictions where supported, TLS, encryption at
rest, deployment secret management, ingress authentication, rate limits, image/
dependency pinning, backups and audit of administrative mutations are required.
Pricing and connectivity consequences of private networking must be costed.
Artifacts remain untrusted data; scanning is not permission to execute them.
No deployment grants software physical servicing, zone-entry, or machine-start
authority.

## Backup, Restore, and Cost

Proposed pilot baseline: **ZRS for operational data/evidence where supported**,
independent protected backup; **LRS may be acceptable for diagnostic data** with
an explicit loss budget. Region/sku support and price must be verified. These
are recommendations, not purchases. All services in one storage account share
its redundancy choice [S16], so separate accounts are needed for different
business/log risk classes.

Table backup is application-managed here; Blob soft delete/versioning does not
back up Table rows. Create a tested checkpoint by fencing organization writes
with a guarded scope-control version in every committing command, pausing work/
external dispatch, then exporting heads, change history, receipts, allocations,
outbox, pins and policies while stable. Copy/verify all referenced object
versions and an immutable manifest before declaring success. Resume writes only
after the fence protocol completes; timed-out/recovering coordinators cannot
silently unlock a newer backup fence. An initial checkpoint may pause writes;
online checkpoints need a separately proven log/snapshot protocol.

Subsequent change exports improve recovery only after completion is verified;
their lag is the application RPO. Preserve source changes until confirmed
protected copy. Restores rebuild views, verify quantities and evidence, retain
dedupe/external references, and reconcile provider effects before reenabling
dispatch. A stale backup may omit a refund that actually happened; “replay every
pending payment” is unsafe. Production release remains blocked during recovery.

Agree numerical RPO/RTO, retention and deletion/legal-hold policies before
production. Geo-redundancy may asynchronously lose recent writes during regional
failover; it is neither zero-RPO nor backup. No active-active multi-region writers
are proposed.

Cost model must include:

```text
Table bytes + read/write/batch operations + index/projection amplification
+ Blob evidence/log/backup bytes + requests + retrieval/egress
+ Queue sends/receives/renewals/empty polls and duplicate retry traffic
+ Kubernetes API/worker/log/query/scan CPU-memory + cluster/network baseline
+ PVC capacity/IOPS/transactions + private endpoints + redundancy/backup copies
+ engineering/support/upgrade/restore effort
```

Measure rows/command (including fences/audit/outbox), conflict rate, events/
second and bytes/event, artifact mix, polling cost, query concurrency/history,
WAL/recovery window and dominant partition. No claim that Azure Storage is
always lowest total cost, that Table transactions are free, or that existing
Kubernetes capacity is free. A hot organization partition is an acceptance gate,
not a problem to fix by discarding atomicity.

## Implementation Sequence and Verification Gates

Only start this sequence after design agreement.

| Stage | Deliverable | Mandatory verification |
|-------|-------------|------------------------|
| 0 | Agree remaining design, initial org/workload, authority/policy and retention/recovery targets; spike Table atomic contracts and qualify selected Loki identity/PVC/cost | Competing writes, timeout-after-commit, dependency fencing, size limits; log ingestion/restart/drop/security tests. Block unqualified deployments; backend changes require review. |
| 1 | Shared ports/Azure adapters, OIDC/access, durable command/change/outbox, evidence lifecycle, basic telemetry and recovery tooling | Azurite/local contract tests plus real Azure identity/private-network/failure tests; idempotency across restarts; tenant isolation; complete checkpoint/restore. |
| 2 | Inventory/custody/WIP and asset foundation | Concurrent reservation, balanced split/remnant, accepted handoff, unknown/quarantine, preserved corrections and replay; then tooling/demand slices. |
| 3 | Care and inspection/release | MC/IP acceptance cases; date/DST/meter gaps, support outages, interruption, raw failure retention, sampling and gate invalidation with concurrent changes. |
| 4 | Owned benchmarks/manual market quotes | Permission/terms, immutable revisions, mismatched/expired quote, explicit raw vs normalized/internal prices, authorized artifact access. |
| 5 | Verified customer/order import and internal RMA/receiving/remedy allocation | Remaining eligible quantity under concurrency, unmatched quarantine, exact customer visibility, no duplicate remedy, itemized calculation and ambiguous-execution recovery. |
| 6 | Selected carrier/QR/freight/payment adapters and remaining domain automation | Sandbox/provider idempotency/reconciliation, cancellation races, return/financial status truth; full linked requirement acceptance without production-control claims. |
| 7 | AWS proof and scale/recovery qualification | Same semantic suite on AWS adapter, export/import hash and balance checks, rebuilt projections, no double dispatch after cutover; acceptance before saying “swappable.” |

Design/documentation checks are not execution of these tests. No infrastructure,
schemas, Java ports, migrations, policy engine, dashboards or provider adapter has
been implemented by this proposal.

## Decisions Requested at Review

| Decision | Recommendation | Consequence of changing it |
|----------|----------------|---------------------------|
| Initial consistency scope | One organization operational partition; low-volume pilot before scale expansion | Sharded writes need a different transaction/workflow design or a database exception. |
| Durable storage | Table/Blob/Queue first, no SQL/Cosmos/Service Bus by default | A transactional DB may reduce application engineering for larger relational/cross-scope needs; evaluate total cost rather than forbid it. |
| Logging | **Approved: Grafana Loki on Azure Blob.** OTel collection and Grafana UI are proposed integration details; sizing/retention/recovery remain open. | Replacing the selected backend requires further agreement; no custom lake or mandatory Azure Monitor indexed ingestion is implied. |
| WAL filesystem | Azure Files if proven; explicit Azure Disk exception otherwise | Insisting on only Blob/Table/Queue cannot meet filesystem WAL needs without replacing components. |
| Identity / roles | Entra staff OIDC, attributable devices/services, separately selected customer onboarding | Production customer return workflow waits for authenticated order ownership. |
| Workflow depth | Incremental manual evidence first, then selected external adapters and complete remaining rules | First slices are not completion of the full 1-5 requirements. |
| Risk/cost targets | Set volume, region, retention, RPO/RTO, allowed diagnostic loss, budget, policy owners before production | No honest production durability, savings, or performance claim can precede these. |

## References

Sources checked 2026-10-31; connector maturity/configuration must be rechecked for
the pinned implementation release.

- [Shared requirements](../../requirements/functional-areas/platform-foundation/unified-operations-storage-and-logging.md).
- [Proposed ADR 0009](decisions/0009-azure-first-portable-operational-platform.md).
- [Design/review prompt](../../requirements/prompts/deploying/20261031-unified-operations-storage-design.md).
- [S1: Azure Table entity-group transaction limits](https://learn.microsoft.com/en-us/rest/api/storageservices/performing-entity-group-transactions).
- [S2: Table data model and entity/property limits](https://learn.microsoft.com/en-us/rest/api/storageservices/understanding-the-table-service-data-model).
- [Table partition/index design](https://learn.microsoft.com/en-us/azure/storage/tables/table-storage-design).
- [S3: Queue size and TTL](https://learn.microsoft.com/en-us/azure/storage/queues/storage-queues-introduction).
- [S4: Visibility, dequeue count and pop receipts](https://learn.microsoft.com/en-us/rest/api/storageservices/get-messages).
- [S5: DynamoDB transactions, limits and idempotency window](https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/transaction-apis.html).
- [SQS duplicate delivery](https://docs.aws.amazon.com/AWSSimpleQueueService/latest/SQSDeveloperGuide/standard-queues-at-least-once-delivery.html).
- [S7: OpenTelemetry logs data model](https://opentelemetry.io/docs/specs/otel/logs/data-model/).
- [S8: Loki native OTel ingestion and metadata mapping](https://grafana.com/docs/loki/latest/send-data/otel/).
- [S9: Loki TSDB/object storage and index scope](https://grafana.com/docs/loki/latest/configure/storage/).
- [S10: OTel Azure Blob exporter, alpha status and identity configuration](https://github.com/open-telemetry/opentelemetry-collector-contrib/blob/main/exporter/azureblobexporter/README.md).
- [S11: Fluent Bit Azure Blob output](https://docs.fluentbit.io/manual/data-pipeline/outputs/azure_blob).
- [S12: Collector resilience and persistent queues](https://opentelemetry.io/docs/collector/resiliency/).
- [S13: Loki WAL guarantees, loss conditions and persistent volumes](https://grafana.com/docs/loki/latest/operations/storage/wal/).
- [S14: AKS workload identity](https://learn.microsoft.com/en-us/azure/aks/workload-identity-overview).
- [S15: Entra authorization for Azure Tables](https://learn.microsoft.com/en-us/azure/storage/tables/authorize-access-azure-active-directory).
- [S16: Redundancy options and limits](https://learn.microsoft.com/en-us/azure/storage/common/storage-redundancy).
