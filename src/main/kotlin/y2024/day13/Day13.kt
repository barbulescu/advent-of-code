package y2024.day13

import utils.executeDay

fun main() {
    executeDay(List<String>::part1, List<String>::part2)
}

private fun List<String>.part1(): Long = this
    .chunked(4)
    .map { it.filterNot(String::isBlank) }
    .map { it.toMachine(prizeTranslation = 0) }
    .sumOf { it.solve() }

private fun List<String>.part2(): Long  = this
    .chunked(4)
    .map { it.filterNot(String::isBlank) }
    .map { it.toMachine(prizeTranslation = 10_000_000_000_000) }
    .sumOf { it.solve() }

private val lineRegex = "\\D+(\\d+)\\D+(\\d+)".toRegex()

private fun List<String>.toMachine(prizeTranslation: Long): Machine {
    require(this.size == 3) { "Each chunk must have 3 lines: $this" }
    val a = this[0].extractNumbers().run { Button(first, second) }
    val b = this[1].extractNumbers().run { Button(first, second) }
    val prize = this[2].extractNumbers().run { Point(first + prizeTranslation, second + prizeTranslation) }
    return Machine(a, b, prize)
}

private fun String.extractNumbers(): Pair<Long, Long> {
    val result = lineRegex.matchEntire(this) ?: error("Invalid string: $this")
    return result.groupValues[1].toLong() to result.groupValues[2].toLong()
}

private data class Machine(val a: Button, val b: Button, val prize: Point) {
    fun solve(): Long {
        val bPushes = (prize.y * a.x - prize.x * a.y) / (b.y * a.x - b.x * a.y)
        val aPushes = (prize.x - bPushes * b.x) / a.x

        val equation1 = a.x * aPushes + b.x * bPushes != prize.x
        val equation2 = a.y * aPushes + b.y * bPushes != prize.y

        return if (equation1 || equation2) {
            0
        } else {
            aPushes * 3 + bPushes
        }
    }
}

private data class Button(val x: Long, val y: Long)

private data class Point(val x: Long, val y: Long)
