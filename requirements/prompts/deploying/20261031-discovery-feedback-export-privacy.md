# Prompt: Publish Discovery Feedback Without Mail Tracking

## Context

The user requested a PR and merge of the current discovery feedback, approved
fixing the exporter before publishing, and requested that later replies continue
updating the same campaign file.

## Requirements

- [Discovery mail requirements](../../functional-areas/notifications/discovery-email-learning-tour.md), rule 8.
- [Feedback handling](../../feedback/README.md).

## Scope

Remove recognized mobile Outlook signatures and Safe Links tracking from
repository exports while retaining destination URLs, response content, stable
feedback IDs, and all private source records. Regenerate the current campaign
offline and publish its feedback as untriaged input, not approved requirements.
Keep sending, collection, request handling, and campaign filenames unchanged.

## Acceptance Criteria

- [x] Existing and future exports omit signature links and wrapper metadata.
- [x] Private replies, feedback IDs, and delivery progress remain unchanged.
- [x] Later replies update the same file while retaining prior responses.
- [x] Offline behavior tests pass without sending mail.
- [ ] Current feedback is reviewed, published in a PR, and merged.

## Validation

All 34 offline behavior tests pass. The current campaign was regenerated using
`ExportFeedback` without Outlook access or sending mail: 33 responses, with no
recognized mobile signature lines, Safe Links wrappers, or email-like strings.
Tests verify private reply preservation, stable exports after reload, failure
for invalid destinations, and later replies updating the same campaign file.
PR merge and scheduled operation validation remain pending.
