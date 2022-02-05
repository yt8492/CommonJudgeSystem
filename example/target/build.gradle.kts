plugins {
    kotlin("jvm")
    kotlin("plugin.serialization") version "1.6.10"
    application
    id ("com.github.johnrengelman.shadow") version "7.1.1"
}

group = "com.yt8492"
version = "1.0.0"

application {
    mainClass.set("com.yt8492.commonjudgesystem.example.target.MainKt")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("io.ktor:ktor-server-core:1.6.7")
    implementation("io.ktor:ktor-server-netty:1.6.7")
    implementation("io.ktor:ktor-serialization:1.6.7")
    implementation("io.ktor:ktor-auth-jwt:1.6.7")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
    implementation("org.jetbrains.exposed:exposed-core:0.34.1")
    implementation("org.jetbrains.exposed:exposed-dao:0.34.1")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.34.1")
    runtimeOnly("org.xerial:sqlite-jdbc:3.36.0.3")
    runtimeOnly("org.slf4j:slf4j-simple:1.7.32")
}
