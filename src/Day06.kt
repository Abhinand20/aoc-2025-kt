import kotlin.io.path.Path
import kotlin.io.path.readText

fun String.parseNums(): List<Long> {
    return this.split("\\s+".toRegex()).filterNot { it.isEmpty() }.map { it.toLong() }
}

fun main() {
    
    fun readPart2Input(name: String) = Path("src/$name.txt").readText().lines()

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
        val grid = input.dropLast(1) // Preserve whitespace
        val ops = Regex("[+*]\\s+").findAll(input.last() + " ").map { it.value }.toList()
        // Each except last has (n-1) digits
        val numCols = ops.size
        var startIdx = 0
        return (0..<numCols).sumOf { colIdx ->
            val numDigits = ops[colIdx].length - 1
            // Go through the grid to create the numbers
            val endIndex = startIdx + numDigits
            val op = ops[colIdx].first()
            val nums = (startIdx..<endIndex).map { digitIdx -> 
                val num = StringBuilder()
                grid.map { row -> 
                    num.append(row[digitIdx])
                }
                num.toString().trim().toLong()
            }
            startIdx = endIndex + 1

            nums.reduce { acc, value ->
                when (op) {
                    '+' -> acc + value
                    '*' -> acc * value
                    else -> error("Unexpected op $op")
                }
            }

        }
    }

    val input = readInput("Day06")
    // part1(input).println()
    part2(readPart2Input("Day06")).println()
}
