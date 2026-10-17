# Prompt: Research Minimum Shop Support Capabilities

## Requirement Reference

- **Functional Area:** manufacturing-automation and facility-operations
- **Discovery Topic:** `requirements/discovery/topics/minimum-shop-support-capabilities.md`

## Objective

Identify the support equipment, supplies, space, utilities, handling,
maintenance, safety controls, downstream processes, and acquisition
dependencies required around each proposed anchor manufacturing capability.
Create a durable discovery backlog that prevents the business from treating an
anchor machine purchase as a complete production capability.

## Acceptance Criteria

1. Existing facility, manufacturing, safety, supplier, and inventory questions
   are reviewed before adding new questions.
2. A dedicated canonical topic covers the minimum viable shop baseline.
3. Questions cover benches, abrasive finishing, tool storage, flat fabrication
   tables, cleaning, temporary corrosion protection, swarf, coolant,
   housekeeping, welding, laser/forming dependencies, and powder coating.
4. Questions distinguish mandatory support from capabilities that may be
   outsourced, rented, shared, or deferred.
5. Questions address complete installed cost, floor space, utilities, safety,
   permits, maintenance, supplies, and capability-readiness evidence.
6. Powder coating is evaluated as a complete process and facility system, not
   merely a coating gun and oven.
7. Laser cutting is evaluated with intentional downstream forming, deburring,
   welding, cleaning, inspection, and finishing choices.
8. The canonical discovery index is regenerated without duplicate IDs.

## Scope

### In Scope

- Requirements discovery and initial authoritative-source research.
- Physical support equipment and consumables.
- Process dependencies and make-versus-buy questions.
- Facility, utility, safety, environmental, and insurer considerations.
- Phased acquisition and readiness questions.

### Out of Scope

- Selecting equipment brands or models.
- Approving capital purchases.
- Producing final facility drawings.
- Legal or engineering certification.
- Implementing inventory or maintenance software.

## Technical Notes

- Keep exact requirements dependent on material, process, volume, location,
  adopted codes, authority having jurisdiction, and insurer.
- Do not assume ordinary vacuums are safe for combustible-metal dust.
- Do not assume powder coating is economical merely because entry-level guns
  and ovens are available.
- Do not assume laser cutting requires an immediate press-brake purchase;
  explicitly compare flat-part scope, outsourcing, and in-house forming.

## Testing Requirements

- [x] Regenerate the canonical discovery index.
- [x] Fail on duplicate permanent IDs.
- [x] Run `git diff --check`.
- [x] Verify new internal Markdown links.

## Dependencies

- `requirements/discovery/topics/facility-site-utilities-and-layout.md`
- `requirements/discovery/topics/manufacturing-processes-and-quality.md`
- `requirements/discovery/topics/inventory-tooling-and-asset-management.md`
- `requirements/discovery/topics/legal-safety-and-compliance.md`
- `requirements/discovery/topics/standards-certifications-and-quality-systems.md`
- `requirements/discovery/topics/suppliers-and-procurement.md`

---
**Status:** completed  
**Created:** 2026-10-17  
**Author:** GitHub Copilot with Mike McAdams
