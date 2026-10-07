# Clients

User-facing delivery channels live here.

## Current Clients

- `web/` — browser client
- `android/` — Android client
- `ios/` — iOS client

Customer, staff, supplier, administrator, and other roles should share these
clients when practical. Create another client only when it has an independent
platform, build, release, deployment, or security boundary—not merely a
different persona.

Clients may depend on contracts and libraries from `common/` and communicate
with runtimes in `backend/`. They must not contain server deployment logic.
