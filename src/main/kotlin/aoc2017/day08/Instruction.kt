package aoc2017.day08

import java.lang.IllegalArgumentException

/**
 * An instruction.
 */
data class Instruction(
    val register: String,
    val action: Action,
    val amount: Int,
    val condition: Condition)
{
    enum class Action {
        INC, DEC
    }

    /**
     * Tries to apply this instruction, based on the given [registers]. Returns the resulting registers.
     */
    fun apply(registers: Map<String, Int>): Map<String, Int> {
        if (condition.isSatisfiedBy(registers)) {
            val value = registers.getOrDefault(register, 0)
            val newValue: Int
            if (action == Action.INC) {
                newValue = value + amount
            } else {
                newValue = value - amount
            }
            return registers + Pair(register, newValue)
        } else {
            return registers
        }
    }

    companion object {
        const val PATTERN = "(\\w+) (inc|dec) (-?\\d+)"
        val REGEX = Regex(PATTERN)

        fun parse(text: String): Instruction {
            val values = REGEX.find(text)?.groupValues!!
            val action = when(values[2]) {
                "inc" -> Action.INC
                "dec" -> Action.DEC
                else -> throw IllegalArgumentException("Invalid action")
            }

            return Instruction(values[1], action, values[3].toInt(), Condition.parse(text))
        }
    }
}

data class Condition(val register: String, val comparison: Comparison, val amount: Int) {
    enum class Comparison {
        LESS_THAN,
        LESS_THAN_OR_EQUAL_TO,
        EQUAL_TO,
        GREATER_THAN_OR_EQUAL_TO,
        GREATER_THAN,
        NOT_EQUAL_TO
    }

    /**
     * Returns true if this condition is satisfied by the given [registers].
     */
    fun isSatisfiedBy(registers: Map<String, Int>): Boolean {
        val value = registers.getOrDefault(register, 0)
        return when (comparison) {
            Comparison.LESS_THAN -> value < amount
            Comparison.LESS_THAN_OR_EQUAL_TO -> value <= amount
            Comparison.EQUAL_TO -> value == amount
            Comparison.GREATER_THAN_OR_EQUAL_TO -> value >= amount
            Comparison.GREATER_THAN -> value > amount
            Comparison.NOT_EQUAL_TO -> value != amount
        }
    }

    companion object {
        const val PATTERN = "if (\\w+) (.+) (-?\\d+)"
        val REGEX = Regex(PATTERN)

        fun parse(text: String): Condition {
            val values = REGEX.find(text)?.groupValues!!
            val comparison = when(values[2]) {
                "<" -> Comparison.LESS_THAN
                "<=" -> Comparison.LESS_THAN_OR_EQUAL_TO
                "==" -> Comparison.EQUAL_TO
                ">=" -> Comparison.GREATER_THAN_OR_EQUAL_TO
                ">" -> Comparison.GREATER_THAN
                "!=" -> Comparison.NOT_EQUAL_TO
                else -> throw IllegalArgumentException("Invalid comparison")
            }

            return Condition(values[1], comparison, values[3].toInt())
        }
    }
}