package aoc2017.day15

import util.loadResourceLines

/**
 * [Advent of Code 2017 Day 15](https://adventofcode.com/2017/day/15)
 */

const val INPUT_FILE = "/aoc2017/day15/input.txt"
val LAST_16_MASK: Long = ((1..16).fold(1) { acc, _ -> acc * 2 } - 1).toLong()

fun main(args: Array<String>) {
    val input = loadResourceLines(INPUT_FILE)
    val regex = Regex("(\\d+)")
    val (aStart, bStart) = input.map { regex.find(it)!!.groupValues }.map { it[1].toLong() }
    val aFactor = 16807L
    val bFactor = 48271L
    val mod = 2147483647L

    println("Part 1")
    val generatorA1 = Generator(aStart, aFactor, mod)
    val generatorB1 = Generator(bStart, bFactor, mod)
    println(countMatches(generatorA1, generatorB1, 40000000, ::last16Match))

    println("Part 2")
    val generatorA2 = Generator(aStart, aFactor, mod, 4)
    val generatorB2 = Generator(bStart, bFactor, mod, 8)
    println(countMatches(generatorA2, generatorB2, 5000000, ::last16Match))
}

fun countMatches(generatorA: Generator, generatorB: Generator, iterations: Int, match: (Long, Long) -> Boolean): Int {
    var total = 0
    val iteratorA = generatorA.asSequence().iterator()
    val iteratorB = generatorB.asSequence().iterator()
    repeat(iterations) {
        if (match(iteratorA.next(), iteratorB.next())) {
            total++
        }
    }
    return total
}

fun last16Match(a: Long, b: Long): Boolean {
    return (a and LAST_16_MASK) == (b and LAST_16_MASK)
}
