package y2024.day01

import utils.executeDay
import kotlin.math.abs

fun main() {
    executeDay(List<String>::part1, List<String>::part2)
}

private fun List<String>.part1(): Long {
    val (left, right) = parseLists(this)
    left.sort()
    right.sort()

    return left.indices
        .sumOf { abs(left[it] - right[it]) }
        .toLong()
}

private fun List<String>.part2(): Long {
    val (left, right) = parseLists(this)
    val rightCounts = right.groupingBy { it }.eachCount()
    return left
        .sumOf {
            val count = rightCounts[it] ?: 0
            it * count
        }
        .toLong()
}

private fun parseLists(lines: List<String>): Pair<MutableList<Int>, MutableList<Int>> {
    val left = mutableListOf<Int>()
    val right = mutableListOf<Int>()
    lines.forEach { line ->
        val parts = line.split("   ")
        require(parts.size == 2) { "Invalid line: $line" }
        left.add(parts[0].toInt())
        right.add(parts[1].toInt())
    }
    require(left.size == right.size) { "Different size detected: $left - $right" }
    return Pair(left, right)
}
