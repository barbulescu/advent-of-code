package day07

import utils.FileData
import utils.expectResult

private val fileData = FileData(7)

fun main() {
    // test if implementation meets criteria from the description, like:
    expectResult(6440) {
        part1(fileData.readTestData(1))
    }
//    expectResult(1) {
//        part2(fileData.readTestData(2))
//    }

    val data = fileData.readData()

    println("#1 -> ${part1(data)}")
    println("#2 -> ${part2(data)}")
}

private fun part1(input: List<String>): Int = input
    .map(String::parseLine)
    .sorted()
    .mapIndexed { index: Int, bid: Bid -> bid.amount * (index + 1) }
    .sum()

private fun part2(input: List<String>): Int {
    return -1
}

fun String.parseLine(): Bid {
    return Bid(
        hand = Hand(this.take(5)),
        amount = this.drop(6).toInt()
    )
}