package y2024.day09

import utils.executeDay
import java.util.*

fun main() {
    executeDay(List<String>::part1, List<String>::part2)
}

private fun List<String>.part1(): Long =
    DiskPartitionTable(this[0]).process1().checksum()

private fun List<String>.part2(): Long =
    DiskPartitionTable(this[0]).process2().checksum()

private data class Page(val id: Int, val used: Int, val free: Int) {
    val size: Int = used + free
}

private class DiskPartitionTable(val diskMap: String) {
    val data: ArrayList<Page> = ArrayList<Page>(diskMap.length.let { it / 2 + it % 2 }).apply {
        var id = 0
        diskMap
            .asSequence()
            .map(Char::digitToInt)
            .chunked(2) {
                Page(id = id++, used = it[0], free = it.getOrElse(1) { 0 })
            }
            .forEach(::addLast)
    }
}

private fun DiskPartitionTable.checksum(): Long {
    var sum = 0L
    var block = 0

    data.forEach { page ->
        repeat(page.used) {
            sum += page.id * block++
        }
        block += page.free
    }

    return sum
}

private fun DiskPartitionTable.process1(): DiskPartitionTable {
    var fullUntil = 0
    while (true) {
        val freeSpace = makeSpaceOrNull(start = fullUntil) ?: return this
        fragmentLast()
        move(data.lastIndex, freeSpace)
        fullUntil = freeSpace
    }
}

private fun DiskPartitionTable.process2(): DiskPartitionTable {
    val processed = mutableSetOf<Int>()
    var index = data.size
    while (index > 1) {
        index--
        val page = data[index]
        if (page.id in processed) continue
        processed.add(page.id)

        val freeSpace = makeSpaceOrNull(minFree = page.used, end = index) ?: continue
        move(index, freeSpace)
        index++
    }
    return this
}

private fun DiskPartitionTable.makeSpaceOrNull(minFree: Int = 1, start: Int = 0, end: Int = data.lastIndex): Int? = data
    .subList(start, end)
    .indexOfFirst { it.free >= minFree }
    .takeIf { it != -1 }
    ?.let { start + it }

private fun DiskPartitionTable.fragmentLast() {
    if (data.last().used <= 1) {
        return
    }
    val page = data.removeLast()
    data.addLast(page.copy(used = page.used - 1, free = 0))
    data.addLast(page.copy(used = 1, free = page.free))
}

private fun DiskPartitionTable.move(source: Int, destination: Int) {
    val page = data.removeAt(source)

    val previous = data.removeAt(source - 1)
    data.add(source - 1, previous.copy(free = previous.free + page.size))

    val insertedPage = data.removeAt(destination)
    data.add(destination, insertedPage.copy(free = 0))
    data.add(destination + 1, Page(page.id, page.used, insertedPage.free - page.used))

}

