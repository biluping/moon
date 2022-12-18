package org.moon.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppConfigVo {

    // 应用自定义配置
    private Map<String, MoonConfigVo> customConfig;
    // 系统配置
    private Map<String, MoonConfigVo> systemConfig;
    // 系统环境变量
    private Map<String, MoonConfigVo> systemEnv;
}
