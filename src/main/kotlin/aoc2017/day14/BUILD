load("@io_bazel_rules_kotlin//kotlin:kotlin.bzl", "kt_jvm_binary")

kt_jvm_binary(
    name = "day14",
    main_class = "aoc2017.day14.Day14Kt",
    srcs = glob(["*.kt"]),
    deps = [
        "//src/main/kotlin/aoc2017/day10:KnotHash",
        "//src/main/kotlin/grid",
        "//src/main/kotlin/util",
    ],
    resources = [
        "//src/main/resources/aoc2017/day14",
    ],
)
