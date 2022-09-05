package org.moon.component;

import org.springframework.core.env.PropertySource;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MoonPropertySource extends PropertySource<Map<String, String>> {

    public MoonPropertySource(String name) {
        super(name, new ConcurrentHashMap<>());
    }

    @Override
    public Object getProperty(String name) {
        return source.get(name);
    }
}
