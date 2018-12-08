package util

/**
 * Various graph algorithms.
 */

data class SearchState<T>(
    val current: List<T>,
    val seen: Set<T> = setOf(),
    val level: Int = 0,
    val iterations: Int = 0,
    val found: Boolean = false
)

/**
 * Performs a breadth-first search, starting with the [start] nodes and looking for the [target].
 *
 * The neighbors of a node are given by [adjacent]. Nodes may be skipped by adding them to [seen].
 */
fun <T> breadthFirstSearch(
    start: Iterable<T>,
    seen: Set<T> = setOf(),
    target: T? = null,
    adjacent: (T) -> Iterable<T>
): SearchState<T> {
    val initial = SearchState(start.toList(), seen)
    return breadthFirstSearch(initial, target, adjacent)
}

private fun <T> breadthFirstSearch(state: SearchState<T>, target: T?, adjacent: (T) -> Iterable<T>): SearchState<T> {
    if (state.current.isEmpty()) {
        return state
    }

    val seen = state.seen.toMutableSet()
    var iterations = state.iterations
    val next = linkedSetOf<T>()
    state.current.forEachIndexed { index, node ->
        seen.add(node)
        iterations++
        if (node == target) {
            return SearchState(
                state.current.subList(index+1, state.current.size),
                seen,
                state.level,
                iterations,
                true)
        }
        next += adjacent(node).filterNot { seen.contains(it) }
    }

    val nextState = SearchState(next.toList(), seen, state.level + 1, iterations)
    return breadthFirstSearch(nextState, target, adjacent)
}

/**
 * Performs a depth-first search, starting with the [start] nodes and looking for the [target].
 *
 * The neighbors of a node are given by [adjacent]. Nodes may be skipped by adding them to [seen].
 */
fun <T> depthFirstSearch(
    start: Iterable<T>,
    seen: Set<T> = setOf(),
    target: T? = null,
    adjacent: (T) -> Iterable<T>
): SearchState<T> {
    val initial = SearchState(start.toList(), seen)
    return depthFirstSearch(initial, target, adjacent)
}

private fun <T> depthFirstSearch(state: SearchState<T>, target: T?, adjacent: (T) -> Iterable<T>): SearchState<T> {
    val seen = state.seen.toMutableSet()
    var current = state.current.filterNot { seen.contains(it) }
    if (current.isEmpty()) {
        return state.copy(current = current)
    }

    val node = current.first()
    seen.add(node)
    if (node == target) {
        return SearchState(
            current.subList(1, current.size),
            seen,
            state.level,
            state.iterations + 1,
            true)
    }
    current = adjacent(node).filterNot { seen.contains(it) } + current
    val nextState = SearchState(current, seen, state.level, state.iterations + 1)
    return depthFirstSearch(nextState, target, adjacent)
}