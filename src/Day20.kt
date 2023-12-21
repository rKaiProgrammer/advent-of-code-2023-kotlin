import java.util.LinkedList

enum class ModuleStatus {
    ON, OFF, NONE
}

enum class Pulse {
    HIGH, LOW
}

data class Task(var pulse: Pulse, val module: Module, val preModule: Module)
sealed class Module() {
    abstract val name: String
    abstract var nextModules: List<Module>
    abstract var status: ModuleStatus
}

data class BroadcasterM(
    override val name: String,
    override var nextModules: List<Module> = listOf(),
    override var status: ModuleStatus = ModuleStatus.NONE
) : Module()

data class FlipFlopM(
    override val name: String,
    override var nextModules: List<Module> = listOf(),
    override var status: ModuleStatus = ModuleStatus.OFF,
) : Module()


data class ConjunctionM(
    override val name: String,
    override var nextModules: List<Module> = listOf(),
    override var status: ModuleStatus = ModuleStatus.NONE,
    val rememberModules: HashMap<String, Pulse> = HashMap(),
    var rememberModulesHighCount: Int = 0,

    ) : Module()

data class Output(
    override val name: String,
    override var nextModules: List<Module> = listOf(),
    override var status: ModuleStatus = ModuleStatus.NONE
) : Module()

fun main() {
    fun part1(input: List<String>): Int {

        val map = HashMap<String, Module>()
        for (s in input) {
            val arrowIndex = s.indexOf("->")
            val moduleName = s.substring(1, arrowIndex - 1)
            when (s[0]) {
                'b' -> {
                    map[moduleName] = BroadcasterM(name = moduleName)
                }

                '%' -> {
                    map[moduleName] = FlipFlopM(name = moduleName)
                }

                '&' -> {
                    map[moduleName] = ConjunctionM(name = moduleName)
                }
            }
        }
        // test2
        map["output"] = Output(name = "output")
        map["rx"] = Output(name = "rx")

        for (s in input) {
            val arrowIndex = s.indexOf("->")
            val moduleName = s.substring(1, arrowIndex - 1)
            val nextNames = s.substring(arrowIndex + 3, s.length).split(", ").toList()
            val nextModules = mutableListOf<Module>()
            val currModule = map[moduleName]!!
            for (name in nextNames) {
                val nextModule = map[name]!!
                nextModules.add(nextModule)
                if (nextModule is ConjunctionM) {
                    nextModule.rememberModules[moduleName] = Pulse.LOW
                }
            }
            currModule.nextModules = nextModules
        }
        fun isInitStatus(): Boolean {
            for ((k, module) in map) {
                if ((module is FlipFlopM && module.status == ModuleStatus.ON) ||
                    (module is ConjunctionM && module.rememberModulesHighCount != 0)
                ) {
                    return false
                }
            }
            return true
        }

        var buttonTimes = 0
        val lowCountList = mutableListOf<Int>()
        val highCountList = mutableListOf<Int>()
        do {
            var lowCount = 0
            var highCount = 0
            "------${++buttonTimes}-----".println()

            val queue = LinkedList<Task>()
            val broadcaster = map["roadcaster"]!!
            "button -low-> broadcaster".println()
            lowCount++
            for (m in broadcaster.nextModules) {
                queue.add(Task(Pulse.LOW, m, broadcaster))
            }
            while (queue.isNotEmpty()) {
                val currTask = queue.poll()
                "${currTask.preModule.name} -${currTask.pulse} -> ${currTask.module.name}".println()
                if (currTask.pulse == Pulse.LOW) lowCount++
                if (currTask.pulse == Pulse.HIGH) highCount++

                when (currTask.module) {
                    is FlipFlopM -> {
                        if (currTask.pulse == Pulse.LOW) {
                            val nextPulse = if (currTask.module.status == ModuleStatus.OFF) Pulse.HIGH else Pulse.LOW
                            currTask.module.status =
                                if (currTask.module.status == ModuleStatus.OFF) ModuleStatus.ON else ModuleStatus.OFF
                            for (m in currTask.module.nextModules) {
                                queue.add(Task(nextPulse, m, currTask.module))
                            }
                        }

                    }

                    is ConjunctionM -> {
                        if (currTask.pulse == Pulse.LOW) {
                            if (currTask.module.rememberModules[currTask.preModule.name] == Pulse.HIGH) {
                                currTask.module.rememberModules[currTask.preModule.name] = Pulse.LOW
                                currTask.module.rememberModulesHighCount--
                            }
                        } else {
                            if (currTask.module.rememberModules[currTask.preModule.name] == Pulse.LOW) {
                                currTask.module.rememberModules[currTask.preModule.name] = Pulse.HIGH
                                currTask.module.rememberModulesHighCount++
                            }
                        }
                        for (m in currTask.module.nextModules) {
                            if (currTask.module.rememberModulesHighCount == currTask.module.rememberModules.size) {
                                queue.add(Task(Pulse.LOW, m, currTask.module))
                            } else {
                                queue.add(Task(Pulse.HIGH, m, currTask.module))
                            }
                        }
                    }

                    is BroadcasterM -> {
                        // won't happen
                    }

                    is Output -> {

                    }
                }

            }
            lowCountList.add(lowCount)
            highCountList.add(highCount)
        } while (!isInitStatus())


        lowCountList.joinToString(",").println()
        highCountList.joinToString(",").println()

        var lowSum = 0
        var highSum = 0
        for (i in 0 until 1000) {
            val pos = i % lowCountList.size
            lowSum += lowCountList[pos]
            highSum += highCountList[pos]
        }
        println("$lowSum, $highSum")


        return lowSum * highSum
    }


    fun part2(input: List<String>): Long {
        var res = 0L

        return res
    }


    val testInput = readInput("Day20_test2")
    println(part1(testInput))
//    check(part1(testInput) == 11687500)
//    println(part2(testInput))
//    check(part2(testInput) == 167409079868000L)

    val input = readInput("Day20")
//    part1(input).println()
//    part2(input).println()
}
