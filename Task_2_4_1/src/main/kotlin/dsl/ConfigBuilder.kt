// dsl/ConfigBuilder.kt
package dsl

import model.*

class ConfigBuilder {
    val tasks = mutableListOf<Task>()
    val groups = mutableListOf<Group>()
    val settings = Settings()

    fun tasks(block: TasksBuilder.() -> Unit) {
        tasks.addAll(TasksBuilder().apply(block).build())
    }

    fun groups(block: GroupsBuilder.() -> Unit) {
        groups.addAll(GroupsBuilder().apply(block).build())
    }

    fun settings(block: SettingsBuilder.() -> Unit) {
        val builder = SettingsBuilder().apply(block)
        this.settings.apply {
            stylePenalty = builder.build().stylePenalty
            softDeadlinePenalty = builder.build().softDeadlinePenalty
            hardDeadlinePenalty = builder.build().hardDeadlinePenalty
        }
    }
}