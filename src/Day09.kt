import kotlin.math.abs
fun main() {

    fun getArea(p1: Pair<Long, Long>, p2: Pair<Long, Long>) =
            (abs(p1.first - p2.first) + 1L) * (abs(p1.second - p2.second) + 1L)

    fun part1(input: List<String>): Long {
        val points = input.map { row -> 
            val (x,y) = row.split(",").map { it.toLong() }
            Pair(x, y)
        }
        // println("$points")
        return points.dropLast(1).asSequence().flatMapIndexed { index, point_i ->
            points.subList(index+1, points.size).asSequence().map { point_j -> 
                getArea(point_i, point_j)
            }
        }.maxOrNull() ?: 0L
    }

    fun part2(input: List<String>): Long {
        return 0L
    }

    val input = readInput("Day09")
    part1(input).println()
    // part2(input).println()
}
