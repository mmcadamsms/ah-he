# Inventory, Material Movement, Tooling, and Equipment Lifecycle Management

## Problem

A manufacturing business cannot reliably quote, schedule, or execute customer
work without knowing what material, tooling, tools, fixtures, spare parts, and
equipment it owns; where each item is; whether it is available; and whether it
is fit for use.

The same need applies after raw material enters production. The business must
know which customer order and revision a workpiece belongs to, which operations
have been performed, where it moved, who or what moved it, who accepted the
handoff, and what operation or destination comes next. Without that work trail,
the current location is difficult to trust and the business cannot reconstruct
how a customer order actually flowed through the shop.

Historical tracking alone is insufficient. Purchasing, staging, automation,
and employees need advance notice of what material, tooling, fixtures, gauges,
equipment, and capacity will be required; where each resource must be; and when
it must arrive. The system must therefore combine actual chain-of-custody
history with time-phased future demand.

Machine-shop tooling is not static inventory. End mills, drills, inserts, taps,
dies, cutters, and related components wear during production. They may remain
in a machine, wait in a tool crib, be assigned to a job, require insert
replacement, enter an internal refurbishment queue, be sent to an external
vendor for regrinding, return with changed geometry, or reach end of life.
Treating all of these states as a simple quantity on hand would create unsafe
plans, production delays, inaccurate costs, and lost assets.

## Objective

Provide an internal inventory system that:

- tracks raw material and stock from receipt through consumption, remnant,
  scrap, or shipment;
- tracks customer-order material, work in process, finished goods, containers,
  and operation handoffs from intake through delivery or disposition;
- tracks tooling at component and assembled-tool levels, including location,
  condition, use, remaining life, refurbishment, and retirement;
- tracks reusable tools, fixtures, gauges, spare parts, and equipment;
- attributes every controlled movement and custody change to the responsible
  employee, user, automation device, vendor, carrier, or system integration;
- makes actual availability visible to quoting, planning, scheduling,
  purchasing, maintenance, and job execution;
- projects dated and location-specific demand before resources are needed;
- preserves lot, serial, ownership, cost, and job traceability; and
- produces an auditable lifecycle history rather than overwriting the current
  state without explanation.

## Terminology

- **Raw material or stock:** Material used to create customer parts, including
  round, square, rectangular, or hex bar; tube; pipe; sheet; plate; structural
  shapes; castings; forgings; plastics; and other supported forms.
- **Stock unit:** A separately identifiable piece, sheet, plate, bar, coil,
  container, or quantity of material.
- **Remnant:** Usable material remaining after a stock unit is cut or partly
  consumed.
- **Material lot:** Material sharing a heat, melt, batch, supplier lot,
  certificate, or other required traceability identity.
- **Workpiece:** A physical item being transformed for a customer order,
  including a cut blank, partially completed part, finished part, assembly, or
  rejected item.
- **Work in process (WIP):** Material or workpieces released to production but
  not yet completed and accepted as finished goods.
- **Handling unit:** A controlled container or grouping used to move items
  together, such as a pallet, tote, rack, cart, bin, package, or traveler.
- **Tooling:** Production items used to perform or support customer work,
  including cutters, end mills, drills, inserts, taps, dies, toolholders,
  collets, arbors, boring bars, probes, workholding components, and similar
  items.
- **Cutting item:** The replaceable or refurbishable portion that directly
  performs a manufacturing operation, such as an insert, end mill, drill, tap,
  or die.
- **Tool assembly:** A qualified combination of cutting item, holder, collet,
  extension, arbor, pull stud, and other components installed or prepared as a
  unit.
- **Useful life:** The bounded period during which an item remains suitable
  for a defined operation and quality requirement. Useful life may depend on
  cutting time, material removed, part count, load, material, operation,
  observed wear, dimensional results, refurbishment history, and qualified
  human judgment.
- **Refurbishment:** An internal or external process that restores some useful
  capability, such as regrinding, recoating, resharpening, repair, or insert
  replacement.
- **Tool:** A reusable shop item not necessarily consumed as cutting tooling,
  including hand tools, inspection tools, lifting accessories, setup tools,
  and portable powered tools.
- **Fixture:** Reusable workholding or positioning equipment used to locate,
  restrain, or support work.
- **Equipment asset:** A durable production or support asset such as a machine
  tool, saw, welder, compressor, forklift, tool presetter, or inspection
  machine.
- **Location:** A physical or custody position such as receiving, quarantine,
  material rack, tool crib, job cart, machine magazine pocket, spindle,
  inspection, maintenance, internal refurbishment, external vendor, scrap, or
  disposal.
- **Movement event:** A confirmed change in physical location, custody,
  handling unit, production stage, or operational responsibility.
- **Responsible actor:** The identified employee, user, automation device,
  robot, machine, carrier, vendor, or system integration accountable for
  initiating, executing, confirming, or accepting an event. Different roles
  may be recorded for one event.
- **Chain of custody:** The ordered history of where an item was, who or what
  controlled it, and when responsibility changed.
- **Reservation:** Inventory committed to a job or purpose but not yet
  consumed.
- **Planned demand:** A future requirement for an item, quantity, capability,
  location, and need-by time derived from a quote, forecast, released order,
  process plan, maintenance plan, or stocking policy.
- **Projected availability:** Expected inventory or resource availability at a
  future time after current supply, reservations, planned demand, purchase
  orders, refurbishment returns, transfers, yields, and lead times are
  considered.
- **Available:** Present, correctly identified, not otherwise reserved, and
  qualified for the intended use.

## Inventory Classes

The system must distinguish at least:

1. raw material, purchased stock, remnants, and customer-owned material;
2. blanks, WIP, finished parts, assemblies, rejected items, and customer-order
   handling units;
3. consumables such as inserts, abrasives, coolant, lubricants, gases, and
   packaging;
4. cutting items with measurable wear or refurbishment history;
5. tool-assembly components and qualified assembled tools;
6. fixtures, workholding, gauges, hand tools, and portable tools;
7. maintenance and replacement parts;
8. durable equipment assets; and
9. scrap, recyclable material, quarantined items, and retired assets.

These classes may share a transaction ledger, but they must not share
incorrect lifecycle, costing, traceability, or availability assumptions.

## Inputs

Inventory records must support the applicable subset of:

- internal identifier, barcode, QR code, RFID identifier, serial number, and
  manufacturer part number;
- category, description, manufacturer, supplier, and approved substitutes;
- quantity, unit of measure, and allowed unit conversions;
- material family, grade, specification, condition, form, dimensions, weight,
  heat, lot, batch, certification, and country-of-origin data;
- ownership, including company, customer, supplier, leased, rented, or
  consigned;
- current location, custody, status, condition, and reservation;
- responsible actor, initiating actor, executing actor, confirming actor,
  accepting actor, and the automation or credential identity used;
- received, inspected, qualified, issued, returned, consumed, moved,
  refurbished, repaired, retired, recycled, and disposed timestamps;
- prior and destination location, handling unit, production stage, and custody;
- purchase order, receipt, supplier, cost, landed cost, and valuation data;
- job, part, revision, operation, machine, person, and transaction references;
- planned operation sequence, expected yield, need-by time, required-at
  location, priority, confidence, and demand source;
- tool geometry, grade, coating, holder, gauge length, diameter, offsets,
  balance, runout, and qualified assembly configuration;
- expected-life model, accumulated use, observed wear, remaining-life
  estimate, failure history, and permitted refurbishment cycles;
- maintenance, inspection, calibration, certification, and next-due status;
- photographs, certificates, inspection evidence, vendor documents, and
  notes; and
- minimum, maximum, reorder point, lead time, criticality, and preferred
  stocking policy.

## Inventory and Location Rules

1. Every controlled item or homogeneous bulk lot must have an unambiguous
   identity appropriate to its risk and value.
2. Every movement, custody change, quantity adjustment, reservation,
   consumption, transformation, operation completion, refurbishment, and
   disposition must create a timestamped transaction.
3. Current state must be derived from or reconcilable to the transaction
   history.
4. Location must support a hierarchy such as site, building, room, rack, bin,
   machine, magazine, pocket, spindle, cart, vendor, or employee custody.
5. An item at an external vendor remains visible with its external custody,
   shipment, service order, expected return, and owner.
6. Unknown location, identity, quantity, condition, or qualification must be
   represented explicitly and must not appear as ordinary available inventory.
7. The system must support cycle counts, full counts, reconciliation,
   adjustment reasons, and approval appropriate to value and risk.
8. Negative inventory and impossible lifecycle transitions must be blocked or
   escalated rather than silently corrected.

## Responsibility, Chain-of-Custody, and Work-in-Process Rules

1. Every controlled movement must identify the item or handling unit, origin,
   destination, event time, reason, related job and operation where applicable,
   and responsible actor.
2. The system must distinguish who or what requested, authorized, physically
   executed, digitally confirmed, and accepted a movement when those are
   different actors.
3. Employees and users must act through attributable identities. Automation
   must act through a stable device, robot, machine, service, or integration
   identity rather than a generic system actor.
4. Shared credentials must not be used as the permanent attribution for a
   controlled movement.
5. A handoff may require destination acceptance, scan, machine confirmation,
   sensor evidence, photograph, weight, count, signature, or another configured
   proof before custody is complete.
6. Planned movements and actual movements must remain distinct. A plan does
   not prove that an item physically moved.
7. Corrections must preserve the original event, correction reason, correcting
   actor, approval where required, and resulting state.
8. Raw material transformed into blanks, WIP, finished parts, assemblies,
   remnants, scrap, or rework must preserve parent-child material genealogy.
9. Splitting, combining, batching, unbatching, and repackaging items must
   preserve quantity reconciliation and traceability.
10. Every controlled workpiece must retain its customer order, part, revision,
    quantity or serial identity, current operation state, current location, and
    next expected operation.
11. Operation completion must identify the responsible person or automation,
    machine or workstation, actual start and completion, quantity accepted,
    quantity rejected, and resulting destination or hold.
12. WIP sent to inspection, rework, outside processing, storage, assembly,
    packaging, or shipping remains in the same auditable chain of custody.
13. A customer-facing status may summarize internal events, but the internal
    record must retain the responsible actors and detailed work trail subject
    to access policy.
14. Lost, delayed, misrouted, unaccepted, or unexpectedly stationary items must
    create an exception rather than remaining silently at a planned location.
15. A material, WIP, tool, or equipment movement plan must honor the current
    and projected human/automation zone states and must not route a person,
    vehicle, or automation through a zone that does not permit that movement.

## Raw Material and Stock Rules

1. Retain grade, form, dimensions, condition, lot or heat, certifications, and
   ownership when required by the job or quality system.
2. Separate available, reserved, quarantined, nonconforming, customer-owned,
   remnant, scrap, and consumed material.
3. Reserve specific stock units or bounded quantities for released jobs while
   exposing the effect on other planned work.
4. When stock is cut, record the consumed amount and create any retained
   remnant with its new dimensions, weight, parent identity, location, and
   traceability.
5. Stock selection must not treat a remnant as usable when dimensions,
   provenance, condition, or certification are insufficient.
6. Material substitution requires the applicable engineering, quality, and
   customer approval; inventory availability alone is not approval.
7. Storage controls must account for corrosion, contamination, shelf life,
   temperature, humidity, segregation, safe stacking, and incompatible
   materials where applicable.

## Tooling Lifecycle Rules

1. Track consumable cutting items separately from reusable holders and other
   assembly components.
2. Preserve the component lineage of every controlled tool assembly.
3. Record where tooling is located, including exact machine and magazine
   pocket when integrated machine data is available.
4. Record use against the actual job, material, operation, machine, cutting
   conditions, and available machine evidence.
5. Useful-life estimates may use elapsed cutting time, material removed, part
   count, load, alarms, inspection drift, observed wear, breakage, and prior
   comparable use. The system must state which evidence supports the estimate.
6. A generic catalog life must not be presented as measured remaining life.
7. Qualified users may reduce, extend, or end a life estimate only with a
   recorded reason and required inspection evidence.
8. Replacing an insert or cutting item resets only the applicable component
   state. It must not erase the use history of the holder or the removed item.
9. Regrinding, recoating, or repair creates a new lifecycle interval linked to
   the pre-service identity and vendor or internal work record.
10. Post-refurbishment geometry, diameter, length, coating, permitted
    operations, offsets, and qualification status must be updated before use.
11. Tooling in an internal refurbishment queue or at an external vendor is not
    available for production until the required service and qualification are
    complete.
12. Broken, suspect, expired, or unqualified tooling must be quarantined from
    automatic planning and machine loading.
13. Retirement must record whether the item was discarded, recycled,
    returned, sold, retained for nonproduction use, or preserved for failure
    analysis.

## Tools, Fixtures, and Equipment Rules

1. Track custody and availability for shared tools, fixtures, gauges, and
   portable equipment when loss, scheduling, safety, or calibration matters.
2. A tool, gauge, fixture, or asset past required inspection, maintenance, or
   calibration must not appear qualified for work requiring that status.
3. Equipment records must connect to maintenance history, downtime, parts,
   warranties, manuals, configuration, and responsible owner where applicable.
4. Fixtures and special tooling must retain ownership, compatible parts and
   revisions, storage location, inspection status, and disposition rules.
5. Maintenance spares must be distinguishable from production material and
   reserved when required for critical equipment recovery.

## Planning, Purchasing, and Cost Rules

1. Quoting and planning must distinguish:
   - on hand and available;
   - on hand but reserved;
   - on hand but unqualified or unavailable;
   - expected from an open purchase order;
   - expected from refurbishment;
   - substitutable with approval; and
   - unavailable.
2. A released manufacturing plan must verify the required material, tooling,
   fixtures, gauges, and equipment against current inventory and reservations.
3. Shortages must create explicit purchase, manufacture, transfer,
   refurbishment, substitution-review, or schedule actions.
4. Reorder recommendations must consider actual consumption, planned demand,
   supplier lead time, minimum order, shelf life, criticality, carrying cost,
   refurbishment alternatives, and shortage risk.
5. Actual material and tooling consumption must feed job cost, historical-part
   records, quoting improvement, and cost-of-goods-sold processes.
6. The system must preserve purchase cost, refurbishment cost, remaining value,
   and disposition proceeds without treating every inventory class as having
   the same accounting method.

## Demand Projection and Need-By Planning

1. Quotes, forecasts, accepted orders, released jobs, process plans,
   maintenance plans, and stocking policies may create planned demand, but the
   source and confidence of each demand must remain visible.
2. Every planned demand must identify the item or capability, quantity,
   required condition or qualification, need-by time, required-at location,
   consuming job or purpose, priority, and current planning status.
3. The system must time-phase demand through the operation plan rather than
   assuming every resource is required when the customer order is accepted.
4. Demand for raw material must consider expected yield, cutoff, setup,
   remnant strategy, scrap allowance, batch size, and units of measure.
5. Demand for tooling must consider assembly components, expected life,
   overlapping jobs, planned cutting conditions, replacement inserts,
   refurbishment turnaround, and conservative reserve.
6. Demand for fixtures, gauges, tools, equipment, people, and locations must
   consider concurrent use and travel, setup, inspection, maintenance,
   calibration, and return time.
7. Projected availability must combine current qualified supply with
   reservations, planned consumption, expected receipts, internal production,
   transfers, refurbishment returns, inspection release, and likely losses.
8. The system must identify the earliest projected shortage date and the jobs,
   operations, and customer commitments at risk.
9. A shortage recommendation must preserve lead time and propose a dated
   action such as purchase, expedite, manufacture, transfer, refurbish,
   substitute, reschedule, or escalate.
10. Schedule, quantity, yield, tool-life, purchase-order, refurbishment, or
    actual-movement changes must recalculate affected demand and projected
    availability.
11. Forecast demand and committed demand must not be presented as equally
    certain.
12. The system must retain prior projections so forecast accuracy, shortage
    prediction, and planning decisions can be evaluated retrospectively.

## Assumptions

- Early operation may use barcode or QR scanning and qualified manual
  transactions before direct machine and RFID integrations are justified.
- Tool-life models will begin conservatively and improve from measured shop
  evidence.
- Not every low-value commodity item requires individual serialization; the
  control level depends on safety, quality, traceability, value, and shortage
  risk.
- Accounting valuation rules will be selected with qualified financial and tax
  advice and may differ from operational availability records.

## Constraints

- Inventory software must not override machine, tooling, material, quality,
  maintenance, calibration, or safety limits.
- Customer-owned and supplier-owned property must remain distinguishable from
  company-owned inventory.
- Export-controlled, hazardous, regulated, certified, and customer-confidential
  items require applicable access, storage, handling, and audit controls.
- Direct machine integrations must fail safely and must not infer that a tool
  is physically present solely because a stale digital record says it is.
- Automation-generated movement records must identify the automation and
  evidence source and must not impersonate a human employee.
- Inventory and movement scheduling must not override facility zone-safety,
  access, egress, lockout/tagout, or emergency controls.
- Financial records and operational records must reconcile without forcing
  one system to discard manufacturing detail needed by the other.

## Failure Behavior

The system must block or visibly escalate:

- insufficient or conflicting identity, grade, lot, dimension, or ownership;
- an unavailable, reserved, quarantined, expired, broken, or unqualified item
  selected for a job;
- a tool with insufficient supported remaining life for the planned operation;
- an overdue inspection, calibration, or maintenance requirement;
- an impossible quantity, negative balance, duplicate serial identity, or
  invalid location transition;
- a movement without an attributable responsible actor or accepted automation
  identity;
- a WIP item whose order, revision, operation, or custody cannot be determined;
- lost custody or an external-service item past its expected return;
- missing certificates or traceability required by the job;
- a planning decision based on stale inventory beyond the allowed age; and
- projected demand that cannot be satisfied by the need-by time; and
- physical count differences that exceed the configured tolerance.

Failure must remain a shortage, hold, discrepancy, or manual-review state. It
must not be converted into a success-shaped fallback.

## Acceptance Criteria

1. A user can identify each supported inventory class and see quantity,
   location, ownership, condition, availability, reservation, and lifecycle
   status.
2. Receiving raw stock records its grade, form, dimensions, quantity, supplier,
   cost, and required lot or certification evidence.
3. Cutting a stock unit records consumption and creates a traceable remnant
   when retained.
4. Customer-owned stock cannot be allocated to another customer's job.
5. A job reservation immediately reduces inventory available to other jobs
   without erasing the physical on-hand quantity.
6. Every controlled movement and adjustment identifies when, where, why, and
   by whom or what integration it occurred.
7. A movement performed by automation identifies the specific robot, machine,
   device, or service and the evidence used to confirm completion.
8. A handoff requiring acceptance remains in transit or pending acceptance
   until the destination actor or configured evidence confirms receipt.
9. Correcting a movement preserves the original event and records the
   correction, reason, responsible actor, and approval.
10. A raw stock unit split into blanks, a remnant, and scrap retains
    reconciled parent-child genealogy.
11. A workpiece can be traced through every completed operation, location,
    handling unit, employee or automation handoff, inspection, outside process,
    and final disposition.
12. The system shows the current and next expected operation for controlled
    WIP without treating the expected location as the confirmed location.
13. A tool assembly exposes all controlled components and their individual
   lifecycle histories.
14. The system can show a cutting item in receiving, storage, a job cart, a
   machine magazine pocket, the spindle, internal refurbishment, an external
   vendor, quarantine, or retired state.
15. Tool use recorded by a job updates supported life measures without
   rewriting the original expected-life evidence.
16. Insert replacement retires or returns the removed insert state while
    preserving the holder's accumulated history.
17. A refurbished tool cannot return to available status until service results
    and required post-service qualification are recorded.
18. Planning rejects tooling whose supported remaining life is insufficient
    for the intended operation or routes it for qualified review.
19. A machine plan identifies shortages across stock, cutting items, holders,
    fixtures, gauges, and required equipment.
20. An item at a refurbishment vendor remains findable with custody, service
    status, shipment reference, and expected return date.
21. Overdue calibration or maintenance removes the affected asset from
    qualified availability where required.
22. Physical-count reconciliation preserves the prior balance, counted
    balance, variance, reason, and approval.
23. Actual material and tooling consumption can be traced to a job, part,
    revision, operation, and machine where applicable.
24. Job-cost and historical-part records receive actual consumption and cost
    rather than only planned values.
25. A released job creates time-phased planned demand with a need-by time and
    required-at location for each controlled resource.
26. Projected availability identifies an expected shortage before the affected
    operation's need-by time and identifies the jobs at risk.
27. Replanning an operation or recording an actual delay recalculates affected
    needs and retains the prior projection for comparison.
28. Unknown location or condition is displayed as unknown and unavailable, not
    silently defaulted to a normal storage location.
29. Role and audit controls show who viewed or changed restricted, valuable,
    customer-owned, or regulated inventory.

## Out of Scope

- Assuming every tool has the same wear model.
- Treating catalog life as guaranteed useful life.
- Fully autonomous tool-life extension without qualified evidence.
- Replacing formal accounting, tax, hazardous-material, export-control, or
  quality-system advice.
- Directly controlling hazardous equipment merely because inventory is
  available.
- Requiring individual serialization for every low-value bulk consumable.

## References

- [Draft Business Vision and Phased Plan](../../discovery/business-vision-and-phased-plan.md)
- [Inventory, Tooling, and Asset Management Questions](../../discovery/topics/inventory-tooling-and-asset-management.md)
- [Manufacturing Processes and Quality Questions](../../discovery/topics/manufacturing-processes-and-quality.md)
- [Raw Materials, Energy, and Input Cost Questions](../../discovery/topics/raw-materials-energy-and-input-costs.md)
- [Suppliers and Procurement Questions](../../discovery/topics/suppliers-and-procurement.md)
- [Geometry-Driven Prismatic 3+2 CAM](geometry-driven-3plus2-cam.md)
- [Historical-Part Analog Estimating](historical-part-analog-estimating.md)
- [Facility, Site, Utilities, and Layout](../facility-operations/facility-site-utilities-and-layout.md)
- [Human and Automation Zone Safety and Scheduling](../facility-operations/human-automation-zone-safety-and-scheduling.md)
