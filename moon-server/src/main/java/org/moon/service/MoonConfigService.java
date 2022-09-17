package org.moon.service;

import org.moon.entity.MoonConfigEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import org.moon.entity.ao.ConfigAo;
import org.moon.entity.dto.AppConfigDto;
import org.moon.entity.vo.MoonConfigVo;

import java.util.List;

public interface MoonConfigService extends IService<MoonConfigEntity> {

    void publish(String appid, String key);

    AppConfigDto getAppConfig(String appid);

    List<MoonConfigVo> getMoonConfig(String appid, Integer isPublish);

    void saveConfig(String appid, ConfigAo ao);
}
