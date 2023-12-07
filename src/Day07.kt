import java.util.*
import kotlin.collections.HashMap

enum class CamelCardType(val value: Int) {
    FiveOfAKind(7),
    FourOfAKind(6),
    FullHouse(5),
    ThreeOfAKind(4),
    TwoPair(3),
    OnePair(2),
    HighCard(1),
}

fun main() {
    data class Item(val hand: String, val type: CamelCardType, val bid: Int)

    fun part1(input: List<String>): Int {
        val list = input.map {
            val arr = it.split(" ")
            val map = HashMap<Char, Int>()
            for (c in arr[0]) {
                map[c] = map.getOrDefault(c, 0) + 1
            }
            val list = mutableListOf<Int>()
            for ((k, v) in map) {
                if (v != 0) {
                    list.add(v)
                }
            }
            list.sortDescending()
            val type = when {
                list[0] == 5 -> CamelCardType.FiveOfAKind
                list[0] == 4 -> CamelCardType.FourOfAKind
                list[0] == 3 -> {
                    if (list[1] == 2) CamelCardType.FullHouse else CamelCardType.ThreeOfAKind
                }

                list[0] == 2 -> {
                    if (list[1] == 2) CamelCardType.TwoPair else CamelCardType.OnePair
                }

                else -> {
                    CamelCardType.HighCard
                }
            }


            Item(arr[0], type, arr[1].toInt())
        }.toMutableList()
        val orderMap = hashMapOf(
            'A' to 13,
            'K' to 12,
            'Q' to 11,
            'J' to 10,
            'T' to 9,
            '9' to 8,
            '8' to 7,
            '7' to 6,
            '6' to 5,
            '5' to 4,
            '4' to 3,
            '3' to 2,
            '2' to 1,


            )
        list.sortWith(kotlin.Comparator<Item>() { a, b ->
            if (b.type.value == a.type.value) {
                var compareResult = 0
                for (i in 0 until 5) {
                    val orderA = orderMap[a.hand[i]]!!
                    val orderB = orderMap[b.hand[i]]!!
                    if (orderB > orderA) {
                        compareResult = 1
                        break
                    } else if (orderA > orderB) {
                        compareResult = -1
                        break
                    }
                }
                compareResult
            } else {
                b.type.value - a.type.value
            }
        })

        var res = 0
        for (i in list.indices) {
            println("${(list.size - i)} ${list[i]}")
            res += (list.size - i) * list[i].bid
        }
        return res

    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val testInput = readInput("Day07_test")
    println(part1(testInput))
    check(part1(testInput) == 6440)
//    println(part2(testInput))
//    check(part2(testInput) == )

    val input = readInput("Day07")
    part1(input).println()
//    part2(input).println()
}
