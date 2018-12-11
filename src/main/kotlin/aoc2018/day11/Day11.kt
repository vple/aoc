package aoc2018.day11

import grid.Coordinate
import kotlin.system.measureTimeMillis

/**
 * [Advent of Code 2018 Day 11](https://adventofcode.com/2018/day/11)
 */

const val SERIAL_NUMBER = 9221

fun main(args: Array<String>) {
    val grid = Grid(SERIAL_NUMBER, 300)

    println("Part 1")
    println(grid.findBestAtSize(3))

    println("Part 2")
    println(grid.findBest())
}


data class Grid(val serialNumber: Int = SERIAL_NUMBER, val gridSize: Int = 300) {
    val fuelCells: List<List<FuelCell>> =
        List(gridSize) { y ->
            List(gridSize) { x ->
                FuelCell(Coordinate(x+1, y+1), serialNumber)
            }
        }
    val powerLevels: List<List<Int>> = fuelCells.map { row -> row.map { it.powerLevel } }
    val powerRectangles: List<List<Int>> =
        List(gridSize) { y ->
            List(gridSize) { x ->
                computePowerRectangle(x, y)
            }
        }

    fun computePowerRectangle(x: Int, y: Int): Int {
        return powerLevels.subList(0, y + 1).map { row ->
            row.subList(0, x + 1).sum()
        }.sum()
    }

    fun powerLevel(coordinate: Coordinate, size: Int): Int {
        // Coordinates are 1-indexed
        val topLeft = coordinate - Coordinate(1, 1)
        val bottomRight = topLeft + Coordinate(size-1, size-1)
        var power = powerRectangles[bottomRight.y][bottomRight.x]
        if (topLeft.y > 0) {
            power -= powerRectangles[topLeft.y - 1][bottomRight.x]
        }
        if (topLeft.x > 0) {
            power -= powerRectangles[bottomRight.y][topLeft.x - 1]
        }
        if (topLeft.x > 0 && topLeft.y > 0) {
            power += powerRectangles[topLeft.y - 1][topLeft.x - 1]
        }
        return power
    }

    fun powerLevelsAtSize(size: Int): Map<Coordinate, Int> {
        return List(gridSize - size + 1) { y ->
            List(gridSize - size + 1) { x ->
                val coordinate = Coordinate(x+1, y+1)
                val power = powerLevel(coordinate, size)
                Pair(coordinate, power)
            }
        }.flatten().toMap()
    }

    fun findBestAtSize(size: Int): Pair<Coordinate, Int> {
        return powerLevelsAtSize(size).maxBy { it.value }!!.toPair()
    }

    fun findBest(): Pair<Pair<Coordinate, Int>, Int> {
        return (1..300).map { Pair(findBestAtSize(it), it) }.maxBy { it.first.second }!!
    }
}

data class FuelCell(val coordinate: Coordinate, val serialNumber: Int) {
    val rackId = coordinate.x + 10
    val powerLevel by lazy {
        val baseValue = ((coordinate.y * rackId) + serialNumber) * rackId
        val hundreds = (baseValue / 100) % 10
        hundreds - 5
    }
}
