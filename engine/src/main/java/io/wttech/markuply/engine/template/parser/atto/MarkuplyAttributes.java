package io.wttech.markuply.engine.template.parser.atto;

import io.wttech.markuply.engine.template.parser.TemplateParser;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Holder of valid attributes.
 */
@Getter
@Setter
@RequiredArgsConstructor(staticName = "instance")
public class MarkuplyAttributes {

  private String componentId;
  private String props;
  private String sectionName;

  public boolean isComponentAttributePresent() {
    return componentId != null;
  }

  public boolean isSectionAttributePresent() {
    return sectionName != null;
  }

  public void extractValidAttribute(char[] buffer, int offset, int length, int valueOffset, int valueLength) {
    if (checkMatch(buffer, offset, length, TemplateParser.COMPONENT_ATTRIBUTE)) {
      String attributeValue = new String(buffer, valueOffset, valueLength);
      componentId = attributeValue;
    }
    if (checkMatch(buffer, offset, length, TemplateParser.PROPS_ATTRIBUTE)) {
      String attributeValue = new String(buffer, valueOffset, valueLength);
      props = attributeValue;
    }
    if (checkMatch(buffer, offset, length, TemplateParser.SECTION_ATTRIBUTE)) {
      String attributeValue = new String(buffer, valueOffset, valueLength);
      sectionName = attributeValue;
    }
  }

  private boolean checkMatch(char[] buffer, int offset, int length, String targetString) {
    if (length == targetString.length()) {
      for (int i = 0; i < length; i++) {
        if (buffer[offset + i] != targetString.charAt(i)) {
          return false;
        }
      }
      return true;
    } else {
      return false;
    }
  }
}
