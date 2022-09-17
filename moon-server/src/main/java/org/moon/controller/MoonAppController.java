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

    @PostMapping("/create")
    public void create(@RequestBody MoonAppAo ao){
        moonAppService.createApp(ao);
    }

    @GetMapping
    public List<MoonAppAo> getApps(){
        return moonAppService.list().stream().map(item -> new MoonAppAo(item.getAppid(), item.getAppUrl()))
                .collect(Collectors.toList());
    }

}

