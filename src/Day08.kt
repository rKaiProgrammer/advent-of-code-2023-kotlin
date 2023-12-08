
fun main() {
    data class Nav(val l: String, val r: String)

    fun part1(input: List<String>): Int {
        val list = input[0]
        val map = HashMap<String, Nav>()
        for (i in 2 until input.size) {
            map[input[i].substring(0, 3)] = Nav(input[i].substring(7, 10), input[i].substring(12, 15))
        }
        var pre = map["AAA"]!!
        var step = 0
        var i = 0
        while (true) {
            val dir = list[i]
            step++
            val curr = if (dir == 'L') {
                pre.l
            } else {
                pre.r
            }
            if (curr == "ZZZ") {
                return step
            } else {
                pre = map[curr]!!
            }
            i++
            i = i % list.length
        }
        return step
    }


    fun getStep(input: List<String>, start: String): Int {
        val list = input[0]
        val map = HashMap<String, Nav>()
        for (i in 2 until input.size) {
            map[input[i].substring(0, 3)] = Nav(input[i].substring(7, 10), input[i].substring(12, 15))
        }
        var pre = map[start]!!
        var step = 0
        var i = 0
        while (true) {
            val dir = list[i]
            step++
            val curr = if (dir == 'L') {
                pre.l
            } else {
                pre.r
            }
            if (curr.endsWith('Z')) {
                return step
            } else {
                pre = map[curr]!!
            }
            i++
            i = i % list.length

        }
        return step
    }

    fun gcd(a: Long, b: Long): Long {
        if (b == 0L) return a
        return gcd(b, a % b)
    }

    fun lcm(a: Long, b: Long): Long {
        return a * b / gcd(a, b)
    }

    fun part2(input: List<String>): Long {

        val stepList = mutableListOf<Int>()
        for (i in 2 until input.size) {
            if (input[i][2] == 'A') {
                stepList.add(getStep(input, input[i].substring(0, 3)))
            }
        }
        var res = 1L
        for (step in stepList) {
            res = lcm(res, step.toLong())
        }
        return res
    }


//    fun part2(input: List<String>): Int {
//        val list = input[0]
//        val map = HashMap<String, Nav>()
//        for (i in 2 until input.size) {
//            map[input[i].substring(0, 3)] = Nav(input[i].substring(7, 10), input[i].substring(12, 15))
//        }
//        var pre = mutableListOf<Nav>()
//        for ((k, v) in map) {
//            if (k[2] == 'A') {
//                pre.add(v)
//            }
//        }
//        var step = 0
//        var i = 0
//        while (true) {
//            val dir = list[i]
//            step++
////            step.println()
//            val tempList = mutableListOf<Nav>()
//            var finishCount = 0
//            for (p in pre) {
//                val curr = if (dir == 'L') {
//                    p.l
//                } else {
//                    p.r
//                }
//                if (curr.endsWith('Z')) {
//                    finishCount++
//                }
//                "$curr".println()
//                tempList.add(map[curr]!!)
//            }
//            "$step: $finishCount/${pre.size}".println()
//            if (finishCount == pre.size){
//                return step
//            } else {
//                pre = tempList
//            }
//
//            i++
//            i = i % list.length
//        }
//        return step
//    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    part1(testInput).println()
    check(part1(testInput) == 6)
    val testInputPart2 = readInput("Day08_part2_test")
    part2(testInputPart2).println()
    check(part2(testInputPart2) == 6L)

    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}
