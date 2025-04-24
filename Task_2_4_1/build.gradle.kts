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
    implementation("org.junit.platform:junit-platform-console:1.10.1")
}

application {
    mainClass.set("MainKt")
}

kotlin {
    jvmToolchain(21)
}

tasks.withType<JavaCompile> {
    targetCompatibility = "21"
}
jacoco {
    toolVersion = "0.8.11" // Укажите актуальную версию
}
tasks.jacocoTestReport {
    reports {
        xml.required.set(true)
        html.required.set(true)
        csv.required.set(false)
    }
}