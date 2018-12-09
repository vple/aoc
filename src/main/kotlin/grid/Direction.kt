package grid

enum class Direction(val coordinate: Coordinate) {
    NORTH(Coordinate(0, 1)),
    EAST(Coordinate(1, 0)),
    SOUTH(Coordinate(0, -1)),
    WEST(Coordinate(-1, 0));

    fun rotateRight(n: Int = 1): Direction {
        val values = Direction.values()
        val index = (ordinal + n) % values.size
        return values[index]
    }

    fun rotateLeft(n: Int = 1): Direction {
        val values = Direction.values()
        val index = (((ordinal - n) % values.size) + values.size) % values.size
        return values[index]
    }
}