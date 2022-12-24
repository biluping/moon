package org.moon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.moon.entity.MoonConfigEntity;
import org.moon.entity.ao.ConfigAo;
import org.moon.entity.vo.MoonConfigVo;

import java.util.List;
import java.util.Map;

public interface MoonConfigService extends IService<MoonConfigEntity> {

    void publish(Long nameSpaceId, List<String> keyList);

    List<MoonConfigVo> getAppConfig(Long nameSpaceId);

    Map<String, String> getMoonConfig(String appid, Integer isPublish);

    void saveConfig(Long nameSpaceId, ConfigAo ao);
}
