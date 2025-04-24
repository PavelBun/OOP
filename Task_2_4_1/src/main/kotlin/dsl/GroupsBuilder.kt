package dsl

import model.Group
import model.Student

class GroupsBuilder {
    private val groups = mutableListOf<Group>()

    fun group(name: String, block: GroupBuilder.() -> Unit) {
        groups.add(GroupBuilder(name).apply(block).build())
    }

    fun build() = groups
}

class GroupBuilder(private val name: String) {
    private val students = mutableListOf<Student>()

    fun student(githubId: String, name: String, repoUrl: String) {
        students.add(Student(githubId, name, repoUrl))
    }

    fun build() = Group(name, students)
}