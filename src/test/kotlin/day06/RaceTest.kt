package day06

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class RaceTest {
    @Test
    fun `calculate distance`() {
        val race = Race(7, 9)

        assertThat(race.canBeatRecord(1)).isFalse()
        assertThat(race.canBeatRecord(2)).isTrue()
        assertThat(race.canBeatRecord(3)).isTrue()
        assertThat(race.canBeatRecord(4)).isTrue()
        assertThat(race.canBeatRecord(5)).isTrue()
        assertThat(race.canBeatRecord(6)).isFalse()
        assertThat(race.canBeatRecord(7)).isFalse()
    }
}