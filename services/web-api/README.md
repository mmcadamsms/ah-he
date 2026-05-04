# Web API Service

Core REST API for the ah-he platform. All clients (web, Android, iOS) communicate through this service.

## Tech Stack
- Java 21
- Spring Boot
- Spring Data JPA

## API Standards
See [docs/standards/api-standards.md](../../docs/standards/api-standards.md)

## Running Locally

```bash
./gradlew :services:web-api:bootRun
```

Available at `http://localhost:8080`

## Docker

```bash
docker build -t ahhe-web-api .
docker run -p 8080:8080 ahhe-web-api
```
