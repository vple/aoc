package `2017`.day06

import util.loadResource

/**
 * [Advent of Code 2017 Day 6](https://adventofcode.com/2017/day/6)
 */

const val INPUT_FILE = "/2017/day06/input.txt"

fun main(args: Array<String>) {
    val input = loadResource(INPUT_FILE)
    val memoryBank = input.lines().first().split("\t").map { it.toInt() }

    val result = countUniqueRedistributionCycles(memoryBank)
    println("Part 1")
    println(result.first)

    println("Part 2")
    println(result.second)
}

/**
 * Redistributes a memory bank.
 */
fun List<Int>.redistribute(): List<Int> {
    val blocks = max()!!
    val index = indexOf(blocks)

    val result = toMutableList()
    result[index] = 0

    (1..blocks).forEach { result[(index + it) % size]++ }
    return result.toList()
}

/**
 * Counts the number of redistribution cycles that can be completed before encountering a duplicate configuration, returning the number of cycles and the period.
 */
fun countUniqueRedistributionCycles(initialMemoryBank: List<Int>): Pair<Int, Int> {
    val seen = mutableListOf<List<Int>>()
    var memoryBank = initialMemoryBank
    var cycles = 0

    while (!seen.contains(memoryBank)) {
        seen.add(memoryBank)
        memoryBank = memoryBank.redistribute()
        cycles++
    }

    return Pair(cycles, seen.size - seen.indexOf(memoryBank))
}