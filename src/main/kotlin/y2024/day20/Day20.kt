package y2024.day20

import utils.Point2D
import utils.findValue

private const val WALL = '#'
private const val START = 'S'
private const val END = 'E'

class Day20(input: List<String>) {
    private val grid = input.map(String::toCharArray)
    private val path: List<Point2D> = findPath(input)

    fun part1(goal: Int) = findCheats(goal, 2)

    fun part2(goal: Int) = findCheats(goal, 20)

    private fun findCheats(goal: Int, cheatTime: Int): Int =
        path.indices.sumOf { start ->
            (start + goal..path.lastIndex).count { end ->
                val physicalDistance = path[start] distanceTo path[end]
                physicalDistance <= cheatTime && physicalDistance <= end - start - goal
            }
        }

    private fun findPath(input: List<String>): List<Point2D> {
        val end = grid.findValue(END)
        val path = mutableListOf(grid.findValue(START))
        while (path.last() != end) {
            val next = path
                .last()
                .cardinalNeighbors()
                .filter { input[it.y][it.x] != WALL }
                .first { it != path.getOrNull(path.lastIndex - 1) }
            path.add(next)
        }
        return path
    }
}
