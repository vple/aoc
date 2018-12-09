package aoc2017.day18

import java.util.*

class Program(val id: Int, val instructions: List<Instruction>, val messageQueue: MessageQueue) {
    val registers: MutableMap<String, Long> = mutableMapOf(Pair("p", id.toLong()))
    var instructionIndex: Int = 0
    var lastSound: Long? = null
    var recover: Long? = null
    var waiting = false
    var terminated = false

    fun evaluate(terminate: () -> Boolean) {
        while (!terminate() && instructionIndex >= 0 && instructionIndex < instructions.size) {
            perform(instructions[instructionIndex])
            if (waiting) return
        }
        terminated = true
    }

    fun perform(instruction: Instruction) {
        if (instruction.canApply(this)) {
            waiting = false
            instruction.apply(this)
            instructionIndex = instruction.nextInstruction(this)
        } else {
            waiting = true
        }
    }

    fun registerOrValue(value: String): Long {
        val intValue = value.toLongOrNull()
        return if (intValue != null) {
            intValue
        } else {
            registers.getOrDefault(value, 0)
        }
    }
}

class MessageQueue {
    val queues: MutableMap<Int, Queue<Long>> =
        mutableMapOf(
            Pair(0, ArrayDeque()),
            Pair(1, ArrayDeque()))
    val sent = mutableMapOf(
        Pair(0, 0),
        Pair(1, 0))

    fun send(from: Int, value: Long) {
        queues[1 - from]!!.add(value)
        sent[from] = sent.getOrDefault(from, 0) + 1
    }

    fun hasMessage(to: Int): Boolean = queues[to]!!.isNotEmpty()

    fun receive(to: Int): Long = queues[to]!!.poll()
}