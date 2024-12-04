package utils

import kotlin.io.path.Path
import kotlin.io.path.readLines

class FileData(day: Int, year: Int) {
    private val formattedDay = "%02d".format(day)
    private val path = "src/main/resources/y$year/day$formattedDay"

    fun results() = Path("$path/results.txt").readLines().map { it.toLongOrNull() ?: -1}
    fun readTestData(part: Int) = Path("$path/test_data_${part}.txt").readLines()
    fun readData() = Path("$path/data.txt").readLines()
}
