package y2024.day06

import utils.executeDay
import y2024.day06.Orientation.*
import java.util.*
import kotlin.streams.asSequence
import kotlin.streams.asStream

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
    val initialPosition = findGuard()
    private val cols = data[0].size
    private val rows = data.size

    fun markVisited(guard: Guard) {
        data[guard.y][guard.x] = 'X'
    }

    fun isBlocked(guard: Guard): Boolean = data[guard.y][guard.x] == '#'

    fun isNotLeaving(guard: Guard): Boolean = when (guard.orientation) {
        NORTH -> guard.y != 0
        EAST -> guard.x != cols - 1
        SOUTH -> guard.y != rows - 1
        WEST -> guard.x != 0
    }

    fun visited() = data.flatten()
        .count { it == 'X' }
        .toLong()

    private fun findGuard(): Guard {
        data.forEachIndexed { y, line ->
            line.forEachIndexed { x, c ->
                if (c == '^') {
                    return Guard(x, y, NORTH)
                }
            }
        }
        error("Unable to find guard")
    }
}

fun main() {
    executeDay(List<String>::part1, List<String>::part2)
}

private fun List<String>.part1(): Long {
    val lab: Lab = buildLab()
    var guard = lab.initialPosition

    lab.markVisited(guard)
    while (lab.isNotLeaving(guard)) {
        val nexPosition = guard.move()

        guard = if (lab.isBlocked(nexPosition)) {
            guard.rotate()
        } else {
            nexPosition
        }
        lab.markVisited(guard)
    }

    return lab.visited()
}

private fun List<String>.buildLab(): Lab = Lab(buildLabMap())

private fun List<String>.buildLabMap(): MutableList<MutableList<Char>> {
    val map: MutableList<MutableList<Char>> = this.map { line ->
        line.chars()
            .asSequence()
            .map(Int::toChar)
            .toMutableList()
    }.toMutableList()
    return map
}

private fun List<String>.part2(): Long = buildLabVariants()
    .count { (lab, _) ->
        var found = false
        var guard = lab.initialPosition

        lab.markVisited(guard)
        var count = 0
        while (lab.isNotLeaving(guard)) {
            count++
            if (count > 10000) {
                found = true
                break
            }
            val nexPosition = guard.move()

            guard = if (lab.isBlocked(nexPosition)) {
                guard.rotate()
            } else {
                nexPosition
            }
            if (guard == lab.initialPosition) {
                found = true
                break
            }
        }
        found
    }
    .toLong()

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

