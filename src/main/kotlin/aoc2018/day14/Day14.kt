package aoc2018.day14

/**
 * [Advent of Code 2018 Day 14](https://adventofcode.com/2018/day/14)
 */

const val INPUT_FILE = "/aoc2018/day14/input.txt"

fun main(args: Array<String>) {
    val input = 290431
    val initialScoreboard = Scoreboard("37", 0, 1)

    println(scoreUntil(5, initialScoreboard).recipes)

    println("Part 1")
    val scores1 = brew { it.size >= input + 10 }
    println(scores1.subList(input, input + 10).joinToString(""))

    println("Part 2")
    val inputDigits = input.toString().toList().map { it.toString().toInt() }
    val inputSize = inputDigits.size
    val scores2 = brew {
        // Each iteration can add one or two scores.
        it.takeLast(inputSize) == inputDigits || it.takeLast(inputSize+1).take(inputSize) == inputDigits
    }
    println(scores2.joinToString("").indexOf(input.toString()))
}

fun brew(complete: (List<Int>) -> Boolean): List<Int> {
    val recipes = mutableListOf(3, 7)
    var elf1Index = 0
    var elf2Index = 1

    while (true) {
        val elf1Score = recipes[elf1Index]
        val elf2Score = recipes[elf2Index]
        recipes += nextScores(elf1Score, elf2Score)
        elf1Index = (elf1Index + elf1Score + 1) % recipes.size
        elf2Index = (elf2Index + elf2Score + 1) % recipes.size
        if (complete(recipes)) {
            return recipes
        }
    }
}

fun nextScores(elf1Score: Int, elf2Score: Int): List<Int> {
    var total = elf1Score + elf2Score
    val digits = mutableListOf<Int>()
    do {
        digits += total % 10
        total /= 10
    } while (total > 0)
    return digits.reversed()
}