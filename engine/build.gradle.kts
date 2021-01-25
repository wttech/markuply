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

tasks.getByName<Jar>("jar") {
    archiveBaseName.set("markuply")
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
        create<MavenPublication>("markuply") {
            from(components["java"])

            artifactId = "markuply"
            artifact(sourcesJar.get())
            artifact(javadocJar.get())
        }
    }
}

val springBootVersion = "2.4.1"
val jsoupVersion = "1.12.2"
val attoParserVersion = "2.0.5.RELEASE"
val caffeineVersion = "2.8.8"
val ioCommonsVersion = "2.8.0"
val jmhVersion = "1.19"
val injectVersion = "1"
val junitVersion = "5.6.0"
val junitRunnerVersion = "1.6.0"
val assertVersion = "3.11.1"
val mockServerVersion = "4.9.0";

dependencies {
    // Spring Boot
    implementation(platform("org.springframework.boot:spring-boot-dependencies:${springBootVersion}"))
    api("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-aop")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor:${springBootVersion}")
    annotationProcessor("org.springframework.boot:spring-boot-autoconfigure-processor:${springBootVersion}")
    // HTML parsers
    implementation("org.jsoup:jsoup:${jsoupVersion}")
    implementation("org.attoparser:attoparser:${attoParserVersion}")
    implementation("commons-io:commons-io:${ioCommonsVersion}")
    // Caching
    api("com.github.ben-manes.caffeine:caffeine:${caffeineVersion}")
    // for @Inject annotation
    implementation("javax.inject:javax.inject:${injectVersion}")

    // Junit 5
    testImplementation("org.junit.jupiter:junit-jupiter-engine:${junitVersion}")
    testImplementation("org.junit.platform:junit-platform-runner:${junitRunnerVersion}")
    testImplementation("org.assertj:assertj-core:${assertVersion}")
    testImplementation("com.squareup.okhttp3:mockwebserver:${mockServerVersion}")
    // Spring
    testAnnotationProcessor("org.springframework.boot:spring-boot-configuration-processor:${springBootVersion}")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude("org.junit.vintage", "junit-vintage-engine")
    }
    testImplementation("io.projectreactor:reactor-test")
    // JMH
    testImplementation("org.openjdk.jmh:jmh-core:${jmhVersion}")
    testAnnotationProcessor("org.openjdk.jmh:jmh-generator-annprocess:${jmhVersion}")
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
