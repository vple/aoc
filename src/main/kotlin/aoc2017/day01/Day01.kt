package aoc2017.day01

import util.loadResource

/**
 * [Advent of Code 2017 Day 1](https://adventofcode.com/2017/day/1)
 */

const val INPUT_FILE = "/aoc2017/day01/input.txt"

fun main(args: Array<String>) {
    val input = loadResource(INPUT_FILE)
    val digits = input.lines().first().map { it.toString().toInt() }

    println("Part 1")
    println(solveNextCaptcha(digits))

    println("Part 2")
    println(solveHalfwayAroundCaptcha(digits))
}

/**
 * Returns the sum of all [digits] who have the same value as their corresponding comparison digit, as determined by [findComparisonDigit].
 */
fun solveCaptcha(digits: List<Int>, findComparisonDigit: (index: Int) -> Int): Int {
    val size = digits.size
    return digits.foldIndexed(0) { index, acc, i ->
        val comparisonDigit = findComparisonDigit(index)
        if (i == comparisonDigit) {
            acc + i
        } else {
            acc
        }
    }
}

/**
 * Solves a captcha by comparing each digit in [digits] to its next digit.
 */
fun solveNextCaptcha(digits: List<Int>) =
    solveCaptcha(digits) { index -> digits[(index + 1) % digits.size] }

/**
 * Solves a captcha by comparing each digit in [digits] to the digit halfway around the list.
 */
fun solveHalfwayAroundCaptcha(digits: List<Int>) =
    solveCaptcha(digits) { index -> digits[(index + digits.size / 2) % digits.size] }