package com.wundermanthompson.markuply.javascript;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wundermanthompson.markuply.javascript.context.ContextExecutor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;
import reactor.core.publisher.Mono;

/**
 * JS script file must contain a global function with a following signature.
 *
 * <p>Name: render First argument: name of type string Second argument: props of type string
 * Returned value: string representing the HTML
 *
 * <p>function render(name: string, props: string): string
 */
@AllArgsConstructor(staticName = "of")
@Builder(builderClassName = "Builder")
public class JavascriptRenderer {

  private static final String LANGUAGE = "js";
  private static final String RENDER_FUNCTION_NAME = "render";

  @NonNull
  private final ObjectMapper objectMapper;
  @NonNull
  private final ContextExecutor contextExecutor;

  public Mono<String> render(String viewName, Object props) {
    try {
      String serializedProps = objectMapper.writeValueAsString(props);
      return render(viewName, serializedProps);
    } catch (JsonProcessingException e) {
      return Mono.error(e);
    }
  }

  public Mono<String> render(String viewName, String props) {
    return contextExecutor.withContext(context -> executeRenderFunction(context, viewName, props));
  }

  private String executeRenderFunction(Context context, String viewName, String serializedProps) {
    try {
      Value func = context.getBindings(LANGUAGE).getMember(RENDER_FUNCTION_NAME);
      if (func == null) {
        throw new JavascriptRendererException("Compiled script does not contain a render(name, props) function");
      }
      Value result = func.execute(viewName, serializedProps);
      return result.asString();
    } catch (UnsupportedOperationException e) {
      throw new JavascriptRendererException("Compiled script does not contain a render(name, props) function");
    } catch (ClassCastException e) {
      throw new JavascriptRendererException("render() function result could not be converted to string.");
    }

  }

}
