# Backend

Independently deployable server-side runtimes live here.

## Current Runtimes

- `web-api/` — Java/Spring Boot API
- `background-worker/` — asynchronous worker boundary
- `geometry-cam-worker/` — Python CAD-kernel geometry service

Each runtime owns its entry point, deployment artifact, configuration, health
contract, and runtime-specific tests. Shared reusable code belongs in
`common/`, not in an arbitrary backend service.
