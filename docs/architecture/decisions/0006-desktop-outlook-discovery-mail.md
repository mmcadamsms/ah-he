# ADR 0006: Use Interactive Desktop Outlook for Discovery Email

## Status

Accepted for the local learning-tour utility.

## Context

The user selected classic Outlook and a signed-in desktop rather than
Microsoft Graph registration and tenant consent. The utility needs persistent
progress, Pacific-time slots, and reply collection without storing credentials.

## Decision

Use a PowerShell 7 script and classic Outlook's COM object model under the
interactive Windows user. Register an optional five-minute Task Scheduler
poller with interactive logon, not a service or password-based task. Compute
due slots using Windows' `Pacific Standard Time` zone, which includes DST.

Freeze the question corpus in local JSON state. Journal attempts before
Outlook submission, reconcile Sent Items and Outbox after restarts, and block
automatic resubmission when the result is ambiguous. Save imported replies
with their deduplication keys; Markdown is a reproducible projection.

## Alternatives and Consequences

Microsoft Graph would be preferable for a supported unattended service, but
requires a separate authentication and tenant-policy setup. SMTP alone does
not provide reply collection. New Outlook does not expose this COM interface.

The desktop must remain signed in. Outlook may prompt, be offline, reject
external mail, or fail automation; no service-grade timing or exactly-once
delivery guarantee is made. Local locks cannot coordinate separate machines;
only one machine may operate a campaign. State and feedback require approved
local storage and backups.

See the [requirements](../../../requirements/functional-areas/notifications/discovery-email-learning-tour.md)
and [runbook](../../runbooks/discovery-email-learning-tour.md).
