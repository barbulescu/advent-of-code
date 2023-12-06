package day05

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class MappingTest {
    @Test
    fun `single range match`() {
        val initial = listOf(
        (2..5).toItem(6)
    )
        val actual = initial.mergeWith(3L..3)
        assertThat(actual).containsExactly(7L..7)
    }

    @Test
    fun `fully inside on range`() {
        val mapping = listOf(
            (50..97).toItem(52),
            (98..99).toItem(50)

        )

        val input = listOf(79L..92, 55L..67)
        val actual = input.flatMap { mapping.mergeWith(it) }
        assertThat(actual).containsExactlyInAnyOrder(81L..94, 57L..69)
    }

    @Test
    fun `fully outside`() {
        val mapping = listOf(
            (0..14).toItem(39),
            (15..51).toItem(0),
            (52..53).toItem(37),

        )

        val input = listOf(81L..94)
        val actual = input.flatMap { mapping.mergeWith(it) }
        assertThat(actual).containsExactlyInAnyOrder(81L..94)
    }

    @Test
    fun `over 2 ranges`() {
        val mapping = listOf(
            (45..63).toItem(81),
            (64..76).toItem(68),
            (77..99).toItem(45),
        )

        val input = listOf(74L..84)
        val actual = input.flatMap { mapping.mergeWith(it) }
        assertThat(actual).containsExactlyInAnyOrder(78L..80, 45L..52)
    }
}