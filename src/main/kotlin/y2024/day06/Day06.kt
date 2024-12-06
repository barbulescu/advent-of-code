package y2024.day06

import utils.executeDay
import y2024.day06.Orientation.*
import java.util.*
import kotlin.streams.asSequence

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

fun main() {
    executeDay(List<String>::part1, List<String>::part2)
}

private fun List<String>.part1(): Long {
    val map: MutableList<MutableList<Char>> = this.map { line ->
        line.chars()
            .asSequence()
            .map(Int::toChar)
            .toMutableList()
    }.toMutableList()

    var guard = Guard(0, 0, NORTH)
    this.forEachIndexed { y, line ->
        line.forEachIndexed { x, c ->
            if (c == '^') {
                guard = Guard(x, y, NORTH)
            }
        }
    }
    map[guard.y][guard.x] = 'X'
    while (isNotLeaving(map, guard)) {
        val nexPosition = guard.move()

        guard = if (map[nexPosition.y][nexPosition.x] == '#') {
            guard.rotate()
        } else {
            nexPosition
        }
        map[guard.y][guard.x] = 'X'
    }

    return map.flatten()
        .count { it == 'X' }
        .toLong()
}

fun isNotLeaving(map: MutableList<MutableList<Char>>, guard: Guard): Boolean = when (guard.orientation) {
    NORTH -> guard.y != 0
    EAST -> guard.x != map[0].size - 1
    SOUTH -> guard.y != map.size - 1
    WEST -> guard.x != 0
}

private fun List<String>.part2(): Long {
    TODO("not implemented yet")
}
