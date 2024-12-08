package y2024.day08

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day08KtTest {
    @Test
    fun `calculate anti nodes`() {
        val node1 = Node(4, 3, 'a')
        val node2 = Node(6, 5, 'a')

        val antiNodes = calculateAntiNodes(node1, node2, 100, 100, 1)
        println(antiNodes)
        assertThat(antiNodes).containsExactlyInAnyOrder(AntiNode(2, 1), AntiNode(8, 7))
    }

    @Test
    fun `calculate anti nodes on the other diagonal`() {
        val node1 = Node(4, 4, 'a')
        val node2 = Node(3, 5, 'a')

        val antiNodes = calculateAntiNodes(node1, node2, 100, 100, 1)
        println(antiNodes)
        assertThat(antiNodes).containsExactlyInAnyOrder(AntiNode(2, 6), AntiNode(5, 3))
    }

    @Test
    fun `calculate anti nodes case 2`() {
        val node1 = Node(8, 1, 'a')
        val node2 = Node(5, 2, 'a')

        val antiNodes = calculateAntiNodes(node1, node2, 100, 100, 1)
        println(antiNodes)
        assertThat(antiNodes).containsExactlyInAnyOrder(AntiNode(2, 3), AntiNode(11, 0))
    }

    @Test
    fun `calculate anti nodes on reverse`() {
        val node1 = Node(4, 3, 'a')
        val node2 = Node(6, 5, 'a')

        val antiNodes = calculateAntiNodes(node2, node1, 100, 100, 1)
        println(antiNodes)
        assertThat(antiNodes).containsExactlyInAnyOrder(AntiNode(2, 1), AntiNode(8, 7))
    }

    @Test
    fun `calculate anti nodes on the sane line`() {
        val node1 = Node(4, 3, 'a')
        val node2 = Node(6, 3, 'a')

        val antiNodes = calculateAntiNodes(node1, node2, 100, 100, 1)
        println(antiNodes)
        assertThat(antiNodes).containsExactlyInAnyOrder(AntiNode(2, 3), AntiNode(8, 3))
    }

    @Test
    fun `calculate anti nodes on the same line reverse`() {
        val node1 = Node(4, 3, 'a')
        val node2 = Node(6, 3, 'a')

        val antiNodes = calculateAntiNodes(node2, node1, 100, 100, 1)
        println(antiNodes)
        assertThat(antiNodes).containsExactlyInAnyOrder(AntiNode(2, 3), AntiNode(8, 3))
    }

    @Test
    fun `calculate anti nodes on the same column`() {
        val node1 = Node(4, 3, 'a')
        val node2 = Node(4, 5, 'a')

        val antiNodes = calculateAntiNodes(node1, node2, 100, 100, 1)
        println(antiNodes)
        assertThat(antiNodes).containsExactlyInAnyOrder(AntiNode(4, 1), AntiNode(4, 7))
    }

    @Test
    fun `calculate anti nodes on the same column on reverse`() {
        val node1 = Node(4, 3, 'a')
        val node2 = Node(4, 5, 'a')

        val antiNodes = calculateAntiNodes(node2, node1, 100, 100, 1)
        println(antiNodes)
        assertThat(antiNodes).containsExactlyInAnyOrder(AntiNode(4, 1), AntiNode(4, 7))
    }


}
