package day07

data class Hand(val cards: String, val labels: List<Char>, val powerCalculator: (Map<Char, Int>) -> Int) :
    Comparable<Hand> {
    private val distribution: Map<Char, Int> = cards
        .toCharArray()
        .groupBy { it }
        .mapValues { it.value.size }

    private val power = powerCalculator(distribution)


    override fun compareTo(other: Hand): Int {
        val result = power - other.power
        if (result != 0) {
            return result
        }

        val a1 = cards.toCharArray()
        val a2 = other.cards.toCharArray()
        for (i in a1.indices) {
            val charResult = compare(a1[i], a2[i])
            if (charResult != 0) {
                return charResult
            }
        }
        return 0
    }

    private fun compare(c1: Char, c2: Char): Int {
        val index1 = labels.indexOf(c1)
        val index2 = labels.indexOf(c2)
        require(index1 != -1) { "invalid char $c1" }
        require(index2 != -1) { "invalid char $c2" }
        return index2 - index1
    }
}

fun Map<Char, Int>.calculatePowerPart1(): Int = when {
    containsValue(5) -> 71
    containsValue(4) -> 67
    containsValue(3) && containsValue(2) -> 61
    containsValue(3) -> 59
    values.count { it == 2 } == 2 -> 53
    values.count { it == 2 } == 1 -> 47
    values.all { it == 1 } -> 43
    else -> 0
}

fun Map<Char, Int>.calculatePowerPart2(): Int {
    val jCount = getOrDefault('J', 0)
    if (jCount == 0) {
        return this.calculatePowerPart1()
    }
    return this.filterNot { it.key == 'J' }
        .calculatePowerPart2(jCount)
}

private fun Map<Char, Int>.calculatePowerPart2(jCount: Int): Int {
    if (jCount == 5) {
        return 71
    }
    val pairs = values.count { it == 2 }
    val max = values.max()
    return when {
        max + jCount == 5 -> 71
        max + jCount == 4 -> 67

        containsValue(3) && containsValue(2) -> 61
        pairs == 2 && jCount == 1 -> 61
        containsValue(2) && containsValue(1) && jCount == 2 -> 61

        containsValue(3) -> 59
        containsValue(2) && jCount == 1 -> 59
        containsValue(1) && jCount == 2 -> 59
        pairs == 2 -> 53
        pairs == 1 && jCount == 1 -> 53
        pairs == 1 -> 47
        pairs == 0 && jCount == 1 -> 47
        values.all { it == 1 } -> 43
        else -> 0
    }
}