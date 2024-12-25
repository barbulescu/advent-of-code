package y2024.day25

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import utils.withFileData

class Day25Test {
    @Test
    fun `part 1 example 1`() {
        withFileData {
            val sut = Day25(readTestData(1))
            assertThat(sut.part1()).isEqualTo(3)
        }
    }

    @Test
    fun `part 1 with data`() {
        withFileData {
            Day25(readData()).part1().also {
                assertThat(it).isEqualTo(results()[0].toLong())
            }
        }
    }
}
