package y2023.day18

import utils.Direction
import utils.Direction.*
import utils.FileData
import utils.expectLongResult

private val fileData = FileData(day = 18, year = 2023)

fun main() {
    // test if implementation meets criteria from the description, like:
    expectLongResult(62) {
        part1(fileData.readTestData(1))
    }
    expectLongResult(952408144115) {
        part2(fileData.readTestData(2))
    }

    val data = fileData.readData()

    println("#1 -> ${part1(data)}")
    println("#2 -> ${part2(data)}")
}

private fun part1(lines: List<String>): Long {
    val dds = lines.map(String::parseLine1)
    val area = dds.fold(emptyContext()) { context, dd -> context.process(dd) }.area
    val perimeter = dds.sumOf { it.distance }
    return area / 2 - perimeter / 2 + perimeter + 1
}

private data class Context(val current: LongPoint, val area: Long, val points: List<LongPoint>) {
    fun process(dd: DirectionDistance): Context {
        val newCurrent = current.move(dd.direction, dd.distance.toLong())
        return Context(
            newCurrent,
            area + points.last().x * newCurrent.y - newCurrent.x * points.last().y,
            points + newCurrent
        )
    }
}

private fun emptyContext() = Context(ORIGIN, 0, mutableListOf(ORIGIN))

private fun String.parseLine1(): DirectionDistance {
    val (directionText, distanceText) = split(" ")
    val direction = directionText.toDirection1()
    val distance = distanceText.toInt()
    return DirectionDistance(direction, distance)
}

private data class DirectionDistance(val direction: Direction, val distance: Int)

private fun String.toDirection1() = when (this) {
    "U" -> NORTH
    "D" -> SOUTH
    "L" -> WEST
    "R" -> EAST
    else -> error("Unable to convert $this to direction for part1")
}

private fun part2(lines: List<String>): Long {
    val dds = lines
        .map { it.extractColor() }
        .map { it.parse2() }
    val area = dds.fold(emptyContext()) { context, dd -> context.process(dd) }.area
    val perimeter = dds.sumOf { it.distance }

    return area / 2 - perimeter / 2 + 1 + perimeter
}

private fun String.parse2(): DirectionDistance = DirectionDistance(toDirection2(), toDistance())

private fun String.extractColor(): String = substringAfter("(").substringBefore(")")

private fun String.toDirection2() = when (this.last().digitToInt()) {
    0 -> EAST
    1 -> SOUTH
    2 -> WEST
    3 -> NORTH
    else -> error("unable to convert ${this.last()} to direction for part2")
}

private fun String.toDistance() = Integer.parseInt(drop(1).dropLast(1), 16)


data class LongPoint(val x: Long, val y: Long) {
    fun move(direction: Direction, distance: Long = 1) = when (direction) {
        EAST -> LongPoint(x + distance, y)
        WEST -> LongPoint(x - distance, y)
        NORTH -> LongPoint(x, y - distance)
        SOUTH -> LongPoint(x, y + distance)
    }
}

private val ORIGIN = LongPoint(0, 0)
