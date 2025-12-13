fun main() {

    fun findMaxNumberWithNDigits(curr: String, n: Int): Long {
        var currMax = 0L
        var startIndex = 0
        for (i in 1..n) {
            val endIndex = minOf(curr.length - (n - i), curr.length)
            val maxIndexAndNum = curr.substring(startIndex, endIndex).withIndex().maxBy { it.value.digitToInt() }
            startIndex += maxIndexAndNum.index + 1
            currMax += maxIndexAndNum.value.digitToInt() * Math.pow(10.0, (n - i).toDouble()).toLong()     
        }
        return currMax
    }

    fun part1(input: List<String>): Long {
        var ans = 0L
        for (curr in input) {
            ans += findMaxNumberWithNDigits(curr, 2)
        }
        return ans
    }

    fun part2(input: List<String>): Long {
        var ans = 0L
        for (curr in input) {
            ans += findMaxNumberWithNDigits(curr, 12)
        }
        return ans
    }

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
