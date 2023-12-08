package day08

import utils.FileData
import utils.expectResult

private val fileData = FileData(8)

fun main() {
    // test if implementation meets criteria from the description, like:
    expectResult(2) {
        part1(fileData.readTestData(1))
    }
//    expectResult(1) {
//        part2(fileData.readTestData(2))
//    }

    val data = fileData.readData()

    println("#1 -> ${part1(data)}")
//    println("#2 -> ${part2(data)}")
}

private fun part1(input: List<String>): Int {
    val instruction = Instruction(input[0])

    val map = input.drop(2).asSequence()
        .associate {
            val key = it.substring(0, 3)
            val left = it.substring(7, 10)
            val right = it.substring(12, 15)
            key to (left to right)
        }

    var steps = 0
    var next = "AAA"

    while (steps < 100_000) {
        val entry = map[next] ?: error("unable to find a value for $next")
        next = instruction.next(entry)
        steps += 1
        if (next == "ZZZ") {
            break
        }
    }

    return steps
}

private fun part2(input: List<String>): Int {
    return -1
}


data class Line(val key: String, val left: String, val right: String)

data class Instruction(val sequence: String) {
    private var currentPosition = 0

    fun next(pair: Pair<String, String>): String {
        val value = sequence.elementAt(currentPosition)
        val result = when (value) {
            'L' -> pair.first
            'R' -> pair.second
            else -> error("Unknown instruction: $value")
        }
        currentPosition += 1
        if (currentPosition >= sequence.length) {
            currentPosition = 0
        }
        return result
    }
}

