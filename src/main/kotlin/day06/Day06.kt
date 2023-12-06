package day06

import utils.FileData
import utils.expectLongResult
import utils.expectResult

private val fileData = FileData(6)

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

private fun part1(input: List<String>): Long {
    return input.parseRaceRecords().countAllWinCases()
}

private fun part2(input: List<String>): Long {
    require(input.size == 2) { "expect 2 lines but got got $input" }
    val race = Race(
        time = String(input[0].drop(5).toCharArray().filterNot { it == ' ' }.toCharArray()).toLong(),
         distance = String(input[1].drop(10).toCharArray().filterNot { it == ' ' }.toCharArray()).toLong()
    )
    return race.validWinCases()
}

val separatorRegex = Regex(" +")

fun List<String>.parseRaceRecords(): RaceRecords {
    require(this.size == 2) { "expect 2 lines but got got $this" }
    val times = this[0].drop(5)
        .split(separatorRegex)
        .filterNot(String::isBlank)
        .map(String::toInt)
    val distances = this[1].drop(10)
        .split(separatorRegex)
        .filterNot(String::isBlank)
        .map(String::toInt)

    require(times.size == distances.size) { "times and distances count must be the same" }

    val raceRecords = times.zip(distances)
        .map { Race(it.first.toLong(), it.second.toLong()) }
    return RaceRecords(raceRecords)
}

data class RaceRecords(val races: List<Race>) {
    fun countAllWinCases() = races.map { it.validWinCases() }.fold(1L) {acc, i -> acc * i }
}

data class Race(val time: Long, val distance: Long) {
    fun canBeatRecord(pressTime: Long) = (time - pressTime) * pressTime > distance

    fun validWinCases() : Long = (0..time)
        .asSequence()
        .filter { canBeatRecord(it) }
        .count()
        .toLong()
}

