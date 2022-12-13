package org.moon.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.HttpStatus;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.moon.entity.MoonAppEntity;
import org.moon.entity.MoonConfigEntity;
import org.moon.entity.ao.ConfigAo;
import org.moon.entity.dto.AppConfigDto;
import org.moon.entity.vo.BaseVo;
import org.moon.entity.vo.MoonConfigVo;
import org.moon.enums.MoonConfigPublishEnum;
import org.moon.exception.MoonBadRequestException;
import org.moon.mapper.MoonConfigMapper;
import org.moon.service.MoonAppService;
import org.moon.service.MoonConfigService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class MoonConfigServiceImpl extends ServiceImpl<MoonConfigMapper, MoonConfigEntity>
    implements MoonConfigService{

    private MoonAppService appService;

    @Override
    public List<MoonConfigVo> getMoonConfig(String appid, Integer isPublish) {
        LambdaQueryWrapper<MoonConfigEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MoonConfigEntity::getAppid, appid);
        wrapper.eq(isPublish!=null,MoonConfigEntity::getIsPublish, MoonConfigPublishEnum.PUBLISH.getCode());
        List<MoonConfigEntity> list = list(wrapper);
        return BeanUtil.copyToList(list, MoonConfigVo.class);
    }

    @Override
    public void saveConfig(String appid, ConfigAo ao) {
        MoonConfigEntity configEntity = getByKey(appid, ao.getKey());
        if (configEntity == null){
            configEntity = new MoonConfigEntity();
            configEntity.setAppid(appid);
        }
        BeanUtils.copyProperties(ao, configEntity);
        saveOrUpdate(configEntity);
    }

    @Override
    public void publish(String appid, String key) {
        LambdaUpdateWrapper<MoonConfigEntity> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(MoonConfigEntity::getKey, key);
        wrapper.set(MoonConfigEntity::getIsPublish, true);
        update(wrapper);
    }

    @Override
    public AppConfigDto getAppConfig(String appid) {
        MoonAppEntity app = appService.getByAppId(appid);
        String json = HttpUtil.get(app.getAppUrl() + "/moon/getAppConfig", 5000);
        BaseVo<AppConfigDto> vo = JSONObject.parseObject(json, new TypeReference<>(){});
        if (vo.getCode() == HttpStatus.HTTP_OK){
            return vo.getData();
        } else {
            throw new MoonBadRequestException(JSONObject.toJSONString(vo));
        }
    }

    public MoonConfigEntity getByKey(String appid, String key){
        return query().eq("appid", appid).eq("key", key).one();
    }
}




