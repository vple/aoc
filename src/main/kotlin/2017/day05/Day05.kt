package `2017`.day05

import util.loadResource

/**
 * [Advent of Code 2017 Day 5](https://adventofcode.com/2017/day/5)
 */

const val INPUT_FILE = "/2017/day05/input.txt"

fun main(args: Array<String>) {
    val input = loadResource(INPUT_FILE)
    val offsets = input.lines().filterNot { it.isEmpty() }.map { it.toInt() }

    println("Part 1")
    println(jump(offsets) { it+1 })

    println("Part 2")
    println(jump(offsets) { if (it >= 3) { it-1 } else { it+1 } })
}

/**
 * Jumps until the exit is reached, using the given [offsets]. Every time an offset is visited, it is changed according to [modify].
 */
fun jump(offsets: List<Int>, modify: (Int) -> Int): Int {
    val mutableOffsets = offsets.toMutableList()
    var index = 0
    var steps = 0

    while (index >= 0 && index < offsets.size) {
        val offset = mutableOffsets[index]
        mutableOffsets[index] = modify(offset)
        index += offset
        steps++
    }

    return steps
}