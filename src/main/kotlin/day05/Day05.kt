package day05

import utils.FileData
import utils.expectLongResult

private val fileData = FileData(5)

fun main() {
    // test if implementation meets criteria from the description, like:
    expectLongResult(35) {
        part1(fileData.readTestData(1))
    }
//    expectLongResult(46) {
//        part2(fileData.readTestData(2))
//    }

    val data = fileData.readData()

//    println("#1 -> ${part1(data)}")
//    println("#2 -> ${part2(data)}")

    expectLongResult(323142486) {
        part1(data)
    }
//    expectLongResult(79874951) {
//        part2(data)
//    }

}

private fun part1(input: List<String>): Long {
    val seeds = input.first().drop(6)
        .split(" ")
        .filterNot(String::isBlank)
        .map { it.toLong() }
        .map { it..it }

    val mappingData = parseMappingData(input)
    return mappingData.findLowestLocation(seeds)
}

private fun part2(input: List<String>): Long {
    val seedsList: List<LongRange> = input.first()
        .drop(6)
        .split(" ")
        .filterNot(String::isBlank)
        .map { it.toLong() }
        .windowed(2, 2)
        .map { it[0]..<it[1] + it[0] }

    println(seedsList)
    val mappingData = parseMappingData(input)
    return mappingData.findLowestLocation(seedsList)
}



/*
[79..92, 55..67]
seed2soil
  (source=98..99, destinationStart=50)
  (source=50..97, destinationStart=52)
soil2fertilizer
  (source=15..51, destinationStart=0)
  (source=52..53, destinationStart=37)
  (source=0..14, destinationStart=39)
fertilizer2water
  (source=53..60, destinationStart=49)
  (source=11..52, destinationStart=0)
  (source=0..6, destinationStart=42)
  (source=7..10, destinationStart=57)
water2light
  (source=18..24, destinationStart=88)
  (source=25..94, destinationStart=18)
light2temperature
  (source=77..99, destinationStart=45)
  (source=45..63, destinationStart=81)
  (source=64..76, destinationStart=68)
temperature2humidity
  (source=69..69, destinationStart=0)
  (source=0..68, destinationStart=1)
humidity2location
  (source=56..92, destinationStart=60)
  (source=93..96, destinationStart=56)
 */