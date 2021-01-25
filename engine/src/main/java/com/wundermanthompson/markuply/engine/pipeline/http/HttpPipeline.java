package com.wundermanthompson.markuply.engine.pipeline.http;

import com.wundermanthompson.markuply.engine.pipeline.context.PageContext;
import com.wundermanthompson.markuply.engine.pipeline.http.proxy.request.RequestEnricher;
import com.wundermanthompson.markuply.engine.pipeline.http.repository.HttpPageRepository;
import com.wundermanthompson.markuply.engine.pipeline.http.repository.HttpPageResponse;
import reactor.core.publisher.Mono;

/**
 * Facade transforming HTML content in the web context. Returned object contains information about the response received
 * from the target server together with transformed content.
 *
 * <p>
 * Path is a mandatory parameter used by {@link HttpPageRepository} to determine where the content to be processed is located.
 *
 * <p>
 * {@link PageContext} is an optional parameter defining what data is available to Markuply components found on the page.
 *
 * <p>
 * {@link RequestEnricher} is an optional parameter allowing modifications of {@link HttpPageRepository} request.
 */
public interface HttpPipeline {

  /**
   * Retrieves and transform a page identified by the provided path.
   *
   * @param path identifier of content to be processed
   * @return response details with Markuply components processed
   */
  Mono<HttpPageResponse> render(String path);

  /**
   * Retrieves and transform a page identified by the provided path.
   *
   * @param path    identifier of content to be processed
   * @param context data to be retrieved by Markuply components
   * @return response details with Markuply components processed
   */
  Mono<HttpPageResponse> render(String path, PageContext context);

  /**
   * Retrieves and transform a page identified by the provided path.
   *
   * @param path     identifier of content to be processed
   * @param enricher {@link HttpPageRepository} request mutator
   * @return response details with Markuply components processed
   */
  Mono<HttpPageResponse> render(String path, RequestEnricher enricher);

  /**
   * Retrieves and transform a page identified by the provided path.
   *
   * @param path     identifier of content to be processed
   * @param context  data to be retrieved by Markuply components
   * @param enricher {@link HttpPageRepository} request mutator
   * @return response details with Markuply components processed
   */
  Mono<HttpPageResponse> render(String path, PageContext context, RequestEnricher enricher);

}
