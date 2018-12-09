package aoc2017.day22

import util.loadResourceLines

/**
 * [Advent of Code 2017 Day 22](https://adventofcode.com/2017/day/22)
 */

const val INPUT_FILE = "/aoc2017/day22/input.txt"

fun main(args: Array<String>) {
    val input = loadResourceLines(INPUT_FILE)

    println("Part 1")
    val cluster1 = Cluster.parse(input)
    cluster1.burst(10000)
    println(cluster1.burstInfections)

    println("Part 2")
    val cluster2 = Cluster.parse(input)
    cluster2.burst(10000000, true)
    println(cluster2.burstInfections)
}
