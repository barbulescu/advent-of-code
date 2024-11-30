package y2023.day16

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import utils.*
import utils.Direction.*

private val fileData = FileData(day = 16, year = 2023)

fun main() {
    // test if implementation meets criteria from the description, like:
    expectResult(46) {
        part1(fileData.readTestData(1))
    }
    expectResult(51) {
        part2(fileData.readTestData(2))
    }

    val data = fileData.readData()

    println("#1 -> ${part1(data)}")
    println("#2 -> ${part2(data)}")
}

private fun part1(lines: List<String>): Int = lines.toArea()
    .processStart(Beam(Point(-1, 0), EAST))

private fun part2(lines: List<String>): Int = runBlocking {
    val grid = lines.toArea()
    buildStartBeams(grid)
        .map { start -> async { grid.processStart(start) } }
        .awaitAll()
        .max()
}

private fun Map<Point, Char>.processStart(start: Beam): Int {
    val beams = mutableListOf(start)
    val beamsHistory = mutableSetOf<Beam>()
    while (beams.isNotEmpty()) {
        move(beams, beamsHistory)
    }
    return beamsHistory.map(Beam::position).distinct().count()
}

private fun buildStartBeams(grid: Map<Point, Char>): MutableList<Beam> {
    val maxX = grid.keys.maxOf(Point::x)
    val maxY = grid.keys.maxOf(Point::y)

    val starts = mutableListOf<Beam>()
    for (x in 0..maxX) {
        starts += listOf(Beam(Point(x, -1), SOUTH), Beam(Point(x, maxY + 1), NORTH))
    }
    for (y in 0..maxY) {
        starts += listOf(Beam(Point(-1, y), EAST), Beam(Point(maxX + 1, y), WEST))
    }
    return starts
}

private fun Map<Point, Char>.move(beams: MutableList<Beam>, beamsHistory: MutableSet<Beam>) {
    val beam = beams.removeFirst()
    val next = beam.position.move(beam.direction)
    if (next !in this || beam.copy(position = next) in beamsHistory) {
        return
    } else {
        beamsHistory += beam.copy(position = next)
    }
    beams += nextBeam(next, beam)
}

private fun Map<Point, Char>.nextBeam(next: Point, beam: Beam) = nextDirection(next, beam)
    .map { Beam(next, it) }

private fun Map<Point, Char>.nextDirection(next: Point, beam: Beam) = when (this[next]) {
    '.' -> beam.directions
    '/' -> DIRECTIONS_1.getValue(beam.direction)
    '\\' -> DIRECTIONS_2.getValue(beam.direction)
    '-' -> beam.map3.getValue(beam.direction)
    '|' -> beam.map4.getValue(beam.direction)
    else -> error("Invalid point: ${this[next]}")
}

data class Beam(val position: Point, val direction: Direction) {
    val directions = listOf(direction)
    val map3 = mapOf(
        NORTH to WEST_EAST,
        EAST to this.directions,
        SOUTH to WEST_EAST,
        WEST to this.directions
    )

    val map4 = mapOf(
        NORTH to this.directions,
        EAST to NORTH_SOUTH,
        SOUTH to this.directions,
        WEST to NORTH_SOUTH
    )

}

val DIRECTIONS_1 = mapOf(NORTH to listOf(EAST), EAST to listOf(NORTH), SOUTH to listOf(WEST), WEST to listOf(SOUTH))
val DIRECTIONS_2 = mapOf(NORTH to listOf(WEST), EAST to listOf(SOUTH), SOUTH to listOf(EAST), WEST to listOf(NORTH))

val WEST_EAST = listOf(WEST, EAST)
val NORTH_SOUTH = listOf(NORTH, SOUTH)

