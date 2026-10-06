[CmdletBinding()]
param()

$ErrorActionPreference = 'Stop'

$repositoryRoot = Split-Path -Parent $PSScriptRoot
$discoveryRoot = Join-Path $repositoryRoot 'requirements\discovery'
$topicsRoot = Join-Path $discoveryRoot 'topics'
$indexPath = Join-Path $discoveryRoot 'question-index.md'
$questionPattern = '(?m)^### ([A-Z]{2,4}-\d{3}) — (.+)$'
$statePattern = '(?m)^\*\*State:\*\* (\w+)'
$questionsById = @{}
$records = [System.Collections.Generic.List[object]]::new()

Get-ChildItem -Path $topicsRoot -Filter '*.md' -File |
    Sort-Object Name |
    ForEach-Object {
        $file = $_
        $content = Get-Content -LiteralPath $file.FullName -Raw
        $matches = [regex]::Matches($content, $questionPattern)

        for ($index = 0; $index -lt $matches.Count; $index++) {
            $match = $matches[$index]
            $questionId = $match.Groups[1].Value
            $title = $match.Groups[2].Value.Trim()
            $bodyStart = $match.Index + $match.Length
            $bodyEnd = if ($index + 1 -lt $matches.Count) {
                $matches[$index + 1].Index
            } else {
                $content.Length
            }
            $body = $content.Substring($bodyStart, $bodyEnd - $bodyStart)
            $stateMatch = [regex]::Match($body, $statePattern)
            $state = if ($stateMatch.Success) {
                $stateMatch.Groups[1].Value
            } else {
                'Unknown'
            }

            if ($questionsById.ContainsKey($questionId)) {
                throw "Duplicate question ID $questionId in $($questionsById[$questionId]) and $($file.Name)."
            }

            $questionsById[$questionId] = $file.Name
            $prefix, $sequenceText = $questionId.Split('-', 2)
            $records.Add([pscustomobject]@{
                    Id = $questionId
                    Prefix = $prefix
                    Sequence = [int]$sequenceText
                    State = $state
                    Title = $title
                    File = $file.Name
                })
        }
    }

$records = $records | Sort-Object Prefix, Sequence
$lines = [System.Collections.Generic.List[string]]::new()
$lines.Add('# Discovery Question Index')
$lines.Add('')
$lines.Add('## Purpose')
$lines.Add('')
$lines.Add('This is the canonical cross-topic index for discovery questions. Every question ID appears exactly once in a topic file. Search this index before creating a new question.')
$lines.Add('')
$lines.Add('This file is generated from headings in `topics/*.md`. Do not use exact wording alone for duplicate detection; review aliases, related questions, and neighboring topics.')
$lines.Add('')
$lines.Add("**Indexed questions:** $($records.Count)")
$lines.Add('')
$lines.Add('## Index')
$lines.Add('')
$lines.Add('| ID | State | Canonical question | Topic file |')
$lines.Add('|---|---|---|---|')

foreach ($record in $records) {
    $safeTitle = $record.Title.Replace('|', '\|')
    $lines.Add("| ``$($record.Id)`` | $($record.State) | $safeTitle | [$($record.File)](topics/$($record.File)) |")
}

$lines.Add('')
$lines.Add('## Maintenance')
$lines.Add('')
$lines.Add('1. Search this index and topic files before assigning a new ID.')
$lines.Add('2. Add repeated wording as an alias or source quotation under the canonical ID.')
$lines.Add('3. Keep IDs permanent even when a question is answered, converted, deferred, or superseded.')
$lines.Add('4. Regenerate this index after changing question headings, files, or states.')
$lines.Add('5. Treat duplicate IDs as an error.')

Set-Content -LiteralPath $indexPath -Value $lines -Encoding utf8
Write-Host "Indexed $($records.Count) questions from $((Get-ChildItem -Path $topicsRoot -Filter '*.md' -File).Count) topic files."
