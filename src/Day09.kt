import java.util.*

fun main() {

    fun part1(input: List<String>): Long {
        var sum  = 0L
        input.forEach {
            var list = it.split(" ").map { it.toInt() }.toMutableList()
            var count0 = 0
            val stack = Stack<Int>()
            while(count0 < list.size) {
                count0 = 0
                val temp = mutableListOf<Int>()
                for(i in 1 until list.size) {
                    val diff = list[i] - list[i-1]
                    temp.add(diff)
                    if (diff == 0) count0++
                    if (i == list.size-1) stack.push(list[i])
                }
                list = temp
            }
            var next = 0
            while (stack.isNotEmpty()) {
                next += stack.pop()
            }
//            next.println()
           sum += next

        }
        return sum
    }

    fun part2(input: List<String>): Long {
        var sum  = 0L
        input.forEach {
            var list = it.split(" ").map { it.toInt() }.toMutableList()
            var count0 = 0
            val stack = Stack<Int>()
            while(count0 < list.size) {
                count0 = 0
                val temp = mutableListOf<Int>()
                stack.push(list[0])
                for(i in 1 until list.size) {
                    val diff = list[i] - list[i-1]
                    temp.add(diff)
                    if (diff == 0) count0++
                }
                list = temp
            }
            var pre  = 0
            while (stack.isNotEmpty()) {
                val curr = stack.pop()
                pre = curr - pre
            }
//            pre.println()
            sum += pre

        }
        return sum
    }

    // 2 1 4 10 10 -19 -107 -262 -420
    //  -1 3 -6 0 -29 -88 -155 -158
    //    4 -9 6 -29 -59 -67 -3
    //     -13 15 -35 -30 -8 64
    //       28  -40 5  22 72
    //        -68   45 17 50
    //           113  -28 33
    //              -141 61
    //                 202

    val testInput = readInput("Day09_test")
    println(part1(testInput))
    check(part1(testInput) == 114L)
    println(part2(testInput))
    check(part2(testInput) == 2L)

    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}
