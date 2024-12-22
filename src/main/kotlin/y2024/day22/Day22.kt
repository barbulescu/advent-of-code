package y2024.day22

import utils.lastDigit

class Day22(input: List<String>) {
    val data = input.map(String::toLong)

    fun part1(): Long = data.sumOf { it.generateSecrets().last() }
    fun part2(): Long {
        val map = buildMap {
            data.forEach { initial ->
                val secretDigits = initial
                    .generateSecrets()
                    .map(Long::lastDigit)
                val differences = secretDigits
                    .zipWithNext()
                    .map { it.second - it.first }

                val computed = mutableSetOf<String>()
                secretDigits
                    .dropLast(4)
                    .indices
                    .map { i -> i to differences.sliceAt(i) }
                    .forEach { (i, seq) ->
                        if (seq !in computed) {
                            compute(seq) { _, current -> secretDigits[i + 4] + (current ?: 0L) }
                            computed += seq
                        }
                    }
            }
        }
        return map
            .values
            .max()
    }

    private fun List<Long>.sliceAt(index: Int) = slice(index..index + 3)
        .joinToString()

    private fun Long.generateSecrets() = generateSequence(this) { it.toNext() }
        .take(2001)
        .toList()

    private fun Long.toNext(): Long = this
        .calculate { this * 64 }
        .calculate { this / 32 }
        .calculate { this * 2048 }

    private fun Long.calculate(operation: Long.() -> Long) = (this xor operation(this)).prune()

    private fun Long.prune(): Long = this % 16777216
}
