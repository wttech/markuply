package io.wttech.markuply.engine.component.method.resolver.properties;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Markuply component method argument marked with this annotation will contain properties defined in data-props attribute.
 * If annotated argument is of type String then data-props attribute value is passed as is without any transformation.
 * If annotated argument is of any other type the data-props value will be treated as JSON string and will be mapped to the target type.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Props {

}
