package aoc2017.day24

data class Bridge(val openPin: Int = 0, val components: List<Component> = listOf()) {
    val strength: Int by lazy {
        components.map { it.strength }.sum()
    }
    val length: Int = components.size

    fun connect(component: Component): Bridge {
        val nextPin = component.connect(openPin)
        return Bridge(nextPin, components + component)
    }

    fun compatibleComponents(components: List<Component>): List<Component> {
        return components.filter { it.matches(openPin) }
    }
}

data class Component(val pins1: Int, val pins2: Int) {
    val strength = pins1 + pins2

    fun matches(pins: Int) : Boolean {
        return pins == pins1 || pins == pins2
    }

    fun connect(pins: Int): Int {
        return if (pins == pins1) {
            pins2
        } else if (pins == pins2) {
            pins1
        } else {
            throw IllegalArgumentException()
        }
    }

    companion object {
        val REGEX = Regex("(\\d+)/(\\d+)")
        fun parse(text: String): Component {
            val (_, pin1, pin2) = REGEX.matchEntire(text)!!.groupValues
            return Component(pin1.toInt(), pin2.toInt())
        }
    }
}
