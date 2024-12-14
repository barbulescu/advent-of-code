package y2024.day04

import utils.executeDay

fun main() {
    executeDay(List<String>::part1, List<String>::part2)
}

private const val x = 88.toByte()
private const val m = 77.toByte()
private const val a = 65.toByte()
private const val s = 83.toByte()

private fun List<String>.part1(): Long {
    val xmas = listOf(x, m, a, s)
    val samx = listOf(s, a, m, x)

    val data = map { it.toByteArray().toList() }
        .toList()

    fun List<List<Byte>>.extract(rows: List<Int>, cols: List<Int>): List<Byte> {
        require(rows.size == cols.size)
        return rows.indices.map { this[rows[it]][cols[it]] }
    }

    fun count(col: List<Int>, transformation: (List<Int>) -> List<Int> = { it }) = data.indices
        .asSequence()
        .windowed(4)
        .flatMap {
            sequenceOf(
                data.extract(col, it),
                data.extract(it, transformation(col))
            )
        }
        .count { it == xmas || it == samx }

    val count = data.indices
        .map { listOf(it, it, it, it) }
        .sumOf { col -> count(col) }
    val diagonalCount = data.indices
        .windowed(4)
        .sumOf { row -> count(row, List<Int>::reversed) }
    return (count + diagonalCount).toLong()
}

private fun List<String>.part2(): Long {
    val mas = listOf(m, a, s)
    val sam = listOf(s, a, m)

    val data = map { it.toByteArray().toList() }
        .toList()

    val expectedValues = listOf(sam, mas)
    return data.indices
        .windowed(3)
        .sumOf { row ->
            data.indices
                .windowed(3)
                .count { col ->
                    val diagonal1 = listOf(data[row[0]][col[0]], data[row[1]][col[1]], data[row[2]][col[2]])
                    val diagonal2 = listOf(data[row[0]][col[2]], data[row[1]][col[1]], data[row[2]][col[0]])

                    diagonal1 in expectedValues && diagonal2 in expectedValues
                }
        }
        .toLong()
}
