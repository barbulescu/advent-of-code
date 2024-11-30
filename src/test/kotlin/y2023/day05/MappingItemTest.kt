package y2023.day05

import day05.MappingItem
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class MappingItemTest {
    private val item = MappingItem(50L..97, 52)

    @Test
    fun `no mapping`() {
        assertThat(item.map(12)).isNull()
    }

    @Test
    fun `test 50 to 52`() {
        assertThat(item.map(50)).isEqualTo(52)
    }

    @Test
    fun `test 97 to 99`() {
        assertThat(item.map(97)).isEqualTo(99)
    }

    @Test
    fun `test 79 to 81`() {
        assertThat(item.map(79)).isEqualTo(81)
    }

    @Test
    fun `test 55 to 81`() {
        assertThat(item.map(55)).isEqualTo(57)
    }
}
