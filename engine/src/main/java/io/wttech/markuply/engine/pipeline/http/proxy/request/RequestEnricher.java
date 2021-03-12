package io.wttech.markuply.engine.pipeline.http.proxy.request;

/**
 * Represents a function mutating the provided builder.
 * Can return a completely new builder if necessary.
 */
@FunctionalInterface
public interface RequestEnricher {

  HttpPageRequest.Builder enrich(HttpPageRequest.Builder builder);

}
