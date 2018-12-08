package aoc2017.day15

class Generator(val seed: Long, val factor: Long, val mod: Long, val multiple: Int = 1) {
    fun asSequence(): Sequence<Long> {
        var current = seed
        return generateSequence {
            do {
                current = (current * factor) % mod
                current = (current + mod) % mod
            } while ((current % multiple).toInt() != 0)
            current
        }
    }
}