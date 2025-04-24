import dsl.ConfigBuilder
import model.Group
import model.Student
import kotlinx.html.*
import kotlinx.html.stream.appendHTML

fun generateReport(config: ConfigBuilder) {
    val html = buildString {
        appendHTML().html {
            head {
                title { unsafe { +"Отчёт по лабораторным работам" } }
                style {
                    unsafe {
                        +"""
                        table { border-collapse: collapse; }
                        th, td { padding: 8px; border: 1px solid #ddd; }
                        """
                    }
                }
            }
            body {
                h1 { unsafe { +"Отчёт по лабораторным работам" } }
                config.groups.forEach { group ->
                    h2 { unsafe { +group.name } }
                    table {
                        tr {
                            th { unsafe { +"Студент" } }
                            th { unsafe { +"Баллы" } }
                            th { unsafe { +"Статус" } }
                        }
                        group.students.forEach { student ->
                            tr {
                                td { unsafe { +student.name } }
                                td { unsafe { +"${student.score}/100" } }
                                td {
                                    unsafe {
                                        +if (student.score >= 60) "Сдано" else "Не сдано"
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    println(html)
}