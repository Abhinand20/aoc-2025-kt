data class Range(val low: Long, val high: Long)

fun List<Range>.isInRange(value: Long): Boolean {
    return this.map { range ->
        value >= range.low && value <= range.high
    }.filter { it == true }.isNotEmpty()
}

fun main() {

    fun part1(input: List<String>): Int {
        val sepIdx = input.indexOf("")
        val allRanges = input.subList(0, sepIdx).map { curr ->
            val tokens = curr.split('-')
            Range(tokens[0].toLong(), tokens[1].toLong())
        }
        val allIds = input.subList(sepIdx+1, input.size).map {
            it.toLong()
        }
        return allIds.map { currVal ->
            allRanges.isInRange(currVal)
        }.count { it == true }
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
