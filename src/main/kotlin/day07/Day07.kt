package day07

import utils.FileData
import utils.expectResult

private val fileData = FileData(7)

fun main() {
    // test if implementation meets criteria from the description, like:
    expectResult(6440) {
        part1(fileData.readTestData(1))
    }
    expectResult(5905) {
        part2(fileData.readTestData(2))
    }

    val data = fileData.readData()

    println("#1 -> ${part1(data)}")
    println("#2 -> ${part2(data)}")
}

val labelsPart1 = listOf('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2')
val labelsPart2 = listOf('A', 'K', 'Q', 'T', '9', '8', '7', '6', '5', '4', '3', '2', 'J')

private fun part1(lines: List<String>): Int = lines
    .map { it.parseLine(labelsPart1, Map<Char, Int>::calculatePowerPart1) }
    .sorted()
    .mapIndexed { index: Int, bid: Bid -> bid.amount * (index + 1) }
    .sum()

private fun part2(lines: List<String>): Int = lines
    .map { it.parseLine(labelsPart2, Map<Char, Int>::calculatePowerPart2) }
    .sorted()
    .mapIndexed { index: Int, bid: Bid -> bid.amount * (index + 1) }
    .sum()

fun String.parseLine(labels: List<Char>, powerCalculator: (Map<Char, Int>) -> Int) = Bid(
    hand = Hand(this.take(5), labels, powerCalculator),
    amount = this.drop(6).toInt()
)