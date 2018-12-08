package collections

import java.util.*

/**
 * A tree.
 */
class Tree<T, N : Node<T>>(val root: N) {
    /**
     * Breadth-first traversal of this tree.
     */
    fun traverseBreadthFirst(): Sequence<N> {
        val queue = ArrayDeque<N>()
        queue.add(root)
        return generateSequence {
            if (queue.isEmpty()) {
                null
            } else {
                @Suppress("UNCHECKED_CAST")
                queue.poll().also { queue.addAll(it.children as Collection<N>) }
            }
        }
    }
}

/**
 * A node in a tree.
 */
interface Node<T> {
    val children: List<Node<T>>
    val value: T
}