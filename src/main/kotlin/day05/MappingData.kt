package day05

data class MappingData(
    val seed2soil: Mapping,
    val soil2fertilizer: Mapping,
    val fertilizer2water: Mapping,
    val water2light: Mapping,
    val light2temperature: Mapping,
    val temperature2humidity: Mapping,
    val humidity2location: Mapping
) {
    fun findLowestLocation(seeds: Sequence<Long>): Long {
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

    fun findLowestLocation(input: List<LongRange>) : Long {
        return input
            .asSequence()
            .flatMap { seed2soil.mergeWith(it) }
//            .flatMap { soil2fertilizer.mergeWith(it) }
//            .flatMap { fertilizer2water.mergeWith(it) }
//            .flatMap { water2light.mergeWith(it) }
//            .flatMap { light2temperature.mergeWith(it) }
//            .flatMap { temperature2humidity.mergeWith(it) }
//            .flatMap { humidity2location.mergeWith(it) }
            .onEach { println(">> $it") }
            .flatMap { it }
            .min()
    }
}

