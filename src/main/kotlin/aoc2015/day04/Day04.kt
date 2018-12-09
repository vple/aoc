package aoc2015.day04

import util.loadResource
import java.math.BigInteger
import java.security.MessageDigest

/**
 * [Advent of Code 2015 Day 4](https://adventofcode.com/2015/day/4)
 */

const val INPUT_FILE = "/aoc2015/day04/input.txt"
val MD5 = MessageDigest.getInstance("MD5")

fun main(args: Array<String>) {
    val input = "bgvyzdsv"

    println("Part 1")
    println(findHash(input, "00000"))

    println("Part 2")
    println(findHash(input, "000000"))
}

fun findHash(prefix: String, hashPrefix: String): Pair<Int, String> {
    var n = 0
    var hash: String
    do {
        n++
        hash = hash(prefix, n)
    } while (!hash.startsWith(hashPrefix))
    return Pair(n, hash)
}

fun hash(prefix: String, number: Int): String {
    val input = prefix + number
    return MD5.digest(input.toByteArray()).joinToString("") {
        String.format("%02x", it)
    }
}
