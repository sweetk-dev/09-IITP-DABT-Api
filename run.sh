#!/bin/bash

# JAR 파일 경로
JAR_FILE="build/libs/iitp-api.jar"

# JAR 파일이 존재하는지 확인
if [ ! -f "$JAR_FILE" ]; then
    echo "Error: $JAR_FILE not found"
    echo "Please build the project first using './gradlew build'"
    exit 1
fi

# 로컬 환경인 경우 프로필 지정
if [ -z "$SPRING_PROFILES_ACTIVE" ]; then
    echo "Running in local profile..."
    java -jar "$JAR_FILE" --spring.profiles.active=local
else
    echo "Running in $SPRING_PROFILES_ACTIVE profile..."
    java -jar "$JAR_FILE"
fi 