package aoc2017.day20

import grid.Coordinate3D

data class Particle(
    val id: Int,
    val position: Coordinate3D,
    val velocity: Coordinate3D,
    val acceleration: Coordinate3D,
    val ticks: Int = 0
) {
    fun tick(): Particle {
        val newVelocity = velocity + acceleration
        val newPosition = position + newVelocity
        return Particle(id, newPosition, newVelocity, acceleration, ticks+1)
    }

    companion object {
        val REGEX = Regex("p=<(-?\\d+),(-?\\d+),(-?\\d+)>, v=<(-?\\d+),(-?\\d+),(-?\\d+)>, a=<(-?\\d+),(-?\\d+),(-?\\d+)>")

        fun parse(id: Int, text: String): Particle {
            val values = REGEX.matchEntire(text)!!.groupValues
            return Particle(
                id,
                Coordinate3D(values[1].toInt(), values[2].toInt(), values[3].toInt()),
                Coordinate3D(values[4].toInt(), values[5].toInt(), values[6].toInt()),
                Coordinate3D(values[7].toInt(), values[8].toInt(), values[9].toInt()))
        }
    }
}