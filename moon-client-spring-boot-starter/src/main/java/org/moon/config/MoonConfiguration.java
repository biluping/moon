package org.moon.config;

import org.moon.processor.MoonPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MoonConfiguration {

    @Bean
    public static MoonPostProcessor moonPostProcessor(){
        return new MoonPostProcessor();
    }
}
