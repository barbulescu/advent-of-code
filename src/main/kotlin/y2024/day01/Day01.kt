package y2024.day01

import utils.executeDay
import kotlin.math.abs

fun main() {
    executeDay(List<String>::part1, List<String>::part2)
}

private fun List<String>.part1(): Long {
    val (left, right) = parseLists(this)
    val sortedLeft = left.sorted()
    val sortedRight = right.sorted()

    return sortedLeft
        .indices
        .sumOf { abs(sortedLeft[it] - sortedRight[it]) }
        .toLong()
}

private fun List<String>.part2(): Long {
    val (left, right) = parseLists(this)
    val rightCounts = right
        .groupingBy { it }
        .eachCount()
    return left
        .sumOf { it * (rightCounts[it] ?: 0) }
        .toLong()
}

private fun parseLists(lines: List<String>): Pair<List<Int>, List<Int>> = lines
    .asSequence()
    .map { line -> line.split("   ").map(String::toInt) }
    .map { line -> line[0] to line[1] }
    .unzip()
