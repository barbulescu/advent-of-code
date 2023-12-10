package day05

import utils.FileData
import utils.expectLongResult

private val fileData = FileData(5)

fun main() {
    // test if implementation meets criteria from the description, like:
    expectLongResult(35) {
        part1(fileData.readTestData(1))
    }
    expectLongResult(46) {
        part2(fileData.readTestData(2))
    }

    val data = fileData.readData()

    println("#1 -> ${part1(data)}")
    println("#2 -> ${part2(data)}")
}

private fun part1(input: List<String>): Long {
    val seeds = input.first().drop(6)
        .split(" ")
        .filterNot(String::isBlank)
        .map { it.toLong() }

    val mappingData = parseMappingData(input)
    return mappingData.findLowestLocation(seeds.asSequence())
}

private fun part2(lines: List<String>): Long {
    val seeds = lines.first()
        .drop(6)
        .split(" ")
        .asSequence()
        .filterNot(String::isBlank)
        .map { it.toLong() }
        .windowed(2,2)
        .map { it[0]..<it[1] + it[0] }
        .onEach { println(it) }
        .flatMap { it.asSequence() }

    val mappingData = parseMappingData(lines)
    return mappingData.findLowestLocation(seeds)
}

fun parseMappingData(lines: List<String>): MappingData {
    var sectionReset = false
    var sectionTitle: String? = null
    val sectionContent = mutableListOf<String>()
    val sections = mutableMapOf<String, List<MappingItem>>()
    lines.forEach { line ->
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
    return MappingData(
        sections.toMapping("seed-to-soil map:"),
        sections.toMapping("soil-to-fertilizer map:"),
        sections.toMapping("fertilizer-to-water map:"),
        sections.toMapping("water-to-light map:"),
        sections.toMapping("light-to-temperature map:"),
        sections.toMapping("temperature-to-humidity map:"),
        sections.toMapping("humidity-to-location map:"),
    )
}

fun parseSection(lines: List<String>): List<MappingItem> = lines.map(String::parseSectionLine)

private fun String.parseSectionLine(): MappingItem {
    val parts = this.split(" ")
    require(parts.size == 3) { "invalid section line: $this -> $parts" }
    val start = parts[1].toLong()
    val end = start + parts[2].toLong() - 1
    return MappingItem(start..end, parts[0].toLong())
}
