plugins {
    java
    jacoco
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

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport) // Добавлено
}

jacoco {
    toolVersion = "0.8.11"
}

tasks.jacocoTestReport {
    reports {
        xml.required.set(true)
        xml.outputLocation.set(layout.buildDirectory.file("reports/jacoco/test/jacocoTestReport.xml")) // Добавлено
        html.required.set(true)
    }
}

kotlin {
    jvmToolchain(21)
}

tasks.withType<JavaCompile> {
    targetCompatibility = "21"
}
