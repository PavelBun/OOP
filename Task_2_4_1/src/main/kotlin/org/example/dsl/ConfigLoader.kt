// ConfigLoader.kt
package org.example.dsl

import groovy.lang.GroovyShell
import groovy.util.DelegatingScript
import org.codehaus.groovy.control.CompilerConfiguration
import java.io.File

object ConfigLoader {
    @JvmStatic // Добавьте эту аннотацию
    fun load(path: String): Configuration {
        val compilerConfig = CompilerConfiguration().apply {
            scriptBaseClass = DelegatingScript::class.qualifiedName
        }
        val shell = GroovyShell(compilerConfig)
        val script = shell.parse(File(path)) as DelegatingScript
        val config = Configuration()
        script.setDelegate(config)
        script.run()
        return config
    }
}