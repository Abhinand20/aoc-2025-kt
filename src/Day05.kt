data class Range(val low: Long, val high: Long) {
    fun isOverlapping(other: Range): Boolean {
        return this.low <= other.high && other.low <= this.high
    }
    fun numElements() = high - low + 1
}

fun List<Range>.isInRange(value: Long): Boolean {
    return this.map { range ->
        value >= range.low && value <= range.high
    }.filter { it == true }.isNotEmpty()
}

fun List<Range>.getMergedRanges(): List<Range> {
    if (this.isEmpty()) return this
    val ranges = this.sortedBy { it.low }
    val mergedRanges = mutableListOf<Range>(ranges.first())
    for (range in ranges.subList(1, ranges.size)) {
        if (mergedRanges.last().isOverlapping(range)) {
            // update the intervals
            var updatedVal = Range(mergedRanges.last().low, maxOf(mergedRanges.last().high, range.high))
            @Suppress("JAVA_MODULE_DOES_NOT_EXPORT_PACKAGE") // False positive
            mergedRanges.removeLast()
            mergedRanges.add(updatedVal)
        } else {
            mergedRanges.add(range)
        }
    }
    return mergedRanges.toList()
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

    fun part2(input: List<String>): Long {
        val sepIdx = input.indexOf("")
        val allRanges = input.subList(0, sepIdx).map { curr ->
            val tokens = curr.split('-')
            Range(tokens[0].toLong(), tokens[1].toLong())
        }
        val merged = allRanges.getMergedRanges()
        println("Merged: $merged")
        return merged.map { it.numElements() }.sum()
    }

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
