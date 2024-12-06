package y2024.day07

import utils.executeDay
import y2024.day06.Orientation.*
import java.util.*

fun main() {
    executeDay(List<String>::part1, List<String>::part2)
}

private fun List<String>.part1(): Long = this.toEquations()
    .filter { (result, numbers) ->
        process(
            numbers.drop(1),
            result,
            numbers.first().toLong(),
            addition,
            multiplication
        )
    }
    .sumOf { it.first }

private fun List<String>.part2(): Long = this.toEquations()
    .filter { (result, numbers) ->
        process(
            numbers.drop(1),
            result,
            numbers.first().toLong(),
            addition,
            multiplication,
            concatenation
        )
    }
    .sumOf { it.first }

fun List<String>.toEquations(): List<Pair<Long, List<Int>>> = map { line ->
    val result = line.substringBefore(":")
        .toLong()
    val numbers = line.substringAfter(":")
        .trim()
        .split(" ")
        .map(String::toInt)
    result to numbers
}

fun process(numbers: List<Int>, result: Long, currentValue: Long, vararg operations: (Long, Long) -> Long): Boolean {
    if (numbers.isEmpty()) {
        return currentValue == result
    }

    return operations.any {
        process(
            numbers = numbers.drop(1),
            result = result,
            currentValue = it(currentValue, numbers.first().toLong()),
            operations = operations
        )
    }
}

val addition: (Long, Long) -> Long = { a: Long, b: Long -> a + b }
val multiplication: (Long, Long) -> Long = { a: Long, b: Long -> a * b }
val concatenation: (Long, Long) -> Long = { a: Long, b: Long -> "$a$b".toLong() }
