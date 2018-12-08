package aoc2018.day08

import collections.Tree
import util.loadResourceLines

/**
 * [Advent of Code 2018 Day 8](https://adventofcode.com/2018/day/8)
 */

const val INPUT_FILE = "/aoc2018/day08/input.txt"

fun main(args: Array<String>) {
    val numbers = loadResourceLines(INPUT_FILE).first().split(" ").map { it.toInt() }
    val node = LicenseNode.parse(numbers)
    val tree = Tree(node)

    println("Part 1")
    println(tree.traverseBreadthFirst().fold(0) { sum, node -> sum + node.metadataSum })

    println("Part 2")
    println(node.value)
}
