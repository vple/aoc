package aoc2015.day10

/**
 * [Advent of Code 2015 Day 10](https://adventofcode.com/2015/day/10)
 */

const val INPUT_FILE = "/aoc2015/day10/input.txt"

fun main(args: Array<String>) {
    val input = "1113222113"

    println("Part 1")
    var result1 = input
    repeat(40) { result1 = lookAndSay(result1) }
    println(result1.length)

    println("Part 2")
    repeat(10) { result1 = lookAndSay(result1) }
    println(result1.length)
}

fun lookAndSay(number: String): String {
    var char = ' '
    var count = 0
    val builder = StringBuilder()
    number.forEach {
        if (it == char) {
            count++
        } else {
            builder.append(count)
            builder.append(char)
            char = it
            count = 1
        }
    }
    builder.append(count)
    builder.append(char)
    return builder.toString().substring(2)
}