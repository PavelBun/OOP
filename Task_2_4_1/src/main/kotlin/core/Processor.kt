// Processor.kt
package core

import dsl.ConfigBuilder

import java.io.File
import model.*
import java.time.LocalDate
import java.util.concurrent.TimeUnit

class Processor(private val config: ConfigBuilder) {
    private val gitService = GitService()
    private val tempDir = File("temp_repos").apply {
        mkdir()
        deleteRecursively() // Очищаем перед началом работы
        mkdir()
    }

    fun process() {
        config.groups.forEach { group ->
            group.students.forEach { student ->
                processStudent(student)
            }
        }
    }

    private fun processStudent(student: Student) {
        try {
            println("\n--- Проверка студента: ${student.name} ---")
            val repoDir = File(tempDir, "${student.githubId}_${System.currentTimeMillis()}")

            // Логируем путь клонирования
            println("Целевая директория: ${repoDir.absolutePath}")

            gitService.cloneRepo(student.repoUrl, repoDir)


            student.results = config.tasks.map { task ->
                println("Проверка задачи: ${task.id}")
                val result = TaskResult()
                processTask(repoDir, task, result)
                println("Результат: ${result.score} баллов")
                result
            }
        } catch (e: Exception) {
            println("Критическая ошибка: ${e.message}")
            student.results = config.tasks.map { TaskResult(score = 0) }        }
    }

    private fun processTask(repoDir: File, task: Task, result: TaskResult) {
        // Шаг 1: Компиляция
        result.pullRequestDate = getPullRequestDate(repoDir)
        result.compilationSuccess = compileProject(repoDir)

        // Шаг 2: Проверка стиля (если компиляция успешна)
        if (result.compilationSuccess) {
            result.styleCheckPassed = checkCodeStyle(repoDir)
        }

        // Шаг 3: Запуск тестов (если проверка стиля пройдена)
        if (result.compilationSuccess) {
            runTests(repoDir, result)
        }


        calculateScore(task, result)
    }

    private fun compileProject(dir: File): Boolean {
        return try {
            val process = ProcessBuilder("javac", "-d", "build", "src/main/java/**/*.java")
                .directory(dir)
                .start()

            process.waitFor(2, TimeUnit.MINUTES)
            process.exitValue() == 0
        } catch (e: Exception) {
            false
        }
    }

    // core/Processor.kt
    private fun checkCodeStyle(dir: File): Boolean {
        return try {
            println("Запуск Checkstyle для: ${dir.absolutePath}")
            val checkstyleJar = File("checkstyle-10.12.5-all.jar")
            val config = File("google_checks.xml")

            // Проверяем существование файлов
            require(checkstyleJar.exists()) { "Checkstyle JAR не найден" }
            require(config.exists()) { "Конфиг Checkstyle не найден" }

            val process = ProcessBuilder(
                "java",
                "-jar", checkstyleJar.absolutePath,
                "-c", config.absolutePath,
                dir.absolutePath
            ).redirectOutput(ProcessBuilder.Redirect.DISCARD)
                .start()

            val success = process.waitFor(2, TimeUnit.MINUTES)
            val exitCode = process.exitValue()

            // Checkstyle возвращает 0 только если нет ошибок
            println("Результат Checkstyle: ${if (exitCode == 0) "Успех" else "Ошибки"}")
            exitCode == 0
        } catch (e: Exception) {
            false
        }
    }

    private fun runTests(dir: File, result: TaskResult) {
        try {
            val process = ProcessBuilder("java", "-jar", "junit-platform-console-standalone.jar", "--class-path", "build", "--scan-classpath")
                .directory(dir)
                .start()

            val output = process.inputStream.bufferedReader().readText()
            result.apply {
                passedTests = Regex("Tests succeeded: (\\d+)").find(output)?.groupValues?.get(1)?.toInt() ?: 0
                failedTests = Regex("Tests failed: (\\d+)").find(output)?.groupValues?.get(1)?.toInt() ?: 0
            }
        } catch (e: Exception) {
            result.passedTests = 0
            result.failedTests = 0
        }
    }

    private fun calculateScore(task: Task, result: TaskResult) {
        val maxScore = task.maxScore

        // Базовый расчет
        var score = when {
            !result.compilationSuccess -> 0
            else -> (maxScore * 0.8 * result.passedTests / (result.passedTests + result.failedTests)).toInt()
        }

        // Штраф за стиль (20 баллов)
        if (!result.styleCheckPassed) {
            score = (score - 20).coerceAtLeast(0)
        }

        // Штраф за дедлайн
        score -= calculateDeadlinePenalty(task, result.pullRequestDate)

        result.score = score.coerceIn(0..maxScore)
    }
    private fun calculateDeadlinePenalty(task: Task, prDate: LocalDate?): Int {
        prDate ?: return task.maxScore // Если дата неизвестна - макс. штраф

        val softDeadline = LocalDate.parse(task.softDeadline)
        val hardDeadline = LocalDate.parse(task.hardDeadline)

        return when {
            prDate.isAfter(hardDeadline) -> task.maxScore
            prDate.isAfter(softDeadline) -> (task.maxScore * 0.5).toInt()
            else -> 0
        }
    }


    private fun getPullRequestDate(repoDir: File): LocalDate? {
        val process = ProcessBuilder("git", "log", "--merges", "--pretty=format:%cs", "-1")
            .directory(repoDir)
            .start()

        return try {
            val dateStr = process.inputStream.bufferedReader().readLine()
            LocalDate.parse(dateStr)
        } catch (e: Exception) {
            null
        }
    }
}