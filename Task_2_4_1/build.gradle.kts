plugins {
    id("java")
    id("jacoco")
    kotlin("jvm") version "1.9.22"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlin:kotlin-scripting-jsr223:1.9.22")
    implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.9.1")
    implementation("com.github.spotbugs:spotbugs:4.8.2")
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.1") // Добавлено
}

application {
    mainClass.set("MainKt")
}
tasks.jacocoTestReport {
    val reportDir = file("../build/reports/jacoco/test")
    reports.xml.outputLocation = reportDir.resolve("jacocoTestReport.xml")
    reports {
        xml.required = true
    }

}


kotlin {
    jvmToolchain(21)
}

tasks.withType<JavaCompile> {
    targetCompatibility = "21"
}
