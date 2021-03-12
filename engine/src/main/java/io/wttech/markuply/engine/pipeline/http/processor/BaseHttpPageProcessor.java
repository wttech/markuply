package io.wttech.markuply.engine.pipeline.http.processor;

import io.wttech.markuply.engine.pipeline.context.PageContext;
import io.wttech.markuply.engine.pipeline.http.HttpPipeline;
import io.wttech.markuply.engine.pipeline.http.proxy.request.RequestEnricher;
import io.wttech.markuply.engine.pipeline.http.repository.HttpPageRepository;
import io.wttech.markuply.engine.pipeline.http.repository.HttpPageResponse;
import io.wttech.markuply.engine.renderer.RenderFunctionProvider;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * Combines {@link HttpPageRepository} with {@link RenderFunctionProvider} to provide a facade transforming
 * content retrieved from external server.
 */
@Slf4j
@RequiredArgsConstructor(staticName = "of")
public class BaseHttpPageProcessor implements HttpPipeline {

  @NonNull
  private final HttpPageRepository pageRepository;
  @NonNull
  private final RenderFunctionProvider renderFunctionProvider;

  @Override
  public Mono<HttpPageResponse> render(String path, RequestEnricher enricher) {
    log.debug("Processing started for path: {}", path);
    return pageRepository.getPage(path, builder -> Mono.just(enricher.enrich(builder)))
        .flatMap(response -> processContent(path, response));
  }

  @Override
  public Mono<HttpPageResponse> render(String path) {
    log.debug("Processing started for path: {}", path);
    return pageRepository.getPage(path)
        .flatMap(response -> processContent(path, response));
  }

  @Override
  public Mono<HttpPageResponse> render(String path, PageContext context) {
    log.debug("Processing started for path: {}", path);
    return pageRepository.getPage(path)
        .flatMap(response -> processContent(path, response, context));
  }

  @Override
  public Mono<HttpPageResponse> render(String path, PageContext context, RequestEnricher enricher) {
    log.debug("Processing started for path: {}", path);
    return pageRepository.getPage(path, builder -> Mono.just(enricher.enrich(builder)))
        .flatMap(response -> processContent(path, response, context));
  }

  private Mono<HttpPageResponse> processContent(String path, HttpPageResponse response) {
    return processContent(path, response, PageContext.empty());
  }

  private Mono<HttpPageResponse> processContent(String path, HttpPageResponse response, PageContext context) {
    return renderFunctionProvider.get(path, response.getBody())
        .flatMap(renderFunction -> renderFunction.render(context))
        .map(response::withBody);
  }

}
