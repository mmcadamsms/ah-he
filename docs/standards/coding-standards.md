# Coding Standards

## General Principles

1. **Readability first** — code is read far more than it is written
2. **Consistency** — follow existing patterns in the codebase
3. **Small units** — prefer small, focused classes and methods
4. **Self-documenting** — use clear naming; add comments only for non-obvious logic

## Java (Backend Services)

- **Version:** Java 21+
- **Framework:** Spring Boot
- **Style:** Follow [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html)
- **Naming:** `camelCase` for methods/variables, `PascalCase` for classes, `UPPER_SNAKE_CASE` for constants
- **Package structure:** `com.ahhe.<service>.<layer>` (e.g., `com.ahhe.webapi.controller`)
- **Layers:** `controller` → `service` → `repository` → `model`
- **Dependency Injection:** Constructor injection only (no field injection)
- **Null safety:** Use `Optional` for return types that may be empty; never return `null`

## Kotlin (Android & Shared)

- **Style:** Follow [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html)
- **UI:** Jetpack Compose (declarative)
- **Coroutines:** Use structured concurrency; avoid `GlobalScope`
- **Nullability:** Leverage Kotlin's null safety; avoid `!!`

## Swift (iOS)

- **Style:** Follow [Swift API Design Guidelines](https://www.swift.org/documentation/api-design-guidelines/)
- **UI:** SwiftUI (declarative)
- **Concurrency:** Use Swift concurrency (`async`/`await`)
- **Error handling:** Use `Result` type or throwing functions

## Enforcement

Standards are enforced via:
- Linting in CI (Checkstyle for Java, ktlint for Kotlin, SwiftLint for Swift)
- Pre-commit hooks (optional, see `.pre-commit-config.yaml`)
- PR review checklists
