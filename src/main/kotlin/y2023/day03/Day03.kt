package y2023.day03

import utils.*

private val fileData = FileData(day = 3, year = 2023)

fun main() {
    // test if implementation meets criteria from the description, like:
    expectResult(4361) {
        part1(fileData.readTestData(1))
    }
    expectResult(467835) {
        part2(fileData.readTestData(2))
    }

    val data = fileData.readData()

    println("#1 -> ${part1(data)}")
    println("#2 -> ${part2(data)}")
}

private fun part1(lines: List<String>): Int = lines
    .toGrid { it }
    .asNumbers()
    .filter(Number::hasAdjacentSymbol)
    .sumOf(Number::toInt)

private fun part2(lines: List<String>): Int = lines
    .toGrid { it }
    .asNumbers()
    .flatMap(Number::findGears)
    .groupBy({ it.first }, { it.second })
    .values
    .filter { it.size == 2 }
    .sumOf { it[0] * it[1] }

private fun Grid<Char>.asNumbers() = this
    .asPoints()
    .map { it to this.getValue(it) }
    .windowed(2)
    .filter { it[0].second.isDigit() }
    .fold(mutableListOf(Number(this))) { numbers, window ->
        val p1: Point = window[0].first
        val p2: Point? = window.getOrNull(1)?.first
        val p2Char: Char = window.getOrNull(1)?.second!!
        numbers.last().addPoint(p1)
        if (p2 == null || !p2Char.isDigit() || p2.isFirstColumn()) {
            numbers.add(Number(this))
        }
        numbers
    }
    .filter(Number::isNotEmpty)


private data class Gear(val x: Int, val y: Int)

private data class Number(private val grid: Grid<Char>, private val data: MutableList<Point> = mutableListOf()) {
    fun addPoint(point: Point) {
        data.add(point)
    }

    fun isNotEmpty() = data.isNotEmpty()

    fun toInt() = data
        .map { grid.getValue(it) }
        .joinToString(separator = "")
        .toInt()

    fun hasAdjacentSymbol() = data.any { it.hasAdjacentSymbol() }

    private fun Point.hasAdjacentSymbol(): Boolean =
        grid.neighbours(x, y)
            .map { grid.getValue(it) }
            .filterNot { it == '.' }
            .filterNot { it.isDigit() }
            .any()

    fun findGears(): List<Pair<Gear, Int>> =
        data.flatMap { it.findGears() }
            .distinct()
            .map { it to toInt() }

    private fun Point.findGears(): Sequence<Gear> =
        grid.neighbours(x, y)
            .filter { grid.getValue(it) == '*' }
            .map { Gear(it.x, it.y) }

}
