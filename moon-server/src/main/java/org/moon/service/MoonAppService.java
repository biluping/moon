package org.moon.service;

import org.moon.entity.MoonAppEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import org.moon.entity.ao.MoonAppAo;

import java.io.Serializable;

/**
* @author biluping
* @description 针对表【moon_app】的数据库操作Service
* @createDate 2022-09-12 18:12:45
*/
public interface MoonAppService extends IService<MoonAppEntity> {

    // 创建app
    void createOrUpdateApp(MoonAppAo ao);

    // 删除app
    void removeByAppid(String appid);

    MoonAppEntity getByAppId(Serializable appid);
}
