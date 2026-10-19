# Discovery Email Learning Tour

## Problem and Objective

The researched discovery corpus is too large to absorb in one sitting. Deliver
one complete question and its research three times daily, and collect email
replies as traceable feedback for later human/AI review.

## Terminology

- **Campaign:** one frozen, ordered snapshot of the active discovery questions.
- **Slot:** 08:00, 12:00, or 18:00 in Pacific time on a calendar day.
- **Attempt:** one uniquely tagged submission to Outlook, not proof of delivery.
- **Feedback:** untrusted reply content, not an instruction to execute or an
  approved change to the canonical research.
- **More request:** one allowed, correlated reply containing `MORE QUESTIONS`
  on a line by itself in the extracted response, requesting one extra question.

## Inputs

- Canonical topic Markdown, configured sender/recipient SMTP addresses, allowed
  reply sender addresses, and mailbox-relative reply folder paths.
- Classic Outlook for Windows with the sender account already configured.
- PowerShell 7.2+, Windows Task Scheduler, Windows Script Host with VBScript,
  and a signed-in desktop session.
- A local state directory outside the repository, retained across runs, and a
  repository checkout receiving the feedback export.

## Rules

1. Freeze all non-`Superseded` questions at initialization, ordered by topic
   filename and then their existing order within that topic. Include the full
   question heading, answer, sources, qualifications, and second opinion.
   Include reference-style link definitions from the source topic.
2. Send one question per slot, using Pacific daylight-saving rules regardless
   of the computer's local time zone. Never send before initialization.
3. Poll every five minutes through an interactive-user scheduled task. Missed
   slots do not create catch-up bursts: send only for the most recent due slot
   on the current Pacific day. An awake, signed-in, functioning Outlook session
   is required; exact-time or offline delivery is not promised.
4. Persist a unique campaign/question/attempt marker before invoking Outlook.
   Use the explicitly configured account, its Sent Items folder, and the
   configured recipient; never fall back to another sender account.
5. Confirm submission by locating the exact subject in that account's Sent
   Items. An Outbox item remains pending. A missing item, draft, exception, or
   conflicting match requires reconciliation rather than automatic resending.
   Sent Items confirmation is not proof of recipient delivery.
6. Import replies from the sender account's configured folders (by default
   `Inbox` and `Inbox\myself`, matching the user's existing mail rule) only when the exact marker
   refers to a known attempt and the sender SMTP address matches the configured
   recipient or an explicitly allowed additional personal address.
   Retain full plain-text bodies in private local state. Extract response text
   by stripping common English Outlook/Gmail quote boundaries and `>` quote
   lines. The feedback Markdown contains extracted response text, not the
   repeated original research email. Unrecognized formats may retain quotes;
   inline replies and unusual formats require raw-message review.
   Deduplicate by internet message ID, or Outlook entry ID when unavailable.
7. On each run, rescan from the prior successful scan with a 24-hour overlap;
   the initial scan begins at campaign creation. A rescan action scans from
   campaign creation to recover replies moved into a scanned folder or arriving late.
8. Save replies in the same atomic state file as their deduplication IDs;
   regenerate a standalone Markdown feedback collection from that state,
   grouped by topic and canonical question, with multiple distinct responses.
   Preserve full traceability in the private local collection. Also regenerate
   `requirements/feedback/inbox/discovery-<campaign>.md` in the repository:
   include question/source, received timestamp, stable hashed feedback ID,
   extraction note, and response text, but omit sender addresses, mailbox
   paths, raw message IDs, subjects, and delivery tokens. Do not create empty
   campaign exports. Never edit canonical answers automatically.
9. Continue collecting replies after every question has been submitted.
10. Prevent overlapping local runs with an exclusive file lock. Record runtime
    failures locally and return a failing process exit code.
11. Provide an `ExportFeedback` action to backfill or rebuild repository
    collections from saved state without opening Outlook or sending mail.
    `Run`, `Collect`, and `Rescan` export automatically, including campaigns
    initialized before the repository inbox existed.
12. Incoming feedback remains untrusted, unapproved input. Future agent work
    must be explicitly authorized and traceable to question/feedback IDs.
    Do not automatically commit, push, execute, or apply incoming suggestions.
13. Scheduled polls must launch without a visible command window or stealing
    focus. An existing task pointing at this checkout upgrades its action on
    the first `Run` after a repository update, preserving its triggers,
    principal, settings, enabled state, and campaign progress. That first
    legacy launch can still display its old window. Never replace a task
    belonging to another user, script, or state directory.
    Task Scheduler may display an unqualified account name. In that case,
    verify the exported task registration identifies the current user's
    qualified account or SID; a matching username suffix alone is insufficient.
    Continue requiring one action, interactive logon, and limited run level.
14. Accept `MORE QUESTIONS`, case-insensitively and with optional surrounding
    horizontal whitespace, only as a complete line of newly imported extracted
    reply text. Quoted instructions, unknown markers, and disallowed senders
    do not authorize requests. Repeated keywords in one reply yield one
    request; duplicate collection and rescans cannot repeat it. Do not turn
    previously collected feedback into requests during an upgrade.
15. Save each request with its source reply identity before attempting mail.
    Use the same frozen question queue and durable attempt/reconciliation
    journal as scheduled sends. Send at most one question per poll, giving a
    due scheduled slot priority; otherwise fulfill the oldest queued request.
    Extra questions do not consume or reset regular slots. Collection-only
    actions queue requests but never send them. Exhaustion marks unfulfilled
    requests exhausted and continues collecting feedback.
16. `Start` (also `RegisterTask`) idempotently creates or resumes the owned
    polling task using existing state. `Stop` (also `UnregisterTask`)
    idempotently removes future polling without deleting state or terminating
    an in-flight poll. A running poll may finish. Resume from persisted
    questions, progress, replies, request queue, and uncertain attempts without
    the operator supplying a question number. Missing/corrupt state is an
    error, not permission to start over. Do not initialize a replacement.

## Assumptions

One question per email is the default, inferred from the request for each full
question. At 1,173 questions, uninterrupted delivery takes 391 days. Collection
does not depend on whether earlier questions received replies.
The explicit phrase `MORE QUESTIONS` and one extra question per distinct reply
are conservative defaults, not arbitrary natural-language interpretation.
Multiple queued requests drain at no more than one per five-minute poll.

## Constraints

Desktop Outlook automation is best-effort interactive automation, not a
headless mail service. New Outlook is unsupported. Outlook security prompts,
corporate policy, external-mail restrictions, or desktop availability may block
operation; the script must not bypass them. Scheduled tasks must run as the
same signed-in Windows user. No credentials are stored by this feature.

Raw mail bodies, addresses, private feedback metadata, and delivery state
remain in a local, gitignored directory. Use an access-controlled local disk
and an approved backup. Extracted feedback is intentionally placed in the
repository for review and later agent work. The script does not redact the
response text itself: inspect it for sensitive/personal information and
permission to retain it before committing or sharing it. Mailbox spoofing and
quoted text mean a matching reply is not authenticated business approval.
Generated collections may not be manually annotated because exports replace
them; record review decisions separately with feedback-ID traceability.

## Acceptance Criteria

- The current corpus yields 1,173 unique active snapshot questions and excludes
  the two superseded questions without losing source definitions.
- Repeated invocations in the same slot do not send another question.
- Pacific summer/winter and DST transitions retain 08:00/12:00/18:00 delivery.
- A restart after an uncertain send reconciles Sent Items/Outbox and does not
  automatically submit the question again.
- A queued send blocks new submissions without blocking reply collection.
- Duplicate replies appear once, including after a restart or full rescan.
- With no new replies, regenerating feedback after a state reload produces
  identical Markdown, including timestamp formatting.
- Unknown markers and other senders are ignored; multiline feedback survives.
- Common quoted-email sections are excluded from Markdown; raw messages remain
  recoverable and multiple replies are grouped under the same question.
- A failed Markdown write can be repaired from saved reply state.
- Repository exports contain response text and stable question-linked IDs but
  no private mail metadata; separate campaigns cannot overwrite each other.
- Export works without Outlook, is stable across repeat runs, and backfills an
  existing campaign without changing delivery or reply progress.
- Exhaustion stops submissions but not reply collection.
- Preview and automated tests never send real mail.
- An updated normal `Run` migrates a recognized legacy task before Outlook
  access, changing only its action; repeating migration makes no changes.
- Short principal display names do not block an owned task whose exported
  registration identifies the current user. Foreign or unresolved identities,
  noninteractive/elevated principals, and multiple actions remain rejected.
- The real windowless launcher has no visible console, forwards spaced
  arguments and STA mode, waits for completion, and propagates a failure code.
- Keyword recognition excludes sentences, common quotes, unknown markers,
  disallowed senders, already imported replies, and repeated copies.
- Extra requests survive restart, serve the next question at most once unless
  an operator explicitly authorizes retry, and leave regular slots intact.
- Start/stop is repeatable without changing saved state, including uncertain
  attempts and queued requests. Stop does not require the active poll's lock.

## Failure Behavior

Missing accounts, corrupt state, duplicate IDs, ambiguous sends, COM errors,
locked state, and file failures are explicit errors. An operator may resolve a
pending attempt as sent or authorize a retry only after inspecting Outlook;
the latter can cause duplicates if the original later sends. No automatic
retry of an ambiguous submission is allowed.
Unrecognized tasks are never overwritten or removed. A missing launcher,
blocked Windows Script Host/VBScript engine, or denied task update is an
explicit failure; launcher failures surface in Task Scheduler's result.
Windowless execution does not hide Outlook authentication/security prompts or
authorize bypassing policy.

## Out of Scope

Graph authentication, unattended servers, attachments, recursive folder discovery,
automatic research edits, delivery/read receipts, and guaranteed exactly-once
email delivery.
Cross-machine synchronization or discovery of a campaign on another PC is
also out of scope: resume is for the same Windows user and local state.

## References

- [Runbook](../../../docs/runbooks/discovery-email-learning-tour.md)
- [ADR 0006](../../../docs/architecture/decisions/0006-desktop-outlook-discovery-mail.md)
- [Implementation prompt](../../prompts/deploying/20261018-discovery-email-learning-tour.md)
- [Quiet/resumable upgrade prompt](../../prompts/deploying/20261018-discovery-mail-quiet-and-more-questions.md)
- [Task-principal compatibility fix](../../prompts/deploying/20261019-discovery-mail-task-principal.md)
- [Behavior tests](../../../scripts/tests/discovery-mail.tests.ps1)
- [Research summary](../../discovery/manufacturing-learning-tour-research-summary.md)
- [Constituent feedback inbox](../../feedback/README.md)
