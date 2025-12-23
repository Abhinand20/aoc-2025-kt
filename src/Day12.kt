import kotlin.io.path.Path
import kotlin.io.path.readText
fun main() {

    fun readContent(name: String) = Path("src/$name.txt").readText().trim()

    fun part1(input: String): Int {
        val numTiles = input.split("\n\n").take(6).map{ row -> 
            row.count { it == '#' }
        }
        val valid = input.split("\n\n").last().lines().map { line ->
            val (dims, nums) = line.split(": ")
            val (w,h) = dims.split("x").map { it.toInt() }
            val counts = nums.split(" ").map { it.toInt() }
            val curr = counts.foldIndexed(0) { idx, acc, count ->
                acc + (count * numTiles[idx])
            }
            curr <= (w * h)
        }.count { it == true }
        return valid
    }

    fun part2(input: String): Long {
        return 0L
    }

    val input = readContent("Day12")
    part1(input).println()
    part2(input).println()
}
