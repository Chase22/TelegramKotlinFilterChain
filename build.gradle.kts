plugins {
    id("org.jetbrains.kotlin.jvm") version "1.6.0"

    `java-library`
}

val mockitoVersion = "3.5.10"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.telegram:telegrambots-meta:4.9.1")

//    testImplementation("org.junit.jupiter:junit-jupiter:5.6.2")

    testImplementation("io.kotest:kotest-runner-junit5:4.6.3")
    testImplementation("io.kotest:kotest-assertions-core:4.0.7")
    testImplementation("io.mockk:mockk:1.12.1")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}
