import kotlin.collections.takeLast
import kotlin.math.pow

data class Machine(val lights: String, val buttons: List<List<Int>>, val joltage: List<Int>)

fun List<String>.parseMachines(): List<Machine> = 
    this.map { row ->
            val lights = Regex("\\[(.+)\\]").find(row)
            val buttons = Regex("\\((.+?)\\)").findAll(row).map { it.groupValues[1].split(",").map { it.trim().toInt() }}
            val joltage = Regex("\\{(.+)\\}").findAll(row).flatMap { it.groupValues[1].split(",").map { it.trim().toInt() } }
            Machine(lights!!.groupValues.get(1), buttons.toList(), joltage.toList())
        }

fun List<Int>.toBinary(maxVal: Int): Int =
        this.fold(0) { mask, index -> 
            mask or (1 shl(maxVal - 1 - index) )
        }

fun List<List<Int>>.getCombBinary(maxVal: Int): Int =
        this.map { it.toBinary(maxVal) }.fold(0) { acc, value -> 
            acc xor value 
        }


fun main() {

    fun <T> getCombinations(elems: List<T>, numElems: Int): List<List<T>> {
        if (numElems == 0) return listOf(emptyList())
        if (elems.isEmpty()) return emptyList()

        val result = mutableListOf<List<T>>()
        for (i in elems.indices) {
            val curr = elems[i]
            val remaining = elems.subList(i+1, elems.size)
            for (comb in getCombinations(remaining, numElems - 1)) {
                // Add current elem to build up the combination
                result.add(listOf(curr) + comb)
            }
        }
        return result
    }

    fun doMatch(pattern: String, btns: List<List<Int>>): Boolean {
        val target = pattern.replace('.', '0').replace('#', '1').toInt(2)
        val candidate = btns.getCombBinary(pattern.length)
        return  candidate == target
    }

    fun findMinButtons(machine: Machine): Int {
        val N = machine.buttons.size
        val ans = -1
        // Iterate through all possible patterns
        for (i in 1..N) {
            val comb = getCombinations(machine.buttons, i)
            for (c in comb) {
                if (doMatch(machine.lights, c)) {
                    return i
                }
            }
        }
        return ans
    }

    fun part1(input: List<String>): Int {
        /* make groupings upto len(arr) and check pattern for each
            -> Convert button to binary for each in list and XOR
            -> Convert battern to binary
         */
        val machines = input.parseMachines()
        return machines.map { findMinButtons(it) }.sum()
    }

    fun part2(input: List<String>): Long {
        return 0L
    }

    val input = readInput("Day10")
    part1(input).println()
    // part2(input).println()
}
