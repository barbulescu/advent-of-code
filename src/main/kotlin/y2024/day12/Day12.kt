package y2024.day12

import utils.executeDay

fun main() {
    executeDay(List<String>::part1, List<String>::part2)
}

private fun List<String>.part1(): Long = this.toGrid()
    .run {
        plants()
            .filterNot { it.visited }
            .forEach { processPlat(it) }
        price()
    }

private fun List<String>.part2(): Long {
    TODO("part 2 is not yet implemented")
}

private fun List<String>.toGrid(): Grid {
    val data = this.mapIndexed { y, line ->
        line.mapIndexed { x, c -> Plant(x, y, PlantType(c)) }
    }
    return Grid(data)
}

private class Grid(private val data: List<List<Plant>>) {
    private var price = 0L
    private val cache = mutableMapOf<PlantType, Plot>()

    fun plants() = data.asSequence().flatten()

    fun processPlat(plant: Plant = data[0][0], depth: Int = 0) {
        if (plant.visited) {
            return
        }
//        println("Processing $plant $depth")

        val plot = cache.getOrPut(plant.type) { Plot(plant.type) }

        val neighbours = neighbours(plant.x, plant.y)
        val perimeter = neighbours.count { it == null || it.type != plant.type }
        plot.addPlant(perimeter)
        plant.visited = true

        neighbours
            .filterNotNull()
            .filter { it.type == plant.type }
            .filterNot { it.visited }
            .forEach { processPlat(it, depth + 1) }

        if (depth == 0) {
            price += plot.price()
            cache.remove(plant.type)
        }
    }

    private fun neighbours(x: Int, y: Int) = listOf(
        pointAt(x - 1, y),
        pointAt(x, y - 1),
        pointAt(x, y + 1),
        pointAt(x + 1, y),
    )

    private fun pointAt(x: Int, y: Int): Plant? {
        if (x < 0 || x >= data[0].size) {
            return null
        }
        if (y < 0 || y >= data.size) {
            return null
        }
        return data[y][x]
    }


    fun price() = price
}

private data class Plant(val x: Int, val y: Int, val type: PlantType, var visited: Boolean = false)

private class Plot(val type: PlantType) {
    private var area: Long = 0
    private var perimeter: Long = 0

    fun addPlant(perimeter: Int) {
        this.perimeter += perimeter
        this.area++
    }

    fun price(): Long {
        val response = area * perimeter
        println("${type.char} -> $area * $perimeter = $response")
        return response
    }
}

@JvmInline
value class PlantType(val char: Char)
