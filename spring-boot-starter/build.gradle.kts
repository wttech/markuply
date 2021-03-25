plugins {
    `java-library`
    id("io.freefair.lombok")
    id("maven-publish")
    id("com.github.hierynomus.license-report")
    signing
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
        create<MavenPublication>("starter") {
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
                name.set("Markuply Spring Boot Starter")
                description.set("Spring Boot Starter for the Markuply library")
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

signing {
    setRequired({
        (!version.toString().endsWith("SNAPSHOT") && gradle.taskGraph.hasTask("publish"))
    })
    val signingKey: String? by project
    val signingPassword: String? by project
    useInMemoryPgpKeys(signingKey, signingPassword)
    sign(publishing.publications["starter"])
}

dependencies {
    api(project(":engine"))
}

