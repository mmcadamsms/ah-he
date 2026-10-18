# Prompt: Desktop Discovery Email Learning Tour

## Requirement Reference

- [Discovery email learning tour](../../functional-areas/notifications/discovery-email-learning-tour.md)
- [ADR 0006](../../../docs/architecture/decisions/0006-desktop-outlook-discovery-mail.md)

## Objective

Add a resumable PowerShell utility to the existing research PR that sends one
complete question at 08:00, 12:00, and 18:00 Pacific through classic Outlook,
then collects tagged replies into a local Markdown feedback collection.

## Acceptance Criteria

- Frozen corpus, explicit sender, persistent progress, DST-aware scheduling.
- Interactive-user task registration and removal; preview without Outlook.
- Fail-closed handling of ambiguous submissions and explicit operator recovery.
- Deduplicated feedback collection, including after campaign completion.
- No automatic task registration, live sending, or credential storage during
  implementation or automated tests.

## Verification

- [x] Offline suite: `pwsh -NoProfile -File .\scripts\tests\discovery-mail.tests.ps1`
      (17 behavior tests, including a fake Outlook object model).
- [x] PowerShell syntax, changed relative Markdown links, and whitespace checks.
- [x] User-authorized live desktop round trip on 2026-10-18: one isolated
      LIF-016 email confirmed in Sent Items; one reply imported from
      `Inbox\myself`, grouped under its question, with quoted research removed.
      Repeated collection across processes preserved identical Markdown and
      did not duplicate the reply. Personal feedback remains outside git.
- [ ] Interactive scheduled-task execution: no daily task installed.

---
**Status:** deploying
**Created:** 2026-10-18
