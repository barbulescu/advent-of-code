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
    private val points: List<Point> = data.flatMapIndexed { x, chars ->
        chars.mapIndexed { y, value -> Point(x, y, value) }
    }

    fun partSum(): Int {
        return points.asSequence()
            .windowed(2)
            .filter { it[0].digit }
            .fold(mutableListOf(mutableListOf<Point>())) { numbers, window ->
                val p1: Point = window[0]
                val p2: Point? = window.getOrNull(1)
                numbers.last().add(p1)
                if (p2 == null || !p2.digit || p2.reset) {
                    numbers.add(mutableListOf())
                }
                numbers
            }
            .filterNot { it.isEmpty() }
            .filter { points -> points.any { point -> data.isValid(point) } }
            .sumOf { points -> points.toInt() }
    }

    fun gearRatioSum(): Int {
        val gearParts = mutableMapOf<Gear, MutableList<Int>>()
        for (x in data.indices) {
            var currentNumber = ""
            val gears = mutableSetOf<Gear>()
            for (y in data[0].indices) {
                if (data[x][y].isDigit()) {
                    currentNumber += data[x][y]
                    gears.addAll(data.gears(x, y))
                } else {
                    addGearParts(gears, gearParts, currentNumber)
                    currentNumber = ""
                    gears.clear()
                }
            }
            addGearParts(gears, gearParts, currentNumber)
        }
        return gearParts
            .filterValues { it.size == 2 }
            .values
            .sumOf { it[0] * it[1] }


    }

    private fun addGearParts(
        gears: MutableSet<Gear>,
        gearParts: MutableMap<Gear, MutableList<Int>>,
        currentNumber: String
    ) {
        if (gears.isNotEmpty()) {
            gears.forEach {
                gearParts.putIfAbsent(it, mutableListOf())
                gearParts[it]?.add(currentNumber.toInt())
            }
        }
    }


}

private fun List<List<Char>>.isValid(point: Point): Boolean =
    this.neighbours(point.x, point.y)
        .filterNotNull()
        .filterNot { it.char == '.' }
        .filterNot { it.char.isDigit() }
        .any()

private fun List<List<Char>>.gears(x: Int, y: Int): List<Gear> =
    this.neighbours(x, y)
        .filterNotNull()
        .filter { it.char == '*' }
        .map { Gear(it.x, it.y) }
        .toList()

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
