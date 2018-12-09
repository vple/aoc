package aoc2017.day25

data class Blueprint(val startState: String, val steps: Int, val states: Map<String, State>) {
    fun createTuringMachine(): TuringMachine {
        return TuringMachine(startState, states)
    }

    companion object {
        const val START_PATTERN = "Begin in state (\\w)."
        const val STEPS_PATTERN = "Perform a diagnostic checksum after (\\d+) steps."
        val START_REGEX = Regex(START_PATTERN)
        val STEPS_REGEX = Regex(STEPS_PATTERN)

        fun parse(lines: List<String>): Blueprint {
            val (_, startState) = START_REGEX.matchEntire(lines[0])!!.groupValues
            val (_, steps) = STEPS_REGEX.matchEntire(lines[1])!!.groupValues

            val numStates = (lines.size - 2) / 9
            val states = (0 until numStates).map { lines.subList(it * 9 + 2, (it + 1) * 9 + 2) }.map { State.parse(it) }

            return Blueprint(startState, steps.toInt(), states.associateBy { it.name })
        }
    }
}