package org.moon.entity.dto;

import lombok.Data;

import java.util.Map;

@Data
public class AppConfigDto {
    // 系统配置
    private Map<Object, Object> systemConfig;
    // 系统环境变量
    private Map<String, String> systemEnv;
    // 应用自定义配置
    private Map<String, Object> customConfig;
}
