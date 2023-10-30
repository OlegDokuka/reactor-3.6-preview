plugins {
    id("java")
}

group = "io.projectreactor.samples"
version = "1.0-SNAPSHOT"

java {
    targetCompatibility = JavaVersion.VERSION_11
}

repositories {
    mavenCentral()
    maven(url="https://repo.spring.io/milestone")
}

dependencies {
    implementation("io.projectreactor:reactor-core:3.5.11")
    implementation("io.micrometer:context-propagation:1.0.6")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}