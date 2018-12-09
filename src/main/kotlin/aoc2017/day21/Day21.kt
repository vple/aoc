package aoc2017.day21

import util.loadResourceLines

/**
 * [Advent of Code 2017 Day 21](https://adventofcode.com/2017/day/21)
 */

const val INPUT_FILE = "/aoc2017/day21/input.txt"

fun main(args: Array<String>) {
    val input = loadResourceLines(INPUT_FILE)
    val rules = input.map { EnhancementRule.parse(it) }
    val book = EnhancementBook(rules)

    println("Part 1")
    var image = Image()
    repeat(5) { image = image.iterate(book) }
    println(image.numPixelsOn)

    println("Part 2")
    image = Image()
    repeat(18) { image = image.iterate(book) }
    println(image.numPixelsOn)
}
