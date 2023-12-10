package utils

import utils.Direction.*

enum class Direction {
    NORTH, EAST, SOUTH, WEST;

    fun reverse() = when (this) {
        NORTH -> SOUTH
        SOUTH -> NORTH
        WEST -> EAST
        EAST -> WEST
    }
}

data class Point(val x: Int, val y: Int) {
    fun isFirstColumn() = this.y == 0

        companion object {
        val ORIGIN = Point(0, 0)
    }

    fun move(direction: Direction, distance: Int = 1) = when (direction) {
        EAST -> Point(x + distance, y)
        WEST -> Point(x - distance, y)
        NORTH -> Point(x, y - distance)
        SOUTH -> Point(x, y + distance)
    }


    fun getAdjacentSides(): List<Point> = listOf(
        Point(x, y - 1), Point(x - 1, y), Point(x + 1, y), Point(x, y + 1),
    )

    fun getAdjacent(): List<Point> = listOf(
        Point(x - 1, y - 1), Point(x, y - 1), Point(x + 1, y - 1),
        Point(x - 1, y), Point(x + 1, y),
        Point(x - 1, y + 1), Point(x, y + 1), Point(x + 1, y + 1),
    )

}

class Grid(private val data: List<List<Char>>) {
    fun asPoints() = data
        .asSequence()
        .flatMapIndexed { x, chars -> chars.toPoints(x) }

    fun neighbours(x: Int, y: Int) = sequenceOf(
        pointAt(x - 1, y - 1),
        pointAt(x - 1, y),
        pointAt(x - 1, y + 1),
        pointAt(x, y - 1),
        pointAt(x, y + 1),
        pointAt(x + 1, y - 1),
        pointAt(x + 1, y),
        pointAt(x + 1, y + 1),
    )
        .filterNotNull()

    private fun pointAt(x: Int, y: Int): Point? {
        if (x < 0 || x >= data.size) {
            return null
        }
        if (y < 0 || y >= data[0].size) {
            return null
        }
        return Point(x, y)
    }

    fun getValue(point: Point) : Char = data[point.x][point.y]
}

fun List<String>.toGrid() = Grid(
    this
        .map(String::toCharArray)
        .map(CharArray::toList)
)

fun List<Char>.toPoints(x: Int) = List(this.size) { y -> Point(x, y) }