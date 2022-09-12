package org.moon.controller;

import lombok.AllArgsConstructor;
import org.moon.service.MoonConfigService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/config")
public class MoonConfigController {

    private MoonConfigService moonConfigService;

    @GetMapping("/publish/{appid}")
    public Map<String, Object> getPublishConfig(@PathVariable String appid){
        return moonConfigService.getPublishConfig(appid);
    }

    @PutMapping("/publish/{appid}/{key}")
    public void publish(@PathVariable String appid, @PathVariable String key){
        moonConfigService.publish(appid, key);
    }

}

