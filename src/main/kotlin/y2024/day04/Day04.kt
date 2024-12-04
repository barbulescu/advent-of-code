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

val search = "XMAS".toByteArray().toList()
val searchBackwards = "XMAS".reversed().toByteArray().toList()

const val x = 88.toByte()
const val m = 77.toByte()
const val a = 65.toByte()
const val s = 83.toByte()

private fun part1(lines: List<String>): Int {
    val data = lines.map { it.toByteArray().toList() }
        .toList()

    val horizontalCount = data
        .flatMap { it.windowed(4) }
        .count { it == search || it == searchBackwards }

    val verticalCount = data.findVerticalMatches()
    val diagonalDown = data.findDiagonalDown()
    val diagonalUp = data.findDiagonalUp()
    return horizontalCount + verticalCount + diagonalDown + diagonalUp
}

private fun List<List<Byte>>.findVerticalMatches(): Int {
    var count = 0
    val indices = this.indices
    indices.forEach { col ->
        indices.windowed(4).forEach { vertical ->
            val found = this[vertical[0]][col] == x
                    && this[vertical[1]][col] == m
                    && this[vertical[2]][col] == a
                    && this[vertical[3]][col] == s
            if (found) {
                count++
            }
            val foundBackwards = this[vertical[0]][col] == s
                    && this[vertical[1]][col] == a
                    && this[vertical[2]][col] == m
                    && this[vertical[3]][col] == x
            if (foundBackwards) {
                count++
            }
        }
    }
    return count
}

private fun List<List<Byte>>.findDiagonalDown(): Int {
    var count = 0
    val indices = this.indices
    indices.windowed(4).forEach { row ->
        indices.windowed(4).forEach { col ->
            val found = this[row[0]][col[0]] == x
                    && this[row[1]][col[1]] == m
                    && this[row[2]][col[2]] == a
                    && this[row[3]][col[3]] == s
            if (found) {
                count++
            }
            val foundBackwards = this[row[0]][col[0]] == s
                    && this[row[1]][col[1]] == a
                    && this[row[2]][col[2]] == m
                    && this[row[3]][col[3]] == x
            if (foundBackwards) {
                count++
            }
        }
    }
    return count
}

private fun List<List<Byte>>.findDiagonalUp(): Int {
    var count = 0
    val indices = this.indices
    indices.windowed(4).forEach { row ->
        indices.windowed(4).forEach { col ->
            val found = this[row[0]][col[3]] == x
                    && this[row[1]][col[2]] == m
                    && this[row[2]][col[1]] == a
                    && this[row[3]][col[0]] == s
            if (found) {
                count++
            }
            val foundBackwards = this[row[0]][col[3]] == s
                    && this[row[1]][col[2]] == a
                    && this[row[2]][col[1]] == m
                    && this[row[3]][col[0]] == x
            if (foundBackwards) {
                count++
            }
        }
    }
    return count
}

val mas = listOf(m, a, s)
val sam = listOf(s, a, m)

private fun part2(lines: List<String>): Int {
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
                println("found on row $row and col $col: $s1 <> $s2")
            }
        }
    }
    return count
}
