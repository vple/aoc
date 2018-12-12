package aoc2018.day12

import util.loadResourceLines

/**
 * [Advent of Code 2018 Day 12](https://adventofcode.com/2018/day/12)
 */

const val INPUT_FILE = "/aoc2018/day12/input.txt"

fun main(args: Array<String>) {
    val input = loadResourceLines(INPUT_FILE)
    val initialState = input[0].substring("initial state: ".length)

    val patterns = input.subList(1, input.size).map { Pattern.parse(it) }
    val patternMap = patterns.associateBy { it.pattern }

    val garden = Garden(initialState, 0, patternMap)

    println("Part 1")
    var garden20 = garden
    repeat(20) { garden20 = garden20.advance() }
    println(garden20.sumPlants)

    println("Part 2")
    val (plantLength, convergeTime) = converge(garden)
    var converged = garden
    repeat(convergeTime) { converged = converged.advance() }
    val sum = converged.sumPlants + (50000000000 - convergeTime) * plantLength
    println(sum)
}

/**
 * Advances the given [garden] until all plants are in a row without gaps.
 *
 * Returns the number of plants and how long it took to converge.
 */
fun converge(garden: Garden): Pair<Int, Int> {
    var converged = garden
    var iterations = 0
    while (converged.plants.contains('.')) {
        converged = converged.advance()
        iterations++
    }
    return Pair(converged.plants.length, iterations)
}

data class Garden(val plants: String, val offset: Int = 0, val patterns: Map<String, Pattern>) {
    val widePlants = "...$plants..."

    val sumPlants: Int by lazy {
        plants.mapIndexedNotNull { index, c ->
            if (c == '#') {
                index + offset
            } else {
                null
            }
        }.sum()
    }

    fun advance(): Garden {
        val changes: MutableMap<Int, Boolean> = mutableMapOf()

        val next = (2 until widePlants.length - 2).map { index ->
            patterns[widePlants.substring(index-2..index+2)]!!.result
        }.joinToString("")

        val earliest = next.indexOf('#')
        return Garden(next.trim('.'), offset-1+earliest, patterns)
    }
}

data class Pattern(val pattern: String, val result: Char, val isAlive: Boolean) {
    companion object {
        val REGEX = Regex("(.*) => (.)")

        fun parse(text: String): Pattern {
            val (_, pattern, result) = REGEX.matchEntire(text)!!.groupValues
            val isAlive = result == "#"
            return Pattern(pattern, result[0], isAlive)
        }
    }
}
