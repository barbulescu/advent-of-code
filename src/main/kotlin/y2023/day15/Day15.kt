package y2023.day15

import utils.FileData
import utils.expectResult

private val fileData = FileData(day = 15, year = 2023)

fun main() {
    // test if implementation meets criteria from the description, like:
    expectResult(1320) {
        part1(fileData.readTestData(1))
    }
    expectResult(145) {
        part2(fileData.readTestData(2))
    }

    val data = fileData.readData()

    println("#1 -> ${part1(data)}")
    println("#2 -> ${part2(data)}")
}

private fun part1(lines: List<String>): Int = lines[0]
    .split(',')
    .asSequence()
    .map(String::toHash)
    .sum()

private fun part2(lines: List<String>): Int {
    val boxes = List(256) { LinkedHashMap<String, Int>() }
    lines[0]
        .split(',')
        .forEach { part ->
            val equalsOperationIndex = part.indexOf('=')
            if (equalsOperationIndex != -1) {
                val label = part.dropLast(2)
                val hash = label.toHash()
                val lens = part.takeLast(1).toInt()
                val box = boxes[hash]
                box[label] = lens
            } else {
                require(part.last() == '-') { "expected last char to be -" }
                val label = part.dropLast(1)
                val hash = label.toHash()
                val box = boxes[hash]
                box.remove(label)
            }
        }
    return boxes.flatMapIndexed { boxIndex, lens ->
        lens.asIterable().mapIndexed { lensIndex, entry -> (boxIndex + 1) * (lensIndex + 1) * entry.value }
    }.sum()
}

fun String.toHash(): Int = this.toCharArray()
    .asSequence()
    .map { it.code }
    .fold(0L) { acc, value -> (acc + value) * 17 % 256 }
    .toInt()















