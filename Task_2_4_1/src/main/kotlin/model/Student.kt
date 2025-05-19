package model

// model/Student.kt
data class Student(
    val githubId: String,
    val name: String,
    val repoUrl: String
) {

    var results: List<TaskResult> = emptyList()
    val score: Int get() = results.sumOf { it.score }
}