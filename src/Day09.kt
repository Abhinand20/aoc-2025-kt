

import kotlin.math.abs
fun main() {

    fun getArea(p1: Pair<Long, Long>, p2: Pair<Long, Long>) =
            abs((p1.first - p2.first + 1) * (p1.second - p2.second + 1))

    fun part1(input: List<String>): Long {
        val points = input.map { row -> 
            val (x,y) = row.split(",").map { it.toLong() }
            Pair(x, y)
        }
        // println("$points")
        return points.dropLast(1).flatMapIndexed { index, point_i ->
            points.subList(index+1, points.lastIndex).map { point_j -> 
                getArea(point_i, point_j)
            }
        }.max()
    }

    fun part2(input: List<String>): Long {
        return 0L
    }

    val input = readInput("Day09")
    part1(input).println()
    // part2(input).println()
}
