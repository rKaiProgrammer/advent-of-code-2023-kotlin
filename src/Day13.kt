fun main() {

    fun find(input: List<String>, map: HashMap<String, Int>, startIndex: Int, endIndex: Int): Int {
        for (i in startIndex + 1..endIndex) {
            var start = i - 1
            var end = i
            var isSame = true
            while (start >= startIndex && end <= endIndex) {
                if (map[input[start]] != map[input[end]]) {
                    isSame = false
                    break
                }
                start--
                end++
            }
            if (isSame) {
                return i - startIndex
            }
        }
        return -1
    }


    fun part1(input: List<String>): Int {
        var res = 0
        val rMap = HashMap<String, Int>()
        var cList = mutableListOf<String>()
        var startR = 0
        for (r in input.indices) {

            rMap[input[r]] = r
            for (c in input[r].indices) {
                if (cList.size <= c) cList.add("")
                cList[c] = cList[c] + input[r][c]
            }

            if (input[r].isEmpty() || r == input.size - 1) {
                val endR = if (input[r].isEmpty()) r - 1 else r
                val cMap = HashMap<String, Int>()
                for (c in cList.indices) {
                    cMap[cList[c]] = c
                }

                val foundRow = find(input, rMap, startR, endR)
                if (foundRow != -1) {
                    res += foundRow * 100
                } else {
                    val foundCol = find(cList, cMap, 0, cList.size - 1)
                    if (foundCol != -1) {
                        res += foundCol
                    }
                }

                startR = r + 1
                rMap.clear()
                cList.clear()
            }
        }

        return res
    }

    fun findAlmostSame(input: List<String>, startIndex: Int, endIndex: Int): Int {
        for (i in startIndex + 1..endIndex) {
            var start = i - 1
            var end = i
            var diffCount = 0
            while (start >= startIndex && end <= endIndex) {
                for (index in input[start].indices) {
                    if (input[start][index] != input[end][index]) {
                        diffCount++
                    }
                }
                if (diffCount > 1) break

                start--
                end++
            }
            if (diffCount == 1) {
                return i - startIndex
            }
        }
        return -1
    }


    fun part2(input: List<String>): Int {
        var res = 0
        var cList = mutableListOf<String>()
        var startR = 0
        for (r in input.indices) {
            for (c in input[r].indices) {
                if (cList.size <= c) cList.add("")
                cList[c] = cList[c] + input[r][c]
            }
            if (input[r].isEmpty() || r == input.size - 1) {
                val endR = if (input[r].isEmpty()) r - 1 else r
                val foundRow = findAlmostSame(input, startR, endR)
                if (foundRow != -1) {
                    res += foundRow * 100
                } else {
                    val foundCol = findAlmostSame(cList, 0, cList.size - 1)
                    if (foundCol != -1) {
                        res += foundCol
                    }
                }
                startR = r + 1
                cList.clear()
            }
        }

        return res


    }


    val testInput = readInput("Day13_test")
    println(part1(testInput))
    check(part1(testInput) == 405)
    println(part2(testInput))
    check(part2(testInput) == 400)

    val input = readInput("Day13")
    part1(input).println()
    part2(input).println()
}
