plugins {
    java
    id("org.springframework.boot")
    id("io.freefair.lombok")
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

val resilience4jVersion: String = "1.6.1"

dependencies {
    implementation(platform(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES))
    implementation(project(":engine"))
    implementation("io.wttech.graal.templating:templating-spring-boot-starter:0.1.0-SNAPSHOT")
    // disabled due to LambdaMetafactory producing error with dev tools enabled
//    developmentOnly("org.springframework.boot:spring-boot-devtools")
    // for @Inject annotation
    implementation("javax.inject:javax.inject:1")
    implementation("io.github.resilience4j:resilience4j-spring-boot2:${resilience4jVersion}")
    implementation("io.github.resilience4j:resilience4j-reactor:${resilience4jVersion}")
    implementation("org.springdoc:springdoc-openapi-webflux-ui:1.3.0")

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude("org.junit.vintage", "junit-vintage-engine")
    }
    testImplementation("io.projectreactor:reactor-test")
    // JUnit 5
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.6.0")
    testImplementation("org.junit.platform:junit-platform-runner:1.6.0")
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging.showStandardStreams = true
}
