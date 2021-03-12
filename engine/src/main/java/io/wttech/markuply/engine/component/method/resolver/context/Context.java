package io.wttech.markuply.engine.component.method.resolver.context;

import io.wttech.markuply.engine.pipeline.context.PageContext;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Markuply component method argument marked with this annotation will contain an element
 * from {@link PageContext} identified by the type of the argument.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Context {

}
