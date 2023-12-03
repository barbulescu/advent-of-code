import utils.expectResult
import utils.readInput

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

private fun part1(input: List<String>): Int {
    val data = input
        .map(String::toCharArray)
        .map(CharArray::toList)
    val engine = Engine(data)
    return engine.partSum()
}

private fun part2(input: List<String>): Int {
    val data = input
        .map(String::toCharArray)
        .map(CharArray::toList)
    val engine = Engine(data)
    return engine.gearRatioSum()
}

private class Engine(private val data: List<List<Char>>) {

    fun partSum(): Int = data.toNumbers()
        .filter { points -> points.any { point -> data.isValid(point) } }
        .sumOf { points -> points.toInt() }

    fun gearRatioSum(): Int = data.toNumbers()
        .asSequence()
        .flatMap { points ->
            points.mapNotNull { data.gear(it) }
                .distinct()
                .map { it to points.toInt() }
        }
        .groupBy({ it.first }, { it.second })
        .values
        .filter { it.size == 2 }
        .sumOf { it[0] * it[1] }
}

private fun List<List<Char>>.isValid(point: Point): Boolean =
    this.neighbours(point.x, point.y)
        .filterNotNull()
        .filterNot { it.char == '.' }
        .filterNot { it.char.isDigit() }
        .any()

private fun List<List<Char>>.gear(point: Point): Gear? =
    this.neighbours(point.x, point.y)
        .filterNotNull()
        .filter { it.char == '*' }
        .map { Gear(it.x, it.y) }
        .singleOrNull()

private fun List<List<Char>>.neighbours(x: Int, y: Int) = sequenceOf(
    valueAt(x - 1, y - 1),
    valueAt(x, y - 1),
    valueAt(x - 1, y),
    valueAt(x - 1, y + 1),
    valueAt(x + 1, y - 1),
    valueAt(x + 1, y + 1),
    valueAt(x, y + 1),
    valueAt(x + 1, y),
)

private fun List<List<Char>>.valueAt(x: Int, y: Int): Point? {
    if (x < 0 || x >= this.size) {
        return null
    }
    if (y < 0 || y >= this[0].size) {
        return null
    }
    return Point(x, y, this[x][y])
}

private fun List<List<Char>>.toNumbers() = this
    .asSequence()
    .flatMapIndexed { x, chars -> chars.toPoints(x) }
    .windowed(2)
    .filter { it[0].digit }
    .fold(mutableListOf(mutableListOf<Point>())) { numbers, window -> splitNumbers(window, numbers) }
    .filterNot { it.isEmpty() }

private fun splitNumbers(
    window: List<Point>,
    numbers: MutableList<MutableList<Point>>
): MutableList<MutableList<Point>> {
    val p1: Point = window[0]
    val p2: Point? = window.getOrNull(1)
    numbers.last().add(p1)
    if (p2 == null || !p2.digit || p2.reset) {
        numbers.add(mutableListOf())
    }
    return numbers
}

private fun List<Char>.toPoints(x: Int) = this.mapIndexed { y, value -> Point(x, y, value) }

private fun MutableList<Point>.toInt() = this
    .map { point -> point.char }
    .joinToString(separator = "")
    .toInt()

private data class Gear(val x: Int, val y: Int)
private data class Point(
    val x: Int,
    val y: Int,
    val char: Char,
    val digit: Boolean = char.isDigit(),
    val symbol: Boolean = !(char.isDigit() || char == '.'),
    val gear: Boolean = char == '*',
    val reset: Boolean = y == 0
)
