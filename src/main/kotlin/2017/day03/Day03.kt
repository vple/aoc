package `2017`.day03

import grid.Coordinate
import kotlin.math.abs

/**
 * [Advent of Code 2017 Day 3](https://adventofcode.com/2017/day/3)
 */

fun main(args: Array<String>) {
    val square = 325489

    println("Part 1")
    println(computeSteps(square))

    println("Part 2")
    println(stressTest(square))
}

/**
 * Computes a grid containing a targetValue at least as large as the given [targetValue].
 *
 * The targetValue of each square is determined by its neighbors, using the [computeValue] function.
 */
fun computeGrid(
    targetValue: Int,
    computeValue: (neighbors: Map<Coordinate, Int>) -> Int
): MutableMap<Coordinate, Int> {
    var xDirection = 1
    var yDirection = 1
    var numSteps = 1

    var coordinate = Coordinate(0, 0)
    var value = 1
    val grid = mutableMapOf(Pair(coordinate, value))
    val neighborsOf = { c: Coordinate -> grid.filterKeys { c.neighbors.contains(it) }}

    while (value < targetValue) {
        repeat(numSteps) {
            coordinate = coordinate.plusX(xDirection)
            value = computeValue(neighborsOf(coordinate))
            grid[coordinate] = value
        }
        repeat(numSteps) {
            coordinate = coordinate.plusY(yDirection)
            value = computeValue(neighborsOf(coordinate))
            grid[coordinate] = value
        }

        println("$coordinate, $value")

        xDirection *= -1
        yDirection *= -1
        numSteps++
    }

    return grid
}

/**
 * Computes the number of steps from the given [square] to square 1, using Manhattan Distance.
 */
fun computeSteps(square: Int): Int {
    val grid = computeGrid(square) { it.values.max()!! + 1 }
    val coordinate = grid.filterValues { it == square }.keys.first()
    return abs(coordinate.x) + abs(coordinate.y)
}

/**
 * Stress tests grid creation, returning the first value that is greated than [targetValue].
 */
fun stressTest(targetValue: Int): Int {
    val grid = computeGrid(targetValue + 1) { it.values.sum() }
    return grid.values.filter { it > targetValue }.min()!!
}