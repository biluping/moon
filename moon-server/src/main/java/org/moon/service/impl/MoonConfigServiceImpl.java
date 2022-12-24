package org.moon.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.moon.entity.MoonConfigEntity;
import org.moon.entity.MoonNameSpaceEntity;
import org.moon.entity.ao.ConfigAo;
import org.moon.entity.vo.MoonConfigVo;
import org.moon.enums.MoonConfigPublishEnum;
import org.moon.exception.MoonBadRequestException;
import org.moon.mapper.MoonConfigMapper;
import org.moon.service.MoonAppService;
import org.moon.service.MoonConfigService;
import org.moon.service.MoonNameSpaceService;
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

    private MoonNameSpaceService nameSpaceService;
    private MoonAppService appService;

    @Override
    public Map<String, String> getMoonConfig(String appid, Integer isPublish) {
        LambdaQueryWrapper<MoonConfigEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MoonConfigEntity::getAppid, appid);
        wrapper.eq(isPublish!=null,MoonConfigEntity::getIsPublish, isPublish!=null&&isPublish==MoonConfigPublishEnum.PUBLISH.getCode());
        return list(wrapper).stream().collect(Collectors.toMap(MoonConfigEntity::getKey, MoonConfigEntity::getValue));
    }

    @Override
    public void saveConfig(Long nameSpaceId, ConfigAo ao) {
        MoonNameSpaceEntity nameSpaceEntity = nameSpaceService.getById(nameSpaceId);
        if (nameSpaceEntity == null){
            throw new MoonBadRequestException(StrUtil.format("nameSpaceId:{} 不存在", nameSpaceId));
        }
        MoonConfigEntity configEntity = getByKey(nameSpaceId, ao.getKey());
        if (configEntity == null){
            configEntity = new MoonConfigEntity();
            configEntity.setAppid(nameSpaceEntity.getAppid());
            configEntity.setNameSpaceId(nameSpaceId);
        }
        configEntity.setIsPublish(false);
        BeanUtils.copyProperties(ao, configEntity);
        saveOrUpdate(configEntity);
    }

    @Override
    public void publish(Long nameSpaceId, List<String> keyList) {
        LambdaUpdateWrapper<MoonConfigEntity> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(MoonConfigEntity::getNameSpaceId, nameSpaceId);
        wrapper.in(MoonConfigEntity::getKey, keyList);
        wrapper.set(MoonConfigEntity::getIsPublish, true);
        update(wrapper);
    }

    @Override
    public List<MoonConfigVo> getAppConfig(Long nameSpaceId) {
        LambdaQueryWrapper<MoonConfigEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MoonConfigEntity::getNameSpaceId, nameSpaceId);
        wrapper.orderByAsc(MoonConfigEntity::getId);
        List<MoonConfigEntity> list = list(wrapper);
        return Convert.toList(MoonConfigVo.class, list);
    }

    public MoonConfigEntity getByKey(Long nameSpaceId, String key){
        return lambdaQuery().eq(MoonConfigEntity::getNameSpaceId, nameSpaceId).eq(MoonConfigEntity::getKey, key).one();
    }
}




