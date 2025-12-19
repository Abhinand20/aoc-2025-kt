#!/usr/bin/env kotlin

import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class DayStatus(
    val day: Int,
    val part1Solved: Boolean,
    val part2Solved: Boolean,
    val hasInput: Boolean,
    val solutionFile: String?
)

val NUM_DAYS = 12

fun analyzeSolutionFile(file: File): Pair<Boolean, Boolean> {
    val content = file.readText()
    
    // Check if part1 function has actual implementation (not just returning 0)
    val part1Match = Regex("""fun part1\(.*?\): \w+ \{\s*([^}]*)\s*\}""", RegexOption.DOT_MATCHES_ALL)
        .find(content)
    val part1Solved = part1Match?.let { match ->
        val body = match.groupValues[1].trim()
        !body.matches(Regex("""return\s+0\s*""")) && body.isNotEmpty()
    } ?: false
    
    // Check if part2 function has actual implementation (not just returning 0)
    val part2Match = Regex("""fun part2\(.*?\): \w+ \{\s*([^}]*)\s*\}""", RegexOption.DOT_MATCHES_ALL)
        .find(content)
    val part2Solved = part2Match?.let { match ->
        val body = match.groupValues[1].trim()
        !body.matches(Regex("""return\s+0\s*""")) && body.isNotEmpty()
    } ?: false
    
    return part1Solved to part2Solved
}

fun generateStarDisplay(part1: Boolean, part2: Boolean): String {
    return when {
        part1 && part2 -> "⭐⭐"
        part1 -> "⭐⚫"
        else -> "⚫⚫"
    }
}

fun generateProgressBar(solved: Int, total: Int): String {
    val percentage = if (total > 0) (solved * 100) / total else 0
    val filledBlocks = (percentage / 8) // Each block represents 8%
    val emptyBlocks = NUM_DAYS - filledBlocks

    return "█".repeat(filledBlocks) + "░".repeat(emptyBlocks) + " $percentage%"
}

fun main() {
    val srcDir = File("src")
    if (!srcDir.exists() || !srcDir.isDirectory) {
        println("Error: src directory not found!")
        return
    }
    
    val days = mutableListOf<DayStatus>()
    
    for (day in 1..NUM_DAYS) {
        val dayStr = String.format("%02d", day)
        val solutionFile = File(srcDir, "Day$dayStr.kt")
        val inputFile = File(srcDir, "Day$dayStr.txt")
        
        if (solutionFile.exists()) {
            val (part1Solved, part2Solved) = analyzeSolutionFile(solutionFile)
            days.add(DayStatus(
                day = day,
                part1Solved = part1Solved,
                part2Solved = part2Solved,
                hasInput = inputFile.exists(),
                solutionFile = "src/Day$dayStr.kt"
            ))
        } else {
            days.add(DayStatus(
                day = day,
                part1Solved = false,
                part2Solved = false,
                hasInput = inputFile.exists(),
                solutionFile = null
            ))
        }
    }
    
    val totalStars = days.sumOf { if (it.part1Solved) 1 else 0 } + days.sumOf { if (it.part2Solved) 1 else 0 }
    val solvedDays = days.count { it.part1Solved || it.part2Solved }
    
    // Generate solution table rows
    val solutionRows = days.joinToString("\n") { dayStatus ->
        val dayStr = String.format("%02d", dayStatus.day)
        val problemLink = "[Day ${dayStatus.day}](https://adventofcode.com/2025/day/${dayStatus.day})"
        val stars = generateStarDisplay(dayStatus.part1Solved, dayStatus.part2Solved)
        
        val solutionLink = if (dayStatus.solutionFile != null) {
            "[Kotlin](${dayStatus.solutionFile})"
        } else {
            "—"
        }
        
        val notes = when {
            dayStatus.part1Solved && dayStatus.part2Solved -> "✅ Complete"
            dayStatus.part1Solved -> "🚧 Part 1 only"
            dayStatus.solutionFile != null -> "📝 Started"
            else -> ""
        }
        
        "| $dayStr | $problemLink | $stars | $solutionLink | $notes |"
    }
    
    val currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM d, yyyy"))
    val progressBar = generateProgressBar(totalStars, NUM_DAYS * 2)
    
    val readme = """
        |# 🎄 Advent of Code 2025 - Kotlin Solutions
        |
        |[![Kotlin](https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white)](https://kotlinlang.org/)
        |[![AoC](https://img.shields.io/badge/AoC-2025-brightgreen?style=for-the-badge)](https://adventofcode.com/2025)
        |
        |My solutions for [Advent of Code 2025](https://adventofcode.com/2025) written in Kotlin.
        |
        |## 📊 Progress
        |
        |```
        |Stars collected: $totalStars/${NUM_DAYS * 2} ⭐
        |Days completed: $solvedDays/$NUM_DAYS 📅
        |Progress: $progressBar
        |```
        |
        |## 📅 Solutions
        |
        || Day | Problem | Stars | Solution | Notes |
        ||-----|---------|-------|----------|-------|
        |$solutionRows
        |
        |## 🚀 Running the Solutions
        |
        |This project uses the [Advent of Code Kotlin Template](https://github.com/kotlin-hands-on/advent-of-code-kotlin-template).
        |
        |### Prerequisites
        |- [Kotlin](https://kotlinlang.org/) (included with the project)
        |- JDK 17 or higher
        |
        |### Running a specific day
        |```bash
        |# Run Day 01
        |./gradlew run --args="Day01"
        |
        |# Or run directly with Kotlin
        |cd src
        |kotlin Day01.kt
        |```
        |
        |### Running all solutions
        |```bash
        |./gradlew run
        |```
        |
        |## 📁 Project Structure
        |
        |```
        |src/
        |├── Day01.kt          # Day 1 solution
        |├── Day01.txt         # Day 1 input
        |├── Day02.kt          # Day 2 solution
        |├── Day02.txt         # Day 2 input
        |├── ...
        |├── Utils.kt          # Utility functions
        |└── ...
        |update-readme.kts     # Script to update this README
        |```
        |
        |## 🔧 Updating Progress
        |
        |After solving a new problem, run the update script:
        |
        |```bash
        |kotlin update-readme.kts
        |```
        |
        |This will automatically analyze your solutions and update this README with the latest progress.
        |
        |---
        |
        |🎄 **Happy Coding!** 🎄
        |
        |*Last updated: $currentDate*
        |""".trimMargin()
    
    File("README.md").writeText(readme)
    println("✅ README.md updated successfully!")
    println("📊 Progress: $totalStars/${NUM_DAYS * 2} stars, $solvedDays/$NUM_DAYS days completed")
}

main()