import utils.expectResult
import utils.readInput
import kotlin.math.min

private const val DAY = "Day04"

fun main() {
    // test if implementation meets criteria from the description, like:
    expectResult(13) {
        part1(readInput("${DAY}_1_test"))
    }
    expectResult(30) {
        part2(readInput("${DAY}_2_test"))
    }

    val data = readInput(DAY)
    println("#1 -> ${part1(data)}")
    println("#2 -> ${part2(data)}")
}

private fun part1(input: List<String>): Int {
    return input
        .map { it.parseLine() }
        .sumOf { it.calculatePoints() }
}

private fun part2(input: List<String>): Int {

    val cards = input
        .map { it.parseLine() }
        .toMutableList()

    cards.forEachIndexed { index, card ->
        val extraCards = card.winningCount()
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

data class Card(val index: Int, val winningNumbers: List<Int>, val numbers: List<Int>, var copies: Int = 1) {
    fun calculatePoints(): Int {
        val intersect: Set<Int> = winningNumbers.intersect(numbers)
        var points = 0
        repeat(intersect.size) {
            if (points == 0) {
                points = 1
            } else {
                points *= 2
            }
        }
        return points
    }

    fun winningCount() = winningNumbers.intersect(numbers).size
}