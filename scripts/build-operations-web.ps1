$ErrorActionPreference = 'Stop'
$root = Split-Path -Parent $PSScriptRoot
$previous = $env:VITE_OPERATIONS_ONLY
Push-Location (Join-Path $root 'clients\web')
try {
    $env:VITE_OPERATIONS_ONLY = 'true'
    npm run build
    if ($LASTEXITCODE -ne 0) {
        throw 'The operational web client build failed.'
    }
} finally {
    $env:VITE_OPERATIONS_ONLY = $previous
    Pop-Location
}
