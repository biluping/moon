package org.moon.http;

import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.moon.entity.dto.MoonConfigDto;
import org.moon.exception.MoonRequestException;
import org.moon.utils.HttpUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
public class MoonHttpRequest {

    private static String serverUrl;
    private static String appid;

    public static void setServerUrl(String serverUrl, String appid) {
        MoonHttpRequest.serverUrl = serverUrl;
        MoonHttpRequest.appid = appid;
    }


    /**
     * 从服务端拉取自定义配置
     */
    public static Map<String, Object> getCustomConfig() {
        String url = String.format("%s/config/custom/%s", serverUrl, appid);
        try {
            return HttpUtils.doGet(url, new TypeReference<>() {});
        } catch (IOException e) {
            log.error("请求"+url+"异常", e);
            throw new MoonRequestException(e.getMessage());
        }
    }

    /**
     * 同步本地配置到服务端
     */
    public static void uploadConfig(List<MoonConfigDto> configList) {
        String url = String.format("%s/config/upload/%s", serverUrl, appid);
        try {
            HttpUtils.doPost(url, configList, Void.class);
        } catch (IOException e) {
            log.error("请求"+url+"异常", e);
            throw new MoonRequestException(e.getMessage());
        }
    }
}
