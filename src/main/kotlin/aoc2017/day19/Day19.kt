package aoc2017.day19

import util.loadResourceLines

/**
 * [Advent of Code 2017 Day 19](https://adventofcode.com/2017/day/19)
 */

const val INPUT_FILE = "/aoc2017/day19/input.txt"

fun main(args: Array<String>) {
    val input = loadResourceLines(INPUT_FILE)
    val diagram = Diagram(input)
    diagram.traverse()

    println("Part 1")
    println(diagram.letters.joinToString(""))

    println("Part 2")
    println(diagram.steps)
}
