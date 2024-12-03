package y2024.day03

import utils.FileData
import utils.expectLongResult

private val fileData = FileData(day = 3, year = 2024)

fun main() {
    expectLongResult(161) {
        part1(fileData.readTestData(1))
    }
    expectLongResult(48) {
        part2(fileData.readTestData(2))
    }

    val data = fileData.readData()

    println("#1 -> ${part1(data)}")
    println("#2 -> ${part2(data)}")
}

private fun part1(lines: List<String>): Long = lines
    .sumOf(String::processOperations)

private fun part2(lines: List<String>): Long {
    val fullLine = lines.joinToString(separator = "")
    val parts = fullLine.split("don't()")
    val sequence = sequenceOf(parts[0]) + parts.asSequence()
        .drop(1)
        .map { it.substringAfter("do()", "") }
    return sequence.sumOf(String::processOperations)
}

private val regex = "mul\\((\\d+),(\\d+)\\)".toRegex()

private fun String.processOperations(): Long = regex
    .findAll(this)
    .map { it.groupValues[1].toLong() * it.groupValues[2].toLong() }
    .sum()
