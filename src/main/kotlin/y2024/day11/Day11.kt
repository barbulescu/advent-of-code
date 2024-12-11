package y2024.day11

import utils.executeDay
import java.util.concurrent.ConcurrentHashMap

fun main() {
    executeDay(List<String>::part1, List<String>::part2)
}

private fun List<String>.part1(): Long = Stones(25)
    .blink(this[0])

private fun List<String>.part2(): Long = Stones(75)
    .blink(this[0])

private class Stones(val maxDepth: Int) {
    private val cache = ConcurrentHashMap<Pair<Int, Long>, Long>()

    fun blink(line: String) = line
        .split(" ")
        .asSequence()
        .map(String::toLong)
        .map { number -> number.blink(0) }
        .sum()


    private fun Long.blink(depth: Int): Long = cache.getOrPut(depth to this) {
        calculateBlinks(depth)
    }

    private fun Long.calculateBlinks(depth: Int): Long {
        if (depth == maxDepth) {
            return 1
        }
        val nextDepth = depth + 1
        if (this == 0L) {
            return 1L.blink(nextDepth)
        }

        val stringValue = this.toString()
        if (stringValue.length % 2 == 0) {
            val part1 = stringValue.firstHalf().blink(nextDepth)
            val part2 = stringValue.secondHalf().blink(nextDepth)
            return part1 + part2
        }

        return (this * 2024).blink(nextDepth)
    }

    private fun String.secondHalf() = substring(length / 2, length).toLong()
    private fun String.firstHalf() = substring(0, length / 2).toLong()

}
