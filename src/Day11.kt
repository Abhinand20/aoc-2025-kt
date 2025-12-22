fun String.getSource(): String =
    this.split(":").first()

fun String.getNeighbors(): List<String> =
    this.split(":").last().trim().split(" ")

const val START_NODE = "you"
const val END_NODE = "out"
const val START_NODE_2 = "svr"

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

    fun getNumPathsWithConstraint(graph: Map<String, List<String>>, node: String, hasDAC: Boolean, hasFFT: Boolean, cache: MutableMap<Triple<String,Boolean,Boolean>,Long>): Long {
        val state = Triple(node, hasDAC, hasFFT)
        if (cache.containsKey(state)) return cache[state]!!
        val nowHasDAC = hasDAC || node == "dac"
        val nowHasFFT = hasFFT || node == "fft"
        if (node == END_NODE) return if (nowHasDAC && nowHasFFT) 1 else 0
        var ans = 0L
        for (ngb in graph.getOrDefault(node, emptyList())) {
            ans += getNumPathsWithConstraint(graph, ngb, nowHasDAC, nowHasFFT, cache)
        }
        cache[state] = ans
        return ans
    }

    fun part1(input: List<String>): Int {
        val graph = input.associate { it.getSource() to it.getNeighbors() }
        return getNumPaths(graph, START_NODE, mutableSetOf())
    }

    fun part2(input: List<String>): Long {
        val graph = input.associate { it.getSource() to it.getNeighbors() }
        val cache = mutableMapOf<Triple<String, Boolean, Boolean>, Long>()
        return getNumPathsWithConstraint(graph, START_NODE_2, false, false, cache)
    }

    val input = readInput("Day11")
    part1(input).println()
    part2(input).println()
}
