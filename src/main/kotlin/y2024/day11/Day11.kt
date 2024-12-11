package y2024.day11

import utils.executeDay
import kotlin.math.abs
import kotlin.math.log10

fun main() {
    executeDay(List<String>::part1, List<String>::part2)
}

private fun List<String>.part1(): Long {
    var stones = this[0].toNumbers()
    repeat(25) { stones = stones.blink() }
    return stones.size.toLong()
}

fun String.toNumbers() = this
    .split(" ")
    .map(String::toLong)
    .toList()

fun List<Long>.blink(): List<Long> = this.asSequence()
    .flatMap(Long::blink)
    .toList()

fun Long.blink(): Sequence<Long> =
    when {
        this == 0L -> sequenceOf(1)
        this.countDigits() % 2 == 0L -> {
            val string = this.toString()
            val part1 = string.substring(0, string.length / 2).toLong()
            val part2 = string.substring(string.length / 2, string.length).toLong()
            sequenceOf(part1, part2)
        }
        else -> sequenceOf(this * 2024)
    }

private fun Long.countDigits() = when (this) {
    0L -> 1L
    else -> log10(abs(toDouble())).toLong() + 1
}

private fun List<String>.part2(): Long {
    var stones = this[0].toNumbers()
    repeat(75) {
        stones = stones.blink()
        println(it)
    }
    return stones.size.toLong()
}
