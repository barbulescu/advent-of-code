package y2024.day02

import utils.FileData
import utils.expectResult
import kotlin.math.abs
import kotlin.math.sign

private val fileData = FileData(day = 2, year = 2024)

fun main() {
    expectResult(2) {
        part1(fileData.readTestData(1))
    }
    expectResult(4) {
        part2(fileData.readTestData(2))
    }

    val data = fileData.readData()

    println("#1 -> ${part1(data)}")
    println("#2 -> ${part2(data)}")
}

private fun part1(lines: List<String>): Int = lines
    .map(String::toLevels)
    .count(List<Int>::isSafe)

private fun part2(lines: List<String>): Int = lines
    .map(String::toLevels)
    .count { levels ->
        levels.isSafe() || levels.indices
            .map { i -> levels.filterIndexed { index, _ -> index != i } }
            .any(List<Int>::isSafe)
    }

private fun String.toLevels() = this
    .split(" ")
    .map(String::toInt)

private fun List<Int>.isSafe(): Boolean {
    val differences = this
        .windowed(2)
        .map { it[0] - it[1] }

    val smoothTransitions = differences.asSequence()
        .map { abs(it) }
        .count { it !in 1..3 } == 0
    if (smoothTransitions) {
        return false
    }

    val directions = differences.asSequence()
        .map { it.sign }
        .groupingBy { it }
        .eachCount()
    return directions.size == 1
}
