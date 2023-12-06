package day05

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class MappingTest {
    private val initial = Mapping(
        listOf(
            MappingItem(2L..5, 6)
        )
    )

    @Test
    fun `before range`() {
        val actual = initial.mergeWith(0L..1)
        assertThat(actual).containsExactly(0L..1)
    }

    @Test
    fun `after range`() {
        val actual = initial.mergeWith(6L..9)
        assertThat(actual).containsExactly(6L..9)
    }

    @Test
    fun `partial intersection at start`() {
        val actual = initial.mergeWith(1L..3)
        assertThat(actual).containsExactly(6L..9)
    }

    @Test
    fun `seed to soil merge`() {
        val mapping = Mapping(
            listOf(
                MappingItem(98L..99, 50),
                MappingItem(50L..97, 52)
            )
        )
        val input = listOf(79L..92, 55L..67)
        val actual = input.flatMap { mapping.mergeWith(it) }
        println(actual)
    }
}