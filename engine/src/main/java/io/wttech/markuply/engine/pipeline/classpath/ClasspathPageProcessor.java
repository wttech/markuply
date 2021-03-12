package io.wttech.markuply.engine.pipeline.classpath;

import io.wttech.markuply.engine.pipeline.context.PageContext;
import io.wttech.markuply.engine.renderer.RenderFunctionProvider;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * Combines {@link ClasspathPageRepository} with {@link RenderFunctionProvider} to provide a facade transforming
 * content retrieved from local resources.
 */
@RequiredArgsConstructor(staticName = "of")
public class ClasspathPageProcessor implements ClasspathPipeline {

  @NonNull
  private final ClasspathPageRepository pageRepository;
  @NonNull
  private final RenderFunctionProvider renderFunctionProvider;

  /**
   * Retrieves and transform a page identified by the provided path.
   *
   * @param path identifier of content to be processed
   * @return response details with Markuply components processed
   */
  public Mono<String> render(String path) {
    return render(path, PageContext.empty());
  }

  /**
   * Retrieves and transform a page identified by the provided path.
   *
   * @param path    identifier of content to be processed
   * @param context data to be retrieved by Markuply components
   * @return response details with Markuply components processed
   */
  public Mono<String> render(String path, PageContext context) {
    return pageRepository.getPage(path)
        .flatMap(content -> renderFunctionProvider.get(path, content))
        .flatMap(renderFunction -> renderFunction.render(context));
  }

}
