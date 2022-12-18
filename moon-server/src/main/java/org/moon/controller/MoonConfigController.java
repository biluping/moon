package org.moon.controller;

import lombok.AllArgsConstructor;
import org.moon.entity.ao.ConfigAo;
import org.moon.entity.vo.AppConfigVo;
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
    public AppConfigVo getPreviewConfig(@PathVariable String appid){
        return moonConfigService.getAppConfig(appid);
    }

    // 获取moon配置,存储在数据库中的配置
    @GetMapping("/moon/{appid}")
    public List<MoonConfigVo> getMoonConfig(@PathVariable String appid, Integer isPublish){
        return moonConfigService.getMoonConfig(appid, isPublish);
    }

    // 增加配置
    @PostMapping("/save/{appid}")
    public void saveConfig(@PathVariable String appid, @RequestBody ConfigAo ao){
        moonConfigService.saveConfig(appid, ao);
    }

    // 发布配置
    @PutMapping("/publish/{appid}")
    public void publish(@PathVariable String appid, @RequestBody List<String> keyList){
        moonConfigService.publish(appid, keyList);
    }

    // 删除配置
    @DeleteMapping("/{appid}/{key}")
    public void delKey(@PathVariable String appid, @PathVariable String key){
        moonConfigService.update().eq("appid", appid).eq("key", key).remove();
    }
}

