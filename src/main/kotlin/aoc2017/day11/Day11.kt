package aoc2017.day11

import grid.Coordinate
import util.loadResource
import kotlin.math.abs
import kotlin.math.max

/**
 * [Advent of Code 2017 Day 11](https://adventofcode.com/2017/day/11)
 */

const val INPUT_FILE = "/aoc2017/day11/input.txt"

fun main(args: Array<String>) {
    val input = loadResource(INPUT_FILE).trim().split(",")
    val directions = input.map { Direction.parse(it) }
    val path = generatePath(directions)

    println("Part 1")
    println(path.last().distance())

    println("Part 2")
    println(path.map { it.distance() }.max())
}

fun generatePath(directions: List<Direction>): List<Coordinate> {
    var current = Coordinate(0, 0)
    return directions.map { current += it.unitCoordinate; current }
}

fun followPath(path: List<Direction>): Coordinate {
    return path.fold(Coordinate(0, 0)) { coordinate , direction -> coordinate + direction.unitCoordinate }
}

fun Coordinate.distance(): Int {
    if (x * y <= 0) {
        return max(abs(x), abs(y))
    } else {
        return abs(x + y)
    }
}