plugins {
    id("java")
}

group = "io.projectreactor.samples"
version = "1.0-SNAPSHOT"
java {
//    targetCompatibility = JavaVersion.toVersion(21)
}

repositories {
    mavenCentral()
    maven(url="https://repo.spring.io/milestone")
}

dependencies {
    implementation("io.projectreactor:reactor-core:3.6.0-RC1")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}