package org.moon.controller;

import lombok.AllArgsConstructor;
import org.moon.entity.MoonConfigEntity;
import org.moon.entity.ao.ConfigAo;
import org.moon.entity.vo.MoonConfigVo;
import org.moon.service.MoonConfigService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/config")
public class MoonConfigController {

    private MoonConfigService moonConfigService;

    // 获取当前应用配置
    @GetMapping("/getByNameSpaceId/{nameSpaceId}")
    public List<MoonConfigVo> getNameSpaceConfig(@PathVariable Long nameSpaceId){
        return moonConfigService.getAppConfig(nameSpaceId);
    }

    // 获取moon配置,存储在数据库中的配置
    @GetMapping("/moon/{appid}")
    public Map<String, String> getMoonConfig(@PathVariable String appid, Integer isPublish){
        return moonConfigService.getMoonConfig(appid, isPublish);
    }

    // 增加配置
    @PostMapping("/save/{nameSpaceId}")
    public void saveConfig(@PathVariable Long nameSpaceId, @RequestBody ConfigAo ao){
        moonConfigService.saveConfig(nameSpaceId, ao);
    }

    // 发布配置
    @PutMapping("/publish/{nameSpaceId}")
    public void publish(@PathVariable Long nameSpaceId, @RequestBody List<String> keyList){
        moonConfigService.publish(nameSpaceId, keyList);
    }

    // 删除配置
    @DeleteMapping("/{nameSpaceId}/{key}")
    public void delKey(@PathVariable Long nameSpaceId, @PathVariable String key){
        moonConfigService.lambdaUpdate().eq(MoonConfigEntity::getNameSpaceId, nameSpaceId)
                .eq(MoonConfigEntity::getKey, key).remove();
    }
}

