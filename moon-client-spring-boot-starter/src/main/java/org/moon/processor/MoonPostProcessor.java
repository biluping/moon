package org.moon.processor;

import com.alibaba.fastjson.JSON;
import org.moon.component.MoonPropertySource;
import org.moon.factory.MoonConfigFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class MoonPostProcessor implements EnvironmentPostProcessor {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        // 初始化moon配置源
        MoonPropertySource moonPropertySource = new MoonPropertySource("moon", new ConcurrentHashMap<>());
        moonPropertySource.init(environment);
        environment.getPropertySources().addFirst(moonPropertySource);

        environment.getPropertySources().stream().filter(s -> s instanceof OriginTrackedMapPropertySource || s instanceof MoonPropertySource)
                .map(s -> ((MapPropertySource) s))
                .map(s -> s.getSource().entrySet())
                .flatMap(Collection::stream)
                .forEach(e -> {
                    MoonConfigFactory.setConfig(e.getKey(), e.getValue().toString());
                });
        System.out.println(JSON.toJSONString(MoonConfigFactory.getConfig()));
    }
}
