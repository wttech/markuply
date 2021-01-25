package com.wundermanthompson.markuply.engine.renderer.registry;

import com.wundermanthompson.markuply.engine.component.ComponentDefinitionException;
import com.wundermanthompson.markuply.engine.component.MarkuplyComponent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * <p>
 * Holds information about all components and their IDs.
 */
@AllArgsConstructor
@Slf4j
public class ComponentRegistry {

  private final Map<String, MarkuplyComponent> components;

  public static ComponentRegistry instance() {
    return new ComponentRegistry(new HashMap<>());
  }

  public MarkuplyComponent get(String componentId) {
    return find(componentId)
        .orElseThrow(() -> new IllegalArgumentException(
            "Component named " + componentId + " is not registered"));
  }

  public Optional<MarkuplyComponent> find(String componentId) {
    return Optional.ofNullable(components.get(componentId));
  }

  public void register(String name, MarkuplyComponent component) {
    if (components.containsKey(name)) {
      throw new ComponentDefinitionException("Component of name " + name + " already exists.");
    }
    components.put(name, component);
    log.info("Registered component named {} of class {}", name,
        component.getClass().getSimpleName());
  }

}
