package dsl

import model.Task

// TasksBuilder.kt
class TasksBuilder {
    private val tasks = mutableListOf<Task>()

    fun task(id: String, block: TaskBuilder.() -> Unit) {
        val taskBuilder = TaskBuilder(id)
        taskBuilder.block()
        tasks.add(taskBuilder.build())
    }

    fun build() = tasks
}