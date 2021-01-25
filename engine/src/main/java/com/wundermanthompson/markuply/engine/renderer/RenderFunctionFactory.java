package com.wundermanthompson.markuply.engine.renderer;

import com.wundermanthompson.markuply.engine.template.graph.RenderFunction;

public interface RenderFunctionFactory {

  RenderFunction create(String html);

}
