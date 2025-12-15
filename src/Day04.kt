data class Pos(var x: Int, var y: Int)

fun List<String>.getElement(p: Pos): Char {
    return this[p.x].elementAt(p.y)
}

fun main() {

    fun part1(input: List<String>): Int {
        val n = input.size
        val m = input[0].length
        var ans = 0

        fun isValid(i: Int, j: Int): Boolean {
            return i >= 0 && i < n && j >= 0 && j < m
        }

        fun shouldInclude(grid: List<String>, i: Int, j: Int): Boolean {
            val currPos = Pos(i, j)
            // println("At Pos: $i,$j")
            if (!grid.getElement(currPos).equals('@')) {
                return false
            }
            var numRolls = 0
            for (dx in -1..1) {
                for (dy in -1..1) {
                    if (dx == 0 && dy == 0) { continue }
                    val newPos = Pos(i + dx, j + dy)
                    // println("Checking new pos: $newPos")
                    if (isValid(newPos.x, newPos.y) && grid.getElement(newPos).equals('@')) {
                        ++numRolls
                    }
                }
            }
            return numRolls < 4
        }
        
        for (i in 0..<n) {
            for (j in 0..<m) {
                if (shouldInclude(input, i, j)) {
                    ++ans
                }
            }
        }
        return ans
    }

    fun part2(input: List<String>): Long {
        return 0
    }

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
