fun main() {
    fun part1(input: List<String>): Int {
        var sum = 0
        input.forEach{
            var firstDigit = -1
            for(i in it.indices) {
                val curr = it[i] -'0'
                if (curr in 0..9){
                    firstDigit = curr
                    break
                }
            }
            var lastDigit = -1
            for(i in it.length-1 downTo 0) {
                val curr = it[i] -'0'
                if (curr in 0..9){
                    lastDigit = curr
                    break
                }
            }
            sum += firstDigit*10 + lastDigit
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 142)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
