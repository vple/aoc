package aoc2015.day02

import util.loadResourceLines

/**
 * [Advent of Code 2015 Day 2](https://adventofcode.com/2015/day/2)
 */

const val INPUT_FILE = "/aoc2015/day02/input.txt"

fun main(args: Array<String>) {
    val input = loadResourceLines(INPUT_FILE)
    val boxes = input.map { Box.parse(it) }

    println("Part 1")
    println(boxes.map { it.wrappingPaperRequired }.sum())

    println("Part 2")
    println(boxes.map { it.ribbonRequired }.sum())
}

data class Box(val length: Int, val width: Int, val height: Int) {
    val sideAreas = listOf(length * width, width * height, height * length)
    val volume = length * width * height
    val perimeters = listOf(2 * (length + width), 2 * (width + height), 2 * (height + length))
    val wrappingPaperRequired = 2 * sideAreas.sum() + sideAreas.min()!!
    val ribbonRequired = perimeters.min()!! + volume

    companion object {
        val REGEX = Regex("(\\d+)x(\\d+)x(\\d+)")
        fun parse(text: String): Box {
            val (_, l, w, h) = REGEX.matchEntire(text)!!.groupValues
            return Box(l.toInt(), w.toInt(), h.toInt())
        }
    }
}