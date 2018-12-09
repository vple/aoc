package aoc2015.day05

import util.loadResourceLines

/**
 * [Advent of Code 2015 Day 5](https://adventofcode.com/2015/day/5)
 */

const val INPUT_FILE = "/aoc2015/day05/input.txt"
val VOWELS = setOf('a', 'e', 'i', 'o', 'u')
val NAUGHTY_PAIRS = setOf(Pair('a', 'b'), Pair('c', 'd'), Pair('p', 'q'), Pair('x', 'y'))

fun main(args: Array<String>) {
    val input = loadResourceLines(INPUT_FILE)

    println("Part 1")
    val nice1Strings = input.filter { it.isNice1() }
    println(nice1Strings.size)

    println("Part 2")
    val nice2Strings = input.filter { it.isNice2() }
    println(nice2Strings.size)
}

fun String.isNice1(): Boolean {
    val numVowels = this.filter { VOWELS.contains(it) }.length

    val asciiFirstLetters = this.slice(0 until length-1)
    val asciiSecondLetters = this.slice(1 until length)
    val letterPairs = asciiFirstLetters.zip(asciiSecondLetters)
    val hasDuplicateCharacters = letterPairs.map { it.first - it.second }.toSet().contains(0)

    val hasNaughtyPair = letterPairs.filter { NAUGHTY_PAIRS.contains(it) }.isNotEmpty()

    return (numVowels >= 3) && hasDuplicateCharacters && !hasNaughtyPair
}

fun String.isNice2(): Boolean {
    return hasRepeatPair() && hasLetterSandwich()
}

fun String.hasRepeatPair(): Boolean {
    for (i in length-2 downTo 0) {
        val pair = substring(i..i+1)
        val matchingIndex = indexOf(pair)
        if (matchingIndex >= 0 && matchingIndex < i-1) {
            return true
        }
    }
    return false
}

fun String.hasLetterSandwich(): Boolean {
    for (i in 0 until length-2) {
        if (this[i] == this[i+2]) {
            return true
        }
    }
    return false
}