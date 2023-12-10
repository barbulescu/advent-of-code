package day10

import utils.Direction
import utils.Direction.*
import utils.FileData
import utils.Point
import utils.expectResult

private val fileData = FileData(10)

fun main() {
    // test if implementation meets criteria from the description, like:
    expectResult(4) {
        part1(fileData.readTestData(1))
    }
    expectResult(4) {
        part2(fileData.readTestData(2))
    }

    val data = fileData.readData()

    println("#1 -> ${part1(data)}")
    println("#2 -> ${part2(data)}")
}

private fun part1(lines: List<String>): Int = lines
    .buildGrid()
    .findMaxLengthPath()

private fun part2(lines: List<String>): Int = lines
    .buildGrid()
    .findEnclosedTiles()

private data class DirectionPoint(val direction: Direction, val point: Point)

private data class PointDistance(val point: Point, val distance: Int)

private fun Map<Point, Char>.directionsFrom(point: Point) =
    pipes.getOrDefault(getValue(point), emptyList())

private operator fun <T> Map<Point, T>.contains(dp: DirectionPoint) = this.keys.contains(dp.point)

private fun Map<Point, Char>.findMaxLengthPath(): Int {
    val startPoint = findStartPoint()
    return calculateExploredPathsWithDistances(startPoint).values.max()
}

private fun Map<Point, Char>.calculateExploredPathsWithDistances(startPoint: Point): MutableMap<Point, Int> {
    val unexplored = mutableListOf(PointDistance(startPoint, 0))
    val explored = mutableMapOf(startPoint to 0)

    while (unexplored.isNotEmpty()) {
        val (currentPoint, distance) = unexplored.removeFirst()
        explored[currentPoint] = distance

        unexplored += directionsFrom(currentPoint)
            .asSequence()
            .map { DirectionPoint(it, currentPoint.move(it)) }
            .filterNot { it in explored }
            .filter { it in this }
            .filter { it.direction.reverse() in directionsFrom(it.point) }
            .map { PointDistance(it.point, distance + 1) }

    }
    return explored
}

private fun Map<Point, Char>.findEnclosedTiles(): Int {
    val startPoint = findStartPoint()
    val explored: MutableSet<Point> = calculateExploredPaths(startPoint)

    val expandedGrid = mutableMapOf<Point, Char>()
    forEach { (point, char) ->
        val expandedPoint = point.triple()
        expandedGrid[expandedPoint] = if (char != '.' && point in explored) '#' else '.'
        expandedPoint.getAdjacent().forEach { expandedGrid[it] = '.' }
        if (point in explored) {
            val directions = pipes.getValue(char)
            directions
                .map { expandedPoint.move(it) }
                .forEach { expandedGrid[it] = '#' }
        }
    }

    val toFlood = mutableListOf(Point.ORIGIN)
    while (toFlood.isNotEmpty()) {
        val current = toFlood.removeFirst()
        expandedGrid[current] = '='
        toFlood += current.getAdjacentSides()
            .filter { expandedGrid[it] == '.'}
            .filterNot { it in toFlood }
    }

    return keys.count { expandedGrid[it.triple()] == '.' }
}

private fun Point.multiply(factor: Int) = Point(x * factor, y * factor)
private fun Point.triple() = this.multiply(3)

private fun Map<Point, Char>.calculateExploredPaths(startPoint: Point): MutableSet<Point> {
    val unexplored = mutableListOf(startPoint)
    val explored = mutableSetOf<Point>()
    while (unexplored.isNotEmpty()) {
        val currentPoint = unexplored.removeFirst()
        explored += currentPoint

        unexplored += directionsFrom(currentPoint)
            .asSequence()
            .map { DirectionPoint(it, currentPoint.move(it)) }
            .filterNot { it.point in explored }
            .filter { it in this }
            .filter { it.direction.reverse() in directionsFrom(it.point) }
            .map { it.point }
    }
    return explored
}

private fun Map<Point, Char>.findStartPoint() = this.entries.first { it.value == 'S' }.key

private fun List<String>.buildGrid(): Map<Point, Char> {
    val grid = mutableMapOf<Point, Char>()
    this.forEachIndexed { y, row ->
        row.forEachIndexed { x, c ->
            grid[Point(x, y)] = c
        }
    }
    return grid.toMap()
}

val pipes = mapOf(
    'S' to listOf(NORTH, EAST, SOUTH, WEST),
    '|' to listOf(SOUTH, NORTH),
    '-' to listOf(WEST, EAST),
    'L' to listOf(NORTH, EAST),
    'J' to listOf(NORTH, WEST),
    '7' to listOf(SOUTH, WEST),
    'F' to listOf(SOUTH, EAST)
)
