#Requires -Version 7.2
$ErrorActionPreference = 'Stop'
Set-StrictMode -Version Latest
. (Join-Path $PSScriptRoot '..\discovery-mail.ps1')

$root = Split-Path -Parent (Split-Path -Parent $PSScriptRoot)
$testRoot = Join-Path ([IO.Path]::GetTempPath()) ("ahhe-discovery-mail-tests-" + [guid]::NewGuid().ToString('N'))
$null = [IO.Directory]::CreateDirectory($testRoot)
$script:passed = 0

function Assert-True {
    param([bool]$Condition, [string]$Message)
    if (-not $Condition) { throw "Assertion failed: $Message" }
}

function Assert-Equal {
    param($Actual, $Expected, [string]$Message)
    if ($Actual -cne $Expected) { throw "Assertion failed: $Message. Expected '$Expected', got '$Actual'." }
}

function Assert-Throws {
    param([scriptblock]$Body, [string]$Pattern)
    $caught = $false
    try { & $Body } catch {
        $caught = $true
        if ($_.Exception.Message -notmatch $Pattern) { throw }
    }
    if (-not $caught) { throw "Expected an error matching '$Pattern'." }
}

function Test-Behavior {
    param([string]$Name, [scriptblock]$Body)
    & $Body
    $script:passed++
    Write-Host "PASS $Name"
}

function New-TestScenario {
    param([int]$Count = 3)
    $directory = Join-Path $testRoot ([guid]::NewGuid().ToString('N'))
    $null = [IO.Directory]::CreateDirectory($directory)
    $questions = @(1..$Count | ForEach-Object {
        @{
            Id = 'TST-{0:000}' -f $_
            Title = "Question $_"
            Source = 'requirements\discovery\topics\test.md'
            Markdown = "### TST-$('{0:000}' -f $_) - Question $_`n`n**State:** Exploring`n`nFull answer and https://example.org/evidence"
        }
    })
    $state = New-DiscoveryMailState $questions 'sender@example.org' 'reader@example.net' `
        @('alias@example.net') @('Inbox', 'Inbox\myself') ([DateTimeOffset]'2026-07-01T00:00:00Z')
    Save-DiscoveryMailState $state $directory
    $fake = @{ Sends = 0; Status = 'Outbox'; Messages = @(); Fail = $false; Since = $null }
    $transport = @{
        Send = {
            param($Attempt)
            $saved = Read-DiscoveryMailState $directory
            Assert-Equal $saved.PendingToken $Attempt.Token 'journal is durable before send'
            if ($Attempt.RequestKey) {
                $request = Get-DiscoveryMoreRequest $saved $Attempt
                Assert-Equal $request.Status 'Pending' 'request and attempt are durable together before send'
            }
            $fake.Sends++
            if ($fake.Fail) { throw 'Simulated Outlook failure' }
        }.GetNewClosure()
        FindAttempt = { param($Attempt); return $fake.Status }.GetNewClosure()
        ReadReplies = {
            param($Since, $State)
            $fake.Since = $Since
            return $fake.Messages
        }.GetNewClosure()
    }
    return @{ State = $state; Directory = $directory; Fake = $fake; Transport = $transport }
}

function Invoke-TestCycle {
    param($Scenario, [string]$At = '2026-07-01T15:00:00Z', [switch]$CollectOnly, [switch]$FullRescan,
        [string]$RepositoryRoot)
    Invoke-DiscoveryMailCycle $Scenario.State $Scenario.Directory $Scenario.Transport `
        ([DateTimeOffset]$At) -CollectOnly:$CollectOnly -FullRescan:$FullRescan -RepositoryRoot $RepositoryRoot
}

function New-TestReply {
    param($Scenario, [string]$Body = 'MORE QUESTIONS', [string]$Key = '<more@example.net>',
        [string]$ReceivedUtc = '2026-07-01T15:02:00Z')
    return @{
        Key = $Key; Sender = 'reader@example.net'
        Subject = "RE: $($Scenario.State.Attempts[0].Subject)"
        ReceivedUtc = $ReceivedUtc; Folder = 'Inbox\myself'; Body = $Body
    }
}

# Scheduler commands are fakes throughout this suite, including CLI entry tests.
$script:scheduler = @{ Task = $null; Registered = 0; Updated = 0; Enabled = 0; Removed = 0 }
function Get-ScheduledTask {
    [CmdletBinding()]
    param([string]$TaskPath)
    if ($null -ne $script:scheduler.Task) { return $script:scheduler.Task }
}
function Export-ScheduledTask {
    [CmdletBinding()]
    param([string]$TaskName, [string]$TaskPath)
    $task = $script:scheduler.Task
    Assert-Equal $TaskName $task.TaskName 'only the named task registration is read'
    Assert-Equal $TaskPath '\' 'only the root task registration is read'
    $userId = $task.Principal.UserId
    if ($null -ne $task.PSObject.Properties['RegisteredUserId']) { $userId = $task.RegisteredUserId }
    return '<Task xmlns="http://schemas.microsoft.com/windows/2004/02/mit/task"><Principals><Principal><UserId>' +
        [Security.SecurityElement]::Escape($userId) + '</UserId></Principal></Principals></Task>'
}
function New-ScheduledTaskAction {
    param([string]$Execute, [string]$Argument)
    return [pscustomobject]@{ Execute = $Execute; Arguments = $Argument }
}
function New-ScheduledTaskTrigger {
    param([switch]$Once, $At, $RepetitionInterval)
    return [pscustomobject]@{ At = $At; RepetitionInterval = $RepetitionInterval }
}
function New-ScheduledTaskPrincipal {
    param([string]$UserId, [string]$LogonType, [string]$RunLevel)
    return [pscustomobject]@{ UserId = $UserId; LogonType = $LogonType; RunLevel = $RunLevel }
}
function New-ScheduledTaskSettingsSet {
    param([switch]$StartWhenAvailable, [string]$MultipleInstances, [switch]$AllowStartIfOnBatteries,
        [switch]$DontStopIfGoingOnBatteries, $ExecutionTimeLimit)
    return [pscustomobject]@{ MultipleInstances = $MultipleInstances; ExecutionTimeLimit = $ExecutionTimeLimit }
}
function Register-ScheduledTask {
    param([string]$TaskName, $Action, $Trigger, $Principal, $Settings, [string]$Description)
    if ($script:scheduler.Task) { throw 'Test task already exists' }
    $script:scheduler.Registered++
    $script:scheduler.Task = [pscustomobject]@{
        TaskName = $TaskName; Actions = @($Action); Triggers = @($Trigger)
        Principal = $Principal; Settings = $Settings; State = 'Ready'
    }
}
function Set-ScheduledTask {
    [CmdletBinding()]
    param([string]$TaskName, [string]$TaskPath, $Action)
    Assert-Equal $TaskName $script:scheduler.Task.TaskName 'only the named task is updated'
    $script:scheduler.Updated++
    $script:scheduler.Task.Actions = @($Action)
}
function Enable-ScheduledTask {
    [CmdletBinding()]
    param([string]$TaskName, [string]$TaskPath)
    $script:scheduler.Enabled++
    $script:scheduler.Task.State = 'Ready'
}
function Unregister-ScheduledTask {
    [CmdletBinding(SupportsShouldProcess)]
    param([string]$TaskName, [string]$TaskPath)
    $script:scheduler.Removed++
    $script:scheduler.Task = $null
}

try {
    Test-Behavior 'real corpus freezes 1173 unique active questions with full research' {
        $questions = @(Get-DiscoveryQuestions $root)
        Assert-Equal $questions.Count 1173 'active corpus size'
        Assert-Equal @($questions.Id | Sort-Object -Unique).Count 1173 'unique IDs'
        Assert-Equal $questions[0].Id 'ADD-001' 'stable topic order'
        foreach ($question in $questions) {
            Assert-True ($question.Markdown -match '\*\*Second-opinion review:\*\*') "$($question.Id) includes review"
            Assert-True ($question.Markdown -match '\*\*Evidence') "$($question.Id) includes sources"
            Assert-True ($question.Markdown -notmatch '\*\*State:\*\* Superseded') 'no superseded questions'
        }
    }

    Test-Behavior 'parser preserves reference definitions and stops at section boundaries' {
        $fixtureRoot = Join-Path $testRoot 'fixture'
        $topicPath = Join-Path $fixtureRoot 'requirements\discovery\topics'
        $null = [IO.Directory]::CreateDirectory($topicPath)
        $file = Join-Path $topicPath 'one.md'
        $fixture = @'
# Fixture
### TST-001 - Full question?
**State:** Exploring
Answer with [evidence][source].
## Another section
Not part of the prior question.
### TST-002 - Duplicate?
**State:** Superseded
[source]: https://example.org/research
'@
        [IO.File]::WriteAllText($file, $fixture)
        $questions = @(Get-DiscoveryQuestions $fixtureRoot)
        Assert-Equal $questions.Count 1 'skip superseded'
        Assert-True ($questions[0].Markdown.Contains('[source]: https://example.org/research')) 'external reference definition preserved'
        Assert-True (-not $questions[0].Markdown.Contains('Not part')) 'next section excluded'
        [IO.File]::WriteAllText((Join-Path $topicPath 'two.md'), $fixture)
        Assert-Throws { Get-DiscoveryQuestions $fixtureRoot } 'Duplicate canonical'
    }

    Test-Behavior 'Pacific slots respect winter, summer, DST changes, and initialization' {
        $s = New-TestScenario
        $s.State.CreatedUtc = '2026-01-01T00:00:00Z'
        foreach ($case in @(
            @('2026-01-15T15:59:59Z', $null),
            @('2026-01-15T16:00:00Z', '2026-01-15T08:00'),
            @('2026-01-15T20:00:00Z', '2026-01-15T12:00'),
            @('2026-01-16T02:00:00Z', '2026-01-15T18:00'),
            @('2026-07-01T14:59:59Z', $null),
            @('2026-07-01T15:00:00Z', '2026-07-01T08:00'),
            @('2026-07-01T19:00:00Z', '2026-07-01T12:00'),
            @('2026-07-02T01:00:00Z', '2026-07-01T18:00'),
            @('2026-03-08T15:00:00Z', '2026-03-08T08:00'),
            @('2026-11-01T16:00:00Z', '2026-11-01T08:00')
        )) {
            Assert-Equal (Get-DiscoveryMailSlot $s.State ([DateTimeOffset]$case[0])) $case[1] "slot at $($case[0])"
        }
        $s.State.CreatedUtc = '2026-07-01T15:01:00Z'
        Assert-Equal (Get-DiscoveryMailSlot $s.State ([DateTimeOffset]'2026-07-01T18:00:00Z')) $null 'no pre-initialization slot'
    }

    Test-Behavior 'journal, queue reconciliation, restart, and one submission per slot' {
        $s = New-TestScenario
        Invoke-TestCycle $s
        Assert-Equal $s.Fake.Sends 1 'first submission'
        Assert-Equal $s.State.NextIndex 0 'not confirmed by Send return'
        $s.State = Read-DiscoveryMailState $s.Directory
        Invoke-TestCycle $s -At '2026-07-01T15:05:00Z'
        Assert-Equal $s.Fake.Sends 1 'outbox never resubmits'
        $s.Fake.Status = 'Sent'
        Invoke-TestCycle $s -At '2026-07-01T15:10:00Z'
        Assert-Equal $s.State.NextIndex 1 'confirmed after restart'
        Assert-Equal $s.Fake.Sends 1 'same slot remains consumed'
        Invoke-TestCycle $s -At '2026-07-01T19:00:00Z'
        Assert-Equal $s.Fake.Sends 2 'next scheduled question'
    }

    Test-Behavior 'ambiguous submissions fail closed without losing feedback' {
        foreach ($status in @('Missing', 'Draft', 'Ambiguous')) {
            $s = New-TestScenario
            Invoke-TestCycle $s
            $s.Fake.Status = $status
            $s.Fake.Messages = @(@{
                Key = '<reply@example.net>'; Sender = 'reader@example.net'
                Subject = "RE: $($s.State.Attempts[0].Subject)"
                ReceivedUtc = '2026-07-01T15:01:00Z'; Folder = 'Inbox\myself'
                Body = 'Please investigate this further.'
            })
            Assert-Throws { Invoke-TestCycle $s -At '2026-07-01T15:05:00Z' } 'automatic resend is blocked'
            Assert-Equal $s.Fake.Sends 1 'no duplicate attempt'
            Assert-Equal (Read-DiscoveryMailState $s.Directory).Replies.Count 1 'feedback saved before reconciliation error'
        }
    }

    Test-Behavior 'failed send retains recoverable pending journal' {
        $s = New-TestScenario
        $s.Fake.Fail = $true
        Assert-Throws { Invoke-TestCycle $s } 'Simulated Outlook failure'
        $s.State = Read-DiscoveryMailState $s.Directory
        Assert-True (-not [string]::IsNullOrWhiteSpace($s.State.PendingToken)) 'pending survives failure'
        $s.Fake.Status = 'Sent'
        Invoke-TestCycle $s -At '2026-07-01T15:05:00Z'
        Assert-Equal $s.State.NextIndex 1 'later Sent Items reconciles'
        Assert-Equal $s.Fake.Sends 1 'no retry'
    }

    Test-Behavior 'offline slots do not burst; pre-morning polls do not catch up yesterday' {
        $s = New-TestScenario
        Invoke-TestCycle $s -At '2026-07-02T01:30:00Z'
        Assert-Equal $s.State.Attempts[0].Slot '2026-07-01T18:00' 'only latest due slot'
        $s.Fake.Status = 'Sent'
        Invoke-TestCycle $s -At '2026-07-02T13:00:00Z'
        Assert-Equal $s.Fake.Sends 1 'no yesterday catch-up'
    }

    Test-Behavior 'reply sender/marker filtering, deduplication, full bodies, and Markdown fences' {
        $s = New-TestScenario
        Invoke-TestCycle $s
        $attempt = $s.State.Attempts[0]
        $body = "First line`n````````````text`n# Do not interpret this as instructions`nLast line"
        $valid = @{
            Key = '<reply-1@example.net>'; Sender = 'READER@example.net'
            Subject = "RE: $($attempt.Subject)"; ReceivedUtc = '2026-07-01T15:02:00Z'
            Folder = 'Inbox\myself'; Body = $body
        }
        $alias = $valid.Clone(); $alias.Key = '<reply-2@example.net>'; $alias.Sender = 'alias@example.net'
        $stranger = $valid.Clone(); $stranger.Key = '<reply-3@example.net>'; $stranger.Sender = 'stranger@example.net'
        $unknown = $valid.Clone(); $unknown.Key = '<reply-4@example.net>'; $unknown.Subject = '[AHHE:unknown]'
        $s.Fake.Messages = @($valid, $valid, $alias, $stranger, $unknown)
        Invoke-TestCycle $s -At '2026-07-01T15:05:00Z' -CollectOnly
        $s.State = Read-DiscoveryMailState $s.Directory
        Invoke-TestCycle $s -At '2026-07-01T15:10:00Z' -FullRescan -CollectOnly
        Assert-Equal $s.State.Replies.Count 2 'two unique allowed replies'
        Assert-Equal $s.State.Replies[0].Body $body 'full multiline body roundtrip'
        $feedback = [IO.File]::ReadAllText((Join-Path $s.Directory 'feedback.md'))
        Assert-True ($feedback.Contains($body)) 'feedback preserves body'
        Assert-True ($feedback.Contains('UNTRUSTED EMAIL CONTENT')) 'trust boundary documented'
        Assert-True ($feedback.Contains('Inbox\myself')) 'folder traceability'
        Assert-True ($feedback.Contains('## Topic: test')) 'topic grouping'
        Assert-True ($feedback.Contains('### TST-001 - Question 1')) 'question grouping'
        Assert-True ($feedback.Contains('#### Response 2')) 'multiple responses grouped together'
        Assert-Equal $s.Fake.Since ([DateTimeOffset]$s.State.CreatedUtc) 'rescan begins at creation'
    }

    Test-Behavior 'response extraction removes common quotes without changing raw replies' {
        foreach ($case in @(
            @("My feedback.`r`n`r`nFrom: sender@example.org`r`nSent: Wednesday`r`nTo: reader@example.net`r`nSubject: Original`r`nOriginal research.", 'My feedback.'),
            @("My feedback.`r`n`r`n________________________________`r`nFrom: sender@example.org`r`nSent: Wednesday`r`nTo: reader@example.net`r`nSubject: Original`r`nOriginal research.", 'My feedback.'),
            @("My feedback.`n-----Original Message-----`nOriginal research.", 'My feedback.'),
            @("My feedback.`nOn Wed, Jul 1, someone wrote:`n> Original research.", 'My feedback.'),
            @("> Original research.`nMy inline feedback.`n> More original research.", 'My inline feedback.'),
            @("My feedback.`nDiscovery learning tour - question 1 of 1173`nSource: requirements\topic.md`nCampaign: abcdef123456`nOriginal research.", 'My feedback.'),
            @("From: this is a single line in my own feedback.`nKeep it.", "From: this is a single line in my own feedback.`nKeep it.")
        )) {
            Assert-Equal (Get-DiscoveryReplyText $case[0]).Text $case[1] 'extracted reply text'
        }
        $s = New-TestScenario
        Invoke-TestCycle $s
        $raw = "Specific response.`n-----Original Message-----`nDo not repeat original research."
        $s.Fake.Messages = @(@{
            Key = '<extract@example.net>'; Sender = 'reader@example.net'
            Subject = "RE: $($s.State.Attempts[0].Subject)"
            ReceivedUtc = '2026-07-01T15:02:00Z'; Folder = 'Inbox\myself'; Body = $raw
        })
        Invoke-TestCycle $s -At '2026-07-01T15:05:00Z' -CollectOnly
        $saved = Read-DiscoveryMailState $s.Directory
        Assert-Equal $saved.Replies[0].Body $raw 'raw body preserved only in state'
        $feedback = [IO.File]::ReadAllText((Join-Path $s.Directory 'feedback.md'))
        Assert-True ($feedback.Contains('Specific response.')) 'response exported'
        Assert-True (-not $feedback.Contains('Do not repeat original research.')) 'original research excluded'
    }

    Test-Behavior 'feedback timestamps and content remain stable after JSON reload and repeated collection' {
        $s = New-TestScenario
        Invoke-TestCycle $s
        $s.Fake.Messages = @(@{
            Key = '<stable@example.net>'; Sender = 'reader@example.net'
            Subject = "RE: $($s.State.Attempts[0].Subject)"
            ReceivedUtc = '2026-07-01T15:02:00.1234567+00:00'
            Folder = 'Inbox\myself'; Body = 'Preserve this response.'
        })
        Invoke-TestCycle $s -At '2026-07-01T15:05:00Z' -CollectOnly
        $path = Join-Path $s.Directory 'feedback.md'
        $before = [IO.File]::ReadAllText($path)
        $s.State = Read-DiscoveryMailState $s.Directory
        Assert-True ($s.State.Replies[0].ReceivedUtc -is [string]) 'reloaded timestamp is a string'
        Invoke-TestCycle $s -At '2026-07-01T15:10:00Z' -CollectOnly
        Assert-Equal ([IO.File]::ReadAllText($path)) $before 'identical Markdown after restart'
        Assert-Equal $s.State.Replies.Count 1 'one reply after re-import'
    }

    Test-Behavior 'repository collection keeps multiple responses and traceability without private mail metadata' {
        $s = New-TestScenario
        Invoke-TestCycle $s
        $attempt = $s.State.Attempts[0]
        $first = @{
            Key = '<repository-one@example.net>'; Sender = 'reader@example.net'
            Subject = "RE: $($attempt.Subject)"; ReceivedUtc = '2026-07-01T15:02:00.0000000+00:00'
            Folder = 'Inbox\myself'; Body = "First public response.`n-----Original Message-----`nPrivate quoted research."
        }
        $second = $first.Clone(); $second.Key = '<repository-two@example.net>'
        $second.Body = 'Second public response.'
        $s.Fake.Messages = @($first, $second)
        $repository = Join-Path $s.Directory 'repository'
        Invoke-TestCycle $s -At '2026-07-01T15:05:00Z' -CollectOnly -RepositoryRoot $repository
        $path = Join-Path $repository "requirements\feedback\inbox\discovery-$($s.State.Campaign).md"
        $before = [IO.File]::ReadAllText($path)
        foreach ($expected in @('First public response.', 'Second public response.', '### TST-001',
            '#### Response 2', 'Feedback ID:', 'requirements\discovery\topics\test.md', 'UNTRUSTED EMAIL CONTENT')) {
            Assert-True ($before.Contains($expected)) "repository includes $expected"
        }
        foreach ($private in @($first.Key, $second.Key, $first.Sender, $first.Subject,
            $first.Folder, $attempt.Token, 'Private quoted research.')) {
            Assert-True (-not $before.Contains($private)) "repository excludes private metadata or quoted research"
        }
        $ids = [regex]::Matches($before, 'Feedback ID: `([a-f0-9]{64})`')
        Assert-Equal $ids.Count 2 'each response has a stable opaque ID'
        Assert-True ($ids[0].Value -cne $ids[1].Value) 'separate message identities stay distinct'
        $s.State = Read-DiscoveryMailState $s.Directory
        Invoke-TestCycle $s -At '2026-07-01T15:10:00Z' -CollectOnly -RepositoryRoot $repository
        Assert-Equal ([IO.File]::ReadAllText($path)) $before 'repository output stable after restart'
        Assert-Equal $s.State.Replies.Count 2 'no duplicate import'
    }

    Test-Behavior 'repository export strips mobile signatures and Safe Links tracking without changing private replies' {
        $s = New-TestScenario
        Invoke-TestCycle $s
        $link = 'https://nam06.safelinks.protection.outlook.com/?url=https%3A%2F%2Fexample.org%2Fevidence%3Fa%3D1%26b%3D2&data=reader%40example.net&sdata=private-tracking'
        $raw = "Keep this evidence <$link>.`r`nGet Outlook for Android <$link>`r`nKeep text after the signature.`r`nGet Outlook for iOS https://example.org/app"
        $s.Fake.Messages = @(New-TestReply $s -Body $raw -ReceivedUtc '2026-07-01T15:02:00.0000000+00:00')
        $repository = Join-Path $s.Directory 'repository'
        Invoke-TestCycle $s -At '2026-07-01T15:05:00Z' -CollectOnly -RepositoryRoot $repository
        $path = Join-Path $repository "requirements\feedback\inbox\discovery-$($s.State.Campaign).md"
        $before = [IO.File]::ReadAllText($path)
        Assert-True ($before.Contains('Keep this evidence <https://example.org/evidence?a=1&b=2>.')) 'destination and query retained'
        Assert-True ($before.Contains('Keep text after the signature.')) 'later response text retained'
        foreach ($private in @('Get Outlook for', 'safelinks.protection.outlook.com', 'reader%40example.net', 'private-tracking')) {
            Assert-True (-not $before.Contains($private)) 'signature and wrapper metadata excluded'
        }
        $s.State = Read-DiscoveryMailState $s.Directory
        Assert-Equal $s.State.Replies[0].Body $raw 'raw reply unchanged'
        Assert-True ($s.State.Replies[0].FeedbackText.Contains($link)) 'private extracted reply unchanged'
        Assert-True ([IO.File]::ReadAllText((Join-Path $s.Directory 'feedback.md')).Contains($link)) 'private collection retains metadata'
        Assert-Equal $s.State.MoreRequests.Count 0 'cleanup does not queue requests'
        Invoke-TestCycle $s -At '2026-07-01T15:10:00Z' -CollectOnly -RepositoryRoot $repository
        Assert-Equal ([IO.File]::ReadAllText($path)) $before 'cleaned existing campaign stays stable after reload'
        $s.Fake.Messages += New-TestReply $s -Body 'A later response.' -Key '<later@example.net>'
        Invoke-TestCycle $s -At '2026-07-01T15:15:00Z' -CollectOnly -RepositoryRoot $repository
        $after = [IO.File]::ReadAllText($path)
        Assert-True ($after.Contains('A later response.') -and $after.Contains('#### Response 2')) 'later replies update the same file'
        Assert-True ($after.Contains('Keep text after the signature.')) 'prior feedback preserved'
    }

    Test-Behavior 'repository cleanup preserves ordinary text and rejects unexportable Safe Links destinations' {
        $text = "Discuss Get Outlook for Android here.`nhttps://example.org/?data=public`nGet Outlook for Android is not a signature."
        Assert-Equal (Get-DiscoveryRepositoryReplyText $text) $text 'ordinary text and links are unchanged'
        Assert-Equal (Get-DiscoveryRepositoryReplyText "Feedback.  `n  Indented continuation.`t`nMore.") `
            "Feedback.`n  Indented continuation.`nMore." 'trailing whitespace normalized without removing indentation'
        Assert-Equal (Get-DiscoveryRepositoryReplyText "Get Outlook for Android`nGet Outlook for iOS") '' 'signature-only reply becomes empty'
        foreach ($query in @('data=private', 'url=ftp%3A%2F%2Fexample.org', 'url=https%3A%2F%2Fuser%3Apassword%40example.org',
            'url=https%3A%2F%2Fexample.org&url=https%3A%2F%2Fother.example.org')) {
            Assert-Throws {
                Get-DiscoveryRepositoryReplyText "https://nam06.safelinks.protection.outlook.com/?$query"
            } 'Cannot export a Safe Links wrapper'
        }
    }

    Test-Behavior 'offline export backfills existing campaigns without modifying progress or accessing Outlook' {
        $repository = Join-Path $testRoot 'offline-export'
        $scriptPath = Join-Path $PSScriptRoot '..\discovery-mail.ps1'
        $paths = @()
        foreach ($number in @(1, 2)) {
            $s = New-TestScenario
            Invoke-TestCycle $s
            $s.Fake.Messages = @(@{
                Key = "<backfill-$number@example.net>"; Sender = 'reader@example.net'
                Subject = "RE: $($s.State.Attempts[0].Subject)"; ReceivedUtc = '2026-07-01T15:02:00Z'
                Folder = 'Inbox\myself'; Body = "Backfilled response $number."
            })
            Invoke-TestCycle $s -At '2026-07-01T15:05:00Z' -CollectOnly
            $path = Join-Path $repository "requirements\feedback\inbox\discovery-$($s.State.Campaign).md"
            $paths += $path
            $statePath = Join-Path $s.Directory 'state.json'
            $before = [IO.File]::ReadAllText($statePath)
            & $scriptPath -Action ExportFeedback -StateDirectory $s.Directory -RepositoryRoot $repository -WhatIf
            Assert-True (-not [IO.File]::Exists($path)) 'WhatIf prevents export'
            & $scriptPath -Action ExportFeedback -StateDirectory $s.Directory -RepositoryRoot $repository
            Assert-True ([IO.File]::ReadAllText($path).Contains("Backfilled response $number.")) 'existing response exported'
            Assert-Equal ([IO.File]::ReadAllText($statePath)) $before 'offline export never modifies delivery or scan state'
        }
        Assert-True ([IO.File]::Exists($paths[0]) -and [IO.File]::Exists($paths[1])) 'campaign files coexist'
        Assert-True ($paths[0] -cne $paths[1]) 'campaigns never share a filename'
    }

    Test-Behavior 'repository export failure retains imported replies for repair' {
        $s = New-TestScenario
        Invoke-TestCycle $s
        $s.Fake.Messages = @(@{
            Key = '<repair-export@example.net>'; Sender = 'reader@example.net'
            Subject = "RE: $($s.State.Attempts[0].Subject)"; ReceivedUtc = '2026-07-01T15:02:00Z'
            Folder = 'Inbox\myself'; Body = "Recover this repository response.`nMORE QUESTIONS"
        })
        $repository = Join-Path $s.Directory 'repository'
        $parent = Join-Path $repository 'requirements\feedback'
        $null = [IO.Directory]::CreateDirectory($parent)
        $blocked = Join-Path $parent 'inbox'
        [IO.File]::WriteAllText($blocked, 'An existing file blocks directory creation.')
        Assert-Throws {
            Invoke-TestCycle $s -At '2026-07-01T15:05:00Z' -CollectOnly -RepositoryRoot $repository
        } '.*'
        $s.State = Read-DiscoveryMailState $s.Directory
        Assert-Equal $s.State.Replies.Count 1 'reply committed before export failure'
        Assert-Equal $s.State.MoreRequests.Count 1 'request committed atomically with reply before export failure'
        [IO.File]::Delete($blocked)
        Invoke-TestCycle $s -At '2026-07-01T15:10:00Z' -CollectOnly -RepositoryRoot $repository
        $path = Join-Path $blocked "discovery-$($s.State.Campaign).md"
        Assert-True ([IO.File]::ReadAllText($path).Contains('Recover this repository response.')) 'next run repairs export'
        Assert-Equal $s.State.Replies.Count 1 'repair never duplicates response'
        Assert-Equal $s.State.MoreRequests.Count 1 'repair never duplicates request'
    }

    Test-Behavior 'repository export skips empty campaigns and rejects unsafe campaign filenames' {
        $s = New-TestScenario
        $repository = Join-Path $s.Directory 'repository'
        Write-DiscoveryMailFeedback $s.State $s.Directory $repository
        Assert-True (-not [IO.Directory]::Exists($repository)) 'no empty inbox export'
        $s.State.Replies = @(@{
            Key = '<unsafe@example.net>'; Token = 'fake'; Sender = 'reader@example.net'
            QuestionId = 'TST-001'; Subject = 'test'; ReceivedUtc = '2026-07-01T15:00:00Z'
            Folder = 'Inbox\myself'; Body = 'Feedback'; FeedbackText = 'Feedback'; Extraction = 'test'
        })
        $s.State.Campaign = '..\escape'
        Assert-Throws {
            Write-DiscoveryMailFeedback $s.State $s.Directory $repository
        } 'Invalid campaign ID'
        Assert-True (-not [IO.Directory]::Exists($repository)) 'unsafe ID never creates repository directories'
    }

    Test-Behavior 'saved replies repair a failed Markdown projection' {
        $s = New-TestScenario
        $statePath = Join-Path $s.Directory 'feedback.md'
        $null = [IO.Directory]::CreateDirectory($statePath)
        Assert-Throws { Invoke-TestCycle $s -CollectOnly } '.*'
        Assert-True ((Read-DiscoveryMailState $s.Directory).LastScanUtc -ne $null) 'state committed before projection'
        [IO.Directory]::Delete($statePath)
        Invoke-TestCycle $s -CollectOnly
        Assert-True ([IO.File]::Exists($statePath)) 'next run repairs projection'
    }

    Test-Behavior 'completed campaign still collects and never sends again' {
        $s = New-TestScenario -Count 1
        Invoke-TestCycle $s
        $s.Fake.Status = 'Sent'
        Invoke-TestCycle $s -At '2026-07-01T15:05:00Z'
        $s.Fake.Messages = @(@{
            Key = '<late@example.net>'; Sender = 'reader@example.net'
            Subject = "RE: $($s.State.Attempts[0].Subject)"
            ReceivedUtc = '2026-07-02T19:00:00Z'; Folder = 'Inbox\myself'; Body = 'Late feedback.'
        })
        Invoke-TestCycle $s -At '2026-07-02T19:00:00Z'
        Assert-Equal $s.Fake.Sends 1 'no mail after exhaustion'
        Assert-Equal $s.State.Replies.Count 1 'late reply collected'
    }

    Test-Behavior 'incremental collection overlaps one day and failures do not advance its watermark' {
        $s = New-TestScenario
        Invoke-TestCycle $s -At '2026-07-03T19:00:00Z' -CollectOnly
        Invoke-TestCycle $s -At '2026-07-03T19:05:00Z' -CollectOnly
        Assert-Equal $s.Fake.Since ([DateTimeOffset]'2026-07-02T19:00:00Z') 'one day overlap'
        $watermark = (Read-DiscoveryMailState $s.Directory).LastScanUtc
        $s.Transport.ReadReplies = { throw 'Simulated inbox read failure' }
        Assert-Throws { Invoke-TestCycle $s -At '2026-07-03T19:10:00Z' -CollectOnly } 'inbox read failure'
        Assert-Equal (Read-DiscoveryMailState $s.Directory).LastScanUtc $watermark 'failed scan is retried'
    }

    Test-Behavior 'real command entry point honors WhatIf and refuses to overwrite an initialized campaign' {
        $directory = Join-Path $testRoot 'command-entry'
        $scriptPath = Join-Path $PSScriptRoot '..\discovery-mail.ps1'
        & $scriptPath -Action Initialize -StateDirectory $directory `
            -Sender 'sender@example.org' -Recipient 'reader@example.net' -WhatIf
        Assert-True (-not [IO.File]::Exists((Join-Path $directory 'state.json'))) 'WhatIf prevents initialization'
        & $scriptPath -Action Initialize -StateDirectory $directory `
            -Sender 'sender@example.org' -Recipient 'reader@example.net'
        $created = (Read-DiscoveryMailState $directory).Campaign
        Assert-Throws {
            & $scriptPath -Action Initialize -StateDirectory $directory `
                -Sender 'sender@example.org' -Recipient 'reader@example.net'
        } 'campaign already exists'
        Assert-Equal (Read-DiscoveryMailState $directory).Campaign $created 'no reset'
        & $scriptPath -Action Run -StateDirectory $directory -WhatIf
        & $scriptPath -Action RegisterTask -StateDirectory $directory -WhatIf
        Assert-Equal (Read-DiscoveryMailState $directory).Attempts.Count 0 'WhatIf never submits mail'
    }

    Test-Behavior 'preview, WhatIf, exclusive lock, and explicit pending resolution' {
        $s = New-TestScenario
        $options = @{
            Action = 'Preview'; StateDirectory = $s.Directory; RepositoryRoot = $root
            TaskName = 'Test only'; PendingToken = $null; Resolution = $null
        }
        $preview = Invoke-DiscoveryMailCommand $options 'unused.ps1' { $true }
        Assert-True ($preview.Contains('PREVIEW - NOT SENT')) 'preview clearly marked'
        Assert-Equal (Read-DiscoveryMailState $s.Directory).Attempts.Count 0 'preview creates no journal'
        $options.Action = 'Run'
        Invoke-DiscoveryMailCommand $options 'unused.ps1' { $false }
        Assert-Equal (Read-DiscoveryMailState $s.Directory).Attempts.Count 0 'declined ShouldProcess never opens Outlook'
        $handle = [IO.File]::Open((Join-Path $s.Directory 'campaign.lock'), 'OpenOrCreate', 'ReadWrite', 'None')
        try {
            Assert-Throws { Invoke-DiscoveryMailCommand $options 'unused.ps1' { $true } } '.*'
        } finally { $handle.Dispose() }
        Invoke-TestCycle $s
        $options.Action = 'ResolvePending'; $options.Resolution = 'Retry'; $options.PendingToken = 'wrong'
        Assert-Throws { Invoke-DiscoveryMailCommand $options 'unused.ps1' { $true } } 'exact pending token'
        $options.PendingToken = $s.State.PendingToken
        Invoke-DiscoveryMailCommand $options 'unused.ps1' { $true }
        $s.State = Read-DiscoveryMailState $s.Directory
        Assert-Equal $s.State.PendingToken $null 'operator cleared pending'
        Assert-Equal $s.State.Attempts[0].Status 'RetryAuthorized' 'old marker retained for feedback'
        Invoke-TestCycle $s
        Assert-Equal $s.Fake.Sends 2 'explicit authorization permits retry'
        $options.Resolution = 'Sent'; $options.PendingToken = $s.State.PendingToken
        Invoke-DiscoveryMailCommand $options 'unused.ps1' { $true }
        Assert-Equal (Read-DiscoveryMailState $s.Directory).NextIndex 1 'operator Sent advances once'
    }

    Test-Behavior 'address and folder validation reject unsafe or ambiguous configuration' {
        Assert-Throws { Assert-MailAddress "reader@example.net`nBcc: stranger@example.org" } '.*'
        Assert-Throws { Assert-MailAddress 'Display Name <reader@example.net>' } 'plain SMTP'
        $s = New-TestScenario
        Assert-Throws {
            New-DiscoveryMailState $s.State.Questions 'sender@example.org' 'reader@example.net' `
                @() @('..\OtherMailbox') ([DateTimeOffset]::UtcNow)
        } 'mailbox-relative'
    }

    Test-Behavior 'reply command requires an allowed correlated standalone unquoted line' {
        $s = New-TestScenario
        Invoke-TestCycle $s
        $bodies = @(
            'Please send MORE QUESTIONS.',
            "My feedback.`n-----Original Message-----`nMORE QUESTIONS",
            "My feedback.`nOn Wed, Jul 1, someone wrote:`nMORE QUESTIONS",
            "> MORE QUESTIONS`nMy feedback.",
            "My feedback.`nFrom: sender@example.org`nSent: Wednesday`nTo: reader@example.net`nSubject: Original`nMORE QUESTIONS",
            "My feedback.`n  more questions `nMORE QUESTIONS"
        )
        $s.Fake.Messages = @(for ($i = 0; $i -lt $bodies.Count; $i++) {
            New-TestReply $s $bodies[$i] "<keyword-$i@example.net>"
        })
        $stranger = New-TestReply $s -Key '<stranger-command@example.net>'
        $stranger.Sender = 'stranger@example.net'
        $unknown = New-TestReply $s -Key '<unknown-command@example.net>'
        $unknown.Subject = '[AHHE:unknown]'
        $s.Fake.Messages += @($stranger, $unknown)
        Invoke-TestCycle $s -At '2026-07-01T15:05:00Z' -CollectOnly
        Assert-Equal $s.State.MoreRequests.Count 1 'only the new unquoted command authorizes one request'
        Assert-Equal $s.State.MoreRequests[0].Key '<keyword-5@example.net>' 'request links to exact reply'
        Assert-Equal $s.State.MoreRequests[0].Status 'Queued' 'collect queues without sending'
        Assert-Equal $s.Fake.Sends 1 'collect-only never sends extra mail'
        Assert-Equal $s.State.Replies.Count 6 'all allowed feedback retained'
        $s.State = Read-DiscoveryMailState $s.Directory
        Invoke-TestCycle $s -At '2026-07-01T15:10:00Z' -FullRescan -CollectOnly
        Assert-Equal $s.State.MoreRequests.Count 1 'restart and rescan cannot repeat the request'
    }

    Test-Behavior 'extra requests drain oldest first outside slots without consuming regular delivery' {
        $s = New-TestScenario -Count 5
        Invoke-TestCycle $s
        $s.Fake.Status = 'Sent'
        Invoke-TestCycle $s -At '2026-07-01T15:05:00Z'
        $s.Fake.Messages = @(
            (New-TestReply $s -Key '<newer@example.net>' -ReceivedUtc '2026-07-01T15:09:00Z'),
            (New-TestReply $s -Key '<older@example.net>' -ReceivedUtc '2026-07-01T15:08:00Z')
        )
        Invoke-TestCycle $s -At '2026-07-01T15:10:00Z'
        Assert-Equal $s.Fake.Sends 2 'only one extra send per poll'
        $saved = Read-DiscoveryMailState $s.Directory
        Assert-Equal $saved.Attempts[1].RequestKey '<older@example.net>' 'oldest request served first'
        Assert-Equal @($saved.MoreRequests | Where-Object Status -CEQ 'Pending').Count 1 'request journal saved before send'
        $s.State = $saved
        Invoke-TestCycle $s -At '2026-07-01T15:15:00Z'
        Assert-Equal $s.Fake.Sends 3 'next poll can serve another request'
        Assert-Equal $s.State.Attempts[2].RequestKey '<newer@example.net>' 'newer request follows'
        Invoke-TestCycle $s -At '2026-07-01T15:20:00Z'
        Assert-Equal $s.Fake.Sends 3 'repeated import cannot send again'
        Assert-Equal @($s.State.MoreRequests | Where-Object Status -CEQ 'Sent').Count 2 'both requests confirmed'
        Assert-Equal $s.State.LastSlot '2026-07-01T08:00' 'extras never change scheduled slot'
        Invoke-TestCycle $s -At '2026-07-01T19:00:00Z'
        Assert-Equal $s.Fake.Sends 4 'noon still sends its regular question'
        Assert-Equal $s.State.Attempts[3].QuestionId 'TST-004' 'one shared question queue'
    }

    Test-Behavior 'due schedule takes priority while extra requests also work before morning' {
        $s = New-TestScenario -Count 5
        Invoke-TestCycle $s
        $s.Fake.Status = 'Sent'
        $s.Fake.Messages = @((New-TestReply $s))
        Invoke-TestCycle $s -At '2026-07-01T19:00:00Z'
        Assert-Equal $s.Fake.Sends 2 'one send when both triggers are eligible'
        Assert-Equal $s.State.Attempts[1].Slot '2026-07-01T12:00' 'due schedule has priority'
        Assert-Equal $s.State.MoreRequests[0].Status 'Queued' 'extra remains queued'
        Invoke-TestCycle $s -At '2026-07-02T13:00:00Z'
        Assert-Equal $s.Fake.Sends 3 'extra can run before the next morning slot'
        Assert-Equal $s.State.Attempts[2].Slot 'ON-DEMAND' 'no catch-up of missed evening slot'
    }

    Test-Behavior 'uncertain extra sends stay pending across restarts and operator recovery' {
        $s = New-TestScenario
        Invoke-TestCycle $s
        $s.Fake.Status = 'Sent'
        $s.Fake.Messages = @((New-TestReply $s))
        $s.Fake.Fail = $true
        Assert-Throws { Invoke-TestCycle $s -At '2026-07-01T15:05:00Z' } 'Simulated Outlook failure'
        $s.State = Read-DiscoveryMailState $s.Directory
        $s.Fake.Fail = $false
        $s.Fake.Status = 'Outbox'
        Invoke-TestCycle $s -At '2026-07-01T15:10:00Z'
        Assert-Equal $s.Fake.Sends 2 'outbox blocks another submission'
        $s.Fake.Status = 'Missing'
        Assert-Throws { Invoke-TestCycle $s -At '2026-07-01T15:15:00Z' } 'automatic resend is blocked'
        $options = @{
            Action = 'ResolvePending'; StateDirectory = $s.Directory; RepositoryRoot = $root
            TaskName = 'Test only'; PendingToken = $s.State.PendingToken; Resolution = 'Retry'
        }
        Invoke-DiscoveryMailCommand $options 'unused.ps1' { $true }
        $s.State = Read-DiscoveryMailState $s.Directory
        Assert-Equal $s.State.MoreRequests[0].Status 'Queued' 'explicit retry requeues the same request'
        Invoke-TestCycle $s -At '2026-07-01T15:20:00Z'
        Assert-Equal $s.Fake.Sends 3 'one explicitly authorized retry'
        Assert-Equal $s.State.Attempts[2].RequestKey $s.State.Attempts[1].RequestKey 'retry keeps its source request'
        $options.PendingToken = $s.State.PendingToken; $options.Resolution = 'Sent'
        Invoke-DiscoveryMailCommand $options 'unused.ps1' { $true }
        $s.State = Read-DiscoveryMailState $s.Directory
        Assert-Equal $s.State.MoreRequests[0].Status 'Sent' 'operator confirmation fulfills once'
        Assert-Equal $s.State.NextIndex 2 'progress advances once for the extra'
        Assert-Equal $s.State.LastSlot '2026-07-01T08:00' 'operator confirmation preserves regular slot'
    }

    Test-Behavior 'legacy state keeps its snapshot and progress without interpreting old feedback as commands' {
        $s = New-TestScenario
        Invoke-TestCycle $s
        $s.Fake.Status = 'Sent'
        $s.Fake.Messages = @((New-TestReply $s))
        Invoke-TestCycle $s -At '2026-07-01T15:05:00Z' -CollectOnly
        $null = $s.State.Remove('MoreRequests')
        foreach ($attempt in $s.State.Attempts) { $null = $attempt.Remove('RequestKey') }
        Save-DiscoveryMailState $s.State $s.Directory
        $before = [IO.File]::ReadAllText((Join-Path $s.Directory 'state.json'))
        $s.State = Read-DiscoveryMailState $s.Directory
        Assert-Equal $s.State.MoreRequests.Count 0 'legacy upgrade does not create retroactive commands'
        Assert-Equal ([IO.File]::ReadAllText((Join-Path $s.Directory 'state.json'))) $before 'reading never overwrites legacy state'
        $originalCampaign = $s.State.Campaign
        Invoke-TestCycle $s -At '2026-07-01T15:10:00Z'
        Assert-Equal $s.State.Campaign $originalCampaign 'same campaign'
        Assert-Equal $s.State.NextIndex 1 'same progress'
        Assert-Equal $s.State.LastSlot '2026-07-01T08:00' 'same slot watermark'
        Assert-Equal $s.State.MoreRequests.Count 0 'rescanned legacy reply stays deduplicated'
        Assert-Equal $s.Fake.Sends 1 'upgrade does not submit another question'
    }

    Test-Behavior 'exhaustion settles requests and still retains later responses' {
        $s = New-TestScenario -Count 1
        Invoke-TestCycle $s
        $s.Fake.Status = 'Sent'
        $s.Fake.Messages = @((New-TestReply $s))
        Invoke-TestCycle $s -At '2026-07-01T15:05:00Z'
        $s.State = Read-DiscoveryMailState $s.Directory
        Assert-Equal $s.State.MoreRequests[0].Status 'Exhausted' 'no phantom pending request at queue end'
        Assert-Equal $s.State.Replies.Count 1 'request feedback still collected'
        Invoke-TestCycle $s -At '2026-07-01T19:00:00Z'
        Assert-Equal $s.Fake.Sends 1 'no send beyond the frozen queue'
    }

    Test-Behavior 'legacy task upgrades only its action and start stop resume preserve uncertain progress' {
        $s = New-TestScenario
        Invoke-TestCycle $s
        $s.Fake.Messages = @((New-TestReply $s))
        Invoke-TestCycle $s -At '2026-07-01T15:05:00Z' -CollectOnly
        $scriptPath = Join-Path $root 'scripts\discovery-mail.ps1'
        $name = 'AHHE Discovery Learning Tour'
        $definition = Get-DiscoveryMailTaskDefinition $name $s.Directory $scriptPath
        $script:scheduler = @{ Task = $null; Registered = 0; Updated = 0; Enabled = 0; Removed = 0 }
        Register-DiscoveryMailTask $name $s.Directory $scriptPath
        $task = $script:scheduler.Task
        $task.Actions = @([pscustomobject]@{ Execute = $definition.LegacyExecute; Arguments = $definition.LegacyArguments })
        $identity = [Security.Principal.WindowsIdentity]::GetCurrent()
        $task.Principal.UserId = ($identity.Name -split '\\')[-1]
        $task | Add-Member -NotePropertyName RegisteredUserId `
            -NotePropertyValue $identity.User.Value
        $task.State = 'Disabled'
        $configuration = @($task.Triggers, $task.Principal, $task.Settings) | ConvertTo-Json -Depth 10
        $stateBefore = [IO.File]::ReadAllText((Join-Path $s.Directory 'state.json'))
        Register-DiscoveryMailTask $name $s.Directory $scriptPath -UpgradeOnly
        Assert-Equal $script:scheduler.Updated 1 'legacy action upgraded using the registered SID despite a short display name'
        Assert-Equal $task.Actions[0].Execute $definition.Execute 'scheduled entry is the GUI host'
        Assert-Equal $task.Actions[0].Arguments $definition.Arguments 'all launcher arguments preserved'
        Assert-Equal $task.State 'Disabled' 'automatic migration never enables a stopped task'
        Assert-Equal (@($task.Triggers, $task.Principal, $task.Settings) | ConvertTo-Json -Depth 10) $configuration 'all other configuration preserved'
        Register-DiscoveryMailTask $name $s.Directory $scriptPath -UpgradeOnly
        Assert-Equal $script:scheduler.Updated 1 'upgrade is idempotent'
        $options = @{ Action = 'Start'; StateDirectory = $s.Directory; RepositoryRoot = $root; TaskName = $name }
        Invoke-DiscoveryMailCommand $options $scriptPath { $true }
        Invoke-DiscoveryMailCommand $options $scriptPath { $true }
        Assert-Equal $script:scheduler.Enabled 1 'start resumes once'
        $options.Action = 'Stop'
        $lock = [IO.File]::Open((Join-Path $s.Directory 'campaign.lock'), 'OpenOrCreate', 'ReadWrite', 'None')
        try { Invoke-DiscoveryMailCommand $options $scriptPath { $true } } finally { $lock.Dispose() }
        Invoke-DiscoveryMailCommand $options $scriptPath { $true }
        Assert-Equal $script:scheduler.Removed 1 'stop is idempotent and does not need the active campaign lock'
        $options.Action = 'Start'
        Invoke-DiscoveryMailCommand $options $scriptPath { $true }
        Assert-Equal $script:scheduler.Registered 2 'resume recreates the poller only'
        Assert-Equal ([IO.File]::ReadAllText((Join-Path $s.Directory 'state.json'))) $stateBefore 'all progress and pending requests byte-for-byte unchanged'
        $s.State = Read-DiscoveryMailState $s.Directory
        $s.Fake.Status = 'Sent'
        Invoke-TestCycle $s -At '2026-07-01T15:10:00Z'
        Assert-Equal $s.State.NextIndex 1 'prior uncertain submission is reconciled, not repeated'
        Assert-Equal $s.State.Attempts[1].QuestionId 'TST-002' 'queued extra resumes at the next question'
    }

    Test-Behavior 'short task names cannot bypass registered ownership or principal safety checks' {
        $s = New-TestScenario
        $scriptPath = Join-Path $root 'scripts\discovery-mail.ps1'
        $name = 'AHHE Discovery Learning Tour'
        $script:scheduler = @{ Task = $null; Registered = 0; Updated = 0; Enabled = 0; Removed = 0 }
        Register-DiscoveryMailTask $name $s.Directory $scriptPath
        $task = $script:scheduler.Task
        $definition = Get-DiscoveryMailTaskDefinition $name $s.Directory $scriptPath
        $task.Actions = @([pscustomobject]@{ Execute = $definition.LegacyExecute; Arguments = $definition.LegacyArguments })
        $identity = [Security.Principal.WindowsIdentity]::GetCurrent()
        $task.Principal.UserId = ($identity.Name -split '\\')[-1]
        $task | Add-Member -NotePropertyName RegisteredUserId -NotePropertyValue 'S-1-0-0'
        $before = [IO.File]::ReadAllText((Join-Path $s.Directory 'state.json'))
        foreach ($registeredId in @('S-1-0-0', "OTHER\$($task.Principal.UserId)", 'unresolved-account', '')) {
            $task.RegisteredUserId = $registeredId
            Assert-Throws { Register-DiscoveryMailTask $name $s.Directory $scriptPath -UpgradeOnly } 'ownership'
            Assert-Throws { Unregister-DiscoveryMailTask $name $s.Directory $scriptPath } 'ownership'
        }
        $task.RegisteredUserId = $identity.User.Value
        $task.Principal.LogonType = 'Password'
        Assert-Throws { Register-DiscoveryMailTask $name $s.Directory $scriptPath -UpgradeOnly } 'ownership'
        $task.Principal.LogonType = 'Interactive'
        $task.Principal.RunLevel = 'Highest'
        Assert-Throws { Register-DiscoveryMailTask $name $s.Directory $scriptPath -UpgradeOnly } 'ownership'
        $task.Principal.RunLevel = 'Limited'
        $task.Actions += $task.Actions[0]
        Assert-Throws { Register-DiscoveryMailTask $name $s.Directory $scriptPath -UpgradeOnly } 'ownership'
        Assert-Equal $script:scheduler.Updated 0 'unverified or unsafe tasks are never migrated'
        Assert-Equal $script:scheduler.Removed 0 'unverified tasks are never removed'
        Assert-Equal ([IO.File]::ReadAllText((Join-Path $s.Directory 'state.json'))) $before 'failed ownership checks preserve campaign state'
    }

    Test-Behavior 'task management refuses foreign actions principals and missing state' {
        $s = New-TestScenario
        $scriptPath = Join-Path $root 'scripts\discovery-mail.ps1'
        $name = 'AHHE Discovery Learning Tour'
        $script:scheduler = @{ Task = $null; Registered = 0; Updated = 0; Enabled = 0; Removed = 0 }
        Register-DiscoveryMailTask $name $s.Directory $scriptPath -UpgradeOnly
        Assert-Equal $script:scheduler.Registered 0 'manual Run does not install a task'
        Register-DiscoveryMailTask $name $s.Directory $scriptPath
        $task = $script:scheduler.Task
        $task.Actions[0].Arguments += ' -Unexpected'
        Assert-Throws { Register-DiscoveryMailTask $name $s.Directory $scriptPath } 'not recognized'
        Assert-Throws { Unregister-DiscoveryMailTask $name $s.Directory $scriptPath } 'not recognized'
        $definition = Get-DiscoveryMailTaskDefinition $name $s.Directory $scriptPath
        $task.Actions[0].Arguments = $definition.Arguments
        $task.Principal.UserId = 'another-user'
        Assert-Throws { Register-DiscoveryMailTask $name $s.Directory $scriptPath -UpgradeOnly } 'ownership'
        Assert-Equal $script:scheduler.Updated 0 'foreign tasks untouched'
        $script:scheduler.Task = $null
        $options = @{ Action = 'Start'; StateDirectory = (Join-Path $testRoot 'missing-state'); RepositoryRoot = $root; TaskName = $name }
        Assert-Throws { Invoke-DiscoveryMailCommand $options $scriptPath { $true } } 'Run Initialize first'
        Assert-Equal $script:scheduler.Registered 1 'missing state never registers a new campaign'
        $scriptPath = Join-Path $s.Directory 'absent\discovery-mail.ps1'
        Assert-Throws { Register-DiscoveryMailTask $name $s.Directory $scriptPath } 'launcher is unavailable'
        Assert-Throws { Get-DiscoveryMailTaskDefinition 'bad%name' $s.Directory $scriptPath } 'Invalid task argument'
        $options.StateDirectory = $s.Directory
        $realScript = Join-Path $root 'scripts\discovery-mail.ps1'
        & $realScript -Action Start -StateDirectory $s.Directory -WhatIf
        & $realScript -Action Stop -StateDirectory $s.Directory -WhatIf
        Assert-Equal $script:scheduler.Task $null 'WhatIf does not touch scheduler'
    }

    Test-Behavior 'an existing scheduled Run self-upgrades before Outlook access without changing campaign state' {
        $s = New-TestScenario
        $scriptPath = Join-Path $root 'scripts\discovery-mail.ps1'
        $name = 'AHHE Discovery Learning Tour'
        $script:scheduler = @{ Task = $null; Registered = 0; Updated = 0; Enabled = 0; Removed = 0 }
        Register-DiscoveryMailTask $name $s.Directory $scriptPath
        $definition = Get-DiscoveryMailTaskDefinition $name $s.Directory $scriptPath
        $legacyAction = [pscustomobject]@{ Execute = $definition.LegacyExecute; Arguments = $definition.LegacyArguments }
        $script:scheduler.Task.Actions = @($legacyAction)
        function New-OutlookTransport { param($Config); throw 'Stopped at the fake Outlook boundary' }
        $options = @{
            Action = 'Run'; StateDirectory = $s.Directory
            RepositoryRoot = (Join-Path $s.Directory 'repository'); TaskName = $name
        }
        $before = [IO.File]::ReadAllText((Join-Path $s.Directory 'state.json'))
        Assert-Throws { Invoke-DiscoveryMailCommand $options $scriptPath { $true } } 'fake Outlook boundary'
        Assert-Equal $script:scheduler.Updated 1 'normal Run installs its own hidden action'
        Assert-Equal ([IO.File]::ReadAllText((Join-Path $s.Directory 'state.json'))) $before 'upgrade does not reset or advance campaign'
        $script:scheduler.Task.Actions = @($legacyAction)
        $options.Action = 'Collect'
        Assert-Throws { Invoke-DiscoveryMailCommand $options $scriptPath { $true } } 'fake Outlook boundary'
        Assert-Equal $script:scheduler.Updated 1 'collection-only action does not manage scheduled tasks'
    }

    Test-Behavior 'windowless launcher forwards spaced arguments waits and propagates the child exit code' {
        $directory = Join-Path $testRoot 'launcher spaced paths'
        $null = [IO.Directory]::CreateDirectory($directory)
        $fixture = Join-Path $directory 'harmless child.ps1'
        [IO.File]::WriteAllText($fixture, @'
param([string]$Action, [string]$StateDirectory, [string]$TaskName)
$ErrorActionPreference = 'Stop'
Add-Type -TypeDefinition @"
using System;
using System.Runtime.InteropServices;
public static class DiscoveryLauncherProbe {
    [DllImport("kernel32.dll")] public static extern IntPtr GetConsoleWindow();
    [DllImport("user32.dll")] public static extern bool IsWindowVisible(IntPtr window);
}
"@
$visible = [DiscoveryLauncherProbe]::IsWindowVisible([DiscoveryLauncherProbe]::GetConsoleWindow())
Start-Sleep -Milliseconds 150
@{ Visible = $visible; Action = $Action; Directory = $StateDirectory; Task = $TaskName;
   Apartment = [Threading.Thread]::CurrentThread.ApartmentState.ToString() } |
    ConvertTo-Json | Set-Content -LiteralPath (Join-Path $StateDirectory 'probe.json')
exit 23
'@)
        $launcher = Join-Path $root 'scripts\discovery-mail-launcher.vbs'
        [IO.File]::Copy($launcher, (Join-Path $directory 'discovery-mail-launcher.vbs'))
        foreach ($stateDirectory in @($directory, "$directory\")) {
            $definition = Get-DiscoveryMailTaskDefinition 'Test task with spaces' $stateDirectory $fixture
            $process = Start-Process -FilePath $definition.Execute -ArgumentList $definition.Arguments -PassThru -Wait
            Assert-Equal $process.ExitCode 23 'launcher propagates nonzero child exit code'
            $probe = [IO.File]::ReadAllText((Join-Path $directory 'probe.json')) | ConvertFrom-Json
            Assert-Equal $probe.Visible $false 'PowerShell console is not visible'
            Assert-Equal $probe.Action 'Run' 'scheduled action forwarded'
            Assert-Equal $probe.Directory $stateDirectory 'spaced state path and trailing separator forwarded'
            Assert-Equal $probe.Task 'Test task with spaces' 'task name forwarded'
            Assert-Equal $probe.Apartment 'STA' 'Outlook-compatible apartment'
        }
        $invalid = Start-Process -FilePath (Join-Path $env:WINDIR 'System32\wscript.exe') `
            -ArgumentList "//B //Nologo `"$launcher`"" -PassThru -Wait
        Assert-Equal $invalid.ExitCode 2 'invalid launcher invocation fails'
    }

    Test-Behavior 'Outlook adapter selects explicit account, folders, plain text, and sent copy' {
        $s = New-TestScenario
        $sent = [pscustomobject]@{ Items = $null }
        $drafts = [pscustomobject]@{ Items = $null }
        $outbox = [pscustomobject]@{ Items = $null }
        $replyFolder = [pscustomobject]@{ Items = $null }
        $inbox = [pscustomobject]@{ Folders = [pscustomobject]@{}; Items = $null }
        $inbox.Folders | Add-Member ScriptMethod Item {
            param($Name)
            Assert-Equal $Name 'myself' 'configured reply subfolder'
            return $replyFolder
        }.GetNewClosure()
        $store = [pscustomobject]@{}
        $store | Add-Member ScriptMethod GetDefaultFolder {
            param($Id)
            switch ($Id) { 4 { $outbox }; 5 { $sent }; 6 { $inbox }; 16 { $drafts }; default { throw 'Unexpected folder' } }
        }.GetNewClosure()
        $account = [pscustomobject]@{ SmtpAddress = 'sender@example.org'; DeliveryStore = $store }
        $other = [pscustomobject]@{ SmtpAddress = 'wrong@example.org' }
        $session = [pscustomobject]@{ Accounts = @($other, $account) }
        $mail = [pscustomobject]@{
            SendUsingAccount = $null; SaveSentMessageFolder = $null; DeleteAfterSubmit = $true
            To = $null; Subject = $null; BodyFormat = 0; Body = $null; Saved = $false; Submitted = $false
        }
        $mail | Add-Member ScriptMethod Save { $this.Saved = $true }
        $mail | Add-Member ScriptMethod Send {
            Assert-True $this.Saved 'draft saved before submission'
            $this.Submitted = $true
        }
        $app = [pscustomobject]@{}
        $app | Add-Member ScriptMethod GetNamespace { param($Name); return $session }.GetNewClosure()
        $app | Add-Member ScriptMethod CreateItem { param($Kind); return $mail }.GetNewClosure()
        $adapter = New-OutlookTransport $s.State.Config $app
        $attempt = New-DiscoveryMailAttempt $s.State '2026-07-01T08:00' ([DateTimeOffset]'2026-07-01T15:00:00Z')
        & $adapter.Send $attempt
        Assert-Equal $mail.SendUsingAccount $account 'never defaults to first account'
        Assert-Equal $mail.SaveSentMessageFolder $sent 'explicit account sent folder'
        Assert-Equal $mail.To 'reader@example.net' 'configured recipient'
        Assert-Equal $mail.Body $attempt.Body 'complete question body'
        Assert-Equal $mail.BodyFormat 1 'plain text'
        Assert-True $mail.Submitted 'one submission'
        $badConfig = $s.State.Config.Clone(); $badConfig.Sender = 'missing@example.org'
        Assert-Throws { New-OutlookTransport $badConfig $app } 'exactly one configured'

        $message = [pscustomobject]@{
            Class = 43; Subject = "RE: $($attempt.Subject)"
            SenderEmailType = 'SMTP'; SenderEmailAddress = 'reader@example.net'
            Body = "New response.`n-----Original Message-----`nQuoted research."
            ReceivedTime = [DateTime]'2026-07-01T08:02:00'
            PropertyAccessor = [pscustomobject]@{}; EntryID = 'entry-id'
        }
        $message.PropertyAccessor | Add-Member ScriptMethod GetProperty {
            param($Name); return '<adapter-reply@example.net>'
        }
        $items = [pscustomobject]@{ Count = 1 }
        $items | Add-Member ScriptMethod Item { param($Index); return $message }.GetNewClosure()
        $items | Add-Member ScriptMethod Restrict { param($Filter); return $this }
        $inbox.Items = $items; $replyFolder.Items = $items
        $s.State.Attempts += $attempt
        $read = @(& $adapter.ReadReplies ([DateTimeOffset]$s.State.CreatedUtc) $s.State)
        Assert-Equal $read.Count 2 'both inbox and rule destination scanned'
        Assert-Equal $read[1].Folder 'Inbox\myself' 'mail-rule folder recorded'
        Assert-Equal $read[0].Key '<adapter-reply@example.net>' 'internet message ID used'
        Import-DiscoveryMailReplies $s.State $read
        Assert-Equal $s.State.Replies.Count 1 'folder overlap deduplicates'
        Assert-Equal $s.State.Replies[0].FeedbackText 'New response.' 'adapter content feeds extraction'
        $message.PropertyAccessor | Add-Member ScriptMethod GetProperty {
            param($Name)
            throw [Runtime.InteropServices.COMException]::new('Property missing', -2147221233)
        } -Force
        $read = @(& $adapter.ReadReplies ([DateTimeOffset]$s.State.CreatedUtc) $s.State)
        Assert-Equal $read[0].Key 'entry:entry-id' 'MAPI missing Message-ID has documented fallback'
        $message.PropertyAccessor | Add-Member ScriptMethod GetProperty {
            param($Name)
            throw [Runtime.InteropServices.COMException]::new('Access denied', -2147024891)
        } -Force
        Assert-Throws {
            & $adapter.ReadReplies ([DateTimeOffset]$s.State.CreatedUtc) $s.State
        } 'Access denied'
    }

    Write-Host "`n$script:passed behavior tests passed. No Outlook instance or live mailbox used."
} finally {
    # Only the GUID-named fixture directory created by this test run is removed.
    [IO.Directory]::Delete($testRoot, $true)
}
