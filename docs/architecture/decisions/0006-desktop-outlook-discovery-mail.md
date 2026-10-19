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

Following the user's feedback-home decision, export extracted responses to
`requirements/feedback/inbox/discovery-<campaign>.md` as repository-resident
input for later authorized agent work. Omit private mail metadata and use
stable hashed response IDs. Retain raw email, addresses, and delivery state
only in private local storage. Collection exports automatically, and an
offline export action supports existing campaigns. Do not automatically
commit, approve, execute, or apply incoming content.

Use a small Windows Script Host VBScript launcher for scheduled execution.
`wscript.exe` is a GUI-subsystem host; `WScript.Shell.Run(..., 0, True)` starts
PowerShell hidden, waits, and propagates its exit code. A console host with
only `-WindowStyle Hidden` can still flash before PowerShell initializes;
Task Scheduler's `Hidden` setting only hides the task from its UI. Existing
polls upgrade only an exactly recognized action for the same user, checkout,
and local state, leaving all other task configuration untouched.

Treat `MORE QUESTIONS` on its own extracted reply line as a narrowly scoped
request for one additional question, not general instruction execution. Queue
it atomically with the deduplicated reply and link its send attempt to the
request. Confirm extra sends through the same journal without changing the
regular slot watermark. Additive state fields preserve existing campaigns;
already collected replies are never reinterpreted as commands. Idempotent
start/stop only manages the owned task and never recreates campaign state.

## Alternatives and Consequences

Microsoft Graph would be preferable for a supported unattended service, but
requires a separate authentication and tenant-policy setup. SMTP alone does
not provide reply collection. New Outlook does not expose this COM interface.

The desktop must remain signed in. Outlook may prompt, be offline, reject
external mail, or fail automation; no service-grade timing or exactly-once
delivery guarantee is made. Local locks cannot coordinate separate machines;
only one machine may operate a campaign. Private state requires approved local
storage and backups. Generated repository feedback requires review before
commit or sharing; response text may itself contain sensitive information.
Scheduled execution also requires Windows Script Host and VBScript, a
deprecated Windows feature that may be absent or blocked by policy. Fail
explicitly rather than falling back to a focus-stealing launcher. Launcher
startup failures surface through Task Scheduler's result; PowerShell runtime
failures also use the private error log. The first legacy poll after syncing
can still display its old console before installing the hidden action.

See the [requirements](../../../requirements/functional-areas/notifications/discovery-email-learning-tour.md)
and [runbook](../../runbooks/discovery-email-learning-tour.md).
