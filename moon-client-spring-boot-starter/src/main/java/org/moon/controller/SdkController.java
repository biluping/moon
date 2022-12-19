package org.moon.controller;

import lombok.AllArgsConstructor;
import org.moon.biz.ConfigBiz;
import org.moon.entity.dto.AppConfigDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/moon")
@AllArgsConstructor
public class SdkController {

    private ConfigBiz configBiz;

    @GetMapping("/getAppConfig")
    public AppConfigDto getAppConfig(){
        return configBiz.getAppConfig();
    }

}
