package io.wttech.markuply.engine.configuration;

import org.springframework.context.annotation.Conditional;
import org.springframework.core.env.Environment;

import java.lang.annotation.*;

/**
 * {@link Conditional @Conditional} that checks if properties required by the {@link #targetClass()}
 * are present in the {@link Environment}.
 * <p>
 * {@link #targetClass()} must implement the {@link OptionalProperties} interface in order to define custom logic
 * checking if properties are in fact set.
 * <p>
 * Condition will not match if any of the following statements is true:
 * <ul>
 *   <li>{@link org.springframework.boot.context.properties.bind.Binder Binder} could not bind properties to the target type</li>
 *   <li>Target class instance was successfully bound but {@link OptionalProperties#isPresent()} returned false</li>
 * </ul>
 *
 * <pre class="code">
 * &#064;ConditionalOnConfigurationProperties(prefix = "spring", targetClass = ConfigurationPropertiesModel.class)
 * class ExampleAutoConfiguration {
 * }
 * </pre>
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(OnConfigurationPropertiesCondition.class)
public @interface ConditionalOnConfigurationProperties {

  String prefix();

  Class<? extends OptionalProperties> targetClass();

}
