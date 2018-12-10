package aoc2015.day11

import util.loadResource

/**
 * [Advent of Code 2015 Day 11](https://adventofcode.com/2015/day/11)
 */

const val INPUT_FILE = "/aoc2015/day11/input.txt"

fun main(args: Array<String>) {
    val password = "hxbxwxba"

    println("Part 1")
    val nextPassword = searchNextPassword(password)
    println(nextPassword)

    println("Part 2")
    println(searchNextPassword(nextPassword))
}

fun searchNextPassword(start: String): String {
    var current = start.toPasswordInt()
    do {
        current++
    } while (!current.toPasswordString().isValidPassword())
    return current.toPasswordString()
}

val forbidden = setOf('i', 'o', 'l')
fun String.isValidPassword(): Boolean {
    val hasForbidden = forbidden.intersect(this.toSet()).isNotEmpty()
    return !hasForbidden && hasStraight() && hasTwoRepeatingPairs()
}

fun String.hasStraight(): Boolean {
    for (i in 0 until this.length-2) {
        if (this[i]+1 == this[i+1] && this[i+1]+1 == this[i+2]) {
            return true
        }
    }
    return false
}

fun String.hasTwoRepeatingPairs(): Boolean {
    val chunked0 = this.chunked(2)
    val chunked1 = this.substring(1).chunked(2)
    val pairs = listOf(chunked0, chunked1).flatten().filter { it.length == 2 }.toSet()
    val repeatingPairs = pairs.filter { it[0] == it[1] }
    return repeatingPairs.size >= 2
}

fun String.toPasswordInt(): Long {
    val digits = toList().map { it - 'a' }
    return digits.fold(0L) { total, digit -> total * 26 + digit }
}

fun Long.toPasswordString(): String {
    val digits = mutableListOf<Long>()
    var current = this
    while (current > 0) {
        digits.add(current % 26)
        current /= 26
    }
    return digits.reversed().map { 'a' + it.toInt() }.joinToString("")
}