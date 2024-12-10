package y2024.day10

import utils.executeDay

fun main() {
    executeDay(List<String>::part1, List<String>::part2)
}

private fun List<String>.part1(): Long {
    val grid = this.mapIndexed { y, line ->
        line.mapIndexed { x, c -> Point(x, y, c.digitToIntOrNull() ?: -1) }
    }

    return grid
        .flatten()
        .filter { it.height == 0 }
        .sumOf { grid.findAllTrails(mutableSetOf(), it, it) }
        .toLong()

}

private fun List<String>.part2(): Long {
    val grid = this.mapIndexed { y, line ->
        line.mapIndexed { x, c -> Point(x, y, c.digitToIntOrNull() ?: -1) }
    }

    return grid
        .flatten()
        .filter { it.height == 0 }
        .sumOf { grid.calculateRatings(mutableSetOf(), it, it) }
        .toLong()
}

private data class Point(val x: Int, val y: Int, val height: Int) {
    override fun toString(): String = "$x:$y-$height"
}

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

private fun List<List<Point>>.findAllTrails(paths: MutableSet<Path>, start: Point, point: Point): Int {
    if (point.height == 9) {
        val path = Path(start, point)
        return if (path in paths) {
            0
        } else {
            paths.add(path)
            1
        }
    }
    val sum = neighbours(point.x, point.y)
        .filterNotNull()
        .filter { it.height == point.height + 1 }
        .map { findAllTrails(paths, start, it) }
        .sum()
    return sum
}

private fun List<List<Point>>.calculateRatings(paths: MutableSet<Path>, start: Point, point: Point): Int {
    if (point.height == 9) {
        val path = Path(start, point)
        return if (path in paths) {
            1
        } else {
            paths.add(path)
            1
        }
    }
    val sum = neighbours(point.x, point.y)
        .filterNotNull()
        .filter { it.height == point.height + 1 }
        .map { calculateRatings(paths, start, it) }
        .sum()
    return sum
}


private fun List<List<Point>>.neighbours(x: Int, y: Int) = sequenceOf(
    pointAt(x - 1, y),
    pointAt(x, y - 1),
    pointAt(x, y + 1),
    pointAt(x + 1, y)
)

private fun List<List<Point>>.pointAt(x: Int, y: Int): Point? {
    if (x < 0 || x >= this[0].size) {
        return null
    }
    if (y < 0 || y >= this.size) {
        return null
    }
    return this[y][x]
}

