package collections

import kotlin.coroutines.experimental.buildIterator

/**
 * A circular list.
 */
public interface CircularList<E> : List<E> {
    val backingList: List<E>
    override val size: Int
        get() = backingList.size

    override fun contains(element: E): Boolean = backingList.contains(element)

    override fun containsAll(elements: Collection<E>): Boolean = backingList.containsAll(elements)

    override fun get(index: Int): E = backingList.get(index)

    override fun indexOf(element: E): Int = backingList.indexOf(element)

    override fun isEmpty(): Boolean = backingList.isEmpty()

    override fun iterator(): Iterator<E> {
        // TODO: I believe this doesn't support removes.
        return generateSequence { backingList.asSequence() }.flatten().iterator()
    }

    override fun lastIndexOf(element: E): Int = backingList.lastIndexOf(element)

    override fun listIterator(): ListIterator<E> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun listIterator(index: Int): ListIterator<E> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun subList(fromIndex: Int, toIndex: Int): List<E> {
        if (fromIndex < 0 || fromIndex > size) {
            throw IndexOutOfBoundsException("fromIndex = $fromIndex")
        }
        if (toIndex < 0 || toIndex > size) {
            throw IndexOutOfBoundsException("toIndex = $toIndex")
        }

        if (fromIndex <= toIndex) {
            return backingList.subList(fromIndex, toIndex)
        } else {
            return backingList.subList(fromIndex, size) + backingList.subList(0, toIndex)
        }
    }
}

/**
 * Returns a new read-only circular list containing the given [elements].
 */
fun <T> circularListOf(vararg elements: T): CircularList<T> = MutableCircularList(elements.asList())

/**
 * Creates a new read-only circular list with the specified [size], where each element is calculated by calling the specified
 * [init] function. The [init] function returns a list element given its index.
 */
inline fun <T> CircularList(size: Int, init: (index: Int) -> T): CircularList<T> = MutableCircularList(size, init)