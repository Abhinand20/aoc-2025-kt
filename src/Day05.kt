fun LongRange.intersects(other: LongRange): Boolean {
    return this.first <= other.last && this.last >= other.first
}

fun LongRange.merge(other: LongRange): LongRange {
    return minOf(this.first, other.first)..maxOf(this.last, other.last)
}

fun List<LongRange>.isInRange(value: Long): Boolean {
    return this.any { value in it }
}

fun List<LongRange>.mergeOverlapping(): List<LongRange> {
    if (isEmpty()) return this

    return sortedBy { it.first }
        .fold(mutableListOf()) { acc, range ->
            val last = acc.lastOrNull()
            if (last != null && last.intersects(range)) {
                acc[acc.lastIndex] = last.merge(range)
            } else {
                acc.add(range)
            }
            acc
        }
}

fun main() {
    fun part1(input: List<String>): Int {
        val sepIdx = input.indexOf("")
        val allRanges = input.take(sepIdx).map { curr ->
            val (low, high) = curr.split('-').map { it.toLong() }
            low..high
        }
        val allIds = input.drop(sepIdx+1).map {
            it.toLong()
        }
        return allIds.count { value -> 
            allRanges.isInRange(value)
        }
    }

    fun part2(input: List<String>): Long {
        val sepIdx = input.indexOf("")
        val allRanges = input.take(sepIdx).map { curr ->
            val (low, high)= curr.split('-').map { it.toLong() }
            low..high
        }
        return allRanges.mergeOverlapping().sumOf { it.last - it.first + 1}
    }

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
