package aoc2017.day22

import grid.Coordinate
import grid.Direction

class Cluster(
    val infected: MutableMap<Coordinate, State>,
    var position: Coordinate = Coordinate(0, 0),
    var direction: Direction = Direction.NORTH
) {
    enum class State {
        CLEAN, WEAKENED, INFECTED, FLAGGED
    }

    val currentInfected
        get() = infected.getOrDefault(position, State.CLEAN)
    var burstInfections = 0

    fun burst(n: Int = 1, evolved: Boolean = false) = repeat(n) {
        turn(evolved)
        changeState(evolved)
        position += direction.coordinate
    }

    fun turn(evolved: Boolean) {
        if (!evolved) {
            direction = if (currentInfected == State.INFECTED) {
                direction.rotateRight()
            } else {
                direction.rotateLeft()
            }
            return
        } else {
            direction = when (currentInfected) {
                State.CLEAN -> direction.rotateLeft()
                State.WEAKENED -> direction
                State.INFECTED -> direction.rotateRight()
                State.FLAGGED -> direction.rotateRight(2)
            }
        }
    }

    fun changeState(evolved: Boolean) {
        if (!evolved) {
            if (currentInfected == State.CLEAN) {
                burstInfections++
                infected[position] = State.INFECTED
            } else {
                infected[position] = State.CLEAN
            }
        } else {
            infected[position] = when(currentInfected) {
                State.CLEAN -> State.WEAKENED
                State.WEAKENED -> {
                    burstInfections++
                    State.INFECTED
                }
                State.INFECTED -> State.FLAGGED
                State.FLAGGED -> State.CLEAN
            }
        }
    }

    companion object {
        fun parse(lines: List<String>): Cluster {
            val size = lines.size
            val offset = (size - 1) / 2

            // y increases when moving up
            val infectedNodes = lines.reversed().mapIndexed { y, s ->
                s.mapIndexedNotNull { x, c ->
                    if (c == '#') {
                        Coordinate(x - offset, y - offset)
                    } else {
                        null
                    }
                }
            }.flatten().associate { Pair(it, State.INFECTED) }

            return Cluster(infectedNodes.toMutableMap())
        }
    }
}