package y2024.day06

import utils.executeDay
import y2024.day06.Orientation.*
import java.util.*
import kotlin.streams.asSequence


fun main() {
    executeDay(List<String>::part1, List<String>::part2)
}

enum class Orientation {
    NORTH,
    EAST,
    SOUTH,
    WEST
}

data class Guard(val x: Int, val y: Int, val orientation: Orientation) {
    fun move(): Guard = when (orientation) {
        NORTH -> Guard(x, y - 1, orientation)
        EAST -> Guard(x + 1, y, orientation)
        SOUTH -> Guard(x, y + 1, orientation)
        WEST -> Guard(x - 1, y, orientation)
    }

    fun rotate(): Guard = when (orientation) {
        NORTH -> Guard(x, y, EAST)
        EAST -> Guard(x, y, SOUTH)
        SOUTH -> Guard(x, y, WEST)
        WEST -> Guard(x, y, NORTH)
    }
}

class Lab(private val data: MutableList<MutableList<Char>>) {
    private val cols = data[0].size
    private val rows = data.size

    private fun markAsVisited(guard: Guard) {
        data[guard.y][guard.x] = 'X'
    }

    private fun isBlocked(guard: Guard): Boolean = data[guard.y][guard.x] == '#'

    private fun isNextMoveInside(guard: Guard): Boolean = when (guard.orientation) {
        NORTH -> guard.y != 0
        EAST -> guard.x != cols - 1
        SOUTH -> guard.y != rows - 1
        WEST -> guard.x != 0
    }

    fun countVisitedPositions() = data.flatten()
        .count { it == 'X' }
        .toLong()

    fun playGuard(startGuard: Guard): Boolean {
        markAsVisited(startGuard)
        var guard = startGuard
        var count = 0
        while (isNextMoveInside(guard)) {
            val nexPosition = guard.move()

            guard = if (isBlocked(nexPosition)) {
                guard.rotate()
            } else {
                nexPosition
            }
            markAsVisited(guard)
            count++

            if (guard == startGuard || count > 10_000) {
                return true
            }
        }
        return false
    }
}

private fun List<String>.part1(): Long = buildLab().run {
    playGuard(findGuard())
    countVisitedPositions()
}

private fun List<String>.part2(): Long = buildLabVariants()
    .count { (lab, _) -> lab.playGuard(findGuard()) }
    .toLong()


private fun List<String>.findGuard() = this
    .asSequence()
    .mapIndexed { y: Int, line: String -> y to line.indexOf('^') }
    .filter { it.second >= 0 }
    .map { Guard(it.second, it.first, NORTH) }
    .first()

private fun List<String>.buildLabVariants(): Sequence<Pair<Lab, String>> = sequence {
    forEachIndexed { y, line ->
        line.forEachIndexed { x, c ->
            if (c == '.') {
                val raw = buildLabMap()
                raw[y][x] = '#'
                yield(Lab(raw) to "$y:$x")
            }
        }
    }
}

private fun List<String>.buildLab(): Lab = Lab(buildLabMap())

private fun List<String>.buildLabMap(): MutableList<MutableList<Char>> = map { line ->
    line.chars()
        .asSequence()
        .map(Int::toChar)
        .toMutableList()
}.toMutableList()
