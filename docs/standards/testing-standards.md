# Testing Standards

## Principles

1. **Every change must include tests** — no exceptions
2. **Test behavior, not implementation** — tests should survive refactoring
3. **Fast feedback** — unit tests must run in seconds, not minutes
4. **Deterministic** — no flaky tests; mock external dependencies

## Test Pyramid

```
     ╱  E2E  ╲          Few, slow, high confidence
    ╱ Integration╲       Moderate count, test service boundaries
   ╱   Unit Tests  ╲     Many, fast, test individual units
```

## By Component

### Java Services
- **Framework:** JUnit 5 + Mockito
- **Coverage target:** ≥ 80% line coverage
- **Structure:** Mirror `src/main/` in `src/test/`
- **Naming:** `<ClassName>Test.java` for unit, `<ClassName>IntegrationTest.java` for integration
- **Integration tests:** Use `@SpringBootTest` with test containers

### Kotlin (Android & Shared)
- **Framework:** kotlin.test + JUnit 5
- **UI tests:** Compose testing framework
- **Shared module:** Test in `commonTest/`

### Swift (iOS)
- **Framework:** XCTest
- **UI tests:** XCUITest for critical flows

## Running Tests

```bash
# All tests
./gradlew test

# With coverage report
./gradlew test jacocoTestReport

# Single service
./gradlew :services:web-api:test
```

## CI Enforcement

Tests run on every PR. A PR cannot merge if:
- Any test fails
- Coverage drops below threshold
- New code lacks corresponding tests
