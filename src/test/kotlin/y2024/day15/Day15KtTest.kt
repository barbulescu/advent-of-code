package y2024.day15

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import utils.withFileData

class Day15KtTest {

    @Test
    fun `part 1 small example`() {
        withFileData {
            assertThat(readTestData(1).part1()).isEqualTo(2028)
        }
    }

    @Test
    fun `part 1 large example`() {
        withFileData {
            assertThat(readTestData(2).part1()).isEqualTo(10092)
        }
    }

    @Test
    fun `part 2 small example`() {
        withFileData {
            assertThat(readTestData(2).part2()).isEqualTo(9021)
        }
    }
}
