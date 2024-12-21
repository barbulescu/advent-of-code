package y2023.day13

import utils.FileData
import utils.Point2D
import utils.expectResult

private val fileData = FileData(day = 13, year = 2023)

fun main() {
    // test if implementation meets criteria from the description, like:
    expectResult(405) {
        part1(fileData.readTestData(1))
    }
    expectResult(400) {
        part2(fileData.readTestData(2))
    }

    val data = fileData.readData()

    println("#1 -> ${part1(data)}")
    println("#2 -> ${part2(data)}")
}

private fun part1(lines: List<String>): Int {
    return lines.split().sumOf { block ->
        val points = block.toPoints()
        val maxX = points.maxOf { it.key.x }
        val maxY = points.maxOf { it.key.y }

        points.calculateReflection1(maxX, maxY, 0..<maxX, Point2D::reflectedHorizontally)?.let { it + 1 }
            ?: points.calculateReflection1(maxX, maxY, 0..<maxY, Point2D::reflectedVertically)?.let { (it + 1) * 100 }
            ?: error("should not be reached on part1")
    }
}

private fun Map<Point2D, Char>.calculateReflection1(
    maxX: Int,
    maxY: Int,
    mainRange: IntRange,
    mapper: (Point2D, Int) -> Points
) = mainRange.asSequence()
    .map { reflectionX ->
        reflectionX to generateCoordinates(maxX, maxY)
            .map { mapper(it, reflectionX) }
            .firstOrNull(this::haveSameValue)
    }
    .filter { it.second == null }
    .map { it.first }
    .firstOrNull()

private fun part2(lines: List<String>): Int {
    return lines.split().map { block ->
        val points = block.toPoints()
        val maxX = points.maxOf { it.key.x }
        val maxY = points.maxOf { it.key.y }

        points.calculateReflection2(maxX, maxY, 0..<maxX, Point2D::reflectedHorizontally)?.let { it + 1 }
            ?: points.calculateReflection2(maxX, maxY, 0..<maxY, Point2D::reflectedVertically)?.let { (it + 1) * 100 }
            ?: error("should not be reached")
    }.sum()
}

private fun Map<Point2D, Char>.calculateReflection2(
    maxX: Int,
    maxY: Int,
    mainRange: IntRange,
    mapper: (Point2D, Int) -> Points
): Int? {
    main@ for (reflectionX in mainRange) {
        var smudge: Point2D? = null
        for (x in 0..maxX) {
            for (y in 0..maxY) {
                val points = mapper(Point2D(x, y), reflectionX)
                if (this.haveSameValue(points)) {
                    if (smudge == null) smudge = points.point
                    else if (smudge == points.reflectedPoint) continue
                    else continue@main
                }
            }
        }
        if (smudge == null) {
            continue@main
        }
        return reflectionX
    }
    return null
}


private fun Point2D.reflectedVertically(reflectionY: Int) = Points(this, Point2D(x, y - (((y - reflectionY) * 2) - 1)))
private fun Point2D.reflectedHorizontally(reflectionX: Int) = Points(this, Point2D(x - (((x - reflectionX) * 2) - 1), y))

private fun Map<Point2D, Char>.haveSameValue(points: Points) = this.haveSameValue(points.point, points.reflectedPoint)
private fun Map<Point2D, Char>.haveSameValue(point: Point2D, reflectedPoint: Point2D) =
    this[reflectedPoint] != null && this[point] != this[reflectedPoint]

private data class Points(val point: Point2D, val reflectedPoint: Point2D)

private fun List<String>.toPoints(): Map<Point2D, Char> {
    val map = mutableMapOf<Point2D, Char>()
    forEachIndexed { y, row ->
        row.forEachIndexed { x, c ->
            map[Point2D(x, y)] = c
        }
    }
    return map.toMap()
}

private fun List<String>.split(): List<List<String>> {
    val result = mutableListOf<List<String>>()
    val buffer = mutableListOf<String>()

    this.forEach { line ->
        if (line.isBlank()) {
            result.add(buffer.toList())
            buffer.clear()
        } else {
            buffer.add(line)
        }
    }
    result.add(buffer.toList())
    return result
        .filterNot { it.isEmpty() }
        .toList()
}

private fun generateCoordinates(maxX: Int, maxY: Int) = (0..maxX).asSequence()
    .flatMap { x ->
        (0..maxY).map { y -> Point2D(x, y) }
    }

