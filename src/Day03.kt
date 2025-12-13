fun main() {

    fun part1(input: List<String>): Int {
        // For each index, find the max to left. O(n)
        // Iterate from right keeping track of the right max and combined (left + right) max. O(n)
        // Return the combined max.
        var ans = 0
        for (curr in input) {
            var leftMaxes = mutableListOf<Int>()
            var leftMax = curr.first().digitToInt()
            for (i in 0..<curr.length) {
                val digit = curr[i].digitToInt()
                if (digit > leftMax) {
                    leftMax = digit
                }
                leftMaxes.add(leftMax)
            }
            var rightMax = curr.last().digitToInt()
            var totalMax = 0
            // We do not include the current index in the right max calculation since it is included in the left max calculation.
            for (i in curr.length - 2 downTo 0) {
                val digit = curr[i+1].digitToInt()
                if (digit > rightMax) {
                    rightMax = digit
                }
                totalMax = maxOf(totalMax, (leftMaxes[i] * 10) + rightMax)
            }
            ans += totalMax
        }

        return ans
    }

    fun part2(input: List<String>): Long {
        var ans = 0L
        for (curr in input) {
            var currMax = 0L
            var startIndex = 0
            for (i in 1..12) {
                val endIndex = minOf(curr.length - (12 - i), curr.length)
                val maxIndexAndNum = curr.substring(startIndex, endIndex).withIndex().maxBy { it.value.digitToInt() }
                startIndex += maxIndexAndNum.index + 1
                currMax += maxIndexAndNum.value.digitToInt() * Math.pow(10.0, 12.0 - i).toLong()
            }
            ans += currMax
        }
        return ans
    }

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
