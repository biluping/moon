package org.moon.component;

import cn.hutool.http.HttpUtil;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.Map;

/**
 * moon 配置源，说白了就是存放配置中心配置的地方
 */
public class MoonPropertySource extends MapPropertySource {

    public MoonPropertySource(String name, Map<String, Object> source) {
        super(name, source);
    }

    public void init(ConfigurableEnvironment environment) {
        String appid = environment.getRequiredProperty("moon.appid");
        String protocol = environment.getProperty("moon.protocol", "http");
        String host = environment.getRequiredProperty("moon.host");
        String port = environment.getProperty("moon.port", "10305");
        String data = HttpUtil.get(String.format("%s://%s:%s/%s", protocol, host, port, appid));
    }
}
