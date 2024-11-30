package y2023.day09

import utils.FileData
import utils.expectLongResult

private val fileData = FileData(day = 9, year = 2023)

fun main() {
    // test if implementation meets criteria from the description, like:
    expectLongResult(114) {
        part1(fileData.readTestData(1))
    }
    expectLongResult(2) {
        part2(fileData.readTestData(2))
    }

    val data = fileData.readData()

    println("#1 -> ${part1(data)}")
    println("#2 -> ${part2(data)}")
}

private fun part1(lines: List<String>): Long = lines
    .asSequence()
    .map(String::parseLine)
    .sumOf(List<Long>::calculateNext)

private fun part2(lines: List<String>): Long = lines
    .asSequence()
    .map(String::parseLine)
    .map { it.reversed() }
    .sumOf(List<Long>::calculateNext)

private fun String.parseLine(): List<Long> = this
    .split(" ")
    .map(String::toLong)

private fun List<Long>.allZero() = this.all { it == 0L }

private fun List<Long>.calculateNext() : Long {
    val lastValues = mutableListOf(this.last())
    var current = this
    while(!current.allZero()) {
        current = current.windowed(size = 2, partialWindows = false)
            .map { it[1] - it[0] }
        lastValues.add(current.last())
    }
    return lastValues.reversed().reduce { acc, l -> acc + l}
}
