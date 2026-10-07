# Thermal Environment and Dimensional Control

## Problem

Parts, fixtures, machine structures, ballscrews, spindles, probes, and measuring
equipment change dimension with temperature. A shop that varies from 55°F to
85°F can produce and measure materially different dimensions even when the
program and process are unchanged. Temperature-controlled coolant helps control
cutting heat but does not by itself stabilize the complete manufacturing and
measurement system.

## Objective

Define risk-based ambient, machine, coolant, part, fixture, gauge,
stabilization, monitoring, warmup, compensation, and inspection controls so
parts are manufactured and accepted relative to the required dimensional
reference and uncertainty.

## Reference Temperature

Unless a specification states otherwise, geometrical and dimensional
properties use the ISO 1 standard reference temperature of `20°C` (`68°F`).

Measurements made away from 20°C require one of:

- demonstrated negligible thermal effect relative to tolerance;
- controlled stabilization near the reference temperature; or
- documented thermal correction with appropriate uncertainty and approval.

## Illustrative Thermal Expansion

Use:

```text
change in length = original length x coefficient of thermal expansion x temperature change
```

Approximate room-temperature coefficients:

- carbon steel: about `6.5 x 10^-6 in/in/°F`;
- 6061-class aluminum: about `13.1 x 10^-6 in/in/°F`.

For a 30°F swing from 55°F to 85°F:

| Feature length | Steel change | Aluminum change |
|---:|---:|---:|
| 1 in | 0.000195 in | 0.000393 in |
| 10 in | 0.00195 in | 0.00393 in |
| 20 in | 0.00390 in | 0.00786 in |

These examples illustrate scale only. Use the actual alloy coefficient,
temperature, geometry, and uncertainty for a real acceptance decision.

## Inputs

- drawing and dimensional reference;
- material and coefficient of thermal expansion;
- feature length and geometry;
- tolerance and measurement uncertainty;
- machine and fixture material;
- ambient temperature and rate of change;
- spatial temperature gradients;
- raw-stock and part temperature;
- coolant temperature and flow;
- spindle, ballscrew, motor, and process heat;
- warmup and idle state;
- probing and compensation capability;
- measuring-equipment material and temperature;
- inspection environment;
- customer and industry requirements; and
- demonstrated process capability.

## Thermal Control Plan

For each thermally sensitive job define:

- approved ambient operating range;
- maximum rate of ambient change;
- machine warmup;
- spindle and axis stabilization;
- coolant target, tolerance, and stabilization;
- stock and fixture acclimation;
- part temperature before finish cuts;
- in-process probing or offsets;
- gauge and part acclimation before inspection;
- temperatures to measure and log;
- correction method;
- uncertainty contribution;
- response to excursions; and
- qualified approver.

## Rules

1. Compare expected thermal dimensional change with the tolerance and
   measurement uncertainty.
2. Do not assume coolant temperature alone controls machine, fixture, stock,
   gauge, or ambient thermal error.
3. Use machine-OEM warmup and thermal-compensation guidance where applicable.
4. Record whether a machine was cold, warming, stable, interrupted, or resumed
   after idle.
5. Keep direct sun, exterior doors, HVAC discharge, heaters, compressors, and
   major heat sources from creating uncontrolled gradients near precision work.
6. Match raw stock, fixture, and process stabilization to the required
   accuracy.
7. Precision inspection uses a controlled environment or documented thermal
   correction.
8. The part and measuring equipment must be sufficiently stabilized relative
   to each other before acceptance measurement.
9. Thermal correction requires actual temperature measurement, appropriate
   material data, documented calculation, and uncertainty.
10. Customer drawings or standards that prohibit correction or require a
    specific environment take precedence.
11. Temperature excursions create a visible hold, remeasurement, or review
    condition for affected work.
12. Process capability must include seasonal and startup behavior, not only
    results from one thermally stable period.

## Facility Strategy

The business may use different thermal-control levels:

1. **General fabrication zone:** Broad comfort and equipment limits for work
   whose tolerances are insensitive to expected thermal change.
2. **Controlled machining zone:** Reduced ambient swing and gradients, machine
   warmup, coolant management, and process monitoring.
3. **Precision cell:** Localized conditioning, stabilized machine and coolant,
   probing, and restricted heat or door exposure.
4. **Metrology room:** Stable temperature, humidity where needed, low vibration,
   controlled access, gauge storage, and recorded conditions.

The selected level must be justified by the actual work and economics.

## Coolant Temperature

Coolant control can:

- reduce cutting-zone temperature variation;
- reduce part and fixture thermal drift;
- stabilize long cycles;
- improve repeatability; and
- support machine compensation.

It cannot fully compensate for:

- cold or changing machine castings;
- ballscrew and spindle growth;
- fixture and stock gradients;
- ambient changes;
- direct sunlight or drafts;
- hot chips;
- gauge temperature;
- material coefficient differences; or
- insufficient acclimation.

## Inspection and Correction

Record:

- ambient temperature;
- part temperature where material;
- gauge temperature where material;
- time and stabilization evidence;
- actual measurement;
- correction equation and coefficient;
- corrected value;
- uncertainty; and
- acceptance decision.

Do not report a corrected value with more precision than the temperature and
coefficient evidence support.

## Failure Behavior

Hold or escalate when:

- expected thermal error is material relative to tolerance;
- required temperatures are unavailable;
- machine warmup or stability is not achieved;
- coolant is outside the approved range;
- part and gauge are not stabilized;
- temperature changed too rapidly;
- correction inputs or alloy coefficient are uncertain;
- inspection environment is outside its approved range;
- OEM thermal compensation is unavailable or invalid; or
- customer requirements conflict with the proposed method.

## Acceptance Criteria

1. Every precision job identifies whether thermal error is negligible,
   controlled, or corrected.
2. The thermal plan records ambient, machine, coolant, part, fixture, and
   inspection controls applicable to the job.
3. A 55°F-to-85°F seasonal scenario can be evaluated quantitatively for the
   actual material, length, and tolerance.
4. Coolant control is never treated as proof that the entire system is
   thermally stable.
5. Machine warmup and resumed-after-idle state are recorded where required.
6. Temperature-corrected measurements retain raw value, temperature,
   coefficient, calculation, corrected value, and uncertainty.
7. Excursions create an auditable hold, review, or remeasurement.
8. Seasonal process-capability evidence is available before claiming
   year-round capability for temperature-sensitive tolerances.

## Out of Scope

- Selecting a universal shop temperature for all processes.
- Assuming nominal material coefficients are sufficient for all precision work.
- Replacing machine-OEM, customer, metrology, or qualified engineering
  requirements.
- Claiming coolant chillers eliminate ambient or machine thermal effects.

## References

- ISO 1:2022, standard reference temperature:
  <https://www.iso.org/standard/80702.html>
- NIST, *The 2016 Revision of ISO 1 Standard Reference Temperature for
  Geometrical Product Specifications and Verification*:
  <https://nvlpubs.nist.gov/nistpubs/jres/121/jres.121.026.pdf>
- NIST Alloy Data:
  <https://www.nist.gov/mml/acmd/trc/nist-alloy-data>
- Haas spindle warmup guidance:
  <https://www.haascnc.com/service/troubleshooting-and-how-to/reference-documents/mill---spindle---programs--run-in--warm-up--break-in-.html>
- Okuma Thermo-Friendly Concept:
  <https://www.okuma.com/thermo-friendly-concept>
- [Facility, Site, Utilities, and Layout Questions](../../discovery/topics/facility-site-utilities-and-layout.md)
- [Manufacturing Processes and Quality Questions](../../discovery/topics/manufacturing-processes-and-quality.md)
