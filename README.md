# ah-he — Approval & Hierarchy Engine

> An agent-first, harness-engineered platform for approval workflows.

## Project Overview

This repository is the single source of truth for the **ah-he** platform — a multi-service, multi-platform application built using [harness engineering](https://openai.com/index/harness-engineering/) principles. Human engineers act as architects; AI agents handle day-to-day coding, testing, and deployment tasks.

## Repository Structure

```
ah-he/
├── AGENTS.md                    # Agent entry point & discovery
├── ARCHITECTURE.md              # System architecture overview
├── CONTRIBUTING.md              # Contribution & workflow guide
├── docs/                        # Structured documentation
│   ├── architecture/            # Architecture docs & ADRs
│   ├── onboarding/              # Getting started guides
│   ├── runbooks/                # Operational runbooks
│   └── standards/               # Coding, testing, API standards
├── requirements/                # Product requirements
│   ├── functional-areas/        # Requirements by domain
│   └── prompts/                 # Agent workflow pipeline
│       ├── new/                 #   Queued for agent pickup
│       ├── in-progress/         #   Agent is working
│       ├── deploying/           #   Being deployed
│       ├── validating/          #   Under validation
│       └── completed/           #   Done
├── services/                    # Containerized backend (Java)
│   ├── web-ux/                  # Web frontend service
│   ├── web-api/                 # REST API service
│   └── background-worker/       # Async job processor
├── androidApp/                  # Android application (Kotlin)
├── iosApp/                      # iOS application (Swift)
├── shared/                      # Shared Kotlin Multiplatform code
├── infrastructure/              # Docker & CI configuration
├── build.gradle.kts             # Root Gradle build
├── settings.gradle.kts          # Gradle module settings
└── docker-compose.yml           # Local development stack
```

## Tech Stack

| Layer | Technology |
|-------|-----------|
| Backend Services | Java 21, Spring Boot |
| Android App | Kotlin, Jetpack Compose |
| iOS App | Swift, SwiftUI |
| Shared Logic | Kotlin Multiplatform |
| Containers | Docker, Docker Compose |
| Build System | Gradle (Kotlin DSL) |

## Getting Started

See [docs/onboarding/getting-started.md](docs/onboarding/getting-started.md).

## For AI Agents

Start with [AGENTS.md](AGENTS.md) — it provides a structured index of everything you need.
