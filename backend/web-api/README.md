# Web API Service

Core REST API for the ah-he platform. All clients (web, Android, iOS) communicate through this service.

## Tech Stack
- Java 21
- Spring Boot
- In-memory prototype job repository
- Bounded asynchronous manufacturing executor

## API Standards
See [docs/standards/api-standards.md](../../docs/standards/api-standards.md)

## Running Locally

```bash
./gradlew :backend:web-api:bootRun
```

Available at `http://localhost:8080`

## Prototype API

- `POST /api/v1/manufacturing-jobs` — multipart `file` and `material`
- `GET /api/v1/manufacturing-jobs/{id}` — live plan and simulation state
- `GET /api/v1/manufacturing-jobs/{id}/gcode` — representative output
- `PATCH /api/v1/manufacturing-jobs/{id}/simulation-speed` — change live
  playback rate without changing estimated cycle time
- `POST /api/v1/manufacturing-jobs/{id}/resume` — acknowledge an M00 operator
  task and resume the simulated program

The prototype accepts exported STEP/STL files. It does not execute uploaded
content or communicate with physical machinery.

Cycle timing is derived from the supported modal G-code subset and the
configured UMC-750 profile. See
`docs/manufacturing/umc-750-cycle-time-model.md`.

## Docker

```bash
docker build -t ahhe-web-api .
docker run -p 8080:8080 ahhe-web-api
```
