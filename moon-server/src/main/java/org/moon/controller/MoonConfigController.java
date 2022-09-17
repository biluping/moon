package org.moon.controller;

import lombok.AllArgsConstructor;
import org.moon.entity.dto.MoonConfigDto;
import org.moon.entity.vo.MoonConfigVo;
import org.moon.service.MoonConfigService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/config")
public class MoonConfigController {

    private MoonConfigService moonConfigService;

    // 获取当前应用配置
    @GetMapping("/app/{appid}")
    public MoonConfigDto previewConfig(@PathVariable String appid){
        return moonConfigService.getAppConfig(appid);
    }

    // 获取moon配置
    @GetMapping("/moon/{appid}")
    public List<MoonConfigVo> getPublishConfig(@PathVariable String appid, Integer isPublish){
        return moonConfigService.getMoonConfig(appid, isPublish);
    }

    // 发布配置
    @PutMapping("/publish/{appid}/{key}")
    public void publish(@PathVariable String appid, @PathVariable String key){
        moonConfigService.publish(appid, key);
    }
}

