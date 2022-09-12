package org.moon.service;

import org.moon.entity.MoonConfigEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

public interface MoonConfigService extends IService<MoonConfigEntity> {

    Map<String, Object> getPublishConfig(String appid);

    void publish(String appid, String key);
}
