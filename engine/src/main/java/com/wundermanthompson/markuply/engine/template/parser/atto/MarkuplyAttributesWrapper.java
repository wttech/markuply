package com.wundermanthompson.markuply.engine.template.parser.atto;

import com.wundermanthompson.markuply.engine.template.parser.TemplateParser;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "instance")
public class MarkuplyAttributesWrapper {

  private MarkuplyAttributes attributes;

  public boolean isComponentAttributePresent() {
    return attributes != null && attributes.isComponentAttributePresent();
  }

  public boolean isSectionAttributePresent() {
    return attributes != null && attributes.isSectionAttributePresent();
  }

  public String getComponentId() {
    return attributes.getComponentId();
  }

  public String getProps() {
    return attributes != null && attributes.getProps() != null
        ? attributes.getProps()
        : "";
  }

  public String getSectionName() {
    return attributes.getSectionName();
  }

  public void clear() {
    attributes = null;
  }

  public void extractValidAttribute(char[] buffer, int offset, int length, int valueOffset, int valueLength) {
    if (checkMatch(buffer, offset, length, TemplateParser.COMPONENT_ATTRIBUTE)) {
      String attributeValue = new String(buffer, valueOffset, valueLength);
      if (this.attributes == null) {
        this.attributes = MarkuplyAttributes.instance();
      }
      this.attributes.setComponentId(attributeValue);
    }
    if (checkMatch(buffer, offset, length, TemplateParser.PROPS_ATTRIBUTE)) {
      String attributeValue = new String(buffer, valueOffset, valueLength);
      if (this.attributes == null) {
        this.attributes = MarkuplyAttributes.instance();
      }
      this.attributes.setProps(attributeValue);
    }
    if (checkMatch(buffer, offset, length, TemplateParser.SECTION_ATTRIBUTE)) {
      String attributeValue = new String(buffer, valueOffset, valueLength);
      if (this.attributes == null) {
        this.attributes = MarkuplyAttributes.instance();
      }
      this.attributes.setSectionName(attributeValue);
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
