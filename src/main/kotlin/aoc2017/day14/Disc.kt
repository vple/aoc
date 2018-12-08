package aoc2017.day14

import aoc2017.day10.KnotHash
import grid.Coordinate

class Disc(val state: List<KnotHash>) {
    val usedBits: Int by lazy {
        usedCoordinates.size
    }

    val usedCoordinates: Set<Coordinate> by lazy {
        state.mapIndexed { row, knotHash ->
            val usedIndices = knotHash.binaryHash.mapIndexedNotNull { column, c -> if (c == '1') column else null }
            usedIndices.map { Coordinate(row, it) }
        }.flatten().toSet()
    }

    companion object {
        fun create(input: String): Disc {
            val hashInputs = (0..127).map { "$input-$it" }
            return Disc(hashInputs.map { KnotHash(it) })
        }
    }
}