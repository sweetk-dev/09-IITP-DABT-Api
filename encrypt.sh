#!/bin/bash
if [ $# -lt 2 ]; then
  echo "Usage: $0 <plain-text> <password>"
  exit 1
fi
./gradlew encrypt -Pplain-text=$1 -Ppassword=$2 -Dfile.encoding=UTF-8