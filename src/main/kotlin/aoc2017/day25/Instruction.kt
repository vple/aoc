package aoc2017.day25

sealed class Instruction {
    companion object {
        const val WRITE_PATTERN = "    - Write the value (\\d)."
        const val MOVE_PATTERN = "    - Move one slot to the (\\w+)."
        const val CONTINUE_PATTERN = "    - Continue with state (\\w)."
        val WRITE_REGEX = Regex(WRITE_PATTERN)
        val MOVE_REGEX = Regex(MOVE_PATTERN)
        val CONTINUE_REGEX = Regex(CONTINUE_PATTERN)

        fun parse(text: String): Instruction {
            WRITE_REGEX.matchEntire(text)?.groupValues?.let { (_, value) ->
                return Write(value.toInt())
            }
            MOVE_REGEX.matchEntire(text)?.groupValues?.let { (_, direction) ->
                if (direction == "right") {
                    return Move(1)
                } else {
                    return Move(-1)
                }
            }
            CONTINUE_REGEX.matchEntire(text)?.groupValues?.let { (_, state) ->
                return Continue(state)
            }
            throw IllegalArgumentException()
        }
    }
}

data class Write(val value: Int): Instruction()
data class Move(val direction: Int): Instruction()
data class Continue(val state: String): Instruction()