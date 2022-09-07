package org.moon.component;

import org.moon.config.MoonConfigProperties;
import org.moon.http.MoonHttpRequest;
import org.springframework.core.env.PropertySource;

import java.util.HashMap;
import java.util.Map;

public class MoonPropertySource extends PropertySource<Map<String, String>> {

    public MoonPropertySource(String name) {
        super(name, new HashMap<>());
    }

    @Override
    public Object getProperty(String name) {
        return source.get(name);
    }

    /**
     * 从服务端获取Moon中手动添加的配置
     */
    public void initConfig(MoonConfigProperties moonConfigProperties) {
        String serverUrl = moonConfigProperties.getServerUrl();
        MoonHttpRequest.setServerUrl(serverUrl);

        Map<String, String> configMap = MoonHttpRequest.getCustomConfig();
        this.source.putAll(configMap);
    }
}
