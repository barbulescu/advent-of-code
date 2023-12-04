package day01

import utils.FileData
import utils.expectResult

private val fileData = FileData(1)

fun main() {
    // test if implementation meets criteria from the description, like:
    expectResult(142) {
        part1(fileData.readTestData(1))
    }
    expectResult(281) {
        part2(fileData.readTestData(2))
    }

    val data = fileData.readData()

    println("#1 -> ${part1(data)}")
    println("#2 -> ${part2(data)}")
}

private fun processLine(line: String) = line.toCharArray()
    .asSequence()
    .filter(Char::isDigit)
    .map(Char::digitToInt)

private fun preProcessLine(line: String): String = line
    .replace("one", "o1e")
    .replace("two", "t2o")
    .replace("three", "t3ree")
    .replace("four", "f4ur")
    .replace("five", "f5ve")
    .replace("six", "s6x")
    .replace("seven", "s7ven")
    .replace("eight", "e8ght")
    .replace("nine", "n9ne")

private fun part1(input: List<String>): Int = input.convertAndSum { it }

private fun part2(input: List<String>): Int = input.convertAndSum { preProcessLine(it) }

private fun List<String>.convertAndSum(preProcessor: (String) -> String): Int = this
    .asSequence()
    .map(preProcessor)
    .map { processLine(it) }
    .map { "${it.first()}${it.last()}" }
    .map(String::toInt)
    .sum()
