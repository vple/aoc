package aoc2017.day16

import util.loadResource

/**
 * [Advent of Code 2017 Day 16](https://adventofcode.com/2017/day/16)
 */

const val INPUT_FILE = "/aoc2017/day16/input.txt"

fun main(args: Array<String>) {
    val input = loadResource(INPUT_FILE).trim().split(",")
    val danceMoves = input.map { DanceMove.parse(it) }

    println("Part 1")
    println(applyMoves(danceMoves))

    println("Part 2")
    println(quickMoves(1000000000, danceMoves))
}

val startingPosition: String = ('a'..'p').joinToString("")

fun applyMoves(danceMoves: List<DanceMove>, start: String = startingPosition): String {
    return danceMoves.fold(start) { order, danceMove -> danceMove.apply(order) }
}

fun period(danceMoves: List<DanceMove>): Int {
    var order = startingPosition
    var count = 0
    do {
        order = applyMoves(danceMoves, order)
        count++
    } while (order != startingPosition)
    return count
}

fun quickMoves(n: Int, danceMoves: List<DanceMove>): String {
    val period = period(danceMoves)
    var result = startingPosition
    repeat(1000000000 % period) { result = applyMoves(danceMoves, result)}
    return result
}