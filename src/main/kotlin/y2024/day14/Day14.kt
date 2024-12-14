package y2024.day14

import utils.executeDay

fun main() {
    executeDay(List<String>::part1, List<String>::part2)
}

private val pattern = "p=(\\d+),(\\d+) v=(-?\\d+),(-?\\d+)".toRegex()
private const val width = 101
private const val height = 103

private fun List<String>.part1(): Long {
    if (this.size < 40) {
        return 0
    }

    val quadrants = Quadrants()

    forEach { line ->
        val (x, y) = line.toRobot()
            .calculateFinalPosition(steps = 100)
        quadrants.placeRobot(x, y)
    }

    return quadrants.calculate()
}

private class Quadrants {
    val quadrants = longArrayOf(0, 0, 0, 0)

    private val middleX = width / 2
    private val middleY = height / 2

    fun placeRobot(x: Int, y: Int) {
        val indexX = if (x < middleX) 2 else 0
        val indexY = if (y < middleY) 1 else 0
        val index = indexX + indexY

        if (x == middleX || y == middleY) {
            return
        }

        quadrants[index]++
    }

    fun calculate() = quadrants.reduce { acc, value -> acc * value }
}

private class Robot(var x: Int, var y: Int, val vx: Int, var vy: Int) {
    fun calculateFinalPosition(steps: Int): Pair<Int, Int> {
        this.x = ((this.x + this.vx * steps) % width + width) % width
        this.y = ((this.y + this.vy * steps) % height + height) % height
        return x to y
    }

    fun point() = Point(x, y)
}

private fun String.toRobot(): Robot {
    val (px, py, vx, vy) = requireNotNull(pattern.matchEntire(this))
        .groupValues
        .drop(1)
        .map(String::toInt)

    return Robot(px, py, vx, vy)
}


private fun List<String>.part2(): Long {
    if (this.size < 40) {
        return 0
    }
    val iterations = width * height

    fun buildRoom(robotsByPos: Map<Point, List<Robot>>): String {
        val s = StringBuilder()
        for (row in 0..<width) {
            for (col in 0..<height) {
                if (Point(col, row) in robotsByPos) {
                    s.append('#')
                } else {
                    s.append(' ')
                }
            }
            s.append('\n')
        }
        return s.toString()
    }

    val robots: List<Robot> = this.map(String::toRobot)

    return (1L..iterations)
        .asSequence()
        .map { iteration ->
            val robotsByPos = robots
                .onEach { it.calculateFinalPosition(1) }
                .groupBy(Robot::point)
            iteration to buildRoom(robotsByPos)
        }
        .filter { "###############################" in it.second }
        .map { it.first }
        .first()
}

private data class Point(val x: Int, val y: Int)
