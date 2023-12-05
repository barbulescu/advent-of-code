package day05

data class SeedData(
    val seeds: List<Long>,
    val seed2soil: Mapping,
    val soil2fertilizer: Mapping,
    val fertilizer2water: Mapping,
    val water2light: Mapping,
    val light2temperature: Mapping,
    val temperature2humidity: Mapping,
    val humidity2location: Mapping
) {
    fun findLowestLocation() : Long {
        return seeds
            .asSequence()
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