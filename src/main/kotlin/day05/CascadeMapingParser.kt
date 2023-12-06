package day05

fun parseMappingData(input: List<String>): CascadeMapping {
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
    return CascadeMapping(
        sections.toMapping("seed-to-soil map:"),
        sections.toMapping("soil-to-fertilizer map:"),
        sections.toMapping("fertilizer-to-water map:"),
        sections.toMapping("water-to-light map:"),
        sections.toMapping("light-to-temperature map:"),
        sections.toMapping("temperature-to-humidity map:"),
        sections.toMapping("humidity-to-location map:"),
    )
}

fun parseSection(lines: List<String>): List<MappingItem> = lines.map(String::parseSectionLine).sortedBy { it.source.first }

private fun String.parseSectionLine(): MappingItem {
    val parts = this.split(" ")
    require(parts.size == 3) { "invalid section line: $this -> $parts" }
    val start = parts[1].toLong()
    val end = start + parts[2].toLong() - 1
    return MappingItem(start..end, parts[0].toLong())
}
