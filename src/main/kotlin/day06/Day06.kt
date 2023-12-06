package day06

import utils.FileData
import utils.expectResult

private val fileData = FileData(6)

fun main() {
    // test if implementation meets criteria from the description, like:
    expectResult(288) {
        part1(fileData.readTestData(1))
    }
//    expectResult(1) {
//        part2(fileData.readTestData(2))
//    }

    val data = fileData.readData()

    println("#1 -> ${part1(data)}")
    println("#2 -> ${part2(data)}")
}

private fun part1(input: List<String>): Int {
    return input.parseRaceRecords().countAllWinCases()
}

private fun part2(input: List<String>): Int {
    return -1
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
        .map { Race(it.first, it.second) }
    return RaceRecords(raceRecords)
}

data class RaceRecords(val races: List<Race>) {
    fun countAllWinCases() = races.map { it.validWinCases() }.fold(1) {acc, i -> acc * i }
}

data class Race(val time: Int, val distance: Int) {
    fun canBeatRecord(pressTime: Int) = (time - pressTime) * pressTime > distance

    fun validWinCases() : Int = (0..time)
        .asSequence()
        .filter { canBeatRecord(it) }
        .count()
}

