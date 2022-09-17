package org.moon.service;

import org.moon.entity.MoonConfigEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import org.moon.entity.dto.MoonConfigDto;
import org.moon.entity.vo.MoonConfigVo;

import java.util.List;
import java.util.Map;

public interface MoonConfigService extends IService<MoonConfigEntity> {

    void publish(String appid, String key);

    MoonConfigDto getAppConfig(String appid);

    List<MoonConfigVo> getMoonConfig(String appid, Integer isPublish);
}
