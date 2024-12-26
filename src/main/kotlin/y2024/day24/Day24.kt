package y2024.day24

class Day24(lines: List<String>) {
    private val inputs: MutableMap<String, Int> = lines
        .takeWhile(String::isNotBlank)
        .map { it.split(": ") }
        .associate { (a, b) -> a to b.toInt() }
        .toMutableMap()

    private val gates: List<Gate> = lines
        .takeLastWhile(String::isNotBlank)
        .map { it.split(" -> ") }
        .associate { (a, b) -> b to a }
        .mapValues { (_, v) -> v.split(" ") }
        .mapValues { (k, v) -> v.toGate(k) }
        .values
        .toList()

    private val lastZ = gates
        .map { it.c }
        .filter { it.startsWith('z') }
        .maxOf { it }


    fun part1() = run(gates, inputs)

    fun part2(): String {
        val nxz = gates
            .filterNot { it.isXor() }
            .filter(Gate::cz)
            .filter { it.c != lastZ }
        val xnz = gates
            .filter { it.isXor() }
            .filterNot(Gate::cz)
            .filterNot(Gate::axy)
            .filterNot(Gate::bxy)

        xnz
            .map { z -> z to nxz.firstOrNull { it.c == gates.firstZThatUses(z.c) } }
            .filter { it.second != null }
            .forEach { (i, j) ->
                val temp = i.c
                i.c = j!!.c
                j.c = temp
            }

        val xy = inputs.toLong('x') + inputs.toLong('y')
        val falseCarry = (xy xor run(gates, inputs))
            .countTrailingZeroBits()
            .toString()
        val ab = gates
            .filter { it.a.endsWith(falseCarry) }
            .filter { it.b.endsWith(falseCarry) }
        return (nxz + xnz + ab)
            .map { it.c }
            .sorted()
            .joinToString(separator = ",")
    }

    private fun List<Gate>.firstZThatUses(c: String): String? {
        val x = filter { it.a == c || it.b == c }
        return x.find(Gate::cz)?.c?.previousOutput() ?: x.firstNotNullOfOrNull { firstZThatUses(it.c) }
    }

    private fun run(gates: List<Gate>, inputs: MutableMap<String, Int>): Long {
        val exclude = HashSet<Gate>()

        while (exclude.size != gates.size) {
            val available = gates
                .filterNot { it in exclude }
                .filter { a ->
                    gates.none { b ->
                        (a.a == b.c || a.b == b.c) && b !in exclude
                    }
                }
            for ((a, b, op, c) in available) {
                val v1 = inputs.getOrDefault(a, 0)
                val v2 = inputs.getOrDefault(b, 0)
                inputs[c] = op.eval(v1, v2)
            }
            exclude.addAll(available)
        }

        return inputs.toLong('z')
    }

    private fun MutableMap<String, Int>.toLong(type: Char) = this
        .filterKeys { it.startsWith(type) }
        .toList()
        .sortedBy { it.first }
        .map { it.second }
        .joinToString(separator = "")
        .reversed()
        .toLong(2)

    private fun List<String>.toGate(c: String): Gate = Gate(this[0], this[2], Operation.valueOf(this[1]), c)

    enum class Operation(val eval: (Int, Int) -> Int) {
        AND(Int::and),
        OR(Int::or),
        XOR(Int::xor)
    }

    private data class Gate(val a: String, val b: String, val op: Operation, var c: String) {
        fun isXor() = op == Operation.XOR
        fun cz() = c.first() == 'z'
        fun axy() = a.first() in "xy"
        fun bxy() = b.first() in "xy"
    }

    private fun String.previousOutput() = this.take(1) + (this.drop(1).toInt() - 1).toString().padStart(2, '0')
}
