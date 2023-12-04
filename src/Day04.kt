import java.util.*
import kotlin.collections.HashSet

fun main() {
    fun part1(input: List<String>): Int {
        var sum = 0
        for (str in input) {

            val winningNums = str.substring(str.indexOf(":") + 1, str.indexOf("|"))
                .split(" ")
            val myNums = str.substring(str.indexOf("|") + 1, str.length)
                .split(" ").filter { it.isNotBlank() }
            val winningSet = HashSet(winningNums)
            var point = 0
            for (n in myNums) {
                if (winningSet.contains(n)) {
                    if (point == 0) {
                        point = 1
                    } else {
                        point *= 2
                    }

                }
            }
            sum += point

        }
        return sum

    }


    fun part2(input: List<String>): Int {
        return input.size

    }


//     test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    println(part1(testInput))
    check(part1(testInput) == 13)
//    println(part2(testInput))
//    check(part2(testInput) == 467835)

    val input = readInput("Day04")
    part1(input).println()
//    part2(input).println()
}