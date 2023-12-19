fun main() {

    fun part1(input: List<String>): Int {
        data class Workflow(val rules: String) {
            // x>10:one,m<20:two,a>30:R,A
            private val rulesArr = rules.split(",")
            fun matchRules(map: HashMap<Char, Int>): String {
                for (i in rulesArr.indices) {
                    val rule = rulesArr[i]
                    if (i == rulesArr.size - 1) {
                        return rule
                    } else {
                        val key = rule[0]
                        val operator = rule[1]
                        val value = rule.substring(2, rule.indexOf(":")).toInt()
                        val result = rule.substring(rule.indexOf(":") + 1, rule.length)
                        val diff = map[key]!! - value
                        if ((operator == '>' && diff > 0) || (operator == '<' && diff < 0)) {
                            return result
                        }
                    }
                }
                return "ERROR"
            }
        }

        var res = 0
        var isSettingWorkflows = true
        val map = HashMap<String, Workflow>()
        for (s in input) {
            if (s.isEmpty()) {
                isSettingWorkflows = false
            } else if (isSettingWorkflows) {
                map[s.substring(0, s.indexOf("{"))] =
                    Workflow(s.substring(s.indexOf("{") + 1, s.indexOf("}")))
            } else {
                // {x=787,m=2655,a=1222,s=2876}
                val arr = s.substring(1, s.length - 1).split(",")
                val xams = HashMap<Char, Int>()
                var xamsSum = 0
                for (a in arr) {
                    val v = a.substring(2, a.length).toInt()
                    xams[a[0]] = v
                    xamsSum += v
                }
                var result = map["in"]!!.matchRules(xams)
                while (result != "A" && result != "R") {
                    result = map[result]!!.matchRules(xams)
                }
//                "x=${xams['x']}, $result".println()
                if (result == "A") {
                    res += xamsSum
                }
            }
        }

        return res
    }


    fun part2(input: List<String>): Long {
        data class Range(val start: Int, val end: Int) {
            val count = end - start + 1
        }

        var res = 0L
        val workflowMap = HashMap<String, String>()
        for (s in input) {
            if (s.isEmpty()) {
                break
            } else {
                workflowMap[s.substring(0, s.indexOf("{"))] =
                    s.substring(s.indexOf("{") + 1, s.indexOf("}"))
            }
        }
        fun count(workflow: String, xamsMap: HashMap<Char, Range>) {
            val new = HashMap(xamsMap)
            // x>10:one,m<20:two,a>30:R,A
            val rulesArr = workflow.split(",")
            for (i in rulesArr.indices) {
                val rule = rulesArr[i]
                if (i == rulesArr.size - 1) {
                    when (rule) {
                        "A" -> {
                            var n = 1L
                            for ((k, v) in new) {
                                n *= v.count
                            }
                            res += n
                        }

                        "R" -> {
                        }

                        else -> {
                            count(workflowMap[rule]!!, new)
                        }
                    }
                } else {
                    val key = rule[0]
                    val operator = rule[1]
                    val value = rule.substring(2, rule.indexOf(":")).toInt()
                    val result = rule.substring(rule.indexOf(":") + 1, rule.length)

                    val rangeL = Range(new[key]!!.start, value - 1)
                    val rangeH = Range(value + 1, new[key]!!.end)

                    val xamsMapL = HashMap(new)
                    xamsMapL[key] = rangeL

                    val xamsMapH = HashMap(new)
                    xamsMapH[key] = rangeH

                    if (operator == '>' && rangeH.count > 0) {
                        when (result) {
                            "A" -> {
                                var n = 1L
                                for ((k, v) in xamsMapH) {
                                    n *= v.count
                                }
                                res += n
                            }

                            "R" -> {
                            }

                            else -> {
                                count(workflowMap[result]!!, xamsMapH)
                            }
                        }
                    } else if (operator == '<' && rangeL.count > 0) {
                        when (result) {
                            "A" -> {
                                var n = 1L
                                for ((k, v) in xamsMapL) {
                                    n *= v.count
                                }
                                res += n
                            }

                            "R" -> {
                            }

                            else -> {
                                count(workflowMap[result]!!, xamsMapL)
                            }
                        }

                    }
                    if (operator == '<') {
                        new[key] = Range(value, new[key]!!.end)
                    } else {
                        new[key] = Range(new[key]!!.start, value)
                    }
                }
            }

        }

        val map = hashMapOf(
            'x' to Range(1, 4000),
            'm' to Range(1, 4000),
            'a' to Range(1, 4000),
            's' to Range(1, 4000),
        )
        count(workflowMap["in"]!!, map)


        return res
    }


    val testInput = readInput("Day19_test")
    println(part1(testInput))
    check(part1(testInput) == 19114)
    println(part2(testInput))
    check(part2(testInput) == 167409079868000L)

    val input = readInput("Day19")
    part1(input).println()
    part2(input).println()
}
