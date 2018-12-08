package aoc2017.day14

import util.breadthFirstSearch
import util.loadResource

/**
 * [Advent of Code 2017 Day 14](https://adventofcode.com/2017/day/14)
 */

const val INPUT_FILE = "/aoc2017/day14/input.txt"

fun main(args: Array<String>) {
    val input = loadResource(INPUT_FILE).trim()
    val disc = Disc.create(input)

    println("Part 1")
    println(disc.usedBits)

    println("Part 2")
    println(countRegions(disc))
}

fun countRegions(disc: Disc): Int {
    var usedCoordinates = disc.usedCoordinates
    var regions = 0
    var totalSeen = 0
    while (usedCoordinates.isNotEmpty()) {
        val results =
            breadthFirstSearch(
                start = setOf(usedCoordinates.first()),
                adjacent = { it.orthogonalNeighbors.filter { disc.usedCoordinates.contains(it) } })
        usedCoordinates -= results.seen
        totalSeen += results.seen.size
        regions++
    }
    return regions
}