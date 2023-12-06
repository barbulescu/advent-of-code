package day05

data class CascadeMapping(
    val seed2soil: List<MappingItem>,
    val soil2fertilizer: List<MappingItem>,
    val fertilizer2water: List<MappingItem>,
    val water2light: List<MappingItem>,
    val light2temperature: List<MappingItem>,
    val temperature2humidity: List<MappingItem>,
    val humidity2location: List<MappingItem>
) {

    fun findLowestLocation(input: List<LongRange>) : Long {
        return input
            .asSequence()
            .flatMap { seed2soil.mergeWith(it) }
            .flatMap { soil2fertilizer.mergeWith(it) }
            .flatMap { fertilizer2water.mergeWith(it) }
            .flatMap { water2light.mergeWith(it) }
            .flatMap { light2temperature.mergeWith(it) }
            .flatMap { temperature2humidity.mergeWith(it) }
            .flatMap { humidity2location.mergeWith(it) }
            .map { it.first }
            .min()
    }
}

