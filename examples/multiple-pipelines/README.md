# Multiple pipelines example

How to register more than one HTTP pipeline. Does not use application.yml file. 

Main pipeline processes pages from /resources/public/main folder.
Secondary pipeline processes pages from /resources/public/secondary folder.

1. Execute `./gradlew :examples:multiple-pipelines:bootRun`.
1. Go to `multiple-pipelines/src/main/js`.
1. Execute `npm install`.
1. Execute `npm run start`.
1. Visit `http://localhost:8080/main/hello-world.html`.
1. `Hello main pipeline!` text will be rendered.
1. Visit `http://localhost:8080/secondary/hello-world.html`.
1. `Hello secondary pipeline!` text will be rendered.
