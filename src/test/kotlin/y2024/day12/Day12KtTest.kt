package y2024.day12

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import utils.withFileData

class Day12KtTest {

    @Test
    fun `part2 case 1`() {
        withFileData {
            readTestData(2, 1).expectResult(80L)
        }
    }

    @Test
    fun `part2 case 2`() {
        withFileData {
            readTestData(2, 2).expectResult(436L)
        }
    }

    @Test
    fun `part2 case 3`() {
        withFileData {
            readTestData(2, 3).expectResult(236L)
        }
    }

    @Test
    fun `part2 case 4`() {
        withFileData {
            readTestData(2, 4).expectResult(368L)
        }
    }

    @Test
    fun `part2 case 5`() {
        withFileData {
            readTestData(2, 5).expectResult(1206L)
        }
    }

    private fun List<String>.expectResult(result: Long) {
        assertThat(part2()).isEqualTo(result)
    }
}
