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
    private val maxX = data.size
    private val maxY = data[0].size

    fun partSum(): Int {
        var sum = 0
        for (x in data.indices) {
            var currentNumber = ""
            var valid = false
            for (y in data[0].indices) {
                if (data[x][y].isDigit()) {
                    currentNumber += data[x][y]
                    valid = valid || isValid(x, y)
                } else {
                    if (valid) {
                        sum += currentNumber.toInt()
                    }
                    currentNumber = ""
                    valid = false
                }
            }
            if (valid) {
                sum += currentNumber.toInt()
            }

        }
        return sum
    }

    fun gearRatioSum(): Int {
        val gearParts = mutableMapOf<Gear, MutableList<Int>>()
        for (x in data.indices) {
            var currentNumber = ""
            val gears = mutableSetOf<Gear>()
            for (y in data[0].indices) {
                if (data[x][y].isDigit()) {
                    currentNumber += data[x][y]
                    gears.addAll(gears(x, y))
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

    private fun isValid(x: Int, y: Int): Boolean =
        neighbours(x, y)
            .filterNotNull()
            .filter { it.char != '.' }
            .filterNot { it.char.isDigit() }
            .any()

    private fun gears(x: Int, y: Int): List<Gear> =
        neighbours(x, y)
            .filterNotNull()
            .filter { it.char == '*' }
            .map { Gear(it.x, it.y) }
            .toList()

    private fun neighbours(x: Int, y: Int) = sequenceOf(
        valueAt(x - 1, y - 1),
        valueAt(x, y - 1),
        valueAt(x - 1, y),
        valueAt(x - 1, y + 1),
        valueAt(x + 1, y - 1),
        valueAt(x + 1, y + 1),
        valueAt(x, y + 1),
        valueAt(x + 1, y),
    )

    private fun valueAt(x: Int, y: Int): Point? {
        if (x < 0 || x >= maxX) {
            return null
        }
        if (y < 0 || y >= maxY) {
            return null
        }
        return Point(x, y, data[x][y])
    }

    private data class Gear(val x: Int, val y: Int)
    private data class Point(val x: Int, val y: Int, val char: Char)
}