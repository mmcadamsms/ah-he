# Freight, Shipping, and Customer Delivery Questions

## Purpose

This topic tracks how the business's location, customer locations, part
characteristics, packaging, carrier network, and service level affect outbound
shipping cost and delivery performance across the United States.

The lowest manufacturing cost may not produce the lowest delivered customer
cost. Freight must be evaluated together with customer geography, material
sources, outside processes, lead time, damage risk, and reverse logistics.

## Question Supplied in Conversation

### LOG-001 — Where are shipping costs to customers high or low across the United States?

**State:** Open

**Original wording:** "shipping costs to customers, where are these high/low for different parts of US"

The eventual answer should:

- compare candidate shipping origins against actual customer-demand regions;
- distinguish parcel, regional courier, LTL, full truckload, air freight, rail,
  and customer pickup;
- model representative part sizes, weights, values, and service levels;
- include packaging and accessorial charges;
- account for dimensional weight, shipping zones, distance, fuel, rural or
  remote delivery, and carrier network structure;
- compare both cost and transit reliability; and
- identify whether one facility, multiple facilities, supplier-direct
  shipping, or distributed partners best fit the business.

## Part and Shipment Profiles

### LOG-002 — What representative shipment profiles should be modeled?

**State:** Open

At minimum, create scenarios for:

- a small machined part in a padded box;
- several dense steel parts in a reinforced parcel;
- a lightweight but bulky printed polymer part;
- a high-value precision part requiring custom foam;
- a corrosion-sensitive part;
- a long shaft or extrusion;
- a heavy fixture or assembly on a pallet;
- an oversized machine component;
- an urgent replacement part sent by air;
- a recurring multi-part production shipment; and
- a customer return or rework shipment.

For each scenario, record actual part weight, packaged weight, dimensions,
declared value, fragility, corrosion protection, destination type, service
level, and delivery commitment.

### LOG-003 — When does dimensional weight control instead of actual weight?

**State:** Open

This is especially important for additive parts, large lightweight prototypes,
protective packaging, and assemblies with substantial empty space.

### LOG-004 — When does dense metal make parcel shipping uneconomical or unsafe?

**State:** Open

Determine carrier weight limits, package-strength requirements, handling
hazards, box failure risk, and the crossover from parcel to multiple packages,
regional freight, or palletized LTL.

## Parcel Shipping

### LOG-005 — How do parcel shipping zones affect cost from each candidate location?

**State:** Open

Model ground and expedited service from candidate origins to:

- Northeast;
- Mid-Atlantic;
- Southeast;
- Great Lakes;
- Midwest;
- Texas and south-central states;
- Mountain West;
- Southwest;
- West Coast;
- Pacific Northwest;
- Alaska;
- Hawaii; and
- US territories where relevant.

### LOG-006 — Which candidate origins reach the largest customer share in one, two, or three ground days?

**State:** Open

Compare geographic centrality with actual customer-demand concentration rather
than population alone.

### LOG-007 — Which parcel surcharges materially change the result?

**State:** Open

Include:

- fuel;
- residential delivery;
- delivery-area and extended-area;
- remote or rural delivery;
- additional handling;
- oversize;
- large package;
- peak or demand;
- address correction;
- signature;
- declared value;
- pickup;
- Saturday or special delivery; and
- dimensional-weight adjustments.

### LOG-008 — Would negotiated carrier rates differ materially from public rates?

**State:** Open

Model startup volume, expected growth, minimum commitments, earned discounts,
dimensional profiles, service mix, and the value of using a shipping platform,
broker, association program, or third-party logistics provider.

### LOG-009 — When are regional parcel carriers or couriers advantageous?

**State:** Open

Compare service territory, pickup, delivery speed, pricing, tracking,
reliability, claims, rural coverage, and integration requirements.

## Freight and Large Parts

### LOG-010 — When should a shipment move by LTL?

**State:** Open

Determine crossover points based on weight, density, dimensions, palletization,
fragility, value, number of packages, and carrier limits.

### LOG-011 — How do freight class and density affect manufactured-part shipments?

**State:** Open

Establish correct classification for metal parts, machinery, fixtures,
assemblies, printed parts, tooling, and mixed shipments. Avoid relying on an
incorrect generic class that causes reclassification charges.

### LOG-012 — Which LTL accessorial charges must be modeled?

**State:** Open

Include:

- liftgate;
- limited access;
- residential;
- inside pickup or delivery;
- appointment;
- notification;
- detention;
- reweigh or reclassification;
- excessive length;
- overdimension;
- nonstackable;
- sort and segregate;
- storage;
- redelivery; and
- trade-show or construction-site service.

### LOG-013 — When are dedicated truck, hot-shot, or full-truckload services justified?

**State:** Open

Consider urgent plant downtime, oversized parts, high value, damage risk,
direct delivery, installation schedules, multiple pallets, and customer
penalties.

### LOG-014 — How do long, wide, tall, or heavy parts change the feasible customer geography?

**State:** Open

Include permits, escorts, route surveys, loading equipment, dock requirements,
rigging, special trailers, bridge and road restrictions, and destination
capability.

## Location and Network Design

### LOG-015 — Which US locations minimize weighted outbound cost to likely customers?

**State:** Open

Use the demand identified in:

- [Customer Demand and Market Geography](customer-demand-and-market-geography.md)

Weight destinations by expected order count, revenue, shipment profile,
urgency, and repeat rate. Do not optimize against geographic center or total
population alone.

### LOG-016 — How do coastal, central, border, and rural origins compare?

**State:** Open

Consider:

- distance to customer clusters;
- carrier hubs;
- airport cargo;
- interstate access;
- rail and ports;
- rural pickup;
- weather disruption;
- traffic;
- tolls;
- carrier competition; and
- same-day courier reach.

### LOG-017 — Is one central facility better than multiple regional facilities or partners?

**State:** Open

Compare:

- outbound freight;
- inbound materials;
- duplicated equipment;
- inventory;
- staffing;
- quality control;
- utilization;
- lead time;
- resilience;
- tax nexus;
- management complexity; and
- supplier-direct or partner-direct fulfillment.

### LOG-018 — When should work be produced near the customer?

**State:** Open

Potential triggers include urgent downtime, large or heavy parts, repeated
engineering visits, field measurement, installation, restricted data,
customer audits, or freight cost exceeding the value of centralized
production.

## Packaging and Damage

### LOG-019 — What packaging is required for each part type?

**State:** Open

Consider:

- corrosion prevention;
- impact and vibration;
- precision surfaces;
- sharp edges;
- threads and sealing surfaces;
- cleanliness;
- ESD;
- moisture;
- cosmetic finishes;
- loose components;
- orientation;
- stacking;
- lifting; and
- reusable containers.

### LOG-020 — How much does packaging add to delivered cost?

**State:** Open

Include material, labor, custom design, storage, dimensional-weight increase,
pallets, crates, foam, rust prevention, labels, documentation, disposal, and
returnable-packaging logistics.

### LOG-021 — How should freight damage and loss be valued?

**State:** Open

The loss is not limited to manufacturing cost. Include:

- remake cost;
- machine schedule disruption;
- material and outside-process replacement;
- expedited freight;
- customer downtime;
- missed milestones;
- claim administration;
- reputation; and
- limits of carrier liability.

### LOG-022 — When is declared-value coverage or separate cargo insurance needed?

**State:** Open

Compare carrier liability, declared value, third-party insurance, exclusions,
packaging requirements, claim evidence, deductibles, and high-value or unique
parts that cannot be replaced quickly.

## Delivery Time and Reliability

### LOG-023 — How should promised delivery account for carrier variability?

**State:** Open

Distinguish manufacturing completion from:

- pickup cutoff;
- origin processing;
- linehaul;
- weekends and holidays;
- weather;
- delivery appointment;
- remote destination;
- customs where applicable; and
- carrier guarantees and exclusions.

### LOG-024 — Which customer segments require same-day, next-day, or scheduled delivery?

**State:** Open

Compare prototypes, maintenance emergencies, line-down replacement parts,
planned production, tooling, regulated shipments, and ordinary replenishment.

### LOG-025 — How should expedited shipping be quoted?

**State:** Open

Determine whether expedited freight is:

- included;
- separately approved;
- prepaid and added;
- charged from an estimate;
- charged at actual cost;
- subject to a handling fee; or
- absorbed when delay is the business's responsibility.

## Returns and Reverse Logistics

### LOG-026 — How are customer returns, rework, inspection, and warranty shipments handled?

**State:** Open

Define authorization, packaging, labels, ownership, insurance, inbound
inspection, responsibility, replacement timing, and who pays freight.

### LOG-027 — How are reusable fixtures, gauges, containers, and customer property returned?

**State:** Open

Track serial numbers, condition, ownership, deposits, cycle time, cleaning,
maintenance, loss, and return scheduling.

## Taxes and Compliance

### LOG-028 — How do freight charges interact with sales tax?

**State:** Open

Taxability can depend on jurisdiction, contract terms, carrier, delivery method,
separate statement, transfer of title, and whether the underlying item is
taxable. Coordinate with:

- [Tax, Finance, and Capital](tax-finance-and-capital.md)

### LOG-029 — Which shipments require special regulatory handling?

**State:** Open

Consider hazardous materials, batteries, compressed gas, chemicals, export
controls, controlled technical data, wood packaging, unusual value, firearms
or defense articles where prohibited or regulated, and carrier-specific
restrictions.

## Cost Model and Data

### LOG-030 — What complete outbound-cost formula should be used?

**State:** Open

```text
packaging material
+ packaging labor
+ pickup or local delivery
+ carrier base charge
+ fuel
+ dimensional or weight charge
+ accessorials
+ declared value or insurance
+ shipping software or broker fees
+ taxes
+ expected damage and claim cost
+ return or reusable-container cost
= total customer delivery cost
```

### LOG-031 — What shipping data should the platform retain?

**State:** Open

Record:

- origin and destination postal codes;
- customer and address type;
- part and packaged dimensions;
- actual and dimensional weight;
- packaging type;
- carrier and service;
- quoted and actual charge;
- surcharges;
- promised and actual pickup and delivery;
- damage, loss, claim, and return;
- customer charge and business cost; and
- associated order, material, and production location.

### LOG-032 — How should shipping estimates be validated?

**State:** Open

Compare estimated and actual cost, transit, damage, and accessorials by carrier,
service, lane, package profile, customer type, season, and origin.

## Research Sources

Potential evidence includes:

- actual carrier and broker quotes;
- negotiated rate simulations;
- carrier service maps and rate guides;
- regional parcel carriers;
- LTL classification and tariff data;
- freight brokers and third-party logistics providers;
- airport cargo and trucking-market data;
- US Department of Transportation freight data;
- Census Commodity Flow Survey;
- fuel-price data;
- claims and damage history; and
- the customer-demand distribution developed in the market-geography topic.

Public maps and list rates are starting points. Final location decisions need
representative packaged shipments quoted from candidate origins to weighted
customer destinations.

## Expected Outputs

This topic may eventually produce:

- a parcel-zone and ground-transit map;
- weighted outbound-cost comparisons for candidate locations;
- parcel-versus-LTL crossover rules;
- representative shipment profiles;
- packaging standards and cost models;
- carrier and broker selection criteria;
- damage and insurance procedures;
- customer freight-pricing rules;
- reverse-logistics procedures; and
- formal shipping-estimate and tracking requirements.
