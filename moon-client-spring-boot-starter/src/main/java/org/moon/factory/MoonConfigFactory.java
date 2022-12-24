package org.moon.factory;

import lombok.extern.slf4j.Slf4j;
import org.moon.component.MoonConfigBean;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 配置中心工厂，用于存放配置，更新配置的值，获取配置值
 */
@Slf4j
public class MoonConfigFactory {

    private static final Map<String, String> moonConfigMap = new ConcurrentHashMap<>();

    public static final Map<String, MoonConfigBean> moonConfigBeanMap = new ConcurrentHashMap<>();
    private static String url;

    public static void setConfig(String key, String value){
        moonConfigMap.put(key, value);
        MoonConfigBean moonConfigBean = moonConfigBeanMap.get(key);
        if (moonConfigBean != null){
            moonConfigBean.setValue(value);
        }
    }

    public static String getConfig(String key){
        return moonConfigMap.get(key);
    }

    public static String getConfig(String key, String defaultVal){
        return moonConfigMap.getOrDefault(key, defaultVal);
    }

    public static Integer getConfigInt(String key, String defaultVal){
        return Integer.parseInt(getConfig(key, defaultVal));
    }

    public static Map<String, String> getConfig(){
        return moonConfigMap;
    }

    public static void addConfigBean(String key, Object bean, Field field){
        moonConfigBeanMap.put(key, new MoonConfigBean(bean, field));
    }

    public static void setUrl(String url) {
        MoonConfigFactory.url = url;
    }

    public static String getUrl() {
        return url;
    }
}
