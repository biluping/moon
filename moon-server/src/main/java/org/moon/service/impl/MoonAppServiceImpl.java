package org.moon.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.moon.entity.MoonAppEntity;
import org.moon.entity.ao.MoonAppAo;
import org.moon.service.MoonAppService;
import org.moon.mapper.MoonAppMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
* @author biluping
* @description 针对表【moon_app】的数据库操作Service实现
* @createDate 2022-09-12 18:12:45
*/
@Service
public class MoonAppServiceImpl extends ServiceImpl<MoonAppMapper, MoonAppEntity>
    implements MoonAppService{

    @Override
    public void createOrUpdateApp(MoonAppAo ao) {
        MoonAppEntity app = getByAppId(ao.getAppid());
        if (app == null){
            app = new MoonAppEntity();
        }
        BeanUtils.copyProperties(ao, app);
        saveOrUpdate(app);
    }

    @Override
    public void removeByAppid(String appid) {
        lambdaUpdate().eq(MoonAppEntity::getAppid, appid).remove();
    }

    @Override
    public MoonAppEntity getByAppId(Serializable appid){
        return query().eq("appid", appid).one();
    }
}




