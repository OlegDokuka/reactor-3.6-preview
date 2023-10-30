plugins {
    id("java")
}

group = "io.projectreactor.samples"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven(url="https://repo.spring.io/snapshot")
}

dependencies {
    implementation("io.projectreactor:reactor-core:3.6.0-SNAPSHOT")
    implementation("io.projectreactor.netty:reactor-netty-http:1.1.12")
    implementation("io.micrometer:context-propagation:1.0.6")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}