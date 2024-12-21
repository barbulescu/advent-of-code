package y2023.day17

import utils.Direction
import utils.Direction.*
import utils.FileData
import utils.Point2D
import utils.expectResult

private val fileData = FileData(day = 17, year = 2023)

fun main() {
    // test if implementation meets criteria from the description, like:
    expectResult(102) {
        part1(fileData.readTestData(1))
    }
    expectResult(94) {
        part2(fileData.readTestData(2))
    }

    val data = fileData.readData()

    println("#1 -> ${part1(data)}")
    println("#2 -> ${part2(data)}")
}

data class State(val point: Point2D, val from: Direction, val line: Int)
data class StateLoss(val state: State, val loss: Int)

class ProcessingContext(lines: List<String>) {
    val grid = lines.toGrid(Char::digitToInt)
    private val target = Point2D(grid.keys.maxOf(Point2D::x), grid.keys.maxOf(Point2D::y))

    private val initialState = State(Point2D(0, 0), EAST, 0)
    val unvisited = mutableListOf(StateLoss(initialState, 0))
    val visited = mutableMapOf<State, Int>()

    fun isTarget(current: StateLoss) = current.state.point == target
    fun hasUnvisited() = unvisited.isNotEmpty()
}

private fun part1(lines: List<String>): Int {
    val pc = ProcessingContext(lines)
//
//    while (pc.hasUnvisited()) {
//        val current = pc.unvisited.minBy(StateLoss::loss)
//        if (pc.isTarget(current)) {
//            return current.loss
//        }
//        pc.unvisited -= current
//        pc.visited += current
//        Direction.entries
//            .asSequence()
//            .map { it to current.point.move(it) }
//            .filter { (_, neighbor) -> neighbor in pc.grid }
//            .filter { (direction, _) -> direction != current.from.reverse() }
//            .filter { (direction, _) -> direction != current.from || current.line < 3 }
//            .forEach { (direction, neighbor) ->
//                aa(pc, direction, current, currentLoss, neighbor)
//            }
//    }
    error("Not reachable")
}

private fun part2(lines: List<String>): Int {
    val pc = ProcessingContext(lines)

//    while (pc.hasUnvisited()) {
//        val (current, currentLoss) = pc.unvisited.minBy { it.second }
//        if (pc.isTarget(current) && current.line >= 4) {
//            return currentLoss
//        }
//        pc.unvisited -= current to currentLoss
//        pc.visited += current to currentLoss
//        Direction.entries
//            .asSequence()
//            .map { it to current.point.move(it) }
//            .filter { (_, neighbor) -> neighbor in pc.grid }
//            .filter { (direction, _) -> direction != current.from.reverse() }
//            .filter { (direction, _) -> direction != current.from || current.line < 10 }
//            .filter { (direction, _) -> direction == current.from || current.line >= 4 }
//            .forEach { (direction, neighbor) ->
//                aa(pc, direction, current, currentLoss, neighbor)
//            }
//    }
    error("Not reachable")
}

private fun aa(
    pc: ProcessingContext,
    direction: Direction,
    current: State,
    currentLoss: Int,
    neighbor: Point2D,
) {
    val line = if (direction == current.from) current.line + 1 else 1
    val newLoss = currentLoss + pc.grid.getValue(neighbor)
    val new = State(neighbor, direction, line)
    val existingVisited = pc.visited[new]
    if (existingVisited == null || existingVisited > newLoss) {
        val existingBetter = pc.unvisited.firstOrNull { (state, loss) ->
            new == state && loss <= newLoss
        }
        if (existingBetter == null) {
//            pc.unvisited += new to newLoss
        }
    }
}

private fun <T> List<String>.toGrid(transform: (Char) -> T): Map<Point2D, T> = mutableMapOf<Point2D, T>().also {
    forEachIndexed { y, row ->
        row.forEachIndexed { x, char ->
            it[Point2D(x, y)] = transform(char)
        }
    }
}
