package day15

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day15KtTest {

    @Test
    fun `calculate hash of HASH`() {
        assertThat("HASH".toHash()).isEqualTo(52)
    }

}