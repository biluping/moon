package org.moon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.moon.entity.MoonConfigEntity;
import org.moon.entity.ao.ConfigAo;
import org.moon.entity.vo.NameSpaceVo;

import java.util.List;
import java.util.Map;

public interface MoonConfigService extends IService<MoonConfigEntity> {

    void publish(String appid, List<String> keyList);

    List<NameSpaceVo> getAppConfig(String appid);

    Map<String, String> getMoonConfig(String appid, Integer isPublish);

    void saveConfig(String appid, ConfigAo ao);
}
