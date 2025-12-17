data class GridPos(val x: Int, val y: Int) {
    fun isValid(n: Int, m: Int) =  (
        x >= 0 && x < n && y >= 0 && y < m
    )
    fun getNextPos() = GridPos(x+1, y)
    fun getNeighbors() = Pair(GridPos(x, y-1), GridPos(x, y+1))
}

fun main() {

    fun part1(input: List<String>): Int {
        val m = input.first().length
        val n = input.size
        val startPos = GridPos(0, input.first().indexOf("S"))
        val visited = mutableSetOf<GridPos>()
        fun recur(currPos: GridPos, visited: MutableSet<GridPos>): Int {
            if (!currPos.isValid(n, m) || visited.contains(currPos)) {
                return 0
            }
            visited.add(currPos)
            if (input[currPos.x][currPos.y] == '^') {
                val (left, right) = currPos.getNeighbors()
                return 1 + recur(left,visited) + recur(right, visited)
            }
            return recur(currPos.getNextPos(), visited)

        }
        return recur(startPos, visited)
    }

    fun part2(input: List<String>): Long {
        return 0L
    }

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}
