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

    private static final Map<String, MoonConfigBean> moonConfigBeanMap = new ConcurrentHashMap<>();

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

    public static Map<String, String> getConfig(){
        return moonConfigMap;
    }

    public static void setConfigBean(String key, Object bean, Field field, String value){
        if (moonConfigBeanMap.containsKey(key)) {
            log.error("key {} 已经存在，请勿重复设置保存", key);
            return;
        }
        moonConfigMap.put(key, value);
        MoonConfigBean configBean = new MoonConfigBean(bean, field);
        configBean.setValue(value);
        moonConfigBeanMap.put(key, configBean);
    }
}
