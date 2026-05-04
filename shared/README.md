# Shared Module

Kotlin Multiplatform module containing shared business logic, data models, and networking used by both Android and iOS apps.

## Targets
- `commonMain` — shared code (Kotlin)
- `androidMain` — Android-specific implementations
- `iosMain` — iOS-specific implementations

## Building

```bash
./gradlew :shared:build
```

## What Belongs Here
- Domain models / data classes
- API client interfaces and implementations
- Business logic (validation, transformation)
- Shared constants and utilities

## What Does NOT Belong Here
- UI code (belongs in `androidApp/` or `iosApp/`)
- Platform-specific system APIs (use `expect`/`actual` pattern)
