package io.wttech.markuply.engine.pipeline.http.proxy.rule;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.regex.Pattern;

@RequiredArgsConstructor(staticName = "of")
public class AllowHeaderRule implements HeaderMatchingRule {

  @NonNull
  private final Pattern pattern;

  @Override
  public boolean matches(String headerName) {
    return pattern.matcher(headerName).matches();
  }

}
