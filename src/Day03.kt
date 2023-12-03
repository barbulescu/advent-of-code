import utils.*

private const val DAY = "Day03"

fun main() {
    // test if implementation meets criteria from the description, like:
    expectResult(4361) {
        part1(readInput("${DAY}_1_test"))
    }
    expectResult(467835) {
        part2(readInput("${DAY}_2_test"))
    }

    val data = readInput(DAY)
    println("#1 -> ${part1(data)}")
    println("#2 -> ${part2(data)}")
}

private fun part1(input: List<String>): Int = input
    .toGrid()
    .asNumbers()
    .filter(Number::hasAdjacentSymbol)
    .sumOf(Number::toInt)

private fun part2(input: List<String>): Int = input
    .toGrid()
    .asNumbers()
    .flatMap(Number::findGears)
    .groupBy({ it.first }, { it.second })
    .values
    .filter { it.size == 2 }
    .sumOf { it[0] * it[1] }

private fun Grid.asNumbers() = this
    .asPoints()
    .windowed(2)
    .filter { it[0].isDigit() }
    .fold(mutableListOf(Number(this))) { numbers, window ->
        val p1: Point = window[0]
        val p2: Point? = window.getOrNull(1)
        numbers.last().addPoint(p1)
        if (p2 == null || !p2.isDigit() || p2.isFirstColumn()) {
            numbers.add(Number(this))
        }
        numbers
    }
    .filter(Number::isNotEmpty)


private data class Gear(val x: Int, val y: Int)

private data class Number(private val grid: Grid, private val data: MutableList<Point> = mutableListOf()) {
    fun addPoint(point: Point) {
        data.add(point)
    }

    fun isNotEmpty() = data.isNotEmpty()

    fun toInt() = data
        .map(Point::char)
        .joinToString(separator = "")
        .toInt()

    fun hasAdjacentSymbol() = data.any { it.hasAdjacentSymbol() }

    private fun Point.hasAdjacentSymbol(): Boolean =
        grid.neighbours(x, y)
            .filterNot { it.char == '.' }
            .filterNot { it.char.isDigit() }
            .any()

    fun findGears(): List<Pair<Gear, Int>> =
        data.flatMap { it.findGears() }
            .distinct()
            .map { it to toInt() }

    private fun Point.findGears(): Sequence<Gear> =
        grid.neighbours(x, y)
            .filter { it.char == '*' }
            .map { Gear(it.x, it.y) }

}
