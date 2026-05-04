# Background Worker Service

Async job processor for long-running tasks: notifications, report generation, scheduled workflows.

## Tech Stack
- Java 21
- Spring Boot
- Spring AMQP (message queue integration)

## Running Locally

```bash
./gradlew :services:background-worker:bootRun
```

## Docker

```bash
docker build -t ahhe-worker .
docker run ahhe-worker
```
