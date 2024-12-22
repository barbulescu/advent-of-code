package y2024.day18

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import utils.withFileData

class Day18Test {
    @Test
    fun `part 1 example 1`() {
        withFileData {
            val sut = Day18(readTestData(1))
            assertThat(sut.part1(7, 12)).isEqualTo(22)
        }
    }

    @Test
    fun `part 1 with data`() {
        withFileData {
            Day18(readData()).part1(71, 1024).also {
                assertThat(it).isEqualTo(results()[0].toLong())
            }
        }
    }

    @Test
    fun `part 2 example 1`() {
        withFileData {
            val sut = Day18(readTestData(2))
            assertThat(sut.part2(7)).isEqualTo("6,1")
        }
    }

    @Test
    fun `part 2 with data`() {
        withFileData {
            Day18(readData()).part2(71).also {
                assertThat(it).isEqualTo(results()[1])
            }
        }
    }

}
