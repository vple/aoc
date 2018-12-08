package aoc2018.day08

import collections.Node
import java.util.*

/**
 * A node in the license file.
 */
data class LicenseNode(override val children: List<LicenseNode>, val metadata: List<Int>): Node<Int> {
    val metadataSum: Int by lazy {
        metadata.sum()
    }

    override val value: Int by lazy {
        if (children.isEmpty()) {
            metadataSum
        } else {
            metadata
                .map { it-1 }
                .filter { it >= 0 && it < children.size }
                .map { children[it].value }
                .sum()
        }
    }

    companion object {
        /**
         * Parses a list of numbers into a LicenseNode.
         */
        fun parse(numbers: List<Int>): LicenseNode {
            return parse(ArrayDeque<Int>(numbers))
        }

        /**
         * Parses a queue of numbers into a LicenseNode.
         */
        private fun parse(numbers: Queue<Int>): LicenseNode {
            val numChildNodes = numbers.poll()
            val numMetadataEntries = numbers.poll()
            val children = List(numChildNodes) { parse(numbers) }
            val metadata = List(numMetadataEntries) { numbers.poll() }
            return LicenseNode(children, metadata)
        }
    }
}