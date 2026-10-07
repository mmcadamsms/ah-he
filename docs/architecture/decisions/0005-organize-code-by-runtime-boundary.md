# ADR 0005: Organize Code by Runtime Boundary

## Status

Accepted

## Context

The repository originally placed Android, iOS, and shared Kotlin modules at the
root while the web client lived beside backend services under `services/`.
That layout mixed delivery channels and server runtimes and made root-level
business, operational, and engineering documentation harder to distinguish
from low-level deployable code.

Future customer, staff, and supplier experiences may share web and mobile
applications. Organizing code by persona would duplicate runtimes and create
unclear ownership. The more stable boundary is how code is built and deployed.

## Decision

Use these top-level code boundaries:

```text
clients/
  web/
  android/
  ios/
backend/
  web-api/
  background-worker/
  geometry-cam-worker/
common/
  kotlin/
```

Rules:

1. `clients/` contains independently built user-facing delivery channels.
2. Customer, staff, supplier, and future roles are modeled within clients
   unless they require an independent build, deployment, security, or release
   lifecycle.
3. `backend/` contains independently deployable server-side APIs and workers.
4. `common/` contains reusable libraries, models, contracts, and utilities
   that are not independently deployed.
5. A component belongs in `common/` only when at least two consumers benefit
   and the dependency direction remains clear.
6. Business domains remain packages/modules inside these runtime boundaries;
   they do not create new root folders by default.
7. Business, operational, financial, product, and engineering knowledge stays
   in `requirements/` and `docs/`.
8. Infrastructure and repository automation stay in `infrastructure/` and
   `scripts/`.

## Consequences

- All client code is discoverable under one root.
- Backend deployables are no longer mixed with the web client.
- Shared KMP code is clearly reusable code rather than another application.
- Persona-specific experiences can reuse channels and services.
- Build paths, Docker contexts, scripts, and documentation must use the new
  locations.
- Future components need an explicit runtime or reuse boundary before gaining
  a new top-level directory.
