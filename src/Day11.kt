fun String.getSource(): String =
    this.split(":").first()

fun String.getNeighbors(): List<String> =
    this.split(":").last().trim().split(" ")

const val START_NODE = "you"
const val END_NODE = "out"

fun main() {

    fun getNumPaths(graph: Map<String, List<String>>, node: String, path: MutableSet<String>): Int {
        if (node == END_NODE) return 1
        var ans = 0
        path.add(node)
        for (ngb in graph.getOrDefault(node, emptyList())) {
            if (!path.contains(ngb)) {
                ans += getNumPaths(graph, ngb, path)
            }
        }
        path.remove(node)
        return ans
    }

    fun part1(input: List<String>): Int {
        val graph = input.associate { it.getSource() to it.getNeighbors() }
        return getNumPaths(graph, START_NODE, mutableSetOf())
    }

    fun part2(input: List<String>): Long {
        return 0L
    }

    val input = readInput("Day11")
    part1(input).println()
    // part2(input).println()
}
