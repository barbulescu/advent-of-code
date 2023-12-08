package day09

import utils.FileData
import utils.expectResult

private val fileData = FileData(9)

fun main() {
    // test if implementation meets criteria from the description, like:
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

private fun part1(input: List<String>): Int {
    return -1
}

private fun part2(input: List<String>): Int {
    return -1
}