package y2024.day24

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import utils.withFileData

class Day24Test {
    @Test
    fun `part 1 example 1`() {
        withFileData {
            val sut = Day24(readTestData(1))
            assertThat(sut.part1()).isEqualTo(2024)
        }
    }

    @Test
    fun `part 1 with data`() {
        withFileData {
            Day24(readData()).part1().also {
                assertThat(it).isEqualTo(results()[0].toLong())
            }
        }
    }

    @Test
    fun `part 2 example 1`() {
        withFileData {
            val sut = Day24(readTestData(2))
            assertThat(sut.part2()).isEqualTo("mjb,tgd,wpb,z02,z03,z05,z06,z07,z08,z10,z11")
        }
    }

    @Test
    fun `part 2 with data`() {
        withFileData {
            Day24(readData()).part2().also {
                assertThat(it).isEqualTo(results()[1])
            }
        }
    }

}
