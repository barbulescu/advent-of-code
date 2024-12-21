package y2024.day12

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day12KtTest {

    @Test
    fun `part2 case 1`() {
        """
            AAAA
            BBCD
            BBCC
            EEEC
        """
            .trimIndent()
            .lines()
            .expectResult(80L)
    }

    @Test
    fun `part2 case 2`() {
        """
            OOOOO
            OXOXO
            OOOOO
            OXOXO
            OOOOO
        """.trimIndent()
            .lines()
            .expectResult(436L)
    }

    @Test
    fun `part2 case 3`() {
        """
            EEEEE
            EXXXX
            EEEEE
            EXXXX
            EEEEE
        """
            .trimIndent()
            .lines()
            .expectResult(236L)
    }

    @Test
    fun `part2 case 4`() {
        """
            AAAAAA
            AAABBA
            AAABBA
            ABBAAA
            ABBAAA
            AAAAAA
        """
            .trimIndent()
            .lines()
            .expectResult(368L)
    }

    @Test
    fun `part2 case 5`() {
        """
            RRRRIICCFF
            RRRRIICCCF
            VVRRRCCFFF
            VVRCCCJFFF
            VVVVCJJCFE
            VVIVCCJJEE
            VVIIICJJEE
            MIIIIIJJEE
            MIIISIJEEE
            MMMISSJEEE
        """.trimIndent()
            .lines()
            .expectResult(1206L)
    }

    private fun List<String>.expectResult(result: Long) {
        assertThat(part2()).isEqualTo(result)
    }
}
