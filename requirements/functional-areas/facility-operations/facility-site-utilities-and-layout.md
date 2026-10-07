# Facility Site, Utilities, and Layout

## Problem

Manufacturing equipment cannot be planned independently from the building.
Insufficient power, slab, access, ventilation, fire protection, utilities,
material flow, employee facilities, or expansion capacity can make a seemingly
inexpensive property unusable or force costly relocation.

## Objective

Derive phased physical-space requirements from the approved process and
equipment plan, screen candidate properties against mandatory gates, estimate
required improvements and operating constraints, and maintain an auditable
layout and utility model for the initial and target facility.

## Terminology

- **Initial facility:** Space required to launch the approved first capability.
- **Target facility:** Plausible year-two/year-three state under the approved
  growth scenario.
- **Utility schedule:** Equipment-by-equipment electrical, air, water, gas,
  exhaust, cooling, network, and other service requirements.
- **Block layout:** Scaled allocation of functional areas and major flow paths.
- **Mandatory gate:** Requirement whose absence disqualifies a site unless a
  feasible approved improvement resolves it.
- **Installed condition:** Building and utility state after required
  improvements, not merely the condition at property showing.

## Inputs

- phased business and capacity plan;
- equipment candidates, dimensions, weight, service clearance, and utilities;
- raw material, WIP, finished goods, packaging, and scrap profiles;
- process hazards and environmental controls;
- staffing, shifts, visitors, and customer access;
- receiving, shipping, vehicle, and rigging profiles;
- automation, robot, guarding, and material-presentation needs;
- inspection and metrology environment;
- IT, cameras, edge devices, and security;
- zoning, building, fire, environmental, accessibility, and insurer
  requirements;
- utility tariffs, capacities, upgrade cost, and lead time;
- employee-facility requirements; and
- expansion scenarios.

## Required Facility Zones

Model, where applicable:

- receiving and identification;
- incoming inspection and quarantine;
- raw stock;
- saw and material preparation;
- CNC machining;
- laser and tube cutting;
- welding and fabrication;
- additive manufacturing;
- deburr and cleaning;
- finishing and outside-process staging;
- inspection and metrology;
- assembly;
- nonconforming material;
- customer returns;
- tooling, maintenance, and spare parts;
- WIP;
- finished goods;
- packaging and shipping;
- scrap, waste, coolant, oil, gases, and chemicals;
- office, engineering, programming, quality, and meeting;
- IT, electrical, compressor, and utility;
- restroom, break, locker, first aid, and employee support;
- visitor and customer; and
- expansion reserve.

## Utility Schedule

For every equipment item, record:

- equipment ID and phase;
- footprint and service envelope;
- weight and point loading;
- anchoring, pit, or isolation;
- height and overhead clearance;
- rigging and removal path;
- voltage, phase, frequency, full-load current, startup demand, and connection;
- transformer and power-quality requirements;
- compressed-air pressure, flow, and quality;
- water flow and quality;
- drain and discharge;
- gas type, pressure, flow, storage, and piping;
- exhaust, mist, fume, dust, heat, and makeup air;
- cooling and environmental range;
- network and data;
- fire and life-safety controls;
- hazardous materials;
- operator and maintenance access; and
- future automation interface.

## Site and Building Gates

The screening process must address:

- permitted use and zoning;
- building occupancy and code status;
- environmental history and flood risk;
- structural condition;
- floor and foundation;
- clear height;
- power;
- compressed air feasibility;
- water, sewer, and stormwater;
- gases and chemicals;
- ventilation and HVAC;
- fire protection;
- truck, dock, drive-in, yard, and rigging access;
- egress and emergency response;
- security;
- employee and visitor facilities;
- parking and transportation;
- insurer acceptability;
- landlord or ownership approval;
- improvement cost and schedule; and
- expansion.

Mandatory failures must remain visible rather than being averaged away in a
weighted score.

## Layout Rules

1. Use scaled equipment footprints and service clearances.
2. Separate people and vehicle paths where practical.
3. Preserve egress, electrical, fire, and maintenance clearances.
4. Keep receiving, quarantine, raw material, process, inspection, packaging,
   and shipping identity controlled.
5. Separate clean, dirty, hot, fume, dust, chemical, confidential, and
   incompatible activities.
6. Provide practical movement for long stock, sheets, pallets, fixtures,
   machines, scrap, and finished parts.
7. Reserve safe space for guarding, robots, cameras, material presentation,
   and manual fallback.
8. Do not locate metrology where temperature, vibration, dirt, or traffic
   invalidates measurement.
9. Include housekeeping, waste, spill, and maintenance access.
10. Preserve expansion paths for utilities and material flow.

## Employee and Customer Facilities

Provide code-compliant and capacity-appropriate:

- restrooms;
- potable water;
- break and food area separated from process contamination;
- lockers, changing, shower, or laundry where required by work;
- first aid, eyewash, safety shower, and AED where required;
- accessible routes and facilities;
- parking and safe shift access;
- offices and collaboration;
- visitor control; and
- customer review or inspection space where justified.

## Candidate Facility Output

For each property provide:

- address and jurisdictions;
- availability date and commercial terms;
- usable and total square footage;
- scaled block layout;
- mandatory gate result;
- utility capacity and gaps;
- required improvements;
- permits and approvals;
- cost and lead time;
- operating constraints;
- employee and logistics assessment;
- expansion capacity;
- risks and unresolved evidence; and
- recommendation and approver.

## Failure Behavior

Reject or escalate a site when:

- use is prohibited or uncertain;
- required utilities cannot be provided;
- slab, structure, height, access, or egress is inadequate;
- process emissions or materials cannot be permitted safely;
- fire protection is inadequate with no feasible correction;
- machinery cannot be delivered, installed, serviced, or removed;
- employee facilities cannot meet requirements;
- improvement cost or schedule is unknown beyond approved tolerance;
- insurer requirements cannot be met; or
- a mandatory gate relies on an undocumented assumption.

## Acceptance Criteria

1. Initial and target square-footage scenarios identify every functional zone.
2. Every planned equipment item has a utility and installed-condition record.
3. Candidate properties are evaluated against mandatory gates before weighted
   scoring.
4. Power analysis includes voltage, phase, service capacity, demand, reserve,
   upgrade cost, and lead time.
5. Layouts include service clearances, egress, material flow, people/vehicle
   separation, receiving, quarantine, inspection, packaging, and waste.
6. Compressed air, water, sewer, gases, exhaust, HVAC, fire, network, and
   security requirements are explicit where applicable.
7. Restroom, break, accessibility, first-aid, and employee-support requirements
   use expected occupancy and shifts.
8. Improvement cost and schedule are included in site economics.
9. No site is recommended with an unresolved mandatory gate.
10. The approved layout and utility model are revision controlled as equipment
    and phases change.

## Out of Scope

- Final architectural, structural, electrical, mechanical, fire, civil, or
  environmental design by unqualified personnel.
- Assuming landlord statements replace utility, code, engineering, or permit
  verification.
- Treating listed future equipment as approved capital expenditure.
- Operating equipment before required permits, inspections, commissioning, and
  acceptance.

## References

- [Facility, Site, Utilities, and Layout Questions](../../discovery/topics/facility-site-utilities-and-layout.md)
- [Draft Business Vision and Phased Plan](../../discovery/business-vision-and-phased-plan.md)
- [Business Climate and Community Acceptance](../../discovery/topics/business-climate-and-community-acceptance.md)
- [Raw Materials, Energy, and Input Costs](../../discovery/topics/raw-materials-energy-and-input-costs.md)
- [Freight, Shipping, and Customer Delivery](../../discovery/topics/freight-shipping-and-customer-delivery.md)
- [Insurance and Risk Financing](../../discovery/topics/insurance-and-risk-financing.md)
