# Prompt: Desktop Discovery Email Learning Tour

## Requirement Reference

- [Discovery email learning tour](../../functional-areas/notifications/discovery-email-learning-tour.md)
- [ADR 0006](../../../docs/architecture/decisions/0006-desktop-outlook-discovery-mail.md)

## Objective

Add a resumable PowerShell utility to the existing research PR that sends one
complete question at 08:00, 12:00, and 18:00 Pacific through classic Outlook,
then collects tagged replies into local private state and a repository
Markdown feedback inbox for later authorized agent work.

## Acceptance Criteria

- Frozen corpus, explicit sender, persistent progress, DST-aware scheduling.
- Interactive-user task registration and removal; preview without Outlook.
- Fail-closed handling of ambiguous submissions and explicit operator recovery.
- Deduplicated feedback collection, including after campaign completion.
- Repository exports under `requirements/feedback/inbox/`, stable response IDs,
  omitted private mail metadata, offline backfill, and an authorized live-reply
  seed. Incoming feedback does not automatically authorize agent execution.
- No automatic task registration, live sending, or credential storage during
  implementation or automated tests.

## Verification

- [x] Offline suite: `pwsh -NoProfile -File .\scripts\tests\discovery-mail.tests.ps1`
      (21 behavior tests, including a fake Outlook object model and repository
      export privacy, recovery, backfill, and repeatability).
- [x] PowerShell syntax, changed relative Markdown links, and whitespace checks.
- [x] User-authorized live desktop round trip on 2026-10-18: one isolated
      LIF-016 email confirmed in Sent Items; one reply imported from
      `Inbox\myself`, grouped under its question, with quoted research removed.
      Repeated collection across processes preserved identical Markdown and
      did not duplicate the reply. Raw mail and private metadata remain outside
      git; the user authorized retaining extracted response text in the repo.
- [x] Backfilled the authorized LIF-016 response through `ExportFeedback` into
      `requirements/feedback/inbox/`; a live `Collect` refreshed identical
      repository Markdown without duplicating it or exposing private mail
      metadata.
- [ ] Interactive scheduled-task execution: no daily task installed.

---
**Status:** deploying
**Created:** 2026-10-18
