import java.util.*

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
        var sum = 0
        val list = LinkedList<String>()
        for (i in 0 until Math.min(input.size, 3)) {
            list.add(input[i])
        }
        var i = 2
        while (list.size == 3) {
            val temp = mutableListOf<String>()
            temp.add(list.poll())
            temp.add(list[0])
            temp.add(list[1])
            for(i in temp[1].indices) {
                val char = temp[1][i]
                if (char == '*'){

                    val matrix = Array(3){CharArray(temp[it].length){' '} }
                    for (r in matrix.indices) {
                        for(c in i-1 downTo 0) {
                            if (temp[r][c]-'0' in 0..9) {
                                matrix[r][c] = temp[r][c]
                            } else {
                                break
                            }
                        }
                        for(c in i+1 until  matrix[r].size) {
                            if (temp[r][c]-'0' in 0..9) {
                                matrix[r][c] = temp[r][c]
                            } else {
                                break
                            }
                        }
                        matrix[r][i] = if (temp[r][i]-'0' in 0..9) temp[r][i] else ' '
                    }

                    val found =  mutableListOf<Int>()
                    for(r in matrix.indices) {
                        var s = ""
                        for (c in matrix[r].indices) {
                            if (matrix[r][c] != ' ') {
                                s += matrix[r][c]
                                if (c == matrix[r].size - 1) {
                                    if (s.isNotEmpty()) {
                                        val n = s.toInt()
                                        found.add(n)
                                        s = ""
                                    }
                                }
                            } else {
                                if (s.isNotEmpty()) {
                                    val n = s.toInt()
                                    found.add(n)
                                    s = ""
                                }
                            }
                        }
                    }

                    if (found.size == 2) {
                        println("${found[0]} * ${found[1]} = ${found[0] * found[1]}")
                        sum += found[0] * found[1]
                    }
                }
            }
            i++

            if (i < input.size) {
                list.add(input[i])
            }


        }

        return sum
    }


//     test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    println(part1(testInput))
    check(part1(testInput) == 4361)
    println(part2(testInput))
    check(part2(testInput) == 467835)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}