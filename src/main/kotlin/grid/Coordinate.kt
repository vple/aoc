package grid

data class Coordinate(val x: Int, val y: Int) {
    /**
     * Returns all neighboring coordinates.
     */
    val neighbors: List<Coordinate> by lazy {
        val neighbors = mutableListOf<Coordinate>()
        for (i in -1..1) {
            for (j in -1..1) {
                if (i != 0 || j != 0) {
                    neighbors.add(Coordinate(x + i, y + j))
                }
            }
        }
        neighbors
    }

    /**
     * Returns all orthogonal neighboring coordinates.
     */
    val orthogonalNeighbors: List<Coordinate> by lazy {
        mutableListOf(
            Coordinate(x+1, y),
            Coordinate(x, y+1),
            Coordinate(x-1, y),
            Coordinate(x, y-1))
    }

    /**
     * Returns the coordinate at (x + [amount], y).
     */
    fun plusX(amount: Int) = Coordinate(x + amount, y)

    /**
     * Returns the coordinate at (x, y + [amount]).
     */
    fun plusY(amount: Int) = Coordinate(x, y + amount)

    /**
     * Adds [other] to this coordinate.
     */
    operator fun plus(other: Coordinate) = Coordinate(x + other.x, y + other.y)

    /**
     * Subtracts [other] from this coordinate.
     */
    operator fun minus(other: Coordinate) = Coordinate(x - other.x, y - other.y)
}