package org.moon.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.moon.entity.vo.BaseVo;
import org.moon.entity.vo.MoonConfigVo;
import org.moon.exception.MoonBadRequestException;
import org.moon.utils.HttpUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    public static List<MoonConfigVo> getPublishConfig() {
        String url = String.format("%s/config/moon/%s?isPublish=1", serverUrl, appid);
        try {
            BaseVo<List<MoonConfigVo>> vo = HttpUtils.doGet(url, new TypeReference<>() {});
            if (vo.getCode() == 200){
                log.info("获取moon配置成功, 数据:");
                System.out.println(JSONObject.toJSONString(vo.getData(), SerializerFeature.PrettyFormat));
                return vo.getData();
            } else {
                throw new MoonBadRequestException(JSON.toJSONString(vo));
            }
        } catch (IOException e) {
            log.error("请求异常, url: {}", url, e);
            return new ArrayList<>();
        }
    }
}
