package y2024.day23

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import utils.withFileData

class Day23Test {
    @Test
    fun `part 1 example 1`() {
        withFileData {
            val sut = Day23(readTestData(1))
            assertThat(sut.part1()).isEqualTo(7)
        }
    }

    @Test
    fun `part 1 with data`() {
        withFileData {
            Day23(readData()).part1().also {
                assertThat(it).isEqualTo(results()[0].toLong())
            }
        }
    }

    @Test
    fun `part 2 example 1`() {
        withFileData {
            val sut = Day23(readTestData(2))
            assertThat(sut.part2()).isEqualTo("co,de,ka,ta")
        }
    }

    @Test
    fun `part 2 with data`() {
        withFileData {
            Day23(readData()).part2().also {
                assertThat(it).isEqualTo(results()[1])
            }
        }
    }

}
