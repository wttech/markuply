plugins {
    `java-library`
    id("io.freefair.lombok")
    id("maven-publish")
    id("com.github.hierynomus.license-report")
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

val sourcesJar = tasks.register<Jar>("sourcesJar") {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allJava)
}

val javadocJar = tasks.register<Jar>("javadocJar") {
    archiveClassifier.set("javadoc")
    dependsOn("javadoc")
    from(tasks.javadoc.get().destinationDir)
}

tasks.getByName<Javadoc>("javadoc") {
    if (JavaVersion.current().isJava9Compatible) {
        (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
    }
}

publishing {
    publications {
        create<MavenPublication>("js-renderer") {
            from(components["java"])

            artifact(sourcesJar.get())
            artifact(javadocJar.get())
        }
    }
}

val springBootVersion: String = "2.4.1"
val graalVersion: String = "20.3.0"

dependencies {
    implementation(platform("org.springframework.boot:spring-boot-dependencies:${springBootVersion}"))
    api(project(":engine"))
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor:${springBootVersion}")
    annotationProcessor("org.springframework.boot:spring-boot-autoconfigure-processor:${springBootVersion}")
    // for @Inject annotation
    implementation("javax.inject:javax.inject:1")
    // GraalVM JS module
    implementation("org.graalvm.js:js:${graalVersion}")
    implementation("org.graalvm.js:js-scriptengine:${graalVersion}")
    implementation("org.graalvm.sdk:graal-sdk:${graalVersion}")
    // Reactor Pool
    implementation("io.projectreactor.addons:reactor-pool:0.2.1")
    // Jackson
    implementation("com.fasterxml.jackson.core:jackson-databind:2.12.0")

    testAnnotationProcessor("org.springframework.boot:spring-boot-configuration-processor:${springBootVersion}")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude("org.junit.vintage", "junit-vintage-engine")
    }
    testImplementation("io.projectreactor:reactor-test")
    // Junit 5
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.6.0")
    testImplementation("org.junit.platform:junit-platform-runner:1.6.0")
    testImplementation("org.assertj:assertj-core:3.11.1")
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging.showStandardStreams = true
}

tasks {
    downloadLicenses {
        includeProjectDependencies = true
        dependencyConfiguration = "runtimeClasspath"
    }
}
