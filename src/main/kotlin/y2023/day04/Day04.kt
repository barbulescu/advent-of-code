package y2023.day04

import utils.FileData
import utils.expectResult
import kotlin.math.pow

private val fileData = FileData(day = 4, year = 2023)

fun main() {
    // test if implementation meets criteria from the description, like:
    expectResult(13) {
        part1(fileData.readTestData(1))
    }
    expectResult(30) {
        part2(fileData.readTestData(2))
    }

    val data = fileData.readData()

    println("#1 -> ${part1(data)}")
    println("#2 -> ${part2(data)}")
}

private fun part1(lines: List<String>): Int {
    return lines
        .map(String::parseLine)
        .sumOf(Card::calculatePoints)
}

private fun part2(lines: List<String>): Int {
    val cards: List<Card> = lines
        .map(String::parseLine)
        .toList()

    return cards
        .onEachIndexed { index, card ->
            cards.subList(index + 1, index + card.guessedNumbers + 1)
                .forEach { it.copies += card.copies }
        }
        .sumOf { it.copies }
}

private val sectionsRegex = Regex("[:|]")

private fun String.parseLine(): Card {
    val sections = this.split(sectionsRegex)
    require(sections.size == 3) { "expecting 3 sections for each line" }
    return Card(
        sections[0].drop(4).trim().toInt(),
        sections[1].parseNumbers(),
        sections[2].parseNumbers()
    )
}

private fun String.parseNumbers() = this.split(" ")
    .filterNot { it.isBlank() }
    .map { it.toInt() }

internal data class Card(val index: Int, val winningNumbers: List<Int>, val numbers: List<Int>, var copies: Int = 1) {
    val guessedNumbers = winningNumbers.intersect(numbers).size

    fun calculatePoints(): Int =
        2.0.pow(guessedNumbers - 1).toInt()

}
