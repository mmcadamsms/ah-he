# Architecture Overview

## System Context

The **ah-he** (Approval & Hierarchy Engine) platform provides approval workflow management across web and mobile interfaces, backed by containerized Java services.

## High-Level Components

```
┌─────────────┐  ┌─────────────┐  ┌──────────────┐
│  Android App │  │   iOS App   │  │   Web UX     │
│  (Kotlin)    │  │   (Swift)   │  │ (TS/browser) │
└──────┬───────┘  └──────┬──────┘  └──────┬───────┘
       │                 │                 │
       └────────┬────────┴────────┬────────┘
                │   Shared (KMP)  │
                │                 │
         ┌──────▼─────────────────▼──────┐
         │         Web API (Java)        │
         │       REST / GraphQL          │
         └──────────────┬────────────────┘
                        │
              ┌─────────▼──────────┐
              │  Background Worker │
              │      (Java)        │
              └─────────┬──────────┘
                        │
              ┌─────────▼──────────┐
              │   Data Store(s)    │
              └────────────────────┘
```

## Service Descriptions

### Web UX (`clients/web/`)
React/TypeScript customer portal. It submits manufacturing requests and
visualizes live planning and simulation progress.

### Web API (`backend/web-api/`)
Core business logic API. Exposes RESTful endpoints for all clients (web, mobile). Stateless, horizontally scalable.

For the CNC prototype, this service also hosts a bounded in-process asynchronous
worker. See [ADR 0002](docs/architecture/decisions/0002-cnc-prototype-vertical-slice.md).
The worker boundary is designed to move to the background-worker service when
durable infrastructure is introduced.

### Background Worker (`backend/background-worker/`)
Async job processor for long-running tasks: notifications, report generation, data synchronization, scheduled workflows.

### Geometry/CAM Worker (`backend/geometry-cam-worker/`)
CAD-kernel service for exact STEP topology and geometry analysis.

### Android App (`clients/android/`)
Native Android client built with Kotlin and Jetpack Compose. Shares business logic via Kotlin Multiplatform.

### iOS App (`clients/ios/`)
Native iOS client built with Swift and SwiftUI. Integrates shared Kotlin Multiplatform module.

### Shared Module (`common/kotlin/`)
Kotlin Multiplatform module containing shared business logic, data models, and networking used by Android and (via KMP) iOS.

## Repository Code Boundaries

- `clients/` contains user-facing delivery channels. Customer, staff, and
  supplier experiences should share these clients unless a channel has an
  independent build, release, or security boundary.
- `backend/` contains independently deployable server-side runtimes.
- `common/` contains reusable libraries and contracts. It does not contain
  deployable services, client applications, or business documentation.
- `requirements/` and `docs/` remain the authoritative business, operational,
  engineering, and architectural knowledge structure.

See [ADR 0005](docs/architecture/decisions/0005-organize-code-by-runtime-boundary.md).

## Cross-Cutting Concerns

| Concern | Approach |
|---------|----------|
| Authentication | TBD — likely OAuth 2.0 / OIDC |
| Authorization | Role-based with hierarchy-aware rules |
| Observability | Structured logging, metrics, distributed tracing |
| Configuration | Environment-based, 12-factor style |
| Data persistence | TBD — relational DB for transactional data |
| Messaging | TBD — message queue for async workflows |

## Architecture Decision Records

All significant decisions are recorded in [docs/architecture/decisions/](docs/architecture/decisions/).
