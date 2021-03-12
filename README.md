# Markuply

Spring based template enriching library with support for Javascript execution on GraalVM.

Markuply itself is not a rendering engine but rather a platform for introducing dynamic markup into static templates using an engine of your choosing.

![Architecture](engine/src/docs/antora/modules/ROOT/assets/images/architecture.svg)

## Documentation

Complete description of the library together with tutorials and how to articles can be found in the link below. 

[Reference documentation](https://wttech.github.io/markuply)

## Example projects

There are several example projects in the `/examples` subfolder showing how to integrate Markuply into your application.

* `hello-world-classpath` - processing templates located on the classpath
* `hello-world-http` - processing templates hosted on an external HTTP server
* `hello-world-js` - rendering component markup with JS renderer on GraalVM
* `inner-content-as-template` - treating component inner content as pebble template
* `multiple-pipelines` - defining and exposing two distinct processing pipelines with code configuration
* `open-library-api` - integration with Open Library API rendering book details based on the provided ISBN
* `spring-dev-tools` - processing templates located on the classpath with server restart on code / resource change

## Contributing

[Contributing rules](CONTRIBUTING.md)

## Code of conduct

[Code of conduct](CODE_OF_CONDUCT.md)

## License

Markuply is licensed under [Apache License, Version 2.0](LICENSE).
