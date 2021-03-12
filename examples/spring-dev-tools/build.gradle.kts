plugins {
    java
    id("org.springframework.boot")
}

group = rootProject.group
version = rootProject.version

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES))
    implementation(project(":engine"))
    developmentOnly("org.springframework.boot:spring-boot-devtools:2.4.1")
}
