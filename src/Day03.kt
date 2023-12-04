fun main() {
    fun part1(input: List<String>): Int {
        var sum = 0

        data class Item(val start: Int, val end: Int, val num: Int, var isAdded: Boolean = false)

        var digitList = mutableListOf<Item>()
        var symbolList = mutableListOf<Int>()
        for (i in input.indices) {
            val w = input[i]
            var digit = 0
            var hasSymbol = false
            var hasDigit = false
            val tempDigitList = mutableListOf<Item>()
            val tempSymbolList = mutableListOf<Int>()

            for (i in w.indices) {
                if (w[i] == '.') {
                    // is '.'
                    if (hasDigit) {
                        val start = i - digit.toString().length
                        val end = i - 1

                        if (hasSymbol) {
                            sum += digit
                        } else {
                            var isAdded = false
                            for (symbolIndex in symbolList) {
                                if (start - 1 <= symbolIndex && symbolIndex <= end + 1) {
                                    sum += digit
                                    isAdded = true
                                    break
                                }
                            }
                            if (!isAdded) {
                                tempDigitList.add(Item(start, end, digit))
                            }
                        }

                    }
                    hasSymbol = false
                    hasDigit = false
                    digit = 0
                } else if (w[i] - '0' in 0..9) {
                    // is Digit
                    hasDigit = true
                    digit = digit * 10 + (w[i] - '0')
                    if (i == w.length - 1) {
                        val start = i - digit.toString().length + 1  // need to plus 1!! different from is '.' block
                        val end = i - 1
                        if (hasSymbol) {
                            sum += digit
                        } else {
                            var isAdded = false
                            for (symbolIndex in symbolList) {
                                if (start - 1 <= symbolIndex && symbolIndex <= end + 1) {

                                    sum += digit
                                    isAdded = true
                                    break
                                }
                            }
                            if (!isAdded) {
                                tempDigitList.add(Item(start, end, digit))
                            }
                        }

                    }
                } else {
                    // is Symbol
                    tempSymbolList.add(i)
                    if (hasDigit) {
                        sum += digit
                        digit = 0
                        hasDigit = false
                    }
                    // check digit in previous line
                    for (item in digitList) {
                        if (item.start - 1 <= i && i <= item.end + 1 && !item.isAdded) {
                            sum += item.num
                            item.isAdded = true
                        }
                    }
                    hasSymbol = true
                }
            }
            digitList = tempDigitList
            symbolList = tempSymbolList
        }
        return sum

    }


    fun part2(input: List<String>): Int {
        return input.size
    }


//     test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    println(part1(testInput))
    check(part1(testInput) == 4361)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}