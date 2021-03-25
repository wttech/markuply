package io.wttech.markuply.engine.template.parser;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TemplateParserConfiguration {

  @Builder.Default()
  boolean propsDecodingEnabled = true;

}
