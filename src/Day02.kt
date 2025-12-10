import kotlin.math.abs
import java.math.BigInteger
fun main() {
    fun part1(input: List<String>): Long {
        fun isNumInvalid(numStr: String): Boolean {
            // Determine if the number meets the condition
            val numDigits = numStr.length
            if (numDigits % 2 != 0) return false
            return numStr.substring(0, numDigits/2) == numStr.substring(numDigits/2)
        }
        var allRanges = input[0].split(",")
        var ans = 0L
        for (range in allRanges) {
            var low = range.split("-")[0].toLong()
            var high = range.split("-")[1].toLong()
            for (num in low..high) {
                if (isNumInvalid(num.toString())) {
                    ans += num
                }
            }
        }
        return ans
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val input = readInput("Day02")
    part1(input).println()
    // part2(input).println()
}
