package day04

import utils.FileData
import utils.expectResult
import kotlin.math.min
import kotlin.math.pow

private val fileData = FileData(4)

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

    expectResult(27454) {
        part1(data)
    }
    expectResult(6857330) {
        part2(data)
    }

}

private fun part1(input: List<String>): Int {
    return input
        .map(String::parseLine)
        .sumOf(Card::calculatePoints)
}

private fun part2(input: List<String>): Int {
    val cards = input
        .map(String::parseLine)
        .toList()

    cards.forEachIndexed { index, card ->
        val extraCards = card.guessedNumbers
        if (extraCards == 0) {
            return@forEachIndexed
        }
        val beginRange = min(index + 1, cards.size - 1)
        val endRange = min(index + extraCards, cards.size - 1)
        (beginRange..endRange).forEach { cardIndex ->
            cards[cardIndex].copies += card.copies
        }
    }

    return cards.sumOf { it.copies }
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