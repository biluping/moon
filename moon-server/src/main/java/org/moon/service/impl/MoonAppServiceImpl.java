package org.moon.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.moon.entity.MoonAppEntity;
import org.moon.entity.MoonNameSpaceEntity;
import org.moon.entity.ao.MoonAppAo;
import org.moon.entity.vo.MoonNameSpaceVo;
import org.moon.service.MoonAppService;
import org.moon.mapper.MoonAppMapper;
import org.moon.service.MoonNameSpaceService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
* @author biluping
* @description 针对表【moon_app】的数据库操作Service实现
* @createDate 2022-09-12 18:12:45
*/
@AllArgsConstructor
@Service
public class MoonAppServiceImpl extends ServiceImpl<MoonAppMapper, MoonAppEntity>
    implements MoonAppService{

    private MoonNameSpaceService nameSpaceService;

    @Override
    public void createOrUpdateApp(MoonAppAo ao) {
        MoonAppEntity app = getByAppId(ao.getAppid());
        if (app == null){
            app = new MoonAppEntity();
        }
        BeanUtils.copyProperties(ao, app);
        saveOrUpdate(app);

        // 如果第一次创建App，默认创建一个 nameSpace
        List<MoonNameSpaceVo> list = nameSpaceService.listByAppid(ao.getAppid());
        if (list.size() == 0){
            MoonNameSpaceEntity entity = new MoonNameSpaceEntity();
            entity.setAppid(ao.getAppid());
            entity.setName("application");
            nameSpaceService.save(entity);
        }

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




