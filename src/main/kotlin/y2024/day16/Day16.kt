package y2024.day16

import utils.Point2D
import utils.Point2D.Companion.EAST
import utils.findValue
import utils.hasValueAt
import java.util.*

class Day16(input: List<String>) {
    private val grid = input.map(String::toCharArray)

    private val start = grid.findValue('S')

    fun part1() = traverse().lowestScore

    fun part2() = traverse().seats

    private fun traverse(): Result {
        val paths = mutableSetOf<Point2D>()
        var lowest = Int.MAX_VALUE
        val scores = mutableMapOf<Pair<Point2D, Point2D>, Int>()
            .withDefault { Int.MAX_VALUE }

        val queue = PriorityQueue<State>(compareBy { it.score })
        queue.add(State(start, EAST, 0))

        while (queue.isNotEmpty()) {
            val (position, direction, score, path) = queue.poll()

            if (score > scores.getValue(position to direction)) continue
            scores[position to direction] = score

            if (grid.hasValueAt(position, 'E')) {
                if (score > lowest) {
                    break
                }
                paths.addAll(path)
                lowest = score
            }

            for ((nextDirection, nextScore) in direction.toScores()) {
                if (grid.hasValueAt(position + nextDirection, '#')) {
                    continue
                }
                val state = State(position + nextDirection, nextDirection, score + nextScore, path + position)
                queue.add(state)
            }
        }

        return Result(lowest, paths.size + 1)
    }

    private fun Point2D.toScores() = listOf(
        this to 1,
        turnRight() to 1001,
        turnLeft() to 1001
    )

    private data class State(
        val position: Point2D,
        val direction: Point2D,
        val score: Int,
        val path: List<Point2D> = emptyList(),
    )

    private data class Result(val lowestScore: Int, val seats: Int)
}
