package utils

import kotlin.io.path.Path
import kotlin.io.path.readLines

class FileData(day: Int, year: Int) {
    private val formattedDay = "%02d".format(day)
    private val path = "src/main/resources/y$year/day$formattedDay"

    fun readTestData(part: Int) = Path("$path/Day${formattedDay}_${part}_test.txt").readLines()
    fun readData() = Path("$path/Day${formattedDay}.txt").readLines()
}
