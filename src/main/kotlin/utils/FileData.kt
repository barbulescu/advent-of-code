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

    fun readTestData(part: Int, index: Int? = null): List<String> {
        val indexSection = index?.let { "_$it" } ?: ""
        return Path("$path/test_data_${part}${indexSection}.txt")
            .readText()
            .toLines()
    }

    fun readData() = Path("$path/data.txt")
        .readText()
        .toLines()
}
