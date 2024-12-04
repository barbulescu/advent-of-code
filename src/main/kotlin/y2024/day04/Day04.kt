package y2024.day04

import utils.FileData
import utils.expectResult

private val fileData = FileData(day = 4, year = 2024)

fun main() {
    expectResult(18) {
        part1(fileData.readTestData(1))
    }
    expectResult(9) {
        part2(fileData.readTestData(2))
    }

    val data = fileData.readData()

    println("#1 -> ${part1(data)}")
    println("#2 -> ${part2(data)}")
}

const val x = 88.toByte()
const val m = 77.toByte()
const val a = 65.toByte()
const val s = 83.toByte()

private fun part1(lines: List<String>): Int {
    val xmas = listOf(x, m, a, s)
    val samx = listOf(s, a, m, x)

    val data = lines.map { it.toByteArray().toList() }
        .toList()

    fun List<List<Byte>>.extract(rows: List<Int>, cols: List<Int>): List<Byte> {
        require(rows.size == cols.size)
        return rows.indices.map {
            this[rows[it]][cols[it]]
        }
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
    return count + diagonalCount
}

private fun part2(lines: List<String>): Int {
    val mas = listOf(m, a, s)
    val sam = listOf(s, a, m)

    val data = lines.map { it.toByteArray().toList() }
        .toList()

    val indices = data.indices
    var count = 0
    val expectedValues = listOf(sam, mas)
    indices.windowed(3).forEach { row ->
        indices.windowed(3).forEach { col ->
            val s1 = listOf(data[row[0]][col[0]], data[row[1]][col[1]], data[row[2]][col[2]])
            val s2 = listOf(data[row[0]][col[2]], data[row[1]][col[1]], data[row[2]][col[0]])

            if (s1 in expectedValues && s2 in expectedValues) {
                count++
            }
        }
    }
    return count
}
