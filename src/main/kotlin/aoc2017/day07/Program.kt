package aoc2017.day07

/**
 * A program.
 */
data class Program(val name: String, val weight: Int, val subprogramNames: Set<String> = setOf()) {
    /**
     * Returns true if this program is balanced.
     */
    fun isBalanced(totalWeights: Map<String, Int>): Boolean {
        if (subprogramNames.isEmpty()) {
            return true
        }

        return subprogramNames.map { totalWeights[it] }.toSet().size == 1
    }

    companion object {
        const val PROGRAM_PATTERN = "(\\w+) \\((\\d+)\\)"
        const val SUBPROGRAM_PATTERN = "-> (.+)"
        val PROGRAM_REGEX = Regex(PROGRAM_PATTERN)
        val SUBPROGRAM_REGEX = Regex(SUBPROGRAM_PATTERN)

        fun parse(program: String): Program {
            val programValues = PROGRAM_REGEX.find(program)!!.groupValues
            val name = programValues[1]
            val weight = programValues[2].toInt()

            val subprogramValues = SUBPROGRAM_REGEX.find(program)?.groupValues
            val subprogramNames =
                if (subprogramValues == null) {
                    setOf()
                } else {
                    subprogramValues[1].split(',').map { it.trim() }.toSet()
                }

            return Program(name, weight, subprogramNames)
        }
    }
}