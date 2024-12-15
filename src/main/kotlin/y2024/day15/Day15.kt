package y2024.day15

import utils.executeDay

fun main() {
    executeDay(List<String>::part1, List<String>::part2)
}

fun List<String>.part1(): Long {
    val warehouseLines = this
        .dropLast(1)
        .asSequence()
        .drop(1)
        .takeWhile(String::isNotBlank)
        .map { it.dropLast(1) }
        .map { it.drop(1) }
        .map(String::toCharArray)
        .map(CharArray::toList)
        .toList()

    val warehouse = Warehouse(warehouseLines)

    this
        .takeLastWhile(String::isNotBlank)
        .flatMap(String::toList)
        .forEach(warehouse::move)

    return warehouse.calculate()
}

private fun List<String>.part2(): Long {
    TODO("part 2 is not yet implemented")
}

private class Warehouse(data: List<List<Char>>) {
    private val width = data[0].size
    private val height = data.size

    private var robot = data
        .findChars(searchChar = '@')
        .first()
    private val boxes = data.findChars('O')
        .toMutableSet()
    private val walls = data.findChars('#')
        .toSet()

    fun move(command: Char) {
        println(command)
        val newRobot = when (command) {
            '^' -> move(robot) { it.copy(y = it.y - 1) }
            '>' -> move(robot) { it.copy(x = it.x + 1) }
            '<' -> move(robot) { it.copy(x = it.x - 1) }
            'v' -> move(robot) { it.copy(y = it.y + 1) }
            else -> error("Unsupported command: `$command`")
        }
        if (newRobot != null) {
            println("move Robot from $robot to $newRobot")
            robot = newRobot
        } else {
            println("Robot cannot move from $robot")
        }
    }

    private fun move(item: Point, move: (Point) -> Point): Point? {
        val nextItem = move(item)

        if (nextItem.x !in (0..<width)) {
            return null
        }
        if (nextItem.y !in (0..<height)) {
            return null
        }
        if (nextItem in walls) {
            return null
        }
        if (nextItem in boxes) {
            val newBox = move(nextItem, move)
            if (newBox != null) {
                boxes.remove(nextItem)
                boxes.add(newBox)
                println("move Box from $nextItem to $newBox")
            } else {
                return null
            }
        }
        return nextItem
    }

    fun calculate(): Long = boxes.sumOf { (it.x + 1) + (it.y + 1) * 100 }.toLong()
}

private data class Point(val x: Int, val y: Int)

private fun List<List<Char>>.findChars(searchChar: Char) = asSequence()
    .flatMapIndexed { y, chars ->
        chars.mapIndexed { x, c ->
            if (c == searchChar) {
                Point(x, y)
            } else {
                null
            }
        }
    }
    .filterNotNull()

