package y2024.day16

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import utils.withFileData

class Day16Test {
    @Test
    fun `part 1 example 1`() {
        withFileData {
            val sut = Day16(readTestData(1))
            assertThat(sut.part1()).isEqualTo(7036)
        }
    }

    @Test
    fun `part 1 example 2`() {
        withFileData {
            val sut = Day16(readTestData(2))
            assertThat(sut.part1()).isEqualTo(11048)
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
        withFileData {
            val sut = Day16(readTestData(1))
            assertThat(sut.part2()).isEqualTo(45)
        }
    }

    @Test
    fun `part 2 example 2`() {
        withFileData {
            val sut = Day16(readTestData(2))
            assertThat(sut.part2()).isEqualTo(64)
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
