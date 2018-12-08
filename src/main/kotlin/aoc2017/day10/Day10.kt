package aoc2017.day10

import util.loadResource

/**
 * [Advent of Code 2017 Day 10](https://adventofcode.com/2017/day/10)
 */

const val INPUT_FILE = "/aoc2017/day10/input.txt"

fun main(args: Array<String>) {
    val input = loadResource(INPUT_FILE).trim()
    val part1Lengths = input.split(",").map { it.toInt() }
    val part2Lengths = fullKnotHashLengths(input)

    println("Part 1")
    val hash = knotHash(part1Lengths)
    println(hash[0] * hash[1])

    println("Part 2")
    val sparseHash = knotHash(part2Lengths.asIterable())
    val denseHash = denseHash(sparseHash)
    println(encode(denseHash))
}

fun encode(denseHash: List<Int>): String {
    return denseHash.map { it.toString(16) }.map { it.padStart(2, '0') }.joinToString("")
}

fun denseHash(sparseHash: List<Int>): List<Int> {
    val denseHash = mutableListOf<Int>()
    var counter = 0
    var xorTotal = 0
    for (element in sparseHash) {
        xorTotal = xorTotal xor element
        if (++counter % 16 == 0) {
            denseHash += xorTotal
            xorTotal = 0
        }
    }
    return denseHash
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

fun fullKnotHashLengths(string: String): Sequence<Int> {
    val base = string.map { it.toInt() } + listOf(17, 31, 73, 47, 23)
    var i = 0
    return generateSequence {
        base.asSequence().takeIf { i++ < 64 }
    }.flatten()
}