package aoc2017.day12

data class Pipe(val programId: Int, val connectedProgramIds: Set<Int>) {
    companion object {
        const val PATTERN = "(\\d+) <-> (.+)"
        val REGEX = Regex(PATTERN)

        fun parse(text: String): Pipe {
            val (_, id, connections) = REGEX.matchEntire(text)!!.groupValues
            return Pipe(id.toInt(), connections.split(",").map { it.trim().toInt() }.toSet())
        }
    }
}