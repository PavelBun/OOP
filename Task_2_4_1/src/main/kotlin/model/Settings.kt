// model/Settings.kt
package model
data class Settings(
        var stylePenalty: Int = 20,
        var softDeadlinePenalty: Int = 50,
        var hardDeadlinePenalty: Int = 100
)

