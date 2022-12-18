package org.moon.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.HttpStatus;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
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
import org.moon.entity.vo.AppConfigVo;
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
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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
        wrapper.eq(isPublish!=null,MoonConfigEntity::getIsPublish, isPublish!=null&&isPublish==MoonConfigPublishEnum.PUBLISH.getCode());
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
    public AppConfigVo getAppConfig(String appid) {
        MoonAppEntity appEntity = appService.getByAppId(appid);
        String json = HttpUtil.get(appEntity.getAppUrl() + "/moon/getAppConfig", 5000);
        BaseVo<AppConfigDto> vo = JSON.parseObject(json, new TypeReference<BaseVo<AppConfigDto>>(){});
        if (vo.getCode() == HttpStatus.HTTP_OK){
            List<MoonConfigEntity> allConfigList = getAllConfig(appid);
            Map<String, MoonConfigEntity> configMap = allConfigList.stream().collect(Collectors.toMap(MoonConfigEntity::getKey, Function.identity()));
            Map<String, MoonConfigVo> customConfigMap = vo.getData().getCustomConfig().entrySet().stream().map(e -> {
                MoonConfigEntity moonConfigEntity = configMap.get(e.getKey());
                MoonConfigVo moonConfigVo = new MoonConfigVo();
                moonConfigVo.setKey(e.getKey());
                moonConfigVo.setValue(moonConfigEntity!=null&&moonConfigEntity.getIsPublish()?moonConfigEntity.getValue():e.getValue().toString());
                moonConfigVo.setAppid(appid);
                moonConfigVo.setIsPublish(true);
                moonConfigVo.setUpdateTime(moonConfigEntity == null ? "-" : moonConfigEntity.getUpdateTime().toString());
                return moonConfigVo;
            }).collect(Collectors.toMap(MoonConfigVo::getKey, Function.identity()));
            allConfigList.stream().filter(c-> !c.getIsPublish()).forEach(c->{
                MoonConfigVo moonConfigVo = new MoonConfigVo();
                moonConfigVo.setKey(c.getKey());
                moonConfigVo.setValue(c.getValue());
                moonConfigVo.setAppid(appid);
                moonConfigVo.setIsPublish(false);
                moonConfigVo.setUpdateTime(c.getUpdateTime().toString());
                customConfigMap.put(c.getKey(), moonConfigVo);
            });
            Map<String, MoonConfigVo> systemConfigMap = vo.getData().getSystemConfig().entrySet().stream().map(e -> {
                MoonConfigVo moonConfigVo = new MoonConfigVo();
                moonConfigVo.setKey(e.getKey().toString());
                moonConfigVo.setValue(e.getValue().toString());
                moonConfigVo.setAppid(appid);
                moonConfigVo.setIsPublish(true);
                moonConfigVo.setUpdateTime("-");
                return moonConfigVo;
            }).collect(Collectors.toMap(MoonConfigVo::getKey, Function.identity()));
            Map<String, MoonConfigVo> systemEnvConfigMap = vo.getData().getSystemEnv().entrySet().stream().map(e -> {
                MoonConfigVo moonConfigVo = new MoonConfigVo();
                moonConfigVo.setKey(e.getKey());
                moonConfigVo.setValue(e.getValue());
                moonConfigVo.setAppid(appid);
                moonConfigVo.setIsPublish(true);
                moonConfigVo.setUpdateTime("-");
                return moonConfigVo;
            }).collect(Collectors.toMap(MoonConfigVo::getKey, Function.identity()));
            return new AppConfigVo(customConfigMap, systemConfigMap, systemEnvConfigMap);
        } else {
            throw new MoonBadRequestException(JSONObject.toJSONString(vo));
        }
    }

    public MoonConfigEntity getByKey(String appid, String key){
        return query().eq("appid", appid).eq("key", key).one();
    }

    public List<MoonConfigEntity> getAllConfig(String appid){
        // 拼上未发布的配置
        LambdaQueryWrapper<MoonConfigEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MoonConfigEntity::getAppid, appid);
        return list(wrapper);
    }
}




