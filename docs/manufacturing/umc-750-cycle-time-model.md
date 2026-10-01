# UMC-750 Commanded Cycle-Time Model

## Purpose

This model estimates the time represented by supported G-code on a configured
2016-era Haas UMC-750. It drives simulation playback and customer visibility.
It is not a production-time guarantee or controller emulator.

## Machine Profile

| Parameter | Prototype value | Basis |
|---|---:|---|
| X/Y/Z travel | 762 / 508 / 508 mm | Haas published specification |
| B travel | -35 to +110 degrees | 2015 UMC-750 supplement |
| C travel | +/-13,320 degrees before unwind | 2015 UMC-750 supplement |
| Linear rapid | 22,860 mm/min (900 in/min) | Haas published specification |
| Maximum G94 feed | 16,510 mm/min (650 in/min) | 2015 UMC-750 supplement |
| B/C rapid | 50 degrees/second | Current Haas published specification; verify by serial |
| Standard spindle | 8,100 RPM | Haas published specification |
| Tool-to-tool | 2.8 seconds | Haas average specification |
| Chip-to-chip | 3.6 seconds | Haas average benchmark |
| Linear acceleration | 2,500 mm/s² | Calibration default; not Haas published |
| Rotary acceleration | 100 degrees/s² | Calibration default; not Haas published |
| Spindle ramp | 2,000 RPM/s | Calibration default; not Haas published |
| Rotary settle | 0.2 seconds | Calibration default; not Haas published |
| Rotary brake transition | 0.5 seconds | Calibration default; not Haas published |
| Coolant on/off | 1.0 / 0.5 seconds | Calibration default; not Haas published |

The calibration defaults must be measured and updated for the actual machine,
control version, options, maintenance condition, fixtures, and auxiliary
equipment.

## Motion Equations

### G94 linear feed

For a linear XYZ path:

```text
distance = sqrt(dx² + dy² + dz²)
commanded speed = F / 60
```

Time uses a trapezoidal profile. With distance `d`, maximum speed `v`, and
acceleration `a`:

```text
acceleration distance = v² / a

if d <= v² / a:
    time = 2 * sqrt(d / a)
else:
    time = 2v / a + (d - v² / a) / v
```

### G00 rapid

Each participating axis is timed independently using its configured rapid and
acceleration. Coordinated-motion duration is the slowest axis time. Rotary
motion adds configured settle time.

### G93 inverse time

For supported simultaneous linear/rotary blocks:

```text
block time in seconds = 60 / F
```

The interpreter rejects simultaneous linear/rotary feed in G94 because the
tool-tip feed cannot be correctly inferred from a conventional units-per-minute
value.

### G04 dwell

Haas distinguishes decimal and integer `P` values:

```text
G04 P10. = 10 seconds
G04 P10  = 10 milliseconds
```

The parser preserves the source token so these blocks do not collapse to the
same floating-point value.

### G81 and G83 drilling

The model includes:

- rapid XY positioning;
- rapid approach to the R plane;
- programmed-feed cutting to Z;
- G83 rapid retract and approach for each Q peck; and
- G98 initial-plane or G99 R-plane return.

## Feeds and Speeds

Procedural G-code uses standard milling formulas:

```text
RPM = cutting speed (m/min) * 1000 / (pi * tool diameter mm)
table feed (mm/min) = chip load (mm/tooth) * RPM * effective cutting edges
drill feed (mm/min) = feed per revolution (mm/rev) * RPM
```

RPM is capped by the configured spindle maximum. Material values are
conservative prototype starting points and do not replace recommendations for
the exact cutter, holder, engagement, coolant, rigidity, and material lot.

## Playback

Playback rate changes wall-clock visualization only:

```text
simulated time advanced per UI tick =
    wall-clock tick duration * playback multiplier
```

The estimated total machine duration is invariant at 1x through 100x.

## Supported Program Subset

- G00, G01, G02/G03 with I/J, G04
- G20/G21, G80/G81/G83, G90/G91, G93/G94, G98/G99
- M03, M05, M06, M08, M09, M30
- M10/M11 B-brake engage/release and M12/M13 C-brake engage/release
- M00/M01 operator stops
- X, Y, Z, B, C, F, S, T, P, Q, R, I, J words

Unsupported or physically ambiguous constructs must fail explicitly rather
than receiving zero time.

## Physical Setups and Indexed Orientations

An automatic B/C index does not create a new physical setup when the part
remains clamped and the same verified work offset remains active. Such
orientations can legitimately live in one NC program.

Unclamping, flipping, changing fixtures, or establishing another work offset
creates a new physical setup. That work should normally have its own setup
sheet and qualified NC program. The current prototype emits one physical setup
with three indexed orientations and explicitly warns that underside/clamped
surfaces are not completed.

M00/M01 human delay is not included in commanded machine time. At M00, the
simulator pauses until the portal receives an explicit operator resume.

## Exclusions

- collision and fixture verification;
- true volumetric stock removal;
- cutting forces, spindle load, chatter, deflection, heat, and tool wear;
- controller look-ahead and exact servo dynamics;
- feed/rapid overrides;
- probing variability;
- operator and inspection delays; and
- simultaneous five-axis machine kinematics beyond G93 block timing.

## References

- Haas UMC-750 specifications:
  <https://www.haascnc.com/machines/vertical-mills/universal-machine/models/umc-750.html>
- Haas mill G-codes:
  <https://www.haascnc.com/service/online-operator-s-manuals/mill-operator-s-manual/mill---g-codes.html>
- Haas mill M-codes:
  <https://www.haascnc.com/service/service-content/guide-procedures/mill---m-codes.html>
- Haas mill programming:
  <https://www.haascnc.com/service/online-operator-s-manuals/mill-operator-s-manual/mill---basic-programming.html>
- Sandvik Coromant milling formulas:
  <https://www.sandvik.coromant.com/en-gb/knowledge/machining-formulas-definitions/pages/milling.aspx?country=us>
- Harvey Tool speeds and feeds:
  <https://www.harveytool.com/resources/speeds-feeds>
