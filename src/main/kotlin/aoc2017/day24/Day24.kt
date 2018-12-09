package aoc2017.day24

import util.breadthFirstSearch
import util.loadResourceLines

/**
 * [Advent of Code 2017 Day 24](https://adventofcode.com/2017/day/24)
 */

const val INPUT_FILE = "/aoc2017/day24/input.txt"

fun main(args: Array<String>) {
    val input = loadResourceLines(INPUT_FILE)
    val components = input.map { Component.parse(it) }
    val results = breadthFirstSearch(start = setOf(Bridge())) { bridge ->
        val remainingComponents = components - bridge.components
        val compatibleComponents = bridge.compatibleComponents(remainingComponents)
        compatibleComponents.map { bridge.connect(it) }
    }

    println("Part 1")
    val strongestBridge = results.seen.maxBy { it.strength }!!
    println(strongestBridge.strength)

    println("Part 2")
    val longestLength = results.seen.maxBy { it.length }!!.length
    val longestBridge = results.seen.filter { it.length == longestLength }
    val strongestLongestBridge = longestBridge.maxBy { it.strength }!!
    println(strongestLongestBridge.strength)
}
