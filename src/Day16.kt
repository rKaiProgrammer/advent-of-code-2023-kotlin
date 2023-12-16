import java.util.*
import kotlin.collections.HashSet

data class BeamPos(val row: Int, val col: Int)

enum class BeamDir(val nexRow: Int, val nextCol: Int) {
    UP(-1, 0), LEFT(0, -1), DOWN(1, 0), RIGHT(0, 1)
}

data class Beam(var pos:BeamPos, var dir: BeamDir)
fun main() {

    fun getNextBeams(beam: Beam, encounteredChar: Char): List<Beam> {
        val dir = when (beam.dir) {
            BeamDir.UP -> {
                when (encounteredChar) {
                    '/' -> {
                        listOf(BeamDir.RIGHT)
                    }

                    '\\' -> {
                        listOf(BeamDir.LEFT)
                    }

                    '-' -> {
                        listOf(BeamDir.LEFT, BeamDir.RIGHT)
                    }

                    else -> {
                        listOf(beam.dir)
                    }
                }
            }

            BeamDir.LEFT -> {
                when (encounteredChar) {
                    '/' -> {
                        listOf(BeamDir.DOWN)
                    }

                    '\\' -> {
                        listOf(BeamDir.UP)
                    }

                    '|' -> {
                        listOf(BeamDir.UP, BeamDir.DOWN)
                    }
                    else -> {
                        listOf(beam.dir)
                    }
                }
            }

            BeamDir.DOWN -> {
                when (encounteredChar) {
                    '/' -> {
                        listOf(BeamDir.LEFT)
                    }

                    '\\' -> {
                        listOf(BeamDir.RIGHT)
                    }

                    '-' -> {
                        listOf(BeamDir.LEFT, BeamDir.RIGHT)
                    }
                    else -> {
                        listOf(beam.dir)
                    }
                }
            }

            BeamDir.RIGHT -> {
                when (encounteredChar) {
                    '/' -> {
                        listOf(BeamDir.UP)
                    }

                    '\\' -> {
                        listOf(BeamDir.DOWN)
                    }

                    '|' -> {
                        listOf(BeamDir.UP, BeamDir.DOWN)
                    }
                    else -> {
                        listOf(beam.dir)
                    }
                }
            }
        }
        return dir.map { Beam(BeamPos(beam.pos.row+ it.nexRow, beam.pos.col + it.nextCol), it) }

    }

    fun isInArea(input: List<CharArray>, curr: Beam): Boolean {
        return curr.pos.row < input.size && curr.pos.row >= 0 && curr.pos.col >= 0 && curr.pos.col < input[0].size
    }

    fun part1(input: List<CharArray>): Int {
        val energizedSet = HashSet<BeamPos>()
        val pathSet = HashSet<Beam>()
        val stack = Stack<Beam>()
        val first = Beam(BeamPos(0, 0), BeamDir.RIGHT)
        pathSet.add(first)
        energizedSet.add(first.pos)

        val second = getNextBeams(first, input[0][0])
        stack.addAll(second)
        var currBeam = stack.pop()
//        var count = 0
//        val records = Array(input.size) {IntArray(input[0].size)}
        while (isInArea(input, currBeam) || stack.isNotEmpty()) {
            if (!isInArea(input, currBeam)) {
//                "finish: ${currBeam}".println()
                currBeam = stack.pop()
            }

            if (!pathSet.add(currBeam)) {
                if (stack.isNotEmpty()){
                    currBeam = stack.pop()
                    continue
                } else {
                    break
                }
            }
            energizedSet.add(currBeam.pos)

//            records[currBeam.pos.row] [currBeam.pos.col]++
//            for (r in records.indices) {
//                for (c in records[r].indices) {
//                    if (records[r][c] != 0) {
//                        print(records[r][c])
//                    } else {
//                        print(".")
//                    }
//                }
//                println()
//            }
//            println()
//            currBeam.println()

                val nextBeams = getNextBeams(currBeam, input[currBeam.pos.row][currBeam.pos.col])
                var isAdded = false
                for (b in nextBeams) {
                    if (isInArea(input, b)) {
                        stack.add(b)
                        isAdded = true
                    }
                }
            if (isAdded) currBeam = stack.pop()

//                stack.addAll(nextBeams)

//            count ++
//            if (count == 100) break
        }

//        for (r in records.indices) {
//                for (c in records[r].indices) {
//                    if (records[r][c] != 0) {
//                        print(records[r][c])
//                    } else {
//                        print(".")
//                    }
//                }
//                println()

//            }
        return energizedSet.size
    }



    fun cal(input: List<CharArray>, first: Beam, encounteredChar: Char): Int {
        val energizedSet = HashSet<BeamPos>()
        val pathSet = HashSet<Beam>()
        val stack = Stack<Beam>()
        pathSet.add(first)
        energizedSet.add(first.pos)

        val second = getNextBeams(first, encounteredChar)
        stack.addAll(second)
        var currBeam = stack.pop()
//        var count = 0
//        val records = Array(input.size) {IntArray(input[0].size)}
        while (isInArea(input, currBeam) || stack.isNotEmpty()) {
            if (!isInArea(input, currBeam)) {
//                "finish: ${currBeam}".println()
                currBeam = stack.pop()
            }

            if (!pathSet.add(currBeam)) {
                if (stack.isNotEmpty()){
                    currBeam = stack.pop()
                    continue
                } else {
                    break
                }
            }
            energizedSet.add(currBeam.pos)

//            records[currBeam.pos.row] [currBeam.pos.col]++
//            for (r in records.indices) {
//                for (c in records[r].indices) {
//                    if (records[r][c] != 0) {
//                        print(records[r][c])
//                    } else {
//                        print(".")
//                    }
//                }
//                println()
//            }
//            println()
//            currBeam.println()

            val nextBeams = getNextBeams(currBeam, input[currBeam.pos.row][currBeam.pos.col])
            var isAdded = false
            for (b in nextBeams) {
                if (isInArea(input, b)) {
                    stack.add(b)
                    isAdded = true
                }
            }
            if (isAdded) currBeam = stack.pop()

//                stack.addAll(nextBeams)

//            count ++
//            if (count == 100) break
        }


//        for (r in records.indices) {
//                for (c in records[r].indices) {
//                    if (records[r][c] != 0) {
//                        print(records[r][c])
//                    } else {
//                        print(".")
//                    }
//                }
//                println()

//            }
        return energizedSet.size
    }
    fun part2(input: List<CharArray>): Int {
        var max = 0
        for (i in input[0].indices) {
            val first = Beam(BeamPos(0, i), BeamDir.DOWN)
            max = Math.max(max, cal(input, first, input[0][i]))
        }
        for (i in input[0].indices) {
            val first = Beam(BeamPos(input.size-1, i), BeamDir.UP)
            max = Math.max(max, cal(input, first, input[input.size-1][i]))
        }
        for (i in input.indices) {
            val first = Beam(BeamPos(i, 0), BeamDir.RIGHT)
            max = Math.max(max, cal(input, first, input[i][0]))
        }
        for (i in input.indices) {
            val first = Beam(BeamPos(i, input[0].size-1), BeamDir.LEFT)
            max = Math.max(max, cal(input, first, input[i][input[0].size-1]))
        }

        return max
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day16_test").map { it.toCharArray() }
    part1(testInput).println()
    check(part1(testInput) == 46)
    part2(testInput).println()
    check(part2(testInput) == 51)

    val input = readInput("Day16").map { it.toCharArray() }
    part1(input).println()
    part2(input).println()
}
