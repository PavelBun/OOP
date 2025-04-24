package model

import java.time.LocalDate

data class TaskResult(
    var compilationSuccess: Boolean = false,
    var styleCheckPassed: Boolean = false,
    var passedTests: Int = 0,
    var failedTests: Int = 0,
    var score: Int = 0,
    var pullRequestDate: LocalDate? = null // Добавляем поле для даты PR
)