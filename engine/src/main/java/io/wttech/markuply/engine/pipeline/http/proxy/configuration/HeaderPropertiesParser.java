package io.wttech.markuply.engine.pipeline.http.proxy.configuration;

import io.wttech.markuply.engine.MarkuplyException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * Transforms data from properties file into a structured model.
 */
@Component
public class HeaderPropertiesParser {

  public HeaderProxyConfiguration parseCopyHeaderConfiguration(List<String> copyHeadersConfiguration) {
    HeaderProxyConfiguration.Builder headerBuilder = HeaderProxyConfiguration.builder();
    copyHeadersConfiguration.forEach(headerBuilder::pattern);
    return headerBuilder.build();
  }

  public StaticHeaderConfiguration parseStaticHeaderConfiguration(List<String> staticHeaderConfiguration) {
    StaticHeaderConfiguration.Builder staticBuilder = StaticHeaderConfiguration.builder();
    staticHeaderConfiguration.forEach(staticHeader -> {
      if (!staticHeader.contains(":")) {
        throw new MarkuplyException("Static header definition in addHeaders property must be in \"headerName:headerValue1,headerValue2\" format.");
      }
      String[] staticHeaderSplit = staticHeader.split(":");
      if (staticHeaderSplit.length != 2) {
        throw new MarkuplyException("Static header definition in addHeaders property must be in \"headerName:headerValue1,headerValue2\" format.");
      }
      String name = staticHeaderSplit[0];
      List<String> values = Arrays.asList(staticHeaderSplit[1].split(","));
      if (values.stream().anyMatch(String::isEmpty)) {
        throw new MarkuplyException("Static header values in addHeaders property must not be empty. Offending example: \"X-Custom-Header:,,\"");
      }
      staticBuilder.setHeader(name, values);
    });
    return staticBuilder.build();
  }

}
