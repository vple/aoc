package aoc2017.day13

import util.loadResourceLines

/**
 * [Advent of Code 2017 Day 13](https://adventofcode.com/2017/day/13)
 */

const val INPUT_FILE = "/aoc2017/day13/input.txt"

fun main(args: Array<String>) {
    val input = loadResourceLines(INPUT_FILE)
    val firewall = Firewall.parse(input)

    println("Part 1")
    println(firewall.tripSeverity(0))

    println("Part 2")
    println(findDelayTime(firewall))
}

fun findDelayTime(firewall: Firewall): Int {
    var delay = 0
    while (!firewall.isSafeTrip(delay)) {
        delay++
    }
    return delay
}