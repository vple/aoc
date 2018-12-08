package aoc2017.day08

import util.loadResource
import util.loadResourceLines
import kotlin.math.max

/**
 * [Advent of Code 2017 Day 8](https://adventofcode.com/2017/day/8)
 */

const val INPUT_FILE = "/aoc2017/day08/input.txt"

fun main(args: Array<String>) {
    val input = loadResourceLines(INPUT_FILE)
    val instructions = input.map { Instruction.parse(it) }
    val results = evaluateInstructions(instructions)

    println("Part 1")
    println(results.first.values.max())

    println("Part 2")
    println(results.second)
}

/**
 * Evaluates the given [instructions].
 */
fun evaluateInstructions(instructions: List<Instruction>): Pair<Map<String, Int>, Int> {
    return instructions.fold(Pair(mapOf(), 0)) { (registers, maxMemory), instruction ->
        val nextRegisters = instruction.apply(registers)
        Pair(instruction.apply(registers), max(maxMemory, nextRegisters.values.max()!!))
    }
}