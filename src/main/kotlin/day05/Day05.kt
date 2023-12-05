package day05

import utils.FileData
import utils.expectLongResult
import utils.expectResult

private val fileData = FileData(5)

fun main() {
    // test if implementation meets criteria from the description, like:
    expectLongResult(35) {
        part1(fileData.readTestData(1))
    }
//    expectResult(1) {
//        part2(fileData.readTestData(2))
//    }

    val data = fileData.readData()

    println("#1 -> ${part1(data)}")
    println("#2 -> ${part2(data)}")
}

private fun part1(input: List<String>): Long {
    return parseData(input)
        .findLowestLocation()

}

private fun part2(input: List<String>): Int {
    return -1
}

fun parseData(input: List<String>): SeedData {
    val seeds = parseSeedLine(input.first())
    var sectionReset = false
    var sectionTitle: String? = null
    val sectionContent = mutableListOf<String>()
    val sections = mutableMapOf<String, List<MappingItem>>()
    input.forEach { line ->
        if (line.isBlank()) {
            sectionReset = true
            if (sectionTitle != null) {
                sections[sectionTitle!!] = parseSection(sectionContent)
            }
            sectionTitle = null
            sectionContent.clear()
            return@forEach
        }
        if (sectionReset) {
            sectionReset = false
            sectionTitle = line
            return@forEach
        }
        sectionContent.add(line)
    }
    if (sectionTitle != null) {
        sections[sectionTitle!!] = parseSection(sectionContent)
    }
    return SeedData(
        seeds,
        sections.toMapping("seed-to-soil map:"),
        sections.toMapping("soil-to-fertilizer map:"),
        sections.toMapping("fertilizer-to-water map:"),
        sections.toMapping("water-to-light map:"),
        sections.toMapping("light-to-temperature map:"),
        sections.toMapping("temperature-to-humidity map:"),
        sections.toMapping("humidity-to-location map:"),
    )
}

fun parseSeedLine(line: String): List<Long> =
    line.drop(6)
        .split(" ")
        .filterNot(String::isBlank)
        .map { it.toLong() }

fun parseSection(lines: List<String>): List<MappingItem> = lines.map(String::parseSectionLine)

private fun String.parseSectionLine(): MappingItem {
    val parts = this.split(" ")
    require(parts.size == 3) { "invalid section line: $this -> $parts" }
    val start = parts[1].toLong()
    val end = start + parts[2].toLong() - 1
    return MappingItem(start..end, parts[0].toLong())
}
