package grid

import kotlin.math.abs

data class Coordinate3D(val x: Int, val y: Int, val z: Int) {
    /**
     * Returns all neighboring coordinates.
     */
    val neighbors: List<Coordinate3D> by lazy {
        val neighbors = mutableListOf<Coordinate3D>()
        for (i in -1..1) {
            for (j in -1..1) {
                for (k in -1..1) {
                    if (i != 0 || j != 0 || k != 0) {
                        neighbors.add(Coordinate3D(x + i, y + j, z + k))
                    }
                }
            }
        }
        neighbors
    }

    /**
     * Returns all orthogonal neighboring coordinates.
     */
    val orthogonalNeighbors: List<Coordinate3D> by lazy {
        mutableListOf(
            Coordinate3D(x+1, y, z),
            Coordinate3D(x-1, y, z),
            Coordinate3D(x, y+1, z),
            Coordinate3D(x, y-1, z),
            Coordinate3D(x, y, z+1),
            Coordinate3D(x, y, z-1))
    }

    /**
     * Returns the coordinate at (x + [amount], y).
     */
    fun plusX(amount: Int) = Coordinate3D(x + amount, y, z)

    /**
     * Returns the coordinate at (x, y + [amount]).
     */
    fun plusY(amount: Int) = Coordinate3D(x, y + amount, z)

    fun manhattanDistance(other: Coordinate3D = Coordinate3D(0, 0, 0)): Int {
        return abs(x - other.x) + abs(y - other.y) + abs(z - other.z)
    }

    /**
     * Adds [other] to this coordinate.
     */
    operator fun plus(other: Coordinate3D) = Coordinate3D(x + other.x, y + other.y, z + other.z)

    /**
     * Subtracts [other] from this coordinate.
     */
    operator fun minus(other: Coordinate3D) = Coordinate3D(x - other.x, y - other.y, z - other.z)
}