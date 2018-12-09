package aoc2017.day25

import util.loadResourceLines

/**
 * [Advent of Code 2017 Day 25](https://adventofcode.com/2017/day/25)
 */

const val INPUT_FILE = "/aoc2017/day25/input.txt"

fun main(args: Array<String>) {
    val input = loadResourceLines(INPUT_FILE)
    val blueprint = Blueprint.parse(input)

    println("Part 1")
    val turingMachine = blueprint.createTuringMachine()
    turingMachine.step(blueprint.steps)
    println(turingMachine.diagnosticChecksum)

    println("Part 2")
}
