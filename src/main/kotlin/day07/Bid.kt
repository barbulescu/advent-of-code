package day07

data class Bid(val hand: Hand, val amount: Int) : Comparable<Bid> {
    override fun compareTo(other: Bid): Int = hand.compareTo(other.hand)
}