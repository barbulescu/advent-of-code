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

private fun part1(lines: List<String>): Long {
    return lines.sumOf { it.processLine1() }
}

private fun part2(lines: List<String>): Long {
    val fullLine = lines.joinToString(separator = "")
    val disabled = fullLine.split("don't()")
    println("------")
    println("1> ${disabled[0]}")
    return disabled
        .asSequence()
        .onEach { println("1> $it") }
        .mapIndexed {index, it->
            if (index == 0) {
                it
            } else {
                var value = it.substringAfter("do()")
                if (it == value) {
                    value = ""
                }
                value
            }
        }
        .onEach { println("2> $it") }
        .sumOf { it.processLine1() }
}

private val regex = "mul\\((\\d+),(\\d+)\\)".toRegex()

private fun String.processLine1(): Long = regex.findAll(this)
    .map { it.groupValues[1].toLong() * it.groupValues[2].toLong() }
    .sum()
