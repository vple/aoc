package aoc2016.day25

import util.loadResource

/**
 * [Advent of Code 2016 Day 25](https://adventofcode.com/2016/day/25)
 */

const val INPUT_FILE = "/aoc2016/day25/input.txt"

fun main(args: Array<String>) {
    val input = loadResource(INPUT_FILE)

    println("Part 1")
    val start = search(643*4)
    println(start)
    signal(start)

    println("Part 2")
}

fun search(offset: Int): Int {
    // 0b10
    var current = 2
    while (current <= offset) {
        // Append 0b10
        current *= 4
        current += 2
    }
    return current - offset
}

fun signal(start: Int) {
    var a = start
//    while (true) {
        a = start + (643*4)
        while (a != 0) {
            a = a / 2
            if (a % 2 == 0) {
                print(0)
            } else {
                print(1)
            }
        }
//    }
    println()
}