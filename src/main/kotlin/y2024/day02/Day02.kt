package y2024.day02

import utils.executeDay
import kotlin.math.abs
import kotlin.math.sign

fun main() {
    executeDay(List<String>::part1, List<String>::part2)
}

private fun List<String>.part1(): Long = map(String::toLevels)
    .count(List<Int>::isSafe)
    .toLong()

private fun List<String>.part2(): Long = map(String::toLevels)
    .count { levels ->
        levels.isSafe() || levels.indices
            .map { i -> levels.filterIndexed { index, _ -> index != i } }
            .any(List<Int>::isSafe)
    }
    .toLong()

private fun String.toLevels() = this
    .split(" ")
    .map(String::toInt)

private fun List<Int>.isSafe(): Boolean {
    val differences = this
        .zipWithNext()
        .map { it.first - it.second }

    val smoothTransitions = differences.asSequence()
        .map { abs(it) }
        .count { it !in 1..3 } == 0
    if (smoothTransitions) {
        return false
    }

    val directions = differences.asSequence()
        .map { it.sign }
        .groupingBy { it }
        .eachCount()
    return directions.size == 1
}
