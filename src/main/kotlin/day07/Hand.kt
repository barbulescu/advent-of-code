package day07

private val labels = listOf('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2')

data class Hand(val cards: String) : Comparable<Hand> {
    private val distribution: Map<Char, Int> = cards
        .toCharArray()
        .groupBy { it }
        .mapValues { it.value.size }

    private val power = when {
        distribution.containsValue(5) -> 71
        distribution.containsValue(4) -> 67
        distribution.containsValue(3) && distribution.containsValue(2) -> 61
        distribution.containsValue(3) -> 59
        distribution.values.count { it == 2 } == 2 -> 53
        distribution.values.count { it == 2 } == 1 -> 47
        distribution.values.all { it == 1 } -> 43
        else -> 0
    }


    override fun compareTo(other: Hand): Int {
        val result = power - other.power
        if (result != 0) {
            return result
        }

        val a1 = cards.toCharArray()
        val a2 = other.cards.toCharArray()
        for(i in a1.indices) {
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