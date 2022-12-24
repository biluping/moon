package org.moon.controller;

import lombok.AllArgsConstructor;
import org.moon.entity.ao.MoonAppAo;
import org.moon.service.MoonAppService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<MoonAppAo> getApps(){
        return moonAppService.list().stream().map(item -> new MoonAppAo(item.getAppid(), item.getHost()))
                .collect(Collectors.toList());
    }

}

