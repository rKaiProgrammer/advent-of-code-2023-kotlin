fun main() {
    fun part1(input: List<String>): Int {
        var sum = 0
        input.forEach {
            var firstDigit = -1
            for (i in it.indices) {
                val curr = it[i] - '0'
                if (curr in 0..9) {
                    firstDigit = curr
                    break
                }
            }
            var lastDigit = -1
            for (i in it.length - 1 downTo 0) {
                val curr = it[i] - '0'
                if (curr in 0..9) {
                    lastDigit = curr
                    break
                }
            }
            sum += firstDigit * 10 + lastDigit
        }
        return sum
    }

    fun getFirstDigitIndex(str: String): Int {
        var firstDigitIndex = -1
        for (i in str.indices) {
            val curr = str[i] - '0'
            if (curr in 0..9) {
                firstDigitIndex = i
                break
            }
        }
        return firstDigitIndex
    }

    fun getLastDigitIndex(str: String): Int {
        var lastDigitIndex = -1
        for (i in str.length - 1 downTo 0) {
            val curr = str[i] - '0'
            if (curr in 0..9) {
                lastDigitIndex = i
                break
            }
        }
        return lastDigitIndex
    }


    fun part2(input: List<String>): Int {
        var sum = 0
        val digitInEnglishArr = arrayOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
        input.forEach {
            var firstIndex = Int.MAX_VALUE
            var lastIndex = -1
            var firstDigit = -1
            var lastDigit = -1
            for (i in digitInEnglishArr.indices) {
                val digitInEnglish = digitInEnglishArr[i]
                val first = it.indexOf(digitInEnglish)
                val last = it.lastIndexOf(digitInEnglish)
                if (first != -1 && first < firstIndex) {
                    firstIndex = first
                    firstDigit = i + 1
                }
                if (last != -1 && last > lastIndex) {
                    lastIndex = last
                    lastDigit = i + 1
                }
            }
            val tempFirstIndex = getFirstDigitIndex(it)
            firstDigit = if (firstIndex == Int.MAX_VALUE) {
                it[tempFirstIndex] - '0'
            } else {
                if (tempFirstIndex == -1) {
                    firstDigit
                } else {
                    if (tempFirstIndex < firstIndex) {
                        it[tempFirstIndex] - '0'
                    } else {
                        firstDigit
                    }
                }
            }


            val tempLastIndex = getLastDigitIndex(it)
            lastDigit = if (lastIndex == -1) {
                it[tempLastIndex] - '0'
            } else {
                if (tempLastIndex == -1) {
                    lastDigit
                } else {
                    if (lastIndex > tempLastIndex) {
                        lastDigit
                    } else {
                        it[tempLastIndex] - '0'
                    }
                }
            }
            sum += firstDigit * 10 + lastDigit
        }
        return sum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 142)
    val testInputPart2 = readInput("Day01_part2_test")
    check(part2(testInputPart2) == 281)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
