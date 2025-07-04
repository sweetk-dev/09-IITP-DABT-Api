@echo off
if "%~2"=="" (
  echo Usage: %0 plain-text password
  exit /b 1
)
gradlew encrypt -Pplain-text=%1 -Ppassword=%2 -Dfile.encoding=UTF-8