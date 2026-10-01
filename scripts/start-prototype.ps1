$ErrorActionPreference = "Stop"

$repositoryRoot = Split-Path -Parent $PSScriptRoot
$gradleCache = Join-Path $HOME ".gradle"

if (-not (Test-Path $gradleCache)) {
    throw "The local Gradle cache was not found at $gradleCache."
}

Push-Location $repositoryRoot
try {
    docker info | Out-Null

    docker run --rm `
        --volume "${repositoryRoot}:/workspace" `
        --volume "${gradleCache}:/home/gradle/.gradle" `
        --workdir /workspace `
        gradle:8.8-jdk21-alpine `
        gradle :services:web-api:bootJar --offline --no-daemon

    if ($LASTEXITCODE -ne 0) {
        throw "The offline backend build failed."
    }

    docker compose up --build
} finally {
    Pop-Location
}

