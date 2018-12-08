package aoc2017.day11

import grid.Coordinate

enum class Direction(val unitCoordinate: Coordinate) {
    N (Coordinate(1, 0)),
    NE (Coordinate(0, 1)),
    SE (Coordinate(-1, 1)),
    S (Coordinate(-1, 0)),
    SW (Coordinate(0, -1)),
    NW (Coordinate(1, -1));

    companion object {
        fun parse(text: String): Direction {
            return when (text) {
                "n" -> Direction.N
                "ne" -> Direction.NE
                "se" -> Direction.SE
                "s" -> Direction.S
                "sw" -> Direction.SW
                "nw" -> Direction.NW
                else -> throw IllegalArgumentException()
            }
        }
    }
}