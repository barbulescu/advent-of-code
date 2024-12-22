package y2024.day20

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import utils.withFileData

class Day20Test {
    @Test
    fun `part 1 example 1`() {
        withFileData {
            val sut = Day20(readTestData(1))
            assertThat(sut.part1(goal = 64)).isEqualTo(1)
        }
    }

    @Test
    fun `part 1 with data`() {
        withFileData {
            Day20(readData()).part1(goal = 100).also {
                assertThat(it).isEqualTo(resultsAsLong()[0])
            }
        }
    }

    @Test
    fun `part 2 example 1`() {
        withFileData {
            val sut = Day20(readTestData(2))
            assertThat(sut.part2(goal = 76)).isEqualTo(3)
        }
    }

    @Test
    fun `part 2 with data`() {
        withFileData {
            Day20(readData()).part2(goal = 100).also {
                assertThat(it).isEqualTo(resultsAsLong()[1])
            }
        }
    }

}
