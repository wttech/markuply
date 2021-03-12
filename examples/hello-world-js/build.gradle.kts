plugins {
    java
    id("org.springframework.boot")
    id("com.moowork.node")
}

group = rootProject.group
version = rootProject.version

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

repositories {
    mavenCentral()
    mavenLocal()
}

val installNpmDependencies = tasks.register<com.moowork.gradle.node.npm.NpmTask>("installNpmDependencies") {
    description = "Installs dependencies from package.json"
    group = "build"
    setWorkingDir(file("src/main/js"))
    setArgs(listOf("install"))
    inputs.file("src/main/js/package.json")
    outputs.dir("src/main/js/node_modules")
}

val watchDev = tasks.register<com.moowork.gradle.node.npm.NpmTask>("watchDev") {
    dependsOn(installNpmDependencies)
    group = "build"
    setWorkingDir(file("src/main/js"))
    inputs.file("src/main/js/package.json")
    inputs.dir("src/main/js/src")
    outputs.files("src/main/resources/public/bundle.js", "src/main/resources/public/main.bundle.css")
    setArgs(listOf("run", "start"))
}

dependencies {
    implementation(project(":engine"))
    implementation("io.wttech.graal.templating:templating-spring-boot-starter:0.1.0-SNAPSHOT")
}
