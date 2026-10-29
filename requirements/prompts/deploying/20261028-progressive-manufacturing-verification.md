# Prompt: CNC Verification, Workholding, Machine Care, and Inspection

## Requirement Reference

- **Functional Area:** manufacturing-automation
- [CNC Program Verification](../../functional-areas/manufacturing-automation/cnc-program-verification-and-cutting-engagement.md)
- [Pass-by-Pass Workholding Assessment](../../functional-areas/manufacturing-automation/pass-by-pass-workholding-analysis.md)
- [Machine Maintenance and Care](../../functional-areas/manufacturing-automation/machine-maintenance-and-care.md)
- [In-Process Inspection](../../functional-areas/manufacturing-automation/in-process-inspection-and-progressive-verification.md)

## Objective

Record requirements and a proposed design for verifying the final CNC program
and cut engagement, assessing workholding through actual tool passes/material
removal, maintaining every production/support machine correctly, and inspecting
the part as it is created. The user explicitly selected requirements/design
first; implementation follows later.

## Acceptance Criteria

1. Workholding requirements cover preload, evolving stock/contact/stiffness,
   pass-level loads, failure modes, uncertainty, unsupported cases, validation,
   and the distinction between physical setups and indexed orientations.
2. Machine-care requirements cover oiling/cleaning and other applicable
   OEM-based tasks, due rules, evidence, safe execution, holds, persistence,
   and post-service release for production and support machines.
3. Inspection requirements embed feature-linked checkpoints before dependent
   or irreversible operations, with qualified methods, actual evidence,
   containment, and separate final acceptance.
4. CNC verification covers final posted NC, controller/setup context, evolving
   stock, swept collisions/gouges, per-cut quantities/engagement, estimated
   loads/time, unsupported coverage, and revision-bound verification.
   Above-zero air cuts require a separately qualified/verified context and
   cannot silently modify production offsets or prove cutting adequacy.
5. Proposed design and ADR separate predictions, measurements, maintenance,
   software eligibility, and independent safety/physical-machine authority.
6. Existing requirements/navigation link to the new documents, and relative
   links and whitespace are checked.

## Scope

### In Scope

Requirements, assumptions, failure behavior, observable acceptance criteria,
design options, existing-requirement integration, and future verification stages.

### Out of Scope

Runtime code, solver selection/implementation, maintenance/inspection UI,
machine control, approved engineering limits, and physical qualification.

## Testing Requirements

Documentation checks only for this increment. Future implementation must test
the specific PV-AC, WH-AC, MC-AC, and IP-AC cases in the linked requirements, plus
numerical/physical validation appropriate to the declared model scope.

## Dependencies

Existing geometry/CAM worker boundary, asset lifecycle, and facility zone
safety requirements; no dependency on the separate logging proposal.

---
**Status:** deploying
**Created:** 2026-10-28
**Delivery:** Documentation-only PR; no runtime deployed or physically qualified.
