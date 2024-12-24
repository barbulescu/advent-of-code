package y2024.day24

class Day24(private val lines: List<String>) {
    fun part1(): Long {
        val inputs: Map<String, Boolean> = lines
            .takeWhile(String::isNotBlank)
            .map { it.split(": ") }
            .associate { (a, b) -> a to (b == "1") }

        val gates: MutableMap<String, Any> = lines
            .takeLastWhile(String::isNotBlank)
            .map { it.split(" -> ") }
            .associate { (a, b) -> b to a }
            .mapValues { (_, v) -> v.split(" ") }
            .mapValues { (k, v) -> v.toOperation(k) }
            .toMutableMap()

        return runCircuit(gates, inputs)
    }

    private fun List<String>.toOperation(c: String): Operation {
        val eval = when (this[1]) {
            "AND" -> Boolean::and
            "OR" -> Boolean::or
            "XOR" -> Boolean::xor
            else -> error("Invalid operation: ${this[1]}")
        }

        return Operation(this[0], this[2], c, eval)
    }

    fun part2(): String = TODO("Not implemented")

    private fun runCircuit(gates: MutableMap<String, Any>, inputs: Map<String, Boolean>) = gates.keys
        .filter { it.startsWith("z") }
        .sorted()
        .reversed()
        .map { calculate(gates, inputs, it) }
        .map { if (it) 1 else 0 }
        .joinToString(separator = "") { it.toString() }
        .toLong(2)

    private fun calculate(gates: MutableMap<String, Any>, inputs: Map<String, Boolean>, output: String): Boolean {
        val value = gates[output] ?: inputs.getValue(output)
        if (value is Boolean) {
            return value
        }
        require(value is Operation) { "Invalid value: $value" }
        val a = calculate(gates, inputs, value.a)
        val b = calculate(gates, inputs, value.b)
        val result = value.eval(a, b)
        gates[output] = result
        return result
    }

    data class Operation(val a: String, val b: String, val c: String, val eval: (Boolean, Boolean) -> Boolean)
}
