# Shipment Evidence and Carrier Performance

## Problem

When a delivered or returned part is damaged, the business needs evidence to
distinguish manufacturing nonconformance, inadequate packaging, carrier
handling, customer handling, installation, and return-transit damage. Without
linked outbound and return records, claims, corrective action, carrier
selection, and customer resolution become subjective.

## Objective

Capture risk-based photo, video, package, custody, carrier, tracking, delivery,
return, and inbound-condition evidence. Link both shipping directions to the
part and return case, and use actual carrier performance to improve packaging,
shipping selection, claims, and delivered-cost estimates.

## Terminology

- **Outbound evidence:** Condition and custody evidence from final inspection
  through customer delivery.
- **Return evidence:** Condition and custody evidence from return authorization
  through inbound receiving and unpacking.
- **Package record:** Dimensions, weight, packaging method, identifiers,
  contents, evidence, carrier, and tracking for one shipping unit.
- **Chain of custody:** Recorded transfer among the business, carrier,
  customer, return carrier, and receiving personnel.
- **Risk-based evidence:** Evidence depth selected according to value,
  fragility, precision, replaceability, claim risk, and customer requirement.

## Inputs

- customer, order, part, revision, quantity, and serial or lot;
- final inspection and release;
- part value and replacement lead time;
- fragility, corrosion, cleanliness, and critical surfaces;
- packaging standard and instructions;
- package dimensions and weight;
- photographs and video;
- shock, tilt, temperature, humidity, or tamper evidence where used;
- outbound carrier, service, tracking, scans, and delivery;
- customer damage report and evidence;
- return authorization and responsibility;
- return packaging and label;
- return carrier, service, tracking, scans, and delivery;
- inbound receiving and unpacking evidence;
- claim, disposition, and cost; and
- carrier and packaging performance history.

## Outbound Evidence Rules

1. Link final inspection release to the exact part, quantity, revision, and
   shipment.
2. Capture condition of risk-relevant surfaces and features before packaging.
3. Record packaging materials, corrosion protection, blocking, bracing,
   orientation, closure, container condition, dimensions, and weight.
4. Capture package identifiers and labels without exposing them outside
   authorized records.
5. Record carrier, service, tracking, pickup, and handoff.
6. Use more evidence for high-value, fragile, irreplaceable, cosmetic,
   precision, hazardous, or customer-critical shipments.
7. Staged photographs are the default when they provide sufficient evidence;
   continuous video requires a documented reason.

## Customer Damage Report

Request, where practical:

- package and label before disposal;
- all exterior faces;
- visible impact, puncture, crush, wetness, opening, or tampering;
- internal packaging before full removal;
- part position;
- damage close-ups and context;
- missing quantity;
- delivery receipt notation;
- tracking;
- date and time; and
- whether installation, use, repair, or repackaging occurred.

Do not make perfect customer evidence a prerequisite for immediate safety or
containment action.

## Return Evidence Rules

1. Every return has an authorization linked to the original order and shipment.
2. Record who is responsible for the return label, packaging, insurance, and
   freight.
3. Provide packaging instructions appropriate to the alleged damage and part.
4. Record return carrier, service, tracking, pickup, scans, and delivery.
5. Retain customer-supplied packaging evidence where available.
6. Photograph receiving condition before opening.
7. Use staged unpacking photographs or video when damage attribution, value,
   fragility, or claim risk justifies it.
8. Record differences between customer-reported and received condition without
   presuming bad faith.

## Carrier Performance

Measure outbound and return performance by:

- carrier;
- service;
- lane;
- origin and destination;
- package profile;
- weight and dimensions;
- value;
- packaging standard;
- season;
- pickup performance;
- transit and delivery;
- scan completeness;
- loss and damage;
- claim filing, payment, and denial;
- accessorials;
- customer experience; and
- total delivered and return cost.

## Privacy and Security

1. Minimize capture of people, neighboring customer property, documents,
   addresses, and unrelated activity.
2. Restrict shipment evidence by customer and role.
3. Define retention by claim, warranty, contract, security, and customer need.
4. Do not use employee or customer video for unrelated surveillance or
   discipline without a separate lawful policy and purpose.
5. Protect shipping labels, addresses, customer identity, and controlled part
   information.

## Failure Behavior

The system must flag unresolved attribution when:

- pre-shipment condition evidence is missing;
- tracking or package identity cannot be linked;
- packaging changed without record;
- customer or return evidence is incomplete;
- multiple damage events are plausible;
- carrier scans conflict;
- the part was installed or modified before documentation;
- evidence integrity is uncertain; or
- responsibility cannot be supported.

Do not fabricate a definitive responsibility classification.

## Acceptance Criteria

1. Every package links to customer, order, shipment, contents, carrier, service,
   and tracking.
2. Outbound and return packages can be linked to one return case.
3. Risk rules determine required evidence depth.
4. Prepack, packed, received-return, and unpacked-return evidence are
   timestamped and access-controlled.
5. Damage cases distinguish manufacturing, packaging, outbound transit,
   customer handling, return packaging, and return transit when evidence
   supports the distinction.
6. Carrier scorecards include both outbound and return performance.
7. Claims link evidence, cost, outcome, and denial reason.
8. Packaging and carrier recommendations use relevant historical shipment
   profiles rather than carrier averages alone.
9. Missing evidence produces an unresolved state rather than automatic blame.
10. Return and damage cost feeds the customer-return and historical-part
    records.

## Out of Scope

- Continuous recording of all shop or customer activity.
- Automatic customer or employee blame from video.
- Guaranteeing carrier claim acceptance.
- Replacing qualified packaging, freight, insurance, quality, or legal review.

## References

- [Freight, Shipping, and Customer Delivery Questions](../../discovery/topics/freight-shipping-and-customer-delivery.md)
- [Lifecycle and Sustainability Questions](../../discovery/topics/lifecycle-and-sustainability.md)
- [Customer Returns and Acceptance Risk](customer-returns-and-acceptance-risk.md)
- [Historical-Part Analog Estimating](../manufacturing-automation/historical-part-analog-estimating.md)
- [Insurance and Risk Financing Questions](../../discovery/topics/insurance-and-risk-financing.md)
