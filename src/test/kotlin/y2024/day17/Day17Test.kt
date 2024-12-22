package y2024.day17

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import utils.withFileData

class Day17Test {
    @Test
    fun `part 1 example 1`() {
        withFileData {
            val sut = Day17(readTestData(1))
            assertThat(sut.part1()).isEqualTo("4,6,3,5,6,3,5,2,1,0")
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
        withFileData {
            val sut = Day17(readTestData(2))
            assertThat(sut.part2()).isEqualTo(117440)
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
