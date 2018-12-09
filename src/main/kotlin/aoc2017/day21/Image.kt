package aoc2017.day21

data class Image(val lines: List<String> = STARTING_PATTERN) {
    val size = lines.size
    val numPixelsOn: Int by lazy {
        lines.map { it.count { it == '#' } }.sum()
    }

    fun iterate(enhancementBook: EnhancementBook): Image {
        val patterns = if (size % 2 == 0) {
            patternSplit(2)
        } else {
            patternSplit(3)
        }
        val outputs = patterns.map { it.map { enhancementBook.map[it]!! } }
        val lines = combinePatterns(outputs)
        return Image(lines)
    }

    fun patternSplit(n: Int): List<List<Pattern>> {
        val patterns = mutableListOf<List<Pattern>>()
        for (yFactor in 0 until size/n) {
            val patternRow = mutableListOf<Pattern>()
            patterns.add(patternRow)
            val rows = lines.subList(yFactor * n, (yFactor + 1) * n)
            for (xFactor in 0 until size/n) {
                val patternLines = rows.map { it.substring(xFactor * n until (xFactor + 1) * n) }
                patternRow.add(Pattern.from(patternLines))
            }
        }
        return patterns
    }

    fun combinePatterns(patterns: List<List<Pattern>>): List<String> {
        val patternSize = patterns[0][0].size
        val lines = mutableListOf<String>()
        for (patternRow in patterns) {
            repeat(patternSize) { index ->
                lines.add(patternRow.map { it.pattern[index] }.flatten().joinToString(""))
            }
        }
        return lines
    }

    override fun toString(): String {
        return lines.joinToString("\n")
    }

    companion object {
        val STARTING_PATTERN = listOf(".#.", "..#", "###")
    }
}