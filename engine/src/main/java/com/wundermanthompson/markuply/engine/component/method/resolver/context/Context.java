package com.wundermanthompson.markuply.engine.component.method.resolver.context;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Markuply component method argument marked with this annotation will contain an element
 * from {@link com.wundermanthompson.markuply.engine.pipeline.context.PageContext} identified by the type of the argument.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Context {

}
