package day05

import java.util.stream.Stream
import kotlin.streams.asSequence

data class MappingData(
    val seed2soil: Mapping,
    val soil2fertilizer: Mapping,
    val fertilizer2water: Mapping,
    val water2light: Mapping,
    val light2temperature: Mapping,
    val temperature2humidity: Mapping,
    val humidity2location: Mapping
) {
    fun findLowestLocation(seeds: Sequence<Long>) : Long {
        return seeds
            .map(seed2soil::map)
            .map(soil2fertilizer::map)
            .map(fertilizer2water::map)
            .map(water2light::map)
            .map(light2temperature::map)
            .map(temperature2humidity::map)
            .map(humidity2location::map)
            .min()
    }
}

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