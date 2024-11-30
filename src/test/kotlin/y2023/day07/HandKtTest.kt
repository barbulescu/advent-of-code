package y2023.day07

import day07.calculatePowerPart2
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class HandKtTest {
    @ParameterizedTest
    @MethodSource("part2Data")
    fun `test part 2`(hand: String, expected: Int) {

        47
        val hand = hand.buildHand()
        assertThat(hand.calculatePowerPart2()).isEqualTo(expected)
    }

    companion object {
        @JvmStatic
        fun part2Data(): Stream<Arguments> = Stream.of(
            Arguments.of("AAAAA", 71),
            Arguments.of("AA8AA", 67),
            Arguments.of("23332", 61),
            Arguments.of("TTT98", 59),
            Arguments.of("23432", 53),
            Arguments.of("A23A4", 47),
            Arguments.of("23456", 43),

            Arguments.of("AAAAJ", 71),
            Arguments.of("AAJAA", 71),
            Arguments.of("2333J", 67),
            Arguments.of("TTT9J", 67),
            Arguments.of("TTJ98", 59),
            Arguments.of("2343J", 59),

            Arguments.of("32T3K", 47),
            Arguments.of("KK677", 53),
            Arguments.of("T55J5", 67),
            Arguments.of("KTJJT", 67),
            Arguments.of("QQQJA", 67),
            Arguments.of("QAT234", 43),
            Arguments.of("QAJ234", 47),
            Arguments.of("JKKK2", 67),
        )
    }

    private fun String.buildHand() = toCharArray()
            .groupBy { it }
            .mapValues { it.value.size }
}
