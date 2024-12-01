package y2024.day01

import utils.FileData
import utils.expectResult
import kotlin.math.abs

private val fileData = FileData(day = 1, year = 2024)

fun main() {
    expectResult(1) {
        part1(fileData.readTestData(1))
    }
    expectResult(1) {
        part2(fileData.readTestData(2))
    }

    val data = fileData.readData()

    println("#1 -> ${part1(data)}")
    println("#2 -> ${part2(data)}")
}

private fun part1(lines: List<String>): Int {
    val left = mutableListOf<Int>()
    val right = mutableListOf<Int>()
    lines.forEach { line ->
        val parts = line.split("   ")
        require(parts.size == 2) { "Invalid line: $line" }
        left.add(parts[0].toInt())
        right.add(parts[1].toInt())
    }
    left.sort()
    right.sort()
    require(left.size == right.size) { "Different size detected: $left - $right" }

    return left.indices
        .sumOf { abs(left[it] - right[it]) }
}

private fun part2(lines: List<String>): Int {
    return -1
}
