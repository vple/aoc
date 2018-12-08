package collections

import kotlin.coroutines.experimental.buildIterator

/**
 * A mutable circular list.
 */
class MutableCircularList<E> constructor(val _backingList: List<E>): CircularList<E>, MutableList<E> {
    override val backingList: MutableList<E> = _backingList.toMutableList()

    override fun iterator(): MutableIterator<E> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun add(element: E): Boolean = backingList.add(element)

    override fun add(index: Int, element: E) = backingList.add(index, element)

    override fun addAll(index: Int, elements: Collection<E>): Boolean = backingList.addAll(index, elements)

    override fun addAll(elements: Collection<E>): Boolean = backingList.addAll(elements)

    override fun clear() = backingList.clear()

    override fun listIterator(): MutableListIterator<E> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun listIterator(index: Int): MutableListIterator<E> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun remove(element: E): Boolean = backingList.remove(element)

    override fun removeAll(elements: Collection<E>): Boolean = backingList.removeAll(elements)

    override fun removeAt(index: Int): E = backingList.removeAt(index)

    override fun retainAll(elements: Collection<E>): Boolean = backingList.retainAll(elements)

    override fun set(index: Int, element: E): E = backingList.set(index, element)

    override fun subList(fromIndex: Int, toIndex: Int): MutableList<E> {
        if (fromIndex < 0 || fromIndex > size) {
            throw IndexOutOfBoundsException("fromIndex = $fromIndex")
        }
        if (toIndex < 0 || toIndex > size) {
            throw IndexOutOfBoundsException("toIndex = $toIndex")
        }

        if (fromIndex <= toIndex) {
            return backingList.subList(fromIndex, toIndex)
        } else {
            return (backingList.subList(fromIndex, size) + backingList.subList(0, toIndex)).toMutableList()
        }
    }

    override fun toString(): String = backingList.toString()
}

/**
 * Returns a new mutable circular list containing the given [elements].
 */
fun <T> mutableCircularListOf(vararg elements: T): CircularList<T> = MutableCircularList(elements.asList())

/**
 * Creates a new mutable circular list with the specified [size], where each element is calculated by calling the specified
 * [init] function. The [init] function returns a list element given its index.
 */
inline fun <T> MutableCircularList(size: Int, init: (index: Int) -> T): MutableCircularList<T> = MutableCircularList(List(size, init))