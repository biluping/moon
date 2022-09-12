package org.moon.controller;

import org.moon.entity.dto.MoonConfigDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/config")
public class ConfigController {

    @GetMapping("/custom/{appid}")
    public Map<String, Object> getCustomConfig(@PathVariable String appid){
        return Map.of("name", "张三", "age", 10);
    }

    @PostMapping("/upload/{appid}")
    public void uploadConfig(@PathVariable String appid, @RequestBody List<MoonConfigDto> dtoList){
        System.out.println(dtoList);
    }

}
