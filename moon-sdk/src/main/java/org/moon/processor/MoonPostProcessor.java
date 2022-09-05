package org.moon.processor;

import org.moon.component.MoonPropertySource;
import org.moon.config.MoonConfigProperties;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * BeanFactory后置处理器，执行时机在 PropertySourcesPlaceholderConfigurer 之后
 * 用于往 MutablePropertySources 中增加注册中心的配置，达到自定义配置的目的
 */
public class MoonPostProcessor implements BeanFactoryPostProcessor, InitializingBean, EnvironmentAware {

    private Environment environment;

    @Override
    public void afterPropertiesSet() throws Exception {
//        configMap.put("name", "毕露平");
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

        // TODO
        MoonPropertySource moonPropertySource = new MoonPropertySource(MoonConfigProperties.class.getName());

        String propertySourcesPlaceholderConfigurerBeanName = "propertySourcesPlaceholderConfigurer";
        PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = beanFactory.getBean(propertySourcesPlaceholderConfigurerBeanName, PropertySourcesPlaceholderConfigurer.class);
        MutablePropertySources propertySources = (MutablePropertySources)propertySourcesPlaceholderConfigurer.getAppliedPropertySources();
        propertySources.addFirst(moonPropertySource);
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
        checkEnvironment(environment);
    }

    /**
     * 校验配置文件中是否配置了使用 Moon 必需的配置,如下
     * moon.url=http://127.0.0.1:8080/moon
     */
    private void checkEnvironment(Environment environment) {
        // Moon 服务端地址，用于从服务端拉取配置
//        String moonUrl = "moon.url";
//        Assert.notNull(environment.getRequiredProperty(moonUrl), "未配置 " + moonUrl);
    }
}
