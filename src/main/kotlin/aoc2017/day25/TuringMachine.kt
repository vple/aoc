package aoc2017.day25

class TuringMachine(val startingState: String, val states: Map<String, State>) {
    val tape: MutableMap<Int, Int> = mutableMapOf()
    var position: Int = 0
    var state: String = startingState

    val currentState: State
        get() = states[state]!!
    val currentValue: Int
        get() = tape.getOrDefault(position, 0)
    val diagnosticChecksum: Int
        get() = tape.values.filter { it == 1 }.count()

    fun step(n: Int = 1) = repeat(n) {
        currentState.instructions(currentValue).forEach { apply(it) }
    }

    fun apply(instruction: Instruction) {
        when (instruction) {
            is Write -> tape[position] = instruction.value
            is Move -> position += instruction.direction
            is Continue -> state = instruction.state
        }
    }
}