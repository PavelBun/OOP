@file:Suppress("unused")

import dsl.*
import model.*

val config = ConfigBuilder().apply {
    tasks {
        task("Task_2_1_1") {
            maxScore = 100
            softDeadline = "2025-02-14"
            hardDeadline = "2025-02-21"
        }
    }

    groups {
        group("23216") {
            student(
                githubId = "PavelBun",
                name = "Бунь Павел", // Используйте name вместо fullName
                repoUrl = "https://github.com/PavelBun/OOP.git"
            )
        }
    }

    settings {
        stylePenalty(20)
        softDeadlinePenalty(30)
        hardDeadlinePenalty(100)
    }
}

config