# Prompt: Define Inventory Lifecycle and Need-By Planning

## Requirement Reference

- **Functional Area:** manufacturing-automation
- **Requirement Doc:** `requirements/functional-areas/manufacturing-automation/inventory-tooling-and-equipment-lifecycle.md`

## Objective

Define the durable requirements and discovery backlog for raw material,
customer-order work in process, tooling, tools, fixtures, spares, and equipment.
The system must preserve accountable chain of custody, material genealogy,
tool-life and refurbishment history, and time-phased need-by demand projections
without confusing planned movements with physically confirmed events.

## Acceptance Criteria

1. A formal requirement defines the supported inventory classes and their
   distinct lifecycle behavior.
2. Every controlled movement identifies when, where, why, and the responsible
   employee, automation, vendor, carrier, or integration.
3. Raw material and customer-order WIP retain operation, custody, location, and
   parent-child genealogy through transformation and handoffs.
4. Tooling tracks component lineage, location, useful life, insert replacement,
   refurbishment, qualification, and retirement.
5. Future demand identifies what is required, where it is required, and its
   need-by time.
6. Projected availability identifies shortages before the affected operation.
7. Permanent discovery questions cover unresolved inventory, movement,
   tooling, asset, and planning decisions.
8. The generated discovery index contains every new permanent question ID
   exactly once.

## Scope

### In Scope

- Requirements and discovery documentation.
- Raw stock, remnants, WIP, finished goods, and customer-owned property.
- Tooling, tool assemblies, fixtures, gauges, tools, spares, and equipment.
- Human and automated movement responsibility.
- Historical transaction and chain-of-custody records.
- Time-phased demand, projected availability, and shortage visibility.
- Related business-vision and historical-estimating documentation.

### Out of Scope

- Production software implementation.
- Database schema, APIs, user interfaces, or machine integrations.
- Selecting a commercial ERP, MRP, warehouse, or tool-management product.
- Final accounting, tax, hazardous-material, export-control, or quality-system
  policy.

## Technical Notes

- Preserve planned and confirmed movements as separate facts.
- Automation must use attributable device or service identities rather than
  impersonating employees.
- Unknown location, condition, custody, or qualification must remain explicit.
- Operational inventory detail must remain reconcilable with future accounting
  and purchasing systems.

## Testing Requirements

- [x] Regenerate the canonical discovery question index.
- [x] Fail index generation on duplicate permanent IDs.
- [x] Run Git whitespace and patch validation with `git diff --check`.
- [x] Confirm the new requirement and discovery references resolve within the
  repository.

## Dependencies

- `requirements/discovery/business-vision-and-phased-plan.md`
- `requirements/functional-areas/manufacturing-automation/historical-part-analog-estimating.md`
- `requirements/discovery/topics/manufacturing-processes-and-quality.md`
- `requirements/discovery/topics/raw-materials-energy-and-input-costs.md`

---
**Status:** deploying  
**Created:** 2026-10-15  
**Author:** GitHub Copilot with Mike McAdams
