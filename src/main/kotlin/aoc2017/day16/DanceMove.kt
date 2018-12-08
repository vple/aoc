package aoc2017.day16

sealed class DanceMove {
    abstract fun apply(order: String): String

    companion object {
        fun parse(text: String): DanceMove {
            if (text[0] == 's') {
                return Spin(text.substring(1).toInt())
            }
            val (first, second) = text.substring(1).split("/")
            if (text[0] == 'x') {
                return Exchange(first.toInt(), second.toInt())
            } else {
                return Partner(first.first(), second.first())
            }
        }
    }
}

data class Spin(val n: Int) : DanceMove() {
    override fun apply(order: String): String {
        return order.takeLast(n) + order.take(order.length - n)
    }
}

data class Exchange(val position1: Int, val position2: Int) : DanceMove() {
    override fun apply(order: String): String {
        val letters = order.toCharArray()
        letters[position1] = order[position2]
        letters[position2] = order[position1]
        return letters.joinToString("")
    }
}

data class Partner(val program1: Char, val program2: Char) : DanceMove() {
    override fun apply(order: String): String {
        val letters =
            order.toCharArray().map {
                when (it) {
                    program1 -> program2
                    program2 -> program1
                    else -> it
                }
            }
        return letters.joinToString("")
    }
}