import kotlin.math.*
import java.util.PriorityQueue

// const val NUM_ELEMS = 1000
const val NUM_ELEMS = 10

data class Point(val x: Int, val y: Int, val z: Int) {
    fun distance(other: Point): Double {
        return Math.sqrt(
            (x - other.x).toDouble().pow(2.0) +
            (y - other.y).toDouble().pow(2.0) +
            (z - other.z).toDouble().pow(2.0)
        )
    }
}

data class QueueElement(val dist: Double, val p1: Point, val p2: Point)

fun List<String>.parsePoints(): List<Point> {
    return this.map { row -> 
        val (x, y, z) = row.split(",").map{ it.toInt() }
        Point(x,y,z)
    }
}

fun main() {
    fun computeDistances(points: List<Point>): List<QueueElement> {
        return points.dropLast(1).flatMapIndexed { index, point_i ->
            points.subList(index+1, points.size).map { point_j ->
                val dist = point_i.distance(point_j)
                QueueElement(dist, point_i, point_j)
            }
        }
    }

    fun findFinalCounts(groupings: Map<Point, List<Point>>): Int {
        val allVisited = mutableSetOf<Point>()
        fun dfs(graph: Map<Point, List<Point>>, node: Point, visited: MutableSet<Point>) {
            if (visited.contains(node)) return
            visited.add(node)
            for (ngb in graph.getOrDefault(node, listOf())) {
                if (!visited.contains(ngb)) {
                    dfs(graph, ngb, visited)
                }
            }
        }
        val allSizes = mutableListOf<Int>()
        for ((node, _) in groupings) {
            val currVisited = mutableSetOf<Point>()
            if (!allVisited.contains(node)) {
                dfs(groupings, node, currVisited)
                // println("Node: $node, visited: $currVisited")
                allVisited += currVisited
                allSizes.add(currVisited.size)
            }
        }
        // println("$allSizes")
        return allSizes.sortedDescending().take(3).reduce { acc, value -> acc * value }
    }

    fun part1(input: List<String>): Int {
        // 1. Find distances
        val pq = PriorityQueue<QueueElement>(compareBy { it.dist })
        computeDistances(input.parsePoints()).forEach { pq.add(it) }
        // 2. Make groupings
        val connections = (0..<NUM_ELEMS).map { pq.remove() }
            .flatMap { listOf(it.p1 to it.p2, it.p2 to it.p1) }
            .groupBy({it.first}, {it.second})
        // 3. DFS to find final counts
        return findFinalCounts(connections)
    }

    fun numElementsInCircuit(graph: Map<Point, List<Point>>, node: Point, visited: MutableSet<Point>): Int {
        if (visited.contains(node)) return 0
        visited.add(node)
        var numElems = 1
        for (ngb in graph.getOrDefault(node, listOf())) {
            if (!visited.contains(ngb)) {
                numElems += numElementsInCircuit(graph, ngb, visited)
            }
        }
        return numElems
    }

    fun part2(input: List<String>): Long {
        val pq = PriorityQueue<QueueElement>(compareBy { it.dist })
        computeDistances(input.parsePoints()).forEach { pq.add(it) }
        val connections = mutableMapOf<Point, MutableList<Point>>()
        val numNodes = input.size
        while (true) {
            if (pq.isEmpty()) {
                error("Could not find the answer.")
            }
            val curr = pq.remove()
            connections.getOrPut(curr.p1) { mutableListOf() }.add(curr.p2)
            connections.getOrPut(curr.p2) { mutableListOf() }.add(curr.p1)
            val currNodes = numElementsInCircuit(connections, curr.p1, mutableSetOf())
            // println("$currNodes, $numNodes")
            if (currNodes == numNodes) {
                return (curr.p1.x * curr.p2.x).toLong()
            }
        }
    }

    val input = readInput("Day08")
    // part1(input).println()
    part2(input).println()
}
