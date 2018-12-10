package aoc2015.day06

import grid.Coordinate
import util.loadResourceLines
import kotlin.math.max
import kotlin.math.min

/**
 * [Advent of Code 2015 Day 6](https://adventofcode.com/2015/day/6)
 */

const val INPUT_FILE = "/aoc2015/day06/input.txt"

fun main(args: Array<String>) {
    val input = loadResourceLines(INPUT_FILE)
    val instructions = input.map { LightInstruction.parse(it) }


    println("Part 1")
    val grid1 = Array(1000) { IntArray(1000) }
    instructions.forEach { it.apply(grid1, onOffActions) }
    println(grid1.map { it.sum() }.sum())

    println("Part 2")
    val grid2 = Array(1000) { IntArray(1000) }
    instructions.forEach { it.apply(grid2, incrementActions) }
    println(grid2.map { it.sum() }.sum())
}

val onOffActions: Map<LightInstruction.Action, (Int) -> Int> = mapOf(
    Pair(LightInstruction.Action.TURN_ON, { it -> 1 }),
    Pair(LightInstruction.Action.TURN_OFF, { it -> 0 }),
    Pair(LightInstruction.Action.TOGGLE, { it -> 1 - it }))

val incrementActions: Map<LightInstruction.Action, (Int) -> Int> = mapOf(
    Pair(LightInstruction.Action.TURN_ON, { it -> it+1 }),
    Pair(LightInstruction.Action.TURN_OFF, { it -> max(it-1, 0) }),
    Pair(LightInstruction.Action.TOGGLE, { it -> it+2 }))

data class LightInstruction(val action: Action, val corner1: Coordinate, val corner2: Coordinate) {
    enum class Action(val operation: (Int) -> Int) {
        TURN_ON({ 1 }),
        TURN_OFF({ 0 }),
        TOGGLE({ 1 - it })
    }

    val minX = min(corner1.x, corner2.x)
    val maxX = max(corner1.x, corner2.x)
    val minY = min(corner1.y, corner2.y)
    val maxY = max(corner1.y, corner2.y)
    val rectangle: List<Coordinate> by lazy {
        val coordinates = mutableListOf<Coordinate>()
        for (x in minX..maxX) {
            for (y in minY..maxY) {
                coordinates.add(Coordinate(x, y))
            }
        }
        coordinates
    }

    fun apply(grid: Array<IntArray>, actions: Map<Action, (Int) -> Int>) {
        rectangle.forEach { (x, y) ->
            grid[x][y] = actions[action]!!(grid[x][y])
        }
    }

    companion object {
        val PATTERN = "(.+) (\\d+),(\\d+) through (\\d+),(\\d+)"
        val REGEX = Regex(PATTERN)

        fun parse(text: String): LightInstruction {
            val ( actionString, x1, y1, x2, y2) = REGEX.matchEntire(text)!!.groupValues.subList(1, 6)
            val action =
                when (actionString) {
                    "turn on" -> Action.TURN_ON
                    "turn off" -> Action.TURN_OFF
                    "toggle" -> Action.TOGGLE
                    else -> throw IllegalArgumentException()
                }
            val corner1 = Coordinate(x1.toInt(), y1.toInt())
            val corner2 = Coordinate(x2.toInt(), y2.toInt())
            return LightInstruction(action, corner1, corner2)
        }
    }
}