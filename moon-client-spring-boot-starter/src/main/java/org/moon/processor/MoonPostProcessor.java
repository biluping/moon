package org.moon.processor;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.moon.component.MoonPropertySource;
import org.moon.factory.MoonConfigFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class MoonPostProcessor implements EnvironmentPostProcessor, InstantiationAwareBeanPostProcessor {


    /**
     * springboot 启动时环境准备好后，注入配置中心的配置
     */
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        // 初始化moon配置源
        MoonPropertySource moonPropertySource = new MoonPropertySource("moon", new ConcurrentHashMap<>());
        // 从服务器获取配置
//        moonPropertySource.init(environment);
        environment.getPropertySources().addFirst(moonPropertySource);

        environment.getPropertySources().stream().filter(s -> s instanceof OriginTrackedMapPropertySource || s instanceof MoonPropertySource)
                .map(s -> ((MapPropertySource) s))
                .map(s -> s.getSource().entrySet())
                .flatMap(Collection::stream)
                .forEach(e -> {
                    // 最前面的配置优先级更高
                    if (!MoonConfigFactory.getConfig().containsKey(e.getKey())){
                        MoonConfigFactory.setConfig(e.getKey(), e.getValue().toString());
                    }
                });
    }


    /**
     * 在bean对象填充属性的时候，获取到标注了@Value的对象属性，存到配置工厂中，方便后续通过反射更新值
     */
    @Override
    public PropertyValues postProcessProperties(@NotNull PropertyValues pvs, @NotNull Object bean, @NotNull String beanName) throws BeansException {
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Value.class)) {
                try {
                    Value annotation = field.getAnnotation(Value.class);
                    String placeHolder = annotation.value();
                    if (placeHolder.startsWith("${")){
                        placeHolder = placeHolder.substring(2, placeHolder.length() - 1);
                    }
                    MoonConfigFactory.addConfigBean(placeHolder, bean, field);
                } catch (Exception e) {
                    log.error("moon 获取解析保存 @Value 属性时发生错误", e);
                }
            }
        }

        return pvs;
    }

}
