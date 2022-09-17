package org.moon.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.moon.entity.MoonAppEntity;
import org.moon.entity.ao.MoonAppAo;
import org.moon.exception.MoonBadRequestException;
import org.moon.service.MoonAppService;
import org.moon.mapper.MoonAppMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
* @author biluping
* @description 针对表【moon_app】的数据库操作Service实现
* @createDate 2022-09-12 18:12:45
*/
@Service
public class MoonAppServiceImpl extends ServiceImpl<MoonAppMapper, MoonAppEntity>
    implements MoonAppService{

    @Override
    public void createApp(MoonAppAo ao) {
        MoonAppEntity app = getById(ao.getAppid());
        if (app != null){
            throw new MoonBadRequestException(String.format("appid %s 已经存在", ao.getAppid()));
        }
        MoonAppEntity entity = new MoonAppEntity();
        BeanUtils.copyProperties(ao, entity);
        save(entity);
    }

    public MoonAppEntity getById(String appid){
        return query().eq("appid", appid).one();
    }
}




