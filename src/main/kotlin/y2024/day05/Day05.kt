package y2024.day05

import utils.executeDay
import utils.middle
import java.util.*

data class Rule(val page1: Int, val page2: Int)

data class Update(val pages: List<Int>)

fun main() {
    executeDay(List<String>::part1, List<String>::part2)
}

private fun List<String>.part1(): Long {
    val rules = this.toRules()

    val dependencies = rules.toMap()

    return this.toUpdates()
        .filter { isValidUpdate(it, dependencies) }
        .map(Update::pages)
        .sumOf(List<Int>::middle)
        .toLong()
}

private fun List<String>.toRules(): List<Rule> {
    val rules = this
        .takeWhile(String::isNotBlank)
        .map(String::toRule)
    return rules
}

private fun List<String>.toUpdates() =
    asSequence()
        .dropWhile { "|" in it }
        .drop(1)
        .map { Update(it.split(",").map(String::toInt)) }

private fun String.toRule(): Rule {
    val values = split("|")
        .map(String::toInt)
    return Rule(values[0], values[1])
}

private fun List<Rule>.toMap(): Map<Int, Set<Int>> {
    val response = mutableMapOf<Int, MutableSet<Int>>()
    forEach { rule ->
        val mapValue = response.computeIfAbsent(rule.page2) { mutableSetOf() }
        mapValue.add(rule.page1)
    }
    return response
}

private fun isValidUpdate(update: Update, dependencies: Map<Int, Set<Int>>): Boolean {
    val indexMap = update.pages
        .withIndex()
        .associate { it.value to it.index }

    fun valueOf(key: Int) = indexMap.getOrDefault(key, Int.MAX_VALUE)

    return dependencies
        .filter { it.key in indexMap }
        .all { (page, values) -> values.none { it in indexMap && valueOf(it) >= valueOf(page) } }
}


private fun List<String>.part2(): Long {
    val rules = this.toRules()

    val dependencies = rules.toMap()

    return this.toUpdates()
        .partition { isValidUpdate(it, dependencies) }
        .second
        .map { sortUpdate(it, dependencies) }
        .map(Update::pages)
        .sumOf(List<Int>::middle)
        .toLong()
}

fun sortUpdate(update: Update, dependencies: Map<Int, Set<Int>>): Update {
    val pages = update.pages.toSet()

    val relevantDependencies = dependencies
        .filterKeys { it in pages }
        .mapValues { entry -> entry.value.filterTo(mutableSetOf()) { it in pages } }

    val dependenciesCount = relevantDependencies.values
        .flatten()
        .groupingBy { it }
        .eachCount()
        .toMutableMap()

    val queue = ArrayDeque(update.pages.filter { dependenciesCount.getOrDefault(it, 0) == 0 })

    val sortedPages = buildList {
        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()
            add(current)
            relevantDependencies[current]?.forEach { neighbor ->
                if (dependenciesCount.merge(neighbor, -1, Int::plus) == 0) queue.addLast(neighbor)
            }
        }
    }

    return Update(sortedPages)
}
