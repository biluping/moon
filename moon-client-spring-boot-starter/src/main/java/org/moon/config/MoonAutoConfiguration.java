package org.moon.config;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.moon.entity.base.BaseVo;
import org.moon.factory.MoonConfigFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
public class MoonAutoConfiguration {

    /**
     * 从配置中心拉取配置
     */
    @Bean
    public ApplicationRunner applicationRunner(){
        return args -> {
            Thread thread = new Thread(() -> {
                try{
                    while (true) {
                        String data = HttpUtil.get(MoonConfigFactory.getUrl());
                        BaseVo<Map<String, String>> vo = JSON.parseObject(data, new TypeReference<BaseVo<Map<String, String>>>(){});
                        vo.getData().entrySet().stream()
                                .filter(e -> !MoonConfigFactory.getConfig().containsKey(e.getKey())||!MoonConfigFactory.getConfig(e.getKey()).equals(e.getValue()))
                                .forEach(e -> {
                                    // TODO 可以搞个字符串表格展示，很酷
                                    log.info("检测到配置更新\nkey:{}\noldVal:{}\nnewVal:{}\n", e.getKey(), MoonConfigFactory.getConfig(e.getKey()), e.getValue());
                                    MoonConfigFactory.setConfig(e.getKey(), e.getValue());
                                });
                        TimeUnit.SECONDS.sleep(MoonConfigFactory.getConfigInt("moon.fetch.timeout", "5"));
                    }
                } catch (Exception e){
                    log.error("获取配置错误", e);
                }
            });

            thread.setDaemon(true);
            thread.start();
        };
    }
}
