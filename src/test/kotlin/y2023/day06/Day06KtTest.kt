package y2023.day06

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day06KtTest {

    @Test
    fun `parse data`() {
        val text = """
            |Time:      7  15   30
            |Distance:  9  40  200
        """.trimMargin()

        val actual = text.lines().parseRaceRecords()
        val expected = listOf(
            Race(7, 9),
            Race(15, 40),
            Race(30, 200),
        )
        assertThat(actual).isEqualTo(expected)
    }
}
