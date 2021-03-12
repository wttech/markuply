package io.wttech.markuply.engine.renderer;

import io.wttech.markuply.engine.template.graph.RenderFunction;

public interface RenderFunctionFactory {

  RenderFunction create(String html);

}
