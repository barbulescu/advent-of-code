plugins {
    kotlin("jvm") version "1.9.20"
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.1")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.1")
    testImplementation("org.assertj:assertj-core:3.24.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.1")
}



tasks {
    wrapper {
        gradleVersion = "8.5"
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

