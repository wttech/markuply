package io.wttech.markuply.engine.pipeline.http.proxy.rule;

public interface HeaderMatchingRule {

  boolean matches(String headerName);

}
