package y2023.day08

import utils.FileData
import utils.expectLongResult
import utils.expectResult
import utils.leastCommonMultiple

private val fileData = FileData(day = 8, year = 2023)

fun main() {
    // test if implementation meets criteria from the description, like:
    expectResult(2) {
        part1(fileData.readTestData(1))
    }
    expectLongResult(6L) {
        part2(fileData.readTestData(2))
    }

    val data = fileData.readData()

    println("#1 -> ${part1(data)}")
    println("#2 -> ${part2(data)}")

    expectResult(17287) {
        part1(data)
    }
    expectLongResult(18625484023687) {
        part2(data)
    }

}

private fun part1(lines: List<String>): Int {
    val instructions = Instructions(lines.first())
    val map = parseInput(lines)

    var steps = 0
    var current = "AAA"

    while (current != "ZZZ") {
        current = instructions.next(map.pair(current))
        steps += 1
    }

    return steps
}

private fun part2(lines: List<String>): Long {
    val instructions = Instructions(lines.first())
    val map = parseInput(lines)
    return map.keys
        .filter { it.endsWith("A") }
        .map { startingPoint -> instructions.countSteps2(startingPoint, map) }
        .reduce { acc, i -> leastCommonMultiple(acc, i) }
}

private fun Map<String, Pair<String, String>>.pair(next: String): Pair<String, String> =
    this[next] ?: error("unable to find a value for $next")

private fun parseInput(input: List<String>) = input.drop(2)
    .associate {
        val key = it.substring(0, 3)
        val left = it.substring(7, 10)
        val right = it.substring(12, 15)
        key to (left to right)
    }


data class Instructions(private val steps: String) {
    private var currentPosition = 0

    fun next(pair: Pair<String, String>): String = nextStep().direction(pair)

    private fun nextStep(): Char {
        val value = steps.elementAt(currentPosition)
        currentPosition += 1
        if (currentPosition >= steps.length) {
            currentPosition = 0
        }
        return value
    }

    fun countSteps2(startingPoint: String, map: Map<String, Pair<String, String>>): Long {
        var current = startingPoint
        var count = 0L
        while (!current.endsWith("Z")) {
            repeat(steps.length) {
                current = next(map.pair(current))
            }
            count += steps.length
        }
        return count
    }

}

private fun Char.direction(pair: Pair<String, String>): String = when (this) {
    'L' -> pair.first
    'R' -> pair.second
    else -> error("Unknown instruction: $this")
}
