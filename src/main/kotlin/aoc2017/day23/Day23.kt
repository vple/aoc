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
    val program = Program(0, instructions)
    program.evaluate { false }
    println(program.instructionCount)

    println("Part 2")
}
