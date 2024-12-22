package y2024.day18

import utils.Point2D
import utils.Point2D.Companion.ORIGIN
import utils.binarySearchFirst

class Day18(input: List<String>) {
    private val bytes: List<Point2D> = input.map { Point2D.of(it) }

    fun part1(size: Int, byteCount: Int): Int =
        traverse(size.toPoint(), bytes.take(byteCount).toSet())
            ?: error("No path found")

    fun part2(size: Int): String {
        val index = bytes
            .indices
            .toList()
            .binarySearchFirst { index ->
                traverse(size.toPoint(), bytes.take(index + 1).toSet()) == null
            }
        val value = bytes[index]
        return "${value.x},${value.y}"
    }

    private fun Int.toPoint() = Point2D(this - 1, this - 1)

    private fun traverse(end: Point2D, barriers: Set<Point2D>): Int? {
        val queue = mutableListOf(ORIGIN to 0)
        val seen = mutableSetOf<Point2D>()

        while (queue.isNotEmpty()) {
            val (place, cost) = queue.removeFirst()

            if (place == end) {
                return cost
            } else if (seen.add(place)) {
                place.cardinalNeighbors()
                    .filter { it.inRange(end) }
                    .filterNot { it in barriers }
                    .forEach { queue.add(it to cost + 1) }
            }
        }
        return null
    }
}

