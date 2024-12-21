package y2024.day12

import utils.executeDay

fun main() {
    executeDay(List<String>::part1, List<String>::part2)
}

fun List<String>.part1(): Long = this
    .findRegions()
    .sumOf { it.area * it.perimeter }
    .toLong()

fun List<String>.part2(): Long = this
    .findRegions()
    .sumOf { it.area * it.sides }
    .toLong()

private fun List<String>.findRegions(): List<Region> {
    val visited = mutableSetOf<Point2D>()
    return flatMapIndexed { y, row ->
        row.mapIndexedNotNull { x, _ ->
            val point = Point2D(x, y)
            if (point !in visited) {
                findRegion(this, point, visited)
            } else {
                null
            }
        }
    }
}

private fun findRegion(grid: List<String>, start: Point2D, visited: MutableSet<Point2D>): Region {
    val target: Char = grid[start] ?: error("Invalid start point: $start")
    val queue = mutableListOf(start)
    var area = 0
    var perimeter = 0
    var corners = 0

    while (queue.isNotEmpty()) {
        val place = queue.removeFirst()
        if (grid[place] == target && place !in visited) {
            visited += place
            area++
            val neighbors = place.cardinalNeighbors()
            queue.addAll(neighbors)
            perimeter += neighbors.count { grid[it] != target }
            corners += place.countCorners(grid)
        }
    }
    return Region(target, area, perimeter, corners)
}

private operator fun List<String>.contains(point: Point2D): Boolean =
    point.y in indices && point.x in get(point.y).indices

private operator fun List<String>.get(point: Point2D): Char? =
    if (point in this) {
        this[point.y][point.x]
    } else {
        null
    }

private fun Point2D.countCorners(grid: List<String>): Int =
    listOf(NORTH, EAST, SOUTH, WEST, NORTH)
        .zipWithNext()
        .map { (first, second) ->
            listOf(
                grid[this],
                grid[this + first],
                grid[this + second],
                grid[this + first + second]
            )
        }
        .count { (target, side1, side2, corner) ->
            (target != side1 && target != side2) || (side1 == target && side2 == target && corner != target)
        }

private data class Region(val name: Char, val area: Int, val perimeter: Int, val sides: Int)

data class Point2D(val x: Int, val y: Int) {
    fun cardinalNeighbors(): Set<Point2D> = setOf(
        this + NORTH,
        this + EAST,
        this + SOUTH,
        this + WEST
    )

    operator fun plus(other: Point2D): Point2D =
        Point2D(x + other.x, y + other.y)

    operator fun minus(other: Point2D): Point2D =
        Point2D(x - other.x, y - other.y)

}

private val NORTH = Point2D(0, -1)
private val EAST = Point2D(1, 0)
private val SOUTH = Point2D(0, 1)
private val WEST = Point2D(-1, 0)


