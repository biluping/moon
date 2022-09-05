package org.moon.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(
        prefix = "moon"
)
public class MoonConfigProperties {

    private String url = "http://localhost:10305";
}
