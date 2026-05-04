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
| `services/web-ux/` | Web frontend service (Java/Spring Boot) |
| `services/web-api/` | REST API service (Java/Spring Boot) |
| `services/background-worker/` | Async job processor (Java/Spring Boot) |
| `androidApp/` | Android app (Kotlin/Jetpack Compose) |
| `iosApp/` | iOS app (Swift/SwiftUI) |
| `shared/` | Shared Kotlin Multiplatform module |
| `docs/` | All project documentation |
| `requirements/` | Product requirements and agent prompts |

## Running Individual Services

```bash
# Web API only
cd services/web-api && ../gradlew bootRun

# Web UX only
cd services/web-ux && ../gradlew bootRun
```

## Running Tests

```bash
# All tests
./gradlew test

# Specific service
./gradlew :services:web-api:test
```

## For AI Agents

Read [AGENTS.md](../../AGENTS.md) first — it provides everything you need to navigate this codebase.
