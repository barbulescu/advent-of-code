package y2024.day11

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day11KtTest {

    @Test
    fun `case 1 blink 1`() {
        val actual = "0 1 10 99 999".toNumbers().blink()
        val expected = "1 2024 1 0 9 9 2021976".toNumbers()

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `case 2 blink 1`() {
        val actual = "125 17".toNumbers().blink()
        val expected = "253000 1 7".toNumbers()

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `case 2 blink 2`() {
        val actual = "253000 1 7".toNumbers().blink()
        val expected = "253 0 2024 14168".toNumbers()

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `case 2 blink 3`() {
        val actual = "253 0 2024 14168".toNumbers().blink()
        val expected = "512072 1 20 24 28676032".toNumbers()

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `case 2 blink 4`() {
        val actual = "512072 1 20 24 28676032".toNumbers().blink()
        val expected = "512 72 2024 2 0 2 4 2867 6032".toNumbers()

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `case 2 blink 5`() {
        val actual = "512 72 2024 2 0 2 4 2867 6032".toNumbers().blink()
        val expected = "1036288 7 2 20 24 4048 1 4048 8096 28 67 60 32".toNumbers()

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `case 2 blink 6`() {
        val actual = "1036288 7 2 20 24 4048 1 4048 8096 28 67 60 32".toNumbers().blink()
        val expected = "2097446912 14168 4048 2 0 2 4 40 48 2024 40 48 80 96 2 8 6 7 6 0 3 2".toNumbers()

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun part1() {
        var stones = "125 17".toNumbers()
        repeat(25) {
            stones = stones.blink()
            println(stones)
        }
        assertThat(stones.size).isEqualTo(55312)
    }

    @Test
    fun `blink 1000`() {
        assertThat(1000L.blink().toList()).containsExactly(10, 0)
    }

    @Test
    fun `blink 1001`() {
        assertThat(1001L.blink().toList()).containsExactly(10, 1)
    }

    @Test
    fun `blink 10000`() {
        assertThat(10000L.blink().toList()).containsExactly(20240000L)
    }
}
