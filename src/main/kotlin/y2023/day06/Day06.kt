package y2023.day06

import utils.FileData
import utils.expectLongResult

private val fileData = FileData(day = 6, year = 2023)

fun main() {
    // test if implementation meets criteria from the description, like:
    expectLongResult(288) {
        part1(fileData.readTestData(1))
    }
    expectLongResult(71503) {
        part2(fileData.readTestData(2))
    }

    val data = fileData.readData()

    println("#1 -> ${part1(data)}")
    println("#2 -> ${part2(data)}")
}

private fun part1(lines: List<String>): Long =
    lines.parseRaceRecords().countWinCases()

private fun part2(lines: List<String>): Long {
    require(lines.size == 2) { "expect 2 lines but got got $lines" }
    val race = Race(
        time = lines[0]
            .drop(5)
            .innerTrim()
            .toLong(),
        distance = lines[1]
            .drop(10)
            .innerTrim()
            .toLong()
    )
    return race.countWinCases()
}

private fun String.innerTrim() =
    this.toCharArray().filterNot { it == ' ' }.joinToString(separator = "")

val separatorRegex = Regex(" +")

fun List<String>.parseRaceRecords(): List<Race> {
    require(this.size == 2) { "expect 2 lines but got got $this" }
    val times = this[0].drop(5)
        .split(separatorRegex)
        .filterNot(String::isBlank)
        .map(String::toLong)
    val distances = this[1].drop(10)
        .split(separatorRegex)
        .filterNot(String::isBlank)
        .map(String::toLong)

    require(times.size == distances.size) { "times and distances count must be the same" }

    val raceRecords = times.zip(distances)
        .map { Race(it.first, it.second) }
    return raceRecords
}


fun List<Race>.countWinCases() = this
    .map(Race::countWinCases)
    .fold(1L) { acc, i -> acc * i }


data class Race(val time: Long, val distance: Long) {
    fun canBeatRecord(pressTime: Long) = (time - pressTime) * pressTime > distance

    fun countWinCases(): Long = (0..time)
        .asSequence()
        .filter { canBeatRecord(it) }
        .count()
        .toLong()
}

