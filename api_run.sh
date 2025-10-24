#!/bin/bash

export SPRING_PROFILES_ACTIVE=dev
export JASYPT_ENCRYPTOR_PASSWORD=iitp

JAR_FILE="iitp-api.jar"
LOG_FILE="./logs/iitp-api.log"

# SPRING_PROFILES_ACTIVE 환경변수 체크
if [ -z "$SPRING_PROFILES_ACTIVE" ]; then
    echo "Error: SPRING_PROFILES_ACTIVE 환경변수가 설정되어 있지 않습니다."
    echo "스크립트 내에서 SPRING_PROFILES_ACTIVE 설정을 하세요."
    exit 1
fi

# JAR 파일 존재 확인
if [ ! -f "$JAR_FILE" ]; then
    echo "Error: $JAR_FILE not found"
    exit 1
fi


# 기존 프로세스 종료
PID=$(pgrep -f "$JAR_FILE")
if [ -n "$PID" ]; then
    echo "기존 $JAR_FILE 프로세스 종료(PID: $PID)..."
    kill -9 $PID
    sleep 10
fi


# 실행 (systemd에서는 foreground, 수동 실행에서는 background)
if [ -n "$INVOKED_BY_SYSTEMD" ]; then
    # systemd에서 호출된 경우: foreground 실행
    echo "$JAR_FILE 실행 (profile: $SPRING_PROFILES_ACTIVE)..."
    exec java -jar "$JAR_FILE"
else
    # 수동 실행인 경우: background 실행
    echo "$JAR_FILE 실행 (profile: $SPRING_PROFILES_ACTIVE)..."
    nohup java -jar "$JAR_FILE" 2>&1 &
    echo "서버가 백그라운드에서 실행되었습니다. (로그: $LOG_FILE)"
fi 