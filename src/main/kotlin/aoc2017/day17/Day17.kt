package aoc2017.day17

/**
 * [Advent of Code 2017 Day 17](https://adventofcode.com/2017/day/17)
 */

const val INPUT_FILE = "/aoc2017/day17/input.txt"

fun main(args: Array<String>) {
    val step = 355

    println("Part 1")
    println(findValueAfter(2017, 2017, step))

    println("Part 2")
    println(findValueAfter0(50000000, step))
}

fun spinlock(n: Int, step: Int): List<Int> {
    val buffer = mutableListOf(0)
    var position = 0
    (1..n).forEach {
        position = (position + step) % it
        buffer.add(position++, it)
    }
    return buffer.toList()
}

fun findValueAfter(target: Int, n: Int, step: Int): Int {
    var buffer = spinlock(n, step)
    val index = buffer.indexOf(target)
    return buffer[(index + 1) % buffer.size]
}

fun findValueAfter0(n: Int, step: Int): Int {
    var position = 0
    var last = -1
    (1..n).forEach {
        position = (position + step) % it + 1
        if (position == 1) {
            last = it
        }
    }
    return last
}