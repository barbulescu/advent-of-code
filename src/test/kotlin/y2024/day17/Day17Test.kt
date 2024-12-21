package y2024.day17

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import utils.toLines
import utils.withFileData

class Day17Test {
    private val example1 = """
        Register A: 729
        Register B: 0
        Register C: 0
        
        Program: 0,1,5,4,3,0
    """.toLines()

    private val example2 = """
        Register A: 2024
        Register B: 0
        Register C: 0
        
        Program: 0,3,5,4,3,0
    """.toLines()

    @Test
    fun `part 1 example 1`() {
        Day17(example1).part1().also {
            assertThat(it).isEqualTo("4,6,3,5,6,3,5,2,1,0")
        }
    }

    @Test
    fun `part 1 with data`() {
        withFileData {
            Day17(readData()).part1().also {
                assertThat(it).isEqualTo(results()[0])
            }
        }
    }

    @Test
    fun `part 2 example 1`() {
        Day17(example2).part2().also {
            assertThat(it).isEqualTo(117440)
        }
    }

    @Test
    fun `part 2 with data`() {
        withFileData {
            Day17(readData()).part2().also {
                assertThat(it).isEqualTo(results()[1].toLong())
            }
        }
    }

}
