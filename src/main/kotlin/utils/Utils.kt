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
    val callerClass = Thread.currentThread().stackTrace
        .map { it.className }
        .first { it.startsWith("y2") }
    val parts = callerClass.split(".")
    require(parts.size == 3) { "Expected something like `y2024.day04.Day04Kt`" }
    val year = parts[0].drop(1).toInt()
    val day = parts[1].drop(3).dropWhile { it == '0' }.toInt()
    val fileData = FileData(day, year)
    val results = fileData.results()
    require(results.size == 4) { "expect 4 results: $results" }

    expectLongResult(results[0]) {
        val lines = fileData.readTestData(1)
        part1Block(lines)
    }

    expectLongResult(results[1]) {
        val lines = fileData.readTestData(2)
        part2Block(lines)
    }

    val lines = fileData.readData()

    val part1 = part1Block(lines)
    require(part1 == results[2]) {
        "expected ${results[2]} but got $part1 for first part"
    }

    val part2 = part2Block(lines)
    require(part2 == results[3]) {
        "expected ${results[3]} but got $part2 for second part"
    }

}
