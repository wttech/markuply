package com.wundermanthompson.markuply.examples.contentastemplate.pebble;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.template.PebbleTemplate;
import com.wundermanthompson.markuply.engine.component.Markuply;
import com.wundermanthompson.markuply.engine.component.method.resolver.properties.Props;
import com.wundermanthompson.markuply.engine.component.method.resolver.section.ChildrenRenderer;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.StringWriter;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.util.Map;

@Component
public class PebbleComponent {

  private final PebbleEngine engine = new PebbleEngine.Builder().build();

  @Markuply("pebble")
  public Mono<String> render(@Props Map<String, Object> props, ChildrenRenderer children) {
    // process the inner content
    return children.render()
        // and pass it to the Pebble engine
        .map(innerContent -> processTemplate(innerContent, props));
  }

  private String processTemplate(String template, Map<String, Object> props) {
    try {
      // compile the template
      PebbleTemplate compiledTemplate = engine.getLiteralTemplate(template);

      // render to a String using props
      Writer writer = new StringWriter();
      compiledTemplate.evaluate(writer, props);

      // trim() to remove the additional new line characters around the template itself
      // not mandatory
      return writer.toString().trim();
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

}
