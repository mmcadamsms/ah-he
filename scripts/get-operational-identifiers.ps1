[CmdletBinding()]
param(
    [Parameter(Mandatory)]
    [ValidatePattern('^[A-Za-z0-9_-]{1,100}$')]
    [string] $BusinessId,

    [string] $Issuer,
    [string] $Subject
)
$ErrorActionPreference = 'Stop'
if ([bool]$Issuer -ne [bool]$Subject) {
    throw 'Supply both verified issuer and subject, or neither.'
}

function Get-Sha256([string] $Value) {
    $algorithm = [Security.Cryptography.SHA256]::Create()
    try {
        return ([BitConverter]::ToString(
            $algorithm.ComputeHash([Text.Encoding]::UTF8.GetBytes($Value))
        )).Replace('-', '').ToLowerInvariant()
    } finally {
        $algorithm.Dispose()
    }
}

$result = [ordered]@{
    businessId = $BusinessId
    workQueueName = 'ops-work-' + (Get-Sha256 $BusinessId).Substring(0, 32)
}
if ($Issuer) {
    $result.principalId = Get-Sha256 "$Issuer`n$Subject"
}
[pscustomobject]$result
