package aoc2017.day09

import util.loadResource

/**
 * [Advent of Code 2017 Day 9](https://adventofcode.com/2017/day/9)
 */

const val INPUT_FILE = "/aoc2017/day09/input.txt"

fun main(args: Array<String>) {
    val stream = loadResource(INPUT_FILE).trim()

    val result = processStream(stream)
    println("Part 1")
    println(result.first)

    println("Part 2")
    println(result.second)
}

fun processStream(stream: String): Pair<Int, Int> {
    var depth = 0
    var score = 0
    var garbageCount = 0
    var inGarbage = false
    var ignoreNext = false
    for (char in stream) {
        if (ignoreNext) {
            ignoreNext = false
            continue
        }
        if (char == '!') {
            ignoreNext = true
            continue
        }
        if (inGarbage && char != '>') {
            garbageCount++
            continue
        }
        when (char) {
            '<' -> inGarbage = true
            '>' -> inGarbage = false
            '{' -> {
                depth++
            }
            '}' -> {
                score += depth
                depth--
            }
            else -> {}
        }
    }
    return Pair(score, garbageCount)
}