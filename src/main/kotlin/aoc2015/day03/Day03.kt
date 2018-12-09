package aoc2015.day03

import grid.Coordinate
import grid.Direction
import util.loadResource

/**
 * [Advent of Code 2015 Day 3](https://adventofcode.com/2015/day/3)
 */

const val INPUT_FILE = "/aoc2015/day03/input.txt"

fun main(args: Array<String>) {
    val input = loadResource(INPUT_FILE).trim().toList()
    val directions = input.map {
        when (it) {
            '^' -> Direction.NORTH
            '>' -> Direction.EAST
            'v' -> Direction.SOUTH
            '<' -> Direction.WEST
            else -> throw IllegalStateException()
        }
    }

    println("Part 1")
    val deliveries = deliver(directions)
    println(deliveries.size)

    println("Part 2")
    val allDeliveries = mutableMapOf<Coordinate, Int>()
    allDeliveries.putAll(deliver(directions.filterIndexed { index, direction -> index % 2 == 0 }))
    val roboDeliveries= deliver(directions.filterIndexed { index, direction -> index % 2 == 1 })
    roboDeliveries.forEach { allDeliveries.merge(it.key, it.value) { x, y -> x + y } }
    println(allDeliveries.size)
}

fun deliver(directions: List<Direction>): Map<Coordinate, Int> {
    val deliveries = mutableMapOf<Coordinate, Int>()
    var current = Coordinate(0, 0)
    deliveries[current] = deliveries.getOrDefault(current, 0) + 1

    directions.forEach {
        current += it.coordinate
        deliveries[current] = deliveries.getOrDefault(current, 0) + 1
    }

    return deliveries
}
