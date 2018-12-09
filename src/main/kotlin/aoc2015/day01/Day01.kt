package aoc2015.day01

import util.loadResource

/**
 * [Advent of Code 2015 Day 1](https://adventofcode.com/2015/day/1)
 */

const val INPUT_FILE = "/aoc2015/day01/input.txt"

fun main(args: Array<String>) {
    val input = loadResource(INPUT_FILE).trim()

    println("Part 1")
    val floor = input.count { it == '(' } - input.count { it == ')'}
    println(floor)

    println("Part 2")
    println(enterBasement(input))
}

fun enterBasement(parens: String): Int {
    var floor = 0
    parens.forEachIndexed { index, c ->
        floor+= if (c == '(') 1 else -1
        if (floor < 0) {
            return index+1
        }
    }
    throw IllegalArgumentException()
}