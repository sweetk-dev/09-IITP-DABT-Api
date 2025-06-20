#!/bin/bash

export SPRING_PROFILES_ACTIVE=dev

JAR_FILE="iitp-api.jar"
LOG_FILE="./logs/iitp-api-console.log"

# SPRING_PROFILES_ACTIVE 환경변수 체크
if [ -z "$SPRING_PROFILES_ACTIVE" ]; then
    echo "Error: SPRING_PROFILES_ACTIVE 환경변수가 설정되어 있지 않습니다."
    echo "스크립트 내에서 SPRING_PROFILES_ACTIVE 설정을 하세요.
    exit 1
fi

# 기존 프로세스 종료
PID=$(pgrep -f "$JAR_FILE")
if [ -n "$PID" ]; then
    echo "기존 $JAR_FILE 프로세스 종료(PID: $PID)..."
    kill $PID
    sleep 5
fi

# JAR 파일 존재 확인
if [ ! -f "$JAR_FILE" ]; then
    echo "Error: $JAR_FILE not found"
    exit 1
fi

# 백그라운드 실행
echo "$JAR_FILE 실행 (profile: $SPRING_PROFILES_ACTIVE)..."
nohup java -jar "$JAR_FILE" > "$LOG_FILE" 2>&1 &

echo "서버가 백그라운드에서 실행되었습니다. (로그: $LOG_FILE)" 