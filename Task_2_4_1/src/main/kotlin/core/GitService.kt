package core

import java.io.File
import java.util.concurrent.TimeUnit

class GitService {
    fun cloneRepo(url: String, dest: File) {
        // Удаляем существующую директорию
        if (dest.exists()) {
            dest.deleteRecursively()
            println("Удалена существующая директория: ${dest.absolutePath}")
        }

        println("Клонируем репозиторий: $url в ${dest.absolutePath}")
        val process = ProcessBuilder("git", "clone", url, dest.absolutePath)
            .start()

        val success = process.waitFor(5, TimeUnit.MINUTES)
        val error = process.errorStream.bufferedReader().readText()

        if (!success || process.exitValue() != 0) {
            println("Ошибка клонирования:\n$error")
            throw RuntimeException("Clone failed for $url")
        }
    }
}