package org.moon.annotation;

import org.moon.config.MoonConfiguration;
import org.moon.processor.MoonPostProcessor;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 添加注解以使 Moon 生效
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(MoonConfiguration.class)
public @interface EnableMoon {
}
