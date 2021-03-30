package io.wttech.markuply.engine.renderer.spring;

import io.wttech.markuply.engine.component.Markuply;
import io.wttech.markuply.engine.component.MarkuplyComponent;
import io.wttech.markuply.engine.component.method.LambdaComponentFactory;
import io.wttech.markuply.engine.renderer.registry.ComponentRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@RequiredArgsConstructor(staticName = "of")
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
        MarkuplyComponent component = methodComponentFactory.build(bean, method);
        registry.register(componentName, component);
      }
    }
    return bean;
  }

}
