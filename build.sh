#!/bin/bash

# Usage: ./build.sh [local|dev|stage|prod]
# If no option is given, 'local' is used by default
ENV=${1:-local}

case $ENV in
  local|dev|stage|prod)
    ;;
  *)
    echo "[ERROR] Please enter a valid environment: local, dev, stage, prod"
    exit 1
    ;;
esac

set -e

echo "[STEP 0] Clean previous build outputs..."
./gradlew -Dfile.encoding=UTF-8 clean

echo "[STEP 1] Generate and copy OpenAPI docs..."
./gradlew -Dfile.encoding=UTF-8 copyDocsToResources

echo "[STEP 2] Build JAR for $ENV environment..."
./gradlew -Dfile.encoding=UTF-8 build${ENV^}

echo "[DONE] $ENV JAR and OpenAPI document are ready. (default: local)" 