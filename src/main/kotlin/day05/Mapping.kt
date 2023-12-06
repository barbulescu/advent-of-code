package day05

fun List<MappingItem>.mergeWith(input: LongRange) : List<LongRange> {
    if (input.isEmpty()) {
        return emptyList()
    }
    if (input.first == input.last) {
        val result = (this
            .filter { input.first in it.source }
            .map { it.map(input.first) }
            .firstOrNull()
            ?: input.first)
        return listOf(result..result)
    }
    TODO("implement all merging cases")
}

data class MappingItem(val source: LongRange, val destinationStart: Long) {
    fun map(input: Long): Long {
        val index = input - source.first
        return destinationStart + index
    }
}

fun Map<String, List<MappingItem>>.toMapping(key: String) = this[key] ?: emptyList()