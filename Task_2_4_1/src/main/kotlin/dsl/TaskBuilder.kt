package dsl
import model.Task

// TaskBuilder.kt
class TaskBuilder(val id: String) {
    var maxScore: Int = 0
    var softDeadline: String = ""
    var hardDeadline: String = ""

    fun build() = Task(
        id = id,
        maxScore = maxScore,
        softDeadline = softDeadline,
        hardDeadline = hardDeadline
    )
}