package aoc2017.day18

import util.loadResourceLines

/**
 * [Advent of Code 2017 Day 18](https://adventofcode.com/2017/day/18)
 */

const val INPUT_FILE = "/aoc2017/day18/input.txt"

fun main(args: Array<String>) {
    val input = loadResourceLines(INPUT_FILE)

    println("Part 1")
    val instructions1 = input.map { Instruction.parse(it, true) }
    val program = Program(0, instructions1, MessageQueue())
    program.evaluate { program.recover != null }
    println(program.recover)

    println("Part 2")
    val instructions2 = input.map { Instruction.parse(it, false) }
    val queue = MessageQueue()
    val program0 = Program(0, instructions2, queue)
    val program1 = Program(1, instructions2, queue)
    val deadlocked = {
        ((program0.waiting && !queue.hasMessage(0)) || program0.terminated)
            && ((program1.waiting && !queue.hasMessage(1)) || program1.terminated)
    }
    val terminated = { program0.terminated && program1.terminated }
    do {
        program0.evaluate(deadlocked)
        program1.evaluate(deadlocked)
    } while (!terminated())
    println(queue.sent[1])
}
