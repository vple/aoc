package util

/**
 * Various graph algorithms.
 */

data class SearchState<T>(
    val current: List<T>,
    val seen: Set<T> = setOf(),
    val level: Int = 0,
    val iterations: Int = 0
)

fun <T> breadthFirstSearch(
    start: Iterable<T>,
    seen: Set<T> = setOf(),
    target: T? = null,
    adjacent: (T) -> Iterable<T>
): SearchState<T> {
    val initial = SearchState(start.toList(), seen)
    return breadthFirstSearch(initial, target, adjacent)
}

fun <T> breadthFirstSearch(state: SearchState<T>, target: T?, adjacent: (T) -> Iterable<T>): SearchState<T> {
    if (state.current.isEmpty()) {
        return state
    }

    val seen = state.seen.toMutableSet()
    var iterations = state.iterations
    val next = linkedSetOf<T>()
    state.current.forEachIndexed { index, item ->
        seen.add(item)
        iterations++
        if (item == target) {
            return SearchState(
                state.current.subList(index+1, state.current.size),
                seen,
                state.level,
                iterations)
        }
        next += adjacent(item).filterNot { seen.contains(it) }
    }

    val nextState = SearchState(next.toList(), seen, state.level + 1, iterations)
    return breadthFirstSearch(nextState, target, adjacent)
}