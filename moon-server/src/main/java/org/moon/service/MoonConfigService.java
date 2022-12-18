package org.moon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.moon.entity.MoonConfigEntity;
import org.moon.entity.ao.ConfigAo;
import org.moon.entity.vo.AppConfigVo;
import org.moon.entity.vo.MoonConfigVo;

import java.util.List;

public interface MoonConfigService extends IService<MoonConfigEntity> {

    void publish(String appid, List<String> keyList);

    AppConfigVo getAppConfig(String appid);

    List<MoonConfigVo> getMoonConfig(String appid, Integer isPublish);

    void saveConfig(String appid, ConfigAo ao);
}
