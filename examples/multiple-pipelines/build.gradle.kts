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
}

val installNpmDependencies = tasks.register<com.moowork.gradle.node.npm.NpmTask>("installNpmDependencies") {
    description = "Installs dependencies from package.json"
    group = "build"
    setWorkingDir(file("src/main/js"))
    setArgs(listOf("install"))
    inputs.file("src/main/js/package.json")
    outputs.dir("src/main/js/node_modules")
}

dependencies {
    implementation(platform(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES))
    implementation(project(":engine"))
}
