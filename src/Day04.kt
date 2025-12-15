data class Pos(val x: Int, val y: Int) {
    operator fun plus(other: Pos) = Pos(x + other.x, y + other.y)

    val neighbors by lazy {
        (-1..1).flatMap { dx -> 
            (-1..1).map { dy -> Pos(dx, dy) }
        }.filter{ it != Pos(0,0) }
    }
}

// Return char at position if valid otherwise null
operator fun List<String>.get(p: Pos): Char? =
    if (p.x in indices && p.y in 0..<this[p.x].length)
        this[p.x][p.y]
    else null


fun main() {

    fun List<String>.getPositionsToRemove(): List<Pos> {
        return flatMapIndexed { x, row ->
            row.mapIndexedNotNull { y, char ->
                val p = Pos(x, y)
                if (char == '@') p else null
            }
        }.filter { pos ->
            val ngbCount = pos.neighbors.count { delta ->
                this[pos + delta] == '@'
            }
            ngbCount < 4
        }
    }

    fun MutableList<String>.removeRolls(positions: List<Pos>) {
        positions.forEach { pos ->
            val row = this[pos.x].toCharArray()
            row[pos.y] = '.'
            this[pos.x] = String(row)
        }
    }

    fun part1(input: List<String>): Int {
        return input.getPositionsToRemove().size
    }

    fun part2(input: List<String>): Int {
        var ans = 0
        var grid = input.toMutableList()
        do {
            val toRemove = grid.getPositionsToRemove()
            val count = toRemove.size
            ans += count
            if (count > 0) grid.removeRolls(toRemove)
        } while (count > 0)
        return ans
    }

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
