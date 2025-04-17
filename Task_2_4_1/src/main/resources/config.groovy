// Удалите строку @Grab, если не используете автоматическую загрузку зависимостей
// @Grab('org.codehaus.groovy:groovy-all:3.0.9')
import org.example.dsl.configuration
import java.time.LocalDate

configuration {
    tasks {
        task("lab1", "Простые числа") {
            maxScore = 100
            softDeadline = LocalDate.parse("2023-10-01")
        }
    }
    groups {
        group("Group A") {
            student("ivanov", "Иван Иванов", "https://github.com/ivanov/repo")
        }
    }
}