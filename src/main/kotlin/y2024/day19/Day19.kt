package y2024.day19

class Day19(input: List<String>) {
    private val cache: MutableMap<String, Long> = mutableMapOf()
    private val patterns: List<String> = input
        .first()
        .split(", ")
    private val designs: List<String> = input.drop(2)

    fun part1(): Int =
        designs.count { it.countOptions() > 0 }

    fun part2(): Long =
        designs.sumOf { it.countOptions() }

    private fun String.countOptions(): Long =
        if (isEmpty()) {
            1
        } else {
            cache.getOrPut(this) {
                patterns
                    .filter { startsWith(it) }
                    .map { removePrefix(it) }
                    .sumOf { it.countOptions() }
            }
        }
}
