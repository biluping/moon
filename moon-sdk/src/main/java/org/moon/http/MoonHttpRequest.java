package org.moon.http;

import org.moon.dto.MoonConfigDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MoonHttpRequest {

    private static String serverUrl;

    public static void setServerUrl(String serverUrl){
        MoonHttpRequest.serverUrl = serverUrl;
    }


    /**
     * 从服务端拉取自定义配置
     */
    public static Map<String, String> getCustomConfig() {
        return new HashMap<>();
    }

    public static void uploadConfig(List<MoonConfigDto> configList) {

    }
}
