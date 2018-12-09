package aoc2017.day25

data class State(val name: String, val valueInstructions: Map<Int, List<Instruction>>) {
    fun instructions(value: Int) = valueInstructions.getOrDefault(value, listOf())

    companion object {
        const val NAME_PATTERN = "In state (\\w):"
        const val VALUE_PATTERN = "  If the current value is (\\d):"
        val NAME_REGEX = Regex(NAME_PATTERN)
        val VALUE_REGEX = Regex(VALUE_PATTERN)

        fun parse(lines: List<String>): State {
            val (_, name) = NAME_REGEX.matchEntire(lines[0])!!.groupValues
            val valueInstructions = listOf(lines.subList(1, 5), lines.subList(5, 9)).map { parseValueInstructions(it) }.toMap()
            return State(name, valueInstructions)
        }

        private fun parseValueInstructions(lines: List<String>): Pair<Int, List<Instruction>> {
            val (_, value) = VALUE_REGEX.matchEntire(lines[0])!!.groupValues
            val instructions = lines.subList(1, 4).map {
                Instruction.parse(
                    it)
            }
            return Pair(value.toInt(), instructions)
        }
    }
}