package org.moon.component;

import org.moon.http.MoonHttpRequest;
import org.springframework.core.env.PropertySource;
import org.springframework.lang.NonNull;
import java.util.HashMap;
import java.util.Map;

public class MoonPropertySource extends PropertySource<Map<String, Object>> {

    public MoonPropertySource(String name) {
        super(name, new HashMap<>());
    }

    @Override
    public Object getProperty(@NonNull String name) {
        return source.get(name);
    }

    /**
     * 从服务端获取Moon中手动添加的配置
     */
    public void initConfig(String serverUrl, String appid) {
        MoonHttpRequest.setServerUrl(serverUrl, appid);
        Map<String, Object> configMap = MoonHttpRequest.getPublishConfig();
        this.source.putAll(configMap);
    }
}
