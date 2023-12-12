package day12

import utils.FileData
import utils.expectLongResult
import utils.expectResult

private val fileData = FileData(12)

fun main() {
    // test if implementation meets criteria from the description, like:
    expectResult(21) {
        part1(fileData.readTestData(1))
    }
    expectLongResult(525152) {
        part2(fileData.readTestData(2))
    }

    val data = fileData.readData()

    println("#1 -> ${part1(data)}")
    println("#2 -> ${part2(data)}")
}

private fun String.symbols() = this.substringBefore(" ")
private fun String.amounts() = this.substringAfter(" ")
    .split(',')
    .map(String::toInt)


private fun part1(lines: List<String>): Int = lines.sumOf(String::processLine)

private fun String.processLine(): Int {
    val symbols = symbols()
    val amounts = amounts()
    val incomplete = mutableListOf(symbols)
    val complete = mutableListOf<String>()
    while (incomplete.isNotEmpty()) {
        incomplete
            .removeFirst()
            .toOptions()
            .forEach { option ->
                if ('?' in option) {
                    incomplete += option
                } else if (option.toAmounts() == amounts) {
                    complete += option
                }
            }
    }
    return complete.size
}

private fun String.toOptions() = listOf(replaceFirst('?', '.'), replaceFirst('?', '#'))

private fun String.toAmounts() = split('.')
    .filter(String::isNotEmpty)
    .map(String::length)

private fun part2(lines: List<String>): Long = lines
    .asSequence()
    .map(::LineProcessor)
    .sumOf(LineProcessor::processLine)

private data class Key(val index: Int, val groupIndex: Int, val partialCount: Int)

class LineProcessor(line: String) {
    private val cache = mutableMapOf<Key, Long>()
    private val symbols = List(5) { line.symbols() }.joinToString("?")
    private val amounts = List(5) { line.amounts() }.flatten()

    fun processLine(index: Int = 0, groupIndex: Int = 0, partialCount: Int = 0): Long {
        val key = Key(index, groupIndex, partialCount)
        cache[key]?.let { return it }

        if (partialCount == 0 && index > symbols.lastIndex) {
            return if (groupIndex == amounts.size) 1 else 0
        }

        val arrangements = calculate1(index, partialCount, groupIndex) + calculate2(index, partialCount, groupIndex)
        cache[key] = arrangements
        return arrangements
    }

    private fun calculate1(index: Int, partialCount: Int, groupIndex: Int): Long {
        if (symbols.elementAtOrNull(index) == '#') return 0

        return when (partialCount) {
            0 -> processLine(index + 1, groupIndex, 0)
            amounts.getOrNull(groupIndex) -> processLine(index + 1, groupIndex + 1, 0)
            else -> 0
        }
    }

    private fun calculate2(index: Int, partialCount: Int, groupIndex: Int): Long =
        if (symbols.elementAtOrElse(index) { '.' } != '.') {
            processLine(index + 1, groupIndex, partialCount + 1)
        } else {
            0
        }
}


