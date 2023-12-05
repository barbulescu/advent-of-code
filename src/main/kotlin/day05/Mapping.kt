package day05

data class Mapping(private val data: List<MappingItem>) {
    fun map(input: Long): Long = data
        .firstNotNullOfOrNull { it.map(input) }
        ?: input
}

data class MappingItem(val source: LongRange, val destinationStart: Long) {
    fun map(input: Long): Long? {
        if (input !in source) {
            return null
        }
        val index = input - source.first
        return destinationStart + index
    }
}

fun Map<String, List<MappingItem>>.toMapping(key: String) = Mapping(this[key]!!)