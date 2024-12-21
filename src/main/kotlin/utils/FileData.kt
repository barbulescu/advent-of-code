package utils

import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.io.path.readText

class FileData(day: Int, year: Int) {
    private val formattedDay = "%02d".format(day)
    private val path = "src/main/resources/y$year/day$formattedDay"

    fun resultsAsLong() = results()
        .map(String::toLong)

    fun results() = Path("$path/results.txt")
            .readLines()

    fun readTestData(part: Int) = Path("$path/test_data_${part}.txt")
        .readLines()

    fun readData() = Path("$path/data.txt")
        .readText()
        .toLines()
}
