@echo off

set JAR_FILE=build\libs\iitp-api.jar

REM JAR 파일이 존재하는지 확인
if not exist "%JAR_FILE%" (
    echo Error: %JAR_FILE% not found
    echo Please build the project first using 'gradlew.bat build'
    exit /b 1
)

REM 로컬 환경인 경우 프로필 지정
if "%SPRING_PROFILES_ACTIVE%"=="" (
    echo Running in local profile...
    java -jar "%JAR_FILE%" --spring.profiles.active=local
) else (
    echo Running in %SPRING_PROFILES_ACTIVE% profile...
    java -jar "%JAR_FILE%"
) 