package y2024.day04

import utils.FileData
import utils.expectLongResult

private val fileData = FileData(day = 4, year = 2024)

fun main() {
    expectLongResult(-1) {
        part1(fileData.readTestData(1))
    }
    expectLongResult(-1) {
        part2(fileData.readTestData(2))
    }

    val data = fileData.readData()

    println("#1 -> ${part1(data)}")
    println("#2 -> ${part2(data)}")
}

private fun part1(lines: List<String>): Long {
    return -1
}

private fun part2(lines: List<String>): Long {
    return -1
}
