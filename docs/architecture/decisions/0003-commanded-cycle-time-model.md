# ADR 0003: Derive Simulation Time from G-code and Machine Kinematics

## Status

Accepted

## Context

The initial simulator delayed each G-code block by a fixed number of
milliseconds. That made every block appear equally expensive and disconnected
the customer experience from programmed feeds, travel distance, rotary
indexing, drilling, spindle changes, and tool changes.

## Decision

The simulator will parse a documented subset of Haas-style modal G-code into
timed blocks. Duration is calculated from:

- path distance and G94 feed, or G93 inverse time;
- axis-specific rapid rates and trapezoidal acceleration;
- B/C rotary distance and settle time;
- spindle RPM changes;
- published tool-to-tool time with chip-to-chip retained as a benchmark;
- coolant latency and programmed dwell; and
- canned-cycle feed, peck, rapid, and return motions.

The machine profile separates published Haas values from calibration defaults.
The UI exposes a playback multiplier that changes wall-clock playback but never
the calculated commanded machine time.

Feeds and speeds are generated from surface speed, tool diameter, chip load,
cutting-edge count, feed per revolution, and the configured spindle limit.

## Consequences

- Long moves and slow feeds visibly take longer than short or rapid moves.
- The same program has the same estimated machine duration at every playback
  rate.
- Timing can be calibrated against a specific UMC-750 without rewriting the
  interpreter.
- Estimates remain unsuitable for quoting guaranteed production capacity until
  serial-specific acceleration and auxiliary timings are measured.
- Cutting forces, collisions, tool deflection, controller look-ahead, overrides,
  and operator delays remain outside this model.
