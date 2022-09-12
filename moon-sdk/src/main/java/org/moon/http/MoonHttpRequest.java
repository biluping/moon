package org.moon.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.moon.entity.vo.BaseVo;
import org.moon.utils.HttpUtils;

import java.io.IOException;
import java.util.HashMap;
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
    public static Map<String, Object> getPublishConfig() {
        String url = String.format("%s/config/publish/%s", serverUrl, appid);
        try {
            BaseVo<Map<String, Object>> vo = HttpUtils.doGet(url, new TypeReference<>() {});
            if (vo.getCode() == 200){
                return vo.getData();
            } else {
                log.warn("请求失败, url: {}, data:{}", url, JSON.toJSONString(vo));
                return new HashMap<>();
            }
        } catch (IOException e) {
            log.error("请求{}异常", url, e);
            return new HashMap<>();
        }
    }
}
