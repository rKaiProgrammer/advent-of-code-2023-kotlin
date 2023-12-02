fun main() {
    fun part1(input: List<String>): Int {
        val colors = listOf( "red", "green", "blue")
        val maxNums = listOf( 12, 13, 14)
        var gameId = 1
        var sum = 0
        input.forEach{
            val substring = it.substring(it.indexOf(':') + 2, it.length)
            var possible = true
            substring.split("; ").forEach{ round->
                round.split(", ").forEach {
                    val num = it.substring(0, it.indexOf(" ")).toInt()
                    for(i in colors.indices) {
                        val color = colors[i]
                        if (it.contains(color)){
                            if (num > maxNums[i]) {
                                possible = false
                            }
                            break
                        }
                    }
                    if (!possible) return@forEach
                }
                if (!possible) return@forEach
            }
            if (possible) {
                sum += gameId
            }
                gameId++
        }
        return sum
    }




    fun part2(input: List<String>): Int {
        val colors = listOf( "red", "green", "blue")
        var sum = 0
        input.forEach{
            val substring = it.substring(it.indexOf(':') + 2, it.length)
            val maxNumArr = IntArray(3)
            substring.split("; ").forEach{ round->
                round.split(", ").forEach {
                    val num = it.substring(0, it.indexOf(" ")).toInt()
                    for(i in colors.indices) {
                        val color = colors[i]
                        if (it.contains(color)){
                            maxNumArr[i] = Math.max(maxNumArr[i], num)
                            break
                        }
                    }
                }
            }
            val power = maxNumArr[0] * maxNumArr[1] * maxNumArr[2]
            sum += power
        }
        return sum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 8)
    check(part2(testInput) == 2286)
    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
