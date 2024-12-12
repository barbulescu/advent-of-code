package y2024.day12

import utils.executeDay

fun main() {
    executeDay(List<String>::part1, List<String>::part2)
}

private fun List<String>.part1(): Long {
    val grid = this.toGrid()
    return grid
        .plants()
        .filterNot { it.visited }
        .sumOf { rootPlant ->
            val plants = grid
                .processPlat(rootPlant)
                .toList()

            val perimeter = plants
                .sumOf { plant ->
                    grid.neighbours(plant)
                        .count { it == null || it.type != plant.type }
                }

            plants.size * perimeter
        }
        .toLong()

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
    fun plants() = data.asSequence().flatten()

    fun processPlat(plant: Plant = data[0][0]): Sequence<Plant> {
        if (plant.visited) {
            return emptySequence()
        }
        plant.visited = true

        return neighbours(plant)
            .filterNotNull()
            .filter { it.type == plant.type }
            .flatMap { processPlat(it) } + plant
    }

    fun neighbours(plant: Plant) = sequenceOf(
        pointAt(plant.x - 1, plant.y),
        pointAt(plant.x, plant.y - 1),
        pointAt(plant.x, plant.y + 1),
        pointAt(plant.x + 1, plant.y),
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
}

private data class Plant(val x: Int, val y: Int, val type: PlantType, var visited: Boolean = false)

@JvmInline
value class PlantType(val char: Char)
