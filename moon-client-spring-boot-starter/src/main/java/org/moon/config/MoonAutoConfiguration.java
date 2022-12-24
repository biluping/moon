package org.moon.config;

import lombok.extern.slf4j.Slf4j;
import org.moon.socket.SelectSocketServer;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Slf4j
@Configuration
public class MoonAutoConfiguration {

    /**
     * 从配置中心拉取配置
     */
    @Bean
    public ApplicationRunner applicationRunner(){
        return args -> {
            new Thread(()->{
                try {
                    new SelectSocketServer().listen();
                } catch (IOException e) {
                    log.error("nio 监听线程启动失败");
                }
            }).start();
        };
    }
}
