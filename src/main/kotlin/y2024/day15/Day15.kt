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
        .map(CharArray::toMutableList)
        .toMutableList()

    val warehouse = Warehouse1(warehouseLines)

    this
        .takeLastWhile(String::isNotBlank)
        .flatMap(String::toList)
        .forEach(warehouse::move)

    return warehouse.calculate()
}

fun List<String>.part2(): Long {
    val warehouseLines = this
        .dropLast(1)
        .asSequence()
        .drop(1)
        .takeWhile(String::isNotBlank)
        .map { it.dropLast(1) }
        .map { it.drop(1) }
        .map {
            it.toCharArray()
                .asSequence()
                .flatMap { x ->
                    when (x) {
                        'O' -> sequenceOf('[', ']')
                        '#' -> sequenceOf('#', '#')
                        else -> sequenceOf(x)
                    }
                }
                .toMutableList()
        }
        .toMutableList()

    val warehouse = Warehouse1(warehouseLines)

    this
        .takeLastWhile(String::isNotBlank)
        .flatMap(String::toList)
        .forEach(warehouse::move)

    return warehouse.calculate()
}

private class Warehouse1(private val data: MutableList<MutableList<Char>>) {
    private val width = data[0].size
    private val height = data.size

    private var robot = data
        .findChars('@')
        .first()

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

    private fun value(point: Point) = data[point.y][point.x]
    private fun value(point: Point, c: Char) {
        data[point.y][point.x ] = c
    }

    private fun move(item: Point, move: (Point) -> Point): Point? {
        val nextItem = move(item)

        if (nextItem.x !in (0..<width)) {
            return null
        }
        if (nextItem.y !in (0..<height)) {
            return null
        }
        if (value(nextItem) == '#') {
            return null
        }
        if (value(nextItem) == 'O') {
            val newBox = move(nextItem, move)
            if (newBox != null) {
                val x = value(newBox)
                value(newBox, value(nextItem))
                value(nextItem, x)

                println("move Box from $nextItem to $newBox")
            } else {
                return null
            }
        }
        return nextItem
    }

    fun calculate(): Long = data
        .findChars('O')
        .sumOf { (it.x + 1) + (it.y + 1) * 100 }.toLong()
}


private data class Point(val x: Int, val y: Int)

private fun List<List<Char>>.findChars(vararg searchChars: Char) = asSequence()
    .flatMapIndexed { y, chars ->
        chars.mapIndexed { x, c ->
            if (c in searchChars) {
                Point(x, y)
            } else {
                null
            }
        }
    }
    .filterNotNull()

