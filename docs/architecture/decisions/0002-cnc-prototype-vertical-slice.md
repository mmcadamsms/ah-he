# ADR 0002: Implement the CNC Prototype as a Local Vertical Slice

## Status

Accepted

## Context

The repository contains service placeholders but no production infrastructure.
The CNC prototype needs immediate asynchronous behavior, a Java backend, a
React UX, and a structure that can grow into independent planning and
simulation services. Introducing a broker, database, CAD kernel, production CAM
engine, and controller integration at the same time would obscure the customer
experience being validated.

## Decision

The prototype will use:

- a React/TypeScript single-page application in `clients/web`;
- a Spring Boot API in `backend/web-api`;
- an in-process bounded asynchronous executor for planning and simulation;
- domain packages for intake, geometry, planning, G-code, and simulation;
- an in-memory job repository behind a repository interface;
- exported STEP/STL as the interchange formats;
- a configurable Haas UMC-750 machine profile; and
- polling of a versioned REST resource for customer-visible progress.

The simulator interprets a documented subset of Haas-style G-code and produces
machine telemetry. It is not a controller emulator, postprocessor, collision
verifier, or production CAM engine. The generated code cannot be sent to a
machine.

When durability or scale is required, the asynchronous orchestration can move
to `backend/background-worker` with a durable queue and shared database while
preserving the REST model and domain interfaces.

## Consequences

- The complete customer journey can run locally with little infrastructure.
- The UI receives realistic incremental status rather than a fabricated
  immediately-complete response.
- State is lost when the API restarts.
- Geometry understanding and material removal are approximate.
- Native CAD formats require later vendor SDK or licensed translation work.
- Production use requires approved CAM, postprocessing, collision verification,
  machinist review, and a separate controlled deployment path.
