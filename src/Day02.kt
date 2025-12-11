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

    fun part2(input: List<String>): Long {
        // Group the digits and find if substr starts with the same digits
        fun isNumInvalid(numStr: String): Boolean {
            for (i in 0..<numStr.length/2) {
                var currDigit = numStr.substring(0,i+1)
                // Match the whole group of digits till the end.
                var pattern = Regex("^($currDigit)+$")
                var remainingDigits = numStr.substring(i+1)
                if (pattern.matches(remainingDigits)) {
                    return true
                }
            }
            return false
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

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
