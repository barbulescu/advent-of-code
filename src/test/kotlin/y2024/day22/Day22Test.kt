package y2024.day22

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import utils.withFileData

class Day22Test {
    @Test
    fun `part 1 example 1`() {
        withFileData {
            val sut = Day22(readTestData(1))
            assertThat(sut.part1()).isEqualTo(37327623L)
        }
    }

    @Test
    fun `part 1 with data`() {
        withFileData {
            Day22(readData()).part1().also {
                assertThat(it).isEqualTo(resultsAsLong()[0])
            }
        }
    }

    @Test
    fun `part 2 example 1`() {
        withFileData {
            val sut = Day22(readTestData(2))
            assertThat(sut.part2()).isEqualTo(23L)
        }
    }

    @Test
    fun `part 2 with data`() {
        withFileData {
            Day22(readData()).part2().also {
                assertThat(it).isEqualTo(resultsAsLong()[1])
            }
        }
    }

}
