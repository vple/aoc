#!/bin/bash

# Expects your AOC session cookie stored in an environment variable named AOC_COOKIE

YEAR=$1
CURRENT_YEAR=$(TZ=America/New_York date +"%Y")
if ((YEAR > CURRENT_YEAR)); then
  echo "Can't get inputs for $YEAR in $CURRENT_YEAR!"
  exit 1
elif ((YEAR < CURRENT_YEAR)); then
  MAX_DAY=25
else
  MAX_DAY=$(TZ=America/New_York date +"%d" | sed "s/^0*//")
fi

INPUT_URL="https://adventofcode.com/2018/day/7/input"

getResourcePath() {
  local YEAR=$1
  local DAY=$(printf "%02g" $2)
  echo "src/main/resources/$YEAR/day$DAY"
}

for DAY in $(seq 1 $MAX_DAY)
do
  TARGET=$(getResourcePath $YEAR $DAY)/input.txt
  URL="https://adventofcode.com/$YEAR/day/$DAY/input"
  curl --cookie "session=$AOC_COOKIE" $URL > $TARGET
done