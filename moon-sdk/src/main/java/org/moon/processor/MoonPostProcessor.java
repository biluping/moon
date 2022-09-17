package org.moon.processor;

import org.moon.component.MoonPropertySource;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.lang.NonNull;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * BeanFactory后置处理器，执行时机在 PropertySourcesPlaceholderConfigurer 之后
 * 用于往 MutablePropertySources 中增加注册中心的配置，达到自定义配置的目的
 */
public class MoonPostProcessor implements BeanFactoryPostProcessor, EnvironmentAware {

    private Environment environment;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

        // 初始化Moon配置
        MoonPropertySource moonPropertySource = new MoonPropertySource("moonPropertySource");
        moonPropertySource.initConfig(environment.getRequiredProperty("moon.server-url"), environment.getRequiredProperty("moon.appid"));

        // 将Moon配置放入首位，以使配置生效
        String propertySourcesPlaceholderConfigurerBeanName = "propertySourcesPlaceholderConfigurer";
        PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = beanFactory.getBean(propertySourcesPlaceholderConfigurerBeanName, PropertySourcesPlaceholderConfigurer.class);
        MutablePropertySources mutablePropertySources = (MutablePropertySources)propertySourcesPlaceholderConfigurer.getAppliedPropertySources();
        mutablePropertySources.addFirst(moonPropertySource);
    }

    @Override
    public void setEnvironment(@NonNull Environment environment) {
        this.environment = environment;
    }
}
