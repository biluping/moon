package org.moon.component;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

/**
 * bean对象与配置字段包装类
 */
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class MoonConfigBean {

    private Object bean;

    private Field field;

    public void setValue(String value){
        try{
            field.setAccessible(true);
            field.set(bean, value);
            field.setAccessible(false);
        } catch (IllegalAccessException e) {
            log.error("moon 配置中心更新属性值失败, fieldName:{}, value:{}", field.getName(), value, e);
        }
    }
}
