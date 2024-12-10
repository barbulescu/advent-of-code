package y2024.day09

import utils.executeDay
import utils.swap
import java.util.*

fun main() {
    executeDay(List<String>::part1, List<String>::part2)
}

private fun List<String>.part1(): Long {
    val initialMap = StringBuilder(this[0].toMap())

    for (l in initialMap.indices.reversed()) {
        if (initialMap[l] != Char.MAX_VALUE) {
            val i = initialMap.indexOf(Char.MAX_VALUE)
            initialMap.swap(i, l)
        }
        val a = initialMap.indexOfLast { it != Char.MAX_VALUE } + 1
        val b = initialMap.indexOf(Char.MAX_VALUE)
        if (a == b) {
            break
        }
    }

    return initialMap.toChecksum()
}

private fun StringBuilder.toChecksum() = filterNot { it == Char.MAX_VALUE }
    .mapIndexed { index, c -> index.toLong() * c.code.toLong() }
    .sum()

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
    val disk = this[0].toCharArray().mapIndexed { i, c ->
        val id = i / 2
        val type = if (i % 2 == 0) {
            Type.DATA
        } else {
            Type.FREE
        }
        Block(id, type, c.digitToInt())
    }.toMutableList()

    var dataIndex = disk.size
    while (dataIndex > 0) {
        dataIndex--

        val data = disk[dataIndex]

        if (data.type == Type.FREE) {
            continue
        }
        if (data.size == 0) {
            continue
        }
        val freeIndex = disk.indexOfFirst { it.type == Type.FREE && it.size >= data.size }
        if (freeIndex < 0 || freeIndex >= dataIndex) {
            continue
        }

        val free = disk[freeIndex]
        if (free.size == data.size) {
            disk.swap(dataIndex, freeIndex)
            continue
        }
        val freeBlock1 = free.copy(size = data.size)
        val freeBlock2 = free.copy(size = free.size - data.size)
        disk[freeIndex] = data
        disk[dataIndex] = freeBlock1
        disk.add(freeIndex + 1, freeBlock2)
    }

    var index = 0
    return disk.sumOf {
        val response = it.calculate(index)
        index += it.size
        response
    }

}

private data class Block(val id: Int, val type: Type, val size: Int) {
    fun calculate(startIndex: Int): Long = if (type == Type.FREE) {
        0
    } else {
        (0..<size)
            .map { it + startIndex }
            .sumOf { id * it }
            .toLong()
    }

    override fun toString(): String =
        String(
            if (type == Type.FREE) {
                List(size) { '.' }
            } else {
                List(size) { id.toChar() }
            }.toCharArray())
}

private enum class Type {
    DATA,
    FREE
}
