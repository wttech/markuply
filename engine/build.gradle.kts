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
}

tasks.getByName<Jar>("jar") {
    archiveBaseName.set("markuply-spring-boot-starter")
}

java {
    withJavadocJar()
    withSourcesJar()
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

            artifactId = "markuply-spring-boot-starter"

            versionMapping {
                usage("java-api") {
                    fromResolutionOf("runtimeClasspath")
                }
                usage("java-runtime") {
                    fromResolutionResult()
                }
            }

            pom {
                name.set("Markuply")
                description.set("Spring library enriching static templates with dynamic data")
                url.set("https://wttech.github.io/markuply")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("tokrug")
                        name.set("Tomasz Krug")
                        email.set("tomasz.krug@gmail.com")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/wttech/markuply.git")
                    url.set("https://github.com/wttech/markuply")
                }
            }
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
