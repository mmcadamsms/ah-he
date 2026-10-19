# Prompt: Quiet, Resumable Discovery Mail with Reply Requests

## Requirement Reference

- **Functional Area:** notifications
- **Requirement Doc:** [Discovery email learning tour](../../functional-areas/notifications/discovery-email-learning-tour.md), rules 13-16

## Objective

Upgrade the already registered desktop automation by syncing the checkout:
prevent focus-stealing command windows, accept an explicit reply keyword for
an extra question, and provide idempotent start/stop using durable progress.

## Acceptance Criteria

1. The next legacy poll upgrades only its owned task action; subsequent polls
   launch without a visible console, preserving triggers and local state.
2. One allowed correlated reply with `MORE QUESTIONS` on its own line queues
   one extra question, surviving restart, repeated collection, and rescans.
3. Extra sends retain the pending-send journal and do not consume regular
   slots. Due scheduled sends have priority; at most one send occurs per poll.
4. Stop/start requires no progress index, preserves uncertain sends and reply
   requests, and does not reset or silently replace missing/corrupt state.
5. The existing campaign and task need no manual reinitialization or
   re-registration after a sync. No live mail is sent during verification.

## Scope

### In Scope

- Windowless Windows Script Host launcher and safe automatic task migration.
- Reply-command recognition, durable queue, journal integration, and status.
- Idempotent start/stop aliases, regression tests, requirements, ADR, runbook.

### Out of Scope

- Cross-machine campaign operation, arbitrary email instructions, automatic
  research edits, additional recipients, or new mail authentication.
- Bypassing corporate restrictions or enabling disabled Windows features.

## Testing Requirements

- [x] Offline transport tests for request recognition, priority, retries,
      rescan deduplication, legacy state, exhaustion, and restart.
- [x] Fake-scheduler tests for owned legacy migration, unrelated-task refusal,
      idempotent lifecycle, and progress preservation.
- [x] Harmless Windows launcher subprocess verifies hidden console, argument
      forwarding, completion wait, and exit-code propagation without Outlook.
- [x] Updated Markdown links and whitespace checks.
- [ ] Validate the first upgraded unattended poll on the user's other PC,
      including the absence of subsequent focus-stealing windows.

## Verification

The dependency-free suite passes 31 behavior tests, including the normal
`Run` self-upgrade path before a fake Outlook boundary, state-preserving
stop/start during a held campaign lock, and a real hidden launcher child
with spaced/trailing-separator paths, STA mode, and a nonzero exit code.
No real mail was sent and no scheduled task was installed by these tests.

## Dependencies

- Existing [desktop Outlook ADR](../../../docs/architecture/decisions/0006-desktop-outlook-discovery-mail.md).

---
**Status:** deploying
**Created:** 2026-10-18
**Author:** Copilot
