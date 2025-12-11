fun Long.isSymmetric(): Boolean {
    val numStr = this.toString()
    val numDigits = numStr.length
    if (numDigits % 2 != 0) return false
    val half = numDigits/2
    return numStr.substring(0, half) == numStr.substring(half)
}

/**
 * (neat math trick) Checks if a number is periodic using the following method:
 *
 * 1. Concatenate the number with itself, making the repeating pattern continuous.
 *    Example: 565656  →  565656565656
 *
 * 2. Search for the first occurrence of the original number within this concatenated string,
 *    starting from position 1 (i.e., after the very first character).
 *    Example: For "5656", in "56565656", "5656" appears again starting at index 2.
 *
 * 3. If this occurrence is found at an index less than the length of the number,
 *    the number is periodic. The substring from index 0 up to that index is the repeating pattern.
 */
fun Long.isPeriodic(): Boolean {
    val s = this.toString()
    return (s + s).indexOf(s, 1) < s.length
}


fun main() {
    // Returns a sequence of long ranges to save memory.
    fun parseRanges(input: String): Sequence<LongRange> {
        return input.splitToSequence(",").map {
            rangeStr -> 
                val (low, high) = rangeStr.split("-")
                low.toLong()..high.toLong()
            }
    }

    fun part1(input: List<String>): Long {
        var allRanges = parseRanges(input.first())
        var ans = 0L
        for (range in allRanges) {
            for (num in range) {
                if (num.isSymmetric()) {
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
        var allRanges = parseRanges(input.first())
        var ans = 0L
        for (range in allRanges) {
            for (num in range) {
                if (num.isPeriodic()) {
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
