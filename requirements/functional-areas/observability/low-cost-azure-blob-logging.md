# Low-Cost Blob Logging and Object-Storage Alternatives

**Status:** Proposed for review; no infrastructure or logging implementation is
delivered by this document.

**Origin:** Conversation requesting common JSON service logs, approximately
one-minute VM buffers, concurrent append-blob writers, background conversion to
cheaper storage, efficient time/field queries, and selectable LRS/ZRS/GRS.
The follow-up explicitly invites better log-storage approaches built on blob or
commodity cloud storage; append blobs and a custom logging service are not fixed
requirements.

**Traceability:** [ADR 0007](../../../docs/architecture/decisions/0007-blob-first-operational-logging.md)
and [design prompt](../../prompts/deploying/20261018-observability-blob-logging-design.md).

## 1. Problem and Objective

We need inexpensive operational logs without committing to an always-on search
cluster or paying indexed-ingestion prices for every diagnostic event. Cheap
storage alone is insufficient: tiny files, transactions, compaction, repeated
scans, recovery copies, and operator time can dominate the bill.

The objective is **low-cost diagnostics backed by commodity object storage**:
a common event contract, recoverable ingestion, and useful queries. A Parquet
lake with on-demand compute is one candidate; an existing log engine may be a
better complete solution. Optimize total cost for the actual workload, not only
the storage price per GB.

### Short answer to the original questions

| Question | Answer |
|----------|--------|
| Can many VMs append to one blob? | Yes, but application-level duplicate handling, rollover, and contention management are still needed. A single unbounded global blob is not the recommended default. [S1] |
| Can an append blob become a cheaper-tier block blob? | Not in place. Write a **new block blob**, validate and publish it, then retire the source. Append blobs cannot have an individual access tier set; they infer the account default tier for billing. [S1-S3] |
| Does a timestamp in the filename make JSON field queries fast? | It helps identify candidate objects. It does not index the JSON records inside them. The query must actually restrict paths/files, not merely filter timestamps after opening the whole dataset. |
| Must everything stream back to my PC? | No. Run the query engine near storage and return results. With Parquet, the engine can fetch selected column chunks/row groups rather than every byte, subject to format statistics, query shape, and reader behavior. [S4-S7] |
| Can this be interactive? | Bounded time/service queries are plausible candidates. Arbitrary message searches over months may still scan extensively. No latency promise is justified before benchmarking. |
| Do ZRS and GRS provide backup? | They provide redundancy, not historical recovery from all deletions/corruption. Backup, soft delete, versioning, and immutability address different risks. [S9-S11] |

## 2. Proposed Starting Point

**Evaluate an existing object-storage-native log engine before building a custom
logging platform.** OpenObserve, Loki, and Quickwit address different query
patterns and already implement substantial ingestion/query/maintenance machinery.
Their compute floor may cost more than occasional batch queries, but avoiding
custom platform engineering may still make them cheaper overall.

For the **occasional-query DIY baseline**, prefer immutable per-writer NDJSON
block batches over a globally shared append blob. Flush nonempty batches
approximately every 60 seconds or at a size limit; compact into time-partitioned,
typed Parquet block blobs; query on demand near storage. Evaluate Iceberg or Delta
Lake for safe table publication before implementing a custom catalog. Compare
DuckDB with managed serverless SQL when a shared endpoint is useful.

Keep **sharded append blobs** as a credible alternative to benchmark, not an
assumption that must survive unfavorable cost or reliability results. Both
approaches can use the same event contract and curated Parquet layout.

### Object-storage-native alternatives

These are different layers, not interchangeable competing file extensions:
**Blob/S3/GCS** store objects; **Parquet** encodes columnar data;
**Iceberg/Delta Lake** manage tables and snapshots over data files; and
**Loki/OpenObserve/Quickwit** provide ingestion and log-query engines.

| Candidate | What lives in commodity storage | Best reason to consider it | Cost/operational tradeoff |
|-----------|---------------------------------|----------------------------|--------------------------|
| Plain Parquet + DuckDB | Compressed columns plus a small published file catalog. | Infrequent, time-bounded SQL investigations with no permanent query cluster. | We own ingestion, publication, deduplication, retention, recovery, and the query interface. Lowest fixed cloud cost is a hypothesis, not lowest engineering cost. |
| Parquet + Iceberg | Data files plus manifests, snapshots, and table metadata. | Safe atomic table changes, concurrent writers, late data, and multiple analytical readers without inventing those protocols. [S15] | A compatible catalog/commit mechanism and maintenance jobs still need ownership; not a log UI, full-text index, or query engine. |
| Parquet + Delta Lake | Parquet data plus the Delta transaction log/checkpoints. | Transactional lake tables and compaction/evolution through an existing ecosystem. Azure storage support is documented. [S16] | Reader/writer protocol, feature, connector, and cleanup compatibility must be tested; not automatically a requirement for an always-on Spark cluster or a paid platform. |
| Grafana Loki | Compressed log chunks and a label/TSDB index, including on Azure Blob. | Service/environment/time-scoped log browsing, especially with Grafana and a disciplined low-cardinality label model. [S17] | The primary index is for labels, not a universal message/JSON-field inverted index. Broad content searches can still scan chunks. Ingest/query/WAL/cache/compactor resources must be costed. |
| OpenObserve | Parquet log data in object storage, with metadata/file lists and ingestion state managed by the product. | An existing SQL-oriented observability/query UI rather than building our own Parquet logging service. Azure Blob is listed as a backend. [S18] | Single-node mode uses local SQLite/state; HA adds coordination, metadata, and node roles. Back up metadata and account for the unflushed WAL window; object durability alone does not protect every acknowledged event. |
| Quickwit | Search-index splits containing inverted indexes, fast fields, and stored documents; Azure Blob is supported. | Frequent selective full-text or indexed-field searches that plain Parquet/label indexing handles poorly. [S19] | Indexing compute/storage amplification, search workers/cache, and metadata persistence. Clustered deployment uses PostgreSQL metadata. Supported Azure authentication needs explicit review. |

**Practical shortlist:** benchmark OpenObserve for ready-made structured-log SQL,
Loki if service/time browsing and Grafana are the primary workflow, and the
Parquet/DuckDB baseline for minimal idle infrastructure. Add Quickwit to the
trial if repeated full-text or selective-field searches are a core requirement.
No product is declared the winner without the same ingestion/query/recovery
workload and an all-in cost comparison.

For the DIY lane, evaluate **Iceberg/Delta before a custom transaction/catalog
implementation**. Iceberg documents atomic snapshot commits and safe file
replacement; DuckDB documents Iceberg reads over Azure storage and
catalog-managed writes, subject to its supported feature set. [S15, S20]
A format-aware reader must honor snapshots and row-level deletion features:
wildcard-reading the underlying Parquet files can return obsolete/deleted data.
Do not assume every Synapse/DuckDB version reads every table protocol feature.

An existing log engine should normally receive batches through its supported
ingestion interface and manage its own blobs. Do **not** force it through our
append-to-Parquet pipeline or build two complete ingestion systems by default.
Loki chunks and Quickwit indexes are not interchangeable with a plain Parquet
lake; OpenObserve using Parquet does not make arbitrary direct bucket scans a
supported consistent view. Respect each engine's metadata and retention APIs.

A hybrid is also reasonable: short-retention interactive logs plus long-retention
Parquet for rare investigations. It must earn its extra copies, export pipeline,
and reconciliation costs. Direct-object ingestion/query alone does not provide
the low-latency alerts that a running log engine can support.

### Azure versus other commodity object stores

Keep Azure as the first deployment candidate, not a required data-format lock-in.
The cited Loki, OpenObserve, and Quickwit documentation lists other object
backends; test the exact chosen release/connector and identity mechanism.
Azure Blob is not simply an S3 endpoint, and S3-compatible providers can differ
in conditional-write, listing, checksum, multipart, and deletion behavior.

Compare **storage + requests + retrieval + egress + query compute + operations**.
Moving a cheap bucket to another cloud while leaving the query engine on Azure
can lose any capacity savings to data transfer and latency. Open formats improve
portability; they do not make identities, redundancy, backup, or query plans
portable without work. License/edition limits, maintained releases, security
updates, and restore support are evaluation gates for every shortlisted engine.

The detailed pipeline below is the **DIY comparison baseline and required
behavior**, not a requirement to reimplement components already supplied by a
chosen engine.

```text
Services / VM instances
        |
Common event contract + bounded local disk spool
        |
        +-- Preferred candidate: immutable minute-batch NDJSON block blobs
        |
        +-- Alternative: time-bounded, sharded NDJSON append blobs
                          |
             Bounded background compaction
             validate / deduplicate / sort
                          |
             New compressed Parquet block blobs
                          |
             Validate -> publish catalog generation
                          |
             On-demand same-region SQL engine
                          |
                  Results to operator
```

This is a proposal for backend service diagnostics. It does not replace business
transactions, approval history, manufacturing traceability, regulated audit
records, metrics, or real-time alerting. Those need their own durability and
latency contracts. A small independent health/alert channel must report logging
pipeline failures; storing all failure signals in the broken pipeline is unsafe.

## 3. Terminology and Inputs

| Term | Meaning |
|------|---------|
| Event time | When the producer says the event occurred; UTC, potentially skewed. |
| Observed time | When the logging adapter observed the event; not necessarily cloud arrival. |
| Storage arrival time | Storage-side creation time of a committed raw object; distinct from both producer timestamps. |
| Batch | Immutable payload with a stable batch ID, checksum, and event count. |
| Landing/raw | Recovery and troubleshooting representation, normally NDJSON. |
| Curated | Validated, deduplicated, typed Parquet published for normal queries. |
| Catalog generation | An immutable manifest of exactly which curated files form a queryable snapshot. |
| RPO / RTO | Acceptable data-loss interval / time to restore useful service. |

Sizing inputs still required: active writers, events/second, event-size
distribution, burst factor, daily GB, retention, query frequency/concurrency,
common predicates, freshness needs, region, currency/agreement pricing, and
acceptable RPO/RTO. No values in the examples below are measured repository
workload facts.

## 4. Common JSON Event Contract

Align semantics with the OpenTelemetry Logs Data Model rather than inventing
incompatible meanings for timestamps, severity, or trace context. This is a
proposed storage envelope, **not OTLP JSON wire encoding**. A later implementation
must supply versioned machine-readable validation and adapter mappings. [S12]

```json
{
  "schema_version": 1,
  "event_id": "baf15c09-3138-40b8-aaf7-6a67998a8725",
  "event_time_utc": "2026-10-19T05:00:00.123Z",
  "observed_time_utc": "2026-10-19T05:00:00.124Z",
  "service": "web-api",
  "environment": "development",
  "instance_id": "web-api-01",
  "producer_epoch": "fd2c044c-641e-4d13-89dc-5f5433056e32",
  "sequence": 42,
  "severity_text": "INFO",
  "severity_number": 9,
  "event_name": "approval.request.accepted",
  "message": "Request accepted",
  "trace_id": "4bf92f3577b34da6a3ce929d0e0e4736",
  "span_id": "00f067aa0ba902b7",
  "attributes": {
    "duration_ms": 12,
    "outcome": "accepted"
  }
}
```

| Fields | Proposed contract |
|--------|-------------------|
| `schema_version` | Required integer; an incompatible change requires a new version and explicit migration. |
| `event_id` | Required producer-generated UUID, assigned once and preserved on every retry/replay. |
| `event_time_utc`, `observed_time_utc` | Required RFC 3339 UTC strings in raw JSON; typed UTC timestamps in Parquet. Clock anomalies are flagged, not silently rewritten. |
| `service`, `environment` | Required bounded, allowlisted strings; suitable low-cardinality partition keys. |
| `instance_id`, `producer_epoch`, `sequence` | Required producer identity, startup UUID, and monotonically increasing integer within that epoch. Useful for gap diagnostics, not a global ordering guarantee. |
| `severity_text`, `severity_number` | Required string/integer with a documented OpenTelemetry mapping. |
| `event_name`, `message` | Required bounded strings; event name is stable machine-readable classification, message is human-readable. |
| `trace_id`, `span_id` | Optional nonzero W3C trace identifiers: 32/16 hexadecimal characters; span ID requires trace ID. Not all events belong to a trace. |
| `attributes` | Optional bounded object with allowlisted keys and scalar string/number/boolean values. Frequently filtered attributes become typed curated columns. |
| Exception details | Optional bounded type/message/stack fields under a versioned extension; redact before upload. |

Each actual NDJSON event is serialized on **one UTF-8 line**; embedded message
newlines are escaped. The displayed JSON is pretty-printed only for review.
Absence of an optional field means unknown/not applicable, not an empty-string
sentinel. Preserve the original schema version through conversion.

Do not log secrets, credentials, authorization headers, customer document
contents, or personal data by default. Redact at the producer before data reaches
the spool. Proposed limits are 64 KiB per serialized event and 32 custom
attributes; truncation must be explicitly marked and counted, never invisible.
Unknown/incompatible versions or malformed events go to an access-controlled,
bounded quarantine with an error signal rather than being silently discarded.

## 5. Ingestion: Append Versus Immutable Block Batches

| Concern | Shared/sharded append blobs | Immutable per-writer block batches |
|---------|-----------------------------|-----------------------------------|
| Concurrent writers | Append operations support multiple writers; no useful global event-time order follows. | Writers own distinct object names; no shared append offset. |
| Ambiguous network result | Retrying can append the same events again. Multiwriter offset conditions alone do not identify whose write succeeded. | Retry the same name with conditional creation; if it already exists, verify batch identity/checksum before acknowledging it. |
| Object count | Fewer raw objects. | Potentially many small objects until compaction and cleanup. |
| Write count | One append per submitted batch, plus creation/rotation and any split batches. | Potentially one upload per small batch, plus extra operations when multipart upload/verification is required. |
| Individual tier changes | Not supported; account-default inference is not per-object tiering. | Supported for block blobs, subject to tier rules and charges. |
| Compaction boundary | Must stop/fence writes to the source before treating it as closed. | Each successfully committed immutable object is a natural boundary. |
| Failure coordination | Shard ownership, generation changes, delayed writers, duplicate retries, and rollover. | Stable batch names, durable spool state, discovery, and duplicate retries. |

**Sharing a blob does not combine separately submitted requests into one billed
write.** With 100 continuously active writers submitting one batch/minute for a
30-day month, either layout starts near **4,320,000 batch submissions**, before
other transactions. Append reduces object count, not automatically request count.

The current Append Block REST reference documents a **50,000-append limit per
blob**. Its 100 MiB append size for service version 2022-11-02 and later is labeled
**Preview**; earlier versions allow 4 MiB. Do not base the design on the preview
limit without verifying the selected API/SDK and service support. [S1]

| Writers sharing one blob, one append/minute each | Appends/hour | Time to 50,000 appends |
|------------------------------------------------|--------------|-----------------------|
| 10 | 600 | About 83 hours |
| 100 | 6,000 | About 8 hours 20 minutes |
| 1,000 | 60,000 | About 50 minutes |

These are count-only ceilings, not throughput guarantees; splitting oversized
batches consumes more appends. Rotate **before** either the append-count or byte
limit. Use a bounded set of shards per environment/service/time window, never one
forever-growing cross-service blob.

An append variant must define a coordinator-owned generation/rollover protocol.
Delayed writers retry into an open generation with the original event IDs; a
closed generation must reject further writes. A timer or changed filename alone
does not fence old writers. Compaction must not read a changing source and then
delete it as though it were complete. Application-level deduplication remains
necessary. [S1]

### Buffering, acknowledgement, and restart

A memory-only minute buffer can lose up to approximately that minute on process
failure, and more during an outage. Storage redundancy cannot recover data never
uploaded. The proposed default is an encrypted/OS-protected bounded local disk
spool, stable event/batch IDs, exponential backoff with jitter, and replay after
restart. Local disk loss still loses unuploaded events; critical audit events
require a different durable ingestion contract.

Remove a spooled batch only after confirmed cloud commit, or verification that
the already-existing immutable object has the same identity and checksum. An
existing name with different contents is an explicit integrity failure.

Do not block normal business requests indefinitely on the logging service.
Proposed overload behavior is to shed DEBUG/TRACE first, record dropped counts
through the independent health channel, and expose spool exhaustion explicitly.
This lossy operational-log policy must never be inherited by mandatory audit
records. Exact spool capacity and outage tolerance need workload approval.

## 6. Storage Layout and Parquet Conversion

Illustrative **Azure object keys**, not local filesystem paths:

```text
raw/v1/environment=development/service=web-api/batch_date=2026-10-19/<producer-epoch>/<batch-id>.ndjson
curated/v1/environment=development/service=web-api/event_date=2026-10-19/event_hour=05/<generation>-<part>.parquet
catalog/v1/environment=development/service=web-api/generations/<generation>.json
catalog/v1/environment=development/service=web-api/current.json
quarantine/v1/environment=development/service=web-api/<batch-id>.ndjson
```

Raw batch dates describe producer batching, **not completeness by event time or
storage arrival**. Offline replay can create an old-named object today. Curated
partitions use event time. Record storage arrival and event min/max separately in
processing metadata. Do not assume that scanning only today's raw prefix finds
all newly uploaded data.

Start with service/environment/date partitions; benchmark hourly partitions for
active services versus daily partitions at low volume. Do not partition by
trace ID, user ID, or individual instance. These create excessive cardinality.
Keep high-cardinality values as columns. Use UTC and half-open time intervals
`[start, end)` so adjacent windows do not overlap.

Parquet conversion is a **read/validate/transform/write-new-object** operation,
not renaming JSON, changing a blob-type property, or simply changing the file
extension. Materialize timestamp, severity, event name, trace ID, and common
filter attributes as typed columns; retain bounded uncommon attributes for
occasional inspection. Sort within partitions primarily by event time to improve
time-range row-group statistics. Benchmark Zstandard versus Snappy and inspect
the actual reader's support. [S2, S4, S7]

Candidate tuning values, not commitments: poll/compact approximately every
15 minutes, target 128-256 MiB compressed files when volume supports it, and start
row-group experiments near 100,000 rows. Publish smaller files after the maximum
batching delay at low volume; never wait days for a size target. The compactor
must spill or split bounded work rather than load a day's logs into memory.

### Recoverable publication and deletion

This is a correctness contract. Prefer established table-format transactions
where they fit; the illustrated custom catalog is an option to cost and validate,
not a mandate to build a new lakehouse protocol. With Iceberg/Delta, use the
format's metadata layout, transactions, readers, and snapshot cleanup instead.

1. Discover committed raw inputs and record immutable names, ETags, batch IDs,
   counts, and checksums. For v0, periodically enumerate retained raw prefixes
   and compare with a durable processed-input ledger, including old batch dates.
   Bound each scan with pagination/checkpoints and reconcile again after it
   finishes. Notifications can accelerate discovery later, but are not the only
   recovery mechanism.
2. A single compactor owner per service/environment selects inputs and affected
   event-time partitions. Validate records, deduplicate replayed event IDs against
   the affected published partitions, and rebuild those partitions as necessary.
   Conflicting duplicate contents are integrity errors, not last-write-wins.
3. Write uniquely named, immutable Parquet outputs. Reopen/validate schema,
   checksums, event counts, duplicate counts, quarantine counts, and time bounds.
   Account for every input record as accepted, duplicate, or quarantined.
4. Write an immutable catalog generation containing the complete curated file
   set, replaced-file list, processed-input ledger/checkpoint, and accounting.
   Publish its pointer using an ETag compare-and-swap or equivalent fenced
   ownership. Losing ownership means stop and reconcile, not overwrite.
5. Readers pin one generation and read **only its listed files**. Normal v0
   queries are curated-only and disclose last successful compaction, catalog
   generation, and known backlog. Recent raw inspection is separate; do not
   blindly union raw and curated records and count them twice.
6. Delete processed raw sources only after successful publication, the recovery
   retention interval, and required backup completion. Garbage-collect abandoned
   outputs separately. Keep replaced files/catalog generations long enough for
   the maximum supported pinned-query lifetime; reject expired snapshots.

There is no multi-blob transaction merely because the objects share a folder.
An ordinary wildcard over all generation files is **not** a substitute for the
catalog: it could include abandoned, replaced, or not-yet-published outputs.
The pointer is the commit boundary. Readers must never observe half of a
compaction or lose their input files while a valid pinned query is running.

Late arrivals reopen the relevant retained event-time partition; they do not
get assigned today's event time. Older-than-retention or implausibly future
events require an explicit quarantine/review policy. A catalog's last-compacted
timestamp is not proof that no older event can arrive later.

## 7. How Queries Avoid Reading Everything

Blob Storage is not a general JSON-field database index. Put a query engine over
the objects and eliminate work at successive levels:

| Layer | Work avoided | Important limitation |
|-------|--------------|----------------------|
| Explicit time/service file selection | Opening unrelated partitions/files. JSON can benefit from this too. | A broad wildcard can still cause expensive listing before filtering. Use narrow prefixes or catalog file lists. |
| Catalog event min/max metadata | Opening files whose event range cannot intersect the query. | Metadata must include late arrivals and be trusted only after publication. |
| Parquet projection | Reading large message/stack/attribute columns when only time/count/severity are needed. | `SELECT *` forfeits much of the benefit. |
| Row-group min/max statistics | Reading groups that cannot satisfy a range/equality predicate. | Overlapping ranges, missing statistics, or unsuitable predicates reduce pruning. |
| Sorted/clustering-aware layout | Makes time statistics more selective. | Sorting by time does not also provide an efficient global trace-ID or full-text index. |
| Same-region query compute | Shipping scanned data to the operator's PC. | The engine still pays compute and reads relevant bytes; only the result transfer is small. |

The reader uses Parquet metadata and range reads to fetch needed portions;
metadata requests, read-ahead, request latency, and decompression still cost
something. Partition naming alone does not guarantee that an engine performs
partition elimination. DuckDB documents Hive-partition pushdown; Synapse supports
specific paths and `filepath()`/`filename()` filters, with behavior depending on
the access method. Verify the chosen query plans and actual reads. [S4-S8]

**Illustrative arithmetic, not a benchmark:** from 100 GiB of Parquet, selecting
1% of files leaves 1 GiB; reading 10% of their column bytes leaves about 102 MiB;
effective row-group pruning to 10% could leave about 10 MiB, plus metadata and
read-ahead. These fractions are invented, need not be independent, and do not
establish a latency or compression ratio.

| Query | Expected behavior to verify |
|-------|-----------------------------|
| One service, last hour, errors by minute | Strong candidate for interactive use: narrow files, few columns, time pruning. |
| One trace ID within a known hour | Time pruning helps; random trace IDs may still require reading that column across all candidate groups. |
| One trace ID across a year | Not a point lookup. Consider a separately costed trace-to-file index if frequent. |
| Arbitrary substring in all messages for 90 days | Usually a broad message-column scan; Parquet is not a full-text index. |
| Frequently repeated dashboards | Consider bounded result caching or small precomputed aggregates, including their refresh cost. |
| Archived data | Requires rehydration before normal reads; not an interactive query path. |

### Query engine options

| Option | Fit | Costs and limits |
|--------|-----|------------------|
| On-demand DuckDB in-region | Preferred experiment for occasional investigations and bounded workloads; supports Azure storage and Parquet directly. [S4-S6] | Job/VM compute, startup, memory/spill, listing/read operations, retrieval, and any network charges still apply. It is not a free managed multiuser service. |
| Synapse serverless SQL | Managed shared SQL endpoint; benchmark against DuckDB for the real workload. [S7-S8] | Billed data processed includes more than returned rows: metadata, reads, intermediate results, and applicable output. Minimum 10 MB/query, rounded up to MB; storage charges are additional. Budget controls do not terminate an already-running query that exceeds a limit. |
| Blob query acceleration | A possible targeted CSV/JSON filtering tool, not the primary historical-query architecture. [S13] | One file/request, no joins or GROUP BY; billed scanned and returned data. Not an arbitrary-field index and not assumed to be integrated with our query engine. |
| Log-native engine | Evaluate alongside the DIY baseline for interactive browsing, selective searches, alerting, or substantial concurrency; see Section 2. | Indexing and ready-made operational features may justify additional fixed compute and state; measure rather than assume an always-on cluster is affordable or necessary. |

Illustrative DuckDB query against **two files selected by a published catalog**.
The placeholders must be replaced with actual committed file names and an account
the operator can read. This is not a deployed or cloud-executed example:

```sql
INSTALL azure;
LOAD azure;
CREATE SECRET log_reader (
    TYPE azure,
    PROVIDER credential_chain,
    ACCOUNT_NAME 'exampleaccount'
);

SELECT event_name, count(*) AS error_count
FROM read_parquet([
    'az://exampleaccount.blob.core.windows.net/logs/curated/v1/environment=development/service=web-api/event_date=2026-10-19/event_hour=05/g42-part000.parquet',
    'az://exampleaccount.blob.core.windows.net/logs/curated/v1/environment=development/service=web-api/event_date=2026-10-19/event_hour=05/g42-part001.parquet'
], hive_partitioning = true)
WHERE event_time_utc >= TIMESTAMPTZ '2026-10-19 05:00:00+00'
  AND event_time_utc < TIMESTAMPTZ '2026-10-19 06:00:00+00'
  AND severity_number >= 17
GROUP BY event_name
ORDER BY error_count DESC;
```

Prefer managed identity for Azure-hosted jobs and a narrowly authorized developer
identity for interactive access. Do not place connection strings, account keys,
or SAS tokens in the repository. Pin the implementation's engine/extensions and
validate schema mappings instead of relying on inference across changing files.

## 8. Cost Model and Retention

```text
Monthly total =
  retained GB-month by tier and redundancy * applicable storage rates
  + write/read/list/metadata transactions * applicable transaction rates
  + retrieval GB * tier retrieval rates
  + compactor compute + query compute OR managed query data-processed charges
  + network/replication/egress and optional private networking charges
  + pipeline health/alerting costs
  + chosen engine's indexing/query compute, metadata service, cache, and coordination
  + separately tracked engineering, upgrades, on-call, and recovery effort
```

Include raw/curated overlap, indexes, metadata/WAL copies, orphan/replaced-file
grace retention, soft-deleted data, versions, backups, and quarantine in the
appropriate capacity/operation charges above. Include local disks where used;
do not count the same storage or query compute twice.

Use the selected region, redundancy, agreement, and currency in the current Azure
calculator/rate card. The public pricing page is a rate reference, not a quote;
no current dollar total is asserted here. Check current minimum billable object
sizes and tier-specific transaction rules when costing small files. [S3, S14]

For an explicitly hypothetical **10 GB/day raw**, 7-day raw retention, 90-day
curated retention, and an assumed **0.25 curated/raw byte ratio**, steady-state
capacity is roughly **70 GB raw + 225 GB curated = 295 GB**, before versions,
soft-delete retention, manifests, quarantine, backup, and transition overhead.
The ratio must be measured. Storage price multiplied by 295 GB is **not** the
total monthly system cost.

| Data class | Candidate policy for evaluation | Guardrail |
|------------|---------------------------------|-----------|
| Raw batches | Hot; retain 7 days after successful publication, longer while processing/backup is incomplete. | Do not use a blind age-only lifecycle delete that can erase an unprocessed backlog. |
| Recent curated | Hot for the first 7 days. | Query freshness depends on compaction, not upload alone. |
| Older curated | Evaluate Cool from day 7 through day 90. | Cool has a 30-day minimum storage duration; rewrites/deletes/tier changes can incur early deletion charges. |
| Long-lived, rarely read curated | Evaluate Cold only if retained long enough to justify it. | Cold minimum 90 days; retrieval/read costs may outweigh capacity savings. |
| Optional offline archive | Separate retention class and restore workflow. | Archive minimum 180 days, rehydration can take up to 15 hours; current ZRS/GZRS accounts do not support it. [S3] |

The day-7/day-90 example leaves most files in Cool longer than 30 days, but late
rewrites near expiration may still incur penalties. Model those explicitly.
Do not automatically enable every protection feature on disposable diagnostics:
versioning and backups can cost more than the active compressed dataset.

## 9. Redundancy Is Not Backup

| Choice | Failure protection | Tradeoff |
|--------|--------------------|----------|
| LRS | Replicas within a primary-region datacenter; lowest-cost redundancy choice. | A datacenter disaster can lose the dataset. Candidate only where that risk is approved. |
| ZRS | Synchronous replicas across availability zones in the primary region. | Better zone resilience, not protection against all regional loss or logical deletion. |
| GRS | Primary LRS plus asynchronous replication to a paired secondary region. | Regional protection with a nonzero potential data-loss window; secondary access/failover requires a plan. |
| GZRS | Primary ZRS plus asynchronous replication to the secondary region. | Combines zone and regional resilience at additional cost. |
| Soft delete/versioning | Recovery from eligible logical deletion/overwrites within configured retention. | Extra retained data and feature/type restrictions; not independent account-level backup. |
| Azure Blob vaulted backup | Separate recovery copies for supported block blobs in standard GPv2 accounts. | Additional cost and restore workflow; append blobs and archive-tier blobs are not supported by the referenced backup matrix. |

Redundancy is an **account-level** setting, not a per-blob or per-container
toggle. Different protection classes may need separate accounts. GRS is not
simply "ZRS plus another region"; GZRS is the combined option. RA variants add
secondary read access but do not eliminate asynchronous replication lag. [S9]

Proposed evaluation profiles: **Economy** (LRS, explicitly accepted loss risk),
**Zone-resilient** (ZRS), and **Region-resilient** (GRS/GZRS with a tested failover
plan). Choose backup/soft-delete policy independently. Retain catalogs and
processed-input metadata with the same recovery plan as their Parquet data.
After geo-failover or restore, validate all catalog references before serving
queries; a replicated pointer is not proof that every referenced object is
available in that recovery copy.

Operational backup is also not a magic backup for every blob type: Azure's
support matrix limits restored data to supported block blobs. Check current
region/account/API/tier compatibility and rehearse restoration before claiming
an RPO/RTO. This document chooses no production protection profile. [S10-S11]

## 10. Rules, Constraints, and Failure Behavior

These proposed rules use DIY pipeline terminology. An engine-backed design must
map them to supported equivalents and explicitly identify any unmet guarantees
for owner review; backend compatibility alone does not qualify a product.

| ID | Required behavior for a future implementation |
|----|-----------------------------------------------|
| OBS-001 | Emit the versioned event contract consistently across backend runtimes; preserve event/batch IDs across restart and retry. |
| OBS-002 | Bound memory, spool, batch size, retry concurrency, and compactor work. Never hide drops, quarantine, exhaustion, or permission failures behind success. |
| OBS-003 | Handle ambiguous commits without claiming exactly-once transport. Detect conflicting immutable payloads and deduplicate replayed events in curated output. |
| OBS-004 | Publish only validated complete catalog generations; interrupted/retried compaction must not double-count events, expose partial outputs, or delete uncommitted sources. |
| OBS-005 | Query bounded time/service scopes by default, project needed columns, pin a published snapshot, and report freshness/backlog. Incomplete or unavailable data must be explicit, not an empty successful result. |
| OBS-006 | Accept late uploads independently of raw path dates. Resolve late events in retained event-time partitions; report older-than-retention and clock-anomalous events explicitly. |
| OBS-007 | Authorize writers, compactor, reader, and deletion/backup operators separately. Grant least privilege, require TLS, disable anonymous access, and redact before upload. A broad contributor role must not be described as append-only. |
| OBS-008 | Keep business/audit durability independent of this diagnostic pipeline. Confirm economy loss risk and stronger recovery profiles with an owner before deployment. |
| OBS-009 | Delete/tier only eligible committed data; account for recovery copies, active readers, late rewrites, minimum tier durations, and retention overrides. |
| OBS-010 | Record per-stage bytes, transactions, lag, duplicate/quarantine/drop counts, and query cost/latency through a bounded monitoring channel without recursive logging. |

Missing manifest files, read authorization failures, unavailable regions, and
archive rehydration requirements must return explicit errors or an explicitly
labeled partial result. A storage outage leaves acknowledged local-spool batches
pending for retry; it must not silently advance a cloud-delivery watermark.

Assumptions awaiting approval: diagnostics can tolerate approximately one-minute
upload batches and approximately 15-minute curated freshness; small initial query
concurrency; same-region compute available; standard GPv2 Blob APIs suffice; no
requirement yet for HNS, an always-on engine, or guaranteed global message order.
File sizes, compression, retention, and redundancy above are candidates, not
customer-approved service levels.

## 11. Acceptance Criteria and Benchmark Plan

The **documentation deliverable** is reviewable when the ingestion alternatives,
schema, pruning behavior, cost equation, redundancy/backup distinctions, failure
boundaries, sources, and unresolved decisions are present and linked. It does not
mean that the following implementation criteria have passed.

| Criterion | Verification required before implementation is accepted |
|-----------|--------------------------------------------------------|
| Contract and confidentiality | Adapter fixtures validate required/optional fields, version changes, timestamps, identifier lengths, redaction, and oversized-event handling. |
| Restart and uncertain commit | Kill/restart a writer around upload acknowledgement; replay returns one logical curated event per stable event ID, with no silent batch loss. |
| Many writers | Run 1, 10, and 100 concurrent writers with bursts; compare append shards versus immutable batches for operations, failures, throughput, and total cost. Exercise append rollover if that option is selected. |
| Publication recovery | Interrupt at each compaction/publication/deletion boundary; race two workers; query readers pinned to an old generation. No partial publication, double counts, or reader-file deletion. |
| Late and malformed input | Replay old-named raw objects, skew timestamps, introduce malformed records and conflicting IDs. Verify discovery, accounting, explicit quarantine, and retention behavior. |
| Query correctness | Compare published results with a trusted fixture containing duplicates, midnight boundaries, multiple services, and late events. Include raw-only pending records in freshness reporting, not silently in curated counts. |
| Query performance and cost | Measure cold/warm p50/p95 latency, listing/read request counts, actual bytes transferred, billed processed bytes, compute/spill, and total cost. Confirm narrow-query pruning, not just returned row counts. |
| Recovery | Restore catalogs plus data under the chosen protection profile; validate references and measure actual RPO/RTO. Confirm append/raw coverage limitations. |
| Lifecycle and overload | Simulate backlog longer than raw retention, full spool, expired snapshots, unavailable archive objects, and late Cool-tier rewrites. Verify explicit errors and no unsafe cleanup. |

Benchmark at **1, 10, and 100 GB/day** synthetic ingestion with at least these
queries: one-hour errors by service; trace lookup within one hour; trace lookup
across retention; 30-day message substring; and a 90-day aggregate. Include
128/256 MiB file targets, low-volume forced flushes, sorted/unsorted data, and
typed promoted attributes versus JSON extraction. These are test scales, not
observed product traffic.

Compare a raw JSON baseline, curated Parquet DuckDB, and Synapse where available.
For the shortlisted log-native engines, run the same event/query workload through
their supported ingestion/query APIs and include the continuously running
components, index size, ingest-to-query delay, metadata recovery, and duplicate
semantics. Do not treat a source-product benchmark or compression claim as our
own result. Compare one minimal deployment and the production recovery profile
separately; HA changes the fixed-cost floor substantially.

Include cold startup and in-region versus desktop execution separately. Record
engine/API versions, region, machine size, data distribution, price inputs,
measurement dates, and query plans so results can be reproduced.

### Decisions still open

| Decision | Evidence/approval needed |
|----------|--------------------------|
| Existing log engine or DIY lake | Compare OpenObserve/Loki and, where justified, Quickwit against on-demand Parquet SQL, including engineering effort, idle cost, query patterns, and recovery. |
| Append shards or immutable batches | Measured total cost and restart/rollover complexity; immutable batches are the current recommendation, not a final selection. |
| Table format or custom catalog | Iceberg/Delta Azure commit and query compatibility, maintenance cost, and actual need for concurrent table writers. Prefer proven publication machinery over rebuilding it. |
| Performance target and query engine | Owner-approved p95 latency, freshness, concurrency, and scan budget, then benchmark results. |
| Retention and protection | Diagnostic/audit classification, privacy obligations, accepted data-loss risk, and actual restore requirements. |
| Spool/outage capacity | Events/second, burst size, available disk, and acceptable shedding policy. |
| Partition and promotion policy | Representative queries, service cardinality, late-arrival distribution, and measured file/read amplification. |
| When to add an index or lakehouse format | Trace/full-text demand or catalog/concurrency complexity justifying additional fixed and operational cost. |

## 12. Out of Scope

This PR does not provision Azure resources, enable paid services, add credentials,
change production logging, implement collectors/compaction/query APIs, or promise
an operating budget. Mobile/browser collection, arbitrary user-supplied SQL,
business audit ingestion, full-text indexing, and a complete metrics/tracing
platform are separate work. Any implementation must receive a scoped prompt and
approved cost, security, retention, and recovery parameters first.

## 13. Evidence and References

Service mechanics below are supported by first-party technical documentation;
cost superiority and latency remain **unmeasured design hypotheses**. Confidence
is high in the documented format/API distinctions, moderate in the proposed
starting architecture, and unestablished for workload-specific prices and timing.
Current product/pricing/preview restrictions must be rechecked before deployment.

- **[S1]** Microsoft, [Append Block REST reference](https://learn.microsoft.com/en-us/rest/api/storageservices/append-block): concurrency, duplicates, conditions, append limits, preview qualification, and inferred-tier billing.
- **[S2]** Microsoft, [Block, append, and page blobs](https://learn.microsoft.com/en-us/rest/api/storageservices/understanding-block-blobs--append-blobs--and-page-blobs): blob types cannot be changed in place. For newer append-size limits, prefer S1 over this older overview.
- **[S3]** Microsoft, [Access tiers](https://learn.microsoft.com/en-us/azure/storage/blobs/access-tiers-overview): block-only tier setting, minimum durations, retrieval, archive latency, and redundancy restrictions.
- **[S4]** DuckDB, [Azure extension](https://duckdb.org/docs/current/core_extensions/azure): direct storage access, credential chain, read controls, HTTP statistics, and glob/list behavior.
- **[S5]** DuckDB, [Parquet reading and writing](https://duckdb.org/docs/current/data/parquet/overview): file-list inputs, projection/filter pushdown, row groups, and compression.
- **[S6]** DuckDB, [Hive partitioning](https://duckdb.org/docs/current/data/partitioning/hive_partitioning): explicit partition interpretation and filter pushdown.
- **[S7]** Microsoft, [Serverless SQL best practices](https://learn.microsoft.com/en-us/azure/synapse-analytics/sql/best-practices-serverless-sql-pool): regional placement, Parquet, layout, and query limitations.
- **[S8]** Microsoft, [Serverless data-processed cost management](https://learn.microsoft.com/en-us/azure/synapse-analytics/sql/data-processed) and [file/path filtering](https://learn.microsoft.com/en-us/azure/synapse-analytics/sql/query-specific-files): billing scope/minimums and file elimination mechanisms.
- **[S9]** Microsoft, [Storage redundancy](https://learn.microsoft.com/en-us/azure/storage/common/storage-redundancy): LRS/ZRS/GRS/GZRS, account scope, and asynchronous regional replication.
- **[S10]** Microsoft, [Blob data protection](https://learn.microsoft.com/en-us/azure/storage/blobs/data-protection-overview): distinct protection and recovery mechanisms.
- **[S11]** Microsoft, [Azure Blob backup support matrix](https://learn.microsoft.com/en-us/azure/backup/blob-backup-support-matrix): supported blob/account/tier combinations and restore limitations.
- **[S12]** OpenTelemetry, [Logs Data Model](https://opentelemetry.io/docs/specs/otel/logs/data-model/): timestamp, severity, event, resource, attribute, and trace semantics.
- **[S13]** Microsoft, [Query acceleration](https://learn.microsoft.com/en-us/azure/storage/blobs/data-lake-storage-query-acceleration): per-file CSV/JSON filtering, SQL limitations, and scan/return billing.
- **[S14]** Microsoft, [Blob Storage pricing](https://azure.microsoft.com/en-us/pricing/details/storage/blobs/): obtain applicable capacity/transaction/retrieval rates for the selected configuration.
- **[S15]** Apache Iceberg, [Reliability](https://iceberg.apache.org/docs/latest/reliability/): atomic snapshots, concurrent commits, reliable reads, compaction, and late data.
- **[S16]** Delta Lake, [Storage configuration](https://docs.delta.io/delta-storage/): transactional storage requirements and provider-specific support, including Azure.
- **[S17]** Grafana, [Loki storage](https://grafana.com/docs/loki/latest/configure/storage/) and [architecture](https://grafana.com/docs/loki/latest/get-started/architecture/): label/TSDB indexing, chunks, Azure Blob, deployment components, and retention.
- **[S18]** OpenObserve, [Architecture](https://openobserve.ai/docs/architecture/): Parquet/object storage, Azure backend listing, single-node/HA metadata dependencies, WAL, compaction, and queries. Vendor statements about acceptable single-copy risk are not adopted as our durability policy.
- **[S19]** Quickwit, [Architecture](https://quickwit.io/docs/overview/architecture) and [storage providers](https://quickwit.io/docs/configuration/storage-config): inverted indexes, splits, metadata roles, supported Azure/commodity backends, and connector configuration.
- **[S20]** DuckDB, [Iceberg extension](https://duckdb.org/docs/current/core_extensions/iceberg/overview): object-store/Azure integration, direct metadata reads, and catalog-managed operations; validate supported features for pinned versions.
