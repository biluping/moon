package org.moon.controller;

import lombok.AllArgsConstructor;
import org.moon.entity.vo.MoonNameSpaceVo;
import org.moon.service.MoonNameSpaceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/nameSpace")
public class MoonNameSpaceController {

    private MoonNameSpaceService moonNameSpaceService;

    @GetMapping("{appid}")
    public List<MoonNameSpaceVo> create(@PathVariable String appid){
        return moonNameSpaceService.listByAppid(appid);
    }

}

