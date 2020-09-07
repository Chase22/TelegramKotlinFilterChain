plugins {
    id("org.jetbrains.kotlin.jvm") version "1.3.72"

    `java-library`
}

val mockitoVersion = "3.5.10"

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.telegram:telegrambots-meta:4.9.1")

    testImplementation("org.junit.jupiter:junit-jupiter:5.6.2")
    testImplementation("org.mockito:mockito-core:$mockitoVersion")
    testImplementation("org.mockito:mockito-junit-jupiter:$mockitoVersion")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}
