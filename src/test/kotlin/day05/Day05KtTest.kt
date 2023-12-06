package day05

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day05KtTest {

    @Test
    fun `parse seed line`() {
        val seeds = "seeds: 79 14 55 13".drop(6)
            .split(" ")
            .filterNot(String::isBlank)
            .map { it.toLong() }
        assertThat(seeds).containsExactly(79, 14, 55, 13)
    }

    @Test
    fun `parse section`() {
        val input = listOf(
            "50 98 2",
            "52 50 48"
        )
        val items = parseSection(input)
        val item1 = MappingItem(98L..99, 50)
        val item2 = MappingItem(50L..97, 52)
        assertThat(items).containsExactly(item1, item2)
    }
}

fun IntRange.toItem(destination: Int) = MappingItem(this.first.toLong()..this.last.toLong(), destination.toLong())