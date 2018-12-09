package aoc2017.day23

sealed class Instruction {
    abstract val name: String

    abstract fun apply(program: Program)
    open fun nextInstruction(program: Program): Int {
        return program.instructionIndex + 1
    }

    companion object {
        fun parse(text: String): Instruction {
            Set.tryParse(text)?.let { return it }
            BinaryInstruction.tryParse(text)?.let { return it }
            Jnz.tryParse(text)?.let { return it }
            throw IllegalArgumentException()
        }
    }
}

data class Set(val register: String, val value: String): Instruction() {
    override val name: String = "set"

    override fun apply(program: Program) = with(program) {
        registers[register] = registerOrValue(value)
    }

    companion object {
        val REGEX = Regex("set (\\w+) (-?\\w+)")

        fun tryParse(text: String): Set? {
            return REGEX.find(text)?.groupValues?.let { (_, register, value) ->
                Set(
                    register,
                    value)
            }
        }
    }
}

sealed class BinaryInstruction(
    open val register: String,
    open val value: String,
    val operation: (Long, Long) -> Long
) : Instruction() {
    override fun apply(program: Program) = with(program) {
        registers[register] = operation(registerOrValue(register), registerOrValue(value))
    }

    companion object {
        val REGEX = Regex("(\\w+) (\\w+) (-?\\w+)")

        fun tryParse(text: String): BinaryInstruction? {
            return REGEX.find(text)?.groupValues?.let { (_, operation, register, value) ->
                when (operation) {
                    "sub" -> Sub(register, value)
                    "mul" -> Mul(register, value)
                    else -> null
                }
            }
        }
    }
}

data class Sub(override val register: String, override val value: String): BinaryInstruction(register, value, Long::minus) {
    override val name: String = "sub"
}

data class Mul(override val register: String, override val value: String): BinaryInstruction(register, value, Long::times) {
    override val name: String = "mul"
}

data class Jnz(val register: String, val value: String): Instruction() {
    override val name: String = "jnz"

    override fun apply(program: Program) {}

    override fun nextInstruction(program: Program): Int {
        return if (program.registerOrValue(register) != 0L) {
            program.instructionIndex + program.registerOrValue(value).toInt()
        } else {
            program.instructionIndex + 1
        }
    }

    companion object {
        val REGEX = Regex("jnz (\\w+) (-?\\w+)")

        fun tryParse(text: String): Jnz? {
            return REGEX.find(text)?.groupValues?.let { (_, register, value) ->
                Jnz(
                    register,
                    value)
            }
        }
    }
}