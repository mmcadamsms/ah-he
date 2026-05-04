# Architecture Overview

## System Context

The **ah-he** (Approval & Hierarchy Engine) platform provides approval workflow management across web and mobile interfaces, backed by containerized Java services.

## High-Level Components

```
┌─────────────┐  ┌─────────────┐  ┌──────────────┐
│  Android App │  │   iOS App   │  │   Web UX     │
│  (Kotlin)    │  │   (Swift)   │  │   (Java)     │
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

### Web UX (`services/web-ux/`)
Server-rendered web frontend. Handles UI rendering, session management, and proxies API calls.

### Web API (`services/web-api/`)
Core business logic API. Exposes RESTful endpoints for all clients (web, mobile). Stateless, horizontally scalable.

### Background Worker (`services/background-worker/`)
Async job processor for long-running tasks: notifications, report generation, data synchronization, scheduled workflows.

### Android App (`androidApp/`)
Native Android client built with Kotlin and Jetpack Compose. Shares business logic via Kotlin Multiplatform.

### iOS App (`iosApp/`)
Native iOS client built with Swift and SwiftUI. Integrates shared Kotlin Multiplatform module.

### Shared Module (`shared/`)
Kotlin Multiplatform module containing shared business logic, data models, and networking used by Android and (via KMP) iOS.

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
