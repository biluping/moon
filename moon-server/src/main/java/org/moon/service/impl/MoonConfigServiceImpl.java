package org.moon.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.moon.Constant;
import org.moon.entity.MoonAppEntity;
import org.moon.entity.MoonConfigEntity;
import org.moon.entity.dto.MoonConfigDto;
import org.moon.entity.vo.MoonConfigVo;
import org.moon.exception.MoonBadRequestException;
import org.moon.service.MoonAppService;
import org.moon.service.MoonConfigService;
import org.moon.mapper.MoonConfigMapper;
import org.moon.utils.HttpUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
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
    public List<MoonConfigVo> getMoonConfig(String appid, Integer isPublish) {
        List<MoonConfigEntity> list = query().eq("appid", appid)
                .eq(isPublish != null, "is_publish", isPublish)
                .list();
        return list.stream().map(item -> {
            MoonConfigVo vo = new MoonConfigVo();
            BeanUtils.copyProperties(item, vo);
            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    public void publish(String appid, String key) {
        lambdaUpdate().eq(MoonConfigEntity::getAppid, appid)
                .eq(MoonConfigEntity::getKey, key)
                .set(MoonConfigEntity::getIsPublish, true)
                .update();
    }

    @Override
    public MoonConfigDto getAppConfig(String appid) {
        MoonAppEntity app = appService.getById(appid);
        try{
            return HttpUtils.doGet(app.getAppUrl(), MoonConfigDto.class);
        }catch (IOException e){
            log.error("获取应用配置失败, appid:{}", appid);
            throw new MoonBadRequestException("获取应用配置失败");
        }
    }
}




