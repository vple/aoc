package aoc2017.day07

import util.loadResource

/**
 * [Advent of Code 2017 Day 7](https://adventofcode.com/2017/day/7)
 */

const val INPUT_FILE = "/aoc2017/day07/input.txt"

fun main(args: Array<String>) {
    val input = loadResource(INPUT_FILE)
    val programs = input.lines().filterNot { it.isEmpty() }.map {
        Program.parse(
            it)
    }

    println("Part 1")
    println(findBottomProgram(programs))

    println("Part 2")
    findProgramWithWrongWeight(programs)
}

/**
 * Finds the bottom program in the given [programs].
 */
fun findBottomProgram(programs: List<Program>): Program {
    val subprogramNames = programs.map { it.subprogramNames }.flatten().toSet()
    return programs.single { !subprogramNames.contains(it.name) }
}

/**
 * Finds the wrong program and how much it should weigh.
 */
fun findProgramWithWrongWeight(programs: List<Program>) {
    val totalWeights = computeTotalWeights(programs)
    val programsByName = programs.associateBy { it.name }
    val unbalancedPrograms = programs.filter { !it.isBalanced(totalWeights) }
    val unbalancedNames = unbalancedPrograms.map { it.name }
    val canaryProgram = unbalancedPrograms.single { it.subprogramNames.intersect(unbalancedNames).isEmpty() }

    val misbalancedPrograms = canaryProgram.subprogramNames.map { programsByName[it]!! }
    println(misbalancedPrograms)
    val misbalancedTotalWeights = misbalancedPrograms.map { totalWeights[it.name]!! }
    println(misbalancedTotalWeights)
}

/**
 * Computes the total weights for all [programs].
 */
fun computeTotalWeights(programs: List<Program>): Map<String, Int> {
    val programsByName = programs.associateBy { it.name }
    var totalWeights = mapOf<String, Int>()
    programs.forEach{ totalWeights = computeTotalWeight(
        it.name,
        programsByName,
        totalWeights)
    }
    return totalWeights
}

/**
 * Computes the total weight for the given program [name], returning a map of all known total weights.
 */
fun computeTotalWeight(name: String, programs: Map<String, Program>, totalWeights: Map<String, Int>): Map<String, Int> {
    if (totalWeights.containsKey(name)) {
        return totalWeights
    }

    val program = checkNotNull(programs[name])
    var computedTotalWeights = totalWeights
    var weight = program.weight
    for (subprogramName in program.subprogramNames) {
        computedTotalWeights = computeTotalWeight(
            subprogramName,
            programs,
            computedTotalWeights)
        weight += computedTotalWeights[subprogramName]!!
    }

    return computedTotalWeights + Pair(name, weight)
}