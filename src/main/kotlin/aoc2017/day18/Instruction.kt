package aoc2017.day18

sealed class Instruction {
    open fun canApply(program: Program) = true
    abstract fun apply(program: Program)

    open fun nextInstruction(program: Program): Int {
        return program.instructionIndex + 1
    }

    companion object {
        fun parse(text: String, part1: Boolean): Instruction {
            Set.tryParse(text)?.let { return it }
            BinaryInstruction.tryParse(text)?.let { return it }
            if (part1) {
                Sound.tryParse(text)?.let { return it }
                Recover.tryParse(text)?.let { return it }
            } else {
                Send.tryParse(text)?.let { return it }
                Receive.tryParse(text)?.let { return it }
            }
            JumpGreaterZero.tryParse(text)?.let { return it }
            throw IllegalArgumentException()
        }
    }
}

data class Sound(val value: String): Instruction() {
    override fun apply(program: Program) = with(program) {
        lastSound = registerOrValue(value)
    }

    companion object {
        val REGEX = Regex("snd (\\w+)")

        fun tryParse(text: String): Sound? {
            return REGEX.find(text)?.groupValues?.let { (_, value) -> Sound(value) }
        }
    }
}

data class Set(val register: String, val value: String): Instruction() {
    override fun apply(program: Program) = with(program) {
        registers[register] = registerOrValue(value)
    }

    companion object {
        val REGEX = Regex("set (\\w+) (-?\\w+)")

        fun tryParse(text: String): Set? {
            return REGEX.find(text)?.groupValues?.let { (_, register, value) -> Set(register, value) }
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
                    "add" -> Add(register, value)
                    "mul" -> Mul(register, value)
                    "mod" -> Mod(register, value)
                    else -> null
                }
            }
        }
    }
}

data class Add(override val register: String, override val value: String): BinaryInstruction(register, value, Long::plus)
data class Mul(override val register: String, override val value: String): BinaryInstruction(register, value, Long::times)
data class Mod(override val register: String, override val value: String): BinaryInstruction(register, value, Long::rem)

data class Recover(val value: String): Instruction() {
    override fun apply(program: Program) = with(program) {
        if (registerOrValue(value) != 0L) {
            recover = lastSound
        }
    }

    companion object {
        val REGEX = Regex("rcv (\\w+)")

        fun tryParse(text: String): Recover? {
            return REGEX.find(text)?.groupValues?.let { (_, value) -> Recover(value) }
        }
    }
}

data class JumpGreaterZero(val register: String, val value: String): Instruction() {
    override fun apply(program: Program) {}

    override fun nextInstruction(program: Program): Int {
        return if (program.registerOrValue(register) > 0) {
            program.instructionIndex + program.registerOrValue(value).toInt()
        } else {
            program.instructionIndex + 1
        }
    }

    companion object {
        val REGEX = Regex("jgz (\\w+) (-?\\w+)")

        fun tryParse(text: String): JumpGreaterZero? {
            return REGEX.find(text)?.groupValues?.let { (_, register, value) -> JumpGreaterZero(register, value) }
        }
    }
}

data class Send(val value: String): Instruction() {
    override fun apply(program: Program) = with(program) {
        messageQueue.send(id, registerOrValue(value))
    }

    companion object {
        val REGEX = Regex("snd (\\w+)")

        fun tryParse(text: String): Send? {
            return REGEX.find(text)?.groupValues?.let { (_, value) -> Send(value) }
        }
    }
}

data class Receive(val register: String): Instruction() {
    override fun canApply(program: Program) = program.run {
        messageQueue.hasMessage(id)
    }

    override fun apply(program: Program) = with(program) {
        registers[register] = messageQueue.receive(id)
    }

    companion object {
        val REGEX = Regex("rcv (\\w+)")

        fun tryParse(text: String): Receive? {
            return REGEX.find(text)?.groupValues?.let { (_, value) -> Receive(value) }
        }
    }
}