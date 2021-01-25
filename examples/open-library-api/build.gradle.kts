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

// In fact it works without this part but I'll leave it here for now.
//
// NOTE: This is really important, some graalvm runtime libs need to be 'unpacked' when bundled by Spring Boot in order
// for the ScriptEngine classes to load properly.
//
// Example exception if this is not done:
// ScriptEngineManager providers.next(): javax.script.ScriptEngineFactory: Provider com.oracle.truffle.js.scriptengine.GraalJSEngineFactory could not be instantiated
// tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
//     requiresUnpack("**/*js*.*", "**/*regex*.*")
// }

//
// Graal compiler section
//
//val graal by configurations.creating
//
//val copyToLib = tasks.register<Copy>("copyToLib") {
//    into("$buildDir/compiler")
//    from(graal)
//}
//
//tasks.getByName<org.springframework.boot.gradle.tasks.run.BootRun>("bootRun") {
//    args=listOf("-ea",
//            "-XX:+UnlockExperimentalVMOptions",
//            "-XX:+EnableJVMCI",
//            "--module-path=build/compiler",
//            "--upgrade-module-path=build/compiler/compiler-20.0.0.jar")
//    dependsOn(copyToLib)
//}
//
//tasks.withType<Jar> {
//    dependsOn(copyToLib)
//}
//
//dependencies {
//    // GraalVM compiler, only for performance
//    implementation("org.graalvm.compiler:compiler:20.0.0")
//    implementation("org.graalvm.truffle:truffle-api:20.0.0")
//    graal("org.graalvm.compiler:compiler:20.0.0")
//    graal("org.graalvm.truffle:truffle-api:20.0.0")
//    graal("org.graalvm.sdk:graal-sdk:20.0.0")
//}
//
// end of GraalVM compiler section
//

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
    implementation(project(":js-renderer"))
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
