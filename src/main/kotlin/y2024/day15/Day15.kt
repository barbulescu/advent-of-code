package y2024.day15

import utils.executeDay

fun main() {
    executeDay(List<String>::part1, List<String>::part2)
}

private const val BOX = 'O'
private const val WALL = '#'
private const val ROBOT = '@'
private const val BOX_LEFT = '['
private const val BOX_RIGHT = ']'

private val UP: (Point) -> Point = { it.copy(y = it.y - 1) }
private val RIGHT: (Point) -> Point = { it.copy(x = it.x + 1) }
private val LEFT: (Point) -> Point = { it.copy(x = it.x - 1) }
private val DOWN: (Point) -> Point = { it.copy(y = it.y + 1) }

fun List<String>.part1(): Long = execute(1, CharArray::toMutableList)

fun List<String>.part2(): Long {
    val transform: (CharArray) -> MutableList<Char> = {
        it
            .asSequence()
            .flatMap { x ->
                when (x) {
                    ROBOT -> sequenceOf(ROBOT, '.')
                    BOX -> sequenceOf(BOX_LEFT, BOX_RIGHT)
                    else -> sequenceOf(x, x)
                }
            }
            .toMutableList()
    }
    return execute(2, transform)
}

private fun List<String>.execute(leftMargin: Int, transform: (CharArray) -> MutableList<Char>): Long {
    val warehouseLines = this
        .takeWhile(String::isNotBlank)
        .dropLast(1)
        .drop(1)
        .map { it.dropLast(1).drop(1) }
        .map(String::toCharArray)
        .map(transform)
        .toMutableList()

    val warehouse = Warehouse(warehouseLines)

    this
        .takeLastWhile(String::isNotBlank)
        .asSequence()
        .flatMap(String::toList)
        .map(Char::toCommand)
        .forEach(warehouse::move)

    return warehouse.calculate(leftMargin)
}

private fun Char.toCommand() = when (this) {
    '^' -> UP
    '>' -> RIGHT
    '<' -> LEFT
    'v' -> DOWN
    else -> error("Unsupported command: `${this}`")
}

private class Warehouse(private val data: MutableList<MutableList<Char>>) {
    private val width = data[0].size
    private val height = data.size
    private val transaction = mutableListOf<Pair<Point, Point>>()

    private var robot = data
        .findChars(ROBOT)
        .first()

    fun move(command: (Point) -> Point) {
        transaction.clear()
        val newRobot = move(robot, command)
        if (newRobot != null) {
            swap(robot, newRobot)
            robot = newRobot
        }
    }

    private fun value(point: Point) = data[point.y][point.x]
    private fun value(point: Point, value: Char) {
        data[point.y][point.x] = value
    }

    private fun swap(p1: Point, p2: Point) {
        transaction.add(p1 to p2)
        val value = value(p1)
        value(p1, value(p2))
        value(p2, value)
    }

    private fun move(item: Point, command: (Point) -> Point): Point? {
        val nextItem = command(item)

        if (nextItem.x !in (0..<width)) {
            return null
        }
        if (nextItem.y !in (0..<height)) {
            return null
        }
        if (value(nextItem) == WALL) {
            return null
        }
        if (
            value(nextItem) == BOX || (command === LEFT || command === RIGHT)
            && (value(nextItem) == BOX_LEFT || value(nextItem) == BOX_RIGHT)
        ) {
            val newBox = move(nextItem, command) ?: return null
            swap(nextItem, newBox)
        }
        if (value(nextItem) == BOX_LEFT) {
            val rightStart = nextItem.copy(x = nextItem.x + 1)
            if (!moveLargeBox(nextItem, rightStart, command)) {
                rollback()
                return null
            }
        }
        if (value(nextItem) == BOX_RIGHT) {
            val leftStart = nextItem.copy(x = nextItem.x - 1)
            if (!moveLargeBox(leftStart, nextItem, command)) {
                rollback()
                return null
            }
        }
        return nextItem
    }

    private fun rollback() {
        (transaction.size - 1 downTo 0).forEach {
            val pair = transaction[it]
            swap(pair.second, pair.first)
        }
        transaction.clear()
    }

    private fun moveLargeBox(leftStart: Point, rightStart: Point, command: (Point) -> Point): Boolean {
        val leftEnd = move(leftStart, command)
        val rightEnd = move(rightStart, command)
        if (leftEnd == null || rightEnd == null) {
            return false
        }
        swap(leftStart, leftEnd)
        swap(rightStart, rightEnd)
        return true
    }

    fun calculate(leftMargin: Int): Long = data
        .findChars(BOX, BOX_LEFT)
        .sumOf { (it.x + leftMargin) + (it.y + 1) * 100 }.toLong()

    override fun toString(): String = data
        .joinToString(separator = "\n", prefix = "\n") {
            it.joinToString(separator = "")
        }
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

