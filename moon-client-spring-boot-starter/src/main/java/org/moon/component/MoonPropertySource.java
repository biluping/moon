package org.moon.component;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.moon.entity.base.BaseVo;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.Map;

/**
 * moon 配置源，说白了就是存放配置中心配置的地方
 */
@Slf4j
public class MoonPropertySource extends MapPropertySource {

    public MoonPropertySource(String name, Map<String, Object> source) {
        super(name, source);
    }

    public void init(ConfigurableEnvironment environment) {
        String appid = environment.getRequiredProperty("moon.appid");
        String protocol = environment.getProperty("moon.protocol", "http");
        String host = environment.getRequiredProperty("moon.host");
        String port = environment.getProperty("moon.port", "10305");
        String url = String.format("%s://%s:%s/config/moon/%s?isPublish=1", protocol, host, port, appid);
        log.info("配置中心地址:{}", url);
        String data = HttpUtil.get(url);
        BaseVo<Map<String, String>> vo = JSON.parseObject(data, new TypeReference<BaseVo<Map<String, String>>>(){});
        vo.getData().forEach((k,v)->{
            getSource().put(k, v);
        });
        log.info("同步配置成功");
    }
}
