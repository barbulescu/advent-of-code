package utils

class Grid<T>(private val data: List<List<T>>) {
    fun asPoints() = data
        .asSequence()
        .flatMapIndexed { x, chars -> chars.toPoints(x) }

    fun neighbours(x: Int, y: Int) = sequenceOf(
        pointAt(x - 1, y - 1),
        pointAt(x - 1, y),
        pointAt(x - 1, y + 1),
        pointAt(x, y - 1),
        pointAt(x, y + 1),
        pointAt(x + 1, y - 1),
        pointAt(x + 1, y),
        pointAt(x + 1, y + 1),
    )
        .filterNotNull()

    private fun pointAt(x: Int, y: Int): Point2D? {
        if (x < 0 || x >= data.size) {
            return null
        }
        if (y < 0 || y >= data[0].size) {
            return null
        }
        return Point2D(x, y)
    }

    fun getValue(point: Point2D): T = data[point.x][point.y]
}

fun <T> List<String>.toGrid(mapper: (Char) -> T) = Grid(
    this
        .map(String::toCharArray)
        .map { it.map(mapper).toList() }
)

fun <T> List<T>.toPoints(x: Int) = List(this.size) { y -> Point2D(x, y) }

fun List<String>.toArea(): Map<Point2D, Char> {
    return mutableMapOf<Point2D, Char>().also {
        forEachIndexed { y, row ->
            row.forEachIndexed { x, char ->
                it[Point2D(x, y)] = char
            }
        }
    }
}

fun List<CharArray>.hasValueAt(point: Point2D, value: Char) = this[point.y][point.x] == value

fun List<CharArray>.findValue(value: Char): Point2D =
    flatMapIndexed { y, line ->
        line.mapIndexed { x, c ->
            if (c == value) {
                Point2D(x, y)
            } else {
                null
            }
        }
    }
        .filterNotNull()
        .single()
