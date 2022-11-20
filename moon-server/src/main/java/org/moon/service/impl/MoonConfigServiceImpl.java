package org.moon.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.moon.constant.MoonConstant;
import org.moon.entity.MoonAppEntity;
import org.moon.entity.MoonConfigEntity;
import org.moon.entity.ao.ConfigAo;
import org.moon.entity.dto.AppConfigDto;
import org.moon.entity.vo.BaseVo;
import org.moon.entity.vo.MoonConfigVo;
import org.moon.exception.MoonBadRequestException;
import org.moon.service.MoonAppService;
import org.moon.service.MoonConfigService;
import org.moon.mapper.MoonConfigMapper;
import org.moon.utils.HttpUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class MoonConfigServiceImpl extends ServiceImpl<MoonConfigMapper, MoonConfigEntity>
    implements MoonConfigService{

    private MoonAppService appService;

    @Override
    public List<MoonConfigVo> getMoonConfig(String appid, Integer isPublish) {
        List<MoonConfigEntity> list = query().eq("appid", appid)
                .eq(isPublish != null, "is_publish", isPublish!=null&&isPublish==1)
                .list();
        return list.stream().map(item -> {
            MoonConfigVo vo = new MoonConfigVo();
            BeanUtils.copyProperties(item, vo);
            return vo;
        }).collect(Collectors.toList());
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
        lambdaUpdate().eq(MoonConfigEntity::getAppid, appid)
                .eq(MoonConfigEntity::getKey, key)
                .set(MoonConfigEntity::getIsPublish, true)
                .update();
    }

    @Override
    public AppConfigDto getAppConfig(String appid) {
        MoonAppEntity app = appService.getById(appid);
        try{
            BaseVo<AppConfigDto> vo = HttpUtils.doGet(app.getAppUrl()+"/moon/getAppConfig", new TypeReference<>(){});
            if (vo.getCode() == MoonConstant.SUCCESS){
                return vo.getData();
            } else {
                throw new MoonBadRequestException(JSONObject.toJSONString(vo));
            }
        }catch (IOException e){
            log.error("获取应用配置失败, appid:{}", appid, e);
            throw new MoonBadRequestException("获取应用配置失败");
        }
    }

    public MoonConfigEntity getByKey(String appid, String key){
        return query().eq("appid", appid).eq("key", key).one();
    }
}




