package aoc2015.day25

import grid.Coordinate

/**
 * [Advent of Code 2015 Day 25](https://adventofcode.com/2015/day/25)
 */

fun main(args: Array<String>) {
    val input = Coordinate(3019, 3010)
    val start = 20151125L
    val multiplier = 252533L
    val mod = 33554393L
    val generator = CodeGenerator(start, multiplier, mod)

    println("Part 1")
    println(generator.generateCode(input))

    println("Part 2")
}

data class CodeGenerator(val start: Long, val multiplier: Long, val mod: Long) {
    val powerModulos = mutableMapOf<Long, Long>(Pair(0, 1), Pair(1, multiplier))

    fun generateCode(coordinate: Coordinate): Long {
        val codeNumber = codeNumber(coordinate)
        val power = codeNumber - 1
        val result = (start * computePowerModulo(power)) % mod
        return result
    }

    private fun codeNumber(coordinate: Coordinate): Long {
        val diagonal = coordinate.manhattanDistance() - 1   // 1-indexed
        return triangularNumber(diagonal.toLong() - 1) + coordinate.x
    }

    private fun triangularNumber(n: Long): Long {
        return n * (n + 1) / 2
    }

    private fun computePowerModulo(power: Long): Long {
        if (powerModulos.containsKey(power)) {
            return powerModulos[power]!!
        }
        val halfPower = power/2
        val result: Long
        if (power % 2L == 0L) {
            result = (computePowerModulo(halfPower) * computePowerModulo(halfPower)) % mod
        } else {
            result = (computePowerModulo(halfPower) * computePowerModulo(halfPower+1)) % mod
        }
        powerModulos[power] = result
        return result
    }
}