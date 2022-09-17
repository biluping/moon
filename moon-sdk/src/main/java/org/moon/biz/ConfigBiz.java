package org.moon.biz;

import lombok.NonNull;
import org.moon.entity.dto.AppConfigDto;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ConfigBiz implements EnvironmentAware {

    private Environment environment;

    @Override
    public void setEnvironment(@NonNull Environment environment) {
        this.environment = environment;
    }

    private Map<String, Object> getPropertiesFileConfigMap(){
        // 获取配置文件中的配置
        MutablePropertySources propertySources = ((ConfigurableEnvironment)environment).getPropertySources();
        return StreamSupport.stream(propertySources.spliterator(), false)
                .filter(ps -> ps instanceof EnumerablePropertySource)
                .filter(ps -> ps.getName().startsWith("Config resource"))
                .map(ps -> ((EnumerablePropertySource<?>) ps).getPropertyNames())
                .flatMap(Arrays::stream)
                .distinct()
                .collect(Collectors.toMap(Function.identity(), key -> environment.getRequiredProperty(key)));
    }

    public AppConfigDto getAppConfig(){
        AppConfigDto dto = new AppConfigDto();
        dto.setSystemConfig(System.getProperties());
        dto.setSystemEnv(System.getenv());
        dto.setCustomConfig(getPropertiesFileConfigMap());
        return dto;
    }

}
