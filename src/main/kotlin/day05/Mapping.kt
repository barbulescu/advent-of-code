package day05


data class Mapping(private val data: List<MappingItem>) {
    fun map(input: Long): Long = data
        .firstNotNullOfOrNull { it.map(input) }
        ?: input

    fun mergeWith(input: LongRange): List<LongRange> {
        return data
            .flatMap { it.mergeWith(input) }
            .distinct()

    }
}

data class MappingItem(val source: LongRange, val destinationStart: Long) {
    fun map(input: Long): Long? {
        if (input !in source) {
            return null
        }
        return mapSafe(input)
    }

    private fun mapSafe(input: Long): Long {
        val index = input - source.first
        return destinationStart + index
    }

    fun mergeWith(input: LongRange) : List<LongRange> {
        val response = when {
            input.first < source.first && input.last > source.first -> listOf(
                input.first..<source.first,
                mapSafe(source.first)..mapSafe(input.last)
            )

            input.first < source.last && input.last > source.last -> listOf(
                mapSafe(input.first)..mapSafe(source.last),
                source.last + 1..input.last
            )

            input.first in source && input.last in source -> listOf(mapSafe(input.first)..mapSafe(input.last))
            else -> listOf(input)
        }
//        println("$input -> $response")
        return response

    }
}

fun Map<String, List<MappingItem>>.toMapping(key: String) = Mapping(this[key] ?: emptyList())