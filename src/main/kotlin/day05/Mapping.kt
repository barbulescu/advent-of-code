package day05

fun List<MappingItem>.mergeWith(input: LongRange): List<LongRange> {
    if (input.isEmpty()) {
        return emptyList()
    }
    if (input.first == input.last) {
        val result = this
            .filter { input.first in it.source }
            .map { it.map(input.first) }
            .firstOrNull()
            ?: input.first
        return listOf(result..result)
    }

    val resultInside = this
        .filter { it.isInside(input) }
        .map { it.map(input.first)..it.map(input.last) }
        .firstOrNull()

    if (resultInside != null) {
        return listOf(resultInside)
    }

    if (all { it.isOutside(input) }) {
        return listOf(input)
    }

    val result = mutableListOf<LongRange>()
    var remainingRange = input
    this
        .dropWhile { it.source.last < remainingRange.first }
        .forEach { println(it.source) }

    TODO("case not supported")
}

data class MappingItem(val source: LongRange, val destinationStart: Long) {
    fun map(input: Long): Long {
        val index = input - source.first
        return destinationStart + index
    }

    fun isInside(input: LongRange): Boolean =
        input.first >= source.first && input.last <= source.last

    fun isOutside(input: LongRange): Boolean =
        input.first !in source && input.last !in source
}

fun Map<String, List<MappingItem>>.toMapping(key: String) = this[key] ?: emptyList()