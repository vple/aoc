package aoc2015.day08

import util.loadResourceLines
import java.lang.StringBuilder

/**
 * [Advent of Code 2015 Day 8](https://adventofcode.com/2015/day/8)
 */

const val INPUT_FILE = "/aoc2015/day08/input.txt"

fun main(args: Array<String>) {
    val input = loadResourceLines(INPUT_FILE)
    val literals = input.map { StringLiteral(it) }

    println("Part 1")
    val totalCodeCharacters = literals.map { it.codeCharacters }.sum()
    val totalMemoryCharacters = literals.map { it.memoryCharacters }.sum()
    println(totalCodeCharacters - totalMemoryCharacters)

    println("Part 2")
    val totalEncodedCharacters = literals.map { it.encodedString.length }.sum()
    println(totalEncodedCharacters - totalCodeCharacters)
}

data class StringLiteral(val string: String) {
    val codeCharacters = string.length
    val memoryCharacters by lazy {
        var count = 0
        var i = 1
        while (i < string.length-1) {
            count++
            if (string[i] == '\\') {
                when (string[i+1]) {
                    '\\' -> i++
                    '"' -> i++
                    'x' -> i+=3
                }
            }
            i++
        }
        count
    }
    val encodedString by lazy {
        StringBuilder().apply {
            append('"')
            string.forEach {
                when (it) {
                    '"' -> append("\\\"")
                    '\\' -> append("\\\\")
                    else -> append(it)
                }
            }
            append('"')
        }.toString()
    }
}