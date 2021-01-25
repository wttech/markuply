package com.wundermanthompson.markuply.engine.pipeline.http.proxy.rule;

import com.wundermanthompson.markuply.engine.MarkuplyException;
import lombok.RequiredArgsConstructor;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

@RequiredArgsConstructor(staticName = "instance")
public class HeaderMatchingRuleFactory {

  /**
   * Accepts regex patterns starting optionally with an exclamation mark.
   *
   * @param pattern string representation of regular expression
   * @return parsed rule
   */
  public HeaderMatchingRule create(String pattern) {
    return allow(pattern);
  }

  public HeaderMatchingRule allow(String pattern) {
    try {
      Pattern compiledPattern = Pattern.compile(pattern);
      return AllowHeaderRule.of(compiledPattern);
    } catch (PatternSyntaxException e) {
      throw new MarkuplyException("Header name pattern is not a valid regular expression.");
    }
  }

}
