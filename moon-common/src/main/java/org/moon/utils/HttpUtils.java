package org.moon.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.squareup.okhttp.*;
import org.moon.exception.MoonBadRequestException;

import java.io.IOException;

public class HttpUtils {

    private static final OkHttpClient client = new OkHttpClient();
    private static final MediaType jsonContentType = MediaType.parse("application/json;charset=utf-8");

    private static <T> T execAndConvert(Class<T> respClass, TypeReference<T> typeReference, Request request) throws IOException {
        Response response = client.newCall(request).execute();
        try(ResponseBody body = response.body()){
            String string = body.string();
            if (respClass != null){
                return JSON.parseObject(string, respClass);
            } else if (typeReference != null){
                return JSON.parseObject(string, typeReference);
            } else {
                throw new MoonBadRequestException("返回值类型未知");
            }
        }
    }

    private static <T> T doPost(String url, Object obj, Class<T> respClass, TypeReference<T> typeReference) throws IOException {
        String json;
        if (obj == null){
            json = "";
        }else if (obj instanceof String){
            json = ((String) obj);
        } else {
            json = JSON.toJSONString(obj);
        }
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(jsonContentType, json))
                .build();

        return execAndConvert(respClass, typeReference, request);
    }

    private static <T> T doGet(String url, Class<T> respClass, TypeReference<T> typeReference) throws IOException {
        Request request = new Request.Builder().url(url).get().build();
        return execAndConvert(respClass, typeReference, request);
    }

    public static <T> T doPost(String url, Object obj, Class<T> respClass) throws IOException {
        return doPost(url, obj, respClass, null);
    }

    public static <T> T doPost(String url, Object obj, TypeReference<T> typeReference) throws IOException {
        return doPost(url, obj, null, typeReference);
    }

    public static <T> T doGet(String url, Class<T> respClass) throws IOException {
        return doGet(url, respClass, null);
    }

    public static <T> T doGet(String url, TypeReference<T> typeReference) throws IOException {
        return doGet(url, null, typeReference);
    }
}
