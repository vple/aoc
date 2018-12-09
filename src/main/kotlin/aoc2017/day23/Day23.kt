package aoc2017.day23

import util.loadResourceLines

/**
 * [Advent of Code 2017 Day 23](https://adventofcode.com/2017/day/23)
 */

const val INPUT_FILE = "/aoc2017/day23/input.txt"

fun main(args: Array<String>) {
    val input = loadResourceLines(INPUT_FILE)
    val instructions = input.map { Instruction.parse(it) }

    println("Part 1")
    val program1 = Program(0, instructions)
    program1.evaluate { false }
    println(program1.instructionCount)

    println("Part 2")
    var nonPrimes = 0
    for (i in 108400..125400 step 17) {
        if (!isPrime(i)) {
            nonPrimes++
        }
    }
    println(nonPrimes)
}

fun isPrime(n: Int): Boolean {
    require(n > 0)
    if (n == 2 || n == 3) {
        return true
    }
    for (factor in 2..n/2) {
        if (n % factor == 0) {
            return false
        }
    }
    return true
}