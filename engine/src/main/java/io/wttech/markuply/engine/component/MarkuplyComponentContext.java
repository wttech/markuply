package io.wttech.markuply.engine.component;

import io.wttech.markuply.engine.pipeline.context.PageContext;
import io.wttech.markuply.engine.template.graph.NamedRenderFunctions;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Contains all data passed to Markuply component by the engine
 */
@AllArgsConstructor(staticName = "of")
@Getter
public class MarkuplyComponentContext {

  private final String props;
  private final PageContext pageContext;
  private final NamedRenderFunctions sections;

}
