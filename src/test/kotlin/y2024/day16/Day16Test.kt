package y2024.day16

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import utils.toLines
import utils.withFileData

class Day16Test {
    private val example1 = """
        ###############
        #.......#....E#
        #.#.###.#.###.#
        #.....#.#...#.#
        #.###.#####.#.#
        #.#.#.......#.#
        #.#.#####.###.#
        #...........#.#
        ###.#.#####.#.#
        #...#.....#.#.#
        #.#.#.###.#.#.#
        #.....#...#.#.#
        #.###.#.#.#.#.#
        #S..#.....#...#
        ###############            
    """.toLines()

    private val example2 = """
        #################
        #...#...#...#..E#
        #.#.#.#.#.#.#.#.#
        #.#.#.#...#...#.#
        #.#.#.#.###.#.#.#
        #...#.#.#.....#.#
        #.#.#.#.#.#####.#
        #.#...#.#.#.....#
        #.#.#####.#.###.#
        #.#.#.......#...#
        #.#.###.#####.###
        #.#.#...#.....#.#
        #.#.#.#####.###.#
        #.#.#.........#.#
        #.#.#.#########.#
        #S#.............#
        #################
    """.toLines()

    @Test
    fun `part 1 example 1`() {
        Day16(example1).part1().also {
            assertThat(it).isEqualTo(7036)
        }
    }

    @Test
    fun `part 1 example 2`() {
        Day16(example2).part1().also {
            assertThat(it).isEqualTo(11048)
        }
    }

    @Test
    fun `part 1 with data`() {
        withFileData {
            Day16(readData()).part1().also {
                assertThat(it).isEqualTo(resultsAsLong()[0])
            }
        }
    }

    @Test
    fun `part 2 example 1`() {
        Day16(example1).part2().also {
            assertThat(it).isEqualTo(45)
        }
    }

    @Test
    fun `part 2 example 2`() {
        Day16(example2).part2().also {
            assertThat(it).isEqualTo(64)
        }
    }

    @Test
    fun `part 2 with data`() {
        withFileData {
            Day16(readData()).part2().also {
                assertThat(it).isEqualTo(resultsAsLong()[1])
            }
        }
    }

}
