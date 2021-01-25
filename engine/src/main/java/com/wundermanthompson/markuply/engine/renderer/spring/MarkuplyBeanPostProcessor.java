package com.wundermanthompson.markuply.engine.renderer.spring;

import com.wundermanthompson.markuply.engine.component.Markuply;
import com.wundermanthompson.markuply.engine.component.method.LambdaComponentFactory;
import com.wundermanthompson.markuply.engine.component.method.MethodComponent;
import com.wundermanthompson.markuply.engine.renderer.registry.ComponentRegistry;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@AllArgsConstructor
public class MarkuplyBeanPostProcessor implements BeanPostProcessor {

  private final LambdaComponentFactory methodComponentFactory;
  private final ComponentRegistry registry;

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    Class<?> beanClass = bean.getClass();
    Method[] methods = beanClass.getDeclaredMethods();
    for (Method method : methods) {
      if (method.isAnnotationPresent(Markuply.class)) {
        Markuply markuplyAnnotation = method.getAnnotation(Markuply.class);
        String componentName = markuplyAnnotation.value().isEmpty() ? method.getName() : markuplyAnnotation.value();
        MethodComponent component = methodComponentFactory.build(bean, method);
        registry.register(componentName, component);
      }
    }
    return bean;
  }

}
