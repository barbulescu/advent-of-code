package y2024.day23

class Day23(input: List<String>) {
    private val connections: Map<String, Set<String>> = input
        .map { it.split("-") }
        .onEach { require(it.size == 2) { "Invalid computer connection: $it" } }
        .flatMap { (a, b) -> listOf(a to b, b to a) }
        .groupBy({ it.first }, { it.second })
        .mapValues { it.value.toSet() }

    fun part1(): Long = connections
        .entries
        .asSequence()
        .flatMap { (a, value) ->
            value
                .filter { b -> b > a }
                .flatMap { b ->
                    commonSetOf(a, b)
                        .filter { c -> c > b }
                        .map { c -> setOf(a, b, c) }
                }
        }
        .count { computers -> computers.any { it.startsWith("t") } }
        .toLong()

    private fun commonSetOf(a: String, b: String) = connections.getValue(a)
        .intersect(connections.getValue(b))

    fun part2(): String =
        findSolution(setOf(), connections.keys.toMutableSet(), mutableSetOf())
            ?.sorted()
            ?.joinToString(",")
            ?: error("Unable to find a solution")


    private fun findSolution(
        r: Set<String>,
        p: MutableSet<String>,
        x: MutableSet<String>,
    ): Set<String>? {
        if (p.isEmpty() && x.isEmpty()) {
            return r
        }
        val pivot = (p + x)
            .maxByOrNull { connections[it]?.size ?: 0 }
            ?: return null
        val neighbors = connections.getOrDefault(pivot, emptySet())
        val toExplore = (p - neighbors)

        return toExplore
            .mapNotNull { neighbor ->
                val newR = r + neighbor
                val newP = p.intersect(connections.getOrDefault(neighbor, emptySet())).toMutableSet()
                val newX = x.intersect(connections.getOrDefault(neighbor, emptySet())).toMutableSet()
                val solution = findSolution(newR, newP, newX)
                p -= neighbor
                x += neighbor
                solution
            }
            .maxByOrNull { it.size }
    }
}
