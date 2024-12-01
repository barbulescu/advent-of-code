package y2024.day01

import utils.FileData
import utils.expectResult
import kotlin.math.abs

private val fileData = FileData(day = 1, year = 2024)

fun main() {
    expectResult(11) {
        part1(fileData.readTestData(1))
    }
    expectResult(31) {
        part2(fileData.readTestData(2))
    }

    val data = fileData.readData()

    println("#1 -> ${part1(data)}")
    println("#2 -> ${part2(data)}")
}

private fun part1(lines: List<String>): Int {
    val (left, right) = parseLists(lines)
    left.sort()
    right.sort()

    return left.indices
        .sumOf { abs(left[it] - right[it]) }
}

private fun part2(lines: List<String>): Int {
    val (left, right) = parseLists(lines)
    val rightCounts = right.groupingBy{ it }.eachCount()
    return left.sumOf {
        val count = rightCounts[it] ?: 0
        it * count
    }
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
