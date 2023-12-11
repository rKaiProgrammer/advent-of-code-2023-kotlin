data class Galaxy(val r: Int, val c: Int, val id: Int)

fun main() {
    // prefix sum or binary search
    fun cal(input: List<String>, expansionMultiplier: Int): Long {
        val galaxies = mutableListOf<Galaxy>()
        val arrR = IntArray(input.size)
        val arrC = IntArray(input[0].length)
        var count = 0
        for (r in input.indices) {
            for (c in input[r].indices) {
                if (input[r][c] == '#') {
                    count++
                    arrR[r]++
                    arrC[c]++
                    galaxies.add(Galaxy(r, c, count))
                }

            }
        }
        val doubleR = IntArray(input.size)
        for (i in doubleR.indices) {
            val curr = if (i != 0) doubleR[i - 1] else 0
            val add = if (arrR[i] == 0) 1 else 0
            doubleR[i] = curr + add
        }
        val doubleC = IntArray(input[0].length)
        for (i in doubleC.indices) {
            val curr = if (i != 0) doubleC[i - 1] else 0
            val add = if (arrC[i] == 0) 1 else 0
            doubleC[i] = curr + add
        }
        var res = 0L
        for (i in galaxies.indices) {
            for (j in i + 1 until galaxies.size) {
                val maxR = Math.max(galaxies[i].r, galaxies[j].r)
                val minR = Math.min(galaxies[i].r, galaxies[j].r)
                val expansionR = (doubleR[maxR] - doubleR[minR]) * (expansionMultiplier - 1)
                val diffR = maxR - minR + expansionR

                val maxC = Math.max(galaxies[i].c, galaxies[j].c)
                val minC = Math.min(galaxies[i].c, galaxies[j].c)
                val expansionC = (doubleC[maxC] - doubleC[minC]) * (expansionMultiplier - 1)
                val diffC = maxC - minC + expansionC
//                "${galaxies[i].id}&${galaxies[j].id} = ${diffR + diffC}: ${galaxies[i].r}, ${galaxies[i].c} - ${galaxies[j].r}, ${galaxies[j].c}, diffR: ${doubleR[maxR] - doubleR[minR]}, diffc: ${doubleR[maxC] - doubleR[minC]}".println()
                res += diffR + diffC
            }
        }

        return res
    }

    fun part1(input: List<String>): Int {
        return cal(input, 2).toInt()
    }

    fun part2(input: List<String>): Long {
        return cal(input, 1000000)
    }


    val testInput = readInput("Day11_test")
    println(part1(testInput))
    check(part1(testInput) == 374)
    println(part2(testInput))

    val input = readInput("Day11")
    part1(input).println()
    part2(input).println()
}
