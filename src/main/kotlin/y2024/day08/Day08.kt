package y2024.day08

import utils.executeDay
import kotlin.math.abs

fun main() {
    executeDay(List<String>::part1, List<String>::part2)
}

private fun List<String>.part1(): Long = nodes()
    .values
    .flatMap { nodes ->
        nodes.flatMap { node1 ->
            nodes.filterNot { node1 === it }
                .flatMap { node2 -> calculateAntiNodes(node1, node2, this[0].length, this.size, 1) }
        }
    }
    .distinct()
    .count()
    .toLong()

fun calculateAntiNodes(node1: Node, node2: Node, maxX: Int, maxY: Int, maxMultiplier: Int): List<AntiNode> {
    if (node1 === node2) {
        return listOf(AntiNode(node1.x, node1.y))
    }

    val xD = abs(node1.x - node2.x)
    val yD = abs(node1.y - node2.y)

    var multiplier = 1
    return buildList {
        while (multiplier <= maxMultiplier) {
            val (x1, x2) = if (node1.x < node2.x) {
                node1.x - xD * multiplier to node2.x + xD * multiplier
            } else {
                node1.x + xD * multiplier to node2.x - xD * multiplier
            }
            val (y1, y2) = if (node1.y < node2.y) {
                node1.y - yD * multiplier to node2.y + yD * multiplier
            } else {
                node1.y + yD * multiplier to node2.y - yD * multiplier
            }
            var added = false
            if (x1 >= 0 && y1 >= 0 && x1 < maxX && y1 < maxY) {
                add(AntiNode(x1, y1))
                added = true
            }
            if (x2 >= 0 && y2 >= 0 && x2 < maxX && y2 < maxY) {
                add(AntiNode(x2, y2))
                added = true
            }
            if (!added) {
                return@buildList
            }
            multiplier++
        }
    }
}

private fun List<String>.nodes(): Map<Char, List<Node>> = this
    .asSequence()
    .flatMapIndexed { y, line ->
        line
            .asSequence()
            .mapIndexed { x, frequency ->
                Node(x, y, frequency)
            }
    }
    .filter { it.frequency != '.' }
    .groupBy { it.frequency }
    .filter { it.value.size != 1 }

private fun List<String>.part2(): Long = nodes().values
    .flatMap { nodes ->
        nodes.flatMap { node1 ->
            nodes.flatMap { node2 ->
                calculateAntiNodes(node1, node2, this[0].length, size, Int.MAX_VALUE)
            }
        }
    }
    .distinct()
    .count()
    .toLong()

data class Node(val x: Int, val y: Int, val frequency: Char)

data class AntiNode(val x: Int, val y: Int)
