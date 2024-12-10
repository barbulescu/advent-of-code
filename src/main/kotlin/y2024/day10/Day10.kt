package y2024.day10

import utils.executeDay

fun main() {
    executeDay(List<String>::part1, List<String>::part2)
}

private fun List<String>.part1(): Long = execute { 0 }

private fun List<String>.part2(): Long = execute { 1 }

private fun List<String>.execute(ratingProvider: () -> Int): Long {
    val grid = this.mapIndexed { y, line ->
        line.mapIndexed { x, c ->
            Point(x, y, c.digitToIntOrNull() ?: -1)
        }
    }

    return grid
        .flatten()
        .startingPoints()
        .sumOf {
            grid.findAllTrails(
                paths = mutableSetOf(),
                start = it,
                point = it,
                ratingProvider = ratingProvider
            )
        }
        .toLong()
}

private fun List<Point>.startingPoints(): List<Point> = filter { it.height == 0 }

private data class Point(val x: Int, val y: Int, val height: Int)

private data class Path(val start: Point, val end: Point) {
    init {
        require(start.height == 0) {
            "invalid start point $start"
        }
        require(end.height == 9) {
            "invalid end point $end"
        }
    }
}

private fun List<List<Point>>.findAllTrails(
    paths: MutableSet<Path>,
    start: Point,
    point: Point,
    ratingProvider: () -> Int,
): Int {
    if (point.height == 9) {
        val path = Path(start, point)
        return if (path in paths) {
            ratingProvider()
        } else {
            paths.add(path)
            1
        }
    }
    return neighbours(point.x, point.y)
        .filterNotNull()
        .filter { it.height == point.height + 1 }
        .map {
            findAllTrails(paths, start, it, ratingProvider)
        }
        .sum()
}


private fun List<List<Point>>.neighbours(x: Int, y: Int) = sequenceOf(
    pointAt(x - 1, y),
    pointAt(x, y - 1),
    pointAt(x, y + 1),
    pointAt(x + 1, y)
)

private fun List<List<Point>>.pointAt(x: Int, y: Int): Point? = when {
    x < 0 || x >= this[0].size -> null
    y < 0 || y >= this.size -> null
    else -> this[y][x]
}

