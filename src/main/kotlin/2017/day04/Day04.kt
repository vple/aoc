package `2017`.day04

import util.loadResource

/**
 * [Advent of Code 2017 Day 4](https://adventofcode.com/2017/day/4)
 */

const val INPUT_FILE = "/2017/day04/input.txt"

fun main(args: Array<String>) {
    val input = loadResource(INPUT_FILE)
    val passphrases = input.lines().filterNot { it.isEmpty() }

    println("Part 1")
    println(countValidPassphrases(passphrases) { it })

    println("Part 2")
    println(countValidPassphrases(passphrases) { it.groupingBy { it }.eachCount() })
}

/**
 * Splits the given [passphrase] into words.
 */
fun passphraseWords(passphrase: String) = passphrase.split(" ")

/**
 * Returns true if the given [passphrase] is valid.
 */
fun <T> isValidPassphrase(passphrase: String, hash: (String) -> T): Boolean {
    val words = passphraseWords(passphrase)
    return words.count() == words.map(hash).toSet().count()
}

/**
 * Returns the number of [passphrases] that are valid.
 */
fun <T> countValidPassphrases(passphrases: List<String>, hash: (String) -> T) =
    passphrases.filter { isValidPassphrase(it, hash) }.count()