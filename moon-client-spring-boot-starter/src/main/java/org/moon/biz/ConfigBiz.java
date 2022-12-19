package org.moon.biz;

import lombok.AllArgsConstructor;
import org.moon.constant.MoonConstant;
import org.moon.entity.dto.AppConfigDto;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.*;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class ConfigBiz {

    private Environment environment;

    private PropertySourcesPlaceholderConfigurer placeholderConfigurer;

    private Map<String, Object> getPropertiesFileConfigMap(){
        // 获取配置文件中的配置
        PropertySources appliedPropertySources = placeholderConfigurer.getAppliedPropertySources();

        @SuppressWarnings("all")
        Map<String, Object> moonConfigMap = (Map<String, Object>) appliedPropertySources.get(MoonConstant.MOON_PROPERTY_SOURCE).getSource();

        MutablePropertySources propertySources = ((ConfigurableEnvironment)environment).getPropertySources();
        Map<String, Object> map = StreamSupport.stream(propertySources.spliterator(), false)
                .filter(ps -> ps instanceof EnumerablePropertySource)
                .filter(ps -> ps.getName().startsWith("Config resource"))
                .map(ps -> ((EnumerablePropertySource<?>) ps).getPropertyNames())
                .flatMap(Arrays::stream)
                .distinct()
                .collect(Collectors.toMap(Function.identity(), key -> environment.getRequiredProperty(key)));
        map.putAll(moonConfigMap);
        return map;

    }

    public AppConfigDto getAppConfig(){
        AppConfigDto dto = new AppConfigDto();
        dto.setSystemConfig(System.getProperties());
        dto.setSystemEnv(System.getenv());
        dto.setCustomConfig(getPropertiesFileConfigMap());
        return dto;
    }

}
