package aoc2017.day13

data class Firewall(val layers: List<Layer>) {
    companion object {
        fun parse(lines: List<String>): Firewall {
            return Firewall(lines.map { Layer.parse(it) })
        }
    }

    fun isSafeTrip(startTime: Int): Boolean {
        return layers.all { it.isSafe(startTime + it.depth) }
    }

    fun tripSeverity(startTime: Int): Int {
        return layers.filter { it.scannerPosition(startTime + it.depth) == 0 }.map { it.severity }.sum()
    }
}

data class Layer(val depth: Int, val range: Int) {
    val severity = depth * range
    val period = 2 * range - 2

    fun isSafe(time: Int): Boolean {
        return scannerPosition(time) != 0
    }

    fun scannerPosition(time: Int): Int {
        val cyclePosition = time % period
        return if (cyclePosition < range) cyclePosition else period - cyclePosition
    }

    companion object {
        const val PATTERN = "(\\d+): (\\d+)"
        val REGEX = Regex(PATTERN)

        fun parse(text: String): Layer {
            val (_, depth, range) = REGEX.matchEntire(text)!!.groupValues
            return Layer(depth.toInt(), range.toInt())
        }
    }
}