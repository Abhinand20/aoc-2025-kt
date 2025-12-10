import kotlin.math.abs
fun main() {
    fun part1(input: List<String>): Int {
        var start = 50
        var ans = 0
        val max = 100
        for (line in input) {
            val direction = line.first()
            val steps = line.substring(1).toInt()
            start = when (direction) {
                'L' -> ((start - steps) + max) % max
                'R' -> (start + steps) % max
                else -> throw IllegalArgumentException("Invalid direction: $direction")
            }
            if (start == 0) ans++
        }
        return ans
    }

    fun part2(input: List<String>): Int {
        var start = 50
        var ans = 0
        val max = 100
        for (line in input) {
            val direction = line.first()
            val steps = line.substring(1).toInt()
            start = when (direction) {
                'L' -> {
                    var newStart = start
                    for (i in 1..steps) {
                        newStart = (newStart - 1 + max).mod(max)
                        if (newStart == 0) ans++
                    }
                    newStart
                }
                'R' -> {
                    var newStart = start
                    for (i in 1..steps) {
                        newStart = (newStart + 1).mod(max)
                        if (newStart == 0) ans++
                    }
                    newStart
                }
                else -> throw IllegalArgumentException("Invalid direction: $direction")
            }
            // if (start == 0) ans++
        }
        return ans
    }

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
