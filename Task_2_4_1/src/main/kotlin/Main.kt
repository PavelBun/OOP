// Main.kt
import dsl.ConfigBuilder
import java.io.File
import javax.script.ScriptEngineManager
import core.Processor
fun main() {
    val engine = ScriptEngineManager().getEngineByExtension("kts")
    val config = engine.eval(File("config.kts").reader()) as ConfigBuilder

    Processor(config).process()
    generateReport(config)
}
