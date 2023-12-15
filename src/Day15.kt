data class Len(val label: String, var focalLength: Int)
fun main() {

    fun part1(input: List<String>): Int {
        var res = 0
        val arr = input[0].split(",")
        for (str in arr) {
            var curr = 0
            for(c in str) {
                curr += c.code
                curr *= 17
                curr %= 256
            }
            res += curr
        }

        return res
    }

    fun String.hash(): Int {
        var curr = 0
        for(c in this) {
            curr += c.code
            curr *= 17
            curr %= 256
        }
        return curr
    }


    fun part2(input: List<String>): Int {
        var res = 0
        val arr = input[0].split(",")
        val boxes = Array(256){ mutableListOf<Len>() }
        for (str in arr) {
            when {
                str[str.length-1] == '-' -> {
                    val label = str.substring(0, str.length-1)
                    val boxNo = label.hash()
                    boxes[boxNo].removeIf { it.label == label }
                }
                str[str.length-2] == '=' -> {
                    val label = str.substring(0, str.length-2)
                    val boxNo = label.hash()
                    val focalLength = str[str.length-1] - '0'
                    val index = boxes[boxNo].indexOfFirst { it.label == label }
                    if (index == -1) {
                        boxes[boxNo].add(Len(label, focalLength))
                    } else {
                        boxes[boxNo][index].focalLength = focalLength
                    }

                }
            }
        }
        for (i in boxes.indices) {
            for (lenIndex in boxes[i].indices) {
                val focusingPower =  (i + 1) * (lenIndex + 1) * boxes[i][lenIndex].focalLength
                res += focusingPower
            }
        }

        return res
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day15_test")
    part1(testInput).println()
    check(part1(testInput) == 1320)
    part2(testInput).println()
    check(part2(testInput) == 145)

    val input = readInput("Day15")
    part1(input).println()
    part2(input).println()
}
