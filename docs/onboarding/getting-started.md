# Getting Started

## Prerequisites

- **Java 21+** — for backend services
- **Kotlin 2.0+** — for Android and shared modules
- **Xcode 15+** — for iOS development (macOS only)
- **Docker & Docker Compose** — for running the service stack
- **Gradle** — included via wrapper (`./gradlew`)

## Clone & Build

```bash
git clone <repo-url>
cd ah-he

# Build all modules
./gradlew build

# Run the local service stack
docker compose up
```

## Project Layout

| Directory | What it contains |
|-----------|-----------------|
| `clients/web/` | Web client (React/TypeScript and local static runtime) |
| `clients/android/` | Android app (Kotlin/Jetpack Compose) |
| `clients/ios/` | iOS app (Swift/SwiftUI) |
| `backend/web-api/` | REST API service (Java/Spring Boot) |
| `backend/background-worker/` | Async job processor |
| `backend/geometry-cam-worker/` | CAD-kernel geometry service (Python) |
| `common/kotlin/` | Shared Kotlin Multiplatform module |
| `docs/` | All project documentation |
| `requirements/` | Product requirements and agent prompts |

## Running Individual Services

```bash
# Web API only
./gradlew :backend:web-api:bootRun

# Web UX only
cd clients/web
npm run dev
```

## Running Tests

```bash
# All tests
./gradlew test

# Specific service
./gradlew :backend:web-api:test
```

## For AI Agents

Read [AGENTS.md](../../AGENTS.md) first — it provides everything you need to navigate this codebase.
