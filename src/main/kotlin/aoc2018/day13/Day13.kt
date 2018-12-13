package aoc2018.day13

import grid.Coordinate
import grid.Direction
import util.loadResourceLines

/**
 * [Advent of Code 2018 Day 13](https://adventofcode.com/2018/day/13)
 */

const val INPUT_FILE = "/aoc2018/day13/input.txt"

fun main(args: Array<String>) {
    val lines = loadResourceLines(INPUT_FILE)
    val carts = parseCarts(lines)
    val track = removeCarts(lines)

    val initialState = State(track, carts)

    println("Part 1")
    var state = initialState
    while (state.collisions.isEmpty()) {
        state = state.tick()
    }
    println(state.collisions)

    println("Part 2")
    state = initialState
    while (state.carts.size != 1) {
        state = state.tick()
    }
    println(state.carts[0])
}

data class State(val track: List<String>, val carts: List<Cart>, val ticks: Int = 0, val collisions: List<Cart> = listOf()) {
    val cartPositions = carts.associateBy { it.position }
    val orderedCarts: List<Cart> by lazy {
        carts.sortedWith(
            Comparator { o1: Cart, o2: Cart ->
                if (o1.position.y != o2.position.y) {
                    o1.position.y - o2.position.y
                } else {
                    o1.position.x - o2.position.x
                }
            }
        )
    }

    fun tick(): State {
        val cartsByPosition = cartPositions.toMutableMap()
        val orderedPositions = orderedCarts.map { it.position }
        val collisions = mutableListOf<Cart>()

        for (position in orderedPositions) {
            val cart = cartsByPosition.remove(position)
            if (cart == null) {
                // This cart has already collided.
                continue
            }

            var nextCart = cart.advance()
            val coordinate = nextCart.position
            val trackChar = track[coordinate.y][coordinate.x]
            nextCart = nextCart.followTrack(trackChar)

            if (cartsByPosition.containsKey(coordinate)) {
                // Collision!
                collisions.add(nextCart)
                collisions.add(cartsByPosition.remove(coordinate)!!)
                continue
            }

            cartsByPosition[coordinate] = nextCart
        }

        return State(track, cartsByPosition.values.toList(), ticks+1, collisions)
    }
}

fun parseCarts(lines: List<String>): List<Cart> {
    val carts = mutableListOf<Cart>()
    lines.forEachIndexed { y, s ->
        s.forEachIndexed { x, c ->
            val coordinate = Coordinate(x, y)
            val cart = when (c) {
                '^' -> Cart(coordinate, Direction.NORTH)
                '>' -> Cart(coordinate, Direction.EAST)
                'v' -> Cart(coordinate, Direction.SOUTH)
                '<' -> Cart(coordinate, Direction.WEST)
                else -> null
            }
            cart?.let { carts.add(it) }
        }
    }
    return carts
}

fun removeCarts(lines: List<String>): List<String> {
    return lines.map { s ->
        s.map {
            when (it) {
                '^' -> '|'
                '>' -> '-'
                'v' -> '|'
                '<' -> '-'
                else -> it
            }
        }.joinToString("")
    }
}

data class Cart(val position: Coordinate, val direction: Direction, val turns: Int = 0) {
    fun advance(): Cart {
        return Cart(position + direction.coordinate, direction, turns)
    }

    fun followTrack(track: Char): Cart {
        return when (track) {
            '+' -> intersection()
            '/', '\\' -> curve(track)
            else -> this
        }
    }

    fun intersection(): Cart {
        val newDirection = when (turns % 3) {
            0 -> direction.rotateLeft()
            1 -> direction
            2 -> direction.rotateRight()
            else -> throw IllegalStateException()
        }
        return Cart(position, newDirection, turns+1)
    }

    fun curve(curve: Char): Cart {
        val newDirection = when (curve) {
            '/' -> {
                when (direction) {
                    Direction.NORTH -> Direction.EAST
                    Direction.EAST -> Direction.NORTH
                    Direction.SOUTH -> Direction.WEST
                    Direction.WEST -> Direction.SOUTH
                }
            }
            '\\' -> {
                when (direction) {
                    Direction.NORTH -> Direction.WEST
                    Direction.EAST -> Direction.SOUTH
                    Direction.SOUTH -> Direction.EAST
                    Direction.WEST -> Direction.NORTH
                }
            }
            else -> throw IllegalArgumentException()
        }
        return Cart(position, newDirection, turns)
    }
}

