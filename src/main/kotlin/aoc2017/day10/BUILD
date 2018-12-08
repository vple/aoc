load("@io_bazel_rules_kotlin//kotlin:kotlin.bzl", "kt_jvm_library", "kt_jvm_binary")

kt_jvm_library(
    name = "KnotHash",
    srcs = glob(["KnotHash.kt"]),
    deps = [
        "//src/main/kotlin/collections",
        "//src/main/kotlin/util",
    ],
)

kt_jvm_binary(
    name = "day10",
    main_class = "aoc2017.day10.Day10Kt",
    srcs = glob(["*.kt"]),
    deps = [
        ":KnotHash",
        "//src/main/kotlin/collections",
        "//src/main/kotlin/util",
    ],
    resources = [
        "//src/main/resources/aoc2017/day10",
    ],
)
