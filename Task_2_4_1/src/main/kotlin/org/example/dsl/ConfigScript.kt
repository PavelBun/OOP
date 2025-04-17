// src/main/kotlin/org/example/dsl/ConfigScript.kt
package org.example.dsl

import org.example.core.models.Student
import java.time.LocalDate

class Configuration {
    val tasks: MutableList<Task> = mutableListOf()
    val groups: MutableList<Group> = mutableListOf()
}

// DSL Builder
fun configuration(block: Configuration.() -> Unit): Configuration =
    Configuration().apply(block)

// Tasks
class Task(
    val id: String,
    val name: String,
    var maxScore: Int = 0,
    var softDeadline: LocalDate? = null
)

fun Configuration.tasks(block: TasksBuilder.() -> Unit) {
    val builder = TasksBuilder()
    builder.block()
    tasks.addAll(builder.tasks)
}

class TasksBuilder {
    val tasks = mutableListOf<Task>()
    fun task(id: String, name: String, block: Task.() -> Unit) {
        tasks.add(Task(id, name).apply(block))
    }
}

// Groups
class Group(val name: String, val students: MutableList<Student> = mutableListOf())

fun Configuration.groups(block: GroupsBuilder.() -> Unit) {
    val builder = GroupsBuilder()
    builder.block()
    groups.addAll(builder.groups)
}

class GroupsBuilder {
    val groups = mutableListOf<Group>()
    fun group(name: String, block: Group.() -> Unit) {
        groups.add(Group(name).apply(block))
    }
}