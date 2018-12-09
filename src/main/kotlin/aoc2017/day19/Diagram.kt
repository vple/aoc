package aoc2017.day19

import grid.Coordinate

class Diagram(val lines: List<String>) {
    enum class Direction(val coordinate: Coordinate) {
        NORTH(Coordinate(0, -1)),
        EAST(Coordinate(1, 0)),
        SOUTH(Coordinate(0, 1)),
        WEST(Coordinate(-1, 0));

        fun turnDirections(): List<Direction> {
            if (this == NORTH || this == SOUTH) {
                return listOf(EAST, WEST)
            } else {
                return listOf(NORTH, SOUTH)
            }
        }
    }

    val startingCoordinate: Coordinate by lazy {
        Coordinate(lines[0].indexOf('|'), 0)
    }
    var currentCoordinate = startingCoordinate
    val currentChar: Char
        get() = charAt(currentCoordinate)
    var direction = Direction.SOUTH
    val letters = mutableListOf<Char>()
    var steps = 0

    fun charAt(coordinate: Coordinate): Char {
        return lines[coordinate.y][coordinate.x]
    }

    fun traverse() {
        while (currentChar != ' ') {
            step()
        }
    }

    fun step() {
        if (currentChar == ' ') {
            // Reached the end.
            return
        } else if (currentChar == '+') {
            turn()
        } else {
            stepStraight()
        }

        // New letter.
        if (currentChar.isLetter()) {
            letters.add(currentChar)
        }
        // Steps past the last coordinate, but we also start on the first coordinate.
        steps++
    }

    fun stepStraight() {
        currentCoordinate = currentCoordinate + direction.coordinate
    }

    fun turn() {
        direction = direction.turnDirections().filter{ charAt(currentCoordinate + it.coordinate) != ' ' }.single()
        stepStraight()
    }
}