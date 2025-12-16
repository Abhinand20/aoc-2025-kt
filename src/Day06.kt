fun String.parseNums(): List<Long> {
    return this.split("\\s+".toRegex()).filterNot { it.isEmpty() }.map { it.toLong() }
}

fun main() {
    fun part1(input: List<String>): Long {
        val gridLines = input.dropLast(1)
        val opsLine = input.last()
        val grid = gridLines.map { it.parseNums() }
        val ops = opsLine.trim().split(Regex("\\s+"))
        val numCols = grid.first().size

        // Iterate through all columns and perform the operation
        // then finally sum the values
        // sumOf {} iterates column-wise
        return (0..<numCols).sumOf { colIdx ->
            val nums = grid.map { row -> row[colIdx] }
            val op = ops[colIdx]

            nums.reduce { acc, value ->
                when (op) {
                    "+" -> acc + value
                    "*" -> acc * value
                    else -> error("Unknown op: $op")
                }
            }

        }
    }

    fun part2(input: List<String>): Long {
        return 0
    }

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
