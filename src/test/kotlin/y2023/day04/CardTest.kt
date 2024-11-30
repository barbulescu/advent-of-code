package y2023.day04

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CardTest {
    @Test
    fun `eight points card calculation`() {
        val card = Card(1, listOf(41, 48, 83, 86, 17), listOf(83, 86, 6, 31, 17, 9, 48, 53))
        assertThat(card.calculatePoints()).isEqualTo(8)
    }

    @Test
    fun `two points card calculation`() {
        val card = Card(1, listOf(13, 32, 20, 16, 61), listOf(61, 30, 68, 82, 17, 32, 24, 19))
        assertThat(card.calculatePoints()).isEqualTo(2)
    }

    @Test
    fun `one point card calculation`() {
        val card = Card(1, listOf(41, 92, 73, 84, 69), listOf(59, 84, 76, 51, 58, 5, 54, 83))
        assertThat(card.calculatePoints()).isEqualTo(1)
    }

    @Test
    fun `zero points card calculation`() {
        val card = Card(1, listOf(31, 18, 13, 56, 72), listOf(74, 77, 10, 23, 35, 67, 36, 11))
        assertThat(card.calculatePoints()).isEqualTo(0)
    }
}
