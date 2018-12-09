package aoc2017.day23

class Program(val id: Int, val instructions: List<Instruction>) {
    val registers: MutableMap<String, Long> = mutableMapOf()
    var instructionIndex: Int = 0
    var terminated = false
    val instructionCount: MutableMap<String, Int> = mutableMapOf()

    fun evaluate(terminate: () -> Boolean) {
        while (!terminate() && instructionIndex >= 0 && instructionIndex < instructions.size) {
            perform(instructions[instructionIndex])
        }
        terminated = true
    }

    fun perform(instruction: Instruction) {
        instruction.apply(this)
        instructionIndex = instruction.nextInstruction(this)
        val name = instruction.name
        instructionCount[name] = instructionCount.getOrDefault(name, 0) + 1
    }

    fun registerOrValue(value: String): Long {
        val longValue = value.toLongOrNull()
        return if (longValue != null) {
            longValue
        } else {
            registers.getOrDefault(value, 0)
        }
    }
}