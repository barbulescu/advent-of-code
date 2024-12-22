package utils

import kotlin.math.abs

data class Point2D(val x: Int, val y: Int) {
    fun isFirstColumn() = this.y == 0

    fun move(direction: Direction, distance: Int = 1) = when (direction) {
        Direction.EAST -> Point2D(x + distance, y)
        Direction.WEST -> Point2D(x - distance, y)
        Direction.NORTH -> Point2D(x, y - distance)
        Direction.SOUTH -> Point2D(x, y + distance)
    }

    fun turnRight(): Point2D =
        when (this) {
            NORTH -> EAST
            EAST -> SOUTH
            SOUTH -> WEST
            WEST -> NORTH
            else -> error("Invalid direction: $this")
        }

    fun turnLeft(): Point2D =
        when (this) {
            NORTH -> WEST
            EAST -> NORTH
            SOUTH -> EAST
            WEST -> SOUTH
            else -> error("Invalid direction: $this")
        }

    fun cardinalNeighbours() = listOf(
        Point2D(x - 1, y),
        Point2D(x + 1, y),
        Point2D(x, y - 1),
        Point2D(x, y + 1),
    )

    fun getAdjacentSides(): List<Point2D> = listOf(
        Point2D(x, y - 1), Point2D(x - 1, y), Point2D(x + 1, y), Point2D(x, y + 1),
    )

    fun getAdjacent(): List<Point2D> = listOf(
        Point2D(x - 1, y - 1), Point2D(x, y - 1), Point2D(x + 1, y - 1),
        Point2D(x - 1, y), Point2D(x + 1, y),
        Point2D(x - 1, y + 1), Point2D(x, y + 1), Point2D(x + 1, y + 1),
    )

    fun cardinalNeighbors(): Set<Point2D> = setOf(
        this + NORTH,
        this + EAST,
        this + SOUTH,
        this + WEST
    )

    operator fun plus(other: Point2D): Point2D = Point2D(x + other.x, y + other.y)

    operator fun minus(other: Point2D): Point2D = Point2D(x - other.x, y - other.y)

    infix fun distanceTo(other: Point2D) = abs(x - other.x) + abs(y - other.y)

    fun inRange(end: Point2D): Boolean = (x in (0..end.x)) && (y in (0..end.y))

    companion object {
        val ORIGIN = Point2D(0, 0)

        val NORTH = Point2D(0, -1)
        val EAST = Point2D(1, 0)
        val SOUTH = Point2D(0, 1)
        val WEST = Point2D(-1, 0)

        fun of(input: String): Point2D =
            input.split(",").let {
                Point2D(it.first().toInt(), it.last().toInt())
            }
    }
}
