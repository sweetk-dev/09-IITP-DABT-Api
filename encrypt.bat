@echo off
if "%~2"=="" (
  echo Usage: %0 plain-text password
  exit /b 1
)
gradlew encrypt --plain-text "%~1" --password "%~2" -Dfile.encoding=UTF-8 