package aoc2017.day10

import util.loadResource

/**
 * [Advent of Code 2017 Day 10](https://adventofcode.com/2017/day/10)
 */

const val INPUT_FILE = "/aoc2017/day10/input.txt"

fun main(args: Array<String>) {
    val input = loadResource(INPUT_FILE).trim()
    val part1Lengths = input.split(",").map { it.toInt() }

    println("Part 1")
    val hash = knotHash(part1Lengths)
    println(hash[0] * hash[1])

    println("Part 2")
    println(KnotHash(input).hash)
}

fun knotHash(lengths: Iterable<Int>): MutableList<Int> {
    val knot = MutableList(256) { it }
    return knotHashHelper(knot, lengths.iterator())
}

fun knotHashHelper(knot: MutableList<Int>, lengths: Iterator<Int>, position: Int = 0, skip: Int = 0): MutableList<Int> {
    if (!lengths.hasNext()) {
        return knot
    }
    val length = lengths.next()
    reverse(knot, position, length)
    return knotHashHelper(knot, lengths, (position + length + skip) % knot.size, skip + 1)
}

fun reverse(knot: MutableList<Int>, position: Int, length: Int) {
    val indices = (position until position+length).map { it % knot.size }
    val reversed = indices.zip(indices.map { knot[it] }.reversed()).toMap()
    indices.forEach { knot[it] = reversed[it]!! }
}