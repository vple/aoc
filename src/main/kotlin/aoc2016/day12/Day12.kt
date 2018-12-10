package aoc2016.day12

import util.loadResource

/**
 * [Advent of Code 2016 Day 12](https://adventofcode.com/2016/day/12)
 */

const val INPUT_FILE = "/aoc2016/day12/input.txt"

fun main(args: Array<String>) {
    val input = loadResource(INPUT_FILE)

    println("Part 1")
    println(part1())

    println("Part 2")
    println(part2())
}

fun part1(): Int {
    var a = 1
    var b = 1
    var d = 26
    var c: Int
    while (d != 0) {
        c = a
        while (b != 0) {
            a++
            b--
        }
        b = c
        d--
    }
    c = 19
    while (c != 0) {
        d = 14
        while (d != 0) {
            a++
            d--
        }
        c--
    }
    return a
}

fun part2(): Int {
    var c = 1
    var a = 1
    var b = 1
    var d = 26
    c = 7
    while (c != 0) {
        d++
        c--
    }
    while (d != 0) {
        c = a
        while (b != 0) {
            a++
            b--
        }
        b = c
        d--
    }
    c = 19
    while (c != 0) {
        d = 14
        while (d != 0) {
            a++
            d--
        }
        c--
    }
    return a
}