package y2024.day03

import utils.executeDay

fun main() {
    executeDay(List<String>::part1, List<String>::part2)
}

private fun List<String>.part1(): Long = sumOf(String::processOperations)

private fun List<String>.part2(): Long {
    val fullLine = joinToString(separator = "")
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
