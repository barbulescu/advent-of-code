package y2024.day17

class Day17(private val input: List<String>) {
    private val program = input
        .last()
        .drop(9)
        .split(",")
        .map(String::toInt)

    fun part1() = execute(input.toCPU())
        .joinToString(",")

    fun part2(): Long {
        fun findA(currentA: Long = 0): Long? = (currentA..currentA + 8)
            .firstNotNullOfOrNull { a ->
                val response = execute(CPU(a, 0, 0))

                if (program.takeLast(response.size) == response) {
                    if (program == response) {
                        a
                    } else {
                        findA(maxOf(a shl 3, 8))
                    }
                } else {
                    null
                }
            }

        return findA() ?: error("No solution")
    }

    private fun execute(cpu: CPU): List<Int> {
        val result = mutableListOf<Int>()

        var pc = 0
        while (pc in program.indices) {
            pc = cpu.process(program[pc], program[pc + 1], result) ?: (pc + 2)
        }

        return result
    }

    private fun List<String>.toCPU(): CPU {
        val registers = this
            .takeWhile(String::isNotBlank)
            .map { it.drop(12) }

        return CPU(
            a = registers[0].toLong(),
            b = registers[1].toLong(),
            c = registers[2].toLong()
        )
    }

    data class CPU(var a: Long, var b: Long, var c: Long) {
        fun process(opcode: Int, operand: Int, result: MutableList<Int>): Int? {
            when (opcode) {
           0 -> a = a shr combo(operand).toInt()
                1 -> b = b xor operand.toLong()
                2 -> b = combo(operand) % 8
                3 -> if (a != 0L) {
                    return operand
                }
                4 -> b = b xor c
                5 -> result.add((combo(operand) % 8).toInt())
                6 -> b = a shr combo(operand).toInt()
                7 -> c = a shr combo(operand).toInt()            }
            return null
        }

        private fun combo(operand: Int): Long = when (operand) {
            in 0..3 -> operand.toLong()
            4 -> a
            5 -> b
            6 -> c
            else -> error("Invalid operand: $operand")
        }
    }
}
