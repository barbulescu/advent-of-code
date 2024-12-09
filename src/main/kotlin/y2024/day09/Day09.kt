package y2024.day09

import utils.executeDay

fun main() {
    executeDay(List<String>::part1, List<String>::part2)
}

private fun List<String>.part1(): Long {
    val initialMap = StringBuilder(this[0].toMap())

    for (l in initialMap.indices.reversed()) {
        if (initialMap[l] != Char.MAX_VALUE) {
            val i = initialMap.indexOf(Char.MAX_VALUE)
            initialMap[i] = initialMap[l]
            initialMap[l] = Char.MAX_VALUE
        }
        val a = initialMap.indexOfLast { it != Char.MAX_VALUE } + 1
        val b = initialMap.indexOf(Char.MAX_VALUE)
        if (a == b) {
            break
        }
    }

    return initialMap
        .takeWhile { it != Char.MAX_VALUE }
        .mapIndexed { index, c -> index.toLong() * c.code.toLong() }
        .sum()
}

private fun String.toMap(): String = toCharArray()
    .flatMapIndexed { i, c ->
        val lc = if (i % 2 == 0) {
            (i / 2).toChar()
        } else {
            Char.MAX_VALUE
        }
        List(c.digitToInt()) { lc }
    }.joinToString(separator = "")

private fun List<String>.part2(): Long {
    TODO("part 2 is not yet implemented")
}
