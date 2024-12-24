package y2024.day24

class Day24(lines: List<String>) {
    private val inputs: Map<String, Int> = lines
        .takeWhile { it.isNotBlank() }
        .map { it.split(": ") }
        .associate { (a, b) -> a to b.toInt() }

    private val gates: MutableMap<String, Any> = lines.takeLastWhile { it.isNotBlank() }
        .map { it.split(" -> ") }
        .associate { (a, b) -> b to a }
        .mapValues { (_, v) -> v.split(" ") }
        .mapValues { (_, v) -> Operation(v[0], v[1], v[2]) }
        .toMutableMap()

    fun part1(): Long = gates.keys
        .filter { it.startsWith("z") }
        .sorted()
        .reversed()
        .map { calculate(it) }
        .joinToString(separator = "") { it.toString() }
        .toLong(2)

    private fun calculate(output: String): Int {
        val value = gates[output] ?: inputs.getValue(output)
        if (value is Int) {
            return value
        }
        require(value is Operation) { "Invalid value: $value" }
        val a = calculate(value.a)
        val b = calculate(value.b)
        val result = when (value.op) {
            "AND" -> a and b
            "OR" -> a or b
            "XOR" -> a xor b
            else -> error("Invalid operation: ${value.op}")
        }
        gates[output] = result
        return result
    }

    fun part2(): String = TODO("Not implemented")

    data class Operation(val a: String, val op: String, val b: String)
}
