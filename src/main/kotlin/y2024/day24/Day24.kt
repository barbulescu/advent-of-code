package y2024.day24

class Day24(private val lines: List<String>) {
    fun part1(): Long {
        val inputs: Map<String, Int> = lines
            .takeWhile(String::isNotBlank)
            .map { it.split(": ") }
            .associate { (a, b) -> a to b.toInt() }

        val gates: MutableMap<String, Any> = lines
            .takeLastWhile(String::isNotBlank)
            .map { it.split(" -> ") }
            .associate { (a, b) -> b to a }
            .mapValues { (_, v) -> v.split(" ") }
            .mapValues { (_, v) -> Operation(v[0], v[1], v[2]) }
            .toMutableMap()

        return runCircuit(gates, inputs)
    }

    fun part2(): String = TODO("Not implemented")

    private fun runCircuit(gates: MutableMap<String, Any>, inputs: Map<String, Int>) = gates.keys
        .filter { it.startsWith("z") }
        .sorted()
        .reversed()
        .map { calculate(gates, inputs, it) }
        .joinToString(separator = "") { it.toString() }
        .toLong(2)

    private fun calculate(gates: MutableMap<String, Any>, inputs: Map<String, Int>, output: String): Int {
        val value = gates[output] ?: inputs.getValue(output)
        if (value is Int) {
            return value
        }
        require(value is Operation) { "Invalid value: $value" }
        val a = calculate(gates, inputs, value.a)
        val b = calculate(gates, inputs, value.b)
        val result = when (value.op) {
            "AND" -> a and b
            "OR" -> a or b
            "XOR" -> a xor b
            else -> error("Invalid operation: ${value.op}")
        }
        gates[output] = result
        return result
    }

    data class Operation(val a: String, val op: String, val b: String)
}
