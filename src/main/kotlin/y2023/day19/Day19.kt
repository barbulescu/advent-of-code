package y2023.day19

import utils.FileData
import utils.expectResult

private val fileData = FileData(day = 19, year = 2023)

fun main() {
    // test if implementation meets criteria from the description, like:
    expectResult(19114) {
        part1(fileData.readTestData(1))
    }
//    expectLongResult(1) {
//        part2(fileData.readTestData(2))
//    }

    val data = fileData.readData()

    println("#1 -> ${part1(data)}")
    println("#2 -> ${part2(data)}")
}

private fun part1(lines: List<String>): Int {
    val splitLineIndex = lines.indexOf("")
    val workflows: Map<String, Workflow> = lines.subList(0, splitLineIndex)
        .associate(String::parseWorkflow)
    return lines.subList(splitLineIndex + 1, lines.lastIndex)
        .map(String::toRating)
        .filter { workflows.isRatingAccepted(it) }
        .sumOf(Rating::sum)

}

fun Map<String, Workflow>.isRatingAccepted(rating: Rating): Boolean {
    var current = "in"
    while(true) {
        val workflow = this.getValue(current)
        workflow.nextStep(rating)
    }
}

private fun String.parseWorkflow(): Pair<String, Workflow> {
    val name = this.substringBefore("{")
    val definition = this.substringAfter("{")
        .dropLast(1)
    return name to definition.toWorkflow()

}

private fun part2(lines: List<String>): Long {
    return -1
}

data class Workflow(val steps: List<WorkflowStep>, val defaultValue: String) {
    fun nextStep(rating: Rating) {

    }
}

fun String.toWorkflow() : Workflow {
    val parts = this.split(',')
    return Workflow(
        steps = parts.dropLast(1).map { it.toWorkflowStep() },
        defaultValue = parts.last()
    )
}

data class WorkflowStep(val partType: Char, val operation: Char, val value: Int, val nextStep: String)
fun String.toWorkflowStep() : WorkflowStep = WorkflowStep(
    partType = this[0],
    operation = this[1],
    value = this.drop(2).substringBefore(':').toInt(),
    nextStep = this.substringAfterLast(':')
)

data class Rating(val x: Int, val m: Int, val a: Int, val s: Int) {
    fun sum() = x + m + a + s
}
fun String.toRating() : Rating {
    val values = this.drop(1)
        .dropLast(1)
        .split(',')
        .associate { it[0] to it.substringAfter('=').toInt() }
    return Rating(
        x = values.getValue('x'),
        m = values.getValue('m'),
        a = values.getValue('a'),
        s = values.getValue('s')
    )
}
