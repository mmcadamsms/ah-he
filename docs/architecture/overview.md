# Detailed Architecture

> For the high-level overview, see [ARCHITECTURE.md](../../ARCHITECTURE.md).

## Service Communication

All client applications (Web UX, Android, iOS) communicate with the **Web API** service over HTTPS.

The **Web API** publishes events to a message queue consumed by the **Background Worker** for async processing.

## Data Flow

```
Client → Web API → Database (synchronous)
                 → Message Queue → Background Worker (asynchronous)
```

## Deployment Topology

Each service is containerized and independently deployable:

| Service | Port (local) | Container |
|---------|-------------|-----------|
| web-ux | 3000 | `ahhe-web-ux` |
| web-api | 8080 | `ahhe-web-api` |
| background-worker | — | `ahhe-worker` |

## Shared Module Architecture

The Kotlin Multiplatform `shared` module provides:

- **Data models** — shared domain objects
- **API client** — networking layer for mobile clients
- **Business logic** — validation, transformation rules

Targets: `androidMain`, `iosMain`, `commonMain`
