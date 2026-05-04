# Web UX Service

A server-rendered web frontend for the ah-he platform.

## Tech Stack
- Java 21
- Spring Boot
- Thymeleaf (or similar template engine)

## Running Locally

```bash
./gradlew :services:web-ux:bootRun
```

Available at `http://localhost:3000`

## Docker

```bash
docker build -t ahhe-web-ux .
docker run -p 3000:3000 ahhe-web-ux
```
