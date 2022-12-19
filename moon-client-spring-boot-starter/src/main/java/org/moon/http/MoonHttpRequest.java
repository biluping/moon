package org.moon.http;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.moon.entity.vo.BaseVo;
import org.moon.entity.vo.MoonConfigVo;
import org.moon.exception.MoonBadRequestException;

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
            String json = HttpUtil.get(url, 5000);
            BaseVo<List<MoonConfigVo>> vo = JSON.parseObject(json, new TypeReference<BaseVo<List<MoonConfigVo>>>(){});
            if (vo.getCode() == 200){
                log.info("获取moon配置成功");
                return vo.getData();
            } else {
                throw new MoonBadRequestException(JSON.toJSONString(vo));
            }
        } catch (Exception e) {
            log.error("请求异常, url: {}", url, e);
            return new ArrayList<>();
        }
    }
}
