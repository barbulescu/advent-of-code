package utils

data class Point(val x: Int, val y: Int, val char: Char) {
    fun isDigit() = this.char.isDigit()
    fun isFirstColumn() = this.y == 0
}

class Grid(private val data: List<List<Char>>) {
    fun asPoints() = data
        .asSequence()
        .flatMapIndexed { x, chars -> chars.toPoints(x) }

    fun neighbours(x: Int, y: Int) = sequenceOf(
        valueAt(x - 1, y - 1),
        valueAt(x - 1, y),
        valueAt(x - 1, y + 1),
        valueAt(x, y - 1),
        valueAt(x, y + 1),
        valueAt(x + 1, y - 1),
        valueAt(x + 1, y),
        valueAt(x + 1, y + 1),
    )
        .filterNotNull()

    private fun valueAt(x: Int, y: Int): Point? {
        if (x < 0 || x >= data.size) {
            return null
        }
        if (y < 0 || y >= data[0].size) {
            return null
        }
        return Point(x, y, data[x][y])
    }
}

fun List<String>.toGrid() = Grid(
    this
        .map(String::toCharArray)
        .map(CharArray::toList)
)

fun List<Char>.toPoints(x: Int) = this.mapIndexed { y, value -> Point(x, y, value) }