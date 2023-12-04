import utils.expectResult
import utils.readInput

private const val DAY = "Day05"

fun main() {
    // test if implementation meets criteria from the description, like:
    expectResult(1) {
        part1(readInput("${DAY}_1_test"))
    }
    expectResult(1) {
        part2(readInput("${DAY}_2_test"))
    }

    val data = readInput(DAY)
    println("#1 -> ${part1(data)}")
    println("#2 -> ${part2(data)}")
}

private fun part1(input: List<String>): Int {
    return -1
}

private fun part2(input: List<String>): Int {
    return -1
}