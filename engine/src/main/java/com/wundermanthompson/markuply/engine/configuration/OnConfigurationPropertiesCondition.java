package com.wundermanthompson.markuply.engine.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * {@link Condition} that checks if properties are defined in environment.
 *
 * @see ConditionalOnConfigurationProperties
 */
@Slf4j
public class OnConfigurationPropertiesCondition extends SpringBootCondition {

  @Override
  public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
    MergedAnnotation<ConditionalOnConfigurationProperties> mergedAnnotation = metadata.getAnnotations().get(ConditionalOnConfigurationProperties.class);
    String prefix = mergedAnnotation.getString("prefix");
    Class<?> targetClass = mergedAnnotation.getClass("targetClass");
    if (!OptionalProperties.class.isAssignableFrom(targetClass)) {
      return ConditionOutcome.noMatch("Target type does not implement the OptionalProperties interface.");
    }
    Object bean = Binder.get(context.getEnvironment()).bind(prefix, targetClass).orElse(null);
    if (bean == null) {
      return ConditionOutcome.noMatch("Binding properties to target type resulted in null value.");
    }
    OptionalProperties props = (OptionalProperties) bean;
    if (props.isPresent()) {
      return ConditionOutcome.match();
    } else {
      return ConditionOutcome.noMatch("Properties are not present.");
    }
  }

}
