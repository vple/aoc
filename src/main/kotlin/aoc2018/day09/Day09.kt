package aoc2018.day09

/**
 * [Advent of Code 2018 Day 9](https://adventofcode.com/2018/day/9)
 */

const val INPUT_FILE = "/aoc2018/day09/input.txt"

fun main(args: Array<String>) {
    println("Part 1")
    val scores1 = game2(71510, 447)
    println(scores1.values.max())

    println("Part 2")
    val scores2 = game2(71510*100, 447)
    println(scores2.values.max())
}

data class Marble(val value: Int) {
    var next: Marble = this
    var prev: Marble = this
}

fun game2(marbles: Int, numPlayers: Int): Map<Int, Long> {
    var currentMarble = Marble(0)
    val scores: MutableMap<Int, Long> = mutableMapOf()

    (1..marbles).forEach { nextMarble ->
        val currentPlayer = nextMarble % numPlayers
        if (nextMarble % 23 == 0) {
            repeat(7) { currentMarble = currentMarble.prev }
            currentMarble.prev.next = currentMarble.next
            currentMarble.next.prev = currentMarble.prev
            val score = currentMarble.value + nextMarble
            currentMarble = currentMarble.next
            scores[currentPlayer] = scores.getOrDefault(currentPlayer, 0) + score
        } else {
            currentMarble = currentMarble.next
            val marble = Marble(nextMarble)
            marble.prev = currentMarble
            marble.next = currentMarble.next
            marble.next.prev = marble
            marble.prev.next = marble
            currentMarble = marble
        }
    }

    return scores
}