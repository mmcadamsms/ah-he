$ErrorActionPreference = 'Stop'
$root = Split-Path -Parent $PSScriptRoot
$azurite = Join-Path $root 'infrastructure\local\node_modules\azurite\dist\src\azurite.js'
if (-not (Test-Path -LiteralPath $azurite)) {
    throw 'Install the pinned emulator first: npm ci --prefix .\infrastructure\local'
}

$names = @(
    'AZURITE_ACCOUNTS', 'OPS_TEST_CONNECTION_STRING', 'OPS_TEST_TABLE_ENDPOINT',
    'OPS_TEST_BLOB_ENDPOINT', 'OPS_TEST_QUEUE_ENDPOINT'
)
$saved = @{}
foreach ($name in $names) {
    $saved[$name] = [Environment]::GetEnvironmentVariable($name, 'Process')
}
$temporary = (New-Item -ItemType Directory -Path (
    Join-Path ([IO.Path]::GetTempPath()) ("ahhe-operations-" + [Guid]::NewGuid())
)).FullName
$listeners = @()
$emulator = $null
Push-Location $root
try {
    for ($index = 0; $index -lt 3; $index++) {
        $listener = [Net.Sockets.TcpListener]::new([Net.IPAddress]::Loopback, 0)
        $listener.Start()
        $listeners += $listener
    }
    $ports = @($listeners | ForEach-Object { $_.LocalEndpoint.Port })
    foreach ($listener in $listeners) { $listener.Stop() }

    $key = [byte[]]::new(64)
    [Security.Cryptography.RandomNumberGenerator]::Fill($key)
    $accountKey = [Convert]::ToBase64String($key)
    $env:AZURITE_ACCOUNTS = "opscontract:$accountKey"
    $env:OPS_TEST_BLOB_ENDPOINT = "http://127.0.0.1:$($ports[0])/opscontract"
    $env:OPS_TEST_QUEUE_ENDPOINT = "http://127.0.0.1:$($ports[1])/opscontract"
    $env:OPS_TEST_TABLE_ENDPOINT = "http://127.0.0.1:$($ports[2])/opscontract"
    $env:OPS_TEST_CONNECTION_STRING = (
        "DefaultEndpointsProtocol=http;AccountName=opscontract;AccountKey=$accountKey;" +
        "BlobEndpoint=$env:OPS_TEST_BLOB_ENDPOINT;QueueEndpoint=$env:OPS_TEST_QUEUE_ENDPOINT;" +
        "TableEndpoint=$env:OPS_TEST_TABLE_ENDPOINT;"
    )
    $arguments = @(
        "`"$azurite`"", '--silent', '--skipApiVersionCheck', '--location', "`"$temporary`"",
        '--blobHost', '127.0.0.1', '--blobPort', $ports[0],
        '--queueHost', '127.0.0.1', '--queuePort', $ports[1],
        '--tableHost', '127.0.0.1', '--tablePort', $ports[2]
    )
    $emulator = Start-Process -FilePath (Get-Command node).Source -ArgumentList $arguments `
        -PassThru -NoNewWindow -RedirectStandardOutput (Join-Path $temporary 'stdout.log') `
        -RedirectStandardError (Join-Path $temporary 'stderr.log')
    $deadline = [DateTime]::UtcNow.AddSeconds(30)
    foreach ($port in $ports) {
        $ready = $false
        while (-not $ready -and [DateTime]::UtcNow -lt $deadline -and -not $emulator.HasExited) {
            $client = [Net.Sockets.TcpClient]::new()
            try {
                $client.Connect('127.0.0.1', $port)
                $ready = $true
            } catch [Net.Sockets.SocketException] {
                Start-Sleep -Milliseconds 100
            } finally {
                $client.Dispose()
            }
        }
        if (-not $ready) { throw "The isolated Azure emulator did not become ready on port $port." }
    }

    & .\gradlew.bat :common:operations:test :backend:web-api:test :backend:background-worker:test `
        :backend:web-api:bootJar :backend:background-worker:bootJar --rerun-tasks
    if ($LASTEXITCODE -ne 0) { throw 'Operational Java tests/builds failed.' }
    $report = Join-Path $root (
        'common\operations\build\test-results\test\TEST-com.ahhe.operations.AzureStorageContractTest.xml'
    )
    [xml] $result = Get-Content -LiteralPath $report -Raw
    if ([int] $result.testsuite.tests -lt 1 -or [int] $result.testsuite.skipped -ne 0) {
        throw 'Azure adapter conformance was skipped; this is not a successful validation.'
    }
    Write-Host 'Operational Java tests/builds and non-skipped Azure adapter contract passed.'
} finally {
    foreach ($listener in $listeners) { $listener.Stop() }
    if ($null -ne $emulator -and -not $emulator.HasExited) {
        Stop-Process -Id $emulator.Id
        $emulator.WaitForExit()
    }
    foreach ($name in $names) {
        [Environment]::SetEnvironmentVariable($name, $saved[$name], 'Process')
    }
    Pop-Location
    Remove-Item -LiteralPath $temporary -Recurse -Force
}
