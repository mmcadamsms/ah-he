# Prompt: Define Human and Automation Zone Safety

## Requirement Reference

- **Functional Area:** facility-operations
- **Requirement Doc:** `requirements/functional-areas/facility-operations/human-automation-zone-safety-and-scheduling.md`

## Objective

Define the requirements and discovery backlog for safely coordinating people,
fixed automation, mobile automation, material movement, interventions, and
emergency access across facility zones that may transition among
automation-only, validated shared, human-only, energy-isolated, emergency, and
degraded states.

## Acceptance Criteria

1. Zone states and terminology distinguish scheduling intent from validated
   safety state.
2. Production systems may request but cannot authorize or bypass safety
   transitions.
3. Human entry and crossing require a separated route or a completed validated
   transition.
4. Restart requires entrant accountability, restored safeguards, deliberate
   reset, and a separate start command.
5. Emergency egress does not depend on production scheduling, badges, or
   ordinary network availability.
6. Scheduling models zone transitions, access routes, intervention time,
   priority, and deadlock risk without weakening safety requirements.
7. Human intervention, lockout/tagout, mobile automation, degraded operation,
   cybersecurity, and management of change are addressed.
8. Permanent discovery questions capture unresolved design and operating
   decisions.

## Scope

### In Scope

- Facility-zone definitions and state transitions.
- Scheduling, priority, access, occupancy, and route coordination.
- Human intervention and hazardous-energy boundaries.
- Fixed and mobile automation.
- Emergency, failure, audit, and revalidation behavior.
- Requirements and discovery documentation.

### Out of Scope

- Final safety PLC or circuit design.
- Selecting safety devices or vendors.
- Calculating final performance levels, safety integrity levels, stopping
  distances, or separation distances.
- Commissioning physical automation.
- Replacing qualified safety engineering or regulatory review.

## Technical Notes

- Prefer fixed separation and simple zones where practical.
- Do not treat badges, cameras, scheduling, or ordinary software as
  safety-rated presence protection unless specifically engineered and
  validated for that purpose.
- A human-only state is not automatically equivalent to lockout/tagout.
- Reset must not initiate motion.
- Safety and emergency priorities always outrank production.

## Testing Requirements

- [x] Regenerate the canonical discovery index.
- [x] Fail on duplicate permanent IDs.
- [x] Run `git diff --check`.
- [x] Verify new internal Markdown links.

## Dependencies

- `requirements/functional-areas/facility-operations/facility-site-utilities-and-layout.md`
- `requirements/functional-areas/manufacturing-automation/inventory-tooling-and-equipment-lifecycle.md`
- `requirements/discovery/topics/legal-safety-and-compliance.md`
- `requirements/discovery/topics/standards-certifications-and-quality-systems.md`

---
**Status:** completed  
**Created:** 2026-10-17  
**Author:** GitHub Copilot with Mike McAdams
