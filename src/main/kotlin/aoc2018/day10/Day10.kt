package aoc2018.day10

import grid.Coordinate
import util.loadResourceLines
import kotlin.math.abs

/**
 * [Advent of Code 2018 Day 10](https://adventofcode.com/2018/day/10)
 */

const val INPUT_FILE = "/aoc2018/day10/input.txt"

fun main(args: Array<String>) {
    val input = loadResourceLines(INPUT_FILE)
    val points = input.map { Point.parse(it) }

    val message = fastForward(points)

    println("Part 1")
    printPoints(message.value)

    println("Part 2")
    println(message.key)
}

fun fastForward(initial: List<Point>): Map.Entry<Int, List<Point>> {
    // Points will be overhead when the message forms, so find some bounds for that.
    val timesUntilAtOrigin =
        initial
            .map { listOf(it.position.x / it.velocity.x, it.position.y / it.velocity.y) }
            .flatten()
            .map { abs(it) }
    val minTime = timesUntilAtOrigin.min()!!
    val maxTime = timesUntilAtOrigin.max()!!

    var currentPoints = initial
    repeat(minTime-1) { currentPoints = next(currentPoints) }

    val messages = mutableMapOf<Int, List<Point>>()
    for (i in minTime..maxTime) {
        currentPoints = next(currentPoints)
        messages[i] = currentPoints
    }

    return messages.minBy { messageArea(it.value) }!!
}

fun next(points: List<Point>) = points.map { it.next() }

fun messageArea(points: List<Point>): Int {
    val (width, height) = bounds(points)
    return width * height
}

fun bounds(points: List<Point>): Pair<Int, Int> {
    val xValues = points.map { it.position.x }
    val yValues = points.map { it.position.y }
    val width = xValues.max()!! - xValues.min()!! + 1
    val height = yValues.max()!! - yValues.min()!! + 1
    return Pair(width, height)
}

fun printPoints(points: List<Point>) {
    val minX = points.map { it.position.x }.min()!!
    val minY = points.map { it.position.y }.min()!!
    val offset = Coordinate(minX, minY)
    val shifted = points.map { it.position - offset }

    val (width, height) = bounds(points)
    val pointsByY = shifted.groupBy { it.y }

    for (i in 0 until height) {
        if (pointsByY.contains(i)) {
            printLine(pointsByY[i]!!, width)
        } else {
            println()
        }
    }
}

fun printLine(points: List<Coordinate>, width: Int) {
    val xValues = points.map { it.x }.toSet()
    val line = List(width) {
        if (xValues.contains(it)) {
            '#'
        } else {
            '.'
        }
    }.joinToString("")
    println(line)
}

data class Point(val position: Coordinate, val velocity: Coordinate) {
    fun next(): Point {
        return Point(position + velocity, velocity)
    }

    companion object {
        val REGEX = Regex("(-?\\d+)")
        fun parse(text: String): Point {
            val (px, py, vx, vy) = REGEX.findAll(text).map { it.groupValues[1] }.toList()
            return Point(Coordinate(px.toInt(), py.toInt()), Coordinate(vx.toInt(), vy.toInt()))
        }
    }
}