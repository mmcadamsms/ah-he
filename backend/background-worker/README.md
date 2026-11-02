# Background Worker Service

Azure Queue wakeups plus authoritative Table outbox reconciliation and guarded
audit projection. Uses the shared `common/operations` library and a named
SERVICE principal with WORKER authority. It does not execute payment, carrier,
scanner or physical-machine effects.

## Tech Stack
- Java 21
- Spring Boot
- Azure Table/Queue SDK adapters behind shared provider-neutral ports
- Structured stdout, Actuator readiness and Prometheus metrics

Configuration, build context, identity and limitations are in the
[operational runbook](../../docs/runbooks/operational-workflows.md).

## Running Locally

```bash
./gradlew :backend:background-worker:bootRun
```

## Docker

```bash
docker build -f .\backend\background-worker\Dockerfile -t ahhe-worker .
```

Run that build from the repository root after creating the worker JAR; supply
approved configuration/workload identity before starting the container.
