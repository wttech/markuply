package io.wttech.markuply.engine.configuration;

/**
 * Allows defining custom logic to check if configuration model class is in fact filled with data.
 */
public interface OptionalProperties {

  boolean isPresent();

}
