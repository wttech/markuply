# Spring dev tools example

Simplest example processing templates located on the classpath with Spring dev tools enabled for classpath resource reloading.

1. Execute `./gradlew :examples:spring-dev-tools:bootRun`.
2. Visit `http://localhost:8080/templating/hello-world.html`.
3. `Hello Java!` text will be rendered.
4. Execute `./gradlew -t :examples:spring-dev-tools:build` in a new terminal.
5. Change `src/main/resources/hello-world.html` contents.
6. Change HelloComponent.java source code.
6. Revisit the original page to see the changes reflected in generated HTML.

