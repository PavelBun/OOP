package model

data class Task(
    val id: String,
    val maxScore: Int,
    val softDeadline: String,
    val hardDeadline: String
)