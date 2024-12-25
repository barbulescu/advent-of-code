package y2024.day25

class Day25(private val input: List<String>) {
    fun part1(): Int {
        val locks = parseInput("#####")
        val keys = parseInput(".....")

        return locks.sumOf { lock ->
            keys.count { lock.hasNoOverlap(it) }
        }
    }

    private fun parseInput(firstLine: String): Set<List<Int>> = input
        .chunked(8)
        .filter { it.first() == firstLine }
        .map { it.toDepths() }
        .toSet()

    private fun List<String>.toDepths(): List<Int> {
        val data = this
            .dropLastWhile { it.isBlank() }
            .dropLast(1)
            .drop(1)
        return data[0]
            .indices
            .map { column -> data.count { it[column] == '#' } }
    }

    private fun List<Int>.hasNoOverlap(other: List<Int>): Boolean =
        this
            .mapIndexed { index, i -> i + other[index] }
            .all { it <= 5 }

}
