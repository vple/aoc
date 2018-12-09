package aoc2017.day20

import util.loadResourceLines

/**
 * [Advent of Code 2017 Day 20](https://adventofcode.com/2017/day/20)
 */

const val INPUT_FILE = "/aoc2017/day20/input.txt"

fun main(args: Array<String>) {
    val input = loadResourceLines(INPUT_FILE)
    val particles = input.mapIndexed { index, s ->  Particle.parse(index, s) }

    println("Part 1")
    val minAcceleration = particles.map { it.acceleration.manhattanDistance() }.min()!!
    val closestA = particles.filter { it.acceleration.manhattanDistance() == minAcceleration }
    val minStartingVelocity = closestA.map { it.velocity.manhattanDistance() }.min()!!
    val closestAV = closestA.filter { it.velocity.manhattanDistance() == minStartingVelocity }
    println(closestAV.single())

    println("Part 2")
    simulate(particles)
}

fun tickAll(particles: List<Particle>): List<Particle> {
    val newParticles = particles.map { it.tick() }
    val collisionPositions = newParticles.groupingBy { it.position }.eachCount().filterValues { it > 1 }.keys
    return newParticles.filterNot { collisionPositions.contains(it.position) }
}

fun simulate(startingParticles: List<Particle>) {
    var particles = startingParticles
    while (true) {
        println(particles.size)
        particles = tickAll(particles)
    }
}