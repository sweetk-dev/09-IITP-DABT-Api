#!/bin/bash
if [ $# -lt 2 ]; then
  echo "Usage: $0 <plain-text> <password>"
  exit 1
fi
./gradlew encrypt --plain-text "$1" --password "$2" -Dfile.encoding=UTF-8 