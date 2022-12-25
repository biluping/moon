package org.moon.controller;

import cn.hutool.core.convert.Convert;
import lombok.AllArgsConstructor;
import org.moon.entity.MoonAppEntity;
import org.moon.entity.ao.MoonAppAo;
import org.moon.entity.vo.MoonAppVo;
import org.moon.service.MoonAppService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/app")
public class MoonAppController {

    private MoonAppService moonAppService;

    @PostMapping
    public void createOrUpdate(@RequestBody MoonAppAo ao){
        moonAppService.createOrUpdateApp(ao);
    }

    @DeleteMapping("{appid}")
    public void create(@PathVariable String appid){
        moonAppService.removeByAppid(appid);
    }

    @GetMapping
    public List<MoonAppVo> getApps(){
        List<MoonAppEntity> list = moonAppService.lambdaQuery().orderByAsc(MoonAppEntity::getId).list();
        return Convert.toList(MoonAppVo.class, list);
    }

}

