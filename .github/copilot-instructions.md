# Copilot Instructions for ah-he

## Project Context
This is an approval & hierarchy engine with Java backend services, a Kotlin Android app, a Swift iOS app, and shared Kotlin Multiplatform code.

## Agent Entry Point
Always start by reading [AGENTS.md](../AGENTS.md) at the repository root. It contains a structured index of all documentation, standards, and conventions.

## Key Rules
1. Follow standards in `docs/standards/` — coding, testing, and API conventions
2. Check `docs/architecture/decisions/` for existing ADRs before making architectural choices
3. All new features must have corresponding requirements in `requirements/functional-areas/`
4. Write tests for every change — see `docs/standards/testing-standards.md`
5. Use conventional commits for commit messages
6. Java services use Spring Boot; follow established patterns in existing service code
7. Android code is in Kotlin with Jetpack Compose
8. iOS code is in Swift with SwiftUI

## Prompt Workflow
When working on a prompt from `requirements/prompts/`:
1. Move the file from `new/` to `in-progress/`
2. Implement the changes described in the prompt
3. Move to `deploying/` when submitting a PR
4. Move to `validating/` when deployed to a test environment
5. Move to `completed/` when validated and merged
