# Common

Reusable, non-deployable code and contracts live here.

## Current Modules

- `kotlin/` — Kotlin Multiplatform models, networking, and shared logic

Code belongs here only when multiple consumers benefit and dependency
direction remains explicit. Do not use `common/` as a miscellaneous folder for
deployable services, client UI, business documents, or one-off helpers.

Business and operational knowledge belongs in `requirements/` and `docs/`.
