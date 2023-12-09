package day09

import utils.FileData
import utils.expectLongResult
import java.awt.Color.red

private val fileData = FileData(9)

fun main() {
    // test if implementation meets criteria from the description, like:
    expectLongResult(114) {
        part1(fileData.readTestData(1))
    }
//    expectResult(1) {
//        part2(fileData.readTestData(2))
//    }

    val data = fileData.readData()

    println("#1 -> ${part1(data)}")
//    println("#2 -> ${part2(data)}")
}

private fun part1(input: List<String>): Long {
    return input
        .asSequence()
        .map(String::parseLine)
//        .filter(List<Long>::allZero)
        .sumOf { calculateNext(it) }

}

private fun part2(input: List<String>): Long {
    return -1
}

private fun String.parseLine(): List<Long> = this
    .split(" ")
    .map(String::toLong)

private fun List<Long>.allZero() = this.all { it == 0L }

private fun calculateNext(numbers: List<Long>) : Long {
    val lastValues = mutableListOf(numbers.last())
    var temp = numbers
    while(!temp.allZero()) {
        temp = temp.windowed(size = 2, partialWindows = false)
            .map { it[1] - it[0] }
        lastValues.add(temp.last())
    }
    return lastValues.reversed().reduce { acc, l -> acc + l}
}
