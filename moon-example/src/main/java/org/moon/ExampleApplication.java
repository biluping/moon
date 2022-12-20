package org.moon;

import org.moon.factory.MoonConfigFactory;
import org.moon.processor.MoonPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ExampleApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ExampleApplication.class, args);
        System.out.println(context.getBean(MoonPostProcessor.class));
        System.out.println(MoonConfigFactory.moonConfigBeanMap);
    }
}
