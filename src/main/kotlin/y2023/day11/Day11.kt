package y2023.day11

import utils.FileData
import utils.Point
import utils.expectLongResult
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

private val fileData = FileData(day = 11, year = 2023)

fun main() {
    // test if implementation meets criteria from the description, like:
    expectLongResult(374) {
        part1(fileData.readTestData(1))
    }
    expectLongResult(82000210) {
        part2(fileData.readTestData(2))
    }

    val data = fileData.readData()

    println("#1 -> ${part1(data)}")
    println("#2 -> ${part2(data)}")
}

private fun part1(lines: List<String>): Long = Galaxies(lines, 1).calculateSumOfDistances()

private fun part2(lines: List<String>): Long = Galaxies(lines, 1000000L - 1).calculateSumOfDistances()

class Galaxies(lines: List<String>, private val factor: Long) {
    private val galaxies = buildGalaxies(lines)
    private val expandedX = lines.expandX(galaxies)
    private val expandedY = lines.expandY(galaxies)

    fun calculateSumOfDistances() = galaxies
        .flatMapIndexed { index, from -> galaxies.drop(index + 1).map { to -> Points(from, to) } }
        .sumOf(::calculateDistance)

    private fun calculateDistance(points: Points): Long {
        val expX = points.rangeX().count { it in expandedX }
        val expY = points.rangeY().count { it in expandedY }
        return points.manhattanDistance() + (expX + expY) * factor
    }

}

private data class Points(val from: Point, val to: Point) {
    fun rangeX() = min(from.x, to.x)..max(from.x, to.x)
    fun rangeY() = min(from.y, to.y)..max(from.y, to.y)
    fun manhattanDistance() = abs(from.x - to.x) + abs(from.y - to.y)
}

private fun List<String>.expandY(galaxies: MutableList<Point>) = indices
    .filter { y -> this[0].indices.none { x -> galaxies.contains(x, y) } }

private fun List<String>.expandX(galaxies: MutableList<Point>) = this[0].indices
    .filter { x -> indices.none { y -> galaxies.contains(x, y) } }

private fun buildGalaxies(lines: List<String>): MutableList<Point> = lines
    .flatMapIndexed { y, line -> line.buildGalaxyRow(y) }
    .toMutableList()

private fun String.buildGalaxyRow(y: Int) = this
    .mapIndexed { x, c -> if (c == '#') Point(x, y) else null }
    .filterNotNull()

private fun MutableList<Point>.contains(x: Int, y: Int) = Point(x, y) in this
