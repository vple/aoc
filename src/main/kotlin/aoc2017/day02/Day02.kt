package aoc2017.day02

import util.loadResource

/**
 * [Advent of Code 2017 Day 2](https://adventofcode.com/2017/day/2)
 */

const val INPUT_FILE = "/aoc2017/day02/input.txt"

fun main(args: Array<String>) {
    val input = loadResource(INPUT_FILE)
    val rows = input.lines().filterNot { it.isEmpty() }
    val spreadsheet = rows.map { it.split("\t").map { it.toInt() } }

    println("Part 1")
    println(computeChecksum(spreadsheet, ::maxDifference))

    println("Part 2")
    println(computeChecksum(spreadsheet, ::findExactQuotient))
}

/**
 * Computes the checksum for the given [spreadsheet].
 */
fun computeChecksum(spreadsheet: List<List<Int>>, computeRowChecksum: (List<Int>) -> Int): Int {
    return spreadsheet.map { computeRowChecksum(it) }.sum()
}

/**
 * Finds the maximum difference between two numbers in the given [row].
 */
fun maxDifference(row: List<Int>): Int {
    val max = checkNotNull(row.max())
    val min = checkNotNull(row.min())
    return max - min
}

/**
 * Finds the only two numbers in [row] where one evenly divides the other and returns their quotient.
 */
fun findExactQuotient(row: List<Int>): Int {
    for (i in 0 until row.size) {
        for (j in i+1 until row.size) {
            val x = row[i]
            val y = row[j]
            if (x % y == 0) {
                return x / y
            } else if (y % x == 0) {
                return y / x
            }
        }
    }
    throw RuntimeException()
}