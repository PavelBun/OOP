// dsl/SettingsBuilder.kt
package dsl

import model.Settings

class SettingsBuilder {
    private val settings = Settings()

    fun stylePenalty(value: Int) {
        settings.stylePenalty = value
    }

    fun softDeadlinePenalty(value: Int) {
        settings.softDeadlinePenalty = value
    }

    fun hardDeadlinePenalty(value: Int) {
        settings.hardDeadlinePenalty = value
    }

    fun build() = settings
}