data class Pos(var x: Int, var y: Int)

fun List<String>.getElement(p: Pos): Char {
    return this[p.x].elementAt(p.y)
}

fun MutableList<String>.removeRolls(positions: List<Pos>) {
    for (pos in positions) {
        var curr = StringBuilder(this[pos.x])
        curr.setCharAt(pos.y, '.')
        this[pos.x] = curr.toString()
    }
}

fun main() {

    fun getPositionsToRemove(grid: List<String>): List<Pos> {
        val n = grid.size
        val m = grid[0].length
        var ans = listOf<Pos>()

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
                if (shouldInclude(grid, i, j)) {
                    ans += Pos(i, j)
                }
            }
        }
        return ans
    }


    fun part1(input: List<String>): Int {
        return getPositionsToRemove(input).size
    }

    fun part2(input: List<String>): Int {
        // Find indices to remove
        var ans = 0
        var grid = input.toMutableList()
        while (true) {
            val toRemove = getPositionsToRemove(grid)
            if (toRemove.isEmpty()) {
                return ans
            }
            ans += toRemove.size
            grid.removeRolls(toRemove)

        }
    }

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
