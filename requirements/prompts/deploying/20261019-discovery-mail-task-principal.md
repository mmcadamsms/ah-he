# Prompt: Recognize Registered Task Identity Despite Short Display Names

## Context

After syncing the quiet-launcher update, the existing scheduled task still opens
a console and fails before Outlook access. Task Scheduler reports an unqualified
principal name while the current Windows identity is qualified. The task otherwise
has one action, interactive logon, and limited privileges.

## Requirements

- [Discovery mail requirements](../../functional-areas/notifications/discovery-email-learning-tour.md), rule 13.
- [Runbook](../../../docs/runbooks/discovery-email-learning-tour.md).

## Scope

Verify the exported registration when the displayed task identity is not already
recognized. Preserve ownership, principal, action/path, and campaign-state guards.
Do not accept a username suffix as ownership or change delivery semantics.
Submit an isolated main-targeted fix for review.

## Acceptance Criteria

- [x] Owned legacy tasks with short display names upgrade using registered identity.
- [x] Foreign/unresolved identities and unsafe principal settings remain rejected.
- [x] Migration and start/stop preserve campaign state and task configuration.
- [x] Offline behavior tests pass without real mail or task registration.
- [ ] Validate scheduled operation on the affected PC after merge and sync.

## Validation

All 32 offline mailer behavior tests pass, including short-name migration,
foreign/unresolved registration rejection, principal constraints, and byte-for-byte
campaign-state preservation. No real scheduled task was registered or modified,
and no Outlook instance or live mailbox was used. Affected-PC validation remains
pending.
