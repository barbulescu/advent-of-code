package y2023.day07

import day07.Hand
import day07.calculatePowerPart1
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class HandTest {

    @Test
    fun `sort correctly`() {
        val input = listOf("32T3K", "T55J5", "KK677", "KTJJT", "QQQJA")
            .map { Hand(it, labelsPart1, Map<Char, Int>::calculatePowerPart1) }

        val actual = input.sorted().map { it.cards }

        assertThat(actual).containsExactly("32T3K", "KTJJT","KK677", "T55J5", "QQQJA")
    }

}
