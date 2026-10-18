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
            Folder = 'Inbox\myself'; Body = 'Recover this repository response.'
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
        [IO.File]::Delete($blocked)
        Invoke-TestCycle $s -At '2026-07-01T15:10:00Z' -CollectOnly -RepositoryRoot $repository
        $path = Join-Path $blocked "discovery-$($s.State.Campaign).md"
        Assert-True ([IO.File]::ReadAllText($path).Contains('Recover this repository response.')) 'next run repairs export'
        Assert-Equal $s.State.Replies.Count 1 'repair never duplicates response'
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
