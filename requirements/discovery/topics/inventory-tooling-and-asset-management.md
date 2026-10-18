# Inventory, Tooling, and Asset Management Questions

## Purpose

This topic tracks unresolved decisions about raw-material inventory, tooling
life, tool assemblies, fixtures, shared tools, maintenance parts, equipment
assets, locations, custody, refurbishment, planning availability, and physical
inventory control. It also covers accountable movement of raw material,
customer-order work in process, finished goods, and future need-by planning.

These are discovery questions, not approved implementation decisions.
Question IDs are permanent and canonical in this file.

## Inventory Model and Control

### INV-001 — Which inventory classes must the internal system distinguish?

**State:** Exploring

**First-pass answer:** Distinguish, at minimum, raw stock and remnants;
consumables; cutting items; tool-assembly components and qualified assemblies;
fixtures; gauges; hand/portable tools; maintenance spares; equipment assets;
customer-, supplier-, leased-, rented-, and consigned property; WIP; finished
goods; scrap; recyclable material; and hazardous waste. Keep class, ownership,
condition, accounting treatment, and availability as separate attributes so
one object can change state without being reclassified.

**Evidence:**
- [FAR 52.245-1, Government Property](https://www.acquisition.gov/far/52.245-1) — **Evidence class:** primary official evidence. It distinguishes material, equipment, special tooling, special test equipment, production scrap, sensitive property, and contractor inventory and requires life-cycle stewardship.
- [Microsoft Dynamics 365 on-hand inventory](https://learn.microsoft.com/en-us/dynamics365/supply-chain/inventory/inventory-on-hand-list) — **Evidence class:** observed commercial implementation. It separates product, storage, tracking, physical, reserved, available, ordered, and financial dimensions rather than collapsing them into one quantity.

**Conflicting evidence:** ERP products use different class names and often mix
accounting and operational categories; the underlying distinctions matter more
than copying one vendor taxonomy.

**Confidence:** High on the minimum distinctions.

**Business-specific validation:** Map ten real examples from each shop area to
the proposed classes and verify ownership, availability, costing, and retention
behavior with operations, quality, and accounting.

**Second-opinion review:** GPT-5.4 — Agree. The sources support separating class, ownership, status, and financial treatment instead of one collapsed category.

### INV-002 — Which items require individual serialization versus lot, package, or bulk-quantity control?

**State:** Exploring

**First-pass answer:** Serialize assets and items whose identity, custody,
maintenance, calibration, warranty, remaining life, customer ownership, export
status, or failure history matters individually. Use lot/heat/batch control when
material or process provenance is shared; package/license-plate control for
sealed containers and pallets; and bulk quantity only for low-risk fungible
goods where individual or lot traceability adds no decision value.

**Evidence:**
- [FDA UDI basics](https://www.fda.gov/medical-devices/device-advice-comprehensive-regulatory-assistance/unique-device-identification-system-udi-system) — **Evidence class:** primary official evidence. UDI separates fixed device identity from production identifiers such as lot, serial, manufacture date, and expiration date and requires direct marking for some reusable items.
- [FAR 52.245-1, Government Property](https://www.acquisition.gov/far/52.245-1) — **Evidence class:** primary official evidence. Property records and controls scale with identity, sensitivity, custody, use, loss, and disposition obligations.

**Conflicting evidence:** Serialization improves accountability but creates
scan, label, migration, and count overhead; serializing cheap fungible items can
cost more than the risk it controls.

**Confidence:** High on the decision rule; item thresholds are business-
specific.

**Business-specific validation:** Score each item family for value, risk,
traceability, custody, service, calibration, and failure impact, then pilot the
resulting serial/lot/bulk rules in receiving and production.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The risk-based serial/lot/bulk rule is sound, but FDA UDI is a regulated exemplar, so thresholds remain shop-specific.

### INV-003 — Which identifiers should be supported: internal IDs, manufacturer part numbers, serial numbers, barcodes, QR codes, RFID tags, or another method?

**State:** Exploring

**First-pass answer:** Use an immutable internal ID as the system key and store
manufacturer part number, customer part/revision, supplier part, serial, lot,
heat, package/license plate, and asset tag as typed alternate identifiers.
Encode the stable identifier and needed production data in standards-based
barcode/2D symbols; add RFID only where no-line-of-sight or bulk reads prove
reliable and valuable. Never make a mutable description or supplier number the
primary key.

**Evidence:**
- [GS1 General Specifications](https://ref.gs1.org/standards/genspecs/) — **Evidence class:** consensus identification standard. GS1 defines interoperable item, location, asset, serial, lot, date, barcode, 2D, and RFID data structures.
- [FDA UDI basics](https://www.fda.gov/medical-devices/device-advice-comprehensive-regulatory-assistance/unique-device-identification-system-udi-system) — **Evidence class:** observed regulated implementation. It pairs human-readable and automatic-identification forms and separates fixed identity from variable production identifiers.

**Conflicting evidence:** Proprietary short codes can be easier inside one
shop, but they reduce interoperability and make supplier/customer relabeling
more likely.

**Confidence:** High on supporting multiple typed identifiers with one internal
key.

**Business-specific validation:** Test label size, coolant/abrasion resistance,
scanner range, duplicate handling, and supplier/customer identifier mapping on
representative stock, tools, WIP, bins, and assets.

**Second-opinion review:** GPT-5.4 — Agree. An immutable internal key plus typed alternate identifiers is the best-supported pattern for traceability and interoperability.

### INV-004 — Which units of measure and controlled conversions are needed for pieces, length, area, volume, weight, and containers?

**State:** Exploring

**First-pass answer:** Store a base unit and dimension for each item, permit
explicit effective-dated conversions for piece, length, area, volume, mass,
count-per-container, and density/yield, and retain transaction-entered units.
Allow exact conversions only where physically exact; treat piece-to-weight,
sheet-to-area, container contents, and density-based conversions as measured or
item/lot-specific with uncertainty and rounding rules. Do not mix mass and
force under a generic “weight” field.

**Evidence:**
- [NIST SI units—mass](https://www.nist.gov/pml/owm/si-units-mass) — **Evidence class:** primary official metrology guidance. NIST defines SI mass units and distinguishes mass from weight as force.
- [NIST Handbook 44, 2026 edition](https://www.nist.gov/pml/owm/nist-handbook-44-current-edition) — **Evidence class:** primary official metrology evidence. It sets specifications and tolerances for scales, automatic weighing, bulk weighing, and measuring devices used in commerce.

**Conflicting evidence:** Nominal catalog dimensions and densities simplify
planning but can materially differ from received or remnant quantity; measured
values should supersede nominal estimates without erasing provenance.

**Confidence:** High on the dimensional model.

**Business-specific validation:** Inventory current purchase, stocking,
production, and sales units; calibrate scales/measuring devices; and quantify
rounding/yield error on representative materials and containers.

**Second-opinion review:** GPT-5.4 — Agree. The dimensional base-unit model, exact-versus-measured conversions, and mass-versus-force distinction are well supported.

### INV-005 — What physical location hierarchy is required?

**State:** Exploring

**First-pass answer:** Use typed, effective-dated locations with parent/child
relationships: site, building, zone/room, rack, shelf/bin, cabinet, controlled
area, dock/staging, cart, machine, magazine, pocket/spindle, employee custody,
internal service, transit, and external vendor/customer. Allow an item to have
one confirmed present location plus planned destination and custody, and retain
location history; do not force external custody into a fake warehouse bin.

**Evidence:**
- [Microsoft Dynamics 365 on-hand inventory](https://learn.microsoft.com/en-us/dynamics365/supply-chain/inventory/inventory-on-hand-list) — **Evidence class:** observed commercial implementation. Inventory can be filtered and expanded by product, storage, and tracking dimensions down to warehouse location.
- [GS1 EPCIS 2.0](https://ref.gs1.org/standards/epcis/2.0.0/) — **Evidence class:** consensus traceability standard. EPCIS event models distinguish read point, business location, source, destination, object, and event time for physical visibility.

**Conflicting evidence:** Very fine locations improve search and control but
increase scan burden and stale-data risk; granularity should stop where workers
can reliably confirm it.

**Confidence:** High on the hierarchy and separation of location from custody.

**Business-specific validation:** Walk every material/tool flow, label proposed
nodes, and measure scan compliance and search time at bin, cart, machine, and
external-service granularity.

**Second-opinion review:** GPT-5.4 — Agree. A typed location hierarchy with separate custody and history is supported by both implementation and traceability standards.

### INV-006 — Which events must create immutable inventory transactions?

**State:** Exploring

**First-pass answer:** Record every quantity, identity, ownership, condition,
status, location, custody, reservation, cost, or genealogy change: receive,
accept/reject, move, reserve/release, issue/return, consume, split/merge,
transform, count/adjust, quarantine/release, refurbish, ship/receive from
service, scrap/recycle, lose/find, capitalize/retire, and correct/reverse. Store
actor, authorization, event/record time, source document, reason, before/after,
and evidence; correct with compensating events, never destructive edits.

**Evidence:**
- [GS1 EPCIS 2.0](https://ref.gs1.org/standards/epcis/2.0.0/) — **Evidence class:** consensus traceability standard. It models object, aggregation, transformation, transaction, source/destination, location, actor-context, and time events.
- [FAR 52.245-1, Government Property](https://www.acquisition.gov/far/52.245-1) — **Evidence class:** primary official evidence. It requires records and accountability from receipt through custody, use, consumption, movement, loss, inventory, and disposition.

**Conflicting evidence:** Event sourcing increases storage and implementation
complexity, but editable balances alone cannot explain discrepancies or
reconstruct genealogy.

**Confidence:** High on the event set and immutability principle.

**Business-specific validation:** Reconstruct several real receipts, cuts,
tool changes, external services, counts, and disposals from the proposed events
and verify that accounting and quality can audit them.

**Second-opinion review:** GPT-5.4 — Agree. Append-only lifecycle events are necessary for auditability, correction, and genealogy.

### INV-007 — How often should cycle counts and full physical inventories occur?

**State:** Exploring

**First-pass answer:** Count by risk and activity rather than one frequency:
high-value, safety/quality critical, regulated, theft-prone, or fast-moving
locations frequently; stable low-value stock less often; and trigger spot counts
after discrepancies, stockouts, location failures, or suspicious events. A full
physical inventory is still appropriate at startup, major migration/control
failure, auditor request, or when cycle-count accuracy is not demonstrated; a
mature validated cycle-count program may reduce disruptive wall-to-wall counts.

**Evidence:**
- [Microsoft Dynamics 365 cycle counting](https://learn.microsoft.com/en-us/dynamics365/supply-chain/warehousing/cycle-counting) — **Evidence class:** observed commercial implementation. It supports threshold-, plan-, item-, location-, and spot-triggered counts with pending-review variance workflow.
- [FAR 52.245-1, Government Property](https://www.acquisition.gov/far/52.245-1) — **Evidence class:** primary official evidence. Accountable property systems must perform, record, and disclose physical inventories at contract-required intervals and control discrepancies.

**Conflicting evidence:** Annual wall-to-wall counts are simple to govern but
can be less timely and more disruptive than continuous risk-based counting.

**Confidence:** High on risk-based cadence; exact frequencies need measured
accuracy and contractual input.

**Business-specific validation:** Establish ABC/criticality bands, baseline
record accuracy, and count labor, then adjust cadence until each band sustains
the required accuracy without excessive disruption.

**Second-opinion review:** GPT-5.4 — Agree. Risk-based cycle counting is better supported than a single fixed full-count cadence once controls are mature.

### INV-008 — Which count variances require investigation, approval, financial reconciliation, or security review?

**State:** Exploring

**First-pass answer:** Investigate any variance involving serialized,
customer-owned, regulated, hazardous, export-controlled, calibrated, critical,
or theft-attractive items, regardless of value. For ordinary stock, use both
absolute quantity/value and percentage thresholds, repeated-location/item
patterns, negative on-hand, unexplained genealogy breaks, and user/device
anomalies. Independent approval and accounting reconciliation should apply
above set materiality; security review applies to suspected loss, diversion,
tampering, or falsification.

**Evidence:**
- [Microsoft Dynamics 365 cycle counting](https://learn.microsoft.com/en-us/dynamics365/supply-chain/warehousing/cycle-counting) — **Evidence class:** observed commercial implementation. Count differences remain pending review until explicitly resolved.
- [FAR 52.245-1, Government Property](https://www.acquisition.gov/far/52.245-1) — **Evidence class:** primary official evidence. Sensitive property, loss, records, investigation, inventory, and relief of stewardship are controlled independently of ordinary quantity adjustment.

**Conflicting evidence:** Pure dollar materiality is efficient for financial
statements but can miss low-value safety, customer, export, or fraud signals.

**Confidence:** High on multi-factor escalation; numeric thresholds are
business- and contract-specific.

**Business-specific validation:** Accounting, quality, security, and operations
must set thresholds using item risk and actual variance history, then test them
against known loss and benign-error scenarios.

**Second-opinion review:** GPT-5.4 — Agree. Escalation should use risk and anomaly signals as well as value, not dollar thresholds alone.

### INV-009 — How should unknown identity, location, quantity, ownership, condition, or status be represented?

**State:** Exploring

**First-pass answer:** Represent each unknown explicitly as `Unknown`, not
blank, zero, “available,” or a guessed default. Create a temporary controlled
identity, quarantine availability, record who observed what and when, preserve
candidate values with confidence/evidence, assign an investigation owner and
deadline, and resolve through a new event. Unknown ownership or identity should
block use, shipment, capitalization, and disposal.

**Evidence:**
- [21 CFR 820.90, Control of nonconforming product](https://www.law.cornell.edu/cfr/text/21/820.90) — **Evidence class:** primary official evidence. Uncertain/nonconforming product must be identified, documented, evaluated, segregated, investigated as needed, and dispositioned by authorized personnel.
- [FAR 52.245-1, Government Property](https://www.acquisition.gov/far/52.245-1) — **Evidence class:** primary official evidence. Property accountability requires identity, records, custody, loss handling, and authorized disposition rather than silent assumptions.

**Conflicting evidence:** Temporary defaults keep work moving but can turn
uncertainty into false certainty; controlled exception queues are safer.

**Confidence:** High because the control pattern is supported by established standards; local parameters still require validation.

**Business-specific validation:** Run receiving, floor-found-item, mixed-remnant,
damaged-label, and disputed-ownership scenarios through the proposed unknown/
quarantine workflow and measure closure time.

**Second-opinion review:** GPT-5.4 — Agree. Explicit unknown and quarantine states are safer than blanks, zeroes, or guessed defaults.

### INV-010 — Which roles may receive, move, reserve, issue, adjust, refurbish, retire, or dispose of inventory?

**State:** Exploring

**First-pass answer:** Use least privilege and separation of duties: trained
receivers receive; warehouse/tool-crib staff move and issue; planners reserve;
quality controls acceptance, quarantine, and release; authorized service staff
refurbish; accounting/property management approves material adjustments and
retirement; and designated management/environmental/security roles approve
disposal. High-risk adjustments and disposals require a second approver; system
administrators must not grant themselves business approval.

**Evidence:**
- [NIST SP 800-53 Rev. 5](https://csrc.nist.gov/pubs/sp/800/53/r5/upd1/final) — **Evidence class:** primary official guidance. Its access-control, audit, identification, configuration, physical-protection, and separation-of-duty controls support role-limited transactions and review.
- [FAR 52.245-1, Government Property](https://www.acquisition.gov/far/52.245-1) — **Evidence class:** primary official evidence. It assigns stewardship, property-administration, inventory, loss, subcontractor, and disposition responsibilities across the property life cycle.

**Conflicting evidence:** Small shops need role overlap, but overlap should be
handled through documented compensating approval and review rather than broad
unlogged rights.

**Confidence:** High on role separation; staffing-specific assignments remain
open.

**Business-specific validation:** Build a RACI and permission matrix from the
actual organization, identify unavoidable conflicts, and test compensating
review with representative transactions.

**Second-opinion review:** GPT-5.4 — Agree. Least privilege and dual approval for high-risk adjustments fit the cited stewardship and control evidence.

## Raw Material and Stock

### INV-011 — Which attributes are mandatory for each supported stock form and material family?

**State:** Exploring

**First-pass answer:** Require internal item/stock-unit identity, ownership,
material family and grade/specification/revision, condition/temper, form,
nominal and measured dimensions, quantity/base unit, received weight where
useful, heat/lot/batch and certificate links when required, country of origin,
supplier/manufacturer, receipt/date, current condition/status/location, and
usable orientation or grain/direction. Add form-specific fields for bar, tube,
sheet, plate, casting, forging, powder, chemicals, and composites rather than
one unstructured description.

**Evidence:**
- [GS1 General Specifications](https://ref.gs1.org/standards/genspecs/) — **Evidence class:** consensus identification standard. It supports item, lot, serial, date, quantity, measure, location, and logistics identifiers without making descriptions the identity.
- [FAA Suspected Unapproved Parts Program](https://www.faa.gov/aircraft/safety/programs/sups) — **Evidence class:** primary official evidence. The program demonstrates the safety consequence of inadequate part identity, source, approval, and documentation in aerospace supply chains.

**Conflicting evidence:** Requiring every attribute for every commodity creates
false data and receiving delay; mandatory fields should be driven by stock form,
customer, material, and regulatory risk.

**Confidence:** High on the common core and typed extensions.

**Business-specific validation:** Build attribute templates from actual
drawings, purchase orders, certificates, and receiving records for every
planned material family.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Typed mandatory attributes are right, but the FAA source is a safety-critical exemplar, so exact fields stay family-specific.

### INV-012 — When is heat, melt, lot, batch, or mill-certificate traceability required?

**State:** Exploring

**First-pass answer:** Require it whenever the customer/order, drawing,
specification, code, regulator, special process, safety/quality risk, country-
of-origin rule, or root-cause/recall need demands material provenance. Preserve
the link through every split, remnant, issue, operation, nonconformance, and
shipment; for ordinary noncritical commercial material, supplier/item/receipt
traceability may be sufficient if the contract permits.

**Evidence:**
- [FDA UDI basics](https://www.fda.gov/medical-devices/device-advice-comprehensive-regulatory-assistance/unique-device-identification-system-udi-system) — **Evidence class:** primary official evidence. The implemented identification model conditionally carries lot, batch, serial, manufacture, and expiration data when production-level traceability matters.
- [FAA Suspected Unapproved Parts Program](https://www.faa.gov/aircraft/safety/programs/sups) — **Evidence class:** primary official evidence. FAA reporting and control of parts with questionable approval or origin supports preserving source and approval evidence for safety-critical work.

**Conflicting evidence:** Universal heat-level traceability reduces ambiguity
but adds segregation and transaction cost where no requirement or failure
consequence justifies it.

**Confidence:** High that the requirement is contract/risk driven.

**Business-specific validation:** Quality and contract review must map each
target market's material clauses to required identifiers, certificate content,
retention, and downstream marking.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Provenance should be contract-, specification-, and risk-driven; the cited regulated examples do not justify universal heat-level traceability.

### INV-013 — How are company-owned, customer-owned, supplier-owned, leased, rented, and consigned material separated?

**State:** Exploring

**First-pass answer:** Store legal owner, beneficial owner, custodian, contract/
order, liability terms, allowed uses, accounting treatment, and return/
disposition instructions independently from physical location. Use visible
labels and logical/physical segregation where mix-up risk exists; prevent
customer, consigned, leased, or rented property from being consumed, pledged,
counted as company inventory, or disposed under company-owned rules.

**Evidence:**
- [FAR 52.245-1, Government Property](https://www.acquisition.gov/far/52.245-1) — **Evidence class:** primary official evidence. It separates government-furnished, contractor-acquired, leased, material, equipment, tooling, scrap, custody, title, records, and disposition obligations.
- [Microsoft Dynamics 365 on-hand inventory](https://learn.microsoft.com/en-us/dynamics365/supply-chain/inventory/inventory-on-hand-list) — **Evidence class:** observed commercial implementation. Separate storage/tracking dimensions and transaction detail support ownership/status views without relying on physical co-location.

**Conflicting evidence:** Physical co-storage can be efficient when controls
are strong, while some contracts require dedicated segregation; the stricter
contract and mix-up risk govern.

**Confidence:** High because the control pattern is supported by established standards; local parameters still require validation.

**Business-specific validation:** Review intended lease, rental, consignment,
and customer-property agreements and test receiving, count, loss, consumption,
return, and bankruptcy/lien scenarios with counsel and accounting.

**Second-opinion review:** GPT-5.4 — Agree. Legal owner, custodian, and accounting treatment should remain separate from physical location.

### INV-014 — When should a job reserve a specific stock unit versus a fungible quantity?

**State:** Exploring

**First-pass answer:** Reserve a specific unit when heat/lot/certificate,
customer ownership, dimensions/orientation, measured condition, remnant shape,
expiration, qualification, location, or defect history affects usability.
Reserve fungible quantity when interchangeable units meet the same requirement
and allocation can occur at picking. Delay serial allocation when flexibility
is valuable, but never beyond the operation or certificate point that requires
genealogy.

**Evidence:**
- [Microsoft Dynamics 365 on-hand inventory](https://learn.microsoft.com/en-us/dynamics365/supply-chain/inventory/inventory-on-hand-list) — **Evidence class:** observed commercial implementation. It distinguishes physical, reserved, available, ordered, and dimension-specific quantities.
- [GS1 EPCIS 2.0](https://ref.gs1.org/standards/epcis/2.0.0/) — **Evidence class:** consensus traceability standard. Events can reference class-level quantities or individually/lot-identified objects and preserve later transformation genealogy.

**Conflicting evidence:** Early specific allocation protects critical jobs but
can strand stock and reduce global flexibility; fungible reservations can fail
if hidden dimensional or provenance differences exist.

**Confidence:** High on the decision rule.

**Business-specific validation:** Compare shortage and reallocation outcomes on
representative certified plate/bar, remnants, commodity stock, and customer-
owned material.

**Second-opinion review:** GPT-5.4 — Agree. Specific-unit reservation is warranted only when provenance, geometry, condition, or ownership affects usability.

### INV-015 — How are reservations prioritized, changed, released, and protected from double allocation?

**State:** Exploring

**First-pass answer:** Make reservations atomic and versioned against nettable
supply; rank them by approved business priority, committed need-by date,
operation criticality, customer promise, and substitution flexibility—not by
who clicked first alone. Require authorization and reason for displacement,
notify affected jobs, expire stale soft holds, release on cancellation/
completion, and prevent concurrent allocation with database constraints or
equivalent transactional locking.

**Evidence:**
- [Microsoft Dynamics 365 on-hand inventory](https://learn.microsoft.com/en-us/dynamics365/supply-chain/inventory/inventory-on-hand-list) — **Evidence class:** observed commercial implementation. It exposes physical reserved and available physical quantities derived from inventory transactions.
- [Microsoft Dynamics 365 master-planning architecture](https://learn.microsoft.com/en-us/dynamics365/supply-chain/master-planning/master-planning-architecture) — **Evidence class:** observed commercial implementation. Planning consumes master and transactional data and returns planned orders and pegging, requiring coherent reservation/supply state.

**Conflicting evidence:** Strict hard reservations protect promises but can
starve higher-value urgent work; unrestricted planner overrides recreate double
allocation and hidden schedule risk.

**Confidence:** High on controls; priority policy is a business decision.

**Business-specific validation:** Define priority tiers and simulate concurrent
quotes/orders, shortages, cancellations, substitutions, and manager overrides
before selecting lock and approval rules.

**Second-opinion review:** GPT-5.4 — Agree. Atomic, versioned reservations and controlled displacement are necessary to prevent hidden double allocation.

### INV-016 — How should cutting a bar, sheet, plate, tube, or other stock create and identify a remnant?

**State:** Exploring

**First-pass answer:** Treat cutting as a transformation event that consumes or
reduces the parent, creates one or more uniquely identified remnants, and
inherits material, heat/lot, certificate, ownership, and cost provenance. Record
measured post-cut dimensions/shape, orientation, quantity/weight, location,
condition, cut event/job/operator/machine, and any unusable kerf/scrap; print a
durable label immediately and require confirmation before availability.

**Evidence:**
- [GS1 EPCIS 2.0](https://ref.gs1.org/standards/epcis/2.0.0/) — **Evidence class:** consensus traceability standard. Transformation events link inputs and outputs while preserving event time, location, and business context.
- [FAR 52.245-1, Government Property](https://www.acquisition.gov/far/52.245-1) — **Evidence class:** primary official evidence. Material consumption, production scrap, records, custody, and disposition remain accountable throughout use.

**Conflicting evidence:** Estimating remnant size from the nesting program is
fast, but measured dimensions are more credible after kerf, edge cleanup,
distortion, and handling.

**Confidence:** High because the control pattern is supported by established standards; local parameters still require validation.

**Business-specific validation:** Pilot labels and measurement on bar, sheet,
plate, and tube cuts; reconcile parent input to parts, remnants, kerf, and scrap
by quantity and cost.

**Second-opinion review:** GPT-5.4 — Agree. Treating a cut as a transformation with inherited provenance and immediate remnant identity is well supported.

### INV-017 — What minimum remnant dimensions, value, provenance, or expected reuse justify retention?

**State:** Exploring

**First-pass answer:** Retain a remnant only when it is safely storable and
identifiable, has required provenance, meets a material/form-specific minimum,
and its expected reuse value exceeds labeling, handling, storage, count, search,
and eventual disposal cost. Use demand history and upcoming jobs to estimate
reuse probability; apply shorter review periods to common cheap stock and more
generous thresholds to expensive, long-lead, certified, or customer material.

**Evidence:**
- [Microsoft Dynamics 365 on-hand inventory](https://learn.microsoft.com/en-us/dynamics365/supply-chain/inventory/inventory-on-hand-list) — **Evidence class:** observed commercial implementation. Dimension-specific on-hand, reserved, available, and transaction detail can support aging and actual-use analysis.
- [FAR 52.245-1, Government Property](https://www.acquisition.gov/far/52.245-1) — **Evidence class:** primary official evidence. Production scrap, excess contractor inventory, customer ownership, records, and authorized disposition can constrain what may simply be discarded.

**Conflicting evidence:** Fixed size cutoffs are easy to administer but can
discard high-value rare stock or retain common low-value clutter.

**Confidence:** Medium; no universal economic cutoff exists.

**Business-specific validation:** Measure twelve months of remnant creation,
retrieval, reuse, storage space, labor, and scrap revenue, then set thresholds
by material/form/value/provenance class.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. No public evidence supports a universal remnant cutoff, so local reuse economics must set retention thresholds.

### INV-018 — How are quarantined, nonconforming, corroded, contaminated, expired, damaged, or unidentified materials controlled?

**State:** Exploring

**First-pass answer:** Immediately identify and block them from reservation,
issue, shipment, and accidental mixing; move them to controlled physical or
virtual quarantine; record defect/uncertainty, source, affected quantity,
evidence, owner, and deadline; and permit release, rework, downgrade, return,
scrap, or use-as-is only through documented authorized disposition. Preserve
the original status and all subsequent decisions.

**Evidence:**
- [21 CFR 820.90, Control of nonconforming product](https://www.law.cornell.edu/cfr/text/21/820.90) — **Evidence class:** primary official evidence. It requires identification, documentation, evaluation, segregation, investigation as needed, authorized disposition, justification, and re-evaluation after rework.
- [Microsoft Dynamics 365 quality and nonconformance management](https://learn.microsoft.com/en-us/dynamics365/supply-chain/inventory/quality-management-processes) — **Evidence class:** observed commercial implementation. It can block inventory during inspection, generate quality orders, track corrections, and retain nonconformance history/KPIs.

**Conflicting evidence:** Virtual blocking reduces material handling, but
physical segregation is safer where labels can fail or mix-up consequences are
high.

**Confidence:** High because the control pattern is supported by established standards; local parameters still require validation.

**Business-specific validation:** Quality must define defect classes,
quarantine zones, authority, maximum age, retest/rework criteria, and emergency
use-as-is approval, then challenge the controls with pick/issue tests.

**Second-opinion review:** GPT-5.4 — Agree. Controlled quarantine plus authorized disposition is well supported for nonconforming or uncertain material.

### INV-019 — Which storage conditions, segregation rules, shelf-life controls, and preservation methods apply to each material?

**State:** Exploring

**First-pass answer:** Derive controls from the manufacturer safety data sheet,
material specification, customer requirements, and law. Maintain a material-
specific profile for temperature/humidity, corrosion protection, contamination,
UV/light, fire/chemical compatibility, ventilation, load/rack limits, FIFO/
FEFO, shelf-life start and extension, inspection frequency, packaging, and
hazard segregation; monitor conditions where excursion could affect safety or
conformance.

**Evidence:**
- [OSHA 29 CFR 1910.1200, Hazard Communication](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.1200) — **Evidence class:** primary official evidence. Hazard classification, labels, safety data sheets, employee information, and training govern chemical-material handling and storage.
- [EPA hazardous-waste basics](https://www.epa.gov/hw/learn-basics-hazardous-waste) — **Evidence class:** primary official guidance. Hazardous materials that become waste enter a distinct identification, accumulation, storage, transport, and disposal regime.

**Conflicting evidence:** Supplier shelf life may be extendable through testing,
but extension cannot be assumed where a specification, customer, or regulator
requires fixed expiration or approved requalification.

**Confidence:** High on the method; profiles are material-specific.

**Business-specific validation:** An EHS/quality professional must review every
planned material/SDS/specification and inspect proposed storage, monitoring,
fire-code, spill, and waste controls with local authorities as needed.

**Second-opinion review:** GPT-5.4 — Agree. Storage and shelf-life controls should come from SDS, specification, customer, and regulatory requirements, not generic rules.

### INV-020 — How should reorder points account for planned demand, lead time, minimum orders, volatility, carrying cost, and shortage risk?

**State:** Exploring

**First-pass answer:** Set reorder triggers from demand during replenishment
lead time plus safety stock for demand/lead-time uncertainty, then constrain the
order by minimum/order multiple, shelf life, storage, cash/carrying cost,
substitution, and shortage consequence. Separate forecast, committed jobs,
maintenance demand, and strategic/critical spares; recalculate from actual
consumption, supplier performance, and service targets rather than fixed tribal
knowledge.

**Evidence:**
- [Microsoft Dynamics 365 master-planning architecture](https://learn.microsoft.com/en-us/dynamics365/supply-chain/master-planning/master-planning-architecture) — **Evidence class:** observed commercial implementation. Master planning combines master and transaction data and returns planned orders, pegging, warnings, and frequent recalculation.
- [Microsoft Dynamics 365 on-hand inventory](https://learn.microsoft.com/en-us/dynamics365/supply-chain/inventory/inventory-on-hand-list) — **Evidence class:** observed commercial implementation. Physical, reserved, available, ordered, and forecast/financial transactions provide the supply-position inputs a reorder policy needs.

**Conflicting evidence:** Higher safety stock improves service but ties up cash
and can create obsolete or expired inventory; criticality and shortage cost
justify different targets.

**Confidence:** High on the model; parameters require local data.

**Business-specific validation:** Fit lead-time and demand distributions from
purchase/usage history, price shortages and carrying cost with finance/
operations, and back-test stockouts and excess under candidate policies.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The replenishment model is sound, but reorder parameters depend on local demand and lead-time distributions.

## Tooling and Tool Assemblies

### INV-021 — Which machine-shop items are classified as tooling, consumables, reusable components, fixtures, gauges, or equipment?

**State:** Exploring

**Original wording:** "'tooling' in machine shops represents bits, endmills,
inserts, taps, dies, and essentially anything that is used to accomplish the
customer job"

**First-pass answer:** Classify by function and life cycle, not colloquial
“tooling”: replaceable cutters/inserts/abrasives and process fluids are
consumables; holders, collets, arbors, boring heads, and modular bodies are
reusable tool components; a measured cutter-plus-holder is a qualified assembly;
part-locating/clamping devices are fixtures/workholding; measurement devices
are gauges; and durable powered or capital items are equipment. One catalog
family may contain multiple classes, so classification belongs at item level.

**Evidence:**
- [ISO 13399 cutting-tool data model](https://www.iso.org/obp/ui/#iso:std:iso:13399:-1:ed-2:v1:en) — **Evidence class:** consensus standard. It distinguishes cutting items, tool items, adaptive items, and assemblies through structured properties and relationships.
- [FAR 52.245-1, Government Property](https://www.acquisition.gov/far/52.245-1) — **Evidence class:** primary official evidence. It distinguishes material, equipment, special tooling, and special test equipment based on identity, consumption, and function.

**Conflicting evidence:** Shops often expense all low-cost items as “tooling,”
but accounting treatment should not erase operational differences in custody,
qualification, life, or calibration.

**Confidence:** High because the control pattern is supported by established standards; local parameters still require validation.

**Business-specific validation:** Classify a representative crib export with
manufacturing, quality, maintenance, and accounting and document disputed
families and thresholds.

**Second-opinion review:** GPT-5.4 — Agree. Function and life cycle are better classification anchors than the colloquial label 'tooling.'

### INV-022 — How should the system represent a qualified tool assembly and the lineage of its components?

**State:** Exploring

**First-pass answer:** Represent a qualified assembly as a versioned instance
with its own ID and status, linked to serialized or lot-controlled cutter,
holder, collet, pull stud, extension, insert edges, and preset/inspection
records. Record build/disassembly events, component positions, measured gauge
length/diameter/runout/balance, offsets, machine/operation qualification, and
effective time; changing a controlled component creates a new assembly revision
or requalification event rather than silently editing history.

**Evidence:**
- [ISO 13399 cutting-tool data model](https://www.iso.org/obp/ui/#iso:std:iso:13399:-1:ed-2:v1:en) — **Evidence class:** consensus standard. It provides structured component and assembly data for interoperable cutting-tool descriptions.
- [MTConnect Part 4, Devices Information Model](https://www.mtconnect.org/s/MTConnect-Part-4-Devices-Information-Model-20.pdf) — **Evidence class:** manufacturing interoperability standard. It models device components and cutting-tool data that can connect assembly identity and condition to machine observations.

**Conflicting evidence:** Treating every inexpensive insert as serialized may
be excessive; lot plus indexed-edge history can be sufficient unless customer,
failure, or cost risk demands unit identity.

**Confidence:** High on assembly/version lineage.

**Business-specific validation:** Model several real milling, drilling, boring,
turning, and driven-tool assemblies and verify presetter, CAM, and control data
can round-trip without losing identity or measurements.

**Second-opinion review:** GPT-5.4 — Agree. Versioned qualified assemblies with component lineage are necessary for measurement and traceability integrity.

### INV-023 — Which tool geometry, holder, gauge length, runout, balance, offset, coating, grade, and compatibility data must be retained?

**State:** Exploring

**First-pass answer:** Retain the data needed to select, simulate, assemble,
measure, load, and safely run the tool: standard/manufacturer IDs; cutter and
connection geometry; holder/adapter stack; usable and stick-out lengths;
diameter/corner/rake/flute/insert/edge data; grade/coating; coolant capability;
hand/orientation; mass and balance class where speed requires it; measured gauge
length/diameter/runout; offsets/wear; machine/spindle/turret/pocket limits; and
approved materials/operations. Separate nominal catalog values from measured
assembly and machine-specific values.

**Evidence:**
- [ISO 13399 cutting-tool data model](https://www.iso.org/obp/ui/#iso:std:iso:13399:-1:ed-2:v1:en) — **Evidence class:** consensus standard. It standardizes cutting-tool and component properties for exchange among catalogs, CAM, presetters, and management systems.
- [OPC UA for Machine Tools](https://reference.opcfoundation.org/specs/OPC-40501-1) — **Evidence class:** consensus interoperability standard. It provides a machine-tool information model for exchanging configuration and operational data with connected systems.

**Conflicting evidence:** Capturing every vendor property creates maintenance
burden; only safety-, compatibility-, planning-, or process-relevant fields
should be mandatory by tool type.

**Confidence:** High on the categories.

**Business-specific validation:** Compare fields required by selected CAM,
presetters, machines, catalogs, and inspection systems and define a minimum
profile for each tool family.

**Second-opinion review:** GPT-5.4 — Agree. The answer correctly separates nominal catalog data from measured assembly- and machine-specific values.

### INV-024 — Which useful-life measures apply to each tooling type?

**State:** Exploring

**First-pass answer:** Use one or more exposure measures plus observed condition:
cutting time, path length, material removed, part/feature count, starts or
impacts, spindle/load/energy exposure, temperature, indexed edges, grind/
recoat cycles, measured wear/runout, dimensional drift, surface finish,
inspection result, breakage, and calendar/storage age. Inserts may use edge-
specific counts and wear; solid tools cutting time/material removal; holders
runout, crashes, cycles, and inspection; gauges calibration and use/environment.

**Evidence:**
- [NASA Prognostics Data Repository—Milling data](https://www.nasa.gov/intelligent-systems-division/discovery-and-systems-health/pcoe/pcoe-data-set-repository/) — **Evidence class:** empirical research data. The milling data vary speed, feed, and depth of cut and record insert flank wear, showing life depends on operating conditions and measured degradation.
- [MTConnect Part 3, Streams Information Model](https://www.mtconnect.org/s/MTConnect-Part-3-Streams-Information-Model-20.pdf) — **Evidence class:** manufacturing interoperability standard. Time-series machine observations can supply execution, load, condition, and event evidence rather than relying only on calendar age.

**Conflicting evidence:** Simple part counts are easy and often useful, but they
misstate life when engagement, material, parameters, or interrupted cutting
vary.

**Confidence:** High on multi-measure life tracking; predictive accuracy is
operation-specific.

**Business-specific validation:** Instrument representative tools and compare
candidate exposure measures against measured wear, dimensions, finish, and
failure over enough complete lives.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Multi-measure life tracking is right, but public evidence does not prove every signal helps every tool family.

### INV-025 — How is an initial expected-life model established for a new tool and operation?

**State:** Exploring

**First-pass answer:** Start with the toolmaker's conservative application range
and CAM process plan, then run controlled trials on the actual material,
machine, holder, coolant, workholding, engagement, tolerance, and finish.
Inspect at planned intervals, define failure and quality limits before the test,
record censored and failed lives, set an initial conservative replacement limit,
and update only from repeatable production evidence; do not infer unattended-
run reliability from one successful part.

**Evidence:**
- [NASA Prognostics Data Repository—Milling data](https://www.nasa.gov/intelligent-systems-division/discovery-and-systems-health/pcoe/pcoe-data-set-repository/) — **Evidence class:** empirical research data. Controlled milling experiments vary operating parameters and record measured wear, demonstrating the type of data needed for an initial model.
- [ISO 3685, Tool-life testing with single-point turning tools](https://www.iso.org/standard/42891.html) — **Evidence class:** consensus test standard. It formalizes controlled tool-life testing and failure criteria, although the detailed standard is not freely reproduced.

**Conflicting evidence:** Vendor recommendations accelerate setup but are not a
production guarantee; shop trials are more specific but can be too small to
estimate tail failures.

**Confidence:** High on the method; low on life until local trials exist.

**Business-specific validation:** Approve a trial protocol, sample size,
inspection method, stop criteria, and conservative release rule for the first
operations selected for automation.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Controlled local trials are required; vendor ranges and ISO 3685 alone do not establish unattended-run reliability.

### INV-026 — Which machine, CAM, inspection, sensor, and operator evidence should update remaining tool life?

**State:** Exploring

**First-pass answer:** Update from verified executed cutting time/path and
material engagement from machine/CAM; actual spindle/feed/load/energy, alarms,
overrides, vibration/acoustic/temperature where validated; in-process probing
and post-process dimensions/surface finish; presetter/runout and wear
measurements; breakage detection; material/lot and coolant state; and structured
operator observations. Weight evidence by measurement reliability and preserve
raw observations, model version, and manual decisions.

**Evidence:**
- [MTConnect Part 3, Streams Information Model](https://www.mtconnect.org/s/MTConnect-Part-3-Streams-Information-Model-20.pdf) — **Evidence class:** manufacturing interoperability standard. It defines time-stamped observations/events/conditions that can connect machine execution to analytics.
- [NASA Prognostics Data Repository—Milling data](https://www.nasa.gov/intelligent-systems-division/discovery-and-systems-health/pcoe/pcoe-data-set-repository/) — **Evidence class:** empirical research data. Measured wear under different cutting conditions supports data-driven updating but also shows operating context must accompany sensor values.

**Conflicting evidence:** High-frequency sensors promise earlier warning but can
produce false alarms and machine-specific models; direct inspection is slower
but often better ground truth.

**Confidence:** Medium-high; usefulness of each signal must be validated.

**Business-specific validation:** Correlate each candidate data source with
ground-truth wear, quality drift, and failures and define missing/bad-data and
sensor-calibration rules.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Remaining-life updates should use executed conditions and inspection results, but each signal needs local validation against wear and failure.

### INV-027 — How should tool-life models differ by work material, operation, machine, holder, cutting conditions, coolant, tolerance, and finish?

**State:** Exploring

**First-pass answer:** Maintain a hierarchical model keyed to tool/edge and
operation context, with separate parameters or correction factors for material/
hardness/lot, roughing versus finishing, machine/spindle, holder/stick-out/
runout, speed/feed/engagement, coolant/dry/MQL, interrupted cuts, workholding,
tolerance, and finish. Pool evidence only after testing that contexts behave
comparably; otherwise a global average will hide damaging combinations.

**Evidence:**
- [NASA Prognostics Data Repository—Milling data](https://www.nasa.gov/intelligent-systems-division/discovery-and-systems-health/pcoe/pcoe-data-set-repository/) — **Evidence class:** empirical research data. Its milling experiments explicitly vary speed, feed, and depth of cut and measure resulting flank wear.
- [ISO 3685, Tool-life testing](https://www.iso.org/standard/42891.html) — **Evidence class:** consensus test standard. Controlled life testing holds and reports cutting conditions because life is not a context-free property of a tool.

**Conflicting evidence:** More segmentation improves physical relevance but can
leave too little data per model; hierarchical pooling with conservative
uncertainty is preferable to either one global model or hundreds of unsupported
models.

**Confidence:** High on context dependence; model form remains experimental.

**Business-specific validation:** Analyze production trials using these
covariates, test out-of-sample error and failure tails, and merge contexts only
when performance supports it.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Context-specific models are necessary, but segmentation must be balanced against sparse data.

### INV-028 — Who may override a tool-life estimate, and what evidence and reason must be retained?

**State:** Exploring

**First-pass answer:** Only authorized manufacturing engineering, qualified
tool-room/quality personnel, or a designated supervisor should override; an
operator may stop early for observed risk but should not extend unattended life
without approval. Record old/new limit, tool/assembly/operation, actor and
approver, time, reason code, measurements/photos/inspection or trial evidence,
affected jobs, risk assessment, temporary/permanent scope, and model version;
never overwrite prior estimates.

**Evidence:**
- [NIST SP 800-53 Rev. 5](https://csrc.nist.gov/pubs/sp/800/53/r5/upd1/final) — **Evidence class:** primary official guidance. Least privilege, separation of duties, identification, audit, and change control support controlled overrides and attributable records.
- [21 CFR 820.90, Control of nonconforming product](https://www.law.cornell.edu/cfr/text/21/820.90) — **Evidence class:** primary official evidence. Use of nonconforming product requires documented justification and signature of authorizing individuals, a useful analogue for life extensions beyond normal limits.

**Conflicting evidence:** Experienced operators can recognize context the model
misses, but undocumented intuition prevents learning and can normalize risky
life extension.

**Confidence:** High because the control pattern is supported by established standards; local parameters still require validation.

**Business-specific validation:** Define role and approval thresholds, then
review historical early changes, life extensions, breakages, and unattended
runs to create reason/evidence codes.

**Second-opinion review:** GPT-5.4 — Agree. Tool-life overrides need attributable evidence, explicit scope, and controlled approval.

### INV-029 — How accurately can the system know that tooling is in a particular machine, magazine pocket, spindle, crib, cart, or setup area?

**State:** Exploring

**First-pass answer:** It can know to transaction-level confidence, not absolute
truth: machine/control integration can report pocket/tool states, while crib,
cart, and setup areas require scan or automated observation. Store last
confirmed location, confirming source/time, confidence, custody, and conflicting
observations. Pocket-level accuracy is achievable only when every load/unload,
manual swap, recovery, and control-table change is captured and periodically
reconciled.

**Evidence:**
- [MTConnect Part 4, Devices Information Model](https://www.mtconnect.org/s/MTConnect-Part-4-Devices-Information-Model-20.pdf) — **Evidence class:** manufacturing interoperability standard. It models machine components and cutting tools for machine-reported state and identity.
- [GS1 EPCIS 2.0](https://ref.gs1.org/standards/epcis/2.0.0/) — **Evidence class:** consensus traceability standard. Time-, location-, source-, and destination-aware events support last-observed location with provenance rather than an unexplained static field.

**Conflicting evidence:** RFID/vision can reduce manual scans, but metal,
coolant, occlusion, tag damage, and ambiguous reads prevent assuming perfect
physical truth without site testing.

**Confidence:** Medium-high on design; achievable accuracy depends on process
discipline and interfaces.

**Business-specific validation:** Pilot on one machine/crib/cart flow and
measure pocket/location precision, stale records, false reads, and recovery from
manual swaps over several weeks.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. 'Last confirmed with confidence' is the right model; perfect location truth is unrealistic without disciplined capture.

### INV-030 — How are manual moves reconciled with machine-reported tooling state?

**State:** Exploring

**First-pass answer:** Treat both as attributed observations. Require scan/
confirmation for planned manual load, unload, and transfer; ingest machine
state with device time and sequence; match by tool/assembly and pocket; and
create an exception when identity, location, or timing conflicts. Freeze
uncertain tools from unattended commitment, have an authorized person inspect
and reconcile, and resolve through correcting events that retain both original
reports.

**Evidence:**
- [MTConnect Part 3, Streams Information Model](https://www.mtconnect.org/s/MTConnect-Part-3-Streams-Information-Model-20.pdf) — **Evidence class:** manufacturing interoperability standard. Time-stamped machine observations/events/conditions provide the machine side of reconciliation.
- [GS1 EPCIS 2.0](https://ref.gs1.org/standards/epcis/2.0.0/) — **Evidence class:** consensus traceability standard. Event time, record time, read point, source/destination, and business context permit late and conflicting physical events to be retained and resolved.

**Conflicting evidence:** Declaring the control always authoritative is simple
but fails after manual swaps or stale tables; declaring scans authoritative
misses unscanned machine actions.

**Confidence:** High on reconciliation logic.

**Business-specific validation:** Intentionally perform correct, missed,
duplicate, late, and reversed tool moves on a pilot machine and verify detection,
quarantine, and recovery.

**Second-opinion review:** GPT-5.4 — Agree. Manual and machine observations should both be retained and reconciled by exception, not by overwriting one source.

### INV-031 — What happens to component history when an insert is indexed or replaced?

**State:** Exploring

**First-pass answer:** Indexing closes the prior edge-use interval and opens the
next edge while retaining insert lot/serial, holder/assembly, operation,
exposure, observed wear, reason, actor, time, and offsets. Replacement removes
the old insert from the assembly, records disposition or retained failure
sample, installs the new insert identity/lot, and requires the checks defined
for that assembly; neither action should reset holder or body history.

**Evidence:**
- [ISO 13399 cutting-tool data model](https://www.iso.org/obp/ui/#iso:std:iso:13399:-1:ed-2:v1:en) — **Evidence class:** consensus standard. Structured cutting-item, tool-item, and assembly relationships support edge/component history instead of treating the assembly as indivisible.
- [GS1 EPCIS 2.0](https://ref.gs1.org/standards/epcis/2.0.0/) — **Evidence class:** consensus traceability standard. Transformation and object events preserve component changes, time, place, and business context.

**Conflicting evidence:** Lot-level insert tracking is usually more economical
than unit serialization, but repeated unexplained breakage may justify temporary
unit-level investigation.

**Confidence:** High because the control pattern is supported by established standards; local parameters still require validation.

**Business-specific validation:** Model turning and indexable milling examples,
including partial edge use, edge damage, insert return, mixed lots, and failure
analysis.

**Second-opinion review:** GPT-5.4 — Agree. Insert indexing and replacement should roll edge history forward without resetting holder or body history.

### INV-032 — What happens to assembly qualification and offsets when a cutter or holder component changes?

**State:** Exploring

**First-pass answer:** Any geometry-, connection-, balance-, runout-, or
identity-affecting change invalidates the prior measured assembly state. Rebuild
or revise the assembly, verify component compatibility/cleanliness/torque,
remeasure gauge length and diameter/runout, rebalance where required, regenerate
or confirm offsets and simulation envelope, and requalify the intended
machine/operation to a risk-based level before release.

**Evidence:**
- [ISO 13399 cutting-tool data model](https://www.iso.org/obp/ui/#iso:std:iso:13399:-1:ed-2:v1:en) — **Evidence class:** consensus standard. Assembly geometry derives from identified components and relationships, so component change changes the represented assembly.
- [Renishaw, Ballbar testing explained](https://www.renishaw.com/en/ballbar-testing-explained--6818) — **Evidence class:** supplier technical evidence. Measured machine behavior can differ from nominal; by analogy, measured assembly state should not be carried across an uncontrolled physical change.

**Conflicting evidence:** Like-for-like insert indexing may require only edge/
wear-offset handling, while cutter, holder, adapter, or stick-out changes
usually require full remeasurement.

**Confidence:** High; exact requalification depth is risk-specific.

**Business-specific validation:** Define a component-change matrix showing
required preset, runout, balance, offset, simulation, probe, and test-cut checks
for every assembly family.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The conclusion is right, but one citation is analogical; remeasurement depth should remain risk-based by component change.

### INV-033 — Which tooling can be reground, recoated, resharpened, repaired, or otherwise refurbished?

**State:** Exploring

**First-pass answer:** Candidate tooling includes solid-carbide/HSS drills,
reamers, end mills, taps, saws, broaches, selected indexable bodies, holders,
chucks, and some fixtures when the OEM or qualified specialist can restore and
verify required geometry, substrate/coating, connection, balance, and strength.
Do not refurbish single-use inserts, cracked/deformed bodies, unknown material,
or safety-critical items without an approved repair specification and economic
case.

**Evidence:**
- [Sandvik Coromant tool reconditioning](https://www.sandvik.coromant.com/en-us/services/tool-reconditioning) — **Evidence class:** supplier operating evidence. A major cutting-tool OEM offers controlled regrinding/recoating services, demonstrating commercial refurbishment for eligible tools.
- [Kennametal reconditioning services](https://www.kennametal.com/us/en/services/reconditioning-services.html) — **Evidence class:** supplier operating evidence. OEM reconditioning supports selected tooling but remains bounded by tool design, condition, and restoration capability.

**Conflicting evidence:** Independent regrinders can be economical and capable,
while OEM programs may better preserve original geometry/data; qualification
should be evidence-based rather than source-name alone.

**Confidence:** Medium-high; eligibility is tool- and damage-specific.

**Business-specific validation:** Obtain written eligibility, restored
tolerances, coating, cycle limit, inspection, price, and turnaround from at
least two qualified providers for the planned tool families.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Refurbishment is real for eligible tool families, but vendor program evidence does not generalize to all tooling.

### INV-034 — How many refurbishment cycles are allowed, and how do they change geometry, capability, remaining life, and cost?

**State:** Exploring

**First-pass answer:** Do not set one universal count. Establish an OEM/vendor-
and tool-family maximum constrained by remaining substrate, usable length/
diameter, flute/edge geometry, connection integrity, balance, coating removal,
and repair history; measure each return and reject it when it no longer meets
the approved capability. Record cycle count, material removed, resulting
geometry, approved applications, expected life, actual life, and cumulative
cost.

**Evidence:**
- [Sandvik Coromant tool reconditioning](https://www.sandvik.coromant.com/en-us/services/tool-reconditioning) — **Evidence class:** supplier operating evidence. Reconditioning restores eligible tools through controlled service, but eligibility and repeated restoration depend on remaining physical condition.
- [NASA Prognostics Data Repository—Milling data](https://www.nasa.gov/intelligent-systems-division/discovery-and-systems-health/pcoe/pcoe-data-set-repository/) — **Evidence class:** empirical research data. Tool wear changes with cutting conditions, so a refurbish count alone cannot establish remaining production life.

**Conflicting evidence:** Vendor programs may advertise “like-new” performance,
but that claim must be verified for the actual geometry, coating, material, and
operation rather than assumed across cycles.

**Confidence:** Medium; public evidence does not support a universal cycle cap.

**Business-specific validation:** Collect provider limits and inspect/test each
cycle on representative operations; retire when capability, expected life, or
cumulative cost fails the approved threshold.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Refurbish limits are tool-family and geometry dependent; public evidence does not support a universal cycle cap.

### INV-035 — How is tooling tracked while waiting in an internal refurbishment queue?

**State:** Exploring

**First-pass answer:** Move the item to a nonavailable internal-service status
and queue location with service request, reason/defect, requested operation,
priority, responsible technician, planned completion, required inspection,
component identity, and pre-service geometry/life. Record each handoff and work
step; do not leave it “on hand” for planning until post-service qualification
releases it.

**Evidence:**
- [GS1 EPCIS 2.0](https://ref.gs1.org/standards/epcis/2.0.0/) — **Evidence class:** consensus traceability standard. Source/destination and business-process events support custody and state transitions through an internal service flow.
- [Microsoft Dynamics 365 quality and nonconformance management](https://learn.microsoft.com/en-us/dynamics365/supply-chain/inventory/quality-management-processes) — **Evidence class:** observed commercial implementation. Blocked inventory, correction tasks, responsible work, and historical dispositions demonstrate an implementable exception/service queue.

**Conflicting evidence:** A simple red-tag rack may work at low volume, but it
does not provide reliable due dates, ownership, shortages, or component
genealogy as queues grow.

**Confidence:** High because the control pattern is supported by established standards; local parameters still require validation.

**Business-specific validation:** Run actual sharpen/repair jobs through intake,
queue, work, inspection, release, and scrap paths and measure lost items,
turnaround, and planning visibility.

**Second-opinion review:** GPT-5.4 — Agree. A nonavailable queue state plus service-trail tracking is the right control for internal refurbishment.

### INV-036 — How is tooling tracked while shipped to, held by, serviced by, and returned from an external refurbishment vendor?

**State:** Exploring

**First-pass answer:** Create an external-service order and custody chain:
identify each tool/lot and assembly state; record pack/ship/carrier/tracking,
declared value and ownership; vendor receipt/acceptance, quote, work scope,
subcontract restrictions, status and promised return; then return receipt,
quantity/condition, certificates, measurements, invoice, and discrepancies.
Availability remains “external service,” not on hand, until qualified release.

**Evidence:**
- [FAR 52.245-1, Government Property](https://www.acquisition.gov/far/52.245-1) — **Evidence class:** primary official evidence. Stewardship and records extend to property held by subcontractors and through shipment, loss, repair, inventory, and disposition.
- [GS1 EPCIS 2.0](https://ref.gs1.org/standards/epcis/2.0.0/) — **Evidence class:** consensus traceability standard. Shipping, receiving, source/destination, and transformation events preserve cross-enterprise custody and state.

**Conflicting evidence:** Lot shipping reduces transaction work but can obscure
individual high-value tools and partial returns; use lot versus serial tracking
according to value and failure/geometry needs.

**Confidence:** High because the control pattern is supported by established standards; local parameters still require validation.

**Business-specific validation:** Test the workflow with candidate vendors,
including vendor loss, partial acceptance, rejected repair, unexpected
subcontracting, split return, customs/export, and certificate mismatch.

**Second-opinion review:** GPT-5.4 — Agree. External refurbishment needs an explicit custody chain and off-hand status until requalified release.

### INV-037 — What inspection and qualification are required before refurbished tooling returns to production?

**State:** Exploring

**First-pass answer:** Verify identity and service certificate, visual condition,
dimensions/profile/edge prep, coating, connection and threads, runout/
concentricity, balance for high-speed use, hardness or nondestructive test where
repair risk warrants, and compatibility with the intended assembly. Preset and
inspect the rebuilt assembly and use a first-piece/test-cut or reduced initial
life limit until local evidence shows refurbished performance is equivalent.

**Evidence:**
- [21 CFR 820.80, Acceptance activities](https://www.law.cornell.edu/cfr/text/21/820.80) — **Evidence class:** primary official evidence. Incoming and in-process product must be inspected, tested, or otherwise verified and documented before release.
- [Sandvik Coromant tool reconditioning](https://www.sandvik.coromant.com/en-us/services/tool-reconditioning) — **Evidence class:** supplier operating evidence. OEM reconditioning is a controlled process, but production release still depends on the customer's required geometry and application.

**Conflicting evidence:** Vendor certificates can justify reduced receiving
inspection after demonstrated performance, but they do not replace checks for
shipping damage, wrong identity, or application-specific capability.

**Confidence:** High on risk-based requalification.

**Business-specific validation:** Define acceptance measurements and initial
life derating per tool family, then compare new versus refurbished capability,
life, and failure modes.

**Second-opinion review:** GPT-5.4 — Agree. Vendor certificates can reduce effort, but production release still needs identity and capability verification.

### INV-038 — When should worn tooling be refurbished, downgraded to less demanding work, discarded, recycled, or retained for failure analysis?

**State:** Exploring

**First-pass answer:** Refurbish when restoration is technically approved and
expected avoided-new cost exceeds service, logistics, inspection, downtime, and
risk. Downgrade only with a documented application envelope and identity that
prevents return to higher-demand work. Retain unusual breakage, repeated
failure, safety events, or costly quality escapes for analysis; otherwise
recycle recoverable materials or dispose under EHS rules when repair is unsafe
or uneconomic.

**Evidence:**
- [Sandvik Coromant tool reconditioning](https://www.sandvik.coromant.com/en-us/services/tool-reconditioning) — **Evidence class:** supplier operating evidence. Commercial reconditioning establishes a viable recovery path for eligible tools.
- [21 CFR 820.90, Control of nonconforming product](https://www.law.cornell.edu/cfr/text/21/820.90) — **Evidence class:** primary official evidence. Nonconforming items require documented evaluation, investigation as needed, authorized disposition, and re-evaluation after rework.

**Conflicting evidence:** Refurbishment lowers purchase cost and material use,
but variable turnaround or shortened life can make new tooling cheaper for
critical operations.

**Confidence:** High on the disposition framework; thresholds need local cost
and reliability data.

**Business-specific validation:** Calculate total cost and quality performance
for new, refurbished, downgraded, recycled, and failed-analysis paths by tool
family and establish approval rules.

**Second-opinion review:** GPT-5.4 — Agree. Disposition should depend on technical recoverability, economics, and failure-learning value.

### INV-039 — How are approved substitute tools selected when the preferred tool is unavailable or lacks sufficient remaining life?

**State:** Exploring

**First-pass answer:** Select only from an engineering-approved equivalence set
that matches connection/envelope, geometry and reach, material/operation,
cutting data, coolant, balance/speed, machine/CAM compatibility, tolerance/
finish capability, and enough supported life. Recalculate collision, feeds/
speeds, offsets, cycle time, quality plan, and job economics; require first-
piece verification when the substitution can affect output.

**Evidence:**
- [ISO 13399 cutting-tool data model](https://www.iso.org/obp/ui/#iso:std:iso:13399:-1:ed-2:v1:en) — **Evidence class:** consensus standard. Structured geometry, connection, component, and cutting properties permit rule-based comparison rather than text-name substitution.
- [OPC UA for Machine Tools](https://reference.opcfoundation.org/specs/OPC-40501-1) — **Evidence class:** consensus interoperability standard. Machine capability and tool-state data can be checked with planning/CAM requirements before release.

**Conflicting evidence:** Catalog similarity can suggest candidates but does not
prove process capability or collision safety; approved equivalence must be
operation-specific.

**Confidence:** High because the control pattern is supported by established standards; local parameters still require validation.

**Business-specific validation:** Build and trial substitute sets for the most
critical tools, including CAM recalculation, first-piece acceptance, life, and
cycle-time impact.

**Second-opinion review:** GPT-5.4 — Agree. Substitutions should come from engineering-approved equivalence, not catalog similarity alone.

### INV-040 — How much supported remaining life must a tool have before it can be committed to a job or unattended run?

**State:** Exploring

**First-pass answer:** Require predicted consumption for the assigned operation/
run plus a validated uncertainty and recovery margin; the margin should be
larger for unattended work, costly workpieces, weak detection, long tool-change
recovery, unstable material, or severe failure consequences. If the lower
confidence bound does not cover the safe intervention point, replace/index the
tool or split the run—do not use the mean estimate as a guarantee.

**Evidence:**
- [NASA Prognostics Data Repository—Milling data](https://www.nasa.gov/intelligent-systems-division/discovery-and-systems-health/pcoe/pcoe-data-set-repository/) — **Evidence class:** empirical research data. Measured wear varies with cutting conditions, supporting uncertainty-aware rather than fixed universal life commitments.
- [MTConnect Part 3, Streams Information Model](https://www.mtconnect.org/s/MTConnect-Part-3-Streams-Information-Model-20.pdf) — **Evidence class:** manufacturing interoperability standard. Machine execution and condition observations can update consumed exposure and support intervention logic.

**Conflicting evidence:** Conservative replacement reduces breakage and scrap
but can waste useful life; better sensing and stable processes can justify a
smaller margin only after measured false-negative and failure performance.

**Confidence:** Medium; no universal percentage is defensible.

**Business-specific validation:** For each unattended operation, measure life
distribution and detection/recovery performance, price failure consequence, and
approve a lower-bound coverage rule.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Lower-bound life coverage is the right rule, but safety margins must be validated per operation and recovery consequence.

## Tools, Fixtures, Spare Parts, and Equipment

### INV-041 — Which hand tools, portable tools, lifting accessories, fixtures, gauges, and shared shop items require custody tracking?

**State:** Exploring

**First-pass answer:** Track custody for items whose loss, misuse, overdue
inspection, calibration, safety condition, customer ownership, export/security
status, high value, scarcity, or production criticality matters. Serialize
lifting accessories, calibrated gauges, powered portable tools, customer
fixtures, and high-value shared tooling; lower-risk common hand tools may use
kit, cabinet, or area accountability rather than individual checkout.

**Evidence:**
- [FAR 52.245-1, Government Property](https://www.acquisition.gov/far/52.245-1) — **Evidence class:** primary official evidence. Custody, sensitive property, records, physical inventory, loss, maintenance, and disposition drive accountable control.
- [NIST SP 800-53 Rev. 5](https://csrc.nist.gov/pubs/sp/800/53/r5/upd1/final) — **Evidence class:** primary official guidance. Physical protection, access, identification, and audit controls support risk-based custody and attributable checkout.

**Conflicting evidence:** Individual tracking improves accountability but can
cause checkout avoidance for cheap high-volume tools; cabinet or kit-level
control can be more reliable for those items.

**Confidence:** High on risk-based scope.

**Business-specific validation:** Inventory shared items, score failure/loss
impact and checkout burden, and pilot serial versus kit/cabinet control with
measured loss and search time.

**Second-opinion review:** GPT-5.4 — Agree. Custody tracking should be risk-based rather than universal serial checkout for every shared item.

### INV-042 — How are fixture ownership, compatible parts and revisions, storage, inspection, maintenance, and retirement tracked?

**State:** Exploring

**First-pass answer:** Give each fixture/configuration an ID and owner; link
design/CAD, compatible part and revision ranges, setup instructions, datums,
clamping limits, machine envelope, qualification results, components/spares,
storage location, custody, maintenance/inspection history, damage/repair,
customer/order restrictions, and status. Configuration changes require revision
and requalification; retirement must consider customer return, retained service
parts, record retention, and destruction of controlled IP.

**Evidence:**
- [FAR 52.245-1, Government Property](https://www.acquisition.gov/far/52.245-1) — **Evidence class:** primary official evidence. Special tooling and customer property require ownership, records, use, care, subcontractor, inventory, loss, and disposition controls.
- [GS1 EPCIS 2.0](https://ref.gs1.org/standards/epcis/2.0.0/) — **Evidence class:** consensus traceability standard. Object identity, location, custody, transformation, and business context can preserve fixture movement and configuration events.

**Conflicting evidence:** Family fixtures may support many revisions, but
unbounded “compatible” status risks misapplication; compatibility should be
explicit and effective-dated.

**Confidence:** High because the control pattern is supported by established standards; local parameters still require validation.

**Business-specific validation:** Model fixture histories for new, revised,
repaired, customer-owned, modular, and obsolete examples and obtain customer/
engineering approval for retirement rules.

**Second-opinion review:** GPT-5.4 — Agree. Fixtures need identity, compatibility, maintenance, and retirement history, especially when ownership varies.

### INV-043 — Which gauges and tools require calibration or periodic inspection before they are considered available?

**State:** Exploring

**First-pass answer:** Calibration is required for measuring equipment used to
accept product or control a process where measurement accuracy matters;
periodic inspection is required for safety-related lifting/torque/protective
tools, fixtures and holders whose wear affects output, and tools with mandated
manufacturer/customer intervals. Availability must consider current calibration/
inspection, correct range/uncertainty, condition, environmental suitability,
and any usage or post-damage check—not merely an unexpired sticker.

**Evidence:**
- [NIST Calibration Services](https://www.nist.gov/calibrations) — **Evidence class:** primary official metrology evidence. NIST provides traceable measurement services supporting calibration chains for measurement equipment.
- [NIST Handbook 44, 2026 edition](https://www.nist.gov/pml/owm/nist-handbook-44-current-edition) — **Evidence class:** primary official evidence. It defines specifications, tolerances, and test requirements for commercial weighing/measuring devices.

**Conflicting evidence:** Fixed calendar intervals are easy to administer, but
usage, stability history, environment, damage, and measurement risk may justify
shorter or longer evidence-based intervals.

**Confidence:** High on the availability gate; intervals are instrument- and
use-specific.

**Business-specific validation:** Quality/metrology must create an equipment
register, measurement-risk analysis, calibration source/uncertainty rules, and
interval-review process using actual as-found data.

**Second-opinion review:** GPT-5.4 — Agree. Availability should depend on valid calibration or inspection plus suitability, not sticker date alone.

### INV-044 — Which equipment assets belong in the inventory system versus a connected maintenance or fixed-asset system?

**State:** Exploring

**First-pass answer:** Keep one shared asset identity and ownership/location/
status spine. Inventory should manage receipt, custody, movement, availability,
parts and operational relationship; maintenance should own work orders,
condition, preventive maintenance, downtime, and service history; fixed assets
should own capitalization, book/tax basis, depreciation, impairment, and
disposal accounting. Avoid duplicate independent asset masters and synchronize
authoritative fields and effective dates.

**Evidence:**
- [FAR 52.245-1, Government Property](https://www.acquisition.gov/far/52.245-1) — **Evidence class:** primary official evidence. It distinguishes equipment/property stewardship and operational records across receipt, custody, use, maintenance, inventory, and disposition.
- [IRS Publication 946](https://www.irs.gov/publications/p946) — **Evidence class:** primary official tax guidance. Depreciation, class life, placed-in-service, basis, and disposition are accounting/tax concerns distinct from operational availability.

**Conflicting evidence:** A small business may initially use one product, but
logical domain ownership is still needed so maintenance facts and depreciation
facts are not conflated.

**Confidence:** High because the control pattern is supported by established standards; local parameters still require validation.

**Business-specific validation:** Accounting, maintenance, and operations must
assign system-of-record fields for a sample fleet and reconcile asset addition,
transfer, rebuild, component replacement, impairment, and retirement.

**Second-opinion review:** GPT-5.4 — Agree. One shared asset identity with separate operational, maintenance, and accounting ownership is the right boundary.

### INV-045 — How are machine configuration, options, manuals, warranties, maintenance, downtime, and spare parts linked to an equipment asset?

**State:** Exploring

**First-pass answer:** Use a serialized asset record with effective-dated
configuration baseline: manufacturer/model/serial, control/firmware, licensed
options, utilities, safety configuration, manuals/drawings/backups, warranty/
service contracts, installed components, meters, maintenance plans/work orders,
failures/downtime, and a spare-parts BOM with compatibility and criticality.
Changes create configuration events and preserve prior state.

**Evidence:**
- [Rockwell Product Compatibility and Download Center](https://www.rockwellautomation.com/en-us/support/product/product-compatibility-migration.html) — **Evidence class:** OEM lifecycle evidence. It exposes firmware/software, compatibility, dependencies, release notes, and lifecycle status needed for a maintained configuration baseline.
- [OPC UA for Machine Tools](https://reference.opcfoundation.org/specs/OPC-40501-1) — **Evidence class:** consensus interoperability standard. A common machine-tool information model supports linking configuration and operational state to external maintenance and inventory systems.

**Conflicting evidence:** Vendor portals contain useful current data but may
change or disappear; the business should retain licensed documentation,
backups, and configuration evidence it is entitled to keep.

**Confidence:** High because the control pattern is supported by established standards; local parameters still require validation.

**Business-specific validation:** Build complete records for two representative
machines and test whether a technician can restore configuration, identify
parts, check warranty, and analyze downtime without tribal knowledge.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Effective-dated configuration and spare linkage are right, though the implementation evidence is mostly vendor and interoperability guidance.

### INV-046 — Which maintenance spares are critical enough to reserve for equipment recovery?

**State:** Exploring

**First-pass answer:** Reserve a spare when failure stops a bottleneck or safety/
environmental function and expected downtime/loss exceeds carrying and
obsolescence cost, especially for long-lead, single-source, failure-prone, or
end-of-life parts. Use failure modes, installed population, lead-time
distribution, repairability, commonality, condition monitoring, service
response, and acceptable outage to set quantity; protect emergency stock from
routine consumption without authorization.

**Evidence:**
- [NIST SP 800-161 Rev. 1](https://doi.org/10.6028/NIST.SP.800-161r1-upd1) — **Evidence class:** primary official guidance. Supply-chain resilience requires identifying, assessing, and mitigating reliability, availability, counterfeit, and supplier risks across the life cycle.
- [Rockwell Product Compatibility and Download Center](https://www.rockwellautomation.com/en-us/support/product/product-compatibility-migration.html) — **Evidence class:** OEM lifecycle evidence. Lifecycle and compatibility information helps identify spares exposed to obsolescence and migration risk.

**Conflicting evidence:** Stocking every possible spare is unaffordable and can
create obsolete inventory; vendor stocking or redundant capacity may be a
better mitigation for some failures.

**Confidence:** High on the criticality method; quantities need local data.

**Business-specific validation:** Perform FMEA and downtime-cost analysis on
planned bottleneck assets and obtain lead-time/service/repair quotes before
setting emergency-spare policy.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Critical-spare policy should be downtime- and risk-based; the public evidence is methodological rather than transactional.

### INV-047 — How are checked-out, loaned, rented, employee-held, lost, stolen, damaged, or off-site tools and equipment controlled?

**State:** Exploring

**First-pass answer:** Record item, custodian, purpose/job, destination,
condition, expected return, authorization, deposit/contract where applicable,
and checkout/return evidence. Escalate overdue items; block damaged or expired
items; record loss/theft with search, security/insurance/customer notification
and financial disposition; and use a formal transfer/acceptance event for
custody changes. Rented/loaned property remains separately owned and governed by
its agreement.

**Evidence:**
- [FAR 52.245-1, Government Property](https://www.acquisition.gov/far/52.245-1) — **Evidence class:** primary official evidence. It defines loss to include missing, theft, damage, and destruction and requires custody, protection, records, investigation, and authorized relief/disposition.
- [NIST SP 800-53 Rev. 5](https://csrc.nist.gov/pubs/sp/800/53/r5/upd1/final) — **Evidence class:** primary official guidance. Identification, physical access, audit, incident response, and asset protection controls support attributable off-site custody and loss handling.

**Conflicting evidence:** Employee self-service checkout reduces crib labor but
needs reliable identity, evidence, and exception review to avoid anonymous
custody.

**Confidence:** High because the control pattern is supported by established standards; local parameters still require validation.

**Business-specific validation:** Define due-date, escalation, loss-reporting,
police/insurance/customer, payroll/legal, and damaged-return procedures with HR,
security, counsel, and operations.

**Second-opinion review:** GPT-5.4 — Agree. Attributable checkout, return, loss, and off-site custody controls are well supported.

## Planning, Purchasing, Cost, and Integration

### INV-048 — At what planning stage must material, tooling, fixtures, gauges, and equipment be reserved?

**State:** Exploring

**First-pass answer:** Use staged commitment: quotations create nonbinding,
probability-weighted demand; accepted orders create capacity/material protection
as policy allows; released jobs hard-reserve long-lead or uniquely qualified
resources; and operation dispatch commits the exact stock unit, assembly,
fixture, gauge, and machine. Reserve scarce/unique resources earlier than common
fungible items, and expire or release holds when probability, date, or scope
changes.

**Evidence:**
- [Microsoft Dynamics 365 master-planning architecture](https://learn.microsoft.com/en-us/dynamics365/supply-chain/master-planning/master-planning-architecture) — **Evidence class:** observed commercial implementation. Planning combines master/transaction data and produces planned orders, pegging, warnings, and frequent replanning.
- [Microsoft Dynamics 365 on-hand inventory](https://learn.microsoft.com/en-us/dynamics365/supply-chain/inventory/inventory-on-hand-list) — **Evidence class:** observed commercial implementation. It distinguishes forecasted, physical, financial, reserved, available, and ordered inventory states.

**Conflicting evidence:** Reserving at quote protects responsiveness but can
starve committed work; waiting until release can expose long-lead shortages.

**Confidence:** High on staged commitment; stage boundaries require business
policy.

**Business-specific validation:** Simulate recent quote-to-order histories and
resource bottlenecks to set probability, lead-time, scarcity, and expiration
rules.

**Second-opinion review:** GPT-5.4 — Agree. Staged reservation authority fits the different confidence levels of quote, order, release, and dispatch.

### INV-049 — How should quoting and scheduling distinguish on-hand, reserved, unqualified, on-order, at-refurbishment, substitutable, and unavailable inventory?

**State:** Exploring

**First-pass answer:** Expose separate dated supply states and confidence:
physically on-hand/nettable; on-hand but reserved; quarantined/unqualified;
purchase or internal order with promised/confirmed date; external/internal
refurbishment with expected qualified return; engineering-approved substitute;
and unavailable/unknown. Quotes may use risk-weighted projected supply;
released schedules should use only nettable qualified supply or an explicitly
approved recovery action.

**Evidence:**
- [Microsoft Dynamics 365 on-hand inventory](https://learn.microsoft.com/en-us/dynamics365/supply-chain/inventory/inventory-on-hand-list) — **Evidence class:** observed commercial implementation. It separately displays physical, reserved, available, ordered, and dimension-specific inventory.
- [Microsoft Dynamics 365 quality and nonconformance management](https://learn.microsoft.com/en-us/dynamics365/supply-chain/inventory/quality-management-processes) — **Evidence class:** observed commercial implementation. Inventory can be blocked during inspection and released only through quality processes.

**Conflicting evidence:** Collapsing all expected supply into one ATP number
simplifies quoting but hides qualification and date risk; too many states can
confuse users unless presented as clear availability tiers.

**Confidence:** High because the control pattern is supported by established standards; local parameters still require validation.

**Business-specific validation:** Recreate availability decisions for recent
shortages and test whether sales, planning, and production interpret each state
consistently.

**Second-opinion review:** GPT-5.4 — Agree. Quotes and schedules need distinct supply states and confidence levels, not one flattened availability number.

### INV-050 — Which shortages should trigger purchasing, internal manufacture, transfer, refurbishment, substitution review, or schedule changes?

**State:** Exploring

**First-pass answer:** Trigger a dated action when projected qualified
availability falls below time-phased demand plus required buffer. Choose
purchase for standard external supply, internal manufacture for approved make
items with capacity, transfer for earlier surplus, refurbishment for eligible
recoverable tools, substitution for engineering-approved equivalents, and
schedule/customer escalation when no feasible action meets need-by at acceptable
cost/risk. Show the affected operation and promise, not only the item shortage.

**Evidence:**
- [Microsoft Dynamics 365 master-planning architecture](https://learn.microsoft.com/en-us/dynamics365/supply-chain/master-planning/master-planning-architecture) — **Evidence class:** observed commercial implementation. Planned orders, pegging, messages, and warnings are generated from current supply/demand data.
- [Microsoft Dynamics 365 on-hand inventory](https://learn.microsoft.com/en-us/dynamics365/supply-chain/inventory/inventory-on-hand-list) — **Evidence class:** observed commercial implementation. Available, reserved, ordered, and forecast transaction detail provides the basis for shortage detection.

**Conflicting evidence:** Automatically selecting the cheapest action can
ignore qualification, capacity, schedule, and failure risk; automation should
propose, with approval for consequential changes.

**Confidence:** High on trigger logic; action rules need local lead-time/cost
data.

**Business-specific validation:** Back-test the decision tree on historical
stockouts and have procurement, planning, engineering, and operations adjudicate
the recommended actions.

**Second-opinion review:** GPT-5.4 — Agree. Shortages should trigger dated alternatives tied to the affected operation and customer promise.

### INV-051 — How should the system choose between buying new tooling, replacing an insert, refurbishing an item, renting, borrowing, manufacturing a tool, or redesigning the process?

**State:** Exploring

**First-pass answer:** Compare feasible alternatives on need-by, qualified
capability, expected useful output, reliability, setup/cycle impact, acquisition
and logistics, inspection/qualification, downtime, ownership/custody, residual
value, and failure consequence. Use cost per expected conforming part or
available production hour, not purchase price; redesign when recurring tooling
cost, availability, or failure risk dominates and a process change has positive
life-cycle value.

**Evidence:**
- [Sandvik Coromant tool reconditioning](https://www.sandvik.coromant.com/en-us/services/tool-reconditioning) — **Evidence class:** supplier operating evidence. Refurbishment is a real commercial alternative for eligible tools but introduces turnaround and requalification.
- [Treasury Regulation 26 CFR 1.263(a)-2](https://www.law.cornell.edu/cfr/text/26/1.263%28a%29-2) — **Evidence class:** primary official evidence. Acquisition and transaction costs for tangible property reinforce full-cost rather than sticker-price comparison.

**Conflicting evidence:** New tooling can minimize process risk, while
refurbished or borrowed tooling can win economically for low utilization; the
answer changes with need-by and failure cost.

**Confidence:** High on the framework.

**Business-specific validation:** Run the model on several high-spend and
shortage-prone tools using actual quotes, life, quality, turnaround, and
downtime.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Full-cost comparison is the right framework, but local life, downtime, and recovery data determine the winning option.

### INV-052 — How are actual material, tooling, consumable, refurbishment, scrap, and disposal costs assigned to jobs?

**State:** Exploring

**First-pass answer:** Post immutable job/operation transactions for specific
material issues and returns, actual consumed quantity, proportional remnant/
scrap yield, inserts/consumables used, tool-life consumption or event cost,
external/internal refurbishment, nonconformance/rework, recycling credits, and
hazardous disposal. Use consistent cost layers and allocation rules for shared
tools; preserve operational quantities separately from accounting valuation and
reconcile variances rather than editing history.

**Evidence:**
- [IRS Publication 538](https://www.irs.gov/publications/p538) — **Evidence class:** primary official tax/accounting guidance. Inventory methods and consistent accounting treatment constrain how inventory cost flows are recognized.
- [FAR 52.245-1, Government Property](https://www.acquisition.gov/far/52.245-1) — **Evidence class:** primary official evidence. Unit acquisition cost, material consumption, scrap, records, and disposition establish an auditable property/cost trail.

**Conflicting evidence:** Precise per-tool-life allocation can improve job
economics but may cost more to capture than the variance for low-value
consumables; use materiality-based methods.

**Confidence:** High on transaction categories; accounting policy needs CPA
review.

**Business-specific validation:** Reconcile several completed jobs from
physical issues through invoices, payroll/overhead, scrap receipts, and general
ledger with accounting.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Immutable cost events are right, but allocation detail should follow materiality and accounting policy.

### INV-053 — How should operational inventory records reconcile with accounting inventory, fixed assets, depreciation, and cost of goods sold?

**State:** Exploring

**First-pass answer:** Maintain a controlled mapping from operational item/
asset/event IDs to accounting items, locations, cost layers, accounts, and
capital assets. Reconcile quantity and value on a defined close cadence; explain
receipts not invoiced, WIP, issues/returns, scrap, adjustments, consignment/
customer property, capitalization, placed-in-service, depreciation, impairment,
and retirement with dated entries. Operational corrections create posted
adjustments, not retroactive deletion.

**Evidence:**
- [IRS Publication 538](https://www.irs.gov/publications/p538) — **Evidence class:** primary official tax/accounting guidance. It addresses inventories, accounting periods/methods, and consistency.
- [IRS Publication 946](https://www.irs.gov/publications/p946) — **Evidence class:** primary official tax guidance. Depreciable basis, placed-in-service, recovery, and disposition apply to capital assets rather than consumable inventory.

**Conflicting evidence:** Operational replacement value and financial carrying
value answer different questions; forcing one value into both uses creates
planning or accounting errors.

**Confidence:** High on reconciliation design; tax/GAAP treatment needs a CPA.

**Business-specific validation:** A CPA/controller must approve capitalization,
inventory, overhead, scrap, impairment, and close policies and reconcile a
parallel pilot to the ledger.

**Second-opinion review:** GPT-5.4 — Agree. Reconciliation should map operational events to accounting entries without retroactive deletion.

### INV-054 — Which inventory actuals should feed historical-part estimating, process planning, maintenance, and purchasing forecasts?

**State:** Exploring

**First-pass answer:** Feed actual issued/returned/consumed material, cut yield
and remnants, scrap/rework, tool/edge exposure and failures, consumables,
fixture/gauge use, shortages/substitutions, receiving and supplier lead time,
quality disposition, refurbishment yield/turnaround, spare use, stockouts, and
price/freight. Preserve part/revision, operation, material lot, machine, supplier,
and time context and exclude unexplained or one-time anomalies only through
documented rules.

**Evidence:**
- [Microsoft Dynamics 365 quality and nonconformance management](https://learn.microsoft.com/en-us/dynamics365/supply-chain/inventory/quality-management-processes) — **Evidence class:** observed commercial implementation. Historical nonconformance, corrections, tests, and KPIs can inform future quality and process decisions.
- [Microsoft Dynamics 365 master-planning architecture](https://learn.microsoft.com/en-us/dynamics365/supply-chain/master-planning/master-planning-architecture) — **Evidence class:** observed commercial implementation. Planning consumes master and transactional data and can rerun frequently as actuals change.

**Conflicting evidence:** Raw history contains launches, outages, bad scans, and
obsolete revisions; aggressive cleaning can remove real variability, while no
cleaning can bias estimates.

**Confidence:** High on actual categories; feature/forecast selection requires
validation.

**Business-specific validation:** Compare predictions with and without each
actual on held-out jobs and have domain owners approve anomaly and revision-
comparability rules.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. These actuals are decision-relevant, but their forecasting value must still be proved empirically.

### INV-055 — Which integrations are needed with ERP/MRP, purchasing, accounting, CAM, machine controls, tool presetters, metrology, maintenance, suppliers, and shipping?

**State:** Exploring

**First-pass answer:** Integrate authoritative identities and events, not
duplicate full databases: ERP/MRP for orders, costing and planning; purchasing/
suppliers for quotes, POs, acknowledgments and ASNs; accounting for valuation
and assets; CAM/tool data for requirements and assemblies; controls/MTConnect/
OPC UA for execution and state; presetters/metrology for measured offsets and
acceptance; maintenance for asset status/spares; and shipping/carriers for
custody. Define ownership, version, idempotency, latency, error queues, and
offline recovery for every interface.

**Evidence:**
- [MTConnect Part 3, Streams Information Model](https://www.mtconnect.org/s/MTConnect-Part-3-Streams-Information-Model-20.pdf) — **Evidence class:** manufacturing interoperability standard. It provides structured machine observations, events, and conditions for operational integration.
- [OPC UA for Machine Tools](https://reference.opcfoundation.org/specs/OPC-40501-1) — **Evidence class:** consensus interoperability standard. It defines a common machine-tool information model for connected applications.
- [ISO 13399 cutting-tool data model](https://www.iso.org/obp/ui/#iso:std:iso:13399:-1:ed-2:v1:en) — **Evidence class:** consensus standard. It standardizes tool/component properties exchanged among catalogs, CAM, presetters, and tool management.

**Conflicting evidence:** Point-to-point real-time integration appears fast but
creates brittle coupling; some records need synchronous validation while others
are safer through durable asynchronous events.

**Confidence:** High on integration domains; product choices remain open.

**Business-specific validation:** Produce a system-of-record and event contract
matrix, then prove end-to-end receipt, reservation, tool load, measurement,
consumption, shipment, and financial posting.

**Second-opinion review:** GPT-5.4 — Agree. Event-based integrations with clear ownership and idempotency are better than duplicated masters.

### INV-056 — What inventory work must continue during a network, scanner, machine-integration, or external-service outage?

**State:** Exploring

**First-pass answer:** Receiving of urgent goods, safety/quarantine actions,
physical moves, job issue/return, tool changes, WIP handoff, count evidence, and
shipping needed to protect people/product/customer commitments must have a
controlled offline path. Use preissued transaction IDs, cached authorized data,
human-readable labels, time/user/device/source evidence, bounded permissions,
and a reconciliation queue; prohibit irreversible high-risk adjustment,
disposal, or life extension unless emergency authority is documented.

**Evidence:**
- [NIST SP 800-53 Rev. 5](https://csrc.nist.gov/pubs/sp/800/53/r5/upd1/final) — **Evidence class:** primary official guidance. Contingency planning, audit, access, identification, integrity, and recovery controls support bounded offline continuity.
- [GS1 General Specifications](https://ref.gs1.org/standards/genspecs/) — **Evidence class:** consensus identification standard. Human-readable and machine-readable identifiers allow physical work to remain attributable when one capture technology fails.

**Conflicting evidence:** Paper fallback maximizes continuity but increases
duplicate/late-entry risk; stopping all work preserves data consistency but can
create safety, spoilage, and customer harm.

**Confidence:** High on the bounded offline pattern.

**Business-specific validation:** Run outage drills for network, identity,
scanner, machine connector, and supplier/cloud failure and measure missing,
duplicate, late, and conflicting reconciliation.

**Second-opinion review:** GPT-5.4 — Agree. Critical physical work needs controlled offline continuity with bounded authority and later reconciliation.

### INV-057 — Which barcode, QR, RFID, vision, scale, or automated-dispensing technologies provide sufficient value and reliability?

**State:** Exploring

**First-pass answer:** Use durable 1D/2D labels as the default where one-at-a-
time confirmation is acceptable; scales for validated bulk quantity; dispensing
for high-use controlled consumables; RFID for no-line-of-sight or batch movement
only after metal/liquid/collision tests; and vision where labels cannot survive
or automated identity/condition adds value. Choose from measured end-to-end
read accuracy, false-positive rate, labor/search reduction, environment,
maintenance, exception recovery, and total cost—not vendor read-range claims.

**Evidence:**
- [GS1 General Specifications](https://ref.gs1.org/standards/genspecs/) — **Evidence class:** consensus identification standard. It defines interoperable barcode, 2D, and RFID identifiers and data carriers.
- [NIST Handbook 44, 2026 edition](https://www.nist.gov/pml/owm/nist-handbook-44-current-edition) — **Evidence class:** primary official metrology evidence. Scales and automatic weighing systems have defined tolerances and test requirements; weight-derived inventory is only as reliable as the measuring system.
- [FDA UDI basics](https://www.fda.gov/medical-devices/device-advice-comprehensive-regulatory-assistance/unique-device-identification-system-udi-system) — **Evidence class:** observed regulated implementation. Human-readable plus automatic-identification forms provide resilience and interoperability.

**Conflicting evidence:** RFID and vision can automate capture, but metal,
coolant, occlusion, tag/label damage, and model drift can make a simpler scan
more reliable.

**Confidence:** High on selection criteria; technology winners require pilots.

**Business-specific validation:** Run side-by-side pilots on representative
materials, bins, tools, WIP, distances, lighting, contamination, and work pace,
including failures and manual recovery.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The selection criteria are sound, but technology choice requires on-site reliability trials.

### INV-058 — Which inventory metrics should drive action?

**State:** Exploring

**First-pass answer:** Use a balanced, drillable set: service/shortage rate and
affected operations; on-time material/tool readiness; record accuracy and
variance closure; excess/aging/dead stock and turns; remnant reuse and scrap
yield; tool utilization, unexpected breakage, life-estimate error, and
refurbishment yield/turnaround; lost/overdue assets; supplier lead-time/quality;
and carrying/expedite/disposal cost. Each metric needs owner, threshold, action,
and denominator; avoid optimizing turns at the expense of critical availability.

**Evidence:**
- [Microsoft Dynamics 365 quality and nonconformance management](https://learn.microsoft.com/en-us/dynamics365/supply-chain/inventory/quality-management-processes) — **Evidence class:** observed commercial implementation. Historical nonconformance and corrective-action KPIs support trend and effectiveness review.
- [Microsoft Dynamics 365 cycle counting](https://learn.microsoft.com/en-us/dynamics365/supply-chain/warehousing/cycle-counting) — **Evidence class:** observed commercial implementation. Count thresholds, plans, differences, and pending review provide actionable record-accuracy measures.

**Conflicting evidence:** Aggregate inventory turns can improve while critical
stockouts worsen; averages must be segmented by criticality, ownership, class,
and location.

**Confidence:** High on metric families.

**Business-specific validation:** Baseline metrics from current records, define
action limits and owners, and verify that each proposed KPI would have changed a
real past decision.

**Second-opinion review:** GPT-5.4 — Agree. Metrics should be action-linked and segmented by criticality rather than optimized in aggregate.

### INV-059 — How should inventory demand be forecast from quotes, released jobs, historical consumption, maintenance plans, and strategic stocking policy?

**State:** Exploring

**First-pass answer:** Keep demand streams separate, then combine with explicit
authority: probability- and timing-weighted quote demand; firm accepted/released
job requirements; operation-phased dependent demand; statistical independent
consumption; dated maintenance demand; and management-approved strategic/safety
stock. Prevent historical usage caused by one-time scrap or stockout substitution
from silently becoming baseline, and reconcile forecasts against actuals by
horizon/source.

**Evidence:**
- [Microsoft Dynamics 365 master-planning architecture](https://learn.microsoft.com/en-us/dynamics365/supply-chain/master-planning/master-planning-architecture) — **Evidence class:** observed commercial implementation. Master and transaction data drive repeated planning calculations, planned orders, pegging, and warnings.
- [Microsoft Dynamics 365 on-hand inventory](https://learn.microsoft.com/en-us/dynamics365/supply-chain/inventory/inventory-on-hand-list) — **Evidence class:** observed commercial implementation. Forecasted, physical, financial, reserved, and ordered transactions remain distinguishable.

**Conflicting evidence:** Combining all demand early simplifies planning but
double-counts quote-to-order conversions and hides confidence; over-separation
can miss shared capacity/material exposure.

**Confidence:** High on stream separation and reconciliation.

**Business-specific validation:** Back-test by source and horizon using quote
conversion, job changes, consumption, maintenance, and stockout-censoring data,
then calibrate authority weights.

**Second-opinion review:** GPT-5.4 — Agree. Demand streams should stay separate until explicitly combined under a defined authority rule.

### INV-060 — What security, access, audit, and retention controls apply to valuable, hazardous, regulated, certified, export-controlled, or customer-owned inventory?

**State:** Exploring

**First-pass answer:** Classify inventory and data by value/hazard/customer/
regulatory/export risk; apply least privilege, separation of duties, strong
identity, physical zones, visitor/device controls, transaction approval,
tamper-evident logs, periodic access/inventory review, incident/loss response,
encrypted controlled-data exchange, and contract/law-based retention and
destruction. Restrict both physical items and the technical data that identifies
or enables them.

**Evidence:**
- [NIST SP 800-53 Rev. 5](https://csrc.nist.gov/pubs/sp/800/53/r5/upd1/final) — **Evidence class:** primary official guidance. It provides access, audit, identification, media, physical, incident, integrity, retention, and supply-chain control families.
- [NIST SP 800-171 Rev. 3](https://csrc.nist.gov/pubs/sp/800/171/r3/final) — **Evidence class:** primary official guidance. It specifies protection requirements for controlled unclassified information in nonfederal systems.
- [FAR 52.245-1, Government Property](https://www.acquisition.gov/far/52.245-1) — **Evidence class:** primary official evidence. Sensitive/customer property requires exceptional protection, control, records, loss handling, and authorized disposition.

**Conflicting evidence:** Broad access improves flow but raises diversion,
tampering, and disclosure risk; controls should be tiered so ordinary stock is
not burdened like export-controlled or hazardous property.

**Confidence:** High on control families; exact legal duties are item/customer/
jurisdiction specific.

**Business-specific validation:** Security, EHS, export counsel, quality, and
customer contracts must classify the planned inventory and approve access,
zone, log, incident, and retention matrices.

**Second-opinion review:** GPT-5.4 — Agree. Security and retention controls should be tiered to item and data risk, especially for customer, export, and hazard cases.

## Responsibility, Work Trail, and Customer-Order Movement

### INV-061 — Which movement events require identification of the requesting, authorizing, executing, confirming, and accepting actors?

**State:** Exploring

**First-pass answer:** Separate these actors for high-value, controlled,
customer-owned, hazardous, quarantined, off-site, custody-changing, adjustment,
disposal, and cross-company movements; ordinary low-risk bin moves may identify
requester/executor and system confirmation only. Every event should state which
roles were applicable, permit the same person only under approved small-shop
exceptions, and retain delegated/automated authority.

**Evidence:**
- [GS1 EPCIS 2.0](https://ref.gs1.org/standards/epcis/2.0.0/) — **Evidence class:** consensus traceability standard. Events can record what, when, where, why, source, destination, and business transaction context across organizations.
- [NIST SP 800-53 Rev. 5](https://csrc.nist.gov/pubs/sp/800/53/r5/upd1/final) — **Evidence class:** primary official guidance. Identification, audit, least privilege, and separation-of-duty controls support distinct attributable actors.

**Conflicting evidence:** Requiring five confirmations for every move creates
workarounds; risk-tiered actor requirements preserve control where consequences
justify it.

**Confidence:** High because the control pattern is supported by established standards; local parameters still require validation.

**Business-specific validation:** Classify real movement types by ownership,
value, safety, quality, and external-custody risk and test the resulting approval
burden on the floor.

**Second-opinion review:** GPT-5.4 — Agree. Actor separation should be risk-tiered and attributable instead of universally burdensome.

### INV-062 — How should employee and user identities be captured when people move, issue, consume, transform, inspect, or receive items?

**State:** Exploring

**First-pass answer:** Capture the authenticated immutable workforce/user ID,
current role/authorization, device/session, event time and record time, and
claimed physical action; display the person's name but do not use mutable names
as keys. Require badge/login plus transaction-specific confirmation where risk
is high, prohibit shared accounts, record delegated work, and preserve identity
through termination without retaining unnecessary personal data.

**Evidence:**
- [NIST SP 800-53 Rev. 5](https://csrc.nist.gov/pubs/sp/800/53/r5/upd1/final) — **Evidence class:** primary official guidance. Identification/authentication, access control, audit, personnel, and accountability controls support unique attributable users.
- [GS1 EPCIS 2.0](https://ref.gs1.org/standards/epcis/2.0.0/) — **Evidence class:** consensus traceability standard. Business context and event provenance can associate responsible parties with physical events.

**Conflicting evidence:** Fast shared terminals are operationally convenient,
but shared credentials destroy individual accountability; badge/tap or rapid
re-authentication is a better compromise.

**Confidence:** High because the control pattern is supported by established standards; local parameters still require validation.

**Business-specific validation:** Integrate the chosen identity source and test
badge loss, gloves, shared stations, contractors, role changes, offline work,
termination, and privacy/retention requirements.

**Second-opinion review:** GPT-5.4 — Agree. Immutable user identities and a ban on shared accounts are necessary for accountable inventory events.

### INV-063 — How should robots, machines, conveyors, mobile equipment, carriers, vendors, and software integrations identify themselves as responsible actors?

**State:** Exploring

**First-pass answer:** Assign each nonhuman actor a unique managed identity tied
to owner, type, certificate/credential, physical asset or external organization,
allowed actions, interface/version, and effective dates. Record both the
device/service that executed the event and the human/work order that authorized
it; rotate/revoke credentials, reject anonymous integrations, and distinguish a
carrier/vendor assertion from company confirmation.

**Evidence:**
- [NIST SP 800-53 Rev. 5](https://csrc.nist.gov/pubs/sp/800/53/r5/upd1/final) — **Evidence class:** primary official guidance. Device/service identification, authentication, least privilege, credential management, audit, and system integrity support managed machine identities.
- [MTConnect Part 3, Streams Information Model](https://www.mtconnect.org/s/MTConnect-Part-3-Streams-Information-Model-20.pdf) — **Evidence class:** manufacturing interoperability standard. Structured device observations/events carry source and time context for machine-generated evidence.

**Conflicting evidence:** Network address or connector name is easy to log but
can change or be spoofed; cryptographic/service identity and registered asset
mapping provide stronger accountability.

**Confidence:** High because the control pattern is supported by established standards; local parameters still require validation.

**Business-specific validation:** Create identities and permission scopes for
one machine, robot/vehicle, carrier feed, vendor portal, and integration service
and test rotation, compromise, offline, and replacement scenarios.

**Second-opinion review:** GPT-5.4 — Agree. Nonhuman actors need managed identities tied to asset or service ownership and human authorization.

### INV-064 — Which movements require destination acceptance or supporting evidence such as a scan, sensor, photograph, weight, count, or signature?

**State:** Exploring

**First-pass answer:** Require destination acceptance for custody transfer,
external/off-site moves, customer-owned or regulated material, quarantine/
release, high-value or serialized items, hazardous goods, WIP between accountable
operations, and discrepancies. Evidence should match risk: paired scans and
count/weight for routine transfers; seal/photo/signature or sensor evidence for
high-consequence handoffs. Do not treat a shipping request as proof of receipt.

**Evidence:**
- [GS1 EPCIS 2.0](https://ref.gs1.org/standards/epcis/2.0.0/) — **Evidence class:** consensus traceability standard. Separate shipping, receiving, source, destination, read-point, quantity, and event times support two-sided handoffs.
- [FAR 52.245-1, Government Property](https://www.acquisition.gov/far/52.245-1) — **Evidence class:** primary official evidence. Shipment discrepancies, custody, records, loss, subcontractor possession, and disposition require evidence beyond intent.

**Conflicting evidence:** Extra evidence improves dispute resolution but adds
labor and data retention; apply the least evidence that reliably detects the
relevant failure.

**Confidence:** High because the control pattern is supported by established standards; local parameters still require validation.

**Business-specific validation:** Risk-rank transfer lanes and pilot evidence
methods against short, damaged, wrong, late, and unaccepted deliveries.

**Second-opinion review:** GPT-5.4 — Agree. Destination acceptance and supporting evidence should scale with transfer risk and dispute consequence.

### INV-065 — How are planned movements kept distinct from physically confirmed movements?

**State:** Exploring

**First-pass answer:** Store requests/tasks/reservations as planned events with
due window and expected source/destination; only an attributed observation or
acceptance creates a physical movement. Show in-transit and overdue explicitly,
allow partial execution, and never update on-hand location merely because work
was released. Link confirmation to the plan but permit unplanned moves with an
exception reason.

**Evidence:**
- [GS1 EPCIS 2.0](https://ref.gs1.org/standards/epcis/2.0.0/) — **Evidence class:** consensus traceability standard. Physical event observations and business-transaction context can remain distinct from business intent.
- [Microsoft Dynamics 365 on-hand inventory](https://learn.microsoft.com/en-us/dynamics365/supply-chain/inventory/inventory-on-hand-list) — **Evidence class:** observed commercial implementation. Forecasted, ordered, reserved, physical, and financial transaction states are not collapsed into one balance.

**Conflicting evidence:** Auto-confirming simple conveyor moves may be valid
when sensors are validated; command issuance alone is not physical proof.

**Confidence:** High because the control pattern is supported by established standards; local parameters still require validation.

**Business-specific validation:** Test manual, scanned, sensor-confirmed,
partial, rejected, missed, and unplanned moves and verify physical availability
changes only on valid evidence.

**Second-opinion review:** GPT-5.4 — Agree. Planned intent and confirmed physical movement must remain separate states.

### INV-066 — How are incorrect, duplicate, missing, or late movement events corrected without erasing the original work trail?

**State:** Exploring

**First-pass answer:** Keep immutable originals and add linked reversal,
replacement, merge/duplicate, or late-arrival events with reason, actor,
approval, effective event time, record time, and downstream impact. Use
idempotency keys and source sequence numbers to prevent duplicates; flag gaps
and recompute balances/genealogy from the corrected event set while preserving
what users and systems previously knew.

**Evidence:**
- [GS1 EPCIS 2.0](https://ref.gs1.org/standards/epcis/2.0.0/) — **Evidence class:** consensus traceability standard. Event time and record time permit late data, while event identifiers support durable cross-enterprise records.
- [NIST SP 800-53 Rev. 5](https://csrc.nist.gov/pubs/sp/800/53/r5/upd1/final) — **Evidence class:** primary official guidance. Audit integrity, nonrepudiation, time synchronization, change control, and error handling support append-only correction.

**Conflicting evidence:** Direct edits produce a clean-looking current record
but conceal who changed what and can invalidate genealogy or financial audit.

**Confidence:** High because the control pattern is supported by established standards; local parameters still require validation.

**Business-specific validation:** Inject duplicate, out-of-order, missing,
wrong-location, wrong-quantity, and reversal cases into a prototype and verify
balances, alerts, and audit reconstruction.

**Second-opinion review:** GPT-5.4 — Agree. Corrections should be append-only with reversals or replacements, not destructive edits.

### INV-067 — How is material genealogy preserved when stock or WIP is split, combined, batched, unbatched, repackaged, scrapped, or reworked?

**State:** Exploring

**First-pass answer:** Model every transformation as inputs and outputs with
identity/lot, quantity/unit, event time/location, operation, actor/machine, and
reason. Splits inherit provenance; combinations link all sources and prevent
claiming a narrower pedigree than the mix; repackaging changes container, not
material identity; scrap remains a disposition output; rework links the prior
nonconforming state and new inspection. Enforce quantity balance with documented
yield/kerf/loss.

**Evidence:**
- [GS1 EPCIS 2.0](https://ref.gs1.org/standards/epcis/2.0.0/) — **Evidence class:** consensus traceability standard. Transformation, aggregation, association, quantity, source/destination, and transaction events support input/output genealogy.
- [21 CFR 820.90, Control of nonconforming product](https://www.law.cornell.edu/cfr/text/21/820.90) — **Evidence class:** primary official evidence. Rework requires controlled procedure, retesting/reevaluation, and documentation of the changed product state.

**Conflicting evidence:** Batch-level genealogy is cheaper than unit-level, but
mixed or serialized safety-critical work may require finer identity.

**Confidence:** High because the control pattern is supported by established standards; local parameters still require validation.

**Business-specific validation:** Trace representative split bar/plate, mixed
batch, repack, partial scrap, rework, and remnant scenarios both forward and
backward and reconcile quantities.

**Second-opinion review:** GPT-5.4 — Agree. Genealogy should be modeled as input-output transformations with quantity balance preserved.

### INV-068 — What identity must remain attached to customer-order WIP through every operation and physical handoff?

**State:** Exploring

**First-pass answer:** Maintain WIP unit/container ID; customer and order/line;
part number and revision/configuration; quantity and unit; material/heat/lot and
customer-property identity where required; current/next operation and routing
revision; quality/hold status; source and destination; and split/merge genealogy.
Use durable physical marking or traveler/container identity appropriate to the
part, with electronic linkage and re-identification controls after processing.

**Evidence:**
- [GS1 General Specifications](https://ref.gs1.org/standards/genspecs/) — **Evidence class:** consensus identification standard. It supports item, lot, serial, logistics-unit, location, quantity, and machine-readable/human-readable identity.
- [GS1 EPCIS 2.0](https://ref.gs1.org/standards/epcis/2.0.0/) — **Evidence class:** consensus traceability standard. Events preserve object identity, location, business step, disposition, source/destination, and transformations through handoffs.

**Conflicting evidence:** Direct part marking gives strongest persistence but
can damage or be prohibited on some workpieces; controlled container/traveler
identity is then necessary.

**Confidence:** High because the control pattern is supported by established standards; local parameters still require validation.

**Business-specific validation:** Manufacturing/quality must define marking and
container methods for each process/material and test survival through cleaning,
heat, coating, machining, and subcontracting.

**Second-opinion review:** GPT-5.4 — Agree. WIP identity must preserve order, revision, routing, and genealogy through each physical handoff.

### INV-069 — Which operation facts must be recorded when a person or automation transforms a workpiece?

**State:** Exploring

**First-pass answer:** Record order/part/revision, routing and operation,
input/output WIP/material genealogy, machine/workstation and configuration,
program/process revision, tool/fixture/gauge where material, responsible human
and automation identities, actual start/complete, quantities accepted/rejected/
scrapped/reworked, key process/inspection results, resulting state/location, and
next destination. Capture parameters only where they support quality, costing,
maintenance, or learning.

**Evidence:**
- [GS1 EPCIS 2.0](https://ref.gs1.org/standards/epcis/2.0.0/) — **Evidence class:** consensus traceability standard. Transformation events represent inputs, outputs, time, location, business step, disposition, and business context.
- [MTConnect Part 3, Streams Information Model](https://www.mtconnect.org/s/MTConnect-Part-3-Streams-Information-Model-20.pdf) — **Evidence class:** manufacturing interoperability standard. Machine observations, events, and conditions provide execution evidence from automation.

**Conflicting evidence:** Capturing every controller tag creates cost and noise;
capture the minimum reproducible/decision-relevant set plus raw high-frequency
data only for validated use cases.

**Confidence:** High on core facts.

**Business-specific validation:** For each initial process, perform a quality/
cost/root-cause information analysis and prove records reconstruct one completed
and one failed operation.

**Second-opinion review:** GPT-5.4 — Agree. Record the minimum reproducible facts plus decision-relevant measurements, not every controller tag by default.

### INV-070 — How should the system detect and escalate lost, delayed, misrouted, unaccepted, or unexpectedly stationary material and WIP?

**State:** Exploring

**First-pass answer:** Compare each confirmed event and current location/state
against an expected route, destination acceptance, and time window. Trigger
risk-tiered alerts for overdue pickup/receipt, impossible transition, wrong
zone, duplicate identity, missing acceptance, stationary dwell, or downstream
operation start without material; show last evidence, owner, affected job/
promise, and recommended search/recovery. Close alerts only with attributed
evidence and reason.

**Evidence:**
- [GS1 EPCIS 2.0](https://ref.gs1.org/standards/epcis/2.0.0/) — **Evidence class:** consensus traceability standard. Event time, record time, read point, source/destination, business step, and disposition enable route and dwell comparison.
- [Microsoft Dynamics 365 master-planning architecture](https://learn.microsoft.com/en-us/dynamics365/supply-chain/master-planning/master-planning-architecture) — **Evidence class:** observed commercial implementation. Frequent recalculation, pegging, messages, and warnings show how changed execution can expose affected downstream demand.

**Conflicting evidence:** Tight dwell thresholds catch problems early but create
alarm fatigue in variable queues; thresholds should reflect operation and risk
distributions, not one plant-wide timer.

**Confidence:** High on detection design; thresholds require operating data.

**Business-specific validation:** Measure actual dwell and transit times by
lane/operation, seed lost/misrouted/unaccepted scenarios, and tune alerts for
time-to-detect and false-alarm rate.

**Second-opinion review:** GPT-5.4 — Agree. Route, dwell, and acceptance-based exception detection is the right control pattern.

## Future Demand and Need-By Planning

### INV-071 — Which sources may create future inventory and resource demand?

**State:** Exploring

**First-pass answer:** Allow approved statistical/management forecasts, open
quotes, accepted orders, engineering/prototype demand, released jobs and
operation plans, maintenance work, calibration/refurbishment plans, safety/
critical spares, internal projects, and strategic stocking policy. Identify
source, version, owner, confidence/authority, earliest/latest timing, consuming
operation, and conversion/cancellation relationship so the same demand is not
counted as it moves from quote to order to job to actual.

**Evidence:**
- [Microsoft Dynamics 365 master-planning architecture](https://learn.microsoft.com/en-us/dynamics365/supply-chain/master-planning/master-planning-architecture) — **Evidence class:** observed commercial implementation. Master planning consumes master and transactional data and produces pegged planned supply and warnings.
- [Microsoft Dynamics 365 on-hand inventory](https://learn.microsoft.com/en-us/dynamics365/supply-chain/inventory/inventory-on-hand-list) — **Evidence class:** observed commercial implementation. Forecasted, physical, reserved, ordered, and financial transactions remain separately visible.

**Conflicting evidence:** Including weak signals improves preparedness but can
inflate stock; excluding them hides long-lead exposure. Authority/confidence
must remain explicit.

**Confidence:** High because the control pattern is supported by established standards; local parameters still require validation.

**Business-specific validation:** Map every current demand signal, define
conversion/cancellation links, and replay actual quote/order/job histories to
check duplicate demand.

**Second-opinion review:** GPT-5.4 — Agree. Future demand sources should remain typed, versioned, and conversion-linked to avoid double counting.

### INV-072 — How should forecast, probable, committed, released, and actual demand differ in confidence and planning authority?

**State:** Exploring

**First-pass answer:** Forecast is uncertain aggregate demand used for capacity/
stock policy; probable demand is opportunity-specific and probability/time
weighted; committed demand follows an accepted customer/internal obligation;
released demand is authorized for execution and can hard-reserve or order; and
actual demand is measured consumption. Each level should have defined actions,
approval, dates, supersession links, and anti-double-counting; confidence does
not convert automatically into authorization.

**Evidence:**
- [Microsoft Dynamics 365 on-hand inventory](https://learn.microsoft.com/en-us/dynamics365/supply-chain/inventory/inventory-on-hand-list) — **Evidence class:** observed commercial implementation. Forecasted, physical, reserved, ordered, and financial transaction states remain distinguishable.
- [Microsoft Dynamics 365 master-planning architecture](https://learn.microsoft.com/en-us/dynamics365/supply-chain/master-planning/master-planning-architecture) — **Evidence class:** observed commercial implementation. Transactional and master data produce planned orders and pegging while allowing frequent replanning as authority changes.

**Conflicting evidence:** A high-probability quote may deserve early long-lead
action, while a nominally committed order can still change; authority and risk
should be policy-controlled, not inferred from labels alone.

**Confidence:** High because the control pattern is supported by established standards; local parameters still require validation.

**Business-specific validation:** Define state-transition and action tables with
sales, operations, finance, and procurement and calibrate probability using
historical conversion and cancellation.

**Second-opinion review:** GPT-5.4 — Agree. Confidence and authority levels should drive different planning actions and anti-double-counting rules.

### INV-073 — How precisely must each future need identify quantity, qualification, required-at location, need-by time, consuming operation, and priority?

**State:** Exploring

**First-pass answer:** Precision must match the decision horizon. Every need
requires item/capability, quantity/unit, confidence/authority, and need-by
window; committed/released needs also require qualification/provenance,
required-at location, consuming job/operation, priority, substitution rules,
and upstream lead-time basis. Store uncertainty ranges rather than invented
point precision for early forecasts, then progressively firm the record.

**Evidence:**
- [Microsoft Dynamics 365 master-planning architecture](https://learn.microsoft.com/en-us/dynamics365/supply-chain/master-planning/master-planning-architecture) — **Evidence class:** observed commercial implementation. Pegged planned orders and warnings require dated, item-specific transactional relationships.
- [Microsoft Dynamics 365 on-hand inventory](https://learn.microsoft.com/en-us/dynamics365/supply-chain/inventory/inventory-on-hand-list) — **Evidence class:** observed commercial implementation. Product, storage, tracking, physical, reserved, and ordered dimensions show why location/qualification cannot be inferred from total quantity.

**Conflicting evidence:** False early precision makes plans look certain; too
little detail prevents lead-time and qualification risk from being seen.

**Confidence:** High because the control pattern is supported by established standards; local parameters still require validation.

**Business-specific validation:** Review actual quote-to-operation information
maturity and define mandatory fields and uncertainty tolerances at each gate.

**Second-opinion review:** GPT-5.4 — Agree. Precision should increase with execution authority rather than being overclaimed early.

### INV-074 — How should demand be time-phased through operations rather than assumed to occur when an order is accepted?

**State:** Exploring

**First-pass answer:** Explode the approved routing backward from operation
start/finish dates using setup, queue, run, move, inspection, subcontract,
staging, and buffer times; place each material/tool/fixture/gauge/machine need at
the operation and location where it must be ready, not at order date. Rephase
when schedule, routing, yield, quantity, or actual progress changes and preserve
the customer promise separately from internal need dates.

**Evidence:**
- [Microsoft Dynamics 365 master-planning architecture](https://learn.microsoft.com/en-us/dynamics365/supply-chain/master-planning/master-planning-architecture) — **Evidence class:** observed commercial implementation. Planning calculates from master/transaction data and returns planned orders, pegging, messages, and warnings.
- [GS1 EPCIS 2.0](https://ref.gs1.org/standards/epcis/2.0.0/) — **Evidence class:** consensus traceability standard. Business-step and time/location events provide actual progression against which operation-phased plans can be updated.

**Conflicting evidence:** Order-date allocation is simple and conservative for
materials needed immediately, but it overstates early demand for later
operations and hides operation-specific shortages.

**Confidence:** High because the control pattern is supported by established standards; local parameters still require validation.

**Business-specific validation:** Time-phase representative routings, compare
with actual issue/use timestamps, and adjust queue/transfer/inspection offsets.

**Second-opinion review:** GPT-5.4 — Agree. Operation-phased need dates are better grounded than order-date assumptions.

### INV-075 — How are yield, scrap allowance, remnants, tool life, refurbishment, calibration, maintenance, transfer, staging, and inspection lead times included?

**State:** Exploring

**First-pass answer:** Model them as explicit quantity multipliers, recoverable
supply, capacity/life consumption, and dated lead-time steps tied to operations:
yield/scrap increases gross input; usable remnants are qualified supply; tools
consume supported life and may return after refurbishment; gauges/equipment are
unavailable during calibration/maintenance; and transfer, staging, receiving,
inspection, and release precede nettable availability. Use distributions or
buffers for variable steps and update from actuals.

**Evidence:**
- [Microsoft Dynamics 365 quality and nonconformance management](https://learn.microsoft.com/en-us/dynamics365/supply-chain/inventory/quality-management-processes) — **Evidence class:** observed commercial implementation. Inventory can be blocked during inspection, sampled, corrected, and released through dated quality work.
- [Microsoft Dynamics 365 master-planning architecture](https://learn.microsoft.com/en-us/dynamics365/supply-chain/master-planning/master-planning-architecture) — **Evidence class:** observed commercial implementation. Frequent planning runs can incorporate changed master and transaction data into planned orders and pegging.

**Conflicting evidence:** One fixed lead time is easier to maintain but hides
tails and queues; excessively granular uncertain times can add noise without
improving decisions.

**Confidence:** High on explicit treatment; parameters require local data.

**Business-specific validation:** Measure actual yield and each lead-time
component by item/process/provider, then back-test shortage timing and buffer
performance.

**Second-opinion review:** GPT-5.4 — Agree. Yield, service queues, availability windows, and lead times should be explicit planning inputs.

### INV-076 — How should projected availability combine present inventory, reservations, purchase orders, internal production, transfers, refurbishment returns, and expected losses?

**State:** Exploring

**First-pass answer:** Calculate a dated, location- and qualification-specific
ledger: confirmed qualified on-hand minus hard reservations and expected losses,
plus probability/status-adjusted purchase, make, transfer, and refurbishment
receipts, minus operation-phased demand. Never treat requested, unacknowledged,
in-transit, uninspected, or uncertain-return supply as equal to available stock;
show base and risk cases with source pegging.

**Evidence:**
- [Microsoft Dynamics 365 on-hand inventory](https://learn.microsoft.com/en-us/dynamics365/supply-chain/inventory/inventory-on-hand-list) — **Evidence class:** observed commercial implementation. It separates physical, reserved, available, ordered, and dimension-specific quantities and transaction detail.
- [Microsoft Dynamics 365 master-planning architecture](https://learn.microsoft.com/en-us/dynamics365/supply-chain/master-planning/master-planning-architecture) — **Evidence class:** observed commercial implementation. Planning returns planned orders and pegging from master and transaction data and can rerun as conditions change.

**Conflicting evidence:** A single available-to-promise number is easy for
users, but it hides confidence and qualification; multiple scenarios require
clear presentation to avoid indecision.

**Confidence:** High because the control pattern is supported by established standards; local parameters still require validation.

**Business-specific validation:** Reconstruct historical projected balances and
compare promised versus actual receipt, inspection, loss, and consumption dates
to calibrate status confidence.

**Second-opinion review:** GPT-5.4 — Agree. Projected availability should be dated, qualified, and status-weighted rather than a single naive total.

### INV-077 — How early must the system identify a projected shortage, and which jobs, operations, and customer promises should it show as at risk?

**State:** Exploring

**First-pass answer:** Detect shortage when the latest feasible action date
approaches: need-by minus procurement/make/refurbish/transfer, inspection,
staging, and approved decision buffer. Show every pegged job, operation,
quantity, location, priority, customer promise, revenue/penalty or safety impact,
and alternative supply; alert earlier for single-source, long-tail, critical, or
customer-owned items and when uncertainty can consume the remaining buffer.

**Evidence:**
- [Microsoft Dynamics 365 master-planning architecture](https://learn.microsoft.com/en-us/dynamics365/supply-chain/master-planning/master-planning-architecture) — **Evidence class:** observed commercial implementation. Planned orders, pegging, warnings, and frequent recalculation support impact-aware shortage alerts.
- [Microsoft Dynamics 365 on-hand inventory](https://learn.microsoft.com/en-us/dynamics365/supply-chain/inventory/inventory-on-hand-list) — **Evidence class:** observed commercial implementation. Reserved, available, ordered, forecast, and transaction detail provide the inputs for dated risk.

**Conflicting evidence:** Earlier alerts preserve options but create churn for
low-confidence demand; escalation should depend on authority, criticality, and
remaining decision slack.

**Confidence:** High on latest-action-date logic.

**Business-specific validation:** Back-test known shortages to measure how much
warning each remedy required and tune alerts for avoided expedites versus false
alarms.

**Second-opinion review:** GPT-5.4 — Agree. Latest-feasible-action timing plus pegged downstream impact is the right shortage-alert basis.

### INV-078 — Which dated actions should the system propose for a projected shortage?

**State:** Exploring

**First-pass answer:** Propose, with required start/decision dates and impact:
release purchase or internal manufacture; expedite/confirm; transfer; retrieve/
refurbish; calibrate/inspect; approve substitute; change lot/stock unit; split
quantity or job; resequence/reschedule; outsource; allocate by priority; or
escalate customer promise. Rank feasible actions by qualification, time, total
cost, capacity, and risk and state why rejected options fail.

**Evidence:**
- [Microsoft Dynamics 365 master-planning architecture](https://learn.microsoft.com/en-us/dynamics365/supply-chain/master-planning/master-planning-architecture) — **Evidence class:** observed commercial implementation. Planning results include planned orders, pegging, messages, and warnings for action.
- [21 CFR 820.50, Purchasing controls](https://www.law.cornell.edu/cfr/text/21/820.50) — **Evidence class:** primary official evidence. Supplier selection and control remain requirement-driven, so expedite/substitute actions cannot bypass qualification.

**Conflicting evidence:** Automated recommendations improve response speed but
cannot safely approve customer, quality, engineering, or financial exceptions
without defined authority.

**Confidence:** High on action set; ranking weights need local economics.

**Business-specific validation:** Compare proposed actions against planner/
buyer decisions for historical shortages and obtain fixed rules for required
human approvals.

**Second-opinion review:** GPT-5.4 — Agree. Proposed shortage actions should be dated, ranked, and authority-aware.

### INV-079 — Which actual events or planning changes must recalculate future demand and projected availability?

**State:** Exploring

**First-pass answer:** Recalculate on quote probability/date/quantity change;
order acceptance/change/cancel; routing/BOM/revision/yield/scrap change; job
release/resequence/progress; receipt/acknowledgment/delay; inspection/
quarantine/release; count/adjustment/loss; reservation/substitution; tool failure/
life update; refurbishment/calibration/maintenance change; transfer/ship/receive;
supplier/capacity/calendar change; and policy/safety-stock update. Debounce
high-frequency events but preserve causality and planning version.

**Evidence:**
- [Microsoft Dynamics 365 master-planning architecture](https://learn.microsoft.com/en-us/dynamics365/supply-chain/master-planning/master-planning-architecture) — **Evidence class:** observed commercial implementation. Planning Optimization supports frequent runs from updated master and transaction data and returns messages and warnings.
- [GS1 EPCIS 2.0](https://ref.gs1.org/standards/epcis/2.0.0/) — **Evidence class:** consensus traceability standard. Physical/business events provide structured changes in location, disposition, quantity, and transformation.

**Conflicting evidence:** Immediate replanning maximizes currency but can create
nervous schedules; event severity, freeze windows, and batching should limit
unnecessary churn without hiding material risk.

**Confidence:** High because the control pattern is supported by established standards; local parameters still require validation.

**Business-specific validation:** Replay a high-change production period and
measure plan latency, churn, missed shortages, and planner workload under
candidate event/batching policies.

**Second-opinion review:** GPT-5.4 — Agree. All material demand or supply changes should trigger replanning, with batching only to limit unnecessary churn.

### INV-080 — How should prior forecasts and projections be retained and scored against what actually happened?

**State:** Exploring

**First-pass answer:** Snapshot or version every decision-relevant forecast and
projected-availability run with as-of time, horizon, inputs, parameters, model/
policy version, planner overrides, and pegging. Score quantity and timing error,
bias, stockout/late-warning rate, excess created, quote calibration, supplier
receipt accuracy, and action usefulness by source/item/horizon; compare against
simple baselines and do not rewrite history after actuals arrive.

**Evidence:**
- [Microsoft Dynamics 365 master-planning architecture](https://learn.microsoft.com/en-us/dynamics365/supply-chain/master-planning/master-planning-architecture) — **Evidence class:** observed commercial implementation. Planning uses identifiable input data and produces planned orders/pegging that can be versioned for later comparison.
- [NASA Prognostics Data Repository](https://www.nasa.gov/intelligent-systems-division/discovery-and-systems-health/pcoe/pcoe-data-set-repository/) — **Evidence class:** empirical research infrastructure. It emphasizes run-to-failure/time-series data and reproducible evaluation, a useful model for retaining predictions and ground truth rather than only current estimates.

**Conflicting evidence:** Full snapshots consume storage, while only retaining
monthly totals prevents diagnosis of item/date/source errors; retain decision-
relevant versions and compact unchanged data.

**Confidence:** High on versioning/scoring principles; metric thresholds need
business targets.

**Business-specific validation:** Backfill several forecast horizons from
available records, define baseline and cost-weighted metrics, and review where
forecast error actually changed cash, service, or schedule outcomes.

**Second-opinion review:** GPT-5.4 — Agree. Forecasts and projections should be versioned and scored against actuals instead of rewritten after the fact.

## Related Topics

- [Manufacturing Processes and Quality](manufacturing-processes-and-quality.md)
- [Raw Materials, Energy, and Input Costs](raw-materials-energy-and-input-costs.md)
- [Suppliers and Procurement](suppliers-and-procurement.md)
- [Data, AI, and Platform](data-ai-and-platform.md)
- [Facility, Site, Utilities, and Layout](facility-site-utilities-and-layout.md)
- [Tax, Finance, and Capital](tax-finance-and-capital.md)
