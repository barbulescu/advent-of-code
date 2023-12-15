package day14

import utils.FileData
import utils.expectResult

private val fileData = FileData(14)

fun main() {
    // test if implementation meets criteria from the description, like:
    expectResult(136) {
        part1(fileData.readTestData(1))
    }
    expectResult(64) {
        part2(fileData.readTestData(2))
    }

    val data = fileData.readData()

    println("#1 -> ${part1(data)}")
    println("#2 -> ${part2(data)}")

    expectResult(107142) {
        part1(data)
    }
    expectResult(104815) {
        part2(data)
    }

}

private fun part1(lines: List<String>): Int = loadSum1(lines)

private fun part2(lines: List<String>): Int = loadSum2(lines)

fun loadSum1(lines: List<String>): Int = lines.toPlatform()
    .tilt()
    .totalLoad()

fun loadSum2(lines: List<String>): Int {
    val maxCycles = 1_000_000_000
    var platform = lines.toPlatform()

    var totalLoad: Int? = null
    val cycleForState = mutableMapOf(platform to 0)
    val stateForCycle = mutableMapOf(0 to platform)
    val loadForState = mutableMapOf(platform to 0)
    for (currentCycle in 1..maxCycles) {
        repeat(4) {
            platform = platform.tilt().rotate()
        }
        if (platform in cycleForState) {
            val repeatedCycle = cycleForState.getValue(platform)
            val finalCycle = repeatedCycle - 1 + (maxCycles - currentCycle + 1) % (currentCycle - repeatedCycle)
            val finalState = stateForCycle.getValue(finalCycle)
            totalLoad = finalState.totalLoad()
            break
        } else {
            cycleForState[platform] = currentCycle
            stateForCycle[currentCycle] = platform
            loadForState[platform] = platform.totalLoad()
        }
    }

    return totalLoad ?: platform.totalLoad()
}

private fun List<String>.toPlatform() = Platform(this.map(String::toCharArray).toTypedArray())

data class Platform(val state: Array<CharArray>) {
    fun tilt(): Platform {
        val state = this.state
        val numRows = state.size
        val numCols = state[0].size
        val cols = state.first().indices.map { colIndex ->
            state
                .mapIndexed { rowIndex, row -> row[colIndex] to rowIndex }
                .filter { it.first != '.' }
        }
        return cols
            .calculateNewState(numRows, numCols)
            .let(::Platform)
    }

    fun rotate(): Platform {
        val state = this.state
        val numRows = state.size
        val numCols = state[0].size
        val newState = Array(numCols) { CharArray(numRows) }
        for (row in 0 until numRows) {
            for (col in 0 until numCols) {
                newState[col][numRows - 1 - row] = state[row][col]
            }
        }
        return Platform(newState)
    }

    fun totalLoad(): Int = state.mapIndexed { index, row ->
        row.count { it == 'O' } * (state.size - index)
    }.sum()

    override fun equals(other: Any?): Boolean = (other as? Platform)?.state.contentDeepEquals(state)
    override fun hashCode(): Int = state.contentDeepHashCode()
    override fun toString() = state.joinToString("\n") { it.joinToString("") }
}

private fun List<List<Pair<Char, Int>>>.calculateNewState(numRows: Int, numCols: Int): Array<CharArray> =
    Array(numRows) { CharArray(numCols) { '.' } }.also { newState ->
        forEachIndexed { colIndex, col ->
            var row = 0
            for ((cell, rowIndex) in col) {
                when (cell) {
                    '#' -> {
                        newState[rowIndex][colIndex] = '#'
                        row = rowIndex + 1
                    }

                    'O' -> {
                        newState[row][colIndex] = 'O'
                        row++
                    }
                }
            }
        }
    }
