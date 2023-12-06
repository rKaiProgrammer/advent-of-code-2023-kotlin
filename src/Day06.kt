import java.util.*


fun main() {

    fun part1(input: List<String>): Int {
        var res = 1
        val time = input[0].split(" ").filter { it.isNotEmpty() && it[0].isDigit() }.map { it.toInt() }
        val distance = input[1].split(" ").filter { it.isNotEmpty() && it[0].isDigit() }.map { it.toInt() }
        for (i in time.indices) {
            var count = 0
            for (t in time[i] downTo 0) {
                if (t * (time[i] - t) > distance[i]) {
                    count++
                }
            }
            res *= count
        }
        return res

    }

    fun part2(input: List<String>): Int {
        val time =
            input[0].replace(" ", "").substring(input[0].indexOf(":") + 1, input[0].replace(" ", "").length).toLong()
        val distance =
            input[1].replace(" ", "").substring(input[1].indexOf(":") + 1, input[1].replace(" ", "").length).toLong()


        var l = 0L
        var r = time / 2
        var mid = (r + l) / 2
        while (mid * (time - mid) >= distance || (mid + 1) * (time - (mid + 1)) < distance) {
            if (mid * (time - mid) >= distance) {
                r = mid
            } else if ((mid + 1) * (time - (mid + 1)) < distance) {
                l = mid + 1
            }
            mid = (l + r) / 2

        }
        val low = mid + 1
        val high = time - low
        return (high - low + 1).toInt()
    }

    val testInput = readInput("Day06_test")
    println(part1(testInput))
    check(part1(testInput) == 288)
    println(part2(testInput))
    check(part2(testInput) == 71503)

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
