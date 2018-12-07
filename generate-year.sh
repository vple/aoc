#!/bin/bash

YEAR=$1

getKotlinPath() {
  local YEAR=$1
  local DAY=$2
  echo "src/main/kotlin/$YEAR/day$DAY"
}

getResourcePath() {
  local YEAR=$1
  local DAY=$2
  echo "src/main/resources/$YEAR/day$DAY"
}

generateKotlinBuild() {
  local YEAR=$1
  local DAY=$2

  file_text=$(cat << END
load("@io_bazel_rules_kotlin//kotlin:kotlin.bzl", "kt_jvm_binary")

kt_jvm_binary(
    name = "day$DAY",
    main_class = "$YEAR.day$DAY.Day${DAY}Kt",
    srcs = glob(["*.kt"]),
    deps = [
        "//src/main/kotlin/util",
    ],
    resources = [
        "//$(getResourcePath $YEAR $DAY)",
    ],
)
END
)

  path="$(getKotlinPath $YEAR $DAY)/BUILD"
  echo "$file_text" > $path
}

generateKotlinSolution() {
  local YEAR=$1
  local DAY=$2
  local DAY_TRIMMED=$(echo $DAY | sed "s/^0*//")

  file_text=$(cat << END
package \`$YEAR\`.day$DAY

import util.loadResource

/**
 * [Advent of Code $YEAR Day $DAY_TRIMMED](https://adventofcode.com/$YEAR/day/$DAY_TRIMMED)
 */

const val INPUT_FILE = "/day$DAY/input.txt"

fun main(args: Array<String>) {
    val input = loadResource(INPUT_FILE)

    println("Part 1")

    println("Part 2")
}
END
)

  path="$(getKotlinPath $YEAR $DAY)/Day$DAY.kt"
  echo "$file_text" > $path
}

generateResourceBuild() {
  local YEAR=$1
  local DAY=$2

  file_text=$(cat << END
filegroup(
    name = "day$DAY",
    srcs = glob(["*.txt"]),
    visibility = [
        "//$(getKotlinPath $YEAR $DAY):__subpackages__",
    ],
)
END
)

  path="$(getResourcePath $YEAR $DAY)/BUILD"
  echo "$file_text" > $path
}

for DAY in $(seq -f "%02g" 1 25)
do
  mkdir -p "$(getKotlinPath $YEAR $DAY)"
  mkdir -p "$(getResourcePath $YEAR $DAY)"
  generateKotlinBuild $YEAR $DAY
  generateKotlinSolution $YEAR $DAY
  generateResourceBuild $YEAR $DAY
done