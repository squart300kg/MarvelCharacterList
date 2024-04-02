package kr.co.korean.investment

class Solution {

    enum class Pedigree(val score: Int) {
        Top(1),
        Pair(2),
        Straight(3),
        StraightFlush(4),
    }

    fun solution(cards: Array<String>): Array<String> {
        val blackCards = listOf("1b", "2b", "3b", "4b", "5b", "6b", "7b", "8b", "9b")
        val redCards = listOf("1r", "2r", "3r", "4r", "5r", "6r", "7r", "8r", "9r")

        val cards = cards.toList()
        val result = mutableListOf<String>()

        val myCard = cards[0]
        val yourCard = cards[1]
        val sharedChard = cards[2]

        for (blackCard in blackCards) {
            if (myCard == blackCard || yourCard == blackCard || sharedChard == blackCard) continue

            val cards = cards.toMutableList()
            val myCards = cards.apply { removeAt(1); add(blackCard) }
            val yourCards = cards.apply { removeAt(0); add(blackCard) }
            val myPedigree = calculatePedigreeAndGet(myCards)
            val yourPedigree = calculatePedigreeAndGet(yourCards)

            if (myPedigree.score >= yourPedigree.score) {
                if (myPedigree > yourPedigree) {
                    result.add(blackCard)
                } else {
                    if (amIWinnerByOneCard("", "")) {
                        result.add(blackCard)
                    }
                }
            }
        }

        for (redCard in redCards) {
            if (myCard == redCard || yourCard == redCard || sharedChard == redCard) continue

            val cards = cards.toMutableList()
            val myCards = cards.apply { removeAt(1); add(redCard) }
            val yourCards = cards.apply { removeAt(0); add(redCard) }
            val myPedigree = calculatePedigreeAndGet(myCards)
            val yourPedigree = calculatePedigreeAndGet(yourCards)

            if (myPedigree.score >= yourPedigree.score) {
                if (myPedigree > yourPedigree) {
                    result.add(redCard)
                } else {
                    if (amIWinnerByOneCard("", "")) {
                        result.add(redCard)
                    }
                }
            }
        }

        return result.sorted().toTypedArray()
    }

    private fun calculatePedigreeAndGet(cards: List<String>): Pedigree {
        val cards = cards.sorted()





        return Pedigree.Pair
    }

    private fun amIWinnerByOneCard(myCard: String, yourCard: String): Boolean {
        return true
    }
}
