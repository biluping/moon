package org.moon.config;

import org.moon.processor.MoonPostProcessor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(MoonConfigProperties.class)
public class MoonConfiguration {

    @Bean
    public MoonPostProcessor moonPostProcessor(MoonConfigProperties moonConfigProperties){
        return new MoonPostProcessor(moonConfigProperties);
    }

}
