$ErrorActionPreference = "Stop"

$projectRoot = Split-Path -Parent $MyInvocation.MyCommand.Path
Set-Location $projectRoot

if (-not (Get-Command javac -ErrorAction SilentlyContinue)) {
    Write-Error "javac not found in PATH. Install a JDK and try again."
    exit 1
}

if (-not (Get-Command java -ErrorAction SilentlyContinue)) {
    Write-Error "java not found in PATH. Install Java and try again."
    exit 1
}

$sourceRoot = Join-Path $projectRoot "src/main/java"
$outputDir = Join-Path $projectRoot "target/classes-java8"

$javaFiles = Get-ChildItem -Path $sourceRoot -Recurse -Filter *.java
if (-not $javaFiles) {
    Write-Error "No Java source files found under $sourceRoot."
    exit 1
}

New-Item -ItemType Directory -Force -Path $outputDir | Out-Null

Write-Host "Compiling project..."
$sourcePaths = $javaFiles | ForEach-Object { $_.FullName }
& javac --release 8 -d $outputDir $sourcePaths
if ($LASTEXITCODE -ne 0) {
    Write-Error "Compilation failed."
    exit $LASTEXITCODE
}

Write-Host "Starting Internship System..."
& java -cp $outputDir boundary.InternShipSystem
exit $LASTEXITCODE
