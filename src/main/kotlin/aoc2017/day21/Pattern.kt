package aoc2017.day21

data class Pattern(val pattern: List<List<Char>>) {
    val size = pattern.size

    fun flipHorizontal(): Pattern {
        return Pattern(pattern.map { it.reversed() })
    }

    fun flipVertical(): Pattern {
        return Pattern(pattern.reversed())
    }

    fun rotateClockwise(): Pattern {
        val newPattern = MutableList(size) { MutableList(size) { ' ' } }
        for (x in 0 until size) {
            for (y in 0 until size) {
                newPattern[x][y] = pattern[size - y - 1][x]
            }
        }
        return Pattern(newPattern)
    }

    fun allTransforms(): Set<Pattern> {
        val transforms = mutableListOf<Pattern>()
        var pattern = this
        repeat(4) {
            transforms.add(pattern)
            pattern = pattern.rotateClockwise()
        }
        return transforms.map { setOf(it, it.flipHorizontal(), it.flipVertical()) }.flatten().toSet()
    }

    override fun toString(): String {
        return pattern.map { it.joinToString("") }.joinToString("\n")
    }

    companion object {
        fun parse(text: String): Pattern {
            return from(text.split("/"))
        }

        fun from(lines: List<String>): Pattern {
            return Pattern(lines.map { it.toList() })
        }
    }
}

data class EnhancementRule(val basePattern: Pattern, val outputPattern: Pattern) {
    val matchingPatterns: Set<Pattern> by lazy { basePattern.allTransforms() }

    fun matches(pattern: Pattern) = matchingPatterns.contains(pattern)

    companion object {
        fun parse(text: String): EnhancementRule {
            val (input, output) = text.split(" => ")
            return EnhancementRule(Pattern.parse(input), Pattern.parse(output))
        }
    }
}

data class EnhancementBook(val rules: List<EnhancementRule>) {
    val map: Map<Pattern, Pattern> =
        rules.map { rule ->
            rule.matchingPatterns.map { Pair(it, rule.outputPattern) }
        }.flatten().toMap()
}