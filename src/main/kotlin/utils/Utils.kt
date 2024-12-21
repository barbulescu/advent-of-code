package utils

import java.math.BigInteger
import java.security.MessageDigest

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')


fun expectResult(expected: Int, processor: () -> Int) {
    val actual = processor()
    if (actual != expected) {
        error("Expected $expected but received $actual")
    }
}

fun expectLongResult(expected: Long, processor: () -> Long) {
    val actual = processor()
    if (actual != expected) {
        error("Expected $expected but received $actual")
    }
}

fun executeDay(part1Block: List<String>.() -> Long, part2Block: List<String>.() -> Long) {
    val fileData = fileData()
    val results = fileData.results()
    require(results.size == 4) { "expect 4 results: $results" }

    println("part 1 - test")
    expectLongResult(results[0]) {
        part1Block(fileData.readTestData(1))
    }

    val lines = fileData.readData()

    println("part 1 - data")
    val part1 = part1Block(lines)
    val expected1 = results[1]
    require(part1 == expected1) {
        "expected $expected1 but got $part1 for first part 1"
    }

    println("part 2 - test")
    expectLongResult(results[2]) {
        part2Block(fileData.readTestData(2))
    }

    println("part 2 - data")
    val part2 = part2Block(lines)
    val expected2 = results[3]
    require(part2 == expected2) {
        "expected $expected2 but got $part2 for second part 2"
    }

}

fun withFileData(block: FileData.() -> Unit) {
    block(fileData())
}

private fun fileData(): FileData {
    val callerClass = Thread.currentThread().stackTrace
        .map { it.className }
        .first { it.startsWith("y2") }
    val parts = callerClass.split(".")
    require(parts.size == 3) { "Expected something like `y2024.day04.Day04Kt`" }
    val year = parts[0].drop(1).toInt()
    val day = parts[1].drop(3).dropWhile { it == '0' }.toInt()
    val fileData = FileData(day, year)
    return fileData
}

fun String.toLines() = this.lines()
    .filterNot(String::isBlank)
    .map(String::trim)
