package org.moon.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.moon.Constant;
import org.moon.entity.MoonConfigEntity;
import org.moon.service.MoonConfigService;
import org.moon.mapper.MoonConfigMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MoonConfigServiceImpl extends ServiceImpl<MoonConfigMapper, MoonConfigEntity>
    implements MoonConfigService{

    @Override
    public Map<String, Object> getPublishConfig(String appid) {
        List<MoonConfigEntity> configList = lambdaQuery().eq(MoonConfigEntity::getAppid, appid)
                .eq(MoonConfigEntity::getIsPublish, Constant.TRUE)
                .list();
        Map<String, Object> map = new HashMap<>();
        configList.forEach(config -> {
            map.put(config.getKey(), config.getValue());
        });
        return map;
    }

    @Override
    public void publish(String appid, String key) {
        lambdaUpdate().eq(MoonConfigEntity::getAppid, appid)
                .eq(MoonConfigEntity::getKey, key)
                .set(MoonConfigEntity::getIsPublish, true)
                .update();
    }
}




