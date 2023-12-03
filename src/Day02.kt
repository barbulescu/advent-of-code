import utils.expectResult
import utils.readInput

fun main() {
    // test if implementation meets criteria from the description, like:
    expectResult(8) {
        part1(readInput("Day02_1_test"))
    }
    expectResult(2286) {
        part2(readInput("Day02_2_test"))
    }

    val data = readInput("Day02")
    println("#1 -> ${part1(data)}")
    println("#2 -> ${part2(data)}")
}

private const val MAX_RED = 12
private const val MAX_GREEN = 13
private const val MAX_BLUE = 14

private val mainGroupsRegex = Regex("[:;]")
private val setRegex = Regex("\\s*(\\d+)\\s+(\\w+)")

private fun part1(input: List<String>): Int {
    return input.asSequence()
        .map(String::parseLine)
        .filter(Game::isValid)
        .map(Game::id)
        .sum()
}

private fun part2(input: List<String>): Int {
    return input.asSequence()
        .map(String::parseLine)
        .map(Game::power)
        .sum()
}

private fun String.parseLine(): Game {
    val groups = this.split(mainGroupsRegex)
    val gameId = groups[0].drop(5).toInt()
    val gameSets = groups.asSequence()
        .drop(1)
        .map(String::trim)
        .map(String::parseGameSet)
        .toSet()

    return Game(gameId, gameSets)
}

fun String.parseGameSet(): GameSet {
    val values = setRegex.findAll(this)
        .map { it.groupValues }
        .associate { it[2] to it[1].toInt() }
    return GameSet(
        values["red"] ?: 0,
        values["green"] ?: 0,
        values["blue"] ?: 0,
    )
}

data class Game(val id: Int, val sets: Set<GameSet>) {
    fun isValid() = sets.all { it.isValid() }

    fun power(): Int {
        val red = sets.maxOfOrNull { it.red } ?: 0
        val green = sets.maxOfOrNull { it.green } ?: 0
        val blue = sets.maxOfOrNull { it.blue } ?: 0
        return red * green * blue
    }
}

data class GameSet(val red: Int, val green: Int, val blue: Int) {
    fun isValid() = red <= MAX_RED && green <= MAX_GREEN && blue <= MAX_BLUE
}