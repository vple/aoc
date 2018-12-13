package util

/**
 * Various graph algorithms.
 */

data class SearchState<T>(
    val current: List<T>,
    val seen: Set<T> = setOf(),
    val level: Int = 0,
    val iterations: Int = 0,
    val found: Boolean = false,
    val result: T? = null
)

/**
 * Performs a breadth-first search, starting with the [start] nodes and looking for the [target].
 *
 * The neighbors of a node are given by [adjacent]. Nodes may be skipped by adding them to [seen].
 */
fun <T> breadthFirstSearch(
    start: Iterable<T>,
    seen: Set<T> = setOf(),
    target: ((T) -> Boolean)? = null,
    finishLevel: Boolean = false,
    adjacent: (T) -> Iterable<T>
): SearchState<T> {
    val initial = SearchState(start.toList(), seen)
    return breadthFirstSearch(initial, target, finishLevel, adjacent)
}

private fun <T> breadthFirstSearch(
    state: SearchState<T>,
    target: ((T) -> Boolean)?,
    finishLevel: Boolean,
    adjacent: (T) -> Iterable<T>
): SearchState<T> {
    if (state.current.isEmpty()) {
        return state
    }

    val seen = state.seen.toMutableSet()
    var iterations = state.iterations
    val next = linkedSetOf<T>()
    var match: SearchState<T>? = null
    state.current.forEachIndexed { index, node ->
        seen.add(node)
        iterations++
        if (target != null && target(node)) {
            val result =
                SearchState(
                    state.current.subList(index + 1, state.current.size),
                    seen,
                    state.level,
                    iterations,
                    true,
                    node)
            if (!finishLevel) {
                return result
            } else if (match == null) {
                match = result
            }
        }
        next += adjacent(node).filterNot { seen.contains(it) }
    }
    match?.let {
        return it.copy(listOf(), seen, iterations = iterations)
    }

    val nextState = SearchState(next.toList(), seen, state.level + 1, iterations)
    return breadthFirstSearch(nextState, target, finishLevel, adjacent)
}

/**
 * Performs a depth-first search, starting with the [start] nodes and looking for the [target].
 *
 * The neighbors of a node are given by [adjacent]. Nodes may be skipped by adding them to [seen].
 */
fun <T> depthFirstSearch(
    start: Iterable<T>,
    seen: Set<T> = setOf(),
    target: ((T) -> Boolean)?,
    adjacent: (T) -> Iterable<T>
): SearchState<T> {
    val initial = SearchState(start.toList(), seen)
    return depthFirstSearch(initial, target, adjacent)
}

private fun <T> depthFirstSearch(
    state: SearchState<T>,
    target: ((T) -> Boolean)?,
    adjacent: (T) -> Iterable<T>
): SearchState<T> {
    val seen = state.seen.toMutableSet()
    var current = state.current.filterNot { seen.contains(it) }
    if (current.isEmpty()) {
        return state.copy(current = current)
    }

    val node = current.first()
    seen.add(node)
    if (target != null && target(node)) {
        return SearchState(
            current.subList(1, current.size),
            seen,
            state.level,
            state.iterations + 1,
            true,
            node)
    }
    current = adjacent(node).filterNot { seen.contains(it) } + current
    val nextState = SearchState(current, seen, state.level, state.iterations + 1)
    return depthFirstSearch(nextState, target, adjacent)
}