package org.moon.service.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.moon.entity.MoonConfigEntity;
import org.moon.entity.ao.ConfigAo;
import org.moon.entity.vo.MoonConfigVo;
import org.moon.entity.vo.NameSpaceVo;
import org.moon.enums.MoonConfigPublishEnum;
import org.moon.mapper.MoonConfigMapper;
import org.moon.service.MoonAppService;
import org.moon.service.MoonConfigService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class MoonConfigServiceImpl extends ServiceImpl<MoonConfigMapper, MoonConfigEntity>
    implements MoonConfigService{

    private MoonAppService appService;

    @Override
    public Map<String, String> getMoonConfig(String appid, Integer isPublish) {
        LambdaQueryWrapper<MoonConfigEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MoonConfigEntity::getAppid, appid);
        wrapper.eq(isPublish!=null,MoonConfigEntity::getIsPublish, isPublish!=null&&isPublish==MoonConfigPublishEnum.PUBLISH.getCode());
        return list(wrapper).stream().collect(Collectors.toMap(MoonConfigEntity::getKey, MoonConfigEntity::getValue));
    }

    @Override
    public void saveConfig(String appid, ConfigAo ao) {
        MoonConfigEntity configEntity = getByKey(appid, ao.getKey());
        if (configEntity == null){
            configEntity = new MoonConfigEntity();
            configEntity.setAppid(appid);
        }
        configEntity.setIsPublish(false);
        BeanUtils.copyProperties(ao, configEntity);
        saveOrUpdate(configEntity);
    }

    @Override
    public void publish(String appid, List<String> keyList) {
        LambdaUpdateWrapper<MoonConfigEntity> wrapper = new LambdaUpdateWrapper<>();
        wrapper.in(MoonConfigEntity::getKey, keyList);
        wrapper.set(MoonConfigEntity::getIsPublish, true);
        update(wrapper);
    }

    @Override
    public List<NameSpaceVo> getAppConfig(String appid) {
        LambdaQueryWrapper<MoonConfigEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MoonConfigEntity::getAppid, appid);
        List<MoonConfigVo> list = Convert.toList(MoonConfigVo.class, list(wrapper));
        return List.of(new NameSpaceVo("application", list));

    }

    public MoonConfigEntity getByKey(String appid, String key){
        return lambdaQuery().eq(MoonConfigEntity::getAppid, appid).eq(MoonConfigEntity::getKey, key).one();
    }
}




