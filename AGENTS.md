# AGENTS.md — Agent Discovery & Navigation

> This file is the entry point for AI agents working in this repository.
> It follows the [harness engineering](https://openai.com/index/harness-engineering/) principle of **progressive disclosure** — start here, follow links to deeper context as needed.

## Quick Orientation

- **What is this project?** An approval & hierarchy engine with web services, mobile apps, and shared logic.
- **Primary language:** Java (services), Kotlin (Android/shared), Swift (iOS)
- **Build system:** Gradle (Kotlin DSL), Docker Compose for services

## Key Entry Points

| What you need | Where to look |
|--------------|---------------|
| System architecture | [ARCHITECTURE.md](ARCHITECTURE.md) |
| Architecture decisions (ADRs) | [docs/architecture/decisions/](docs/architecture/decisions/) |
| Coding standards | [docs/standards/coding-standards.md](docs/standards/coding-standards.md) |
| Testing standards | [docs/standards/testing-standards.md](docs/standards/testing-standards.md) |
| API conventions | [docs/standards/api-standards.md](docs/standards/api-standards.md) |
| Product requirements | [requirements/](requirements/) |
| Agent workflow prompts | [requirements/prompts/](requirements/prompts/) |
| Service code (web-ux) | [services/web-ux/](services/web-ux/) |
| Service code (web-api) | [services/web-api/](services/web-api/) |
| Service code (worker) | [services/background-worker/](services/background-worker/) |
| Android app | [androidApp/](androidApp/) |
| iOS app | [iosApp/](iosApp/) |
| Shared KMP module | [shared/](shared/) |
| Operational runbooks | [docs/runbooks/](docs/runbooks/) |

## Agent Workflow

Requirements flow through a pipeline as `.md` prompt files:

```
requirements/prompts/new/          → Agent picks up work
requirements/prompts/in-progress/  → Agent is actively working
requirements/prompts/deploying/    → Changes are being deployed
requirements/prompts/validating/   → Under automated/human validation
requirements/prompts/completed/    → Successfully delivered
```

See [requirements/prompts/README.md](requirements/prompts/README.md) for the full workflow and prompt template.

## Conventions to Follow

1. **All architecture decisions** must be recorded as ADRs in `docs/architecture/decisions/`
2. **All requirements** must live in `requirements/functional-areas/` grouped by domain
3. **All agent work** flows through the `requirements/prompts/` pipeline
4. **No tacit knowledge** — if an agent needs it, it must be written down in this repo
5. **Mechanical enforcement** — prefer CI checks and linters over manual review
6. **Tests are mandatory** — every change must include tests per [testing standards](docs/standards/testing-standards.md)

## Build & Run

```bash
# Build all services
./gradlew build

# Run local stack
docker compose up

# Run tests
./gradlew test
```
