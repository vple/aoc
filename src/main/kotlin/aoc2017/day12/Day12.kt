package aoc2017.day12

import util.breadthFirstSearch
import util.loadResourceLines

/**
 * [Advent of Code 2017 Day 12](https://adventofcode.com/2017/day/12)
 */

const val INPUT_FILE = "/aoc2017/day12/input.txt"

fun main(args: Array<String>) {
    val input = loadResourceLines(INPUT_FILE)
    val pipes = input.map { Pipe.parse(it) }
    val connections = constructConnections(pipes)

    println("Part 1")
    val searchResult = breadthFirstSearch(start = setOf(0), adjacent = { connections.getOrDefault(it, setOf()) })
    println(searchResult.seen.size)

    println("Part 2")
    println(countGroups(connections))
}

fun constructConnections(pipes: List<Pipe>): Map<Int, Set<Int>> {
    val connections = mutableMapOf<Int, Set<Int>>()
    pipes.forEach { (programId, connectedProgramIds) ->
        connections[programId] = connections.getOrDefault(programId, setOf()) + connectedProgramIds
        connectedProgramIds.forEach {
            connections[it] = connections.getOrDefault(it, setOf()) + programId
        }
    }
    return connections.toMap()
}

fun countGroups(connections: Map<Int, Set<Int>>): Int {
    var programIds = connections.keys
    var groups = 0
    while (programIds.isNotEmpty()) {
        val programId = programIds.first()
        val searchResult = breadthFirstSearch(start = setOf(programId), adjacent = { connections.getOrDefault(it, setOf()) })
        programIds -= searchResult.seen
        groups++
    }
    return groups
}