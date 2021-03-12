rootProject.name = "markuply"

include("engine")
include("examples:hello-world-classpath")
include("examples:hello-world-http")
include("examples:hello-world-js")
include("examples:multiple-pipelines")
include("examples:inner-content-as-template")
include("examples:open-library-api")
include("examples:spring-dev-tools")

pluginManagement {
    plugins {
        id("net.linguica.maven-settings") version "0.5"
        id("pl.allegro.tech.build.axion-release") version "1.10.1"
        id("org.springframework.boot") version "2.4.1"
        id("io.freefair.lombok") version "4.1.6"
        id("com.moowork.node") version "1.3.1"
        id("com.github.hierynomus.license-report") version "0.15.0"
    }
}

