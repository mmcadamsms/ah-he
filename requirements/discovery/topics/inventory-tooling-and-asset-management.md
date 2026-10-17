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

**State:** Open

Distinguish raw stock, remnants, consumables, cutting items, tool-assembly
components, fixtures, gauges, hand tools, maintenance spares, equipment,
customer-owned property, scrap, and recyclable material.

### INV-002 — Which items require individual serialization versus lot, package, or bulk-quantity control?

**State:** Open

### INV-003 — Which identifiers should be supported: internal IDs, manufacturer part numbers, serial numbers, barcodes, QR codes, RFID tags, or another method?

**State:** Open

### INV-004 — Which units of measure and controlled conversions are needed for pieces, length, area, volume, weight, and containers?

**State:** Open

### INV-005 — What physical location hierarchy is required?

**State:** Open

Include site, building, room, rack, bin, cabinet, cart, machine, magazine,
pocket, spindle, employee custody, internal service, and external vendor.

### INV-006 — Which events must create immutable inventory transactions?

**State:** Open

### INV-007 — How often should cycle counts and full physical inventories occur?

**State:** Open

### INV-008 — Which count variances require investigation, approval, financial reconciliation, or security review?

**State:** Open

### INV-009 — How should unknown identity, location, quantity, ownership, condition, or status be represented?

**State:** Open

### INV-010 — Which roles may receive, move, reserve, issue, adjust, refurbish, retire, or dispose of inventory?

**State:** Open

## Raw Material and Stock

### INV-011 — Which attributes are mandatory for each supported stock form and material family?

**State:** Open

Address grade, specification, condition, dimensions, weight, heat, lot, batch,
certificate, country of origin, supplier, and usable orientation.

### INV-012 — When is heat, melt, lot, batch, or mill-certificate traceability required?

**State:** Open

### INV-013 — How are company-owned, customer-owned, supplier-owned, leased, rented, and consigned material separated?

**State:** Open

### INV-014 — When should a job reserve a specific stock unit versus a fungible quantity?

**State:** Open

### INV-015 — How are reservations prioritized, changed, released, and protected from double allocation?

**State:** Open

### INV-016 — How should cutting a bar, sheet, plate, tube, or other stock create and identify a remnant?

**State:** Open

### INV-017 — What minimum remnant dimensions, value, provenance, or expected reuse justify retention?

**State:** Open

### INV-018 — How are quarantined, nonconforming, corroded, contaminated, expired, damaged, or unidentified materials controlled?

**State:** Open

### INV-019 — Which storage conditions, segregation rules, shelf-life controls, and preservation methods apply to each material?

**State:** Open

### INV-020 — How should reorder points account for planned demand, lead time, minimum orders, volatility, carrying cost, and shortage risk?

**State:** Open

## Tooling and Tool Assemblies

### INV-021 — Which machine-shop items are classified as tooling, consumables, reusable components, fixtures, gauges, or equipment?

**State:** Open

**Original wording:** "'tooling' in machine shops represents bits, endmills,
inserts, taps, dies, and essentially anything that is used to accomplish the
customer job"

### INV-022 — How should the system represent a qualified tool assembly and the lineage of its components?

**State:** Open

### INV-023 — Which tool geometry, holder, gauge length, runout, balance, offset, coating, grade, and compatibility data must be retained?

**State:** Open

### INV-024 — Which useful-life measures apply to each tooling type?

**State:** Open

Consider cutting time, material removed, part count, starts, load, temperature,
wear measurements, dimensional drift, surface finish, breakage, and qualified
inspection.

### INV-025 — How is an initial expected-life model established for a new tool and operation?

**State:** Open

### INV-026 — Which machine, CAM, inspection, sensor, and operator evidence should update remaining tool life?

**State:** Open

### INV-027 — How should tool-life models differ by work material, operation, machine, holder, cutting conditions, coolant, tolerance, and finish?

**State:** Open

### INV-028 — Who may override a tool-life estimate, and what evidence and reason must be retained?

**State:** Open

### INV-029 — How accurately can the system know that tooling is in a particular machine, magazine pocket, spindle, crib, cart, or setup area?

**State:** Open

### INV-030 — How are manual moves reconciled with machine-reported tooling state?

**State:** Open

### INV-031 — What happens to component history when an insert is indexed or replaced?

**State:** Open

### INV-032 — What happens to assembly qualification and offsets when a cutter or holder component changes?

**State:** Open

### INV-033 — Which tooling can be reground, recoated, resharpened, repaired, or otherwise refurbished?

**State:** Open

### INV-034 — How many refurbishment cycles are allowed, and how do they change geometry, capability, remaining life, and cost?

**State:** Open

### INV-035 — How is tooling tracked while waiting in an internal refurbishment queue?

**State:** Open

### INV-036 — How is tooling tracked while shipped to, held by, serviced by, and returned from an external refurbishment vendor?

**State:** Open

### INV-037 — What inspection and qualification are required before refurbished tooling returns to production?

**State:** Open

### INV-038 — When should worn tooling be refurbished, downgraded to less demanding work, discarded, recycled, or retained for failure analysis?

**State:** Open

### INV-039 — How are approved substitute tools selected when the preferred tool is unavailable or lacks sufficient remaining life?

**State:** Open

### INV-040 — How much supported remaining life must a tool have before it can be committed to a job or unattended run?

**State:** Open

## Tools, Fixtures, Spare Parts, and Equipment

### INV-041 — Which hand tools, portable tools, lifting accessories, fixtures, gauges, and shared shop items require custody tracking?

**State:** Open

### INV-042 — How are fixture ownership, compatible parts and revisions, storage, inspection, maintenance, and retirement tracked?

**State:** Open

### INV-043 — Which gauges and tools require calibration or periodic inspection before they are considered available?

**State:** Open

### INV-044 — Which equipment assets belong in the inventory system versus a connected maintenance or fixed-asset system?

**State:** Open

### INV-045 — How are machine configuration, options, manuals, warranties, maintenance, downtime, and spare parts linked to an equipment asset?

**State:** Open

### INV-046 — Which maintenance spares are critical enough to reserve for equipment recovery?

**State:** Open

### INV-047 — How are checked-out, loaned, rented, employee-held, lost, stolen, damaged, or off-site tools and equipment controlled?

**State:** Open

## Planning, Purchasing, Cost, and Integration

### INV-048 — At what planning stage must material, tooling, fixtures, gauges, and equipment be reserved?

**State:** Open

### INV-049 — How should quoting and scheduling distinguish on-hand, reserved, unqualified, on-order, at-refurbishment, substitutable, and unavailable inventory?

**State:** Open

### INV-050 — Which shortages should trigger purchasing, internal manufacture, transfer, refurbishment, substitution review, or schedule changes?

**State:** Open

### INV-051 — How should the system choose between buying new tooling, replacing an insert, refurbishing an item, renting, borrowing, manufacturing a tool, or redesigning the process?

**State:** Open

### INV-052 — How are actual material, tooling, consumable, refurbishment, scrap, and disposal costs assigned to jobs?

**State:** Open

### INV-053 — How should operational inventory records reconcile with accounting inventory, fixed assets, depreciation, and cost of goods sold?

**State:** Open

### INV-054 — Which inventory actuals should feed historical-part estimating, process planning, maintenance, and purchasing forecasts?

**State:** Open

### INV-055 — Which integrations are needed with ERP/MRP, purchasing, accounting, CAM, machine controls, tool presetters, metrology, maintenance, suppliers, and shipping?

**State:** Open

### INV-056 — What inventory work must continue during a network, scanner, machine-integration, or external-service outage?

**State:** Open

### INV-057 — Which barcode, QR, RFID, vision, scale, or automated-dispensing technologies provide sufficient value and reliability?

**State:** Open

### INV-058 — Which inventory metrics should drive action?

**State:** Open

Consider shortages, stockouts, excess, aging, turns, dead stock, remnant reuse,
tool utilization, unexpected breakage, life-estimate error, refurbishment
yield, vendor turnaround, lost tools, count variance, and carrying cost.

### INV-059 — How should inventory demand be forecast from quotes, released jobs, historical consumption, maintenance plans, and strategic stocking policy?

**State:** Open

### INV-060 — What security, access, audit, and retention controls apply to valuable, hazardous, regulated, certified, export-controlled, or customer-owned inventory?

**State:** Open

## Responsibility, Work Trail, and Customer-Order Movement

### INV-061 — Which movement events require identification of the requesting, authorizing, executing, confirming, and accepting actors?

**State:** Open

### INV-062 — How should employee and user identities be captured when people move, issue, consume, transform, inspect, or receive items?

**State:** Open

### INV-063 — How should robots, machines, conveyors, mobile equipment, carriers, vendors, and software integrations identify themselves as responsible actors?

**State:** Open

### INV-064 — Which movements require destination acceptance or supporting evidence such as a scan, sensor, photograph, weight, count, or signature?

**State:** Open

### INV-065 — How are planned movements kept distinct from physically confirmed movements?

**State:** Open

### INV-066 — How are incorrect, duplicate, missing, or late movement events corrected without erasing the original work trail?

**State:** Open

### INV-067 — How is material genealogy preserved when stock or WIP is split, combined, batched, unbatched, repackaged, scrapped, or reworked?

**State:** Open

### INV-068 — What identity must remain attached to customer-order WIP through every operation and physical handoff?

**State:** Open

### INV-069 — Which operation facts must be recorded when a person or automation transforms a workpiece?

**State:** Open

Consider order, part, revision, operation, machine or workstation, responsible
actor, actual start and completion, accepted quantity, rejected quantity,
resulting state, and next destination.

### INV-070 — How should the system detect and escalate lost, delayed, misrouted, unaccepted, or unexpectedly stationary material and WIP?

**State:** Open

## Future Demand and Need-By Planning

### INV-071 — Which sources may create future inventory and resource demand?

**State:** Open

Consider forecasts, quotes, accepted orders, released jobs, process plans,
maintenance plans, safety stock, and strategic stocking policies.

### INV-072 — How should forecast, probable, committed, released, and actual demand differ in confidence and planning authority?

**State:** Open

### INV-073 — How precisely must each future need identify quantity, qualification, required-at location, need-by time, consuming operation, and priority?

**State:** Open

### INV-074 — How should demand be time-phased through operations rather than assumed to occur when an order is accepted?

**State:** Open

### INV-075 — How are yield, scrap allowance, remnants, tool life, refurbishment, calibration, maintenance, transfer, staging, and inspection lead times included?

**State:** Open

### INV-076 — How should projected availability combine present inventory, reservations, purchase orders, internal production, transfers, refurbishment returns, and expected losses?

**State:** Open

### INV-077 — How early must the system identify a projected shortage, and which jobs, operations, and customer promises should it show as at risk?

**State:** Open

### INV-078 — Which dated actions should the system propose for a projected shortage?

**State:** Open

Consider purchase, expedite, manufacture, transfer, refurbish, substitute,
reschedule, split the job, or escalate.

### INV-079 — Which actual events or planning changes must recalculate future demand and projected availability?

**State:** Open

### INV-080 — How should prior forecasts and projections be retained and scored against what actually happened?

**State:** Open

## Related Topics

- [Manufacturing Processes and Quality](manufacturing-processes-and-quality.md)
- [Raw Materials, Energy, and Input Costs](raw-materials-energy-and-input-costs.md)
- [Suppliers and Procurement](suppliers-and-procurement.md)
- [Data, AI, and Platform](data-ai-and-platform.md)
- [Facility, Site, Utilities, and Layout](facility-site-utilities-and-layout.md)
- [Tax, Finance, and Capital](tax-finance-and-capital.md)
