package day01

import readInput
import expectResult

fun main() {
    // test if implementation meets criteria from the description, like:
    expectResult(142) {
        part1(readInput("Day01_1_test"))
    }
    expectResult(281) {
        part2(readInput("Day01_2_test"))
    }

    val data = readInput("Day01")
    println("#1 -> ${part1(data)}")
    println("#2 -> ${part2(data)}")
}

private fun processLine(line: String) = line.toCharArray()
    .asSequence()
    .filter(Char::isDigit)
    .map(Char::digitToInt)

private val mapping = mapOf(
    "one" to "1ne",
    "two" to "2wo",
    "three" to "3hree",
    "four" to "4our",
    "five" to "5ive",
    "six" to "6ix",
    "seven" to "7even",
    "eight" to "8ight",
    "nine" to "9ine"
)

private fun preProcessLine(line: String): String {
    var current = line
    while (true) {
        current.indices.forEach c@{ index ->
            mapping.forEach { pair ->
                if (current.startsWith(pair.key, index)) {
                    current = current.replaceFirst(pair.key, pair.value)
                    return@c
                }
            }
        }
        break
    }

    return current
}

private fun part1(input: List<String>): Int {
    return input
        .asSequence()
        .map { processLine(it) }
        .map { "${it.first()}${it.last()}" }
        .map(String::toInt)
        .sum()
}

private fun part2(input: List<String>): Int {
    return input
        .asSequence()
        .map { preProcessLine(it) }
        .map { processLine(it) }
        .map { "${it.first()}${it.last()}" }
        .map(String::toInt)
        .sum()
}
