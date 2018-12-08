package aoc2017.day10

/**
 * The Knot Hash for a given [input].
 */
data class KnotHash(val input: String) {
    val lengths: List<Int> by lazy { input.map { it.toInt() } + listOf(17, 31, 73, 47, 23) }
    val fullLengths: Sequence<Int> by lazy {
        var i = 0
        generateSequence { lengths.asSequence().takeIf { i++ < 64 } }.flatten()
    }
    val sparseHash: List<Int> by lazy {
        val knot = MutableList(256) { it }
        val size = knot.size
        var position = 0
        var skip = 0
        for (length in fullLengths) {
            reverse(knot, position, length)
            position = (position + length + skip) % size
            skip++
        }
        knot
    }
    val denseHash by lazy {
        val denseHash = mutableListOf<Int>()
        var counter = 0
        var xorTotal = 0
        for (element in sparseHash) {
            xorTotal = xorTotal xor element
            if (++counter % 16 == 0) {
                denseHash += xorTotal
                xorTotal = 0
            }
        }
        denseHash.toList()
    }
    val binaryHash by lazy {
        denseHash.map { it.toString(2) }.map { it.padStart(8, '0') }.joinToString("")
    }
    val hexHash by lazy {
        denseHash.map { it.toString(16) }.map { it.padStart(2, '0') }.joinToString("")
    }

    private fun reverse(knot: MutableList<Int>, position: Int, length: Int) {
        val indices = (position until position+length).map { it % knot.size }
        val reversed = indices.zip(indices.map { knot[it] }.reversed()).toMap()
        indices.forEach { knot[it] = reversed[it]!! }
    }
}