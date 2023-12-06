package day05

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class MappingItemTest {
    private val item1 = MappingItem(50L..97, 52)
    private val item2 = MappingItem(2L..5, 6)

    @Test
    fun `no mapping`() {
        assertThat(item1.map(12)).isNull()
    }

    @Test
    fun `test 50 to 52`() {
        assertThat(item1.map(50)).isEqualTo(52)
    }

    @Test
    fun `test 97 to 99`() {
        assertThat(item1.map(97)).isEqualTo(99)
    }

    @Test
    fun `test 79 to 81`() {
        assertThat(item1.map(79)).isEqualTo(81)
    }

    @Test
    fun `test 55 to 81`() {
        assertThat(item1.map(55)).isEqualTo(57)
    }

    private fun checkSameCount(actual: List<LongRange>, input: LongRange) {
        assertThat(actual.flatMap { it.toList() }.count()).isEqualTo(input.count())
    }

    @Test
    fun `before range`() {
        val input = 0L..1
        val actual = item2.mergeWith(input)
        assertThat(actual).containsExactly(input)
        checkSameCount(actual, input)
    }

    @Test
    fun `after range`() {
        val input = 6L..9
        val actual = item2.mergeWith(input)
        assertThat(actual).containsExactly(input)
        checkSameCount(actual, input)
    }

    @Test
    fun `partial intersection at start`() {
        val input = 1L..3
        val actual = item2.mergeWith(input)
        assertThat(actual).containsExactly(1L..1, 6L..7)
        checkSameCount(actual, input)
    }

    @Test
    fun `partial intersection at end`() {
        val input = 4L..6
        val actual = item2.mergeWith(input)
        assertThat(actual).containsExactly(8L..9, 6L..6)
        checkSameCount(actual, input)
    }
    @Test
    fun `complete intersection`() {
        val input = 3L..4
        val actual = item2.mergeWith(input)
        assertThat(actual).containsExactly(7L..8)
        checkSameCount(actual, input)
    }
}