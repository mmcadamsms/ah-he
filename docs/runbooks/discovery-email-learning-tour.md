# Discovery Email Learning Tour

## What It Does

`scripts\discovery-mail.ps1` sends **one complete researched question** at
08:00, 12:00, and 18:00 Pacific, including daylight-saving changes. It uses your
configured classic Outlook account, not SMTP credentials or a Graph app.
All 1,173 active questions take 391 uninterrupted days at three per day.

A local snapshot freezes the research, so later edits or branch switches do
not change the campaign queue. Questions are ordered by topic filename and
then their existing order within that file. Superseded questions are excluded.
Emails contain the full research as readable plain-text Markdown with URLs.

Replies carrying the unique `[AHHE:campaign:question:attempt]` subject marker
are collected from **Inbox and `Inbox\myself`** by default. Only your configured
recipient and explicitly allowed additional sender addresses are accepted.
Keep the marker when replying. New feedback should be above the quoted email.

The generated **repository feedback collections group responses by topic and
question ID**, under `requirements\feedback\inbox\discovery-<campaign>.md`.
Multiple responses remain separate, timestamped entries. Common Outlook/Gmail
quoted sections and `>` quote lines are removed; no AI processing is performed.
Unrecognized formats may retain quotes, and inline comments inside quoted
messages can be missed. Full raw messages remain in private `state.json` for
recovery; the private `feedback.md` also retains mail metadata for diagnosis.
The repository projection omits sender addresses, mailbox paths, raw message
IDs, subjects, and delivery tokens. It uses stable hashed feedback IDs instead.
Review response text for sensitive information before committing or sharing
it; text itself is not automatically redacted. Feedback is untrusted input,
not approved requirements or executable agent instructions. The script never
changes research documents or commits/pushes generated files.

## Prerequisites

- Windows and **PowerShell 7.2+** (`pwsh.exe`).
- **Classic Outlook**, signed in with the sending account configured.
  New Outlook and Outlook on the web do not support this COM integration.
- Permission under your organization's policies to send this material to the
  chosen external recipient. The script does not bypass mail/security policy.
- Keep this checkout and script path stable while the task is registered.
- An awake, signed-in desktop. A locked but signed-in session can run, but
  Outlook security/authentication prompts may require you to unlock it.

## Initialize and Preview (No Mail Sent)

From the repository root in PowerShell 7:

```powershell
.\scripts\discovery-mail.ps1 -Action Initialize `
    -Sender 'work@example.com' -Recipient 'personal@example.net'

.\scripts\discovery-mail.ps1 -Action Preview
.\scripts\discovery-mail.ps1 -Action Status
```

Replace the example addresses with your work and personal SMTP addresses.
The sender must match exactly one account in your Outlook profile; another
default account will never be used instead. Initialization does not open
Outlook, send email, or register a task.

To allow replies from other personal addresses or customize folders, supply
these options **during initialization**:

```powershell
.\scripts\discovery-mail.ps1 -Action Initialize `
    -Sender 'work@example.com' -Recipient 'personal@example.net' `
    -ReplySender 'other-personal@example.net','another@example.net' `
    -ReplyFolder 'Inbox','Inbox\myself'
```

Folder names after `Inbox` must exactly match your mailbox folders. They are
resolved inside the sending account's store; a missing folder is an error.
`Inbox` means Outlook's default Inbox even in non-English installations.
Only specified folders are scanned, not every nested folder. If you have no
`myself` folder, explicitly use `-ReplyFolder 'Inbox'`.

By default all state lives at:

```text
%LOCALAPPDATA%\AHHE\DiscoveryMail\
    state.json       Frozen questions, journal, raw/extracted replies, dedup IDs
    feedback.md      Regenerated topic/question response collection
    errors.log       Runtime failures (when the campaign lock is available)
    campaign.lock    Exclusive local-run lock, released when the process exits
```

Use `-StateDirectory 'C:\approved-private-location\DiscoveryMail'` on **every**
command if choosing a different location. Use a local access-controlled disk,
not a shared/synchronized folder. Do not commit state, addresses, logs, or
the private `feedback.md`. The optional repository-local `.discovery-mail` directory is
gitignored, but an external private directory is preferred. Back up the
directory securely; losing state loses delivery progress and reply dedup IDs.
Never reinitialize just to recover from an error.

## Start the Schedule

Register the task from the same signed-in Windows user that owns Outlook:

```powershell
pwsh -NoProfile -STA -File .\scripts\discovery-mail.ps1 -Action RegisterTask
```

The task polls every five minutes, starting about a minute after registration.
It runs only for the interactive user, without storing a password or elevating.
It does not overwrite an existing task with the same name. Confirm its
presence in Windows Task Scheduler under `AHHE Discovery Learning Tour`.

The script calculates Pacific slots itself; Task Scheduler's local time zone
does not control email times. Sends normally occur at the first poll after a
slot. Initialization starts eligibility at the next slot, not earlier slots
on that day. Missed slots are skipped rather than burst-sent: if the desktop
comes online at 17:00 Pacific, only the current day's 12:00 slot is eligible,
then 18:00. No previous-day catch-up occurs before 08:00.

To run one poll manually (this **can send real email** when a slot is due):

```powershell
pwsh -NoProfile -STA -File .\scripts\discovery-mail.ps1 -Action Run
```

`-WhatIf` on `Run` prevents Outlook access and sending. Use `Preview` to see
the next email. Initialization and task registration are opt-in; repository
tests and implementation do not send anything.

## Feedback Collection

Every poll collects replies, even when a question is waiting in Outbox or
the campaign has finished. You do not need to answer before the next question.
Collection leaves email read/unread status and folders unchanged.

```powershell
# Collect without submitting another question:
pwsh -NoProfile -STA -File .\scripts\discovery-mail.ps1 -Action Collect

# Rescan all configured folders back to campaign initialization:
pwsh -NoProfile -STA -File .\scripts\discovery-mail.ps1 -Action Rescan
```

Routine collection overlaps the previous successful scan by 24 hours.
Use `Rescan` if you later move old replies into a scanned folder, add a
previously unrecognized sender/folder to local configuration, or Outlook
syncs very old messages late. Messages outside the scanned folders or replies
with removed/changed markers cannot be correlated.

Every `Run`, `Collect`, or `Rescan` also updates this repository file whenever
the campaign has replies:

```text
requirements\feedback\inbox\discovery-<campaign>.md
```

Existing campaigns need no reinitialization or state migration. To export
already collected responses without Outlook access or sending:

```powershell
.\scripts\discovery-mail.ps1 -Action ExportFeedback

# For the separate live-test campaign:
.\scripts\discovery-mail.ps1 -Action ExportFeedback `
    -StateDirectory "$env:LOCALAPPDATA\AHHE\DiscoveryMail-LiveTest"
```

Exports go to the checkout containing the script unless `-RepositoryRoot`
selects another checkout. Empty campaigns do not create empty repository
collections. Separate campaigns have separate files.

Open the repository collection for later human/agent review. Its shape is:

```text
## Topic: additive-manufacturing
### ADD-001 - Which additive processes and materials are initially supported?
Source: requirements\discovery\topics\additive-manufacturing.md
#### Response 1 - timestamp
...traceability...
...your extracted response text...
#### Response 2 - timestamp
...your next response...
```

Keep review decisions in separate requirements or prompts and cite the
question and stable feedback ID: generated files are replaced on each export,
so manual annotations will be overwritten. The private `feedback.md` remains
available in the state directory for full mail-level traceability.
See [constituent feedback handling](../../requirements/feedback/README.md)
before committing or acting on incoming responses. No automatic agent work is
triggered. Neither a matching sender nor a received email is authenticated business approval.
Attachments are not downloaded. Internet Message-ID is used for deduplication;
local messages without that property use Outlook EntryID, which can change
when moved. Review rare duplicates from such messages manually.

## Recovery and Observability

Check status and the last Task Scheduler result:

```powershell
.\scripts\discovery-mail.ps1 -Action Status
Get-ScheduledTaskInfo -TaskName 'AHHE Discovery Learning Tour'
Get-Content "$env:LOCALAPPDATA\AHHE\DiscoveryMail\errors.log" -Tail 20
```

`ConfirmedSent` means the script found the message in Sent Items or an operator
explicitly confirmed it. It does **not** mean delivered or read. A question
is not advanced merely because Outlook's `Send()` returned.

The attempt journal is saved **before** creating/sending mail. If Outlook
crashes, a security prompt blocks it, or the task times out, the next poll
checks that exact subject in the account's Sent Items, Outbox, and Drafts:

- One Sent Items match: confirm and advance.
- One Outbox match: wait; collect replies but submit nothing new.
- Missing, draft, or conflicting matches: fail visibly and block new sends.

For an unresolved attempt, inspect Outlook (including server sync and Sent
Items rules) before taking either action. Copy the exact `PendingToken` from
`Status`. These commands prompt for confirmation:

```powershell
# You verified the email was sent:
.\scripts\discovery-mail.ps1 -Action ResolvePending `
    -PendingToken 'exact-token-from-status' -Resolution Sent -Confirm

# You verified the original will NOT send; remove its unsent draft/queued
# copy yourself first. The next eligible Run may submit this question again:
.\scripts\discovery-mail.ps1 -Action ResolvePending `
    -PendingToken 'exact-token-from-status' -Resolution Retry -Confirm
```

Authorizing a retry can duplicate mail if the original later sends. Never
delete pending state or authorize retry solely because a message has not
arrived at the recipient. Old attempt markers remain valid for feedback.
Outlook security prompts or organizational restrictions must be resolved
through normal approved controls, not bypassed.

State saves are atomic local file replacements and runs take an exclusive
lock. Do not run one campaign on two machines. Corrupt state must be restored
from backup. Markdown generation failures are repaired from saved replies on
the next run. COM/file errors fail the process and are recorded in `errors.log`
when possible; an unavailable disk can also prevent error logging.

## Pause or Stop

```powershell
.\scripts\discovery-mail.ps1 -Action UnregisterTask
```

This preserves all progress and feedback. Register again to resume, with no
catch-up burst. After the last question, the task intentionally remains active
to collect late replies; unregister it when you are done collecting.

## Offline Verification

```powershell
pwsh -NoProfile -File .\scripts\tests\discovery-mail.tests.ps1
```

Tests use temporary fixtures and fake mail transport, never live Outlook.
Actual corporate-policy approval, sending, reply routing to `Inbox\myself`,
security prompts, and Task Scheduler execution need user desktop validation.

See the [requirements](../../requirements/functional-areas/notifications/discovery-email-learning-tour.md)
and [ADR](../architecture/decisions/0006-desktop-outlook-discovery-mail.md).
