package org.moon;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = "org.moon.mapper")
@SpringBootApplication
public class MoonServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoonServerApplication.class, args);
    }
}
