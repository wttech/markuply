package com.wundermanthompson.markuply.engine.component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * Annotate a templating method to define the component ID.
 * <p>
 * Works out of the box for any Spring bean.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Markuply {

  String value() default "";

}
