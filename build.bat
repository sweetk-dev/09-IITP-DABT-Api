@echo off
REM 사용법: build.bat [local|dev|stage|prod]

set ENV=%1
if "%ENV%"=="" set ENV=local

if /I "%ENV%"=="local" (
    set TASK=buildLocal
) else if /I "%ENV%"=="dev" (
    set TASK=buildDev
) else if /I "%ENV%"=="stage" (
    set TASK=buildStage
) else if /I "%ENV%"=="prod" (
    set TASK=buildProd
) else (
    echo [ERROR] Please enter a valid environment: local, dev, stage, prod
    exit /b 1
)

REM Clean previous build outputs
call gradlew -Dfile.encoding=UTF-8 clean

REM Generate and copy OpenAPI docs
call gradlew -Dfile.encoding=UTF-8 copyDocsToResources

REM Build for the selected environment
call gradlew -Dfile.encoding=UTF-8 %TASK%

echo [DONE] %ENV% JAR and OpenAPI document are ready. (default: local) 